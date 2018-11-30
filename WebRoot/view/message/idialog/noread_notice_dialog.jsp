<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<script type="text/javascript">
//跳转至上一条或下一条
function jump_(indexSize,booleans){//第二个参数判断往左还是往右
	var sums= $("#indexSize").val();
	var curs=$("#indexCur").val();
	if(booleans){
		curs--;
	}else{
		curs++;
	}
	$("#alert_message_center_e_a>div>h2>span", window.parent.document).text("共"+sums+"封公告，当前处于第"+curs+"封");
	document.location.href = "${ctx}/message/getNOReadNoticeInfo.do?indexSize="+indexSize;
}

$(function(){
	var shadowhost = $(".shadow-box").get(0);
	var shadowroot = shadowhost.createShadowRoot();
	var resetCss = '<style>body{height:100%;}pre{overflow:hidden;margin:0;padding:0;border:none;background-color:transparent;text-overflow:ellipsis;white-space:pre-wrap;word-wrap:break-word}a{color:#333}a,a:hover{text-decoration:none}a[name=icon-play]{color:#337ab7}label{font-weight:400}textarea{height:75pt}.app-header{background-color:#fafafa}.app-menu{position:relative;border-bottom:1px solid #e4e4e4;font-size:14px}.app-menu>.logout-btn{position:absolute;top:5px;right:10px}.app-menu .navbar-nav{float:left}.app-menu .navbar-nav>.dropdown{float:left;height:40px}.app-menu .navbar-nav>.dropdown:last-child{display:none}.app-menu .navbar-nav>.dropdown>a{padding-top:10px;padding-bottom:10px;color:#333}.app-menu .navbar-nav>.dropdown:active,.app-menu .navbar-nav>.dropdown:hover,.app-menu .navbar-nav>.dropdown:visited{background-color:#469bff}.app-menu .navbar-nav>.dropdown:active>a,.app-menu .navbar-nav>.dropdown:hover>a,.app-menu .navbar-nav>.dropdown:visited>a{color:#fff}.app-menu .navbar-nav>.dropdown:active>a:active,.app-menu .navbar-nav>.dropdown:active>a:hover,.app-menu .navbar-nav>.dropdown:active>a:visited,.app-menu .navbar-nav>.dropdown:hover>a:active,.app-menu .navbar-nav>.dropdown:hover>a:hover,.app-menu .navbar-nav>.dropdown:hover>a:visited,.app-menu .navbar-nav>.dropdown:visited>a:active,.app-menu .navbar-nav>.dropdown:visited>a:hover,.app-menu .navbar-nav>.dropdown:visited>a:visited{background-color:#469bff}.app-menu .navbar-nav>.dropdown:active .dropdown-menu,.app-menu .navbar-nav>.dropdown:hover .dropdown-menu,.app-menu .navbar-nav>.dropdown:visited .dropdown-menu{display:block}.app-tabs{position:relative;overflow:hidden;border-bottom:1px solid #ddd}.app-tabs .menu-leftbtn,.app-tabs .menu-rightbtn{position:absolute;top:0;z-index:99;visibility:hidden;box-sizing:border-box;width:20px;height:26px;border:1px solid #ddd;background-color:#fff;color:#ddd;text-align:center;line-height:26px;cursor:pointer}.app-tabs .menu-leftbtn:hover,.app-tabs .menu-rightbtn:hover{color:#333}.app-tabs .menu-leftbtn{left:0}.app-tabs .menu-rightbtn{right:0}.app-tabs .nav.nav-tabs{position:relative;clear:both;overflow-x:hidden;overflow-y:hidden;margin:0 20px;border-bottom:none;white-space:nowrap}.app-tabs .nav.nav-tabs>li{float:none;display:inline-block}.app-tabs .nav.nav-tabs>li.active a{border-top:none}.app-tabs .nav.nav-tabs>li a,.app-tabs .nav.nav-tabs>li a:hover{border-bottom-color:#ddd}.app-tabs .nav.nav-tabs>li a.app-tabs-name{padding:0;width:150px;height:26px;border-radius:0;color:#555;text-align:center;font-size:14px;line-height:26px}.app-tabs .nav.nav-tabs>li a.app-tabs-close{position:absolute;top:7px;right:7px;margin:0;padding:0;width:9pt;height:9pt;border:none;background-color:transparent;color:#333;line-height:9pt;cursor:pointer}.app-tabs .nav.nav-tabs>li a.app-tabs-close:hover{font-weight:700}.app-tabs .nav.nav-tabs>li:first-of-type .app-tabs-close{display:none}.app-main{position:absolute;top:66px;right:0;bottom:0;left:0}.app-main .app-content>.tab-pane{position:absolute;top:0;right:0;bottom:0;left:0}.container .nav.nav-tabs{padding:5px 0;border-bottom:none;background-color:#e4e4e4;text-align:center}.container .nav.nav-tabs>.datastatictabs,.container .nav.nav-tabs>.hooksmstabs{float:none;display:inline-block;margin-right:0;margin-right:-5px;border:1px solid #ccc;background-color:#fff;color:#000}.container .nav.nav-tabs>.datastatictabs.active,.container .nav.nav-tabs>.datastatictabs.active>a,.container .nav.nav-tabs>.hooksmstabs.active,.container .nav.nav-tabs>.hooksmstabs.active>a{background-color:#0e9eff;color:#fff}.container .nav.nav-tabs>.datastatictabs>a,.container .nav.nav-tabs>.hooksmstabs>a{display:inline-block;padding:4px 15px;width:100%;border:none;color:#333}.container .nav.nav-tabs>.datastatictabs{width:190px}.container .nav.nav-tabs>.hooksmstabs{width:285px}.app-content .container{padding-top:10px;width:100%;height:100%}.app-content .container .cmp-search{margin:0 auto;margin-bottom:10px}.app-content .container .container-side{width:200px;background-color:#f5f5f5}.app-content .container .container-side-head{height:30px;background-color:#e1e1e1;text-align:center;line-height:30px}.text-indent1{text-indent:.5em}.text-indent2{text-indent:1em}.text-indent3{text-indent:1.5em}.text-indent4{text-indent:2em}.text-indent5{text-indent:2.5em}.text-indent6{text-indent:3em}.text-indent7{text-indent:3.5em}.text-indent8{text-indent:4em}.text-indent9{text-indent:4.5em}.text-indent10{text-indent:5em}.groupDataStatic .echartsBoxParent,.personalDataStatic .echartsBoxParent{position:relative;margin-top:20px;width:100%;height:300px}.groupDataStatic .echartsBoxParent .echartsBox,.personalDataStatic .echartsBoxParent .echartsBox{position:absolute;top:0;left:0;visibility:hidden;width:100%;height:300px}.groupDataStatic .tab-content,.personalDataStatic .tab-content{padding-top:20px}.groupDataStatic .tab-content>.tab-pane,.personalDataStatic .tab-content>.tab-pane{visibility:hidden}.groupDataStatic .tab-content>.active,.personalDataStatic .tab-content>.active{visibility:visible}.groupDataStatic .tab-content table,.personalDataStatic .tab-content table{margin:20px auto}.groupDataStatic .tab-content .output-btn,.personalDataStatic .tab-content .output-btn{float:right}.groupDataStatic .bootstrap-select.btn-group .dropdown-toggle .filter-option,.personalDataStatic .bootstrap-select.btn-group .dropdown-toggle .filter-option{padding:8px 5px}.groupDataStatic .btn-link.activeNow,.personalDataStatic .btn-link.activeNow{text-decoration:underline}.hookSms .tab-content{padding-top:20px}.reminder{margin-top:20px}.modal-header{padding:10px 15px}.modal-header .modal-title{font-size:14px}.modal-body{overflow:auto;max-height:25pc}.orgIdiaglog .modal-body{height:450px;max-height:450px}.modal-sm .modal-body{overflow:visible}.modal-footer{padding:10px 15px;text-align:center}.modal-footer>span>i{color:red;font-style:normal}.bootstrap-select.btn-group .dropdown-menu{font-size:9pt}.bootstrap-select.btn-group .dropdown-menu li.selected,.bootstrap-select.btn-group .dropdown-menu li.selected:hover,.bootstrap-select.btn-group .dropdown-menu li.selected>a{background-color:#469bff;color:#fff}.bootstrap-select.btn-group .dropdown-toggle .filter-option{color:#333}.modal-content .bootstrap-select.form-control{padding-right:0}.modal-content .bootstrap-select.form-control>.dropdown-toggle{margin-right:0;height:36px;background-color:#fff}.ieltalert{position:fixed;top:0;left:50%;z-index:9999;margin-left:-190px}.ieltalert>a{display:inline-block;height:21px;line-height:21px}.ieltalert .close{font-size:18px}</style>';
	shadowroot.innerHTML=resetCss+"<span>"+$("#noticeContent").val()+"</span>";
})
</script>
<title>公告栏-通知公告 </title>
<!--公共样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css"><!--弹框样式-->
</head>
<body>

		<c:if test="${indexSize ne 0}"> <!-- 0 表示第一条 -->
			<div class="changeBtn_left" onclick="jump_(${indexSize - 1},true)"><img src="${ctx}/static/images/yd_l.png" alt="加载图片"></div>
		</c:if>
		<c:if test="${isLast ne 1 }"> <!-- 不等于1 表示不是最后一条 -->
			<div class="changeBtn_right" onclick="jump_(${indexSize + 1},false)"><img src="${ctx}/static/images/yd_r.png" alt="加载图片"></div>
		</c:if>
	
<input type="hidden" id="indexCur" value="${indexSize+1 }"><!-- 当前条数在列表中的位置 从0开始  -->
<input type="hidden" id="indexSize" value="${sum }"><!-- 总条数 -->
<div class='bomp-cen bomp-cma'>
	<div class="bomp-mce-form">
		<h2 class="fl_l">${dto.title}</h2>
		<div class="tit fl_l">
			<p>
				<label class="tit_box title-box-first"><label>发布人：</label><span class="publisher-name" title="${dto.sendAcc }">${dto.sendAcc }</span></label>
				<label class="tit_box title-box-second"><label>发布时间：</label><span><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${dto.sendDate}"/></span></label>
			</p>
		</div>
		<div class="cont fl_l">
			<input type="hidden" id="noticeContent" value='${dto.msgCenterContent}'/>
			<div class="cont_cen shadow-box"></div>
		</div>
	</div>
<%-- 	<div class='bomb-btn'>
		<label class='bomb-btn-cen'>
			<c:if test="${indexSize ne 0}"> <!-- 0 表示第一条 -->
				<a href="javascript:;" onclick="jump_(${indexSize - 1})" class="com-btna bomp-btna fl_l"><label>上一条</label></a>
			</c:if>
			<c:if test="${isLast ne 1 }"> <!-- 不等于1 表示不是最后一条 -->
				<a href="javascript:;" onclick="jump_(${indexSize + 1})"  class="com-btna bomp-btna fl_l"><label>下一条</label></a>
			</c:if>
		</label>
	</div> --%>
</div>
</body>
</html>
