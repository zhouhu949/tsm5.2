<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>销售计划管理-月度计划（团队编辑）-编辑页面（团队）-偏差值编辑</title>
<!--公共样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css"><!--弹框样式-->
<script type="text/javascript">
var groupType="${plan.groupType}";
$(function(){
	var flag = true;
    $(".sure-btn").click(function(){
    	$("input,textarea").each(function(i,item){
    		if(!check(item)){
    			flag=false;
    		}
    	});
    	
    	if(flag){
    		$(".warp-plan").each(function(i,item){
    			var sign = $(item).find(".sel").val();
    			var value = $(item).find(".ipt").val();
        		
    			if(sign=="-"){
    				value=-value;
    			}
    			if(groupType=="1"){
    				window.parent.$("#iframepage")[0].contentWindow.warp(i,value);
    			}else{
    				window.parent.warp(i,value);
    			}
    			
        	});
    		if(groupType=="1"){
    			window.parent.$("#iframepage")[0].contentWindow.warp(3,$("#dcontext").val());
    		}else{
    			window.parent.warp(3,$("#dcontext").val());
    		}
        	closeParentPubDivDialogIframe("warpWindow");
    	}
	});
    
    $("input").blur(function(){
		var _this = this;
		if(check(_this)){
			val=$($(_this).siblings(".sel")).val();
	    	if(val=="+"){
	    		$($(_this).siblings(".mte_a")[1]).text(accAdd(parseFloat($($(_this).siblings(".mte_a")[0]).text()),parseFloat($(_this).val())));
	    	}else{
	    		$($(_this).siblings(".mte_a")[1]).text(accAdd(parseFloat($($(_this).siblings(".mte_a")[0]).text()),-parseFloat($(_this).val())));
	    	}
		}
    });
    function accAdd(arg1,arg2){ 
    	var r1,r2,m; 
    	try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0} 
    	try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0} 
    	m=Math.pow(10,Math.max(r1,r2)); 
    	return (arg1*m+arg2*m)/m; 
    } 
    
    $("textarea").blur(function(){
    	check(this);
    });
    
    $(".sel").change(function(){
    	var _this = this;
    	val=$(_this).val();
    	if(val=="+"){
    		$(_this).siblings(".mte_down").attr("class","mte_up");
    		$($(_this).siblings(".mte_a")[1]).text(accAdd(parseFloat($($(_this).siblings(".mte_a")[0]).text()),parseFloat($($(_this).siblings("input")).val())));
    	}else{
    		$(_this).siblings(".mte_up").attr("class","mte_down");
    		$($(_this).siblings(".mte_a")[1]).text(accAdd(parseFloat($($(_this).siblings(".mte_a")[0]).text()),-parseFloat($($(_this).siblings("input")).val())));
    	}
    	
    });
    
    $("input").focusin(function(){
		$(this).siblings("span").remove();
	}); 
    $("textarea").focusin(function(){
		$(this).parent().siblings("span").remove();
	}); 
    
	$(".cancel-btn").click(function(){
		closeParentPubDivDialogIframe("warpWindow");
	});

	/*textarea提示信息*/
    $('.bomp-sce-area').find('textarea,span').click(function(){
    	var ipt_a = $(this).parent().find('textarea');
        /* ipt_a.focus(); */
        $(this).parent().find('span').hide();
        ipt_a.blur(function(){
            if($(this).val() == ''){
                $(this).parent().find('span').show();
            }
         }); 
    });
});
 
function check(_this){
	var reg1=/^\d{1,5}$/;
	var reg2=/^\d{1,5}(\.\d{1,2})?$/;
	var flag=false;
	var msg='';
	
	var _clazz = $(_this).attr("class");
	var value = $(_this).val();
	value = value.replace(/(^\s+)|(\s+$)/);
	
	if(_clazz.indexOf("doubleNumber")!=-1){
		//double
		if(reg2.test(value)) {
			flag=true;
		}else{
			msg='请输入5位小数，小数点后2位！';
		}
	}else if(_clazz.indexOf("intNumber")!=-1){
		//int
		if(reg1.test(value)) {
			flag=true;
		}else{
			msg='请输入5位整数！';
		}
	}else{
		if(value.length>500) {
			msg='最大长度为500！';
		}else{
			flag=true;
		}
	}
	//TEXTAREA
	//input
	var type=$(_this)[0].tagName;
	if(!flag){
		if(type=="INPUT"){
			if($(_this).siblings("span").length==0){
				var html='<span class="bomp-red bomp-mteef-tip">'+msg+'</span>';
				$(_this).parent().append(html);
			}
		}else{
			if($(_this).parent().siblings("span").length==0){
				var html='<span class="bomp-red bomp-mteef-tip">'+msg+'</span>';
				$(_this).parent().parent().append(html);
			}
		}
		return false;
	}else{
		if(type=="INPUT"){
			$(_this).siblings("span").remove();
		}else{
			$(_this).parent().siblings("span").remove();
		}
		return true;
	}
}

</script>
</head>
<body> 
<div class='bomp-cen'>
	<p class="bomp-p bomp-mteef-p warp-plan">
		<label class='lab_a fl_l'>新增意向数：</label>
		<label class="mte_a">${plan.usersPlanWillcustnum}</label>
		<select class="sel">
			<option <c:if test="${plan.warpWillcustnum>=0}">selected="selected"</c:if>value="+">增加</option>
			<option <c:if test="${plan.warpWillcustnum<0}">selected="selected"</c:if>value="-">减少</option>
		</select>
		<input type="text" value="${plan.warpWillcustnum>=0?plan.warpWillcustnum:-plan.warpWillcustnum}" class="ipt intNumber" />
		<label class="mte_b">=</label>
		<label class="mte_a">${plan.usersPlanWillcustnum+plan.warpWillcustnum}</label>
		<label class="mte_a mte_c">个</label>
		<c:choose>
			<c:when test="${plan.warpWillcustnum>=0}">
				<label class="mte_up"></label>
			</c:when>
			<c:when test="${plan.warpWillcustnum<0}">
				<label class="mte_down"></label>
			</c:when>
		</c:choose>
	</p>
	<p class="bomp-p bomp-mteef-p warp-plan">
		<label class='lab_a fl_l'>新增签约数：</label>
		<label class="mte_a">${plan.usersPlanSigncustnum}</label>
		<select class="sel">
			<option <c:if test="${plan.warpSigncustnum>=0}">selected="selected"</c:if>value="+">增加</option>
			<option <c:if test="${plan.warpSigncustnum<0}">selected="selected"</c:if>value="-">减少</option>
		</select>
		<input type="text" value="${plan.warpSigncustnum>=0?plan.warpSigncustnum:-plan.warpSigncustnum}" class="ipt intNumber" />
		<label class="mte_b">=</label>
		<label class="mte_a">${plan.usersPlanSigncustnum+plan.warpSigncustnum}</label>
		<label class="mte_a mte_c">个</label>
		<c:choose>
			<c:when test="${plan.warpSigncustnum>=0}">
				<label class="mte_up"></label>
			</c:when>
			<c:when test="${plan.warpSigncustnum<0}">
				<label class="mte_down"></label>
			</c:when>
		</c:choose>
		
	</p>
	<p class="bomp-p bomp-mteef-p warp-plan">
		<label class='lab_a fl_l'>回款金额：</label>
		<label class="mte_a">${plan.usersPlanMoney}</label>
		<select class="sel">
			<option <c:if test="${plan.warpMoney>=0}">selected="selected"</c:if>value="+">增加</option>
			<option <c:if test="${plan.warpMoney<0}">selected="selected"</c:if>value="-">减少</option>
		</select>
		<input type="text" value="${plan.warpMoney>=0?plan.warpMoney:-plan.warpMoney}" class="ipt doubleNumber" />
		<label class="mte_b">=</label>
		<label class="mte_a"><fmt:formatNumber value="${plan.usersPlanMoney+plan.warpMoney}" pattern="0.##" />  </label>
		<label class="mte_a mte_c">万元</label>
		<c:choose>
			<c:when test="${plan.warpMoney>=0}">
				<label class="mte_up"></label>
			</c:when>
			<c:when test="${plan.warpMoney<0}">
				<label class="mte_down"></label>
			</c:when>
		</c:choose>
	</p>
	<p class='bomp-p bomp-p-width'>
		<label class='lab_a fl_l'>调整原因：</label>
		<label class="bomp-sce-area">
			<textarea id="dcontext" class='area_a w_b fl_l' style="height:125px;" value="${plan.warpDesc}">${plan.warpDesc}</textarea>
			<span>
				<c:if test="${plan.warpDesc ==null or plan.warpDesc==''}">最多可输入500个汉字</c:if>
			</span>
		</label>
	</p>
	<div class='bomb-btn bomb-btn-top'>
		<label class='bomb-btn-cen'>
			<a href="javascript:;" class="com-btna bomp-btna sure-btn com-btna-sty fl_l"><label>确定</label></a>
			<a href="javascript:;" class="com-btna bomp-btna cancel-btn fl_l"><label>取消</label></a>
		</label>
	</div>
</div>
</body>
</html>