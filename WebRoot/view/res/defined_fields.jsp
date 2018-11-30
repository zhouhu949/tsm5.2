<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<% pageContext.setAttribute("ctx", request.getContextPath());%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

        <style type="text/css">
        	.bomp-p .select {
		    	position: relative;
		    	float: left;
		    	width: 260px;
		    }
		    
		    .bomp-p .select dt {
		    	width: 236px;
		    	background: url(${ctx}/static/images/drop-down-arrow.png) 245px center no-repeat rgb(255, 255, 255);
		    	font-weight: normal;
		    	font-size: 12px;
		    }
		    
		    .bomp-p .select,.sel_a{
		    	font-size:12px;
		    }
		    
		    .bomp-p .select dd{
		    	box-sizing: border-box;
		    	width: 100%;
		    	z-index: 200;
		    }
		    .bomp-p .select dd ul {
		    	padding: 0;
		    	width: 100%;
		    }
        </style>



    	<c:forEach var="field" items="${fieldSets}"  varStatus="vs">
    	<c:if test="${field.fieldCode.toString().contains('defined') && field.enable eq 1}">

    	   <c:choose>
            <c:when test="${ field.dataType eq 1 }">
					<p class='bomp-p'>
					   	<label class='lab_a fl_l'>
					   		<c:choose>
					   	      	<c:when test="${field.isRequired eq 1 }">
                                	<i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i>											   	      
					   	      	</c:when>
					   		</c:choose>
					    	${field.fieldName}：
					   	</label>
					   	<input type='text'   id="${field.fieldCode }"  name="custInfo.${field.fieldCode }"  value='${field.showValue }'  checkProp="${field.isRequired eq 0?'':'chk_1_1' }" class='ipt_a w_b fl_l' />
						<span class="error"  name='a' id="error_${ field.fieldCode}"></span>
					</p> 		                                    
            </c:when>
            <c:when test="${ field.dataType eq 2 }">
					<p class='bomp-p'>
					   	<label class='lab_a fl_l'>
					   	   	<c:choose>
					   	    	<c:when test="${field.isRequired eq 1 }">
                                    <i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i>											   	      
					   	    	</c:when>
					   	   	</c:choose>
					    	${field.fieldName}：
					   	</label>
					   	<input type='text'  onclick="WdatePicker()"  id="${field.fieldCode }"  name="custInfo.${field.fieldCode }"  value='${field.showValue }'  checkProp="${field.isRequired eq 0?'':'chk_5_1' }" class='ipt_a w_b fl_l' />
						<span class="error"  name='a' id="error_${ field.fieldCode}"></span>
					</p>		                                    
            </c:when>
            <c:when test="${ field.dataType eq 3 }">
					<p class='bomp-p' >
						<label class='lab_a fl_l'><c:if test="${field.isRequired eq 1 }"><i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i></c:if>${field.fieldName}：</label>
						<select name="custInfo.${field.fieldCode }" class='sel_a fl_l' <c:if test="${field.isRequired eq 1 }">checkProp="chk_2_1"</c:if>>
						    <option value="">--请选择--</option>
							<c:forEach var="os" items="${field.optionList }">
								<option value="${os.optionlistId}"  <c:if test="${field.showValue eq os.optionlistId}">selected="selected"</c:if>>${os.optionName }</option>
							</c:forEach>
						</select>	
						<span class="error" name='a' id="error_${field.fieldCode }"></span>				
					</p>
            </c:when>
            <c:when test="${ field.dataType eq 4 }">
            	<div class='bomp-p' >
					<label class='lab_a fl_l'><c:if test="${field.isRequired eq 1 }"><i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i></c:if>${field.fieldName}：</label>
                	<dl class="select pos2" data-input="[name='custInfo.${ field.fieldCode}']" data-multi="true">
                	 	<c:set var="optionNames"  value="" />
                		<c:forEach items="${field.optionList }" var="os">
							<c:if test="${field.showValue.contains(os.optionlistId)}">
								<c:set var="optionNames"  value="${optionNames},${os.optionName } " />
							</c:if>
						</c:forEach>
						<dt>
							<c:choose>
					   	    	<c:when test="${empty optionNames }">
                                    --请选择--											   	      
					   	    	</c:when>
					   	    	<c:otherwise>
					   	    		${optionNames.substring(1) }
					   	    	</c:otherwise>
					   	   	</c:choose>
						</dt>
						<dd>
							<ul>
								<c:forEach items="${field.optionList }" var="os">
									<li <c:if test="${field.showValue.contains(os.optionlistId)}">class="selected"</c:if>><a href="javascript:void(0);"  data-value="${os.optionlistId}" title="${os.optionName }">${os.optionName }</a></li>
								</c:forEach>
							</ul>
						</dd>
					</dl>
                    <input type="hidden" name="custInfo.${ field.fieldCode}" id="s_${ field.fieldCode}" value="${field.showValue }" checkProp="${field.isRequired eq 0?'':'chk_1_1' }" />
					<span class="error" name='a' id="error_${field.fieldCode }"></span>
				</div>
            </c:when>
              </c:choose>
            </c:if>
        </c:forEach>
        
        <script type="text/javascript">
        $(function(){
        	selectBitch();
        });
        </script>
