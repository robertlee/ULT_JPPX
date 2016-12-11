package com.uletian.ultcrm.business.entity;
import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;


/**
 * The persistent class for the schedule database table.
 * 
 */
@Entity
@Table(name="schedule")
public class Schedule implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(name="class_name")
	private String className;

	@Column(name="create_date")
	private Timestamp createDate = null;

	@Column(name="create_user_id")
	private Long createUserId = null;

	@Column(name="last_update_date")
	private Timestamp lastUpdateDate = null;

	@Column(name="last_update_userid")
	private Long lastUpdateUserid = null;

	@Column(name="teacher_name")
	private String teacherName = null;
	
	@Column(name="class_room_id")
	private Long classRoomId = null;

	@Column(name="class_room_name")
	private String classRoomName = null;
	
	private BigDecimal price = null;

	@Temporal(TemporalType.DATE)
	@Column(name="start_class_date")
	private Date startClassDate;
	
	@Column(name="seat_type")
	private String seatType = null;//座位类型
	
	private String status;//课程状态0：即将授课   1：正在授课
	
	@Column(name="class_hour")
	private Long classHour;
	
	@Column(name="class_time_detail")
	private String classTimeDetail = null;
	
	@Column(name="business_address")
	private String businessAddress;
	
	@Temporal(TemporalType.DATE)
	@Column(name="end_class_time")
	private Date endClassTime;
	
	@Temporal(TemporalType.DATE)
	@Column(name="start_class_time")
	private Date startClassTime;//开课时间
	@Column(name="store_id")
	private Long storeId;//门店编号
	
	@Column(name="start_batch")
	private String startBatch;
	
	//bi-directional many-to-one association to BusinessType
	@ManyToOne
	@JoinColumn(name="class_id")
	private BusinessType businessType;

	//bi-directional many-to-one association to Teacher
	@ManyToOne
	private Teacher teacher;

	@Transient
	private Long teachId;
	
	public Schedule() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClassName() {
		return this.className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Long getClassRoomId() {
		return this.classRoomId;
	}

	public void setClassRoomId(Long classRoomId) {
		this.classRoomId = classRoomId;
	}

	public String getClassRoomName() {
		return this.classRoomName;
	}

	public void setClassRoomName(String classRoomName) {
		this.classRoomName = classRoomName;
	}

	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public Long getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public Timestamp getLastUpdateDate() {
		return this.lastUpdateDate;
	}

	public void setLastUpdateDate(Timestamp lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Long getLastUpdateUserid() {
		return this.lastUpdateUserid;
	}

	public void setLastUpdateUserid(Long lastUpdateUserid) {
		this.lastUpdateUserid = lastUpdateUserid;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Date getStartClassDate() {
		return this.startClassDate;
	}

	public void setStartClassDate(Date startClassDate) {
		this.startClassDate = startClassDate;
	}

	public String getTeacherName() {
		return this.teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	
	public String getSeatType() {
		return this.seatType;
	}

	public void setSeatType(String seatType) {
		this.seatType = seatType;
	}

	public BusinessType getBusinessType() {
		return this.businessType;
	}

	public void setBusinessType(BusinessType businessType) {
		this.businessType = businessType;
	}

	public Teacher getTeacher() {
		return this.teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Long getTeachId() {
		return this.teachId;
	}

	public void setTeachId(Long teachId) {
		this.teachId = teachId;
	}
	
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public Long getClassHour() {
		return this.classHour;
	}

	public void setClassHour(Long classHour) {
		this.classHour = classHour;
	}
	
	public String getClassTimeDetail() {
		return this.classTimeDetail;
	}

	public void setClassTimeDetail(String classTimeDetail) {
		this.classTimeDetail = classTimeDetail;
	}
	
	public String getbusinessAddress() {
		return this.businessAddress;
	}
	public void setbusinessAddress(String businessAddress) {
		this.businessAddress = businessAddress;
	}
	
	public Date getEndClassTime() {
		return this.endClassTime;
	}

	public void setEndClassTime(Date endClassTime) {
		this.endClassTime = endClassTime;
	}
	
	public Date getStartClassTime() {
		return this.startClassTime;
	}

	public void setStartClassTime(Date startClassTime) {
		this.startClassTime = startClassTime;
	}
	public Long getStoreId() {
		return this.storeId;
	}
	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}
	
	public String getStartBatch() {
		return this.startBatch;
	}
	public void setStartBatch(String startBatch) {
		this.startBatch = startBatch;
	}
}