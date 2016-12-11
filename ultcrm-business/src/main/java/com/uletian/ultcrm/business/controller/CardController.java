/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.business.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.uletian.ultcrm.business.entity.Tech;
import com.uletian.ultcrm.business.entity.Card;
import com.uletian.ultcrm.business.entity.CardConsume;
import com.uletian.ultcrm.business.entity.Customer;
import com.uletian.ultcrm.business.entity.Order;
import com.uletian.ultcrm.business.entity.Store;
import com.uletian.ultcrm.business.repo.TechRepository;
import com.uletian.ultcrm.business.repo.CardBatchRepository;
import com.uletian.ultcrm.business.repo.CardRepository;
import com.uletian.ultcrm.business.repo.CouponRepository;
import com.uletian.ultcrm.business.repo.CustomerRepository;
import com.uletian.ultcrm.business.service.CustomerInfoSyncService;
import com.uletian.ultcrm.common.util.DateUtils;

/**
 * 
 * @author robertxie,Javen Leung
 * 2016年10月22日
 */
@RestController
public class CardController {
	@Autowired
	private CardRepository cardRepository;

	@Autowired
	private CouponRepository couponRepository;
	
	@Autowired
	private CardBatchRepository cardbatchRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private TechRepository techRepository;
	
	@Autowired
	private CustomerInfoSyncService customerInfoSyncService;
	
	private static Logger logger = Logger.getLogger(CardController.class);
	

	@RequestMapping(value="/getCardByCustomerId/{customerId}", method=RequestMethod.GET)
	public List<Object>  getCardByCustomerId(@PathVariable("customerId")Long customerId){
		
		
		Customer customer = customerRepository.findOne(customerId);
		List<Card> cards = customer.getCards();
		List<Object> returnCards = new ArrayList<Object>();
		if(cards!= null)
		{
			for(int i=0; i < cards.size();i++)
			{
				Map<String,Object> cardMap = new HashMap<String,Object>();
				Card card = (Card)cards.get(i);
				//获取车牌号
				String techlevelno = "";
				Tech tech = card.getTech();
				if(tech != null){
					techlevelno = tech.getTechlevelno();
				}
				cardMap.put("techlevelno", techlevelno);
				cardMap.put("id", card.getId());
				cardMap.put("status", card.getStatus());
				cardMap.put("type", card.getType());
				cardMap.put("cardNo", card.getCardNo());
				cardMap.put("startDate", card.getStartDate());
				cardMap.put("endDate", card.getEndDate());
				cardMap.put("totalCount", card.getTotalCount());
				cardMap.put("usedCount", card.getUsedCount());
				cardMap.put("name", card.getName());
				cardMap.put("description", card.getDescription());
				returnCards.add(cardMap);
			}
		}
		
		return returnCards;
	}
	

	/**
	 * 获取次卡详情,map方式返回，返回格式为
	 * {
	 * 		cardNo:"No.SO15031234",
	 * 		cardName:"快乐语文5次试听卡",
	 * 		techlevelno:"粤B12345",
	 * 		totalCount:5,
	 * 		usedCount:1,
	 * 		endDate:"2015/12/10",
	 * 		comsumptionItems:[
	 * 			{
	 * 				storeName:"深圳店",
	 * 				time:"2015/10/28",
	 * 				orderNo:"S20151028123"
	 * 			},
	 * 			...
	 * 		]
	 * }
	 * @param cardId
	 * @return
	 */
	@RequestMapping(value="/getCardDetail/{cardId}", method=RequestMethod.GET)
	public Map<String,Object> getCardDetail(@PathVariable("cardId")Long cardId){
		Map<String,Object> map = new HashMap<String,Object>();		
		Card card = cardRepository.findOne(cardId);		
		//返回空对象
		if(card == null){
			return map;
		}			
		map.put("cardNo", card.getCardNo());	//卡号
		map.put("cardName", card.getName());	//卡名称		
		//技能号
		String techlevelno = "";
		Tech tech = card.getTech();
		if(tech != null){
			techlevelno = tech.getTechlevelno();
		}
		map.put("techlevelno", techlevelno);
		map.put("description", card.getDescription());//描述
		map.put("totalCount", card.getTotalCount());  //总数
		map.put("usedCount", card.getUsedCount());	  //使用次数		
		//截止日期
		Date endDate = new Date(card.getEndDate().getTime());
		map.put("endDate",DateUtils.formatDate(endDate, "yyyy/MM/dd"));			
		//存储使用记录
		List<Object> consumptiomItems = new ArrayList<Object>();		
		//遍历所有消费记录，使用详情
		List<CardConsume> cardConsumes = card.getCardConsumes();
		for(CardConsume cardConsume : cardConsumes){
			//单个消费记录
			Map<String,Object> consumptionMap = new HashMap<String,Object>();			
			//消费时间
			Date date = new Date(cardConsume.getTime().getTime());
			consumptionMap.put("time", DateUtils.formatDate(date, "yyyy-MM-dd HH:mm"));			
			//订单号
			Order order = cardConsume.getOrder();
			if(order != null){
				consumptionMap.put("orderNo", order.getCrmWorkOrderId());
			}		
			//门店名称
			Store store = cardConsume.getStore();
			if(store != null){
				consumptionMap.put("storeName", store.getName());
			}
			
			consumptiomItems.add(consumptionMap);
		}		
		map.put("cunsumptionItems", consumptiomItems);
		return map;
	}
}
