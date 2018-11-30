$(function() {
	window.onerror=function(){return true;}
	
	 var m_top = $('#mid_totop');
	 var m_bottom =$('#mid_tobuttom');
	 
    var scrTop = document.body.scrollTop; // 网页正文全文高
    var offsetHeight = document.body.offsetHeight; // 网页可见区域高
    var clientHeight = document.body.clientHeight; // 网页可见区域高
    var availHeight = window.screen.availHeight; // 屏幕可用工作区高度
    var $toTop = $(this);
    

//    window.onscroll = function(){
//    	var scrolltop = $(this).scrollTop();  
//    	if( 100 < scrolltop < offsetHeight){
//    	    if(scrolltop + availHeight > offsetHeight){
//    	    	alert(1);
//    	    	m_bottom.fadeIn();
//    	    	m_top.fadeOut();
//    	    }
//    	    else if((scrolltop + availHeight) >= availHeight){
//    	    	alert(11);
//    	    	m_bottom.fadeIn();
//    			m_top.fadeOut();
//    	    }
//    	    else{
//    	    	m_top.show();
//    			m_bottom.show();
//    	    }
//    	}
//    };
        $("html,body").animate({scrollTop: 0}, 500);
        m_top.click(function(){
        	$("html,body").animate({scrollTop: 0}, 500);
        	m_top.fadeOut();
        	m_bottom.fadeIn();
            this.blur();
            return false;
        });
        
        m_bottom.click(function(){
           $("html,body").animate({scrollTop:offsetHeight}, 500);
           m_top.fadeIn();
           m_bottom.fadeOut();
           this.blur();
           return false;
        });
});
