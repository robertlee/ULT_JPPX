package com.uletian.ultcrm.business.entity;

import org.apache.commons.httpclient.NameValuePair;

public class SmsMessage {
	private String content;
	private String phoneList;
	private String sendDate;
	private String serialNumber;
	private String extendAccess;
	private String f;
	
	public SmsMessage(){}
	
	public SmsMessage(String phone, String content){
		this.phoneList = phone;
		this.content = content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
		public void setPhoneList(String phoneList) {
		this.phoneList = phoneList;
	}

	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public void setExtendAccess(String extendAccess) {
		this.extendAccess = extendAccess;
	}

	public void setF(String f) {
		this.f = f;
	}

	public NameValuePair[] getParam(){
		NameValuePair[] values = new NameValuePair[]{
				new NameValuePair("MessageContent", this.content),
				new NameValuePair("UserNumber", this.phoneList),
				new NameValuePair("SerialNumber", this.serialNumber),
				new NameValuePair("ScheduleTime", this.sendDate),
				new NameValuePair("ExtendAccessNum", this.extendAccess),
				new NameValuePair("f", this.f),
		};
		return values;
	}
}