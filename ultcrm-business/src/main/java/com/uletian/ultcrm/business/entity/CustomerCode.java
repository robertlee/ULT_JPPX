package com.uletian.ultcrm.business.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the customer_code database table.
 * 
 */
@Entity
@Table(name="customer_code")
@NamedQuery(name="CustomerCode.findAll", query="SELECT c FROM CustomerCode c")
public class CustomerCode implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private String code;

	@Column(name="create_time")
	private Timestamp createTime;

	@Column(name="create_user_id")
	private Long createUserId;

	@Column(name="invalid_time")
	private Timestamp invalidTime;

	@Column(name="last_update_time")
	private Timestamp lastUpdateTime;

	@Column(name="last_update_userid")
	private Long lastUpdateUserid;
	
	@Column(name="image_code")
	private String imageCode;

	@Column(name="phone")
	private String phone;

	private Long typeid;

	//bi-directional many-to-one association to Customer
	@ManyToOne
	@JoinColumn(name="customerid")
	private Customer customer;

	public CustomerCode() {
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

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getInvalidTime() {
		return this.invalidTime;
	}

	public void setInvalidTime(Timestamp invalidTime) {
		this.invalidTime = invalidTime;
	}

	public Timestamp getLastUpdateTime() {
		return this.lastUpdateTime;
	}

	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
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
	 * @return the typeid
	 */
	public Long getTypeid() {
		return typeid;
	}

	/**
	 * @param typeid the typeid to set
	 */
	public void setTypeid(Long typeid) {
		this.typeid = typeid;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getImageCode() {
		return imageCode;
	}

	public void setImageCode(String imageCode) {
		this.imageCode = imageCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}