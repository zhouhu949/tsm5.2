<%@page import="com.qftx.base.shiro.ShiroUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
    <title></title>
    <%@ include file="/common/include.jsp" %>
   <style type="text/css">
		body{width:100%;height:100%;overflow:hidden;background-color:#f4f4f4;}
   </style>
    <script type="text/javascript" src="${ctx}/static/js/common/replaceWord.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/view/card/cust_info_card.js${_v}"></script>
    <script type="text/javascript" src="${ctx }/static/js/common/button_auth.js${_v}"></script>
	<script type="text/javascript">
		$(function(){
			/*左边、右边铺满整屏*/
			//var winhight=$(window).height();
			//$(".hyx-cc-tabTagBox,.hyx-cc-right").height(winhight-2);
			//$("#iframepage").contents().find(".hyx-cca").height(winhight-2);
			//$("#iframepage").contents().find(".hyx-cca-bot").height(winhight-80);
		});
		window.onload=function(){
			loadNum();
		};

		function loadNum(){
			$.ajax({
				url:'${ctx}/cust/card/getEventsNum',
				type:'post',
				data:{custId:'${custInfo.resCustId}'},
				dataType:'json',
				error:function(){},
				success:function(data){
					$('#sp1').text(data['1']);
					$('#sp2').text(data['2']);
					$('#sp3').text(data['3']);
					$('#sp4').text(data['4']);
					$('#sp5').text(data['5']);
					$('#sp6').text(data['6']);
          			$('#sp7').text(data['7']);
          			$('#sp8').text(data['8']);
				}
			});
		}
	</script>
</head>
<body>
<input type="hidden" id="pageTypeId" value="2" />
<input type="hidden" id="idReplaceWord" value="${idReplaceWord}" />
<div class="hyx-cc hyx-layout">
	<div class="hyx-sce-left hyx-cc-tabTagBox hyx-layout-side">
		<c:choose>
			<c:when test="${custInfo.state eq 1 }"><!-- 企业 -->

				<div class="card fl_l" style="position: absolute; left: 0; top: 0; z-index: 999;">
					<div class="p_a fl_l">
						<label title="${custInfo.name }">${custInfo.name }</label>
						<a href="javascript:;" class="demoBtn_a" custId="${custInfo.resCustId }" custType="${custType } " title="点评"><i class="icon-review"></i></a>
						<div class="icon icon-conte-list mail" title="通讯录">
							<div class="drop" title="">
								<div class="arrow"><em>◆</em><span>◆</span></div>
								<ul>
									<c:set var="mainLink" />
									<c:forEach items="${details }" var="detail">
										<li>
											<p class="list fl_l">
												<span title="${detail.name }<c:if test="${!empty detail.work }">（${detail.work }）</c:if>">${detail.name }<c:if test="${!empty detail.work }">（${detail.work }）</c:if></span><i style="display:inline-block;height:25px;line-height:20px;float:left;font-style:normal;">：</i>
												<label phone="tel" title="${empty detail.telphone ? detail.telphonebak : detail.telphone }">${empty detail.telphone ? detail.telphonebak : detail.telphone }</label>
												<c:if test="${(!empty detail.telphone) || (!empty detail.telphonebak) }">
													<a href="javascript:;" <c:if test="${shiroUser.serverDay gt 0}">id="call_${empty detail.telphone ? detail.telphonebak : detail.telphone }" custId="${custInfo.resCustId }" custName="${custInfo.name }" custType="${custInfo.type }" custState="${custInfo.status }" define1="${detail.name }" lastOptionId="${custInfo.lastOptionId }" lastOptionName="${lastOptionName }"</c:if> class="icon-phone tel ${shiroUser.serverDay gt 0 ? '' : 'img-gray' }" title="拨打电话"></a>
												</c:if>
												<c:if test="${!empty detail.email }">
													<a href="javascript:;" <c:if test="${shiroUser.serverDay gt 0}">onclick="emailBysend('${detail.name}','${detail.email }')"</c:if> class="e-mail demoBtn_m ${shiroUser.serverDay gt 0 ? '' : 'img-gray' }" title="发送邮件"></a>
												</c:if>
												<c:if test="${detail.isDefault eq 1 }">
													<c:set var="mainLink" value="${detail }"/>
												</c:if>
											</p>
										</li>
									</c:forEach>
								</ul>
							</div>
						</div>
						<c:if test="${not empty custType && custType ne 6 }">
							<c:choose>
								<c:when test="${commonOnwerOpen eq 1 && shiroUser.account eq custInfo.commonAcc }">
										<c:choose>
											<c:when test="${commonOnwerEdit eq 1 }">
												<i <c:if test="${userGroupType ne 2 }">btn-type="auth" auth_id="base_custEdit"</c:if> id="editInfo" custId="${custInfo.resCustId }" class="icon icon-edit demoBtn_b" title="编辑"></i>
											</c:when>
											<c:otherwise>
												<i <c:if test="${userGroupType ne 2 }">btn-type="auth" auth_id="base_custEdit"</c:if> class="icon icon-edit img-gray demoBtn_b" title="编辑"></i>
											</c:otherwise>
										</c:choose>
								</c:when>
								<c:otherwise>
										<i <c:if test="${userGroupType ne 2 }">btn-type="auth" auth_id="base_custEdit"</c:if> id="editInfo" custId="${custInfo.resCustId }" class="icon icon-edit demoBtn_b" title="编辑"></i>
								</c:otherwise>
							</c:choose>
						</c:if>
					</div>
					<p class="p_b fl_l">
						<label>资源分组：</label>
						<c:set var="groupName">未分组</c:set>
						<c:forEach items="${groupList }" var="group">
							<c:if test="${group.resGroupId eq custInfo.resGroupId }">
								<c:set var="groupName">${group.groupName }</c:set>
							</c:if>
						</c:forEach>
						<span title="${groupName }">${groupName }</span>
					</p>
					<c:set var= "mark" value="0" />
					<c:forEach items="${fieldSets}" var="fieldSet" varStatus="vs">
						<c:if test="${mark eq 5 }">
							<label class="fl_l" style="display:none;">
							<c:set var="mark" value="${mark+1 }" />
						</c:if>
						<c:choose>
							<c:when test="${fieldSet.fieldCode eq 'comArea' }">
								<c:if test="${!empty pname }">
									<p class="p_b fl_l">
										<c:set var="area"></c:set>
										<c:set var="area">${pname }</c:set>
										<c:if test="${!empty cname }">
											<c:set var="area">${pname }-${cname }</c:set>
											<c:if test="${!empty oname }">
												<c:set var="area">${pname }-${cname }-${oname }</c:set>
											</c:if>
										</c:if>
										<label>${fieldSet.fieldName}：</label><span title="${area }">${area }</span>
									</p>
									<c:set var="mark" value="${mark+1 }" />
								</c:if>
							</c:when>
							<c:when test="${fieldSet.fieldCode eq 'isMajor' }">

							</c:when>
							<c:when test="${fieldSet.fieldCode eq 'name' }">

							</c:when>
							<c:when test="${fieldSet.fieldCode eq 'companyTrade' }">
								<c:if test="${!empty custInfo[fieldSet.fieldCode] }">
									<p class="p_b fl_l">
										<label>${fieldSet.fieldName}：</label>
										<c:forEach items="${trades }" var="trade">
											<c:if test="${trade.optionlistId eq custInfo[fieldSet.fieldCode] }">
												<c:set var="tradeName">${trade.optionName }</c:set>
											</c:if>
										</c:forEach>
										<span title="${tradeName }">
											${tradeName }
										</span>
									</p>
									<c:set var="mark" value="${mark+1 }" />
								</c:if>
							</c:when>
							<c:when test="${fieldSet.fieldCode eq 'unithome' }">
								<c:if test="${!empty custInfo[fieldSet.fieldCode] }">
									<p class="p_b fl_l">
										<label>${fieldSet.fieldName}：</label><span title="${custInfo[fieldSet.fieldCode] }"><a href="###" onclick="showPublicUrl('${custInfo[fieldSet.fieldCode] }')">${custInfo[fieldSet.fieldCode] }</a></span>
									</p>
									<c:set var="mark" value="${mark+1 }" />
								</c:if>
							</c:when>
							<c:when test="${fieldSet.fieldCode eq 'defined16' }">
								<c:if test="${!empty custInfo.showdefined16 }">
									<p class="p_b fl_l">
										<label>${fieldSet.fieldName}：</label><span title="${custInfo.showdefined16 }">${custInfo.showdefined16 }</span>
									</p>
									<c:set var="mark" value="${mark+1 }" />
								</c:if>
							</c:when>
							<c:when test="${fieldSet.fieldCode eq 'defined17' }">
								<c:if test="${!empty custInfo.showdefined17 }">
									<p class="p_b fl_l">
										<label>${fieldSet.fieldName}：</label><span title="${custInfo.showdefined17 }">${custInfo.showdefined17 }</span>
									</p>
									<c:set var="mark" value="${mark+1 }" />
								</c:if>
							</c:when>
							<c:when test="${fieldSet.fieldCode eq 'defined18' }">
								<c:if test="${!empty custInfo.showdefined18 }">
									<p class="p_b fl_l">
										<label>${fieldSet.fieldName}：</label><span title="${custInfo.showdefined18 }">${custInfo.showdefined18 }</span>
									</p>
									<c:set var="mark" value="${mark+1 }" />
								</c:if>
							</c:when>
							<c:otherwise>
								<c:if test="${!empty custInfo[fieldSet.fieldCode] }">
									<p class="p_b fl_l">
										<label>${fieldSet.fieldName}：</label><span title="${custInfo[fieldSet.fieldCode] }">${custInfo[fieldSet.fieldCode] }</span>
									</p>
									<c:set var="mark" value="${mark+1 }" />
								</c:if>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					<c:if test="${mark gt 5 }">
						</label>
						<i class="icon_down" name="down"></i>
					</c:if>
				</div>
			</c:when>
			<c:otherwise>



                <div class="card fl_l" style="position: absolute; left: 0; top: 0; z-index: 999;">
					<p class="p_a fl_l">
						<label>${custInfo.name }</label>
						<a href="javascript:;" class="demoBtn_a" custId="${custInfo.resCustId }" custType="${custType } " title="点评"><i class="icon-review"></i></a>
						<c:if test="${not empty custType && custType eq 2 }">
							<i custId="${custInfo.resCustId }" class="${custInfo.isMajor eq 1 ? 'icon-focus-atten' : 'icon-nofocus' }" title="${custInfo.isMajor eq 1 ? '取消关注' : '重点关注'}"></i>
						</c:if>
						<c:if test="${not empty custType && custType ne 6}">
							<c:choose>
								<c:when test="${commonOnwerOpen eq 1 && shiroUser.account eq custInfo.commonAcc }">
										<c:choose>
											<c:when test="${commonOnwerEdit eq 1 }">
												<i id="editInfo" <c:if test="${userGroupType ne 2 }">btn-type="auth" auth_id="base_custEdit"</c:if> custId="${custInfo.resCustId }" class="icon-edit demoBtn_b" title="编辑"></i>
											</c:when>
											<c:otherwise>
												<i <c:if test="${userGroupType ne 2 }">btn-type="auth" auth_id="base_custEdit"</c:if> class="icon-edit img-gray demoBtn_b" title="编辑"></i>
											</c:otherwise>
										</c:choose>
								</c:when>
								<c:otherwise>
										<i <c:if test="${userGroupType ne 2 }">btn-type="auth" auth_id="base_custEdit"</c:if> id="editInfo" custId="${custInfo.resCustId }" class="icon-edit demoBtn_b" title="编辑"></i>
								</c:otherwise>
							</c:choose>
						</c:if>
					</p>
					<p class="p_b fl_l">
						<label>资源分组：</label>
						<c:set var="groupName">未分组</c:set>
						<c:forEach items="${groupList }" var="group">
							<c:if test="${group.resGroupId eq custInfo.resGroupId }">
								<c:set var="groupName">${group.groupName }</c:set>
							</c:if>
						</c:forEach>
						<span title="${groupName }">${groupName }</span>
					</p>
					<c:set var= "mark" value="0" />
					<c:forEach items="${fieldSets}" var="fieldSet">
						<c:if test="${mark eq 5 }">
							<label class="fl_l" style="display:none;">
							<c:set var="mark" value="${mark+1 }" />
						</c:if>
						<c:choose>
							<c:when test="${fieldSet.fieldCode eq 'sex' }">
<%-- 								<c:choose> --%>
<%-- 									<c:when test="${custInfo[fieldSet.fieldCode] eq 1}"><i class="sex_a" title="男"></i></c:when> --%>
<%-- 									<c:when test="${custInfo[fieldSet.fieldCode] eq 2}"><i class="sex" title="女"></i></c:when> --%>
<%-- 								</c:choose> --%>
									<c:if test="${!empty custInfo[fieldSet.fieldCode] }">
										<c:choose>
											<c:when test="${custInfo[fieldSet.fieldCode] eq 1}"><c:set var="sexName" value="男" /></c:when>
											<c:when test="${custInfo[fieldSet.fieldCode] eq 2}"><c:set var="sexName" value="女" /></c:when>
										</c:choose>
										<p class="p_b fl_l">
											<label>${fieldSet.fieldName}：</label><span title="${sexName }">${sexName }</span>
										</p>
										<c:set var="mark" value="${mark+1 }" />
									</c:if>
							</c:when>
							<c:when test="${fieldSet.fieldCode eq 'area' }">
								<c:if test="${!empty pname }">
									<p class="p_b fl_l">
										<c:set var="area"></c:set>
										<c:set var="area">${pname }</c:set>
										<c:if test="${!empty cname }">
											<c:set var="area">${pname }-${cname }</c:set>
											<c:if test="${!empty oname }">
												<c:set var="area">${pname }-${cname }-${oname }</c:set>
											</c:if>
										</c:if>
										<label>${fieldSet.fieldName}：</label><span title="${area }">${area }</span>
									</p>
									<c:set var="mark" value="${mark+1 }" />
								</c:if>
							</c:when>
							<c:when test="${fieldSet.fieldCode eq 'mobilephone' || fieldSet.fieldCode eq 'telphone' }">
								<c:if test="${!empty custInfo[fieldSet.fieldCode] }">
									<p class="p_b hyx-sce-tel fl_l">
										<label>${fieldSet.fieldName}：</label>
										<span phone="tel" title="${custInfo[fieldSet.fieldCode] }">${custInfo[fieldSet.fieldCode] }</span>
										<c:if test="${!empty custInfo[fieldSet.fieldCode] }">
											<a href="javascript:;" <c:if test="${shiroUser.serverDay gt 0}">id="call_${custInfo[fieldSet.fieldCode] }" custId="${custInfo.resCustId }" custName="${custInfo.name }" custType="${custInfo.type }" custState="${custInfo.status }" define1="${custInfo.name }" lastOptionId="${custInfo.lastOptionId }" lastOptionName="${lastOptionName }"  define3="${custInfo.company }"</c:if> class="icon_a icon-phone ${shiroUser.serverDay gt 0 ? '' : 'img-gray' }" title="拨打电话"></a>
											<i <c:if test="${shiroUser.serverDay gt 0}">onclick="toSmsSendPage('${custInfo.name }','${custInfo[fieldSet.fieldCode] }')"</c:if> class="icon_b icon-message demoBtn_e ${shiroUser.serverDay gt 0 ? '' : 'img-gray' }" title="发送短信"></i>
										</c:if>
									</p>
									<c:set var="mark" value="${mark+1 }" />
								</c:if>
							</c:when>
							<c:when test="${fieldSet.fieldCode eq 'email' }">
								<c:if test="${!empty custInfo[fieldSet.fieldCode] }">
									<p class="p_b hyx-sce-mail fl_l">
										<label>${fieldSet.fieldName}：</label><span title="${custInfo[fieldSet.fieldCode] }">${custInfo[fieldSet.fieldCode] }</span><c:if test="${!empty custInfo[fieldSet.fieldCode] }"><i <c:if test="${shiroUser.serverDay gt 0}">onclick="emailBysend('${custInfo.name}','${custInfo[fieldSet.fieldCode] }')"</c:if> class="icon-email demoBtn_m ${shiroUser.serverDay gt 0 ? '' : 'img-gray' }" title="发送邮件"></i></c:if>
									</p>
									<c:set var="mark" value="${mark+1 }" />
								</c:if>
							</c:when>
							<c:when test="${fieldSet.fieldCode eq 'birthday' }">
								<c:if test="${!empty custInfo[fieldSet.fieldCode] }">
									<p class="p_b fl_l">
										<label>${fieldSet.fieldName}：</label><span title="<fmt:formatDate value="${custInfo[fieldSet.fieldCode] }" pattern="yyyy年MM月dd日"/>"><fmt:formatDate value="${custInfo[fieldSet.fieldCode] }" pattern="yyyy年MM月dd日"/></span>
									</p>
									<c:set var="mark" value="${mark+1 }" />
								</c:if>
							</c:when>
							<c:when test="${fieldSet.fieldCode eq 'isMajor' }">

							</c:when>
							<c:when test="${fieldSet.fieldCode eq 'name' }">

							</c:when>
							<c:when test="${fieldSet.fieldCode eq 'unithome' }">
								<c:if test="${!empty custInfo[fieldSet.fieldCode] }">
									<p class="p_b fl_l">
										<label>${fieldSet.fieldName}：</label><span title="${custInfo[fieldSet.fieldCode] }"><a href="###" onclick="showPublicUrl('${custInfo[fieldSet.fieldCode] }')">${custInfo[fieldSet.fieldCode] }</a></span>
									</p>
									<c:set var="mark" value="${mark+1 }" />
								</c:if>
							</c:when>
							<c:when test="${fieldSet.fieldCode eq 'defined16' }">
								<c:if test="${!empty custInfo.showdefined16 }">
									<p class="p_b fl_l">
										<label>${fieldSet.fieldName}：</label><span title="${custInfo.showdefined16 }">${custInfo.showdefined16 }</span>
									</p>
									<c:set var="mark" value="${mark+1 }" />
								</c:if>
							</c:when>
							<c:when test="${fieldSet.fieldCode eq 'defined17' }">
								<c:if test="${!empty custInfo.showdefined17 }">
									<p class="p_b fl_l">
										<label>${fieldSet.fieldName}：</label><span title="${custInfo.showdefined17 }">${custInfo.showdefined17 }</span>
									</p>
									<c:set var="mark" value="${mark+1 }" />
								</c:if>
							</c:when>
							<c:when test="${fieldSet.fieldCode eq 'defined18' }">
								<c:if test="${!empty custInfo.showdefined18 }">
									<p class="p_b fl_l">
										<label>${fieldSet.fieldName}：</label><span title="${custInfo.showdefined18 }">${custInfo.showdefined18 }</span>
									</p>
									<c:set var="mark" value="${mark+1 }" />
								</c:if>
							</c:when>
							<c:otherwise>
								<c:if test="${!empty custInfo[fieldSet.fieldCode] }">
									<p class="p_b fl_l">
										<label>${fieldSet.fieldName}：</label><span title="${custInfo[fieldSet.fieldCode] }">${custInfo[fieldSet.fieldCode] }</span>
									</p>
									<c:set var="mark" value="${mark+1 }" />
								</c:if>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					<c:if test="${mark gt 5 }">
						</label>
						<i class="icon_down" name="down"></i>
					</c:if>
				</div>
			</c:otherwise>
		</c:choose>
		<div class="hyx-cc-btn">
			<!--<a href="javascript:;" custId="${custInfo.resCustId }" custType="${custType } " class="com-btnb demoBtn_a fl_l addWeChat"><i class="icon-review"></i><label class="lab-mar">点&nbsp;&nbsp;评</label></a>-->
			<%-- <a href="javascript:;" custId="${custInfo.resCustId }" lifeCode="${custInfo.lifeCode }" cycleTime="${cycleTime}" state="${custType }" id="operateLog" class="com-btnb fl_l addWeChat"><i class="icon-work-log"></i><label class="lab-mar">客户日志</label></a> --%>
			<!--<a href="javascript:;" custId="${custInfo.resCustId }" state="${custType }" id="wechatRecord" class="com-btnb fl_l addWeChat"><i class="icon-wechat-log"></i><label class="lab-mar">微聊记录</label></a>-->
		</div>
		<ul class="tabTagList fl_l" style="margin-top: 230px;">
			<li name="a" rel="${ctx }/cust/card/events?custId=${custInfo.resCustId }&type=1&state=${custType }&userId=${custInfo.ownerAcc}&lifeCode=${custInfo.lifeCode}&custType=${custInfo.type}&custStatus=${custInfo.status}&custName=${custInfo.name }&ownerAcc=${custInfo.ownerAcc}&cycleTime=${cycleTime }&commonAcc=${custInfo.commonAcc}" class="${type eq '1' ? 'current': '' } top">行动记录（<span id="sp1">0</span>）<label class="arrow" ${type eq '1' ? 'style="display:inline-block;"' : '' }><em>◆</em><span>◆</span></label></li>
			<li name="b" rel="${ctx }/cust/card/events?custId=${custInfo.resCustId }&type=2&state=${custType }&userId=${custInfo.ownerAcc}&lifeCode=${custInfo.lifeCode}&cycleTime=${cycleTime }" class="${type eq '2' ? 'current': '' }">服务记录（<span id="sp2">0</span>）<label class="arrow" ${type eq '2' ? 'style="display:inline-block;"' : '' }><em>◆</em><span>◆</span></label></li>
			<li name="c" rel="${ctx }/cust/card/events?custId=${custInfo.resCustId }&type=3&state=${custType }&userId=${custInfo.ownerAcc}&lifeCode=${custInfo.lifeCode}&cycleTime=${cycleTime }" class="${type eq '3' ? 'current': '' }">通话记录（<span id="sp3">0</span>）<label class="arrow" ${type eq '3' ? 'style="display:inline-block;"' : '' }><em>◆</em><span>◆</span></label></li>
			<li name="d" rel="${ctx }/cust/card/events?custId=${custInfo.resCustId }&type=4&state=${custType }&userId=${custInfo.ownerAcc}&lifeCode=${custInfo.lifeCode}&cycleTime=${cycleTime }" class="${type eq '4' ? 'current': '' }">评论记录（<span id="sp4">0</span>）<label class="arrow" ${type eq '4' ? 'style="display:inline-block;"' : '' }><em>◆</em><span>◆</span></label></li>
			<li name="e" rel="${ctx }/cust/card/events?custId=${custInfo.resCustId }&type=5&state=${custType }&userId=${custInfo.ownerAcc}&lifeCode=${custInfo.lifeCode}&cycleTime=${cycleTime }" class="${type eq '5' ? 'current': '' }">合同记录（<span id="sp5">0</span>）<label class="arrow" ${type eq '5' ? 'style="display:inline-block;"' : '' }><em>◆</em><span>◆</span></label></li>
			<li name="f" rel="${ctx }/cust/card/events?custId=${custInfo.resCustId }&type=6&state=${custType }&userId=${custInfo.ownerAcc}&lifeCode=${custInfo.lifeCode}&cycleTime=${cycleTime }" class="${type eq '6' ? 'current': '' }">订单记录（<span id="sp6">0</span>）<label class="arrow" ${type eq '6' ? 'style="display:inline-block;"' : '' }><em>◆</em><span>◆</span></label></li>
      		<li name="g" rel="${ctx }/cust/card/events?custId=${custInfo.resCustId }&type=7&state=${custType }&userId=${custInfo.ownerAcc}&lifeCode=${custInfo.lifeCode}&cycleTime=${cycleTime }" class="${type eq '7' ? 'current': '' }">客户日志（<span id="sp7">0</span>）<label class="arrow" ${type eq '7' ? 'style="display:inline-block;"' : '' }><em>◆</em><span>◆</span></label></li>
      		<li name="h" rel="${ctx }/cust/card/events?custId=${custInfo.resCustId }&type=8&state=${custType }&userId=${custInfo.ownerAcc}&lifeCode=${custInfo.lifeCode}&cycleTime=${cycleTime }" class="${type eq '8' ? 'current': '' }">销售机会（<span id="sp8">0</span>）<label class="arrow" ${type eq '8' ? 'style="display:inline-block;"' : '' }><em>◆</em><span>◆</span></label></li>
		</ul>
	</div>


	<div class="hyx-cc-right hyx-layout-content" style="overflow-y:hidden;">
		<iframe src="${ctx }/cust/card/events?custId=${custInfo.resCustId }&type=${type }&state=${custType }&userId=${custInfo.ownerAcc}&lifeCode=${custInfo.lifeCode}&custType=${custInfo.type}&custStatus=${custInfo.status}&custName=${custInfo.name }&ownerAcc=${custInfo.ownerAcc}&cycleTime=${cycleTime }&commonAcc=${custInfo.commonAcc}" width="100%" height="100%" id="iframepage" name="frameson" frameborder="0" scrolling="no" marginheight="0" marginwidth="0"></iframe>
	</div>



</div>



</body>
</html>
