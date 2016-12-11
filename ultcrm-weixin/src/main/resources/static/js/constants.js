/*存放一些常量相关的东西, 常量不可以被修改*/

angular.module('ultcrm.constants', [])
.constant('defaultStore',{
	province:'广东',
	city:'深圳',
	name:'海上田园深圳店',
	id:'1'
})
.constant('province',
[
{shortName:'粤',fullName:'广东'},{shortName:'港',fullName:'香港'},{shortName:'豫',fullName:'河南'},{shortName:'闽',fullName:'福建'},{shortName:'赣',fullName:'江西'},{shortName:'湘',fullName:'湖南'},{shortName:'鄂',fullName:'湖北'},{shortName:'宁',fullName:'宁夏'},
{shortName:'浙',fullName:'浙江'},{shortName:'沪',fullName:'上海'},{shortName:'皖',fullName:'安徽'},{shortName:'桂',fullName:'广西'},{shortName:'苏',fullName:'江苏'},{shortName:'津',fullName:'天津'},{shortName:'京',fullName:'北京'},{shortName:'青',fullName:'青海'},
{shortName:'渝',fullName:'重庆'},{shortName:'鲁',fullName:'山东'},{shortName:'琼',fullName:'海南'},{shortName:'川',fullName:'四川'},{shortName:'冀',fullName:'河北'},{shortName:'贵',fullName:'贵州'},{shortName:'晋',fullName:'山西'},{shortName:'藏',fullName:'西藏'},
{shortName:'云',fullName:'云南'},{shortName:'辽',fullName:'辽宁'},{shortName:'陕',fullName:'陕西'},{shortName:'吉',fullName:'吉林'},{shortName:'甘',fullName:'甘肃'},{shortName:'黑',fullName:'黑龙江'},{shortName:'蒙',fullName:'内蒙古'},{shortName:'新',fullName:'新疆'}
]		
)
.constant('courseList',
[
    {name:'软件测试',id:'1',img:'/img/baoma.png',iconClass:'icon-bmw',iconSmallClass:'icon-bmw-sm'},
    {name:'系统测试',id:'2',img:'/img/benz.png',iconClass:'icon-benz',iconSmallClass:'icon-benz-sm'},
    {name:'JAVA开发',id:'3',img:'/img/aodi.png',iconClass:'icon-audi',iconSmallClass:'icon-audi-sm'},
    {name:'原型设计',id:'4',img:'/img/luhu.png',iconClass:'icon-land-rover',iconSmallClass:'icon-land-rover-sm'},
    {name:'测试分析',id:'5',img:'/img/baoshijie.png',iconClass:'icon-porsche',iconSmallClass:'icon-porsche-sm'},
    {name:'其他',id:'9',img:'/img/baoshijie.png',iconClass:'iconfont icon-other blue',iconSmallClass:'iconfont icon-other icon-sm blue'}
])
.constant('plateAreaCodeArray',
		[ "粤B","粤A","粤F","粤C","粤D","粤E","粤J","粤G","粤K","粤H","粤L","粤M","粤N","粤P","粤Q","粤R","粤S","粤T","粤U","粤V","粤W","琼A","琼B","琼D","琼C","琼" ,"川A","川C","川D","川E","川F","川B","川H","川J","川K","川L","川R","川Z","川Q","川X","川S","川T","川Y","川M","川U","川V","川W","贵A","贵B","贵C","贵G","贵F","贵D","贵E","贵H","贵J","云A","云D","云F","云M","云C","云P","云J","云S","云E","云G","云H","云K","云L","云N","云Q","云R","陕A","陕B","陕C","陕D","陕E","陕J","陕F","陕K","陕G","陕H","青" ,"宁A","宁B","宁C","宁D","宁E","冀A","冀B","冀C","冀D","冀E","冀F","冀G","冀H","冀J","冀R","冀T","蒙A","蒙B","蒙C","蒙D","蒙G","蒙K","蒙E","蒙L","蒙J","蒙F","蒙H","蒙M","吉A","吉B","吉C","吉D","吉E","吉F","吉J","吉G","吉H","沪" ,"沪R","皖A","皖B","皖C","皖D","皖E","皖F","皖G","皖H","皖J","皖M","皖K","皖L","皖N","皖S","皖R","皖P","鲁A","鲁B","鲁C","鲁D","鲁E","鲁F","鲁G","鲁H","鲁J","鲁K","鲁L","鲁S","鲁Q","鲁N","鲁P","鲁M","鲁R","鄂A","鄂B","鄂C","鄂E","鄂F","鄂G","鄂H","鄂K","鄂D","鄂J","鄂L","鄂S","鄂Q","鄂M","鄂N","鄂R","鄂P","桂A","桂B","桂C","桂D","桂E","桂P","桂N","桂R","桂K","桂L","桂J","桂M","桂G","桂F","渝F","渝G","渝" ,"渝B","渝A","渝H","渝C","藏A","藏B","藏C","藏D","藏E","藏F","藏G","甘A","甘B","甘C","甘D","甘E","甘H","甘G","甘L","甘F","甘M","甘J","甘K","甘N","甘P","新A","新J","新K","新L","新B","新E","新M","新N","新P","新Q","新R","新D","新G","新H","新C","新" ,"晋A","晋B","晋C","晋D","晋E","晋F","晋K","晋M","晋H","晋L","晋J","辽A","辽B","辽C","辽D","辽E","辽F","辽G","辽H","辽J","辽K","辽L","辽M","辽N","辽P","黑A","黑B","黑G","黑H","黑J","黑E","黑F","黑D","黑K","黑C","黑N","黑M","黑P","苏A","苏B","苏C","苏D","苏E","苏F","苏G","苏H","苏J","苏K","苏L","苏M","苏N","浙A","浙B","浙C","浙F","浙E","浙D","浙G","浙H","浙L","浙J","浙K","闽A","闽D","闽B","闽G","闽C","闽E","闽H","闽F","闽J","赣A","赣H","赣J","赣G","赣K","赣L","赣B","赣D","赣C","赣F","赣E","豫A","豫B","豫C","豫D","豫E","豫F","豫G","豫H","豫J","豫K","豫L","豫M","豫R","豫N","豫S","豫P","豫Q","豫U","湘A","湘B","湘C","湘D","湘E","湘F","湘J","湘G","湘H","湘L","湘M","湘N","湘K","湘U"]
)
.constant('businessTypeMap',
	{
		 "1":{name:'初级' , iconClass:'icon-washing' , iconColor:'#2fb0e3'},
		 "2":{name:'中级' ,  iconClass:'icon-maintain' , iconColor:'#fd9f19'},
		 "3":{name:'高级' , iconClass:'icon-repair' , iconColor:'#fe7c26'},
		 "4":{name:'咨询' , iconClass:'icon-painting' , iconColor:'#35ba8a'},
		 "other":{name:'其他' , iconClass:'icon-other' , iconColor:'#2fb0e3'}
	}
)
