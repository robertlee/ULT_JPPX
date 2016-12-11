package com.uletian.ultcrm.business.service;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.annotation.PostConstruct;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.uletian.ultcrm.business.entity.SmsMessage;

@Component
public class SMSSender {
	
	private static final Logger logger = LoggerFactory.getLogger(SMSSender.class);

	private String enterpriseId;
	private String userid;
	private String password;
	private String baseUrl;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	
	private HttpClient httpClient;
	
	@PostConstruct
	public void initSMSSender() {
		baseUrl = "http://sms.api.ums86.com:8899/sms/Api/Send.do";
		enterpriseId = "224993111";
		userid = "admin1";
		password = "uletian888!";
		httpClient = new HttpClient();
	}

	public synchronized String send(SmsMessage msg) throws HttpException, IOException {
		PostMethod post = new PostMethod(baseUrl);
		post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "gbk");
		post.addParameter("SpCode", enterpriseId);
		post.addParameter("LoginName", userid);
		post.addParameter("Password", password);
		post.addParameters(msg.getParam());
		httpClient.executeMethod(post);
		logger.debug("服务器返回的状态：" + post.getStatusLine());

		String value = post.getResponseBodyAsString();
		post.releaseConnection();
		logger.info("短信发送成功");
		return value;
	}
}
