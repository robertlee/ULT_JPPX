/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.business.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.uletian.ultcrm.business.entity.Card;
import com.uletian.ultcrm.business.entity.CardBatch;
import com.uletian.ultcrm.business.repo.CardBatchRepository;
import com.uletian.ultcrm.business.value.CardMessage.CardContent;

/**
 * 
 * @author robertxie
 * 2015年10月27日
 */
@Component
public class CardBatchService {
	
	@Autowired
	private CardBatchRepository cardBatchRepository;
	
	public CardBatch createCrmBatch(CardContent cardContent){
		CardBatch  cardBatch = new CardBatch();
		cardBatch.setIsULTcrmBatch("N");
		cardBatch.setBatchNo(cardContent.getBatchid());
		cardBatch.setType(cardContent.getType());
		cardBatch.setTotalCount(cardContent.getTotalcount());
		cardBatch.setStartDate(cardContent.getStartdate());
		cardBatch.setEndDate(cardContent.getEnddate());
		cardBatch.setPeriodType(CardBatch.PERIOD_TYPE_FIXED);
		cardBatch.setStatus("1");
		cardBatch.setDescription(cardContent.getDesc());
		cardBatch.setName(cardContent.getDesc());
		
		cardBatchRepository.save(cardBatch);
		return cardBatch;
	}
	
	/**
	 * 使用模板的方式，从cardBatch拷贝一些字段，生成新的card对象
	 * @return
	 */
	public Card createCardByCardBatchTemplate(CardBatch  cardBatch ) {
		Card card = new Card();
		BeanUtils.copyProperties(cardBatch, card);
		card.setId(null);
		card.setCardBatch(cardBatch);
		return card;
	}
	
}
