<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>系统设置-系统属性设置-客户放弃原因-编辑</title>
<!--公共样式-->
<link rel="stylesheet" type="text/css" href="${ctx }/static/css/dialog.css"><!--弹框样式-->
<script type="text/javascript">
var code = "";
var pid = "";
var errMsg1 = "数据重复";
var reeMsg2 = "不能为空";
var isSubmit = true;
$(function(){

    code = $("#code").val();
	pid = $("#pid").val();
	$('#addhtml').click(function(){
		var len = $("tr[name='tr']").length;
		$("#f"+len).after(
				'<tr name="tr" id="f'+(len+1)+'">'+
                   '<td>'+
                   		'<input type="text" name="options['+len+'].optionName" id="p'+(len+1)+'" ck="ck_1" maxlength="15" class="ipt_bor" />'+
                   		'<span class="err_text_1" id="err_p'+(len+1)+'"></span>'+
                   '</td>'+
                   '<td>'+
                   		'<input type="text" name="options['+len+'].sort" id="m'+(len+1)+'" ck="ck_2" maxlength="3" onkeyup="this.value=this.value.replace(\/\\D\/g, \'\');" onblur="this.value=this.value.replace(\/\\D\/g, \'\');" class="ipt_bor ipt_w140" />'+
                   		'<span class="err_text_1" id="err_m'+(len+1)+'"></span>'+
                   '</td>'+
                   '<td>'+
	                   	'<a href="javascript:del('+(len+1)+');" class="del alert_sys_pro_set_d_a" title="删除"></a>'+
                   '</td>'+
                   '<input type="hidden" name="options['+len+'].itemCode" value="'+code+'"/>'+
                   '<input type="hidden" name="options['+len+'].pid" value="'+pid+'"/>'+
                '</tr>'
		);
	});
	//动态绑定
	$("[ck^='ck_']").live('blur', function(){
		var id = $(this).attr('id');
		var chkType = $(this).attr('ck').split('_')[1];
		checkVal(id,chkType);
	});
	
    $(".sure-btn").click(function(){
    	if(isSubmit && checkAll()){
			isSubmit = false;
			$("#myForm").ajaxSubmit({
				url :'${ctx}/opt/set/addSubOption.do',
				type : 'post',
				dataType : 'json',
				error : function(XMLHttpRequest, textStatus){alert("请求失败！");},
				success : function(data) {
					if(data != 0){
						// 提示失败
						window.top.iDialogMsg("提示","保存失败！");
					}else{
					   // 默认刷新主页面
						window.top.iDialogMsg("提示","保存成功！");
						setTimeout('window.parent.frames[0].document.forms[0].submit();',1000);		
						setTimeout('closeParentPubDivDialogIframe("alert_sys_pro_set");',1000);	
					}
				}
			});
		}
	});
    
	$(".cancel-btn").click(function(){
		closeParentPubDivDialogIframe('alert_sys_pro_set');
	});
});

// 全选
function checkAll(){
	var isTrue = true;
	var r = 0;
	$("[ck^='ck_']").each(function(){
		var id = $(this).attr('id');
		var chkType = $(this).attr('ck').split('_')[1];
		var i = checkVal(id,chkType);
		if(!i){
			r++;
		}
	});
	if(r == 0){
		isTrue=true;
	}else{
		isTrue=false;
	}
	return isTrue;
}

// 验证
function checkVal(id,chkType){
	var val = $.trim($('#'+id).val());
	var flg = true;
	//验证非空
	if(val == ''){
		return setMsg(id,reeMsg2,false);
	}
	//验证当前表单中唯一
	if(chkType == '1'){
		$('[ck^="ck_1"]').each(function(i){
			var tmpv = $.trim($(this).val());
			var tmpid = $(this).attr('id');
			if(tmpid != id && tmpv != '' && tmpv == val){
				flg = false;
				setMsg(id,errMsg1,false);
				return false;
			}
		});
	}
	//验证当前表单中唯一
	if(chkType == '2'){
		$('[ck^="ck_2"]').each(function(){
			var tmpv = $.trim($(this).val());
			var tmpid = $(this).attr('id');
			if(tmpid != id && tmpv == val){
				flg = false;
				setMsg(id,errMsg1,false);
			}
		});
	}
	if(!flg){
		return false;
	}
	return setMsg(id,"",true);
}

//设置错误消息
function setMsg(id,msg,bool){
	$("#err_"+id).text(msg);
		return bool;
}

//删除行
function del(id){
	$("#f"+id).remove();
}
</script>
</head>
<body> 
<div class='bomp-cen' style="padding-bottom:0;">
	<form id="myForm" method="post">
	<input type="hidden" name="code" id="code" value="${code}"/>
	<input type="hidden" name="pid" id="pid" value="${pid}"/>
	<div class="com-table bomp_sps_table fl_l">
		<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius">
			<thead>
				<tr class="sty-bgcolor-b">
					<th><span class="sp sty-borcolor-b">子项名称</span></th>
					<th><span class="sp sty-borcolor-b">排序值</span></th>
					<th style="border-right: #f5f6f7 1px solid"><a href="javascript:;" id="addhtml" class="add alert_sys_pro_set_d_a" title="添加"></a></th>
				</tr>
			</thead>
			<tbody>    
			<c:choose>
                  	<c:when test="${!empty entitys}">
                  		<c:forEach items="${entitys}" var="entity" varStatus="v">
                  			<tr name="tr" id="f${v.count}">
								<td>
								<div class="overflow_hidden" ><input type="text" name="options[${v.index}].optionName" id="p${v.count}" ck="ck_1" value="${entity.optionName}" maxlength="10" class="ipt_bor" /></div>
								<span class="err_text_1" id="err_p${v.count}"></span>
								</td>
								<td>
								<div class="overflow_hidden w150" ><input type="text" name="options[${v.index}].sort" id="m${v.count}" ck="ck_2" value="${entity.sort}" maxlength="3"   class="ipt_bor ipt_w140" /></div>
								<span class="err_text_1" id="err_m${v.count}"></span>
								</td>
								<td style="width:70px;">
								<div class="overflow_hidden_01 w70 link">
								<c:choose>
		                   			<c:when test="${v.index eq '0'}">
		                   				&nbsp;
		                   			</c:when>
		                   			<c:otherwise>
		                   					<a href="javascript:del(${v.count});" class="del alert_sys_pro_set_d_a" title="删除"></a>
		                   			</c:otherwise>
		                   		</c:choose>
								</div></td>
								 <input type="hidden" name="options[${v.index}].itemCode" value="${code}"/>
		                   		<input type="hidden" name="options[${v.index}].pid" value="${pid}"/>
							</tr>
                  		</c:forEach>
                  	</c:when>
               	<c:otherwise>
                  		<tr name="tr" id="f1">
		                   <td>
		                   		<input type="text" name="options[0].optionName" id="p1" ck="ck_1" maxlength="10" class="ipt_bor" />
		                   		<span class="err_text_1" id="err_p1"></span>
		                   </td>
		                   <td>
		                   		<input type="text" name="options[0].sort" id="m1" ck="ck_2" maxlength="3" onkeyup="this.value=this.value.replace(/\D/g, '');" onblur="this.value=this.value.replace(/\D/g, '');" class="ipt_bor ipt_w140" />
		                   		<span class="err_text_1" id="err_m1"></span>
		                   </td>
		                   <td>&nbsp;</td>
		                   <input type="hidden" name="options[0].itemCode" value="${code}"/>
		                   <input type="hidden" name="options[0].pid" value="${pid}"/>
		                </tr>
                  	</c:otherwise>   	
            </c:choose>         
			</tbody>
		</table>
	</div>
	<div class='bomb-btn bomb-btn-top'>
		<label class='bomb-btn-cen'>
			<a href="javascript:;" class="com-btna bomp-btna com-btna-sty sure-btn fl_l" id="btn_save" ><label>保存</label></a>
			<a href="javascript:;" class="com-btna bomp-btna cancel-btn fl_l" ><label>取消</label></a>
		</label>
	</div>
	</form>
</div>
</body>
</html>