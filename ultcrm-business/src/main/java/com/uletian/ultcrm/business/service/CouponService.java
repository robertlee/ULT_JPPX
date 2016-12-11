/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.business.service;

import java.sql.Timestamp;
import java.util.Calendar;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.uletian.ultcrm.business.entity.Tech;
import com.uletian.ultcrm.business.entity.CardBatch;
import com.uletian.ultcrm.business.entity.Coupon;
import com.uletian.ultcrm.business.entity.CouponBatch;
import com.uletian.ultcrm.business.entity.Customer;
import com.uletian.ultcrm.business.msg.CouponMsgService;
import com.uletian.ultcrm.business.repo.TechRepository;
import com.uletian.ultcrm.business.repo.CouponBatchRepository;
import com.uletian.ultcrm.business.repo.CouponRepository;
import com.uletian.ultcrm.business.repo.CustomerRepository;
import com.uletian.ultcrm.business.value.CouponMessage.CouponContent;
import com.uletian.ultcrm.common.util.StringUtils;

/**
 * 
 * @author robertxie
 * 2015年10月27日
 */
@Component
public class CouponService {
	
	private static Logger logger = Logger.getLogger(CouponService.class);
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private CouponRepository couponRepository;
	
	@Autowired
	private CouponBatchRepository couponBatchRepository;
	
	@Autowired
	private CouponMsgService couponMsgService;
	
	@Autowired
	private CouponBatchService couponBatchService;
	
	@Autowired
	private TechRepository techRepository;

	@Autowired
	private IdsManager idsManager;
	
	public Coupon adjust(Coupon coupon){
		Coupon theCoupon = getCouponFromCrmMsg(coupon);
		//theCoupon.setStatus(Coupon.STATUS_EXPIRE);
		return theCoupon;
	}
	
	public Coupon expire(Coupon coupon){
		Coupon theCoupon = getCouponFromCrmMsg(coupon);
		if (theCoupon == null) {
			logger.error("根据crm发送的消息不能查询到对应的coupon信息，因此不处理");
			return null;
		}
		theCoupon.setStatus(Coupon.STATUS_EXPIRE);
		couponRepository.save(theCoupon);
		return theCoupon;
	}
	
	public Coupon crmCancel(Coupon coupon){
		Coupon theCoupon = getCouponFromCrmMsg(coupon);
		if (theCoupon == null) {
			logger.error("根据crm发送的消息不能查询到对应的coupon信息，因此不处理");
			return null;
		}
		theCoupon.setStatus(Coupon.STATUS_CANCEL);
		couponRepository.save(theCoupon);
		return theCoupon;
	}
	
	public Coupon ultcrmCancel(Coupon coupon){
		//Coupon theCoupon = getCouponFromCrmMsg(coupon);
		coupon.setStatus(Coupon.STATUS_CANCEL);
		couponRepository.save(coupon);
		couponMsgService.cancel(coupon);
		return coupon;
	}
	
	public Coupon writeoff(Coupon coupon){
		Coupon theCoupon = getCouponFromCrmMsg(coupon);
		if (theCoupon == null) {
			logger.error("根据crm发送的消息不能查询到对应的coupon信息，因此不处理");
			return null;
		} 
		theCoupon.setStatus(Coupon.STATUS_WRITEOFF);
		couponRepository.save(theCoupon);
		return theCoupon;
	}
	
	public Coupon crmPublish(Coupon coupon){
		// 从消息数据中整理
		if (coupon.getCouponContent().getCouponid().toUpperCase().startsWith("W")) {
			logger.warn("收到一个ULTCRM系统发布的优惠券的信息，不需要处理");
			return null;
		}
		
		// 先查询一下数据是否已经存在，存在则是重复数据
		CouponBatch couponBatch = couponBatchRepository.findByBatchNo(coupon.getCouponContent().getBatchid());
		Coupon newCoupon = couponRepository.findByCouponNoAndCouponBatch(coupon.getCouponContent().getCouponid(),couponBatch);
		if (newCoupon != null) {
			//throw new RuntimeException("重复的消息数据，丢弃不处理");
			logger.error("重复的消息数据，丢弃不处理");
			return null;
		}
		
		// 查询是否已经存在批次，如果没有, 那么立即创建一个批次
		if (couponBatch == null) {
			couponBatch = couponBatchService.createCrmBatch(coupon.getCouponContent());
		}
		
		// 创建coupon对象
		newCoupon = couponBatchService.createcouponBycouponBatchTemplate(couponBatch);
		CouponContent couponContent = coupon.getCouponContent();
		
		newCoupon.setPublishTime(couponContent.getPublishtime());
		newCoupon.setStatus(Coupon.STATUS_PUBLISH);
		newCoupon.setDescription(couponContent.getDesc());
		String desc = couponContent.getDesc();
		newCoupon.setName(desc.substring(0, desc.length()>50 ? 50 : desc.length()));	//名称由Desc截取
		
		Customer customer = StringUtils.isNoneEmpty(coupon.getCouponContent().getULTcrmcustid())
							?customerRepository.findOne(Long.valueOf(coupon.getCouponContent().getULTcrmcustid().trim()))
							:StringUtils.isNoneEmpty(coupon.getCouponContent().getPhone())
							?customerRepository.findByPhone(coupon.getCouponContent().getPhone())
							:null;
							
		newCoupon.setCustomer(customer);
		newCoupon.setCouponNo(coupon.getCouponContent().getCouponid().trim());
		couponRepository.save(newCoupon);
		return newCoupon;
	}
	
	public void ultcrmPublish(Coupon coupon){
		// 将在ultcrm系统中创建好的coupon通过消息推送给crm系统
		couponMsgService.publish(coupon);
	}
	
	public Coupon use(Coupon coupon){
		Coupon theCoupon = getCouponFromCrmMsg(coupon);
		if (theCoupon == null) {
			logger.error("根据crm发送的消息不能查询到对应的coupon信息，因此不处理");
			return null;
		}
		theCoupon.setStatus(Coupon.STATUS_WRITEOFF);
		couponRepository.save(theCoupon);
		return theCoupon;
	}

	private Coupon  getCouponFromCrmMsg(Coupon coupon) {
		CouponBatch couponBatch = couponBatchRepository.findByBatchNo(coupon.getCouponContent().getBatchid());
		Coupon theCoupon = couponRepository.findByCouponNoAndCouponBatch(coupon.getCouponContent().getCouponid(),couponBatch);
		if (theCoupon == null) {
			theCoupon = crmPublish(coupon);
		}
		return theCoupon;
	}
	
	public Coupon createCoupon(CouponBatch couponBatch, Customer customer, Tech tech) {
		Calendar currentDate = Calendar.getInstance();
		Calendar targetDate = Calendar.getInstance();
		Coupon coupon = new Coupon();
		coupon.setAmount(couponBatch.getAmount());
		coupon.setCouponBatch(couponBatch);
		coupon.setCouponNo(couponBatch.getBatchNo() + idsManager.getCode());
		coupon.setName(couponBatch.getName());
		coupon.setCustomer(customer);
		coupon.setTech(tech);
		coupon.setType(couponBatch.getType());
		coupon.setDescription(couponBatch.getDescription());
		coupon.setCreateTime(new Timestamp(currentDate.getTimeInMillis()));
		coupon.setPublishTime(currentDate.getTime());
		coupon.setPeriodType(couponBatch.getPeriodType());
		coupon.setStatus(Coupon.STATUS_PUBLISH);
		if (CardBatch.PERIOD_TYPE_FIXED.equals(couponBatch.getPeriodType())) {
			coupon.setStartDate(couponBatch.getStartDate());
			coupon.setEndDate(couponBatch.getEndDate());
		} else if (CardBatch.PERIOD_TYPE_DELAY.equals(couponBatch.getPeriodType())){
			coupon.setStartDate(currentDate.getTime());
			targetDate.add(Calendar.DAY_OF_MONTH, couponBatch.getPeriod().intValue());
			coupon.setEndDate(targetDate.getTime());
		}
		return coupon;
	}
}