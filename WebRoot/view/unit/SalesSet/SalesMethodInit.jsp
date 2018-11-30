<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/include.jsp"%>
<tiles:insertDefinition name="unitSideLayout">
	<tiles:putAttribute name="title" type="string">系统属性设置</tiles:putAttribute>
	<tiles:putAttribute name="mainContent" type="string">
	<script type="text/javascript" src="${ctx}/js/unit/SalesSet/SalesMethodInit.js${_v}"></script>
	<!-- ajax session time out 处理 -->
<%@ include file="/common/ajaxSessionTimeout.jsp" %>
<form action="${ctx}/option/SalesMethodInit.do" method="post">
<p class="title"><b>系统属性设置 </b></p>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="role_manage">
  <tr>
    <td valign="top" width="172">
       <div class="role_manage_list">
	       <h2 class="role_manage_title">系统属性设置</h2>
		   <ul class="role_ul">
		   	  <li ${option.itemCode eq 'SALES_10005'?'class=all':''}><a href="${ctx}/option/SalesMethodInit.do?option.itemCode=SALES_10005">销售产品维护</a></li>
		      <li ${option.itemCode eq 'SALES_10001'?'class=all':''}><a href="${ctx}/option/SalesMethodInit.do?option.itemCode=SALES_10001">销售进程设计</a></li>
		      <li ${option.itemCode eq 'SALES_10002'?'class=all':''}><a href="${ctx}/option/SalesMethodInit.do?option.itemCode=SALES_10002">目标客户分类</a></li>
		      <li ${option.itemCode eq 'SALES_10006'?'class=all':''}><a href="${ctx}/option/SalesMethodInit.do?option.itemCode=SALES_10006">客户放弃原因</a></li>
		      <li ${option.itemCode eq 'RECORD_1000'?'class=all':''}><a href="${ctx}/option/SalesMethodInit.do?option.itemCode=RECORD_1000">录音范例分类</a></li>
		      <li ${option.itemCode eq 'SALES_10010'?'class=all':''}><a href="${ctx}/option/SalesMethodInit.do?option.itemCode=SALES_10010">行动标签维护</a></li>
		      <li ${option.itemCode eq 'SALES_10011'?'class=all':''}><a href="${ctx}/option/SalesMethodInit.do?option.itemCode=SALES_10011">服务评级维护</a></li>
              <li ${option.itemCode eq 'SALES_10012'?'class=all':''}><a href="${ctx}/option/SalesMethodInit.do?option.itemCode=SALES_10012">服务标签维护</a></li>
		   </ul>
	   </div>
    </td>
    <td valign="top">
       <div class="role_manage_cont">
		   <c:choose>
		   	<c:when test="${option.itemCode eq 'SALES_10006'}">
		   		<div class="tools_new">
  				  <p><input type="radio" class="ipt_radio" id="btn_on" name="subBtn" value="${dic.dictionaryId}_1" ${dic.dictionaryValue eq '1'?'checked':''}/><label for="btn_on">开启</label></p>
  				  <p><input type="radio" class="ipt_radio" id="btn_off" name="subBtn" value="${dic.dictionaryId}_0" ${dic.dictionaryValue eq '0'?'checked':''}/><label for="btn_off">关闭</label></p>
  				  <p class="shuom_new">（<!--  开启：应用子功能项；关闭：关闭子功能项-->开启：对非意向客户进行细化分类，可清晰了解【淘客户失败】原因。）</p>
  				</div>
		   	</c:when>
		   	<c:otherwise>
		   	<div class="sale float_l">
		   	   	<c:choose>
					<c:when test="${ (option.itemCode eq 'SALES_10010' || option.itemCode eq 'SALES_10012')&& page.totalResult ge 15}">
		   		   		&nbsp;
		   		   	</c:when>
		   		   	<c:otherwise>
		   		   	   <p class="button2"> <a href="javascript:toAddOrEdit('${option.itemCode}','0');" class="xinz_new"></a></p>
		   		   	</c:otherwise>
		   		</c:choose>
		   		 <p class="button2"><a href="javascript:toDelete();" class="del_new"></a></p>
		   	</div>		   	
		   	</c:otherwise>
		   </c:choose>
		
		<c:if test="${option.itemCode != 'SALES_10006'}">
		<div class="page">${option.page.pageSubStr}</div>
		<div class="clr_both"></div>
		</c:if>
		<div class="">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_list">
				<c:choose>
					<c:when test="${option.itemCode eq 'SALES_10005'}">
						  <thead>
	                         <tr>
	                           <th><input type="checkbox" id="checkAll" /></th>
	                           <th>产品名称</th>
	                           <th>是否默认值</th>
	                           <th>排序值</th>
	                           <th>计量单位</th>
	                           <th>操作</th>
	                         </tr>
	                      </thead>
	                      <tbody>
	                      <c:choose>
	                      	<c:when test="${!empty optionList}">
	                      		<c:forEach items="${optionList}" var="options" varStatus="v">
		                         <tr class="${v.count%2==0?'bgcolor_f5':''} hover_tr">
		                           <td><input type="checkbox" value="${options.optionlistId}" name="selectboxs" /></td>
		                           <td><div class="overflow_hidden w150" title="${options.optionName}">${options.optionName}</div></td>
		                           <td>
		                           	<div class="overflow_hidden w150">
		                           		<c:if test="${options.isDefault eq '1'}">√</c:if>
		                           	</div>
		                           </td>
		                           <td><div class="overflow_hidden w150">${options.sort}</div></td>
		                           <td><div class="overflow_hidden w150">${options.units}</div></td>
		                           <td>
		                           	<div class="overflow_hidden w150">
		                           		<a href="javascript:toAddOrEdit('${options.itemCode}','1','${options.optionlistId}');" class="edit" title="编辑"></a>
		                           		<c:if test="${options.isDefault ne '1'}">
		                           			<a href="###" id="def-${options.itemCode}-${options.optionlistId}-0" class="setDefault" title="设置默认"></a>
		                           		</c:if>
		                           		<c:if test="${options.isDefault eq '1'}">
		                           			<a href="###" id="def-${options.itemCode}-${options.optionlistId}-1" class="removeDefault" title="取消默认"></a>
		                           		</c:if>
		                           	</div>
		                           </td>
		                         </tr>
		                        </c:forEach>
	                      	</c:when>
	                      	<c:otherwise>
	                      		<tr>
                                    <td colspan="6" align="center"><b>该列表当前列表无数据！</b></td>
                                </tr>
	                      	</c:otherwise>
	                      </c:choose>
	                       </tbody>
					</c:when>
					<c:when test="${option.itemCode eq 'RECORD_1000' || option.itemCode eq 'SALES_10010' || option.itemCode eq 'SALES_10012'}">
							<thead>
			                 <tr>
			                   <th><input type="checkbox" id="checkAll"/></th>
			                   <th>数据项名称</th>
			                   <th>排序值</th>
			                   <th>操作</th>
			                 </tr>
			               </thead>
			               <tbody>
				              <c:choose>
				              	<c:when test="${!empty optionList}">
				              		<c:forEach items="${optionList}" var="options" varStatus="v">
						              	<tr class="${v.count%2==0?'bgcolor_f5':''} hover_tr">
						                   <td><input type="checkbox" value="${options.optionlistId}" name="selectboxs" /></td>
						                   <td><div class="overflow_hidden w400" title="${options.optionName}">${options.optionName}</div></td>
						                   <td><div class="overflow_hidden w50">${options.sort }</div></td>
						                   <td><div class="overflow_hidden w50"><a href="javascript:toAddOrEdit('${options.itemCode}','1','${options.optionlistId}');" class="edit"></a></div></td>
						                 </tr>
						              </c:forEach>
				              	</c:when>
				              	<c:otherwise>
				              		<tr>
				                        <td colspan="4" align="center"><b>该列表当前列表无数据！</b></td>
				                    </tr>
				              	</c:otherwise>
				              </c:choose>
				           </tbody>
					</c:when>
					<c:otherwise>
						<thead>
					       <tr>
					       <c:if test="${option.itemCode ne 'SALES_10006'}">
					         <th><input type="checkbox" id="checkAll" /></th>
					       </c:if>
					         <th>数据项名称</th>
					         <c:if test="${option.itemCode ne 'SALES_10006'}">
					         <th>是否默认值</th>
					         <th>排序值</th>
					         </c:if>
					         <c:if test="${option.itemCode eq 'SALES_10006'}">
					         <th>子项名称</th>
					         </c:if>
					         <th>操作</th>
					       </tr>
					    </thead>
					    <tbody>
					    <c:choose>
					    	<c:when test="${!empty optionList}">
					    		<c:forEach items="${optionList}" var="options" varStatus="v">
					    		<c:choose>
					    			<c:when test="${option.itemCode eq 'SALES_10006'}">
					    				<tr class="${v.count%2==0?'bgcolor_f5':''} hover_tr">
								         <td><div class="overflow_hidden w150" title="${options.optionName}">${options.optionName}（<span class="required">系统默认</span>）</div></td>
								         <td><div class="overflow_hidden w200" title="${options.subNameList}">${options.subNameList}</div></td>
								         <td><div class="overflow_hidden w150"><a href="###" id="find_${options.optionlistId}" class="edit"></a></div></td>
								        </tr>
					    			</c:when>
					    			<c:otherwise>
					    				<tr class="${v.count%2==0?'bgcolor_f5':''} hover_tr">
								         <td><input type="checkbox" value="${options.optionlistId}" name="selectboxs" /></td>
								         <td><div class="overflow_hidden w150" title="${options.optionName}">${options.optionName}</div></td>
								         <td>
								         	<div class="overflow_hidden w150"><c:if test="${options.isDefault eq '1'}">√</c:if></div>
								         </td>
								         <td><div class="overflow_hidden w150">${options.sort }</div></td>
								         <td>
								         	<div class="overflow_hidden w150">
								         		<a href="javascript:toAddOrEdit('${options.itemCode}','1','${options.optionlistId}');" class="edit" title="编辑"></a>
								         		<c:if test="${options.isDefault ne '1'}">
				                           			<a href="###" id="def-${options.itemCode}-${options.optionlistId}-0" class="setDefault" title="设置默认"></a>
				                           		</c:if>
				                           		<c:if test="${options.isDefault eq '1'}">
				                           			<a href="###" id="def-${options.itemCode}-${options.optionlistId}-1" class="removeDefault" title="取消默认"></a>
				                           		</c:if>
								         	</div>
								         </td>
								        </tr>
					    			</c:otherwise>
					    		</c:choose>
					    	</c:forEach>
					    	</c:when>
					    	<c:otherwise>
					    		<tr>
					    			<c:choose>
					    				<c:when test="${option.itemCode ne 'SALES_10006'}">
					    					<td colspan="5" align="center"><b>该列表当前列表无数据！</b></td>
					    				</c:when>
					    				<c:otherwise>
					    					<td colspan="3" align="center"><b>该列表当前列表无数据！</b></td>
					    				</c:otherwise>
					    			</c:choose>
                                </tr>
					    	</c:otherwise>
					    </c:choose>
					     </tbody>
					</c:otherwise>
				</c:choose>
			</table>
		</div>
		<c:if test="${option.itemCode != 'SALES_10006'}">
			<div class="page">${option.page.pageStr}</div>
		</c:if>
	</div>
    </td>
  </tr>
</table>
<input type="hidden" name="option.itemCode" value="${option.itemCode}" />
<input type="hidden" name="code"/>
<input type="hidden" name="id"/>
<input type="hidden" name="flg"/>
</form>
	</tiles:putAttribute>
</tiles:insertDefinition>