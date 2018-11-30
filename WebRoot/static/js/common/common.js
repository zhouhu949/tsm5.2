/***********************************************
 * 慧营销——共同JS文件
 ***********************************************/
/**
 * 加载或刷新页面 
 * @param {Object} type 刷新类型：1:无表单条件刷新;2:带表单条件刷新; Url:请求地址;
 */
function loadPage(typeOrUrl){ 
    if (typeOrUrl == 1) { // 无表单条件刷新 
        parent.location.href = parent.location.href; 
    } else if (typeOrUrl == 2) { // 带 表单条件刷新 (注：弹出窗口使用此方法刷新主页面时要先关闭窗口)
        document.forms[0].action = location.href; 
        document.forms[0].submit(); 
    }else{ // 重定位
		parent.location.href = typeOrUrl;
    }
}

/**
 *  修改基本信息后 动态设置新的值
 * @param phone
 * @param custId
 * @param followId
 */
function callPhone2(phone,custId){
	if(phone!=null && phone !=''){
		var params = phone.split('-');
		var param = params[0] + "_" + custId;
		try{
			var paramXml = "<xml><Oparation Phone=\""+params[0]+"\" CustID=\""+custId+"\" ";
			if(followId != null && followId != '')
				paramXml+="FollowID=\""+followId+"\" ";
			paramXml+="/></xml>";
			external.OnCallXml(paramXml);
		}catch(e){
			external.OnCall(param);
		}
	}
}