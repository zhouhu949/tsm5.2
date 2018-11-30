<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE>
<html>
<head>
    <%@ include file="/common/report_include.jsp" %>
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
    				<!-- 分组 -->
    				<div class="hyx-silent-p clearfix">
    					<div class="com-search-report fl_l" style="margin-top:0;min-height:0">
    						<label class="fl_l" for="" >部门名称：</label>
    						<dl class="select fl_l" style="position:relative;z-index:555;width:145px;margin:0 auto;margin-right:10px;">
								<dt id="groupNameStr">-请选择-</dt>
								<dd>
									<ul id="tt1" style="max-height:160px;margin-top:10px;text-align:left;">
										
									</ul>
									<div class="sure-cancle clearfix" style="width:120px;margin:15px auto 0;padding-bottom:10px;">
										<a class="com-btnc bomp-btna com-btna-sty reso-owner-sure fl_l" onclick="selGroup()" href="javascript:;" style="margin-left:10px;"><label style="height:19px;line-height:19px;">确定</label></a>
										<a class="com-btnd bomp-btna com-btna-cancle fl_l" onclick="clearGroup()" href="javascript:;" style="margin-left:10px;"><label style="height:19px;line-height:19px;">清空</label></a>
									</div>
								</dd>
							</dl>
    					</div>
    				</div>
    				<!-- <h2>客户统计</h2> -->
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
<script type="text/javascript" src="${ctx }/static/js/echarts-2.2.7/asset/js/bootstrap.min.js${_v}"></script>
<script type="text/javascript" src="${ctx }/static/thirdparty/echarts-3.3.2/echarts.min.js${_v}"></script>
<script type="text/javascript" src="${ctx }/static/js/view/report/teamLayoutPlan.js${_v}"></script>
<script type="text/javascript">
	window.onload=function(){
		resetHeight();
		changeGroup('');
	};
	
	function resetHeight(){
		var height = $(".person-static-max").height()+50;
		window.parent.$("#iframepage").css({"height":height+"px"});
	}
	
	function selGroup(){
		var ids = '';
		var names = '';
		$($("#tt1").tree('getChecked', 'checked')).each(function(index,node){
			ids+=node.id+",";
			names+=node.text+",";
		});
		
		if(ids != ''){
			ids = ids.substring(0,ids.length-1);
			names = names.substring(0,names.length-1);
		}
		$("#groupNameStr").text(names == '' ? '-请选择-' : names );
		changeGroup(ids);
	}
	
	function clearGroup(){
		var nodes = $('#tt1').tree('getChecked', 'checked');
		$.each(nodes,function(index,obj){
			 $('#tt1').tree("uncheck",obj.target);
		});
	}
	
	$(function(){
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
		//选择
		$(".select").find("dt").live("click",function(){
			var isHidden = $(this).next().is(":hidden");
			if(isHidden){
				$(this).next().slideDown(200);
				$(this).next().addClass("cur");
			}else{
				$(this).next().slideUp(200);
				$(this).next().removeClass("cur");
			}
			return false;
		});
		$(".reso-owner-sure").live("click",function(){
			$(this).parent().parent().slideUp(200);
			$(this).parent().parent().removeClass("cur");
			return false;
		});
		$(".com-btna-cancle").live("click",function(){
			return false;	
		});
		
		$(document).click(function(i){
			 $(".select dd").hide();
		});
	})
</script>
</body>
</html>