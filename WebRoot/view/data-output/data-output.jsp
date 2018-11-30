<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>数据导出</title>
		<%@ include file="/common/common.jsp"%>
		<%@ include file="/common/include-cut-version.jsp"%>
		<%@ include file="/common/common-function/function.form.jsp"%>
		<%@ include file="/common/common-function/function.searchHead.jsp"%>
		<%@ include file="/common/common-function/function.idialog.jsp"%>
		<%@ include file="/common/common-function/function.date.jsp"%>
		<%@ include file="/common/common-function/function.table.jsp"%>
		<%@ include file="/common/common-function/function.handlebar.jsp"%>
		<link rel="stylesheet" type="text/css" href="${ctx }/static/thirdparty/font-awesome-4.7.0/css/font-awesome.min.css"/>
		<style type="text/css">
			button.btn {
				border: 1px solid #d3d3d3;
				border-radius: 5px;
				cursor: pointer;
				margin: 0 10px;
				padding:0 15px;
				line-height:22px;
			}
			
			button.btn:disabled {
				cursor: not-allowed;
			}
			
			.btn.btn-md {
				width: 100px;
				height: 30px;
				line-height: 30px;
			}
			
			.btn.btn-submit {
				color: #fff;
				background-color: #1979ca;
			}
			
			.btn.btn-submit:hover {
				background-color: #0f5aac;
			}
			
			.btn.btn-default {
				background-color: #fff;
			}
			
			.btn.btn-default:hover {
				color: #fff;
				background-color: #1979ca;
			}
			
			body {
				padding: 0 16px;
			}
			
			.head-title {
				margin: 15px 0;
				font-size: 14px;
				font-weight: bold; 
			}
			
			.output-record .head-title {
				margin-bottom: 0;
				padding-bottom: 10px;
				border-bottom: 1px solid #ccc;
			}
			
			.data-output {
				margin: 10px 0;
				border: 1px solid #ccc;
			    padding: 20px 0;
			    position: relative;
			}
			
			.cust-pool {
				height: 24px;
				line-height: 24px;
				width: 120px;
			}
			
			#btn-output,#btn-query {
				margin-left: 10px;
			}
			
			#btn-output {
				position: absolute;
				right: 10px;
			}
			
			.query-block {
				display: inline-block;
			}
			
			.query-area {
				padding-left: 10px;
			}
			
			.query-result table{
				width: 100%;
				border: 1px solid #469bff;
				border-collapse: collapse;
				text-align: center;
				position: relative;
			}
			
			.query-result table tr{
				height: 30px;
				line-height: 30px;
			}
			
			.query-result table thead th{
				background-color: #f5f6f8;
			}
			
			ul,li{
				margin: 0;
				padding: 0;
				list-style: none;
			}
			
			.conditions-block .content>label {
				display: inline-block;
				width: 160px;
				overflow: hidden;
				text-overflow: ellipsis;
				white-space: nowrap;
			}
			
			.display-text {
				display: inline-block;
				color: #1979ca;
				border: 1px solid #1979ca;
				border-radius: 8px;
				padding: 2px;
				margin: 4px 2px 4px 0;
				line-height: 20px;
			}
			
			.reso-sub-dep {
				display: inline-block;
				border: 1px solid #ccc;
				height: 24px;
				line-height: 24px;
				margin-bottom: 0;
			}
			
			.sub-dep-inpu {
				margin: 0;
				padding-left: 5px;
			}
			
			.date-fast-check.active {
				color: grey;
				text-decoration: underline;
			}
			
			.output-record>.head {
				padding: 10px 0;
			}
			
			a.not-allowed {
				color: #ccc;
			}
		</style>
	</head>
	<body>
		<form data-render-url="${ctx }/export/log/data">
			<div class="head-title"><span class="title">数据导出</span></div>
			<div class="output-block data-output">
				<div class="head"></div>
				<div class="content">
					<div class="query-area">
						<div class="query-block">
							<label for="">选择导出数据源：</label>
							<select class="cust-pool" name="">
								<option data-url="${ctx}/export/toPage?module=4&exportType=1" value="1">资源</option>
								<option data-url="${ctx}/export/toPage?module=5&exportType=2" value="2">意向客户</option>
								<option data-url="${ctx}/export/toPage?module=6&exportType=3" value="3">签约客户</option>
								<option data-url="${ctx}/export/toPage?module=cust_silent&exportType=4" value="4">沉默客户</option>
								<option data-url="${ctx}/export/toPage?module=cust_losing&exportType=5" value="5">流失客户</option>
								<option data-url="${ctx}/export/toPage?module=call_record&exportType=6" value="6">通话记录</option>
							</select>
						</div>
						<button type="button" class="btn btn-md btn-submit" id="btn-output">导出数据</button>
					</div>
				</div>
			</div>
			<div class="output-block output-record">
				<div class="head-title"><span class="title">导出记录</span></div>
				<div class="head">
					<div class="query-area">
						<c:if test="${isSys eq 1 }">
							<div class="query-block">
								<label for="">选择导出人：</label>
								<input type="hidden" name="userAccsStr" value="" id="ownerAccsStr" />
								<input type="hidden" name="ownerUserIdsStr" value="" id="ownerUserIdsStr" />
								<input type="hidden" name="osType" value="1" id="osType" />
								<div class="reso-sub-dep">
									<input style="border-right: none;" id="ownerNameStr" readonly="readonly" class="sub-dep-inpu" type="text" value="">
									<div class="manage-drop"  style="height:370px;">
										<div class="shows-radio-boxs"></div>
										<ul class="shows-allorme-boxs">
											<li><input type="radio"  value="1"  name="searchOwnerType" checked>查看全部</li>
											<li><input type="radio" name="searchOwnerType"  value="2">查看自己</li>
										</ul>
										<div class="sub-dep-ul" style="width:190px;" data-type="search-tree">
											<ul id="tt1">
	
							       			</ul>
							    		</div>
									    <div class="sure-cancle clearfix" style="width:120px">
											<a class="com-btnc bomp-btna com-btna-sty reso-alloc-sure fl_l" onclick="seleTree()" href="javascript:;"><label>确定</label></a>
											<a class="com-btnd bomp-btna fl_l" id="close02" onclick="unCheckTree()" href="javascript:;"><label>清空</label></a>
										</div>
									</div>
								</div>
							</div>
						</c:if>
						<div class="query-block">
							<label for="">选择日期：</label>
							<span>
								<input id="data_01" name="startDate" value="" type="text" class="selec-year-inpu com-form-data" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'data_02\')}'})" />
								—
								<input id="data_02" name="endDate" value="" type="text" class="selec-year-inpu com-form-data" onclick="WdatePicker({minDate:'#F{$dp.$D(\'data_01\');}',maxDate:'%y-%M-{%d}'})" />
							</span>
						</div>
						<div class="query-block">
							<a href="javascript:;" class="date-fast-check" data-type="week">本周</a>
							<a href="javascript:;" class="date-fast-check" data-type="month">本月</a>
						</div>
						<button type="button" class="btn btn-md btn-submit" id="btn-query">查询</button>
					</div>
				</div>
				<div class="content">
					<div class="query-result">
						<table id="t1" class="ajax-table">
							<thead>
								<th>导出时间</th>
								<th>数据源</th>
								<th>查询条件</th>
								<th>导出数量</th>
								<th>导出人</th>
								<th>导出文件</th>
							</thead>
							<tbody>
								<tr class="no_data_tr"><td></td></tr>
								<tr class="no_data_tr sty-bgcolor-b"><td></td></tr>
								<tr class="no_data_tr"><td></td></tr>
								<tr class="no_data_tr sty-bgcolor-b"><td></td></tr>
								<tr class="no_data_tr"><td></td></tr>
								<tr class="no_data_tr sty-bgcolor-b"><td></td></tr>
								<tr class="no_data_tr"><td></td></tr>
								<tr class="no_data_tr sty-bgcolor-b"><td></td></tr>
								<tr class="no_data_tr"><td></td></tr>
							</tbody>
						</table>
						<div class="tip_search_data" >
							<div class="tip_search_text">
								<span><img alt="加载中" src="${ctx }/static/images/hua_loading.gif" /></span>
								<span>数据加载中…</span>
							</div>
							<div class="tip_search_error"></div>
						</div>
						<div class="com-bot" >
							<div class="com-page fl_r">
								<div class="page" id="new_page"></div>
								<div class="clr_both"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</form>
		
		<script type="text/javascript">
			$(function(){
				var init_startDate = moment().day("Sunday").format('YYYY-MM-DD');
				var init_endDate = moment().day("Saturday").format('YYYY-MM-DD');
				$("input[name=startDate]").val(init_startDate);
				$("input[name=endDate]").val(init_endDate);
				$(".date-fast-check[data-type=week]").addClass("active");
			
				$(".date-fast-check").on("click",function(e){
					var _this = $(this);
					var startDate = "";
					var endDate = "";
					if(_this.data("type") == "week"){
						startDate = moment().day("Sunday").format('YYYY-MM-DD');
						endDate = moment().day("Saturday").format('YYYY-MM-DD');
						if(moment().isBefore(endDate)){
							endDate = moment().format('YYYY-MM-DD');
						}
					}else if(_this.data("type") == "month"){
						startDate = moment().startOf('month').format('YYYY-MM-DD');
						endDate = moment().endOf('month').format('YYYY-MM-DD');
						if(moment().isBefore(endDate)){
							endDate = moment().format('YYYY-MM-DD');
						}
					}
					$("input[name=startDate]").val(startDate);
					$("input[name=endDate]").val(endDate);
					$(".date-fast-check").removeClass("active");
					_this.addClass("active");
					loadData();
				});
				
				$("#btn-query").on("click",function(e){
					loadData();
				});
				
				$("#btn-output").on("click",function(e){
					$.ajax({
						url: ctx+"/export/exportPhoneCheck",
						type: "get",
						success: function(data){
							if(data.success && data.isCheckPhone){
								var _url = $(".cust-pool option:checked").data("url");
								var _value = $(".cust-pool option:checked").data("value");
								var _title = "数据导出";
								pubDivShowIframe('alert_data_output',_url,_title,680,550);
							}else {
								window.top.iDialogMsg("提示","请联系管理员设置审核人后再进行数据导出。");
							}
						},
						error: function(error){
							alert(error.msg);
						}
					});
				});
				
				$(".ajax-table").delegate(".btn-export-search-context","click",function(e){
					e.stopPropagation();
					var _this = $(this);
					var _url = ctx+"/export/toExportContextPage?exportSearchContext="+_this.data("value");
					var _title = "查询条件";
					pubDivShowIframe('alert_data_output',_url,_title,680,550);
				});
				
				/*表单优化*/
			    $('.skin-minimal input').iCheck({
			        checkboxClass: 'icheckbox_minimal',
			        radioClass: 'iradio_minimal',
			        increaseArea: '20%'
			    });
			
			   $("input[name=searchOwnerType]").iCheck({
			    	radioClass: 'iradio_minimal'
			    });
			    $("input[name=searchOwnerType]").on('ifClicked',function(){
			    	var nodes = $('#tt1').tree('getChecked');
			    	if(nodes.length > 0){
			    		$.each(nodes,function(index,obj){
			    			 $('#tt1').tree("uncheck",obj.target);
			    		});
			    	}
			    });
				
				if($("#tt1").length > 0){
					$("#tt1").tree({
						url:ctx+"/orgGroup/get_group_user_json",
						checkbox:true,
						onLoadSuccess:function(node,data){
							$("#tt1").tree("collapseAll");
							for(var i=0;i<data.length;i++){
								$("#tt1").tree("expand",data[i].target);
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
									var n = $("#tt1").tree("find",obj);
									if(n != null){
										text+=n.text+',';
										$("#tt1").tree("check",n.target).tree("expandTo",n.target);
									}
								});
								if(text != ''){
									text=text.substring(0,text.length-1);
									$("#ownerNameStr").val(text);
								}else{
									$("#ownerNameStr").val("");
								}
							}
						},
						onCheck:function(node,isCheck){
							var nodes = $('#tt1').tree('getChecked');
							if(nodes.length > 0){
								$("input[name=searchOwnerType]").iCheck('uncheck');
							}else if($("input[name=searchOwnerType]:checked").length == 0){
								$("input[name=searchOwnerType]:eq(0)").iCheck('check');
							}
						}
					});
				}
				
				$(document).click(function(e){
					$(".manage-drop").hide();
				});
				
				$(".manage-drop").on("click",function(e){
					e.stopPropagation();
				});
			});
			var seleTree=function(){
				var nodes = $('#tt1').tree('getChecked', 'checked');
				var accs = "";
				var names = "";
				var userIds = "";
				$.each(nodes,function(index,obj){
					var type = obj.attributes.type;
					if(type == "M"){
						accs+=obj.id+",";
						names+=obj.text+",";
						userIds+=obj.user_id+",";
					}
				});
				if(accs != ""){
					accs=accs.substring(0,accs.length-1);
					names=names.substring(0,names.length-1);
					userIds=userIds.substring(0,userIds.length-1);
					$("#osType").val("3");
				}else{
					var searchOwnerType = $("input[name=searchOwnerType]:checked").val();
					if(searchOwnerType == '1'){
						names="查看全部";
					}else{
						names="查看自己";
					}
					$("#osType").val(searchOwnerType);
				}
				$("#ownerAccsStr").val(accs);
				$("#ownerNameStr").val(names);
				if($("#ownerUserIdsStr").length > 0){
					$("#ownerUserIdsStr").val(userIds);
				}
				$(".manage-drop").hide();
			};
			
			var unCheckTree=function(tid){
				tid=tid||'tt1';
				var nodes = $('#'+tid).tree('getChecked', 'checked');
				$.each(nodes,function(index,obj){
					 $('#'+tid).tree("uncheck",obj.target);
				});
			};
		</script>
		<script type="text/javascript" src="${ctx }/static/js/view/bill/render_table.js${_v}"></script>
		<script type="text/x-handlebars-template" id="template">
		{{#each list}}
			<tr class="{{even_odd @index}}">
				<td>{{formatDate exportDate "YYYY-MM-DD HH:mm:ss"}}</td>
				<td>{{judgeExportCustType exportCustType}}</td>
				<td><a href="javascript:;" class="btn-export-search-context" data-value="{{exportSearchContext}}">查询</a></td>
				<td>{{exportNum}}</td>
				<td>{{userName}}</td>
				<td>
					{{#compare deletedFile "==" 1}}
						<a href="javascript:;" class="not-allowed">下载</a>
						{{else}}
						<a href="${ctx}/fileupload/log/download?exportId={{exportId}}">下载</a>
					{{/compare}}
				</td>
			</tr>
		{{/each}}
		</script>
	</body>
</html>
