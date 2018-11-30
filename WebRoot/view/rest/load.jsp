<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge charset=utf-8" />
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
    <%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
    <%
        pageContext.setAttribute("ctx",request.getContextPath());
    %>
    <script type="text/javascript">
        var ctx="${ctx}";
    </script>
    <c:set var="_v">?v=1</c:set>
    <c:set var="ctx" value="${pageContext.request.contextPath}"/>

    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/style.css${_v}">
    <!--主要样式-->
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/skin/default/color.css${_v}">
    <!--换肤样式-->
    <link rel="stylesheet" type="text/css" href="${ctx}/static/js/form/skins/all.css${_v}">
    <link rel="stylesheet" type="text/css" href="${ctx}/static/thirdparty/easyui/themes/gray/easyui.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/static/thirdparty/easyui/themes/icon.css${_v}"><!--树形结构样式-->
    <link rel="stylesheet" type="text/css" href="${ctx}/static/js/time/css/daterangepicker.css${_v}" /><!--选择区域日期插件样式-->
    <link rel="stylesheet" type="text/css" href="${ctx}/static/js/idialog/theme/default/style.css${_v}"><!--可移动弹框插件公共css-->
    <!--公共js-->
    <script type="text/javascript" src="${ctx}/static/thirdparty/jquery/jquery-1.8.2.min.js${_v}"></script>
    <script type="text/javascript" src="${ctx}/static/js/idialog/jquery.idialog.js${_v}" dialog-theme="default"></script><!--可移动弹框插件-->
    <script type="text/javascript" src="${ctx}/static/js/idialog/idialog.common.js${_v}"></script><!--可移动弹框插件公共js-->
    <script type="text/javascript" src="${ctx}/static/js/time/moment.min.js${_v}"></script><!--选择区域日期插件-->
    <script type="text/javascript" src="${ctx}/static/js/time/jquery.daterangepicker.js${_v}"></script><!--选择区域日期插件-->
    <script type="text/javascript" src="${ctx}/static/js/time/date.common.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/form/icheck.js${_v}"></script><!--表单优化-->
    <script type="text/javascript" src="${ctx}/static/js/form/js/custom.min.js${_v}"></script><!--表单优化-->
    <script type="text/javascript" src="${ctx}/static/thirdparty/easyui/jquery.easyui.min.js${_v}"></script>
    <script type="text/javascript" src="${ctx}/static/js/stepbar/js/jquery.easing.1.3.js${_v}"></script><!--步骤-->
    <!--引入公共样式及jquery--->
    <script type="text/javascript" src="${ctx}/static/thirdparty/jquery/jquery.form.js${_v}"></script>
    <!-- JS时间插件 -->
    <script src="${ctx}/static/js/my97datepicker/WdatePicker.js${_v}" type="text/javascript"></script>
    <!-- 排序JS -->
    <script src="${ctx}/static/js/TableSort.js${_v}"></script>
    <!--表单优化-->
    <script type="text/javascript" src="${ctx}/static/js/table.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/form.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/common/common_business.js${_v}"></script>
    <%-- <script type="text/javascript" src="${ctx}/static/js/common/tablerows.js${_v}"></script> --%>
    <script type="text/javascript" src="${ctx}/static/js/common/replaceWord.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/view/res/custcommon.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/view/res/myRests.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/common/tablerows.js"></script>
</head>
<body>
<input type="hidden" id="idReplaceWord" value="${idReplaceWord}" />
<input type="hidden" id="isState" value="${shiroUser.isState }" />
<form action="${ctx }/res/cust/myRests" method="post">
    <div class="hyx-cma hyx-layout">
        <div class="hyx-cm-left hyx-layout-content">
            <div class="com-search hyx-cm-search">
                <div class="com-search-box fl_l">
                    <p class="search clearfix">
                        <input class="input-query fl_l" type="text" name="queryText" value="${resCustInfoDto.queryText }" />
                        <label class="hide-span">输入${empty filedMap['name'] ? '客户名称' : filedMap['name'] }/联系人/联系电话</label>
                    </p>
                    <div class="list-box">
                        <!-- 资源分组 -->
                        <input name="groupId" type="hidden" value="${resCustInfoDto.groupId }" id="s_groupId">
                        <c:set var="selGroupName" value="资源分组" />
                        <c:forEach items="${groupList }" var="g">
                            <c:if test="${g.key eq resCustInfoDto.groupId}">
                                <c:set var="selGroupName" value="${g.value }" />
                            </c:if>
                        </c:forEach>
                        <dl class="select">
                            <dt>${selGroupName }</dt>
                            <dd>
                                <ul>
                                    <li><a href="###" onclick="$('#s_groupId').val('');" title="资源分组">资源分组</a></li>
                                    <c:forEach items="${groupList }" var="group">
                                        <li><a href="###" onclick="$('#s_groupId').val('${group.key }');" groupId="${group.key }">${group.value }</a></li>
                                    </c:forEach>
                                </ul>
                            </dd>
                        </dl>
                        <!-- 添加分配时间 -->
                        <input type="hidden" name="pstartDate" id="s_pstartDate" value="${resCustInfoDto.pstartDate }" />
                        <input type="hidden" name="pendDate" id="s_pendDate" value="${resCustInfoDto.pendDate }"/>
                        <input type="hidden" name="oDateType" id="oDateType" value="${resCustInfoDto.oDateType }" />
                        <c:set var="oDateName" value="添加/分配时间" />
                        <c:choose>
                            <c:when test="${resCustInfoDto.oDateType eq 1 }"><c:set var="oDateName" value="当天" /></c:when>
                            <c:when test="${resCustInfoDto.oDateType eq 2 }"><c:set var="oDateName" value="本周" /></c:when>
                            <c:when test="${resCustInfoDto.oDateType eq 3 }"><c:set var="oDateName" value="本月" /></c:when>
                            <c:when test="${resCustInfoDto.oDateType eq 4 }"><c:set var="oDateName" value="半年" /></c:when>
                            <c:when test="${resCustInfoDto.oDateType eq 5 }">
                                <fmt:parseDate var="psd" value="${resCustInfoDto.pstartDate }" pattern="yyyy-MM-dd" />
                                <fmt:parseDate var="ped" value="${resCustInfoDto.pendDate }" pattern="yyyy-MM-dd" />
                                <c:set var="oDateName">
                                    <fmt:formatDate value="${psd }" pattern="yy.MM.dd"/>/<fmt:formatDate value="${ped }" pattern="yy.MM.dd"/>
                                </c:set>
                            </c:when>
                        </c:choose>
                        <dl class="select prec-cont-time">
                            <dt>${oDateName }</dt>
                            <dd>
                                <ul>
                                    <li><a href="###" onclick="setPdate(0)" title="添加/分配时间">添加/分配时间</a></li>
                                    <li><a href="###" onclick="setPdate(1)" title="当天">当天</a></li>
                                    <li><a href="###" onclick="setPdate(2)" title="本周">本周</a></li>
                                    <li><a href="###" onclick="setPdate(3)" title="本月">本月</a></li>
                                    <li><a href="###" onclick="setPdate(4)" title="半年">半年</a></li>
                                    <li><a href="###" id="pDate" title="自定义" class="diy date-range">自定义</a></li>
                                </ul>
                            </dd>
                        </dl>
                        <!-- 最近联系时间 -->
                        <input type="hidden" name="startActionDate" id="s_startActionDate" value="${resCustInfoDto.startActionDate }" />
                        <input type="hidden" name="endActionDate" id="s_endActionDate" value="${resCustInfoDto.endActionDate }"/>
                        <input type="hidden" name="lDateType" id="lDateType" value="${resCustInfoDto.lDateType }" />
                        <c:set var="lDateName" value="最近联系时间" />
                        <c:choose>
                            <c:when test="${resCustInfoDto.lDateType eq 1 }"><c:set var="lDateName" value="当天" /></c:when>
                            <c:when test="${resCustInfoDto.lDateType eq 2 }"><c:set var="lDateName" value="本周" /></c:when>
                            <c:when test="${resCustInfoDto.lDateType eq 3 }"><c:set var="lDateName" value="本月" /></c:when>
                            <c:when test="${resCustInfoDto.lDateType eq 4 }"><c:set var="lDateName" value="半年" /></c:when>
                            <c:when test="${resCustInfoDto.lDateType eq 5 }">
                                <fmt:parseDate var="sad" value="${resCustInfoDto.startActionDate }" pattern="yyyy-MM-dd" />
                                <fmt:parseDate var="ead" value="${resCustInfoDto.endActionDate }" pattern="yyyy-MM-dd" />
                                <c:set var="lDateName">
                                    <fmt:formatDate value="${sad }" pattern="yy.MM.dd"/>/<fmt:formatDate value="${ead }" pattern="yy.MM.dd"/>
                                </c:set>
                            </c:when>
                        </c:choose>
                        <dl class="select prec-cont-time">
                            <dt>${lDateName }</dt>
                            <dd>
                                <ul>
                                    <li><a href="###" onclick="setActionDate(0)" title="最近联系时间">最近联系时间</a></li>
                                    <li><a href="###" onclick="setActionDate(1)" title="当天">当天</a></li>
                                    <li><a href="###" onclick="setActionDate(2)" title="本周">本周</a></li>
                                    <li><a href="###" onclick="setActionDate(3)" title="本月">本月</a></li>
                                    <li><a href="###" onclick="setActionDate(4)" title="半年">半年</a></li>
                                    <li><a href="###" title="自定义" id="actionDate" class="diy date-range">自定义</a></li>
                                </ul>
                            </dd>
                        </dl>
                        <c:if test="${shiroUser.issys eq 1 }">
                            <input type="hidden" name="ownerAccsStr" value="${resCustInfoDto.ownerAccsStr }" id="ownerAccsStr" />
                            <div class="reso-sub-dep fl_l">
                                <input id="ownerNameStr" readonly="readonly" class="sub-dep-inpu" type="text" value="所有者">
                                <div class="manage-drop">
                                    <div class="sub-dep-ul">
                                        <ul id="tt1">

                                        </ul>
                                    </div>
                                    <div class="sure-cancle clearfix" style="width:120px">
                                        <a class="com-btnc bomp-btna com-btna-sty reso-alloc-sure fl_l" onclick="seleTree()" href="###"><label>确定</label></a>
                                        <a class="com-btnd bomp-btna reso-sub-clear fl_l" id="close02" onclick="unCheckTree()" href="###"><label>清空</label></a>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                    </div>
                </div>
                <input class="query-button fl_r" type="button" value="查&nbsp;询" id="query"/>
            </div>
            <input type="hidden" id="noteType" value="${resCustInfoDto.noteType }" name="noteType" />
            <p class="com-moTag">
                <label class="a">数据分类：</label>
                <a href="###" nt="1" class="e${resCustInfoDto.noteType eq '1' ? ' e-hover' : '' }">全部</a>
                <a href="###" nt="2" class="e${resCustInfoDto.noteType eq '2' ? ' e-hover' : '' }">未联系资源</a>
                <a href="###" nt="3" class="e${resCustInfoDto.noteType eq '3' ? ' e-hover' : '' }">已联系资源</a>
                <a href="###" nt="4" class="e${resCustInfoDto.noteType eq '4' ? ' e-hover' : '' }">已安排计划</a>
                <a href="###" nt="5" class="e${resCustInfoDto.noteType eq '5' ? ' e-hover' : '' }">未安排计划</a>
            </p>
            <div class="com-btnlist hyx-cm-btnlist fl_l">
                <a href="###" id="giveUpBtn" class="com-btna demoBtn_a fl_l"><i class="min-turn-will"></i><label class="lab-mar">放入公海</label></a>
                <a href="###" id="addResBtn" class="com-btna demoBtn_b fl_l"><i class="min-add-resou"></i><label class="lab-mar">添加资源</label></a>
                <a href="###" id="importId" class="com-btna demoBtn_c fl_l"><i class="min-into-resou"></i><label class="lab-mar">导入资源</label></a>
                <a href="###" id="importResultId" class="com-btna demoBtn_d fl_l"><i class="min-into-resou"></i><label class="lab-mar">导入结果</label></a>
                <a href="###" <c:if test="${shiroUser.serverDay gt 0}">onclick="toBatchSmsSend()"</c:if> class="${shiroUser.serverDay gt 0 ? 'com-btna' : 'com-btna-no' } demoBtn_e fl_l"><i class="min-messag ${shiroUser.serverDay gt 0 ? '' : 'img-gray' }"></i><label class="lab-mar">发送短信</label></a>
                <a href="###" id="setImpBtn" class="com-btna demoBtn_f fl_l"><i class="min-set-prior"></i><label class="lab-mar">设置优先</label></a>
                <a href="###" id="setUnImpBtn" class="com-btna demoBtn_g fl_l"><i class="min-cancel-prior"></i><label class="lab-mar">取消优先</label></a>
            </div>
            <div class="com-table com-mta hyx-cm-table fl_l" style="display:block;">
                <table id="t1" width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius">
                    <thead>
                    <tr role="head" class="sty-bgcolor-b">
                        <th><span class="sp sty-borcolor-b skin-minimal"><input type="checkbox" id="checkAll" class="check" /></span></th>
                        <th><span class="sp sty-borcolor-b">操作</span></th>
                        <th><span class="sp sty-borcolor-b">${empty filedMap['name'] ? '客户名称' : filedMap['name'] }</span></th>
                        <th><span class="sp sty-borcolor-b">资源分组</span></th>
                        <th hidesort="true"><span class="sp sty-borcolor-b" sort="true">添加/分配</span></th>
                        <th hidesort="true"><span class="sp sty-borcolor-b" sort="true">最近联系</span></th>
                        <th hidesort="true"><span class="sp sty-borcolor-b" sort="true">计划安排</span></th>
                        <th>所有者</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:choose>
                        <c:when test="${empty rests }">
                            <tr>
                                <td colspan="8">
                                    <center>当前列表无数据！</center>
                                </td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${rests }" var="res" varStatus="vs">
                                <tr id="rightView_${res.resCustId }" class="${(vs.count % 2) ne 0 ? '' : 'sty-bgcolor-b' }">
                                    <td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="checkbox" name="check_" name_tel="${res.name }|${res.telphone}" name_mobile="${res.name }|${res.mobilephone}" mobile="${res.mobilephone }" telPhone="${res.telphone}" id="chk_${res.resCustId }" /></div></td>
                                    <td style="width:100px;">
                                        <div class="overflow_hidden w100 link">
                                            <c:choose>
                                                <c:when test="${shiroUser.serverDay gt 0 }">
                                                    <a href="javascript:;" id="tao_${res.resCustId }" class="icon-amoy-cust" title="开始淘"></a>
                                                    <a href="javascript:;" id="cardInfo_${res.resCustId }" custName="${res.name }" class="icon-cust-card" title="客户卡片"></a>
                                                    <a href="javascript:;" id="editInfo_${res.resCustId }" class="icon-edit" title="修改资源"></a>
                                                    <a href="javascript:;" id="sign_${res.resCustId }" class="icon-sign-cont" title="签约"></a>
                                                </c:when>
                                                <c:otherwise>
                                                    <a href="javascript:;" class="icon-amoy-gray" title="开始淘"></a>
                                                    <a href="javascript:;" id="cardInfo_${res.resCustId }" custName="${res.name }" class="icon-cust-card" title="客户卡片"></a>
                                                    <a href="javascript:;" id="editInfo_${res.resCustId }" class="icon-edit" title="修改资源"></a>
                                                    <a href="javascript:;" class="icon-sign-gray" title="签约"></a>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </td>
                                    <td><div class="overflow_hidden w70" title="${res.name }"><c:if test="${res.isPrecedence eq 1 }"><i class="min-key-areas"></i></c:if>${res.name }</div></td>
                                    <td><div class="overflow_hidden w70" title="${res.groupName }">${res.groupName }</div></td>
                                    <td hidevalue="<fmt:formatDate value="${res.ownerStartDate }" pattern="yyyy-MM-dd HH:mm:ss"/>"><div class="overflow_hidden w120" title="<fmt:formatDate value="${res.ownerStartDate }" pattern="yyyy-MM-dd HH:mm:ss"/>"><fmt:formatDate value="${res.ownerStartDate }" pattern="yyyy-MM-dd"/></div></td>
                                    <td hidevalue="<fmt:formatDate value="${res.actionDate }" pattern="yyyy-MM-dd HH:mm:ss"/>"><div class="overflow_hidden w120" title="<fmt:formatDate value="${res.actionDate }" pattern="yyyy-MM-dd HH:mm:ss"/>"><fmt:formatDate value="${res.actionDate }" pattern="yyyy-MM-dd"/></div></td>
                                    <td hidevalue="<fmt:formatDate value="${res.planDate }" pattern="yyyy-MM-dd HH:mm:ss"/>"><div class="overflow_hidden w120" title="<fmt:formatDate value="${res.planDate }" pattern="yyyy-MM-dd"/>"><fmt:formatDate value="${res.planDate }" pattern="yyyy-MM-dd"/></div></td>
                                    <td><div class="overflow_hidden w50" title="${res.ownerName }">${res.ownerName }</div></td>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                    </tbody>
                </table>
            </div>
            <div class="com-bot">
                <div class="com-page fl_r">${resCustInfoDto.page.pageStr }</div>
            </div>
        </div>
        <div id="custRight" class="hyx-cm-right hyx-layout-side">

        </div>
    </div>
</form>
</body>
</html>
