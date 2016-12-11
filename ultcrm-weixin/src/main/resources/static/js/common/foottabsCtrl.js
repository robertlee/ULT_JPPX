
ultcrm.controller('foottabsCtrl', function($scope,$ionicHistory, $location,$http,customerDataService) {

	
	$scope.clearTabsHistory = function() {
	     $ionicHistory.clearHistory();
	}

});