<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>单位设置-黑名单</title>
<script type="text/javascript">
	$(function(){

		// 最近来电时间
	    $('#d1').dateRangePicker({
			showShortcuts:false,
	        endDate:getNowFormatDate(),format: 'YYYY-MM-DD'
	    }).bind('datepicker-apply',function(event,obj){
	    	if(obj.date1 != 'Invalid Date'){
	    		$('#startDate').val(moment(obj.date1).format('YYYY-MM-DD'));
	    	}
	    	if(obj.date2 != 'Invalid Date'){
	    		$('#endDate').val(moment(obj.date2).format('YYYY-MM-DD'));
	    	}
	    	var s = $(this).parents('.select');
	    	var dt=s.children("dt");
	        var dd=s.children("dd");
	        dt.html("自定义");
	        $("#nDateType").val(5);
	        dd.slideUp(200);
	        dt.removeClass("cur");
	        //s.css("z-index",z);
	 	});

	    /*所有者*/
		var url ="${ctx}/orgGroup/get_all_group_user_json.do";
		$("#tt1").tree({
			url:url,
			checkbox:true,
			onLoadSuccess:function(node,data){
				var $accs = $('#accs').val();
				if($accs != null && $accs.length > 0){
					$($accs.split(',')).each(function(index,data){
						var node = $("#tt1").tree("find",data);
						if(node != null){
							$("#tt1").tree("check",node.target);
						}
					});
				}
			}
		});

		// 选择点保存
		$("#checkedId").click(function() {
			var nodes =$("#tt1").tree("getChecked");
			var accs = "";
			$(nodes).each(function(index,obj){
				if(obj.attributes.type == 'M'){
					accs+=","+obj.id;
				}
			});
			if(accs.length > 0){
				accs=accs.substring(1,ids.length);
				$("#accs").val(accs);
			}
			$("#manage_drop").hide();
		});
		//清空
		$('#clearId').click(function(){
			var nodes = $('#tt1').tree('getChecked');
			 $(nodes).each(function(index,obj){
	        	$('#tt1').tree('uncheck', obj.target);
	        });
	        $('#accs').val('');
			 $("#manage_drop").hide();
		});
		// 显示所有者
		$("#accs").click(function(){
			$("#manage_drop").toggle();
		});
	});
	var setNdate = function(i){
		$('#startDate').val('');
		$('#endDate').val('');
		$("#nDateType").val(i);
	};
</script>

<script type="text/javascript">
	$(document).ready(function(){
		// 添加黑名单
           $('.add-number').click(function(){
           	pubDivShowIframe('alert_black_list_a','${ctx}/view/call/idialog/callRecord_balck_add.jsp','添加号码',400,300);
		});

		$('.dele-member').click(function(){
			var ids = "";
			$("input[name='check_']:checked").each(function(){
				ids += $(this).val() + ",";
			});
			if(ids == ""){
				window.top.iDialogMsg("提示","请选择！");
				return false;
			}
			queDivDialog('dele-member','确定要删除吗？',function(){
				$.ajax({
					url:ctx+'/callrecord/black/deleteCallBlack',
					type:'post',
					data:{ids:ids},
					dataType:'json',
					error:function(){window.top.iDialogMsg("提示",'网络异常，请稍后再试！');},
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

		//复选框全选/全不选
		$("#checkAll").click(function() {
			//复选框全选
			if ($(this).is(":checked")) {
				$("input[name='check_']").each(function() {
					$(this).attr("checked", true);
				});
			} else {
				//复选框取消全选
				$("input[name='check_']").each(function() {
					$(this).attr("checked", false);
				});
			}
		});
	});
</script>

<style>
	.select dd ul{width:99%;max-height:99%;}
</style>
<script type="text/javascript" src="${ctx}/static/js/common/tablerows.js${_v}"></script>
</head>
<body>
<form action="${ctx}/callrecord/black/list" method="post">
<div class="hyx-ctr clearfix" style="width:99% !important;padding-left:8px">
		<div class="com-search hyx-cm-search">
			<div class="com-search-box fl_l">
			   <p class="search clearfix"><input class="input-query fl_l" type="text" value="${item.phone}" name="phone" /><label class="hide-span">输入电话号码</label></p>
			   <div class="list-box">
				<input type="hidden" id="reason" name="reason" value="${item.resaon}">
				<c:set var="reasonName" value="举报原因"/>
				<c:choose>
					<c:when test="${item.resaon eq 1 }"><c:set var="reasonName" value="诈骗电话"/></c:when>
					<c:when test="${item.resaon eq 2 }"><c:set var="reasonName" value="骚扰电话"/></c:when>
					<c:when test="${item.resaon eq 3 }"><c:set var="reasonName" value="非法业务"/></c:when>
					<c:when test="${item.resaon eq 4 }"><c:set var="reasonName" value="中介"/></c:when>
					<c:when test="${item.resaon eq 5 }"><c:set var="reasonName" value="商家"/></c:when>
					<c:when test="${item.resaon eq 6 }"><c:set var="reasonName" value="其他"/></c:when>
				</c:choose>
				<dl class="select">
					<dt>${reasonName }</dt>
					<dd>
						<ul>
							<li><a href="###" onclick="$('#reason').val('');">举报原因</a></li>
							<li><a href="###" onclick="$('#reason').val('');">全部</a></li>
							<li><a href="###" onclick="$('#reason').val('1');">诈骗电话</a></li>
							<li><a href="###" onclick="$('#reason').val('2');">骚扰电话</a></li>
							<li><a href="###" onclick="$('#reason').val('3');">非法业务</a></li>
							<li><a href="###" onclick="$('#reason').val('4');">中介</a></li>
							<li><a href="###" onclick="$('#reason').val('5');">商家</a></li>
							<li><a href="###" onclick="$('#reason').val('6');">其他</a></li>
						</ul>
					</dd>
				</dl>
			<!-- 举报/添加时间 -->
			<input type="hidden" name="startDate" id="startDate" value="${item.startDate }" />
			  <input type="hidden" name="endDate" id="endDate" value="${item.endDate }"/>
			  <input type="hidden" name="nDateType" id="nDateType" value="${nDateType }" />
			  <c:set var="nDateName" value="举报/添加时间" />
			  <c:choose>
		   		<c:when test="${nDateType eq 1 }"><c:set var="nDateName" value="当天" /></c:when>
		   		<c:when test="${nDateType eq 2 }"><c:set var="nDateName" value="本周" /></c:when>
		   		<c:when test="${nDateType eq 3 }"><c:set var="nDateName" value="本月" /></c:when>
		   		<c:when test="${nDateType eq 4 }"><c:set var="nDateName" value="半年" /></c:when>
		   		<c:when test="${nDateType eq 5 }"><c:set var="nDateName" value="自定义" /></c:when>
		  	 </c:choose>
			<dl class="select">
				<dt>${nDateName }</dt>
				<dd>
					<ul>
						<li><a href="###" onclick="setNdate(0)">举报/添加时间</a></li>
						<li><a href="###" onclick="setNdate(1)">当天</a></li>
						<li><a href="###" onclick="setNdate(2)">本周</a></li>
						<li><a href="###" onclick="setNdate(3)">本月</a></li>
						<li><a href="###" onclick="setNdate(4)">半年</a></li>
						<li><a href="###" onclick="setNdate(5)" id="d1" class="diy">自定义</a></li>
					</ul>
				</dd>
			</dl>
			<div class="reso-sub-dep fl_l">
				<input class="owner-sour"  name="accs" id="accs" type="text" value="${accs}">
				<div class="manage-owner-sour" id="manage_drop" style="display:none;">
					<div class="sub-dep-ul">
						<ul id="tt1"  >

				        </ul>
		    		</div>
				    <div class="sure-cancle clearfix" style="width:120px">
						<a  id="checkedId"   class="com-btnc bomp-btna com-btna-sty reso-owner-sure fl_l" href="javascript:;"><label>确定</label></a>
						<a class="com-btnd bomp-btna fl_l" id="clearId" href="javascript:;"><label>清空</label></a>
					</div>
				</div>
			</div>
				</div>
			</div>
			<input class="query-button fl_r" type="button" value="查&nbsp;询" id="query" />
		</div>
		<div class="com-btnlist margin-trbl clearfix">
			<a href="javascript:;" class="com-btna add-number fl_l" id="roleBtn"><label>添加号码</label></a>
			<a href="javascript:;" class="com-btna dele-member fl_l" id="editPass"><label>删除</label></a>
		</div>
		<div class="detail">
		<!-- 表格一 -->
		<div class="com-table test-table fl_l" style="display:block;">
			<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius">
				<thead>
					<tr class="sty-bgcolor-b">
						<th><span class="sp sty-borcolor-b skin-minimal"><input type="checkbox" id="checkAll" class="check" /></span></th>
						<th><span class="sp sty-borcolor-b">号码</span></th>
						<th><span class="sp sty-borcolor-b">举报/添加人</span></th>
						<th><span class="sp sty-borcolor-b">举报原因</span></th>
						<th>举报/添加时间</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${not empty list }">
							<c:forEach items="${list }" var="black" varStatus="vs">
									<tr class=" ${vs.count%2==0?'sty-bgcolor-b':''}">
										<td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="checkbox" name="check_"  value="${black.id}"/></div></td>
										<td><div class="overflow_hidden w120" title="${black.phone }">${black.phone }</div></td>
										<td><div class="overflow_hidden w70" title="${black.inputAcc}">${black.inputAcc}</div></td>
										<td><div class="overflow_hidden w70" >
													<c:choose>
														<c:when test="${black.reason eq 1 }">诈骗电话</c:when>
														<c:when test="${black.reason eq 2 }">骚扰电话</c:when>
														<c:when test="${black.reason eq 3 }">非法业务</c:when>
														<c:when test="${black.reason eq 4 }">中介</c:when>
														<c:when test="${black.reason eq 5 }">商家</c:when>
														<c:when test="${black.reason eq 6 }">其他</c:when>
													</c:choose>
										</div></td>
										<td><div class="overflow_hidden w90" title="<fmt:formatDate value='${black.inputDate}' pattern='yyyy-MM-dd HH:mm:ss'/>"><fmt:formatDate value='${black.inputDate}' pattern='yyyy-MM-dd HH:mm:ss'/></div></td>
									</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
								<tr>
									<td colspan="5" style="text-align: center;">
	                                		<div class="overflow_hidden w120" title="当前列表无数据">当前列表无数据！</div>
	                            	</td>
                            	</tr>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
		</div>
		<div class="com-bot">
			<div class="com-page fl_r">
					${item.page.pageStr}
			</div>
		</div>
	</div>
</div>
</form>
</body>
</html>
