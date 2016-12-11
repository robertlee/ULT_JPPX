//
ultcrm.controller('scoreDetailCtrl', function($scope,$http,$location,$ionicLoading,$state,$ionicPopup,$window,$ionicModal,$ionicSlideBoxDelegate,appointmentService,customerDataService) {
	//设置标题栏分数，如果为null设置为0
	function setTotalScore(value){
		$scope.score = (value ? value : 0)  +  "分";
	}
	
	//所有车辆的积分详情
	$scope.scoreDetailItems = [];
	
	// 跳转到积分详情页面
	$scope.toScoreDetail = function() {
		$state.go("index.myindex",{}, {reload: true});
	}
	
	// 跳转到卡券列表页面
	$scope.toCardList = function() {
		$state.go("index.myindex",{}, {reload: true});
	}
				
	//返回按钮跳转回我的钱包页面
	$scope.toWallet=function(){
		$state.go("index.wallet",{},{reload:true});
	};

	//标题栏状态
	$scope.techlevelno = "";
	$scope.score = "";
	$scope.leftArrowState = {show:false};
	$scope.rightArrowState = {show:false};
	
	//页面改变后，标题也随着改变
	$scope.slideHasChanged = function(index){
		
		//当前为第一页时，左箭头隐藏，当前页为最后一页时，右侧箭头隐藏
		$scope.leftArrowState.show = (index > 0);
		$scope.rightArrowState.show = (index < $scope.scoreDetailItems.length - 1);

		$scope.techlevelno = $scope.scoreDetailItems[index].techlevelno;
		setTotalScore($scope.scoreDetailItems[index].totalScore);
	}
	
	//标题栏按钮左右翻页
	$scope.nextSlide = function(){
		$ionicSlideBoxDelegate.next();		
	}
	
	$scope.previousSlide = function(){
		$ionicSlideBoxDelegate.previous();		
	}

	//刷新页面数据
	$http.get('/getScoreDetail/' + (customerDataService.getCustomer().id)).success(
		function(data){
			$scope.scoreDetailItems = data;
			//暂无积分
			if(data.length == 0){
				$scope.techlevelno = "您暂时未开通积分功能";
				$scope.score = "";
				//展示一个页面，显示无积分信息
				$scope.scoreDetailItems = [{scoreItems:[]}];;
			}
			
			//默认显示第一条数据的车牌号和积分
			if(data.length > 0){
				$scope.techlevelno = data[0].techlevelno;
				setTotalScore(data[0].totalScore);
			}
			
			//如果超过一条数据，显示右侧箭头
			if(data.length > 1){
				$scope.rightArrowState.show = true;
			}
			
			//数据更新后必须update视图
			$ionicSlideBoxDelegate.update();
		}
	).error(function(data,header,config,status){
		alert("获取信息失败");
	});
});