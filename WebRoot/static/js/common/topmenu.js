var centerTabs;
var isFresh=0;
var freshCard = null;
$(function () {
    if (top.location != self.location) {
        top.location = self.location;
    }
    centerTabs = $('#centerTabs').tabs({border: false,plain:false,width:'auto'});
    //  centerTabs.tabs({width: centerTabs.parent().width(), height: getClientHeight() - 100});//减掉菜单高度
    addTab(ctx+'/main/sale/home', '首页');

    $("#nav li").hover(function () {
        $(this).find('ul:first').css({visibility: "visible", display: "block"});
    }, function () {
        $(this).find('ul:first').css({visibility: "hidden"});
    });
    $("#centerTabs").tabs({
        //height:getClientHeight()-66,
        //   justified:true,
        tabWidth:110,
        onSelect:function(title,index){
            if(title=='淘客户' && isFresh == 1){
                $('iframe[src*=taoMyRes]').attr('src',ctx+"/res/tao/taoMyRes?resId=");
                isFresh=0;
            }
            var helpUrl= $(".help-video-url").data("url")+encodeURIComponent($(this).tabs('getSelected').find("iframe").attr("src"));
            $(".help-video-url").attr("href",helpUrl);
			/*
			 //freshCard 一直null 有毛用
			 if(freshCard != null){
			 var currCard = centerTabs.tabs("getTab",index);
			 var options = currCard.panel("options");

			 $(".help-video-url").attr("href",options.src);
			 if(options._title == freshCard){
			 var content = '<iframe src="' + options.src + '" frameborder="0" style="border:0;width:100%;height:100%;"></iframe>';
			 centerTabs.tabs('update', {
			 tab: currCard,
			 options: {
			 content: content
			 //或者 href : '';
			 }
			 });
			 freshCard = null;
			 }
			 }
			 */
        }
    });
});
function initTopMenu(){
    $.ajax({
        type: "GET",
        dataType: 'JSON',
        url: ctx + '/menu/list',
        success: function (data) {
            var obj = eval(data);
            var tsmAiRobotUrl = $("#tsmAiRobotUrl").val();
            var str = "<ul id=\"nav\">";
            str+= " <li class=\"current_page_item \"><a href=\"javascript:addTab('/main/sale/home','首页')\">首页</a></li>" ;
            for (var i = 0; i < obj.length; i++) {
                var url= "http://"+window.location.host+ ctx+obj[i].resourceString;
                if(url==""||url==null||url.length==0){
                    url="/query/list";
                }
                var childList = obj[i].childList;
                var clsname = childList.length > 0 ? "page_item" : "current_page_item";
                str += "<li class=\"" + clsname + "\">";
                if (childList.length== 0) {
                    str += "<a href=\"javascript:addTab('"+url+"','"+ obj[i].resourceName+"')\">" + obj[i].resourceName + "</a>";
                }else{
                    str += "<a href=\"javascript:void(0);\">" + obj[i].resourceName + "</a>";
                }
                if (childList.length > 0) {
                    str += "<ul class='children'>";
                    for (var j = 0; j < childList.length; j++) {
                        if(obj[i].resourceName=="服务管理" || childList[j].resourceName=="服务记录"){
                            url=  $("#tsmServiceUrl").val()+ctx+childList[j].resourceString;
                        }else if(obj[i].resourceName=="小蜂机器人"){
                        	 if(tsmAiRobotUrl==undefined || tsmAiRobotUrl=="" || tsmAiRobotUrl==null){
                        		 url=  "http://xfzz.qftx.net"+ctx+childList[j].resourceString;
                        	 }else{
                        		 url=  tsmAiRobotUrl+ctx+childList[j].resourceString;
                        	 }       	
                        }else if(obj[i].resourceName=="线索管理"||obj[i].resourceName=="蚁探魔方"){
                            url=  $("#tsmClueUrl").val()+ctx+childList[j].resourceString;
                        }else{
                            url= "http://"+window.location.host+ctx+childList[j].resourceString;
                        }
                        if(url==""||url==null||url.length==0){
                            url="/query/list";
                        }
                        if(childList[j].resourceName == "跟进警报"){     // 判断是否显示跟进警报菜单
                            if($("#showAlarm").val() == "1"){
                                str += "<li class=\"page_item add\"><a href=\"javascript:addTab('"+url+"','"+childList[j].resourceName+"')\" >"+childList[j].resourceName+"</a></li>";
                            }
                        }else{
                            str += "<li class=\"page_item add\"><a href=\"javascript:addTab('"+url+"','"+childList[j].resourceName+"')\" >"+childList[j].resourceName+"</a></li>";
                        }
                    }
                    str += "</ul>";
                }
                str += "</li>";
            }
            str += "  </ul>";
            // alert(str);
            $("#sysMenu").html(str);
        }
    }) ;
    showNoReadMsg();
}
function initLeftTree() {
    var tree = $("ul.easyui-tree").tree({
        url: ctx + '/menu/left',
        animate: false,
        lines: true,
        onClick: function (node) {
            var href = "${ctx}/query/list";
            if (node.attributes && node.attributes.url && node.attributes.url != '' && node.attributes.url != '#') {

				/*   if (/^\//.test(node.attributes.url)) {/!*以"/"符号开头的,说明是本项目地址*!/
				 href = node.attributes.url.substr(1);
				 //$.messager.progress({
				 //	text : '请求数据中....',
				 //	interval : 100
				 //});
				 } else {
				 href = node.attributes.url;
				 }*/
                addTabFun({
                    src: href,
                    title: node.text
                });
            } else {
                addTabFun({
                    src: href,
                    title: node.text
                });
            }
        },

        onLoadSuccess: function (node, data) {
            var t = $(this);
            if (data) {
                $(data).each(function (index, d) {
                    if (this.state == 'closed') {
                        t.tree('expandAll');
                    }
                });
            }
        }

    });
}
function closeTab(title) {
    centerTabs.tabs('close', title);
}
function addTabName(urlString, titleString,name) {
    addTabFun({src: urlString, title: titleString,name:name});
}

function addTab(urlString, titleString) {
    addTabFun({src: urlString, title: titleString});
}
function addMenu(urlString, titleString) {
    addTab(urlString, titleString);
}
function addTabFun(opts) {
    if(opts.title == '回访操作' || opts.title == '未回访明细'){
        opts.src = $("#tsmServiceUrl").val()+opts.src;
    }
    var content ='<iframe src="' + opts.src + '" frameborder="0" style="border:0;width:100%;height:100%;"></iframe>';
    if( opts.title == '淘客户'){
        content ='<iframe  id="workDeskId" src="' + opts.src + '" frameborder="0" style="border:0;width:100%;height:100%;"></iframe>';
    }
    if( opts.title == '跟进'){
        content ='<iframe  id="followDeskId" src="' + opts.src + '" frameborder="0" style="border:0;width:100%;height:100%;"></iframe>';
    }
    if(opts.name){
        content ='<iframe src="' + opts.src + '" frameborder="0" style="border:0;width:100%;height:100%;" name="'+opts.name+'"></iframe>';
    }
    var options = $.extend({
        id: 0,
        title: opts.title,
        content: content,
        closable: opts.title != '首页',
        iconCls: ''
    }, opts);

    if (centerTabs.tabs('exists', options.title)) {
        centerTabs.tabs('select', options.title);
        //当前tab
        var current_tab = centerTabs.tabs('getSelected');
        var timestamp = new Date().getTime();
        var url = opts.src;
        if(url.indexOf('res/tao/taoMyRes?')>-1){
            url = opts.src + "&v="+ timestamp;
        }else if(url.indexOf('res/tao/taoMyRes')>-1){
            url = opts.src + "?v="+ timestamp;
        }
        if( opts.title == '淘客户'){
            content ='<iframe  id="workDeskId" src="' + url + '" frameborder="0" style="border:0;width:100%;height:100%;"></iframe>';
        }
        centerTabs.tabs('update', {
            tab: current_tab,
            options: {
                content: content
                //或者 href : '';
            }
        });
    } else {
        centerTabs.tabs('add', options);
    }
}


//给一个资源ID，打电话
function saveCallRecordInfo(id){
    $.ajax({
        type: "POST",
        dataType: 'text',
        data:'id='+id,
        url: ctx + '/callrecord/save',
        success: function (data) {
            iDialogMsg("提示", "模拟拔打电话成功");
        },error:function(e){
            alert(JSON.stringify(e));
        }
    })
}
//给一个资源ID，电话号码，销售进程，销售进程名称 跟进ID， 打电话
function saveCallRecordInfoNum(id,tel,saleProcessId,saleProcessName,followId){
    $.ajax({
        type: "POST",
        dataType: 'text',
        data:'id='+id+"&tel="+tel+"&saleProcessId="+saleProcessId+"&saleProcessName="+saleProcessName+"&followId="+followId,
        url: ctx + '/callrecord/save',
        success: function (data) {
            iDialogMsg("提示", "模拟拔打电话成功");

        },error:function(e){
            alert(JSON.stringify(e));
        }
    })
}

/**
 * 资源，客户拨打电话共同方法
 * @param  phone	手机或固话号码
 * @param  custId	客户id
 * @param  custName 客户名称
 * @param  custType 客户类型(1资源，2意向)
 * @param  custState 客户状态
 * @param  saleProcessId 销售进程id
 * @param  saleProcessName 销售进程名称
 * @param  followId 跟进id
 */
function custCallPhone(phone,arrays,custId,isConcat,lianXiId){
    if(isConcat==undefined || isConcat=="" || isConcat==null){
        isConcat = 0;
    }
    if(lianXiId==undefined || lianXiId=="" || lianXiId==null){
        lianXiId = "";
    }
    if(phone!=null && $.trim(phone) !=''){
        var params = phone.split('-');
        var param = params[0] + "_" + custId;
        try{
            var arrayVal = "";
            var data = "{\"orgId\":\""+shrioUserOrgId+"\""+
                ",\"inputName\":\""+shrioUserName+"\""+
                ",\"inputAcc\":\""+shrioUserAccount+"\""+
                ",\"define18\":\"6001\""+
                ",\"orgName\":\""+shrioUserOrgName+"\"";
            for(var i = 0;i<arrays.length;i++){
                if(typeof(arrays[i]) != "undefined"){
                    arrayVal = arrays[i].replace(/\s/g, "");
                    data+= ","+arrayVal+"";
                }
            }
            data +="}";
            // base64 编码
            var b = new Base64();
            var base64Data = b.encode(data);
            var paramXml = "<xml><Oparation isConcat=\""+isConcat+"\" lianXiId=\""+lianXiId+"\"  custId=\""+custId+"\"  Phone=\""+params[0]+"\" Data=\""+base64Data+"\" ";
            paramXml+="/></xml>";
//			external.OnCallXml(paramXml);
            queryCall(phone,paramXml);
        }catch(e){
            external.OnCall(param);
        }
    }
}

//校验公海客户和共有客户通话次数
function  queryCall(phone,paramXml){
    $.ajax({
        type: "POST",
        dataType: 'json',
        data:{'telPhone':phone},
        url: ctx + '/call/queryCall',
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            alert("queryCall:"+textStatus+"/status:"+XMLHttpRequest.status);
        },
        success: function (data) {
            if(data.code == "0"){ //
                external.OnCallXml(paramXml);
            }else if(data.code == "11"){
                queDivDialog("common_cust_idialog", "该客户今日已被联系，是否继续呼出？", function() {
                    external.OnCallXml(paramXml);
                });

            }else if(data.code == "3"){
                iDialogMsg("提示", data.desc);
//	        		alert(data.desc);
            }
        }
    })
}



/**
 * 客户端 发送短信方法
 */
function toSmsSend() {
    pubDivShowIframe('sms_send',ctx+'/call/sms/clientToIdialogSmsSend?v='+new Date().getTime(),'发送短信',915,530);
}

// 展示消息中心
function showMsgCenter(){
    addTab(ctx+"/message/list","消息中心");
}

//展示消息中心_new
function showMsgCenterEx(strType){
    if(strType != null && strType != ""){
        addTab(ctx+"/message/list?type="+strType,"消息中心");
    }else{
        addTab(ctx+"/message/list","消息中心");
    }
}


//展示短信接收记录
function showSmsReceive(){
    addTab(ctx+"/sms/record/smsReceiveList","短信接收记录");
}

/**
 * 客户端 版本说明
 */
function showSysVersion(){
    pubDivShowIframe('SysVersion',ctx+'/message/sysVersion?v='+new Date().getTime(),'版本说明',880,450);
}
/**
 * 客户端 版本特性
 */
function showVersion(param){
	if(param == 1){
		pubDivShowIframe('version_tips',ctx+'/message/sysVersion_chara?v='+new Date().getTime(),'版本特性',800,494);
	}
}

//登录弹出未阅读公告消息
function  showNoReadMsg(){
    $.ajax({
        type: "POST",
        dataType: 'json',
        data:{},
        url: ctx + '/message/IfNoReadnotice',
        error : function(XMLHttpRequest, textStatus, errorThrown) {
//            alert("IfNoReadnotice:"+textStatus+"/status:"+XMLHttpRequest.status);
        },
        success: function (data) {
            if(data.isok == "0"){ //当前用户有 未阅读公告
                window.parent.pubDivShowIframe('alert_message_center_e_a',ctx+'/message/getNOReadNoticeInfo.do?indexSize='+0,'公告栏 <span class="havenKnew">共'+data.sum+'封公告，当前处于第1封</span>',900,550);
            }
        }
    })
}

//展示合同明细
function showHTDetail(id){
    addTab(ctx+'/contract/toView?contractId='+id,'查看合同');
}
//展示合同明细
function showOrderDetail(id){
    addTab(ctx+'/contract/toView?contractId='+id,'查看合同');
}

// 设置弹幕开关
function setBarOpen(isOpen){
    $.ajax({
        type: "POST",
        dataType: 'json',
        data:{'open':isOpen},
        url: ctx + '/user/setBarOpen',
        success: function (data) {
            if(data == 0){
                iDialogMsg("提示", "设置成功");
            }
        }
    })
}

// 首次登陆，回收提示消息
function firstLoginMsg(){
    $.ajax({
        type: "POST",
        dataType: 'json',
        url: ctx + '/main/sale/followMsg',
    })
}


//邮箱变更
function changeEmailAccount(){
    pubDivShowIframe('email_bind',ctx+'/email/config/toUnBind?v='+new Date().getTime(),'邮箱变更',400,250);
}

//慧营销钱包
function hyxWallet(){
    var timestamp = new Date().getTime();
    addTab($("#tsmPayUrl").val()+"/pay/index?v="+ timestamp,'钱包');
}
//绑定短信接收号码
function smsReceivePhone(){
    pubDivShowIframe('smsReceive_bind',ctx+'/message/smsReceiveBindPage?v='+new Date().getTime(),'帐号绑定手机号',450,350);
}

//关联微信 -- 提供客服端调用
function shipWxChat(base64Json) {
    var custId = "";
    var custName = "";
    var wxLoginId = "";
    var wxId ="";
    var wxName = "";
    var type = "";
    var b = new Base64();
    var encodeJosn =  b.decode(base64Json); //base64Json 一定好utf-8处理后在base64加密，才能解出来。否则会乱码
    var jsonObj =JSON.parse(encodeJosn);
    if(typeof(jsonObj.custId)=="undefined"){
        custId ="";
    }else{
        custId= jsonObj.custId;
    }
    if(typeof(jsonObj.custName)=="undefined"){
        custName ="";
    }else{
        custName= jsonObj.custName;
    }
    if(typeof(jsonObj.wxLoginId)=="undefined"){
        wxLoginId ="";
    } else{
        wxLoginId= jsonObj.wxLoginId;
    }
    if(typeof(jsonObj.wxId)=="undefined"){
        wxId ="";
    } else{
        wxId= jsonObj.wxId;
    }
    if(typeof(jsonObj.wxName)=="undefined"){
        wxName ="";
    } else{
        wxName= jsonObj.wxName;
    }
    if(typeof(jsonObj.wxAct)=="undefined"){
        type ="";
    } else{
        type= jsonObj.wxAct;
    }
    if(type=="unbind"){
        ajaxBindOrUnBind(custId,custName,wxLoginId,wxId,wxName,type);
    }else{
        $.ajax({
            url : ctx + '/res/tao/IsBindedByWx', // 这里要根据微信id和微信登录id两个条件来判断。微信登录id---慧营销用户对应微信id；微信id---联系人对应的微信id。
            data : {
                custId : custId,custName:custName,wxLoginId:wxLoginId,wxId:wxId,wxName:wxName
            },
            dataType : 'json',
            type : 'post',
            error : function(jqXHR, textStatus, errorThrown) {
            },
            success : function(data) {
                var code = data.code;
                var msg = data.msg;
                if(code =="1"){
                    pubDivDialog("bindWx","当前微信已经关联了客户"+msg+"，确认要重新关联吗？",function(){
                        ajaxBindOrUnBind(custId,custName,wxLoginId,wxId,wxName,type);
                    },function(){
                    })
                }else{
                    ajaxBindOrUnBind(custId,custName,wxLoginId,wxId,wxName,type);
                }
            }
        });
    }

}

function ajaxBindOrUnBind(custId,custName,wxLoginId,wxId,wxName,type){
    $.ajax({
        url : ctx + '/res/tao/bindOrUnBind',
        data : {
            custId : custId,custName:custName,wxLoginId:wxLoginId,wxId:wxId,wxName:wxName,type:type
        },
        dataType : 'json',
        type : 'post',
        error : function(jqXHR, textStatus, errorThrown) {
        },
        success : function(data) {
            getCurrWxWindow().reBindWxBtn(type,wxName);
            var msg = "";
            if(type=="unbind"){
                msg ='解绑成功！';
            }else{
                msg ='绑定成功！';
            }
            iDialogMsg("提示", msg);
        }
    });
}

//获取当前tab标题
function getCurrWxWindow(){
    var title = $('.tabs-selected').text();
    var iframeId = "workDeskId";
    if(title == '跟进'){
        iframeId = "followDeskId";
    }
    return document.getElementById(iframeId).contentWindow;
}

//设置资源为已联系
/**
 *  已联系未联系 不根据时长
 *  拨通次数 通话时长大于0
 */
function setConcated4Res(custId,lianXiId,orgId,isConcat,timeLength){
    //蚁盟:3未联系，4已联系
    if(isConcat=='2'||isConcat=='3'||isConcat=='4'){
        $.ajax({
            url : ctx + '/res/tao/updateClueConcat',
            data : {
                custId : custId,lianXiId:lianXiId,orgId:orgId,isConcat:isConcat,timeLength:timeLength
            },
            type : 'post',
            dataType : 'json',
            error : function(jqXHR, textStatus, errorThrown) {
            },
            success : function(data) {
            }
        });
    }else{

        $.ajax({
            url : ctx + '/res/tao/updateConcat4Res',
            data : {
                custId : custId,lianXiId:lianXiId,orgId:orgId,isConcat:isConcat,timeLength:timeLength
            },
            dataType : 'json',
            type : 'post',
            error : function(jqXHR, textStatus, errorThrown) {
            },
            success : function(data) {
            }
        });
    }
}
