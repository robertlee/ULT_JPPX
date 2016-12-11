package com.uletian.ultcrm.business.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.uletian.ultcrm.business.entity.BusinessType;
import com.uletian.ultcrm.business.entity.Schedule;
import com.uletian.ultcrm.business.repo.BusinessTypeRepository;
import com.uletian.ultcrm.business.entity.Store;
import com.uletian.ultcrm.business.repo.StoreRepository;
import com.uletian.ultcrm.business.entity.ClassType;
import com.uletian.ultcrm.business.repo.ClassTypeRepository;

@RestController
public class BusinessTypeController {
	private static Logger logger = Logger.getLogger(BusinessTypeController.class);
	
	@Autowired
	private BusinessTypeRepository businessTypeRepository;
	@Autowired
	private StoreRepository storeRepository;
	@Autowired
	private ClassTypeRepository classTypeRepository;
  
    @RequestMapping(value = "/getBusinessList/{batchName}/{addressId}", method = RequestMethod.GET)  
    public Map<String,Object> getBusinessList(@PathVariable("batchName")String batchName,@PathVariable("addressId")Long addressId){
    	Map<String,Object> map = new HashMap<String,Object>();
    	List<BusinessType> businessinglist = new ArrayList<BusinessType>();
    	List<BusinessType> businesslist = new ArrayList<BusinessType>();
    	try {
    		logger.info("开始获取课程信息，查询参数为【batchName:" + batchName + ",addressId:" + addressId + "】");
			//查询正在上课课程
			businessinglist = businessTypeRepository.getBusinessList(batchName,addressId,"0");
			logger.info("获取正在上课课程数量为：" + businessinglist.size());
			//查询即将培训课程
			businesslist = businessTypeRepository.getBusinessList(batchName,addressId,"1");
			logger.info("获取即将培训课程数量为：" + businesslist.size());
			if(businessinglist != null && !businessinglist.isEmpty()){
				map.put("businessinglist", businessinglist);
			}
			if(businesslist != null && !businesslist.isEmpty()){
				map.put("businesslist", businesslist);
			}
		} catch (Exception e) {
			logger.error("获取课程信息失败");
			logger.error(e.getMessage());
			return new HashMap<String,Object>();
		}
    	return map;
    }
    
    //查询开课批次select
    @RequestMapping(value = "/getBatchList", method = RequestMethod.GET)  
    public List<ClassType> getBatchList(){
    	List<ClassType> batchList = new ArrayList<ClassType>();
    	try {
    		logger.info("开始所有的课程批次");
			//查询所有的课程批次
    		batchList = classTypeRepository.getClassBatchList();
			logger.info("获取所有的课程批次select数量为：" + batchList.size());
		} catch (Exception e) {
			logger.error("获取课程批次select失败");
			logger.error(e.getMessage());
			return new ArrayList<ClassType>();
		}
    	return batchList;
    }
    
    //根据课程批次编号查询该批次下所有的门店地址select
    @RequestMapping(value = "/getAddressList/{batchName}", method = RequestMethod.GET)  
    public List<ClassType> getAddressList(@PathVariable("batchName")String batchName){
    	List<ClassType> addressList = new ArrayList<ClassType>();
    	try {
    		logger.info("开始根据课程批次编号查询该批次下所有的门店地址，查询条件为【batchName：" + batchName + "】");
			//查询所有的课程批次
    		addressList = classTypeRepository.getAddressList(batchName);
			logger.info("获取所有的门店select数量为：" + addressList.size());
		} catch (Exception e) {
			logger.error("获取门店select失败");
			logger.error(e.getMessage());
			return new ArrayList<ClassType>();
		}
    	return addressList;
    }
    
    //根据门店编号查询该门店下所有的课程select
    @RequestMapping(value = "/getClassList", method = RequestMethod.GET)  
    public List<ClassType> getClassList(@PathVariable("batchId")Long batchId,@PathVariable("addressId")Long addressId){
    	List<ClassType> classList = new ArrayList<ClassType>();
    	try {
    		logger.info("开始根据门店编号查询该门店下所有的课程，查询条件为【batchId：" + batchId + "，addressId：" + addressId + "】");
			//查询所有的课程批次
    		classList = classTypeRepository.getClassList(batchId,addressId);
			logger.info("获取所有的课程select数量为：" + classList.size());
		} catch (Exception e) {
			logger.error("获取课程select失败");
			logger.error(e.getMessage());
			return new ArrayList<ClassType>();
		}
    	return classList;
    }
    
    @RequestMapping(value = "/getStoreListToHome", method = RequestMethod.GET)
    public List<Store> getStoreListToHome(){ 
    	List<Store> list = new ArrayList<Store>();
    	try {
    		logger.debug("开始获取门店数据");
    		list = storeRepository.getStoreList();
    		logger.debug("获取门店数据条数" + list.size());
		} catch (Exception e) {
			logger.error("获取门店信息失败");
			logger.error(e.getMessage());
		}
    	return list;
    }
}
