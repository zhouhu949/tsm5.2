<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
  <title></title>
  <%@ include file="/common/headContent.jsp"%>
  <script type="text/javascript">
    var resHeight = window.screen.height;
    var resWidth = window.screen.width;
    if(resWidth < 1280){
      <!-- 1024*768 -->
      document.write('<link rel="stylesheet" type="text/css" href="http://192.168.1.15/tsm/css/tsm/css/dxgj_1024.css?_v=3d78f345beb64f9b826b93f850d05d23" />');
      document.write('<link rel="stylesheet" type="text/css" href="http://192.168.1.15/tsm/css/tsm/css/dxgj_css.css?_v=3d78f345beb64f9b826b93f850d05d23" />');
    }
    else if(resWidth ==  1440)
    {
      <!-- 1440*900 -->
      document.write('<link rel="stylesheet" type="text/css" href="http://192.168.1.15/tsm/css/tsm/css/dxgj_1440.css?_v=3d78f345beb64f9b826b93f850d05d23" />');
      document.write('<link rel="stylesheet" type="text/css" href="http://192.168.1.15/tsm/css/tsm/css/dxgj_css_1440.css?_v=3d78f345beb64f9b826b93f850d05d23" />');
    }
    else if(resWidth ==  1366 || resWidth ==  1360)
    {
      <!-- 1440*900 -->
      document.write('<link rel="stylesheet" type="text/css" href="http://192.168.1.15/tsm/css/tsm/css/dxgj_1366.css?_v=3d78f345beb64f9b826b93f850d05d23" />');
      document.write('<link rel="stylesheet" type="text/css" href="http://192.168.1.15/tsm/css/tsm/css/dxgj_css_1366.css?_v=3d78f345beb64f9b826b93f850d05d23" />');
    }
    else{
      <!-- 1280*768 -->
      document.write('<link rel="stylesheet" type="text/css" href="http://192.168.1.15/tsm/css/tsm/css/dxgj_1280.css?_v=3d78f345beb64f9b826b93f850d05d23" />');
      document.write('<link rel="stylesheet" type="text/css" href="http://192.168.1.15/tsm/css/tsm/css/dxgj_css_1280.css?_v=3d78f345beb64f9b826b93f850d05d23" />');
    }
  </script><link rel="stylesheet" type="text/css" href="http://192.168.1.15/tsm/css/tsm/css/dxgj_1440.css?_v=3d78f345beb64f9b826b93f850d05d23"><link rel="stylesheet" type="text/css" href="/tsm/css/tsm/css/dxgj_css_1440.css?_v=3d78f345beb64f9b826b93f850d05d23">
	<script type="text/javascript">
	var flg = '${param.flg}';
	var i = true;
	if("${isInit}" == 1){
		var val = confirm("当前单位需要初始化！");
		if(val){
			location.href = "${ctx}/option/initUnit.do";
		}else{
			location.href = "${ctx}/j_spring_security_logout";
		}
	}

	if(flg == 1){
		if(i){
			dialogMsg(0,'单位初始化成功');
			i = false;
		}
	}	
	
	 if("${isHas}"=="1"){
        var strUrl="";
        if("${not empty roleId}"){
            strUrl="?roleId=${roleId}";
        }
	    location.href = "${ctx}/auth/role/unit_roleList.do"+strUrl;
	 }
	 
	var roleName="${roleName}";
	</script>
	<script type="text/javascript" src="${ctx}/static/js/view/unit/role/role_list.js"></script>
	</head>
	<body>
	<p class="title">成员管理 > <b>角色编辑</b></p>
	
	<form action="${ctx}/auth/role/role_save.do" id="myform" method="post">
       <table width="100%" border="0" cellspacing="0" cellpadding="0" class="role_team">
		  <tr>
		    <td valign="top" width="172">
		      <div class="role_team_list">
			        <h2 class="role_team_title">角色列表</h2>
			        <ul class="role_team_ul">
			           <li class="create_role"><a href="unit_roleList.do" class="role_team_name">+&nbsp;添加角色</a></li>
			           <c:forEach items="${roles}" var="v">
							<li id="role_${v.roleId}" title="${v.roleName}" class="${roleId eq v.roleId?'all':''}">
							<a href="unit_roleList.do?roleId=${v.roleId}" class="role_team_name">${v.roleName}</a>
							<a id="del_${v.roleId}" href="###" onclick="del_roleId('${v.roleId}')"></a>
							</li>
					 </c:forEach>
			        </ul>
			     </div>
		    
		    </td>
		    <td valign="top">
		      <div class="role_team_cont">
			         <div class="tools">
			            <input type="hidden" id="ids" name="ids"  />
			            <input type="hidden" id="roleId" name="roleId" value="${roleId }"/>
			            <input type="hidden" id="isPhones" name="isPhones" value="${isPhone}"/>
			            <input type="hidden" id="isRoleName" value="${!empty roleId?'true':''}" />
			            <p class="tool"><label><span class="required">*</span>角色名称：</label><input type="text" id="roleName" name="roleName" value="${roleName}" class="role_text_code" maxlength="20"/></p>
			        </div>
			        <div class="h10"></div>
			        <div class="role_function">
			            <div class="role_function_title">请勾选当前角色可查看的页面/功能项</div>
			            <div class="role_function_cont">
			               <p class="role_function_operate"><a href="###" id="allChecked">全选</a><a href="###" id="allClear">取消勾选</a></p>
			               <ul>
							<c:forEach items="${menu}" var="v" varStatus="vS">
							   <c:if test="${empty v.parentId and v.resourceId ne indexPage}">
							   <ul class="auth_cont1">
			                       <li><input type="checkbox" name="menu" class="check1" value="${v.resourceId }" onclick="parentClick('parent${v.resourceId}_${vS.index}')" id="parent${v.resourceId }_${vS.index}" ${!empty map02[v.resourceId]?'checked':''} /><label><b>${v.resourceName}</b></label></li>
			                   </ul>
			                    <ul class="auth_cont2">
				                   <c:forEach items="${menu}" var="rv" varStatus="rvS">
									   <c:if test="${rv.parentId eq v.resourceId}"> 
							                  <li class="margin_l20" title="${rv.resourceName}"><input type="checkbox" class="check1" value="${rv.resourceId }" name="menu"  onclick="menuClick('menu${v.resourceId}_${rvS.index}','parent${v.resourceId}_${vS.index}')" id="menu${v.resourceId}_${rvS.index}" ${!empty map02[rv.resourceId]?'checked':''} /><label>${rv.resourceName}</label></li>
					                   </c:if>
				                  </c:forEach>
			                   </ul>
							  </c:if>
							</c:forEach>
							</ul>
			            </div>
			            <div class="role_function_title">客户端通讯功能设置</div>
			            <div class="role_function_cont">
			                  <ul class="auth_cont1"><li><input type="checkbox" class="check1" id="isCall" ${isPhone==1?'checked':''}/><label><b>是否有工作时间限制</b></label></li></ul>   
			                  <div class="time_set">时间段设置：</div>																																																																	                            
			                  <ul class="day_checkbox"><li><input type="checkbox" id="workDay1" name="roleTimeControls[1].workDay" class="day_check1" value="1" ${isPhone!=1?'disabled':''} ${map[1].workDay==1?'checked':''}/><span class="day_span">星期一</span><input type="text" class="create_text_time Wdate" id="startTime1" name="roleTimeControls[1].startTime" value="${map[1].startTime}" onclick="WdatePicker({dateFmt:'H:mm:ss',minDate:'00:00:00',maxDate:'#F{$dp.$D(\'endTime1\')||\'23:59:59\'}'})"/><i>-</i><input type="text" class="create_text_time Wdate" id="endTime1" name="roleTimeControls[1].endTime" value="${map[1].endTime}" onclick="WdatePicker({dateFmt:'H:mm:ss',minDate:'#F{$dp.$D(\'startTime1\')||\'00:00:00\'}',maxDate:'23:59:59'})"/><span id="error_span1" class="day_error"></span></li></ul>   
			                  <ul class="day_checkbox"><li><input type="checkbox" id="workDay2" name="roleTimeControls[2].workDay" class="day_check1" value="2" ${isPhone!=1?'disabled':''} ${map[2].workDay==2?'checked':''}/><span class="day_span">星期二</span><input type="text" class="create_text_time Wdate" id="startTime2" name="roleTimeControls[2].startTime" value="${map[2].startTime}" onclick="WdatePicker({dateFmt:'H:mm:ss',minDate:'00:00:00',maxDate:'#F{$dp.$D(\'endTime2\')||\'23:59:59\'}'})"/><i>-</i><input type="text" class="create_text_time Wdate" id="endTime2" name="roleTimeControls[2].endTime" value="${map[2].endTime}" onclick="WdatePicker({dateFmt:'H:mm:ss',minDate:'#F{$dp.$D(\'startTime2\')||\'00:00:00\'}',maxDate:'23:59:59'})"/><span id="error_span2" class="day_error"></span></li></ul>   
			                  <ul class="day_checkbox"><li><input type="checkbox" id="workDay3" name="roleTimeControls[3].workDay" class="day_check1" value="3" ${isPhone!=1?'disabled':''} ${map[3].workDay==3?'checked':''}/><span class="day_span">星期三</span><input type="text" class="create_text_time Wdate" id="startTime3" name="roleTimeControls[3].startTime" value="${map[3].startTime}" onclick="WdatePicker({dateFmt:'H:mm:ss',minDate:'00:00:00',maxDate:'#F{$dp.$D(\'endTime3\')||\'23:59:59\'}'})"/><i>-</i><input type="text" class="create_text_time Wdate" id="endTime3" name="roleTimeControls[3].endTime" value="${map[3].endTime}" onclick="WdatePicker({dateFmt:'H:mm:ss',minDate:'#F{$dp.$D(\'startTime3\')||\'00:00:00\'}',maxDate:'23:59:59'})"/><span id="error_span3" class="day_error"></span></li></ul>   
			                  <ul class="day_checkbox"><li><input type="checkbox" id="workDay4" name="roleTimeControls[4].workDay" class="day_check1" value="4" ${isPhone!=1?'disabled':''} ${map[4].workDay==4?'checked':''}/><span class="day_span">星期四</span><input type="text" class="create_text_time Wdate" id="startTime4" name="roleTimeControls[4].startTime" value="${map[4].startTime}" onclick="WdatePicker({dateFmt:'H:mm:ss',minDate:'00:00:00',maxDate:'#F{$dp.$D(\'endTime4\')||\'23:59:59\'}'})"/><i>-</i><input type="text" class="create_text_time Wdate" id="endTime4" name="roleTimeControls[4].endTime" value="${map[4].endTime}" onclick="WdatePicker({dateFmt:'H:mm:ss',minDate:'#F{$dp.$D(\'startTime4\')||\'00:00:00\'}',maxDate:'23:59:59'})"/><span id="error_span4" class="day_error"></span></li></ul>   
			                  <ul class="day_checkbox"><li><input type="checkbox" id="workDay5" name="roleTimeControls[5].workDay" class="day_check1" value="5" ${isPhone!=1?'disabled':''} ${map[5].workDay==5?'checked':''}/><span class="day_span">星期五</span><input type="text" class="create_text_time Wdate" id="startTime5" name="roleTimeControls[5].startTime" value="${map[5].startTime}" onclick="WdatePicker({dateFmt:'H:mm:ss',minDate:'00:00:00',maxDate:'#F{$dp.$D(\'endTime5\')||\'23:59:59\'}'})"/><i>-</i><input type="text" class="create_text_time Wdate" id="endTime5" name="roleTimeControls[5].endTime" value="${map[5].endTime}" onclick="WdatePicker({dateFmt:'H:mm:ss',minDate:'#F{$dp.$D(\'startTime5\')||\'00:00:00\'}',maxDate:'23:59:59'})"/><span id="error_span5" class="day_error"></span></li></ul>   
			                  <ul class="day_checkbox"><li><input type="checkbox" id="workDay6" name="roleTimeControls[6].workDay" class="day_check1" value="6" ${isPhone!=1?'disabled':''} ${map[6].workDay==6?'checked':''}/><span class="day_span">星期六</span><input type="text" class="create_text_time Wdate" id="startTime6" name="roleTimeControls[6].startTime" value="${map[6].startTime}" onclick="WdatePicker({dateFmt:'H:mm:ss',minDate:'00:00:00',maxDate:'#F{$dp.$D(\'endTime6\')||\'23:59:59\'}'})"/><i>-</i><input type="text" class="create_text_time Wdate" id="endTime6" name="roleTimeControls[6].endTime" value="${map[6].endTime}" onclick="WdatePicker({dateFmt:'H:mm:ss',minDate:'#F{$dp.$D(\'startTime6\')||\'00:00:00\'}',maxDate:'23:59:59'})"/><span id="error_span6" class="day_error"></span></li></ul>   
			                  <ul class="day_checkbox"><li><input type="checkbox" id="workDay0" name="roleTimeControls[0].workDay" class="day_check1" value="0" ${isPhone!=1?'disabled':''} ${map[0].workDay==0?'checked':''}/><span class="day_span">星期日</span><input type="text" class="create_text_time Wdate" id="startTime0" name="roleTimeControls[0].startTime" value="${map[0].startTime}" onclick="WdatePicker({dateFmt:'H:mm:ss',minDate:'00:00:00',maxDate:'#F{$dp.$D(\'endTime0\')||\'23:59:59\'}'})"/><i>-</i><input type="text" class="create_text_time Wdate" id="endTime0" name="roleTimeControls[0].endTime" value="${map[0].endTime}" onclick="WdatePicker({dateFmt:'H:mm:ss',minDate:'#F{$dp.$D(\'startTime0\')||\'00:00:00\'}',maxDate:'23:59:59'})"/><span id="error_span0" class="day_error"></span></li></ul>   
			            </div>           
			            <div class="btn"><a href="###" id="btn_submit" class="btn_submit_1">保存</a></div>
			        </div>
		    </td>
		  </tr>
		</table>
    </form>
</body>