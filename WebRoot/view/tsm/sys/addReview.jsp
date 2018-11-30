<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/include.jsp"%>
	<!--公共样式-->
	<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css"><!--主要样式-->
<%-- 	<link rel="stylesheet" type="text/css" href="${ctx}/static/js/idialog/skins/iblue.css"> --%>
    <script type="text/javascript" src="${ctx}/static/js/common/common.js"></script>
	<script type="text/javascript">
	$(function(){
	    $(".sure-btn").click(function(){
			$("#form").ajaxSubmit({
				dataType:'json',
				success:function(data){
					if(data['result'] == "1"){
						window.top.iDialogMsg("提示","保存成功！");
						setTimeout('window.parent.document.forms[0].submit();',1000);
					}else{
						window.top.iDialogMsg("提示","保存失败！");
					}					
				}
			});    	
		});
		$(".cancel-btn").click(function(){
			closeParentPubDivDialogIframe('add_review');
		});
	});
	
	function setExmple(id){
		$('#recordExampId').val(id);
	}
	</script>
</head>
<body>
<form action="${ctx }/sys/record/addReview" method="post">
<input type="hidden" name='recordExampId' id='recordExampId' id="form">
			<div class="cust-impo-res" style="width:auto">
				<div class="com-search clearfix" style="min-height:30px;z-index:999">
					<label class="fl_l" for="">示范分类：</label>
					<dl class="select" style="z-index:999">
						<dt>--请选择--</dt>
						<dd>
							<ul style="width:134px;">
								<li><a href="#">--请选择--</a></li>
							    <c:if test="${!empty oplist }">
							         <c:forEach items="${oplist }" var="exmple">
							             <li onclick="setExmple('${exmple.optionlistId}')" >${exmple.optionName }</li>
							         </c:forEach>
							    </c:if>
							</ul>
						</dd>
					</dl>
				</div>
				<div class="sound-prenr clearfix">
					<label class="fl_l" for="">点评内容：</label>
					<textarea name="revComment" id="revComment" cols="35" rows="10" ></textarea>
				</div>
			</div>
			<div class="bomb-btn" style="margin-top:40px">
				<label class="bomb-btn-cen">
					<a class="com-btna bomp-btna com-btna-sty sure-btn fl_l" href="###"><label>确定</label></a>
					<a class="com-btna bomp-btna cancel-btn fl_l" href="###"><label>取消</label></a>
				</label>
			</div>
</form>
</body>
</html>