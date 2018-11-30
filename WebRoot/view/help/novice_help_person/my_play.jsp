<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/include.jsp"%>
<html>
<head>
<title>我的计划</title>
<!--公共样式-->

<link rel="stylesheet" href="${ctx }/static/js/intro/css/introjs.css" type="text/css" /><!--新手引导-->
<link rel="stylesheet" href="${ctx }/static/js/intro/css/bootstrap-responsive.min.css" type="text/css" /><!--新手引导-->
<style type="text/css">
.tree-title{width:52px;}
/*.com-btna{top:20px;right:15px;}*/
.date_box .head{margin-top:-15px;}
</style>

<!--本页面js-->
<script type="text/javascript" src="${ctx }/static/js/date/date.js"></script>
<script type="text/javascript" src="${ctx }/static/js/date/nongli.js"></script>
<script type="text/javascript" src="${ctx }/static/js/popup_layer.js"></script>
<script type="text/javascript">
//选择时间
var date = new Date();
//服务器时间
var server_date = null;
//日历控件
var dateShow = null;


$(document).ready(function() {
//渲染日历控件
	dateShow = new DateShow("date_show");
	dateShow.init();

});
var resHeight = window.screen.height;
if(resHeight < 790){
  document.write('<link rel="stylesheet" type="text/css" href="${ctx}/static/js/date/css/date_768.css" />');
}
else{
  document.write('<link rel="stylesheet" type="text/css" href="${ctx}/static/js/date/css/date_900.css" />');
}
</script>
<script type="text/javascript">
$(function(){
    $('.my-play-a').click(function(){
        showIframe('alert_my_play_a','alert_my_play_a.html','上报计划',430,190);
    });
    var setHeight = function(height){
        $("#iframepage").css({"height":height+"px"});
    }
});
</script>
<script language="javascript">
$(function(){
     $('.hyx-mp-bar').find('.charts').each(function(i,item){
        var a = $(item).parent().parent().next().find('.a');
        var a_a = parseInt(a.attr('name'));   //实际完成值
        var a_b = parseInt(a.text());       //滚动的实际完成值
        var b = $(item).parent().parent().parent().find('.p');
        var b_a = parseInt(b.text());  //滚动的百分之百的值
        var f = $(item).parent().parent().next().find('.b');
        var f_a = parseInt(f.text());   //计划完成值
        var c = 0;
        var c_a = Math.round((a_a/f_a*100)*10)/10;  //完成百分之百的值
        var e = a_a*(1/100);
        var d = c_a/parseInt(a_a/e);
        var inter = setInterval(function(){
            a.text(a_b);
            b.text(b_a+'%');
            if(a_b <= a_a){
                if(a_a-a_b>=e){
                    a_b=Math.round((a_b+e)*10)/10;
                }else{
                    a_b=a_a;
                }
            }
            if(b_a <= c_a){
                if(c_a-b_a>=d){
                    b_a=Math.round((b_a+d)*10)/10;
                }else{
                    b_a=c_a;
                }
            }
            if(c <= c_a){
                c=c+d;
                if(c_a-c>=d){
                    $(item).css({"width":c+"%"});
                }else{
                    $(item).css({"width":c_a+"%"});
                }
                if(c >= 100){
                    $(item).css({'background-color':'#ff0000','border-color':'#ff0000'});
                }else{
                    $(item).css({'background-color':'#1979ca','border-color':'#1979ca'});
                }
            }
            if(a_b == a_a && b_a == c_a && c == c_a){
                a.text(a_a);
                b.text(c_a+'%');
                $(item).css({"width":c_a+"%"});
                clearInterval(inter);
            }
        },20);
    });
});
</script>
</head>
<body>
<div class="com-contb hyx-mp">
	<div class="hyx-wlm-left fl_l">
		<div class="hyx-wlm-left-date sty-borcolor-a com-radius">
            <a href="javascript:;" class="com-btna my-play-a fl_r"><i class="com_btn com_btn_16"></i><label class="lab-mar">上报计划</label></a>
			<div class="date_box" id="date_show"></div>
		</div>
        <div class="hyx-mp-bar sty-borcolor-a com-radius">
            <p class="tit">七月份目标</p>
            <div class="votebox fl_l">
                <dl class="barbox">
                    <dd class="barline">
                        <span style="width:0%;" class="charts"></span>
                    </dd>
                </dl>
                <span class="sp">回款金额：<label class="a" name="90">0</label>万/<label class="b">10</label>万</span>
                <p class="p">0</p>
            </div>
            <div class="votebox fl_l">
                <dl class="barbox">
                    <dd class="barline">
                        <span style="width:0%;" class="charts"></span>
                    </dd>
                </dl>
                <span class="sp">签约客户数：<label class="a" name="43">0</label>个/<label class="b">1</label>个</span>
                <p class="p">0</p>
            </div>
            <div class="votebox fl_l">
                <dl class="barbox">
                    <dd class="barline">
                        <span style="width:0%;" class="charts"></span>
                    </dd>
                </dl>
                <span class="sp">新增意向数：<label class="a" name="50">0</label>个/<label class="b">100</label>个</span>
                <p class="p">0</p>
            </div>
        </div>
	</div>
	<div class="my-work-right fl_l">
		<div class="hyx-mpr fl_l">
            <div class="year-sale-play">
                <ul class="ann-sale-plan clearfix">
                    <a href="my_play_right_a.html"><li class="select li_first">待联系资源列表（30/20）</li></a>
                    <a href="my_play_right_b.html"><li>意向客户列表（30/20）</li></a>
                    <a href="my_play_right_c.html"><li class="li_last">签约客户列表（30/20）</li></a>
                </ul>
            </div>
            <div class="hyx-mpr-box">
                <div class="com-search hyx-cm-search">
                    <div class="com-search-box fl_l" style="background-color:#fff;">
                       <p class="search clearfix"><input class="input-query fl_l" type="text" value="" style="background-color:#fff;" /><label class="hide-span">输入客户名称/联系人/联系电话</label></p>
                    </div>
                    <input class="query-button fl_r" type="button" value="查&nbsp;询" id="query" onClick="doQuery();" />
                </div>
                <p class="com-moTag">
                    <label class="a">数据标签：</label>
                    <a href="javascript:;" class="e e-hover">计划联系资源（20）</a>
                    <a href="javascript:;" class="e">临时计划资源（0）</a>
                    <a href="javascript:;" class="e">转签约（0）</a>
                    <a href="javascript:;" class="e">转意向（0）</a>
                    <a href="javascript:;" class="e">转公海（0）</a>
                    <a href="javascript:;" class="e">联系无变化（0）</a>
                    <a href="javascript:;" class="e">未联系（0）</a>
                </p>
                <div class="com-btnlist hyx-cm-btnlist fl_l">
                    <a href="javascript:;" class="com-btna demoBtn_a fl_l"><i class="min-set-prior"></i><label class="lab-mar">设置优先</label></a>
                    <a href="javascript:;" class="com-btna demoBtn_b fl_l"><i class="min-cancel-prior"></i><label class="lab-mar">取消优先</label></a>
                    <a href="javascript:;" class="com-btna demoBtn_c fl_l" id="step_1"><i class="min-add-resou"></i><label class="lab-mar">加入资源</label></a>
                    <a href="javascript:;" class="com-btna demoBtn_d fl_l"><i class="min-remove-resou"></i><label class="lab-mar">移出资源</label></a>
                    <a href="javascript:;" class="com-btna demoBtn_e fl_l"><i class="min-turn-will"></i><label class="lab-mar">放入公海</label></a>
                    <!--昨天开始-->
                    <a href="javascript:;" class="com-btna demoBtn_f fl_l"><label>转入明日计划</label></a>
                    <a href="javascript:;" class="com-btna demoBtn_g fl_l"><label>创建临时计划</label></a>
                    <!--昨天结束-->
                </div>
                <div class="com-table com-mta hyx-cm-table fl_l">
                    <table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius">
                        <thead>
                            <tr class="sty-bgcolor-b">
                                <th><span class="sp sty-borcolor-b skin-minimal"><input type="checkbox" name="a" class="check" /></span></th>
                                <th><span class="sp sty-borcolor-b">操作</span></th>
                                <th><span class="sp sty-borcolor-b">客户名称</span></th>
                                <th><span class="sp sty-borcolor-b">联系人</span></th>
                                <th><span class="sp sty-borcolor-b">联系电话</span></th>
                                <th><span class="sp sty-borcolor-b">临时计划</span></th>
                                <th><span class="sp sty-borcolor-b"><label class="lab-d"><span>分配时间</span><i></i></label></span></th>
                                <th><span class="sp sty-borcolor-b"><label class="lab-d"><span>联系结果</span><i></i></label></span></th>
                                <th>资源备注</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="checkbox" name="a" /></div></td>
                                <td style="width:30px;"><div class="overflow_hidden w30 link"><a href="javascript:;" class="icon-cust-card" title="客户卡片"></a></div></td>
                                <td><div class="overflow_hidden w70" title="大华科技"><i class="min-key-areas"></i><i class="min-next-day"></i>大华科技</div></td>
                                <td><div class="overflow_hidden w70" title="大宝">大宝</div></td>
                                <td><div class="overflow_hidden w100" title="123456789">123456789</div></td>
                                <td><div class="overflow_hidden w70" title="是">是</div></td>
                                <td><div class="overflow_hidden w120" title="2015-01-01">2015-01-01</div></td>
                                <td><div class="overflow_hidden w90" title="转客户">转客户</div></td>
                                <td><div class="overflow_hidden w200" title="此资源是好资源">此资源是好资源</div></td>
                            </tr>
                            <tr class="sty-bgcolor-b">
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr class="sty-bgcolor-b">
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr class="sty-bgcolor-b">
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr class="sty-bgcolor-b">
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr class="sty-bgcolor-b">
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="com-bot">
                    <div class="com-page fl_r">
                        <a href="javascript:;" class="no">首页</a>
                        <a href="javascript:;" class="no">上页</a>
                        <input type="text" value="1" class="pa" /><label>/2</label>
                        <a href="javascript:;">下页</a>
                        <a href="javascript:;">尾页</a>
                        <select>
                            <option value="10">10</option>
                            <option value="20">20</option>
                            <option value="30">30</option>
                            <option value="50">50</option>
                            <option value="100">100</option>
                        </select>
                        <label>条/页</label>
                    </div>
                </div>
            <!-- <div class="hyx-mpr-tit fl_l">
                <p class="com-red">温馨提示：</p><p class="p-a">联系无进展包含两类情况：一类是当日延后呼叫未处理的，另一类是当日联系后仍作为资源处理的。</p>
            </div> -->
            </div>
        </div>
	</div>
</div>
<script type="text/javascript" src="${ctx }/static/js/intro/js/intro.js"></script><!--新手引导-->
<script type="text/javascript">
var intro = introJs();
function startIntro(){
    intro.setOptions({
    steps: [
      {
        element: '#step_1',
        intro: "<p style='font-size:14px;width:165px;line-height:25px;position:absolute;left:15px;top:30px;'>添加完资源，您可以安排自己的计划了，点击“加入资源”按钮可以筛选出资源加入到计划中。</p><img src='${ctx}/static/images/hyx_inform.png' style='margin-left:165px;' />",
        position: 'right'
      }
    ]
  });

  intro.setOption("doneLabel","下一步").start();
}
window.onload=function(){
    startIntro();
    var height = $(".hyx-mpr").height();
    window.parent.$("#iframepage").css({"height":height+"px"});
}
$(function(){
    $(".introjs-skipbutton").live('click',function(){
        window.parent.window.location.href = '${ctx}/view/help/novice_help_person/scouring_cust_enter.jsp';
    })
});
</script>
</body>
</html>
