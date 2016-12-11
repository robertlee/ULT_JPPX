package com.uletian.ultcrm.business.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the class_room database table.
 * 
 */
@Entity
@Table(name="class_room")
public class ClassRoom implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name="class_room_name")
	private String classRoomName;

	@Temporal(TemporalType.DATE)
	@Column(name="create_date")
	private Date createDate;

	@Column(name="create_user_id")
	private int createUserId;

	@Temporal(TemporalType.DATE)
	@Column(name="last_update_date")
	private Date lastUpdateDate;

	@Column(name="last_update_userid")
	private int lastUpdateUserid;

	private String status;

	//bi-directional many-to-one association to Store
	@ManyToOne
	@JoinColumn(name="storeid")
	private Store store;

	//bi-directional many-to-one association to Seat
//	@OneToMany(mappedBy="classRoom")
//	private List<Seat> seats;

	public ClassRoom() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClassRoomName() {
		return this.classRoomName;
	}

	public void setClassRoomName(String classRoomName) {
		this.classRoomName = classRoomName;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}

	public Date getLastUpdateDate() {
		return this.lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public int getLastUpdateUserid() {
		return this.lastUpdateUserid;
	}

	public void setLastUpdateUserid(int lastUpdateUserid) {
		this.lastUpdateUserid = lastUpdateUserid;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Store getStore() {
		return this.store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

//	public List<Seat> getSeats() {
//		return this.seats;
//	}
//
//	public void setSeats(List<Seat> seats) {
//		this.seats = seats;
//	}
}