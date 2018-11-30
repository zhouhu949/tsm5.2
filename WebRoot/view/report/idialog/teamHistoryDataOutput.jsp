<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/include.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx }/static/css/dialog.css"><!--主要样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/thirdparty/easyui/themes/gray/easyui.css${_v}"/>
 <script type="text/javascript" src="${ctx }/static/js/view/report/teamHistoryDataOutput.js${_v}"></script>
 <style>.tree-hit{margin-top:0;}.select dd ul li{overflow-x:hidden;}</style>
 <form id="historyData-outputForm" method="post" action="${url }">
 		<input type="hidden" name="dateStr" id="dateStr" value="${dateStr }" />
		<input type="hidden" name="fromDateStr" id="fromDateStr" value="${fromDateStr }" />
		<input type="hidden" name="toDateStr" id="toDateStr" value="${toDateStr }" />
		<input type="hidden" name="groupId" id="groupId" value="${groupId }" />
		<input type="hidden" name="column" id="column" value="${column }" />
		<input type="hidden" name="orderType" id="orderType" value="${orderType}" />
		<div class="cust-impo-res" style="width:auto;padding-left:0;">
			<div class="com-search clearfix mem-role-edit-search">
					<label class="fl_l" for="">部门名称：</label>
					 <dl class="select fl_l" style="position:relative;z-index:555;width:145px;margin:0 auto;margin-right:10px;">
						<dt id="groupNameStr">-请选择-</dt>
						<dd>
							<ul id="tt1">
									
							</ul>
							<div class="sure-cancle clearfix" style="width:120px;margin:15px auto 0;padding-bottom:10px;">
								<a class="com-btnc bomp-btna com-btna-sty reso-owner-sure fl_l"  id="team_output_tree_sure"  href="javascript:;" style="margin-left:10px;">确定</a>
								<a class="com-btnd bomp-btna com-btna-cancle fl_l"  id="team_output_tree_cancel"  href="javascript:;" style="margin-left:10px;">清空</a>
							</div>
						</dd>
					</dl>
				</div>
			</div>
			
		<div class="bomb-btn" style="margin-top:70px">
			<label class="bomb-btn-cen">
					<a class="com-btna bomp-btna com-btna-sty fl_l" onclick="submitForm();" href="javascript:;"><label>确定</label></a>
					<a class="com-btna bomp-btna fl_l" id="close02"  onclick="close02();" href="javascript:;"><label>取消</label></a>
				</label>
			</div>
 </form>

  

	

