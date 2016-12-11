/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.business.controller;

import java.text.SimpleDateFormat;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.collections.CollectionUtils;

import net.sf.json.JSONObject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import net.sf.json.JSONObject;

import com.pingplusplus.Pingpp;
import com.pingplusplus.model.Charge;
import com.pingplusplus.util.WxpubOAuth;
import com.uletian.ultcrm.business.entity.Order;
import com.uletian.ultcrm.business.repo.OrderRepository;
import com.uletian.ultcrm.business.repo.StoreRepository;

import com.uletian.ultcrm.business.entity.Schedule;
import com.uletian.ultcrm.business.entity.ClassRoom;
import com.uletian.ultcrm.business.entity.Schedule;
import com.uletian.ultcrm.business.repo.ScheduleRepository;
import com.uletian.ultcrm.business.entity.Store;
import com.uletian.ultcrm.business.entity.Seat;
import com.uletian.ultcrm.business.repo.SeatRepository;
import com.uletian.ultcrm.business.entity.Customer;
import com.uletian.ultcrm.business.repo.CustomerRepository;
import com.uletian.ultcrm.common.util.DateUtils;
import com.uletian.ultcrm.business.entity.BusinessType;
import com.uletian.ultcrm.business.repo.BusinessTypeRepository;
import com.uletian.ultcrm.business.service.TemplateQueueService;
import com.uletian.ultcrm.business.service.WeixinConfig;
import com.uletian.ultcrm.business.value.TemplateMessage;
import com.uletian.ultcrm.business.entity.MessageTemplate;
import com.uletian.ultcrm.business.service.WeixinConfig;
import com.uletian.ultcrm.business.entity.OrderComment;
import com.uletian.ultcrm.business.repo.OrderCommentRepository;



import org.springframework.beans.BeanUtils;

 /**
 * 
 * @author robertxie
 * 2015年9月10日
 */
@RestController
public class OrderController {
	private static Logger logger = Logger.getLogger(OrderController.class);
	@Value("${pingApiKeyTest}")
	private String pingApiKeyTest;
	@Value("${pingApiKey}")
	private String pingApiKey;
	@Value("${pingAppId}")
	private String pingAppId;
	@Value("${appId}")
	private String appId;		
	@Value("${pingSecKey}")
	private String pingSecKey;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private StoreRepository storeRepository;
	
	//@Autowired
	//private ClassroomRepository classroomRepository;
	
	@Autowired
	private SeatRepository seatRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private BusinessTypeRepository businessTypeRepository;
	@Autowired
	private TemplateQueueService templateQueueService;
	@Autowired
	private WeixinConfig weixinConfig;
	@Autowired
	private OrderCommentRepository orderCommentRepository;
	@Autowired
	private ScheduleRepository scheduleRepository;
	
	private static Map<Long, String> imgUrlMap = new HashMap<Long,String>();
	
	private static Integer PAGE_SIZE=10000;
	
	static {
		imgUrlMap.put(1L, "icon_a.png");//业务1
		imgUrlMap.put(2L, "icon_b.png");//业务2
		imgUrlMap.put(3L, "icon_c.png");//业务3
		imgUrlMap.put(4L, "icon_d.png");//业务4
		imgUrlMap.put(5L, "icon_e.png");//业务5
		imgUrlMap.put(6L, "icon_f.png");//业务6
	}
 
	@RequestMapping(value = "/createPayOrder/{jsonStr}", method = RequestMethod.GET)
	public Map<String,Object> getOrderList(@PathVariable("jsonStr")String jsonStr){
		Map<String, Object> map = new HashMap<String, Object>();
		Long oid = 0l;
		Long seatId = 0l;
		try {
			logger.info("开始创建支付订单，订单参数为：【" + jsonStr + "】 ");
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);
			Object classId = jsonObj.get("classId");
			Object className = jsonObj.get("className");
			Object seatName = jsonObj.get("seatName");
			Object choiceSeat = jsonObj.get("choiceSeat");
			Object roomId = jsonObj.get("roomId");
			Object roomName = jsonObj.get("roomName");
			Object price = jsonObj.get("price");
			Object totalPrice = jsonObj.get("totalPrice");
			Object scheId = jsonObj.get("scheId");
			Object openId= jsonObj.get("openId");//用户openID   
			Object teachId = jsonObj.get("teachId");//教师编号
			Object teachName = jsonObj.get("teachName");//教师名称
			Object classTimeDetail = jsonObj.get("hiddenclassTimeDetail");//开课时间
			Object startTime = jsonObj.get("startTime");//开课时间
			Object endTime = jsonObj.get("endTime");//结课时间
			Object classHour = jsonObj.get("classHour");//课程课时
			Object childName = jsonObj.get("childName");//学生姓名

			boolean bl = true;
			
			if(openId == null){
				bl = false;
				map.put("msg", "false");
				logger.error("未获取到用户openId");
				return map;
			}
			if(scheId == null){
				bl = false;
				logger.error("课程编号为空");
				map.put("msg", "false");
				return map;
			}
			
			//根据openId获取用户信息
			Customer customer = customerRepository.findByOpenid(openId.toString());
			if(customer == null){
				bl = false;
				logger.error("根据openId获取用户信息失败");
				map.put("msg", "false");
				return map;
			}
			
			logger.info("先判断所选座位是否存在");
			Long scheduleId = Long.parseLong(scheId.toString());
			//处理并发性用户下单事件
			
			
			Seat se = seatRepository.getSeatByName(seatName.toString(),scheduleId);
			if(se != null){
				bl = false;
				logger.error("生成订单失败,该座位已被下单");
				map.put("msg", "该座位已被下单");
				return map;
			}				

			logger.info("生成订单编号");
			Long tmp = System.currentTimeMillis()/1000;
	    	Random random = new Random();
	    	int num = (int)(random.nextDouble()*(10000 - 1000) + 1000);
	    	String orderId = tmp.toString() + num;
			
			if(bl){
				logger.info("开始保存已选座位数据");
				Seat seat = new Seat();
				seat.setName(seatName.toString());
				seat.setStatus("1");
				seat.setCreateTime(new Date());
				seat.setLastUpdateTime(new Date());
				if(childName != null && !"".equals(childName)){
					seat.setChildName(childName.toString());
				}
				seat.setCustomerPhone(customer.getPhone());
				Schedule cr = new Schedule();
				cr.setId(scheduleId);
				seat.setSchedule(cr);
				seat.setCustomerId(customer.getId().toString());
				seatRepository.save(seat);
				logger.info("保存已选座位数据成功");
				seatId = seat.getId();
				
				logger.info("开始保存订单");
				Order o = new Order();
		    	o.setOrderId(orderId);
		    	o.setCustomer(customer);
				o.setCustomername(customer.getNickname());
				if(classId != null && !"".equals(classId)){
					o.setClassid(Long.parseLong(classId.toString()));
					o.setClassname(className.toString());
				}
				if(classHour != null && !"".equals(classHour)){
					o.setClassHour(Long.parseLong(classHour.toString()));					
				}		
				if(seat.getId() != null){
					o.setSeatid(seat.getId());
				}
				if(choiceSeat != null && !"".equals(choiceSeat)){
					o.setSeatname(choiceSeat.toString());				
				}
				if(roomId != null && !"".equals(roomId)){
					o.setRoomId(Long.parseLong(roomId.toString()));
					o.setRoomName(roomName.toString());
				}
				o.setStatus(1);
				o.setCreateTime(new Date());
				o.setLastUpdateTime(new Date());
				
				if(price != null && !"".equals(price)){
					o.setPrice(new BigDecimal(price.toString()));
				}
				
				if(totalPrice != null && !"".equals(totalPrice)){
					o.setTotalPrice(new BigDecimal(totalPrice.toString()));
				}
				if(scheId != null && !"".equals(scheId)){
					Schedule s = new Schedule();
					s.setId(Long.parseLong(scheId.toString()));
					o.setSchedule(s);
				}
				if(teachId != null && !"".equals(teachId)){
					o.setTeacherId(Long.parseLong(teachId.toString()));
				}
				if(teachName != null && !"".equals(teachName)){
					o.setTeacherName(teachName.toString());
				}
				if(startTime != null && !"".equals(startTime)){
					Date tmpTime = DateUtils.parseDate(startTime);
					o.setStartTime(tmpTime);
				}
				if(endTime != null && !"".equals(endTime)){
					Date tmpTime = DateUtils.parseDate(endTime);
					o.setEndTime(tmpTime);
				}
				if(classTimeDetail != null && !"".equals(classTimeDetail)){
					o.setClassTimeDetail(classTimeDetail.toString());
				}
				if(childName != null && !"".equals(childName)){
					o.setChildName(childName.toString());
				}
				orderRepository.save(o);
				logger.info("创建支付订单成功 ");
				oid = Long.parseLong(o.getOrderId());
				try
				{
					se = seatRepository.getSeatByName(seatName.toString(),scheduleId);
					long cId0=Long.parseLong(se.getCustomerId());
					long cId1=Long.parseLong(customer.getId().toString());
					logger.info("创建支付订单后se信息2016-8-11:"+cId0+":"+cId1);
					if(se != null){
						if (cId0!=cId1)
						{	
							o.setStatus(0);
							orderRepository.save(o);
							seat.setStatus("0");
							seatRepository.save(seat);
							logger.error("该座位已被下单");
							map.put("msg", "该座位已被下单");
							return map;
						}	
					}					
					
				}catch (Exception e) {							
						o.setStatus(0);
						orderRepository.save(o);
						seat.setStatus("0");
						seatRepository.save(seat);	
						logger.error("确认座位信息：已被下单异常");
						map.put("msg", "该座位已被下单");
						return map;
				}
	

				
				//开始调用支付接口
				map = startPay(oid,totalPrice.toString(),className.toString(),openId.toString());
				map.put("seatId", seatId);
				map.put("orderId", oid);
				
			
				logger.info("返回订单号和选中座位号【orderId：" + oid + ",seatId：" + seatId + "】");  
				//Robert Lee 2016-07-11 	
						
			}

		} catch (Exception e) {
			map.put("msg", "false");
			logger.error("创建支付订单失败");
			logger.error(e.getMessage());
			delOrder(oid,seatId);
		}
		return map;
	}

	@RequestMapping(value = "/getMessageTemplate/{orderId}/{jsonStr}", method = RequestMethod.GET)
	public void  getMessageTemplate(@PathVariable("orderId")Long orderId,@PathVariable("jsonStr")String jsonStr){

		try {
			logger.info("开始消息模板参数为：【" + jsonStr + "】 ");
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);
			Object className = jsonObj.get("className");
			//Object seatName = jsonObj.get("seatName");
			Object choiceSeat = jsonObj.get("choiceSeat");
			
			Object roomName = jsonObj.get("roomName");							
			Object openId= jsonObj.get("openId");//用户openID   					
			Object startTime = jsonObj.get("startTime");//开课时间			
			//notifycationSuccess(orderId,openId.toString(),className.toString(),roomName.toString(),choiceSeat.toString(),startTime.toString());
			notifycationSuccess(orderId,openId.toString(),className.toString(),roomName.toString(),choiceSeat.toString(),startTime.toString());				
			}

		catch (Exception e) {			
			logger.error("发送模板消息");
			logger.error(e.getMessage());			
		}
		
	}
	
	@RequestMapping(value = "/delOrder/{orderId}/{seatId}", method = RequestMethod.GET)
	public String delOrder(@PathVariable("orderId")Long orderId,@PathVariable("seatId")Long seatId){
		try {
			logger.info("开始取消订单 ，参数【orderId：" + orderId + "】");
			Order o = orderRepository.findByOrderId(orderId);			
			if(o != null){
				o.setStatus(0);
				orderRepository.save(o);
				logger.info("取消订单成功,开始删除订单中的座位信息，参数【seatId：" + seatId + "】 ");
			}
			Seat s = seatRepository.getSeatById(seatId);
			if(s != null){
				s.setStatus("0");
				seatRepository.save(s);
				logger.info("删除座位信息成功");
			}
		} catch (Exception e) {
			logger.error("订单失效失败 ");
			logger.error(e.getMessage());
			return "false";
		}
		return "true";
	}
	
	/**
	 * @param id	订单号
	 * @param className	课程名称
	 * @param classAddr 开课地址
	 * @param datatime	开课时间
	 * {{first.DATA}}
	 * 课程：{{keyword1.DATA}}
	 * 时间：{{keyword2.DATA}}
	 * 地点：{{keyword3.DATA}}
	 * {{remark.DATA}}
	 */
	public void notifycationSuccess(Long id,String openId, String className,String classAddr,String seatSpec,String datatime){
		
		
		try {
			TemplateMessage messageValue = new TemplateMessage();
			MessageTemplate messageTemplate = templateQueueService.getMessageTemplate("order_success");
			
			if (messageTemplate == null) {
				logger.warn("找不到消息对应的模板");
			}else{
				HashMap<String, String> param = new HashMap<String, String>(0);
				String smsContent = "";
				String first = "您好,您的报名已成功登记";
				
				 
				first += "\n报名编号："+id;
				param.put("first", first);
				param.put("keyword1", className);
				param.put("keyword2", datatime);
				param.put("keyword3", classAddr+" "+seatSpec);				
				String csRemak= "\n欢迎您使用，客服电话：13367006212 0791-86836192 ！";
				param.put("remark",csRemak);
				
				messageValue.setOpenid(openId);
				messageValue.setTemplateId(messageTemplate.getTmpid());
				messageValue.setParam(param);				
				//messageValue.setUrl(messageTemplate.makeUrl(weixinConfig,id.toString()));
				String csURL=messageTemplate.makeUrl(weixinConfig,"create");
				logger.info("访问csURL："+csURL);
				messageValue.setUrl(csURL);				
				templateQueueService.sendTemplateMessage(messageValue);
			}
		} catch (Exception e) {
			logger.error("发送消息失败 ");
			logger.error(e.getMessage());
		}
	}
	
	public Map<String,Object> startPay(Long orderId,String price,String className,String openId){
		Map<String, Object> map = new HashMap<String, Object>();
	    try {
	    	String ticket = WxpubOAuth.getJsapiTicket(appId, pingSecKey);
	    	logger.info("ticket " + ticket);
	        // 创建 Charge
	        Charge charge = payApp(orderId,price,className,openId);
	        // 获得签名
	        String signature = WxpubOAuth.getSignature(charge.toString(), ticket, "");
	        logger.info("------- JSAPI 签名 -------");
	        logger.info(signature);
	        logger.info("charge：【" + charge + "】");
	        map.put("charge", charge);
	        map.put("signature", signature);					
		} 
	    catch (Exception e) {
			logger.error("调用支付接口失败 ");
			logger.error(e.getMessage());
		}
	    return map;
	}
	
	public Charge payApp(Long orderId,String price,String className,String openId){
    	Charge charge = null;
	    try {
		    Pingpp.apiKey = pingApiKey;
		  
		    Map<String, Object> chargeMap = new HashMap<String, Object>();  
		    // 某些渠道需要添加extra参数，具体参数详见接口文档  
			//Robert Lee 2016-08-01
		    chargeMap.put("amount", Long.parseLong(price)*100);//金额，单位为分，例 100 表示 1.00 元，233 表示 2.33 元  
		    chargeMap.put("currency", "cny");//货币类型   cny：人民币
		    chargeMap.put("subject", "培训课程报名费用");  
		    chargeMap.put("body", className);
		    chargeMap.put("order_no", orderId);// 订单号
		    chargeMap.put("channel", "wx_pub");//支付方式   wx_pub： 微信公众账号支付
		    chargeMap.put("client_ip", "127.0.0.1");// 客户端的 IP 地址
		    
		    Map<String, String> app = new HashMap<String, String>();
		    //Ping++ 管理平台【应用名称】->【应用信息】中得得到/////支付使用的 app 对象的 id
		    app.put("id", pingAppId);
		    chargeMap.put("app", app);
		    
		    Map<String, String> extramap = new HashMap<String, String>();  
		    //extra的参数根据文档: https://pingxx.com/document/api#api-c-new  
		    extramap.put("open_id", openId);
		    chargeMap.put("extra", extramap);  
	      
	        //发起交易请求
	        charge = Charge.create(chargeMap);  
	        System.out.println(charge);
			//notifycationSuccess(orderId,openId,className,SeatSpec,startTime);
	    } 
	    catch (Exception e) {
			e.printStackTrace();
			logger.error("调用支付payApp接口失败");
			logger.error(e.getMessage());
		}
        return charge;
    }
	
	@RequestMapping("/getOrderListByCustomerId/{customerId}")
	public Map<String,List<Order>> getOrderList(@PathVariable("customerId")Long customerId){
		logger.info("The customer id "+customerId);
		Map<String,List<Order>> result = new HashMap<String,List<Order>>();
		try {
			Customer customer = customerRepository.findOne(customerId);
			// 根据状态分组查询订单列表, 分页查询，查询5个
			Sort sort = new Sort(Direction.DESC, "lastUpdateTime");
			Pageable pageable = new PageRequest(0, PAGE_SIZE, sort);
			logger.info("开始查询状态数据");
			List<Order> newOrderList  = orderRepository.findByCustomerAndStatus(customer, 1,pageable).getContent();
			logger.info("查询状态数据【newOrderList：" + newOrderList.size() + "】");
			List<Order> workingOrderList  = orderRepository.findByCustomerAndStatus(customer, 2,pageable).getContent();
			
			List<Integer> statusList = new ArrayList<Integer>();
			statusList.add(3);
			statusList.add(4);
			statusList.add(5);
			List<Order> completeOrderList  = orderRepository.findByCustomerAndStatusIn(customer,statusList ,pageable).getContent();

			// 添加待评价的订单列表
			List<Order> commentOrderList = new ArrayList<Order>();
			if (CollectionUtils.isNotEmpty(completeOrderList)) {
				for (Order order : completeOrderList) {
					if (order.getStatus().equals(Order.STATUS_COMPLETE) && CollectionUtils.isEmpty(order.getOrderComments())) {
						commentOrderList.add(order);
					}
				}
			}
			
			if(newOrderList != null)
			{
				for(int i=0;i < newOrderList.size(); i++ )
				{
					Order od = newOrderList.get(i);
					if(od.getAppointment() != null && od.getAppointment().getId() != null)
					{
						od.setAppointmentId(od.getAppointment().getId());
					}
				}
			}
			
			if(workingOrderList != null)
			{
				for(int i=0;i < workingOrderList.size(); i++ )
				{
					Order od = workingOrderList.get(i);
					if(od.getAppointment() != null && od.getAppointment().getId() != null)
					{
						od.setAppointmentId(od.getAppointment().getId());
					}
				}			
			}
			
			if(completeOrderList != null)
			{
				for(int i=0;i < completeOrderList.size(); i++ )
				{
					Order od = completeOrderList.get(i);
					if(od.getAppointment() != null && od.getAppointment().getId() != null)
					{
						od.setAppointmentId(od.getAppointment().getId());
					}
				}			
			}
			
			if(commentOrderList != null)
			{
				for(int i=0;i < commentOrderList.size(); i++ )
				{
					Order od = commentOrderList.get(i);
					if(od.getAppointment() != null && od.getAppointment().getId() != null)
					{
						od.setAppointmentId(od.getAppointment().getId());
					}
				}			
			}
			
			newOrderList = arrange(newOrderList);
			logger.info("查询状态数据【newOrderList：" + newOrderList.size() + "】");
			workingOrderList = arrange(workingOrderList);
			completeOrderList = arrange(completeOrderList);
			commentOrderList = arrange(commentOrderList);
			
			result.put("new", newOrderList);
			result.put("working", workingOrderList);
			result.put("complete", completeOrderList);
			result.put("comment", commentOrderList);
		} catch (Exception e) {
			logger.error("查询状态失败 ");
			logger.error(e.getMessage());
		}
		
		return result;
	}
	
	private List<Order> arrange(List<Order> inputOrderList) {
		List<Order> result = new ArrayList<Order>();
		for (int i = 0 ; i < inputOrderList.size(); i ++) {
			Order order = inputOrderList.get(i);
			try {
				// 根据appointment对象来判断是否是预约订单还是线下订单
//				if (order.getAppointment() != null) {
//					Date theDate = order.getAppointment().getTimeSegment().getDateSegment();
//					Long segIndex = order.getAppointment().getTimeSegment().getTimeSegment();
//					String dateString = DateUtils.formatDate(theDate,"yyyy/MM/dd");
//					String segString = segIndex+":00-"+(segIndex+1)+":00";
//					order.setAppointTimeStr(dateString+" "+segString);
//					order.setBusiTypeId(order.getAppointment().getTimeSegment().getBusiTypeId());
//					String imgUrl = imgUrlMap.get(order.getAppointment().getTimeSegment().getBusiTypeId());
//					order.setImgUrl(imgUrl);
//				}
//				else {
//					// 这种是线下订单
					order.setImgUrl("icon_u.png");
					// 线下订单设置业务类型为0
					// 线上订单设置业务类型为1
					order.setBusiTypeId(1L);
//				}
				if (order.getTech() != null) {
					order.setTechlevelno(order.getTech().getTechlevelno());
				}
				else {
					order.setTechlevelno("");
				}
				
				// 检查是否有评论信息
				if (order.getStatus().equals(Order.STATUS_COMPLETE)) {
					if (CollectionUtils.isNotEmpty(order.getOrderComments())) {
						order.setHasComment(true);
					}
				}
				
				String tmpTime = "";
				if(order.getStartTime() != null){
					tmpTime = DateUtils.formatDate(order.getStartTime(),"yyyy-MM-dd HH:mm");
				}
				order.setStartClassTime(tmpTime);

				String tmpStr = "";
				if(order.getSeatname() != null){
					tmpStr = order.getSeatname().replace("-","排");
				}
				order.setSeatname(tmpStr);
				result.add(order);
			}
			catch(Exception e){
				logger.error("Can't handle the dirty order data.",e);
				continue;
			}
		}
		return result;
	}
	
	@RequestMapping(value = "/getOrderInfo/{orderid}", method = RequestMethod.GET)
	public Map<String, String> getOrderInfo(@PathVariable("orderid") Long orderid){
		HashMap<String, String> infoMap = new HashMap<String, String>(0);
		try {
			logger.info("开始查询订单详情，参数为【orderid：" + orderid + "】 ");
			Order order = orderRepository.findOne(orderid);
			infoMap.put("orderId", order.getOrderId());
			infoMap.put("classId", order.getClassid()+"");
			infoMap.put("classname", order.getClassname());
			infoMap.put("roomName", order.getRoomName());
			infoMap.put("teachName", order.getTeacherName());
			infoMap.put("teachId", order.getTeacherId()+"");
			infoMap.put("classHour", order.getClassHour().toString());
			infoMap.put("price", order.getPrice().intValue() + "");
			infoMap.put("childName", order.getChildName());
			infoMap.put("classTimeDetail", order.getClassTimeDetail());
			
			String tmpTime = "";
			if(order.getStartTime() != null){
				tmpTime = DateUtils.formatDate(order.getStartTime(),"yyyy/MM/dd");
			}
			if(order.getEndTime() != null){
				tmpTime += "--" + DateUtils.formatDate(order.getEndTime(),"yyyy/MM/dd");
			}
			infoMap.put("classTime",tmpTime);
			String tmp = "";
			
			if(order.getCreateTime() != null){
				tmp = DateUtils.formatDate(order.getCreateTime(),"yyyy/MM/dd HH:mm:ss");
			}
			infoMap.put("createTime", tmp);
			
			Schedule sche = scheduleRepository.getScheduleById(order.getSchedule().getId());
			if(sche != null){
				infoMap.put("roomAddress", sche.getbusinessAddress());
			}
		} catch (Exception e) {
			logger.error("查询订单详情失败 ");
			logger.error(e.getMessage());
		}
		return infoMap;
	}

	// 获取订单的评价数据
	@RequestMapping("/getCommentByOrderId/{orderId}")
	public OrderComment getCommentByOrderId(@PathVariable("orderId")Long orderId){
		
		Order order = orderRepository.findOne(orderId);
		
		OrderComment orderComment = orderCommentRepository.findByOrder(order);
		
		
		return orderComment;
	}

	// 创建订单的评价数据
	@RequestMapping(value="/createCommentForOrder/{orderId}/{customerId}",method=RequestMethod.POST)
	public OrderComment createCommentForOrder(@PathVariable("orderId")Long orderId,@PathVariable("customerId")Long customerId,@RequestBody OrderComment orderComment){
		Order order = orderRepository.findOne(orderId);
		Customer customer = customerRepository.findOne(customerId);
		OrderComment orderCommentOld = orderCommentRepository.findByOrder(order);
		
		if (orderCommentOld != null) {
			BeanUtils.copyProperties(orderComment, orderCommentOld,"id");
			orderCommentOld.setCustomer(customer);
			orderCommentOld.setOrder(order);
			orderCommentRepository.save(orderCommentOld);
		}
		else {
			orderComment.setCustomer(customer);
			orderComment.setOrder(order);
			orderCommentRepository.save(orderComment);
		}
		return orderComment;
	}
	//查询小孩信息
	@RequestMapping(value="/searchChild/{openId}",method=RequestMethod.GET)
	public Map<String, Object> searchChild(@PathVariable("openId")String openId){
		Map<String, Object> map = new HashMap<String, Object>();
		//根据openId获取用户信息
		logger.info("开始根据用户openId查询用户信息，查询参数为【openId：" + openId + "】 ");
		Customer customer = customerRepository.findByOpenid(openId.toString());
		if(customer != null){
			map.put("customerId", customer.getId());
			if(customer.getChilds() != null){
				map.put("childs", customer.getChilds());
				logger.info("查询到用户小孩数量为为：" + customer.getChilds().size() + "】 ");
			}
			if(customer.getPhone() != null){
				map.put("contactPhone", customer.getPhone());
				logger.info("查询到用户手机号为：" + customer.getPhone() + "】 ");
			}
		}
		return map;
	}	
	//查询登录人信息
	@RequestMapping(value="/searchCutomerId/{openId}",method=RequestMethod.GET)
	public long searchCutomerId(@PathVariable("openId")String openId){
		
		//根据openId获取用户信息
		logger.info("开始根据用户openId查询用户信息，查询参数为【openId：" + openId + "】 ");
		Customer customer = customerRepository.findByOpenid(openId.toString());
		if(customer != null){
			return customer.getId();
		}
		else{
			return 0;
		}
		
	}
}