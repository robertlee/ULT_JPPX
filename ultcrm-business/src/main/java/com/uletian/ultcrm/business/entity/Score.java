package com.uletian.ultcrm.business.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;


/**
 * The persistent class for the score database table.
 * 
 */
/**
 * 
 * @author robertxie
 * 2015年10月26日
 */
@Entity
@NamedQuery(name="Score.findAll", query="SELECT s FROM Score s")
public class Score implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String itemid;

	@Column(name="create_time")
	private Timestamp createTime;

	@Column(name="create_user_id")
	private Long createUserId;

	private String description;

	@Column(name="last_update_time")
	private Timestamp lastUpdateTime;

	@Column(name="last_update_userid")
	private Long lastUpdateUserid;

	private Date time;

	private Integer value;

	//bi-directional many-to-one association to Tech
	@ManyToOne
	private Tech tech;

	//bi-directional many-to-one association to Order
	@ManyToOne
	private Order order;

	//bi-directional many-to-one association to Store
	@ManyToOne
	private Store store;
	
	@Transient
	private String techlevelno;
	@Transient
	private String crmtechid;
	@Transient
	private Integer totalscore;
	@Transient
	private String ultcrmcustid;
	@Transient
	private String phone;

	// 这个字段其实是crm order id
	@Transient
	private String orderid;

	@Transient
	private String desc;
	@Transient
	private String storecode;
	
	@Transient
	private String techerno;

	public Score() {
	}
	
	/**
	 * @return the techerno
	 */
	public String getTecherno() {
		return techerno;
	}

	/**
	 * @param techerno the techerno to set
	 */
	public void setTecherno(String techerno) {
		this.techerno = techerno;
	}

	/**
	 * @return the itemid
	 */
	public String getItemid() {
		return itemid;
	}

	/**
	 * @param itemid the itemid to set
	 */
	public void setItemid(String itemid) {
		this.itemid = itemid;
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
	 * @return the orderid
	 */
	public String getOrderid() {
		return orderid;
	}




	/**
	 * @param orderid the orderid to set
	 */
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}




	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}




	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}




	/**
	 * @return the storecode
	 */
	public String getStorecode() {
		return storecode;
	}




	/**
	 * @param storecode the storecode to set
	 */
	public void setStorecode(String storecode) {
		this.storecode = storecode;
	}




	/**
	 * @param value the value to set
	 */
	public void setValue(Integer value) {
		this.value = value;
	}




	/**
	 * @return the techlevelno
	 */
	public String getTechlevelno() {
		return techlevelno;
	}

	/**
	 * @param techlevelno the techlevelno to set
	 */
	public void setTechlevelno(String techlevelno) {
		this.techlevelno = techlevelno;
	}

	/**
	 * @return the crmtechid
	 */
	public String getCrmtechid() {
		return crmtechid;
	}

	/**
	 * @param crmtechid the crmtechid to set
	 */
	public void setCrmtechid(String crmtechid) {
		this.crmtechid = crmtechid;
	}



	/**
	 * @return the totalscore
	 */
	public Integer getTotalscore() {
		return totalscore;
	}




	/**
	 * @param totalscore the totalscore to set
	 */
	public void setTotalscore(Integer totalscore) {
		this.totalscore = totalscore;
	}




	/**
	 * @return the ultcrmcustid
	 */
	public String getULTcrmcustid() {
		return ultcrmcustid;
	}

	/**
	 * @param ultcrmcustid the ultcrmcustid to set
	 */
	public void setULTcrmcustid(String ultcrmcustid) {
		this.ultcrmcustid = ultcrmcustid;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
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
	 * @return the time
	 */
	public Date getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(Date time) {
		this.time = time;
	}

	public Integer getValue() {
		return this.value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public Tech getTech() {
		return this.tech;
	}

	public void setTech(Tech tech) {
		this.tech = tech;
	}

	public Order getOrder() {
		return this.order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Store getStore() {
		return this.store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

}