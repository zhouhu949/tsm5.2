<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
  <title></title>
  <%@ include file="/common/include.jsp"%>
  <script type="text/javascript" src="${ctx}/static/js/common/hack_common.js"></script>
  <script type="text/javascript" src="http://192.168.1.15/tsm/js/jquery/jquery.form.js${_v}"></script>
  <script type="text/javascript" src="http://192.168.1.15/tsm/js/jquery/jquery.cookie.js${_v}"></script>
  <script type="text/javascript" src="http://192.168.1.15/tsm/js/jquery/jquery.validate.js${_v}"></script>
  <script type="text/javascript" src="http://192.168.1.15/tsm/js/jquery/jquery.divbox.js${_v}"></script>
  <script type="text/javascript" src="http://192.168.1.15/tsm/js/easyui1.3.3/jquery.easyui.min.js${_v}"></script>
  <!-- JS时间插件 -->
  <script src="http://192.168.1.15/tsm/js/My97DatePicker/WdatePicker.js${_v}" type="text/javascript"></script>
  <link href="http://192.168.1.15/tsm/js/My97DatePicker/skin/WdatePicker.css${_v}" rel="stylesheet" type="text/css">
  <!-- JQuery弹窗插件 -->
  <link rel="stylesheet" href="http://192.168.1.15/tsm/js/artDialog/skins/blue.css${_v}">
  <script src="http://192.168.1.15/tsm/js/artDialog/jquery.artDialog.js${_v}&amp;skin=blue" type="text/javascript"></script>
  <script src="http://192.168.1.15/tsm/js/artDialog/plugins/iframeTools.js${_v}" type="text/javascript"></script>
  <script type="text/javascript" src="http://192.168.1.15/tsm/js/dialog/zDialog.js${_v}"></script>
  <script type="text/javascript" src="http://192.168.1.15/tsm/js/dialog/zDrag.js${_v}"></script>
  <script type="text/javascript" src="http://192.168.1.15/tsm/js/dialog/main_dialog.js${_v}"></script>
  <!-- 共同JS文件 -->
  <script type="text/javascript" src="http://192.168.1.15/tsm/js/common/common.js${_v}"></script>
  <script type="text/javascript" src="http://192.168.1.15/tsm/js/common/common_business.js${_v}"></script>
  <script type="text/javascript" src="http://192.168.1.15/tsm/js/common/replaceWord.js${_v}"></script>
  <script type="text/javascript">
    var resHeight = window.screen.height;
    var resWidth = window.screen.width;
    if(resWidth < 1280){
      <!-- 1024*768 -->
      document.write('<link rel="stylesheet" type="text/css" href="http://192.168.1.15/tsm/css/tsm/css/dxgj_1024.css${_v}" />');
      document.write('<link rel="stylesheet" type="text/css" href="http://192.168.1.15/tsm/css/tsm/css/dxgj_css.css${_v}" />');
    }
    else if(resWidth ==  1440)
    {
      <!-- 1440*900 -->
      document.write('<link rel="stylesheet" type="text/css" href="http://192.168.1.15/tsm/css/tsm/css/dxgj_1440.css${_v}" />');
      document.write('<link rel="stylesheet" type="text/css" href="http://192.168.1.15/tsm/css/tsm/css/dxgj_css_1440.css${_v}" />');
    }
    else if(resWidth ==  1366 || resWidth ==  1360)
    {
      <!-- 1440*900 -->
      document.write('<link rel="stylesheet" type="text/css" href="http://192.168.1.15/tsm/css/tsm/css/dxgj_1366.css${_v}" />');
      document.write('<link rel="stylesheet" type="text/css" href="http://192.168.1.15/tsm/css/tsm/css/dxgj_css_1366.css${_v}" />');
    }
    else{
      <!-- 1280*768 -->
      document.write('<link rel="stylesheet" type="text/css" href="http://192.168.1.15/tsm/css/tsm/css/dxgj_1280.css${_v}" />');
      document.write('<link rel="stylesheet" type="text/css" href="http://192.168.1.15/tsm/css/tsm/css/dxgj_css_1280.css${_v}" />');
    }
  </script>
  <link rel="stylesheet" type="text/css" href="http://192.168.1.15/tsm/css/tsm/css/dxgj_1440.css${_v}">
  <link rel="stylesheet" type="text/css" href="http://192.168.1.15/tsm/css/tsm/css/dxgj_css_1440.css${_v}">



  <script type="text/javascript">
    function searchMeet(){
      var orgid = $("#orgid").val();
      var ids = $("#ids").val();
     // alert($("#page.currentPage").val());
     $("#data-list").datagrid('load',{});
    }
  </script>
</head>
<body>
<form action="${ctx}/resource/list" method="post">

  <p class="title">系统管理 > <b>资源管理</b></p>
  <div class="padding_10 distributed_boxresource">
    <div class="distributed_resource">
      <p class="distributed_dxgj"><label>名称：</label><input type="text" id="resstaffNameid" name="resourceName" value="${item.resourceName }" class="text_code_dxgj" /></p>
      <p class="distributed_dxgj"><label>地址：</label><input type="text" id="resstaffAccid" name="resourceString" value="${item.resourceString }" class="text_code_dxgj" /></p>

      <p class="distributed_dxgj"><a href="javascript:document.forms[0].submit();"  class="search_dxgj">查询</a></p>
    </div>
  </div>
  <div class="clr"></div>

  <div class="page">${item.page.pageSubStr}</div>
  <div class="clr_both"></div>
  <div class="border2">
  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_list">
    <thead>
    <tr>
      <th>序号</th>
      <th>资源名称</th>
      <th>URL地址</th>
      <th>级别</th>
      <th>排序</th>

      <th>录入时间</th>
      <th>操作</th>

    </tr>
    </thead>
    <tbody>
    <c:choose>
      <c:when test="${!empty list}">
        <c:forEach var="operlog" items="${list}" varStatus="i">
          <tr class="${i.count%2==1?'':'bgcolor_f5'} hover_tr">
          <td><div class="overflow_hidden w50">${i.count }</div></td>
          <td><div class="overflow_hidden w80" title ="${operlog.resourceName}"  >${operlog.resourceName}</div></td>
          <td><div class="overflow_hidden w150" title ="${operlog.resourceString}"  >${operlog.resourceString}</div></td>
          <td><div class="overflow_hidden w50" title ="${operlog.level}"  >${operlog.level}</div></td>
          <td><div class="overflow_hidden w70" >${operlog.sort}</div></td>
               <td><div class="overflow_hidden w80" ><fmt:formatDate value="${operlog.createTime}" pattern="yyyy/MM/dd HH:mm"/></div></td>
          <td><div class="overflow_hidden w100" ><a class="edit_" href="javascript:openInfo('${operlog.resourceId}')" style="cursor:hand">删除</a>
            &nbsp;&nbsp;<a class='edit_' href="javascript:openInfo('${operlog.resourceId}')" style='cursor:hand'>编辑</a>
            &nbsp;&nbsp;<a class='edit_' href="javascript:openInfo('${operlog.resourceId}')" style='cursor:hand'>详情</a>


          </div></td>
        </c:forEach>
      </c:when>
      <c:otherwise>
        <tr><td colspan="8" style="text-align: center;"><b>当前列表无数据！</b></td></tr>
      </c:otherwise>
    </c:choose>
    </tbody>
  </table>
  </div>
  <div class="page">${item.page.pageStr}</div>
  <div class="clr_both"></div>
</form>
</body>
</html>
