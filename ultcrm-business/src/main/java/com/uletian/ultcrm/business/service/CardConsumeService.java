/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.business.service;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.uletian.ultcrm.business.entity.Card;
import com.uletian.ultcrm.business.entity.CardConsume;
import com.uletian.ultcrm.business.entity.Order;
import com.uletian.ultcrm.business.entity.Store;
import com.uletian.ultcrm.business.repo.CardConsumeRepository;
import com.uletian.ultcrm.business.repo.OrderRepository;
import com.uletian.ultcrm.business.repo.StoreRepository;
import com.uletian.ultcrm.business.value.CardMessage.CardConsumeItem;

/**
 * 
 * @author robertxie
 * 2015年10月27日
 */
@Component
public class CardConsumeService {
	private static Logger logger = Logger.getLogger(CardConsumeService.class);
	@Autowired
	private CardConsumeRepository cardConsumeRepository;
	
	@Autowired
	private StoreRepository storeRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	/**
	 * 未card增加一条消费记录
	 * @param card 这个必须是托管的对象
	 * @param time
	 * @param store
	 */
	public CardConsume addConsumeItem(Card card, Date time, Store store, Order order){
		CardConsume cardConsume = new CardConsume();
		cardConsume.setCard(card);
		cardConsume.setStore(store);
		cardConsume.setTime(time);
		cardConsume.setOrder(order);
		cardConsumeRepository.save(cardConsume);
		return cardConsume;
	}
	
	/**
	 * @param card 这个必须是托管的对象
	 * @param item 这个是CRM消息的内容
	 * @return
	 */
	public CardConsume addConsumeItem(Card card, CardConsumeItem item){
		// 检查是否有重复的记录，重复的不处理, 通过itemId来判断
		CardConsume oldData = cardConsumeRepository.findByItemid(item.getItemid());
		if (oldData != null) {
			// 重复的数据, 抛弃不处理
			//throw new RuntimeException("Duplicate data, discard the data."+card);
			logger.warn("Duplicate data, discard the data."+card);
			return null;
		}
//		Store store = storeRepository.findByCode(item.getConsumestore());
		Order order = null;
		if (StringUtils.isNotBlank(item.getOrderid())) {
			try {
				order = orderRepository.findByCrmWorkOrderId(item.getOrderid());
			}
			catch(Exception e) {
				logger.error("Can not fetch order , crm order id is"+item.getOrderid(),e);
			}
		}
		return addConsumeItem(card,item.getTime(),null,order);
	}
	
}
