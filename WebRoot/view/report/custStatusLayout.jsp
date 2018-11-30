<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE>
<html>
<head>
    <%@ include file="/common/report_include.jsp" %>
    <title>销售统计-团队客户分布-客户状态分布详情</title>
	<style type="text/css">
		.tree-title{width:200px;}
		.com-search .select dd{top:30px;}
		.com-search .select dt.cur{border:solid #e1e1e1 1px !important;}
		.select dd ul{overflow-x:hidden;}
		.select dd .easyui-tree{width:260px !important;min-height:40px;}
		
		.hyx-silent-p{margin-bottom:0;}
	</style>
	<script type="text/javascript">
		window.onload=function(){
			var height = $(".person-static-max").height()+30;
			window.parent.$("#iframepage").css({"height":height+"px"});
			$(".sty-bgcolor-b:eq(0)").find("th:last").find("span").removeAttr("class");
			$(".sty-bgcolor-b:eq(1)").find("th:last").find("span").attr("style","border:0;");
			var names = '';
			$("input[name=col_checkbox]:checked").each(function(idx,obj){
				names+=$(obj).parent().text()+",";
			});
			if(names != ''){
				names = names.substring(0,names.length-1);
			}
			$("#nameStr").text(names == '' ? '-请选择-' : names );
			
			//选中第一行
			var firstGroup = $('tr[id^=view_group_]:first');
			if(firstGroup.length > 0){
				var back_color_a = firstGroup.css('background-color');
		        if(firstGroup.find('td').hasClass('td-hover') == false){
		        	firstGroup.find('td').removeClass('td-link');
		        	firstGroup.find('td').addClass('td-hover');
		        	firstGroup.find('td:first').css({'border-left-color':'#469bff'});
		        	firstGroup.find('td:last').css({'border-right-color':'#469bff'});
		        	firstGroup.siblings('tr').find('td').removeClass('td-hover');
		        	firstGroup.siblings('tr').find('td:first').css({'border-left-color':back_color_a});
		        	firstGroup.siblings('tr').find('td:last').css({'border-right-color':back_color_a});
		        }
				var groupId = firstGroup.attr("id").split("_")[2];
				var statusStr = $("#statusStr").attr("searchValue");
				loadAccountLayout(groupId,statusStr,'');
			}
		};
		
		function selGroup(){
			var ids = '';
			var names = '';
			$($("#tt1").tree('getChecked', 'checked')).each(function(index,node){
				ids+=node.id+",";
				names+=node.text+",";
			});
			
			if(ids != ''){
				ids = ids.substring(0,ids.length-1);
				names = names.substring(0,names.length-1);
			}
			$("#groupIds").val(ids);
			$("#groupNameStr").text(names == '' ? '-请选择-' : names );
		}
		
		function clearGroup(){
			var nodes = $('#tt1').tree('getChecked', 'checked');
			$.each(nodes,function(index,obj){
				 $('#tt1').tree("uncheck",obj.target);
			});
		}
		
		function selec(){
			var ids = '';
			var names = '';
			$("input[name=col_checkbox]:checked").each(function(idx,obj){
				ids+=$(obj).val()+",";
				names+=$(obj).parent().text()+",";
			});
			if(ids != ''){
				ids = ids.substring(0,ids.length-1);
				names = names.substring(0,names.length-1);
			}
			$("#statusStr").val(ids);
			$("#nameStr").text(names == '' ? '-请选择-' : names );
		}
		function clearSelec(){
			$("input[name=col_checkbox]:checked").attr("checked",false);
		}
		
		function loadAccountLayout(groupId,statusStr,accountStr){
			$.post(ctx+"/layout/team/custStatusReport/member",{groupId:groupId,statusStr:statusStr},function(data){
				$("#userDiv").html(data);
				$.parser.parse();
				var height = $(".person-static-max").height()+30;
				window.parent.$("#iframepage").css({"height":height+"px"});
			});
		}
		
		//根据账号筛选用户
		function filterAccount(){
			var accounts = '';
			var names = '';
			$("input[name=account_filter]:checked").each(function(idx,obj){
				accounts+= $(obj).val()+',';
				names+=$(obj).parent().text()+",";
			});
			if(accounts != ''){
				accounts = accounts.substring(0,accounts.length-1);
				names = names.substring(0,names.length-1);
				var accountsObj = new Object();
				$(accounts.split(",")).each(function(idx,obj){
					accountsObj[obj] = 1;
				});
				$("#filterNames").text(names);
				$("tr[id^=member_]").each(function(idx,obj){
					var acc = $(obj).attr("id").split("_")[1];
					if(accountsObj[acc] != null){
						$(obj).show();
					}else{
						$(obj).hide();
					}
				});
				$("tr[id^=member_]:visible").each(function(idx,obj){
					if((idx+1) % 2 == 0){
						$(this).attr("class","sty-bgcolor-b");
					}else{
						$(this).attr("class","");
					}
				});
			}else{
				$("#filterNames").text("-请选择-");
				$("tr[id^=member_]").show();
				$("tr[id^=member_]:visible").each(function(idx,obj){
					if((idx+1) % 2 == 0){
						$(this).attr("class","sty-bgcolor-b");
					}else{
						$(this).attr("class","");
					}
				});
			}
		}
		
		//清除账号筛选
		function clearFilter(){
			$("input[name=account_filter]:checked").attr("checked",false);
		}
		
		$(function(){
			$("#tt1").tree({
				url:ctx+'/orgGroup/get_group_json',
				checkbox:true,
				onLoadSuccess:function(node,data){
					var oas = $("#groupIds").val();
					if(oas != null && oas != ''){
						var text='';
						$.each(oas.split(','),function(index,obj){
							var n = $("#tt1").tree("find",obj);
							if(n != null){
								text+=n.text+',';
								$("#tt1").tree("check",n.target).tree("expandTo",n.target);
							}
						});
						if(text != ''){
							text=text.substring(0,text.length-1);
							$("#groupNameStr").text(text);
						}else{
							$("#groupNameStr").text("-请选择-");
						}
					}
				}
			})
			$("tr[id^=view_group_]").click(function(){
				var groupId = $(this).attr("id").split("_")[2];
				var statusStr = $("#statusStr").attr("searchValue");
				loadAccountLayout(groupId,statusStr,'');
			});
			//选择
			$(".select").find("dt").live("click",function(){
				var isHidden = $(this).next().is(":hidden");
				if(isHidden){
					$(this).next().slideDown(200);
					$(this).next().addClass("cur");
				}else{
					$(this).next().slideUp(200);
					$(this).next().removeClass("cur");
				}
				return false;
			});
			$(".reso-owner-sure").live("click",function(){
				$(this).parent().parent().slideUp(200);
				$(this).parent().parent().removeClass("cur");
				return false;
			});
			$(".com-btna-cancle").live("click",function(){
				return false;	
			});
			
			$(document).click(function(i){
				 $(".select dd").hide();
			});
		});
		
	</script>
</head>
<body>
<form action="${ctx }/layout/team/custStatusReport" method="post">
	<div class="person-static-max">
		<div class="hyx-silent-p clearfix" style="z-index:555;">
			<label class="lab fl_l sty-bgcolor-a sty-color-a sty-borcolor-d com-radius">部门统计</label>
			<div class="com-search-report fl_l" style="margin-top:0;min-height:0">
				<input type="hidden" name="groupIds" id="groupIds" value="${groupIds }" />
				<label class="fl_l" for="" >选择部门：</label>
				<dl class="select fl_l" style="position:relative;z-index:555;width:145px;margin:0 auto;margin-right:10px;">
					<dt id="groupNameStr">-请选择-</dt>
					<dd>
						<ul id="tt1" style="max-height:160px;margin-top:10px;">
							
						</ul>
						<div class="sure-cancle clearfix" style="width:120px;margin:15px auto 0;padding-bottom:10px;">
							<a class="com-btnc bomp-btna com-btna-sty reso-owner-sure fl_l" onclick="selGroup()" href="javascript:;" style="margin-left:10px;"><label style="height:19px;line-height:19px;">确定</label></a>
							<a class="com-btnd bomp-btna com-btna-cancle fl_l" onclick="clearGroup()" href="javascript:;" style="margin-left:10px;"><label style="height:19px;line-height:19px;">清空</label></a>
						</div>
					</dd>
				</dl>
			</div>
			<div class="com-search-report fl_l" style="margin-top:-4px;min-height:0">
				<label class="fl_l" for="" style="line-height:34px;">选择客户状态：</label>
				<input type="hidden" searchValue="${statusStr }" value="${statusStr }" name="statusStr" id="statusStr" />
				<dl class="select fl_l" style="position:relative;z-index:555;width:145px;margin:0 auto;margin-right:10px;">
					<dt id="nameStr">-请选择-</dt>
					<dd>
						<ul id="tt2" class="easyui-tree" data-options="animate:true,dnd:false" style="max-height:160px;margin-top:10px;">
							<li><span><input name="col_checkbox" type="checkbox" ${empty colMap['1'] ? '' : 'checked' } value="1">资源</span></li>
							<li><span><input name="col_checkbox" type="checkbox" ${empty colMap['2'] ? '' : 'checked' } value="2">意向客户</span></li>
							<li><span><input name="col_checkbox" type="checkbox" ${empty colMap['3'] ? '' : 'checked' } value="3">签约客户</span></li>
							<li><span><input name="col_checkbox" type="checkbox" ${empty colMap['4'] ? '' : 'checked' } value="4">沉默客户</span></li>
							<li><span><input name="col_checkbox" type="checkbox" ${empty colMap['5'] ? '' : 'checked' } value="5">流失客户</span></li>
						</ul>
						<div class="sure-cancle clearfix" style="width:120px;margin:15px auto 0;padding-bottom:10px;">
							<a class="com-btnc bomp-btna com-btna-sty reso-owner-sure fl_l" href="javascript:;" onclick="selec()" style="margin-left:10px;"><label style="height:19px;line-height:19px;">确定</label></a>
							<a class="com-btnd bomp-btna com-btna-cancle fl_l" href="javascript:;" onclick="clearSelec()" style="margin-left:10px;"><label style="height:19px;line-height:19px;">清空</label></a>
						</div>
					</dd>
				</dl>
			</div>
			<a href="javascript:;" onclick="document.forms[0].submit();" class="com-btna fl_l"><i class="min-search"></i><label class="lab-mar">查询</label></a>
		</div>
		<div class="com-table hyx-mpe-table hyx-cm-table">
			<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-a">
				<thead>
					<tr class="sty-bgcolor-b">
						<th><span class="sp sty-borcolor-b">部门名称</span></th>
						<c:if test="${empty colMap || !empty colMap['1'] }">
							<th><span class="sp sty-borcolor-b">资源</span></th>
						</c:if>
						<c:if test="${empty colMap || !empty colMap['2'] }">
							<th><span class="sp sty-borcolor-b">意向客户</span></th>
						</c:if>
						<c:if test="${empty colMap || !empty colMap['3'] }">
							<th><span class="sp sty-borcolor-b">签约客户</span></th>
						</c:if>
						<c:if test="${empty colMap || !empty colMap['4'] }">
							<th><span class="sp sty-borcolor-b">沉默客户</span></th>
						</c:if>
						<c:if test="${empty colMap || !empty colMap['5'] }">
							<th>流失客户</th>
						</c:if>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${dtos }" var="dto" varStatus="vs">
						<tr id="view_group_${dto.groupId }" class="${vs.count%2 eq 0 ? 'sty-bgcolor-b' : '' }">
							<td><div class="overflow_hidden w120" title="${dto.groupName }">${dto.groupName }</div></td>
							<c:if test="${empty colMap || !empty colMap['1'] }">
								<td><div class="overflow_hidden w100" title="${dto.resNum }/<fmt:formatNumber type="number" value="${dto.allNum gt 0 ? (dto.resNum/dto.allNum*100) : 0 }" pattern="0.00" maxFractionDigits="2" />%">${dto.resNum }/<fmt:formatNumber type="number" value="${dto.allNum gt 0 ? (dto.resNum/dto.allNum*100) : 0 }" pattern="0.00" maxFractionDigits="2" />%</div></td>
							</c:if>
							<c:if test="${empty colMap || !empty colMap['2'] }">
								<td><div class="overflow_hidden w100" title="${dto.custNum }/<fmt:formatNumber type="number" value="${dto.allNum gt 0 ? (dto.custNum/dto.allNum*100) : 0 }" pattern="0.00" maxFractionDigits="2" />%">${dto.custNum }/<fmt:formatNumber type="number" value="${dto.allNum gt 0 ? (dto.custNum/dto.allNum*100) : 0 }" pattern="0.00" maxFractionDigits="2" />%</div></td>
							</c:if>
							<c:if test="${empty colMap || !empty colMap['3'] }">
								<td><div class="overflow_hidden w100" title="${dto.signNum }/<fmt:formatNumber type="number" value="${dto.allNum gt 0 ? (dto.signNum/dto.allNum*100) : 0 }" pattern="0.00" maxFractionDigits="2" />%">${dto.signNum }/<fmt:formatNumber type="number" value="${dto.allNum gt 0 ? (dto.signNum/dto.allNum*100) : 0 }" pattern="0.00" maxFractionDigits="2" />%</div></td>
							</c:if>
							<c:if test="${empty colMap || !empty colMap['4'] }">
								<td><div class="overflow_hidden w100" title="${dto.silentNum }/<fmt:formatNumber type="number" value="${dto.allNum gt 0 ? (dto.silentNum/dto.allNum*100) : 0 }" pattern="0.00" maxFractionDigits="2" />%">${dto.silentNum }/<fmt:formatNumber type="number" value="${dto.allNum gt 0 ? (dto.silentNum/dto.allNum*100) : 0 }" pattern="0.00" maxFractionDigits="2" />%</div></td>
							</c:if>
							<c:if test="${empty colMap || !empty colMap['5'] }">
								<td><div class="overflow_hidden w100" title="${dto.losingNum }/<fmt:formatNumber type="number" value="${dto.allNum gt 0 ? (dto.losingNum/dto.allNum*100) : 0 }" pattern="0.00" maxFractionDigits="2" />%">${dto.losingNum }/<fmt:formatNumber type="number" value="${dto.allNum gt 0 ? (dto.losingNum/dto.allNum*100) : 0 }" pattern="0.00" maxFractionDigits="2" />%</div></td>
							</c:if>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="userDiv">
			
		</div>
		<p class="hyx-mpe-ptit"><label class="com-red">温馨提示：</label>“/”前面的值表示该状态的数据量，“/”后面的值表示该状态的数量占总量的占比。</p>
	</div>
</form>
</body>
</html>