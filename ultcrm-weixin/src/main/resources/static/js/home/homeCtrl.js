//home页面的控制器
ultcrm.controller('homeCtrl', function($scope,$http,$state,$stateParams,$window,$location,$sanitize,$ionicPopup,$ionicSlideBoxDelegate,appointmentService,customerDataService) {
	
	$http.get('/getAdvertiseList').success(function(result) {    	
		$scope.activityList=[];    	
		var i=0;
    	for (var item in result)	
		{
			$scope.activityList[i]={template:"<div class='ad-text0'>"+result[i].title+"</div>"};
			i++;
		}
    	
    }).
    error(function() {
    	$scope.activityList = [];//清空select
    });	

	
	
	
	//$scope.activityList=[{template:"<div class='ad-text0'>2016年8月28日——2016年9月30日止</div>"},
	//                     {template:"<div class='ad-text0'>南昌乐天作文培训月卡火热销售中</div>"},
	//                     {template:"<div class='ad-text0'>2016年9月30日——2016年10月30日止</div>"}];	
	$scope.toActivityDetail=function(index){
		console.log(index);
		$state.go('index.activityDetail',{id:index},{reload:true});
	};
	

});

