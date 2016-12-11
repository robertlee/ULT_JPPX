ultcrm.controller('myChildrenCtrl', function($scope,$http,$location,$state,$stateParams,$window,customerDataService) {
	//获取用户相关信息
	var customerId = $stateParams.customerId;
	$scope.hiddenCustomerId = customerId;
	$scope.childrenList = [];
	$http.get("/findChildrenByCustomer/" + customerId).success(function(result){
		$scope.childrenList = result;
	});
	// 选择儿童	
	$scope.toAddChild = function() {
		$state.go('index.newChild',{customerId:$scope.hiddenCustomerId},{reload:true});
	};	
	$scope.isSelected = function(tech) {
		return true;
	};
	//编辑小孩信息
	$scope.editChild = function(childId,childName,year,month){
		var str = "{childId:" + childId + ",childName:'" + childName 
		+ "',year:'" + year + "',month:'" + month + "',type:'edit'}";
		$state.go('index.newChild',{customerId:$scope.hiddenCustomerId,str:str},{reload:true});
	};
	$scope.deleteChild = function(childId,childName,year,month){
		var type="edit";
		$http.get("/createChildForCustomer/1/" + childId + "/" 
				+ childName + "/" + year + "/" + month + "/" + type).success(function(data){
			// 如果返回了空的字符串， 表示没有添加成功，有重复的添加行为
		    if(data == "0"){
		    		alert("编辑失败");
					console.error("Edit Child failed!");
		    }
		    else if(data == "1"){					
					console.log("Edit Child successfully!");
					$http.get("/findChildrenByCustomer/" + customerId).success(function(result){
						$scope.childrenList = result;
					});
		    }		    
		});		
	};	
});