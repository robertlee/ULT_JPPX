/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.business.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.uletian.ultcrm.business.entity.Appointment;
import com.uletian.ultcrm.business.entity.Order;
import com.uletian.ultcrm.business.repo.AppointmentRepository;
import com.uletian.ultcrm.business.repo.OrderRepository;

import reactor.util.CollectionUtils;

/**
 * 妫�鏌ラ绾︽暟鎹殑鐘舵�侊紝灏嗚繃鏈熸病澶勭悊鐨勯绾﹀崟鐨勭姸鎬佷慨鏀逛竴涓嬨��
 * @author robertxie
 * 2015骞�9鏈�21鏃�
 */
@Component
public class AppointmentDataCheckSchedule {
	
		private static Logger logger = Logger.getLogger(AppointmentDataCheckSchedule.class);
		
		@Autowired
		private AppointmentRepository appointmentRepository;
		
		@Autowired
		private CardCouponService cardCouponService;
		
		@Autowired
		private OrderRepository orderRepository;

/*	 	@Scheduled(fixedRate = 1000 * 30)
	    public void reportCurrentTime(){
	        System.out.println ("Scheduling Tasks Examples: The time is now " + dateFormat().format (new Date ()));
	    }*/

	    /**
	     * 姣忓ぉ0鐐规墽琛�
	     */
	    //@Scheduled(cron = "0 7 16 * * ?")
		@Scheduled(cron = "0 5 0 * * ?")
	    public void run(){
	    	logger.info("Scheduling Tasks running: The time is now " + dateFormat().format (new Date ()));
	    	// 鎶撳彇棰勭害鍗曟暟鎹�
	    	List<Appointment> expireDataList = appointmentRepository.findExpireData(new Date());
	    	if (!CollectionUtils.isEmpty(expireDataList)){
	    		for (Appointment appoint : expireDataList) {
	    			logger.info("Change status to expire of order "+appoint.getOrder().getId());
	    			Order order = appoint.getOrder();
	    			order.setStatus(Order.STATUS_EXPIRE);
	    			orderRepository.save(order);
	    			appointmentRepository.save(appoint);
	    		}
	    	}
	    }
	    
	    private SimpleDateFormat dateFormat(){
	        return new SimpleDateFormat ("yyyy MM dd HH:mm:ss");
	    }	    	  
}
