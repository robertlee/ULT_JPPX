package com.uletian.ultcrm.business.entity;

import java.io.Serializable;

import javax.persistence.*;

import org.apache.http.client.utils.DateUtils;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the tech database table.
 * 
 */
@Entity
@NamedQuery(name="Tech.findAll", query="SELECT c FROM Tech c")
public class Tech implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	//车架号
	private String techname;

	@Column(name="create_time")
	private Timestamp createTime;

	@Column(name="create_user_id")
	private Long createUserId;

	@Column(name="last_update_time")
	private Timestamp lastUpdateTime;

	@Column(name="last_update_userid")
	private Long lastUpdateUserid;
	
	// 车牌号
	@Column(name="techlevelno", length = 128)
	private String techlevelno;
	
	// vin码
	@Column(name="techerno", length = 128)
	private String techerno;
	
	// 会员级别
	@Column(name="member_level", length = 65)
	private String memberLevel;
	
	// 当前里程数
	@Column(name="coursetime", length = 128)
	private String coursetime;
	
	// 保险过期日期
	@Column(name="train_expire_date")
	private Date trainExpireDate;
	
	// 投保公司
	@Column(name="train_company", length = 128)
	private String trainCompany;
	
	// 
	@Column(name="course_code", length = 128)
	private String courseCode;
	
	// 车身颜色
	@Column(name="color", length = 128)
	private String color;
	
	// 总积分
	@Column(name="total_score")
	private Integer totalScore;
	
	@Column(name="crm_tech_id")
	private String crmTechId;
	
	// 人工主类型
	@Column(name="code", length = 128)
	private String code;
	
	// 注册日期
	@Column(name="register_date")
	private Date registerDate;
	
	// 购买日期
	private Date buyDate;
	
	// 下次保养里程
	private String nextMaintCoursetime;
	
	// 下次保养时间
	private Date nextMaintDate;
	
	// 最近到店时间
	private Date lastConsumeDate;
	
	@Column(name="course_license", length = 128)
	private String courseLicense;
	
	// 年审到期日期
	@Column(name="check_expire_date")
	private Date checkExpireDate;
	
	//bi-directional many-to-one association to TechCourse
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name="courseid")
	private TechCourse techCourse;

	//bi-directional many-to-one association to Customer
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name="customerid")
	private Customer customer;

	//bi-directional many-to-one association to TechModel
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name="modelid")
	private TechModel techModel;

	//bi-directional many-to-one association to TechSery
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name="seriesid")
	private TechSery techSery;

	//bi-directional many-to-one association to Order
	@OneToMany(mappedBy="tech")
	private List<Order> orders;
	
	@OneToMany(mappedBy="tech")
	private List<Score> scores;
	
	// 下面这一排是非持久化字段
	@Transient
	private Long courseId;
	@Transient
	private String courseName;
	@Transient
	private Long seryId;
	@Transient
	private String seryName;
	@Transient
	private Long modelId;
	@Transient
	private String modelName;
	
	@Transient
	private Long customerId;
	
	@Transient
	private String courseImg;
	
	@Transient
	private String year;
	
	@Transient
	private String month;
	
	/**
	 * @return the buyDate
	 */
	public Date getBuyDate() {
		return buyDate;
	}

	/**
	 * @param buyDate the buyDate to set
	 */
	public void setBuyDate(Date buyDate) {
		this.buyDate = buyDate;
	}

	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * @return the month
	 */
	public String getMonth() {
		return month;
	}

	/**
	 * @param month the month to set
	 */
	public void setMonth(String month) {
		this.month = month;
	}

	/**
	 * @return the nextMaintCoursetime
	 */
	public String getNextMaintCoursetime() {
		return nextMaintCoursetime;
	}

	/**
	 * @param nextMaintCoursetime the nextMaintCoursetime to set
	 */
	public void setNextMaintCoursetime(String nextMaintCoursetime) {
		this.nextMaintCoursetime = nextMaintCoursetime;
	}

	/**
	 * @return the nextMaintDate
	 */
	public Date getNextMaintDate() {
		return nextMaintDate;
	}

	/**
	 * @param nextMaintDate the nextMaintDate to set
	 */
	public void setNextMaintDate(Date nextMaintDate) {
		this.nextMaintDate = nextMaintDate;
	}

	/**
	 * @return the lastConsumeDate
	 */
	public Date getLastConsumeDate() {
		return lastConsumeDate;
	}

	/**
	 * @param lastConsumeDate the lastConsumeDate to set
	 */
	public void setLastConsumeDate(Date lastConsumeDate) {
		this.lastConsumeDate = lastConsumeDate;
	}

	/**
	 * @return the scores
	 */
	public List<Score> getScores() {
		return scores;
	}

	/**
	 * @param scores the scores to set
	 */
	public void setScores(List<Score> scores) {
		this.scores = scores;
	}

	/**
	 * @return the totalScore
	 */
	public Integer getTotalScore() {
		return totalScore;
	}

	/**
	 * @param totalScore the totalScore to set
	 */
	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}

	/**
	 * @return the crmTechId
	 */
	public String getCrmTechId() {
		return crmTechId;
	}

	/**
	 * @param crmTechId the crmTechId to set
	 */
	public void setCrmTechId(String crmTechId) {
		this.crmTechId = crmTechId;
	}

	/**
	 * @return the courseImg
	 */
	public String getCourseImg() {
		return courseImg;
	}

	/**
	 * @param courseImg the courseImg to set
	 */
	public void setCourseImg(String courseImg) {
		this.courseImg = courseImg;
	}

	/**
	 * @return the customerId
	 */
	public Long getCustomerId() {
		return customerId;
	}

	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public void fillOtherFields() {
		if (this.getTechCourse() != null) {
			this.courseId = this.getTechCourse().getId();
			this.courseName = this.getTechCourse().getName();
			this.courseImg = this.getTechCourse().getLogo();
		}
		if (this.getTechSery() != null) {
			this.seryId = this.getTechSery().getId();
			this.seryName = this.getTechSery().getName();
		}
		if (this.getTechModel() != null) {
			this.modelId = this.getTechModel().getId();
			this.modelName = this.getTechModel().getName();
		}
		if (this.getBuyDate() != null) {
			this.year = DateUtils.formatDate(this.getBuyDate(), "yyyy");
			this.month = DateUtils.formatDate(this.getBuyDate(), "MM");
		}

	}
	
	/**
	 * @return the courseId
	 */
	public Long getCourseId() {
		return courseId;
	}

	/**
	 * @param courseId the courseId to set
	 */
	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	/**
	 * @return the courseName
	 */
	public String getCourseName() {
		return courseName;
	}

	/**
	 * @param courseName the courseName to set
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	/**
	 * @return the seryId
	 */
	public Long getSeryId() {
		return seryId;
	}

	/**
	 * @param seryId the seryId to set
	 */
	public void setSeryId(Long seryId) {
		this.seryId = seryId;
	}

	/**
	 * @return the seryName
	 */
	public String getSeryName() {
		return seryName;
	}

	/**
	 * @param seryName the seryName to set
	 */
	public void setSeryName(String seryName) {
		this.seryName = seryName;
	}

	/**
	 * @return the modelId
	 */
	public Long getModelId() {
		return modelId;
	}

	/**
	 * @param modelId the modelId to set
	 */
	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}

	/**
	 * @return the modelName
	 */
	public String getModelName() {
		return modelName;
	}

	/**
	 * @param modelName the modelName to set
	 */
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public Tech() {
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

	public String getTechname() {
		return this.techname;
	}

	public void setTechname(String techname) {
		this.techname = techname;
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
	
	


	public String getTechlevelno() {
		return this.techlevelno;
	}

	public void setTechlevelno(String techlevelno) {
		this.techlevelno = techlevelno;
	}

	public String getTecherno() {
		return this.techerno;
	}

	public void setTecherno(String techerno) {
		this.techerno = techerno;
	}

	public TechCourse getTechCourse() {
		return this.techCourse;
	}

	public void setTechCourse(TechCourse techCourse) {
		this.techCourse = techCourse;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public TechModel getTechModel() {
		return this.techModel;
	}

	public void setTechModel(TechModel techModel) {
		this.techModel = techModel;
	}

	public TechSery getTechSery() {
		return this.techSery;
	}

	public void setTechSery(TechSery techSery) {
		this.techSery = techSery;
	}

	public List<Order> getOrders() {
		return this.orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Order addOrder(Order order) {
		getOrders().add(order);
		order.setTech(this);

		return order;
	}

	public Order removeOrder(Order order) {
		getOrders().remove(order);
		order.setTech(null);

		return order;
	}

	public String getCoursetime() {
		return coursetime;
	}

	public void setCoursetime(String coursetime) {
		this.coursetime = coursetime;
	}

	public Date getTrainExpireDate() {
		return trainExpireDate;
	}

	public void setTrainExpireDate(Date trainExpireDate) {
		this.trainExpireDate = trainExpireDate;
	}

	public String getTrainCompany() {
		return trainCompany;
	}

	public void setTrainCompany(String trainCompany) {
		this.trainCompany = trainCompany;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public String getCourseLicense() {
		return courseLicense;
	}

	public void setCourseLicense(String courseLicense) {
		this.courseLicense = courseLicense;
	}

	public Date getCheckExpireDate() {
		return checkExpireDate;
	}

	public void setCheckExpireDate(Date checkExpireDate) {
		this.checkExpireDate = checkExpireDate;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMemberLevel() {
		return memberLevel;
	}

	public void setMemberLevel(String memberLevel) {
		this.memberLevel = memberLevel;
	}
}