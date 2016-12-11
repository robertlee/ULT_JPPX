ultcrm.controller('newChildCtrl', function($scope,$state,$stateParams,$http) {
	//传递参数
	var str = $stateParams.str;
	var type = "new";
	if(str != null && str != "undefined"){
		var jsonObj = eval("(" + str + ")");
		$scope.hiddenChildId = jsonObj["childId"];
		type = jsonObj["type"];
		angular.element(document.querySelector('#childName')).val(jsonObj["childName"]);
		angular.element(document.querySelector('#year')).val(jsonObj["year"]);
		angular.element(document.querySelector('#month')).val(jsonObj["month"]);
	}
	
	var customerId = $stateParams.customerId;
	$scope.reBack = function() {
		$state.go('index.myindex',{},{reload:true});
	};	
	// 保存数据并且返回
	$scope.saveData = function() {
		var childName = angular.element(document.querySelector('#childName')).val();
		var year = angular.element(document.querySelector('#year')).val();
		var month = angular.element(document.querySelector('#month')).val();
		if (childName == null || childName == "") {
			alert("没有小孩姓名，请重新输入！");
		}
		
		var childId = $scope.hiddenChildId;
		if(childId == null){
			childId = 0;
		}
		$http.get("/createChildForCustomer/" + customerId +"/" + childId + "/" 
				+ childName + "/" + year + "/" + month + "/" + type).success(function(data){
			// 如果返回了空的字符串， 表示没有添加成功，有重复的添加行为
		    if (data == "" || data == null) {
		    	alert("该小孩信息输入错误，请确认！");
		    	$scope.clicking = false;
		    	return;
		    }
		    else if(data == "0"){
		    	if(type == "new"){
		    		alert("新增失败");
					console.error("Add Child failed!");
		    	}
		    	else if(type == "edit"){
		    		alert("编辑失败");
					console.error("Edit Child failed!");
		    	}
		    }
		    else if(data == "1"){
		    	if(type == "new"){
		    		//alert("新增成功");
					console.log("Add Child successfully!");
		    	}
		    	else if(type == "edit"){
					//alert("编辑成功");
					console.log("Edit Child successfully!");
		    		
		    	}
		    }
			if ($stateParams.jsonStr==null)
			{
				$state.go('index.myChildren',{customerId:customerId},{reload:true});
			}
			else
			{
				$state.go('index.servicePay',{jsonStr:$stateParams.jsonStr},{reload:true});	
			}

		    
		});
	};
});