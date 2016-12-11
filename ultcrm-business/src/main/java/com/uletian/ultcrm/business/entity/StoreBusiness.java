package com.uletian.ultcrm.business.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the store_business database table.
 * 
 */
@Entity
@Table(name="store_business")
@NamedQuery(name="StoreBusiness.findAll", query="SELECT s FROM StoreBusiness s")
public class StoreBusiness implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	

	@Column(name="create_time")
	private Timestamp createTime;

	@Column(name="create_user_id")
	private Long createUserId;

	@Column(name="last_update_time")
	private Timestamp lastUpdateTime;

	@Column(name="last_update_userid")
	private Long lastUpdateUserid;

	private Long storeid;

	//bi-directional many-to-one association to BusinessType
	@ManyToOne
	@JoinColumn(name="businesstypeid")
	private BusinessType businessType;

	public StoreBusiness() {
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



	public BusinessType getBusinessType() {
		return this.businessType;
	}

	public void setBusinessType(BusinessType businessType) {
		this.businessType = businessType;
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
	 * @return the storeid
	 */
	public Long getStoreid() {
		return storeid;
	}

	/**
	 * @param storeid the storeid to set
	 */
	public void setStoreid(Long storeid) {
		this.storeid = storeid;
	}
	
	

}