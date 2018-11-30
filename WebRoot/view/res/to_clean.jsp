<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx }/static/css/dialog.css"><!--弹框样式-->
<script type="text/javascript">
	$(function(){
	    var is_submit = false;
		$(".sure-btn").click(function(){
			if(!is_submit){
				is_submit = true;
		    	var code = $("#v_code").val();
		    	if(code == null || code == ''){
		    		window.top.iDialogMsg("提示","请填写验证码!");
		    		is_submit = false;
		    		return;
		    	}
		    	
		    	var taskId = uuid();
		    	
		    	var _param = window.parent.$('#queryForm').serialize();
		    	_param+="&checkCode="+code+"&taskId="+taskId;
		    	
		    	ajaxLoading("正在删除数据，请稍后");
				$(".datagrid-mask,.datagrid-mask-msg").ajaxStop(function() {
					$(this).remove();
				});
		    	
		    	$.ajax({
		    		url:ctx+'/res/sea/custClean',
		    		type:'post',
		    		data:_param,
		    		dataType:'json',
		    		error:function(){is_submit = false;reloadcode();},
		    		success:function(data){
		    			if(data == '1'){
		    				$('#p').progressbar({
							    value: 100
							});
		    				window.top.iDialogMsg("提示","操作成功！");
							setTimeout(function(){
								//window.parent.document.loadData();
								closeParentPubDivDialogIframe("clean_cust");
								window.parent.loadData();
							},1000)
		    			}else if(data == '9'){
		    				closeIdialog("process_idialog")
		    				window.top.iDialogMsg("提示","没有找到要清空的公海资源！");
		    				setTimeout(function(){
		    					window.parent.loadData();
		    					closeParentPubDivDialogIframe("clean_cust");
		    				},1000)
		    			}else if(data == '10'){
		    				closeIdialog("process_idialog")
		    				window.top.iDialogMsg("提示","验证码输入错误！");
		    				is_submit = false;
		    				reloadcode();
		    			}else{
		    				closeIdialog("process_idialog")
		    				window.top.iDialogMsg("提示","操作失败！");
		    				is_submit = false;
		    				reloadcode();
		    			}
		    		}
		    	})
		    	//进度条轮询
		    	setTravl(taskId,3000);
			}
		});
		$(".cancel-btn").click(function(){
			closeParentPubDivDialogIframe('clean_cust');
		});
		
		queryConditionsInit();
	});
	
	//重新加载验证码
	function reloadcode(){
	    var verify=document.getElementById("code");
	    verify.setAttribute("src","/getCaptcha?t="+Math.random());
    }
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
		if($("[data-input=actionTags]",window.parent.document).find("input[name=allLabelsName]").val()!=""){
            appendCode += '<div><label>'+$("[data-input=actionTags]",window.parent.document).find(".a").text()+'</label><span title="'+$("[data-input=actionTags]",window.parent.document).find("input[name=allLabelsName]").val()+'">'+$("[data-input=actionTags]",window.parent.document).find("input[name=allLabelsName]").val()+'</span></div>';
		}
		appendCode += '<div><label>'+$("[data-input='input[name=notContactedType]']",window.parent.document).find(".a").text()+'</label><span>'+$("[data-input='input[name=notContactedType]']",window.parent.document).find(".e-hover").text()+'</span></div>';
		appendCode += '<div><label>'+$("[data-input='input[name=noteType]']",window.parent.document).find(".a").text()+'</label><span>'+$("[data-input='input[name=noteType]']",window.parent.document).find(".e-hover").text()+'</span></div>';
		appendCode += '<div><label>清空条数：</label><span class="get-num">0</span></div>';
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
</head>
<body>
	<div class='bomp-cen'>
		<div class="query-conditions clearfix">
			
		</div>
		<div class="bomp-tip-a">
			<label class="tip-a fl_l"></label>
			<span class="sp-a fl_l">确定清空筛选条件下所展示的公海客户吗？</span>
		</div>
		<p class='bomp_tit_c fl_l' style="margin:-8px 0 0 103px;"><label class="bomp-red">温馨提示：</label>清空后将无法恢复！</p>
		<div class='bomp-p' style="width:355px;padding-left:6px;">
			<label class='lab_a fl_l'>验证码：</label><input type='text' id="v_code" value='' class='ipt_a fl_l' />
			<img src="${ctx}/getCaptcha" id="code" onclick="reloadcode()" width="66" height="24" style="display:inline-block;float:left;margin:5px 0 0 5px;" />
		</div>
		<div class='bomb-btn' style="margin-top:35px;">
			<label class='bomb-btn-cen'>
				<a href="javascript:;" class="com-btna bomp-btna com-btna-sty sure-btn fl_l"><label>确定</label></a>
				<a href="javascript:;" class="com-btna bomp-btna cancel-btn fl_l"><label>取消</label></a>
			</label>
		</div>
	</div>
</body>
</html>
