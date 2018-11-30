<%@ page language="java" pageEncoding="UTF-8"%>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <%@ include file="/common/common.jsp"%>
        <%@ include file="/common/common-function/function.area.select.jsp"%>
        <title>高级查询</title>
        <link href="${ctx}/static/css/dialog.css" rel="stylesheet" type="text/css" />
        <style type="text/css">
            .formItem{
                width: 100px;
			    display: inline-block;
			    font-size: 12px;
		        white-space: nowrap;
			    overflow: hidden;
			    text-overflow: ellipsis;
			    height: 24px;
                line-height: 24px;
                cursor:pointer;
            }
            .formItem input{
                vertical-align: middle;
            }
            .btn-box{
                overflow: hidden;
			    position: absolute;
			    bottom: 10px;
			    left: 50%;
			    margin-left: -104px;
            }
            .formItem-box{
                padding: 10px 20px;
                overflow-y: auto;
			    height: 100%;
			    box-sizing: border-box;
            }
            .form-title{
                margin-bottom: 20px;
                font-size: 14px;
            }
            .submit-form{
                height:100%;
                padding-bottom:40px;
                position: relative;
                box-sizing: border-box;
            }
        </style>
        <script type="text/javascript" src="${ctx}/static/thirdparty/jquery/jquery-1.8.2.min.js${_v}"></script>
        <script type="text/javascript" src="${ctx}/static/js/handlebars-v4.0.10.js${_v}"></script>
        <script type="text/javascript" src="${ctx}/static/js/idialog/jquery.idialog.js${_v}" dialog-theme="default"></script><!--可移动弹框插件-->
        <script type="text/javascript" src="${ctx}/static/js/idialog/idialog.common.js${_v}"></script><!--可移动弹框插件公共js-->
        <script type="text/javascript" src="${ctx}/static/js/view/qupai/export.js${_v}"></script>
        <%@ page import="com.qftx.base.shiro.ShiroUtil" %>
        <% pageContext.setAttribute("shiroUser", ShiroUtil.getShiroUser());%>
        <script type="text/javascript">
           var leadData = ${leadInfoDto}
        </script>
    </head>

    <body>
        <form class="submit-form">
            <div class="formItem-box">
                 <!-- formitem 填充 --> 
            </div>
            <div class="btn-box">
                    <a href="###" id="saveResBtn" class="com-btna bomp-btna sure-btn com-btna-sty fl_l"><label>保存</label></a>
                    <a href="###" id="cacleResBtn" class="com-btna bomp-btna cancel-btn fl_l"><label>取消</label></a>
            </div>
        </form>
        <script type="text/x-handlebars-template" id="template">
            <div class="form-title">当前查询条件下共{{totalCount}}条数据，导出需要选择导出字段</div>
             <input type="hidden" id="totalCount" value="{{totalCount}}"/>
           {{#each fields}}
            <label class="formItem" title="{{searchName}}">
                <input type="checkbox" name="{{developCode}}" value="1" checked/>
                <span class="labelName">{{searchName}}</span>
            </label>
            {{/each}}
        </script>
    </body>
</html>
