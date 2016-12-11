/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.business.service;

import java.sql.Timestamp;
import java.util.Calendar;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.uletian.ultcrm.business.entity.Tech;
import com.uletian.ultcrm.business.entity.Card;
import com.uletian.ultcrm.business.entity.CardBatch;
import com.uletian.ultcrm.business.entity.Customer;
import com.uletian.ultcrm.business.msg.CardMsgService;
import com.uletian.ultcrm.business.repo.TechRepository;
import com.uletian.ultcrm.business.repo.CardBatchRepository;
import com.uletian.ultcrm.business.repo.CardConsumeRepository;
import com.uletian.ultcrm.business.repo.CardRepository;
import com.uletian.ultcrm.business.repo.CustomerRepository;
import com.uletian.ultcrm.business.value.CardMessage.CardConsumeItem;
import com.uletian.ultcrm.common.util.StringUtils;

/**
 * 
 * @author robertxie
 * 2015年10月23日
 */
@Component
public class CardService {
	private static Logger logger = Logger.getLogger(CardService.class);
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private TechRepository techRepository;
	
	@Autowired
	private CardRepository cardRepository;
	
	@Autowired
	private CardBatchRepository cardBatchRepository;
	
	@Autowired
	private CardMsgService cardMsgService;
	
	@Autowired
	private CardBatchService cardBatchService;
	
	@Autowired
	private CardConsumeRepository cardConsumeRepository;
	
	@Autowired
	private CardConsumeService cardConsumeService;
	
	@Autowired
	private IdsManager idsManager;
	
	@Autowired
	private TechService techService;
	
	/**
	 * 生成卡
	 * @param card
	 * @return 
	 */
	public Card createCard(CardBatch cardBatch, Customer customer, Tech tech) {
		Calendar currentDate = Calendar.getInstance();
		Calendar targetDate = Calendar.getInstance();
		Card card = new Card();
		card.setAmount(cardBatch.getAmount());
		card.setCardBatch(cardBatch);
		card.setCardNo(cardBatch.getBatchNo() + idsManager.getCode());
		card.setType(cardBatch.getType());
		card.setCustomer(customer);
		card.setTech(tech);
		card.setDescription(cardBatch.getDescription());
		card.setName(cardBatch.getName());
		card.setTotalCount(cardBatch.getTotalCount());
		card.setStatus(Card.STATUS_PUBLISH);
		card.setPeriodType(cardBatch.getPeriodType());
		if (CardBatch.PERIOD_TYPE_FIXED.equals(cardBatch.getPeriodType())) {
			card.setStartDate(cardBatch.getStartDate());
			card.setEndDate(cardBatch.getEndDate());
		} else if (CardBatch.PERIOD_TYPE_DELAY.equals(cardBatch.getPeriodType())){
			card.setStartDate(currentDate.getTime());
			targetDate.add(Calendar.DAY_OF_MONTH, cardBatch.getPeriod().intValue());
			card.setEndDate(targetDate.getTime());
		}
		card.setPublishTime(currentDate.getTime());
		card.setUsedCount(0);
		card.setCreateTime(new Timestamp(System.currentTimeMillis()));
		return card;
	}
	
	/**
	 * 下发卡 PUBLISH
	 * 要分辨是CRM下发卡的行为还是ULTCRM下发卡的行为
	 * @param card
	 */
	public Card crmPublishCard(Card card) {
		// 从消息数据中整理
		// 先判断是否是ULTCRM的卡，如果是，就不处理, 通过卡的前缀判断
		if (card.getCardMsgConetnt().getCardid().toUpperCase().startsWith("C")) {
			logger.warn("收到一个ULTCRM系统发布的卡的信息，不需要处理");
			return null;
		}
		// 先查询一下数据是否已经存在，存在则是重复数据
		CardBatch cardBatch = cardBatchRepository.findByBatchNo(card.getCardMsgConetnt().getBatchid());
		Card newCard = cardRepository.findByCardNoAndCardBatch(card.getCardMsgConetnt().getCardid(),cardBatch);
		if (newCard != null) {
			logger.error("重复的消息数据，丢弃不处理");
			return null;
		}
		
		// 查询是否已经存在批次，如果没有, 那么立即创建一个批次
		if (cardBatch == null) {
			cardBatch = cardBatchService.createCrmBatch(card.getCardMsgConetnt());
		}
		// 创建card对象
		newCard = cardBatchService.createCardByCardBatchTemplate(cardBatch);
		
		
		Customer customer = StringUtils.isNoneEmpty(card.getCardMsgConetnt().getULTcrmcustid())
							?customerRepository.findOne(Long.valueOf(card.getCardMsgConetnt().getULTcrmcustid().trim()))
							:StringUtils.isNoneEmpty(card.getCardMsgConetnt().getPhone())
							?customerRepository.findByPhone(card.getCardMsgConetnt().getPhone())
							:null;
		newCard.setCustomer(customer);

		// 搜索车辆信息
		Tech tech = techService.getTechByCrmTechIdAndPlateNo(card.getCardMsgConetnt().getCrmTechId(),card.getCardMsgConetnt().getTechlevelno());
				
		newCard.setTech(tech);
		// 计算消费次数
		if (newCard.getType().equals("X")) {
			newCard.setUsedCount(CollectionUtils.isNotEmpty(card.getCardMsgConetnt().getConsumeItemList())?card.getCardMsgConetnt().getConsumeItemList().size():0);
		}
		else {
			newCard.setUsedCount(newCard.getTotalCount()-card.getCardMsgConetnt().getRemaincount());
		}
		newCard.setCardNo(card.getCardMsgConetnt().getCardid());
		newCard.setStatus(Card.STATUS_PUBLISH);
		newCard.setPublishTime(card.getCardMsgConetnt().getPublishtime());
		newCard.setAmount(card.getCardMsgConetnt().getAmount());
		cardRepository.save(newCard);

		// 整理统计tech的使用情况
		if (CollectionUtils.isNotEmpty(card.getCardMsgConetnt().getConsumeItemList()) ) {
			for (CardConsumeItem item : card.getCardMsgConetnt().getConsumeItemList()) {
				cardConsumeService.addConsumeItem(newCard, item);
			}
		}
		return newCard;
	}
	
	/**
	 * 下发卡 PUBLISH
	 * 要分辨是CRM下发卡的行为还是ULTCRM下发卡的行为
	 * @param card
	 */
	public void ultcrmPublishCard(Card card) {
		// 这里将创建好的card推送消息到crm系统中去
		cardMsgService.publish(card);
	}
	
	private Card getCardFromCrmMsg(Card card) {
		CardBatch cardBatch = cardBatchRepository.findByBatchNo(card.getCardMsgConetnt().getBatchid());
		Card theCard = cardRepository.findByCardNoAndCardBatch(card.getCardMsgConetnt().getCardid(),cardBatch);
		if (theCard == null) {
			theCard = crmPublishCard(card);
		}
		if (StringUtils.isNotBlank(card.getCardMsgConetnt().getStatus())) {
			theCard.setStatus(card.getCardMsgConetnt().getStatus().trim());
		}
		return theCard;
	}
	
	/**
	 * 使用卡, 从CRM系统传递的消息, 里面有增量的消费记录
	 * @param card
	 */
	public void useCard(Card card) {
		Card theCard = getCardFromCrmMsg(card);
		if (theCard == null) {
			logger.error("根据crm发送的消息不能查询到对应的card信息，因此不处理");
			return;
		}
		if (CollectionUtils.isNotEmpty(card.getCardMsgConetnt().getConsumeItemList())) {
			for (CardConsumeItem item : card.getCardMsgConetnt().getConsumeItemList()) {
				// 检查是否有重复的记录，重复的不处理
				cardConsumeService.addConsumeItem(theCard, item);
			}
		}
		else {
			logger.error("一个使用的消息，但是消息体中没有使用相关的数据。");
			return;
		}
		// 更新和统计数据
		theCard.setUsedCount(theCard.getTotalCount()-card.getCardMsgConetnt().getRemaincount());
		if (theCard.getTotalCount().equals(theCard.getUsedCount())) {
			// 如果卡已经使用完毕，那么自动核销
			theCard.setStatus(Card.STATUS_WRITEOFF);
		}
		cardRepository.save(theCard);
	}
	
	/**
	 * 取消卡， 从CRM系统传递的消息
	 * @param card
	 */
	public void crmCancelCard(Card card) {
		Card theCard = getCardFromCrmMsg(card);
		if (theCard == null) {
			logger.error("根据crm发送的消息不能查询到对应的card信息，因此不处理");
			return;
		}
		theCard.setStatus(Card.STATUS_CANCEL);
		cardRepository.save(theCard);
	}
	
	/**
	 * 取消卡, 需要发送消息给crm系统
	 * @param card
	 */
	public void ultcrmCancelCard(Card card) {
		card.setStatus(Card.STATUS_CANCEL);
		cardRepository.save(card);
		cardMsgService.cancel(card);
	}
	
	/**
	 * 过期卡， 从CRM系统传递的消息
	 * @param card
	 */
	public void expireCard(Card card) {
		Card theCard = getCardFromCrmMsg(card);
		if (theCard == null) {
			logger.error("根据crm发送的消息不能查询到对应的card信息，因此不处理");
			return;
		}
		theCard.setStatus(Card.STATUS_EXPIRE);
		cardRepository.save(theCard);
	}
	
	/**
	 * 调整卡， 从CRM系统传递的消息
	 * @param card
	 */
	public void adjustCard(Card card) {
		Card theCard = getCardFromCrmMsg(card);
		// 调整可以调整哪些字段呢？主要是调整车牌号码
		if (theCard == null) {
			logger.error("根据crm发送的消息不能查询到对应的card信息，因此不处理");
			return;
		}
		
	}
	
	
	/**
	 * 核销卡， 从CRM系统传递的消息
	 * @param card
	 */
	public void writeoffCard(Card card) {
		Card theCard = getCardFromCrmMsg(card);
		if (theCard == null) {
			logger.error("根据crm发送的消息不能查询到对应的card信息，因此不处理");
			return;
		}
		theCard.setStatus(Card.STATUS_WRITEOFF);
		cardRepository.save(theCard);
	}
	
	
}