package com.uletian.ultcrm.weixin.event;

import java.sql.Timestamp;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.uletian.ultcrm.business.entity.Customer;
import com.uletian.ultcrm.business.entity.Location;
import com.uletian.ultcrm.business.repo.CustomerRepository;
import com.uletian.ultcrm.business.repo.LocationRepository;
import com.uletian.ultcrm.weixin.controller.WeixinCheckController;

import weixin.popular.bean.EventMessage;
import weixin.popular.bean.xmlmessage.XMLMessage;
import weixin.popular.event.EventHandler;

@Component
public class LocationEventhandler implements EventHandler {
	
	private static Logger logger = Logger.getLogger(WeixinCheckController.class);
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private LocationRepository locationRepository;
	
	@Override
	public XMLMessage handleEvent(EventMessage eventMessage) {
		String to = eventMessage.getToUserName();
		String from = eventMessage.getFromUserName();
		String latitude = eventMessage.getLatitude();
		String longitude = eventMessage.getLongitude();
		String precision = eventMessage.getPrecision();
		Integer time = eventMessage.getCreateTime();
		logger.debug("接收到用户 " + from + " 地理信息 \nlatitude:"+latitude+"\nlongitude:"+longitude);
		
		Customer customer = customerRepository.findByOpenid(from);
		if (customer == null) {
			logger.info("地理位置信息用户没有找到");
		} else {
			Location location = locationRepository.findByCustomerid(customer.getId());
			if (location == null) {
				location = new Location();
				location.setCreateUserId(customer.getId());
			}
			location.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
			location.setTypeid(2);
			location.setLatitude(latitude);
			location.setLongitude(longitude);
			location.setPrecision(precision);
			locationRepository.save(location);
		}
		return null;
	}

}
