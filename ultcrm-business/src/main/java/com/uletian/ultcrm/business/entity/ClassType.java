package com.uletian.ultcrm.business.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the area database table.
 * 
 */
@Entity
@Table(name="class_type")
@NamedQuery(name="ClassType.findAll", query="SELECT a FROM ClassType a")
public class ClassType implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;//主键
	@Column(name="start_batch")
	private String startBatch = null;//开课批次
	@Column(name="address_id")
	private Long addressId;//门店地址编号
	@Column(name="address")
	private String address = null;//门店地址
	@Column(name="class_id")
	private Long classId;//课程名称编号
	@Column(name="class_name")
	private String className = null;//课程名称
	@Column(name="create_time")
	private Timestamp createTime;
	@Column(name="create_user_id")
	private Long createUserId;
	@Column(name="last_update_time")
	private Timestamp lastUpdateTime;
	@Column(name="last_update_userid")
	private Long lastUpdateUserid;

	public ClassType() {
	}
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Timestamp getCreateTime() {
		return this.createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Timestamp getLastUpdateTime() {
		return this.lastUpdateTime;
	}
	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public Long getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}
	public Long getLastUpdateUserid() {
		return lastUpdateUserid;
	}
	public void setLastUpdateUserid(Long lastUpdateUserid) {
		this.lastUpdateUserid = lastUpdateUserid;
	}
	public String getStartBatch() {
		return this.startBatch;
	}
	public void setStartBatch(String startBatch) {
		this.startBatch = startBatch;
	}
	public Long getAddressId() {
		return this.addressId;
	}
	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}
	public String getAddress() {
		return this.address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Long getClassId() {
		return this.classId;
	}
	public void setClassId(Long classId) {
		this.classId = classId;
	}
	public String getClassName() {
		return this.className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
}