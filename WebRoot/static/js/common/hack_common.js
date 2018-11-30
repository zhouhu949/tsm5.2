/***********************************************
 * 慧营销——共同JS文件
 * @author xiangli
 * @date 2013-11-28
 ***********************************************/

/****************************
*禁止backSpace键，但是可以删除文本框 ie下面可以使用，火狐暂不支持
****************************/
window.onload=function(){
	document.getElementsByTagName("body")[0].onkeydown =function(){
	//获取事件对象
	   var event = window.event || arguments[0];
	   if(event.keyCode==8){//判断按键为backSpace键
			//获取按键按下时光标做指向的element
			var elem = event.srcElement || event.currentTarget;
		    //判断是否需要阻止按下键盘的事件默认传递
		    var name = elem.nodeName;
			if(name!='INPUT' && name!='TEXTAREA' && !jQuery(elem).hasClass("barrage-reply-textarea")){
			    return _stopIt(event);
			}
		    var type_e = elem.type.toUpperCase();
			if(name=='INPUT' && (type_e!='TEXT' && type_e!='TEXTAREA' && type_e!='PASSWORD' && type_e != 'FILE')){
			    return _stopIt(event);
			}
			if(name=='INPUT' && (elem.readOnly==true || elem.disabled ==true)){
			   return _stopIt(event);
			}
		}
	}
}
function _stopIt(e){
	if(e.returnValue){
	    e.returnValue = false;
	}
	if(e.preventDefault ){
	    e.preventDefault();
	}
    return false;
}

//屏蔽右键
function stop(){
    return false;
}
document.oncontextmenu=stop;

// 屏蔽F1帮助
function help() {
   return false;
}
window.onhelp=help;


//屏蔽掉脚本错误
function onerrorFun(){
	 return true;
}
window.onerror=onerrorFun;


//屏蔽快捷键F5
function  KeyDown()
{
    if   ((window.event.altKey)   &&
          ((window.event.keyCode==37)||         //屏蔽   Alt+   方向键   ←
          (window.event.keyCode==39)))           //屏蔽   Alt+   方向键   →
          {
            event.keyCode=0;
            event.returnValue=false;
          }
    if   (event.keyCode==116)                                   //屏蔽   F5   刷新键
        {
           event.keyCode=0;
           event.returnValue=false;
        }
    //获取按键按下时光标做指向的element
	var elem = event.srcElement || event.currentTarget;
	type_e="";
	if(!elem){
		type_e = elem.type.toUpperCase();
	}
    if((type_e!=""&&type_e!='TEXTAREA') && event.keyCode==13){                      //除了TEXTAREA，屏蔽enter键
    	event.keyCode=0;
        event.returnValue=false;
    }
    return   true;
}
document.onkeydown=KeyDown;
