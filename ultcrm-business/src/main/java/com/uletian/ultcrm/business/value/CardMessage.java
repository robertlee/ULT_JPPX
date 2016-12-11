/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.business.value;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.uletian.ultcrm.business.msg.MsgObject;
import com.uletian.ultcrm.common.AdvanceIntConverter;
import com.uletian.ultcrm.common.XStreamDateConverter;
import com.uletian.ultcrm.common.XStreamTimeDateConverter;
import com.uletian.ultcrm.common.util.Reflections;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamImplicit;



/**
 * 
 * @author robertxie
 * 2015年10月22日
 */
@XStreamAlias("cards")
public class CardMessage implements MsgObject<CardMessage>{
	
	private String action;
	
	private String sourceSys;
	
	@XStreamImplicit
	private List<CardContent> cardList = new ArrayList<CardContent>();
	
	public void addCard(CardContent card) {
		this.cardList.add(card);
	}
	
	public  String toXML(){
		XStream xstream = new XStream();
		xstream.processAnnotations(CardMessage.class); 
		xstream.processAnnotations(CardContent.class);
		
		xstream.processAnnotations(CardConsumeItem.class);
		return xstream.toXML(this);
	}
	
	public  CardMessage toObject(String xmlString){
		XStream xstream = new XStream();
		xstream.processAnnotations(CardMessage.class); 
		xstream.processAnnotations(CardContent.class);
		xstream.processAnnotations(CardConsumeItem.class);
		Object o =  xstream.fromXML(xmlString);
		arrangeAndTrimProperties((CardMessage)o);
		return (CardMessage)o;
	}
	
	public void arrangeAndTrimProperties(CardMessage cardMessage){
		// 对前后字符串进行整理， trim
		Reflections.trimAll(cardMessage);
		if (CollectionUtils.isNotEmpty(cardMessage.getCardList())) {
			for (CardContent cardContent : cardMessage.getCardList()) {
				Reflections.trimAll(cardContent);
				if (CollectionUtils.isNotEmpty(cardContent.getConsumeItemList())) {
					for (CardConsumeItem item : cardContent.getConsumeItemList()) {
						Reflections.trimAll(item);
					}
				}
			}
		} 
	}
	
	
	@XStreamAlias("card")
	public static class CardContent{
		public String batchid ="";
		
		public String cardid ="";
		
		@XStreamConverter(value=AdvanceIntConverter.class)
		public Integer totalcount=0;
		
		@XStreamConverter(value=AdvanceIntConverter.class)
		public Integer remaincount = 0 ;
		
		public String type="";
		
		@XStreamConverter(value=XStreamDateConverter.class)
		public Date startdate;
		@XStreamConverter(value=XStreamDateConverter.class)
		public Date enddate;
		public Double amount;
		public String status="";
		public String storecode="";
		
		public String techerno="";
		public String techlevelno="";
		public String crmTechId="";
		public String phone=" ";
		
		public String ultcrmcustid="";
		
		@XStreamConverter(value=XStreamTimeDateConverter.class)
		public Date publishtime; 
		
		public String desc="";
		
		@XStreamAlias("consumerecord")
		public List<CardConsumeItem> consumeItemList = new ArrayList<CardConsumeItem>();

		
		
		/**
		 * @return the crmTechId
		 */
		public String getCrmTechId() {
			return crmTechId;
		}
		/**
		 * @param crmTechId the crmTechId to set
		 */
		public void setCrmTechId(String crmTechId) {
			this.crmTechId = crmTechId;
		}
		/**
		 * @return the publishtime
		 */
		public Date getPublishtime() {
			return publishtime;
		}
		/**
		 * @param publishtime the publishtime to set
		 */
		public void setPublishtime(Date publishtime) {
			this.publishtime = publishtime;
		}
		/**
		 * @return the remaincount
		 */
		public Integer getRemaincount() {
			return remaincount;
		}
		/**
		 * @param remaincount the remaincount to set
		 */
		public void setRemaincount(Integer remaincount) {
			this.remaincount = remaincount;
		}
		public String getBatchid() {
			return batchid;
		}
		public void setBatchid(String batchid) {
			this.batchid = batchid;
		}
		public String getCardid() {
			return cardid;
		}
		public void setCardid(String cardid) {
			this.cardid = cardid;
		}
		public Integer getTotalcount() {
			return totalcount;
		}
		public void setTotalcount(Integer totalcount) {
			this.totalcount = totalcount;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public Date getStartdate() {
			return startdate;
		}
		public void setStartdate(Date startdate) {
			this.startdate = startdate;
		}
		public Date getEnddate() {
			return enddate;
		}
		public void setEnddate(Date enddate) {
			this.enddate = enddate;
		}
		public Double getAmount() {
			return amount;
		}
		public void setAmount(Double amount) {
			this.amount = amount;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getStorecode() {
			return storecode;
		}
		public void setStorecode(String storecode) {
			this.storecode = storecode;
		}
		public String getTecherno() {
			return techerno;
		}
		public void setTecherno(String techerno) {
			this.techerno = techerno;
		}
		public String getTechlevelno() {
			return techlevelno;
		}
		public void setTechlevelno(String techlevelno) {
			this.techlevelno = techlevelno;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public String getULTcrmcustid() {
			return ultcrmcustid;
		}
		public void setULTcrmcustid(String ultcrmcustid) {
			this.ultcrmcustid = ultcrmcustid;
		}
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
		public List<CardConsumeItem> getConsumeItemList() {
			return consumeItemList;
		}
		public void setConsumeItemList(List<CardConsumeItem> consumeItemList) {
			this.consumeItemList = consumeItemList;
		}
	}
	
	/**
	 * 
	 * @author robertxie
	 * 2015年10月27日
	 */
	@XStreamAlias("item")
	public static class CardConsumeItem{
		@XStreamConverter(value=XStreamTimeDateConverter.class)
		public Date time = null;
		public String itemid = "";
		public String consumestore ="";
		public String orderid ="";

		/**
		 * @return the itemid
		 */
		public String getItemid() {
			return itemid;
		}

		/**
		 * @param itemid the itemid to set
		 */
		public void setItemid(String itemid) {
			this.itemid = itemid;
		}

		public Date getTime() {
			return time;
		}

		public void setTime(Date time) {
			this.time = time;
		}

		public String getConsumestore() {
			return consumestore;
		}

		public void setConsumestore(String consumestore) {
			this.consumestore = consumestore;
		}
		public String getOrderid() {
			return orderid;
		}
		public void setOrderid(String orderid) {
			this.orderid = orderid;
		}
		
	}
	

	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @return the sourceSys
	 */
	public String getSourceSys() {
		return sourceSys;
	}

	/**
	 * @param sourceSys the sourceSys to set
	 */
	public void setSourceSys(String sourceSys) {
		this.sourceSys = sourceSys;
	}

	/**
	 * @return the cardList
	 */
	public List<CardContent> getCardList() {
		return cardList;
	}

	/**
	 * @param cardList the cardList to set
	 */
	public void setCardList(List<CardContent> cardList) {
		this.cardList = cardList;
	}


	
	
	
}
