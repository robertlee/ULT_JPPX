ultcrm.controller('serviceSeatCtrl', function($scope,$state,$stateParams,$window,$http,$ionicModal,
			appointmentService,appointmentBusinessType) {
	var scheId = 0;
	var seatName = null;
	var hiddenEndTimeTime = null;
	var roomName = $stateParams.roomName;
	var classId = $stateParams.classId;
	var seatType = $stateParams.seatType;
	var classHour = 0;
	$scope.sure_seat = "sure_seat";
	var seatImg1 = "../../img/course/seat_01.jpg";
	var seatImg2 = "../../img/course/seat_02.jpg";
	var seatImg3 = "../../img/course/seat_03.jpg";
	
	if(seatType == "A"){
		$scope.X=[{num:1},{num:2},{num:3},{num:4}];
		$scope.Y=[{num:1},{num:2},{num:3},{num:4},{num:0},{num:5},{num:6},{num:7},{num:8}];
	}
	else if(seatType == "B"){
		$scope.X=[{num:1},{num:2},{num:3},{num:4}];
		$scope.Y=[{num:1},{num:2},{num:3},{num:0},{num:4},{num:5},{num:6}];
	}
	else if(seatType == "C"){
		$scope.X=[{num:1},{num:2},{num:3},{num:4},{num:5}];
		$scope.Y=[{num:1},{num:2},{num:3},{num:0},{num:4},{num:5},{num:6}];
	}
	else if(seatType == "D"){
		$scope.X=[{num:1},{num:2},{num:3},{num:4}];
		$scope.Y=[{num:1},{num:2},{num:3},{num:0},{num:4},{num:5}];
	}
	else if(seatType == "E"){
		$scope.X=[{num:1},{num:2},{num:3},{num:4},{num:5}];
		$scope.Y=[{num:1},{num:2},{num:0},{num:3},{num:4}];
	}
	else if(seatType == "F"){
		$scope.X=[{num:1},{num:2},{num:3}];
		$scope.Y=[{num:1},{num:2},{num:3},{num:0},{num:4},{num:5},{num:6},{num:7}];
	}
	else if(seatType == "G"){
		$scope.X=[{num:1},{num:2},{num:3},{num:4},{num:5},{num:6}];
		$scope.Y=[{num:1},{num:2},{num:3},{num:0},{num:4},{num:5},{num:6}];
	}
	
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
		document.getElementById("closeWindow").addEventListener(
				"click",function(){
					WeixinJSBridge.invoke("closeWindow",{},function(e){});
				},!1);
	});
	
	//展示课程信息
	$scope.showSchedule=function(){
		scheId = $stateParams.scheId;
		$http.get('/getScheduleBy/' + scheId).success(function(result) {
        	//先清空原始数据
        	$scope.className = "";
        	$scope.classDetail = "";
        	$scope.hiddenPrice = "";
	    	if(result != null){
	    		if(result.schedule != null){
	    			$scope.hiddenPrice = result.schedule.price;
	    			$scope.className = result.schedule.className;
	    			$scope.hiddenRoomId = result.schedule.classRoomId;
	    			$scope.classDetail = result.schedule.startClassTime+ "-"+ result.schedule.endClassTime+ "   " + result.schedule.teacherName;
	    			$scope.hiddenStartTime = result.schedule.startClassTime;
	    			hiddenEndTimeTime = result.schedule.endClassTime;
	    			$scope.hiddenTeachId = result.schedule.teachId;					
	    			$scope.hiddenTeachName = result.schedule.teacherName;
	    			$scope.hiddenclassTimeDetail = result.schedule.classTimeDetail;
					classHour = result.schedule.classHour;
					$scope.storeFullAddress = result.schedule.classRoomName;
	    		}
	    		
	    		//展示可选座位
	    		if(result.seatList != null && result.seatList.length > 0){
	    			for(var j = 0; j < result.seatList.length; j++){
    					var tmpImg = angular.element(document.querySelector("#itemy" + result.seatList[j].name));
    					tmpImg.attr("src",seatImg3);
    					tmpImg.attr("ng-click","");
		    		}
	    		}
	    	}
        }).
        error(function() {
        	$scope.className = "";
        	$scope.classDetail = "";
        	$scope.hiddenPrice = "";
        	$scope.seatTable = null;
        });
	};
	//展示选中座位的位置
	$scope.check_choice=function(seat,obj){
		var reg = /\d+/g;
		var tmpStr = seat.match(reg);
		var tmpY = 0;
		if(seatType == "A"){
			tmpY = (parseInt(tmpStr[0])-1)*8 + parseInt(tmpStr[1]);
		}
		else if(seatType == "B" || seatType == "C" || seatType == "G"){
			tmpY = (parseInt(tmpStr[0])-1)*6 + parseInt(tmpStr[1]);
		}
		else if(seatType == "D"){
			tmpY = (parseInt(tmpStr[0])-1)*5 + parseInt(tmpStr[1]);
		}
		else if(seatType == "E"){
			tmpY = (parseInt(tmpStr[0])-1)*4 + parseInt(tmpStr[1]);
		}
		else if(seatType == "F"){
			tmpY = (parseInt(tmpStr[0])-1)*7 + parseInt(tmpStr[1]);
		}
		
		var str = parseInt(tmpStr[0]) + "排" + tmpY + "号";
		//修改选中座位的图片
		var tmpnow = angular.element(document.querySelector("#itemy" + obj));
		if(tmpnow.attr("ng-click") != null && tmpnow.attr("ng-click") != ""){
			var temp = $scope.show_seat;
			if(temp != null && temp.trim() != ""){
			    var tmp = temp.match(reg);
			    //修改上次选中座位的图片
			    var numY = 0;
			    if(seatType == "A"){
			    	numY = parseInt(tmp[1]) - (parseInt(tmp[0])-1)*8;
				}
				else if(seatType == "B" || seatType == "C" || seatType == "G"){
					numY = parseInt(tmp[1]) - (parseInt(tmp[0])-1)*6;
				}
				else if(seatType == "D"){
					numY = parseInt(tmp[1]) - (parseInt(tmp[0])-1)*5;
				}
				else if(seatType == "E"){
					numY = parseInt(tmp[1]) - (parseInt(tmp[0])-1)*4;
				}
				else if(seatType == "F"){
					numY = parseInt(tmp[1]) - (parseInt(tmp[0])-1)*7;
				}
			    var tmpold = angular.element(document.querySelector("#itemy" + tmp[0] + '-' + numY));
			    tmpold.attr("src",seatImg1);
			    $scope.show_seat = "";
			}
			if(str != temp){
				tmpnow.attr("src",seatImg2);
				seatName = obj;
				$scope.show_seat = str;
				$scope.hiddenShowSeat = seat;
				$scope.sure_seat = "sure_seat seat_change_color";
			}
		}
	};
	//确认选座
	$scope.choice_seat=function(){
		var temp = $scope.show_seat;
		if(temp == null || temp.trim() == ""){
			alert("您好！请先选择上课您想要的座位!");
			return;
		}
		var jsonStr = "{";
		var className = $scope.className;
		var classDetail = $scope.classDetail;
		var price = $scope.hiddenPrice;
		var storeFullAddress = $scope.storeFullAddress;
		var hiddenTeachId = $scope.hiddenTeachId;
		var hiddenTeachName = $scope.hiddenTeachName;
		var hiddenStartTime = $scope.hiddenStartTime;
		var hiddenclassTimeDetail = $scope.hiddenclassTimeDetail;	
		 
		if(className != null && className != ""){
			jsonStr += "classId:" + classId + ",className:'" + className + "',";
		}
		if(classDetail != null && classDetail != ""){
			classDetail = classDetail.substr(0,classDetail.indexOf("   "));
			jsonStr += "classDetail:'" + classDetail + "',";
		}
		if(price != null && price != ""){
			jsonStr += "price:" + price + ",";
		}
		if(storeFullAddress != null && storeFullAddress != ""){
			jsonStr += "storeFullAddress:'" + storeFullAddress + "',";
		}
		if(hiddenTeachId != null && hiddenTeachId != ""){
			jsonStr += "hiddenTeachId:'" + hiddenTeachId + "',";
		}
		if(hiddenTeachName != null && hiddenTeachName != ""){
			jsonStr += "hiddenTeachName:'" + hiddenTeachName + "',";
		}
		if(hiddenStartTime != null && hiddenStartTime != ""){
			jsonStr += "hiddenStartTime:'" + hiddenStartTime + "',";
		}
		if(hiddenEndTimeTime != null && hiddenEndTimeTime != ""){
			jsonStr += "hiddenEndTimeTime:'" + hiddenEndTimeTime + "',";
		}
		if(hiddenclassTimeDetail != null && hiddenclassTimeDetail != ""){
			jsonStr += "hiddenclassTimeDetail:'" + hiddenclassTimeDetail + "',";
		}
		
		var roomId = $scope.hiddenRoomId;
		var openId = $stateParams.openId;
		jsonStr += "choiceSeat:'" + temp + "',seatName:'" + seatName + "',scheId:" 
			+ scheId + ",classRoomId:" + roomId + ",roomName:'" + roomName 
			+ "',classHour:" + classHour + ",seatType:'" + seatType 
			+ "',storeFullAddress:'" + storeFullAddress + "',openId:'" + openId + "'}";
		$state.go('index.servicePay',{jsonStr:jsonStr},{reload:true});
	};
	
	//展示课程信息
	$scope.showSchedule();
});