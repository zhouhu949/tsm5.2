var checkBox = null;
var commitFlag = true;


function addToPlanFn(){
	commitFlag = false;
	$.post(ctx+"/plan/day/resource/insert",{"custIdsStr":checkBox.ids.toString(),"pageType":pageType,"dateTime":dateTime},function(data){
		if(data.status){
			window.parent.refreshRight();
			setTimeout('closeParentPubDivDialogIframe("resourceAddWindow");',10);
		}else{
			alert(data.errorMsg);
		}
	});
} 

function resGroup(groupId,groupName){
	$("#groupId").val(groupId);
	$("#groupName").val(groupName);
}

function button_bind(){
	$('.demoBtn_a').click(function(){
		if(commitFlag && checkBox.getIds()!=null){
			pubDivDialog("cfmAddToPlan","确定要加入计划吗？",addToPlanFn);
		}
	});	
}

function init(){
	checkBox= new CheckBox("checkAll","chk_","");
	button_bind();
}

$(document).ready(function() {
	init();
});
