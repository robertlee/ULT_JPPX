package com.uletian.ultcrm.business.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * 优惠券批次对象
 */
@Entity
@Table(name="coupon_batch")
@NamedQuery(name="CouponBatch.findAll", query="SELECT c FROM CouponBatch c")
public class CouponBatch implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private Double amount;

	@Column(name="batch_no")
	private String batchNo;

	@Column(name="create_time")
	private Timestamp createTime;

	@Column(name="create_user_id")
	private Long createUserId;

	private String description;

	@Column(name="end_date")
	private Date endDate;

	@Column(name="last_update_time")
	private Timestamp lastUpdateTime;

	@Column(name="last_update_userid")
	private Long lastUpdateUserid;

	private String name;

	private Long period;
	
	/**
	 * 期限类型，FIXED, DELAY
	 */
	@Column(name="period_type")
	private String periodType;

	@Column(name="start_date")
	private Date startDate;

	private String status;
	
	/**
	 *  优惠券的类型
	 *  ZPVH  通用优惠抬头折扣 
	 *	ZVAC  精品优惠金额折扣
	 *	ZVME  机电人工优惠金额折扣
	 *	ZVPR  配件优惠金额折扣
	 *	ZVPT  钣喷人工优惠金额折扣
	 *	ZVRS  保养优惠金额折扣
	 */
	private String type;
	
	// 优惠说明
	private String term;
	
	// 备注信息
	private String remark;
	
	// 适用门店
	private String applyStore; 
	
	/**
	 * Y, N
	 */
	@Column(name="is_ultcrm_batch")
	private String isULTcrmBatch;

	public CouponBatch() {
	}

	
	/**
	 * @return the isULTcrmBatch
	 */
	public String getIsULTcrmBatch() {
		return isULTcrmBatch;
	}



	/**
	 * @param isULTcrmBatch the isULTcrmBatch to set
	 */
	public void setIsULTcrmBatch(String isULTcrmBatch) {
		this.isULTcrmBatch = isULTcrmBatch;
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


	public String getBatchNo() {
		return this.batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}



	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}



	public Timestamp getLastUpdateTime() {
		return this.lastUpdateTime;
	}

	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}



	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
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



	/**
	 * @return the period
	 */
	public Long getPeriod() {
		return period;
	}



	/**
	 * @param period the period to set
	 */
	public void setPeriod(Long period) {
		this.period = period;
	}



	public String getPeriodType() {
		return this.periodType;
	}

	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}



	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}


	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}


	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}


	/**
	 * @return the term
	 */
	public String getTerm() {
		return term;
	}


	/**
	 * @param term the term to set
	 */
	public void setTerm(String term) {
		this.term = term;
	}


	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}


	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}


	/**
	 * @return the applyStore
	 */
	public String getApplyStore() {
		return applyStore;
	}


	/**
	 * @param applyStore the applyStore to set
	 */
	public void setApplyStore(String applyStore) {
		this.applyStore = applyStore;
	}
	
	
}