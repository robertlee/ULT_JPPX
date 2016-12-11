
ultcrm.controller('profileListCtrl', function($scope,$ionicPopup,$location,$http,$interval,$state,$window,customerData,customerDataService) {
	
	//获取用户姓名，性别，手机号码
	$scope.customer={};
	
	
	var customer = customerData;
	
	console.log(customer);
	$scope.customer.headimgurl=customer.headimgurl;
	if(!$scope.customer.headimgurl){
		$scope.customer.headimgurl="/img/my/icon_a.png";
	}
	$scope.customer.name=customer.name;
	if(!$scope.customer.name){
		$scope.customer.name=customer.nickname;
	}
	$scope.customer.sex=customer.sex;
	$scope.customer.phone=customer.phone;
	
	console.log($scope.customer.name);
	console.log($scope.customer.sex);
	console.log($scope.customer.phone);
	
	
	
	//去修改姓名页面
	$scope.toModifyName=function(){
		$state.go('index.modifyName',{},{reload:true});
	};
	
	//去修改性别页面
	$scope.toModifySex=function(){
		$state.go('index.modifySex',{},{reload:true});
	};
	
	//去修改手机号码页面
	$scope.toModifyPhone=function(){		
		$state.go('index.modifyPhone',{},{reload:true});
	};



});
