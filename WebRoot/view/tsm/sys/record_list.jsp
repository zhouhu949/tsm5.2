<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/tree_top_part.css"/>
<script type="text/javascript" src="${ctx}/static/js/common/common.js"></script>
<script type="text/javascript" src="${ctx}/static/js/view/tsm/sys/record_list.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/common/tablerows.js"></script>
<script type="text/javascript" src="${ctx}/static/js/view/tsm/res/safePhone.js"></script>
<style>
	.tree-node{width:240px;}
	.hyx-hsm-left dl dd {
	text-indent: 30px;
}
</style>
<!--本页面js-->

<script type="text/javascript">
$(function() {

	$(".owner-sour").click(function(){
		$(".manage-owner-sour").show();
		return false;
	});

	if ($('#qKeyWordId').val() == "" || $('#qKeyWordId').val() == null) {
		$('#qKeyWordId').next().text('输入点评人/点评内容');
	} else {
		$('#qKeyWordId').next().text('');
	}
	if($("#onwerTree").length > 0){
		$("#onwerTree").tree({
			url:ctx+"/orgGroup/get_all_group_user_json",
			checkbox:true,
			onLoadSuccess:function(node,data){
				$("#onwerTree").tree("collapseAll");
				for(var i=0;i<data.length;i++){
					$("#onwerTree").tree("expand",data[i].target);
				}
				var searchOwnerType = $("input[name=searchOwnerType]:checked").val();
				var oas = $("#ownerAccsStr").val();
				if(searchOwnerType == '1'){
					$("#ownerNameStr").val("查看全部");
				}else if(searchOwnerType == '2'){
					$("#ownerNameStr").val("查看自己");
				}else if(oas != null && oas != ''){
					var text='';
					$.each(oas.split(','),function(index,obj){
						var n = $("#onwerTree").tree("find",obj);
						if(n != null){
							text+=n.text+',';
							$("#onwerTree").tree("check",n.target).tree("expandTo",n.target);
						}
					});
					if(text != ''){
						text=text.substring(0,text.length-1);
						$("#ownerNameStr").val(text);
					}else{
						$("#ownerNameStr").val("所有者");
					}
				}
			},
			onCheck:function(node,isCheck){
				var nodes = $('#onwerTree').tree('getChecked');
				if(nodes.length > 0){
					$("input[name=searchOwnerType]").iCheck('uncheck');
				}else if($("input[name=searchOwnerType]:checked").length == 0){
					$("input[name=searchOwnerType]:eq(0)").iCheck('check');
				}
			}
		});
	}


	//选择账号
	$("#checkOnwerId").click(function() {
		var nodes = $('#onwerTree').tree('getChecked', 'checked');
		var accs = "";
		var names = "";
		$.each(nodes,function(index,obj){
			var type = obj.attributes.type;
			if(type == "M"){
				accs+=obj.id+",";
				names+=obj.text+",";
			}
		});
		if(accs != ""){
			accs=accs.substring(0,accs.length-1);
			names=names.substring(0,names.length-1);
		}else{
			names="所有者";
		}
		$("#ownerAccsStr").val(accs);
		$("#ownerNameStr").val(names);
	});

	//清空
	$('#clearOnwerId').click(function() {
		var nodes = $('#onwerTree').tree('getChecked');
		$(nodes).each(function(index, obj) {
			$('#onwerTree').tree('uncheck', obj.target);
		})
		$("#ownerAccsStr").val("");
		$("#ownerNameStr").val("所有者");
	})

	//播放按钮点击的时候
	$("[name='icon-play']").click(function(){
		var url = $(this).attr("url");
		var httpUrl = $("#project_path").val();
		var callId = $(this).attr("callId");
		var timeLengh = $(this).attr('timeLengh');
		if(timeLengh=='' || timeLengh==null ){
			timeLengh = 0;
		}
		//window.location.href = httpUrl+"/call/callPlay.do?timeElngth="+plength+"&recordUrl="+encodeURIComponent(encodeURIComponent(url,"utf-8"))+"&callId="+callId+"&callName="+encodeURI(encodeURI(callName))+"&callNum="+callNum;
		recordCallPlay(httpUrl,timeLengh,url,callId,"录音范例","");
	});
})

 function review(custId){
	pubDivShowIframe('alert_cust_card_a',ctx+'/cust/card/toReview?custId='+custId,'评论',700,250);
}
</script>
</head>
<body>
	<form action="${ctx}/sys/record/recordList" method="post" id="myform" name="myform" enctype="multipart/form-data">
		<input type="hidden" id='project_path' value="${project_path}" />
		<input type="hidden" id="shareAcc" value="${shareAcc}" name="shareAcc">
		<input type="hidden" id="opid" name="recordExampId" value="${reviewDto.recordExampId}">
		<input type="hidden" name="startDate" id="s_pstartDate" value="${reviewDto.startDate }" />
		<input type="hidden" name="endDate" id="s_pendDate" value="${reviewDto.endDate }" />
		<input type="hidden" name="isSys" id="isSys" value="${isSys }" />
		<input type="hidden" id="account" value="${account}" name="account">
		<input type="hidden" name="oDateType" id="oDateType" value="${reviewDto.oDateType }" />
		<div class="hyx-dfpzy-reso hyx-layout">
			<div class="hyx-hsm-left hyx-layout-side">
				<dl>
					<dt>录音范例分类</dt>
					<c:forEach items="${oplist}" var="op">
					    <dd id="groupP_${op.optionlistId }" class="<c:if test='${ reviewDto.recordExampId eq op.optionlistId}'>dd-hover</c:if>">
					        ${op.optionName}
					    </dd>
					</c:forEach>
				</dl>
			</div>
			<div class="hyx-hsm-right hyx-layout-content" style="border:none;">
				<div class="com-search hyx-cm-search">
					<div class="com-search-box fl_l">
					   <p class="search clearfix"><input class="input-query fl_l" id="qKeyWordId" type="text" name="qKeyWord" value="${reviewDto.qKeyWord }" /><label class="hide-span">输入点评人/点评内容</label></p>
					   <div class="list-box">
						<c:set var="oDateName" value="创建日期" />
						<c:choose>
							<c:when test="${reviewDto.oDateType eq 1 }">
								<c:set var="oDateName" value="当天" />
							</c:when>
							<c:when test="${reviewDto.oDateType eq 2 }">
								<c:set var="oDateName" value="本周" />
							</c:when>
							<c:when test="${reviewDto.oDateType eq 3 }">
								<c:set var="oDateName" value="本月" />
							</c:when>
							<c:when test="${reviewDto.oDateType eq 4 }">
								<c:set var="oDateName" value="半年" />
							</c:when>
					   		<c:when test="${reviewDto.oDateType eq 5 }">
					   			<fmt:parseDate var="psd" value="${reviewDto.startDate }" pattern="yyyy-MM-dd" />
					   			<fmt:parseDate var="ped" value="${reviewDto.endDate }" pattern="yyyy-MM-dd" />
					   			<c:set var="oDateName">
					   				<fmt:formatDate value="${psd }" pattern="yy.MM.dd"/>/<fmt:formatDate value="${ped }" pattern="yy.MM.dd"/>
					   			</c:set>
					   		</c:when>
						</c:choose>

						<dl class="select">
							<dt>${oDateName }</dt>
							<dd>
								<ul>
									<li><a href="###" onclick="setPdate(0)"
										title="创建日期">创建日期</a></li>
									<li><a href="###" onclick="setPdate(1)"
										title="当天">当天</a></li>
									<li><a href="###" onclick="setPdate(2)"
										title="本周">本周</a></li>
									<li><a href="###" onclick="setPdate(3)"
										title="本月">本月</a></li>
									<li><a href="###" onclick="setPdate(4)"
										title="半年">半年</a></li>
									<li><a href="###" id="pDate" title="自定义"
										class="diy date-range">自定义</a></li>
								</ul>
							</dd>
						 </dl>
                            <input type="hidden" name="ownerAccsStr" value="${reviewDto.ownerAccsStr }" id="ownerAccsStr" />
                            <input type="hidden" name="ownerUserIdsStr" value="${reviewDto.ownerUserIdsStr }" id="ownerUserIdsStr" />
                            <input type="hidden" name="osType" value="${reviewDto.osType }" id="osType" />
							<div class="reso-sub-dep fl_l">
							<div class="input_tree_outerDiv">
								<input style="border-right: 2px solid #e1e1e1;" id="ownerNameStr" readonly="readonly" class="owner-sour" type="text" value="所有者">
								<div class="manage-owner-sour"  style="height:370px;">
									<div class="shows-radio-boxs"></div>
									<ul class="shows-allorme-boxs1">
										<li><input type="radio"  value="1"  name="searchOwnerType" ${reviewDto.osType eq '1' ? 'checked':'' }>查看全部</li>
										<li><input type="radio"  value="2"  name="searchOwnerType" ${reviewDto.osType eq '2' ? 'checked':'' }>查看自己</li>
									</ul>
									<div class="sub-dep-ul" style="width:260px;" data-type="search-tree">
										<ul id="onwerTree">

						       			</ul>
						    		</div>
								    <div class="sure-cancle clearfix" style="width:120px">
										<a class="com-btnc bomp-btna com-btna-sty reso-alloc-sure fl_l" onclick="seleTree()" href="###"><label>确定</label></a>
										<a class="com-btnd bomp-btna fl_l"  onclick="unCheckTree()" href="###"><label>清空</label></a>
									</div>
								</div>
								</div>
							</div>
						</div>
					</div>
					<input class="query-button fl_r" type="button" value="查&nbsp;询" id="query" />
				</div>
				<div class="com-btnlist hyx-cm-btnlist fl_l">
					<a href="###" class="com-btna recor-sample-a fl_l" id="deleteRecordId"><i class="min-dele"></i><label class="lab-mar">删除</label></a>
					<a href="###" class="com-btna recor-sample-b fl_l" id="addRecord"><i class="min-new-add"></i><label class="lab-mar">添加范例</label></a>
				</div>
				<div class="hid" style="display:block;">
					<div class="com-table com-mta test-table fl_l">
						<!-- <p class="reso-math">目前共有资源数：<span>3</span>条</p> -->
						<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius">
							<thead>
								<tr class="sty-bgcolor-b">
									<th><span class="sp sty-borcolor-b skin-minimal"><input type="checkbox" id="checkAll" class="check" /></span></th>
									<th><span class="sp sty-borcolor-b">操作</span></th>
									<th><span class="sp sty-borcolor-b">加入时间</span></th>
									<th><span class="sp sty-borcolor-b">所有者<span></th>
									<th><span class="sp sty-borcolor-b">通话时间</span></th>
									<th><span class="sp sty-borcolor-b">点评人</span></th>
									<th>点评内容</th>
								</tr>
							</thead>
					         <tbody id="list">
					         <c:set var="index" value="0"/>
							    <c:choose>
										<c:when test="${!empty relist }">
											<c:forEach items="${relist}" var="re" varStatus="vs">
												<tr class="${(vs.count % 2) ne 0 ? '' : 'sty-bgcolor-b' }">
												   <td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="checkbox" delParam="${re.callD}_${re.callCode}_${re.reviewId}" id="${( (account eq re.ownerAcc && empty re.reviewAcc ) || isSys=='1') ?'chk':'' }_${re.reviewId }_${re.ownerAcc }" ${( (account eq re.ownerAcc && empty re.reviewAcc ) || isSys=='1') ?'':'disabled' }/></div></td>
													<td style="width:120px;"><div class="overflow_hidden w120 link">
													     <a href="###" name="icon-play" class="icon-play" title="录音播放" url="${re.recordUrl}" callId="${re.reviewId }" timeLengh ="${re.timeLengh }"></a>
													<td><div class="overflow_hidden w150" title="<fmt:formatDate value='${re.reviewDate}' pattern='yyyy-MM-dd HH:mm:ss'/>"> <fmt:formatDate value="${re.reviewDate}" pattern="yyyy-MM-dd HH:mm:ss"/></div></td>
													<td><div class="overflow_hidden w80" title="${re.ownerName}">${re.ownerName}</div></td>
													<td><div class="overflow_hidden w150" title='<fmt:formatDate value="${re.retaTime}" pattern="yyyy-MM-dd HH:mm:ss"/>'> <fmt:formatDate value="${re.retaTime}" pattern="yyyy-MM-dd HH:mm:ss"/></div></td>
										            <td><div class="overflow_hidden w80"  title="${re.reviewName}">${re.reviewName}</div></td>
										            <td><div class="overflow_hidden w220" title="${re.revComment}">${re.revComment} </div></td>
												</tr>
											</c:forEach>
										</c:when>
						              	<c:otherwise>
							              		<tr>
						                          <td colspan="7" style="border-width: 0px 1px 1px 1px;text-align:center;"><b>当前列表无数据！</b></td>
						                        </tr>
						              	</c:otherwise>
								</c:choose>
							</tbody>
						</table>
					</div>
					<div class="com-bot">
						<div class="com-page fl_r" style="margin:10px 10px 0 0">
							<div class="page">${reviewDto.page.pageStr}</div>
							<div class="clr_both"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
   </form>

<script type="text/x-handlebars-template" id="template">
    {{each list}}
        <tr class="{{even_odd @index}}">
           <td style="width:30px;">
			   <div class="overflow_hidden w30 skin-minimal">
                   //此处后面代码没有转义
                   <input type="checkbox" delParam="{{callD}}_{{callCode}}_{{reviewId}}" id="${( (account eq re.ownerAcc && empty re.reviewAcc ) || isSys=='1') ?'chk':'' }_${re.reviewId }_${re.ownerAcc }" ${( (account eq re.ownerAcc && empty re.reviewAcc ) || isSys=='1') ?'':'disabled' }/>
               </div>
		   </td>
            <td style="width:120px;"><div class="overflow_hidden w120 link">
                 <a href="javascript:;" name="icon-play" class="icon-play" title="录音播放" url="{{recordUrl}}" callId="{{reviewId }}" timeLengh ="{{timeLengh }}"></a>
            <td><div class="overflow_hidden w150" title='{{formatDate reviewDate "yyyy-MM-dd HH:mm:ss"}}'>{{formateDate reviewDate "yyyy-MM-dd HH:mm:ss"}}</div></td>
            <td><div class="overflow_hidden w80" title="{{ownerName}}">{{ownerName}}</div></td>
			<td><div class="overflow_hidden w150" title='{{formatDate retaTime "yyyy-MM-dd HH:mm:ss"}}'>{{formateDate retaTime "yyyy-MM-dd HH:mm:ss"}}</div></td>
            <td><div class="overflow_hidden w80"  title="{{reviewName}}">{{reviewName}}</div></td>
            <td><div class="overflow_hidden w220" title="{{revComment}}">{{revComment}} </div></td>
        </tr>
    {{/each}}
</script>
</body>
</html>
