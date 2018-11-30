$(document).ready(function() {
	//弹出层
    var t1 = new PopupLayer({trigger:"#ele1",popupBlk:"#blk1",closeBtn:"#close01",useOverlay:true,useFx:true,
        offsets:{
            x:0,
            y:0
        }
    });
    t1.doEffects = function(way){
        if(way == "open"){
            this.popupLayer.css({opacity:1}).show(500,function(){
                this.popupLayer.animate({opacity:1},0,function(){this.popupLayer.css("opacity",1)}.binding(this));
            }.binding(this));
        }
        else{
            this.popupLayer.animate({
                opacity:0.1
            },{duration:500,complete:function(){this.popupLayer.css("opacity",1);this.popupLayer.hide()}.binding(this)});
        }
    }
    var t2 = new PopupLayer({trigger:"#new_log_window_open_btn",popupBlk:"#new_log_window",closeBtn:"#new_log_window_close_btn",useOverlay:true,useFx:true,
        offsets:{
            x:-400,
            y:0
        }
    });
    t2.doEffects = function(way){
        if(way == "open"){
            this.popupLayer.css({opacity:1}).show(500,function(){
                this.popupLayer.animate({opacity:1},0,function(){this.popupLayer.css("opacity",1)}.binding(this));
            }.binding(this));
        }
        else{
            this.popupLayer.animate({
                opacity:0.1
            },{duration:500,complete:function(){this.popupLayer.css("opacity",1);this.popupLayer.hide()}.binding(this)});
        }
    }
    var t3 = new PopupLayer({trigger:"#ele3",popupBlk:"#blk3",closeBtn:"#close03",useOverlay:true,useFx:true,
        offsets:{
            x:-400,
            y:0
        }
    });
    t3.doEffects = function(way){
        if(way == "open"){
            this.popupLayer.css({opacity:1}).show(500,function(){
                this.popupLayer.animate({opacity:1},0,function(){this.popupLayer.css("opacity",1)}.binding(this));
            }.binding(this));
        }
        else{
            this.popupLayer.animate({
                opacity:0.1
            },{duration:500,complete:function(){this.popupLayer.css("opacity",1);this.popupLayer.hide()}.binding(this)});
        }
    }
    var t4 = new PopupLayer({trigger:"#ele4",popupBlk:"#blk4",closeBtn:"#close04",useOverlay:true,useFx:true,
        offsets:{
            x:-400,
            y:0
        }
    });
    t4.doEffects = function(way){
        if(way == "open"){
            this.popupLayer.css({opacity:1}).show(500,function(){
                this.popupLayer.animate({opacity:1},0,function(){this.popupLayer.css("opacity",1)}.binding(this));
            }.binding(this));
        }
        else{
            this.popupLayer.animate({
                opacity:0.1
            },{duration:500,complete:function(){this.popupLayer.css("opacity",1);this.popupLayer.hide()}.binding(this)});
        }
    }
});