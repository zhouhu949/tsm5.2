<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>呼叫限制名单</title>
    <style type="text/css"></style>
    <%@ include file="/common/include.jsp" %>
    <script type="text/javascript" src="${ctx}/static/js/common/page.js${_v}"></script>
    <script type="text/javascript" src="${ctx }/static/js/view/res/complaint_list.js${_v}"></script>
    <script type="text/javascript" src="${ctx}/static/js/common/tablerows.js${_v}"></script>
</head>
<body>
	<form id="cform" action="${ctx}/res/cust/selectBlackComList" method="post">

		<div class="hyx-ctr" >
		   <div class="com-search hyx-cfu-search">
		      <div class="com-search-box fl_l">
			   <p class="search clearfix"><input class="input-query fl_l" type="text" name="phone"  placeholder="联系电话" /></p>
			   <div class="list-box">
				    <input type="hidden" name="startDate" id="s_startDate" value="${optorDto.startDate }" />
				    <input type="hidden" name="endDate" id="s_endDate" value="${optorDto.endDate }" />
				    <input type="hidden" name="dDateType" id="dDateType" value="${optorDto.dDateType }" />
				    <dl class="select">
						<dt>添加时间</dt>
						<dd>
							<ul>
								<li><a href="javascript:;" onclick="setDdate(0)" title="添加时间">添加时间</a></li>
								<li><a href="javascript:;" onclick="setDdate(1)" title="当天">当天</a></li>
								<li><a href="javascript:;" onclick="setDdate(2)" title="本周">本周</a></li>
								<li><a href="javascript:;" onclick="setDdate(3)" title="本月">本月</a></li>
								<li><a href="javascript:;" onclick="setDdate(4)" title="半年">半年</a></li>
								<li><a href="javascript:;" title="自定义" class="diy date-range" id="dDate">自定义</a></li>
							</ul>
						</dd>
					</dl>
					<input type="hidden" name="selectType" id="type" value="" />
					<dl class="select">
						<dt>限制类型</dt>
						<dd>
							<ul>
								<li><a href="javascript:;" onclick="setSelectType('')" title="限制类型">限制类型</a></li>
								<li><a href="javascript:;" onclick="setSelectType(2)" title="限制呼入，限制呼出">限制呼入，限制呼出</a></li>
								<li><a href="javascript:;" onclick="setSelectType(0)" title="限制呼出">限制呼出</a></li>
								<li><a href="javascript:;" onclick="setSelectType(1)" title="限制呼入">限制呼入</a></li>
							</ul>
						</dd>
					</dl>
					
          <%-- 操作人 --%>
          <input type="hidden" id="accountsStr" name="userAccounts" value="" />
				  <div class="reso-sub-dep fl_l">
						<input class="owner-sour" id="userNames" readonly="readonly" type="text" value="添加人">
						<div class="manage-owner-sour">
							<div class="sub-dep-ul">
								<ul id="tt1">

						        </ul>
				    		</div>
						    <div class="sure-cancle clearfix" style="width:120px">
								<a class="com-btnc bomp-btna com-btna-sty reso-owner-sure fl_l" onclick="seleTree()" href="javascript:;"><label>确定</label></a>
								<a class="com-btnd bomp-btna fl_l" onclick="unCheckTree()" href="javascript:;"><label>清空</label></a>
							</div>
						</div>
					</div>

				</div>
			  </div>
				<input class="query-button fl_r" type="submit" value="查&nbsp;询" id="query"/>
		   </div>

       <div class="com-btnlist hyx-cm-btnlist fl_l">
         <a href="javascript:;" class="com-btna demoBtn_a fl_l complaint_add">新增</a>
         <a href="javascript:;" class="com-btna demoBtn_b fl_l complaint_del">删除</a>
       </div>

		   <div class="com-table com-mta hyx-cla-table fl_l" style="display:block;margin-top:5px !important;width:100%;">
				<table id="t1" width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius ajax-table">
					<thead>
						<tr role="head" class="sty-bgcolor-b">
							<th><span class="sp sty-borcolor-b skin-minimal"><input type='checkbox' name='check_' class="check" id="checkAll" /></span></th>
							<th><span class="sp sty-borcolor-b">添加时间</span></th>
							<th><span class="sp sty-borcolor-b">限制类型</span></th>
							<th><span class="sp sty-borcolor-b">联系电话</span></th>
							<th>添加人</th>
						</tr>
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
            <tr class="no_data_tr sty-bgcolor-b"><td></td></tr>
          </tbody>
				</table>
			</div>
      <div class="com-bot" >
        <div class="com-page fl_r">
          <div class="page" id="new_page"></div>
          <div class="clr_both"></div>
        </div>
      </div>
      <div class="reminder-complist" ><span class="com-red">温馨提示：</span></div>
      <div class="reminder reminder-complist" >限制呼入：限制该号码对单位内坐席的呼入操作；</div>
      <div class="reminder reminder-complist" >限制呼出：限制单位内坐席对该号码的呼出操作；</div>
		</div>

<script type="text/x-handlebars-template" id="template">
  {{#each list}}
  <tr class="hyx-fur-tr {{even_odd @index}}">
    <td style="width:30px;">
      <div class="overflow_hidden w30 skin-minimal">
        <input type="checkbox" name="check_" id="chk_{{blackId}}" blackId="{{blackId}}" phone="{{phone}}"/>
      </div>
    </td>
    <td>
      {{formatDate inputDate "YYYY-MM-DD"}}
    </td>
    <td>
      {{complaintType type}}
    </td>
    <td>
      {{phone}}
    </td>
    <td>
      {{userName}}
    </td>
  </tr>
 {{/each}}
</script>
 </form>
</body>
</html>
