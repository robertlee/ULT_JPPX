/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.business.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;



import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.uletian.ultcrm.business.entity.Advertise;
import com.uletian.ultcrm.business.repo.AdvertiseRepository;

@RestController
public class IndexController {
	private static Logger logger = Logger.getLogger(IndexController.class);
	
	@Autowired
	private AdvertiseRepository advertiseRepository;
	

	
    @RequestMapping(value = "/getAdvertiseList", method = RequestMethod.GET)
    public List<Advertise> getAdvertiseList(){ 
    	List<Advertise> list = new ArrayList<Advertise>();
    	try {
    		logger.debug("开始获取门店数据");
    		list = advertiseRepository.getAdvertiseList();
    		logger.debug("获取广告数据条数" + list.size());
		} catch (Exception e) {
			logger.error("获取广告数据信息失败");
			logger.error(e.getMessage());
		}
    	return list;
    }	
	
	
}
