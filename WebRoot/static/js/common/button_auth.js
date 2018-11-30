$(function(){
	$("[btn-type=auth]").each(function(index,element){
		var _this = $(element);
		var auth_id = _this.attr("auth_id");
		var is_show = true;
		if(window.parent.shrioAuthObj){
			is_show = window.parent.shrioAuthObj(auth_id);
		}else if(window.shrioAuthObj){
			is_show = window.shrioAuthObj(auth_id);
		}else if(window.top.shrioAuthObj){
			is_show = window.top.shrioAuthObj(auth_id);
		}else{
			is_show = true;
		}
		if(_this.hasClass("hidden-element")){
			is_show ? _this.css("visibility","visible"):_this.css("visibility","hidden");
		}else{
			is_show ? _this.show():_this.hide();
		}
	});

	isAuthority("data-authority");
});

function isAuthority(dataAuthorityName){
	$("["+dataAuthorityName+"]").each(function(index,element){
		var _this = $(element);
		var authority = _this.attr(dataAuthorityName);
		var is_show = true;
		if(window.parent.shrioAuthObj){
			is_show = window.parent.shrioAuthObj(authority);
		}else if(window.shrioAuthObj){
			is_show = window.shrioAuthObj(authority);
		}else if(window.top.shrioAuthObj){
			is_show = window.top.shrioAuthObj(authority);
		}else{
			is_show = true;
		}
		if(_this.hasClass("hidden-element")){
			is_show ? _this.css("visibility","visible"):_this.css("visibility","hidden");
		}else{
			is_show ? _this.show():_this.hide();
		}
	});
}
