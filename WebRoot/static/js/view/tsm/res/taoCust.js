/**
 * 
 */
var  array  =new Array();
var mark =0;
$(function(){
	var custId = $('#custId').val();
	if(custId!=''){
		init(custId);
		
	}
	/*表单优化*/
	 $('.skin-minimal input').iCheck({
	     checkboxClass: 'icheckbox_minimal',
	     radioClass: 'iradio_minimal',
	     increaseArea: '20%'
	 });
	 
    window.onload=function(){
    	var date1=new Date();  //开始时间
    	    getRemarkList();
    	    taoCustGuide(custId);
    	    taoMainPlatform(custId);
    	var date2=new Date();  //开始时间
    	var date3=date2.getTime()-date1.getTime();
    	//    	alert(date3/1000);
    }
	//模糊处理手机、电话号码
	var idReplaceWord = $("#idReplaceWord").val();
	if(idReplaceWord==1){
		$("[phone=tel]").each(function(idx,obj){
			var phone = $(obj).text();
			if(phone != null && phone != ''){
				replaceWord(phone,$(obj));
				replaceTitleWord(phone,$(obj));
			}
		});
	 }       
    $('.demoBtn_a').click(function(){
        //新增资源
//    	pubDivShowIframe('add_cust',ctx+'/res/cust/toAddResByScreen','添加资源',800,570);
//        pubDivShowIframe('add_cust',ctx+'/res/incall/toEditResIframe','添加资源',800,570);
//        pubDivShowIframe('add_cust',ctx+'/res/cust/toAddRes?phone=123456','添加资源',800,570);
		$.ajax({
			url:ctx+'/res/resmanage/getResLimitNum',
			type:'post',
			data:{code:'DATA_10028'},
			success:function(data){
				if(data=='1'){
					window.top.iDialogMsg("提示","超出个人拥有资源限制数量！");
					return false;
				}else{
				   pubDivShowIframe('add_cust',ctx+'/res/cust/toAddRes','添加资源',500,483);
				}
			}
		});
	});
//	$('.demoBtn_d').click(function(){
//		showIframe('alert_scouring_cust_b','alert_scouring_cust_b.html','话术模板',1000,550);
//	});

	//话术模板展示
	$('.demoBtn_d').click(function(){
//		 pubDivShowIframe('add_cust',ctx+'/sys/knowlege/list','添加资源',1000,570);
		 var timestmp = (new Date()).valueOf();  
		 var account = $("#account").val();
		 var orgId = $("#orgId").val();
	     var hsUrl =$('#project_path').val() + '/sys/knowlege/list?'+ "account=" + account+ "&orgId=" + orgId + "&t=" + timestmp;
		 var reqXml = '<xml><Oparation Name=\"ExpandCallDlg\" Title=\"话术模板\"  Http=\"' + hsUrl + '\" StartPos=\"-1,-1\" EndPos=\"1000,550\"/></xml>';
		 external.OnBtnClickShowDetail(reqXml); 
	});

    /*card下拉*/
    $('.icon_down,.icon_down_a').live('click',function(){
    	var sty_hid = $(this).prev();
    	if($(this).attr('name') == 'up'){
    		$(this).css({'background':'url('+ctx+'/static/images/down-alert.png) no-repeat'});
    		$(this).attr('name','down');	
    		sty_hid.slideUp();
    	}else{
    		if($(this).hasClass('icon_down') == true){
    			$(this).parent().css({'height':'auto'});
    		}
    		$(this).css({'background':'url('+ctx+'/static/images/up-alert.png) no-repeat'});
    		$(this).attr('name','up');
    		sty_hid.slideDown();
    	}
    });
    
    /*自动连拨*/
    $('.hyx-sce-right-btn').find('.switch').click(function(){
    	var isAutoCall= '0';
    	if($(this).attr('name') == 'on'){
    		$(this).addClass('click-hover');
    		$(this).attr('name','off');
    		isAutoCall='0'
    	}else{
    		$(this).removeClass('click-hover');
    		$(this).attr('name','on');
    		isAutoCall='1'
    	}
    	
    	//设置到sesion中
		$.ajax({
			url :ctx+ '/res/tao/changeAutoCall',
			data:{'isAutoCall':isAutoCall},
			type : 'post',
			success : function(data) {
				if(data != 0){
					dialogMsg(-1,'操作失败,请重试');
				}else{
					
				}
			}
		});
    });
   /*表格radio*/
    $('.hyx-scp-tr').live('mouseover',function(){
    	var $_this = $(this);
    	$_this.find('.hyx-scp-disable').css({'display':'inline-block'});
    	var bgcolor = $_this.css('background-color');
    	if(bgcolor == "#f5f6f7"){
    		$_this.find('.hyx-scp-disable').css({'background-color':bgcolor});
    	}else{
    		$_this.find('.hyx-scp-disable').css({'background-color':'#fff'});
    	}
    });
    $('.hyx-scp-tr').live('mouseleave',function(){
    	$(this).find('.hyx-scp-disable').hide();
    });
 
    var otherRes= function(resId,upOrDown,isFromOtherPage){
    	setOnPoint(resId);
    	getRes(resId,upOrDown,isFromOtherPage);
    }
    
	 
    //其他资源
	$('input[name=otherRes]').each(function(item,obj){
		$(this).on("ifChecked",function(){
			var resId = $(this).attr('id');
			otherRes(resId,2,1);
		});		
	})

	
	//点击其他资源
	var isSubmited = false;
	$('tr[id^=otherRes_Tr_]').live('click',function(){
		if(isSubmited){
			return false;
		}
		var click_tr = $(this);
		if(!click_tr.hasClass('hyx-scp-tr') ){
			click_tr.siblings('tr').find('.iradio_minimal').removeClass('checked');
			click_tr.find('.iradio_minimal').addClass('checked');
			var resId = $(this).attr('id').split('_')[2];
			otherRes(resId,2,1);
    	}
	})
	default_tr($('.hyx-scp-nowtr'));
})

// 表格默认选中第一条
function default_tr(tab){
	tab.find('td').removeClass('td-link');
    tab.find('td').addClass('td-hover');
    tab.find('td:first').css({'border-left-style':'solid','border-left-color':'#469bff','border-left-width':'1px'});
    tab.find('td:last').css({'border-right-style':'solid','border-right-color':'#469bff','border-right-width':'1px'});
}
// 表格删除选中第一条
function del_checked_tr(tab){
    tab.find('td').removeClass('td-hover');
    tab.find('td:first').css({'border-left-style':'solid','border-left-color':'#fff','border-left-width':'1px'});
    tab.find('td:last').css({'border-right-style':'solid','border-right-color':'#fff','border-right-width':'1px'});
}
//  //选择筛选分组
//function selectRes(val){
//	$('#resourceGroupId').val(val);
//	//淘客户资源分组排序选中,判断当前所选分组的资源数是否超过固定大小
//	var faxNum = 50000;
//	var resourceGroupId = $("#resourceGroupId").val();
//	var orderType = $("#orderType").val();
//	var isConcat = $('#isConcat').val();
//	var resNum = 0 ;
//		$.ajax({
//			url : 'getMyResByGroupId',
//			type : 'post',
//			dataType :'json',
//			data:{"resourceGroupId":resourceGroupId,"orderType":orderType},
//            error:function(jqXHR, textStatus, errorThrown){   
//                if(textStatus=="timeout"){  
//                    alert("加载超时，请重试");  
//                }else{   
//                    alert(textStatus);   
//                }  
//            },
//			success : function(data) {
//				if(data == '-3'){
//					window.top.iDialogMsg('提示','操作失败,请重试');
//					return ;
//				}else{
//					resNum=parseInt(data);
//					if(resNum>faxNum){
//						window.top.iDialogMsg('提示',"所选分组下资源超过5000条，建议合理划分资源组");
//						$("#resourceGroupId").val($("#groupSelectId").val());
//					}else{
//						document.location.href = ctx +'/res/tao/updateTaoTag?resourceGroupId='+resourceGroupId+"&orderType="+orderType+"&isConcat="+isConcat;
//						$("#refreshOneId").val("1"); 
//					}
//				}
//			}
//		});
//}

//选择筛选分组
function selectRes(val){
	$('#resourceGroupId').val(val);
	var resourceGroupId = $("#resourceGroupId").val();
	var orderType = $("#orderType").val();
	var isConcat = $('#isConcat').val();
	document.location.href = ctx +'/res/tao/updateTaoTag?resourceGroupId='+resourceGroupId+"&orderType="+orderType+"&isConcat="+isConcat;
	$("#refreshOneId").val("1"); 

}

//初始十条数据
function init(custId){
	$.ajax({
		url : 'initTaoRes',
		type : 'post',
		dataType:'json',
        error:function(jqXHR, textStatus, errorThrown){   
            if(textStatus=="timeout"){  
                alert("加载超时，请重试");  
            }else{   
                alert(textStatus);   
            }  
        },
		success : function(data) {
			if(data == null || data == "" || data == undefined){ return false;}
	    	for(var i=0;i<data.length;i++){
	    		var obj =new Object();
	    		obj.name=data[i].name;
	    		obj.phone=data[i].phone;
	    		obj.resId=data[i].resId;
	    		obj.isCall=data[i].isCall;
	    		obj.status=data[i].status;
	    		obj.filterType=data[i].filterType;
	    		array[i]=obj;
	    	}
	    	if(array.length>0){
	    		showCurr(custId);
	    		setOnPoint(custId);
	    	}
		}
        });
}

function showCurr(id){
	$('#'+id).attr('checked',true);
}

function setOnPoint(resId){
	for(var i=0;i<array.length;i++){
		if(resId==array[i].resId){
			mark=i;
		}
	}
}
function setNextMark(){
	mark++;
	var click_tab = $('#otherTableId').find('tbody').find('tr').eq(mark);
	if(click_tab.hasClass('hyx-scp-tr') == false){
		click_tab.siblings('tr').find('.iradio_minimal').removeClass('checked');
		click_tab.find('.iradio_minimal').addClass('checked');
	}
}

function setUpMark(){
	mark--;
	var click_tab = $('#otherTableId').find('tbody').find('tr').eq(mark);
	if(click_tab.hasClass('hyx-scp-tr') == false){
		click_tab.siblings('tr').find('.iradio_minimal').removeClass('checked');
		click_tab.find('.iradio_minimal').addClass('checked');
	}
}
function next(){
	setNextMark();
	if(mark>=array.length){
		getFresh("2");	
	}else{
		var custId = array[mark].resId;
		getRes(custId,2,1)
	}
}

function up(){
	setUpMark();
	if(mark<0){
		getFresh("1");	
	}else{
		var custId = array[mark].resId;
		getRes(custId,1,1)
	}
}

function updatePlanResult(custId){
	$.ajax({
		url : ctx+'/res/tao/updatePlanResult',
		data:{resId:custId},
		dataType:'text',
		type : 'post',
        error:function(jqXHR, textStatus, errorThrown){   
            if(textStatus=="timeout"){  
                alert("加载超时，请重试");  
            }else{   
                alert(textStatus);   
            }  
        },
		success : function(data) {
		}
	});
}
//修改状态
function updateStatus(status,resId,isCall,name,phone,filterType,type){
	//模糊处理手机、电话号码
	var idReplaceWord = $("#idReplaceWord").val();
	if(idReplaceWord==1){
		phone =replaceWordStr(phone)
	 }
	if(status== null || status==undefined){
		status = "";
	}
	if(resId== null || resId==undefined){
		resId = "";
	}
	if(isCall== null || isCall==undefined){
		isCall = "";
	}
	if(name== null || name==undefined){
		name = "";
	}
	if(phone== null || phone==undefined){
		phone = "";
	}
	if(type== null || type==undefined){
		type = "";
	}	
	 var $obj = "";
     var statusName = '';
     var isCheck = "";
     if( (status ==2 || status==3) && type==1 && isCall !=0 && filterType!=2 ){ // 选中的资源
    	 if(array[mark] != undefined  && array[mark].resId!= undefined){ 
    		 resId==array[mark].resId?'checked':''; 
    	 }
    	 $obj =  $('#otherRes_Tr_'+resId).addClass("hyx-scp-nowtr");
    	 html =
			'<td style="width: 30px;" class="hyx-scp-oldcall"><div class="overflow_hidden w30 skin-minimal" style="text-align: left;">'+
			'<input type="radio" name="otherRes" id="'+resId+'" '+isCheck+' />'+
			'</div> <span class="hyx-scp-zz"></span></td>'+
		    '<td id="'+resId+'" name="otherRes_Td_'+resId+'"><div class="overflow_hidden w100" title="'+name+'">'+name+'</div></td>'+
	    	'<td id="'+resId+'" name="otherRes_Td_'+resId+'"><div class="overflow_hidden w100" phone="tel" title="'+phone+'">'+phone+'</div></td>';
    	 $obj.html(html) 
    	 return ;
     }
//     if( status==3 && type==3 && filterType!=2){
//    	 if(array[mark] != undefined  && array[mark].resId!= undefined){ 
//    		 resId==array[mark].resId?'checked':''; 
//    	 }
////    	 $obj =  $('#otherRes_Tr_'+resId).addClass("hyx-scp-nowtr");
//    	 $obj = $('#otherRes_Tr_'+resId).removeClass('hyx-scp-nowtr').addClass("hyx-scp-tr");
//    	 alert('该客户已成为意向客户:'+$obj.attr('class'))
//    	 html =
//			'<td style="width: 30px;"><span class="hyx-scp-disable">该客户已成为意向客户！</span><div class="overflow_hidden w30 skin-minimal" style="text-align: left;">'+
//			'<input type="radio" name="otherRes" id="'+resId+'" "'+isCheck+'" />'+
//			'</div> <span class="hyx-scp-zz"></span></td>'+
//		    '<td id="'+resId+'" name="otherRes_Td_'+resId+'"><div class="overflow_hidden w100" title="'+name+'">'+name+'</div></td>'+
//	    	'<td id="'+resId+'" name="otherRes_Td_'+resId+'"><div class="overflow_hidden w100" phone="tel" title="'+phone+'">'+phone+'</div></td>';
//	    	 $obj.html(html) 
//	     return ;
//			
//     }
     if((status ==2 || status==3) && type==1 && filterType==2){
    	 statusName =' 该资源被设置为延后呼叫资源！';
     }
     if(status==5 || status==4){
    	 statusName ='该资源已转入公海';
     }else if(status==3 && type==2){
    	 statusName ='该资源已转意向！';
     }else if(status==1){
    	 statusName =' 该资源已被回收！';
     }else if(status==6){
    	 statusName ='该资源已完成签约！';
     }else if(status==''){
    	 statusName ='该资源已被回收分配给其他人员';
     }else{
    	 
     }
     $obj = $('#otherRes_Tr_'+resId).removeClass('hyx-scp-nowtr').addClass("hyx-scp-tr");
	 var html =
	   '<td style="width: 30px;"><span class="hyx-scp-disable">'+statusName+
			'</span>'+
			'<div class="overflow_hidden w30 skin-minimal" style="text-align: left;">'+
				'<input type="radio" disabled="disabled" name="otherRes" id="'+resId+'" />'+
			'</div> <span class="hyx-scp-zz"></span>'+
		'</td>'+
	    '<td id="'+resId+'" name="otherRes_Td_'+resId+'"><div class="overflow_hidden w100" title="'+name+'">'+name+'</div></td>'+
    	'<td id="'+resId+'" name="otherRes_Td_'+resId+'"><div class="overflow_hidden w100" phone="tel" title="'+phone+'">'+phone+'</div></td>';
	 $obj.html(html);
	 /*表单优化*/
	 $('.skin-minimal input').iCheck({
	     checkboxClass: 'icheckbox_minimal',
	     radioClass: 'iradio_minimal',
	     increaseArea: '20%'
	 });
}

//全部刷新
function getFresh( upOrDown){
	var timestamp = new Date().getTime();
	var resourceGroupId = $("#resourceGroupId").val();
	var orderType = $("#orderType").val();
	var isConcat = $('#isConcat').val();
	document.location.href = ctx +'/res/tao/getFresh?timestamp='+timestamp+ '&resourceGroupId='+resourceGroupId+"&orderType="+orderType+"&isConcat="+isConcat+"&upOrDown="+upOrDown;
}
//刷新操作页面
function getRes(resId,upOrDown,isFromOtherPage){
	var resourceGroupId = $("#resourceGroupId").val();
	var orderType = $("#orderType").val();
	var isConcat = $('#isConcat').val();
	$.ajax({
		url : ctx+'/res/tao/getMyRes',
		data:{resId:resId,upOrDown:upOrDown,resourceGroupId:resourceGroupId,orderType:orderType,isConcat:isConcat,isFromOtherPage:isFromOtherPage},
		dataType:'json',
		type : 'post',
		async : false,
        error:function(jqXHR, textStatus, errorThrown){   
            if(textStatus=="timeout"){  
                alert("加载超时，请重试");  
            }else{   
                alert(textStatus);   
            }  
        },
		success : function(data) {
			if(isFromOtherPage=='1'){
				$('#isFromOtherPage').val(1);
			}else{
				$('#isFromOtherPage').val(0);
			}
			var status =data.status;
			var resId = data.resId;
			var isCall = data.isCall;
			var name = data.name;
			var phone = data.phone;
			var filterType = data.filterType;
			var type = data.type;
			var followId = data.followId;
			$('#followId').val(followId);
			if((status==2 || status ==3) && type==1 && filterType!=2){
				showCurr(resId);
				$('tr[id^="otherRes_Tr_"]').each(function(item,obj){
					del_checked_tr($(obj))
				})
				default_tr($('#otherRes_Tr_'+resId));
		    	$('#otherRes_Tr_'+resId).addClass("hyx-scp-nowtr");
				taoMainPlatform(resId); //联系显示
				//setTimeout(function(){taoMainPlatform(resId);},10000);
				taoCustGuide(resId);//产品列表显示
			}else{
				//修改状态
				updateStatus(status,resId,isCall,name,phone,filterType,type);
				if(upOrDown==1){
					up();
				}else{
					next();
				}
				
			}
			isSubmited = false;
		}
	});
}
//选择筛选排序
function selectOrder(val){
	$('#orderType').val(val);
	var resourceGroupId = $("#resourceGroupId").val();
	var orderType = val;
	var isConcat = $('#isConcat').val();	
	document.location.href = ctx +'/res/tao/updateTaoTag?resourceGroupId='+resourceGroupId+"&orderType="+orderType+"&isConcat="+isConcat;
	$("#refreshOneId").val("1");	
}

//选择筛选是否联系过的资源
function selectConcat(val){
	$('#isConcat').val(val);
	var resourceGroupId = $("#resourceGroupId").val();
	var orderType = $("#orderType").val();
	var isConcat = val;	
	document.location.href = ctx +'/res/tao/updateTaoTag?resourceGroupId='+resourceGroupId+"&orderType="+orderType+"&isConcat="+isConcat;
	$("#refreshOneId").val("1");
}
/**淘客户修改资料时局部刷新*/
function taoMainPlatform(custId){
	$.ajax({
		url:ctx+'/res/tao/taoMainPlatform',
		type:'post',
		data:{custId:custId},
		dataType:'html',
		error:function(data){},
		success:function(data){
			$('#updateCardId').html(data);
			queryTaoByCustId(custId);
		}
	});
}

//其他资源
function getRemarkList(){
	$.ajax({
		url:ctx+'/res/tao/getRemarkList',
		type:'post',
		dataType:'html',
		error:function(data){},
		success:function(data){
			$('#taoCustDelayId').html(data);
		}
   	});
}

//销售导航
function taoCustGuide(custId){
	$.ajax({
		url:ctx+'/res/tao/taoCustGuide',
		type:'post',
		data:{custId:custId},
		dataType:'html',
		error:function(data){},
		success:function(data){
			$('#taoCustGuideId').html(data);
			$.parser.parse();
		},error:function(){
			alert("error");
		}
  	});
}

function queryTaoByCustId(custid) {
	var currTime =new Date().getTime();
	var orgId = $('#orgId').val();
	var timeElngth = 0;
	var inputAcc = $('#account').val();
    var par ="orgId="+orgId+"&timeLength="+timeElngth+"&custId="+custid+"&inputAcc="+inputAcc+'&v='+currTime;
    $.ajax({
        type: "get",
        url: "http://call.qftx.net/service/queryTaoByCustId?" + par,
//        url:"http://192.168.1.15:6090/service/queryTaoByCustId?" + par,
        dataType: "jsonp",
        jsonp: "callback",//传递给请求处理程序或页面的，用以获得jsonp回调函数名的参数名(一般默认为:callback)
        jsonpCallback: "flightHandler",//自定义的jsonp回调函数名称，默认为jQuery自动生成的随机函数名，也可以写"?"，jQuery会自动为你处理数据
        success: function (json) {
            var str ="";
            str+=JSON.stringify(json);
//            str += '\n通话类型=' + json[0].name + "  通话数=" + json[0].num;
//            str += '\n通话类型=' + json[1].name + "  通话数=" + json[1].num;
//            str += '\n通话类型=' + json[2].name + "  通话数=" + json[2].num;
//            alert(JSON.stringify(json))
            var allCallNum = parseInt(json[2].num);
            var totalTimeElngth = parseInt(json[0].num);
            var calledNum = parseInt(json[1].num);
            if(allCallNum==0  || calledNum==0 || totalTimeElngth ==0){
            	$('#callTimeId').html("--");
            	$('#callTimeId').css({'margin-top':'20px'});
//            	$('#avgId').html('--');
            	$('#rateId').html('--');
            }else{
            	$('#callTimeId').html(calledNum+ '次');
            	$('#avgId').html(Math.round(totalTimeElngth /calledNum)+"秒/次")
            	$('#rateId').html(((calledNum /allCallNum)*100).toFixed(2) + '%');
            }
        },
        error: function () {
        }
    });
}

function setIsLianxi(custId,phone,name,defined1){
	var custFollowId = $('#followId').val();
	 $('#concat_phone').val(phone);
	 $('#concat_name').val(name);	
	if(custId==null || custId ==''){
		window.top.iDialogMsg('提示',"资源id 不存在，刷新页面试试！！！");
		return false;
	}
	taoCallPhone(phone,custId,name,custFollowId,defined1);
	queryTaoByCustId(custId)
	updatePlanResult(custId)
}

// 打电话公共方法
function taoCallPhone(phone,resCustId,name,followId,defined1){
	var arrays = new Array();
	arrays[0] = "\"custId\":\""+resCustId+"\"";
	arrays[1] = "\"custName\":\""+name+"\"";
	arrays[2] = "\"followId\":\""+followId+"\"";
	arrays[3] = "\"custType\":\""+1+"\"";
	arrays[4] = "\"custState\":\""+2+"\"";
	arrays[5] = "\"account\":\""+$('#account').val()+"\"";
	arrays[6] = "\"orgId\":\""+$('#orgId').val()+"\"";	
	arrays[7] = "\"define1\":\""+defined1+"\"";
	window.top.custCallPhone(phone,arrays,resCustId);
}
function uuid() {
    var s = [];
    var hexDigits = "0123456789abcdef";
    for (var i = 0; i < 36; i++) {
        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
    }
    s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
    s[8] = s[13] = s[18] = s[23] = "";

    var uuid = s.join("");
    return uuid;
}