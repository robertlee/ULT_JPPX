ultcrm.controller('cardListCtrl',function($scope,$state,$http,$stateParams,appointmentService,customerDataService,myWalletService){
	
	var screenWidth = document.documentElement.clientWidth;
	
	/*初始化数据*/
	$scope.couponList = [];
	$scope.cardList = [];
	$scope.publishCount = 0;
	$scope.expiredCount = 0;
	
	$scope.hasPublishItem = true;
	$scope.hasExpiredItem = true;
	
	$scope.cardRect = {
		width: screenWidth - 30,
		height: (screenWidth - 30) * 0.345	//根据背景图片比例计算来的
	}
	
	//获取从钱包获取客户id
	var customer= customerDataService.getCustomer();
	$scope.customerId=customer.id;
	console.log($scope.customerId);
	//获取钱包中可使用，已失效的数量
	var walletData=myWalletService.getByData();
	$scope.publishCount = walletData.publishCount || 0;
	$scope.expiredCount = (walletData.writeoffCount + walletData.cancelCount) || 0;
		
	//初始化可使用、已失效的显示效果
	$scope.useableActive=true;
	$scope.expiredActive=false;
	//点击可使用
	$scope.viewUseable=function(){
		$scope.useableActive=true;
		$scope.expiredActive=false;
	};
	//点击已失效
	$scope.viewExpired=function(){
		$scope.useableActive=false;
		$scope.expiredActive=true;
	};
	//左右滑动切换
	//可使用向左滑动切换至已失效
	$scope.useableSwipeLeft=function(){
		$scope.useableActive=false;
		$scope.expiredActive=true;
	};
	
	//已失效向右滑动切换至可使用
	$scope.expiredSwipeRight=function(){
		$scope.useableActive=true;
		$scope.expiredActive=false;
	};


	
	//获取卡的List集合
	$http.get('/getCardByCustomerId/'+$scope.customerId).success(function(data){
		$scope.cardList=data;
		console.log('get cards success');
		console.log($scope.cardList);
		addCardList();
	}).error(function(data){
		console.log('get cards error');
	});
	
	
	//获取优惠券的List集合
	$http.get('/getCouponByCustomerId/'+$scope.customerId).success(function(data){	
		$scope.couponList=data;
		console.log('get coupons success');	
		console.log($scope.couponList);
		addCouponList();
	}).error(function(data){
		console.log('get coupons error');
	});
	
	//刷新数量
    $http.get("getMyWalletInfo/" + $scope.customerId).success(function(data){
		
    	myWalletService.setByData(data);
		
		$scope.cardCouponCount = data.cardCouponCount;
		
		$scope.publishCount = data.publishCount;
		
		$scope.expiredCount = data.writeoffCount + data.cancelCount;
		
		//转换成bool类型
		$scope.hasPublishItem=!!$scope.publishCount;
		$scope.hasExpiredItem=!!$scope.expiredCount;
	});
	
	var addCardList=function(){
		//cardlist非空才执行函数
		if($scope.cardList != null){
			for(var i  in $scope.cardList){
				//显示总次数和剩余次数
				if($scope.cardList[i].totalCount==999){		
					$scope.cardList[i].totalCount='不限';
					$scope.cardList[i].remainCount='不限';
				}else{
					$scope.cardList[i].remainCount=($scope.cardList[i].totalCount-$scope.cardList[i].usedCount)+'次';
				}
				
				switch($scope.cardList[i].status){
					case '002':
						$scope.cardList[i].state=1;
						break;
					case '003':
						$scope.cardList[i].state=2;
						$scope.cardList[i].isUsed=true;
						$scope.cardList[i].isExpired=false;
						break;		
					case '005':
						$scope.cardList[i].state=2;
						$scope.cardList[i].isUsed=false;
						$scope.cardList[i].isExpired=true;
						break;
					default:
						break;
				}	
			}
		}
	};

	var addCouponList=function(){
		//优惠券列表非空才执行函数
		if($scope.couponList != null){
			for(var i  in $scope.couponList){
				//对金额进行4舍5入
				$scope.couponList[i].amount=Math.round($scope.couponList[i].amount);

				switch($scope.couponList[i].status){
					case '003':
						$scope.couponList[i].state=1;
						break;
					case '004':
						$scope.couponList[i].state=2;
						$scope.couponList[i].isUsed=true;
						$scope.couponList[i].isExpired=false;
						break;		
					case '006':
						$scope.couponList[i].state=2;
						$scope.couponList[i].isUsed=false;
						$scope.couponList[i].isExpired=true;
						break;
					default:
						break;
				}
				
			}
		}
	};
	
	//状态导航到卡券详情
	$scope.toCardDetail=function(card){
		$state.go('index.cardCouponDetail',{type:'card',id:card.id},{reload:true});
	};
	$scope.toCouponDetail=function(coupon){
		$state.go('index.cardCouponDetail',{type:'coupon',id:coupon.id},{reload:true});
	};
	
	
});
