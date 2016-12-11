package com.uletian.ultcrm.business.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the teacher database table.
 * 
 */
@Entity
public class Teacher implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private String address = null;

	@Column(name="card_id")
	private String cardId = null;

	@Column(name="create_time")
	private Timestamp createTime = null;

	@Column(name="create_user_id")
	private Long createUserId = null;

	@Column(name="last_update_time")
	private Timestamp lastUpdateTime = null;

	@Column(name="last_update_userid")
	private Long lastUpdateUserid = null;

	private String mobilephone = null;

	private String name = null;

	private String passbook = null;

	@Column(name="qq_id")
	private Long qqId = null;

	private String sex = null;

	//bi-directional many-to-one association to Schedule
	@OneToMany(mappedBy="teacher")
	private List<Schedule> schedules;

	public Teacher() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCardId() {
		return this.cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Long getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public Timestamp getLastUpdateTime() {
		return this.lastUpdateTime;
	}

	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Long getLastUpdateUserid() {
		return this.lastUpdateUserid;
	}

	public void setLastUpdateUserid(Long lastUpdateUserid) {
		this.lastUpdateUserid = lastUpdateUserid;
	}

	public String getMobilephone() {
		return this.mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassbook() {
		return this.passbook;
	}

	public void setPassbook(String passbook) {
		this.passbook = passbook;
	}

	public Long getQqId() {
		return this.qqId;
	}

	public void setQqId(Long qqId) {
		this.qqId = qqId;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public List<Schedule> getSchedules() {
		return this.schedules;
	}

	public void setSchedules(List<Schedule> schedules) {
		this.schedules = schedules;
	}

	public Schedule addSchedule(Schedule schedule) {
		getSchedules().add(schedule);
		schedule.setTeacher(this);

		return schedule;
	}

	public Schedule removeSchedule(Schedule schedule) {
		getSchedules().remove(schedule);
		schedule.setTeacher(null);

		return schedule;
	}

}