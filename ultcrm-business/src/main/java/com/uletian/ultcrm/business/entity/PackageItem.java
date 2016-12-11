package com.uletian.ultcrm.business.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the package_item database table.
 * 
 */
@Entity
@Table(name="package_item")
@NamedQuery(name="PackageItem.findAll", query="SELECT p FROM PackageItem p")
public class PackageItem implements Serializable {

	private static final long serialVersionUID = -383547412373552824L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private String course;

	@Column(name="create_time")
	private Timestamp createTime;

	@Column(name="create_user_id")
	private Long createUserId;

	//private BigDecimal discount;

	private String flag;

	@Column(name="last_update_time")
	private Timestamp lastUpdateTime;

	@Column(name="last_update_userid")
	private Long lastUpdateUserid;

	private String name;

	//private String originalid;

	private String pic;

	private BigDecimal price;

	private Long typeid;

	//bi-directional many-to-one association to Package
	@ManyToOne
	@JoinColumn(name="subpackageid")
	private SubPackage pkg;

	public PackageItem() {
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



	public String getCourse() {
		return this.course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}



	//public BigDecimal getDiscount() {
	//	return this.discount;
	//}

	//public void setDiscount(BigDecimal discount) {
	//	this.discount = discount;
	//}

	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
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

	//public String getOriginalid() {
	//	return this.originalid;
	//}

	//public void setOriginalid(String originalid) {
	//	this.originalid = originalid;
	//}

	public String getPic() {
		return this.pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
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



	/**
	 * @return the pkg
	 */
	public SubPackage getPkg() {
		return pkg;
	}

	/**
	 * @param pkg the pkg to set
	 */
	public void setPkg(SubPackage pkg) {
		this.pkg = pkg;
	}



}