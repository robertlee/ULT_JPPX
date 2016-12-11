/**
 * 领取卡券Controller
 */
ultcrm.controller('getCardCtrl', function($scope){
	$scope.isFront = true;
	
	//翻页
	$scope.turnOver = function(){
		$scope.isFront = !$scope.isFront;
	}
});