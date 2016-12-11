/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.business.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.uletian.ultcrm.business.entity.CardBatch;
import com.uletian.ultcrm.business.entity.CouponBatch;
import com.uletian.ultcrm.business.entity.Event;
import com.uletian.ultcrm.business.entity.EventBatch;
import com.uletian.ultcrm.business.repo.CardBatchRepository;
import com.uletian.ultcrm.business.repo.CouponBatchRepository;
import com.uletian.ultcrm.business.repo.EventBatchRepository;
import com.uletian.ultcrm.business.repo.EventRepository;
import com.uletian.ultcrm.business.value.CardCoupon;

/**
 * 
 * @author robertxie
 * 2015年11月16日
 */
@Component
public class EventService {
	
	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private EventBatchRepository eventBatchRepository;
	
	
	@Autowired
	private CouponBatchRepository couponBatchRepository;
	
	@Autowired
	private CardBatchRepository cardBatchRepository;
	
	public CardCoupon getCouponBatchByEventCode(String code) {
		CardCoupon result = new CardCoupon();
		Event event = eventRepository.findEventByCode(code);
		if (event != null) {
			List<EventBatch> eventBatchs = event.getEventBatchs();
			if (CollectionUtils.isNotEmpty(eventBatchs)) {
				EventBatch batch = eventBatchs.get(0);
				Long batchId = batch.getBatchId();
				
				if (batch.getBatchType().equalsIgnoreCase("card")) {
					CardBatch cardBatch = cardBatchRepository.findOne(batchId);
					BeanUtils.copyProperties(cardBatch, result);
					result.setSubType("cardBatch");
				}
				else {
					CouponBatch couponBatch = couponBatchRepository.findOne(batchId);
					BeanUtils.copyProperties(couponBatch, result);
					result.setSubType("couponBatch");
				}

			}
		}
		return result;
	}
	
}
