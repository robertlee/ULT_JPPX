ultcrm.controller('serviceStoreCtrl', function($scope,$http,$state,$stateParams) {
	var classId = 0;//课程编号
	$scope.searchSchedule=function(){
		classId = $stateParams.id;
		var typeId = $stateParams.typeId;
		var status = $stateParams.status;
		var addressId = $stateParams.addressId;
		$http.get('/getBusinessBy/' + classId + '/' + addressId + '/' + typeId + '/' + status).success(function(result) {
        	//先清空原始数据
        	$scope.choice_1 = "";
        	if(result.scheList != null && result.scheList.length > 0){
        		$scope.storeName = result.scheList[0].store;
				switch  ($scope.storeName){
					case "孺子路": $scope.mobile="18942231850";break;
					case "中山路少年宫":$scope.mobile="13367006212";break;
					case "朝阳分部":$scope.mobile="18679135437";break;
					case "梵顿公馆":$scope.mobile="18679135437";break;
					case "高新少年宫":$scope.mobile="18942231850";break;
					default:$scope.mobile="18942231850";break;
				}
        		$scope.courseName = result.scheList[0].className;
				$scope.choice_1 = result.scheList;
	    	}
        }).
        error(function() {
        	$scope.choice_1 = "";
        });
	};
	$scope.choiceSeat=function(scheId,seatType,roomName,storeAddress){
		var openId = $stateParams.openId;
		$state.go('index.serviceSeat',{scheId:scheId,classId:classId,seatType:seatType,roomName:roomName,openId:openId},{reload:true});
//		if(seatType == "A"){
//			$state.go('index.serviceSeatA',{scheId:scheId,classId:classId,seatType:seatType,roomName:roomName,openId:openId},{reload:true});
//		}
//		else if(seatType == "B"){
//			$state.go('index.serviceSeatB',{scheId:scheId,classId:classId,seatType:seatType,roomName:roomName,openId:openId},{reload:true});
//		}
//		else if(seatType == "C"){
//			$state.go('index.serviceSeatC',{scheId:scheId,classId:classId,seatType:seatType,roomName:roomName,openId:openId},{reload:true});
//		}
//		else if(seatType == "D"){
//			$state.go('index.serviceSeatD',{scheId:scheId,classId:classId,seatType:seatType,roomName:roomName,openId:openId},{reload:true});
//		}
//		else if(seatType == "E"){
//			$state.go('index.serviceSeatE',{scheId:scheId,classId:classId,seatType:seatType,roomName:roomName,openId:openId},{reload:true});
//		}
//		else if(seatType == "F"){
//			$state.go('index.serviceSeatF',{scheId:scheId,classId:classId,seatType:seatType,roomName:roomName,openId:openId},{reload:true});
//		}
//		else if(seatType == "G"){
//			$state.go('index.serviceSeatG',{scheId:scheId,classId:classId,seatType:seatType,roomName:roomName,openId:openId},{reload:true});
//		}
	};
	//查询课程安排
	$scope.searchSchedule();
});