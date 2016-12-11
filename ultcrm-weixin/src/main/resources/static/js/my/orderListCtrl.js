ultcrm.controller('orderListCtrl', function($scope,$location,$http,$stateParams,$state,businessTypeMap,$filter,$ionicPopup,$timeout,$window,$ionicListDelegate,orderDataService,customerDataService,appointmentService) {
	// 查询用户的数据
	var customer = customerDataService.getCustomer();
	
	$scope.businessTypeMap = businessTypeMap;
	// 根据客户id获取客户的订单
	$scope.newActive = false;
	$scope.workingActive = false;
	$scope.completeActive = false;
	$scope.commentActive = false;
	
	$scope.newHasMoreDate = true;
	$scope.workingHasMoreDate = true;
	$scope.completeHasMoreDate = true;
	$scope.commentHasMoreDate = true;
	
	$scope.newPage = 0;
	$scope.workingPage = 0;
	$scope.completePage = 0;
	$scope.commentPage = 0;
	
	$scope.newCount = 0;
	$scope.workingCount = 0;
	$scope.completeCount = 0;
	$scope.commentCount = 0;
	
	
	$scope.newLastTime = $filter('date')(new Date(),"yyyyMMddHHmmss"); 
	$scope.workingLastTime = $filter('date')(new Date(),"yyyyMMddHHmmss");
	$scope.completeLastTime = $filter('date')(new Date(),"yyyyMMddHHmmss");
	$scope.commentLastTime = $filter('date')(new Date(),"yyyyMMddHHmmss");
	
	$scope.orderList = [];
	$scope.newOrderList = [];
	$scope.workingOrderList = [];
	$scope.completeOrderList = [];
	$scope.commentOrderList = [];

	$scope.option = {
			listCanSwipe:true,
			shouldShowReorder:false,
			shouldShowDelete:false,
			shouldShowHistory:true
	};
	
	/*格式化时间，时间格式为20150304121300,2015/03/04 12:13*/
	$scope.formatTime = function(time){
		//过滤无效时间
		if(time == false || !angular.isString(time) || time.replace(' ','')==''){
			return ' -';
		}
		
		return time.substr(0,4) + '/' + time.substr(4,2) + '/' + time.substr(6,2) + ' '	//年月日
			+ time.substr(8,2) + ':' + time.substr(10,2);	//时分
	}
	
	$http.get("/getOrderListByCustomerId/"+customer.id).success(function(data){
		//$scope.$apply(function(){
			//时间降序
			$scope.newOrderList = data['new'].sort(function(a,b){
												return new Date(b.lastUpdateTime) - new Date(a.lastUpdateTime)
											});
			$scope.workingOrderList = data['working'].sort(function(a,b){
												return new Date(b.lastUpdateTime) - new Date(a.lastUpdateTime)
											});
			$scope.completeOrderList = data['complete'].sort(function(a,b){
												return new Date(b.lastUpdateTime) - new Date(a.lastUpdateTime)
											});
			console.log($scope.completeOrderList);
			$scope.commentOrderList = data['comment'].sort(function(a,b){
												return new Date(b.lastUpdateTime) - new Date(a.lastUpdateTime)
											});
					
			if ($scope.newOrderList == null || $scope.newOrderList.length<5) {
				$scope.newHasMoreDate = false;
			}
			
			if ($scope.newOrderList.length>0) {
				$scope.newPage = 1;
			}
			
			if ($scope.workingOrderList == null || $scope.workingOrderList.length<5) {
				$scope.workingHasMoreDate = false;
			}
			
			if ($scope.workingOrderList.length>0) {
				$scope.workingPage = 1;
			}
			
			if ($scope.completeOrderList == null || $scope.completeOrderList.length<5) {
				$scope.completeHasMoreDate = false;
			}
			
			if ($scope.completeOrderList.length>0) {
				$scope.completePage = 1;
			}
			
			if ($scope.commentOrderList == null || $scope.commentOrderList.length<5) {
				$scope.commentHasMoreDate = false;
			}
			
			if ($scope.commentOrderList.length>0) {
				$scope.commentPage = 1;
			}
			
			for (var i = 0; i < $scope.completeOrderList.length; i++) {
				if (i == 0) {//从上到下显示数据，第一个数据一定会有头
					$scope.completeOrderList[i].shouldShowDivider = true;
				}else{
					var c = $scope.completeOrderList[i].createTime.slice(0,7);
					var p = $scope.completeOrderList[i-1].createTime.slice(0,7);
					if (c == p) {
						$scope.completeOrderList[i].shouldShowDivider = false;
					}else{
						$scope.completeOrderList[i].shouldShowDivider = true;
					}
				}
			}
			
			if ($scope.newActive) {
				$scope.orderList = $scope.newOrderList;
			}
			else if ($scope.workingActive) {
				$scope.orderList = $scope.workingOrderList;
			}
			else if ($scope.completeActive) {
				$scope.orderList = $scope.completeOrderList;
			}
			else if ($scope.commentActive) {
				$scope.orderList = $scope.commentOrderList;
			}
	});
	
	
	$scope.viewNew = function() {
		$scope.newActive = true;
		$scope.workingActive = false;
		$scope.completeActive = false;
		$scope.commentActive = false;
		$scope.orderList = $scope.newOrderList;
		$scope.option.listCanSwipe = true;
		$scope.option.shouldShowHistory = false;
	}
	
	$scope.viewWorking = function() {
		$scope.newActive = false;
		$scope.workingActive = true;
		$scope.completeActive = false;
		$scope.commentActive = false;
		$scope.orderList =$scope.workingOrderList;
		$scope.option.listCanSwipe = false;
		$scope.option.shouldShowHistory = false;
	}

	$scope.viewComplete = function() {
		$scope.newActive = false;
		$scope.workingActive = false;
		$scope.completeActive = true;
		$scope.commentActive = false;
		$scope.orderList = $scope.completeOrderList;
		$scope.option.listCanSwipe = false;
		$scope.option.shouldShowHistory = true;
	}
	
	$scope.viewComment = function() {
		$scope.newActive = false;
		$scope.workingActive = false;
		$scope.completeActive = false;
		$scope.commentActive = true;
		$scope.orderList = $scope.commentOrderList;
		$scope.option.listCanSwipe = false;
		$scope.option.shouldShowHistory = true;
	}
	

	$scope.isShow = function(item) {
		if (item.status == 1 && item.operShow) {
			return true;
		}
		else {
			return false;
		}
	}
	$scope.moreDataCanBeLoaded = function() {
		
		if ($scope.newActive) {

			return $scope.newHasMoreDate;
		}
		else if ($scope.workingActive) {

			return $scope.workingHasMoreDate;
		}
		else if ($scope.completeActive) {

			return $scope.completeHasMoreDate;
		}
		else if ($scope.commentActive) {

			return $scope.commentHasMoreDate;
		}
		
	}
	
	$scope.toggleItem = function(item) {
		// 将所有其他的order item 关闭
		for (var i = 0 ; i < $scope.orderList.length; i++) {
			var orderItem = $scope.orderList[i];
			if (orderItem.id != item.id) {
				orderItem.operShow = false;
			}
		}
		item.operShow = item.operShow === false ? true: false;
	}
	$scope.viewWorkingOrder = function(order) {
		//alert("viewWorkingOrder "+order.id);
		orderDataService.setViewOrderId(order.id);
		$state.go("index.myorderProcessDetail",{orderId:order.id}, {reload: true}); 
	}
	
	$scope.viewCompleteOrder = function(order) {
		//alert("viewCompleteOrder "+order.id);
		orderDataService.setViewOrderId(order.id);
		$state.go("index.myorderProcessDetail",{orderId:order.id}, {reload: true}); 
	}
	
	$scope.commentOrder = function(order) {
		
		$state.go("index.myorderComment",{orderId:order.id}, {reload: true}); 
	}
	
	$scope.orderOperation = function(order) {

		if (order.status==2) {
			$state.go("index.myorderProcessDetail",{orderId:order.id}, {reload: true}); 
		}
		
		// 如果是完成了的，都可以评价
		if (order.status==3) {
			$state.go("index.myorderComment",{orderId:order.id}, {reload: true}); 
		}
	}
	
	$scope.viewNewOrder = function(order) {
		//跳转到订单详情页面
		$state.go("index.orderInfo",{orderId:order.id,seatname:order.seatname,startClassTime:order.startClassTime}, {reload: true});
	}
	
	// 获取统计数据
	$http.get("/getCountInfo/"+customer.id).success(function(data){			
		$scope.newCount = data.newCount;
		$scope.workingCount = data.workingCount;
		$scope.completeCount = data.completeCount;
		$scope.commentCount = data.commentCount;
		
		// 如果从订单完成页面来的请求，直接到未处理tab
		if ($stateParams.viewType ) {
			if ($stateParams.viewType =="new") {
				$scope.viewNew();
			}
			else if ($stateParams.viewType =="working") {
				$scope.viewWorking();
			}
			else if ($stateParams.viewType =="complete") {
				$scope.viewComplete();
			}
			else if ($stateParams.viewType =="comment") {
				$scope.viewComment();
			}
		}
		else {
			
			if ($scope.workingCount>0) {
				$scope.viewWorking();
			}
			else if ($scope.commentCount>0) {
				$scope.viewComment();
			}
			else if ($scope.newCount>0) {
				$scope.viewNew();
			}
			else {
				$scope.viewComplete();
			}
		}

	});
	
});
