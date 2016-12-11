
ultcrm.controller('appointmentOkCtrl', function($scope,$stateParams,$state,$window,$http,$ionicModal,appointmentService) {
	
	$scope.appointmentData = appointmentService.getAppointData();
	
	
	var courseId = appointmentService.getAppointData().courseId;
	var modelId = appointmentService.getAppointData().modelId;
//	var storeId = appointmentService.getAppointData().storeId;
	var bussTypeId  = appointmentService.getAppointData().businessTypeId;
	
	$scope.data = $stateParams.data;
	$scope.hasCard = $stateParams.hasCard;
	

	if ($scope.data != null && $scope.data.updateTimesegment != null && $scope.data.updateTimesegment == "Y") {
		/*预约成功页面信息*/
		$scope.titleStr = '恭喜您课程时间修改成功! ';
		$scope.buttonStr = '我要领券 ';
		$scope.techlevelno = $scope.data.techlevelno;
		
		appointmentService.setOrderId($scope.data.orderId) ;
		
	}else{
		/*修改成功页面信息*/
		$scope.titleStr = '恭喜您课程预约成功!';
		$scope.buttonStr = '订单查询 ';
		/*后退时，没有参数，所以不覆盖原有orderId*/
		if($stateParams.orderId !=null){
			appointmentService.setOrderId($stateParams.orderId) ;	
		}
	}
	
	$scope.orderId = appointmentService.getOrderId();
	
	$scope.canReceive = function() {
		if ($scope.data && $scope.data.updateTimesegment) {
			return false;
		}
		else {
			return $scope.hasCard?true:false;
		}
	}
	
	$scope.toDirect = function(){
		$state.go("index.direct"); 
	}
	
	//回到订单页面
	$scope.toOrderList = function($index){
		$state.go("index.myorderList",{viewType:"new"}, {reload: true});
	};
	
	$scope.getCard = function() {
		$state.go("index.cardCouponBatch",{orderId:$scope.orderId}, {reload: true});
	}

});