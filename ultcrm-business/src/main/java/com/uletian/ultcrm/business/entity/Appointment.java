package com.uletian.ultcrm.business.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the appointment database table.
 * 
 */
@Entity
@NamedQuery(name="Appointment.findAll", query="SELECT a FROM Appointment a")
public class Appointment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(name="create_time")
	private Timestamp createTime;

	@Column(name="create_user_id")
	private int createUserId;

	@Column(name="last_update_time")
	private Timestamp lastUpdateTime;

	@Column(name="last_update_userid")
	private int lastUpdateUserid;

	//bi-directional one-to-one association to Order
	@OneToOne
	@JoinColumn(name="orderid")
	private Order order;

	//bi-directional many-to-one association to TimeSegment
	@ManyToOne
	@JoinColumn(name="segmentid")
	private TimeSegment timeSegment;
	
    @Transient
	private Long timeSegmentId;
	
	@Column(name="customerid")
	private Long customerId;

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getTimeSegmentId() {
		return timeSegmentId;
	}

	public void setTimeSegmentId(Long timeSegmentId) {
		this.timeSegmentId = timeSegmentId;
	}

	//bi-directional many-to-one association to Store
	@ManyToOne
	@JoinColumn(name="storeid")
	private Store store;

	public Appointment() {
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



	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public int getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}

	public Timestamp getLastUpdateTime() {
		return this.lastUpdateTime;
	}

	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public int getLastUpdateUserid() {
		return this.lastUpdateUserid;
	}

	public void setLastUpdateUserid(int lastUpdateUserid) {
		this.lastUpdateUserid = lastUpdateUserid;
	}

	public Order getOrder() {
		return this.order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public TimeSegment getTimeSegment() {
		return this.timeSegment;
	}

	public void setTimeSegment(TimeSegment timeSegment) {
		this.timeSegment = timeSegment;
	}

	public Store getStore() {
		return this.store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

}