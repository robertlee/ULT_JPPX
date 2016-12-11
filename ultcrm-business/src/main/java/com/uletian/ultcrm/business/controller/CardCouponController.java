/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.business.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.uletian.ultcrm.business.entity.Appointment;
import com.uletian.ultcrm.business.entity.BusinessType;
import com.uletian.ultcrm.business.entity.Tech;
import com.uletian.ultcrm.business.entity.Card;
import com.uletian.ultcrm.business.entity.CardBatch;
import com.uletian.ultcrm.business.entity.CardConsume;
import com.uletian.ultcrm.business.entity.Coupon;
import com.uletian.ultcrm.business.entity.CouponBatch;
import com.uletian.ultcrm.business.entity.Customer;
import com.uletian.ultcrm.business.entity.MessageTemplate;
import com.uletian.ultcrm.business.entity.Order;
import com.uletian.ultcrm.business.entity.OrderCardCoupon;
import com.uletian.ultcrm.business.entity.Store;
import com.uletian.ultcrm.business.entity.TimeSegment;
import com.uletian.ultcrm.business.repo.BusinessTypeRepository;
import com.uletian.ultcrm.business.repo.TechRepository;
import com.uletian.ultcrm.business.repo.CardBatchRepository;
import com.uletian.ultcrm.business.repo.CardRepository;
import com.uletian.ultcrm.business.repo.CouponBatchRepository;
import com.uletian.ultcrm.business.repo.CouponRepository;
import com.uletian.ultcrm.business.repo.CustomerRepository;
import com.uletian.ultcrm.business.repo.OrderCardCouponRepository;
import com.uletian.ultcrm.business.repo.OrderRepository;
import com.uletian.ultcrm.business.service.AppointmentService;
import com.uletian.ultcrm.business.service.EventMessageService;
import com.uletian.ultcrm.business.service.SmsQueueService;
import com.uletian.ultcrm.business.service.TemplateQueueService;
import com.uletian.ultcrm.business.service.WeixinConfig;
import com.uletian.ultcrm.business.value.CardCoupon;
import com.uletian.ultcrm.business.value.TemplateMessage;

/**
 * 
 * @author robertxie 
 * 2016年11月16日
 */
@RestController
public class CardCouponController {
	
	@Autowired
	private CouponRepository couponRepository;

	@Autowired
	private CouponBatchRepository couponBatchRepository;
	
	@Autowired
	private CardRepository cardRepository;
		
	@Autowired
	private CardBatchRepository cardBatchRepository;
		
	@Autowired
	private OrderRepository orderRepository;
		
	@Autowired
	private OrderCardCouponRepository orderCardCouponRepository;
	
	@Autowired
	private AppointmentService appointmentService;
	
	@Autowired
	private CustomerRepository customerRepository;
		
	@Autowired
	private TechRepository techRepository;
	
	@Autowired
	private BusinessTypeRepository businessTypeRepository;
	
	@Autowired
	private TemplateQueueService templateQueueService;
	
	@Autowired
	private EventMessageService eventMessageService;
	
	@Autowired
	private SmsQueueService smsQueueService;
	
	@Autowired
	private WeixinConfig weixinConfig;
	
	private static Logger logger = Logger.getLogger(CardController.class);
	 
	/**
	 * 检查一下这个订单是否已经被发券, true 表示预约已经发发送券了， false 表示这个订单没有发送券
	 */
	@RequestMapping(value="/checkAppointmentCouponSent/{orderId}", method=RequestMethod.GET)
	public Boolean checkAppointmentCouponSent(@PathVariable("orderId")Long orderId) {
		Order order = orderRepository.findOne(orderId);
		logger.error("order" );
		List<OrderCardCoupon> list = orderCardCouponRepository.findByOrder(order);
		if (CollectionUtils.isNotEmpty(list)) {
			logger.error("this tech has the card，orderId ="+orderId);
			return true;
		}
		else {
			return false;
		}
	} 
	
	
	// 领取卡和优惠券
	@RequestMapping(value="/receiveCoupon/{orderId}/{customerId}", method=RequestMethod.GET)
	public Map<String,String> receiveCoupon(@PathVariable("orderId")Long orderId,@PathVariable("customerId")Long customerId) {
		Map<String,String> result = new HashMap<String,String>();
		// 首先检查
		if (checkAppointmentCouponSent(orderId)) {
			result.put("result", "duplicate");
			logger.error("This technic has already some cards，orderId ="+orderId);
			return result;
		}
		
		// 首先通过orderId获取一些参数
		Order order = orderRepository.findOne(orderId);
		Appointment appointment = order.getAppointment();
		TimeSegment segment = appointment.getTimeSegment();
		Long businessTypeId = segment.getBusiTypeId();
		Tech tech = order.getTech();
		
		Customer customer = order.getCustomer();
		if (customerId.intValue() != customer.getId().intValue() && !customerId.toString().trim().equals(customer.getId().toString().trim())) {
			result.put("result", "fail");
			logger.error("the customer is error，orderId ="+orderId+", customer is is "+customerId+", custoemr id of order is"+customer.getId());
			return result;
		}
		
		boolean hasCard = sendEvent(customer.getId(),tech.getId(), businessTypeId);


		BusinessType  bt = businessTypeRepository.findOne(businessTypeId);
		Store store = order.getAppointment().getStore();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		result.put("result", "success");
		return result;
	}
	

	private void notifycationSuccess(Long id, Customer customer, String techlevelno, BusinessType bt, String storeName,
			String datatime, boolean hasCard) {
		TemplateMessage messageValue = new TemplateMessage();
		MessageTemplate messageTemplate = templateQueueService.getMessageTemplate("appointment_success");
		
		if (messageTemplate == null) {
			logger.error("db can't find card and coupon msg template");
		}else{
			HashMap<String, String> param = new HashMap<String, String>(0);
			String smsContent = "";
			String first = "您好,您的预约已成功登记";
			smsContent += first;
			if (hasCard) {
				if (bt.getId() == 4) {
					first += ",点击详情领取1次试听卡。";
					smsContent += ",请在微信公众号中领取价值1次试听卡。";
				} else if (bt.getId() == 9) {
					first += ",点击详情领取价值100元学时券。";
					smsContent += ",请在微信公众号中领取价值100元学时券。";
				} else if (bt.getId() == 10) {
					first += ",点击详情领取价值100元学时券。";
					smsContent += ",请在微信公众号中领取价值100元学时券。";
				} else if (bt.getId() == 11) {
					first += ",点击详情领取价值100元学时券。";
					smsContent += ",请在微信公众号中领取价值100元学时券。";
				}
			}
			

			//robert Lee ULeTian 2016-05-12
			/*			
			first += "\n服务单号：" + id;
			param.put("first", first);
			param.put("keyword1", techlevelno);
			param.put("keyword2", bt.getName());
			param.put("keyword3", storeName);
			param.put("keyword4", datatime);
			param.put("remark", "\n服务热线：13367006212" );
			
			smsContent += "\n服务单号：" + id;
			smsContent += "\n课程号：" + techlevelno;
			smsContent += "\n服务类型：" + bt.getName();
			smsContent += "\n预约中心：" + storeName;
			smsContent += "\n预约时间：" + datatime;
			smsContent += "\n服务热线：13367006212";			
			*/
			/*
			{{first.DATA}}
			课程名称：{{keyword1.DATA}}
			教练姓名：{{keyword2.DATA}}
			教练电话：{{keyword3.DATA}}
			开课时间：{{keyword4.DATA}}			
			{{remark.DATA}}
			*/
			first += "\n服务单号："+id;
			param.put("first", first);
			param.put("keyword1", techlevelno);
			param.put("keyword2", bt.getName());
			param.put("keyword3", storeName);
			param.put("keyword4", datatime);
			param.put("remark", "\n欢迎您使用，客服电话：13367006212！" );
			
			smsContent += "课程名称："+bt.getName()+"("+storeName+")"+" 开课时间："+datatime;			
			smsQueueService.sendMessage(customer.getPhone(), smsContent, null, false);
			//smsQueueService.sendMessage("13367006212", smsContent, null, false);
			
			messageValue.setOpenid(customer.getOpenid());
			messageValue.setTemplateId(messageTemplate.getTmpid());
			messageValue.setParam(param);			
			messageValue.setUrl(messageTemplate.makeUrl(weixinConfig,null));
			templateQueueService.sendTemplateMessage(messageValue);
		}

	}
	
	
	private boolean sendEvent(Long customerid,Long techid, Long businessid) {
		String businessStr = "";
		switch (businessid.intValue()) {
		case 1:
			businessStr = "appointment_business1";			
			break;
		case 2:
			businessStr = "appointment_business2";
			break;
		case 3:
			businessStr = "appointment_business3";
			break;
		case 4:
			businessStr = "appointment_business4";
			break;
		case 5:
			businessStr = "appointment_business5";
			break;
		case 6:
			businessStr = "appointment_business6";
			break;						
		default:
			break;
		}
		return eventMessageService.sendEvent(businessStr, customerid, techid);
	}
	
	
	// 获取指定的优惠券批次信息
	@RequestMapping(value="/getCardCouponBatch/{type}/{batchId}", method=RequestMethod.GET)
	public CouponBatch  getCouponBatch(@PathVariable("type")String type,@PathVariable("batchId")Long batchId){
		CouponBatch batch = couponBatchRepository.findOne(batchId);
		return batch;
	}
	
	// 获取cardCouponBatch 通过orderId
	@RequestMapping(value="/getCardCouponBatchByOrderId/{orderId}", method=RequestMethod.GET)
	public CardCoupon  getCardCouponBatchByOrderId(@PathVariable("orderId")Long orderId){
		CardCoupon batch = null; 
		Order order = orderRepository.findOne(orderId);
		Appointment appointment = order.getAppointment();
		TimeSegment ts = appointment.getTimeSegment();
		Long btId = ts.getBusiTypeId();
		batch =  appointmentService.getCardCouponBatchForThisAppointment(btId);
		return batch;
	}
	
	
	// 获取指定的卡券相关信息
	@RequestMapping(value="/getCardCouponDetail/{type}/{id}", method=RequestMethod.GET)
	public Map getCardCouponDetail(@PathVariable("type")String type,@PathVariable("id")Long id) {
		Map result = new HashMap();
		CardCoupon cardCoupon = getCardCouponBatch(type,id);
		List<CardConsume> consumeList = getCardCouponRecord(type,id);
		result.put("batch", cardCoupon);
		result.put("consumeList", consumeList);
		return result;
	}
	
	@RequestMapping(value="/getCardCouponBatch/{type}/{id}", method=RequestMethod.GET)
	public CardCoupon getCardCouponBatch(@PathVariable("type")String type,@PathVariable("id")Long id) {
		CardCoupon batch = new CardCoupon();
		
		if (type.equalsIgnoreCase("card") || type.equalsIgnoreCase("cardBatch")) {
			CardBatch cardBatch = null;
			if (type.equalsIgnoreCase("card")) {
				Card card = cardRepository.findOne(id);
				cardBatch = card.getCardBatch();
			}
			else {
				cardBatch = cardBatchRepository.findOne(id);
			}
			BeanUtils.copyProperties(cardBatch, batch);
			batch.setSubType("card");
		}
		
		if (type.equalsIgnoreCase("coupon") || type.equalsIgnoreCase("couponBatch")) {
			CouponBatch couponBatch= null;
			if (type.equalsIgnoreCase("coupon")) {
				Coupon coupon = couponRepository.findOne(id);
				couponBatch = coupon.getCouponBatch();
			}
			else {
				couponBatch  = couponBatchRepository.findOne(id);
			}
			BeanUtils.copyProperties(couponBatch, batch);
			batch.setSubType("coupon");
		}
		
		return batch;
	}
	
	@RequestMapping(value="/getCardCouponRecord/{type}/{id}", method=RequestMethod.GET)
	public List<CardConsume> getCardCouponRecord(@PathVariable("type")String type,@PathVariable("id")Long id) {
		List<CardConsume> result = new ArrayList<CardConsume>();
		if (type.equalsIgnoreCase("card") ) {
			Card card = cardRepository.findOne(id);
			result = card.getCardConsumes();
			if (result != null) {
				for (CardConsume consume :result) {
					consume.setOrderNo(consume.getOrder()!=null?consume.getOrder().getId().toString():"");
					consume.setStoreName(consume.getStore()!=null?consume.getStore().getName():"");
				}
			}
		}
		else if (type.equalsIgnoreCase("coupon") ) {
			Coupon coupon = couponRepository.findOne(id);
			// 已核销
			if (coupon.getStatus().equalsIgnoreCase(Coupon.STATUS_WRITEOFF)) {
				CardConsume consume = new CardConsume();
				consume.setTime(coupon.getLastUpdateTime());
				consume.setStoreName("");
				consume.setOrderNo("");
				result.add(consume);
			}
		}
		return result;
	}	
}
