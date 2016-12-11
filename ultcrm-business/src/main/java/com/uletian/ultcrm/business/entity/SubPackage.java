package com.uletian.ultcrm.business.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.List;

/**
 * The persistent class for the package database table.
 * 
 */
@Entity
@NamedQuery(name = "SubPackage.findAll", query = "SELECT p FROM SubPackage p")
public class SubPackage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "create_time")
	private Timestamp createTime;

	@Column(name = "create_user_id")
	private Long createUserId;

	@Column(name = "last_update_time")
	private Timestamp lastUpdateTime;

	@Column(name = "last_update_userid")
	private Long lastUpdateUserid;

	@Column(name = "detail", length = 4096)
	private String detail;

	private Integer discount;

	@Column(name = "name", length = 255)
	private String name;

	@Column(name = "pic", length = 255)
	private String pic;

	private BigDecimal price;

	@Column(name = "pkgflag", length = 8)
	private String pkgflag;

	@Column(name = "code", length = 64)
	private String code;

	// bi-directional many-to-one association to OrderItem
	@OneToMany(mappedBy = "pkg")
	private List<OrderItem> orderItems;

	// bi-directional many-to-one association to PackageItem
	@OneToMany(mappedBy = "pkg")
	private List<PackageItem> packageItems;
	
	@ManyToOne
	@JoinColumn(name="courseid")
	private TechCourse course;
	
	@ManyToOne
	@JoinColumn(name="storeid")
	private Store store;
	
	@ManyToOne
	@JoinColumn(name="businesstypeid")
	private BusinessType businessType;
	
	@ManyToOne
	@JoinColumn(name="packageid")
	private Package packages;

	public SubPackage() {
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
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

	public String getDetail() {
		return this.detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Integer getDiscount() {
		return this.discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
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

	public List<OrderItem> getOrderItems() {
		return this.orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public OrderItem addOrderItem(OrderItem orderItem) {
		getOrderItems().add(orderItem);
		orderItem.setPkg(this);

		return orderItem;
	}

	public OrderItem removeOrderItem(OrderItem orderItem) {
		getOrderItems().remove(orderItem);
		orderItem.setPkg(null);

		return orderItem;
	}

	public List<PackageItem> getPackageItems() {
		return this.packageItems;
	}

	public void setPackageItems(List<PackageItem> packageItems) {
		this.packageItems = packageItems;
	}

	public PackageItem addPackageItem(PackageItem packageItem) {
		getPackageItems().add(packageItem);
		packageItem.setPkg(this);

		return packageItem;
	}

	public PackageItem removePackageItem(PackageItem packageItem) {
		getPackageItems().remove(packageItem);
		packageItem.setPkg(null);

		return packageItem;
	}

	/**
	 * @return the createUserId
	 */
	public Long getCreateUserId() {
		return createUserId;
	}

	/**
	 * @param createUserId
	 *            the createUserId to set
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
	 * @param lastUpdateUserid
	 *            the lastUpdateUserid to set
	 */
	public void setLastUpdateUserid(Long lastUpdateUserid) {
		this.lastUpdateUserid = lastUpdateUserid;
	}

	public String getPkgflag() {
		return pkgflag;
	}

	public void setPkgflag(String pkgflag) {
		this.pkgflag = pkgflag;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public TechCourse getCourse() {
		return course;
	}

	public void setCourse(TechCourse course) {
		this.course = course;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public BusinessType getBusinessType() {
		return businessType;
	}

	public void setBusinessType(BusinessType businessType) {
		this.businessType = businessType;
	}

	public Package getPackages() {
		return packages;
	}

	public void setPackages(Package packages) {
		this.packages = packages;
	}
}