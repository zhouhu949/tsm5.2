$(function(){
	$("body").delegate("input[name=radioBtn]","click",function(){
		$("#commonAcc").val($(this).val());
		$("#commonName").val($(this).attr("ownerName"));
	});
	
    $(".confirm").click(function(){
    	$("#commonOwnerForm").ajaxSubmit({
    		dataType:'json',
    		error:function(){
    			window.top.iDialogMsg("提示",'网络异常！');
    		},
    		success:function(data){
    			if(data == '0'){
    				window.top.iDialogMsg("提示",'设置共有者成功！');
    				window.parent.refreshPageData(window.parent.pa.pageParameter());
    				closeParentPubDivDialogIframe('add_common_owner');
    			}else{
    				window.top.iDialogMsg("提示",'设置共有者失败！');
    			}
    		}
    	});
	});
	$(".cancle-click").click(function(){
		closeParentPubDivDialogIframe('add_common_owner');
	});
	
	$("body").delegate(".cancel-check","click",function(e){
		$("input[name=radioBtn]:checked").attr("checked",false);
		$("#commonAcc").val("");
		$("#commonName").val("");
	});
	
	var placehold=$(".placehold");//spanobj
	var placehold1=$(".placehold1");//spanobj
	var inputs=$(".shows-input-box input");//inputobj

	hidespan(inputs,placehold);
	hidespan(inputs,placehold1);
	//传入的obj是jquery对象
	function hidespan(inputobj,spanobj){
		spanobj.click(function(e){
			e.stopPropagation();
			$(this).hide();//点击input上的span隐藏span提示
			inputobj.focus();
		});
		inputobj.focus(function(){
			spanobj.hide();//input获取焦点后隐藏span提示

		});
		inputobj.blur(function(){
			if($(this).val() == ""){
				spanobj.show();
			}
		});
	};
	
	$("#searchUser").click(function(){
		initUser($("#searchText").val());
	});
	
	initUser(null);
});

function initUser(searchText){
	$.post(ctx+"/res/cust/toTransfer/getUsers",{"searchText":searchText,"queryScale":"1"},function(data){
		jsonin(data,"shows-idialog-middle");
  	}, "json");
	if(searchText==null ||searchText==''){
		$("#searchText").val('');
		$(".placehold1").show();
	}
}
function jsonin(data,classname){
	var divs=$("."+classname);
	var html='<div class="shows-middle-title">选择共有者</div>';/*<button class="cancel-check">取消选中</button>*/
//	console.log(data);
	for(var i=0;i<data.length;i++){
		html += createbox(data[i]);
	}
//	console.log(html);
	divs.html(html);
	checkRadio();
}
function createbox(data){//创建一个contbox,data 代表上面jons里面datas的数组中的每一项目
	var html = "<div class='shows-middle-cont-box' value="+data.groupName+">"+data.groupName+"<div class='shows-middle-radio-box'>";
	for(var i=0;i<data.members.length;i++){
		html += "<span><input type='radio' name='radioBtn' ownerName='"+data.members[i].memberName+"' value='"+data.members[i].memberAcc+"'>"+data.members[i].memberName+"</span>";
	}
	html += "</div></div>";
	return html;
}

function checkRadio(){
	$("input[name=radioBtn]:checked").attr("checked",false);
	$("input[name=radioBtn]").each(function(){
		var acc = $("#ownerAcc").val();
		if($(this).val()==acc) $(this).attr("checked","checked");
	});
}

