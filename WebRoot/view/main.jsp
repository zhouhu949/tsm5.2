<%@ page import="com.qftx.base.shiro.ShiroUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>慧营销V5.0 Web1</title>
    <%@ include file="/common/common.jsp"%>
    <!--配置菜单Color-->
    <link type="text/css" href="${ctx}/static/css/menu.css${_v}" rel="stylesheet"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css${_v}" />
    <link rel="stylesheet" type="text/css" href="${ctx}/static/js/barrage/barrage.bundle.css${_v}" />
    <!--配置Tab菜单-->
    <script src="${ctx}/static/thirdparty/jquery/jquery-1.8.2.min.js${_v}" type="text/javascript"></script>
    <script src="${ctx}/static/thirdparty/easyui/jquery.easyui.min.js${_v}" type="text/javascript"></script>
    <script type="text/javascript" src="${ctx}/static/js/idialog/jquery.idialog.js${_v}" dialog-theme="default"></script><!--可移动弹框插件-->
    <script type="text/javascript" src="${ctx}/static/js/idialog/idialog.common.js${_v}"></script><!--可移动弹框插件公共js-->
    <link href="${ctx}/static/thirdparty/easyui/themes/gray/easyui.css${_v}" rel="stylesheet" type="text/css" />
    <% pageContext.setAttribute("shrioUser", ShiroUtil.getShiroUser());%>
    <% pageContext.setAttribute("shiroIdsList", ShiroUtil.getSession("shiroIdsList"));%>

    <script type="text/javascript">
        var shrioUserOrgId='${shrioUser.orgId}';
        var shrioUserOrgName='${shrioUser.orgName}';
        var shrioUserAccount='${shrioUser.account}';
        var shrioUserName='${shrioUser.name}';
        var shrioUserBarrage='${isBarOpen}';
        var shiroIdsList = ${shiroIdsList};
        var product_qq = '${qq}'?'${qq}':'4008262277';
        var product_serviceTel = '${serviceTel}'?'${serviceTel}':'4008262277';
        // var product_serviceTel = '123456789';
        var product_currencyUnit = '${currencyUnit}'?'${currencyUnit}':'蜂豆';
        // var product_currencyUnit = '大白菜';
        localStorage.setItem("product_qq",product_qq);
        localStorage.setItem("product_serviceTel",product_serviceTel);
        localStorage.setItem("product_currencyUnit",product_currencyUnit);
        var shrioAuthObj=function(propName){
            if(shiroIdsList == null) {
                return false;
            }
            return shiroIdsList.indexOf(propName)==-1?false:true;
            //return shiroIdsList.hasOwnProperty(propName);
        }
    </script>

    <style >
        .tabs-wrap{border-top:1px solid #ccc;border-bottom:1px solid #ccc;}
        .tabs{border:none;}
        .tabs li{margin:0 0 -1px 0;height:27px;}
        .tabs li a.tabs-inner{max-width:125px;min-width:124px;background:#f5f6f7;filter:none;border:none;border-right:1px solid #ccc;border-radius:0;}
        .tabs li.tabs-selected a.tabs-inner{background:#fff;filter:none;}
        .tabs-closable{display:inline-block;max-width:110px;overflow-x:hidden;text-overflow:ellipsis;white-space: nowrap;}
        .tabs-header{border:none;background:none;height: 30px;position: fixed; top: 42px;}
        .tabs-panels{border:none;height: 100%;}
        .tabs-panels>.panel,.tabs-panels>.panel>.panel-body{height:100%;overflow:hidden;}
        .tabs-header,.tabs-panels,.panel-body{ width:100% !important;}
        .easyi-utabs{width:100%;height:100%;}

        #nav{float:left;height:38px;line-height:38px;padding-left:13px;}
        #nav, #nav ul{margin:0;padding:0;position:relative;line-height:38px;z-index:5;}
        #nav a{height:40px;display:block;padding:0 16px;text-decoration:none;text-align:center;line-height:40px; outline:none;z-index:35;position:relative;float:left;}
        #nav ul a{line-height:38px; }
        #nav li{float:left;position:relative;z-index:20;line-height:38px; border:1px solid #fff;border-top:none; }
        #nav li li{border-left:none;margin-top:0;}
        #nav ul{position:absolute;display:none;width:175px;top:41px;padding-bottom:4px;left:-1px;background:#f5f5f5;-moz-border-bottom-left-radius:4px;-moz-border-bottom-right-radius:4px;-moz-border-top-left-radius:0px;-moz-border-top-right-radius:0px; -webkit-border-bottom-left-radius:4px;-webkit-border-bottom-right-radius:4px;-webkit-border-top-left-radius:0px;-webkit-border-top-right-radius:0px; border-top-left-radius:0px;border-top-right-radius:0px;border-bottom-left-radius:4px;border-bottom-right-radius:4px;-moz-box-shadow:0 4px 8px rgba(0,0,0,0.15); -webkit-box-shadow:0 4px 8px rgba(0,0,0,0.15); box-shadow:0 4px 8px rgba(0,0,0,0.15);}
        #nav li ul a{width:118px;height:30px;line-height:30px;float:left;text-align:left;padding:0 21px;box-sizing: content-box;}/* 总宽度160 左右内边距21px  宽度118px  别再改了。 */
        #nav ul ul{top:auto;border-top:none;}
        #nav li ul ul{left:172px;top:0px;}

        #nav a{color:#888;}
        #nav ul{border:1px solid #DFDFDF;}
        #nav li ul a{border-bottom:1px solid #f0f0f0;}
        #nav ul li{background-color:#f5f5f5;border:1px solid #f5f5f5;border-bottom:none;}

        #nav .current_page_item a{color:#555;}
        #nav .current_page_parent .current_page_item a, #nav .current_page_item ul a{border-right:1px solid #f5f5f5;border-left:none;background-image:none;color:#444;}

        #nav a:hover{color:#fff;background-color:#469bff;}
        #nav li:hover{background-color:#469bff;}
        #nav li:hover ul ul, #nav li:hover ul ul ul, #nav li:hover ul ul ul ul{display:none;}
        #nav li:hover ul, #nav li li:hover ul, #nav li li li:hover ul, #nav li li li li:hover ul{display:block;}
        .navwrap{
            height:40px;
            position:fixed ;
            top:0;
            left:0;
            right:0;
            z-index: 1000; 
        }
        .fixedRight{
            position:absolute; right:10px; top:10px;z-index: 9999
        }
        .help-video{
            width:40px;
            height: 20px;
            display: inline-block;
        }
        
        /*-----------消息警告提醒弹框-----*/
		.bomp-cen{float:left;width:100%;padding-bottom:15px;font-size:14px;}
		.bomb-btn{overflow:hidden;width:100%;float:left;text-align:center;margin-top:20px;}
		.bomb-btn-cen{display:inline-block;overflow:hidden;margin:0 auto;}
		.bomb-btn-cen a{margin:0 10px;}
		.bomp-tip-a{width:90%;overflow:hidden;margin:20px auto 0;}
		.bomp-tip-a .tip-a{display:inline-block;width:49px;height:49px;background:url(${ctx}/static/images/question.png) no-repeat;}
		.bomp-tip-a .tip-warm{display:inline-block;width:49px;height:49px;background:url(${ctx}/static/images/warning.png) no-repeat;}
		.tip-wrong{display:inline-block;width:49px;height:49px;background:url(${ctx}/static/images/tip-wrong.png) no-repeat;}
		.tip-cup{display:inline-block;width:49px;height:49px;background:url(${ctx}/static/images/tip-cup.png) no-repeat;}
		.bomp-tip-a .sp-a{display:inline-block;width:72%;margin-left:15px;margin-top:15px;}
		/*-----------消息警告提醒弹框结束-----*/

    </style>
</head>
<body style="overflow:hidden;">
<input type="hidden" id="intro"  value="${intro }"><!-- 用于新手引导  -->
<input type="hidden" id="showAlarm"  value="${showAlarm }"><!-- 用于新手引导  -->
<input type="hidden" id="tsmServiceUrl" value=${tsmServiceUrl }><!--  服务web路径 -->
<input type="hidden" id="tsmAiRobotUrl" value=${tsmAiRobotUrl }><!-- 机器人web路径 -->
<input type="hidden" id="tsmClueUrl" value=${tsmClueUrl }><!-- 蟻盟web路径 -->
<input type="hidden" id="tsmPayUrl" value="${tsmPayUrl }"><!--  钱包web路径 -->
<div id="sysMenu" class="navwrap"></div>
<div class="fixedRight">
    <div class="help-video" style="display: none;"><a href="javascript:void(0)"  class="help-video-url" data-url="${ctx}/sysDateHelp/jump?url="  target="_blank">帮助</a></div>
    <c:if test="${empty j_KernelVer}">
        <a href="/logout">退出</a>
    </c:if>
</div>

<div id="centerTabs" class="easyi-utabs" style="padding-top:72px;box-sizing: border-box;">
</div>
<!--dm start-->
<!--end dm-->
<!-- 在线客服 -->
<!-- <div id="QQOnLine" style="display: none;">
	<script charset="utf-8" type="text/javascript" src="http://wpa.b.qq.com/cgi/wpa.php?key=XzkzODAwNjMxNF8xNjk2MTFfNDAwODI2MjI3N18"></script>
</div> -->
<div id="container"></div>

<script type="text/javascript" src="${ctx}/static/js/base64.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/common/topmenu.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/common/common_business.js${_v}"></script>


<script type="text/javascript">
    $(function () {
        //iDialogMsg("提示","当前chrome内核"+window.location.host);
        //告诉客户端有小窗口弹幕版本
        
        external.tanmuDisplay && external.tanmuDisplay("-1");
		external.OnLoadComplete && external.OnLoadComplete('showVersion')
        initTopMenu();
        if('${shrioUser.account}' != '${shrioUser.adminAccount}'){
            // pubDivDialog("serviceTime","<shiro:principal property="name"/>你好，服务到期时间<fmt:formatDate value='${shrioUser.serveTime}' pattern='yyyy/MM/dd'/>&nbsp;",null,null);
            var day =${shrioUser.serverDayEnd};
            var istrue = false;
            if (day > 0) {
                istrue = true;
            }
            if (istrue) {
                //  warmDivDialog("serviceTime", "<shiro:principal property="name"/>你好，服务还有${shrioUser.serverDay}天到期&nbsp;", null, null);
                serviceDialog("serviceTime", "${shrioUser.serverDayEnd}",'${not empty shrioUser.adminName? shrioUser.adminName : shrioUser.adminAccount}','${shrioUser.mobile}', null, null);
            }
        }
        /**************************************** 新手引导  ********************************** */

        //5.0暂时去掉新手引导
        /*  if($("#intro").val() != null && $("#intro").val() != ""){
         if($("#intro").val()==1){
         addTab("${ctx}/auth/role/addRolePage","角色编辑");
         }else if($("#intro").val()==2){//开始配置系统属性设置，行动标签
         addTab("${ctx}/opt/set/propertyset","系统属性设置");
         }else if($("#intro").val()==3){//开始配置系统属性设置，录音范例分类
         addTab("${ctx}/opt/set/propertyset","系统属性设置");
         }else if($("#intro").val()==4){//开始配置系统属性设置，客户放弃原因
         addTab("${ctx}/opt/set/propertyset","系统属性设置");
         }else if($("#intro").val()==5){//开始配置系统属性设置，目标客户分类
         addTab("${ctx}/opt/set/propertyset","系统属性设置");
         }else if($("#intro").val()==6){//开始配置系统属性设置，销售进程设计
         addTab("${ctx}/opt/set/propertyset","系统属性设置");
         }else if($("#intro").val()==7){//开始配置系统属性设置，销售产品维护
         addTab("${ctx}/opt/set/propertyset","系统属性设置");
         }else if($("#intro").val()==8){//开始配置系统销售管理设置
         addTab("${ctx}/sales/manage/salesmanage","销售管理设置");
         }else if($("#intro").val()==9){// 开始配置系统 消息设置
         addTab("${ctx}/msg/manage/msgmanage","消息设置");
         }
         }else{
         var isWarn = '${shrioUser.isWarn}';  // 4.0修改为 (2：表示已经显示过新手指导，其余表示 需要做新手引导)
         var isFalse = true;
         if(isWarn == '2'){ // 已做过新手引导
         isFalse = false;
         }
         if(isFalse){
         var issys = ${shrioUser.issys};  // 角色类别：0--销售，1--管理者
         if(issys == 1 || issys == 2){
         // 管理者 新手引导
         setIsWarn_(1)
         }else{
         // 普通销售 新手引导
         setIsWarn_(0)
         }
         }
         } */
        /**************************************** 新手引导  ********************************** */
    });
    function test() {
        // alert("出来就好了");
        pubDivShowIframe("22", "/test", "", 800, 450);
    }

    function mainCostWin(){
        pubDivShowIframe('reward',$("#tsmPayUrl").val()+'/pay/reward','打赏',600,500);
    }
    
    function addWallet(){
        window.top.addTab(ctx+'/user/toPersonal_center?pay=1', '个人中心');
    }
    
    function intoPersonal(){
        window.top.addTab(ctx+'/user/toPersonal_center', '个人中心');
    }

    window.mainCardScreen=function(content){
         var ele=document.createElement("div");
         ele.innerHTML=content;
         document.body.appendChild(ele);
    }
    window.renderView=function(){
        external && external.tanmuDisplay("1")
    }
    window.destoryView=function(){
       external &&  external.tanmuDisplay("0")
    }
    window.setBarOpen = function(isOpen){
        $.ajax({
            type: "POST",
            dataType: 'json',
            data:{'open':isOpen},
            url: ctx + '/main/sale/usertBarOpen',
            success: function (data) {
                if(data == 0){
                    iDialogMsg("提示", "设置成功");
                }
            },
            error:function(err){
                alert(err.statusText);
            }
        })
        if(isOpen==1){
            renderView();
        }else if(isOpen ==0){
            destoryView();
        }

    }

    //新手引导 修改状态
    function setIsWarn_(isSys){
        $.ajax({
            url:ctx+'/auth/role/setIsWarn_',
            type:'post',
            dataType:'json',
            error:function(){alert('网络异常，请稍后再试！')},
            success:function(data){
                /* if(data == 0){ //0：表示需要做新手引导
                 if(isSys == 1){ // 管理者 新手引导
                 pubDivShowIframe('alert_novice_help_a',ctx+'/view/help/novice_help_com/alert_novice_help_b.jsp','系统设置引导说明',650,350);
                 }else{   //普通销售 新手引导
                 pubDivShowIframe('alert_novice_help_sale',ctx+'/view/help/novice_help_person/alert_novice_help_sale.jsp','新手引导说明',650,350);
                 }
                 } */
            }
        });
    }
</script>

</body>
</html>

