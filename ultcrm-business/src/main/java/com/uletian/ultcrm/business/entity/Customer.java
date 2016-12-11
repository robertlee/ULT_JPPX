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
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;


/**
 * The persistent class for the customer database table.
 * 
 */
@Entity
@NamedQuery(name="Customer.findAll", query="SELECT c FROM Customer c")
public class Customer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(name="create_time")
	private Timestamp createTime;

	@Column(name="create_user_id")
	private Long createUserId;

	@Column(name="last_update_time")
	private Timestamp lastUpdateTime;

	@Column(name="last_update_userid")
	private Long lastUpdateUserid;
	
	// 客户真实的名字
	@Column(name="name")
	private String name;

	@Column(name="openid")
	private String openid;

	@Column(name="phone")
	private String phone;
	
	@Column(name="unionid")
	private String unionid;

	//bi-directional many-to-one association to Tech
	@OneToMany(mappedBy="customer")
	private List<Tech> techs;

	//bi-directional many-to-one association to CustomerCode
	@OneToMany(mappedBy="customer")
	private List<CustomerCode> customerCodes;

	//bi-directional many-to-one association to Order
	@OneToMany(mappedBy="customer")
	private List<Order> orders;
	
	//bi-directional many-to-one association to OrderComment
	@OneToMany(mappedBy="customer")
	private List<OrderComment> orderComments;
	
	
	//bi-directional many-to-one association to Tech
	//Javen添加的
	@OneToMany(mappedBy="customer")
	private List<Card> cards;
	
	//bi-directional many-to-one association to Tech
		//Javen添加的
	@OneToMany(mappedBy="customer")
	private List<Coupon> coupons;
	
		
	@Column(name="syncid", length = 65)
	private String syncid;
	
	//--------------下面这些字段都是微信的资料-------------------------------------------------
	// 客户在微信的昵称
	@Column(name="nickname", length = 65)
	private String nickname;
	
	//性别，值为1时是男性，值为2时是女性，值为0时是未知
	@Column(name="sex", length = 6)
	private String sex;
	
	//个人资料填写的省份
	@Column(name="province", length = 65)
	private String province;
	
	//个人资料填写的城市
	@Column(name="city", length = 65)
	private String city;
	
	//个人资料填写的国家,国家，如中国为CN
	@Column(name="country", length = 65)
	private String country;
	
	@Column(name="address", length = 255)
	private String address;
	
	@Column(name="postcode", length = 65)
	private String postcode;
	
	//用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
	@Column(name="headimgurl", length = 255)
	private String headimgurl;
	
	// 用户状态， 0， 取消关注， 1 已经关注， 默认值为1，已经在数据库中设置默认值
	@Column(name="status")
	private Integer status;
	
	// 上一次在微信系统检查的时间，每抓取一次微信的用户详细资料就记录一次时间
	@Column(name="last_weixin_check_time")
	private Date lastWeixinCheckTime;
	
	@Column(name="crm_customer_id")
	private String crmCustomerId;
	
	// 从crm同步过来的用户的级别，1， 表示银， 2， 表示金， 3 表示钻石 ， null 表示没有级别
	private String rank = "3";
	
	@OneToMany(mappedBy="customer")
	private List<Children> childs;

	/**
	 * @return the rank
	 */
	public String getRank() {
		return rank;
	}

	/**
	 * @param rank the rank to set
	 */
	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getCrmCustomerId() {
		return crmCustomerId;
	}

	public void setCrmCustomerId(String crmCustomerId) {
		this.crmCustomerId = crmCustomerId;
	}

	public Customer() {
	}

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
	 * @return the lastWeixinCheckTime
	 */
	public Date getLastWeixinCheckTime() {
		return lastWeixinCheckTime;
	}

	/**
	 * @param lastWeixinCheckTime the lastWeixinCheckTime to set
	 */
	public void setLastWeixinCheckTime(Date lastWeixinCheckTime) {
		this.lastWeixinCheckTime = lastWeixinCheckTime;
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



	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOpenid() {
		return this.openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@OrderBy("createTime DESC")
	public List<Tech> getTechs() {
		return this.techs;
	}

	public void setTechs(List<Tech> techs) {
		this.techs = techs;
	}

	public Tech addTech(Tech tech) {
		getTechs().add(tech);
		tech.setCustomer(this);

		return tech;
	}

	public Tech removeTech(Tech tech) {
		getTechs().remove(tech);
		tech.setCustomer(null);

		return tech;
	}

	public List<CustomerCode> getCustomerCodes() {
		return this.customerCodes;
	}

	public void setCustomerCodes(List<CustomerCode> customerCodes) {
		this.customerCodes = customerCodes;
	}

	public CustomerCode addCustomerCode(CustomerCode customerCode) {
		getCustomerCodes().add(customerCode);
		customerCode.setCustomer(this);

		return customerCode;
	}

	public CustomerCode removeCustomerCode(CustomerCode customerCode) {
		getCustomerCodes().remove(customerCode);
		customerCode.setCustomer(null);

		return customerCode;
	}

	public List<Order> getOrders() {
		return this.orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Order addOrder(Order order) {
		getOrders().add(order);
		order.setCustomer(this);

		return order;
	}

	public Order removeOrder(Order order) {
		getOrders().remove(order);
		order.setCustomer(null);

		return order;
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



	public String getUnionid() {
		return unionid;
	}



	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}



	/**
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}



	/**
	 * @param nickname the nickname to set
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}



	/**
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}



	/**
	 * @param sex the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}



	/**
	 * @return the province
	 */
	public String getProvince() {
		return province;
	}



	/**
	 * @param province the province to set
	 */
	public void setProvince(String province) {
		this.province = province;
	}



	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}



	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}



	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}



	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}



	/**
	 * 如果头像为640 * 640,替换为 64 * 64 大小地址
	 * @return the headimgurl
	 */
	public String getHeadimgurl() {
		if(headimgurl!=null && headimgurl.trim().endsWith("0")){
			String substr = headimgurl.substring(0, headimgurl.length() - 1);
			return substr + "64";
		}
		
		return headimgurl;
	}



	/**
	 * @param headimgurl the headimgurl to set
	 */
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	

	public String getSyncid() {
		return syncid;
	}

	public void setSyncid(String syncid) {
		this.syncid = syncid;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
    
	
	//Javen添加的方法
	public List<Card> getCards() {
		return this.cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}
	
	public List<Coupon> getCoupons() {
		return this.coupons;
	}

	public void setCoupons(List<Coupon> coupons) {
		this.coupons = coupons;
	}
	
	public List<Children> getChilds() {
		return this.childs;
	}

	public void setChilds(List<Children> childs) {
		this.childs = childs;
	}
}