package com.uletian.ultcrm.business.entity;

import java.io.Serializable;
import java.sql.Timestamp;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


/**
 * The persistent class for the time_segment database table.
 * 
 */
@Entity
@Table(name="time_segment")
@NamedQuery(name="TimeSegment.findAll", query="SELECT t FROM TimeSegment t")
public class TimeSegment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private Long capacity;

	private Long count;

	@Column(name="create_time")
	private Timestamp createTime;

	@Column(name="create_user_id")
	private Long createUserId;

	@Column(name="date_segment")
	@Temporal(TemporalType.DATE)
	private Date dateSegment;

	@Column(name="last_update_time")
	private Timestamp lastUpdateTime;

	@Column(name="last_update_userid")
	private Long lastUpdateUserid;

	@Column(name="time_segment")
	private Long timeSegment;

	//bi-directional many-to-one association to Appointment
	@OneToMany(mappedBy="timeSegment")
	private List<Appointment> appointments;

	//bi-directional many-to-one association to Store
	@ManyToOne
	@JoinColumn(name="storeid")
	private Store store;
	
	@Transient
	private Long storeId;

	@Column(name="busitypeid")
	private Long busiTypeId;
	
	
	public TimeSegment() {
	}



	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}



	/**
	 * @return the storeId
	 */
	public Long getStoreId() {
		return storeId;
	}

	/**
	 * @param busiTypeId the busiTypeId to set
	 */
	public void setBusiTypeId(Long busiTypeId) {
		this.busiTypeId = busiTypeId;
	}

	/**
	 * @return the busiTypeId
	 */
	public Long getBusiTypeId() {
		return busiTypeId;
	}

	/**
	 * @param storeId the storeId to set
	 */
	public void setStoreId(Long storeId) {
		this.storeId = storeId;
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





	/**
	 * @return the dateSegment
	 */
	public Date getDateSegment() {
		return dateSegment;
	}



	/**
	 * @param dateSegment the dateSegment to set
	 */
	public void setDateSegment(Date dateSegment) {
		this.dateSegment = dateSegment;
	}



	public Timestamp getLastUpdateTime() {
		return this.lastUpdateTime;
	}

	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}


	/**
	 * @return the capacity
	 */
	public Long getCapacity() {
		return capacity;
	}



	/**
	 * @param capacity the capacity to set
	 */
	public void setCapacity(Long capacity) {
		this.capacity = capacity;
	}



	/**
	 * @return the count
	 */
	public Long getCount() {
		return count;
	}



	/**
	 * @param count the count to set
	 */
	public void setCount(Long count) {
		this.count = count;
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
	 * @return the timeSegment
	 */
	public Long getTimeSegment() {
		return timeSegment;
	}



	/**
	 * @param timeSegment the timeSegment to set
	 */
	public void setTimeSegment(Long timeSegment) {
		this.timeSegment = timeSegment;
	}



	public List<Appointment> getAppointments() {
		return this.appointments;
	}

	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}

	public Appointment addAppointment(Appointment appointment) {
		getAppointments().add(appointment);
		appointment.setTimeSegment(this);

		return appointment;
	}

	public Appointment removeAppointment(Appointment appointment) {
		getAppointments().remove(appointment);
		appointment.setTimeSegment(null);

		return appointment;
	}

	public Store getStore() {
		return this.store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

}