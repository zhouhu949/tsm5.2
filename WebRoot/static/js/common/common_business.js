/***********************************************
 * 慧营销——业务共同JS文件
 * @author haoqj
 * @date 2013-11-13
 ***********************************************/
/**
 * 拨打电话共同方法
 * @param  phone	手机或固话号码
 * @param  custId	客户id
 * @param  followId 跟进ID
 */
function callPhone(phone,custId,followId){
	if(phone!=null && phone !=''){
		var params = phone.split('-');
		var param = params[0] + "_" + custId;
		try{
			var paramXml = "<xml><Oparation Phone=\""+params[0]+"\" CustID=\""+custId+"\" ";
			if(followId != null && followId != '')
				paramXml+="FollowID=\""+followId+"\" ";
			paramXml+="/></xml>";
			external.OnCallXml(paramXml);
		}catch(e){
			external.OnCall(param);
		}
	}
}

/**
 *  修改基本信息后 动态设置新的值
 * @param phone
 * @param custId
 * @param followId
 */
function callPhone2(elementId,custId,followId){
	var phone = $("#" + elementId ).val();
	if(phone!=null && phone !=''){
		var params = phone.split('-');
		var param = params[0] + "_" + custId;
		try{
			var paramXml = "<xml><Oparation Phone=\""+params[0]+"\" CustID=\""+custId+"\" ";
			if(followId != null && followId != '')
				paramXml+="FollowID=\""+followId+"\" ";
			paramXml+="/></xml>";
			external.OnCallXml(paramXml);
		}catch(e){
			external.OnCall(param);
		}
	}
}

/**
 * 根据链接地址打开网页
 */
function showPublicUrl(url){
	if(url!=null && url !=''){
		external.ShowPublicUrl(url);
	}
}

//群发短信
function toBatchSmsSend(){
	var name_mobile= '';
	$("input[name='check_']").each(function(index) {
		if ($(this).attr("checked")) {
			var temp = "";
			var tempStr = $(this).attr('name_mobile');
			var tempStr1 = $(this).attr('name_tel');
			var mobile =  $(this).attr('mobile');
			var telPhone =  $(this).attr('telPhone');
			var pattern = /^[0-9]*$/;
			if (tempStr!='' && tempStr != null && (pattern.test(mobile) && mobile.length==11)) {
				temp = tempStr.split("|")[0];
				if(temp!=""){
					name_mobile += tempStr + ";";
				}else{
					name_mobile += mobile + ";";
				}				
			}else if (tempStr1!='' && tempStr1 != null && (pattern.test(telPhone) && telPhone.length==11)) {
				temp = tempStr1.split("|")[0];
				if(temp!=""){
					name_mobile += tempStr1 + ";";
				}else{
					name_mobile += telPhone + ";";
				}
			}
		}
	});
	if(name_mobile==""){
		window.top.iDialogMsg("提示","请先选择客户！");
	}else{
		pubDivShowIframe('sms_send',ctx+'/call/sms/toIdialogSmsSend.do?phone='+name_mobile+'&v='+new Date().getTime(),'发送短信',915,570);
	}
}

/**
 * 发送短信共同方法
 * custName 客户名称
 * phone 电话号码
 */
function toSmsSendPage(custName,phone) {
	if(phone==null && phone ==''){
		window.top.iDialogMsg("提示","手机号码为空！");
		return;
	}
	if(!checkMobileFamat(phone) || phone.length != 11){
		window.top.iDialogMsg("提示","手机号码格式不正确");
		return;
	}
	pubDivShowIframe('sms_send',ctx+'/call/sms/toIdialogSmsSend.do?name='+custName+'&phone='+phone+'&v='+new Date().getTime(),'发送短信',915,570);
}


/** 发送邮件公共方法，先查询是否已绑定邮箱，再做下一步操作  */
function emailBysend(custName,email){
	$.ajax({
		url: ctx+'/email/config/getEmailConfig.do',
		type:'POST',
		dataType:'json',
		error:function(){window.top.iDialogMsg("提示","网络异常，请稍后再操作！")},
		success:function(data){
			if(data == 0){
				toEmailSendPage(custName,email);
			}else{
				bindEmail();
			}
		}
	});
}

/**
 * 发送邮件共同方法
 * custName 客户名称
 * email 邮箱
 */
function toEmailSendPage(custName,email) {
	if(email==null && email ==''){
		window.top.iDialogMsg("提示","邮箱不可为空！");
		return;
	}
	if(!checkEmailFamat(email)){
		window.top.iDialogMsg("提示","邮箱格式不正确");
		return;
	}
	pubDivShowIframe('email_send',ctx+'/call/email/toIdialogEmailSend.do?name='+custName+'&email='+email+'&v='+new Date().getTime(),'发送邮件',1050,520);
}

/** 绑定邮箱 */
function bindEmail(){
	pubDivShowIframe('email_bind',ctx+'/view/follow/idialog/emailBind.jsp','绑定邮箱',400,250);
}

//验证正则格式
function checkEmailFamat(val){
	// 不带姓名的邮箱
	var reg1 = /^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/;
	return reg1.test(val);
}

//验证正则格式
function checkMobileFamat(val){
	var pattern = /^[0-9]*$/;
	return pattern.test(val);
}

/** 过期提醒 **/
function serveOverAlert(realnameUrl,account){
	//content: "您的服务已到期，为了不影响使用，请&nbsp;<a target=\"_blank\" href=\""+realnameUrl+"/avoid/openUp.do?account="+account+"\"><font color=\"red\"><font color=\"red\">立即续订</font></a>",
	$("#serverOver").val(1);
	$.dialog({
		lock: true,
	    background: '#F0F0F0',
	    opacity: 0.50,
		icon: 'warning',   
		content: "您的服务已到期，请联系管理员",
		title:'提示',
		close:function(){
			var height = $(document.body).height();//获得窗口高度
			if(document.getElementById("transl_div_id")){
				$("#transl_div_id").addClass("transl_div").css({height:height});
			}
			//需求说这里可以关闭,因此注释
			//return false;
		}
	});
}

/** 即将过期提醒 **/
function serveQuickOverAlert(date_time,realnameUrl,account,dayCount){
	var _base = $("#base").val();
	$.dialog({
		lock: true,
	    background: '#F0F0F0',
	    opacity: 0.50,
		icon: 'warning',   
		content: "您的服务将于&nbsp;<font color='blue'>"+date_time+"</font>&nbsp;到期,还剩余<font color='red'><b>"+dayCount+"</b></font>天。<br/><br/><input type=\"checkbox\" id=\"isWarn\" name=\"isWarn\" value=\"1\"/>&nbsp;不再提示",
		title:'提示',
		cancel: true,
		ok:function(){
			if($("#isWarn").attr("checked") == "checked"){
				$.ajax({ 
					type:"POST", //请求方式
					url:_base+"/auth/isWarn.do", //请求路径
					cache: false,   
					success:function(data){//成功之后
			        }
				});
			}
		}
	});
}

function checkIsOpenService(){
	var _base = $("#base").val();
	/** 打开首页第一件事开始检查是否过期 **/
	$.ajax({ 
		type:"POST", //请求方式
		url:_base+"/auth/checkIsOpenService.do", //请求路径
		cache: false,
		async:false,
		success:function(data){//成功之后
			if(null != data && "" != data){
				var datas_ = data.split(",");
				var status = datas_[0];
				var endTime = datas_[1];
				var realnameUrl = datas_[2];
				var account = datas_[3];
				var dayCount = datas_[4];
				if("1" == status){
					serveQuickOverAlert(endTime,realnameUrl,account,dayCount);
				}
				if("2" == status){
					serveOverAlert(realnameUrl,account);
				}
			}
			
	    }
	});
}


/**
 * 播放器列表播放 公共方法
 * @param ip 主机IP
 * @param port 主机端口
 * @param base  工程名
 * @param plength	录音时长
 * @param url		录音地址
 * @param callId	录音ID
 * @param callName  录音名称
 */
function recordCallPlay(httpUrl2,plength,url,callId,callName,callNum,bPlay){
	try{
		var showRecordPlay ="{\"RecordPlay\":" +
				"{\"code\":\""+callId+"\""+
					",\"name\":\""+callName+"\""+
					",\"phone\":\""+callNum+"\""+
					",\"url\":\""+url+"\""+
					",\"dur\":\""+plength+"\"" ;
						 if(bPlay !=null && $.trim(bPlay)!=""){
							 showRecordPlay +=",\"bPlay\":\""+bPlay+"\"";
						 }
				showRecordPlay += "}" +
				"}";
		external.ShowRecordPlay(showRecordPlay);
	}catch(x){		
		var httpUrl = httpUrl2 + "/call/callPlay.do?timeElngth="+plength+"&recordUrl="+encodeURIComponent(encodeURIComponent(url,"utf-8"))+"&callId="+callId+"&callName="+encodeURI(encodeURI(callName))+"&callNum="+callNum;
		var reqXml = '<xml><Oparation Name=\"ExpandCallDlg\" Title=\"播放列表\"  Http=\"' + httpUrl + '\" StartPos=\"-1,-1\" EndPos=\"302,413\"/></xml>';			
		try{
			external.OnBtnClickDisplayRecord(reqXml);
		}catch(x){
			external.OnBtnClickShowDetail(reqXml); 
		}
	}		
}
/**
 *播放器列表播放 新开方法
 * @param url 录音url
 * @param plength 录音时长
 * @param bPlay 是否立即播放 1 为立即播放 0为不
 * 	name 和 phone 是写死的 不重要
 * 	code 为生成的唯一uuid 
 */
function signLogPlayRecords(url,code,plength,bPlay){
	var showRecordPlay ={
			RecordPlay:{
				code:code,
				name:"播放录音",
				phone:"",
				url:url,
				dur:plength,
				bPlay:bPlay
			}
	};
	var showRecordPlayStr = JSON.stringify(showRecordPlay);
	external.ShowRecordPlay && external.ShowRecordPlay(showRecordPlayStr);
}
/**
 * 客户卡片的公共调用方法
 * @param custId  客户id
 * @param type    团队意向客户池的点评入口（一般可以不用填写，固定参数：teamCust）
 * */
function click_custInfo(custId,name){
	window.top.addTab('/res/cust/custCardInfo.do?custId='+custId,false,name+'_'+custId,'icon-gear-wheel');
}

/**
 * 拷贝电话号码
 * @param phone
 */
function copyPhone(id) {
    var phone = $('#' + id).val();
	try{
		external.OnCopyToClipBoard(phone);
		window.top.iDialogMsg("提示","复制成功");
	}catch(e){

	}

}
//清除电话号码
function clearPhone(telphone,telphone_safe,errorId){
	 $('#' + telphone).val(''); //
	 $('#' + telphone_safe).val('');
	 $('#' + errorId).html(''); //清除去重的错误消息  id=error_dp
	 $('#error'+'_'+telphone).html(''); //清除验证的错误消息
	 $('#' + telphone).nextAll(':last').html("");
	 $('#' + telphone).parent().attr('class',"bomp-p p_relative"); // 清除 含有  bomp-error 的样式
}
