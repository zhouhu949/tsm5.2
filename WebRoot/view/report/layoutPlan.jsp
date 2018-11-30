<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE>
<html>
<head>
    <%@ include file="/common/include.jsp" %>
    <title>销售统计-个人客户分布</title>
<style>
	.com-table table thead tr  th{font-weight:normal;font-size:12px;}
</style>
</head>
<body>
	<div class="person-static-max">
		<div class="analy-max-first clearfix">
            <div class="analy-a-contain clearfix">
    			<div class="analy-a-left fl_l">
    				<h2>客户统计</h2>
    				<p><label for="">客户总数：</label><span id="allCustNum">0个</span></p>
    				<p><label for="">意向客户：</label><span id="intCustNum">0个</span></p>
    				<p><label for="">签约客户：</label><span id="signCustNum">0个</span></p>
    				<p><label for="">沉默客户：</label><span id="silentCustNum">0个</span></p>
    				<p><label for="">流失客户：</label><span id="losingCustNum">0个</span></p>
    				<p><label for="">资源数量：</label><span id="poolCustNum">0个</span></p>
    			</div>
    			<div class="analy-a-right fl_l">
    				<div class="tip-box" id="main" style="height:300px;"></div>
    			</div>
            </div>
		</div>
        <div class="sales-process-analy">
            <div class="sale-proce-title"><span>销售进程分析</span></div>
            <div class="sale-proce-contain clearfix">
                <div class="proce-contain-left fl_l">
                    <div id="main02" class="team-custom-circle02"></div>
                </div>
                <div class="proce-contain-right fl_l">
                    <ul id="saleProcUl">

                    </ul>
                </div>
            </div>
        </div>
        <div class="sales-process-analy">
            <div class="sale-proce-title"><span>客户类型分析</span></div>
            <div class="sale-proce-contain clearfix">
                <div class="proce-contain-left fl_l">
                    <div id="main03" class="team-custom-circle03"></div>
                </div>
                <div class="proce-contain-right fl_l">
                    <ul id="custTypeUl">
						
                    </ul>
                </div>
            </div>
        </div>
        <div class="sales-process-analy">
            <div class="sale-proce-title"><span>产品类型分析</span></div>
            <div class="sale-proce-contain clearfix">
                <div class="proce-contain-left fl_l">
                    <div id="main04" class="team-custom-circle04"></div>
                </div>
                <div class="proce-contain-right fl_l">
                    <ul id="productUl">
                        
                    </ul>
                </div>
            </div>
        </div>
</div>
<script type="text/javascript" src="${ctx }/static/js/echarts-2.2.7/asset/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctx }/static/thirdparty/echarts-3.3.2/echarts.min.js"></script>
<script type="text/javascript" src="${ctx }/static/js/view/report/layoutPlan.js"></script>	
<script type="text/javascript">
	window.onload=function(){
		changeGroup('');
	};
</script>
</body>
</html>