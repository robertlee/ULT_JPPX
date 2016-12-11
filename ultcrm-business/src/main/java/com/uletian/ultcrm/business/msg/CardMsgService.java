/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.business.msg;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;

import org.apache.activemq.command.ActiveMQTopic;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.stereotype.Component;

import com.uletian.ultcrm.business.entity.Card;
import com.uletian.ultcrm.business.service.CardService;
import com.uletian.ultcrm.business.value.CardMessage;
import com.uletian.ultcrm.business.value.MsgConstants;
import com.uletian.ultcrm.common.util.StringUtils;

/**
 * 处理card相同消息的服务
 * @author robertxie
 * 2015年10月22日
 */
@Component
public class CardMsgService extends AbstractMsgHandler {
	private static Logger logger = Logger.getLogger(CardMsgService.class);
	public static String CARD_TOPIC = "CARD";
	
	@Autowired
	private JmsTemplate topicJmsTemplate;
	
	@Autowired
	private CardService cardService;
	
	public CardMsgService(){
		super(CardMessage.class,"cards");
	}
	
/*	public abstract class CardMsgActionHandler implements MsgActionHandler{
		public void handleMsg(MsgObject msgObject) {
			List<Card> cardList = convertToCardList((CardMessage)msgObject);
			for (Card card : cardList) {
				handle(card);
			}
		}
		
		public abstract void handle(Card card);
	}*/
	
	public void addHandlers() {
		
		MsgActionHandler publishHandler = new MsgActionHandler(){
			@Override
			public void handleMsg(MsgObject msgObject) {
				List<Card> cardList = convertToCardList((CardMessage)msgObject);
				for (Card card : cardList) {
					cardService.crmPublishCard(card);
				}
			}
		};
		
		MsgActionHandler useHandler = new MsgActionHandler(){
			@Override
			public void handleMsg(MsgObject msgObject) {
				List<Card> cardList = convertToCardList((CardMessage)msgObject);
				for (Card card : cardList) {
					cardService.useCard(card);
				}
			}
		};
		
		MsgActionHandler cancelHandler = new MsgActionHandler(){
			@Override
			public void handleMsg(MsgObject msgObject) {
				List<Card> cardList = convertToCardList((CardMessage)msgObject);
				for (Card card : cardList) {
					cardService.crmCancelCard(card);
				}
			}
		};
		
		MsgActionHandler expireHandler = new MsgActionHandler(){
			@Override
			public void handleMsg(MsgObject msgObject) {
				List<Card> cardList = convertToCardList((CardMessage)msgObject);
				for (Card card : cardList) {
					cardService.expireCard(card);
				}
			}
		};
		
		MsgActionHandler adjustHandler = new MsgActionHandler(){
			@Override
			public void handleMsg(MsgObject msgObject) {
				List<Card> cardList = convertToCardList((CardMessage)msgObject);
				for (Card card : cardList) {
					cardService.adjustCard(card);
				}
			}
		};
		
		MsgActionHandler writeoffHandler = new MsgActionHandler(){
			@Override
			public void handleMsg(MsgObject msgObject) {
				List<Card> cardList = convertToCardList((CardMessage)msgObject);
				for (Card card : cardList) {
					cardService.writeoffCard(card);
				}
			}
		};
		
		
		this.addHandler("PUBLISH", publishHandler);
		this.addHandler("USE", useHandler);
		
		this.addHandler("WRITEOFF", writeoffHandler);
		this.addHandler("CANCEL", cancelHandler);
		this.addHandler("EXPIRE", expireHandler);
		this.addHandler("ADJUST", adjustHandler);
	}
	
	/**
	 *  下发次卡
	 * @param card
	 */
	public void publish(Card card) {
		CardMessage cardMessage = convertToCardMessage(card,"PUBLISH");
		
		String xmlString = this.convertObjectToMsgString(cardMessage, "cards");
		logger.info("ultcrm publish a card msg , msg is "+xmlString);
		topicJmsTemplate.convertAndSend(CARD_TOPIC, xmlString, new MessagePostProcessor() {
			public Message postProcessMessage(Message message)
					throws JMSException {
				message.setStringProperty("SENDER", "ICCRM");
				message.setStringProperty("ACTION", "PUBLISH");
				return message;
			}
		});
	}
	
	/**
	 * 取消发出去的卡
	 * @param card  
	 */ 
	public void cancel(Card card) {
		StringBuffer msg = new StringBuffer();
		CardMessage cardMessage = convertToCardMessage(card,"CANCEL");
		
		String xmlString = this.convertObjectToMsgString(cardMessage, "card");
		//topicJmsTemplate.send(destinationName, messageCreator);
		topicJmsTemplate.convertAndSend(CARD_TOPIC, msg, new MessagePostProcessor() {
			public Message postProcessMessage(Message message)
					throws JMSException {
				message.setStringProperty("SENDER", "ICCRM");
				message.setStringProperty("ACTION", "CANCEL");
				return message;
			}
		});
	}
	
	public CardMessage convertToCardMessage(Card card,String action) {
		CardMessage cardMessage = new CardMessage();
		cardMessage.setAction(action);
		cardMessage.setSourceSys(MsgConstants.SOURCE_SYS_ICCRM);
		
		CardMessage.CardContent innerCard = new CardMessage.CardContent();
		innerCard.batchid = card.getCardBatch().getBatchNo();
		innerCard.cardid = card.getCardNo();
		innerCard.amount = card.getAmount();
		innerCard.startdate = card.getStartDate().getClass().equals(java.sql.Timestamp.class)?new Date(card.getStartDate().getTime()):card.getStartDate();
		innerCard.enddate = card.getEndDate().getClass().equals(java.sql.Timestamp.class)?new Date(card.getEndDate().getTime()):card.getEndDate();
		innerCard.status = card.getStatus();
		innerCard.phone = StringUtils.isNotEmpty(card.getCustomer().getPhone())?card.getCustomer().getPhone():"";
		innerCard.ultcrmcustid = card.getCustomer().getId().toString();
		innerCard.desc = StringUtils.isNotEmpty(card.getDescription()) ? card.getDescription() :"";
		innerCard.type = card.getType();
		innerCard.publishtime = card.getPublishTime();
		innerCard.totalcount = card.getTotalCount();
		
		innerCard.techlevelno = card.getTech() != null && StringUtils.isNotBlank(card.getTech().getTechlevelno()) ? card.getTech().getTechlevelno() :"";
		innerCard.techerno = card.getTech() != null && StringUtils.isNotBlank(card.getTech().getTecherno()) ? card.getTech().getTecherno():"";
		innerCard.crmTechId = card.getTech() != null && StringUtils.isNotBlank(card.getTech().getCrmTechId()) ? card.getTech().getCrmTechId():"";
		
		innerCard.remaincount = card.getTotalCount() - card.getUsedCount();
		//innerCard.phone = card.getcu
		cardMessage.addCard(innerCard);
		
		return cardMessage;
	}
	
	public List<Card> convertToCardList(CardMessage cardMessage) {
		List<Card> cardList = new ArrayList<Card>();
		for (CardMessage.CardContent innerCard : cardMessage.getCardList()) {
			Card card = new Card();
			//card.setAmount(innerCard.amount);
			//BeanUtils.copyProperties(innerCard, card);
			card.setCardMsgConetnt(innerCard);
			cardList.add(card);
		}
		return cardList;
	}
	
	
	
	
	
}
