ultcrm.controller('cardCouponDetailCtrl', function($scope,$http,$state,$stateParams,$ionicPopup,$timeout,customerDataService,appointmentService) {
	
	$scope.id = $stateParams.id;
	$scope.batch = {};
	$scope.consumeList = new Array();
	$scope.from = $stateParams.from;
	console.log($stateParams);
	$scope.orderId = $stateParams.orderId;
	
	$scope.canReceive = true;
	
	// 通过orderId判断是否已经领取了
	if (!$stateParams.orderId) {
		$scope.canReceive = false;
	}
	
	$http.get("/getCardCouponDetail/"+$stateParams.type+"/"+$stateParams.id).success(function(data){
		if (data) {
			$scope.batch = data.batch?data.batch:{};
			$scope.consumeList = data.consumeList?data.consumeList:[];
		}
	});
	
	// 导航
	$scope.toDirect = function(){
		$state.go("index.direct"); 
	};
	
	// 领取优惠券
	$scope.receive = function() {
		// 先判断是否可以领取
		if ($scope.canReceive == null) {
			$http.get("/checkAppointmentCouponSent/"+$stateParams.orderId,{}).success(function(data){
				//$scope.canReceive = !data;
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
			if (data.result == "success") {
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