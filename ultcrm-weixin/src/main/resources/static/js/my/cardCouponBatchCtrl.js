// 优惠券或者卡的批次页面
ultcrm.controller('cardCouponBatchCtrl', function($scope,$http,$state,$ionicPopup,$timeout,$stateParams,customerDataService,appointmentService) {

	var screenWidth = document.documentElement.clientWidth;
	$scope.cardRect = {
		width: screenWidth - 30,
		height: (screenWidth - 30) * 0.345	//根据背景图片比例计算来的
	} 
	
	$scope.batch = {};
	$scope.card = {};
	$scope.coupon = {};
	$scope.canReceive = true;
	$scope.orderId = $stateParams.orderId;
	
	$scope.isCard = false;
	$scope.isCoupon = false;
	
	// 通过orderId判断是否已经领取了
	if (!$stateParams.orderId) {
		$scope.canReceive = false;
	}
	
	// 获取参数
	$http.get("/getCardCouponBatchByOrderId/"+$stateParams.orderId,{}).success(function(data) {
		$scope.batch = data;
		if (data.subType =="cardBatch") {
			$scope.card = data;
			$scope.isCard = true;
		}
		else {
			$scope.coupon = data;
			$scope.isCoupon = true;
		}
	});
	
	// 查看优惠券批次详情
	$scope.toDetail = function() {
		$state.go("index.cardCouponDetail",{type:$scope.batch.subType,id:$scope.batch.id,orderId:$scope.orderId}, {reload: true});
	}
	
	// 导航
	$scope.toDirect = function() {
		$state.go("index.direct"); 
	}
	
	// 领取优惠券
	$scope.receive = function() {
		// 先判断是否可以领取
		if ($scope.canReceive == null) {
			$http.get("/checkAppointmentCouponSent/"+$stateParams.orderId,{}).success(function(data){
				
				if (data == "false" || data == false) {
					$scope.canReceive = true;
				}
				else {
					$scope.canReceive = false;
				}
				
				if ($scope.canReceive) {
					$scope.toReceive();
				}
				else {
	    		  	var alertPopup = $ionicPopup.show({
	   			   		cssClass: 'popup-custom popup-show',
	   			   		title: '<span class="iconfont icon-alert orange"></span>领取失败,已经被领取！'
	    		   });
	    		   $timeout(function() {
	    			   alertPopup.close(); 
	    			}, 1500);
				}
			});
		}
		else {
			if ($scope.canReceive) {
				$scope.toReceive();
			}
			else {
    		  	var alertPopup = $ionicPopup.show({
   			   		cssClass: 'popup-custom popup-show',
   			   		title: '<span class="iconfont icon-alert orange"></span>领取失败,已经被领取！'
    		   });
    		   $timeout(function() {
    			   alertPopup.close(); 
    			}, 1500);
			}
		}
	}
	
	$scope.toReceive = function() {
		var orderId = $stateParams.orderId;
		$http.get("/receiveCoupon/"+orderId+"/"+customerDataService.getCustomerId(),{}).success(function(data){
			//alert(data+","+data.result+",customer is "+customerDataService.getCustomerId());
			if ( data.result == "success") {
				//领取成功
    		  	var alertPopup = $ionicPopup.show({
   			   		cssClass: 'popup-custom popup-show',
   			   		title: '<span class="iconfont icon-confirm blue"></span>领取优惠券成功！'
    		   });
    		   $timeout(function() {
    			   alertPopup.close(); 
    			}, 1500);
				alertPopup.then(function(res) {
				    // 页面跳转到优惠券列表页面
					$state.go("index.cardList",{}, {reload: true});
				});
			}
			else if (data.result =="duplicate") {
    		  	var alertPopup = $ionicPopup.show({
   			   		cssClass: 'popup-custom popup-show',
   			   		title: '<span class="iconfont icon-alert orange"></span>领取失败,已经被领取！'
    		   });
    		   $timeout(function() {
    			   alertPopup.close(); 
    			}, 1500);
			}
			else {
				//alert(data+","+data.result);
				// 领取失败
    		  	var alertPopup = $ionicPopup.show({
   			   		cssClass: 'popup-custom popup-show',
   			   		title: '<span class="iconfont icon-alert orange"></span>领取失败,已经被领取！'
    		   });
    		   $timeout(function() {
    			   alertPopup.close(); 
    			}, 1500);
			}
		});
	}
});