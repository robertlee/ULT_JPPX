package com.uletian.ultcrm.business.service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.uletian.ultcrm.business.entity.Appointment;
import com.uletian.ultcrm.business.entity.Customer;
import com.uletian.ultcrm.business.repo.AppointmentRepository;
import com.uletian.ultcrm.business.repo.CustomerRepository;

@Component
public class AppointmentNotifycationService {
	
	private static final Logger logger = LoggerFactory.getLogger(AppointmentNotifycationService.class);
	@Autowired
	private AppointmentRepository appointmentRepository;
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private SmsQueueService smsQueueService;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
	
	@Scheduled(cron="0 0 * * * ?")
	public void test(){
		logger.info("Scheduled run appointment notifycation");
		//Robert Lee 2016-8-29
		/*
		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR_OF_DAY, 2);
		Long segvalue = Long.decode(c.get(Calendar.HOUR_OF_DAY)+"");
		List<Appointment> appointments = appointmentRepository.findByTimeSegment(new Date(c.getTimeInMillis()), segvalue);
		for (Appointment appointment : appointments) {
			Customer customer = customerRepository.findOne(appointment.getCustomerId());
			//尊敬的用户，您预约的{0}店，{1}，{2}服务已由专属SA为您安排，我们恭候您的光临。
			String content = "尊敬的用户，您预约的";
			content += appointment.getStore().getName();
			content += "，";
			content += (sdf.format(appointment.getTimeSegment().getDateSegment()) +" "+appointment.getTimeSegment().getTimeSegment() + "点");
			content += "，";
			content += appointment.getOrder().getDescription();
			content += "即将开课，我们恭候您的光临。";			
			smsQueueService.sendMessage(customer.getPhone(), content, null, false);
		}
		*/
	}
}
