package com.uletian.ultcrm.business.entity;
import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the store database table.
 * 
 */
@Entity
@Table(name="store")
public class Store implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private String code = null;

	@Column(name="create_time")
	private Timestamp createTime = null;

	@Column(name="create_user_id")
	private Long createUserId = null;

	@Column(name="full_address")
	private String fullAddress = null;

	@Column(name="last_update_time")
	private Timestamp lastUpdateTime = null;

	@Column(name="last_update_userid")
	private Long lastUpdateUserid = null;

	private String name = null;

	private String phone = null;

	//bi-directional many-to-one association to Appointment
//	@OneToMany(mappedBy="store")
//	private List<Appointment> appointments;

	//bi-directional many-to-one association to ClassRoom
	@OneToMany(mappedBy="store")
	private List<ClassRoom> classRooms;

	//bi-directional many-to-one association to Package
//	@OneToMany(mappedBy="store")
//	private List<Package> packages;

	//bi-directional many-to-one association to Location
//	@ManyToOne
//	@JoinColumn(name="locationid")
//	private Location location;

	//bi-directional many-to-one association to Address
//	@ManyToOne
//	@JoinColumn(name="addressid")
//	private Address address;

	//bi-directional many-to-one association to Company
//	@ManyToOne
//	@JoinColumn(name="companyid")
//	private Company company;

	//bi-directional many-to-one association to Area
//	@ManyToOne
//	@JoinColumn(name="areaid")
//	private Area area;
 
	public Store() {
	}

	public Long getId() {
		return this.id;
	}

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

	public Long getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public String getFullAddress() {
		return this.fullAddress;
	}

	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}

	public Timestamp getLastUpdateTime() {
		return this.lastUpdateTime;
	}

	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Long getLastUpdateUserid() {
		return this.lastUpdateUserid;
	}

	public void setLastUpdateUserid(Long lastUpdateUserid) {
		this.lastUpdateUserid = lastUpdateUserid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

//	public List<Appointment> getAppointments() {
//		return this.appointments;
//	}
//
//	public void setAppointments(List<Appointment> appointments) {
//		this.appointments = appointments;
//	}
 
	public List<ClassRoom> getClassRooms() {
		return this.classRooms;
	}

	public void setClassRooms(List<ClassRoom> classRooms) {
		this.classRooms = classRooms;
	}
 
//	public List<Package> getPackages() {
//		return this.packages;
//	}
//
//	public void setPackages(List<Package> packages) {
//		this.packages = packages;
//	}
// 
//
//	public Location getLocation() {
//		return this.location;
//	}
//
//	public void setLocation(Location location) {
//		this.location = location;
//	}
//
//	public Address getAddress() {
//		return this.address;
//	}
//
//	public void setAddress(Address address) {
//		this.address = address;
//	}
//
//	public Company getCompany() {
//		return this.company;
//	}
//
//	public void setCompany(Company company) {
//		this.company = company;
//	}
//
//	public Area getArea() {
//		return this.area;
//	}
//
//	public void setArea(Area area) {
//		this.area = area;
//	}
}