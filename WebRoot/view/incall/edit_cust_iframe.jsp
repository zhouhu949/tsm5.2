<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<link href="${ctx}/static/css/dialog.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/static/js/view/tsm/res/safePhone.js"></script>
<script type="text/javascript">
var shiroIdsList = ${shiroIdsList};
var shrioAuthObj=function(propName){
	if(shiroIdsList == null) {
		return false;
	}
	return shiroIdsList.indexOf(propName)==-1?false:true;
	//return shiroIdsList.hasOwnProperty(propName);
}
$(function(){
    $('.bomp_ic_tip').find('li').click(function(){
    	$(this).addClass('li_click').siblings('li').removeClass('li_click');
    	var url =$(this).attr('name');
        var getTimestamp=new Date().getTime(); 
        if(url.indexOf('queryCustVisitListPage')>=0){
            $('#iframepage').attr('src',$('#serviceProjectUrl').val()+ '/'+url+'?custId='+$('#custId').val()+'&phone='+$('#phone').val()+'&isCommon='+$('#isCommon').val()+'&v='+getTimestamp);
         }else{
 	   $('#iframepage').attr('src',ctx+ '/'+ url+'?custId='+$('#custId').val()+'&phone='+$('#phone').val()+'&isCommon='+$('#isCommon').val()+'&v='+getTimestamp);             
         } 
    });
});

function to_cust_follow (){
	var getTimestamp=new Date().getTime();  
	 $('li[name$=custFollowPage]').addClass('li_click').siblings('li').removeClass('li_click');
	 $('#iframepage').attr('src',ctx+'/res/incall/custFollowPage?custId='+$('#custId').val()+'&isCommon='+$('#isCommon').val()+'&v='+getTimestamp);
}

function changeIframe (url){
	var getTimestamp=new Date().getTime();  
	 $('li[name$=toSearchRes]').addClass('li_click').siblings('li').removeClass('li_click');
	 $('#iframepage').attr('src',url+'?custId='+$('#custId').val()+'&isCommon='+$('#isCommon').val()+'&v='+getTimestamp);
}
</script>
</head>
<body> 
<div class="bomp_ic">
<input type="hidden" name="custId" id="custId" value="${custId}">
<input type="hidden" name="phone" id="phone" value="${phone }">
<input type="hidden" name="isCommon" id="isCommon" value="${isCommon }">
<input type="hidden" name="serviceProjectUrl" id="serviceProjectUrl" value="${serviceProjectUrl}">
	<div class="bomp_ic_tip">
		<ul class="list">
			<li class="li_click" name="res/incall/toSearchRes">客户信息</li>
			<li name='res/incall/custFollowPage'>客户跟进</li>
			<li name="res/incall/findFollowList">行动记录</li>
			<li name="res/incall/callList">通话记录</li>
			<li name="res/incall/queryReviewList">评论记录</li>
			<li name="res/incall/queryContractList">合同信息</li>
			<li name="res/incall/queryOrderList">订单信息</li>
			<li name="res/incall/queryCustVisitListPage">服务记录</li>
		</ul>
	</div>
	<div class="bomp_ic_bot">
		<iframe src="${ctx }/res/incall/toSearchRes?custId=${custId}&isCommon=${isCommon}" width="100%" height="100%" id="iframepage" frameborder="0" scrolling="no" marginheight="0" marginwidth="0"></iframe>
	</div>
</div>
</body>
</html>