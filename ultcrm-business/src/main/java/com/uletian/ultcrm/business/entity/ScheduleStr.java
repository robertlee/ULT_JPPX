package com.uletian.ultcrm.business.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Date;

/**
 * The persistent class for the business_type database table.
 * 
 */
public class ScheduleStr implements Serializable {
	private static final long serialVersionUID = 1L;

	private int orderNum = 0;//剩余名额人数
	private Schedule schedule = null;//报名人数
	private String store = null;
	private String className = null;

	public int getOrderNum() {
		return this.orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public Schedule getSchedule() {
		return this.schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}
	public String getStore() {
		return this.store;
	}

	public void setStore(String store) {
		this.store = store;
	}
	public String getClassName() {
		return this.className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
}