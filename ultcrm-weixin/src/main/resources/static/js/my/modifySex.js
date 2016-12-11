//优乐天2016
ultcrm.controller('modifySexCtrl', function($scope,$ionicPopup,$location,$http,$timeout,$state,$window,customerDataService) {
	
	$scope.sexs = ["男","女"];
	
	$scope.customer=customerDataService.getCustomer();
	switch($scope.customer.sex){
	case '男':
		$scope.isMale=true;
		$scope.isFemale=false;
		break;
	case '女':
		$scope.isMale=false;
		$scope.isFemale=true;
		break;
	default:
		$scope.isMale=false;
		$scope.isFemale=false;
		break;
	}
	
	
    $scope.changeSex=function(){
    	switch($scope.customer.sex){
	    	case '男':
	    		$scope.isMale=true;
	    		$scope.isFemale=false;
	    		break;
	    	case '女':
	    		$scope.isMale=false;
	    		$scope.isFemale=true;
	    		break;
	    	default:
	    		$scope.isMale=false;
	    		$scope.isFemale=false;
	    		break;
    	}
    	customerDataService.setSex($scope.customer.sex);
    	// 后台修改
    	$http.post("/modifyUser/",$scope.customer).success(function(data){
    		// 修改用户的性别成功，不做任何事情,跳转回去
    		console.log("Modify user successfully!");
    		$timeout(function(){
    			$state.go('index.myprofileList',{},{reload:true});
    		},100);
    	});
    };


    
});

