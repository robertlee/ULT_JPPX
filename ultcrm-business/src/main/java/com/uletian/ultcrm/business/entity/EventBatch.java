package com.uletian.ultcrm.business.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * 事件批次关联对象
 * 
 */
@Entity
@Table(name="event_batch")
@NamedQuery(name="EventBatch.findAll", query="SELECT e FROM EventBatch e")
public class EventBatch implements Serializable {
	private static final long serialVersionUID = 1L;

	public enum Type {
		card(1), coupon(2);
		private int value = 0;
		Type(int value){
			this.value = value;
		}
		int getValue(){
			return value;
		}
	};
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	

	@Column(name="batch_id")
	private Long batchId;

	@Column(name="create_time")
	private Timestamp createTime;
	
	private String batchType;

	@Column(name="create_user_id")
	private Long createUserId;
	
	@Column(name="last_update_time")
	private Timestamp lastUpdateTime;

	@Column(name="last_update_userid")
	private Long lastUpdateUserid;

	public EventBatch() {
	}

	/**
	 * @return the batchType
	 */
	public String getBatchType() {
		return batchType;
	}

	/**
	 * @param batchType the batchType to set
	 */
	public void setBatchType(String batchType) {
		this.batchType = batchType;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the batchId
	 */
	public Long getBatchId() {
		return batchId;
	}

	/**
	 * @param batchId the batchId to set
	 */
	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}

	/**
	 * @return the createUserId
	 */
	public Long getCreateUserId() {
		return createUserId;
	}

	/**
	 * @param createUserId the createUserId to set
	 */
	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	/**
	 * @return the lastUpdateUserid
	 */
	public Long getLastUpdateUserid() {
		return lastUpdateUserid;
	}

	/**
	 * @param lastUpdateUserid the lastUpdateUserid to set
	 */
	public void setLastUpdateUserid(Long lastUpdateUserid) {
		this.lastUpdateUserid = lastUpdateUserid;
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
}