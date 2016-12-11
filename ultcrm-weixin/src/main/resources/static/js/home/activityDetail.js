//home页面的控制器
ultcrm.controller('activityDetailCtrl', function($scope,$http,$state,$stateParams,$window,$location,$sanitize,$ionicPopup,$ionicSlideBoxDelegate,appointmentService,customerDataService) {

	$scope.id=$stateParams.id;
	 $scope.myActiveSlide = 0;
	//alert($scope.id);
	//if ($scope.id == 2) {
	//	$state.go('index.service',{},{reload:true});
	//}
	var activeID=$scope.id;
	document.querySelector('#previewImageA').onclick = function () {
    wx.previewImage({        
    current: 'http://www.91jpfw.cn/img/activity/ad_detail'+activeID.toString()+'A.jpg',
    urls: [	   
	   'http://www.91jpfw.cn/img/activity/ad_detail'+activeID.toString()+'A.jpg',
       'http://www.91jpfw.cn/img/activity/ad_detail'+activeID.toString()+'B.jpg'      
    ]
    });
	};
	document.querySelector('#previewImageB').onclick = function () {
    wx.previewImage({        
    current: 'http://www.91jpfw.cn/img/activity/ad_detail'+activeID.toString()+'B.jpg',
    urls: [	   
	   'http://www.91jpfw.cn/img/activity/ad_detail'+activeID.toString()+'A.jpg',
       'http://www.91jpfw.cn/img/activity/ad_detail'+activeID.toString()+'B.jpg'      
    ]
    });
	};	
});

