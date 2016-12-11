ultcrm.controller('orderProcessDetailCtrl', function($scope,$http,$state,$stateParams,$ionicModal,$window,orderDataService,customerDataService) {
	
	
	$scope.headimgurl = "/img/my/index/icon_i.png";
	$scope.fivestarurl= "/img/my/orderDetail/fivestar.png";
 
	 
	console.log("enter process detial");
	
	//$scope.initDetail = function() {
	$scope.orderId = null;
	   
	   if ($stateParams.orderId) {
		   $scope.orderId = $stateParams.orderId;
	   }
	   else {
		   $scope.orderId = orderDataService.getViewOrderId();
	   }
       
	   $http.get("/getOrderStatusData/" + $scope.orderId)

		  .success(function(data){
			 // alert("my order ok!")
			  if (data ) {
				 $scope.orderProcess = data.orderProcess;
				 $scope.enterStore = data.enterStore;
				 if($scope.enterStore != null)
					 {
					     if($scope.saNames[$scope.enterStore.saName] != null)
					    	{
					    	     $scope.enterStore.saNameImg = $scope.saNames[$scope.enterStore.saName];
					    	}else{
					    		$scope.enterStore.saNameImg = "defaultworker";
					    	} 
					    	 
						 if($scope.enterStore.happenStatus == '-1')
						 {
						    $scope.enterStore.backimageurl = "/img/my/orderDetail/entered.png";
						 }else if($scope.enterStore.happenStatus == '0')
						{
						    $scope.enterStore.backimageurl = "/img/my/orderDetail/entered.png";
						}
					 
					     $scope.enterStore.workerimageurl = "/img/my/orderDetail/" + $scope.enterStore.saNameImg + ".png";
					 }
				 
				 $scope.printBill = data.printBill;
				 
				 if($scope.printBill != null)
				 { 
					if($scope.saNames[$scope.printBill.saName] != null)
				    	{
				    	     $scope.printBill.saNameImg = $scope.saNames[$scope.printBill.saName];
				    	}else{
				    		$scope.printBill.saNameImg = "defaultworker";
				    	} 
					 
					if($scope.printBill.happenStatus == '-1')
					 {
					    $scope.printBill.backimageurl = "/img/my/orderDetail/printedbill.png";
					 }else if($scope.printBill.happenStatus == '0')
					 {
					    $scope.printBill.backimageurl = "/img/my/orderDetail/printingbill.png";
					 }
					 
				    $scope.printBill.workerimageurl = "/img/my/orderDetail/" + $scope.printBill.saNameImg + ".png";
					 
				 	$scope.billitems = data.billitems;
				 	$scope.billgifts = data.billgifts;
				 	
				 }
				 
				 $scope.plantWorks = data.plantWorks;
				 if($scope.plantWorks != null)
					 {
					     $scope.currentPlantWork = data.currentPlantWork;
					     
					     if($scope.saNames[$scope.currentPlantWork.saName] != null)
					    	{
					    	     $scope.currentPlantWork.saNameImg = $scope.saNames[$scope.currentPlantWork.saName];
					    	}else{
					    		$scope.currentPlantWork.saNameImg = "defaultworker";
					    	} 

						 if($scope.currentPlantWork.happenStatus == '0')
						 {
						    $scope.currentPlantWork.backimageurl = "/img/my/orderDetail/working.png";
						 }else if($scope.currentPlantWork.happenStatus == '-1')
						 {
						    $scope.currentPlantWork.backimageurl = "/img/my/orderDetail/worked.png";
						 }
					     $scope.currentPlantWork.workerimageurl = "/img/my/orderDetail/" + $scope.currentPlantWork.saNameImg + ".png";

					 }
				 
				 $scope.quaCheck = data.quaCheck;
				 if($scope.quaCheck != null)
					 {
						
						 
						 if($scope.saNames[$scope.quaCheck.saName] != null)
					    	{
					    	     $scope.quaCheck.saNameImg = $scope.saNames[$scope.quaCheck.saName];
					    	}else{
					    		$scope.quaCheck.saNameImg = "defaultworker";
					    	} 
						 
						 
					     $scope.quaCheck.workerimageurl = "/img/my/orderDetail/" + $scope.quaCheck.saNameImg + ".png";

					 }
				 
				 $scope.custPay = data.custPay; 
				 if($scope.custPay != null)
				 {
					
					 if($scope.saNames[$scope.custPay.saName] != null)
				    	{
				    	    $scope.custPay.saNameImg = $scope.saNames[$scope.custPay.saName];
				    	}else{
				    		$scope.custPay.saNameImg = "defaultworker";
				    	} 
					 
				     $scope.custPay.workerimageurl = "/img/my/orderDetail/" + $scope.custPay.saNameImg + ".png";

				 }
				 
			  }
		  })
		  .error(function(data){
			  var output = "";
			  for (var property in data) {
			    output += property + ': ' + data[property]+'; ';
			  }
			  console.error(output);
			  
			  console.log("getOrderStatusData error...")
			  
		  });
		
	//}
	
	//$scope.initDetail();
	
	//设置出单信息
	$ionicModal.fromTemplateUrl('tpl/my/orderPrintBill.html', {
		  scope: $scope,
		  animation: 'slide-in-up'
		}).then(function (modal) {
		  $scope.modalPrintBill = modal;
		});
	
	//设置出单信息
	$ionicModal.fromTemplateUrl('tpl/my/orderPlantWork.html', {
		  scope: $scope,
		  animation: 'slide-in-up'
		}).then(function (modal) {
		  $scope.modalPlantWork = modal;
		});
	
	//设置出单信息
	$ionicModal.fromTemplateUrl('tpl/my/orderCustomerPay.html', {
		  scope: $scope,
		  animation: 'slide-in-up'
		}).then(function (modal) {
		  $scope.modalCustomerPay = modal;
		});
	
	
	// function to open the modal
	$scope.openModalPrintBill = function () {
	  $scope.modalPrintBill.show();
	};

	// function to close the modal
	$scope.closeModalPrintBill = function () {
	  $scope.modalPrintBill.hide();
	};
	
	// function to open the modal
	$scope.openModalPlantWork = function () {
	  $scope.modalPlantWork.show();
	};

	// function to close the modal
	$scope.closeModalPlantWork = function () {
	  $scope.modalPlantWork.hide();
	};

	// function to open the modal
	$scope.openModalCustomerPay = function () {
	  $scope.modalCustomerPay.show();
	};

	// function to close the modal
	$scope.closeModalCustomerPay = function () {
	  $scope.modalCustomerPay.hide();
	};
	
	//Cleanup the modal when we're done with it!
	$scope.$on('$destroy', function () {
	  $scope.modalPrintBill.remove();
	  $scope.modalPlantWork.remove();
	  $scope.modalCustomerPay.remove();
	});
    //ULETIAN 讲师列表
	$scope.saNames = {defaultworker : "defaultworker", 
			何世友:"heshiyou", 洪春娇:"hongchunjiao",
			蒋银银:"jiangyinyin",刘福:"liufu",刘荣杰:"liurongjie",
			陆锦番:"lujinfan",肖兆坤:"xiaozhaokun",郑先文:"zhengxianwen",
			陈光旭:"chenguangxu",洪志辉:"hongzhihui",胡德坤:"hudekun",
			柯金生:"kejinsheng",柯圣涛:"keshengtao",李风:"lifeng",
			李钟锭:"lizhongdian",唐立超:"tanglichao",吴词福:"wucifu",
			吴俊杰:"wujunjie",许爱松:"xuaisong",
			叶文辉:"yewenhui",钟燕君:"zhongyanjun",周介飞:"zhoujiefei"};
	
});