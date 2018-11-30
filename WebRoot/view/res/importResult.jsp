<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>客户管理-我的客户-新增资源</title>
<!--公共样式-->
<!--公共样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css"><!--弹框样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/skin/default/color.css"><!--换肤样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/js/form/skins/all.css"><!--form样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/js/time/css/daterangepicker.css" /><!--选择区域日期插件样式-->
<%-- <link rel="stylesheet" type="text/css" href="${ctx}/static/js/idialog/skins/iblue.css"> --%>
<style type="text/css" rel="stylesheet">
    .label_oprater,.label_contact_result{font-size: 14px;}
	.label_oprater{margin-left:20px;}
	.label_contact_result{position:absolute;right:20px;top:0;font-weight:bold;}
</style>

<!--公共js-->
<script type="text/javascript" src="${ctx}/static/thirdparty/jquery/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/idialog/jquery.idialog.js?v=222" dialog-theme="default"></script><!--可移动弹框插件-->
<script type="text/javascript" src="${ctx}/static/js/idialog/idialog.common.js"></script><!--可移动弹框插件公共js-->
<script type="text/javascript" src="${ctx}/static/js/form/icheck.js"></script><!--表单优化-->
<script type="text/javascript" src="${ctx}/static/js/form/js/custom.min.js"></script><!--表单优化-->
<script type="text/javascript" src="${ctx}/static/js/time/moment.min.js"></script><!--选择区域日期插件-->
<script type="text/javascript" src="${ctx}/static/js/time/jquery.daterangepicker.js"></script><!--选择区域日期插件-->

<script type="text/javascript">
var datevalue1="";
var opera = true;
$(function(){
    $('.bomp-time').find('.b').click(function(){
    	if($(this).parents('.li-load').find('.miss').css('display') == 'none'){
    		$(this).parents('.li-load').addClass('li-click').find('.miss').slideDown();
    		$(this).parents('.li-load').siblings('.li-load').removeClass('li-click').find('.miss').slideUp();
    	}else{
    		$(this).parents('.li-load').removeClass('li-click').find('.miss').slideUp();
    	}
    });

    // 滚动条 分页 事件
    $('.timeline').each(function(i,item){
        $('.bomp-time').scroll(function(){
            if($(this)[0].scrollTop + $(this).height() >= $(this)[0].scrollHeight){
                getData("last");
            }           
            if($(this)[0].scrollTop == 0){
            	 getData("first");
            }
         });
    });
	
});


// 查看错误详情
function expErrorExcel(errorCode,fileId){
	var url = ctx+"/resimp/expErrorExcel?errorCode="+errorCode+"&fileId="+fileId;
	document.location = url;	
}

// 忽略并导入
function ignoreImp(errorCode,filedId){
	if(opera){
		opera = false;
		var url = ctx+"/resimp/ignoreImp";
		queDivDialog("res_remove_cust","确定导入？",function(){
			$("#errorCode_"+errorCode).hide();
			$.ajax({
				url: url,
				type:'POST',
				data:{'errorCode':errorCode,'fileId':filedId},
				dataType:'json',
				timeout: 15000, //超时时间：15秒
				error:function(){document.location.href = ctx+"/resimp/result?v="+new Date().getTime();},
				success:function(data){		
					if(data == 0){
						opera = true;
						document.location.href = ctx+"/resimp/result?v="+new Date().getTime();	
					}else{
						document.location.href = ctx+"/resimp/result?v="+new Date().getTime();	
					}				
					
				}
			});
		});		 
	}else{
		window.top.iDialogMsg("提示","正在忽略导入，请稍后操作！");
	}
	
}

function getData(type){
	var dateTime = $('.li-load:'+type).attr("datetime");
	$.post(ctx+"/resimp/result/json", {"impTime": dateTime,"type":type},function(data){
		var item = data.item;
		var results = data.results;
		var html='';
		for (var i=0;i<results.length;i++){
			var result = results[i];
			html+='<li datetime="'+result.impTime+'" class="li-load'+(i==0?' li-hover ':'')+((i==results.length&&type=="last")?' end ':'')+'">'+
        		'<label class="cir"></label>'+
        		'<div class="right">'+
        			'<div class="arrow"><em>◆</em><span>◆</span></div>'+
        			'<div class="rightbox">'+
        				'<label class="top">'+
        					'<span class="time fl_l">'+result.impTime+'<label class="label_oprater">操作人：'+result.inputAcc+'</label>';
        					if(result.isDetail > 0){
        						html+='<label class="label_contact_result">联系人导入结果</label>';
        					}
        				html+='</span></label>';
        				if(result.status == 0){
        					html += '<label class="tit">'+
	        					'<span class="a fl_l"><b class="bomp-red">正在导入中......</b></span>'+
	        					'<i class="b fl_r"></i>'+
        					'</label>';
        				}else{
        					html += '<label class="tit">'+
	        					'<span class="a fl_l">共解析数据<b class="bomp-red">'+result.totalNum+'</b>条，成功导入<b class="bomp-red">'+result.successNum+'</b>条，导入失败<b class="bomp-red">'+result.failNum+'</b>条</span>'+
	        					'<i class="b fl_r"></i>'+
        						'</label>'+
		        				'<div class="miss">';
		        					if(result.failNum > 0){
		        						html += '<label class="typ fl_l">导入失败数据分类</label>';
		        					}
		        					html += '<ol>';
			        					if(result.phoneRepeat > 0){
			        						html += '<li class="error-bg" id="errorCode_1"><label>电话号码重复：<b class="bomp-red">'+result.phoneRepeat+'</b>个</label><a href="javascript:;" onclick="expErrorExcel(\'1\',\''+result.fileId+'\')" class="link" >查看详情</a></li>';
			        					}
        								if(result.phoneFormart > 0){
        									html += '<li id="errorCode_2"><label>电话号码格式错误：<b class="bomp-red">'+result.phoneFormart+'</b>个</label><a href="javascript:;" class="link" onclick="expErrorExcel(\'2\',\''+result.fileId+'\')" >查看详情</a></li>';
        								}
        								if(result.custNameRepeat > 0){
        									html += '<li class="error-bg" id="errorCode_3"><label>单位名称重复：<b class="bomp-red">'+result.custNameRepeat+'</b>个</label><a href="javascript:;" onclick="expErrorExcel(\'3\',\''+result.fileId+'\')" class="link"  >查看详情</a></li>';	
        								}
        								if(result.companyUrlRepeat > 0){
        									html += '<li id="errorCode_4"><label>公司网站重复：<b class="bomp-red">'+result.companyUrlRepeat+'</b>个</label><a href="javascript:;" class="link" onclick="expErrorExcel(\'4\',\''+result.fileId+'\')" >查看详情</a></li>';
        								}
        							
        								if(result.defectRequired > 0){
    										html += '<li class="error-bg" id="errorCode_5"><label>缺失必填项：<b class="bomp-red">'+result.defectRequired+'</b>个</label><a href="javascript:;" onclick="expErrorExcel(\'5\',\''+result.fileId+'\')" class="link" >查看详情</a></li>';
    									}
        							    								
        								if(result.ownIllegalChar > 0){
        									html += '<li id="errorCode_6"><label>拥有非法字符：<b class="bomp-red">'+result.ownIllegalChar+'</b>个</label><a href="javascript:;" class="link" onclick="expErrorExcel(\'6\',\''+result.fileId+'\')" >查看详情</a></li>';
        								}
        								if(result.formartError > 0){
        									html += '<li class="error-bg" id="errorCode_7"><label>格式错误：<b class="bomp-red">'+result.formartError+'</b>个</label><a href="javascript:;" onclick="expErrorExcel(\'7\',\''+result.fileId+'\')" class="link" >查看详情</a></li>';
        								}
        								if(result.custNameNotExist > 0){
        									html += '<li class="error-bg" id="errorCode_11"><label>客户名称不存在：<b class="bomp-red">'+result.custNameNotExist+'</b>个</label><a href="javascript:;" onclick="expErrorExcel(\'11\',\''+result.fileId+'\')" class="link" >查看详情</a></li>';
        								}
        								html += '</ol></div> ';				
        				}    				
		html += '</div></div></li>';
		}
		console.log(html)
		if(results.length>0){
			$(".li-hover").removeClass("li-hover");
			if(type=="last"){
				$(".end").removeClass("end");
				$(".timeline>ul").append(html);
			}else{
				$(".timeline>ul").prepend(html);
			}
		}	
	});
}
</script>

</head>

<body> 
<div class='bomp-cen bomp-amcd' style="width:99%;height:auto;margin:0 5px;text-align:center;padding-bottom:0 !important;">
	<c:choose>
		<c:when test="${empty results}">
			<label class="bomp-none-bg"><span>暂无数据！</span></label>
		</c:when>
		<c:otherwise>
		
	<!-- <div class="bomp-box">
		<p class='bomp-p bomp-pos-b'>
			<input type='text' value='' class='ipt_a fl_l' />
			<label class="lab_hid">成员名称</label>
		</p>
		<p class='bomp-p bomp-pos-b'>
			<input type='text' value='' class='ipt_a date-range fl_l' id="d1" />
			<label class="lab_hid">导入时间</label>
		</p>
		<p class='bomp-p' style="margin:15px 0 0 0;">
			<a href="javascript:;" class="com-btna bomp-btna com-btna-sty fl_l" onClick="doQuery();"><label>查询</label></a>
	</div> -->

	<div class="bomp-time" style="height:548px;">
		<div class="timeline">
			<span class="top-icon"></span>
		    <ul>
		    	<c:forEach items="${results}" var="result" varStatus="state">		 	
		        <li datetime="${result.impTime}" class="li-load<c:choose><c:when test="${state.first}"> li-click</c:when><c:when test="${state.last}"> end</c:when></c:choose>">
	        		<label class="cir"></label>
	        		<div class="right">
	        			<div class="arrow"><em>◆</em><span>◆</span></div>
	        			<div class="rightbox">
	        				<label class="top">
	        					<span class="time fl_l">
		        					${result.impTime}
		        					<label class="label_oprater">
		        					     操作人：${result.inputAcc}
		        					</label>
		        					<c:if test="${result.isDetail gt 0}">
		        					 <label class="label_contact_result">联系人导入结果</label>
		        					</c:if>
		        			   </span>
	        				</label>
	        				<c:choose>
	        					<c:when test="${result.status eq '0'}">
	        						
	        						<label class="tit">
				        					<span class="a fl_l"><b class="bomp-red">正在导入中......</b></span>
				        					<i class="b fl_r"></i>
			        					</label>
	        					</c:when>
	        					<c:otherwise>
	        							<label class="tit">
				        					<span class="a fl_l">共解析数据<b class="bomp-red">${result.totalNum}</b>条，成功导入<b class="bomp-red">${result.successNum}</b>条，导入失败<b class="bomp-red">${result.failNum}</b>条</span>
				        					<i class="b fl_r"></i>
			        					</label>
					        				<div class="miss">
					        					<c:if test="${result.failNum gt 0}">
					        						<label class="typ fl_l">导入失败数据分类</label>
					        					</c:if>
					        					<ol>
					        						<c:if test="${result.phoneRepeat gt 0}">
					        							<li class="error-bg" id="errorCode_1"><label>电话号码重复：<b class="bomp-red">${result.phoneRepeat}</b>个</label><a href="javascript:;" onclick="expErrorExcel('1','${result.fileId}')" class="link" >查看详情</a></li>
					        						</c:if>
													<c:if test="${result.phoneFormart gt 0}">
														<li id="errorCode_2"><label>电话号码格式错误：<b class="bomp-red">${result.phoneFormart}</b>个</label><a href="javascript:;" class="link" onclick="expErrorExcel('2','${result.fileId}')" >查看详情</a></li>
													</c:if>
													<c:if test="${result.custNameRepeat gt 0}">
														<li class="error-bg" id="errorCode_3"><label>单位名称重复：<b class="bomp-red">${result.custNameRepeat}</b>个</label><a href="javascript:;" onclick="expErrorExcel('3','${result.fileId}')" class="link"  >查看详情</a></li>
													</c:if>
													<c:if test="${result.companyUrlRepeat gt 0}">
														<li id="errorCode_4"><label>公司网站重复：<b class="bomp-red">${result.companyUrlRepeat}</b>个</label><a href="javascript:;" class="link" onclick="expErrorExcel('4','${result.fileId}')" >查看详情</a></li>
													</c:if>
													<c:if test="${result.defectRequired gt 0}">
														<li class="error-bg" id="errorCode_5"><label>缺失必填项：<b class="bomp-red">${result.defectRequired}</b>个</label><a href="javascript:;" onclick="expErrorExcel('5','${result.fileId}')" class="link" >查看详情</a></li>
													</c:if>
													<c:if test="${result.ownIllegalChar gt 0}">
														<li id="errorCode_6"><label>拥有非法字符：<b class="bomp-red">${result.ownIllegalChar}</b>个</label><a href="javascript:;" class="link" onclick="expErrorExcel('6','${result.fileId}')" >查看详情</a></li>
													</c:if>
													<c:if test="${result.formartError gt 0}">
														<li class="error-bg" id="errorCode_7"><label>格式错误：<b class="bomp-red">${result.formartError}</b>个</label><a href="javascript:;" onclick="expErrorExcel('7','${result.fileId}')" class="link" >查看详情</a></li>
													</c:if>
													<c:if test="${result.custNameNotExist gt 0}">
														<li class="error-bg" id="errorCode_11"><label>客户名称不存在：<b class="bomp-red">${result.custNameNotExist}</b>个</label><a href="javascript:;" onclick="expErrorExcel('11','${result.fileId}')" class="link" >查看详情</a></li>
													</c:if>
													<!-- <li><label>电话号码为空号：<b class="bomp-red">${result.phoneSpace}</b>个</label><a href="javascript:;">忽略并导入</a><a href="javascript:;" class="link">查看详情</a></li>
													<li class="error-bg"><label>电话停机：<b class="bomp-red">${result.phoneStop}</b>个</label><a href="javascript:;">忽略并导入</a><a href="javascript:;" class="link">查看详情</a></li>
													<li class="error-bg"><label>电话从不接听：<b class="bomp-red">${result.phoneNeverAnswer}</b>个</label><a href="javascript:;">忽略并导入</a><a href="javascript:;" class="link">查看详情</a></li>
													<li><label>平均通话时长不足10秒：<b class="bomp-red">${result.callTimeAverageShort}</b>个</label><a href="javascript:;">忽略并导入</a><a href="javascript:;" class="link">查看详情</a></li>
													 -->
												</ol>
			        						</div>
	        					</c:otherwise>
	        				</c:choose>
	        				
	        			</div>
	      			</div>
		        </li>
		        </c:forEach>
	  		</ul>
		</div>
	</div>
		</c:otherwise>
	</c:choose>
	
</div>
</body>

</html>
