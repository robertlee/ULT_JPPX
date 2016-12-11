package com.uletian.ultcrm.business.entity;

import java.io.Serializable;
import javax.persistence.*;
import org.apache.http.client.utils.DateUtils;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * The persistent class for the area database table.
 * 
 */
@Entity
@NamedQuery(name="Children.findAll", query="SELECT a FROM Children a")
public class Children implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;//主键
	private String name = null;//姓名
	@Column(name="birthday")
	private Date birthday = null;//出生年月
	@Column(name="create_time")
	private Date createTime;
	@Column(name="create_user_id")
	private Long createUserId;
	@Column(name="last_update_time")
	private Date lastUpdateTime;
	@Column(name="last_update_userid")
	private Long lastUpdateUserid;
	@ManyToOne
	@JoinColumn(name="customerid")
	private Customer customer;
	
	@Transient
	private Long customerId;
	@Transient
	private String year;
	@Transient
	private String month;

	public Children() {
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
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreateTime() {
		return this.createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getLastUpdateTime() {
		return this.lastUpdateTime;
	}
	public void setLastUpdateTime(Date lastUpdateTime) {
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
	public Date getBirthday() {
		return this.birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public Customer getCustomer() {
		return this.customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * @return the month
	 */
	public String getMonth() {
		return month;
	}

	/**
	 * @param month the month to set
	 */
	public void setMonth(String month) {
		this.month = month;
	}	
	public void fillOtherFields() {

		if (this.getCreateUserId() == null) {
			this.createUserId =1L ;			
		}
		if (this.getBirthday()!=null){
			this.year = DateUtils.formatDate(this.getBirthday(), "yyyy");
			this.month = DateUtils.formatDate(this.getBirthday(), "MM");
		}
	}
}