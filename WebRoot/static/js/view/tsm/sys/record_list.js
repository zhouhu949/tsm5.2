$(function(){

	//开始拥有时间
	 $('#pDate').dateRangePicker({
		    showShortcuts:false,
	        endDate:getNowFormatDate(),format: 'YYYY-MM-DD'
	    }).bind('datepicker-apply',function(event,obj){
	    	var s_t = '';
	    	var e_t = '';
	    	if(obj.date1 != 'Invalid Date'){
	    		$('#s_pstartDate').val(moment(obj.date1).format('YYYY-MM-DD'));
	    		s_t = moment(obj.date1).format('YY.MM.DD');
	    	}
	    	if(obj.date2 != 'Invalid Date'){
	    		$('#s_pendDate').val(moment(obj.date2).format('YYYY-MM-DD'));
	    		e_t = moment(obj.date2).format('YY.MM.DD');
	    	}
	    	var s = $(this).parents('.select');
	    	var dt=s.children("dt");
	        var dd=s.children("dd");
	        dt.html(s_t+'/'+e_t);
	        $("#oDateType").val(5);
	        dd.slideUp(200);
	        dt.removeClass("cur");
//	        s.css("z-index",z);
	 });
	// 提交
	$('#queryBtn').click(function() {
		loadPage(2);
	});	
    /*下拉框部分*/
    $('.mail').click(function(){
        $(this).find('.drop').fadeToggle();
    });
    $('.hyx-cm-new').find('.load').each(function(){
    	if($(this).find('.sp').text().length >= 23){
	    	$(this).find('.sp').text($(this).find('.sp').text().substr(0,23) + '...');
	    }
    });
    
	// 获取和失去焦点
	$("#qKeyWordId").focus(function() {
		$(this).next().text('');
	});
	$("#qKeyWordId").blur(function() {
		$(this).next().text('输入点评人/点评内容');
	});    
    $('.hyx-cm-new').find('.load').mouseover(function(){
        $(this).find('.drop').fadeIn(1);
    });
    $('.hyx-cm-new').find('.load').mouseleave(function(){
        $(this).find('.drop').fadeOut(1);
    });
    $('.hyx-hsm-left').find('dd').each(function(){
		var opId = $(this).attr("id").substring(7);
		var findTag = $("#groupP_" + opId);
    	$(this).siblings().hover(function(){
    		$(this).addClass("dd-hovers");
    	},function(){
    		$(this).removeClass("dd-hovers");
    	});
    	$(this).click(function(){
    		$(this).addClass("dd-hover");
    		$(this).siblings().removeClass("dd-hover");
    	});
		// // 查找点击
		findTag.click(function() {
			findMembers(opId)
		});
    	
    });

/*    $("body").click(function(){
            $(".sub-dep-inpu").click(function(){
             $(".manage-drop").show();
               return false;
            });
            $(".manage-drop").click(function(){
                $(".manage-drop").show();
               return false;
            });
            $(".reso-alloc-sure").click(function(){
                $(".manage-drop").hide();
               return false;
            });
            $(".owner-sour").click(function(){
             $(".manage-owner-sour").show();
             return false;
            });

            $(".manage-drop").hide();
            $(".manage-owner-sour").hide();
        });*/
		$('#deleteRecordId').click(function(){
		  deleteRecord();
		})
    	$('#addRecord').click(function(){
    		var opid = $('#opid').val();
    		if(opid==null || opid ==''){
    			window.top.iDialogMsg("提示", '请选择范例分类！');
    			return;
    		}
    		pubDivShowIframe('addRecordId',ctx+'/sys/record/uploadPage?opid='+opid,'添加范例',600,250);
		});
    
//		//播放按钮点击的时候
		$("[name='play']").click(function(){
			var v = $(this).attr("v");		
			// 获取当前行的客户姓名
			var callName = $(this).attr("callName");			
			if(callName=='' || callName==null){
				callName = $(this).attr("company");
			}
			var callNum = $(this).attr("phone");
			var httpUrl = $("#project_path").val();
			var plength = $(this).attr("timeElngth");
			var url = $(this).attr("url");
			var callId = $(this).attr("callId");
			//window.location.href = base+"/call/recordcall/newRecordCallList.do?timeElngth="+plength+"&recordUrl="+encodeURIComponent(encodeURIComponent(url,"utf-8"))+"&callId="+callId+"&callName="+encodeURI(encodeURI(callName))+"&callNum="+callNum;
			recordCallPlay(httpUrl,plength,url,callId,callName,callNum);
		});   
		
})

// 删除录音
function deleteRecord() {
	// 放弃客户
	var ids = '';
	var ownerAcc = "";
	var isOwnerAcc = true;
	var account = $('#account').val();
	var delParam = "";
	$('input[id^=chk_]').each(function(index, obj) {
		if ($(obj).is(':checked')) {
			ids += $(obj).attr('id').split('_')[1] + ',';
			delParam += $(obj).attr('delParam') + ',';
			ownerAcc = $(obj).attr('id').split('_')[2];
			if (ownerAcc != account) {
				isOwnerAcc = false;
				return false;
			}
		}
	});
	var isSys = $('#isSys').val();
	if(isSys =='0'){
		if (!isOwnerAcc) {
			window.top.iDialogMsg("提示", '只能删除自己的录音!');
			return ;
		}
	}
	if (ids.length == 0) {
		window.top.iDialogMsg("提示", '请先选择录音范例！');
		return;
	}
	ids = ids.substring(0, ids.length - 1);
	pubDivDialog("deleteRecord", "是否删除录音范例？", function() {
		$.ajax({
			url : 'delRecordReview',
			type : 'post',
			data : {
				ids : ids,delParam:delParam
			},
			dataType : 'html',
			error : function() {
				alert('网络异常，请稍后再试！')
			},
			success : function(data) {
				if (data == 0) {
					window.top.iDialogMsg("提示", '删除录音范例成功！');
					setTimeout('document.forms[0].submit()', 1000);
				} else {
					window.top.iDialogMsg("提示", '删除录音范例失败！');
				}
			}
		});
	})
}

//查看
function findMembers(opId) {
	$("#opid").val(opId);
	$("#myform").submit();
}

function setPdate(i) {
	$('#s_pstartDate').val('');
	$('#s_pendDate').val('');
	$("#oDateType").val(i);
}