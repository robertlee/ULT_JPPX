ultcrm.controller('myIndexCtrl', function($scope,$http,$location,$state,$filter,$stateParams,$window,myWalletService,customerData,customerDataService) {
	 
	var searchObject = $location.search();
	
	var code = searchObject['code'];
	var state = searchObject['state'];
	
	$scope.handingCount = 0;

	$scope.newCount = 0;
	$scope.workingCount = 0;
	$scope.completeCount = 0;
	$scope.commentCount = 0;
	$scope.techList = new Array();
	$scope.techSubArray = new Array();
	$scope.even = false;
	
	$scope.addTech = function(){
		$state.go("index.addTech",{}, {reload: true});
	};
	
	$scope.addTechPre = function(tech){
		$state.go("index.addTechPre",{techId:tech.id}, {reload: false});
	};
	
	//将男女转换为先生女士
	$scope.convertSex = function(sex){
		var sexMap = {
				"男":"先生",
				"女":"女士"
		};
		//如果没有设置性别，则不显示称呼
		return sexMap[sex] || '';
	};
	
	// 获取用户统计信息
	$scope.getUserCountInfo = function(customerId) {
		$http.get("/getCountInfo/"+customerId).success(function(data){			
			
			$scope.newCount = data.newCount;
			$scope.workingCount = data.workingCount;
			$scope.completeCount = data.completeCount;
			$scope.commentCount = data.commentCount;
			
			$scope.handingCount = parseInt(data.newCount)+parseInt(data.workingCount);
			
		});
	};
	
	// 获取用户技能信息

	
	// 首选从customerDataService获取customer数据，如果没有的话 就开始抓取后台的数据	
	var custId = customerData.id;	
	$scope.customer = customerData;
	console.log($scope.customer.sex);
	
	if ( !($scope.customer.nickname)  && code ) {

		$http.get("/getCustomerByCode/"+code+"/snsapi_userinfo").success(
				function(data){
					//alert(data);
					result = data;
					console.log(data);
					customerDataService.setCustomer(data);
					$scope.customer = data;
					// 获取用户统计信息
					$scope.getUserCountInfo($scope.customer.id);
									
					// 查询用户的数据
					if (!($scope.customer.headimgurl)) {
						$scope.customer.headimgurl = "/img/my/index/icon_a.png";
					}
					
				}
		);
	}
	else {
		$scope.getUserCountInfo($scope.customer.id);
		
	}
	// 处理rank对应的图片
	if ($scope.customer.rank) {
		switch($scope.customer.rank) {
			case "1":$scope.customer.rankImg ="icon-j.png";break;
			case "2":$scope.customer.rankImg ="icon-i.png";break;
			case "3":$scope.customer.rankImg ="icon-k.png";break;
		}
	}
	
	
    $http.get("getMyWalletInfo/"+$scope.customer.id).success(function(data){
		
    	myWalletService.setByData(data);
    	
		$scope.totalScore = data.totalScore;
		
		$scope.cardCouponCount = data.cardCouponCount;
		
		$scope.publishCount = data.publishCount;
		
		console.log(data);	
	});
	// 跳转到的一些页面
	// 跳转到我的技能库
	$scope.toChild = function(){
		var customer=customerDataService.getCustomer();
		$state.go("index.myChildren",{customerId:customer.id}, {reload: true});
	};	
	// 跳转到我的卡包
	$scope.toWallet = function(){
		var customer=customerDataService.getCustomer();
		$state.go("index.cardList",{customerId:customer.id}, {reload: true});
	};	
	// 跳转到我的积分
	$scope.toScores = function(){
		var customer=customerDataService.getCustomer();
		$state.go("index.scoreDetail",{customerId:customer.id}, {reload: true});
	};	
	// 跳转到我的预约课程页面 
	$scope.toOrderList = function(viewTypeValue){
		var customer=customerDataService.getCustomer();
		$state.go("index.myorderList",{viewType:viewTypeValue}, {reload: true});
	};	
	//点击个人信息链接至个人信息修改页面
	$scope.toProfileList=function(){
		$state.go('index.myprofileList',{},{reload:true});
	};
	//跳转到状态页面
	$scope.toOrder=function(){
		$state.go('index.orderOrderList',{},{reload:true});
	};
});