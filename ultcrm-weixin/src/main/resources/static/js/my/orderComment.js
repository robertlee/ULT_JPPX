ultcrm.controller('orderCommentCtrl', function($scope,$http,$timeout,$state,$ionicPopup,$stateParams,$window,$ionicSlideBoxDelegate,orderDataService,customerDataService) {
	// 查询用户的数据
	var customer = customerDataService.getCustomer();
	//var order = orderDataService.getOrder();
	$scope.orderId = $stateParams.orderId;
	
	$scope.saStarArray = [0,0,0,0,0];
	$scope.plantStarArray = [0,0,0,0,0];
	$scope.lobbyStarArray = [0,0,0,0,0];

	$scope.saStar = 0;
	$scope.plantStar =0;
	$scope.lobbyStar =0;
	
	$scope.data = {};
	
	
	// 获取用户的评价数据
	$http.get("/getCommentByOrderId/"+$scope.orderId).success(function(data){
		
		$scope.data.comment = data.comment;
		
		// 解析星星的数量，成数组
		$scope.saStarArray = $scope.getStarArray(data.saStar);
		$scope.plantStarArray = $scope.getStarArray(data.plantStar);
		$scope.lobbyStarArray = $scope.getStarArray(data.lobbyStar);
		
		$scope.saStar = data.saStar;
		$scope.plantStar =data.plantStar;
		$scope.lobbyStar =data.lobbyStar;
		
	});
	
	$scope.getStarArray = function(starValue) {
		var result = new Array();
		for (var i = 0 ; i < 5; i ++) {
			if (i<starValue) {
				result.push(1);
			}
			else {
				result.push(0);
			}
			
		}
		return result;
	}
	
	$scope.setSaStar = function(starValue) {
		$scope.saStar = starValue+1;
		$scope.saStarArray = $scope.getStarArray($scope.saStar);
	}
	
	$scope.setPlantStar = function(plantValue) {
		$scope.plantStar = plantValue+1;
		$scope.plantStarArray = $scope.getStarArray($scope.plantStar);
	}
	
	$scope.setLobbyStar = function(starValue) {
		$scope.lobbyStar = starValue+1;
		$scope.lobbyStarArray = $scope.getStarArray($scope.lobbyStar);
	}
	
	$scope.isValid = function(){
		return $scope.saStarArray[0] || $scope.plantStarArray[0] || $scope.lobbyStar[0] || $scope.data.comment;
	}
	
	$scope.confirm = function() {		
		// 创建一个评论
		$http.post("/createCommentForOrder/"+$scope.orderId+"/"+customer.id,{"saStar":$scope.saStar,"plantStar":$scope.plantStar,"lobbyStar":$scope.lobbyStar,"comment":$scope.data.comment})
		.success(function(data){
			
    		   //取消订单，成功后弹出提醒，2秒后消失
    		   var alertPopup = $ionicPopup.show({
   			   		cssClass: 'popup-custom popup-show',
   			   		title: '<span class="iconfont icon-appointmentok blue"></span>课程评论创建成功！'
    		   });
    		   $timeout(function() {
    			   alertPopup.close(); //close the popup after 3 seconds for some reason
    			}, 1500);
			   alertPopup.then(function(res) {
				   $state.go("index.myorderList",{'viewType':'comment'}, {reload: true});
				   //console.log('Create comment successfully!');
			   });
			
		});
	}
	
	$scope.back = function() {
		$state.go("index.myorderList",{'viewType':'comment'}, {reload: true});
	}
	
	
});