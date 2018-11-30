<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/include.jsp"%>
<html>
<head>
<title>系统设置-通知公告</title>
<script type="text/javascript">
$(function(){


  	/**  公告编辑 */
  	$("#add_").click(function(){
  		document.location.href = '${ctx}/notice/toAddOrEditPage';
  	});

	$('.alert_sys_pro_set_a_b').click(function(){
		var ids = "";
		$("input[name='check_box']:checked").each(function(){
			ids += $(this).val() + ",";
		});
		if(ids == ""){
			window.top.iDialogMsg("提示","请选择！");
			return false;
		}
		queDivDialog("res_remove_cust","是否删除？",function(){
			$.ajax({
				url:ctx+'/notice/delnotice',
				type:'post',
				data:{ids:ids},
				dataType:'html',
				error:function(){window.top.iDialogMsg("提示",'网络异常，请稍后再试！')},
				success:function(data){
					if(data == 0){
						window.top.iDialogMsg("提示",'删除成功！');
						setTimeout('document.forms[0].submit()',1000);
					}else{
						window.top.iDialogMsg("提示",'删除失败！');
					}
				}
			});
		});
	});
	//编辑点击
	$(".edit-detail").on("click",function(e){
		e.stopPropagation();
		var id=$(this).parents("tr").attr("announceId");
		editNotice(id);
	})
	
	//查看点击
	$(".see-detiel,a.notice-detail").on("click",function(e){
		e.stopPropagation();
		var id=$(this).parents("tr").attr("announceId");
		showNotice(id);
	})
	//点击行查看
	$("a.").on("click",function(e){
		e.stopPropagation();
		var id=$(this).parents("tr").attr("announceId");
		showNotice(id);
	})
});

// 编辑
function editNotice(id){
	document.location.href = '${ctx}/notice/toAddOrEditPage?id='+id+'&v='+new Date().getTime();
}

// 查看
function showNotice(id){
	document.location.href = '${ctx}/notice/toNoticeInfoPage?id='+id+'&v='+new Date().getTime();
}
</script>
</head>
<body>
<div class="hyx-na-tit fl_l"><label class="lab">通知公告</label></div>
<form action="${ctx }/notice/noticelist.do"  id="myForm"  method="post">
<div class="hyx-cfu-left hyx-ctr">
	<div class="hyx-na-top" style="padding-top:10px;">
		<div class="com-btnlist hyx-na-btnlist fl_l">
			<a href="javascript:;"  class="com-btna fl_l" id="add_"><i class="com_btn min-new-add"></i><label class="lab-mar">新&nbsp;&nbsp;&nbsp;&nbsp;增</label></a>
			<a href="javascript:;" class="com-btna alert_sys_pro_set_a_b fl_l"><i class="com_btn min-dele"></i><label class="lab-mar">删&nbsp;&nbsp;&nbsp;&nbsp;除</label></a>
		</div>
		<div class="hyx-na-ser fl_r">
			<input type="text" class="ipt" name="keyword" value="${item.keyword }">
			<a href="javascript:;" class="btn" onclick="document.forms[0].submit();"></a>
		</div>
	</div>
	<div class="com-table com-mta hyx-cla-table">
		<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius">
			<thead>
				<tr class="sty-bgcolor-b">
					<th><span class="sp sty-borcolor-b skin-minimal"><input type="checkbox" id="checkAll"  class="check" /></span></th>
					<th><span class="sp sty-borcolor-b">操作</span></th>
					<th><span class="sp sty-borcolor-b">标题</span></th>
					<th><span class="sp sty-borcolor-b">阅读人数</span></th>
					<th><span class="sp sty-borcolor-b">发布人</span></th>
					<th>发布时间</th>
				</tr>
			</thead>
			<tbody>
			<tbody>
				<c:choose>
			 	<c:when test="${!empty entitys}">
			 		<c:forEach items="${entitys }" var="entity" varStatus="v">
			 				<tr class="${v.count%2==0?'sty-bgcolor-b':''}" announceId="${entity.announceId}"  >
								<td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="checkbox" name="check_box" value="${entity.announceId}" id="chk_${entity.announceId}" /></div></td>
								<td style="width:50px;"><div class="overflow_hidden w50 link"><a href="###" class="icon-detail see-detiel"  title="查看"></a><a href="###"  class="icon-edit edit-detail" title="编辑"></a></div></td>
								<td><div class="overflow_hidden w310" title="${entity.title }">${entity.title }</div></td>
								<td><div class="overflow_hidden w100" title="${entity.sum }"> 
								<c:choose>
                                <c:when test="${entity.sum != null}">  
                                <a href="javascript:;" class="notice-detail">${entity.sum }人已阅</a> 
                                </c:when>
                                <c:otherwise> 
                                0人已阅
                                </c:otherwise>
                                </c:choose>
								</div></td>     
								<td><div class="overflow_hidden w100" title="${entity.inputerAcc }">${entity.inputerAcc }</div></td>
								<td><div class="overflow_hidden w100" ><fmt:formatDate value="${entity.inputdate}" pattern="yyyy-MM-dd"/></div></td>
							</tr>
			 		</c:forEach>
			 	</c:when>
			 	<c:otherwise>
			 		<tr class="no_data_tr"><td style="border: solid #E1E1E1;border-width: 0px 0px 1px 1px;text-align:center;"><b>当前列表无数据！</b></td></tr>
			 	</c:otherwise>
			 	</c:choose>
			</tbody>
		</table>
	</div>
	<div class="com-bot" >
		<div class="com-page fl_r">
			${item.page.pageStr}
		</div>
	</div>
</div>
</form>
<script type="text/x-handlebars-template" id="template">
  {{#each list}}
	<tr class="{{even_odd @index}}">
		<td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="checkbox" name="check_box" value="{{announceId}}" /></div></td>
		<td style="width:50px;">
			<div class="overflow_hidden w50 link">
                <a href="javascript:;" class="icon-detail" onclick="showNotice('{{announceId}}')" title="查看"></a>
                <a href="javascript:;" onclick="editNotice('{{announceId}}')" class="icon-edit" title="编辑"></a>
            </div>
		</td>
		<td><div class="overflow_hidden w310" title="{{title }}">{{title }}</div></td>
		<td><div class="overflow_hidden w100" title="{{sum }}">{{sum }}人已阅</div></td>
		<td><div class="overflow_hidden w100" title="{{inputerAcc }}">{{inputerAcc }}</div></td>
		<td>
			<div class="overflow_hidden w100" >
                {{formatDate inputDate "YYYY-MM-DD"}}
            </div>
		</td>
	</tr>
 {{/each}}
</script>
</body>
