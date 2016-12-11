
angular.module('ultcrm.services', [])
.service('customerDataService',function($q,$http){
	
	var customer = {};
	
	this.getCustomer = function() {
		return customer;
	}

	this.getCustomerId = function() {
		if (customer.id != null && (customer.id != "" && customer.id != "0" && customer.id != 0))
		{
		    return customer.id;	
		}else{
			return null;
		}		
	}
	
	this.setOpenId = function(openId){
		customer.openid = openId;
	}
	
	this.setCustomerId = function(customerId) {
		customer.id = customerId;
	}
	

	this.setCustomer = function(customerArg) {
		customer = customerArg;
	}
	this.setPhone = function(phone){
		customer.phone = phone;
	}
	
	this.setName = function(name){
		customer.name = name;
	}
	
	this.setSex = function(sex){
		customer.sex = sex;
	}
	
	this.setUnionid = function(unionid){
		customer.unionid = unionid;

	}
	
	this.setSex = function(sex) {
		customer.sex = sex;
	}
	
	this.find = function (uid,code) {

		var type = 'snsapi_userinfo';
		var dfd = $q.defer();
		if(this.getCustomerId() != null)
			{
			    dfd.resolve(customer);
			}else{
				if(code){
					console.log("init customer by weixincode=" + code);
					var url = '/getCustomerByCode/' + code + '/' + type;
					$http.get(url).success(function(data) {
						if (data == '') {
						}else{
							customer = data;
							dfd.resolve(data);
							console.log("init customer by weixincode= " + data.id);
						}
					}).error(function() {
					});
					
				} else if (uid) {
					
					console.log("init customer by uid=" + uid);
					$http.get('/getCustomer/' + uid).success(
						function(data){
							customer = data;
							dfd.resolve(data);
							console.log("end init customer ");
						}
					);
					
				}				
		}
	   return dfd.promise;
	}
})
.service('orderDataService',function(){
	var order = {};
	
	this.getOrder = function() {
		return order;
	}
	
	this.setOrder = function(orderValue){
		order = orderValue;
	}
	
	var viewOrderId = null;
	
	this.getViewOrderId = function() {
		return viewOrderId;
	}
	
	this.setViewOrderId = function(orderId){
		viewOrderId = orderId;
	}

})
.service('commonService',function(){

	
	this.toIndexPage = function() {
		window.location.href="http://"+window.location.host+":"+window.location.port+"/index.html#/index/home";
	}
	
	
})
// 微信接口的一些公共服务
.service('weixinService',function($http,customerDataService){
	this.getCustomerByCode= function() {
		console.log("weixinService getCustomerByCode");
		var code = getQueryString("code");
		console.log(code);
		//var code = queryMap['code'];
		var result = null;
		$http.get("/getCustomerByCode/"+code).success(function(data){
			result = data;
			console.log(data)
			customerDataService.setCustomer(data);
		}
		);
		return result;
	}

})
.service('appointmentService',function(){
	var appointData = {};
	
	appointData.packageArray = new Array();	
	
	this.setPackageArray = function(packages) {
		appointData.packageArray = packages;
	}
	
	this.getPackageArray = function(packages) {
		return appointData.packageArray;
	}
	
	this.getPackageIdArray = function() {
		var result = new Array();
		for (var i = 0 ; i < appointData.packageArray.length; i++) {
			var pkg = appointData.packageArray[i];
			result.push(pkg.packageid);
		}
		return result;
	}
	
	this.addBusinessTypeId = function(businessTypeId) {
		appointData.businessTypeArray.push(businessTypeId);
	}
	
	this.setBusinessTypeId = function(businessTypeId) {
		appointData.businessTypeId = businessTypeId;
	}
	
	this.getBusinessTypeId = function() {
		return appointData.businessTypeId;
	}
	
	this.setAppointmentId = function(id){
		appointData.id = id;
	}
	
	this.getAppointmentId = function() {
		return appointData.id;
	}	
	
	this.setCourseId = function(courseId) {
		appointData.courseId = courseId;
	}
	this.setCourseName = function(courseName) {
		appointData.courseName = courseName;
	}
	
	this.setSeryName = function(seryName) {
		appointData.seryName = seryName;
	}
	
	this.getCourseName = function() {
		return appointData.courseName;
	}
	
	this.setModelId = function(modelId) {
		appointData.modelId = modelId;
	}
	
	this.getModelId = function() {
		return appointData.modelId;
	}
	
	this.setModelName = function(modelName) {
		appointData.modelName = modelName;
	}
	
	this.setStoreId = function(storeId) {
		appointData.storeId = storeId;
	}
	
	this.setStoreName = function(storeName) {
		appointData.storeName = storeName;
	}
	
	this.setTimeSegmentId = function(timeSegmentId) {
		appointData.timeSegmentId = timeSegmentId;
	}

	this.getTimeSegmentId = function(){
		return appointData.timeSegmentId;
	}
	
	this.getAppointData = function() {
		return appointData;
	}
	
	this.getSegmentDate = function() {
		return appointData.segmentDate;
	}
	
	this.setSegmentDate = function(segmentDate){		
		appointData.segmentDate = segmentDate;
	}
	
	this.getSegmentTime = function() {
		return appointData.segmentTime;
	}
	
	this.setSegmentTime = function(segmentTime){	
		appointData.segmentTime = segmentTime;
	}
	
	//总价
	this.getTotalPrice = function() {
		return appointData.totalPrice;
	}
	
	//设置总价
	this.setTotalPrice = function(price){	
		appointData.totalPrice = price;
	}
	
	//总价
	this.getDiscountTotalPrice = function() {
		return appointData.discountTotalPrice;
	}
	
	//设置总价
	this.setDiscountTotalPrice = function(price){	
		appointData.discountTotalPrice = price;
	}
	
	// tech id
	this.setTechId = function(techId){	
		appointData.techId = techId;
	}
	
	this.getTechId = function(){
		return appointData.techId;
	}
	
	this.setOrderId = function(orderId) {
		appointData.orderId = orderId;
	}
	
	this.getOrderId = function() {
		return appointData.orderId;
	}
	
	this.setTech = function(tech) {
		appointData.tech = tech;	
		appointData.techId = tech.id;
		appointData.modelId = tech.techModel.id;
		appointData.modelName = tech.techModel.name;
		appointData.courseId = tech.techCourse.id;
		appointData.courseName = tech.techCourse.name;
		appointData.seryName = tech.techSery.name;
		appointData.techlevelno = tech.techlevelno;
	}
	
	this.getTech = function() {
		return appointData.tech;
	}
	
}).service('myWalletService',function() {
	
	var myWalletData = {};
	
	
	this.setByData = function(data){	
		myWalletData = data;
	}
	
	this.getByData = function(){	
		return myWalletData;
	}
	// 
	this.getPublishCardCount = function(){	
		myWalletData.publishCardCount;
	}
	this.getWriteoffCardCount = function(){	
		myWalletData.writeoffCardCount;
	}
	this.getCancelCardCount = function(){	
		myWalletData.cancelCardCount;
	}
	
	
	this.getPublishCouponCount = function(){	
		myWalletData.publishCouponCount;
	}
	this.getWriteoffCouponCount = function(){	
		myWalletData.writeoffCouponCount;
	}
	this.getCancelCouponCount = function(){	
		myWalletData.cancelCouponCount;
	}
	
	
	this.getPublishCount = function(){	
		myWalletData.publishCount;
	}
	this.getWriteoffCount = function(){	
		myWalletData.writeoffCount;
	}
	this.getCancelCount = function(){	
		myWalletData.cancelCount;
	}
});

