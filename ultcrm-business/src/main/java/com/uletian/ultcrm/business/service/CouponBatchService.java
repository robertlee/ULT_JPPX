/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.business.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.uletian.ultcrm.business.entity.Coupon;
import com.uletian.ultcrm.business.entity.CouponBatch;
import com.uletian.ultcrm.business.repo.CouponBatchRepository;
import com.uletian.ultcrm.business.value.CouponMessage.CouponContent;

@Component
public class CouponBatchService {
	
	@Autowired
	private CouponBatchRepository couponBatchRepository;
	
	public CouponBatch createCrmBatch(CouponContent couponContent){
		
		CouponBatch  couponBatch = new CouponBatch();
		couponBatch.setIsULTcrmBatch("N");
		couponBatch.setBatchNo(couponContent.getBatchid());
		couponBatch.setType(couponContent.getType());
		couponBatch.setAmount(couponContent.getAmount());
		couponBatch.setStartDate(couponContent.getStartdate());
		couponBatch.setEndDate(couponContent.getEnddate());
		couponBatch.setStatus("1");
		couponBatch.setPeriodType("FIXED");
		couponBatch.setDescription(couponContent.getDesc());
		couponBatch.setName(couponContent.getDesc());
		couponBatchRepository.save(couponBatch);
		return couponBatch;
	}
	
	/**
	 * 使用模板的方式，从couponBatch拷贝一些字段，生成新的coupon对象
	 * @return
	 */
	public Coupon createcouponBycouponBatchTemplate(CouponBatch  couponBatch ) {
		Coupon coupon = new Coupon();
		BeanUtils.copyProperties(couponBatch, coupon);
		coupon.setId(null);
		coupon.setCouponBatch(couponBatch);
		return coupon;
	}
}
