/**
 *  屏蔽手机或固话的中间几位，用 ‘*’ 替代
 *  
 */
  function replaceWord(str,jobject){
	  str = str.replace("-","a");
	  //1，验证手机
		var mobilePattern =/^(0?((13[0-9]{1})|(15[^4,\D])|(18[0-9]{1})|(14[0-9]{1})|(17[0-9]{1}))+\d{8})$/;
		var telPattern = /^((0(10|2[^6,\D]|[3-9]\d{2}))-?[1-9]\d{6,7}(-\d{1,8})?)$/;
		var isMobileFomat = mobilePattern.test(str);
		var isTelFomat = telPattern.test(str);
		if(str == '' || str == null || str=='undefined'){
			return ;
		}else if(str.length<7){
			return ;
		}else if(str.length ==7){
			jobject.text(str.replace(/(\d{2})\d{4}(\d{1})/, '$1****$2'));
		}else if(str.length ==8 ){
			jobject.text(str.replace(/(\d{2})\d{4}(\d{2})/, '$1****$2'));
		}else if(str.length ==9 ){
			jobject.text(str.replace(/(\d{3})\d{4}(\d{2})/, '$1****$2'));
		}else if(isMobileFomat){
			jobject.text(str.replace(/(\d{3})\d{4}(\d{4})/, '$1****$2'));
		}else if( str.length ==11 ){
			jobject.text(str.replace(/(\d{4})\d{4}(\d{3})/, '$1****$2'));
		}else if(str.length ==12){
			jobject.text(str.replace(/(\d{4})\d{4}(\d{4})/, '$1****$2'));
		}else if(str.length ==10){
			jobject.text(str.replace(/(\d{3})\d{4}(\d{3})/, '$1****$2'));
		}else if(str.length ==13){
			jobject.text(str.replace(/(\d{4})\d{4}(\d{5})/, '$1****$2'));
		}else{
			var firstStr = str.substring(0,14);
			var secondStr = str.substring(14);
			str = firstStr.replace(/(\d{4})\w{4}(\w{6})/, '$1****$2') + secondStr;
			jobject.text(str.replace("a","-"));
		}		
  }
  
   /**
   *  屏蔽手机或固话的中间几位，用 ‘*’ 替代
   *  
   */
    function replaceTitleWord(str,jobject){
  	  	str = str.replace("-","a");
  	  	//1，验证手机
  		var mobilePattern =/^(0?((13[0-9]{1})|(15[^4,\D])|(18[0-9]{1})|(14[0-9]{1})|(17[0-9]{1}))+\d{8})$/;
  		var telPattern = /^((0(10|2[^6,\D]|[3-9]\d{2}))-?[1-9]\d{6,7}(-\d{1,8})?)$/;
  		var isMobileFomat = mobilePattern.test(str);
  		var isTelFomat = telPattern.test(str);
  		if(str == '' || str == null || str=='undefined'){
  			return ;
  		}else if(str.length<7){
  			return ;
  		}else if(str.length ==7){
  			jobject.attr('title',str.replace(/(\d{2})\d{4}(\d{1})/, '$1****$2'));
  		}else if(str.length ==8 ){
  			jobject.attr('title',str.replace(/(\d{2})\d{4}(\d{2})/, '$1****$2'));
  		}else if(str.length ==9 ){
  			jobject.attr('title',str.replace(/(\d{3})\d{4}(\d{2})/, '$1****$2'));
  		}else if(isMobileFomat){
  			jobject.attr('title',str.replace(/(\d{3})\d{4}(\d{4})/, '$1****$2'));
  		}else if( str.length ==11 ){
  			jobject.attr('title',str.replace(/(\d{4})\d{4}(\d{3})/, '$1****$2'));
  		}else if(str.length ==12){
  			jobject.attr('title',str.replace(/(\d{4})\d{4}(\d{4})/, '$1****$2'));
  		}else if(str.length ==10){
  			jobject.attr('title',str.replace(/(\d{3})\d{4}(\d{3})/, '$1****$2'));
  		}else if(str.length ==13){
  			jobject.attr('title',str.replace(/(\d{4})\d{4}(\d{5})/, '$1****$2'));
  		}else{
  			var firstStr = str.substring(0,14);
  			var secondStr = str.substring(14);
  			str = firstStr.replace(/(\d{4})\w{4}(\w{6})/, '$1****$2') + secondStr;
  			jobject.attr('title',str.replace("a","-"));
  		}		
    }
    
    //替换输入框值
    function replaceWordInput(str,jobject,isFomatCheck){
  	  str = str.replace("-","a");
  	  //1，验证手机
  		var mobilePattern =/^(0?((13[0-9]{1})|(15[^4,\D])|(18[0-9]{1})|(14[0-9]{1})|(17[0-9]{1}))+\d{8})$/;
  		var telPattern = /^((0(10|2[^6,\D]|[3-9]\d{2}))-?[1-9]\d{6,7}(-\d{1,8})?)$/;
  		var isMobileFomat = mobilePattern.test(str);
  		if(isFomatCheck<0){
  	  		var isTelFomat = telPattern.test(str);
  	  		if(str == '' || str == null || str=='undefined'){
  	  			return ;
  	  		}else if(str.length<7){
  	  			return ;
  	  		}else if(str.length ==7){
  	  			jobject.val(str.replace(/(\w{2})\w{4}(\w{1})/, '$1****$2'));
  	  		}else if(str.length ==8 ){
  	  			jobject.val(str.replace(/(\w{2})\w{4}(\w{2})/, '$1****$2'));
  	  		}else if(str.length ==9 ){
  	  			jobject.val(str.replace(/(\w{3})\w{4}(\w{2})/, '$1****$2'));
  	  		}else if(isMobileFomat){
  	  			jobject.val(str.replace(/(\w{3})\w{4}(\w{4})/, '$1****$2'));
  	  		}else if( str.length ==11 ){
  	  			jobject.val(str.replace(/(\w{4})\w{4}(\w{3})/, '$1****$2'));
  	  		}else if(str.length ==12){
  	  			jobject.val(str.replace(/(\w{4})\w{4}(\w{4})/, '$1****$2'));
  	  		}else if(str.length ==10){
  	  			jobject.val(str.replace(/(\w{3})\w{4}(\w{3})/, '$1****$2'));
  	  		}else if(str.length ==13){
  	  			jobject.val(str.replace(/(\w{4})\w{4}(\w{5})/, '$1****$2'));
  	  		}else{
  	  			var firstStr = str.substring(0,14);
  	  			var secondStr = str.substring(14);
  	  			str = firstStr.replace(/(\w{4})\w{4}(\w{6})/, '$1****$2') + secondStr;
  	  			jobject.val(str.replace("a","-"));
  	  		}	
  		}else{
  	  		var isTelFomat = telPattern.test(str);
  	  		if(str == '' || str == null || str=='undefined'){
  	  			return ;
  	  		}else if(str.length<7){
  	  			return ;
  	  		}else if(str.length ==7){
  	  			jobject.val(str.replace(/(\d{2})\d{4}(\d{1})/, '$1****$2'));
  	  		}else if(str.length ==8 ){
  	  			jobject.val(str.replace(/(\d{2})\d{4}(\d{2})/, '$1****$2'));
  	  		}else if(str.length ==9 ){
  	  			jobject.val(str.replace(/(\d{3})\d{4}(\d{2})/, '$1****$2'));
  	  		}else if(isMobileFomat){
  	  			jobject.val(str.replace(/(\d{3})\d{4}(\d{4})/, '$1****$2'));
  	  		}else if( str.length ==11 ){
  	  			jobject.val(str.replace(/(\d{4})\d{4}(\d{3})/, '$1****$2'));
  	  		}else if(str.length ==12){
  	  			jobject.val(str.replace(/(\d{4})\d{4}(\d{4})/, '$1****$2'));
  	  		}else if(str.length ==10){
  	  			jobject.val(str.replace(/(\d{3})\d{4}(\d{3})/, '$1****$2'));
  	  		}else if(str.length ==13){
  	  			jobject.val(str.replace(/(\d{4})\d{4}(\d{5})/, '$1****$2'));
  	  		}else{
  	  			var firstStr = str.substring(0,14);
  	  			var secondStr = str.substring(14);
  	  			str = firstStr.replace(/(\d{4})\d{4}(\d{6})/, '$1****$2') + secondStr;
  	  			jobject.val(str.replace("a","-"));
  	  		}	
  		}
    } 

    //替换输入框值
    function replaceTitleInput(str,jobject,isFomatCheck){
  	  str = str.replace("-","a");
  	  //1，验证手机
  		var mobilePattern =/^(0?((13[0-9]{1})|(15[^4,\D])|(18[0-9]{1})|(14[0-9]{1})|(17[0-9]{1}))+\d{8})$/;
  		var telPattern = /^((0(10|2[^6,\D]|[3-9]\d{2}))-?[1-9]\d{6,7}(-\d{1,8})?)$/;
  		var isMobileFomat = mobilePattern.test(str);
  		if(isFomatCheck<0){
  	  		var isTelFomat = telPattern.test(str);
  	  		if(str == '' || str == null || str=='undefined'){
  	  			return ;
  	  		}else if(str.length<7){
  	  			return ;
  	  		}else if(str.length ==7){
  	  			jobject.attr('title',str.replace(/(\w{2})\w{4}(\w{1})/, '$1****$2'));
  	  		}else if(str.length ==8 ){
  	  			jobject.attr('title',str.replace(/(\w{2})\w{4}(\w{2})/, '$1****$2'));
  	  		}else if(str.length ==9 ){
  	  			jobject.attr('title',str.replace(/(\w{3})\w{4}(\w{2})/, '$1****$2'));
  	  		}else if(isMobileFomat){
  	  			jobject.attr('title',str.replace(/(\w{3})\w{4}(\w{4})/, '$1****$2'));
  	  		}else if( str.length ==11 ){
  	  			jobject.attr('title',str.replace(/(\w{4})\w{4}(\w{3})/, '$1****$2'));
  	  		}else if(str.length ==12){
  	  			jobject.attr('title',str.replace(/(\w{4})\w{4}(\w{4})/, '$1****$2'));
  	  		}else if(str.length ==10){
  	  			jobject.attr('title',str.replace(/(\w{3})\w{4}(\w{3})/, '$1****$2'));
  	  		}else if(str.length ==13){
  	  			jobject.attr('title',str.replace(/(\w{4})\w{4}(\w{5})/, '$1****$2'));
  	  		}else{
  	  			var firstStr = str.substring(0,14);
  	  			var secondStr = str.substring(14);
  	  			str = firstStr.replace(/(\w{4})\w{4}(\w{6})/, '$1****$2') + secondStr;
  	  			jobject.attr('title',str.replace("a","-"));
  	  		}	
  		}else{
  	  		var isTelFomat = telPattern.test(str);
  	  		if(str == '' || str == null || str=='undefined'){
  	  			return ;
  	  		}else if(str.length<7){
  	  			return ;
  	  		}else if(str.length ==7){
  	  			jobject.attr('title',str.replace(/(\d{2})\d{4}(\d{1})/, '$1****$2'));
  	  		}else if(str.length ==8 ){
  	  			jobject.attr('title',str.replace(/(\d{2})\d{4}(\d{2})/, '$1****$2'));
  	  		}else if(str.length ==9 ){
  	  			jobject.attr('title',str.replace(/(\d{3})\d{4}(\d{2})/, '$1****$2'));
  	  		}else if(isMobileFomat){
  	  			jobject.attr('title',str.replace(/(\d{3})\d{4}(\d{4})/, '$1****$2'));
  	  		}else if( str.length ==11 ){
  	  			jobject.attr('title',str.replace(/(\d{4})\d{4}(\d{3})/, '$1****$2'));
  	  		}else if(str.length ==12){
  	  			jobject.attr('title',str.replace(/(\d{4})\d{4}(\d{4})/, '$1****$2'));
  	  		}else if(str.length ==10){
  	  			jobject.attr('title',str.replace(/(\d{3})\d{4}(\d{3})/, '$1****$2'));
  	  		}else if(str.length ==13){
  	  			jobject.attr('title',str.replace(/(\d{4})\d{4}(\d{5})/, '$1****$2'));
  	  		}else{
  	  			var firstStr = str.substring(0,14);
  	  			var secondStr = str.substring(14);
  	  			str = firstStr.replace(/(\d{4})\d{4}(\d{6})/, '$1****$2') + secondStr;
  	  			jobject.attr('title',str.replace("a","-"));
  	  		}	
  		}
	
    } 

    //淘客户或客户信息时
    function replaceWordRead(str,jobject){
  	  str = str.replace("-","a");
  	  //1，验证手机
  		var mobilePattern =/^(0?((13[0-9]{1})|(15[^4,\D])|(18[0-9]{1})|(14[0-9]{1})|(17[0-9]{1}))+\d{8})$/;
  		var telPattern = /^((0(10|2[^6,\D]|[3-9]\d{2}))-?[1-9]\d{6,7}(-\d{1,8})?)$/;
  		var isMobileFomat = mobilePattern.test(str);
  		var isTelFomat = telPattern.test(str);
  		if(str == '' || str == null || str=='undefined'){
  			return ;
  		}else if(str.length<7){
  			return ;
  		}else if(str.length ==7){
  			jobject.text(str.replace(/(\w{2})\w{4}(\w{1})/, '$1****$2'));
  		}else if(str.length ==8 ){
  			jobject.text(str.replace(/(\w{2})\w{4}(\w{2})/, '$1****$2'));
  		}else if(str.length ==9 ){
  			jobject.text(str.replace(/(\w{3})\w{4}(\w{2})/, '$1****$2'));
  		}else if(isMobileFomat){
  			jobject.text(str.replace(/(\w{3})\w{4}(\w{4})/, '$1****$2'));
  		}else if( str.length ==11 ){
  			jobject.text(str.replace(/(\w{4})\w{4}(\w{3})/, '$1****$2'));
  		}else if(str.length ==12){
  			jobject.text(str.replace(/(\w{4})\w{4}(\w{4})/, '$1****$2'));
  		}else if(str.length ==10){
  			jobject.text(str.replace(/(\w{3})\w{4}(\w{3})/, '$1****$2'));
  		}else if(str.length ==13){
  			jobject.text(str.replace(/(\w{4})\w{4}(\w{5})/, '$1****$2'));
  		}else{
  			var firstStr = str.substring(0,14);
  			var secondStr = str.substring(14);
  			str = firstStr.replace(/(\w{4})\w{4}(\w{6})/, '$1****$2') + secondStr;
  			jobject.text(str.replace("a","-"));
  		}	
    } 

    //替换输入框值
    function replaceTitleRead(str,jobject){
  	  str = str.replace("-","a");
  	  //1，验证手机
  		var mobilePattern =/^(0?((13[0-9]{1})|(15[^4,\D])|(18[0-9]{1})|(14[0-9]{1})|(17[0-9]{1}))+\d{8})$/;
  		var telPattern = /^((0(10|2[^6,\D]|[3-9]\d{2}))-?[1-9]\d{6,7}(-\d{1,8})?)$/;
  		var isMobileFomat = mobilePattern.test(str);
  	  		var isTelFomat = telPattern.test(str);
  	  		if(str == '' || str == null || str=='undefined'){
  	  			return ;
  	  		}else if(str.length<7){
  	  			return ;
  	  		}else if(str.length ==7){
  	  			jobject.attr('title',str.replace(/(\w{2})\w{4}(\w{1})/, '$1****$2'));
  	  		}else if(str.length ==8 ){
  	  			jobject.attr('title',str.replace(/(\w{2})\w{4}(\w{2})/, '$1****$2'));
  	  		}else if(str.length ==9 ){
  	  			jobject.attr('title',str.replace(/(\w{3})\w{4}(\w{2})/, '$1****$2'));
  	  		}else if(isMobileFomat){
  	  			jobject.attr('title',str.replace(/(\w{3})\w{4}(\w{4})/, '$1****$2'));
  	  		}else if( str.length ==11 ){
  	  			jobject.attr('title',str.replace(/(\w{4})\w{4}(\w{3})/, '$1****$2'));
  	  		}else if(str.length ==12){
  	  			jobject.attr('title',str.replace(/(\w{4})\w{4}(\w{4})/, '$1****$2'));
  	  		}else if(str.length ==10){
  	  			jobject.attr('title',str.replace(/(\w{3})\w{4}(\w{3})/, '$1****$2'));
  	  		}else if(str.length ==13){
  	  			jobject.attr('title',str.replace(/(\w{4})\w{4}(\w{5})/, '$1****$2'));
  	  		}else{
  	  			var firstStr = str.substring(0,14);
  	  			var secondStr = str.substring(14);
  	  			str = firstStr.replace(/(\w{4})\w{4}(\w{6})/, '$1****$2') + secondStr;
  	  			jobject.attr('title',str.replace("a","-"));
  	  		}	
    } 

    //淘客户或客户信息时
    function replaceWordStr(str){
  	  str = str.replace("-","a");
  	  //1，验证手机
  		var mobilePattern =/^(0?((13[0-9]{1})|(15[^4,\D])|(18[0-9]{1})|(14[0-9]{1})|(17[0-9]{1}))+\d{8})$/;
  		var telPattern = /^((0(10|2[^6,\D]|[3-9]\d{2}))-?[1-9]\d{6,7}(-\d{1,8})?)$/;
  		pattern =/^(0?1[123456789]\d{9})$|^((0(10|2[1-3]|[3-9]\d{2}))?[1-9]\d{6,7})$/;
  		var isMobileFomat = mobilePattern.test(str);
  		var isTelFomat = telPattern.test(str);
  		if(str == '' || str == null || str=='undefined'){
  			return str;
  		}else if(str.length<7){
  			return str;
  		}else if(str.length ==7){
  			str = str.replace(/(\w{2})\w{4}(\w{1})/, '$1****$2');
  		}else if(str.length ==8 ){
  			str = str.replace(/(\w{2})\w{4}(\w{2})/, '$1****$2');
  		}else if(str.length ==9 ){
  			str = str.replace(/(\w{3})\w{4}(\w{2})/, '$1****$2');
  		}else if(isMobileFomat){
  			str = str.replace(/(\w{3})\w{4}(\w{4})/, '$1****$2');
  		}else if( str.length ==11 ){
  			str = str.replace(/(\w{4})\w{4}(\w{3})/, '$1****$2');
  		}else if(str.length ==12){
  			str = str.replace(/(\w{4})\w{4}(\w{4})/, '$1****$2');
  		}else if(str.length ==10){
  			str = str.replace(/(\w{3})\w{4}(\w{3})/, '$1****$2');
  		}else if(str.length ==13){
  			str = str.replace(/(\w{4})\w{4}(\w{5})/, '$1****$2');
  		}else{
  			var firstStr = str.substring(0,14);
  			var secondStr = str.substring(14);
  			str = firstStr.replace(/(\w{4})\w{4}(\w{6})/, '$1****$2') + secondStr;
  		}	
  		return str;
    }

	// 电话号码安全设置
	function initSafePhone(){
		var idReplaceWord = $("#idReplaceWord").val();
		if(idReplaceWord==1){
			$("dd[name^=telphone]").each(function(idx,obj){
				var phone = $(obj).text();
				if(phone != null && phone != ''){
					replaceWord(phone,$(obj));

				}
			});
		}
	}