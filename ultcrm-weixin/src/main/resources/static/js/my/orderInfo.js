/**
 * 
 */
ultcrm.controller('orderInfoCtrl', function($scope, $http, $interval, $state, $stateParams, $window, orderDataService) {
	$scope.orderInfo = {};
	
	// 返回
	$scope.back = function(){
		$state.go("index.myorderList",{'view Type':'new'}, {reload: true}); 
	};	
	// 导航页面
	$scope.toDirect = function(){
		$state.go("index.direct",{}, {reload: true}); 
	};
	$scope.toSeat = function(){
		$state.go("index.direct",{}, {reload: true}); 
	};	
	
	// 获取订单信息
	$http.get('/getOrderInfo/'+$stateParams.orderId).success(function(data, status, headers, config){
	    $scope.orderInfo.orderId = data.orderId;
		$scope.orderInfo.classId = data.classId;	
		$scope.orderInfo.classname = data.classname;
		$scope.orderInfo.roomName = data.roomName;
		$scope.orderInfo.roomAddress = data.roomAddress;
		
		var tmpSeat = $stateParams.seatname;
		$scope.orderInfo.seatname = tmpSeat;
		$scope.orderInfo.startClassTime = data.classTime;
		$scope.orderInfo.classTimeDetail = data.classTimeDetail;
		$scope.orderInfo.teachName = data.teachName;
		$scope.orderInfo.teachId = data.teachId;
		$scope.orderInfo.price = data.price;
		$scope.orderInfo.classHour = data.classHour;
		$scope.orderInfo.childName = data.childName;	
		}).error(function(data, status, headers, config) {
		//发生错误，返回订单列表
		//alert("获取课程订单信息失败");
		$state.go("index.myorderList",{'viewType':'new'}, {reload: true}); 
	});
	//跳转到教师详情页面
	$scope.click_teacher=function(){
		var id = parseInt($scope.orderInfo.teachId);
		$state.go('index.teacherDetail',{id:id},{reload:true});
	}
});