<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ include file="/common/include.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css" />
<link href="${ctx}/static/css/skin/default/color.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/js/form/skins/all.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
$(function(){
	$("input").focus();
	})
</script>
</head>
<body>
	<form action="${ctx}/res/cust/myRests" method="post">
		<div class='bomp-cen' id='alert_b'>
			<p class='bomp_tit'><label>企业信息</label></p>
			<div class='bomp-p'>
				<label class='lab_a fl_l'>客户名称：</label><input type='text' value='' class='ipt_a fl_l' />
				<label class='lab_a fl_l'>注册资本：</label><input type='text' value='' class='ipt_a fl_l' />
				<label class='lab_a fl_l'>资源分组：</label><select class='sel_a fl_l'><option>-请选择-</option><option>未分组</option></select>
			</div>
			<div class='bomp-p'>
				<label class='lab_a fl_l'>所属行业：</label><input type='text' value='' class='ipt_a fl_l' />
				<label class='lab_a fl_l'>法人代表：</label><input type='text' value='' class='ipt_a fl_l' />
				<label class='lab_a fl_l'>企业传真：</label><input type='text' value='' class='ipt_a fl_l' />
			</div>
			<div class='bomp-p skin-minimal'>
				<label class='lab_a fl_l'>公司网站：</label><input type='text' value='' class='ipt_a w_a fl_l' />
				<input type='checkbox' class='fl_l' /><label class='lab_b fl_l'>重点关注</label>
			</div>
			<div class='bomp-p'>
				<label class='lab_a fl_l'>所在地区：</label><select class='sel_a sel_mar fl_l'><option>-请选择-</option><option>浙江</option></select><select class='sel_a sel_mar fl_l'><option>-请选择-</option></select><select class='sel_a sel_mar fl_l'><option>-请选择-</option></select>
			</div>
			<div class='bomp-p'>
				<label class='lab_a fl_l'>联系地址：</label><input type='text' value='' class='ipt_a w_b fl_l' />
			</div>
			<div class='bomp-p'>
				<label class='lab_a fl_l'>经营范围：</label><input type='text' value='' class='ipt_a w_b fl_l' />
			</div>
			<p class='bomp_tit bomp_tit_a'><label>联系人信息</label></p>
			<div class='bomp-p'>
				<label class='lab_a fl_l'>联系人：</label><input type='text' value='' class='ipt_a fl_l' />
				<label class='lab_a fl_l'>常用电话：</label><input type='text' value='' class='ipt_a fl_l' />
				<label class='lab_a fl_l'>备用电话：</label><input type='text' value='' class='ipt_a fl_l' />
			</div>
			<div class='bomp-p skin-minimal'>
				<label class='lab_a fl_l'>职务：</label><input type='text' value='' class='ipt_a fl_l' />
				<label class='lab_a fl_l'>生日：</label><input type='text' value='' class='ipt_a fl_l' />
				<label class='lab_a fl_l'>性别：</label><input name="sex" type="radio" value="" class="radio_a fl_l" /><label class="lab_d fl_l">男</label><input name="sex" type="radio" value="" class="radio_a fl_l" /><label class="lab_d fl_l">女</label>
			</div>
			<div class='bomp-p'>
				<label class='lab_a fl_l'>传真：</label><input type='text' value='' class='ipt_a fl_l' />
				<label class='lab_a fl_l'>关键字：</label><input type='text' value='' class='ipt_a fl_l' />
				<label class='lab_a fl_l'>邮箱：</label><input type='text' value='' class='ipt_a fl_l' />
			</div>
			<div class='bomp-p'>
				<label class='lab_a fl_l'>所在部门：</label><input type='text' value='' class='ipt_a fl_l' />
				<label class='lab_a fl_l'>IM：</label><input type='text' value='' class='ipt_a fl_l' />
			</div>
			<div class='bomp-p'>
				<label class='lab_a fl_l'>备注：</label><textarea class='area_a fl_l'></textarea>
			</div>
			<div class='bomb-btn'>
				<label class='bomb-btn-cen'>
					<a href="javascript:;" class="com-btna bomp-btna com-btna-sty fl_l"><label>保存</label></a>
					<a href="javascript:;" class="com-btna bomp-btna fl_l" id='close02'><label>取消</label></a>
				</label>
			</div>
		</div>
	</form>
</body>
</html>
