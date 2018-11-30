<%@ page language="java" pageEncoding="UTF-8"%>

<!--选择区域日期插件-->
<%-- 
<script type="text/javascript" src="${ctx}/static/js/time/moment.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/time/jquery.daterangepicker.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/time/date.common.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/my97datepicker/WdatePicker.js${_v}"></script>
 --%>
<%@ include file="/common/common-function/function.date.jsp"%>

<!--可移动弹框插件-->
<%-- 
<script type="text/javascript" src="${ctx}/static/js/idialog/jquery.idialog.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/idialog/idialog.common.js${_v}"></script>
 --%>
<%@ include file="/common/common-function/function.idialog.jsp"%> 
 
<!-- 等待页面加载遮盖层 -->
<script type="text/javascript" src="${ctx}/static/js/idialog/loading.js${_v}"></script>

<%-- 
<script type="text/javascript" src="${ctx}/static/js/form.js${_v}"></script><!-- 主要是搜索区域以及一些表单填写的事件 -->
<script type="text/javascript" src="${ctx }/static/js/common/search_input_tree.js${_v}"></script><!-- 树顶部添加搜索框 -->
 --%>
<%@ include file="/common/common-function/function.form.jsp"%>

<!-- 表格排序 -->
<%-- 
<script type="text/javascript" src="${ctx}/static/js/table.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/tableSort.js${_v}"></script>
 --%>
<%@ include file="/common/common-function/function.table.jsp"%>

<!-- handlebar -->
<%-- 
<script type="text/javascript" src="${ctx}/static/js/handlebars-v4.0.10.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/handlebar.helper.js${_v}"></script>
 --%>
<%@ include file="/common/common-function/function.handlebar.jsp"%> 
