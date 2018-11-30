<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
  </script>
  <link rel="stylesheet" type="text/css" href="http://192.168.1.15/tsm/css/tsm/css/dxgj_1440.css">
  <link rel="stylesheet" type="text/css" href="http://192.168.1.15/tsm/css/tsm/css/dxgj_css_1440.css">

<!-- 此页面专属 js -->
<script src="${ctx}/static/js/view/unit/auth/org_member_list.js${_v}" type="text/javascript"></script>
</head>
<body>
<p class="title">成员管理 >&nbsp;<b>成员编辑</b></p>
<form  id="myForm" name="myForm" action="orgMemberList.do" method="post">
<input type="hidden" id="groupId" name="groupId" value="${groupId}">
<input type="hidden" id="orgId" name="orgId" value="${orgId}">

<table width="100%" border="0" cellspacing="0" cellpadding="0" class="create_team">
  <tr>  
    <td valign="top">
       <div class="create_team_cont">
         <div class="tools">
            <p class="tool"><label>帐号：</label><input type="text" class="create_text_code" id="account" name="orgMemberDto.userAccount" value="${orgMemberDto.userAccount}"/></p>
            <p class="tool"><label>员工姓名：</label><input type="text" class="create_text_code" name="orgMemberDto.userName" value="${orgMemberDto.userName}"/></p>
             <div class="tool">
               <label>开通情况：</label>
                <div class="create_select_border">
                      <div class="create_container">
                        <select class="create_select" name="orgMemberDto.orderState">
                        	<option value="">全部</option>
                        	<option value="0" <c:if test="${orgMemberDto.orderState eq '0'}">selected</c:if>>未开通</option>
             			 	<option value="1" <c:if test="${orgMemberDto.orderState eq '1'}">selected</c:if>>已开通</option>
             			 	<option value="2" <c:if test="${orgMemberDto.orderState eq '2'}">selected</c:if>>已过期</option>
                        </select>
                      </div>
                </div>
            </div>
         </div>
         
         <div class="tools">
            
            <div class="tool">
               <label>角色：</label>
                <div class="create_select_border">
                      <div class="create_container"> 
                        <select class="create_select"  name="orgMemberDto.roleId">
                        	<option value="">全部</option>
	             			<c:forEach items="${roles}" var="v">
	             			   <option value="${v.roleId}" <c:if test="${v.roleId eq orgMemberDto.roleId}">selected</c:if>>${v.roleName}</option>
							</c:forEach>
                        </select>
                      </div>
                </div>
            </div>
            <div class="tool"><label>到期时间：</label><input type="text" class="create_text_time Wdate" id="startDate" name="orgMemberDto.startDate" value="${orgMemberDto.startDate}" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})"/><i>-</i><input type="text" class="create_text_time Wdate" id="endDate" name="orgMemberDto.endDate" value="${orgMemberDto.endDate}" onclick="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})"/></div>
        </div>
        <div class="tools">
        	<div class="tool">
            	<label>帐号类型：</label>
            	<div class="create_select_border">
                      <div class="create_container"> 
            				<select  class="create_select" name="orgMemberDto.isFastTalk">
            					<option value="">全部</option>
            					<option value="0" <c:if test="${orgMemberDto.isFastTalk eq '0'}">selected</c:if>>电销帐号</option>
             			 		<option value="1" <c:if test="${orgMemberDto.isFastTalk eq '1'}">selected</c:if>>快话帐号</option>
            				</select>
            		  </div>
            	</div>
            </div>
            <p class="tool"><a href="javascript:void(0)" id="btn_find" class="search"></a></p>
        </div>
        <div class="button float_l">
        	<a href="javascript:void(0);" id="editRole" class="button_org1"></a>
        	<a href="javascript:void(0);" id="setShareGroup" class="button_org2"></a>
        	<a href="javascript:void(0);" id="changeGroup" class="button_org3" ></a>
        	<a href="javascript:void(0);" id="batchUpdatepwd" class="button_org4"></a> 
        	<a href="${ctx}/avoid/openUp.do?account=${username}" target="_blank" class="button_org5"></a>
        	<c:if test="${is_sysnc eq '1'}">
              <a href="javascript:void(0);" id="sysncId" class="button_org6" ></a> 
            </c:if>
        </div>
        <div class="page">${page.pageSubStr}</div>
        <div class="clr_both"></div>
        <div class="border">
           <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_list">
                 <thead>
                     <tr>
                       <th><input id="allSel" type="checkbox" /></th>
                       <th>帐号</th>
                       <th>员工姓名</th>
                       <th>通信号码</th>
                       <th>所在分组</th>
                       <th>帐号类型</th>
                       <th>角色</th>
                       <th>权限</th>
                       <th>服务情况</th>
                       <th>到期时间</th>
                       <th>操作</th>
                     </tr>
                  </thead>
                  <tbody>
                     <c:choose>
				<c:when test="${!empty orgMemberList }">
				<c:forEach items="${orgMemberList}" var="member" varStatus="vs">
                     <tr class="${vs.count%2 == 1?'':'bgcolor_f5'} hover_tr">
                       <td width="20"><input issel="1" type="checkbox" id="member_${vs.count}" name="memberAcc" memberId="${member.userId}" memberName="${member.userName}" value="${member.userAccount}"/></td>
                       <td><div class="overflow_hidden w80" title="${member.userAccount}">${member.userAccount}</div></td>
                       <td><div class="overflow_hidden w80" title="${member.userName}">${member.userName}</div></td>
                       <td><div class="overflow_hidden w80" title="${member.communicationNo}">${member.communicationNo}</div></td>
                       <td><div class="overflow_hidden w80" title="${member.groupName}">${member.groupName}</div></td>
                       <td>
                       <c:choose>
                      		<c:when test="${member.isFastTalk=='0'}"><c:set var="accountType" value="电销帐号"></c:set></c:when>
                      		<c:when test="${member.isFastTalk=='1'}"><c:set var="accountType" value="快话帐号"></c:set></c:when>
                      		<c:otherwise><c:set var="accountType" value="其他帐号"></c:set></c:otherwise>
                      	</c:choose>
                      	<div class="overflow_hidden w80" title="${accountType }">${accountType }</div></td>
                       <td><div class="overflow_hidden w60" title="${member.roleName}">${member.roleName}</div></td>
                       <td><div class="overflow_hidden w80" title="${member.shareGroupNames}">${member.shareGroupNames}</div></td>
                       <td><div class="overflow_hidden w60">
							<c:choose>
							<c:when test="${member.orderState == '1'}">已开通&nbsp;</c:when>
							<c:when test="${member.orderState == '2'}">已过期&nbsp;</c:when>
							<c:otherwise>未开通&nbsp;</c:otherwise>
						</c:choose>
					   </div></td>
                       <td><div class="overflow_hidden w80"><fmt:formatDate value="${member.serveTime}" pattern="yyyy-MM-dd"/></div></td>
                       <td>
                       	<div class="overflow_hidden w60">
                       		<a href="javascript:void(0);" class="edit" title="编辑" onclick="openMemberEdit('${member.userAccount}')"></a>
                       		<a href="javascript:void(0);" class="upd_pwd" title="修改密码" onclick="updPwd('${member.userAccount}')"></a>
                       	</div>
                       		<%--<a href="" class="order_server" title="订购服务"></a>--%>
                       </td>
                     </tr>
				</c:forEach>
				</c:when>
				<c:otherwise>
					<tr><td colspan="11" style="text-align: center;"><b>当前列表无数据！</b></td></tr>
				</c:otherwise>
			</c:choose>   
                  </tbody>     
           </table>
        </div>
        <div class="page">${page.pageStr}</div>
        <div class="clr_both"></div>
     </div>
    </td>
  </tr>
</table>
</form>

<!-- 角色编辑_弹出层  -->
<div id="editRoleDiv" style="display: none;">
<form method="post" id="editRoleForm">
	<div class="pop_layer edit_person">
	    <!-- 
	     <div class="pop_layer_title">
	        <h3>编辑角色</h3>
	        <a href=""></a>
	    </div>
	     -->
	     <div class="pop_layer_cont padding_10">
	           <p>
	               <label class="pop_label_admin">目标角色：</label>
	               <span class="select_pop_admin">
	               	<select class="select_pop_admin" name="roleId" id="newRoleId">
		           		<c:forEach items="${roles}" var="v">
							<option value="${v.roleId }">${v.roleName}</option>
						</c:forEach>
	               	</select>
	               </span>
	            </p>  
	          <div class="overflow_hidden margin_tb20px">
	          	<a href="javascript:void(0);" class="pop_submit" id="btn_editRole"></a>
	          	<a href="javascript:void(0);" class="pop_cancel"></a>
	          </div>
	     </div> 
	</div>
</form>
</div>

<!-- 设置权限 -->
<div id="setShareGroupDiv" style="display: none;">
<form  method="post" id="setShareGroupForm">
	<div class="pop_layer edit_person">
	     <!-- 
	     <div class="pop_layer_title">
	        <h3>设置权限</h3>
	        <a href=""></a>
	    </div>
	     -->
	     <div class="pop_layer_cont padding_10">
	           <p class="text_code_admin" style="text-align: left;">权限：</p>
	           <p>
	               <ul class="share_set">
               		<li>
       				    <span><input type="checkbox" class="checkbox" id="groupsSelect"/></span>
       				    <label>全选</label>
  			   		</li>
      			   </ul>
	           </p> 
	           <p class="overflow_hidden">
	               <ul class="share_set">
	               <c:forEach items="${orgGroups}" var="group" varStatus="vs">
          				    <li>
           				    <span><input type="checkbox" class="checkbox" name="shareGroupId" value="${group.groupId}"/></span>
           				    <label>${group.groupName}</label>
          				    </li>
          			   </c:forEach>
	               </ul>
	            </p>  
	          <div class="overflow_hidden margin_tb20px">
	          	<a href="javascript:void(0);" class="pop_submit" id="btn_setShareGroup"></a>
	          	<a href="javascript:void(0);" class="pop_cancel"></a>
	          </div>
	     </div> 
	</div>
</form>
</div>

<!-- 更换分组 -->
<div id="changeGroupDiv" style="display: none;">
<form  method="post" id="changeGroupForm">
	<div class="pop_layer edit_person">
	     <!-- 
	     <div class="pop_layer_title">
	        <h3>更换分组</h3>
	        <a href=""></a>
	    </div>
	    -->
	     <div class="pop_layer_cont padding_10">
	           <p>
	               <label class="pop_label_admin">目标分组：</label>
	               <span class="select_pop_admin">
	               <select class="select_pop_admin" id="newGroupId" name="newGroupId">
                   	<c:forEach items="${orgGroups}" var="group" varStatus="vs">
        				<option value="${group.groupId}">${group.groupName}</option>
        			</c:forEach>
	               </select>
	               </span>
	            </p>  
	          <div class="overflow_hidden margin_tb20px">
		          	<a href="javascript:void(0);" class="pop_submit" id="btn_changeGroup"></a>
		          	<a href="javascript:void(0);" class="pop_cancel"></a>
	          </div>
	     </div> 
	</div>
</form>
</div>
</body>
