$(document).ready(function() {
	  //成员角色唯一性验证
    $("#roleName").blur(function(){
    	  var roleName_value=$("#roleName").val();
    	  $("#isRoleName").val("false");
		  if(roleName_value!=""&&roleName_value!=null&&roleName_value!=roleName){
    		  //校验角色名称
    		  $.ajax({
	                url: "check_unitRoleName.do",
	                type: "post",
	                data:{
	                	"roleName":roleName_value
	                },	
	                dataType: "text",
	                contentType:"application/x-www-form-urlencoded",
	                success: function (data) {
	                	var d=escape(data);
	                    if(d.indexOf("yes")!=-1){
	                        $("#isRoleName").val("true");
	                    }
	                },
	                error: function (XMLHttpRequest, textStatus,  errorThrown) {
	                    dialogMsg(-1,'验证出错了！！！');
	                    return;
	                }
	          });
		  }else{
			  $("#isRoleName").val("true");
		  }
    });
    
	//全选
	$("#allChecked").click(function(){
		 $("input[name='menu']").attr("checked", true); 
	});
	
	
	//全不选
    $("#allClear").click(function(){
    	 $("input[name='menu']").attr("checked", false); 
	});
    
    //保存按钮
    $("#btn_submit").click(function(){
    	  var roleId_value=$("#roleId").val();
    	  var roleName_value=$("#roleName").val();
    	  alert(roleName_value);
    	  if(roleName_value==""||roleName_value==null){
    		  dialogMsg(1,'请输入角色名称');
    		  return;
    	  }
    	  var ids=getAllCheckBoxValuesAndSnames();
    	  if($("#isRoleName").val() != "true"){
    		  dialogMsg(1,'该角色名称已经存在');
    		  return;
    	  }else if(ids==""){
              dialogMsg(1,'请至少选择一个菜单');
              return;
          }
    	  //验证时间控制
    	  var k=check_inputTime();
    	  if(k>0){
    		  return;
    	  }
    	  
    	  $("#ids").val(ids);
    	  $("#myform").submit();
    });
    
    // 左侧角色列表处理
	$("li[id^='role_']").each(function() {
		var roleId = $(this).attr("id").substring(5);
		var deleteTag = $("#del_"+roleId);
		
		$(this).mouseenter(function(){
            deleteTag.attr("class" ,'delete'); 
		}).mouseleave(function(){
            deleteTag.attr("class" ,'');
		});
	});
	
	//是否启用通讯功能
	$("#isCall").click(function(){
		var isPhones=$("#isCall").is(":checked");
		
		removeAttr();
		setAttr("checked",isPhones);
		
		if(isPhones){
			$("#isPhones").val("1");
		}else{
			setAttr("disabled","disabled");
			$("#isPhones").val("0");
		}
	});
	
});

function setAttr(name,value){
	for(var i=0;i<7;i++){
		$("input[name='roleTimeControls["+i+"].workDay']").attr(name,value);
	}
}

function removeAttr(){
	for(var i=0;i<7;i++){
		$("input[name='roleTimeControls["+i+"].workDay']").removeAttr("disabled");
	}
}

//验证工作时间限制的录入时间
function check_inputTime(){
	  var k=0;
	  $(".day_check1").each(function() {
		    var id=$(this).attr("id").substring(7);
			var isChecked=$("input[name='roleTimeControls["+id+"].workDay']").is(":checked");
			var startTime_value="";
			var endTime_value="";
			if(isChecked){
				startTime_value=$("#startTime"+id).val();
				endTime_value=$("#endTime"+id).val();
				if(startTime_value==""){
					$("#error_span"+id).html("请录入开始时间");
					k++;
					return;
				}else if(endTime_value==""){
					$("#error_span"+id).html("请录入结束时间");
					k++;
					return;
				}else if(startTime_value!=""||endTime_value!=""){
					$("#error_span"+id).html("");
				}
			}
	  });
	  return k;
}


//删除应用角色
function del_roleId(roleId){
    var del=function(){
		window.location.href="del_UnitRoleMenu.do?roleId="+roleId;
	}
    dialogMsg(2,'',del);
}


//获取所有选中的menu节点
function getAllCheckBoxValuesAndSnames() {
	var returns = "";
	$("input[name='menu']").each(function(index) {
		if ($(this).attr("checked")) {
			var appid = $(this).val();
			if (appid && appid != null) {
				returns += appid + ",";
			}
		}
	});
	return returns;
}


//父节点
function parentClick(id){
     var checked=$("#"+id).is(":checked");
     var len=id.indexOf("_");
     var str=id.substring(6,len);
     $(":checkbox").each(function(index) {
		if ($(this).attr("id").indexOf(str)!=-1) {
		    $(this).attr("checked",checked);
		}
	 });
}

//菜单节点
function menuClick(id,parent){
    var checked=$("#"+id).is(":checked");
    var checkedValue=$("#"+id).val();
    if(checked){
    	$("#"+parent).attr("checked",checked);
    }else{      //导航不选中时，按钮也不选中
    	$(":checkbox").each(function(index) {
    		var parentMenu=this.getAttribute("parentmenu"); 
    	    if(checkedValue==parentMenu){
    	    	 $(this).attr("checked",false);
    	    }  
    	});
    }
}

//子节点
function sonClick(id,parent,menu){
    var checked=$("#"+id).is(":checked");
    if(checked){
	    $("#"+parent).attr("checked",checked);
	    $("#"+menu).attr("checked",checked);
    }
}

//全选/全不选
function checkAll(id,bool,num){
	for(var i=0;i<num;i++){
		$("#"+id+"_"+i).attr("checked",bool);
	}
}

//拼接权限字符串
function appendStr(id,num){
	var result="";
	for(var i=0;i<num;i++){
		if($("#"+id+"_"+i).is(":checked")){
			var id_value=$("#"+id+"_"+i).val();
			if(id_value!=""&&id_value!=null){
			    result+=$("#"+id+"_"+i).val()+",";
			}
		}
	}
	return result;
}
