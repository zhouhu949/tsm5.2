<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
<head>
	<%@ include file="/common/include.jsp"%>
	<title>业务对象管理-字段管理</title>
	<style type="text/css">
	    .box{
	       padding:10px 20px;
	       margin: 10px auto;
	       width:1100px;
	    }
	    .formItem{
	       position:relative;
	       padding-left:120px;
	       margin:15px 0;
	    }
	    .formItem>label{
	       position:absolute;
	       left:0;
	       top:0;
	       height:30px;
	       line-height:30px;
	       width:110px;
	       text-align:right;
	    }
	    .formItem input,.formItem select{
	       height:30px;
	       line-height:30px;
	    }
	    .formItem textarea{
	       resize:none;
	       height:150px;
	       line-height:30px;
	    }
	    .formItem input,.formItem textarea{
	       width:800px;
	    }
	    .borderbottom{
	       padding-bottom:30px;
	       border-bottom:1px solid #bcbcbc;
	    }
	    button.add-condition{
           padding: 4px 5px;
            background-color: #fff;
            outline: none;
            border: 1px solid #a9a9a9;
            cursor: pointer;
        }
        button.form-submit-btn{
           margin: 20px auto 0;
            border-radius: 5px;
            position: relative;
            display: block;
            width: 84px;
            height: 25px;
            background-color: #0f5aac;
            line-height: 25px;
            outline: none;
            text-align: center;
            border: 1px solid #0f5aac;
            color: #fff;
            cursor: pointer;
        }
	    /*修补样式*/
	    .reso-sub-dep{
           width:auto;
           height:auto;
	    }
	    .reso-sub-dep .manage-owner-sour{
            margin-left: 0;
		    overflow-y: auto;
		    left: 120px;
		    top:30px;
		    height:190px;
		    width:300px;
	    }
	    .reso-sub-dep .manage-owner-sour #founder-tree{
	       height:140px;
	       overflow-y:auto;
	    }
	    .btn-remove-formItem {
	    	padding: 0 4px;
	    	visibility: hidden;
	    }
	    .add_box .formItem.reso-sub-dep:last-child>.btn-remove-formItem{
	    	visibility: visible;
	    }
	</style>
	<script>
	   var obj = {
	       1:{accValue:"${entity.auditorAcc1}",nameValue:"${entity.auditorName1}",num:1},
	       2:{accValue:"${entity.auditorAcc2}",nameValue:"${entity.auditorName2}",num:2},
	       3:{accValue:"${entity.auditorAcc3}",nameValue:"${entity.auditorName3}",num:3}
       }
	</script>
	<script type="text/javascript" src="${ctx }/static/js/view/qupai/queryWorkFlowSet.js"></script>
 </head>
  
  <body>
  <div class="hyx-sfs com-table-a hyx-mpe-table hyx-cm-table fl_l" style="overflow-x:hidden;height:100%;box-sizing:border-box;margin-bottom:0;">
    <div class="year-sale-play">
        <ul class="ann-sale-plan clearfix">
            <a href="${ctx }/custField/quPai/custFieldQupaiPage.do"><li class="li_first">字段管理</li></a>
            <a href="${ctx }/workflowSet/toWorkflowSetPage.do"><li class="li_last select">工作流程设置</li></a>
            <a href="${ctx }/quPaiSearchManager/searchSetPage.do"><li class="li_last">查询项管理</li></a>
        </ul>
    </div>
    <div class="box">
         <form class="work_flow_set_form">
            <input type="hidden" id="workflowId" name="workflowId" value="${entity.workflowId}"/>
            <input type="hidden" id="auditNum" name="auditNum" value="${entity.auditNum}"/>
            <div class="formItem">
                <label>审核流程名称：</label>
                <input type="text" name="workflowName" class="" value="${entity.workflowName}" readonly/>
            </div>
            <div class="formItem borderbottom">
                <label>审核描述：</label>
                <textarea class="" name="workflowDescribe" maxlength="100">${entity.workflowDescribe}</textarea>
            </div>
            <div class="formItem">
                <label>审核条件：</label>
                <span>当放款提交后</span>
                <select name="type" class="condition-select">
                     <option value="0" <c:if test="${entity.type eq '0'}">selected</c:if>>总不</option>
                     <option value="1" <c:if test="${entity.type eq '1'}">selected</c:if>>总是</option>
                </select>
                <span>触发审核动作</span>
                <button type="button" class="add-condition">添加审核</button>
            </div>
            <div class="add_box"></div>
            <button type="submit" class="form-submit-btn">提交</button>
         </form>
        </div>
    </div>
    <script type="text/x-handlebars-template" id="template">
        <div class="formItem reso-sub-dep">
                <label>设置审核：</label>
                <input type="hidden" class="tree-accs" name="auditorAcc{{num}}" value="{{accValue}}"/>
                <input class="setting-flow tree-show-value" value="{{nameValue}}"/>
                <span>{{num}}级审核</span>
				{{#compare num '>' 1}}
				<button type="button" class="btn-remove-formItem">-</button>
				{{/compare}}
                <div class="manage-owner-sour" >
                         <ul id="founder-tree" class="member-tree">
                        </ul>
                     <div class="sure-cancle clearfix" style="width:120px">
                         <a class="com-btnc bomp-btna com-btna-sty reso-owner-sure fl_l" id="" href="javascript:;"><label>确定</label></a>
                         <a class="com-btnd bomp-btna reso-sub-clear fl_l" id=""  href="javascript:;"><label>清空</label></a>
                     </div>
                 </div>
            </div>
    </script>
  </body>
</html>
