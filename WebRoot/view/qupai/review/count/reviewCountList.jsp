<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <%@ include file="/common/include.jsp" %>
    <title>销售统计-个人统计分析-日统计分析</title>
    <style>
        .com-table table thead tr  th{font-weight:normal;font-size:12px;}
        .color_grey{color:grey !important;}
    </style>
    <script type="text/javascript" src="${ctx}/static/js/view/worklog/dateUtils.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/my97datepicker/rangeLimit.js${_v}"></script>
    <script type="text/javascript">
	    var toWeekFrom="${toWeekFrom}";
		var toWeekTo="${toWeekTo}";
		var toMonthFrom="${toMonthFrom}";
		var toMonthTo="${toMonthTo}";
		function selGroup(){
            var ids = '';
            var names = '';
            $($("#tt1").tree('getChecked', 'checked')).each(function(index,node){
            	console.log(node);
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

        $(function () {
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
            });


            /* $(".hyx-silent-p").find('dt').click(function(){
             $(this).parent().find('li')
             })*/

           	$("#doQuery").click(function(){
           	    $("#noteType").val(3)
           		doQuery();
           	});

           	$("#toWeek").click(function(){
        		$("#noteType").val(1);
        		doQuery();
            });

        	$("#toMonth").click(function(){
        		$("#noteType").val(2);
        		/* $("#data_01").val("<fmt:formatDate value='${toMonthArray[0]}' pattern='yyyy-MM-dd'/>");
        		$("#data_02").val("<fmt:formatDate value='${toMonthArray[1]}' pattern='yyyy-MM-dd'/>"); */
        		doQuery();
        	});

        	$('[data-moneyformat]').each(function(idx,item){
                var $item = $(item);
                var num = $item.text();
                num = formatMoney(num);
                $item.text(num);
                $item.attr('title',num);
            });
        });

       	function doQuery(){
       		$("form")[0].submit();
       	}

        window.onload=function(){
            var height = $(".person-static-max").height()+30;
            window.parent.$("#iframepage").css({"height":height+"px"});
//            console.log(${item.startDate},${toWeekArray[0]})
        };
    </script>
    <style type="text/css" rel="stylesheet">
		<c:forEach items="${sorts}" var="sort">
			.com-table>table>tbody>tr>td:nth-child(${sort}),.com-table>table>thead>tr>th:nth-child(${sort}){display: none;}
		</c:forEach>
	</style>
</head>
<body>
<form action="${ctx }/credit/count/reviewCountList">
<input id="noteType" name="noteType" type="hidden" value="${noteType }"/>
<%-- <input id="orgId" name="orgId" type="hidden" value="rd"/>
<input id="timeType" name="timeType" type="hidden" value="${timeType }"/> --%>
<%-- <input id="dataIndex" name="dataIndex" value="${dataIndex}" type="hidden" />
<input id="dataName" name="dataName" value="${dataName}" type="hidden" />
<input id="index" name="index" value="${index}" type="hidden" /> --%>

<div class="person-static-max">
    <div class="hyx-silent-p">

        <div class="com-search-report fl_l" style="margin-top:0;min-height:0">
            <label class="fl_l" for="" >部门名称：</label>
            <input type="hidden" id="groupIds" name="groupIds" value="${item.groupIds}" />
            <dl class="select fl_l" style="position:relative;z-index:555;width:145px;margin:0 auto;margin-right:10px;">
                <dt id="groupNameStr">-请选择-</dt>
                <dd>
                    <ul id="tt1">

                    </ul>
                    <div class="sure-cancle clearfix" style="width:120px;margin:15px auto 0;padding-bottom:10px;">
                        <a class="com-btnc bomp-btna com-btna-sty reso-owner-sure fl_l" onclick="selGroup()" href="javascript:;" style="margin-left:10px;"><label style="height:19px;line-height:19px;">确定</label></a>
                        <a class="com-btnd bomp-btna com-btna-cancle fl_l" onclick="clearGroup()" href="javascript:;" style="margin-left:10px;"><label style="height:19px;line-height:19px;">清空</label></a>
                    </div>
                </dd>
            </dl>
        </div>

        <label class="fl_l" for="">选择日期：</label>
		<span class="fl_l" style="display: inline-block; position: relative;">
		<input id="data_01" name="startDate" value="${item.startDate }" type="text" class="selec-year-inpu com-form-data fl_l" onclick="WdatePicker({onpicked:pickedFunc1,maxDate:'#F{$dp.$D(\'data_02\')}'})" />
		<span class="silent-custo-span fl_l">&nbsp;—&nbsp;</span>
		<input id="data_02" name="endDate" value="${item.endDate }" type="text" class="selec-year-inpu com-form-data fl_l" onclick="WdatePicker({onpicked:pickedFunc2,minDate:'#F{$dp.$D(\'data_01\');}',maxDate:'%y-%M-{%d}'})" />
		</span>
		<a id="toWeek" href="javascript:;" class="silent-week fl_l ${item.noteType eq '1'?' color_grey':''}">本周</a>
		<a id="toMonth" href="javascript:;" class="silent-month fl_l ${item.noteType eq '2'?' color_grey':''}">本月</a>

		<a id="doQuery" href="javascript:;" class="com-btna fl_l"><i class="min-search"></i><label class="lab-mar">查询</label></a>
        <div class="static-time fl_r" style="width:280px;margin-right:0"><label for="">统计时间：</label>${item.startDate }至${item.endDate }</div>
    </div>

    <div class="com-table hyx-person-table hyx-cm-table">
        <table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-a">
            <thead>
            <tr class="sty-bgcolor-b">
                <th><span class="sp sty-borcolor-b">销售姓名</span></th>
                <th><span class="sp sty-borcolor-b">部门名称</span></th>
                <th><span class="sp sty-borcolor-b">新增放款数（次）</span></th>
                <th><span class="sp sty-borcolor-b">借款金额（元）</span></th>
                <th><span class="sp sty-borcolor-b">用户到账金额（元）</span></th>
                <th><span class="sp sty-borcolor-b">综合服务费（元）</span></th>
                <th><span class="sp sty-borcolor-b">服务费2</span></th>
                <th><span class="sp sty-borcolor-b">服务费3</span></th>
                <th><span class="sp sty-borcolor-b">借据金额（元）</span></th>
                <th><span class="sp sty-borcolor-b" data-defined="defined1">${definedNameMap["defined1"]}</span></th>
		   		<th><span class="sp sty-borcolor-b" data-defined="defined2">${definedNameMap["defined2"]}</span></th>
		   		<th><span class="sp sty-borcolor-b" data-defined="defined3">${definedNameMap["defined3"]}</span></th>
		   		<th><span class="sp sty-borcolor-b" data-defined="defined4">${definedNameMap["defined4"]}</span></th>
		   		<th><span class="sp sty-borcolor-b" data-defined="defined5">${definedNameMap["defined5"]}</span></th>
		   		<th><span class="sp sty-borcolor-b" data-defined="defined6">${definedNameMap["defined6"]}</span></th>
		   		<th><span class="sp sty-borcolor-b" data-defined="defined7">${definedNameMap["defined7"]}</span></th>
		   		<th><span class="sp sty-borcolor-b" data-defined="defined8">${definedNameMap["defined8"]}</span></th>
		   		<th><span class="sp sty-borcolor-b" data-defined="defined9">${definedNameMap["defined9"]}</span></th>
		   		<th><span class="sp sty-borcolor-b" data-defined="defined10">${definedNameMap["defined10"]}</span></th>
		   		<th><span class="sp sty-borcolor-b" data-defined="defined11">${definedNameMap["defined11"]}</span></th>
		   		<th><span class="sp sty-borcolor-b" data-defined="defined12">${definedNameMap["defined12"]}</span></th>
		   		<th><span class="sp sty-borcolor-b" data-defined="defined13">${definedNameMap["defined13"]}</span></th>
		   		<th><span class="sp sty-borcolor-b" data-defined="defined14">${definedNameMap["defined14"]}</span></th>
		   		<th><span class="sp sty-borcolor-b" data-defined="defined15">${definedNameMap["defined15"]}</span></th>
            </tr>
            </thead>
            <tbody>
            <c:choose>
			<c:when test="${!empty list}">
				<c:forEach var="bean" items="${list}" varStatus="i">
					<c:if test="${i.index%2!=0}"><tr class="sty-bgcolor-b"></c:if>
					<c:if test="${i.index%2==0}"><tr></c:if>
					<%-- <td style="width:150px;"><div class="overflow_hidden w150 link" title="<fmt:formatDate value="${bean.inputtime }" pattern="YYYY-MM-dd"/>"><fmt:formatDate value="${bean.inputtime }" pattern="YYYY-MM-dd"/></div></td>
	                <td style="width:150px;"><div class="overflow_hidden w150 link" title="${bean.callInNum }/${bean.callOutNum}">${bean.callInNum }/${bean.callOutNum}</div></td> --%>
	                <td><div class="overflow_hidden w100" title="${bean.userName }">${bean.userName }</div></td>
	                <td><div class="overflow_hidden w100" title="${bean.importDeptName }">${bean.importDeptName }</div></td>
	                <td><div class="overflow_hidden w100" title="${bean.count }">${bean.count }</div></td>
	                <td><div class="overflow_hidden w100" data-moneyformat="true" title="${bean.borrowMoney }">${bean.borrowMoney }</div></td>
	                <td><div class="overflow_hidden w100" data-moneyformat="true" title="${bean.accountMoney }">${bean.accountMoney }</div></td>
	                <td><div class="overflow_hidden w100" data-moneyformat="true" title="${bean.serviceMoney }">${bean.serviceMoney }</div></td>
	                <td><div class="overflow_hidden w100" data-moneyformat="true" title="${bean.serviceMoney2 }">${bean.serviceMoney2 }</div></td>
	                <td><div class="overflow_hidden w100" data-moneyformat="true" title="${bean.serviceMoney3 }">${bean.serviceMoney3 }</div></td>
	                <td><div class="overflow_hidden w100" data-moneyformat="true" title="${bean.billMoney }">${bean.billMoney }</div></td>
			   		<td><div class="overflow_hidden w70" data-moneyformat="true" title="${bean.defined1 }">${bean.defined1}</div></td>
					<td><div class="overflow_hidden w70" data-moneyformat="true" title="${bean.defined2 }">${bean.defined2}</div></td>
					<td><div class="overflow_hidden w70" data-moneyformat="true" title="${bean.defined3 }">${bean.defined3}</div></td>
					<td><div class="overflow_hidden w70" data-moneyformat="true" title="${bean.defined4 }">${bean.defined4}</div></td>
					<td><div class="overflow_hidden w70" data-moneyformat="true" title="${bean.defined5 }">${bean.defined5}</div></td>
					<td><div class="overflow_hidden w70" data-moneyformat="true" title="${bean.defined6 }">${bean.defined6}</div></td>
					<td><div class="overflow_hidden w70" data-moneyformat="true" title="${bean.defined7 }">${bean.defined7}</div></td>
					<td><div class="overflow_hidden w70" data-moneyformat="true" title="${bean.defined8 }">${bean.defined8}</div></td>
					<td><div class="overflow_hidden w70" data-moneyformat="true" title="${bean.defined9 }">${bean.defined9}</div></td>
					<td><div class="overflow_hidden w70" data-moneyformat="true" title="${bean.defined10 }">${bean.defined10}</div></td>
					<td><div class="overflow_hidden w70" data-moneyformat="true" title="${bean.defined11 }">${bean.defined11}</div></td>
					<td><div class="overflow_hidden w70" data-moneyformat="true" title="${bean.defined12 }">${bean.defined12}</div></td>
					<td><div class="overflow_hidden w70" data-moneyformat="true" title="${bean.defined13 }">${bean.defined13}</div></td>
					<td><div class="overflow_hidden w70" data-moneyformat="true" title="${bean.defined14 }">${bean.defined14}</div></td>
					<td><div class="overflow_hidden w70" data-moneyformat="true" title="${bean.defined15 }">${bean.defined15}</div></td>
				</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr class="no_data_tr" ><td style="text-align: center;" colspan="7"><b>当前列表无数据！</b></td></tr>
			</c:otherwise>
		    </c:choose>
            </tbody>
        </table>
    </div>

</div>
</form>


</body>
</html>
