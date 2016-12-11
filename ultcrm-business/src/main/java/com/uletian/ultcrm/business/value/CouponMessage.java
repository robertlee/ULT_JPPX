/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.business.value;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.uletian.ultcrm.business.msg.MsgObject;
import com.uletian.ultcrm.business.value.ScoreMessage.ScoreContent;
import com.uletian.ultcrm.common.XStreamDateConverter;
import com.uletian.ultcrm.common.XStreamTimeDateConverter;
import com.uletian.ultcrm.common.util.Reflections;
import com.uletian.ultcrm.common.util.StringUtils;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * @author robertxie
 * 2015年10月22日
 */
@XStreamAlias("coupons")
public class CouponMessage implements MsgObject<CouponMessage>{
	
	private String action;
	
	private String sourceSys;
	
	@XStreamImplicit
	private List<CouponContent> couponList = new ArrayList<CouponContent>();
	
	public void addCoupon(CouponContent Coupon) {
		this.couponList.add(Coupon);
	}
	
	public  String toXML(){
		XStream xstream = new XStream();
		xstream.processAnnotations(CouponMessage.class); 
		xstream.processAnnotations(CouponContent.class);
		return xstream.toXML(this);
	}
	
	public  CouponMessage toObject(String xmlString){
		XStream xstream = new XStream();
		xstream.processAnnotations(CouponMessage.class); 
		xstream.processAnnotations(CouponContent.class);
		Object o =  xstream.fromXML(xmlString);
		arrangeAndTrimProperties((CouponMessage)o);
		return (CouponMessage)o;
	}
	
	
	public void arrangeAndTrimProperties(CouponMessage couponMessage){
		// 对前后字符串进行整理， trim
		Reflections.trimAll(couponMessage);
		if (CollectionUtils.isNotEmpty(couponMessage.getCouponList())) {
			for (CouponContent item : couponMessage.getCouponList()) {
				Reflections.trimAll(item);
			}
		}
	}
	
	
	@XStreamAlias("coupon")
	public static class CouponContent{
		public String batchid ="";
		
		public String couponid ="";

		
		public String type="";
		
		@XStreamConverter(value=XStreamDateConverter.class)
		public Date startdate;
		
		@XStreamConverter(value=XStreamDateConverter.class)
		public Date enddate;
		
		public Double amount;
		public String status="";
		public String storecode="";
		
		@XStreamConverter(value=XStreamTimeDateConverter.class)
		public Date publishtime;
		
		public String techerno="";
		public String techlevelno="";

		public String crmTechId = "";
		
		public String phone="";
		
		public String ultcrmcustid="";
		public String consumestore="";
		public String desc="";


 
		
		/**
		 * @return the batchid
		 */
		public String getBatchid() {
			return batchid;
		}
		/**
		 * @param batchid the batchid to set
		 */
		public void setBatchid(String batchid) {
			this.batchid = batchid;
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
		 * @return the couponid
		 */
		public String getCouponid() {
			return couponid;
		}
		/**
		 * @param couponid the couponid to set
		 */
		public void setCouponid(String couponid) {
			this.couponid = couponid;
		}
		/**
		 * @return the type
		 */
		public String getType() {
			return type;
		}
		/**
		 * @param type the type to set
		 */
		public void setType(String type) {
			this.type = type;
		}
		/**
		 * @return the startdate
		 */
		public Date getStartdate() {
			return startdate;
		}
		/**
		 * @param startdate the startdate to set
		 */
		public void setStartdate(Date startdate) {
			this.startdate = startdate;
		}
		/**
		 * @return the enddate
		 */
		public Date getEnddate() {
			return enddate;
		}
		/**
		 * @param enddate the enddate to set
		 */
		public void setEnddate(Date enddate) {
			this.enddate = enddate;
		}
		/**
		 * @return the amount
		 */
		public Double getAmount() {
			return amount;
		}
		/**
		 * @param amount the amount to set
		 */
		public void setAmount(Double amount) {
			this.amount = amount;
		}
		/**
		 * @return the status
		 */
		public String getStatus() {
			return status;
		}
		/**
		 * @param status the status to set
		 */
		public void setStatus(String status) {
			this.status = status;
		}
		/**
		 * @return the storecode
		 */
		public String getStorecode() {
			return storecode;
		}
		/**
		 * @param storecode the storecode to set
		 */
		public void setStorecode(String storecode) {
			this.storecode = storecode;
		}
		/**
		 * @return the techerno
		 */
		public String getTecherno() {
			return techerno;
		}
		/**
		 * @param techerno the techerno to set
		 */
		public void setTecherno(String techerno) {
			if (StringUtils.isNotBlank(techerno)) {
				this.techerno = techerno;
			}
		}
		/**
		 * @return the techlevelno
		 */
		public String getTechlevelno() {
			return techlevelno;
		}
		/**
		 * @param techlevelno the techlevelno to set
		 */
		public void setTechlevelno(String techlevelno) {
			if (StringUtils.isNotBlank(techlevelno)) {
				this.techlevelno = techlevelno;
			}
		}
		/**
		 * @return the phone
		 */
		public String getPhone() {
			return phone;
		}
		/**
		 * @param phone the phone to set
		 */
		public void setPhone(String phone) {
			if (StringUtils.isNotBlank(phone)) {
				this.phone = phone;
			}
		}
		/**
		 * @return the ultcrmcustid
		 */
		public String getULTcrmcustid() {
			return ultcrmcustid;
		}
		/**
		 * @param ultcrmcustid the ultcrmcustid to set
		 */
		public void setULTcrmcustid(String ultcrmcustid) {
			if (StringUtils.isNotBlank(ultcrmcustid)) {
				this.ultcrmcustid = ultcrmcustid;
			}
		}
		/**
		 * @return the consumestore
		 */
		public String getConsumestore() {
			return consumestore;
		}
		/**
		 * @param consumestore the consumestore to set
		 */
		public void setConsumestore(String consumestore) {
			this.consumestore = consumestore;
		}
		/**
		 * @return the desc
		 */
		public String getDesc() {
			return desc;
		}
		/**
		 * @param desc the desc to set
		 */
		public void setDesc(String desc) {
			this.desc = desc;
		}
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
	 * @return the CouponList
	 */
	public List<CouponContent> getCouponList() {
		return couponList;
	}

	/**
	 * @param CouponList the CouponList to set
	 */
	public void setCouponList(List<CouponContent> CouponList) {
		this.couponList = CouponList;
	}
	
	
	


}
