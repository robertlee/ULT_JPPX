/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.business.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.uletian.ultcrm.business.entity.Employee;
import com.uletian.ultcrm.business.entity.Order;
import com.uletian.ultcrm.business.entity.Store;
import com.uletian.ultcrm.business.repo.EmployeeRepository;
import com.uletian.ultcrm.business.repo.OrderRepository;
import com.uletian.ultcrm.business.value.OrderProcessStatus;
import com.uletian.ultcrm.business.value.WorkOrderInfoGift;
import com.uletian.ultcrm.business.value.WorkOrderInfoGiftList;
import com.uletian.ultcrm.business.value.WorkOrderInfoItem;
import com.uletian.ultcrm.business.value.WorkOrderInfoItemList;
import com.uletian.ultcrm.business.value.WorkOrderStatusInfo;
import com.uletian.ultcrm.business.value.WorkOrderStatusInfoList;
import com.thoughtworks.xstream.XStream;
import reactor.util.CollectionUtils;

/**
 * 
 * @author robertxie,  @modifier liuhua
 * 2015年9月22日
 */
@Component
public class OrderService {
	
	private static Logger logger = Logger.getLogger(OrderService.class);
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	private static Map<String,StatusInfoDataHandler> HANDLER_MAP = new HashMap<String,StatusInfoDataHandler>();

	@PostConstruct
	public void initMap() {
		HANDLER_MAP.put(WorkOrderStatusInfo.STATUS_ENTER, new EnterStoreHandler());
		HANDLER_MAP.put(WorkOrderStatusInfo.STATUS_PRINTBILL, new PrintBillHandler());
		HANDLER_MAP.put(WorkOrderStatusInfo.STATUS_PLANTWORKING, new PlantWorkingHandler(employeeRepository));
		HANDLER_MAP.put(WorkOrderStatusInfo.STATUS_QUALITYCHECK, new QualityCheckHandler());
		HANDLER_MAP.put(WorkOrderStatusInfo.STATUS_CUSTOMERPAY, new CustomerPayHandler());
	}

	public WorkOrderStatusInfoList getWorkOrderStatuInfoList(Order order) {
		
		//return  new WorkOrderStatuInfoList();
		String orderStatusMsg = order.getStatusRelationInfo();
		
		WorkOrderStatusInfoList statusInfo = new WorkOrderStatusInfoList();
		
		if(orderStatusMsg == null || orderStatusMsg.trim().equals(""))
		{
		    return statusInfo;	
		}
		
		XStream xStream = new XStream();
		xStream.alias("status", WorkOrderStatusInfo.class);
		xStream.alias("statusrelationinfo", WorkOrderStatusInfoList.class);
		xStream.addImplicitCollection(WorkOrderStatusInfoList.class, "statusList");
		//xStream.alias("status", WorkOrderStatuInfo.class);
		
		
		statusInfo = (WorkOrderStatusInfoList)xStream.fromXML(orderStatusMsg);
		return statusInfo;
		
	}
	
	
	/**
	 * 获取界面上需要的工单进程总信息
	 * @param orderId
	 * @return
	 */
	public OrderProcessStatus getStatusInfo(Long orderId) {
		
		Order order = orderRepository.findOne(orderId);
		
		WorkOrderStatusInfoList infoList = getWorkOrderStatuInfoList(order);	
		OrderProcessStatus result = new OrderProcessStatus();
		result.setOrderId(orderId);
		result.setName(order.getDescription());
		result.setPlanFinishedtime(order.getCrmPlanFinishedtime());
		
		
		
		result.setTechModelName(order.getTech().getTechModel().getName());
		result.setTechlevelno(order.getTech().getTechlevelno());
		
		
		result.setTotalAmount(order.getCrmTotalAmount());
		result.setDiscountTotalAmount(order.getCrmDiscountTotalAmount());
		result.setTotalCard(order.getCrmTotalCard());
		result.setTotalCredit(order.getCrmTotalCredit());
		
		// 获取工单列表
		List<WorkOrderStatusInfo> infoDataList = infoList.getStatusList();
		// 顺序排序
		//Collections.sort(infoDataList);		
		List<WorkOrderStatusInfo> workInfoList = new ArrayList<WorkOrderStatusInfo>();//存放车间状态
		
		int currentStatus = this.getWorkOrderCurrentStatus(infoDataList);		
		//StatusInfoDataHandler dbHandler = null;
		for (Iterator<WorkOrderStatusInfo> iter = infoDataList.iterator(); iter.hasNext();) {			
			WorkOrderStatusInfo statusInfo = (WorkOrderStatusInfo)iter.next();
			
			//dbHandler = HANDLER_MAP.get(statusInfo.getStatu());
			if(statusInfo.getStatu().equals(WorkOrderStatusInfo.STATUS_ENTER))
			{
				//result.setEnterStoreNode((OrderProcessStatus.NodeStatus)dbHandler.arrangeData(statusInfo));
				result.setEnterStoreNode(this.enterStoreHandler(currentStatus,statusInfo));
				
			} else if(statusInfo.getStatu().equals(WorkOrderStatusInfo.STATUS_PRINTBILL)) {
				
				result.setPrintBillNode(this.printBillHandler(order,currentStatus,statusInfo));
			} else if(statusInfo.getStatu().equals(WorkOrderStatusInfo.STATUS_PLANTWORKING))
			{
				workInfoList.add(statusInfo);
				
			}else if(statusInfo.getStatu().equals(WorkOrderStatusInfo.STATUS_QUALITYCHECK))
			{
				result.setQualityCheckNode(this.qualityCheckHandler(currentStatus,statusInfo));
			}else if(statusInfo.getStatu().equals(WorkOrderStatusInfo.STATUS_CUSTOMERPAY))
			{
			    result.setCustomerPayNode(this.customerPayHandler(currentStatus,statusInfo));
			}
		}
		
		if(workInfoList.size() > 0)
		{
			//result.setPlantWorkingNodes((ArrayList<OrderProcessStatus.NodeStatus>)dbHandler.arrangeData(workInfoList));
            result.setPlantWorkingNodes(this.plantWorkingHandler(currentStatus,workInfoList));
		}
		
		// 根据状态定位到数据行
		//Map<Integer,List<WorkOrderStatusInfo>> dataMap = new HashMap<Integer,List<WorkOrderStatusInfo>>();
		//indexDataLine(infoDataList,dataMap);
		
		//for (Integer key : dataMap.keySet()) {
		//	OrderProcessStatus.NodeStatus node = handleNodeData(key,dataMap.get(key));
		//	result.addNodeStatus(node);
		//}
		return result;  //order process status
	}
	

	/**
	 * 环车检查前台数据处理
	 * @param currentStatus
	 * @param statusInfo
	 */
    private OrderProcessStatus.NodeStatus enterStoreHandler(int currentStatus,WorkOrderStatusInfo statusInfo)
    {
    	OrderProcessStatus.NodeStatus node = new OrderProcessStatus.NodeStatus();
    	
    	
    	node.setCurrentStatus(currentStatus);
    	node.setStatus(statusInfo.getStatusInt());
    	
    	node.setName("环车检查已经完成");	

    	node.setStartTime(statusInfo.getErdat() + statusInfo.getErzet());;
	    node.setSaName(statusInfo.getSname());
    	node.setPosition(statusInfo.getPlans());
    	return node;
    	
    }
    
    private OrderProcessStatus.NodeStatus printBillHandler(Order order,Integer currentStatus,WorkOrderStatusInfo statusInfo)
    {
    	OrderProcessStatus.NodeStatus node = new OrderProcessStatus.NodeStatus();
    	
    	node.setCurrentStatus(currentStatus);
    	node.setStatus(statusInfo.getStatusInt());
    	
    	if(node.getHappenStatus() == -1)
    	{
    		node.setName("方案已经确认");	
    	}else if(node.getHappenStatus() == 0)
    	{
    		node.setName("方案正在确认");
    	}else if(node.getHappenStatus() == 1)
    	{
    		node.setName("方案已经确认");
    	}
    	
    	node.setStartTime(statusInfo.getErdat() + statusInfo.getErzet());;
	    node.setSaName(statusInfo.getSname());
    	node.setPosition(statusInfo.getPlans());
    	//构造出单和礼券信息
    	node.setData(this.getPrintBillInfo(order));
    	
    	
    	
    	return node;
    }
    /**
     * 处理工单对象
     * @param currentStatus
     * @param statusInfo
     * @return
     */
    private List<OrderProcessStatus.NodeStatus> plantWorkingHandler(Integer currentStatus,List<WorkOrderStatusInfo> statusInfo)
    {
        ArrayList<OrderProcessStatus.NodeStatus> nodeList = new  ArrayList<OrderProcessStatus.NodeStatus>();
    	if(statusInfo ==null || statusInfo.size() <= 0)
    	{
    		return null;	
    	}
    	
    	Iterator<WorkOrderStatusInfo> statusItem = statusInfo.iterator();
    	while (statusItem.hasNext())
    	{
    		
    		WorkOrderStatusInfo item = statusItem.next();
    		
    		OrderProcessStatus.NodeStatus node = new OrderProcessStatus.NodeStatus();
    		 
    		 node.setCurrentStatus(currentStatus);
    	     node.setStatus(item.getStatusInt());
    	    	
    	    if(node.getHappenStatus() == -1)
    	    	{
    	    		node.setName("车间作业已经完成");	
    	    	}else if(node.getHappenStatus() == 0)
    	    	{
    	    		node.setName("钣金");
    	    	}else if(node.getHappenStatus() == 1)
    	    	{
    	    		node.setName("待车间作业");
    	    	}
    	    	
    	    node.setStartTime(item.getErdat() + item.getErzet());;
    	    node.setSaName(item.getSname());
    	    node.setPosition(item.getPlans());
    	    node.setBezei(item.getBezei());
    	    node.setDescr(item.getDescr());
    	    nodeList.add(node);
    	}
    	
    	return nodeList;
    	
    }
    private OrderProcessStatus.NodeStatus qualityCheckHandler(Integer currentStatus,WorkOrderStatusInfo statusInfo)
    {
    	OrderProcessStatus.NodeStatus node = new OrderProcessStatus.NodeStatus();

    	
    	node.setStartTime(statusInfo.getErdat() + statusInfo.getErzet());;
	    node.setSaName(statusInfo.getSname());
    	node.setPosition(statusInfo.getPlans());
    	return node;
    }
    private OrderProcessStatus.NodeStatus customerPayHandler(Integer currentStatus,WorkOrderStatusInfo statusInfo)
    {
    	OrderProcessStatus.NodeStatus node = new OrderProcessStatus.NodeStatus();
    	node.setStartTime(statusInfo.getErdat() + statusInfo.getErzet());;
	    node.setSaName(statusInfo.getSname());
    	node.setPosition(statusInfo.getPlans());
    	return node;
    
    }
	
    /**
	 * 获取工单相关信息及优惠券信息
	 * @param orderId
	 * @return
	 */
	public Map<String, Object> getPrintBillInfo(Order order) {
		
		//return  new WorkOrderStatuInfoList();
		String orderMsg = order.getOrderInfo();
		
        int startIndexItem = orderMsg.indexOf("<items>");
        int endIndexItem = orderMsg.indexOf("</items>")+8;
 
        int startIndexGift = orderMsg.indexOf("<gifts>");
        int endIndexGift = orderMsg.indexOf("</gifts>")+8;
        
		String itemInfo = orderMsg.substring(startIndexItem,endIndexItem);
		
		String giftInfo = orderMsg.substring(startIndexGift,endIndexGift);
			
		XStream xStream = new XStream();
		
		xStream.alias("item", WorkOrderInfoItem.class);
		xStream.alias("items", WorkOrderInfoItemList.class);
		xStream.addImplicitCollection(WorkOrderInfoItemList.class, "items");
		
		WorkOrderInfoItemList itemList = (WorkOrderInfoItemList)xStream.fromXML(itemInfo);
		
		XStream xStreamGift = new XStream();
		xStreamGift.alias("gift", WorkOrderInfoGift.class);
		xStreamGift.alias("gifts", WorkOrderInfoGiftList.class);
		xStreamGift.addImplicitCollection(WorkOrderInfoGiftList.class, "gifts");
		
		WorkOrderInfoGiftList giftList = (WorkOrderInfoGiftList)xStreamGift.fromXML(giftInfo);
	
		//System.out.println("item list size = " + itemList.getItems().size() + " ; gift list size= " +
		//giftList.getGifts().size());
		
		//xStream.alias("status", WorkOrderStatuInfo.class);
		//WorkOrderStatusInfoList statusInfo = (WorkOrderStatusInfoList)xStream.fromXML(null);
		HashMap<String, Object> result = new HashMap<String, Object>();
		if(itemList != null)
		{
			result.put("billitems", itemList.getItems());
		}
		
		if(giftList != null)
		{
			result.put("billgifts", giftList.getGifts());
		}
		
		return result;
	}
	
    /**
     * 根据所有工单记录，获取当前最新的状态
     * @param statusList
     * @return
     */
	private Integer getWorkOrderCurrentStatus(List<WorkOrderStatusInfo> statusList)
	{
		int currNum = 0;
		
		for (Iterator<WorkOrderStatusInfo> iter = statusList.iterator(); iter.hasNext();) {
		  
			WorkOrderStatusInfo status = (WorkOrderStatusInfo)iter.next();
			
			int i = status.getStatusInt();
			
			if(currNum < i)
			{
				currNum = i;
			}
		}
		return currNum;
		
		/**
		String currentStatus = "";
		switch(currNum)
		
		{
		    case 2:
		    	
		    	currentStatus = WorkOrderStatusInfo.STATUS_ENTER;
			    break;
		    case 3:
		    	currentStatus = WorkOrderStatusInfo.STATUS_PRINTBILL;
			    break;
		    case 7:
		    	currentStatus = WorkOrderStatusInfo.STATUS_PLANTWORKING;
			    break;
		    case 8:
		    	currentStatus = WorkOrderStatusInfo.STATUS_QUALITYCHECK;
			    break;
		    case 10:
		    	currentStatus = WorkOrderStatusInfo.STATUS_CUSTOMERPAY;
			    break;
		    default:
		    	currentStatus = WorkOrderStatusInfo.STATUS_ENTER;
			    
		}
		return currentStatus;
		**/
		
	}
	private OrderProcessStatus.NodeStatus handleNodeData(Integer nodeIndex, List<WorkOrderStatusInfo> data) {
		Integer status = 1;
		OrderProcessStatus.NodeStatus node = new OrderProcessStatus.NodeStatus();
		if (!org.apache.commons.collections.CollectionUtils.isEmpty(data)) {
			status = 3;
			String startTimeStr = "";
			for (WorkOrderStatusInfo record : data) {
				if (StringUtils.isEmpty(startTimeStr)) {
					if (StringUtils.isNotBlank(record.getErdat()) && StringUtils.isNotBlank(record.getErzet()) ) {
						String date = record.getErdat();
						String time = record.getErzet();
						startTimeStr = date+""+time;
						node.setStartTime(startTimeStr);
					}
				}
			}
		}
		//node.setStatus(status);
		StatusInfoDataHandler handler = HANDLER_MAP.get(nodeIndex);
		if (handler != null) {
			Object arrangedData = handler.arrangeData(data);
			node.setData(arrangedData);
		}
		return node;
	}
	
	/**
	 * 对处理中的车间数据行进行处理，主要是通过人的姓名获取人的头像
	 * @param nodeIndex
	 * @param data
	 */
	private void handleWorkingData(Integer nodeIndex, List<WorkOrderStatusInfo> data) {
		if (org.apache.commons.collections.CollectionUtils.isNotEmpty(data)) {
			for (WorkOrderStatusInfo record : data) {
//				Store store = new Store();
//				store.setId(1l);
				Employee employee = employeeRepository.findByStoreAndName(null, record.getOrgeh());
				if (employee != null) {
					record.setAvatar(employee.getAvatar());
				}
			}
		}
	} 
	
	private void indexDataLine(List<WorkOrderStatusInfo> infoDataList,Map<Integer,List<WorkOrderStatusInfo>> dataMap) {
    	List<String>  reverseList  = new ArrayList<String>();
    	Collections.copy(reverseList, WorkOrderStatusInfo.SPECIAL_STATUS_LIST);
		
    	if (dataMap.size() == 5) {
    		return;
    	}
    	
    	String currentStatus = reverseList.get(dataMap.size());
    	
    	boolean has = false;
    	
    	List<WorkOrderStatusInfo> currentNodeList = new ArrayList<WorkOrderStatusInfo>();
    	
    	for (WorkOrderStatusInfo statusInfo :infoDataList) {
    		if (statusInfo.getStatu().equals("currentStatus")) {
    			has = true;
    			currentNodeList.add(statusInfo);
    		}
    		else if (!statusInfo.getStatu().equals("currentStatus") && has) {
    			break;
    		}
    		
    	}
    	dataMap.put(dataMap.size()+1, currentNodeList);
	}
	
	
	public static class CustomerPayHandler implements StatusInfoDataHandler{
		
		@Override
		public Object arrangeData(List<WorkOrderStatusInfo> statusInfoData) {
			if (!CollectionUtils.isEmpty(statusInfoData)) {
				return statusInfoData.get(0);
			}
			else {
				return null;
			}
		}

		@Override
		public Object arrangeData(WorkOrderStatusInfo statusInfoData) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}

	
	public interface StatusInfoDataHandler{
		Object arrangeData(List<WorkOrderStatusInfo> statusInfoData);
		Object arrangeData(WorkOrderStatusInfo statusInfoData);
	}
	
	public static class EnterStoreHandler implements StatusInfoDataHandler{
		
		@Override
		public Object arrangeData(List<WorkOrderStatusInfo> statusInfoData) {
			return null;
			
		}

		@Override
		public Object arrangeData(WorkOrderStatusInfo statusInfoData) {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
	public static class PrintBillHandler implements StatusInfoDataHandler{
		
		@Override
		public Object arrangeData(List<WorkOrderStatusInfo> statusInfoData) {
			return null;
			
		}

		@Override
		public Object arrangeData(WorkOrderStatusInfo statusInfoData) {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
	public static class PlantWorkingHandler implements StatusInfoDataHandler{
		
		private EmployeeRepository employeeRepository;
		
		public PlantWorkingHandler(EmployeeRepository employeeRepository) {
			this.employeeRepository = employeeRepository;
		}
		
		@Override
		public Object arrangeData(List<WorkOrderStatusInfo> statusInfoData) {
			if (org.apache.commons.collections.CollectionUtils.isNotEmpty(statusInfoData)) {
				for (WorkOrderStatusInfo record : statusInfoData) {
//					Store store = new Store();
//					store.setId(1l);
					Employee employee = employeeRepository.findByStoreAndName(null, record.getOrgeh());
					if (employee != null) {
						record.setAvatar(employee.getAvatar());
					}
				}
			}
			return statusInfoData;
		}

		@Override
		public Object arrangeData(WorkOrderStatusInfo statusInfoData) {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
	public static class QualityCheckHandler implements StatusInfoDataHandler{
		
		@Override
		public Object arrangeData(List<WorkOrderStatusInfo> statusInfoData) {
			if (!CollectionUtils.isEmpty(statusInfoData)) {
				return statusInfoData.get(0);
			}
			else {
				return null;
			}
		}

		@Override
		public Object arrangeData(WorkOrderStatusInfo statusInfoData) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
}
