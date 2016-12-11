ultcrm.controller('servicePayCtrl', function($scope,$stateParams,$location,$state,$window,$http,$ionicModal,$ionicPopup,$timeout,appointmentService) {
	var clickNum = 0;
	var bTimerDo = 0;
	var scheId = 0,classRoomId = 0;
	var classId = 0;
	var jsonStr = null;
	var roomName = null;
	var customerId = 0;
	var seatType = null;
	var openId = null;
	var timet = null;
	$scope.countTime = 0;
	$scope.countDown=function(sysSecond){
		// 如果秒数还是大于0，则表示倒计时还没结束		
	   		
		if(sysSecond > 1&& bTimerDo == 1){			
			var second = Math.floor(sysSecond % 30);//计算秒         
	    	$scope.countTime = second;
			sysSecond--;   
			// 一秒后重复执行 			
			timet = $timeout(function(){$scope.countDown(sysSecond);},1000);			
	    }else{			
			$scope.reChoice();			
	    }  
	};
	
	
// 获取订单信息
	$scope.getData=function(){
	    jsonStr = $stateParams.jsonStr;
		if(jsonStr != null){
			var jsonObj = eval("(" + jsonStr + ")");
			classId = (jsonObj["classId"]);
			var className = (jsonObj["className"]);
			var classDetail = (jsonObj["classDetail"]);
			var price = (jsonObj["price"]);
			var storeFullAddress = (jsonObj["storeFullAddress"]);
			var choiceSeat = (jsonObj["choiceSeat"]);
			var hiddenclassTimeDetail = (jsonObj["hiddenclassTimeDetail"]);
			
			scheId = (jsonObj["scheId"]);
			classRoomId = (jsonObj["classRoomId"]);
			roomName = jsonObj["roomName"];
			seatType = jsonObj["seatType"];
			openId = jsonObj["openId"];
			if(className != null && className != "undefined"){
				$scope.className = className;
			}
			if(classDetail != null && classDetail != "undefined"){
				if (classDetail=="null-null"){classDetail="具体时间待定";};
					
				$scope.classDetail = classDetail;					
			}
			if(price != null && price != "undefined"){
				$scope.priceOne = "￥" + price;
				$scope.totalPrice = "￥" + price;
				$scope.actualPrice = "￥" + price;
			}
			if(storeFullAddress != null && storeFullAddress != "undefined"){
				$scope.classAddress = storeFullAddress;
			}
			if(choiceSeat != null && choiceSeat != "undefined"){
				$scope.classSeat = choiceSeat;
			}
			if(hiddenclassTimeDetail != null && hiddenclassTimeDetail != "undefined"){
				$scope.hiddenclassTimeDetail = hiddenclassTimeDetail;
			}
			//查询小孩信息
			$scope.searchChild(openId);
		}
	};
	//支付失败时删除订单
	$scope.FailedOrder=function(orderId,seatId){
		if (orderId!=null)
		{
			$http.get('/delOrder/' + orderId + '/' + seatId).success(function(result) {
			});		      
		};
	};
	$scope.SendOrderMessage=function(orderId,str){
		if (orderId!=null)
		{
			$http.get('/getMessageTemplate/' + orderId + '/' + str).success(function(result) {
			}		      
			).error(function() {											
				alert("报名订单模板消息发送失败");	
			});
		};
	};	
	
	//重新选择座位
	$scope.reChoice=function(){
		if (bTimerDo == 1)
		{
			$timeout.cancel(timet);		
		}					
		$state.go('index.serviceSeat',{scheId:scheId,classId:classId,seatType:seatType,roomName:roomName,openId:openId},{reload:true});
	};
	$scope.VerifyOrderDataStatus=function(orderId){	
		$http.get('/getOrderInfo/'+orderId).success(function(data, status, headers, config){
			$scope.orderInfo.orderId = data.orderId;			
			$scope.orderInfo.classname = data.classname;			
			$scope.orderInfo.roomAddress = data.roomAddress;
			
			var tmpSeat = $stateParams.seatname;
			$scope.orderInfo.seatname = tmpSeat;
			$scope.orderInfo.startClassTime = data.classTime;
			$scope.orderInfo.classTimeDetail = data.classTimeDetail;			
			}).error(function(data, status, headers, config) {
			//发生错误，返回订单列表
			alert("获取课程订单信息失败");
				
		});
	};
	//立即支付
	 function pay(){
		if (bTimerDo == 1)
		{
			$timeout.cancel(timet);		
		}	 
		
		//禁用支付按钮
		var jsonObj = eval("(" + jsonStr + ")");
		var classId = jsonObj["classId"];//课程编号
		var className = jsonObj["className"];//课程名称
		var seatName = jsonObj["seatName"];//座位名称
		var choiceSeat = jsonObj["choiceSeat"];//座位名称
		var roomId = classRoomId;//教室编号
		var roomName = jsonObj["roomName"];//教室名称
		var scheuleId = scheId;//课时编号
		var price = jsonObj["price"];//单价
		var teachId = jsonObj["hiddenTeachId"];//教师编号
		var teachName = jsonObj["hiddenTeachName"];//教师名称
		var startTime = jsonObj["hiddenStartTime"];//开课时间
		var endTime = jsonObj["hiddenEndTimeTime"];//结课时间
		var hiddenclassTimeDetail = jsonObj["hiddenclassTimeDetail"];//开课具体时间

		var classHour = jsonObj["classHour"];//课时;
	
		var childNameSTR = $scope.hiddenChildName.name;

		var str = "{classId:" + classId + ",className:'" + className 
			+ "',seatName:'" + seatName + "',choiceSeat:'" + choiceSeat + "',roomId:" + roomId 
			+ ",roomName:'" + roomName + "',scheId:" + scheuleId + ",price:" + price 
			+ ",totalPrice:" + price + ",openId:'" + openId 
			+ "',teachId:" + teachId + ",teachName:'" + teachName 	
			+ "',startTime:'" + startTime + "',classHour:" + classHour + ",hiddenclassTimeDetail:'" + hiddenclassTimeDetail 
			+ "',childName:'" + childNameSTR +"',endTime:'" + endTime + "'}";
		$http.get('/createPayOrder/' + str).success(function(data) {
			var orderId = data.orderId;
			var seatId = data.seatId;			
			if(data.charge != null){
				pingpp.createPayment(data.charge, function(result, err) {
		    	    if (result=="success") {
		    	    	//发送通知						
						$scope.SendOrderMessage(orderId,str);
		    	    	$state.go('index.myorderList',{'viewType':'new'},{reload:true});												
		    	    } else {						
		    	    	$scope.FailedOrder(orderId,seatId);
		    	        alert(className+"支付失败");
		    	        //发送通知
						//$scope.sendMsg(orderId,openId,className,roomName,'2016-7-1');
                        //Robert Lee  Debug Basic Information 
						if (bTimerDo == 1)
						{
							$scope.reChoice();
						}else
						{
							clickNum = 0;
						}
		    	    }
				}, data.signature, false);
			}
			else{
				$scope.FailedOrder(orderId,seatId);
				
				alert("支付结果：" + data.msg);
				//Robert Lee  Debug Basic Information 
				if (bTimerDo == 1)
				{
					$scope.reChoice();
				}else
				{
					if ("该座位已被下单"==data.msg)
					{
						$scope.reChoice();
					}
					clickNum = 0;
				}
			}
        }).
        error(function() {
			$scope.FailedOrder(orderId,seatId);
        	alert("下单失败");
			//clickNum = 0;
			//Robert Lee  Debug Basic Information 
			if (bTimerDo == 1)
			{
				$scope.reChoice();
			}else
			{
				clickNum = 0;
			}
        	
        });
	};
	//发送通知
	$scope.sendMsg=function(id,openId,className,classAddr,datatime){
           //notifycationSuccess(orderId,openId,className,SeatSpec,startTime);		
	};
	//查询小孩信息
	$scope.searchChild=function(openId){
		//customerId = result.customerId;
		$http.get('/searchCutomerId/' + openId).success(function(result) {
			if(result != 0){
					customerId = result;						
				}
				else{
					alert("查询用户失败");
				}
			}).error(function() {											
			alert("查询用户失败");	
			
		});
		
		
		$http.get('/searchChild/' + openId).success(function(result) {
			if(result != null){
				$scope.contactPhone = result.contactPhone;
				if(result.childs != null && result.childs.length > 0){
					$scope.childList=result.childs;
					$scope.childList=[];
					for(var i=0,length=result.childs.length;i<length;i++){  					
						$scope.childList[i] = result.childs[length-i-1];
					}
					$scope.selectedChild = $scope.childList[0];
					$scope.hiddenChildName = $scope.selectedChild;
					customerId = result.customerId;					
				}
				else{
					$scope.childList = [];
				}
			}
			
		}).error(function() {	
			alert("查询小孩失败");
			$scope.contactPhone = "";
			$scope.childList = [];
		});
	};
	$scope.toMy=function(){
		if (bTimerDo == 1)
		{
			$timeout.cancel(timet);		
		}	
		clickNum = 0;
		//$state.go('index.myprofileList',{}, {reload: true}); 
		$state.go('index.modifyPhone',{customerId:customerId,str:null,jsonStr:jsonStr}, {reload: true}); 
	};
	$scope.toMyChild=function(){
		if (bTimerDo == 1)
		{
			$timeout.cancel(timet);		
		}		
		clickNum = 0;	
		$state.go('index.newChild',{customerId:customerId,str:null,jsonStr:jsonStr}, {reload: true}); 
	};	
	$scope.changeChild=function(childName){
		$scope.hiddenChildName = childName;
	};
	$scope.toPay=function(){
		clickNum += 1;
		if(clickNum > 1){
			return;
		}
		else{
			var childName = $scope.hiddenChildName;
			//若手机号为空或者未选择学院信息，停止定时器
			if($scope.contactPhone == null || $scope.contactPhone == ""
					|| childName == null){
				if($scope.contactPhone == null || $scope.contactPhone == ""){
					alert("请绑定联系电话");
				}
				else if(childName == null){
					alert("请输入学员姓名！");
				}								
				clickNum = 0;				
			}
			else{
				clickNum = 1;	
				pay();
				//开启支付
			}			
		}
	};
	$scope.getData();
	if (bTimerDo == 1)
	{
		$scope.countDown(30);	
	}
	
});
