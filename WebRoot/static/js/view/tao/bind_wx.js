/**
 *   初始化微信按钮显示
 */

function initWxButton(concatId,name) {
	$.ajax({
		url : ctx + '/res/tao/getWxBindInfo',
		data : {
			concatId : concatId,name:name
		},
		dataType : 'json',
		type : 'post',
		error : function(jqXHR, textStatus, errorThrown) {
		},
		success : function(data) {
			var img = $(".custInfo_weChat>img");
			var wxId = data.wxId;
			if(typeof(wxId)=="undefined" || wxId == null || wxId==""){ 
				$(".btn_bind_weChat").show();
				$(".btn_reBind_weChat").hide();
				changeImgGrey(img);
				showWxName(false,data.wxName);
			} else{
				$(".btn_bind_weChat").hide();
				$(".btn_reBind_weChat").show();
				changeImgBlue(img);
				showWxName(true,data.wxName)
			}
		}
	});
} 

function bindWxChat(concatId,name,type) {
	$.ajax({
		url : ctx + '/res/tao/getWxBindInfo',
		data : {
			concatId : concatId,name:name
		},
		dataType : 'json',
		type : 'post',
		error : function(jqXHR, textStatus, errorThrown) {
		},
		success : function(data) {
			data.wxAct= type;
			var jsonStr = JSON.stringify(data);
			bindWx(jsonStr);
		}
	});
}
////重新绑定微信
//function reBindWxChat(custId,custName) {
//
//	var object = new Object();
//	object.custId = custId;
//	object.custName = custName;
//	var jsonStr = JSON.stringify(object);
//	bindWx(jsonStr);
//}

function bindWx(jsonStr){
	var b = new Base64();  
	var encodeJosn =  b.encode(jsonStr); 
	external.bindWxLiao(encodeJosn);
}

function reBindWxBtn(type,wxName){
	var img = $(".custInfo_weChat>img");
	if(type=="unbind"){
		$(".btn_bind_weChat").show();
		$(".btn_reBind_weChat").hide();
		changeImgGrey(img);
		showWxName(false,wxName);
	}else{
		changeImgBlue(img);
		showWxName(true,wxName);
		$(".btn_bind_weChat").hide();
		$(".btn_reBind_weChat").show();
	}

}

function showWxName(type,wxName){
	if(type){
		$("#hidden_wxName").html(wxName);
		$('.custInfo_weChat').attr('title',$("#hidden_wxName").text());
	}else{
		$('.custInfo_weChat').attr('title','');
	}
}
