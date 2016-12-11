/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.business.msg;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.stereotype.Component;

import com.uletian.ultcrm.business.entity.Coupon;
import com.uletian.ultcrm.business.entity.Coupon;
import com.uletian.ultcrm.business.service.CouponService;
import com.uletian.ultcrm.business.value.CouponMessage;
import com.uletian.ultcrm.business.value.CouponMessage;
import com.uletian.ultcrm.business.value.MsgConstants;
import com.uletian.ultcrm.business.value.CouponMessage.CouponContent;

/**
 * 
 * @author robertxie
 * 2015年10月27日
 */
@Component
public class CouponMsgService extends AbstractMsgHandler {
	private static Logger logger = Logger.getLogger(CouponMsgService.class);
	public static String COUPON_TOPIC = "COUPON";
	
	@Autowired
	private JmsTemplate topicJmsTemplate;
	
	@Autowired
	private CouponService couponService;
	
	public CouponMsgService(){
		super(CouponMessage.class,"coupons");
		addHandlers();
	}
	
	// 下发次卡
	public void publish(Coupon Coupon) {
		//msg
		CouponMessage CouponMessage = convertToCouponMessage(Coupon,"PUBLISH");
		
		String xmlString = this.convertObjectToMsgString(CouponMessage, "coupons");
		logger.info("ultcrm publish a coupon msg, msg is "+xmlString);
		topicJmsTemplate.convertAndSend(COUPON_TOPIC, xmlString, new MessagePostProcessor() {
			public Message postProcessMessage(Message message)
					throws JMSException {
				message.setStringProperty("SENDER", "ICCRM");
				message.setStringProperty("ACTION", "PUBLISH");
				return message;
			}
		});
	}
	
	// 下发次卡
	public void cancel(Coupon Coupon) {
		//msg
		CouponMessage CouponMessage = convertToCouponMessage(Coupon,"CANCEL");
		
		String xmlString = this.convertObjectToMsgString(CouponMessage, "coupons");
		logger.info("ultcrm publish a coupon msg, msg is "+xmlString);
		topicJmsTemplate.convertAndSend(COUPON_TOPIC, xmlString, new MessagePostProcessor() {
			public Message postProcessMessage(Message message)
					throws JMSException {
				message.setStringProperty("SENDER", "ICCRM");
				message.setStringProperty("ACTION", "CANCEL");
				return message;
			}
		});
	}
	
	public CouponMessage convertToCouponMessage(Coupon coupon,String action) {
		CouponMessage CouponMessage = new CouponMessage();
		CouponMessage.setAction(action);
		CouponMessage.setSourceSys(MsgConstants.SOURCE_SYS_ICCRM);
		
		CouponMessage.CouponContent innerCoupon = new CouponMessage.CouponContent();
		innerCoupon.batchid = coupon.getCouponBatch().getBatchNo();
		innerCoupon.couponid = coupon.getCouponNo();
		innerCoupon.amount = coupon.getAmount();
		innerCoupon.startdate = coupon.getStartDate().getClass().equals(java.sql.Timestamp.class)?
				new Date(coupon.getStartDate().getTime()):coupon.getStartDate();
		innerCoupon.enddate = coupon.getEndDate().getClass().equals(java.sql.Timestamp.class)?
				new Date(coupon.getEndDate().getTime()):coupon.getEndDate();
		innerCoupon.status = coupon.getStatus();
		innerCoupon.phone = StringUtils.isNotBlank(coupon.getCustomer().getPhone()) ? coupon.getCustomer().getPhone():"";
		innerCoupon.desc = StringUtils.isNotBlank(coupon.getDescription()) ? coupon.getDescription():"";
		innerCoupon.ultcrmcustid = coupon.getCustomer().getId().toString();
		innerCoupon.type = coupon.getType();
		
		innerCoupon.techlevelno = coupon.getTech() != null && StringUtils.isNotBlank(coupon.getTech().getTechlevelno()) ? coupon.getTech().getTechlevelno() : "";
		innerCoupon.techerno = coupon.getTech() != null && StringUtils.isNotBlank(coupon.getTech().getTecherno()) ? coupon.getTech().getTecherno() : "";
		innerCoupon.crmTechId = coupon.getTech() != null && StringUtils.isNotBlank(coupon.getTech().getCrmTechId()) ? coupon.getTech().getCrmTechId() : "";
		
		innerCoupon.publishtime = coupon.getPublishTime();
		//innerCoupon.techlevelno = coupon.get
		CouponMessage.addCoupon(innerCoupon);
		
		return CouponMessage;
	}
	
	
	
	@Override
	public void addHandlers() {
		MsgActionHandler publishHandler = new MsgActionHandler(){
			@Override
			public void handleMsg(MsgObject msgObject) {
				List<Coupon> couponList = convertToCouponList((CouponMessage)msgObject);
				for (Coupon coupon : couponList) {
					couponService.crmPublish(coupon);
				}
			}
		};
		MsgActionHandler useHandler = new MsgActionHandler(){
			@Override
			public void handleMsg(MsgObject msgObject) {
				List<Coupon> couponList = convertToCouponList((CouponMessage)msgObject);
				for (Coupon coupon : couponList) {
					couponService.use(coupon);
				}
			}
		};
		MsgActionHandler expireHandler = new MsgActionHandler(){
			@Override
			public void handleMsg(MsgObject msgObject) {
				List<Coupon> couponList = convertToCouponList((CouponMessage)msgObject);
				for (Coupon coupon : couponList) {
					couponService.expire(coupon);
				}
			}
		};
		MsgActionHandler cancelHandler = new MsgActionHandler(){
			@Override
			public void handleMsg(MsgObject msgObject) {
				List<Coupon> couponList = convertToCouponList((CouponMessage)msgObject);
				for (Coupon coupon : couponList) {
					couponService.crmCancel(coupon);
				}
			}
		};
		MsgActionHandler adjustHandler = new MsgActionHandler(){
			@Override
			public void handleMsg(MsgObject msgObject) {
				List<Coupon> couponList = convertToCouponList((CouponMessage)msgObject);
				for (Coupon coupon : couponList) {
					couponService.adjust(coupon);
				}
			}
		};
		MsgActionHandler writeoffHandler = new MsgActionHandler(){
			@Override
			public void handleMsg(MsgObject msgObject) {
				List<Coupon> couponList = convertToCouponList((CouponMessage)msgObject);
				for (Coupon coupon : couponList) {
					couponService.writeoff(coupon);
				}
			}
		};
		
		this.addHandler("PUBLISH", publishHandler);
		this.addHandler("USE", useHandler);
		this.addHandler("WRITEOFF", writeoffHandler);
		this.addHandler("CANCEL", cancelHandler);
		this.addHandler("EXPIRE", expireHandler);
		this.addHandler("ADJUST", adjustHandler);
		
	}
	
	
	public List<Coupon> convertToCouponList(CouponMessage couponMessage) {
		List<Coupon> couponList = new ArrayList<Coupon>();
		for (CouponContent content : couponMessage.getCouponList()) {
			Coupon coupon = new Coupon();
			coupon.setCouponContent(content);
			couponList.add(coupon);
		}
		return couponList;
	}
	
}
