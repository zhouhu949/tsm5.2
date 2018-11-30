<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<%@page import="com.qftx.base.enums.FollowCustEnum"%>
<%@ include file="/common/include-cut-version.jsp"%>
<%@ include file="/common/common-function/function.table.jsp"%>
<%@ include file="/common/common-function/function.date.jsp"%>
<%@ include file="/common/common-function/function.handlebar.jsp"%>
<%@ include file="/common/common-function/function.searchHead.jsp"%>

<title>客户跟进-销售机会</title>
<style type="text/css" rel="stylesheet">
<c:forEach items="${sorts}" var="sort">
	.com-table>table>tbody>tr>td:nth-child(${sort}),.com-table>table>thead>tr>th:nth-child(${sort}){display: none;}
</c:forEach>
</style>
</head>
<body>
<input type="hidden" id="shiro_issys" value="${shiroUser.issys }" />
<input type="hidden" id="shiro_account" value="${shiroUser.account }" />
<div class="hyx-layout">
<div class="hyx-cfu-left hyx-layout-content">
	<form id="myForm" method="post" data-render-url="${ctx }/cust/saleChance/custSaleChanceJson">
   <div class="com-search hyx-cfu-search">
      <div class="com-search-box fl_l">
	    <div class="search clearfix" >
		   <div class="select_outerDiv fl_l">
		   <span class="icon_down_5px"></span>
		    <select class="fl_l search_head_select"  name="queryType">
	   			<option value="1" >销售机会</option>
	   			<option value="2" >${shiroUser.isState eq 0 ?'单位名称' : '客户名称'}</option>
	   		</select>
		   	</div>
		   	<input class="input-query fl_l" type="text" name="queryText" value="${item.queryText }" />
		   	<label class="hide-span"></label>
		  </div>
	   <div class="list-box">

		   <c:set var="custTypeName"  value="预期成功率"/>
		   <c:forEach var="v" items="${custTypeList }">
				<c:if test="${v.optionlistId eq item.custTypeId }">
					<c:set var="custTypeName">${v.optionName }</c:set>
				</c:if>
			</c:forEach>
		   <dl class="select pos1"  data-input="[name='theorySuccessRate']">
				<dt>${custTypeName }</dt>
				<dd>
					<ul class="theory-success-rate">
						<li><a href="javascript:;"  data-value="">预期成功率</a></li>
						<li><a href="javascript:;"  data-value="1">0%</a></li>
						<li><a href="javascript:;"  data-value="2">20%</a></li>
						<li><a href="javascript:;"  data-value="3">50%</a></li>
						<li><a href="javascript:;"  data-value="4">70%</a></li>
						<li><a href="javascript:;"  data-value="5">90%</a></li>
					</ul>
				</dd>
				<input type="hidden" name="theorySuccessRate" id="theorySuccessRate" value=""/>
			</dl>
			
			<dl class="select pos2"  data-input="[name='isFollow']">
				<dt>机会状态</dt>
				<dd>
					<ul class="theory-success-rate">
						<li><a href="javascript:;"  data-value="">机会状态</a></li>
						<li><a href="javascript:;"  data-value="0">跟进中</a></li>
						<li><a href="javascript:;"  data-value="1">作废</a></li>
					</ul>
				</dd>
				<input type="hidden" name="isFollow" id="isFollow" value=""/>
			</dl>
			
			 	<!-- 预计签单时间  -->
				<input type="hidden" name="theoryStartSignDate" id="theoryStartSignDate" value="" />
			   <input type="hidden" name="theoryEndSignDate" id="theoryEndSignDate" value=""/>
			   <input type="hidden" name="tDateType" id="tDateType" value="${tDateType }" />
			   <c:set var="tDateName" value="预期签单时间" />
			   <c:choose>
			   		<c:when test="${tDateType eq 1 }"><c:set var="tDateName" value="当天" /></c:when>
			   		<c:when test="${tDateType eq 2 }"><c:set var="tDateName" value="本周" /></c:when>
			   		<c:when test="${tDateType eq 3 }"><c:set var="tDateName" value="本月" /></c:when>
			   		<c:when test="${tDateType eq 4 }"><c:set var="tDateName" value="半年" /></c:when>
			   		<c:when test="${tDateType eq 5 }">
			   			<fmt:parseDate var="msd" value="" pattern="yyyy-MM-dd" />
						<fmt:parseDate var="med" value="" pattern="yyyy-MM-dd" />
			   			<c:set var="tDateName">
			   				<fmt:formatDate value="" pattern="yy.MM.dd"/>/<fmt:formatDate value="" pattern="yy.MM.dd"/>
			   			</c:set>
			   		</c:when>
			   </c:choose>
				<dl class="select pos3">
					<dt>${tDateName }</dt>
					<dd>
						<ul>
							<li><a href="javascript:;" onclick="setTdate(0)">预期签单时间</a></li>
							<li><a href="javascript:;" onclick="setTdate(1)">当天</a></li>
							<li><a href="javascript:;" onclick="setTdate(2)">本周</a></li>
							<li><a href="javascript:;" onclick="setTdate(3)">本月</a></li>
							<li><a href="javascript:;" onclick="setTdate(4)">半年</a></li>
							<li><a href="javascript:;"  onclick="setTdate(5)" id="d3" class="diy date-range">自定义</a></li>
						</ul>
					</dd>
				</dl>
				<!-- 负责人 -->
		     <div class="reso-sub-dep ${shiroUser.issys eq 1?'pos4':'pos0' }">
				 <c:choose>
					 <c:when test="${shiroUser.issys eq 0 }">
						 <input class="owner-sour-nostr"  name="iAccs" type="text" readonly="readonly" value="${shiroUser.name}" />
					 </c:when>
					 <c:otherwise>
						 <input type="hidden" name="iAccs" id="iAccs"  value="${item.iAccs}">
						 <input class="owner-sour" id="inputAccNames"  type="text"  value="${!item.inputAccs?'负责人':item.inputAccs }" style="border-right:2px solid #e1e1e1;">
					 </c:otherwise>
				 </c:choose>
				 <div class="manage-owner-sour " style="height:370px;" >
					 <div class="sub-dep-ul" style="width:190px;" data-type="search-tree">
						 <ul id="tt2" >

						 </ul>
					 </div>
					 <div class="sure-cancle clearfix" style="width:120px">
						 <a class="com-btnc bomp-btna com-btna-sty reso-owner-sure fl_l" id="checkedId_2" href="javascript:;"><label>确定</label></a>
						 <a class="com-btnd bomp-btna reso-sub-clear fl_l" id="clearId_2"  href="javascript:;"><label>清空</label></a>
					 </div>
				 </div>
			 </div>
		</div>
	</div>
		<input class="query-button fl_r" type="button" value="查&nbsp;询"  id="query"/>
  </div>
  
	<div class="com-btnlist hyx-cfu-btnlist fl_l">
		<input type="hidden" id="custIds" > <!-- 跟进时间调整 选中资源Id s -->
		<input type="hidden" id="followIds" ><!-- 跟进时间调整 选中跟进Id s -->
		<a href="javascript:;" class="com-btna fl_l" id="chance_add" data-url="${ctx }/cust/saleChance/toAddSaleChance"><label>新增</label></a>
		<%-- <a href="javascript:;" class="com-btna fl_l" id="chance_dele" data-url="${ctx }/cust/saleChance/delSaleChance"><label>删除</label></a> --%>
	</div>
	<div class="com-table hyx-fur-table com-mta fl_l" style="display:block;">
		<table id="t1" width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius ajax-table" data-first-tr-selected="true">
			<thead>
				<tr class="sty-bgcolor-b" role="head">
					<th><span class="sp sty-borcolor-b skin-minimal"><input type="checkbox" id="checkAll" class="check" /></span></th>
					<th><span class="sp sty-borcolor-b">操作</span></th>
					<th><span class="sp sty-borcolor-b">销售机会名称</span></th>
					<th><span class="sp sty-borcolor-b">${shiroUser.isState eq 1 ? '客户名称':'客户姓名' }</span></th>
                    <c:if test="${shiroUser.isState eq 0  }">
                    	<th><span class="sp sty-borcolor-b">单位名称</span></th>
                    </c:if>
					<th><span class="sp sty-borcolor-b">预期签单金额</span></th>
					<th><span class="sp sty-borcolor-b">预期签单时间</span></th>
			   		<th><span class="sp sty-borcolor-b">预期成功率</span></th>
			   		<th><span class="sp sty-borcolor-b">机会状态</span></th>
			   		<th><span class="sp sty-borcolor-b">负责人</span></th>
			   		<th><span class="sp sty-borcolor-b">竞争对手</span></th>
			   		<th><span class="sp sty-borcolor-b">销售对策</span></th>
			   		<th><span class="sp sty-borcolor-b">商机备注</span></th>
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
		<div class="tip_search_data" >
			<div class="tip_search_text">
				<span><img alt="加载中" src="${ctx }/static/images/hua_loading.gif" /></span>
				<span>数据加载中…</span>
			</div>
			<div class="tip_search_error"></div>
		</div>
	</div>
	<div class="com-bot" style="width:99%;">
		<div class="com-page fl_r">
			<div class="page" id="new_page"></div>
			<div class="clr_both"></div>
		</div>
	</div>
	</form>
</div>
<div class="hyx-cm-right hyx-cfu-right hyx-layout-side" id="list_right">
	<div class="hyx-cfu-card fl_l">
		<div class="tit fl_l"><span  class="sp"></span>
			<a href="javascript:;" class="icon-edit fl_r img-gray" title="编辑"></a>
		</div>
		<c:choose>
			<c:when test="${shiroUser.isState eq 1 }">
					<!-- 企业资源 -->
				<p class="list fl_l"><span>联系人：</span></p>
				<p class="list fl_l"><span>联系电话：</span></p>
				<p class="list fl_l"><span>邮&nbsp;&nbsp;箱：</span></p>
				<p class="list fl_l"><span>联系地址：</span></p>
			</c:when>
			<c:otherwise>
				<!-- 个人资源 -->
				<p class="list fl_l"><span>性别：</span></p>
				<p class="list fl_l"><span>联系电话：</span></p>
				<p class="list fl_l"><span>邮&nbsp;&nbsp;箱：</span></p>
				<p class="list fl_l"><span>联系地址：</span></p>
		</c:otherwise>
		</c:choose>
	</div>
	<div class="hyx-cfu-tab">
		<ul class="tab-list clearfix">
			<li class="select li_first">跟进记录</li>
			<li >通话记录</li>
			<li class="li_last">点评信息</li>
		</ul>
	</div>
	<div class="hyx-wlm-cent-timeline hyx-cfu-timeline" id="follow_show">
		<!-- 暂无数据开始 -->
		<div class="none-bg">
			<p class="tit_none">暂无跟进记录！</p>
		</div>
		<!-- 暂无数据结束 -->
	</div>
</div>
</div>
<script type="text/javascript" src="${ctx}/static/js/common/tablerows.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/common/page.js${_v}"></script>
<script type="text/javascript" src="${ctx }/static/js/view/bill/render_table.js${_v}"></script>
<script type="text/x-handlebars-template" id="template">
	{{#each list}}
	<tr class="{{even_odd @index}}" data-sale-chanceid="chk_{{saleChanceId}}" data-custid="{{custId}}" data-followid="{{followId}}" >
		<td style="width:30px;">
			<div class="overflow_hidden w30 skin-minimal"><input type="checkbox" name="check_" id="chk_{{saleChanceId}}" data-custid="{{custId}}" data-sale-chanceid="{{saleChanceId}}" /></div>
		</td>
		<td style="width:130px;">
			<div class="overflow_hidden w80 link">
				{{saleChanceBtn ../isState custId custName company saleChanceId ../shareAccs}}
			</div>
		</td>
		<td>
			<div class="overflow_hidden w100" title="{{saleChanceName }}">{{saleChanceName }}</div>
		</td>
		{{#if ../isState}}
			<td>
				<div class="overflow_hidden w100" title="{{custName }}">{{custName }}</div>
			</td>
		{{else}}
			<td>
				<div class="overflow_hidden w100" title="{{custName }}">{{custName }}</div>
			</td>
			<td>
	           	<div class="overflow_hidden w100" title="{{company }}">{{company }}</div>
	       	</td>
		{{/if}}
		<td>
			<div class="overflow_hidden w80" title="{{theorySignMoney }}">{{theorySignMoney }}</div>
		</td>
		<td>
			<div class="overflow_hidden w80" title="{{formatDate theorySignDate 'YYYY-MM-DD' }}">{{formatDate theorySignDate 'YYYY-MM-DD' }}</div>
		</td>
		<td>
			<div class="overflow_hidden w50" title="{{saleChanceRate theorySuccessRate }}">{{saleChanceRate theorySuccessRate }}</div>
		</td>
		<td>
			<div class="overflow_hidden w50" title="{{#compare isFollow '==' '0'}}跟进中{{else}}作废{{/compare}}" data-isfollow="{{#compare isFollow '==' '0'}}true{{else}}false{{/compare}}">{{#compare isFollow '==' '0'}}跟进中{{else}}作废{{/compare}}</div>
		</td>
		<td>
			<div class="overflow_hidden w50" title="{{inputName }}">{{inputName }}</div>
		</td>
		<td>
			<div class="overflow_hidden w50" title="{{rival }}">{{rival }}</div>
		</td>
		<td>
			<div class="overflow_hidden w50" title="{{signPlan }}">{{signPlan }}</div>
		</td>
		<td>
			<div class="overflow_hidden w50" title="{{remark }}">{{remark }}</div>
		</td>
	</tr>
	{{/each}}
</script>
<script type="text/javascript">
	$(function(){
		var url =ctx+"/orgGroup/get_group_user_json";
    	$("#tt2").tree({
    		url:url,
    		checkbox:true,
    		onLoadSuccess:function(node,data){
    			var $accs = $('#iAccs').val();	
    			$("#tt2").tree("collapseAll");
				for(var i=0;i<data.length;i++){
					$("#tt2").tree("expand",data[i].target);
				}
				if($accs != null && $accs.length > 0){
    				var text='';
    				$($accs.split(',')).each(function(index,data){
    					var node = $("#tt2").tree("find",data);
    					if(node != null){
    						text+=node.text+',';
    						$("#tt2").tree("check",node.target);
    					}				
    				});
    				if(text != ''){
						text=text.substring(0,text.length-1);
						$("#inputAccNames").val(text);
					}else{
						$("#inputAccNames").val("负责人");
					}
    			}
    		}
    	});
		// tt2选择点保存
		$("#checkedId_2").click(function() {	
			var nodes =$("#tt2").tree("getChecked");
			var accs = "";
			var text='';
			$(nodes).each(function(index,obj){
				if(obj.attributes.type == 'M'){
					accs+=","+obj.id;
					text+=obj.text+',';
				}		
			});
			if(accs.length > 0){
				accs=accs.substring(1,accs.length);
				text=text.substring(0,text.length-1);			
			}else{
				text = "负责人";
			}
			$("#iAccs").val(accs);
			$("#inputAccNames").val(text);	
		});
		//tt2清空
		$("#clearId_2").click(function(){
			var nodes = $('#tt2').tree('getChecked');
			 $(nodes).each(function(index,obj){
	        	$('#tt2').tree('uncheck', obj.target);
	        });
	        $("#iAccs").val('');
	        $("#inputAccNames").val('负责人');
		});
	
		//客户卡片
	    $(".ajax-table").delegate("a[id^=cardInfo_]","click",function(){
	    	var custId = $(this).attr("id").split("_")[1];
			var custName = $(this).attr("custName")||"客户卡片";
			window.top.addTab(ctx+"/cust/card/custInfoCard?custId="+custId,custName);
	    });
	    
	    //编辑
	    $(".ajax-table").delegate("a[id^=edit_]","click",function(){
	    	var saleChanceId = $(this).attr("id").split("_")[1];
	    	var url = ctx+"/cust/saleChance/toEditSaleChance?saleChanceId="+saleChanceId;
	    	pubDivShowIframe('sale_chance_edit',url,'编辑销售机会',480,500);
	    });
	    
	    $("#chance_add").on("click",function(e){
	    	var _this = $(this);
	    	var url = _this.attr("data-url");
	    	pubDivShowIframe('sale_chance_add',url,'新增销售机会',480,500);
	    });
	    $(".ajax-table").delegate("a[id^=dele_]","click",function(e){
	    	var _this = $(this);
	    	var url = ctx+"/cust/saleChance/delSaleChance";
	    	var saleChanceIds = [];
	    	saleChanceIds.push(_this.attr("id").split("_")[1]);
	    	
			pubDivDialog("res_remove_cust","是否确认作废？",function(){
				$.ajax({
		    		url: url,
		    		type:"get",
		    		data: {"saleChanceIds":saleChanceIds.join()},
		    		success: function(data){
		    			if(data == -3){
		    				window.top.iDialogMsg("提示", "作废失败！");
		    			}else if(data == 0){
		    				window.top.iDialogMsg("提示", "作废成功！");
		    				loadData();
		    			}
		    		},
		    		error: function(status){}
		    	}); 
			});
	    });
	    
		$('#d3').dateRangePicker({
	    	showShortcuts:false,
	        format: 'YYYY-MM-DD'
	    }).bind('datepicker-apply',function(event,obj){
	    	var s_t = '';
	    	var e_t = '';
	    	if(obj.date1 != 'Invalid Date'){
	    		$('#theoryStartSignDate').val(moment(obj.date1).format('YYYY-MM-DD'));
	    		s_t = moment(obj.date1).format('YY.MM.DD');
	    	}
	    	if(obj.date2 != 'Invalid Date'){
	    		$('#theoryEndSignDate').val(moment(obj.date2).format('YYYY-MM-DD'));
	    		e_t = moment(obj.date2).format('YY.MM.DD');
	    	}
	    	var s = $(this).parents('.select');
	    	var dt=s.children("dt");
	        var dd=s.children("dd");
	        dt.html(s_t+'/'+e_t);
	        $("#tDateType").val(5);
	        dd.slideUp(200);
	        dt.removeClass("cur");
	        //s.css("z-index",z);
	    });
	    
	    $(".ajax-table").delegate("tbody tr","click",function(){
	    	var _this = $(this);
	    	var isFollow = _this.find("[data-isfollow]").data("isfollow");
	    	if(!isFollow){
	    		return false;
	    	}
    		loadRecordRight(_this.data("custid"),_this.data("followid"),_this.data("sale-chanceid").split("_")[1]);
	    });
	});
	/** 异步加载 右侧列表信息 */
	function loadRecordRight(reCustId,followId,saleChanceId){
		 $.ajax({
			url: ctx+'/cust/custFollow/List/followListRight',
			type:'get',
			data:{'reCustId':reCustId,'followId':followId,'saleChanceId':saleChanceId},
			dataType:'html',
			error:function(){alert("网络异常，请稍后再操作！")},
			success:function(data){
				$("#list_right").html(data);
			}
		});
	}
	
	function setTdate(i){
		$('#theoryStartSignDate').val('');
		$('#theoryEndSignDate').val('');
		$("#tDateType").val(i);
	};
</script>
</body>

</html>
