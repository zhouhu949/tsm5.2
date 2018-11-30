var step = 0;
//var isSearch = false;//是否搜索   搜索后点击上一步下一步重新加载
$(function(){
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
		})
		inputobj.focus(function(){
			spanobj.hide();//input获取焦点后隐藏span提示

		})
		inputobj.blur(function(){
			if($(this).val() == ""){
				spanobj.show();
			}
		})
	};
	
	$("#searchUser").click(function(){
		initUser($("#searchText").val());
	});
	
	initUser(null);
})

function initUser(searchText){
	$.post(ctx+"/res/cust/toTransfer/getUsers",{"searchText":searchText},function(data){
		jsonin(data,"shows-idialog-middle");
  	}, "json");
	if(searchText==null ||searchText==''){
		$("#searchText").val('');
		$(".placehold1").show();
	}
}
function jsonin(data,classname){
	var divs=$("."+classname);
	var html="<div class=\"shows-middle-title\">选择"+(step==0?'移交人':'接收人')+"</div>";
	for(var i=0;i<data.length;i++){
		html += createbox(data[i]);
	}
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
		var acc = step==0 ?$("#moveAcc").val():$("#recAcc").val();
		if($(this).val()==acc) $(this).attr("checked","checked");
	});
}
