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
import javax.persistence.Transient;

import com.uletian.ultcrm.business.value.CardMessage.CardContent;


/**
 * 次卡对象
 * 
 */
@Entity
@NamedQuery(name="Card.findAll", query="SELECT c FROM Card c")
public class Card implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public static final String STATUS_PUBLISH="002";// 生成发布
	public static final String STATUS_WRITEOFF="003";// 核销
	public static final String STATUS_CANCEL="004";// 取消冻结
	public static final String STATUS_EXPIRE="005";// 过期

	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	/**
	 * 长度是12位，以C打头
	 */
	@Column(name="card_no")
	private String cardNo;

	@Column(name="create_time")
	private Timestamp createTime;

	@Column(name="create_user_id")
	private Long createUserId;

	private String description;

	@Column(name="end_date")
	private Date endDate;

	@Column(name="last_update_time")
	private Timestamp lastUpdateTime;

	@Column(name="last_update_userid")
	private Long lastUpdateUserid;

	private String name;

	@Column(name="start_date")
	private Date startDate;
	
	private Integer period;
	
	@Column(name="period_Type")
	private String periodType;
	
	private String status;
	/**
	 * yyyyMMdd
	 */
	private Date publishTime;

	@Column(name="total_count")
	private Integer totalCount = 0;
	
	/**
	 * 卡类型， X 表示终身卡， A表示普通卡
	 */
	private String type;
	
	private Double amount;

	/**
	 * 已经使用的次数
	 */
	@Column(name="used_count")
	private Integer usedCount =0;

	//bi-directional many-to-one association to CardBatch
	@ManyToOne
	@JoinColumn(name="tech_id")
	private Tech tech;
	
	@ManyToOne
	@JoinColumn(name="batch_id")
	private CardBatch cardBatch;
	

	//bi-directional many-to-one association to Customer
	@ManyToOne
	@JoinColumn(name="customer_id")
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(name="event_id")
	private Event event;

	//bi-directional many-to-one association to CardConsume
	@OneToMany(mappedBy="card")
	private List<CardConsume> cardConsumes;
	
	@Transient
	private String sourceSys;
	
	@Transient
	private CardContent cardMsgConetnt; 

	@Transient
	private String batchId;

	public String getBatchId() {
		if(cardBatch != null)
		{
			return cardBatch.getId().toString();
		}
		return null;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public Card() {
	}
	
	/**
	 * @return the sourceSys
	 */
	public String getSourceSys() {
		return sourceSys;
	}

	/**
	 * @return the event
	 */
	public Event getEvent() {
		return event;
	}

	/**
	 * @param event the event to set
	 */
	public void setEvent(Event event) {
		this.event = event;
	}

	/**
	 * @return the publishTime
	 */
	public Date getPublishTime() {
		return publishTime;
	}

	/**
	 * @param publishTime the publishTime to set
	 */
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	/**
	 * @param createUserId the createUserId to set
	 */
	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	/**
	 * @param lastUpdateUserid the lastUpdateUserid to set
	 */
	public void setLastUpdateUserid(Long lastUpdateUserid) {
		this.lastUpdateUserid = lastUpdateUserid;
	}

	/**
	 * @param totalCount the totalCount to set
	 */
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * @param sourceSys the sourceSys to set
	 */
	public void setSourceSys(String sourceSys) {
		this.sourceSys = sourceSys;
	}









	/**
	 * @return the cardMsgConetnt
	 */
	public CardContent getCardMsgConetnt() {
		return cardMsgConetnt;
	}

	/**
	 * @param cardMsgConetnt the cardMsgConetnt to set
	 */
	public void setCardMsgConetnt(CardContent cardMsgConetnt) {
		this.cardMsgConetnt = cardMsgConetnt;
	}

	/**
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}



	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}



	/**
	 * @return the cardBatch
	 */
	public CardBatch getCardBatch() {
		return cardBatch;
	}

	/**
	 * @param cardBatch the cardBatch to set
	 */
	public void setCardBatch(CardBatch cardBatch) {
		this.cardBatch = cardBatch;
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
	 * @return the usedCount
	 */
	public Integer getUsedCount() {
		return usedCount;
	}

	/**
	 * @param usedCount the usedCount to set
	 */
	public void setUsedCount(Integer usedCount) {
		this.usedCount = usedCount;
	}

	/**
	 * @return the tech
	 */
	public Tech getTech() {
		return tech;
	}


	/**
	 * @param tech the tech to set
	 */
	public void setTech(Tech tech) {
		this.tech = tech;
	}


	/**
	 * @return the cardConsumes
	 */
	public List<CardConsume> getCardConsumes() {
		return cardConsumes;
	}


	/**
	 * @param cardConsumes the cardConsumes to set
	 */
	public void setCardConsumes(List<CardConsume> cardConsumes) {
		this.cardConsumes = cardConsumes;
	}


	public String getCardNo() {
		return this.cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}





	/**
	 * @return the customer
	 */
	public Customer getCustomer() {
		return customer;
	}



	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}



	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}



	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
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



	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	/**
	 * @return the createUserId
	 */
	public Long getCreateUserId() {
		return createUserId;
	}

	/**
	 * @return the lastUpdateUserid
	 */
	public Long getLastUpdateUserid() {
		return lastUpdateUserid;
	}

	/**
	 * @return the totalCount
	 */
	public Integer getTotalCount() {
		return totalCount;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}




	/**
	 * @return the periodType
	 */
	public String getPeriodType() {
		return periodType;
	}

	/**
	 * @param periodType the periodType to set
	 */
	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}

	/**
	 * @return the period
	 */
	public Integer getPeriod() {
		return period;
	}

	/**
	 * @param period the period to set
	 */
	public void setPeriod(Integer period) {
		this.period = period;
	}

}