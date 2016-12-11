/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.business.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.uletian.ultcrm.business.entity.Config;
import com.uletian.ultcrm.business.repo.ConfigRepository;
import com.uletian.ultcrm.business.value.CardCoupon;

/**
 * 
 * @author robertxie
 * 2015年11月16日
 */
@Component
public class AppointmentService {
	
	// business type --->event map
	private static Map<Long,String> busiEventMap = new HashMap<Long,String>();
	
	@Autowired
	private ConfigRepository configRepository;
	
	@Autowired
	private EventService eventService;
	
	@PostConstruct
	public void setMap() {
		busiEventMap.put(1l, "appointment_business1");
		busiEventMap.put(2l, "appointment_business2");
		busiEventMap.put(3l, "appointment_business3");
		busiEventMap.put(4l, "appointment_business4");
		busiEventMap.put(5L, "appointment_business5");
		busiEventMap.put(6L, "appointment_business6");
		busiEventMap.put(7L, "appointment_other");
		
	}
	
	// 优先获取数据库配置，如果没有配置那么从map中获取一个
	public String getEventForThisAppointment(Long businessTypeId) {
		String code = "APPOINTMENT_EVENT_"+businessTypeId;
		Config config = configRepository.findByCode(code);
		if (config != null) {
			return config.getValue();
		}
		else {
			return busiEventMap.get(businessTypeId);	
		}
	}
	
	public CardCoupon getCardCouponBatchForThisAppointment(Long businessTypeId) {
		String eventCode = getEventForThisAppointment(businessTypeId);
		CardCoupon batch = eventService.getCouponBatchByEventCode(eventCode);
		return batch;
	}
	
	
}
