package com.uletian.ultcrm.business.value;

/**
 * ULTCRM和CRM集成订单的大对象,仅仅做集成是用. 根据CRM要求，每个属性都必须有默认值
 * @author robertliu1
 *
 */
public class OrderMessage {    

    private String action = "";        //同步动作
    private String sourceSys = "";     //同步源
    private String orderid = "";       //ultcrm预约单id
    private String status = "";        // 订单状态
    private String customerId = "";        // 客户ID
    private String businessTypeId = "";     // 业务类型ID
    private String customerName = "";  //客户名称
    private String customerPhone = ""; //客户电话
    private String sdateSegment = "";  
    private String stimeSegment = "";
    private String saName = "";

    private String packageItem = "";
    private String erpstoreid = "";

    private String techlevelno = "";
    private String description= "";
    
    private String techerno = "";
    private String statusRelationInfo = "";  //工单状态详细信息
    private String crmOrderid = "";         //工单ID
    private String crmCustomerid = "";      //crm客户id
    private String crmOrderStatus = "";      //crm的订单状态
    private String crmTotalAmount= "";          //crm 总金额
    private String crmDiscountTotalAmount = "";  //crm 折扣后总金额
    private String crmPlanFinishedtime= "";      //计划结束时间
    private String crmTotalCredit = "";  //
    private String crmTotalCard= "";      //
    private String orderInfo= "";              //订单相关信息

	public String getStatusRelationInfo() {
		return statusRelationInfo;
	}

	public void setStatusRelationInfo(String statusRelationInfo) {
		this.statusRelationInfo = statusRelationInfo;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}


	public String getTechlevelno() {
		return techlevelno;
	}

	public void setTechlevelno(String techlevelno) {
		this.techlevelno = techlevelno;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTecherno() {
		return techerno;
	}


	public void setTecherno(String techerno) {
		this.techerno = techerno;
	}

	public String getSourceSys() {
		return sourceSys;
	}

	public void setSourceSys(String sourceSys) {
		this.sourceSys = sourceSys;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getErpstoreid() {
		return erpstoreid;
	}

	public void setErpstoreid(String erpstoreid) {
		this.erpstoreid = erpstoreid;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getSdateSegment() {
		return sdateSegment;
	}
	
	public void setSdateSegment(String sdateSegment) {
		this.sdateSegment = sdateSegment;
	}

	public String getStimeSegment() {
		return stimeSegment;
	}

	public void setStimeSegment(String stimeSegment) {
		this.stimeSegment = stimeSegment;
	}

	public String getPackageItem() {
		return packageItem;
	}

	public void setPackageItem(String packageItem) {
		this.packageItem = packageItem;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCrmOrderId() {
		return crmOrderid;
	}

	public void setCrmworkorderid(String crmOrderId) {
		this.crmOrderid = crmOrderId;
	}

	public String getCrmCustomerId() {
		return crmCustomerid;
	}

	public void setCrmCustomerId(String crmCustomerId) {
		this.crmCustomerid = crmCustomerId;
	}

	public String getCrmOrderstatus() {
		return crmOrderStatus;
	}

	public void setCrmOrderstatus(String crmOrderstatus) {
		this.crmOrderStatus = crmOrderstatus;
	}

	public String getCrmTotalAmount() {
		return crmTotalAmount;
	}

	public void setTotalAmount(String crmTotalAmount) {
		this.crmTotalAmount = crmTotalAmount;
	}

	public String getCrmDiscountTotalAmount() {
		return crmDiscountTotalAmount;
	}

	public void setCrmDiscountTotalAmount(String crmDiscountTotalAmount) {
		this.crmDiscountTotalAmount = crmDiscountTotalAmount;
	}

	public String getCrmPlanFinishedtime() {
		return crmPlanFinishedtime;
	}

	public void setCrmPlanFinishedtime(String crmPlanFinishedtime) {
		this.crmPlanFinishedtime = crmPlanFinishedtime;
	}

	public String getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(String orderInfo) {
		this.orderInfo = orderInfo;
	}

	public String getCrmTotalCredit() {
		return crmTotalCredit;
	}

	public void setCrmTotalCredit(String crmTotalCredit) {
		this.crmTotalCredit = crmTotalCredit;
	}

	public String getCrmTotalCard() {
		return crmTotalCard;
	}

	public void setCrmTotalCard(String crmTotalCard) {
		this.crmTotalCard = crmTotalCard;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getBusinessTypeId() {
		return businessTypeId;
	}

	public void setBusinessTypeId(String businessTypeId) {
		this.businessTypeId = businessTypeId;
	}

	public String getSaName() {
		return saName;
	}

	public void setSaName(String saName) {
		this.saName = saName;
	}
  
}
