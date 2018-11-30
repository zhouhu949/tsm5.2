<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp"%>
<%@ page import="com.qftx.base.shiro.ShiroUtil" %>
<% pageContext.setAttribute("shiroUser", ShiroUtil.getShiroUser());%>
<form id="rightForm_" method="post">
		<div class="hyx-cfu-card fl_l">
		<div class="tit fl_l"><span title="杭州企蜂科技有限公司" class="sp">杭州企蜂科技有限公司</span><div class="icon-conte-list" title="通讯录">
			<div class="drop" style="display:none;">
				<div class="arrow"><em>◆</em><span>◆</span></div>
					<ul>
						<li><p class="list fl_l"><span>张三（总监）：</span><label title="15898898774">15898898774</label><a href="javascript:;" class="icon-phone" title="拨打电话"></a><a href="javascript:;" class="icon-email demoBtn_d" title="邮件联系"></a></p></li>
					</ul>
				</div>
			</div>
			<a href="javascript:;" class="edit link demoBtn_c fl_r" title="编辑"></a>
		</div>
		<p class="list fl_l"><span>联系人：</span><label title="小米">小米</label></p>
		<p class="list fl_l"><span>联系电话：</span><label title="15898898774">15898898774</label><a href="javascript:;" class="icon-phone" title="拨打电话"></a><a href="javascript:;" class="icon-message demoBtn_b" title="发送短信" id="demoBtn_fa"></a></p>
		<p class="list fl_l"><span>邮&nbsp;&nbsp;箱：</span><label title="15898898774">453646096@qq.com</label><a href="javascript:;" class="icon-email demoBtn_d" title="绑定邮箱"></a></p>
		<p class="list fl_l"><span>联系地址：</span><label title="杭州市西湖区翠柏路">杭州市西湖区翠柏路</label></p>
	</div>
	<div class="hyx-cfu-tab">
		<ul class="tab-list clearfix">
			<li class="select li_first">跟进记录</li>
			<li class="li_last">点评信息</li>
		</ul>
	</div>
	<div class="hyx-wlm-cent-timeline hyx-cfu-timeline">
		<div class="timeline">
		    <ul>
	        	<li class="li-load">
	        		<div class="cfu-cir"></div>
	        		<div class="cfu-time">2015-07-30&nbsp;16:11:19&nbsp;电话联系</div>
		        </li>
		        <li class="li-load cfu-mt">
	        		<div class="cfu-cirb"></div>
	        		<div class="right">
	        			<div class="arrow"><em>◆</em><span>◆</span></div>
	        			<div class="cfu-box">
	        				<p class="cfu-list" style="text-overflow:clip;"><label class="lab">客服人员：徐大同（hyx2001）</label><i></i></p>
	        				<p class="cfu-list">服务标签：初次跟进，高意向客户</p>
	        				<p class="cfu-list com-none">下次联系时间：2015-01-01&nbsp;16:16:16</p>
	        				<p class="cfu-list com-none">联系小记：消防规则</p>
	        			</div>
	      			</div>
		        </li>
		        <li class="li-load">
	        		<div class="cfu-cir"></div>
	        		<div class="cfu-time">2015-07-30&nbsp;16:11:19&nbsp;电话联系</div>
		        </li>
		        <li class="li-load cfu-mt">
	        		<div class="cfu-cirb"></div>
	        		<div class="right">
	        			<div class="arrow"><em>◆</em><span>◆</span></div>
	        			<div class="cfu-box">
	        				<p class="cfu-list"><label class="lab">客服人员：徐大同（hyx2001）</label><i></i></p>
	        				<p class="cfu-list">服务标签：初次跟进，高意向客户</p>
	        				<p class="cfu-list com-none">下次联系时间：2015-01-01&nbsp;16:16:16</p>
	        				<p class="cfu-list com-none">联系小记：消防规则</p>
	        			</div>
	      			</div>
		        </li>
		        <li class="li-load">
	        		<div class="cfu-cir"></div>
	        		<div class="cfu-time">2015-07-30&nbsp;16:11:19&nbsp;电话联系</div>
		        </li>
		        <li class="li-load cfu-mt">
	        		<div class="cfu-cirb"></div>
	        		<div class="right">
	        			<div class="arrow"><em>◆</em><span>◆</span></div>
	        			<div class="cfu-box">
	        				<p class="cfu-list"><label class="lab">客服人员：徐大同（hyx2001）</label><i></i></p>
	        				<p class="cfu-list">服务标签：初次跟进，高意向客户</p>
	        				<p class="cfu-list com-none">下次联系时间：2015-01-01&nbsp;16:16:16</p>
	        				<p class="cfu-list com-none">联系小记：消防规则</p>
	        			</div>
	      			</div>
		        </li>
		        <li class="li-load">
	        		<div class="cfu-cir"></div>
	        		<div class="cfu-time">2015-07-30&nbsp;16:11:19&nbsp;电话联系</div>
		        </li>
		        <li class="li-load cfu-mt end">
	        		<div class="cfu-cirb"></div>
	        		<div class="right">
	        			<div class="arrow"><em>◆</em><span>◆</span></div>
	        			<div class="cfu-box">
	        				<p class="cfu-list"><label class="lab">客服人员：徐大同（hyx2001）</label><i></i></p>
	        				<p class="cfu-list">服务标签：初次跟进，高意向客户</p>
	        				<p class="cfu-list com-none">下次联系时间：2015-01-01&nbsp;16:16:16</p>
	        				<p class="cfu-list com-none">联系小记：消防规则</p>
	        			</div>
	      			</div>
		        </li>
	  		</ul>
		</div>
	</div>
	<div class="hyx-wlm-cent-timeline hyx-cfu-timeline com-none">
		<div class="timeline">
		    <ul>
		        <li class="li-load">
	        		<div class="cfu-cir"></div>
	        		<div class="cfu-time">2015-07-30&nbsp;16:11:19&nbsp;</div>
		        </li>
		        <li class="li-load cfu-mt">
	        		<div class="cfu-cirb"></div>
	        		<div class="right">
	        			<div class="arrow"><em>◆</em><span>◆</span></div>
	        			<div class="cfu-box">
	        				<p class="" style="padding:0 10px"><label class="">这次跟进挺好的，再接再厉，注意说话技巧</label></p>
	        			</div>
	        			<div class="comm-on-peop">
	        				<p class="cfu-list">点评人：hyx002</p>
	        			</div>
	      			</div>
		        </li>
		        <li class="li-load">
	        		<div class="cfu-cir"></div>
	        		<div class="cfu-time">2015-07-30&nbsp;16:11:19&nbsp;</div>
		        </li>
		        <li class="li-load cfu-mt">
	        		<div class="cfu-cirb"></div>
	        		<div class="right">
	        			<div class="arrow"><em>◆</em><span>◆</span></div>
	        			<div class="cfu-box">
	        				<p class="" style="padding:0 10px"><label class="">这次跟进挺好的，再接再厉，注意说话技巧</label></p>
	        			</div>
	        			<div class="comm-on-peop">
	        				<p class="cfu-list">点评人：hyx002</p>
	        			</div>
	      			</div>
		        </li>
		        <li class="li-load">
	        		<div class="cfu-cir"></div>
	        		<div class="cfu-time">2015-07-30&nbsp;16:11:19&nbsp;</div>
		        </li>
		        <li class="li-load cfu-mt">
	        		<div class="cfu-cirb"></div>
	        		<div class="right">
	        			<div class="arrow"><em>◆</em><span>◆</span></div>
	        			<div class="cfu-box">
	        				<p class="" style="padding:0 10px"><label class="">这次跟进挺好的，再接再厉，注意说话技巧</label></p>
	        			</div>
	        			<div class="comm-on-peop">
	        				<p class="cfu-list">点评人：hyx002</p>
	        			</div>
	      			</div>
		        </li>
	  		</ul>
		</div>
		<div class="comm-content fl_l">
			<p class="clearfix">
				<label class="fl_l" for="">发表评论：</label>
				<textarea class="fl_l" name="" id="" cols="30" rows="10"></textarea>
				<a class="com-btnc bomp-btna com-btna-sty fl_r" href="javascript:;">发表</a>
			</p>
		</div>
	</div>
</form>