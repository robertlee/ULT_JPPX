ultcrm.controller('modifyNameCtrl', function($scope,$ionicPopup,$location,$http,$interval,$state,$window,customerDataService) {
	
	$scope.isFocus = false;
	$scope.setFocus = function(bfocus){
		$scope.isFocus = bfocus;
	}
	$scope.customer = {};
	$scope.customer.name = customerDataService.getCustomer().name?customerDataService.getCustomer().name:customerDataService.getCustomer().nickname;
	
	$scope.placeholderName = $scope.customer.name;
	$scope.clearName = function(){
		$scope.customer.name = "";
		$scope.placeholderName = customerDataService.getCustomer().name?customerDataService.getCustomer().name:customerDataService.getCustomer().nickname;
	}

    $scope.toProfileList=function(){
    	if(!$scope.customer.name){
    		$scope.customer.name=customerDataService.getCustomer().name?customerDataService.getCustomer().name:customerDataService.getCustomer().nickname;
    	}else{customerDataService.setName($scope.customer.name);}
    	// 后台修改
    	$http.post("/modifyUser/",customerDataService.getCustomer()).success(function(data){
    		// 修改用户的名称成功，不做任何事情,跳转回去
    		console.log("Modify user successfully!");
    		$state.go('index.myprofileList',{},{reload:true});
    	});
    }
     

});

