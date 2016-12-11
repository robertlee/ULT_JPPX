/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.business.service;

import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.uletian.ultcrm.business.entity.Customer;
import com.uletian.ultcrm.business.repo.TechRepository;
import com.uletian.ultcrm.business.repo.CustomerRepository;
import com.uletian.ultcrm.business.repo.OrderRepository;

/**
 * 
 * @author robertxie 2015年9月17日
 */
@Component
public class CustomerService {
	private static Logger logger = Logger.getLogger(CustomerService.class);
	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private TechRepository techRepository;

	@Autowired
	private OrderRepository orderRepository;

	/**
	 * 检查微信的更新时间, 以30天的时间做为期限
	 * 
	 * @param openId
	 * @return 如果判断需要重新抓取数据，那么返回null,否则返回当前的customer对象
	 */
	public Customer checkWeixinUpdateTime(String openId) {
		Customer customer = customerRepository.findByOpenid(openId);
		if (customer != null) {
			Date lastCheckTime = customer.getLastWeixinCheckTime();
			Date now = new Date();
			Calendar cl = Calendar.getInstance();
			// cl.set(new Date().getTime());
			cl.setTimeInMillis(now.getTime() - (1000 * 60 * 60 * 24 * 30));
			Date midDate = cl.getTime();
			try {

					
				if (midDate.after(lastCheckTime)) {
					// 超过时间期限
					//南昌项目暂时不判断用户时间；
					return customer;
				} else {
					return customer;
				}
			} catch (Exception e) {
				logger.warn("save customer last_weixin_check_time error", e);
				return customer;
				
			}
				
		}
		return customer;
	}

	public Customer getCustomerByOpenId(String openId) {
		Customer customer = customerRepository.findByOpenid(openId);
		return customer;
	}
	
	public Customer createCustomer(Customer customer) {
		try {
			// 整理用户头像的大小
			String headimg = customer.getHeadimgurl();
			int index = headimg.lastIndexOf("/");
			customer.setHeadimgurl(customer.getHeadimgurl().substring(0, index)+"/64");
			customerRepository.save(customer);
		} catch (Exception e) {
			logger.info("save customer error.", e);
			customer.setNickname("***");
			customerRepository.save(customer);
		}
		return customer;
	}

	public Customer unsubscribeCustomerByOpenId(String openId) {
		Customer customer = customerRepository.findByOpenid(openId);
		// 0 表示用户取消关注
		if (customer != null) {
			customer.setStatus(0);
			customerRepository.save(customer);
		}
		return customer;
	}
	
	public static void main(String[] args) {
		String headimg = "http://wx.qlogo.cn/mmopen/PiajxSqBRaELhnwBiaoIvkQQVE4Rk4eGhvmuywQGpnjZ71cBMVUricTKj8CJiasR7vwK2HZYicPzqoFqib4zlXYxoPBA/0";
		int index = headimg.lastIndexOf("/");
		System.out.println(headimg.substring(0,index));
	}
}
