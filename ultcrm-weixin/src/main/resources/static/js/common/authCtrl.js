var app = angular.module('auth', []);
app.controller('authCtrl', function($scope,$location) {
	//发起OAuth的处理流程
	//第一步，用户同意授权，获取code
	var searchObject = $location.search();
	
	var appid = searchObject['appid'];
	var returnUrl = searchObject['returnUrl'];
	
	window.location.href="https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appid+"&redirect_uri="+returnUrl+"&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect";
	
});