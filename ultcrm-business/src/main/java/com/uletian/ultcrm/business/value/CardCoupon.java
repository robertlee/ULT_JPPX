/**
 * Copyright &copy; 2014 uletian All right reserved
 */
package com.uletian.ultcrm.business.value;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author robertxie
 * 2015年10月28日
 */
public class CardCoupon {
	/**
	 * 1. card, coupon
	 * 2. 卡券的类型
	 */
	private String type;
	
	/**
	 * card, coupon
	 */
	private String subType;
	
	private Object data;

	private Long id;

	private Double amount;

	private String batchNo;

	private Timestamp createTime;

	private Long createUserId;

	private String description;

	private Date endDate;

	private Timestamp lastUpdateTime;

	private Long lastUpdateUserid;

	private String name;

	private Long period;

	private String periodType;

	private Date startDate;

	private String status;

	private Integer totalCount;
	
	private Integer usedCount =0;
	
	// 优惠说明
	private String term;
	
	// 备注信息
	private String remark;
	
	// 适用门店
	private String applyStore; 
	
	private Integer remainCount;

	/**
	 * @return the remainCount
	 */
	public Integer getRemainCount() {
		if (totalCount != null) {
			return totalCount - usedCount;
		}
		else {
			return 0;
		}
		
	}

	/**
	 * @param remainCount the remainCount to set
	 */
	public void setRemainCount(Integer remainCount) {
		this.remainCount = remainCount;
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
	 * @return the data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * @return the subType
	 */
	public String getSubType() {
		return subType;
	}

	/**
	 * @param subType the subType to set
	 */
	public void setSubType(String subType) {
		this.subType = subType;
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

	/**
	 * @return the batchNo
	 */
	public String getBatchNo() {
		return batchNo;
	}

	/**
	 * @param batchNo the batchNo to set
	 */
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	/**
	 * @return the createTime
	 */
	public Timestamp getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		
		if (this.getSubType().equals("couponBatch") || this.getSubType().equals("cardBatch")) {
			if (this.getPeriodType().equals("DELAY")) {
				Calendar c = Calendar.getInstance();
				c.setTime(new Date());
				c.roll(Calendar.DAY_OF_YEAR, this.getPeriod().intValue());
				return c.getTime();
			}
			else {
				return endDate;
			}
		}
		else {
			return endDate;
		}
		
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the lastUpdateTime
	 */
	public Timestamp getLastUpdateTime() {
		return lastUpdateTime;
	}

	/**
	 * @param lastUpdateTime the lastUpdateTime to set
	 */
	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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

	/**
	 * @return the periodType
	 */
	public String getPeriodType() {
		return periodType;
	}

	/**
	 * @param periodType the periodType to set
	 */
	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		
		if (this.getSubType().equals("couponBatch") || this.getSubType().equals("cardBatch")) {
			if (this.getPeriodType().equals("DELAY")) {
				return new Date();
			}
			else {
				return startDate;
			}
		}
		else {
			return startDate;
		}
		
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
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
	 * @return the totalCount
	 */
	public Integer getTotalCount() {
		return totalCount;
	}

	/**
	 * @param totalCount the totalCount to set
	 */
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * @return the usedCount
	 */
	public Integer getUsedCount() {
		return usedCount;
	}

	/**
	 * @param usedCount the usedCount to set
	 */
	public void setUsedCount(Integer usedCount) {
		this.usedCount = usedCount;
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
