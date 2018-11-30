<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<script type="text/javascript" src="${ctx }/static/js/view/tao/searchHeadSelect.js"></script>
<script type="text/javascript">
	$(function(){
	  //取回
	  $("a[id^=getBack_]").click(function(){
		  var custId = $(this).attr("id").split("_")[1];
		  pubDivDialog("get_back_cust","是否确认取回？",function(){
				$.ajax({
					url:ctx+'/res/sea/updateBatchResourceOrCust',
					type:'post',
					data:{ids:custId,module:2},
					dataType:'html',
					error:function(){},
					success:function(data){
						if(data == '1'){
							window.top.iDialogMsg("提示","取回成功!");
							setTimeout("document.forms[0].submit();",1000);
						}else if(data == '-1'){
							window.top.iDialogMsg("提示","取回失败!");
						}else{
							window.top.iDialogMsg("提示",data);
						}
					}
				});
			});
	  });
	});
</script>
</head>
<body>
<form action="${ctx }/res/cust/hitSigle" method="post">
<div class="hyx-cfu-left hyx-ctr">
   <div class="com-search hyx-cfu-search" style="min-height:39px;">
      <div class="com-search-box fl_l" style="background-color:#fff;">
	   <div class="search clearfix" >
		   <div class="select_outerDiv fl_l">
			    <span class="icon_down_5px"></span>
		   		<select name="queryType" class="fl_l search_head_select" style="background-color:#fff;">
			   		<option value="1" ${(empty custInfoDto.queryType or custInfoDto.queryType eq '1') ? 'selected' : ''}>${empty filedMap['name'] ? '客户名称' : filedMap['name'] }</option>
		   			<option value="2" ${custInfoDto.queryType eq '2' ? 'selected' : ''}>联系人</option>
			   		<option value="3" ${custInfoDto.queryType eq '3' ? 'selected' : ''}>联系电话</option>
			   		<option value="4" ${custInfoDto.queryType eq '4' ? 'selected' : ''}>${empty filedMap['keyWord'] ? '关键字' : filedMap['keyWord'] }</option>
			   	</select>
			</div>
	   		<input class="input-query fl_l" type="text" id="queryText" name="queryText" value="${custInfoDto.queryText }" style="border-bottom:0 none;background-color:#fff;" /><label class="hide-span"></label>
	   </div>
	</div>
		<input class="query-button fl_r" type="button" value="查&nbsp;询" id="query"/>
   </div>
    <c:if test="${!empty custInfoDtos && custInfoDto.page.totalResult le 10 }">
    	<div style="margin-left:1%;color:red;">当前客户已经是本单位的客户，信息如下：</div>
    </c:if>
	<div class="com-table com-mta hyx-cla-table" style="display:block;margin-top:20px !important;">
		<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius">
			<thead>
				<tr class="sty-bgcolor-b">
					<th><span class="sp sty-borcolor-b">操作</span></th>
					<th><span class="sp sty-borcolor-b">${empty filedMap['name'] ? '客户名称' : filedMap['name'] }</span></th>
					<th><span class="sp sty-borcolor-b">资源分组</span></th>
					<th><span class="sp sty-borcolor-b">客户状态</span></th>
					<th><span class="sp sty-borcolor-b">销售进程</span></th>
					<th>所有者</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${!empty custInfoDtos }">
						<c:choose>
							<c:when test="${custInfoDto.page.totalResult le 10 }">
								<c:forEach items="${custInfoDtos }" var="cust" varStatus="vs">
									<tr rt="c" class="${vs.count%2 == 1 ? '' : 'sty-bgcolor-b' }">
										<td>
											<div class="overflow_hidden w50 link">
												<c:if test="${cust.custType eq '6' }">
													<a href="javascript:;" id="getBack_${cust.resCustId }" class="icon-get-back" title="取回资源"></a>
												</c:if>
											</div>
										</td>
										<td><div class="overflow_hidden w120" title="${cust.name }">${cust.name }</div></td>
										<td><div class="overflow_hidden w120" title="${cust.groupName }">${cust.groupName }</div></td>
										<c:choose>
											<c:when test="${cust.custType eq '0' }"><c:set var="custTypeName">待分配资源</c:set></c:when>
											<c:when test="${cust.custType eq '1' }"><c:set var="custTypeName">个人资源</c:set></c:when>
											<c:when test="${cust.custType eq '2' }"><c:set var="custTypeName">意向客户</c:set></c:when>
											<c:when test="${cust.custType eq '3' }"><c:set var="custTypeName">签约客户</c:set></c:when>
											<c:when test="${cust.custType eq '4' }"><c:set var="custTypeName">沉默客户</c:set></c:when>
											<c:when test="${cust.custType eq '5' }"><c:set var="custTypeName">流失客户</c:set></c:when>
											<c:when test="${cust.custType eq '6' }"><c:set var="custTypeName">公海客户</c:set></c:when>
										</c:choose>
										<td><div class="overflow_hidden w120" title="${custTypeName }">${custTypeName }</div></td>
										<td><div class="overflow_hidden w120" title="${cust.saleProcessName }">${cust.saleProcessName }</div></td>
										<c:choose>
											<c:when test="${cust.custType eq '0' }">
												<td><div class="overflow_hidden w50" title=""></div></td>
											</c:when>
											<c:otherwise>
												<td><div class="overflow_hidden w50" title="${cust.ownerName }">${cust.ownerName }</div></td>
											</c:otherwise>
										</c:choose>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr class="no_data_tr">
									<td>
										查询结果大于10条，您输入的查询词不够精确！
									</td>
								</tr>
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>
						<tr  class="no_data_tr">
							<td>
								<c:if test="${!empty custInfoDto.queryText }">当前客户还不是本单位的客户，请添加！</c:if>
								<c:if test="${empty custInfoDto.queryText }">请输入关键词查询！</c:if>
							</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
	</div>
	</div>
	</form>
</body>
</html>
