ultcrm.controller('modifyPhoneCtrl', function($scope,$stateParams,$ionicPopup,$location,$http,$interval,$state,$window,customerDataService) {
	//ADD Simple Methode;
	var str = $stateParams.str;	
	
	var cDate = new Date();
	var customer={};
	customer=customerDataService.getCustomer();

	if(customer == null || customer.id == null || customer.id == '0')
		{
		    customer={};
		    customer.id = 16;
		}
	console.log(customer.id + " &" + customer.phone);

	var timecount = 60;//验证码等待时间
	$scope.data = {btnText:'获取验证码',
			btndisable: false,
			show: false,
			phone:'',
			code:'',
			imgUrl:'/image/customer/' + customer.id +'/token/' + cDate.toLocaleTimeString(),
			imgCode:'',
			infophone:'',
			infocode:''};  //验证码控制对象
	
	$scope.data.phone=customer.phone;
    $scope.toProfileList=function(phone){
    	checkCode();
    };
    
    $scope.placeholderPhone = customer.phone;
   
    $scope.resetPhone=function(){
    	$scope.data.phone = "";

    };
    
    //获取验证吗
    $scope.obtainCode = function() {
    	//如果为空，默认原有号码
    	if($scope.data.phone == ''){
    		$scope.data.phone = $scope.placeholderPhone;
			$scope.data.codePrompt = '';
    	}
		var phonereg = /^(((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(16[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/; 
		if ($scope.data.phone == '' || (!phonereg.test($scope.data.phone))) {
			$scope.data.phonePrompt = '请输入正确的手机号码';
			$scope.data.show = true;
		}else{
			$scope.data.phonePrompt = '';
			$scope.data.btndisable = true;
			var urlStr = '/customer/obtaincode/phone/' + $scope.data.phone +
				'/customer/' + customer.id;
			console.log(urlStr);
			$http({
		        url: urlStr,
		        method: "GET",
		    })
		    .then(function(response) {
		    	if (response.data.code == 0) {
		    	$scope.timer = $interval(function(){
		    		timecount--;
		    		if (timecount < 0) {
						timecount = 0;
					}
		    		if (timecount == 0) {
		    			$scope.data.btnText = '获取验证码';
		    			$scope.data.btndisable = false;
		    			timecount = 60;
					}else{
						$scope.data.btndisable = true;
						$scope.data.btnText = timecount + 's';
					}
		    	}, 1000, 60)
		    	} else {
		    		$scope.data.btndisable = false;
		    		$scope.data.show = true;
			    	$scope.data.codePrompt = response.data.msg;
		    	}
		    	console.log(response);
		    }, 
		    function(response) { // optional
		            // failed
		    	$scope.data.codePrompt = '发送失败请重试';
		    	console.log(response);
		    });
		}
	};
	
    //检验验证码是否正确
    var checkCode = function() {
		$http({
	        url: '/customer/checkcode',
	        method: "POST",
	        data:{
	        	"codeStr":$scope.data.code,
	        	"customerid":customer.id,
	        	"phone":$scope.data.phone
	        	}
	    })
	    .success(function(data, status, headers, config) {  
	    	//加载成功之后做一些事  
	    	if (data.code == 0) {
	    		$scope.data.codePrompt = '';
	    		//加载成功
	        	if($scope.createAppointment != null)
	        		{
	        		   //预约过程的调用
	        		    $scope.createAppointment();
	        		}else{
	        			//修改手机号的调用
	        			customerDataService.setPhone($scope.data.phone);      
						if ($stateParams.jsonStr==null)
						{
							$state.go('index.myprofileList',{},{reload:true});
						}
						else
						{
							$state.go('index.servicePay',{jsonStr:$stateParams.jsonStr},{reload:true});	
						}
	        		}
			}else{				
				$scope.data.codePrompt = data.msg;
			}
	    }).error(function(data, status, headers, config) {  
	        //处理错误  
	    	console.log(data);
	    });
    }
    
    $scope.refreshCode = function(){
		var date = new Date();
		$scope.data.imgUrl = '/image/customer/' + customer.id +'/token/' + date.toLocaleTimeString();
	};
});
