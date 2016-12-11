package com.uletian.ultcrm.business.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import reactor.util.StringUtils;


/**
 * The persistent class for the orders database table.
 * 
 */
@Entity
@Table(name="orders")
@NamedQuery(name="Order.findAll", query="SELECT o FROM Order o where id = 1")
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final Integer STATUS_NEW=1;

	public static final Integer STATUS_WORKING=2;
	public static final Integer STATUS_COMPLETE=3;
	public static final Integer STATUS_CANCEL=4;
	public static final Integer STATUS_EXPIRE=5;
	
	public static final Map<Integer,String> STATUS_DESC_MAP = new HashMap<Integer,String>();
	
	static{
		STATUS_DESC_MAP.put(STATUS_NEW, "等待");
		STATUS_DESC_MAP.put(STATUS_WORKING, "处理中");
		STATUS_DESC_MAP.put(STATUS_COMPLETE, "完成");
	}

	public enum status {
		create(1), working(2), complete(3), cancel(4), expire(5);
		private int value = 0;

		private status(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}
	
	public enum WorkState{
		ZVM002,// 环车检查
		ZVM003,// 方案确认
		ZVM007,// 作业开始
		ZVM008,// 作业完成
		ZVM009, // 结算
		ZVM010 // 付款
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="orderid")
	private String orderId;

	@Column(name="create_time")
	private Date createTime;

	@Column(name="create_user_id")
	private Long createUserId;

	private BigDecimal discountprice;

	@Column(name="description")
	private String description;

	@Column(name="erp_no")
	private String erpNo;

	@Column(name="last_update_time")
	private Date lastUpdateTime;

	@Column(name="last_update_userid")
	private Long lastUpdateUserid;

	@Column(name="price")
	private BigDecimal price;
	
	@Column(name="total_price")
	private BigDecimal totalPrice;

	@Column(name="crm_created_errormeg")
	private String crmCreatedErrormeg;

	@Column(name="crm_created_flag")
	private String crmCreatedFlag;
	
	@Column(name="crm_work_orderid")
	private String crmWorkOrderId;

//	@Column(name="customerid")
//	private Long customerid;
	
	@Column(name="customername")
	private String customername;
	
	@Column(name="crm_customerid")
	private String crmCustomerId;
	
	@Column(name="classid")
	private Long classid;
	
	@Column(name="classname")
	private String classname;
	
	@Column(name="seatid")
	private Long seatid;
	
	@Column(name="seatname")
	private String seatname;
	
	@Column(name="room_id")
	private Long roomId;
	
	@Column(name="room_name")
	private String roomName;

	@Column(name="teacherid")
	private Long teacherId;
	
	@Column(name="teachername")
	private String teacherName;

	@Column(name="class_hour")
	private Long classHour;	
	
	@Column(name="start_time")
	private Date startTime;
	@Column(name="end_time")
	private Date endTime;
	@Column(name="class_time_detail")
	private String classTimeDetail;
	
//	@Column(name="scheduleid")
//	private Long scheduleid;
	
	@Column(name="status_relation_info")
	private String statusRelationInfo;
	
	@Column(name="crm_work_orderstatus")
	private String crmOrderstatus;

	@Column(name="crm_totalamount")
	private String crmTotalAmount;
	
	@Column(name="crm_discounttotalamount")
	private String crmDiscountTotalAmount;

	@Column(name="crm_total_credit")
	private String crmTotalCredit;
	
	@Column(name="crm_sa_name")
	private String crmSaName;
	
	private Integer status;

	private Long typeid;
	
	@Column(name="child_name")
	private String childName = null;

	//bi-directional many-to-one association to Appointment
	@OneToOne(mappedBy="order")
	private Appointment appointment;

	//bi-directional many-to-one association to OrderItem
	@OneToMany(mappedBy="order")
	private List<OrderItem> orderItems;

	//bi-directional many-to-one association to Tech
	@ManyToOne
	@JoinColumn(name="techid")
	private Tech tech;

	//bi-directional many-to-one association to Customer
	@ManyToOne
	@JoinColumn(name="customerid")
	private Customer customer;
	
	//bi-directional many-to-one association to OrderComment
	@OneToMany(mappedBy="order")
	private List<OrderComment> orderComments;

	@ManyToOne
	@JoinColumn(name="scheduleid")
	private Schedule schedule;
	
	@Transient
	private String statusDesc;

	@Transient
	private String techlevelno;
	
	@Transient
	private String appointTimeStr;
	
	@Transient
	private Long busiTypeId;
	
	@Transient
	private String crmPlanFinishedtimeStr;
	
	@Transient
	private String imgUrl;
	
	@Transient
	private Boolean hasComment = false;
	
	@Transient
	private Long appointmentId = null;
	@Transient
	private String startClassTime = null;
	@Transient
	private String roomAddress = null;
	
	public Long getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(Long appointmentId) {
		this.appointmentId = appointmentId;
	}

	/**
	 * @return the hasComment
	 */
	public Boolean getHasComment() {
		return hasComment;
	}

	/**
	 * @param hasComment the hasComment to set
	 */
	public void setHasComment(Boolean hasComment) {
		this.hasComment = hasComment;
	}

	/**
	 * @return the imgUrl
	 */
	public String getImgUrl() {
		return imgUrl;
	}

	/**
	 * @param imgUrl the imgUrl to set
	 */
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	/**
	 * @return the busiTypeId
	 */
	public Long getBusiTypeId() {
		return busiTypeId;
	}

	/**
	 * @param busiTypeId the busiTypeId to set
	 */
	public void setBusiTypeId(Long busiTypeId) {
		this.busiTypeId = busiTypeId;
	}

	/**
	 * @return the crmPlanFinishedtimeStr
	 */
	public String getCrmPlanFinishedtimeStr() {
		return crmPlanFinishedtimeStr;
	}

	/**
	 * @param crmPlanFinishedtimeStr the crmPlanFinishedtimeStr to set
	 */
	public void setCrmPlanFinishedtimeStr(String crmPlanFinishedtimeStr) {
		this.crmPlanFinishedtimeStr = crmPlanFinishedtimeStr;
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
	 * @return the appointTimeStr
	 */
	public String getAppointTimeStr() {
		return appointTimeStr;
	}

	/**
	 * @param appointTimeStr the appointTimeStr to set
	 */
	public void setAppointTimeStr(String appointTimeStr) {
		this.appointTimeStr = appointTimeStr;
	}

	public String getCrmTotalCredit() {
		return crmTotalCredit;
	}

	public void setCrmTotalCredit(String crmTotalCredit) {
		this.crmTotalCredit = crmTotalCredit;
	}

	public String getCrmTotalCard() {
		return crmTotalCard;
	}

	public void setCrmTotalCard(String crmTotalCard) {
		this.crmTotalCard = crmTotalCard;
	}


	@Column(name="crm_total_card")
	private String crmTotalCard;
	
	@Column(name="crm_planfinishedtime")
	private String crmPlanFinishedtime;
	
	@Column(name="crm_orderinfo")
	private String orderInfo;
	
	
	public String getCrmOrderstatus() {
		return crmOrderstatus;
	}

	public void setCrmOrderstatus(String crmOrderstatus) {
		this.crmOrderstatus = crmOrderstatus;
	}


	public String getCrmTotalAmount() {
		return crmTotalAmount;
	}


	public void setCrmTotalAmount(String crmTotalAmount) {
		this.crmTotalAmount = crmTotalAmount;
	}


	public String getCrmDiscountTotalAmount() {
		return crmDiscountTotalAmount;
	}


	public void setCrmDiscountTotalAmount(String crmDiscountTotalAmount) {
		this.crmDiscountTotalAmount = crmDiscountTotalAmount;
	}


	public String getCrmPlanFinishedtime() {
		return crmPlanFinishedtime;
	}


	public void setCrmPlanFinishedtime(String crmPlanFinishedtime) {
		this.crmPlanFinishedtime = crmPlanFinishedtime;
	}


	public String getOrderInfo() {
		return orderInfo;
	}


	public void setOrderInfo(String orderInfo) {
		this.orderInfo = orderInfo;
	}

	public Order() {
	}

	
	/**
	 * @return the orderComments
	 */
	public List<OrderComment> getOrderComments() {
		return orderComments;
	}

	/**
	 * @param orderComments the orderComments to set
	 */
	public void setOrderComments(List<OrderComment> orderComments) {
		this.orderComments = orderComments;
	}


	public String getStatusRelationInfo() {
		return statusRelationInfo;
	}




	public void setStatusRelationInfo(String statusRelationInfo) {
		this.statusRelationInfo = statusRelationInfo;
	}

	/**
	 * @return the statusDesc
	 */
	public String getStatusDesc() {
		if (StringUtils.isEmpty(statusDesc)) {
			statusDesc = STATUS_DESC_MAP.get(status);
		}
		if (status == 3 && (orderComments == null || orderComments.size() == 0) ) {
			statusDesc ="未评价";
		}
		if (status == 3 && (orderComments != null && orderComments.size() > 0) ) {
			statusDesc ="已评价";
		}
		return statusDesc;
	}

	

	/**
	 * @param statusDesc the statusDesc to set
	 */
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
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



	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}



	public BigDecimal getDiscountprice() {
		return this.discountprice;
	}

	public void setDiscountprice(BigDecimal discountprice) {
		this.discountprice = discountprice;
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



	public String getErpNo() {
		return this.erpNo;
	}

	public void setErpNo(String erpNo) {
		this.erpNo = erpNo;
	}

	public Date getLastUpdateTime() {
		return this.lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}



	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	public BigDecimal getTotalPrice() {
		return this.totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getCrmCreatedErrormeg() {
		return this.crmCreatedErrormeg;
	}

	public void setCrmCreatedErrormeg(String crmCreatedErrormeg) {
		this.crmCreatedErrormeg = crmCreatedErrormeg;
	}

	public String getCrmCreatedFlag() {
		return this.crmCreatedFlag;
	}

	public void setCrmCreatedFlag(String crmCreatedFlag) {
		this.crmCreatedFlag = crmCreatedFlag;
	}

	public String getCrmCustomerId() {
		return crmCustomerId;
	}

	public void setCrmCustomerId(String crmCustomerId) {
		this.crmCustomerId = crmCustomerId;
	}
	
	public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}
	
//	public Long getCustomerid() {
//		return customerid;
//	}
//
//	public void setCustomerid(Long customerid) {
//		this.customerid = customerid;
//	}
	
	public Long getClassid() {
		return classid;
	}

	public void setClassid(Long classid) {
		this.classid = classid;
	}
	
	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}
	
	public Long getSeatid() {
		return seatid;
	}

	public void setSeatid(Long seatid) {
		this.seatid = seatid;
	}
	
	public String getSeatname() {
		return seatname;
	}

	public void setSeatname(String seatname) {
		this.seatname = seatname;
	}
	
	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}
	
	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public Long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getClassTimeDetail() {
		return classTimeDetail;
	}

	public void setClassTimeDetail(String classTimeDetail) {
		this.classTimeDetail = classTimeDetail;
	}
	
	public Long getClassHour() {
		return classHour;
	}

	public void setClassHour(Long classHour) {
		this.classHour = classHour;
	}	
//	public Long getScheduleid() {
//		return scheduleid;
//	}
//
//	public void setScheduleid(Long scheduleid) {
//		this.scheduleid = scheduleid;
//	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}



	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
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
	 * @return the appointment
	 */
	public Appointment getAppointment() {
		return appointment;
	}

	/**
	 * @param appointment the appointment to set
	 */
	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}


	public List<OrderItem> getOrderItems() {
		return this.orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public OrderItem addOrderItem(OrderItem orderItem) {
		getOrderItems().add(orderItem);
		orderItem.setOrder(this);

		return orderItem;
	}

	public OrderItem removeOrderItem(OrderItem orderItem) {
		getOrderItems().remove(orderItem);
		orderItem.setOrder(null);

		return orderItem;
	}

	public Tech getTech() {
		return this.tech;
	}
	public void setTech(Tech tech) {
		this.tech = tech;
	}
	public Customer getCustomer() {
		return this.customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getCrmWorkOrderId() {
		return crmWorkOrderId;
	}
	public void setCrmWorkOrderId(String crmWorkOrderId) {
		this.crmWorkOrderId = crmWorkOrderId;
	}
	public String getCrmSaName() {
		return crmSaName;
	}
	public void setCrmSaName(String crmSaName) {
		this.crmSaName = crmSaName;
	}
	public Schedule getSchedule() {
		return this.schedule;
	}
	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}
	public String getStartClassTime() {
		return startClassTime;
	}
	public void setStartClassTime(String startClassTime) {
		this.startClassTime = startClassTime;
	}
	public String getRoomAddress() {
		return roomAddress;
	}
	public void setRoomAddress(String roomAddress) {
		this.roomAddress = roomAddress;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getChildName() {
		return this.childName;
	}
	public void setChildName(String childName) {
		this.childName = childName;
	}
}