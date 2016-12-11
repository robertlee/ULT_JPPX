package com.uletian.ultcrm.business.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * The persistent class for the address database table.
 * 
 */
@Entity
@NamedQuery(name="Address.findAll", query="SELECT a FROM Address a")
public class Address implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private String content;

	@Column(name="create_time")
	private Timestamp createTime;

	@Column(name="create_user_id")
	private Long createUserId;

	@Column(name="last_update_time")
	private Timestamp lastUpdateTime;

	@Column(name="last_update_userid")
	private Long lastUpdateUserid;

	//bi-directional many-to-one association to Area
//	@ManyToOne
//	@JoinColumn(name="areaid")
//	private Area area;
//
//	//bi-directional one-to-many association to Store
//	@OneToMany(mappedBy="address")
//	private List<Store> stores;

	public Address() {
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
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
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
//	public Area getArea() {
//		return this.area;
//	}
//
//	public void setArea(Area area) {
//		this.area = area;
//	}
//
//	public List<Store> getStores() {
//		return this.stores;
//	}
//
//	public void setStores(List<Store> stores) {
//		this.stores = stores;
//	}
//
//	public Store addStore(Store store) {
//		getStores().add(store);
//		store.setAddress(this);
//
//		return store;
//	}
//
//	public Store removeStore(Store store) {
//		getStores().remove(store);
//		store.setAddress(null);
//
//		return store;
//	}

}