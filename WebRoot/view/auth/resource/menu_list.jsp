<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
  <link rel="stylesheet" type="text/css" href="http://192.168.1.15/tsm/css/tsm/css/dxgj_1440.css?_v=3d78f345beb64f9b826b93f850d05d23"><link rel="stylesheet" type="text/css" href="/tsm/css/tsm/css/dxgj_css_1440.css?_v=3d78f345beb64f9b826b93f850d05d23">
	<script type="text/javascript">
		function addMenu(){
			parent.addTab(ctx+"/resource/toEditResourceMenu.do","添加资源");
		}
	
		function editMenu_(resourceId){
			parent.addTab(ctx+"/resource/toEditResourceMenu.do?resourceid="+resourceId,"修改资源");
		}
		
		function delMenu(resourceId){
			document.location.href=ctx+"/resource/delResourcMenu.do?status=1&id="+resourceId;
		}
		
		$(function(){
			$("#menuTree").tree({
				url:ctx+"/resource/resourceMng.do",
				formatter:function(node){
					var nodeText ="<label>"+ node.text+"</label>";			
					return nodeText;
				},
				onClick:function(node){
					$("#parentId").val(node.id);
					$("#myForm").submit();
				},
				onLoadSuccess:function(node,data){
					//移除图标和空格
					$(".tree-indent").remove();
					//选中
					var pid = $("#parentId").val();
					if(pid == ""){
						pid = "1"
					}
					var fnode = $('#menuTree').tree('find', pid);
					$('#menuTree').tree('select', fnode.target).tree("expandTo",fnode.target);
				}
			});
		});
		
	</script>   
<div class="padding_10">     
	<input type="button"  id="add_but" onclick="addMenu()" value="新增资源">
     <form action="resourceList.do" id="myForm" method="post">
     	<input type="hidden" name="parentId" id="parentId">
        <div class="tree-box tree-max-height">
			<div >
				<ul id="menuTree">
						<!-- 渠道树 -->
				</ul>											
			</div>
		</div>
        <div class="mycustomer_order_take">
          <p class="mycustomer_order_dxgj"><label>资源名称：</label><input name="resourceName" value="${item.resourceName}" type="text" class="text_code_dxgj"/></p>        
		  <p class="mycustomer_order_dxgj"><a href="javascript:loadPage(2);" class="search_dxgj">查询</a></p>
        </div>
        <div class="page_ordertaker">${item.page.pageSubStr}</div>
    	<div class="clr_both"></div>
        <div class="order_srcoll_x">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_resource">
          <thead>
              <tr>
                <th>操作</th>
                <th>菜单名称</th>
                <th>URL地址</th>
                <th>是否后台</th>
                <th>级别</th>
                <th>排序</th>
                <th>录入时间</th>
              </tr>
       </thead>
           <tbody>
	           <c:choose>
	        	<c:when test="${!empty menuLists}">
	        		<c:forEach items="${menuLists}" var="menu" varStatus="v">
	        			<tr class="${v.count%2==0?'bgcolor_f5':''} hover_tr">  
			                  <td>
			                  <div class="w45px">
			                 	<a href="###"  onclick="editMenu_('${menu.resourceId}')" title="编辑">编辑</a>
			                 	<a href="###"  onclick="delMenu('${menu.resourceId}')"  title="删除">删除</a>
			                  </div>
			                 </td>
			                 <td><div class="w70px overflow_hidden" title="${menu.resourceName}">${menu.resourceName}</div></td>
			                 <td><div class="w150px overflow_hidden" title="${menu.resourceString}">${menu.resourceString}</div></td>
			                 <td>
			                 	<div class="w100px overflow_hidden">
								<c:choose>
									<c:when test="${menu.isbackground ==1}">是</c:when>
									<c:otherwise>否</c:otherwise>
								</c:choose>
								</div>
							 </td>			
			                 <td><div class="w80px overflow_hidden" >${menu.level}</div></td>
			                 <td><div class="w80px overflow_hidden" >${menu.sort}</div></td>
			                 <td><div class="w70px overflow_hidden"><fmt:formatDate value='${orderInfo.tradeDate}' pattern='yyyy-MM-dd'/></div></td>                 
	        			</tr>
	        		</c:forEach>
	        	</c:when>
	        	<c:otherwise>
	        		<tr>
	                  <td colspan="7" style="border: solid #E1E1E1;border-width: 0px 0px 1px 1px;text-align:center;"><b>当前列表无数据！</b></td>
	                </tr>
	        	</c:otherwise>  
	          </c:choose>   
           </tbody>     
        </table>
        </div>
        <div class="page_ordertaker">${item.page.pageStr}</div>
    	<div class="clr_both"></div>
     </form>
</div>     