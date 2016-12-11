
package com.uletian.ultcrm.business.service;


import java.util.HashMap;

import javax.jms.JMSException;
import javax.jms.Message;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.stereotype.Component;


import com.uletian.ultcrm.business.entity.Tech;
import com.uletian.ultcrm.business.entity.Customer;
import com.uletian.ultcrm.business.entity.MessageTemplate;
import com.uletian.ultcrm.business.entity.Order;
import com.uletian.ultcrm.business.entity.Store;
import com.uletian.ultcrm.business.repo.TechRepository;
import com.uletian.ultcrm.business.repo.CustomerRepository;
import com.uletian.ultcrm.business.repo.MessageTemplateRepository;
import com.uletian.ultcrm.business.repo.OrderRepository;
import com.uletian.ultcrm.business.repo.StoreRepository;
import com.uletian.ultcrm.business.value.OrderMessage;
import com.uletian.ultcrm.business.value.TemplateMessage;
import com.thoughtworks.xstream.XStream;

@Component
public class OrderMessageServcie {
	
	public static String ACTION_UPDATE_ORDER_STATUS= "UPDATE_ORDER_STATUS";  //同步动作： 更新订单状态信息
	
	public static String ACTION_UPDATE_ORDER_STATUS_FROMWORK= "UPDATE_ORDER_STATUS_FROMWORK";  //同步动作： 更新订单状态信息
	public static String ACTION_CANCEL_ORDER = "CANCEL_ORDER";  //同步动作: 取消预约单
	public static String ACTION_UPDATE_ORDER_DATETIME = "UPDATE_ORDER_DATETIME";  //同步动作: 更新预约单时间
	
	
	
	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private TechRepository techRepository;
	
	@Autowired
	private  JmsTemplate topicJmsTemplate;
	
	@Autowired
	private MessageTemplateRepository messageTemplateRepository;
	
	@Autowired
	private WeixinConfig weixinConfig;
	
	@Autowired
	private TemplateQueueService templateQueueService;
	
	@Autowired
	private SmsQueueService smsQueueService;
	
	@Autowired
	private StoreRepository storeRepository;
	
	private static Logger logger = Logger.getLogger(OrderMessageServcie.class);
	
	//@JmsListener(destination = "someQueue")
    public void processMessage(String content) {
		//System.out.println("receive msg -----------------------");
        //System.out.println(""+content);
    }
	 
	
	public void sendMessage(String msg) {
			 
		//topicJmsTemplate.convertAndSend("ICCRMORDER", msg);
		logger.info("Send a order message to crm system, msg is "+msg);
		topicJmsTemplate.convertAndSend("ICCRMORDER", msg, new MessagePostProcessor() {
			public Message postProcessMessage(Message message)
					throws JMSException {
				message.setStringProperty("SENDER", "ULTCRM");
				message.setStringProperty("ACTION", "CREATE_ORDER");
				return message;
			}
		});

	}
	

	

	
	/**
	 * 2015-10-05,liuhua
	 * 把order infoxml转换成order info对象
	 * @param orderMsg
	 */
	public void orderInfoXMLToJAVA(OrderMessage orderMsg)
	{	
		
	}
	
	/**
	 * 2015-10-05,liuhua
	 * 把order xml转换成order status info对象
	 * @param orderMsg
	 */
	public OrderMessage orderXMLToJAVA(String orderMessage)
	{
		int startIndex = orderMessage.indexOf("<action>");
        int endIndex = orderMessage.indexOf("</ns0");    
        orderMessage = orderMessage.substring(startIndex, endIndex);   
        orderMessage = "<order>" + orderMessage +"</order>";
		XStream xStream = new XStream();
		xStream.alias("order", OrderMessage.class);
		OrderMessage orderMsg = (OrderMessage)xStream.fromXML(orderMessage);
		return orderMsg;	
	}
	
	/**
	 * 2015-10-05,liuhua
	 * 把order xml转换成order status info对象
	 * @param orderMsg
	 */
	public OrderMessage orderStatusInfoXMLToJAVA(String orderMessage)
	{
		int startIndex = orderMessage.indexOf("<action>");
        int endIndex = orderMessage.indexOf("</ns0");    
        orderMessage = orderMessage.substring(startIndex, endIndex);   
        orderMessage = "<order>" + orderMessage +"</order>";
		XStream xStream = new XStream();
		xStream.alias("order", OrderMessage.class);
		OrderMessage orderMsg = (OrderMessage)xStream.fromXML(orderMessage);
		return orderMsg;	
	}
	
	/**
	 * 2015-10-05,liuhua
	 * 把order status relation infoxml转换成order status info对象
	 * @param orderMsg
	 */
	public StringBuffer orderStatusInfoJAVAToXML(String customerId,String custName,String custPhone,String sdate,String stime,
			Long orderId,String storeId,String descrip,String platno,String action,String businessTypeId)
	{
		//转换时间格式
		sdate = sdate.replace("-", "");
		if(stime.length() == 1)
		{
			stime = "0" + stime + "0000";
		}else {
			stime = stime + "0000";
		}
	
		OrderMessage orderMsg = new OrderMessage();
		
		if(custName == null || custName.trim().equals(""))
		{
			custName = "newuser";
		}
		if(custPhone == null)
		{
			custPhone = "";
		}
		orderMsg.setCustomerName(custName);
		orderMsg.setCustomerPhone(custPhone);
		orderMsg.setErpstoreid(storeId);
		orderMsg.setOrderid(orderId.toString());
		orderMsg.setSdateSegment(sdate);
		orderMsg.setStimeSegment(stime);
		orderMsg.setDescription(descrip);
		orderMsg.setPackageItem(""); // tobe:  待集成
		orderMsg.setStatus("1");
		orderMsg.setAction(action);
		orderMsg.setSourceSys("ULTCRM");
		orderMsg.setCustomerId(customerId);
		orderMsg.setTechlevelno(platno);
		orderMsg.setSaName("");
		
		if(businessTypeId != null)
		{
			orderMsg.setBusinessTypeId(businessTypeId);
		}
				
		XStream xStream = new XStream();
		xStream.alias("order", OrderMessage.class);
		
		StringBuffer xmlstrbuf = new StringBuffer();

        xmlstrbuf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        xmlstrbuf.append("\r\n");
        xmlstrbuf.append("<ns0:MT_ICCRM_Reservation_Order xmlns:ns0=\"http://crm/91jpfw.cn\">");
        xmlstrbuf.append("\r\n");
        
        String toxml = xStream.toXML(orderMsg);
        
        int startIndex = toxml.indexOf("<action>");
        int endIndex = toxml.indexOf("</order>");
        
        xmlstrbuf.append(toxml.substring(startIndex,endIndex));
        
        xmlstrbuf.append("</ns0:MT_ICCRM_Reservation_Order>");
		//System.out.println(xmlstrbuf.toString());
		logger.info(xmlstrbuf.toString());
		
		return xmlstrbuf;
		
	}
	
	/**
	 * 2015-10-05,liuhua
	 * 根据从CRM传递过来的消息对象更新订单信息
	 * @param orderMsg
	 */
	public void updateOrderFromCRM(OrderMessage orderMsg)
	{
		//订单状态更新,包含状态、工单状态、工单相关信息等。都在这里
		if(ACTION_UPDATE_ORDER_STATUS.equals(orderMsg.getAction()))
		{
			this.updateOrderStateAction(orderMsg,false);
		   
		} else if(ACTION_CANCEL_ORDER.equals(orderMsg.getAction())) //取消预约单, 在工单之前
		{
			
		   logger.info("updateOrderFromCRM ACTION_CANCEL_ORDER order message: crm orderId=" + orderMsg.getCrmOrderId() + ","
				   		+ " orderstatus info " + orderMsg.getStatusRelationInfo() + " ,orderinfo = " + orderMsg.getOrderInfo());
		   Order order = orderRepository.findOne(Long.valueOf(orderMsg.getOrderid()));
 		   order.setStatus(4);  //预约单取消
 		   orderRepository.save(order);
 		   
 		} else if(ACTION_UPDATE_ORDER_STATUS_FROMWORK.equals(orderMsg.getAction()))
 		{
 			this.updateOrderStateAction(orderMsg,true);
 		}
	}


	/**
	 * 详细的订单逻辑操作在这里
	 * @param orderMsg  订单消息体
	 * @param sendSMS   是否要发微信短信
	 */
	private void updateOrderStateAction(OrderMessage orderMsg,boolean isSendSMS)
	{
		Order order  = null;
		logger.info("ULTCRM order id from CRM=" + orderMsg.getOrderid() + 
				", CRM order id from CRM=" + orderMsg.getCrmOrderId());
		
		boolean isNewOrder = false;
		
		if(orderMsg.getOrderid() == null || orderMsg.getOrderid().trim().equals("")  || 
				orderMsg.getOrderid().trim().equals("0"))
		{
			order = orderRepository.findByCrmWorkOrderId(orderMsg.getCrmOrderId());
			if(order == null)
			{
				//新建
				order = new Order();
				isNewOrder = true;
				
				logger.debug("from crm is new order...");
				Customer customer = customerRepository.findOne(Long.valueOf(orderMsg.getCustomerId()));
				order.setCustomer(customer);
			}
		}else {
			//更新,
			order = orderRepository.findOne(Long.valueOf(orderMsg.getOrderid()));
		}
		
		if(order == null)
		{
			logger.info("ERROR!!!!!++++++CRM orderid = " + orderMsg.getCrmOrderId() 
			+ "and ultcrm orderid " + orderMsg.getOrderid() 
			+" not exist in ULTCRM!!");
			return;
		}
		
		
	   String appointStatus = orderMsg.getStatus();  //crm 中的预约单状态	   
	   //tobe：  crm 状态 到 ultcrm状态的转换。  此处只处理  分配2， 成功3的场景
	   String workOrderStatus = orderMsg.getCrmOrderstatus();
	   
	   //订单的历史状态
	   String preWorkOrderStatu = order.getCrmOrderstatus();
	   
	   if(workOrderStatus == null || workOrderStatus.trim().equals(""))
		{
		   workOrderStatus = "";
		}else{
			workOrderStatus = workOrderStatus.trim();
		}
		
		if(preWorkOrderStatu == null || preWorkOrderStatu.trim().equals(""))
		{
			preWorkOrderStatu = "";
		}else{
			preWorkOrderStatu = preWorkOrderStatu.trim();
		}
		/*		
		create(1), working(2), complete(3), cancel(4), expire(5);
		ZVM002,// 培训报名
		ZVM003,// 培训确认
		ZVM007,// 培训开始
		ZVM008,// 培训完成
		ZVM009,// 培训结算
		ZVM010 // 付款	
		*/	
	   //线上订单
	   if(appointStatus != null  && (appointStatus.equals("1") || appointStatus.equals("2")))
	   {		   
		   order.setStatus(1);
		   
	   } else if(appointStatus != null  && (appointStatus.equals("4") || appointStatus.equals("5")))
	   {
		   
		   order.setStatus(Integer.valueOf(appointStatus));
		   
	   }else if("ZVM008".equals(workOrderStatus) || "ZVM009".equals(workOrderStatus) || "ZVM010".equals(workOrderStatus))
		{
		   
			order.setStatus(3);
			
		}else
		{
			
		    order.setStatus(2);
		    
		}
	   
	   order.setCrmOrderstatus(workOrderStatus);
	   order.setStatusRelationInfo(orderMsg.getStatusRelationInfo());   
	   order.setCrmWorkOrderId(orderMsg.getCrmOrderId());
	   order.setCrmCustomerId(orderMsg.getCrmCustomerId());
	   order.setCrmPlanFinishedtime(orderMsg.getCrmPlanFinishedtime());
	   order.setCrmTotalAmount(orderMsg.getCrmTotalAmount());
	   order.setCrmDiscountTotalAmount(orderMsg.getCrmDiscountTotalAmount());
	   order.setCrmSaName(orderMsg.getSaName());
	   
	   logger.info("updateOrderFromCRM order message: crm orderId=" + orderMsg.getCrmOrderId() + ","
	   		+ " orderstatus info " + orderMsg.getStatusRelationInfo() + " ,orderinfo = " + orderMsg.getOrderInfo() + 
	   		" ,plan finished time = " +orderMsg.getCrmPlanFinishedtime() +" ,crm order status = " +workOrderStatus +
	   		" , crm order prestatus " + preWorkOrderStatu + " tech techlevelno = " + orderMsg.getTechlevelno() +" appointment order status = " +appointStatus);
	   
	   order.setOrderInfo(orderMsg.getOrderInfo());
	   	
	   if(isNewOrder)
	   {
		   //从CRM过来的新订单，  不存在ultcrm预约单的情况		   
		   Tech tech = techRepository.findTechByTechlevelno(orderMsg.getTechlevelno());
		   if (tech == null) {
			   for (int i = 0; i < 3; i++) {
				   try {
					   Thread.sleep(1000);
					   tech = techRepository.findTechByTechlevelno(orderMsg.getTechlevelno());
					   logger.info("updateOrderFromCRM find tech: i=" + i);
					   if (tech != null) {
						   logger.info("updateOrderFromCRM find tech: techId=" + tech.getId());
						break;
					   }
				   } catch (InterruptedException e) {
					   logger.error("thread wake up",e);
				   }
			   }
		   }
		   order.setTech(tech);
	   }
	   
	   //保存订单
	   orderRepository.save(order);
	   logger.info("updateOrderFromCRM is ok!");
	   
	   if(isSendSMS)
	   {
		   boolean isFirst007 = false;
			////zvm007会有多次，我们只发一次
			if(workOrderStatus.equals(Order.WorkState.ZVM007.toString()))
			{
				if(!preWorkOrderStatu.equals(Order.WorkState.ZVM007.toString()))
				{
					isFirst007 = true;	
				}else{
					isFirst007 = false;
				}
				logger.info("ZVM007: crm order status = " +workOrderStatus +" , crm order prestatus " + preWorkOrderStatu + (isFirst007? " IS first" : ""));
				
			}
		   
	       notifycationOrderStateChange(order.getCustomer(), order,workOrderStatus,isFirst007, orderMsg.getErpstoreid());   
	   }
	   
		
	}

	private void notifycationOrderStateChange(Customer customer, Order order,String status,boolean isFirst007, String erpstoreid) {
		try {
			MessageTemplate messageTemplate = templateQueueService.getMessageTemplate("order_change");
			if (messageTemplate == null) {
				logger.warn("db can't find change messageTemplate");
			} else {
//				Store store = storeRepository.findByCode(erpstoreid);
				HashMap<String, String> param = new HashMap<String, String>(0);
				TemplateMessage messageValue = new TemplateMessage();
				String smsContent = "";
				String first = "服务订单进展提醒";
				param.put("first", first);
				smsContent += "服务订单进展提醒";
				param.put("keyword1", order.getCrmWorkOrderId());
				smsContent += "\n服务单号：" + order.getCrmWorkOrderId();
				String storeName = "";
//				if (store != null) {
//					storeName = store.getName();
//				}
				param.put("keyword2", storeName);
				smsContent += "\n服务门店：" + storeName;
				param.put("keyword3", parseWorkState(order.getCrmOrderstatus()));
				smsContent += "\n当前进度：" + parseWorkState(order.getCrmOrderstatus());
				String remark = "培训课程：" + order.getTech().getTechModel().getTechSery().getTechCourse().getName() + " " + order.getTech().getTechlevelno();
				smsContent += "\n";
				smsContent += remark;
				remark += "\n服务顾问：" + (order.getCrmSaName() == null ? "" : order.getCrmSaName());
				smsContent += "\n服务顾问：" + (order.getCrmSaName() == null ? "" : order.getCrmSaName());
				remark += "\n服务热线：13367006212";
				smsContent += "\n服务热线：13367006212";
				remark += parseAmount(order);
				smsContent += (parseAmount(order));
				remark += parseDesc(order.getCrmOrderstatus());
				param.put("remark", remark);
				
				messageValue.setOpenid(customer.getOpenid());
				messageValue.setTemplateId(messageTemplate.getTmpid());
				messageValue.setParam(param);
				
				
				
				if (order.getStatus().equals(Order.STATUS_COMPLETE)) {
					messageValue.setUrl(messageTemplate.makeUrl(weixinConfig,"complete"));
				}
				else {
					messageValue.setUrl(messageTemplate.makeUrl(weixinConfig,"working"));
				}
				
			
				
				//发送微信模板消息
				if (Order.WorkState.ZVM002.toString().equals(status)||Order.WorkState.ZVM003.toString().equals(status)||
						Order.WorkState.ZVM008.toString().equals(status)||
						Order.WorkState.ZVM009.toString().equals(status) ||
						Order.WorkState.ZVM007.toString().equals(status)||
						Order.WorkState.ZVM010.toString().equals(status)) {
					
						//zvm007会有多次，我们只发一次
					if(Order.WorkState.ZVM007.toString().equals(status))
					{
						    if(isFirst007)
						    {
						    	//logger.info("Order Status First Send ZVM007 WEIXIN MESSAGE");
						    	
						    	templateQueueService.sendTemplateMessage(messageValue);
						    }
					} else {
						
						//logger.info("Order status: " + status +  " WEIXIN MESSAGE!!");
						templateQueueService.sendTemplateMessage(messageValue);
					}
				}
				
				//发送短信，由于短信平台的问题， 在订单状态变更时，所有短信先不发了。
				//sendSMSMessage(customer.getPhone(), smsContent, order.getBusiTypeId(), order.getCrmOrderstatus());
				
			}
		} catch (Exception e) {
			logger.error("发送模板消息失败", e);
		}
	}


	private void sendSMSMessage(String phone, String content, Long busiType, String status) {
		status = status.replaceAll("\\n", "");
		//发短信过滤状态.  todo： 由于短信平台的问题， 先不发短信了。
		if (Order.WorkState.ZVM009.toString().equals(status)||
				Order.WorkState.ZVM010.toString().equals(status)) {
		    smsQueueService.sendMessage(phone, content, null, false);
		}
	}


	private String parseDesc(String crmOrderstatus) {
		String desc = "";
		crmOrderstatus = crmOrderstatus.replaceAll("\\n", "");
		if (Order.WorkState.ZVM002.toString().equals(crmOrderstatus)) {
			desc = "\n尊敬的用户，您的服务已完成报名确认，点击详情可查看具体内容。";
		} else if (Order.WorkState.ZVM003.toString().equals(crmOrderstatus)) {
			desc = "\n尊敬的用户，您的服务方案已确认，点击详情可查看具体内容。";
		} else if (Order.WorkState.ZVM007.toString().equals(crmOrderstatus)) {
			desc = "\n尊敬的用户，您的服务业务已开始，点击详情可查看服务环节。";
		} else if (Order.WorkState.ZVM008.toString().equals(crmOrderstatus)) {
			desc = "\n尊敬的用户，您的服务已完成，点击详情可查看服务环节。";
		} else if (Order.WorkState.ZVM009.toString().equals(crmOrderstatus)) {
			desc = "\n尊敬的用户，您有如上消费信息，如需了解请点击详情查看。";
		} else {			
			desc += "\n点击详情查看详细信息";
		}
		return desc;
	}


	private String parseAmount(Order order) {
		String amountStr = "";
		if (Order.WorkState.ZVM009.toString().equals(order.getCrmOrderstatus())) {
			amountStr = "\n消费金额：" + order.getCrmTotalAmount();
		}
		return amountStr;
	}


	private String parseWorkState(String crmOrderstatus) {
		String workState = "";
		logger.debug("当前订单状态为：" + crmOrderstatus.trim());
		crmOrderstatus = crmOrderstatus.replaceAll("\\n", "");
		if (Order.WorkState.ZVM002.toString().equals(crmOrderstatus.trim())) {
			workState = "到店报名";
		} else if (Order.WorkState.ZVM003.toString().equals(crmOrderstatus)) {
			workState = "报名确认";
		} else if (Order.WorkState.ZVM007.toString().equals(crmOrderstatus)) {
			workState = "开始培训";
		} else if (Order.WorkState.ZVM008.toString().equals(crmOrderstatus)) {
			workState = "培训完成";
		} else if (Order.WorkState.ZVM009.toString().equals(crmOrderstatus)) {
			workState = "结算";
		} else {			
		}
		return workState;
	}
	
}
