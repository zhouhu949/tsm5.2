function pubDivShowIframe(id, url, title, width, height) {
    var content = "<iframe  src=\"" + url + "\"  scrolling=\"yes\" frameborder=\"0\" style=\"width:" + width + "px;height:" + height + "px;\"></iframe>";
    $.dialog({
        title: title,
        id: id,
        content: content,
        padding: 1,
        lock: true,
        fixed: true,
        show: function () {
            // $.dialog.get['test'].$content.html(content);
        },
        hide: function () {
          //  $.dialog.get[id].$content.html(content);
//			$.dialog.get[id].remove();
		//	$(".i-dialog").remove();//强行将弹框移除
        	setTimeout(function(){
//        		$.dialog.get[id].parent().focus();
        		$.dialog.get[id].$content.find("iframe").remove();
        		 	 $.dialog.get[id].remove();
        		 	 setTimeout(function(){
        		 		window.focus();
        		 	 },400);
        	},800);
        }
    });
}

function pubDivShowIframeNoLock(id, url, title, width, height) {
    var content = "<iframe  src=\"" + url + "\"  scrolling=\"yes\" frameborder=\"0\" style=\"width:" + width + "px;height:" + height + "px;\"></iframe>";
    $.dialog({
        title: title,
        id: id,
        content: content,
        padding: 1,
        fixed: true,
        show: function () {
            // $.dialog.get['test'].$content.html(content);
        },
        hide: function () {
          //  $.dialog.get[id].$content.html(content);
//			$.dialog.get[id].remove();
		//	$(".i-dialog").remove();//强行将弹框移除
        	setTimeout(function(){
//        		$.dialog.get[id].parent().focus();
        		$.dialog.get[id].$content.find("iframe").remove();
        		 	 $.dialog.get[id].remove();
        		 	 setTimeout(function(){
        		 		window.focus();
        		 	 },400);
        	},800);
        }
    });
}

function pubPlanDivShowIframe(id, url, title, width, height,className) {
    var content = "<iframe  src=\"" + url + "\"  scrolling=\"yes\" frameborder=\"0\" style=\"width:" + width + "px;height:" + height + "px;\"></iframe>";
    $.dialog({
        title: title,
        id: id,
        content: content,
        padding: 1,
        //lock: true,
        fixed: true,
        show: function () {
        	if(className!=="" && className!==undefined){
        		setTimeout(function(){
        			$.dialog.get[id].$dialog.addClass(className)	
        		},100)
        	}
        	console.log(id)
        },
        hide: function () {
          //  $.dialog.get[id].$content.html(content);
//			$.dialog.get[id].remove();
		//	$(".i-dialog").remove();//强行将弹框移除
        	setTimeout(function(){
//        		$.dialog.get[id].parent().focus();
        		$.dialog.get[id].$content.find("iframe").remove();
        		 	 $.dialog.get[id].remove();
        		 	 setTimeout(function(){
        		 		window.focus();
        		 	 },400);
        	},800);
        }
    });
}

function closeParentPubDivDialogIframe(id) {
    window.parent.closePubDivDialogIframe(id);
}
function closePubDivDialogIframe(id) {
    $.dialog.get[id].hide();
}

function pubDivDialog(id,msg,okFn,cancleFn){
	var str="<div class='bomp-cen'>\n" +
			"\t<div class=\"bomp-tip-a bomp-tip-idialog\"><label class=\"tip-a fl_l\"></label><span class=\"sp-a fl_l\">"+msg+"</span></div>\n" +
			"\t<div class='bomb-btn'>\n" +
			"\t\t<label class='bomb-btn-cen'>\n" +
			"\t\t\t<a href=\"javascript:;\" class=\"com-btna bomp-btna com-btna-sty fl_l\" id='ok_"+id+"'><label>确定</label></a>\n" +
			"\t\t\t<a href=\"javascript:;\" class=\"com-btna bomp-btna fl_l\" id='close_"+id+"'><label>取消</label></a>\n" +
			"\t\t</label>\n" +
			"\t</div>\n" +
			"</div>";

	var dialog = $.dialog({
		title:'确认',
		id:id,
		content:str,
		drag:true,
		lock: true,
		max:false,
		min:false,
		padding:1,
		width:450,
		fixed: true,
		init:function(){
			var that = this;
			$("#ok_"+this.id).click(function(){
				that.hide();
				if(okFn != null){
					okFn();
				}
			});
			$("#close_"+this.id).click(function(){
				that.hide()
				if(cancleFn != null){
					cancleFn();
				}
			});
		},
		hide: function () {
			//  $.dialog.get[id].$content.html(content);
		//	$(".i-dialog").remove();//强行将弹框移除
//			$.dialog.get[id].remove();
			setTimeout(function(){
        		$.dialog.get[id].$content.find("iframe").remove();
        		 	 $.dialog.get[id].remove();
        		 	 setTimeout(function(){
        		 		window.focus();
        		 	 },200);
        	},800);
		}
});
}
function iDialogMsg(title,msg){
	//cueDivDialog("msg11",msg,null,null);
	iDialogMsg1(title,msg);
}
function iDialogMsg1(title,msg){
	//alert(32);
	$.dialog({
		id:"idialog_msg_auto_cancel",
		fixed:true,
		lock:true,
		title:title,
		width:250,
		time:2000,
		content:msg,
		hide: function () {
			$.dialog.get["idialog_msg_auto_cancel"].remove();
		}
	});
}
/*询问弹框*/
function queDivDialog(id,msg,okFn,cancleFn){
	var str="<div class='bomp-cen'>\n" +
		"\t<div class=\"bomp-tip-a bomp-tip-idialog\"><label class=\"tip-a fl_l\"></label><span class=\"sp-a fl_l\">"+msg+"</span></div>\n" +
		"\t<div class='bomb-btn'>\n" +
		"\t\t<label class='bomb-btn-cen'>\n" +
		"\t\t\t<a href=\"javascript:void(0);\" class=\"com-btna bomp-btna com-btna-sty fl_l\" id='ok_"+id+"'><label>确定</label></a>\n" +
		"\t\t\t<a href=\"javascript:void(0);\" class=\"com-btna bomp-btna fl_l\" id='close_"+id+"'><label>取消</label></a>\n" +
		"\t\t</label>\n" +
		"\t</div>\n" +
		"</div>";

	var dialog = $.dialog({
		title:'确认',
		id:id,
		content:str,
		drag:true,
		lock: true,
		max:false,
		width:450,
		min:false,
		padding:1,
		fixed: true,
		init:function(){
			var that = this;
			$("#ok_"+this.id).click(function(){
				that.hide();
				if(okFn != null){
					okFn();
				}
			});
			$("#close_"+this.id).click(function(){
				that.hide()
				if(cancleFn != null){
					cancleFn();
				}
			});
		},
		hide: function () {
			//  $.dialog.get[id].$content.html(content);
			//	$(".i-dialog").remove();//强行将弹框移除
//			$.dialog.get[id].remove();
			setTimeout(function(){
        		$.dialog.get[id].$content.find("iframe").remove();
        		 	 $.dialog.get[id].remove();
        		 	 setTimeout(function(){
        		 		window.focus();
        		 	 },200);
        	},800);
		}
	});
}


function serviceDialog(id,msg,adminName,mobile,okFn,cancleFn){
	if(msg.length==1)msg="0"+msg;
	var str="<div class='bomp-cen bomp-da'>\n" +
		"\t<div class=\"bomp-tip-a bomp-tip-idialog\"><div class=\"date_box fl_l\"><i class=\"bg fl_l\"><i class=\"line\"></i></i><i class=\"bg fl_r\"><i class=\"line\"></i></i><span class=\"num\">"+msg+"</span></div><span class=\"sp-a fl_l\" style=\"\">天后系统将无法登录！</span></div>\n" +
		"\t<p class='bomp_tit_c fl_l'>目前部分功能已经停用</p>\n" +
		"\t<p class='bomp_tit_c fl_l'>请及时联系管理员或联系我们解决！</p>\n" +
		"\t<p class=\"bomp-call bomp-mt fl_l\">\n" +
		"\t\t<span class=\"tit fl_l\">管理员：</span>\n" +
		"\t\t<label class=\"fl_l\"><span class=\"fl_l\">"+adminName+"</span></label>\n" +
		"\t\t<label class=\"fl_l\"><span class=\"fl_l\">"+mobile+"</span></label>\n" +
		"\t</p>\n" +
		"\t<p class=\"bomp-call fl_l\">\n" +
		"\t\t<span class=\"tit fl_l\">联系我们：</span>\n" +
		"\t\t<label class=\"fl_l\"><a href=\"javascript:;\" id=\"open_qq\" class=\"qq fl_l\" title=\"QQ联系\" onclick=\"$('#QQOnLine iframe').contents().find('#launchBtn').click();\"></a><span class=\"fl_l\">"+localStorage.getItem("product_serviceTel")+"</span></label>\n" +
		"\t\t<label class=\"fl_l\"><i href=\"javascript:;\" id=\"open_call\" class=\"phone fl_l\" title=\"电话联系\"></i><span class=\"fl_l\">"+localStorage.getItem("product_serviceTel")+"</span></label>\n" +
		"\t</p>\n" +
		"</div>";
	var tit_html = "<i class=\"bomp-da-tip\"></i><span class=\"bomp-da-sp\">您的帐号已到期</span>";
	var dialog = $.dialog({
		title:tit_html,
		id:id,
		content:str,
		drag:true,
		lock: true,
		max:false,
		width:380,
		height:250,
		min:false,
		padding:1,
		fixed: true,
		init:function(){
			var that = this;
			$("#ok_"+this.id).click(function(){
				that.hide();
				if(okFn != null){
					okFn();
				}
			});
			$("#close_"+this.id).click(function(){
				that.hide();
				if(cancleFn != null){
					cancleFn();
				}
			});
			$("#open_qq").click(function(){
				window.top.$("iframe:visible")[0].contentWindow.$('#QQOnLine iframe').contents().find('#launchBtn').click();
			});
			$("#open_call").click(function(){
				var param = "<xml><Oparation Phone=\""+localStorage.getItem("product_serviceTel")+"\" /></xml>";
				try{
					external.OnCallXml(param);
				}catch(e){
					external.OnCall(phone+"_");
				}
			});
		},
		hide: function () {
			//  $.dialog.get[id].$content.html(content);
			//	$(".i-dialog").remove();//强行将弹框移除
//			$.dialog.get[id].remove();
			setTimeout(function(){
        		$.dialog.get[id].$content.find("iframe").remove();
        		 	 $.dialog.get[id].remove();
        		 	 setTimeout(function(){
        		 		window.focus();
        		 	 },200);
        	},800);
		}
	});


}

/*警告弹框*/
function warmDivDialog(id,msg,okFn,cancleFn){
	var str="<div class='bomp-cen'>\n" +
		"\t<div class=\"bomp-tip-a bomp-tip-idialog\"><label class=\"tip-warm fl_l\"></label><span class=\"sp-a fl_l\" style=\"margin-top:0;\">"+msg+"</span></div>\n" +
		"\t<div class='bomb-btn'>\n" +
		"\t\t<label class='bomb-btn-cen'>\n" +
		"\t\t\t<a href=\"javascript:;\" class=\"com-btna bomp-btna com-btna-sty fl_l\" id='close_"+id+"'><label>确定</label></a>\n" +
		//"\t\t\t<a href=\"javascript:;\" class=\"com-btna bomp-btna fl_l\" id='close_"+id+"'><label>取消</label></a>\n" +
		"\t\t</label>\n" +
		"\t</div>\n" +
		"</div>";

	var dialog = $.dialog({
		title:'确认',
		id:id,
		content:str,
		drag:true,
		lock: true,
		max:false,
		width:450,
		min:false,
		padding:1,
		fixed: true,
		init:function(){
			var that = this;
			$("#ok_"+this.id).click(function(){
				that.hide();
				if(okFn != null){
					okFn();
				}
			});
			$("#close_"+this.id).click(function(){
				that.hide();
				if(cancleFn != null){
					cancleFn();
				}
			});
		},
		hide: function () {
			//  $.dialog.get[id].$content.html(content);
			//	$(".i-dialog").remove();//强行将弹框移除
//			$.dialog.get[id].remove();
			setTimeout(function(){
        		$.dialog.get[id].$content.find("iframe").remove();
        		 	 $.dialog.get[id].remove();
        		 	 setTimeout(function(){
        		 		window.focus();
        		 	 },200);
        	},800);
		}
	});
}

/*成员编辑警告弹框*/
function memWarmDivDialog(id,msg,okFn,cancleFn){
	var str="<div class='bomp-cen'>\n" +
		"\t<div class=\"bomp-tip-a bomp-tip-idialog\"><label class=\"tip-warm fl_l\"></label><span class=\"sp-a fl_l\" style=\"margin-top:0;line-height:20px;\">"+msg+"</span></div>\n" +
		"\t<div class='bomb-btn'>\n" +
		"\t\t<label class='bomb-btn-cen'>\n" +
		"\t\t\t<a href=\"javascript:;\" class=\"com-btna bomp-btna com-btna-sty fl_l\" id='ok_"+id+"'><label>确定</label></a>\n" +
		"\t\t\t<a href=\"javascript:;\" class=\"com-btna bomp-btna fl_l\" id='close_"+id+"'><label>取消</label></a>\n" +
		"\t\t</label>\n" +
		"\t</div>\n" +
		"</div>";

	var dialog = $.dialog({
		title:'确认',
		id:id,
		content:str,
		drag:true,
		lock: true,
		max:false,
		width:450,
		min:false,
		padding:1,
		fixed: true,
		init:function(){
			var that = this;
			$("#ok_"+this.id).click(function(){
				that.hide();
				if(okFn != null){
					okFn();
				}
			});
			$("#close_"+this.id).click(function(){
				that.hide();
				if(cancleFn != null){
					cancleFn();
				}
			});
		},
		hide: function () {
			//  $.dialog.get[id].$content.html(content);
			//	$(".i-dialog").remove();//强行将弹框移除
//			$.dialog.get[id].remove();
			setTimeout(function(){
        		$.dialog.get[id].$content.find("iframe").remove();
        		 	 $.dialog.get[id].remove();
        		 	 setTimeout(function(){
        		 		window.focus();
        		 	 },200);
        	},800);
		}
	});
}





/*错误弹框*/
function wrongDivDialog(id,msg,okFn,cancleFn){
	var str="<div class='bomp-cen'>\n" +
		"\t<div class=\"bomp-tip-a bomp-tip-idialog\"><label class=\"tip-wrong fl_l\"></label><span class=\"sp-a fl_l\">"+msg+"</span></div>\n" +
		"\t<div class='bomb-btn'>\n" +
		"\t\t<label class='bomb-btn-cen'>\n" +
		"\t\t\t<a href=\"javascript:;\" class=\"com-btna bomp-btna com-btna-sty fl_l\" id='ok_"+id+"'><label>确定</label></a>\n" +
		"\t\t\t<a href=\"javascript:;\" class=\"com-btna bomp-btna fl_l\" id='close_"+id+"'><label>取消</label></a>\n" +
		"\t\t</label>\n" +
		"\t</div>\n" +
		"</div>";

	var dialog = $.dialog({
		title:'确认',
		id:id,
		content:str,
		drag:true,
		lock: true,
		max:false,
		min:false,
		width:450,
		padding:1,
		fixed: true,
		init:function(){
			var that = this;
			$("#ok_"+this.id).click(function(){
				that.hide();
				if(okFn != null){
					okFn();
				}
			});
			$("#close_"+this.id).click(function(){
				that.hide()
				if(cancleFn != null){
					cancleFn();
				}
			});
		},
		hide: function () {
			//  $.dialog.get[id].$content.html(content);
			//	$(".i-dialog").remove();//强行将弹框移除
//			$.dialog.get[id].remove();
			setTimeout(function(){
        		$.dialog.get[id].$content.find("iframe").remove();
        		 	 $.dialog.get[id].remove();
        		 	 setTimeout(function(){
        		 		window.focus();
        		 	 },200);
        	},800);
		}
	});
}



/*提示弹框*/
function cueDivDialog(id,msg,okFn,cancleFn){
	var str="<div class='bomp-cen'>\n" +
		"\t<div class=\"bomp-tip-a bomp-tip-idialog\"><label class=\"tip-cup fl_l\"></label><span class=\"sp-a fl_l\">"+msg+"</span></div>\n" +
		"\t<div class='bomb-btn'>\n" +
		"\t\t<label class='bomb-btn-cen'>\n" +
		"\t\t\t<a href=\"javascript:;\" class=\"com-btna bomp-btna com-btna-sty fl_l\" id='ok_"+id+"'><label>确定</label></a>\n" +
		//"\t\t\t<a href=\"javascript:;\" class=\"com-btna bomp-btna fl_l\" id='close_"+id+"'><label>取消</label></a>\n" +
		"\t\t</label>\n" +
		"\t</div>\n" +
		"</div>";

	var dialog = $.dialog({
		title:'确认',
		id:id,
		content:str,
		drag:true,
		lock: true,
		max:false,
		min:false,
		width:450,
		padding:1,
		fixed: true,
		init:function(){
			var that = this;
			$("#ok_"+this.id).click(function(){
				that.hide();
				if(okFn != null){
					okFn();
				}
			});
			$("#close_"+this.id).click(function(){
				that.hide()
				if(cancleFn != null){
					cancleFn();
				}
			});
		},
		hide: function () {
			//  $.dialog.get[id].$content.html(content);
			//	$(".i-dialog").remove();//强行将弹框移除
//			$.dialog.get[id].remove();
			setTimeout(function(){
        		$.dialog.get[id].$content.find("iframe").remove();
        		 	 $.dialog.get[id].remove();
        		 	 setTimeout(function(){
        		 		window.focus();
        		 	 },200);
        	},800);
		}
	});
}

/*未设置交易密码提示弹框*/
function payCueDivDialog(id,title,msg,clickbtn,okFn,cancleFn){
	var str="<div class='bomp-cen'>\n" +
		"\t<div class=\"bomp-tip-a bomp-tip-idialog\"><label class=\"tip-cup fl_l\"></label><span class=\"sp-a fl_l\">"+msg+"</span></div>\n" +
		"\t<div class='bomb-btn'>\n" +
		"\t\t<label class='bomb-btn-cen'>\n" +
		"\t\t\t<a href=\"javascript:;\" class=\"com-btna bomp-btna com-btna-sty fl_l\" id='ok_"+id+"'><label>"+clickbtn+"</label></a>\n" +
		//"\t\t\t<a href=\"javascript:;\" class=\"com-btna bomp-btna fl_l\" id='close_"+id+"'><label>取消</label></a>\n" +
		"\t\t</label>\n" +
		"\t</div>\n" +
		"</div>";

	var dialog = $.dialog({
		title:title,
		id:id,
		content:str,
		drag:true,
		lock: true,
		max:false,
		min:false,
		width:450,
		padding:1,
		fixed: true,
		init:function(){
			var that = this;
			$("#ok_"+this.id).click(function(){
				that.hide();
				if(okFn != null){
					okFn();
				}
			});
			$("#close_"+this.id).click(function(){
				that.hide()
				if(cancleFn != null){
					cancleFn();
				}
			});
		},
		hide: function () {
			//  $.dialog.get[id].$content.html(content);
			//	$(".i-dialog").remove();//强行将弹框移除
//			$.dialog.get[id].remove();
			setTimeout(function(){
        		$.dialog.get[id].$content.find("iframe").remove();
        		 	 $.dialog.get[id].remove();
        		 	 setTimeout(function(){
        		 		window.focus();
        		 	 },200);
        	},800);
		}
	});
}

/*信息查看弹框*/
function viewDetailDivDialog(id,msg,okFn,cancleFn){
	var str="<div class='bomp-cen'>\n" +
		"\t<div class=\"bomp-tip-a bomp-tip-idialog\">"+msg+"</div>\n" +
		"</div>";

	var dialog = $.dialog({
		title:'联系小记',
		id:id,
		content:str,
		drag:true,
		lock: false,
		max:true,
		min:true,
		width:880,
		height:450,
		padding:1,
		fixed: false,
		init:function(){

		},
		hide: function () {
			//  $.dialog.get[id].$content.html(content);
			//	$(".i-dialog").remove();//强行将弹框移除
//			$.dialog.get[id].remove();
			setTimeout(function(){
        		$.dialog.get[id].$content.find("iframe").remove();
        		 	 $.dialog.get[id].remove();
        		 	 setTimeout(function(){
        		 		window.focus();
        		 	 },200);
        	},800);
		}
	});
}


function pubDivDialog1(id,msg,okFn,cancleFn,width,height){
	var str="<div class='bomp-cen'>\n" +
			"\t<div class=\"bomp-tip-a bomp-tip-idialog\"><label class=\"tip-a fl_l\"></label><span class=\"sp-a fl_l\">"+msg+"</span></div>\n" +
			"\t<div class='bomb-btn'>\n" +
			"\t\t<label class='bomb-btn-cen'>\n" +
			"\t\t\t<a href=\"javascript:;\" class=\"com-btna bomp-btna com-btna-sty fl_l\" id='ok_"+id+"'><label>确定</label></a>\n" +
			"\t\t\t<a href=\"javascript:;\" class=\"com-btna bomp-btna fl_l\" id='close_"+id+"'><label>取消</label></a>\n" +
			"\t\t</label>\n" +
			"\t</div>\n" +
			"</div>";

	var dialog = $.dialog({
		title:'确认',
		id:id,
		content:str,
		drag:true,
		lock: true,
		max:false,
		min:false,
		padding:1,
		width:width,
		height:height,
		fixed: true,
		init:function(){
			var that = this;
			$("#ok_"+this.id).click(function(){
				that.hide();
				if(okFn != null){
					okFn();
				}
			});
			$("#close_"+this.id).click(function(){
				that.hide()
				if(cancleFn != null){
					cancleFn();
				}
			});
		},
		hide: function () {
			//  $.dialog.get[id].$content.html(content);
		//	$(".i-dialog").remove();//强行将弹框移除
//			$.dialog.get[id].remove();
			setTimeout(function(){
        		$.dialog.get[id].$content.find("iframe").remove();
        		 	 $.dialog.get[id].remove();
        		 	 setTimeout(function(){
        		 		window.focus();
        		 	 },200);
        	},800);
		}
});
}

/*询问弹框*/
function queSaveDialog(id,msg,okFn,cancleFn){
	var str="<div class='bomp-cen'>\n" +
		"\t<div class=\"bomp-tip-a bomp-tip-idialog\"><label class=\"tip-a fl_l\"></label><span class=\"sp-a fl_l\">"+msg+"</span></div>\n" +
		"\t<div class='bomb-btn'>\n" +
		"\t\t<label class='bomb-btn-cen'>\n" +
		"\t\t\t<a href=\"###\" class=\"com-btna bomp-btna com-btna-sty fl_l\" id='ok_"+id+"'><label>切换</label></a>\n" +
		"\t\t\t<a href=\"###\" class=\"com-btna bomp-btna fl_l\" id='close_"+id+"'><label>取消</label></a>\n" +
		"\t\t</label>\n" +
		"\t</div>\n" +
		"</div>";

	var dialog = $.dialog({
		title:"确认",
		id:id,
		content:str,
		drag:true,
		lock: true,
		max:false,
		width:450,
		min:false,
		padding:1,
		fixed: true,
		init:function(){
			var that = this;
			$("#ok_"+this.id).click(function(){
				that.hide();
				if(okFn != null){
					okFn();
				}
			});
			$("#close_"+this.id).click(function(){
				that.hide()
				if(cancleFn != null){
					cancleFn();
				}
			});
		},
		hide: function () {
			//  $.dialog.get[id].$content.html(content);
			//	$(".i-dialog").remove();//强行将弹框移除
//			$.dialog.get[id].remove();
			setTimeout(function(){
        		$.dialog.get[id].$content.find("iframe").remove();
        		 	 $.dialog.get[id].remove();
        		 	 setTimeout(function(){
        		 		window.focus();
        		 	 },200);
        	},800);
		}
	});
}
