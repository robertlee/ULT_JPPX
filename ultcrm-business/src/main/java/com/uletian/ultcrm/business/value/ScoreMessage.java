/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.business.value;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.uletian.ultcrm.business.msg.MsgObject;
import com.uletian.ultcrm.business.value.CardMessage.CardConsumeItem;
import com.uletian.ultcrm.business.value.CardMessage.CardContent;
import com.uletian.ultcrm.common.XStreamTimeDateConverter;
import com.uletian.ultcrm.common.util.Reflections;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

/**
 * 
 * @author robertxie
 * 2015年10月22日
 */
@XStreamAlias("score")
public class ScoreMessage implements MsgObject<ScoreMessage>{

	
	private String action;
	private String sourceSys;
	private String ultcrmcustid="";
	private String techerno="";
	private String techlevelno="";
	private String phone="";
	private String totalscore="";
	private String crmtechid="";
	
	
	@XStreamAlias("items")
	private List<ScoreContent> scoreList = new ArrayList<ScoreContent>();
	
	public void addScore(ScoreContent score) {
		this.scoreList.add(score);
	}
	
	public  String toXML(){
		XStream xstream = new XStream();
		xstream.processAnnotations(ScoreMessage.class); 
		xstream.processAnnotations(ScoreContent.class);
		return xstream.toXML(this);
	}
	
	public ScoreMessage toObject(String xmlString){
		XStream xstream = new XStream();
		xstream.processAnnotations(ScoreMessage.class); 
		xstream.processAnnotations(ScoreContent.class);
		Object o =  xstream.fromXML(xmlString);
		arrangeAndTrimProperties((ScoreMessage)o);
		return (ScoreMessage)o;
	}
	
	
	public void arrangeAndTrimProperties(ScoreMessage scoreMessage){
		// 对前后字符串进行整理， trim
		Reflections.trimAll(scoreMessage);
		if (CollectionUtils.isNotEmpty(scoreMessage.getScoreList())) {
			for (ScoreContent item : scoreMessage.getScoreList()) {
				Reflections.trimAll(item);
			}
		}

	}
	
	/**
	 * 
	 * @author robertxie
	 * 2015年10月27日
	 */
	@XStreamAlias("item")
	public static class ScoreContent{
		
		@XStreamConverter(value=XStreamTimeDateConverter.class)
		public Date time;
		public String desc="";
		public String orderid="";
		public String value="";
		public String storecode="";
		public String itemid ="";
		
		
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
		/**
		 * @return the time
		 */
		public Date getTime() {
			return time;
		}
		/**
		 * @param time the time to set
		 */
		public void setTime(Date time) {
			this.time = time;
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
		 * @return the orderid
		 */
		public String getOrderid() {
			return orderid;
		}
		/**
		 * @param orderid the orderid to set
		 */
		public void setOrderid(String orderid) {
			this.orderid = orderid;
		}
	

		/**
		 * @return the value
		 */
		public String getValue() {
			return value;
		}
		/**
		 * @param value the value to set
		 */
		public void setValue(String value) {
			this.value = value;
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
		this.ultcrmcustid = ultcrmcustid;
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
		this.techerno = techerno;
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
		this.techlevelno = techlevelno;
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
		this.phone = phone;
	}

	/**
	 * @return the totalscore
	 */
	public String getTotalscore() {
		return totalscore;
	}

	/**
	 * @param totalscore the totalscore to set
	 */
	public void setTotalscore(String totalscore) {
		this.totalscore = totalscore;
	}

	/**
	 * @return the crmtechid
	 */
	public String getCrmtechid() {
		return crmtechid;
	}

	/**
	 * @param crmtechid the crmtechid to set
	 */
	public void setCrmtechid(String crmtechid) {
		this.crmtechid = crmtechid;
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
	 * @return the scoreList
	 */
	public List<ScoreContent> getScoreList() {
		return scoreList;
	}

	/**
	 * @param scoreList the scoreList to set
	 */
	public void setScoreList(List<ScoreContent> scoreList) {
		this.scoreList = scoreList;
	}

	
	
	



}
