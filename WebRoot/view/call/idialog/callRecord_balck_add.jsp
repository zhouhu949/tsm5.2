<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
<head>
<%@ include file="/common/include.jsp"%>
	<title>添加黑名单</title>
	<!--公共样式-->
	<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css"><!--主要样式-->
	
	<script type="text/javascript">
	var isSubmited = false;
	$(function(){
	    $(".sure-btn").click(function(){
	    	var isPass = checkIsNull();
			if(!isSubmited && isPass){
				isSubmited = true;
					$("#myForm").ajaxSubmit({
						url : '${ctx}/callrecord/black/addBlack.do',
						type : 'post',
						error : function(XMLHttpRequest, textStatus){alert("请求失败！");},
						success : function(data) {							
							if(data == 0){
								// 默认刷新主页面
								window.top.iDialogMsg("提示","保存成功！");
								setTimeout('window.parent.document.forms[0].submit();',1000);							
							}else{
								// 提示失败
								window.top.iDialogMsg("提示","保存失败！");
							}
						}
					});			
			}
		});
	    
	    // 取消
		$(".cancel-btn").click(function(){
			closeParentPubDivDialogIframe('alert_black_list_a');
		});
	});
	
	function checkIsNull(){
		var isTrue = true;
    	
    	return isTrue;
	}
	</script>
</head>
<body>
	<form id="myForm" method="post">
	<div class="cust-impo-res" style="width:auto">
		<div class="com-search clearfix" style="min-height:30px;z-index:999">
			<label class="fl_l" for=""><span class="font-color">*</span>电话号码：</label>
			<input type="text" name="phone" id="phone"><span class="font-color">必填项</span>
		</div>
		<div class="com-search clearfix" style="min-height:30px;z-index:999">
			<label class="fl_l" for="">举报原因：</label>
		</div>
		<div class="report-reason clearfix">

			<div class="skin-minimal clearfix"><input type="radio"checked="checked" name="reason"><span class="fl_l">诈骗电话（如谎称欠费、涉案、退税、老熟人等）</span></div>
			<div class="skin-minimal clearfix"><input type="radio" name="reason"><span class="fl_l" >骚扰电话（如"响一声"电话、辱骂骚扰等）</span></div>
			<div class="skin-minimal clearfix"><input type="radio" name="reason"><span class="fl_l">非法业务（从事非法业务或相关诈骗活动）</span></div>
			<div class="skin-minimal clearfix"><input type="radio" name="reason"><span class="fl_l">中介（如房产中介、票务中介等）</span></div>
			<div class="skin-minimal clearfix"><input type="radio" name="reason"><span class="fl_l">商家（各类商家）</span></div>
			<div class="skin-minimal clearfix"><input type="radio" name="reason"><span class="fl_l">其他</span></div>
		</div>
	</div>
	<div class="bomb-btn">
			<label class="bomb-btn-cen">
				<a class="com-btna bomp-btna com-btna-sty sure-btn fl_l" href="javascript:;"><label>确定</label></a>
				<a class="com-btna bomp-btna cancel-btn fl_l" href="javascript:;"><label>取消</label></a>
			</label>
		</div>
		</form>
</body>
</html>