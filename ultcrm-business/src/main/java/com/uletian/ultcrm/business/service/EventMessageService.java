package com.uletian.ultcrm.business.service;

import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.uletian.ultcrm.business.entity.Tech;
import com.uletian.ultcrm.business.entity.Card;
import com.uletian.ultcrm.business.entity.CardBatch;
import com.uletian.ultcrm.business.entity.Config;
import com.uletian.ultcrm.business.entity.Coupon;
import com.uletian.ultcrm.business.entity.CouponBatch;
import com.uletian.ultcrm.business.entity.Customer;
import com.uletian.ultcrm.business.entity.Event;
import com.uletian.ultcrm.business.entity.EventBatch;
import com.uletian.ultcrm.business.entity.Order;
import com.uletian.ultcrm.business.entity.OrderCardCoupon;
import com.uletian.ultcrm.business.repo.TechRepository;
import com.uletian.ultcrm.business.repo.CardBatchRepository;
import com.uletian.ultcrm.business.repo.CardRepository;
import com.uletian.ultcrm.business.repo.ConfigRepository;
import com.uletian.ultcrm.business.repo.CouponBatchRepository;
import com.uletian.ultcrm.business.repo.CouponRepository;
import com.uletian.ultcrm.business.repo.CustomerRepository;
import com.uletian.ultcrm.business.repo.EventRepository;
import com.uletian.ultcrm.business.repo.OrderCardCouponRepository;
import com.uletian.ultcrm.business.repo.OrderRepository;
import com.uletian.ultcrm.business.value.CardCoupon;
import com.uletian.ultcrm.business.value.TemplateMessage;


@Service
public class EventMessageService implements MessageListener {

	private static Logger logger = Logger.getLogger(EventMessageService.class);
	
	@Qualifier("eventMessageQueue")
	@Autowired
	private Destination eventMessageQueue;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Autowired
	private CardCouponService cardCouponService;
	
	@Autowired
	private CardAuthProvide cardAuthProvide;
	
	@Autowired
	private CardBatchRepository cardBatchRepository;
	
	@Autowired
	private CouponBatchRepository couponBatchRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private CardRepository cardRepository;
	
	@Autowired
	private TechRepository techRepository;
	
	@Autowired
	private CouponRepository couponRepository;
	

	@Autowired
	private ConfigRepository configRepository;
	
	@Autowired
	private TemplateQueueService templateQueueService;
	
	@Autowired
	private OrderRepository orderRepository;
	
	
	@Autowired
	private OrderCardCouponRepository orderCardCouponRepository;
	

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd号");
	
	@Override
	public void onMessage(Message message) {
		StringWriter writer = new StringWriter();
		OutputFormat format = OutputFormat.createPrettyPrint();
		TextMessage textMessage = (TextMessage) message;
		try{
			Document doc = DocumentHelper.parseText(textMessage.getText());
			
			Element rootElement = doc.getRootElement();
			
			XMLWriter xmlwriter = new XMLWriter(writer, format);
			xmlwriter.write(doc);
			logger.info("接收事件数据\n" + writer.toString());
			
			// 解析报文
			Event event = parseEvent(rootElement);
			Customer customer = parseCustomer(rootElement);
			Tech tech = parseTech(rootElement);
			// 发放卡券
			if (cardAuthProvide.authentication(event, customer)) {
				List<CardCoupon> cardCoupons = generateCard(event, customer,tech);
				for (int i = 0; i < cardCoupons.size(); i++) {
					CardCoupon cardCoupon = cardCoupons.get(i);
					// 保存卡券
					if (EventBatch.Type.card.toString().equals(cardCoupon.getType())) {
						Card card = (Card) cardCoupon.getData();
						cardRepository.save(card);
						// 保存订单卡券关系表
						recordOrderCardCoupon(card,customer,tech);
					} else if (EventBatch.Type.coupon.toString().equals(cardCoupon.getType())) {
						Coupon coupon = (Coupon) cardCoupon.getData();
						couponRepository.save(coupon);
						// 保存订单卡券关系表
						recordOrderCardCoupon(coupon,customer,tech);
					}
					// 同步到crm
					cardCouponService.publishToCrm(cardCoupon);

				}
			}
		}catch(Exception e){
			logger.warn("解析事件报文出错" ,e);
		}
		
	}
	
	private void recordOrderCardCoupon(Card card, Customer customer, Tech tech) {
		// 定位查找到原来的订单记录
		Order order = orderRepository.findTopByCustomerAndTech(customer, tech, new Sort(Direction.DESC,"createTime"));
		OrderCardCoupon orderCardcoupon = new OrderCardCoupon();
		orderCardcoupon.setOrder(order);
		orderCardcoupon.setCardcouponId(card.getId());
		orderCardcoupon.setType("appointment");
		orderCardcoupon.setCardcouponType("card");
		orderCardCouponRepository.save(orderCardcoupon);
	}
	
	private void recordOrderCardCoupon(Coupon coupon, Customer customer, Tech tech) {
		// 定位查找到原来的订单记录
		Order order = orderRepository.findTopByCustomerAndTech(customer, tech, new Sort(Direction.DESC,"createTime"));
		OrderCardCoupon orderCardcoupon = new OrderCardCoupon();
		orderCardcoupon.setOrder(order);
		orderCardcoupon.setCardcouponId(coupon.getId());
		orderCardcoupon.setType("appointment");
		orderCardcoupon.setCardcouponType("coupon");
		orderCardCouponRepository.save(orderCardcoupon);
	}


	private void notifycationSuccess(Customer customer, CardCoupon cardCoupon) {
		Config cardConfig = configRepository.findByCode("TEMPLATE_CARD");
		Config cardConfigUrl = configRepository.findByCode("TEMPLATE_CARD_URL");
		if (cardConfig == null || cardConfigUrl == null) {
		} else {
			TemplateMessage message = new TemplateMessage();
			String name = "";
			String code = "";
			String invalidate = "";
			if (EventBatch.Type.card.toString().equals(cardCoupon.getType())) {
				Card card = (Card) cardCoupon.getData();
				name = card.getName();
				code = card.getCardNo();
				invalidate = sdf.format(card.getEndDate());
			} else if (EventBatch.Type.coupon.toString().equals(cardCoupon.getType())) {
				Coupon coupon = (Coupon) cardCoupon.getData();
				name = coupon.getName();
				code = coupon.getCouponNo();
				invalidate = sdf.format(coupon.getEndDate());
			}
			message.setOpenid(customer.getOpenid());
			message.setTemplateId(cardConfig.getValue());
			message.setUrl(cardConfigUrl.getValue());
			HashMap<String, String> params = new HashMap<String, String>(0);
			params.put("first", "恭喜您领到一张课程优惠券。");
			params.put("keyword1", name);
			params.put("keyword2", code);
			params.put("keyword3", invalidate);
			params.put("remark", "凭兑换码到店使用！");
			message.setParam(params);
			templateQueueService.sendTemplateMessage(message);
		}
	}


	private List<CardCoupon> generateCard(Event event, Customer customer,Tech tech) {
		List<CardCoupon> cardCoupons = new ArrayList<CardCoupon>(0);
		List<EventBatch> eventBatchs = event.getEventBatchs();
		for (int i = 0; i < eventBatchs.size(); i++) {
			CardCoupon cardCoupon = new CardCoupon();
			EventBatch eventBatch = eventBatchs.get(i);
			String cardType = EventBatch.Type.card.toString();
			String couponType = EventBatch.Type.coupon.toString();
			// 创建卡券信息，并同步到CRM
			if (cardType.equals(eventBatch.getBatchType())) {
				CardBatch cardBatch = cardBatchRepository.findOne(eventBatch.getBatchId());
				Card card = cardCouponService.create(cardBatch, customer,tech);
				cardCoupon.setType(cardType);
				card.setEvent(event);
				cardCoupon.setData(card);
			} else if (couponType.equals(eventBatch.getBatchType())) {
				CouponBatch couponBatch = couponBatchRepository.findOne(eventBatch.getBatchId());
				Coupon coupon = cardCouponService.create(couponBatch, customer,tech);
				coupon.setEvent(event);
				cardCoupon.setType(couponType);
				cardCoupon.setData(coupon);
			}
			cardCoupons.add(cardCoupon);

		}
		return cardCoupons;
	}

	private Customer parseCustomer(Element rootElement) {
		Element customerElement = rootElement.element(new QName("customer"));
		String idStr = customerElement.elementText(new QName("id"));
		Customer customer = customerRepository.findOne(Long.decode(idStr));
		return customer;
	}
	
	private Tech parseTech(Element rootElement) {

		Tech tech = null;
		try {
			Element techElement = rootElement.element(new QName("tech"));
			String idStr = techElement.elementText(new QName("id"));
			tech = techRepository.findOne(Long.decode(idStr));
		} catch (Exception e) {
		}

		return tech;
	}

	private Event parseEvent(Element rootElement) {
		String idStr = rootElement.elementText(new QName("id"));
		Event event = eventRepository.findOne(Long.decode(idStr));
		return event;
	}
	
	public boolean sendEvent(Event event, Customer customer, Tech tech){
		jmsTemplate.send(eventMessageQueue, new EventMessageCreator(encode(event, customer, tech)));
		return true;
	}
	
	public boolean sendEvent(String eventCode, Long customerid, Long techid){
		boolean result = false;
		Event event = eventRepository.findEventByCode(eventCode);
		Customer customer = customerid!=null ?customerRepository.findOne(customerid):null;
		Tech tech = techid!=null ?techRepository.findOne(techid):null;

		if (event == null) {
			logger.warn("找不到事件或者客户或者课程信息\nevent=" + event);
		} else {
			result = sendEvent(event, customer, tech);
		}
		return result;
	}
	
	private String encode(Event event, Customer customer, Tech tech){
		Document doc = DocumentHelper.createDocument();
		Namespace namespace = new Namespace("ns0", "http://ultcrm/91jpfw.cn");
		Element root = doc.addElement(new QName("event", namespace));
		
		root.addElement(new QName("id")).addText(event.getId().toString());
		

		Element customerElement = root.addElement(new QName("customer"));
		if (customer != null) {
			customerElement.addElement(new QName("id")).addText(customer.getId().toString());
		}
		
		Element techElement = root.addElement(new QName("tech"));
		if (tech != null) {
			techElement.addElement(new QName("id")).addText(tech.getId().toString());
		} else {
			techElement.addElement(new QName("id")).addText("");

		}

		return doc.asXML();
	}
	
	private class EventMessageCreator implements MessageCreator {

		private String message;

		public EventMessageCreator(String message) {
			this.message = message;
		}

		@Override
		public Message createMessage(Session session) throws JMSException {
			TextMessage msg = session.createTextMessage();
			msg.setText(message);
			return msg;
		}
	}
}