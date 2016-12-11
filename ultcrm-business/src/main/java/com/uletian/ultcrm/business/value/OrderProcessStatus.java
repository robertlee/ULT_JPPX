/**
 * Copyright &copy; 2014 uletian All right reserved
 */
package com.uletian.ultcrm.business.value;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.uletian.ultcrm.business.service.OrderService;

/**
 * 表示订单工序处理状态
 * @author robertxie
 * 2015年9月22日
 */
public class OrderProcessStatus {
	
	// 订单名称或者desc字段
	private String name;
	//订单id
	private Long orderId;
	private String planFinishedtime=""; //预计时间
	private String techModelName = ""; //车型
	private String techlevelno = ""; //车牌
	private String totalAmount = ""; //
	private String discountTotalAmount = ""; //
	private String totalCredit = ""; //   优惠
	private String totalCard = ""; //	
	
	private NodeStatus enterStoreNode = null;
	private NodeStatus printBillNode = null;
	private NodeStatus plantWorkingNode = null;
	
	private List<NodeStatus> plantWorkingNodes = null; // 所有的工序节点
		
	private NodeStatus qualityCheckNode = null;
	private NodeStatus customerPayNode = null;
	
	private List<NodeStatus> nodeStatusList = new ArrayList<NodeStatus>();
	
	public void addNodeStatus(NodeStatus node) {
		this.nodeStatusList.add(node);
	}
	
	/**
	 * @return the nodeStatusList
	 */
	public List<NodeStatus> getNodeStatusList() {
		return nodeStatusList;
	}

	/**
	 * @param nodeStatusList the nodeStatusList to set
	 */
	public void setNodeStatusList(List<NodeStatus> nodeStatusList) {
		this.nodeStatusList = nodeStatusList;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the orderId
	 */
	public Long getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public NodeStatus getEnterStoreNode() {
		return enterStoreNode;
	}

	public void setEnterStoreNode(NodeStatus enterStoreNode) {
		this.enterStoreNode = enterStoreNode;
	}

	public NodeStatus getPrintBillNode() {
		return printBillNode;
	}

	public void setPrintBillNode(NodeStatus printBillNode) {
		this.printBillNode = printBillNode;
	}

	public NodeStatus getPlantWorkingNode() {
		return plantWorkingNode;
	}

	public void setPlantWorkingNode(NodeStatus plantWorkingNode) {
		this.plantWorkingNode = plantWorkingNode;
	}

	public List<NodeStatus> getPlantWorkingNodes() {
		return plantWorkingNodes;
	}

	public void setPlantWorkingNodes(List<NodeStatus> plantWorkingNodes) {
		this.plantWorkingNodes = plantWorkingNodes;
	}

	public NodeStatus getQualityCheckNode() {
		return qualityCheckNode;
	}

	public void setQualityCheckNode(NodeStatus qualityCheckNode) {
		this.qualityCheckNode = qualityCheckNode;
	}

	public NodeStatus getCustomerPayNode() {
		return customerPayNode;
	}

	public void setCustomerPayNode(NodeStatus customerPayNode) {
		this.customerPayNode = customerPayNode;
	}

	public String getPlanFinishedtime() {
		return planFinishedtime;
	}

	public void setPlanFinishedtime(String planFinishedtime) {
		this.planFinishedtime = planFinishedtime;
	}

	public String getTechModelName() {
		return techModelName;
	}

	public void setTechModelName(String techModelName) {
		this.techModelName = techModelName;
	}

	public String getTechlevelno() {
		return techlevelno;
	}

	public void setTechlevelno(String techlevelno) {
		this.techlevelno = techlevelno;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getDiscountTotalAmount() {
		return discountTotalAmount;
	}

	public void setDiscountTotalAmount(String discountTotalAmount) {
		this.discountTotalAmount = discountTotalAmount;
	}

	public String getTotalCredit() {
		return totalCredit;
	}

	public void setTotalCredit(String totalCredit) {
		this.totalCredit = totalCredit;
	}

	public String getTotalCard() {
		return totalCard;
	}

	public void setTotalCard(String totalCard) {
		this.totalCard = totalCard;
	}

	public static class NodeStatus {
		private static Logger logger = Logger.getLogger(NodeStatus.class);
		private String name;
		
		// 1, 2, 3
		private int status = 0;
		private Date startTime;
		private Date endTime;
		
		private String saName = ""; //工程师名字
		private String position = ""; //职位
	    private Integer currentStatus = 0;
	    private String descr = "";
	    private String bezei = ""; //人工组名称
	    
		
		private Object data;
		//private Integer index;
		
		private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		private SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");

		public NodeStatus(){}
		
		/**
		 * @param status
		 * @param startTime
		 * @param data
		 */
		public NodeStatus(Integer status, Date startTime, Object data) {
			super();
			this.status = status;
			this.startTime = startTime;
			this.data = data;
		}

		


		/**
		 * @param status
		 */
		public NodeStatus(Integer status) {
			super();
			this.status = status;
		}




		/**
		 * @param status
		 * @param startTime
		 */
		public NodeStatus(Integer status, Date startTime) {
			super();
			this.status = status;
			this.startTime = startTime;
		}




		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}



		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}



		/**
		 * @return the status
		 */
		public int getStatus() {
			return status;
		}

		/**
		 * @param status the status to set
		 */
		public void setStatus(int status) {
			this.status = status;
		}

		/**
		 * @return the startTime
		 */
		public Date getStartTime() {
			return startTime;
		}
		
		public String getStartTimeStr() {
			if(startTime == null)
			{
				return "";
			}
			return sdf.format(startTime);
		}

		/**
		 * @param startTime the startTime to set
		 */
		public void setStartTime(Date startTime) {
			this.startTime = startTime;
		}
		
		
		public void setStartTime(String startTime) {
			try {
				this.startTime = sdf2.parse(startTime);
			} catch (ParseException e) {
				logger.error("Parse start time error.",e);
			}
		}

		/**
		 * @return the endTime
		 */
		public Date getEndTime() {
			return endTime;
		}
		
		
		public String getEndTimeStr() {
			if (endTime == null)
			{
				return "";
			}
			return sdf.format(endTime);
		}

		/**
		 * @param endTime the endTime to set
		 */
		public void setEndTime(Date endTime) {
			this.endTime = endTime;
		}
		
		public void setEtartTime(String endTime) {
			try {
				this.endTime = sdf2.parse(endTime);
			} catch (ParseException e) {
				logger.error("Parse end time error.",e);
			}
		}

		/**
		 * @return the data
		 */
		public Object getData() {
			return data;
		}

		/**
		 * @param data the data to set
		 */
		public void setData(Object data) {
			this.data = data;
		}

		public String getSaName() {
			return saName;
		}

		public void setSaName(String saName) {
			this.saName = saName;
		}

		public String getPosition() {
			return position;
		}

		public void setPosition(String position) {
			this.position = position;
		}

		public int getCurrentStatus() {
			return currentStatus;
		}

		public void setCurrentStatus(int currentStatus) {
			this.currentStatus = currentStatus;
		}
		
		public int getHappenStatus()
		{
			if(this.status < this.currentStatus)
			{
				return -1; //已经完成
			}else if(this.status == this.currentStatus)
			{
				return 0; //进行中
			}else{
				return 1; //等待
			}
		}

		public String getDescr() {
			return descr;
		}

		public void setDescr(String descr) {
			this.descr = descr;
		}

		public String getBezei() {
			return bezei;
		}

		public void setBezei(String bezei) {
			this.bezei = bezei;
		}
		
		

		
	}
}
