package com.uletian.ultcrm.business.controller;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import javax.annotation.Resource;
import org.apache.log4j.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.uletian.ultcrm.business.entity.BusinessType;
import com.uletian.ultcrm.business.entity.Schedule;
import com.uletian.ultcrm.business.entity.ScheduleStr;
import com.uletian.ultcrm.business.entity.Store;
import com.uletian.ultcrm.business.repo.ScheduleRepository;
import com.uletian.ultcrm.business.repo.BusinessTypeRepository;
import com.uletian.ultcrm.business.repo.StoreRepository;
import com.uletian.ultcrm.business.entity.Order;
import com.uletian.ultcrm.business.repo.OrderRepository;
import com.uletian.ultcrm.business.repo.ClassTypeRepository;
import com.uletian.ultcrm.business.entity.ClassType;

@RestController
public class StoreController {
	private static Logger logger = Logger.getLogger(StoreController.class);
	
	@Autowired
	private BusinessTypeRepository businessTypeRepository;
	@Autowired
	private StoreRepository storeRepository;
	@Autowired
	private ScheduleRepository scheduleRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private ClassTypeRepository classTypeRepository;
  
	@RequestMapping(value = "/getBusinessBy/{classId}/{addressId}/{typeId}/{status}", method = RequestMethod.GET)  
    public Map<String,Object> getBusinessBy(@PathVariable("classId")Long classId,@PathVariable("addressId")Long addressId,@PathVariable("typeId")String typeId,@PathVariable("status")String status){
    	Map<String,Object> map = new HashMap<String,Object>();
    	try {
    		logger.info("开始查询课时安排信息，参数课程编号classId值为：" + classId);
    		List<ScheduleStr> lists = new ArrayList<ScheduleStr>();
    		List<Schedule> list = scheduleRepository.getScheduleByClassId(classId,status,typeId,addressId);
    		logger.info("查询门店地址，参数课程类别编号typeId值为：" + typeId);
    		ClassType c = classTypeRepository.getAddressById(typeId,addressId);
    		if(c != null){
    			logger.info("根据课程编号查询到门店地址为：" + c.getAddress());
        		logger.info("根据课程编号查询到课程名称为：" + c.getClassName());
    		}
    		
			if(!list.isEmpty() && list.size() > 0){
				for(int i = 0; i < list.size(); i++){
					list.get(i).setLastUpdateUserid(list.get(i).getId());
					ScheduleStr s = new ScheduleStr();
					int totalNum = 0;
					if("A".equals(list.get(i).getSeatType())){
						totalNum = 32;
					}
					else if("B".equals(list.get(i).getSeatType())){
						totalNum = 24;
					}
					else if("C".equals(list.get(i).getSeatType())){
						totalNum = 30;
					}
					else if("D".equals(list.get(i).getSeatType())){
						totalNum = 20;
					}
					else if("E".equals(list.get(i).getSeatType())){
						totalNum = 20;
					}
					else if("F".equals(list.get(i).getSeatType())){
						totalNum = 21;
					}
					else if("G".equals(list.get(i).getSeatType())){
						totalNum = 36;
					}
					
					List<Order> olist = orderRepository.findOrderList(list.get(i).getId());
					logger.info("查询该课时的下单量为：" + olist.size());
					int orderNum = totalNum-olist.size();
					if(orderNum < 0){
						orderNum = 0;
					}
					s.setOrderNum(orderNum);
					s.setSchedule(list.get(i));
					if(c != null){
						s.setStore(c.getAddress());
						s.setClassName(c.getClassName());
					}
					lists.add(s);
				}
			}
			map.put("scheList",	lists);
		} catch (Exception e) {
			logger.error("根据课程编号" + classId + "获取课程安排信息失败");
			logger.error(e.getMessage());
		}
    	return map;
    }
}
