ultcrm.controller('serviceCtrl', function($scope,$http,$location,$state,$stateParams,$timeout,$ionicPopup,customerData,classTypeData) {
	//初始化数据
	var count = 1;
	$scope.div1 = true;//显示
	$scope.div2 = false;//隐藏
	//筛选：			  
	//1-2   看图写话  ，
	//3-6   快乐作文  ，
	//7-8   幼小衔接  ，
	$scope.classType =  ["看图写话", "快乐作文", "幼小衔接"];
	$scope.title1 = "title1 change1";
	$scope.title2 = "title2 change2";
	angular.element(document.querySelector('#imagesClass1')).addClass('active');
	angular.element(document.querySelector('#smallImgClass1')).addClass('trigger current');
	
	function selectClassType(typeId,Leng,businesslist){
		var divContent = [];
		switch(typeId)
		{
			case $scope.classType[0]: for (var i = 0,j=0; i < Leng; i++) 
									  {
										  if (businesslist[i].id<=2)
										  {
											  divContent[j]=businesslist[i];
											  j=j+1;
										  }
									  };
									  break;
			case $scope.classType[1]: for (var i = 0,j=0; i < Leng; i++) 
									  {
										  if ((businesslist[i].id<=6)&&(businesslist[i].id>=3))
										  {
											  divContent[j]=businesslist[i];
											  j=j+1;
										  }
									  };
									  break;	
			case $scope.classType[2]: for (var i = 0,j=0; i < Leng; i++) 
									  {
										  if ((businesslist[i].id<=8)&&(businesslist[i].id>=7))
										  {
											  divContent[j]=businesslist[i];
											  j=j+1;
										  }
									  };
									  break;
			default:				  return businesslist;
									  
		}
		return divContent;
	};
	
	$scope.searchClassType=function(batchName,addressId,typeId){
		$scope.hiddenAddressId = addressId;
		$scope.hiddenBatch = batchName;
		$scope.hiddenTypeId = typeId;
		$http.get('/getBusinessList/' + batchName + '/' + addressId).success(function(result) {
        	//先清空原始数据
        	$scope.divContent1 = null;
        	$scope.divContent2 = null;
        	if(result != null)
	        {
	        	if(result.businessinglist != null && result.businessinglist.length > 0){
					var Leng=result.businessinglist.length;	
					$scope.divContent2=[];
					$scope.divContent2=selectClassType(typeId,Leng,result.businessinglist);
					//$scope.divContent2 = result.businessinglist;
	        	}
				
	        	if(result.businesslist != null && result.businesslist.length > 0){
					Leng=result.businesslist.length;
					$scope.divContent1=[];
					$scope.divContent1=selectClassType(typeId,Leng,result.businesslist);
	        		//$scope.divContent1 = result.businesslist;
	        	}
	        }
        }).
        error(function() {
        	$scope.divContent1 = null;
        	$scope.divContent2 = null;
        });
	};
	//查询课程信息函数
	$scope.searchTrainClass=function(batchName,addressId){
		$scope.hiddenAddressId = addressId;
		$scope.hiddenBatch = batchName;
		classTypeData.startBatch = batchName;
		classTypeData.addressId = addressId;
		$scope.hiddenTypeId ="";
		$scope.selectedSite3 = "";
		$http.get('/getBusinessList/' + batchName + '/' + addressId).success(function(result) {
        	//先清空原始数据
        	$scope.divContent1 = null;
        	$scope.divContent2 = null;
        	if(result != null)
	        {
	        	if(result.businessinglist != null && result.businessinglist.length > 0){
	        		$scope.divContent2 = result.businessinglist;
	        	}
	        	if(result.businesslist != null && result.businesslist.length > 0){
	        		$scope.divContent1 = result.businesslist;
	        	}
	        }
        }).
        error(function() {
        	$scope.divContent1 = null;
        	$scope.divContent2 = null;
        });
	};
	
	//查询开课批次select
	$scope.changeBatch=function(){
		$scope.selectedSite3 = "";
		$http.get('/getBatchList').success(function(result) {
			$scope.batch = result;//再给select赋值
			if(classTypeData.startBatch != null && classTypeData.startBatch != ""){
				$scope.selectedSite1 = classTypeData.startBatch;
				$scope.changeAddress(classTypeData.startBatch,0);
			}
			else{
				$scope.selectedSite1 = result[0].startBatch;
				$scope.changeAddress(result[0].startBatch,0);
			}
        }).
        error(function() {
        	$scope.batch = [];//清空select
        });
	};
	//根据课程批次编号查询该批次下所有的门店地址select
	$scope.changeAddress=function(batchName,type){
		$http.get('/getAddressList/' + batchName).success(function(result) {
        	//先清空原始数据
			$scope.addressList = [];//先清空select
			$scope.addressList = result;//再给select赋值
			var tmpStr = result[0].addressId;
			if(classTypeData.addressId != null && type != 1){
				tmpStr = classTypeData.addressId;
			}
			$scope.selectedSite2 = tmpStr;
    		$scope.searchTrainClass(batchName,tmpStr);
        }).
        error(function() {
        	$scope.addressList = [];//清空select
        });
	};
	//导航切换函数
	$scope.switchNav=function(index){
		if(index == "navon1"){
			$scope.div1 = true;//显示
			$scope.div2 = false;//隐藏
			$scope.title1 = "title1 change1";
			$scope.title2 = "title2 change2";
		}
		else if(index == "navon2"){
			$scope.div1 = false;//隐藏
			$scope.div2 = true;//显示
			$scope.title1 = "title1 change2";
			$scope.title2 = "title2 change1";
		}
	};
	$scope.click_buy=function(classId,status){
		var openId = customerData.openid;
		var hiddenAddressId = $scope.hiddenAddressId;
		var hiddenBatch = $scope.hiddenBatch;
		$state.go('index.serviceStore',{id:classId,addressId:hiddenAddressId,openId:openId,typeId:hiddenBatch,status:status},{reload:true});
	};
	//展示教师详情
	$scope.click_teacher=function(id){
		$state.go('index.teacherDetail',{id:id},{reload:true});
	};
	//展示课程详情
	$scope.showCourseDetail=function(index){
		$state.go('index.courseDetail',{index:index},{reload:true});
	};
	//定时切换导航图片
	$scope.myActiveSlide=0;
	$scope.changeBatch();
});

