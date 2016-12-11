
ultcrm.controller('cardDetailCtrl', function($scope,$http, $state,$stateParams,$ionicSlideBoxDelegate) {
	
	$scope.cardId=$stateParams.cardId;

	console.log($scope.cardId);
	
	//次卡信息
	$scope.cardDetail = {
		cardNo : "",	
		techlevelno: "",
		description:"",
		usedCount:0,
		totalCount:0,
		cardName:"",
		endDate:"",
		cunsumptionItems:[]
	};
	
	
	//消费记录
	$scope.consumeList=[];
	
	//返回卡券列表
	$scope.toCardList = function(){
		$state.go('index.cardList',{},{reload:true});
	};
	
	//使用详情，如果没有消费记录，则使用详情不显示
	$scope.usedListState = {
		show: false
	}
	
	//刷新页面数据
	$http.get("/getCardDetail/"+$stateParams.cardId).success(
		function(data){
			$scope.cardDetail = data;
			
			//有使用记录才显示列表标题 ‘使用详情’
			if(data.cunsumptionItems.length>0){
				$scope.usedListState.show = true;
			}
			if(!$scope.cardDetail.description){
				$scope.cardDetail.description="首次关注赠送";
			}
		}		
	).error(function(data){
			console.log('get cardDetail error');
	});
	
});