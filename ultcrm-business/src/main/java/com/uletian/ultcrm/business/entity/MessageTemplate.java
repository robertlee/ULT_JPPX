package com.uletian.ultcrm.business.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.uletian.ultcrm.business.service.WeixinConfig;
import com.uletian.ultcrm.common.util.StringUtils;

@Entity
@Table(name="message_template")
@NamedQuery(name="MessageTemplate.findAll", query="SELECT mt FROM MessageTemplate mt")
public class MessageTemplate implements Serializable{

	private static final long serialVersionUID = -6936626665032316794L;
	
	public enum scope {
		snsapi_base, snsapi_userinfo
	}
	
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
	
	@Column(name="tmpid", length=55)
	private String tmpid;
	
	@Column(name="name", length=25)
	private String name;
	
	@Column(name="description", length=255)
	private String description;
	
	@Column(name="code", length=255)
	private String code;
	
	@Column(name="state", length=25)
	private String state;
	
	@Column(name="scope", length=25)
	private String scope;
	
	@Column(name="url", length=255)
	private String url;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public Timestamp getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Long getLastUpdateUserid() {
		return lastUpdateUserid;
	}

	public void setLastUpdateUserid(Long lastUpdateUserid) {
		this.lastUpdateUserid = lastUpdateUserid;
	}

	public String getTmpid() {
		return tmpid;
	}

	public void setTmpid(String tmpid) {
		this.tmpid = tmpid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String makeUrl(WeixinConfig weixinConfig,String param) {
		String url = null;
		//try {
			//url = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
			//		"appid=" + weixinConfig.getAppId() +
			//		"&redirect_uri=" + URLEncoder.encode(weixinConfig.getHostPath() + getUrl(), "utf-8") +
			//		"&response_type=code" +
			//		"&scope=" + getScope() +
			//		"&state=" + (param==null?"orderlist":(getState() + (StringUtils.isNotBlank(param)?"_"+param:"")))+
			//		"#wechat_redirect";
			url = "https://open.weixin.qq.com/connect/oauth2/authorize?" +			  
				  "appid=" + weixinConfig.getAppId() +
				  "&redirect_uri="+"http%3a%2f%2fwww.91jpfw.cn%2f%23%2findex%2fmyindex"+
				  "&response_type=code&scope=snsapi_userinfo" +
				  "&state=my" +
				  "#wechat_redirect";
		//} catch (UnsupportedEncodingException e) {
			
		//}
		return url;
	}
}
