$(function(){
	$(".href-more").on("click",function(e){
		var _this = $(this);
		var custId = _this.attr("data-custId");
		var custName = _this.attr("data-custName") || '客户卡片';
		window.top.addTab(ctx+"/cust/card/custInfoCard?custId="+custId,custName);
	});
	 $(".contact_note").click(function(){
		var $_this = $(this);
		var title = $_this.parent().attr("title");
		var text = $_this.text();
		if(title.length >= 50){
	    	text = title.substr(0,50) + '...';
	    }
		$_this.html('<textarea rows="4" readonly>'+title+'</textarea>');
		$_this.children().focus();
		$_this.children().focusout(
			function(){
				$_this.text(text);
			}
		);
	});


    /********** 屏蔽手机或固话中间几位  *********/
    var idReplaceWord = $("#idReplaceWord").val();
    console.log("idReplaceWord",idReplaceWord)
	if(idReplaceWord==1){
		$("label[name='detailTelphone']").each(function(idx,obj){
				var phone = $(obj).text();
				if(phone != null && phone != ''){
					replaceWord(phone,$(obj));
					replaceTitleWord(phone,$(obj));
				}
			});
		$("label[name='mobilephone']").each(function(idx,obj){
			var phone = $(obj).text();
			console.log(phone)
			if(phone != null && phone != ''){
				replaceWord(phone,$(obj));
				replaceTitleWord(phone,$(obj));
			}
		});

	}
	/********** 屏蔽手机或固话中间几位  *********/

	//播放按钮点击的时候
	$("[name='icon-play']").click(function(){
		var v = $(this).attr("v");
		var callState = $(this).attr("callState");//呼叫类型
		// 获取当前行的客户姓名
		var callName = $(this).attr("custName");
		var callNum = "";
		// 如果单位姓名为空就获取号码
		if(callState == "1"){ //1:呼入，2:呼出
				callNum = $(this).attr("callerNum");// 主叫号码
		}else{
				callNum = $(this).attr("calledNum");// 被叫号码
		}
		var httpUrl = $("#project_path").val();
		var plength = $(this).attr("timeLength");
		var url = $(this).attr("url");
		var callId = $(this).attr("callId");
		//window.location.href = httpUrl+"/call/callPlay.do?timeElngth="+plength+"&recordUrl="+encodeURIComponent(encodeURIComponent(url,"utf-8"))+"&callId="+callId+"&callName="+encodeURI(encodeURI(callName))+"&callNum="+callNum;
		recordCallPlay(httpUrl,plength,url,callId,callName,callNum);
	});

	   /*客户跟进tab*/
    $('.hyx-cfu-tab').find('li').click(function(){
        $(this).addClass('select').siblings('li').removeClass('select');
        var id = $(this).attr("id");
        if(id == "follow_"){
        	$("#cust_infor").hide();
        	$("#follow_show").show();
        }else if(id == "cust_"){ // 通话记录
        	$("#cust_infor").show();
        	$("#follow_show").hide();
        }
    });

    /*log加载*/
    $('.timeline').each(function(i,item){
        var load_num = 6,i_num = 6,forLiLen = 0,
        load_len = $(item).find('.li-load').length;
        if($(item).parent().hasClass('hyx-cfu-timeline') == true){
            load_num = 6;
            i_num = 4;
        }
        $(item).find('.li-load').css({'display':'none'});
        for(var i=0;i<load_num;i++){
            $(item).find('.li-load').eq(i).fadeIn(500);
        }
        function limitLi(){
            var timer = setTimeout(function(){
                if(i_num >= load_len){
                        clearTimeout(timer);
                }
                if(load_len - i_num < load_num){
                        forLiLen = load_len;
                }else{
                        forLiLen = i_num + load_num;
                }
                if(forLiLen <= load_len){
                        for(var i=i_num;i<forLiLen;i++){
                                $(item).find('.li-load').eq(i).fadeIn(1000);
                        }
                }
                i_num = i_num + load_num;
            },200);
        }

        $('.hyx-wlm-cent-timeline').scroll(function(){
            if($(this)[0].scrollTop + $(this).height() >= $(this)[0].scrollHeight){
                limitLi();
            }
         });



        /*下拉框部分*/
      $('.icon-conte-list').click(function(){
            $(this).find('.drop').fadeToggle();
        });
    });
    /*联系小记字数限制*/
    $('.cfu-list-note').each(function(){
    	var $_this = $(this).find('.contact_note');
    	var text = $_this.text();
    	if(text.length > 100){
	    	$_this.text(text.substr(0,100) + '...');
	    	$_this.append('<span style="width:20px;height:20px;float:none;cursor:pointer;" class="icon-search-look note_detail" title="查看详情"></span>');
	    	$_this.find(".note_detail").click(function(){
	    		viewDetailDivDialog("note_detail_dialog",text);
		    	$("#note_detail_dialog .i-content").css({
			    	"height":"405px",
			    	"overflow-y":"auto",
			    });
	    	});
	    }
    });

    // 发送短信
	$('.demoBtn_b').click(function(){
		var phone = $(this).attr("phone");
		var name = $(this).attr("name");
		toSmsSendPage(name,phone);
	});

	//编辑
    $("#editCustBtn").live('click',function(){
    	
    	var heights = $("#list_right").innerHeight();
    	var custId = $(this).attr("custid");
    	var getTimestamp=new Date().getTime();
    	pubDivShowIframeNoLock('edit_'+custId,ctx+'/res/cust/toEditRes?custId='+custId+'&v='+getTimestamp+'&dayPlanflag=1','信息编辑',270,heights);
    });
    //新增跟进
    $(".new-daily-plan-rightbottom-editbtn-follow").on("click",function(e){
    	e.stopPropagation();
    	var $this = $(this)
    	var heights = $("#list_right").innerHeight();
    	var getTimestamp=new Date().getTime();
    	var custId = $this.data("custid");
    	pubDivShowIframeNoLock('dayPlan_add_follow_',ctx+'/cust/card/custFollowPage?custId='+custId+'&v='+getTimestamp+'&dayPlan=1','新增跟进',270,heights);
    })
    
    

    //客户资料附加项
   appendCustData();
});

function appendCustData(){
	var html = "";
    $.ajax({
    	url:$("#url").val(),
    	data:{
    		custId:$("#custId").val()
    	},
    	dataType:'json',
    	success:function(data){
    		var custData = data.custData;
    		for(var i = 0 ; i < custData.length ; i++){
    			if(custData[i].fieldCode == "sex"){
    				html += '<div class="new-daily-plan-right-item" title="'+custData[i].fieldName +'：'+ (custData[i].fieldValue == 1 ? "男":"女") +'"><label class="new-daily-plan-right-item-left">'+custData[i].fieldName+'：</label>	'+(custData[i].fieldValue == 1 ? "男":"女") +'</div>'
    			}else{
    				html += '<div class="new-daily-plan-right-item" title="'+custData[i].fieldName +'：'+ custData[i].fieldValue +'"><label class="new-daily-plan-right-item-left">'+custData[i].fieldName+'：</label>	'+custData[i].fieldValue+'</div>'
    			}
    		}
    		$("#cust_infor>.hyx-cfu-card>.append-div").html(html);
    	}
    })
}

// 打电话公共方法
function followCallPhone(phone,resCustId,name,status,detailName,custType,company,isConcat,saleProcessId,saleProcessName){
	var arrays = new Array();
	arrays.push("\"custId\":\""+resCustId+"\"");
	arrays.push("\"custName\":\""+name+"\"");
	arrays.push("\"custState\":\""+status+"\"");
	arrays.push("\"custType\":\""+custType+"\"");
	if($.trim(detailName)!=""){
		arrays.push("\"define1\":\""+detailName+"\"");
	}
	if($.trim(company)!=""){
		arrays.push("\"define3\":\""+company+"\"");
	}
	if(saleProcessName && saleProcessId){
		arrays.push("\"saleProcessId\":\""+saleProcessId+"\"");
		arrays.push("\"saleProcessName\":\""+saleProcessName+"\"");
	}
	window.top.custCallPhone(phone,arrays,resCustId,isConcat);
}