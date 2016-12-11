ultcrm.controller('directCtrl', function($scope, $http, $state, $window,
		$ionicPopup, customerDataService) {
	
	//获取取用户信息
	var customer = customerDataService.getCustomer();
	
	//初始化地图
	$scope.initMap = function(){
		//导航平铺满'ion-content'
		var ionContent = document.getElementById('map-content');
		var mapContainer = document.getElementById('map-container');
		mapContainer.style.height = ionContent.offsetHeight + 'px';
		mapContainer.style.width = ionContent.offsetWidth + 'px';
		
		$scope.map = new AMap.Map('map-container',{resizeEnable:true});
	}

	
	//创建导航，from和to格式为{"longitude":00.0000, "latitude":00.0000}
	$scope.navigate = function(from,to){
		$scope.map.setZoom(16);
		AMap.service([ "AMap.Driving" ], function() {
			var driving = new AMap.Driving({
				map : $scope.map,
				panel : "nav-result"
			}); 
			
			// 根据起终点坐标规划导航路线
			driving.search([ from.longitude, from.latitude], 
					[to.longitude, to.latitude], function(status, result) {
				
				//如果导航失败，标记快乐语文培训中心位置
				if(status=="error"){
					 //alert("导航失败");
					 markNCStore();
					 return;
				}
				
				//导航成功，标记快乐语文培训中心店
				 var marker = new AMap.Marker({
				      position: [to.longitude, to.latitude]
				    });
				    marker.setMap($scope.map);
				 
				    // 设置鼠标划过点标记显示的文字提示
				    marker.setTitle('快乐语文培训中心');

				    // 设置label标签
				    marker.setLabel({//label的父div默认蓝框白底右下角显示，样式className为：amap-marker-label
				        offset:new AMap.Pixel(15,30),//修改父div相对于maker的位置
				        content:to.name||"快乐语文培训中心"
				    });
				console.log(status);
				console.log(result);

			});
		});	
	};
	
	//标记快乐语文总店
	var markNCStore = function(){
		$scope.map.setZoom(12);
		$scope.map.setCenter( [115.8959784799,28.6757951998]);
		
		//设置标记
		 var marker = new AMap.Marker({
		      position: [115.8959784799,28.6757951998]
		    });
	    marker.setMap($scope.map);
	 
	    // 设置鼠标划过点标记显示的文字提示
	    marker.setTitle('快乐语文培训中心');

	    // 设置label标签
	    marker.setLabel({
	        offset:new AMap.Pixel(-130,-90),	//marker的相对位置
	        content:'<div class="map-label"><h5><b>快乐语文培训中心</b></h5><p>\
	        	<b>地址：</b>快乐语文教育<br><b>\
	        	电话：</b>(0794)186836192 、13367006212<br><em>\
	        	</div>'
	    });
	}
	
	//请求用户和门店位置信息
	var url = '/customer/' + customer.id + '/navigateTo/' + 1    /*storeId，目前固定为快乐语文培训中心*/;
	$http.get(url).success(function(data) {
		$scope.initMap();
		var errorHandler = {
				"STORE_NOT_EXISTS":function(){
					//alert("该门店不存在");
				},
				"CUSTOMER_NOT_EXISTS":function(){
					//alert("用户不存在，请重新进入【服务】");
				},
				"STORE_LOCATION_NOT_EXISTS":function(){
					//alert("Sorry,该门店不支持导航");
				},
				"CUSTOMER_LOCATION_NOT_EXISTS":function(){
					if(!navigator.geolocation){
						//alert("不能获取到您的位置");
					}
					
					//如果服务器没有客户位置，则启动定位
					navigator.geolocation.getCurrentPosition(function(position){
						var from = position.coords;
						var to = data.store;
						$scope.navigate(from,to);
					}, function(error){
						if(error){
							console.log(error);
							//alert("不能获取到您的位置");
						}
					},{
						// 指示浏览器获取高精度的位置，默认为false
						enableHighAcuracy: true,
						// 指定获取地理位置的超时时间，默认不限时，单位为毫秒
						timeout: 5000,
						// 最长有效期，在重复获取地理位置时，此参数指定多久再次获取位置。
						maximumAge: 3000
					});
				}
			};
		
		//如果返回错误，弹出信息，标记快乐语文总店位置
		if(data.error){
			errorHandler[data.error]();
			markNCStore();
			return;
		}
		
		$scope.navigate(data.customer,data.store);
		
	}).error(function() {
		$scope.initMap();
		//alert("获取位置信息失败");
		markNCStore();

	});
});