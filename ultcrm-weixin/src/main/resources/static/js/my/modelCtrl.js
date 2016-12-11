ultcrm.controller('modelCtrl', function($scope,$state,$http,$stateParams,customerDataService,courseList,newTech) {
	
	$scope.courseList = courseList;
	// 第一个course
	$scope.selectedCourse = courseList[0];
	
	// 首先从后台获取品牌列表
	$http({method:"GET",url:"/getTechModelByCourseId/"+$scope.selectedCourse.id, cache: true}).success(function(data){
		$scope.groups = data;
	});
	
	 $scope.toggleGroup = function(group) {
		    if ($scope.isGroupShown(group)) {
		      $scope.shownGroup = null;
		    } else {
		      $scope.shownGroup = group;
		    }
	 };
		
	 $scope.isGroupShown = function(group) {
	    return $scope.shownGroup === group;
	 };
	  
	  $scope.selectCourse = function(course) {
		  if ($scope.selectedCourse ==course) {
			  return;
		  }
		  $scope.selectedCourse = course;
		  
		  //重新加载数据
		  $http({method:"GET",url:"/getTechModelByCourseId/"+$scope.selectedCourse.id, cache: true}).success(function(data){
			    $scope.groups = data;
		  });
	  }
	  
	  $scope.selectModel = function(model,sery) {
		  	newTech.customerId = customerDataService.getCustomerId();
		  	
		  	// 添加参数
	  		newTech.courseId= $scope.selectedCourse.id;
	  		newTech.courseName=$scope.selectedCourse.name;
	  		newTech.seryId=sery.id;
	  		newTech.seryName=sery.seryName;
	  		newTech.modelId=model.id;
	  		newTech.modelName=model.name;
		    if (!$stateParams.fromPage || $stateParams.fromPage=="newTech" ) {
		  		$state.go('index.newTech',
					  {
				  		courseId:$scope.selectedCourse.id,
					    courseName:$scope.selectedCourse.name,
					    seryId:sery.id,
					    seryName:sery.seryName,
					    modelId:model.id,
					    modelName:model.name
					  }
			  		,{reload:true});
		    }
		    else {
		    	$state.go('index.tech',
						  {techId:newTech.id}
				  		,{reload:true});
		    }
	  }
	
});