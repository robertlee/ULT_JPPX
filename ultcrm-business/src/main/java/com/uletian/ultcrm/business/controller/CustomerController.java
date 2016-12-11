/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.business.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.uletian.ultcrm.business.entity.Tech;
import com.uletian.ultcrm.business.entity.Customer;
import com.uletian.ultcrm.business.entity.CustomerCode;
import com.uletian.ultcrm.business.entity.Location;
import com.uletian.ultcrm.business.entity.Order;
import com.uletian.ultcrm.business.entity.Store;
import com.uletian.ultcrm.business.repo.TechRepository;
import com.uletian.ultcrm.business.repo.CardRepository;
import com.uletian.ultcrm.business.repo.CouponRepository;
import com.uletian.ultcrm.business.repo.CustomerCodeRepository;
import com.uletian.ultcrm.business.repo.CustomerRepository;
import com.uletian.ultcrm.business.repo.LocationRepository;
import com.uletian.ultcrm.business.repo.OrderRepository;
import com.uletian.ultcrm.business.repo.StoreRepository;
import com.uletian.ultcrm.business.service.CustomerInfoSyncService;
import com.uletian.ultcrm.business.service.CustomerService;
import com.uletian.ultcrm.business.service.SmsQueueService;
import com.uletian.ultcrm.business.value.Result;

/**
 * 
 * @author robertxie
 * 2015年9月8日
 */
@RestController
public class CustomerController {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class); 
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private TechRepository techRepository;

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CustomerCodeRepository customerCodeRepository;
	
	@Autowired
	private SmsQueueService smsQueueService;
	
	@Autowired
	private CustomerInfoSyncService customerInfoSyncService;
	
	@Autowired
	private LocationRepository locationRepository;
	
	@Autowired
	private CardRepository  cardRepository;
	
	@Autowired
	private CouponRepository couponRepository;
	
	@Autowired
	private StoreRepository storeRepository;
	
	@RequestMapping(value = "/customer/openid/{openid}", method = RequestMethod.GET)
	public Customer findCustomer(@PathVariable("openid") String openid) {
		Customer customer = null;
		customer = customerRepository.findByOpenid(openid);
		List<Tech> techs = techRepository.findTechByCustomer(customer.getId());
		for (Tech tech : techs) {
			customer.addTech(tech);
		}
		List<Order> orders = orderRepository.findOrderByCustomer(customer.getId());
		for (Order order : orders) {
			customer.addOrder(order);
		}
		return customer;
	}
	
	@RequestMapping(value="/getCustomerByOpenId/{openId}",method=RequestMethod.GET)
	public Customer getCustomerByOpenId(@PathVariable("openId")String openId){
		return customerService.getCustomerByOpenId(openId);
	}
	
	@RequestMapping(value="/getCustomer/{customerId}",method=RequestMethod.GET)
	public Customer getCustomer(@PathVariable("customerId")Long customerId,HttpServletRequest request){
		return customerRepository.findOne(customerId);
	}
	
	@RequestMapping(value="/modifyUser",method=RequestMethod.POST)
	public void modifyUser(@RequestBody Customer customer){
		Customer oldCustomer = customerRepository.findOne(customer.getId());
		oldCustomer.setSex(customer.getSex());
		oldCustomer.setName(customer.getName());
		oldCustomer.setPhone(customer.getPhone());
		
		customerRepository.save(oldCustomer);
		
	}
	
	@RequestMapping(value="/customer/{customerId}/navigateTo/{storeId}",method=RequestMethod.GET)
	public Map<String,Object> getCustomerLocation(@PathVariable("customerId")Long customerId, @PathVariable("storeId")Long storeId){
		HashMap<String,Object> result = new HashMap<String,Object>(0);
		
		//查询门店
//		Store store = storeRepository.findOne(storeId);
//		if(store == null){
//			result.put("error", "STORE_NOT_EXISTS");
//			return result;
//		}
		
		//查询客户
		Customer customer = customerRepository.findOne(customerId);
		if (customer == null) {
			result.put("error", "CUSTOMER_NOT_EXISTS");
			return result;
		} 
		
		//查询门店位置
//		Location storeLocation = store.getLocation();
//		if(storeLocation == null){
//			result.put("error", "STORE_LOCATION_NOT_EXISTS");
//			return result;
//		}
		Map<String,Object> storeLocationResult = new HashMap<String,Object>();
//		storeLocationResult.put("latitude", storeLocation.getLatitude());
//		storeLocationResult.put("longitude", storeLocation.getLongitude());
//		storeLocationResult.put("precision", storeLocation.getPrecision());
		result.put("store", storeLocationResult);
		
		//查询客户位置
		Location customerLocation = locationRepository.findByCustomerid(customerId);
		if (customerLocation == null) {
			result.put("error", "CUSTOMER_LOCATION_NOT_EXISTS");
			return result;
		}
		Map<String,Object> customerLocationResult = new HashMap<String,Object>();
		customerLocationResult.put("latitude", customerLocation.getLatitude());
		customerLocationResult.put("longitude", customerLocation.getLongitude());
		customerLocationResult.put("precision", customerLocation.getPrecision());
		result.put("customer", customerLocationResult);
		
		return result;
	}
	// 个人中心主页，获取用户的统计信息
	@RequestMapping(value="/getCountInfo/{customerId}",method=RequestMethod.GET)
	public Map<String,Object> getCountInfo(@PathVariable("customerId")Long customerId){
		Map<String,Object> result = new HashMap<String,Object>();
		
		Customer customer = customerRepository.findOne(customerId);
		// 根据状态分组查询订单列表
		Long newOrderSize  = orderRepository.countByCustomerAndStatus(customer, 1);
		Long workingOrderSize  = orderRepository.countByCustomerAndStatus(customer, 2);
		//List<Integer> statusList = new ArrayList<Integer>();
		//不要已取消，只要已完成
		List<Integer> statusList = new ArrayList<Integer>();
		statusList.add(3);
		statusList.add(4);
		statusList.add(5);
		Long completeOrderSize  = orderRepository.countByCustomerAndStatusIn(customer, statusList);
		// 待评价的条数
		Long commentSize  = orderRepository.countByCustomerAndStatusAndOrderCommentsIsNull(customer, 3);
		
		result.put("newCount", String.valueOf(newOrderSize));
		result.put("workingCount", String.valueOf(workingOrderSize));
		result.put("completeCount", String.valueOf(completeOrderSize));
		result.put("commentCount", String.valueOf(commentSize));
		
		result.put("customer", customer);
		return result;
	}
	
	@RequestMapping(value="/customer/update",method=RequestMethod.POST)
	public Result updateCustomer(@RequestBody Map<String,String> customerMap){
		Result result = new Result();
		String openid = customerMap.get("openid");
		String name = customerMap.get("name");
		String phone = customerMap.get("phone");
		String unitid = customerMap.get("unionid");
		Customer customer = customerRepository.findByOpenid(openid);
		if (customer == null) {
			result.setCode(-1);
			result.setResult(false);
			result.setMsg("用户不存在");
			return result;
		}
		if (!StringUtils.isEmpty(name)) {
			customer.setName(name);
		}
		if (!StringUtils.isEmpty(phone)) {
			customer.setPhone(customerMap.get("phone"));
		}
		if (!StringUtils.isEmpty(unitid)) {
			customer.setPhone(customerMap.get("unitid"));
		}
		customerRepository.save(customer);
		result.setCode(0);
		result.setMsg("用户更新成功");
		result.setResult(false);
		return result;
	}
	
	@RequestMapping(value = "/customer/obtaincode/phone/{phone}/customer/{customerid}", method = RequestMethod.GET)
	public Result sendSms(
			@PathVariable("phone") String phone,
			@PathVariable("customerid") Long customerid, 
			HttpServletRequest request, 
			HttpServletResponse response) {
		Result result = new Result();
		/**  校验手机号码重复  **/
		Customer customer = customerRepository.findByPhone(phone);
		if (customer != null) {
			result.setCode(2);
			result.setResult(false);
			result.setMsg("该手机号码已经存在");
			return result;
		}
		
		/**  生成验证码  **/
		Calendar c = Calendar.getInstance();
		CustomerCode code = new CustomerCode();

		c.add(Calendar.HOUR_OF_DAY, 1);
		code.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
		code.setInvalidTime(new Timestamp(c.getTimeInMillis()));
		code.setCode(getCodeStr());
		code.setPhone(phone);
		code.setCustomer(customerRepository.findOne(customerid));
		code.setTypeid(1L);

		customerCodeRepository.save(code);

		logger.info("用户获取验证码"+ code.getCode());

		String content = "尊敬的用户，您的短信验证码是："+code.getCode()+"，如非本人操作请您忽略。";
		result = smsQueueService.sendMessage(phone, content, smsQueueService.getIpAddr(request), false);
		return result;
	}

	@RequestMapping(value = "/customer/checkcode", method = RequestMethod.POST)
	public Result checkCode(@RequestBody Map<String,String> parameterMap){
		Result result = new Result();
		String codeStr = parameterMap.get("codeStr");
		String customerid = parameterMap.get("customerid");
		String phone = parameterMap.get("phone");
		String imgCode = parameterMap.get("imgCode");
		
		Customer customer = customerRepository.findOne(Long.parseLong(customerid));
		List<CustomerCode> codes = customerCodeRepository.findByCustomer(customer);
		
		result.setMsg("输入的验证码不正确，请重新绑定。");
		
		if ((codes.size() > 0) && customer!= null) {
			for (CustomerCode code : codes) {
				if(phone.equals(code.getPhone())&&codeStr.equals(code.getCode())){
					if (imgCode != null) {
						CustomerCode cc = customerCodeRepository.findByImageCode(imgCode);
						if (cc == null) {
							result.setMsg("图形验证码不正确");
						} else {
							result.setResult(true);
							result.setCode(0);
							result.setMsg("手机绑定成功");
							customer.setPhone(phone);
							customerRepository.save(customer);
							customerCodeRepository.delete(codes);
							customerInfoSyncService.notifycationDataChange(customer);
						}
						break;
					} else {
						result.setResult(true);
						result.setCode(0);
						result.setMsg("手机绑定成功");
						customer.setPhone(phone);
						customerRepository.save(customer);
						customerCodeRepository.delete(codes);
						customerInfoSyncService.notifycationDataChange(customer);
						break;
					}
				} else {
					result.setMsg("验证码或者手机号码不正确");
				}
			}
		}else{
			result.setMsg("输入的参数不正确");
		}
		return result;
	}
	
	private String getCodeStr() {
		Random rand = new Random();
		int result = rand.nextInt(10000);
		String s = "" + result;
		if (result < 10) {
			s = "000" + result;
			return s;
		}
		if (result < 100) {
			s = "00" + result;
			return s;
		}
		if (result < 1000) {
			s = "0" + result;
			return s;
		}
		return s;
	}
	
	/**
	 * 汇总我的钱包信息
	 * @param customerId
	 * @return
	 */
	@RequestMapping(value="/getMyWalletInfo/{customerId}",method=RequestMethod.GET)
	public Map<String, Object> getMyWalletInfo(@PathVariable("customerId")Long customerId){
		
		HashMap<String,Object> resultMap = new HashMap<String,Object>();
				
		Customer customer = new Customer();
		
		customer.setId(customerId);
		
		//总积分
		List<Long> totalScore = techRepository.sumTotalScoreByCustomer(customer);
		if(totalScore != null && totalScore.size() >0)
		{
			if(totalScore.get(0) == null)
			{
				resultMap.put("totalScore", "0");
			}else{
				resultMap.put("totalScore", totalScore.get(0).toString());
			}
		}
		
		//卡券总数
		Long publishCardCount =  cardRepository.countByCustomerAndStatus(customer, "002");  //发布
		Long writeoffCardCount =  cardRepository.countByCustomerAndStatus(customer, "003");     //核销
		Long cancelCardCount =  cardRepository.countByCustomerAndStatus(customer, "005");  //过期
		
		if(publishCardCount == null)
		{
			publishCardCount = new Long(0);
		}
		if(writeoffCardCount == null)
		{
			writeoffCardCount = new Long(0);
		}
		if(cancelCardCount == null)
		{
			cancelCardCount = new Long(0);
		}
		
		
		
		Long publishCouponCount =  couponRepository.countByCustomerAndStatus(customer, "003");  //发布
		Long writeoffCouponCount =  couponRepository.countByCustomerAndStatus(customer, "004");        //核销
		Long cancelCouponCount =  couponRepository.countByCustomerAndStatus(customer, "006");  //过期
		
		
		
		if(publishCouponCount == null)
		{
			publishCouponCount = new Long(0);
		}
		if(writeoffCouponCount == null)
		{
			writeoffCouponCount = new Long(0);
		}
		if(cancelCouponCount == null)
		{
			cancelCouponCount = new Long(0);
		}
		
		resultMap.put("publishCardCount", publishCardCount);
		resultMap.put("writeoffCardCount", writeoffCardCount);
		resultMap.put("cancelCardCount", cancelCardCount);

		
		resultMap.put("publishCouponCount", publishCouponCount);
		resultMap.put("writeoffCouponCount", writeoffCouponCount);
		resultMap.put("cancelCouponCount", cancelCouponCount);
		
		
		
		resultMap.put("publishCount", publishCardCount+publishCouponCount);
		resultMap.put("writeoffCount", writeoffCardCount+writeoffCouponCount);
		resultMap.put("cancelCount", cancelCouponCount+cancelCardCount);
		
		resultMap.put("cardCouponCount", publishCardCount+publishCouponCount+ 
				writeoffCardCount+writeoffCouponCount+cancelCouponCount+cancelCardCount);
		
		return resultMap;
	}
	
}