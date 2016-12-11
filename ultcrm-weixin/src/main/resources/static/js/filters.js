angular.module('ultcrm.filters', [])
.filter("sex", function() {
    return function(sex){
    	if (sex == 1 || sex=='男') {
			return '男';
		}else if(sex==2 || sex=='女'){
			return '女';
		}else{
			return '';
		}
    };
})
.filter("order_state", function() {
    return function(stateVal){
    	var stateStr = '';
    	switch(stateVal){
    	case 1:
    		stateStr = '等待开课';
    		break;
    	case 2:
    		stateStr = '学习中';
    		break;
    	case 3:
    		stateStr = '已结业';
    		break;
    	case 4:
    		stateStr = '取消';
    		break;
    	default:
    		stateStr = '未知';
    		break;
    	}
    	return stateStr;
    };
});