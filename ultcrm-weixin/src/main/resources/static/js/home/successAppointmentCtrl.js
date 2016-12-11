/**
 * 预约成功Controller
 */
ultcrm.controller('successAppointmentCtrl', function($scope, $state){
	
	//进入领取卡券
	$scope.getCard = function(){
		$state.go("index.getCard",{}, {reload: true}); 
	}
});