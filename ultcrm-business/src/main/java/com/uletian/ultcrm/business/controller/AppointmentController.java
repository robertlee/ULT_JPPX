package com.uletian.ultcrm.business.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.uletian.ultcrm.business.entity.Appointment;
import com.uletian.ultcrm.business.entity.BusinessType;
import com.uletian.ultcrm.business.entity.Tech;
import com.uletian.ultcrm.business.entity.TechModel;
import com.uletian.ultcrm.business.entity.Customer;
import com.uletian.ultcrm.business.entity.Event;
import com.uletian.ultcrm.business.entity.MessageTemplate;
import com.uletian.ultcrm.business.entity.Order;

import com.uletian.ultcrm.business.entity.Store;

import com.uletian.ultcrm.business.entity.TimeSegment;
import com.uletian.ultcrm.business.repo.AppointmentRepository;
import com.uletian.ultcrm.business.repo.BusinessTypeRepository;
import com.uletian.ultcrm.business.repo.TechModelRepository;
import com.uletian.ultcrm.business.repo.TechRepository;
import com.uletian.ultcrm.business.repo.CardRepository;
import com.uletian.ultcrm.business.repo.ConfigRepository;
import com.uletian.ultcrm.business.repo.CustomerRepository;
import com.uletian.ultcrm.business.repo.EventRepository;
import com.uletian.ultcrm.business.repo.MessageTemplateRepository;

import com.uletian.ultcrm.business.repo.OrderRepository;
import com.uletian.ultcrm.business.repo.StoreRepository;

import com.uletian.ultcrm.business.repo.TimeSegmentRepository;
import com.uletian.ultcrm.business.service.CustomerInfoSyncService;
import com.uletian.ultcrm.business.service.EventMessageService;
import com.uletian.ultcrm.business.service.OrderMessageServcie;
import com.uletian.ultcrm.business.service.SmsQueueService;
import com.uletian.ultcrm.business.service.TemplateQueueService;
import com.uletian.ultcrm.business.service.WeixinConfig;
import com.uletian.ultcrm.business.value.TemplateMessage;

@RestController
public class AppointmentController {
	
	private static Logger logger = Logger.getLogger(AppointmentController.class);
	
	@Autowired
	private TechModelRepository techModelRepository;
	
	@Autowired
	private BusinessTypeRepository businessTypeRepository;
	
	@Autowired
	private AppointmentRepository appointmentRepository;
	
	@Autowired
	private StoreRepository storeRepository;
	
	@Autowired
	private TechRepository techRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	
	@Autowired
	private TimeSegmentRepository timeSegmentRepository;
	
	@Autowired
	private OrderMessageServcie orderMessageServcie;
	
	@Autowired
	private TemplateQueueService templateQueueService;
	
	@Autowired
	private CustomerInfoSyncService customerInfoSyncService;
	
	@Autowired
	private SmsQueueService smsQueueService;
	
	@Autowired
	private EventMessageService eventMessageService;
	
	@Autowired
	private ConfigRepository configRepository;
	
	@Autowired
	private WeixinConfig weixinConfig;
	
	@Autowired
	private MessageTemplateRepository messageTemplateRepository;
	
	@Autowired
	private CardRepository cardRepository;
	
	@Autowired
	private EventRepository eventRepository;
	
	/*data:{"customerId":customer.id,	"techId":"",	"courseId":appointData.courseId,
	"modelId":appointData.modelId,	"discountPrice":"100",
	"price":"100",	"typeId":"1",	"segmentDate":appointData.segmentDate,
	"segmentTime":appointData.segmentTime,	"packageId":appointData.courseId, 
	"storeId":appointData.storeId}*/
	@RequestMapping(value="/createAppointment",method=RequestMethod.POST)
	public Map<String,Object> createAppointment(
			@RequestBody Map<String,String> appointmentData,
			HttpServletRequest request, 
			HttpServletResponse response){
		HashMap<String,Object> result = new HashMap<String,Object>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String customerId = appointmentData.get("customerId");
		String busiTypeId = appointmentData.get("busiTypeId");
		String techId = appointmentData.get("techId");
		String modelId = appointmentData.get("modelId");
		String totalPrice = appointmentData.get("totalPrice");
		String discountTotalPrice = appointmentData.get("discountTotalPrice");
		String segmentDate = appointmentData.get("segmentDate");
		Date segmDate = null;

		try
		{
			segmDate = dateFormat.parse(segmentDate);
		}catch(Exception e)
		{
			result.put("msg", "date format error,please select.");
			result.put("code", "error");		
			return result;
		}
		
		String typeId = appointmentData.get("typeId");	
		String segmentTime = appointmentData.get("segmentTime");
		String storeId = appointmentData.get("storeId");
		
		// 进行数据校验
		if(storeId == null || busiTypeId == null || modelId == null || customerId == null || 
				segmentDate == null || segmentTime == null || techId == null)
		{
			result.put("msg", "提供的信息不全，无法创建课程的预约单!");
			logger.error("创建课程的预约单的信息不全： " + "storeId = " + storeId + " ,busitypeid = " + busiTypeId + 
					", modelId = " + modelId + " customerId =" + customerId + " techId = " + techId);
			result.put("code", "error");
			return result;
		}
		
		//客户信息
		Customer customer = null;
		if (StringUtils.isNotBlank(customerId)) {
			customer = customerRepository.findOne(Long.valueOf(customerId));
		}

		Tech tech = null;
		TechModel techModel = null;
		if (StringUtils.isNotBlank(techId)) {
			tech = techRepository.findOne(Long.valueOf(techId));
		}
		if(tech != null)
		{
			
		}else {
			// 创建tech对象
			techModel = techModelRepository.findOne(Long.valueOf(modelId));
			tech = new Tech();
			tech.setTechModel(techModel);
			tech.setCustomer(customer);
			techRepository.save(tech);
		}
		
		//首先校验时段相关问题
		TimeSegment timeSegm = this.updateTimeSegmentCount(Long.valueOf(storeId),
				                                     Long.valueOf(busiTypeId),
				                                     segmDate, Long.valueOf(segmentTime));
		//创建order
		Order order = new Order();
		order.setCustomer(customer);
		order.setPrice(new BigDecimal(StringUtils.isEmpty(totalPrice)?"0":totalPrice));
		order.setDiscountprice(new BigDecimal(StringUtils.isEmpty(discountTotalPrice)?"0":discountTotalPrice));
		order.setTypeid(Long.valueOf(typeId));
		order.setStatus(1);
		order.setTech(tech);
		orderRepository.save(order);
		
		BusinessType bt = businessTypeRepository.findOne(Long.decode(busiTypeId));		
		order.setDescription(bt.getName()); //订单的描述是业务类型名称				
		Appointment appointment = new Appointment();
		
		Store store = null;
		store = storeRepository.findOne(Long.valueOf(storeId));
		
		appointment.setStore(store);
		appointment.setTimeSegment(timeSegm);
		appointment.setOrder(order);
		appointment.setCustomerId(customer.getId());
		appointmentRepository.save(appointment);
		
        // 创建订单到crm， 目前定为到深圳宝安沙井中心
		String cName = "";
		
		if(customer.getName() == null || customer.getName().trim().equals(""))
		{
			cName = customer.getNickname();
		}else{
			cName = customer.getName(); 
		}
		
		if(cName == null || "".equals(cName.trim()))
		{
			cName = "newuser";
		}
		
		tech.fillOtherFields();
		String content = "预约单创建成功,课程名称:"+ tech.getCourseName()+ "；课程等级：" + bt.getName()+ ";预约时间：" + segmentDate + " " + segmentTime + ":00";			
		smsQueueService.sendMessage(customer.getPhone(), content, null, false);

		
		// 判断预约初级服务是否需要送卡
		Boolean hasCard = hasCard(bt.getId(), tech, customer);

		notifycationSuccess(order.getId(), customer, tech.getTechlevelno(), bt, store.getName(), segmentDate + " " +segmentTime + ":00", hasCard);
		
		result.put("msg", "createorderok"); //关键信息
		result.put("code", "appook");   //关键信息
		
		result.put("orderId", order.getId().toString());
		result.put("hasCard",hasCard);
		
		//logger.info("【快乐语文】预约单创建成功：课程编码 " + tech.getTechlevelno() + " 业务类型: " + bt.getName() + " 客户名称： " + customer.getName() + " 时间: " + segmentDate +  segmentTime);
		return result;
	}
	
	private boolean hasCard(Long businessid, Tech tech, Customer customer) {
		if (businessid == 4) {
			// 先判断这个技能有没有卡
			Long count = cardRepository.countByCustomerAndTechAndStatusAndType(customer, tech, "002", "W");
			if (count > 0) {
				return false;
			} else {
				// 再判断这个人有没有卡
				count = cardRepository.countByCustomerAndStatusAndType(customer,  "002", "W");
				return count>0?false:true;
			}
		}
		else {
			return true;
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

	private void notifycationSuccess(Long id, Customer customer, String techlevelno, BusinessType bt, String storeName,
			String datatime, boolean hasCard) {
		TemplateMessage messageValue = new TemplateMessage();
		MessageTemplate messageTemplate = templateQueueService.getMessageTemplate("appointment_success");
		
		if (messageTemplate == null) {
			logger.warn("找不到消息对应的模板");
		}else{
			HashMap<String, String> param = new HashMap<String, String>(0);
			String smsContent = "";
			String first = "您好,您的预约已成功登记";
			smsContent += first;
			//Event用于发放卡劵使用EVENT
			Event event = null;
			if (hasCard) {
				//业务类型·
				if (bt.getId() == 1) {
					event = eventRepository.findEventByCode("appointment_business1");
					first += ",点击详情领取" + event.getDescription();
					smsContent += ",请在微信公众号中领取" + event.getDescription();;
				} else if (bt.getId() == 2) {
					event = eventRepository.findEventByCode("appointment_business2");
					first += ",点击详情领取" + event.getDescription();
					smsContent += ",请在微信公众号中领取" + event.getDescription();
				} else if (bt.getId() == 3) {
					event = eventRepository.findEventByCode("appointment_business3");
					first += ",点击详情领取" + event.getDescription();
					smsContent += ",请在微信公众号中领取" + event.getDescription();;
				} else if (bt.getId() == 4) {
					event = eventRepository.findEventByCode("appointment_business4");
					first += ",点击详情领取" + event.getDescription();
					smsContent += ",请在微信公众号中领取" + event.getDescription();;
				}else if (bt.getId() == 5) {
					event = eventRepository.findEventByCode("appointment_business5");
					first += ",点击详情领取" + event.getDescription();
					smsContent += ",请在微信公众号中领取" + event.getDescription();;
				}else if (bt.getId() == 6) {
					event = eventRepository.findEventByCode("appointment_business6");
					first += ",点击详情领取" + event.getDescription();
					smsContent += ",请在微信公众号中领取" + event.getDescription();;
				}				
			}
			//robert Lee ULeTian 2016-05-12

			/*
			{{first.DATA}}
			课程技能号：{{keyword1.DATA}}
			服务类型：{{keyword2.DATA}}
			预约店铺：{{keyword3.DATA}}
			预约时间：{{keyword4.DATA}}
			{{remark.DATA}}
			*/
			first += "\n服务单号："+id;
			param.put("first", first);
			param.put("keyword1", techlevelno);
			param.put("keyword2", bt.getName());
			param.put("keyword3", storeName);
			param.put("keyword4", datatime);
			param.put("remark", "\n欢迎您使用，客服电话：13367006212！" );
			
			smsContent += "课程名称："+bt.getName()+"("+storeName+")"+"开课时间："+datatime;		
			smsQueueService.sendMessage(customer.getPhone(), smsContent, null, false);
			//smsQueueService.sendMessage("13367006212", smsContent, null, false);
			messageValue.setOpenid(customer.getOpenid());
			messageValue.setTemplateId(messageTemplate.getTmpid());
			messageValue.setParam(param);
			
			messageValue.setUrl(messageTemplate.makeUrl(weixinConfig,hasCard?id.toString():null));
			templateQueueService.sendTemplateMessage(messageValue);
		}

	}


	/**
	 * 更新或创建时间段
	 * @param storeId
	 * @param busiTypeId
	 * @param segmentDate
	 * @param timeSegment
	 * @return
	 */
	private TimeSegment updateTimeSegmentCount(Long storeId,Long busiTypeId,Date segmentDate, Long timeSegment ) {
		
		//timeSegment
		Store store = storeRepository.findOne(storeId);
				
		TimeSegment timeSegmentOld = timeSegmentRepository.findByStoreAndBusiTypeIdAndDateSegmentAndTimeSegment(store, 
						                                                         busiTypeId,
						                                                         segmentDate, 
						                                                         timeSegment);
		if (timeSegmentOld !=null) {
					timeSegmentOld.setCount(timeSegmentOld.getCount()+1);
					timeSegmentRepository.save(timeSegmentOld);
					return timeSegmentOld;
				}
				else {
					TimeSegment timeSegm = new TimeSegment();					
					timeSegm.setCount(1L);
					timeSegm.setTimeSegment(timeSegment);
					timeSegm.setDateSegment(segmentDate);
					timeSegm.setBusiTypeId(busiTypeId);
					timeSegm.setStore(store);
					timeSegmentRepository.save(timeSegm);
					return timeSegm;
				}
	}
	
	//校验是否重复预约
	public boolean checkAppointExist(Long customerId,Long businessTypeId,
			                         Date segmentDate,Long timeSegment,
			                         Long storeId,Long techId) {
		
		
		Store store = storeRepository.findOne(storeId);
		TimeSegment timeSegm = timeSegmentRepository.findByStoreAndBusiTypeIdAndDateSegmentAndTimeSegment(store, 
				businessTypeId,
                segmentDate, 
                timeSegment);
		
		if(timeSegm == null)
		{
			return false;
		}
		
		//查看预约单
		List<Appointment> appointments = appointmentRepository.findByStoreAndTimeSegmentAndTech(storeId,timeSegm.getId(),techId);
		if(appointments == null || appointments.isEmpty())
		{
			return false;  //不存在预约，可以预约
		}
	
		return true;   //  此客户已经存在预约了。
		
	 }
	
	    // 校验是否重复预约同一个类型
	    @RequestMapping("/getTimeSegmentByTechAndStatusAndBusiTypeId/{techId}/{busiTypeId}")
		public Map<String,String> getTimeSegmentByTechAndStatusAndBusiTypeId(@PathVariable("techId")Long techId,
                @PathVariable("busiTypeId")Long busiTypeId) {
			HashMap<String,String> result = new HashMap<String,String>();

			result.put("msg", "");
			result.put("code", "");
			
			Tech tech = techRepository.findOne(techId);
			
			//状态为1的订单， 是正在接受预约但为进行的。
			List<Integer> statusList = new ArrayList<Integer>();
			statusList.add(1);
			statusList.add(2);
			List<Order> orders = orderRepository.findByTechAndStatusIn(tech, statusList);
			
			if(orders == null || orders.size() <= 0)
			{
				return result;
			}
			

			List<TimeSegment> tss = new ArrayList<TimeSegment>();
			
			for(Order od : orders)
			{
				Appointment at = od.getAppointment();
				if (at != null) {
					tss.add(at.getTimeSegment());
				}
			}
			
			if(tss == null || tss.size() <= 0)
			{
    			return result;
			}
			
			for(TimeSegment ts : tss)
			{
				if(busiTypeId == ts.getBusiTypeId())
				{
					result.put("msg", "existorder");
	    			result.put("code", "existorder");
				}
			}
			return result;
	}
	    
	/**
	 * 创建crm预约单
	 * @param custName
	 * @param custPhone
	 * @param sdate
	 * @param stime
	 * @param orderId
	 * @param storeId
	 * @param descrip
	 */
	public void createOrderToCrm(String customerId,String custName,String custPhone,String sdate,String stime,
			Long orderId,String storeId,String descrip,String platNo,String businessTypeId)
	{
		StringBuffer xmlstrbuf = orderMessageServcie.orderStatusInfoJAVAToXML(customerId,custName, 
				custPhone, sdate, stime, 
				orderId, storeId, descrip,platNo,"CREATE_ORDER",businessTypeId);
		
		//发送消息
		orderMessageServcie.sendMessage(xmlstrbuf.toString());
	}
}