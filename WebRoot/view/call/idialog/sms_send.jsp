<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>发送短信</title>
<!--公共样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css"><!--主要样式-->
<script type="text/javascript">
var valMap = new Map(); // 存储实际的号码
var opera = true;
$(function(){
	
	//渠道平台和 本机短信的change
	//0 是本机  1是渠道 2是ema
	$(".channel_selection").change(function(){
		var $this = $(this);
		var signInput = $("#jq_sign_input");
		var signval = $("#signVal");
		if($this.val() == 0||$this.val() == 2){
			signInput.val("");
		}else if($this.val() == 1){
			signInput.val(signval.val());
		}
		noteCount();
	})
	
	
	// 请输入内容 隐藏/显示
    $(".bomp-pos-a").find('.lab_hid').click(function(){
        var ipt_b = $(this).parent().find('.bomp-focus');
        ipt_b.focus();
        $(this).parent().find('.lab_hid').hide();
        ipt_b.blur(function(){
            if($(this).val() == ''){
                $(this).parent().find('.lab_hid').show();
            }
         }); 
    });
    // 请输入内容 隐藏/显示
    $(".bomp-pos-a").find('.bomp-focus').click(function(){
        var ipt_c = $(this);
        ipt_c.focus();
        $(this).parent().parent().find('.lab_hid').hide();
        ipt_c.blur(function(){
            if($(this).val() == ''){
                $(this).parent().parent().find('.lab_hid').show();
            }
         }); 
    });
    
    $(".bomp-pos-a").find('.bomp-focus').blur(function(){
         if($(this).val() == ''){
             $(this).parent().parent().find('.lab_hid').show();
         }
      }); 

	// 请输入接收人 隐藏/显示
    $(".lab_icon").find('.drop').find('.ipt,.icon').click(function(){
        var ipt = $(this).parent().find('.ipt');
        ipt.focus();
        $(this).parent().find('.icon').hide();
        ipt.blur(function(){
            if($(this).val() == ''){
                $(this).parent().find('.icon').show();
            }
         }); 
    });
    
    //添加接收人按钮
    $(".lab_icon_a").on("click", function(e){
		var lab_icon_a = $(this).parent();
		if(lab_icon_a.find('.drop').is(":hidden")){
			var url = "${ctx}/call/phone/custInfos";
			$.post(url,function(data){
				lab_icon_a.find('.drop').fadeIn();
				lab_icon_a.find('.drop').html(data);
			},'html');
	    }else{
	    	lab_icon_a.find('.drop').html("");
	    	lab_icon_a.find('.drop').fadeOut();
	    }

	    $(document).one("click", function(){
	    	lab_icon_a.find('.drop').html("");
	        lab_icon_a.find('.drop').fadeOut();
	    });

	    e.stopPropagation();
	});
	$(".lab_icon_a").parent().find('.drop').on("click", function(e){
	    e.stopPropagation();
	});
    
    // 控制接收人输入框的高度
    var adaptiveHeight = function(a, baserows, maxrows) {
	/*
	    a: textarea元素
	    baserows: 基础行数
	    maxrows: 最大行数
	*/
	    /*获取textarea的padding的上下高度*/
	    var po =  parseInt(a.css('padding-top')) + parseInt(a.css('padding-bottom'));
	    var baseLineHeight = parseInt(a.css('line-height'));
	    var baseHeight = baseLineHeight * baserows;
	    a.height(baseHeight);
	    var scrollval = a[0].scrollHeight;
	    /*检测是否达到了最大行数，达到了，则把高度设置为最大高度*/
	    if (scrollval - po >= baseLineHeight * maxrows) {
	        scrollval = baseHeight + baseLineHeight * (maxrows-baserows) + po;
	    }
	    a.height(scrollval - po);
	};

	var adaptiveTextarea = function(sel, baserows, maxrows, callback){
	    sel.bind('input propertychange', function(e) {
	        adaptiveHeight($(this), baserows, maxrows);
	        if(callback) callback(e);
	    });
	    /*初始化textarea高度*/
	    adaptiveHeight(sel, baserows, maxrows);
	};

	/*使用 初始行数为1， 最大行数为3*/
	adaptiveTextarea($('.bomp-mce-text'), 1, 3);
	
    // 保存
    $("#save_buf").click(function(){
    	if(opera && checkIsNull()){
    		opera = false;
        	$('#idialogForm').ajaxSubmit({
    			url : ctx+'/call/sms/smsSend.do',
    			type : 'post',
    			error : function(XMLHttpRequest, textStatus){opera = true;window.top.iDialogMsg("提示","请求失败！");},
    			success : function(data) {	
    				opera = true;
    				var  data2 =data.replace(/(\n|\r\n)+/g,"");//回车换行替换为空格
    				
    				if(data2.substr(0,1)=="0"){
    					window.top.iDialogMsg("提示","发送成功！");
    					closeParentPubDivDialogIframe('sms_send');			
    				}else{
    					if('3'==data2){
    						window.top.iDialogMsg("提示","发送失败！");
    					}else if('9001'==data2){
    						window.top.iDialogMsg("提示","用户不存在！");
    					}else if('9002'==data2){
    						window.top.iDialogMsg("提示","接收人所有手机格式不正确！");
    					}else if('9003'==data2){
    						window.top.iDialogMsg("提示","当前的短信剩余条数不够，请充值！");
    					}else if('9004'==data2){
    						window.top.iDialogMsg("提示","短信条数为空，请充值！");
    					}else if('9005'==data2){
    						window.top.iDialogMsg("提示","短信接收人总数不得超过10000！");
    					}else if('9007'==data2){
    						window.top.iDialogMsg("提示","短信内容以及签名不可以为空！");
    					}else{
    						window.top.iDialogMsg("提示",data2);
    					}
    				}
    			}
    		});	
    	}
    });

    /********** 屏蔽手机或固话中间几位  *********/
	replacePhone();
});

//短信字数及条数统计：
function noteCount() {
	var $jq_content_area = $("#jq_content_area");
	var $jq_sign_input = $("#jq_sign_input");
	var $jq_charSize = $("#jq_charSize"); // 还可以输入多少字
	var $jq_noteCount_span = $("#jq_noteCount_span"); // 短信条数
	var signVal = $.trim($jq_sign_input.val());
	var AREA_MAX_LEN = 280; // 最多可以输入的字节
	var oneNoteMaxChars = 66; // 一条短信最多字节
	var areaVal =$.trim( $jq_content_area.val());
	
	var signLength = 0;
	if (signVal.length != 0) {
		signLength = signVal.length;
	}
	
	// 过滤当字数超出AREA_MAX_LEN 的情况：
	if (areaVal.length > AREA_MAX_LEN - signLength) {
		areaVal = areaVal.substring(0, AREA_MAX_LEN - signLength);
		$jq_content_area.val(areaVal);
	}
	
	// 文本域长度：【回车换行符统一计为2字】
	areaVal = areaVal.replace(/\r\n/g, "##").replace(/\n/g, "##").replace(/\r/g, "##");
	var total = 0;
	total = areaVal.length;
	total += signLength;
	$jq_charSize.text(AREA_MAX_LEN-parseInt(total));
	$jq_noteCount_span.text(Math.ceil(total / oneNoteMaxChars));
}

function checkIsNull(){	
	if($("#jq_content_area").val() == null || $.trim($("#jq_content_area").val())==""){
		window.top.iDialogMsg("提示","短信内容不能为空！");
		return false;
	}
	if($("#name_mobile_textarea").val() == null || $.trim($("#name_mobile_textarea").val())==""){
		window.top.iDialogMsg("提示","接收人不能为空！");
		return false;
	}

	 var $checks =$("#name_mobile_textarea").val().split(";");	
	 var name_val = "";
	 var clength = 1;
	    if($checks.length >1){
	    	clength = $checks.length -1;
	    }	
	   for(var i = 0;i<clength;i++){
	    		var checkVal = valMap.get($checks[i]);
	    		if(typeof(checkVal) == 'undefined'){
	    			name_val += $checks[i]+",";
	    		}else{
	    			name_val += checkVal+",";
	    		}	    		
	    }
		$("#name_mobile").val(name_val);
	return checkReciever($("#name_mobile").val())
}

//判断接收者格式是否正确
function checkReciever(receiveId){
	  var isValid = true;
	  var array = receiveId.split(";");
	  for (var i=0;i<array.length-1;i++)
	  { 
	     var subArray = array[i].split("|");
	     if(subArray.length>0){
	    	 if(subArray.length==1){
	    		 if($.trim(subArray[0]).length == 11 && checkMobileFamat($.trim(subArray[0]))){
	    			 isValid = true;
	    		 }else{
						window.top.iDialogMsg("提示","接收人格式不正确！");
	    	    	    isValid = false;
	    	    	    break;
	    		 }
	    	 }else{
	    		 if($.trim(subArray[1]).length == 11 && checkMobileFamat($.trim(subArray[1]))){
	    			 isValid = true;
	    		 }else{
	    			 window.top.iDialogMsg("提示","接收人格式不正确！");
	    	    	 isValid = false;
	    	    	 break;
	    		 } 
	    	 }
	     }else{
	    	 window.top.iDialogMsg("提示","输入格式不正确！");
	    	 isValid = false;
	    	 break;
	     }
	  }
	  return isValid;
}

/**
 *  屏蔽手机或固话的中间几位，用 ‘*’ 替代
 *  
 */
  function replaceWord_(str){	
	  str = str.replace("-","a");
	  //1，验证手机
		var mobilePattern =/^(0?((13[0-9]{1})|(15[^4,\D])|(18[0-9]{1})|(14[0-9]{1}))+\d{8})$/;
		var telPattern = /^((0(10|2[^6,\D]|[3-9]\d{2}))-?[1-9]\d{6,7}(-\d{1,8})?)$/;
		var isMobileFomat = mobilePattern.test(str);
		var isTelFomat = telPattern.test(str);
		if(str == '' || str == null || str=='undefined'){
			return ;
		}else if(str.length<7){
			return ;
		}else if(str.length ==7){
			return str.replace(/(\d{2})\d{4}(\d{1})/, '$1****$2');
		}else if(str.length ==8 ){
			return str.replace(/(\d{2})\d{4}(\d{2})/, '$1****$2');
		}else if(str.length ==9 ){
			return str.replace(/(\d{3})\d{4}(\d{2})/, '$1****$2');
		}else if(isMobileFomat){
			return str.replace(/(\d{3})\d{4}(\d{4})/, '$1****$2');
		}else if( str.length ==11 ){
			return str.replace(/(\d{4})\d{4}(\d{3})/, '$1****$2');
		}else if(str.length ==12){
			return str.replace(/(\d{4})\d{4}(\d{4})/, '$1****$2');
		}else if(str.length ==10){
			return str.replace(/(\d{3})\d{4}(\d{3})/, '$1****$2');
		}else if(str.length ==13){
			return str.replace(/(\d{4})\d{4}(\d{5})/, '$1****$2');
		}else{
			var firstStr = str.substring(0,14);
			var secondStr = str.substring(14);
			str = firstStr.replace(/(\d{4})\w{4}(\w{6})/, '$1****$2') + secondStr;
			return str.replace("a","-");
		}		
  }
  
  /********** 屏蔽手机或固话中间几位  *********/
  function replacePhone(){
	    var idReplaceWord = $("#idReplaceWord").val();	
	    var name_mobile_textarea = $("#name_mobile_textarea");
	    var name_mobile= $("#name_mobile");
	    if(name_mobile.val()!=null && $.trim(name_mobile.val())!=""){
	    	var $checks =name_mobile.val().split(";");	
			 var clength = 1;
			    if($checks.length >1){
			    	clength = $checks.length -1;
			    }	
			    	var phoneVal = "";
			    	for(var i = 0;i<clength;i++){
			    		if($checks[i].indexOf("|") > 0 ){
			    			phoneVal = $checks[i].split("|")[1];
			    			var rs = "";
			    			if(idReplaceWord==1){	
			    				rs = replaceWord_(phoneVal);
			    			}			    			
			    			if($.trim(rs) != ""){
			    				name_mobile_textarea.val(name_mobile_textarea.val()+$checks[i].split("|")[0]+"|"+rs+";");
			    				valMap.put($checks[i].split("|")[0]+"|"+rs,$checks[i]);
			    			}else{
			    				name_mobile_textarea.val(name_mobile_textarea.val()+$checks[i]+";");
			    				valMap.put($checks[i],$checks[i]);
			    			}
			    		}else{	
			    			var rs1 = "";
			    			if(idReplaceWord==1){	
			    				rs1 = replaceWord_($checks[i]);
			    			}
			    			if($.trim(rs1) != ""){
			    				name_mobile_textarea.val(name_mobile_textarea.val()+rs1+";");
			    				valMap.put(rs1,$checks[i]);
			    			}else{
			    				name_mobile_textarea.val(name_mobile_textarea.val()+$checks[i]+";");
			    				valMap.put($checks[i],$checks[i]);
			    			}
			    		}
			    	}
	    }	    			
  }
  
  // 接收人 失去焦点后事件
  function changeArea(){
	  var name_mobile_textarea = $("#name_mobile_textarea");
	  var $checks =name_mobile_textarea.val().split(";");
	  var channel = $(".channel_selection");
	  var box = $(".channel_select_box");
	  var roleflag = $("#rolesByTrans");
	  var lengths=$checks.length ;
	  if(roleflag.length > 0){
	  	if(lengths > 5){
	  		channel.find("option[value='0']").attr("selected","true");
	  		box.hide();
	  	}else{
	  		channel.find("option[value='0']").attr("selected","true");
	  		box.show();
	  	}
	  }
	  	
	    if(lengths >0){
	    	for(var i = 0;i<$checks.length-1;i++){
	    		if(!($.trim(valMap.get($checks[i])) != "")){
	    			valMap.put($checks[i],$checks[i]);
	    		}
	    	}
	    }
  }
  
  /** =========定义MAP================ */
	function Map(){
		this.container = new Object();
	}

	Map.prototype.put = function(key, value){
	this.container[key] = value;
	}

	Map.prototype.get = function(key){
	return this.container[key];
	}

	Map.prototype.keySet = function() {
	var keyset = new Array();
	var count = 0;
	for (var key in this.container) {
	// 跳过object的extend函数
	if (key == 'extend') {
		continue;
	}
	keyset[count] = key;
	count++;
	}
	return keyset;
	}


	Map.prototype.size = function() {
	var count = 0;
	for (var key in this.container) {
	// 跳过object的extend函数
	if (key == 'extend'){
	continue;
	}
	count++;
	}
	return count;
	}


	Map.prototype.remove = function(key) {
		delete this.container[key];
	}
/**=============================================*/
</script>
</head>
<body> 
<div class='bomp-cen alert_d'>
	<input type="hidden" id="idReplaceWord" value="${idReplaceWord}">
	<input type="hidden" id="isSim" value="${isSim}"> <!-- 手机卡短信是否启用（1:启用、0:停用） -->
	<form id="idialogForm" method="post">
		<div class="bomp_mess_left fl_l">
			<div class='bomp-p bomp-p-widtha bomp-pos' style="z-index:555;">
				<label class='lab_a fl_l'>接收人：</label>
				<div class="lab_icon" style="margin-right:0 !important;width:476px !important;">
				    <textarea class='ipt_a w_d bomp-focus bomp-mce-text fl_l' id="name_mobile_textarea" onblur="changeArea()" style="min-height:60px;"></textarea>
					<input type='hidden' id="name_mobile" name="name_mobile" value='${name_mobile }' class='ipt_a w_d bomp-focus fl_l' />
					<label class="lab_hid" style="display:none;">例：张三|186xxxx1234;186xxxx5678;</label>
					<c:if test="${groupType != 2 }"><i class="lab_icon_a"></i></c:if>
					<!-- <i class="lab_icon_a"></i> -->
					<div class="drop" style="display:none;" id="drop_">
						<!-- 异步查询 -->
					</div>
				</div>
			</div>
			<p class='bomp-p bomp-p-widtha bomp-pos-a'>
				<label class='lab_a fl_l'>短信内容：</label>
				<label class="area_box">
					<textarea class='area_a bomp-focus fl_l' name="smsContent"  id="jq_content_area" onkeyup="noteCount()" onblur="noteCount()"></textarea>
					<span class="box_a">剩余<b class="bomp-red" id="jq_charSize">280</b>字&nbsp;&nbsp;预计<b class="bomp-red" id="jq_noteCount_span">0</b>条短信&nbsp;&nbsp;&nbsp;&nbsp;</span>
				</label>
				<label class="lab_hid">请输入短信内容</label>
			</p>
			<p class='bomp-p bomp-p-widtha bomp-pos'>
				<label class='lab_a fl_l'>签名：</label>
				<input type='text' name="smslabel" readonly="readonly" id="jq_sign_input" value='${smslabel}' class='ipt_a  bomp-focus fl_l'  onkeyup="noteCount()" onblur="noteCount()" />
				<input  type="hidden" id="signVal" value="${smslabel}" readonly/>
				<span style="display:inline-block; line-height:30px;">（签名也计入字数）</span>
				<label class="lab_hid" style="display:none;">输入请不要超过10个字</label>
			</p>
			 <c:if test="${groupType != 2 && (isSim ==1 || isEma ==1) }"> 
				<input type="hidden" id="rolesByTrans"/>
				<p class='bomp-p bomp-p-widtha bomp-pos channel_select_box' >
					<label class='lab_a fl_l'>通道选择：</label>
					<select name="channelSelect" class='ipt_a channel_selection  bomp-focus fl_l'>						
						<option value="1" selected>短信渠道平台</option>
						<c:if test ="${isSim ==1 }">
							<option value="0"  >本机号码</option>
						</c:if>
						<c:if test ="${isEma ==1 }">
							<option value="2" >EMA短信</option>
						</c:if>
					</select>
				</p>
			 </c:if> 
			<dl class="tit">
				<dt class="bomp-red"><h2>温馨提示：</h2></dt>
				<dd>1.本平台支持非营销类短信发送；</dd>
				<dd>2.EMA短信和本机号码一天不能超过300个号码</dd>
				<dd>3.EMA短信和手机号码单次提交不能超过20个号码</dd>
				<dd>4.营销或非法短信将被系统自动屏蔽；</dd>
				<dd>5.短信有效发送时间为8:30-18:00，非有效时间发送平台自动顺延发送时间；</dd>
				<dd>6.请遵守《信息源入网信息安全责任书》的相关规定，并接受通信管理局审计。</dd>
			</dl>
			<div class='bomb-btn' style="margin-top:5px !important;">
				<label class='bomb-btn-cen'>
					<a href="javascript:;" class="com-btna bomp-btna com-btna-sty fl_l"  id="save_buf"><label>发送</label></a>
				</label>
			</div>
		</div>
	</form>
	<div class="bomp_mess_right fl_l" id="bomp_mess_right">
		<c:import url="/view/call/idialog/sms_send_right.jsp"/>
	</div>
</div>
</body>
</html>