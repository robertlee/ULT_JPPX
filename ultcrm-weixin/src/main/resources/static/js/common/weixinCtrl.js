//preIndex页面的控制器
ultcrm.controller('weixinCtrl', 
		function($scope, $http, $location, $state, $window, $ionicLoading, commonService, customerDataService) {
	var searchObject = $location.search();
	var code = searchObject['code'];
	var state = searchObject['state'];
	var uid = searchObject['uid'];
	$scope.type = 'snsapi_userinfo';
	$scope.state = state;
	//alert("state="+state);
	var result = null;
	if(code){
		var url = '/getCustomerByCode/' + code + '/' + $scope.type;
		$http.get(url)
		.success(function(data) {
			if (data == '') {
			}else{
				result = data;
				console.log(data)
				customerDataService.setCustomer(data);
				$scope.load(state);
			}
		}).error(function() {
		});
	} else if (uid) {
		$http.get('/getCustomer/' + uid).success(
			function(data){
				result = data;
				console.log(data);
				customerDataService.setCustomer(data);
				$scope.load(state);
			}
		);
	}else{
		$http.get('/getCustomer/' + 1).success(
				function(data){
					result = data;
					console.log(data);
					customerDataService.setCustomer(data);
					$scope.load(state);
				}
			);
	}
	
	$scope.load = function(state){
		switch (state) {
		case 'my':
			$state.go('index.myindex',{}, {reload: true}); 
			break;
		case 'orderlist':
			$state.go('index.myorderList',{viewType:"new"}, {reload: true}); 
			break;
		case 'profile':
			$state.go('index.myprofileList',{}, {reload: true}); 
			break;
		case 'home':
			$state.go('index.home',{}, {reload: true}); 
			break;
		case 'cardlist':
			$state.go('index.cardList',{}, {reload: true}); 
			break;
		default:
			$scope.handleParamState(state);
			break;
		}
	};
	
	$scope.handleParamState = function(state) {
		if (state.match("receiveCard")) {
			var params = state.split("_");
			var orderIdParam = params[1];
			$state.go('index.cardCouponBatch', {orderId:orderIdParam}, {reload: true});
		}
		else if (state.match("orderlist")) {
			// 有参数的orderList
			var params = state.split("_");
			var viewTypeParam = params[1];
			$state.go('index.myorderList',{viewType:viewTypeParam}, {reload: true});
		}
		else {
			$state.go('index.home', {}, {reload: true});
		}
	}
	
	
});
