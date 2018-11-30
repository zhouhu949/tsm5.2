var checkBox = null;
var commitFlag = true;

function addToPlanFn(){
	commitFlag = false;
	$.post(ctx+"/plan/day/willcust/insert",{"custIdsStr":checkBox.ids.toString(),"dateTime":dateTime},function(data){
		if(data.status){
			window.parent.refreshRight();
			setTimeout('closeParentPubDivDialogIframe("willcustAddWindow");',10);
		}else{
			alert(data.errorMsg);
		}
	});
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
