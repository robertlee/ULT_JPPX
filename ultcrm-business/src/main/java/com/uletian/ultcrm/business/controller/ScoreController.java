/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.business.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.uletian.ultcrm.business.entity.Tech;
import com.uletian.ultcrm.business.entity.Customer;
import com.uletian.ultcrm.business.entity.Order;
import com.uletian.ultcrm.business.entity.Score;
import com.uletian.ultcrm.business.entity.Store;
import com.uletian.ultcrm.business.repo.TechRepository;
import com.uletian.ultcrm.business.repo.CustomerRepository;
import com.uletian.ultcrm.business.repo.ScoreRepository;
import com.uletian.ultcrm.common.util.DateUtils;

/**
 * 
 * @author robertxie
 * 2015年10月22日
 */
@RestController
public class ScoreController {
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private ScoreRepository scoreRepository;
	/*
	 * 返回格式：
	 * [
	 * 	   {	//单个Tech的积分信息
	 * 			techlevelno:"粤B12345"，
	 * 			totalScore:100,
	 * 			scoreItems:[
	 * 				{	//单个积分的信息
	 * 					storeName:"深圳店",
	 * 					time:"2015/10/10 7:00",
	 * 					value: 20,
	 * 					orderNo:"S2015070811"
	 * 				},
	 * 				...
	 * 			]
	 * 	   },
	 * 	   ...
	 * ]
	 */
	@RequestMapping(value="/getScoreDetail/{customerId}", method=RequestMethod.GET)
	List<Object> getScoresByCustomerId(@PathVariable Long customerId){		
		List<Object> list = new ArrayList<Object>();		
		Customer customer = customerRepository.findOne(customerId);
		if(customer == null){
			//TODO:用户不存在，错误处理
			return null;
		}
		List<Tech> techs = customer.getTechs();
		if(techs==null){
			techs = new ArrayList<Tech>();
		}
		
		for(Tech tech : techs){
			//单个Tech的积分详情
			Map<String,Object> techMap = new HashMap<String,Object>();
			techMap.put("techlevelno", tech.getTechlevelno());
			techMap.put("totalScore", tech.getTotalScore());
			//此Tech的所有积分列表
			List<Score> scores = scoreRepository.findByTech(tech);
			//存储筛选后的积分列表
			List<Object> scoreItemList = new ArrayList<Object>();
			//获取积分和门店信息，加到scoreItemList中
			for(Score score : scores){
				//单个积分信息
				Map<String,Object> scoreItemMap = new HashMap<String,Object>();
				//时间
				Date date = new Date(score.getTime().getTime());
				scoreItemMap.put("time", DateUtils.formatDate(date, "yyyy-MM-dd HH:mm")) ;	//时间
				scoreItemMap.put("value", score.getValue());	//积分
				scoreItemMap.put("description", score.getDescription());	//描述
				//订单号
				Order order = score.getOrder();
				if(order != null){
					scoreItemMap.put("orderNo", order.getCrmWorkOrderId());
				}
				scoreItemList.add(scoreItemMap);
			}
			techMap.put("scoreItems", scoreItemList);
			list.add(techMap);
		}
		return list;
	}
}
