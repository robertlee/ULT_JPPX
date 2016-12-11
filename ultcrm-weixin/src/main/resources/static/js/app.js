// Ionic Starter App

// angular.module is a global place for creating, registering and retrieving Angular modules
// 'starter' is the name of this angular module example (also set in a <body> attribute in index.html)
// the 2nd parameter is an array of 'requires'
// 'starter.services' is found in services.js
// 'starter.controllers' is found in controllers.js
angular.module('ultcrm', ['ionic','ultcrm.controllers', 'ultcrm.services','ultcrm.values','ultcrm.constants','ultcrm.filters','ngSanitize'])

.run(function($ionicPlatform,$location,$state) {
  $ionicPlatform.ready(function() {
    // Hide the accessory bar by default (remove this to show the accessory bar above the keyboard
    // for form inputs)
	  
    if (window.cordova && window.cordova.plugins && window.cordova.plugins.Keyboard) {
      cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);
      cordova.plugins.Keyboard.disableScroll(true);
    }
    if (window.StatusBar) {
      // org.apache.cordova.statusbar required
      StatusBar.styleLightContent();
    }
  });
  

})

.config(function($stateProvider, $urlRouterProvider,$ionicConfigProvider) {
	$ionicConfigProvider.tabs.position("bottom");
	//$ionicConfigProvider.views.transition("none");
                                                                                
  // Ionic uses AngularUI Router which uses the concept of states
  // Learn more here: https://github.com/angular-ui/ui-router
  // Set up the various states which the app can be in.
  // Each state's controller can be found in controllers.js
  $stateProvider
  //setup an abstract state for the tabs directive	  
	    .state('index', {
	        url: '/index',
	        abstract: true,
	        templateUrl: 'tpl/common/foottabs.html'
	      })
	     .state('weixin', {
	        url: '/weixin',
	        templateUrl: 'tpl/common/weixin.html',
	        controller: 'weixinCtrl'
          })
	      // Each tab has its own nav history stack:
	      .state('index.home', {
	        url: '/home',
	        cache:false, 
	        views: {
	          'index-home': {
	            templateUrl: 'tpl/home/home.html',
	            controller: 'homeCtrl'
	          }
	        },
	        resolve: {
	            customerData: function($location,customerDataService) {
	            	console.log("into resolve customer index data.....");
	            	var searchObject = $location.search();
	        		var code = searchObject['code'];
	        		var uid = searchObject['uid'];
	            	return customerDataService.find(uid,code);
	            }
	          }
	      })
	       .state('index.activityDetail', {
	        url: '/activityDetail/:id',
	        cache:false, 
	        params:{'id':null},
	        views: {
	          'index-home': {
	            templateUrl: 'tpl/home/activityDetail.html',
	            controller: 'activityDetailCtrl'
	          }
	        }
	      })
	      
	      //服务首页
	      .state('index.service', {
	        url: '/service',
	        cache:false, 
	        params:{'techlevelno':null,'courseName':null},
	        views: {
	          'index-service': {
	            templateUrl: 'tpl/service/service.html',
	            controller: 'serviceCtrl'
	          }
	        },
	        resolve: {
	            customerData: function($location,customerDataService) {
	            	console.log("into resolve customer data.....");
	            	var searchObject = $location.search();
	        		var code = searchObject['code'];
	        		var uid = searchObject['uid'];
	            	return customerDataService.find(uid,code);
	            }
	          }
	      })
	      //课程详情展示
	      .state('index.courseDetail', {
	        url: '/courseDetail/:index',
	        cache:false,
	        params:{'index':null},
	        views: {
	          'index-service': {
	            templateUrl: 'tpl/service/courseDetail.html',
	            controller: 'courseDetailCtrl'
	          }
	        }
	      })
	      //教师详情展示
	      .state('index.teacherDetail', {
	        url: '/teacherDetail/:id',
	        cache:false,
	        params:{'id':null},
	        views: {
	          'index-service': {
	            templateUrl: 'tpl/service/teacherDetail.html',
	            controller: 'teacherDetailCtrl'
	          }
	        }
	      })
	       //课程安排选择
	      .state('index.serviceStore', {
	        url: '/serviceStore/:id/:addressId/:openId/:typeId/:status',
	        cache:true,
	        params:{'id':null,'addressId':null,'openId':null,'typeId':null,'status':null},
	        views: {
	          'index-service': {
	            templateUrl: 'tpl/service/serviceStore.html',
	            controller: 'serviceStoreCtrl'
	          }
	        }
	      })
	      .state('index.serviceSeat', {
	        url: '/serviceSeat/:scheId/:classId/:seatType/:roomName/:openId',
	        cache:false,
	        params:{'scheId':null,'classId':null,'seatType':null,'roomName':null,'openId':null},
	        views: {
	          'index-service': {
	            templateUrl: 'tpl/service/serviceSeat.html',
	            controller: 'serviceSeatCtrl'
	          }
	        }
	      })
	      .state('index.servicePay', {
	        url: '/servicePay/:jsonStr',
	        cache:false,
	        params:{'jsonStr':null},
	        	views: {
					  'index-service':{
						  templateUrl: 'tpl/service/servicePay.html',
						  controller: 'servicePayCtrl'
					  }
				  }
	      })
		  .state('index.appointmentok', {
		        url: '/appointmentok',
		        cache:false, 
		        params: {'data': null, 'orderId': null,"hasCard":null},
		        views: {
		        	'index-service':{
		        		templateUrl: 'tpl/home/appointmentok.html',
		        		controller: 'appointmentOkCtrl'
		        	}
		        }
		   })
		   .state('index.getCard', {
			   url: '/getCard',
			   cache:false, 
			   params: {'data': null},
			   views: {
				   'index-home':{
					   templateUrl: 'tpl/home/getCard.html',
					   controller: 'getCardCtrl'
				   }
			   }
		   })
		   .state('index.myindex', {
	        url: '/myindex',
	        cache:false, 
	        views: {
	        	'index-my':{
	        		templateUrl: 'tpl/my/myindex.html',
	        		controller: 'myIndexCtrl'
	        	}
	        },
	        resolve: {
	            customerData: function($location,customerDataService) {
	            	console.log("into resolve customer data.....");
	            	var searchObject = $location.search();
	        		var code = searchObject['code'];
	        		var uid = searchObject['uid'];
	            	return customerDataService.find(uid,code);
	            }
	          }
	      })  
	      .state('index.myorderList', {
	        url: '/myorderList/:viewType',
	        cache:false,
	        params: {'viewType': null},
	        views: {
	        	'index-my':{
	        		templateUrl: 'tpl/my/orderList.html',
	        		controller: 'orderListCtrl'
	        	}
	        }
	      })
		  .state('index.orderOrderList', {
		        url: '/orderOrderList/:viewType',
		        cache:false,
		        params: {'viewType': null},
		        views: {
		        	'index-order':{
		        		templateUrl: 'tpl/my/orderList.html',
		        		controller: 'orderListCtrl'
		        	}
		        }
		      })
		  .state('index.myorderComment', {
		      url: '/myorderComment',
		      cache:false, 
		      params: {'orderId': null},
		      views: {
		      	'index-my':{
		      		templateUrl: 'tpl/my/orderComment.html',
		      		controller: 'orderCommentCtrl'
		      	}
		      }
		    })
  
		  .state('index.myorderProcessDetail', {
		      url: '/myorderProcessDetail',
		      cache:false, 
		      params: {'orderId': null},
		      views: {
		      	'index-my':{
		      		templateUrl: 'tpl/my/orderProcessDetail.html'
		      	}
		      }
		    })
		    .state('index.myprofileList', {
		      url: '/myprofileList',
		      cache:false, 
		      views: {
		      	'index-my':{
		      		templateUrl: 'tpl/my/profileList.html',
		      		controller: 'profileListCtrl'
		      	}
		      },
		        resolve: {
		            customerData: function($location,customerDataService) {
		            	console.log("into resolve customer data.....");
		            	var searchObject = $location.search();
		        		var code = searchObject['code'];
		        		var uid = searchObject['uid'];
		            	return customerDataService.find(uid,code);
		            }
		          }
		    })
		    .state('index.modifyName', {
		      url: '/modifyName',
		      cache:false, 
		      views: {
		      	'index-my':{
		      		templateUrl: 'tpl/my/modifyName.html',
		      		controller: 'modifyNameCtrl'
		      	}
		      }
		    })
		    .state('index.modifySex', {
		      url: '/modifySex',
		      cache:false, 
		      views: {
		      	'index-my':{
		      		templateUrl: 'tpl/my/modifySex.html',
		      		controller: 'modifySexCtrl'
		      	}
		      }
		    })
		    .state('index.modifyPhone', {
		      url: '/modifyPhone/:customerId/:jsonStr',
		      cache:false, 
			  params:{'customerId':null,'str':null,'jsonStr':null},
		      views: {
		      	'index-my':{
		      		templateUrl: 'tpl/my/modifyPhone.html',
					controller: 'modifyPhoneCtrl'
		      	}
		      }
		    })
		    .state('index.orderInfo', {
			  url: '/orderInfo/:orderId:seatname:startClassTime',
			  cache:false, 
			  params: {'orderId': null,'seatname':null,'startClassTime':null},
			  views: {
				  'index-order':{
					  templateUrl: 'tpl/my/orderInfo.html',
					  controller: 'orderInfoCtrl'
				  }
			  }
		    })
		   .state('index.myChildren', {
	        url: '/myChildren/:customerId',
	        cache:false,
	        params: {'customerId': null},
	        views: {
	          'index-my': {
	            templateUrl: 'tpl/my/myChildren.html',
	            controller: 'myChildrenCtrl'
	          }
	        }
	      })
			.state('index.newChild', {
				  url: '/newChild/:customerId/:jsonStr',
				  cache:false, 
				  params:{'customerId':null,'str':null,'jsonStr':null},
				  views: {
					  'index-my':{
						  templateUrl: 'tpl/my/newChild.html',
						  controller: 'newChildCtrl'
					  }
				  }
			})
		    .state('index.cardList', {
			  url: '/cardList',
			  cache:false,
			  views: {
				  'index-my':{
					  templateUrl: 'tpl/my/cardList.html',
					  controller: 'cardListCtrl'
				  }
			  }
		    })
		    .state('index.cardDetail', {
			  url: '/cardDetail/:cardId',
			  cache:false,
			  params:{'cardId':null},
			  views: {
				  'index-my':{
					  templateUrl: 'tpl/my/cardDetail.html',
					  controller: 'cardDetailCtrl'
				  }
			  }
		    })		    
		    .state('index.couponDetail', {
				  url: '/couponDetail',
				  cache:false, 
				  views: {
					  'index-my':{
						  templateUrl: 'tpl/my/couponDetail.html',
						  controller: 'couponDetailCtrl'
					  }
				  }
			 })
		    .state('index.scoreDetail', {
			  url: '/scoreDetail',
			  cache:false, 
			  views: {
				  'index-my':{
					  templateUrl: 'tpl/my/scoreDetail.html',
					  controller: 'scoreDetailCtrl'
				  }
			  }
		    })
		    .state('index.direct', {
			  url: '/direct',
			  cache:false, 
			  views: {
				  'index-my':{
					  templateUrl: 'tpl/my/direct.html',
					  controller: 'directCtrl'
				  }
			  }
		    })
			.state('index.cardCouponBatch', {
				  url: '/cardCouponBatch/:orderId',
				  cache:true, 
				  params:{'orderId':null},
				  views: {
					  'index-my':{
						  templateUrl: 'tpl/my/cardCouponBatch.html',
						  controller: 'cardCouponBatchCtrl'
					  }
				  }
			})
			.state('index.cardCouponDetail', {
				  url: '/cardCouponDetail/:type/:id/:orderId',
				  cache:true, 
				  params:{'orderId':null,'type':null,'id':null},
				  views: {
					  'index-my':{
						  templateUrl: 'tpl/my/cardCouponDetail.html',
						  controller: 'cardCouponDetailCtrl'
					  }
				  }
			})
			;
	      
	      // if none of the above states are matched, use this as the fallback
	      $urlRouterProvider.otherwise('/index/home');

});
