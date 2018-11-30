<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>客户管理-我的客户-筛查结果</title>
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
	.bomp-amcd .bomp-time .rightbox .tit .a{height:auto;}
	.bomp-amcd .bomp-time .rightbox .tit{height: auto; text-align: left;}
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
	$.post(ctx+"/phone/chooseResult/Json", {"impTime": dateTime,"type":type,"moduleId":$('#moduleId').val()},function(data){
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
        				if(result.status == 1){
        					html += '<label class="tit">'+
	        					'<span class="a fl_l"><b class="bomp-red">开始筛查......</b></span>'+
        					'</label>';
        				}else if(result.status == 2){
        				html += '<label class="tit">'+
                                '<span class="a fl_l"><b class="bomp-red">筛查进行中......</b></span>'+
                            '</label>';
        				}
        				else{
        				if(result.screenReson==''||result.screenReson==null){
        				html += '<label class="tit">'+
                                '<span class="a fl_l">共提交<b class="bomp-red">'+result.totalCount+'</b>条资源，成功筛查<b class="bomp-red">'+result.processedCount+'</b>条，共消耗<b class="bomp-red">'+result.moneyConsume+'</b>'+localStorage.getItem("product_currencyUnit")+'</span>'
                                '</label>';
        				}else{
        					html += '<label class="tit">'+
	        					'<span class="a fl_l">共提交<b class="bomp-red">'+result.totalCount+'</b>条资源，成功筛查<b class="bomp-red">'+result.processedCount+'</b>条，<b>'+result.screenReson +'</b>，共消耗<b class="bomp-red">'+result.moneyConsume+'</b>'+localStorage.getItem("product_currencyUnit")+'</span>'
        						'</label>';
        								}
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
    <input type="hidden" id="moduleId" name="moduleId" value="${moduleId}" />
	<c:choose>
		<c:when test="${empty results}">
			<label class="bomp-none-bg"><span>暂无数据！</span></label>
		</c:when>
		<c:otherwise>
		

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
		        			   </span>
	        				</label>
	        				<c:choose>
	        					<c:when test="${result.status eq '1'}">
                                    <label class="tit">
                                        <span class="a fl_l"><b class="bomp-red">开始筛查......</b></span>
                                        <!-- <i class="b fl_r"></i> -->
                                    </label>
	        					</c:when>
	        					<c:when test="${result.status eq '2'}">
                                    <label class="tit">
                                        <span class="a fl_l"><b class="bomp-red">筛查进行中......</b></span>
                                        <!-- <i class="b fl_r"></i> -->
                                    </label>
                                </c:when>

	        					<c:otherwise>
	        							<label class="tit">
				        					<span class="a fl_l">共提交<b class="bomp-red">${result.totalCount}</b>条资源，成功筛查<b class="bomp-red">${result.processedCount}</b>条，
				        					    <c:if test="${not empty result.screenReson }">
				        					        <b>${result.screenReson}</b>，
				        					    </c:if>
				        					   共消耗<b class="bomp-red">${result.moneyConsume}</b><span class="product-currencyUnit">蜂豆</span></span>
				        					<!-- <i class="b fl_r"></i> -->
			        					</label>

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
