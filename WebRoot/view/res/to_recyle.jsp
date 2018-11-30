<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx }/static/css/dialog.css"><!--弹框样式-->
<script type="text/javascript">
	$(function(){

		$("#cleanCheck").on('ifChecked',function(){
			$("#isClean").val("1");
		});
		
		$("#cleanCheck").on('ifUnchecked',function(){
			$("#isClean").val("0");
		});
		
		$("#groupCheck").on('ifChecked',function(){
			$("#oldResGroup").val("1");
			$("#resGroupId").attr("disabled","disabled");
		});
		
		$("#groupCheck").on('ifUnchecked',function(){
			$("#oldResGroup").val("0");
			$("#resGroupId").removeAttr("disabled");
		});
		
		var is_submit = false;
	    $(".sure-btn").click(function(){
	    	ajaxLoading("正在回收，请稍后") ;
	    	if(!is_submit){
	    		is_submit = true;
	    		var _param = window.parent.$('#queryForm').serialize();
	    		var isClean = $("#isClean").val();
		    	var oldResGroup = $("#oldResGroup").val();
		    	var regroupId = $("#resGroupId").val();
		    	var taskId = uuid();
		    	_param+="&isClean="+isClean+"&oldResGroup="+oldResGroup+"&regroupId="+regroupId+"&taskId="+taskId;
		    	$.ajax({
		    		url:ctx+'/res/sea/recyleSource',
		    		type:'post',
		    		data:_param,
		    		dataType:'json',
		    		error:function(){is_submit = false;},
		    		success:function(data){
		    			if(data == '1'){
		    				$('#p').progressbar({
							    value: 100
							});
		    				window.top.iDialogMsg("提示","操作成功！");
							setTimeout('window.parent.loadData();closeParentPubDivDialogIframe("recyle_cust");',1000);
		    			}else if(data == '9'){
		    				window.top.iDialogMsg("提示","没有找到要回收的公海资源！");
		    				setTimeout('window.parent.loadData();closeParentPubDivDialogIframe("recyle_cust");',1000);
		    			}else{
		    				window.top.iDialogMsg("提示","操作失败！");
		    				is_submit = false;
		    			}
		    		}
		    	});
		    	//进度条轮询
		    	setTravl(taskId,3000);
	    	}
		});
		$(".cancel-btn").click(function(){
			closeParentPubDivDialogIframe('recyle_cust');
		});
		queryConditionsInit();
		
		$.ajax({
			url: ctx+"/res/group/get_res_group_json",
			type: "get",
			success: function(data){ 
				var appendCode = "";
				for(var i=0;i<data.length;i++){
					var _this_data = data[i];
					var _this_data_child = _this_data.children;
					appendCode += '<optgroup label="'+_this_data.text+'" data-id="'+_this_data.id+'">';
					if(_this_data_child.length > 0){
						for(var j=0;j<_this_data_child.length;j++){
							appendCode += '<option value="'+_this_data_child[j].id+'">'+_this_data_child[j].text+'</option>';
						}
					}
					appendCode += '</optgroup>';
				}
				$("#resGroupId").html(appendCode);
			},
			error: function(){
				console.error("加载资源分组失败");
			}
		});
	});

    function checkEmpty(selector){
        var emptyAble=true
        selector.find("input:hidden").each(function(idx,it){
            if(jQuery(it).val()!=""){
                emptyAble=false;
                return emptyAble
            }
        })
        return emptyAble;
    }

	function queryConditionsInit(){
    	var data_dt_value = $("[data-dt-value]",window.parent.document);
		var appendCode = "";
        if($("#queryText",window.parent.document).val()!=""){
            appendCode += '<div><label>'+$("[name=queryType]",window.parent.document).find("option:selected").text()+'：</label><span title="'+$("#queryText",window.parent.document).val()+'">'+$("#queryText",window.parent.document).val()+'</span></div>';
        }
		//console.log(data_dt_value);
		for(var i=0;i<data_dt_value.length;i++){
			if(!data_dt_value.eq(i).hasClass("pos0") && !data_dt_value.eq(i).hasClass("reso-sub-dep") && !checkEmpty(data_dt_value.eq(i))){
				appendCode += '<div><label>'+data_dt_value.eq(i).attr("data-dt-value")+'：</label><span title="'+data_dt_value.eq(i).find("dt").text()+'">'+data_dt_value.eq(i).find("dt").text()+'</span></div>';
			}else if(!data_dt_value.eq(i).hasClass("pos0") && data_dt_value.eq(i).hasClass("reso-sub-dep") && !checkEmpty(data_dt_value.eq(i))){
				appendCode += '<div><label>'+data_dt_value.eq(i).attr("data-dt-value")+'：</label><span title="'+data_dt_value.eq(i).find(".sub-dep-inpu").val()+'">'+data_dt_value.eq(i).find(".sub-dep-inpu").val()+'</span></div>';
			}
		}
        if($("[data-input=actionTags]",window.parent.document).find("input[name=allLabelsName]").val()!="") {
            appendCode += '<div><label>' + $("[data-input=actionTags]", window.parent.document).find(".a").text() + '</label><span title="' + $("[data-input=actionTags]", window.parent.document).find("input[name=allLabelsName]").val() + '">' + $("[data-input=actionTags]", window.parent.document).find("input[name=allLabelsName]").val() + '</span></div>';
        }
		appendCode += '<div><label>'+$("[data-input='input[name=notContactedType]']",window.parent.document).find(".a").text()+'</label><span>'+$("[data-input='input[name=notContactedType]']",window.parent.document).find(".e-hover").text()+'</span></div>';
		appendCode += '<div><label>'+$("[data-input='input[name=noteType]']",window.parent.document).find(".a").text()+'</label><span>'+$("[data-input='input[name=noteType]']",window.parent.document).find(".e-hover").text()+'</span></div>';
		appendCode += '<div><label>回收客户数：</label><span class="get-num">0</span></div>';
		$(".query-conditions").html(appendCode);
		getNum();
    }
	
	function getNum(){
		var _param = window.parent.$('#queryForm').serialize();
    	$.ajax({
    		url:ctx+'/res/sea/recyleSourceNum',
    		type:'post',
    		data:_param,
    		dataType:'json',
    		error:function(){},
    		success:function(data){
    			$(".get-num").text(data);
    		}
    	});
	}
	
	/**生成uuid*/
	function uuid() {
	    var s = [];
	    var hexDigits = "0123456789abcdef";
	    for (var i = 0; i < 36; i++) {
	        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
	    }
	    s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
	    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
	    s[8] = s[13] = s[18] = s[23] = "-";
	 
	    var uuid = s.join("");
	    uuid = uuid.replace(new RegExp('-','gm'),'');
	    return uuid;
	}
	//进度条轮询函数
function setTravl(taskId,time){
	var content = '<div class="finished-prograss">处理中...需处理总数<span class="total">0</span>,当前完成<span class="finished">0</span></div><div id="p" style="width:200px;"></div>';
	var idialogId = "process_idialog";
	$.dialog({
		title:"处理中",
		id:idialogId,
		width:250,
		height:135,
		lock:true,
		content:content,
		show:function(){
			$('#p').progressbar({
			    value: 0
			});
		}
	})
	var t=setInterval(function(){
		$.ajax({
			url:ctx+"/progress/getProgress",
			data:{
				taskId:taskId
			},
			success:function(data){
				console.log(data+new Date().getTime())
				if(data){
					if(data.status){
			    		var percent = (data.finished/ data.total).toFixed(2)*100;
			    		$('#p').progressbar({
						    value: percent
						});
			    		$(".finished-prograss>.total").text(data.total);
			    		$(".finished-prograss>.finished").text(data.finished);
			    		if(percent >= 100){//完成 成功
			    			$(".finished-prograss").html("操作成功！共操作"+data.total+"条数据");
			    			clearInterval(t);
			    			setTimeout(function(){
			    				closeIdialog(idialogId)
			    	    	},100)
			    		}
			    	}else{//失败
			    		clearInterval(t);
			    		$('#p').progressbar({
						    value: 0
						});
			    		$(".finished-prograss").html(data.errorMsg)
			    		setTimeout(function(){
			    				closeIdialog(idialogId)
			    	    	},100)
			    	}
				}
			}
		});
	},time)
}
function closeIdialog(id){
	$.dialog.get[id].remove();
}
</script>
<style>
	.lab_a.shows-labs{display:block;text-align:left;width:100%;}
</style>
</head>
<body>
	<div class='bomp-cen'>
		<input type="hidden" value="0" id="isClean" />
		<input type="hidden" value="0" id="oldResGroup" />
		<div class="query-conditions clearfix"></div>
		<div class='bomp-p w-a' style="margin-top:20px;margin-left:15px;">
			<!-- <label class='lab_a shows-labs'>回收客户数：<span class="recycleNum">0</span></label> -->
			<label class='lab_a shows-labs'>回收后客户所在分组：
			<select id="resGroupId" class='sel_a ' style="width:130px;"></select>
			</label>
		</div>
		<div class='bomp-p skin-minimal' style="width:235px;margin-left:15px;margin-top:15px;font-size:12px;">
			<input id="cleanCheck" type='checkbox' class='fl_l' /><label class='lab_b fl_l' style="padding-left:2px;">同时清空被选客户的历史联系信息</label>
		</div>
		<div class='bomp-p skin-minimal' style="width:235px;margin-left:15px;margin-top:5px;margin-bottom:10px;font-size:12px;">
			<input id="groupCheck" type='checkbox' class='fl_l' /><label class='lab_b fl_l' style="padding-left:2px;">自动回收到客户原有资源组中</label>
		</div>
 		<p class='bomp_tit_c fl_l' style="width:85%;margin:0px 0 0px 15px;"><label class="bomp-red fl_l" style="display:inline-block;width:100%;height:20px;line-height:20px;">温馨提示：</label><span class="fl_l" style="display:inline-block;width:100%;line-height:20px;">确定后，系统将当前查询条件下的<span class="get-num">0</span>条客户自动回收到【待分配资源】中！</span></p>
		<div class='bomb-btn'>
			<label class='bomb-btn-cen'>
				<a href="javascript:;" class="com-btna bomp-btna com-btna-sty sure-btn fl_l"><label>确定</label></a>
				<a href="javascript:;" class="com-btna bomp-btna cancel-btn fl_l"><label>取消</label></a>
			</label>
		</div>
	</div>
</body>
</html>
