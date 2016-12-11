package com.uletian.ultcrm.business.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;

import com.uletian.ultcrm.business.value.CouponMessage.CouponContent;


/**
 * 优惠券对象
 * 
 */
@Entity
@NamedQuery(name="Coupon.findAll", query="SELECT c FROM Coupon c")
public class Coupon implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String STATUS_PUBLISH="003";// 已发布
	public static final String STATUS_WRITEOFF="004"; // 已核销
	public static final String STATUS_CANCEL="005"; // 已取消 已冻结
	public static final String STATUS_EXPIRE="006"; // 已过期

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	/**
	 * 优惠券的抵扣金额
	 */
	private Double amount;
	
	@ManyToOne
	@JoinColumn(name="batch_id")
	private CouponBatch couponBatch;
	
	/**
	 * 长度是12位，以W打头
	 */
	@Column(name="coupon_no")
	private String couponNo;

	@Column(name="create_time")
	private Timestamp createTime;

	@Column(name="create_user_id")
	private Long createUserId;

	@ManyToOne
	@JoinColumn(name="customer_id")
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(name="tech_id")
	private Tech tech;
	
	@ManyToOne
	@JoinColumn(name="event_id")
	private Event event;
	
	private Integer period;
	
	@Column(name="period_Type")
	private String periodType;
	
	private String description;

	@Column(name="end_date")
	private Date endDate;

	@Column(name="last_update_time")
	private Timestamp lastUpdateTime;

	@Column(name="last_update_userid")
	private Long lastUpdateUserid;

	private String name;

	@Column(name="start_date")
	private Date startDate;

	private String status;
	
	
	private Date publishTime;
	
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

	
	@Transient
	private CouponContent couponContent;

	public Coupon() {
	}
	
	/**
	 * @return the publishTime
	 */
	public Date getPublishTime() {
		return publishTime;
	}

	/**
	 * @return the tech
	 */
	public Tech getTech() {
		return tech;
	}

	/**
	 * @param tech the tech to set
	 */
	public void setTech(Tech tech) {
		this.tech = tech;
	}

	/**
	 * @return the event
	 */
	public Event getEvent() {
		return event;
	}

	/**
	 * @param event the event to set
	 */
	public void setEvent(Event event) {
		this.event = event;
	}

	/**
	 * @param publishTime the publishTime to set
	 */
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	/**
	 * @return the couponContent
	 */
	public CouponContent getCouponContent() {
		return couponContent;
	}

	/**
	 * @param couponContent the couponContent to set
	 */
	public void setCouponContent(CouponContent couponContent) {
		this.couponContent = couponContent;
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
	 * @return the couponBatch
	 */
	public CouponBatch getCouponBatch() {
		return couponBatch;
	}

	/**
	 * @param couponBatch the couponBatch to set
	 */
	public void setCouponBatch(CouponBatch couponBatch) {
		this.couponBatch = couponBatch;
	}






	/**
	 * @return the lastUpdateUserid
	 */
	public Long getLastUpdateUserid() {
		return lastUpdateUserid;
	}



	public String getCouponNo() {
		return this.couponNo;
	}

	public void setCouponNo(String couponNo) {
		this.couponNo = couponNo;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

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
	 * @param lastUpdateUserid the lastUpdateUserid to set
	 */
	public void setLastUpdateUserid(Long lastUpdateUserid) {
		this.lastUpdateUserid = lastUpdateUserid;
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
	 * @return the customer
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
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
	 * @return the period
	 */
	public Integer getPeriod() {
		return period;
	}

	/**
	 * @param period the period to set
	 */
	public void setPeriod(Integer period) {
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

}