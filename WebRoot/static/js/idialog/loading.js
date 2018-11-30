//采用jquery easyui loading css效果
        function ajaxLoading(msg) {
        	var bodys=$("body").parent();
            $("<div class=\"datagrid-mask\"></div>").css({
                display: "block",
                width: "100%",
                height: $(window).height(),
                zIndex:1000
            }).appendTo(bodys);
            $("<div class=\"datagrid-mask-msg\"></div>").html(msg).appendTo(bodys).css({
                display: "block",
                left: ($(document.body).outerWidth(true) - 190) / 2,
                top: ($(window).height() - 45) / 2
            });
        }
        function ajaxLoading1(msg) {
        	var bodys=$("body");
            $("<div class=\"datagrid-mask\"></div>").css({
                display: "block",
                width: "100%",
                height: $(window).height(),
                zIndex:1000
            }).appendTo(bodys);
            $("<div class=\"datagrid-mask-msg\"></div>").html(msg).appendTo(bodys).css({
                display: "block",
                left: ($(document.body).outerWidth(true) - 190) / 2,
                top: ($(window).height() - 45) / 2
            });
        }
        function ajaxLoadEnd() {
            $(".datagrid-mask").remove();
            $(".datagrid-mask-msg").remove();
        }

/**
 * main.jsp
 * qq、serviceTel、currencyUnit
 * QQ、服务电话、货币单位
 */
$(function(){
    $(".product-currencyUnit").each(function(index,item){
		var $item = $(item);
		var product_currencyUnit = localStorage.getItem("product_currencyUnit")
		if(product_currencyUnit){
			$item.text(product_currencyUnit);
		}
	})
	$(".product-currencyUnit-title").each(function(index,item){
		var $item = $(item);
		var title = $item.data('title');
		var product_currencyUnit = localStorage.getItem("product_currencyUnit")
		if(product_currencyUnit){
			$item.attr("title",(title+product_currencyUnit));
		}
    })
	$(".product-qq").each(function(index,item){
		var $item = $(item);
		var product_qq = localStorage.getItem("product_qq")
		if(product_qq){
			$item.text(product_qq);
		}
	});
	$(".product-qq-title").each(function(index,item){
		var $item = $(item);
		var title = $item.data('title');
		var product_qq = localStorage.getItem("product_qq")
		if(product_qq != "4008262277"){
			// $item.attr("title",(title+product_qq));
			$item.hide();
		}
	});
	$(".product-serviceTel").each(function(index,item){
		var $item = $(item);
		var product_serviceTel = localStorage.getItem("product_serviceTel")
		if(product_serviceTel){
			$item.text(product_serviceTel);
		}
	});
	$(".product-serviceTel-title").each(function(index,item){
		var $item = $(item);
		var title = $item.data('title');
		var product_serviceTel = localStorage.getItem("product_serviceTel")
		if(product_serviceTel){
			$item.attr("title",(title+product_serviceTel));
		}
		if(product_serviceTel && $item.attr('onclick')){
			$item.attr('onclick',('call("'+product_serviceTel+'")'));
		}
	});
})