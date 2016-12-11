/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.business.controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestController;

import com.uletian.ultcrm.business.entity.BusinessType;
import com.uletian.ultcrm.business.entity.Tech;
import com.uletian.ultcrm.business.entity.Coupon;
import com.uletian.ultcrm.business.entity.CouponBatch;
import com.uletian.ultcrm.business.entity.Customer;
import com.uletian.ultcrm.business.entity.MessageTemplate;
import com.uletian.ultcrm.business.entity.Order;
import com.uletian.ultcrm.business.entity.Store;
import com.uletian.ultcrm.business.entity.TimeSegment;
import com.uletian.ultcrm.business.msg.CouponMsgService;
import com.uletian.ultcrm.business.repo.AppointmentRepository;
import com.uletian.ultcrm.business.repo.BusinessTypeRepository;
import com.uletian.ultcrm.business.repo.TechRepository;
import com.uletian.ultcrm.business.repo.ConfigRepository;
import com.uletian.ultcrm.business.repo.CouponBatchRepository;
import com.uletian.ultcrm.business.repo.CouponRepository;
import com.uletian.ultcrm.business.repo.CustomerRepository;
import com.uletian.ultcrm.business.repo.MessageTemplateRepository;
import com.uletian.ultcrm.business.repo.OrderRepository;
import com.uletian.ultcrm.business.repo.StoreRepository;
import com.uletian.ultcrm.business.repo.TimeSegmentRepository;
import com.uletian.ultcrm.business.service.AppointmentService;
import com.uletian.ultcrm.business.service.CustomerInfoSyncService;
import com.uletian.ultcrm.business.service.EventMessageService;
import com.uletian.ultcrm.business.service.OrderMessageServcie;
import com.uletian.ultcrm.business.service.SmsQueueService;
import com.uletian.ultcrm.business.service.TemplateQueueService;
import com.uletian.ultcrm.business.service.WeixinConfig;
import com.uletian.ultcrm.business.value.CouponMessage;
import com.uletian.ultcrm.business.value.TemplateMessage;
/**
 * 
 * @author robertxie,Javen
 * 2015年10月22日
 */
@RestController
public class CouponController {
	@Autowired
	private CouponRepository couponRepository;

	@Autowired
	private CouponBatchRepository couponBatchRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private ConfigRepository configRepository;
	
	@Autowired
	private CouponMsgService couponMsgService;
	@Autowired
	private CustomerInfoSyncService customerInfoSyncService;
	
	@Autowired
	private EventMessageService eventMessageService;
	
	@Autowired
	private AppointmentService appointmentService;
	
	@Autowired
	private BusinessTypeRepository businessTypeRepository;
	
	@Autowired
	private AppointmentRepository appointmentRepository;
	
	@Autowired
	private StoreRepository storeRepository;
	
	@Autowired
	private TechRepository techRepository;

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private TimeSegmentRepository timeSegmentRepository;
		
	@Autowired
	private OrderMessageServcie orderMessageServcie;
	
	@Autowired
	private TemplateQueueService templateQueueService;

	@Autowired
	private SmsQueueService smsQueueService;
	
	@Autowired
	private WeixinConfig weixinConfig;
	
	@Autowired
	private MessageTemplateRepository messageTemplateRepository;
	
	private static Logger logger = Logger.getLogger(CardController.class);
	
	@RequestMapping(value="/getCouponByCustomerId/{customerId}", method=RequestMethod.GET)
	public List<Object>  getCardByCustomerId(@PathVariable("customerId")Long customerId){
		Customer customer = customerRepository.findOne(customerId);
		List<Coupon> coupons = customer.getCoupons();
		List<Object> returnCoupons = new ArrayList<Object>();
		if(coupons!= null)
		{
			for(int i=0; i < coupons.size();i++)
			{
				Map<String,Object> couponMap = new HashMap<String,Object>();
				Coupon coupon = (Coupon)coupons.get(i);
				couponMap.put("id", coupon.getId());
				couponMap.put("status", coupon.getStatus());
				couponMap.put("type", coupon.getType());
				couponMap.put("amount", coupon.getAmount());
				couponMap.put("couponNo", coupon.getCouponNo());
				couponMap.put("startDate", coupon.getStartDate());
				couponMap.put("endDate", coupon.getEndDate());
				couponMap.put("name", coupon.getName());
				couponMap.put("description", coupon.getDescription());
				returnCoupons.add(couponMap);
			}
		}
		return returnCoupons;

	}	


	@RequestMapping(value="/coupon/test/{couponId}/{action}", method=RequestMethod.GET)
	public String  testCoupon(@PathVariable("couponId")Long couponId,@PathVariable("action")String action){
		 Coupon coupon = couponRepository.findOne(couponId);
		 CouponMessage msg = couponMsgService.convertToCouponMessage(coupon, action);
		return msg.toXML();
	}	
	// 获取指定的优惠券批次信息
	@RequestMapping(value="/getCouponBatch/{batchId}", method=RequestMethod.GET)
	public CouponBatch  getCouponBatch(@PathVariable("batchId")Long batchId){
		CouponBatch batch = couponBatchRepository.findOne(batchId);
		return batch;
	}
	
	private void notifycationSuccess(Long id, Customer customer, String techlevelno, BusinessType bt, String storeName,
			String datatime, boolean hasCard) {
		TemplateMessage messageValue = new TemplateMessage();
		MessageTemplate messageTemplate = templateQueueService.getMessageTemplate("appointment_success");
		
		if (messageTemplate == null) {
			logger.error("db can't find coupon message Template ");
		}else{
			HashMap<String, String> param = new HashMap<String, String>(0);
			String smsContent = "";
			String first = "您好,您的预约已成功登记";
			smsContent += first;
			if (hasCard) {
				if (bt.getId() == 4) {
					first += ",点击详情领取价值1次试听卡。";
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
			
			
			smsContent += " 课程名称："+bt.getName()+"("+storeName+")";			
			smsContent += " 开课时间："+datatime;

			
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
	
	
}
