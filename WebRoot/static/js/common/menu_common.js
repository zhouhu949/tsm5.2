/***********************************************

 * 菜单样式设置_专属JS文件
 * @author haoqj
 * @date 2013-06-07
 ***********************************************/
var _base = $('#base').val();
var wide;
$(function(){
	// 初始化菜单
	//initMenu();
	
	$('li[id^="menu_"]').each(function (){
		$(this).click(function(){
			var id = $(this).attr("id").substring(5);
//			blindEvent(id);
		});
	});
//	var resWidth = window.screen.width;
	var _h = 40;
	var _h1 = 60;
//	if(resWidth == 1280){
//		_h = 60;
//		_h1 = 80;
//	}
	$("#content_id").attr("style","float:left;width:100%;height:"+(getClientHeight()-_h)+"px;");
	wide=$("#tt").parent().width();
	$("#tt").tabs({
		width:wide,
		height:getClientHeight()-_h1
	});
});

function getClientHeight()
{
    //可见高
    var clientHeight=document.documentElement.clientHeight;//其它浏览器默认值
    if(navigator.userAgent.indexOf("MSIE 6.0")!=-1)
    {
        clientHeight=document.body.clientHeight;
    }
    else if(navigator.userAgent.indexOf("MSIE")!=-1)
    {
        //IE7 IE8
        clientHeight=document.documentElement.offsetHeight
    }

    if(navigator.userAgent.indexOf("Chrome")!=-1)
    {
        clientHeight=document.body.scrollHeight;
    }

    if(navigator.userAgent.indexOf("Firefox")!=-1)
    {
        clientHeight=document.documentElement.scrollHeight;
    }
    return clientHeight;
}

// 初始化菜单
function initMenu(){
	var pms = (window.location+'').split('/');
	var shortUrl = pms[pms.length -1];
	$('li[linkIndex^="index_"]').each(function (){
		var menuUrl = $(this).attr('alink');
		var url=menuUrl;
		
		//页面切换页面无法定位到菜单，所以暂时写死
		//我的客户，客户跟进记录
		if(url.indexOf("todayToFollowList.do")!=-1){
			url+="historyToFollowList.do";
		}
		//我的客户，客户跟进
		else if(url.indexOf("todayToFollowUpList.do")!=-1){
			url+="allToFollowUpList.do"+"followUpTheAlertList.do?tsmCustFollowDto.whetherTheAlarm=1";
		}
		//我的客户，签约记录
		else if(url.indexOf("custSignList.do")!=-1){
			url+="custOrderList.do"+"myTradeInfoList.do";
		}
		//团队管理，客户跟进记录
		else if(url.indexOf("todayToTeamFollowList.do")!=-1){
			url+="historyToTeamFollowList.do"+"allToTeamFollowList.do";
		}
		//团队管理，客户待跟进
		else if(url.indexOf("todayToTeamFollowUpList.do")!=-1){
			url+="allToTeamFollowUpList.do";
		}
		//统计分析，签约客户排行榜
		else if(url.indexOf("teamSignCustList.do")!=-1){
			url+="empSignCustList.do";
		}
		//统计分析，意向客户排行榜
		else if(url.indexOf("teamInterestCustList.do")!=-1){
			url+="empInterestCustList.do";
		}
		//统计分析，个人统计分析
		else if(url.indexOf("interestCust.do")!=-1){
			url+="signCust.do"+"dayAnalyze.do"+"monthAnalyze.do";
		}
		//团队统计分析，个人统计分析
		else if(url.indexOf("teamInterestCustPool.do")!=-1){
			url+="teamSignCustPool.do"+"teamDayAnalyze.do"+"teamMonthAnalyze.do"+"teamContrastAnalyze.do";
		}
		else if(url.indexOf("resourceUseStatistics.do")!=-1){
			url+="resourceUseStatistics.do";
		}
		//团队管理，意向客户池
		else if(url.indexOf("teamCustList.do")!=-1){
			url+="deferredAuditList.do" +"teamCustSaleProcess.do";
		}
		//团队管理，团队短信记录
		else if(url.indexOf("teamSendTsmSmslist.do")!=-1){
			url+="teamGetTsmSmslist.do";
		}
		//团队管理，订单管理
		else if(url.indexOf("teamOrderInfoList.do")!=-1){
			url+="teamTradeInfoList.do";
		}
		//团队管理，客户回访管理
		else if(url.indexOf("queryTeamTodayVisitList.do")!=-1){
			url+="queryTeamVisitList.do";
		}
		//我的客户，客户交接记录
		else if(url.indexOf("custIncomeList.do")!=-1){
			url+="custOutList.do";
		}
		//系统设置，系统属性设置
		else if(url.indexOf("propertyset.do")!=-1){
			url+="propertyset.do";
			/*url+="propertyset.do?option.itemCode=SALES_10005";
			url+="propertyset.do?option.itemCode=SALES_10001";
			url+="propertyset.do?option.itemCode=SALES_10002";
			url+="propertyset.do?option.itemCode=SALES_10004";
			url+="propertyset.do?option.itemCode=SALES_10006";
			url+="propertyset.do?option.itemCode=RECORD_1000";*/
		}
		//系统设置，销售管理设置
		else if(url.indexOf("salesmanage.do")!=-1){
			url+="salesmanage.do";
		}
		//系统设置，系统字段设置
		else if(url.indexOf("selfdefined.do")!=-1){
			url+="selfdefined.do";
		}
		else if(url.indexOf("queryKnowLegeMgListPage.do")!=-1){
			url+="toSaveOrUpdateKnowlege.do";
		}
		else if(url.indexOf("noticelist.do")!=-1){
			url+="jump.do";
		}
		else if(url.indexOf("myResList.do") != -1){
			url+="myResFollowUpList.do";
		}
		else if(url.indexOf("querySmsTemplateList.do") != -1){
			url+="updateTemplateList.do";
		}
		else if(url.indexOf("authList.do") != -1){
			url+="authGroupList.do";
		}
		else if(url.indexOf("newVisitPlanList.do") != -1){
			url+="custVisitPlanList.do"+"myReservates.do";
		}
		else if(url.indexOf("todayVisitList.do") != -1){
			url+="custVisitList.do";
		}
		shortUrl = shortUrl.split('?')[0];

		if(url.indexOf(shortUrl)!=-1 || url.indexOf(shortUrl.substring(0,shortUrl.length -3 ))!=-1){
			var indexs = $(this).attr('id').split('_');
			var menu1 = indexs[1];
			var menu2 = indexs[2];
			
//			$('#menu_'+menu1).attr('class','hover');
			if(menu2!='0'){
				$('#menuDiv_'+indexs[1]).show();
				$('#menu2_'+indexs[1]+'_'+indexs[2]).attr('class','active');
			}
		}
		
		if(menuUrl == '###'){ 	// 服务过期时，无权限
			$(this).click(function(){checkIsOpenService();});
		}else if(menuUrl!=''){
			$(this).click(function(){
//				window.location.href = _base + menuUrl;
			});
		}
		
	});
}

function blindEvent(id){
	$('.menu_dxgj li[class^="hover"]').each(function(i){
		var upId = $(this).attr("id").substring(5);
		$(this).attr('class','');
		$('#menuDiv_'+upId).hide();
	});
	var menu = $('#menu_'+id);
	var menu2 = $('#menuDiv_'+id);
	$('#img_'+id).attr("src",_base+"/css/tsm/images/menu_pic"+id+".png");
	menu.attr('class','hover');
	menu2.show();
 
  }

function addTabName(url,externalLinkIs,funNameTile,css,name){
	return addTab(url,externalLinkIs,funNameTile,css,name)
}
function addTab(url,externalLinkIs,funNameTile,css,name){
	var addId;
	var needRefresh=false;
	if(funNameTile.indexOf("_") > -1){
		var arr=funNameTile.split("_");
		funNameTile=arr[0];
		addId=arr[1];
		if(addId=='follow' && $('#tt').tabs('exists',funNameTile)){
			$('#tt').tabs('close',funNameTile);
			addId=undefined;
		}
	}
	$("#tt").find("ul.tabs li.tabs-selected").attr("class","");
	var visib=$("#tt").find("div.tabs-panels").children('div:visible');
	if($(visib).length>0){
		var vstyle=$(visib).attr("style");
		vstyle=vstyle.replace("display: block","display: none");
		$(visib).attr("style",vstyle);
	}
	if(funNameTile=='消息中心'&&$('#tt').tabs('exists',funNameTile)){$('#tt').tabs('close',funNameTile);}
	if($('#tt').tabs('exists',funNameTile)&& ($("#"+addId).length>0||funNameTile=='首页')){needRefresh=true;}
	if(needRefresh){
		if(funNameTile=='公海客户池'){
			$("#li"+addId).addClass('tabs-selected');
			var divstyle=$("#"+addId).parent().attr("style");
			divstyle=divstyle.replace("display: none","display: block");
			$("#"+addId).parent().attr("style",divstyle);
			document.getElementById('frm'+addId).contentWindow.document.forms[0].submit();
		}else{
			var tab=$('#tt').tabs('getTab',funNameTile);
			$('#tt').tabs('select',funNameTile);
			tab.panel('refresh');	
		}
	}else{
		var content='<iframe scrolling="yes" id="frm'+addId+'" name="'+name+'" frameborder="0"  src="'+_base+url+'" style="width:'+wide+'px;height:100%;"></iframe>';
		if(funNameTile == "淘客户" || funNameTile == "未回访明细"){
			if($('#tt').tabs('exists',funNameTile))
				$('#tt').tabs('close',funNameTile)
		}
		$('#tt').tabs('add',{
			id:addId,
			title:funNameTile,
			content:content,
			iconCls:css,
			closable:funNameTile!='首页'
		});
	}
}
function closeTab(title){
	$('#tt').tabs('close',title);
}
// 经实验，使用Cookie时无二级菜单时有效
// 有子菜单时取得Cookie值有混乱，可能是上次或前N次
//function setMenuCookie(index1,index2){
//	$.cookie('menu1Index',index1);
//	$.cookie('menu2Index',index2);
//}
//
//function getMenuCookie(){
//	var menu1_index = $.cookie('menu1Index');
//	var menu2_index = $.cookie('menu2Index');
//	alert(menu1_index+'_'+menu2_index);
//	if(menu1_index!= null && menu2_index!=null){
//		alert('test_'+menu1_index+'_'+menu2_index+'**********');
//	}else{
//		setMenuCookie(1,0);
//	}
//}