<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>邮件发送记录</title>
    <%@ include file="/common/include.jsp" %>
    <script src="${ctx}/static/js/view/email/loglist.js${_v}" type="text/javascript"></script>
</head>
<body>
<div class="hyx-cmc fl_l">
	<!--资源-->

	<div class=" fl_l">
		<form action="${ctx}/email/log/list" method="post">
		<div class="com-search hyx-cm-search">
			<div class="com-search-box fl_l">
				<p class="search clearfix"><input class="input-query fl_l" type="text" value="" /><label class="hide-span">输入发件人/邮件主题</label></p>
				<div class="list-box">
					<dl class="select">
						<dt style="border-right:none">发送时间</dt>
						<dd>
							<ul>
								<li><a href="###" title="日期">日期</a></li>
								<li><a href="###" title="当天">当天</a></li>
								<li><a href="###" title="本周">本周</a></li>
								<li><a href="###" title="本月">本月</a></li>
								<li><a href="###" title="半年">半年</a></li>
								<li><a href="###" title="自定义" class="diy date-range" id="d1">自定义</a></li>
							</ul>
						</dd>
					</dl>
					<div class="reso-sub-dep fl_l" style="margin-right:165px">
						<input id="s_startDate" type="hidden" value="">
						<input id="s_endDate" type="hidden" value="">
						<input class="owner-sour" type="text" value="联系人">

						<div class="manage-owner-sour">
							<div class="sub-dep-ul">
								<ul id="tt1" class="easyui-tree" >
									<li>
										<span><input type="checkbox">踦借古喻今</span>
										<ul>
											<li data-options="state:'closed'">
												<span><input type="checkbox">Sub 踦借古喻今 1</span>
												<ul>
													<li>
														<span><input type="checkbox">随便一睦11</span>
														<ul>
															<li><input type="checkbox">大中企业</li>
															<li><input type="checkbox">地大中企业</li>
														</ul>
													</li>
													<li>
														<span><input type="checkbox">埯当空舞</span>
													</li>
													<li>
														<span><input type="checkbox">极礌有</span>
													</li>
												</ul>
											</li>
											<li>
												<span><input type="checkbox">端口端口 2</span>
											</li>
											<li>
												<span><input type="checkbox">端口端口 3</span>
											</li>
											<li><input type="checkbox">端口端口 4</li>
											<li><input type="checkbox">端口端口 5</li>
										</ul>
									</li>
									<li>
										<span>端口端口21</span>
									</li>
								</ul>
							</div>
							<div class="sure-cancle clearfix" style="width:120px">
								<a class="com-btnc bomp-btna com-btna-sty reso-owner-sure fl_l" href="javascript:doQuery()"><label>确定</label></a>
								<a class="com-btnd bomp-btna fl_l" id="close02" href="javascript:;"><label>清空</label></a>
							</div>
						</div>
					</div>
				</div>
			</div>
			<input class="query-button fl_r" type="button" value="查&nbsp;询" onClick="doQuery();" />
		</div>
		<div class="com-btnlist hyx-cm-btnlist fl_l">
			<a class="com-btna demoBtn_a fl_l" href="javascript:doDelete();"><i class="com_btn com_btn_5"></i><label class="lab-mar">删除</label></a>
		</div>
		<div class="com-table com-mta hyx-cm-table fl_l" style="display:block;">
			<table id="tb" width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius">
				<thead>
				<tr class="sty-bgcolor-b">
					<th><span  class="sp sty-borcolor-b skin-minimal">
                        <input type="checkbox"  id="checkAll" name="a" class="check" />
                    </span></th>
					<th><span class="sp sty-borcolor-b">发送时间</span></th>
					<th><span class="sp sty-borcolor-b">收件人</span></th>
					<th><span class="sp sty-borcolor-b "><label class="lab-d"><span>邮件主题</span></label></span></th>
					<th>发件人</th>
				</tr>
				</thead>
				<tbody>
                <c:choose>
                <c:when test="${!empty list}">
                <c:forEach var="item" items="${list}" varStatus="i">

				<tr class="even" onclick="doSelect('${item.id}');">
					<td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="checkbox" name="ac" id="chk_${item.id }" value="${item.id }"/></div></td>
					<td><div class="overflow_hidden w120" title="<fmt:formatDate value="${item.inputtime}" pattern="yyyy/MM/dd HH:mm:ss"/>"><fmt:formatDate value="${item.inputtime}" pattern="yyyy/MM/dd HH:mm:ss"/></div></td>
					<td><div class="overflow_hidden w120" title="企蜂通信有限公司">企蜂通信有限公司</div></td>
					<td><div class="overflow_hidden w220" title="随便写写右三中全会都是露脐进爵"><a class="email-sub-dbl" href="###">随便写写右三中全会都是露脐进爵</a></div></td>
					<td><div class="overflow_hidden w120" title="惠英晓">惠英晓</div></td>
				</tr>
                </c:forEach>
                </c:when>
                    <c:otherwise>
                        <tr><td colspan="5" style="text-align: center;"><div class="overflow_hidden w120" title="当前列表无数据">当前列表无数据！</div></td></tr>
                    </c:otherwise>

                </c:choose>
				</tbody>
			</table>
		</div>
		<div class="com-bot">
            ${item.page.pageStr}

		</div>
		</form>
	</div>


	<div class="hyx-cm-right hyx-layout-side">
		<div id="emailinfo" class="hyx-cfu-card fl_l" style="height:508px;">
			<div class="send-record-prod">
				<div class="record-prod-title">关于产品一的产品介绍</div>
				<p><label for="">收件人：</label><span class="overflow_hidden">企蜂通信有限公司企蜂通信有限公司企蜂通信有限公司</span></p>
				<p><label for="">发送时间：</label><span class="overflow_hidden"><fmt:formatDate value="${info.inputtime}" pattern="yyyy-MM-dd HH:mm:ss"/></span></p>
				<p><label for="">收件人：</label><span class="overflow_hidden">企蜂通信有限公司</span></p>
				<p><label for="">发件人：</label><span class="overflow_hidden">企蜂通信有限公司</span></p>
			</div>
			<div class="mail-record-enve">
				<div class="record-enve-title">亲爱的用户</div>
				<p><span>你好，我们最近扔出了“广千邮件聚合”功能，现邀请你来试用你好，我们最近扔出了“广千邮件聚合”功能，现邀请你来试用你好，我们最近扔出了“广千邮件聚合”功能，现邀请你来试用你好，我们最近扔出了“广千邮件聚合”功能，现邀请你来试用你好，我们最近扔出了“广千邮件聚合”功能，现邀请你来试用你好，我们最近扔出了“广千邮件聚合”功能，现邀请你来试用你好，我们最近扔出了“广千邮件聚合”功能，现邀请你来试用你好，我们最近扔出了“广千邮件聚合”功能，现邀请你来试用你好，我们最近扔出了“广千邮件聚合”功能，现邀请你来试用你好，我们最近扔出了“广千邮件聚合”功能，现邀请你来试用你好，我们最近扔出了“广千邮件聚合”功能，现邀请你来试用你好，我们最近扔出了“广千邮件聚合”功能，现邀请你来试用</span></p>
				<p><span>现在，看看你的收件箱我们最近扔出了“广千邮件聚合”功能，现邀请你来试用我们最近扔出了“广千邮件聚合”功能，现邀请你来试用我们最近扔出了“广千邮件聚合”功能，现邀请你来试用我们最近扔出了“广千邮件聚合”功能，现邀请你来试用我们最近扔出了“广千邮件聚合”功能，现邀请你来试用我们最近扔出了“广千邮件聚合”功能，现邀请你来试用我们最近扔出了“广千邮件聚合”功能，现邀请你来试用</span></p>
				<div class="encl-osure clearfix">
					<div class="encl-osure-title"><img width="15" height="15" src="${ctx}/static/images/fashion.png" alt="">附件（1个）</div>
					<div class="encl-osure-left fl_l"><img width="30" height="30" src="${ctx}/static/images/fashion.png" alt=""></div>
					<div class="encl-osure-right fl_l">
						<p><span>慧营销操作手册.doc(100MB)</span></p>
						<p><a href="">下载</a><a href="">预览</a></p>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

</body>
</html>
