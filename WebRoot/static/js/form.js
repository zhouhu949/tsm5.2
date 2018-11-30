$.fn.serializeObject = function() {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};
$(function() {
	$(".product-currencyUnit").each(function(index,item){
		var $item = $(item);
		var product_currencyUnit = localStorage.getItem("product_currencyUnit")
		if(product_currencyUnit){
			$item.text(product_currencyUnit);
		}
	});
	$(".product-currencyUnit-title").each(function(index,item){
		var $item = $(item);
		var title = $item.data('title');
		var product_currencyUnit = localStorage.getItem("product_currencyUnit")
		if(product_currencyUnit){
			$item.attr("title",(title+product_currencyUnit));
		}
	});
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
	$('[data-fieldcode=phone]').bind('replaceWord',function(){
		var $this = $(this);
		if(this.tagName == 'INPUT'){
			var phone = $this.val();
			if(phone != null && phone != ''){
				replaceWordInput(phone,$this);
			}
		}else if(this.tagName == 'SPAN'){
			var phone = $this.text();
			if(phone != null && phone != ''){
				replaceWord(phone,$this);
				replaceTitleWord(phone,$this);
			}
		}
	});
	
	$('[data-fieldcode=phone]').each(function(idx,item){
		var $item = $(item);
		var idReplaceWord = $("#idReplaceWord").val();
    	if(idReplaceWord==1){
    		$item.trigger('replaceWord');
    	}
	});
	
	$(".sub-dep-inpu").click(function() {
		$(this).parent().find(".manage-drop").show();
		$(this).parent().find(".manage-drop").parent().siblings().find("dd").hide();
		$(this).parent().find(".manage-owner-sour").hide();
		return false;
	});
	$(".owner-sour").click(function(e) {
		e.preventDefault();
		var $this = $(this);
		var x = parseInt($this.siblings(".manage-owner-sour").css("z-index"));
		var show_x = function() {
			$this.siblings(".manage-owner-sour").css("z-index", x);
		};
		var hide_x = function() {
			$this.siblings(".manage-owner-sour").css("z-index", x - 1);
		};
		$this.siblings(".manage-owner-sour").is(":hidden") ? show_x() : hide_x();
		/*$(".manage-owner-sour").show();*/
		$(".manage-owner-sour").parent().siblings().find("dd").hide();
		$(".manage-drop").hide();
		return false;
	});
	/*查询部分调用函数*/
	multiDropDownFn();

   $('.custom-picker').length>0 && $('.custom-picker').dateRangePicker({
        showShortcuts:false,
        endDate:getNowFormatDate(),format: 'YYYY-MM-DD'
    }).bind('datepicker-apply',function(event,obj){
        var s_t = '';
        var e_t = '';
        var s = $(this).parents('.select');
        if(obj.date1 != 'Invalid Date'){
            console.log( s.find('input[data-type="start"]'))
            s.find('input[data-type="start"]').val(moment(obj.date1).format('YYYY-MM-DD'));
            s_t = moment(obj.date1).format('YY.MM.DD');
        }
        if(obj.date2 != 'Invalid Date'){
            console.log( s.find('input[data-type="end"]'))
            s.find('input[data-type="end"]').val(moment(obj.date2).format('YYYY-MM-DD'));
            e_t = moment(obj.date2).format('YY.MM.DD');
        }else{
            s.find('input[data-type="end"]').val("");
		}
        var dt=s.children("dt");
        var dd=s.children("dd");
        dt.html(s_t+'/'+e_t);
        $("#dDateType").val(5);
        dd.slideUp(200);
        dt.removeClass("cur");
        //s.css("z-index",z);
    });

	if($(".com-search").find(".input-query").val() != '') {
		$(".com-search").find(".input-query").parent().find('.hide-span').hide();
	}

	$(".com-search").find('.input-query,.hide-span').click(function() {
		var input_query = $(this).parent().find('.input-query');
		input_query.focus();
		$(this).parent().find('.hide-span').hide();
		input_query.blur(function() {
			if($(this).val() == '') {
				$(this).parent().find('.hide-span').show();
			}
		});
	});

	$(".com-search").find('.input-query').focus(function() {
		var input_query = $(this);
		input_query.parent().find('.hide-span').hide();
		input_query.blur(function() {
			if(input_query.val() == '') {
				input_query.parent().find('.hide-span').show();
			}
		});
	});
	/*
	   $(".com-search").find('.list-topbor').live("mouseleave",function(){
	        $(".date-picker-wrapper").mouseover(function(){
	            $(this).parent().find('.com-search').find('.list-box').addClass('list-topbor').find('.list-hid').show();
	        });
	        $(this).removeClass('list-topbor').find('.list-hid').hide();
	        isShow = false;
	    });*/
	/*表单优化*/

	$(".reso-sub-clear").click(function() {
		$(this).parent().parent(".manage-drop").show();
	});

	/*表单查询条件样式调整*/
/*	if($(".search").find(".select_outerDiv").length == 0) {
		$(".com-search .hide-span").css("left", "10px");
	}*/

    $(".com-search").find('.more').click(function(){
        if($(this).hasClass('more_up') == true){
            $(this).removeClass('more_up');
            $(this).parent().find('.list-box').removeClass("pos_more");
           // $(this).parent().find('.list-box').css("height","60px")
            $(this).parent().find('.list-hid').hide();
        }else{
            $(this).addClass('more_up');
            $(this).parent().find('.list-box').addClass("pos_more");
           // $(this).parent().find('.list-box').css("height","30px")
            $(this).parent().find('.list-hid').show();
        }
    });

	// 高级查询
	$(".com-search #superQuery").click(function(e){
		var _this = $(this);
		var url = _this.data("url");// + "&t=" + new Date().getTime()
		var title = _this.data("title");
		pubDivShowIframe('alert_high_search_e',url,title,680,550);
	});

	$(".com-search #query").on("click",function(e){
		var _this = $(this);
		_this.attr("disabled",true);
		try{
			loadData();
		}catch(e){
			document.forms[0].submit();
		}
		setTimeout(function(e){
			_this.attr("disabled",false);
		},500);
	});

	//树添加搜索框
	$('[data-type="search-tree"]').each(function(){
		var _this = $(this);
		_this.treeSearch();
	});

	$('.select[data-multi="true"] dd ul li').live("click",function(e){
		e.stopPropagation();
		var _this = $(this);
		_this.find("a").click();
	});
	$('.select[data-multi="true"] dd ul li a').live("click",function(e){
		e.stopPropagation();
	});
	
	$(".resGroup .resource-group-tree").each(function(){
		var _this = $(this);
		var resGroup = _this.parents(".resGroup");
		var selectedNode = resGroup.data("selected-node");
		var input = resGroup.data("input");
		$.ajax({
			url: _this.attr("data-url"),
			type: "get",
			success: function(data){
//				console.log(data);
				
//				var render_data = [{
//					"id":"",
//					"text":"资源分组",
//					"type":"none",
//					"level":"none",
//					"isSharePool":"none",
//					"inputAcc":"none",
//					"children":data,
//				}];
				var first_source = {
					"id":"",
					"text":"资源分组",
					"type":"none",
					"level":"none",
					"isSharePool":"none",
					"inputAcc":"none"
				};
				
				data.splice(0,0,first_source);
				
				_this.tree({
					data: data,
					formatter: function(node){
						var nodeText = "<label class='tree-node-label' data-id='"+node.id+"' data-share-pool='"+node.isSharePool+"' title='"+node.text+"'>" + node.text + "</label>";
						return nodeText;
					},
					onBeforeSelect:function(node){
						if(node.level != 1 && node.level != "none"){
							return false;
						}
					},
					onSelect:function(node){
						resGroup.find(input).val(node.id);
						resGroup.find("dt").text(node.text);
						resGroup.find("dd").hide();
					},
					onLoadSuccess: function(node,data){
						if(selectedNode && selectedNode!=""){
							_this.tree("select",_this.tree("find",selectedNode).target);
						}
					}
				});
			},
			error: function(status){
				alert("分组加载失败！");
			}
		});
	});
	
	//添加行动标签
	$("#addLabelBtn").click(function(){
		var labelCodeId = $(this).attr("data-chosen-id")?$(this).attr("data-chosen-id"):"";
		pubDivShowIframe('alert_action_tag_choose',ctx+'/res/tao/toActionTagChoose?optionlistIds='+labelCodeId,'行动标签',610,420);
	});
});
//查询部分函数
function multiDropDownFn(){
	$(".com-search,.com-search01,.com-search-report").find('.select').each(function() {
		var s = $(this);
		//防止多次绑定
		if(s.data("has") == "true"){
			return;
		}else{
			s.data("has","true");
		}
		
		var z = parseInt(s.css("z-index"));
		var dt = s.children("dt");
		dt.data("placeholder",dt.text());
		var dd = s.children("dd");
		var _show = function() {
			dd.slideDown(200);
			dt.addClass("cur");
			s.css("z-index", z + 1);
		}; //展开效果
		var _hide = function() {
			dd.slideUp(200);
			dt.removeClass("cur");
			s.css("z-index", z);
		}; //关闭效果
		dt.click(function(e) {
			e.stopPropagation();
			var _this_dt = $(this);
			if(s.data("select-type") == "area"){
				SelCity(this,e,function(data){
					data==""?_this_dt.text("所在地区"):_this_dt.text(data);
				});
			}else{
				$(".com-search,.com-search01").find('.select').children("dd").hide();
				dd.is(":hidden") ? _show() : _hide();
			}
		});
        if(dd.find('a').hasClass('custom-picker') == true) {
            dd.find(".custom-picker").parent().siblings().click(function (e) {
            	e.stopPropagation();
                dt.html($(this).html());
                var type=$(this).find("a").data("type");
                if(type=="0"){
                    s.find('input[data-type="start"]').val("");
                    s.find('input[data-type="end"]').val("");
				}else if(type=="1"){
                    s.find('input[data-type="start"]').val(moment().format('YYYY-MM-DD'));
                    s.find('input[data-type="end"]').val(moment().format('YYYY-MM-DD'));
                }else if(type=="2"){
                s.find('input[data-type="start"]').val(moment().day("Sunday").format('YYYY-MM-DD'));
                s.find('input[data-type="end"]').val(moment().day("Saturday").format('YYYY-MM-DD'));
                }else if(type=="3"){
                    s.find('input[data-type="start"]').val(moment().startOf('month').format('YYYY-MM-DD'));
                    s.find('input[data-type="end"]').val(moment().endOf('month').format('YYYY-MM-DD'));
				}else if(type=="4"){
                    s.find('input[data-type="start"]').val(moment().subtract(6, 'months').format('YYYY-MM-DD'));
                    s.find('input[data-type="end"]').val(moment().format('YYYY-MM-DD'));
				}
                _hide();
            }); //选择效果（如需要传值，可自定义参数，在此处返回对应的“value”值 ）
            $('.apply-btn').click(function (e) {
            	e.stopPropagation();
                _hide();
				/*$('.com-search').find('.list-box').removeClass('list-topbor').find('.list-hid').hide();*/
            });
        }else if(dd.find('a').hasClass('diy') == true) {
			dd.find(".diy").parent().siblings().click(function(e) {
				e.stopPropagation();
				dt.html($(this).html());
				_hide();
			}); //选择效果（如需要传值，可自定义参数，在此处返回对应的“value”值 ）
			$('.apply-btn').click(function(e) {
				e.stopPropagation();
				_hide();
				/*$('.com-search').find('.list-box').removeClass('list-topbor').find('.list-hid').hide();*/
			});

		} else {
			dd.find("li a").click(function(e) {
				e.stopPropagation();
				var $target= jQuery(e.currentTarget);
				var field = $target.parents(".select");
                var input= field.data("input")
                var uls = $target.parent().parent();
				if(field.data('multi')==true){
					if(s.attr("disabled") == "disabled"){
						return false;
					}
                    $target.parents("li").toggleClass("selected");
                    var inputsVal =uls.find(".selected a").map(function(){
                        return $(this).data('value')
                    }).get().join(",");
                    field.find(input).val(inputsVal);
                    dt.html(
                    		uls.find(".selected a").map(function(){
                            return $(this).text();
                        }).get().join(",")
					);
                    if(uls.find(".selected a").length == 0){
                    	if(uls.parent().parent().hasClass("sales-select")){//销售管理
                    		dt.text(uls.data("palceholder"));
                    	}else{
                    		dt.text(dt.data("placeholder"));
                    	}
					}
//                    field.find(input).html($(this).html());
				}else{
				//	$target.parents("li").siblings().removeClass("selected");
                 //   $target.parents("li").toggleClass("selected");
                    field.find(input).val($(this).data('value'));
                    dt.html($(this).html());
                    _hide();
				}
			}); //选择效果（如需要传值，可自定义参数，在此处返回对应的“value”值 ）
		};
		$(document).click(function(i) {
			$(".sub-dep-inpu").click(function() {
				$(this).parent().find(".manage-drop").show();
				$(this).parent().find(".manage-drop").parent().siblings().find("dd").hide();
				$(this).parent().find(".manage-drop").parent().parent().siblings().find("dd").hide();
				$(this).parent().find(".manage-owner-sour").hide();
				return false;
			});
			$(".manage-owner-sour").click(function() {
				$(this).show();
				return false;
			});
			$(".reso-owner-sure").click(function() {
				$(this).parent().parent().hide();
				return false;
			});
			$(".manage-drop").click(function() {
				$(this).show();
				return false;
			});
			$(".owner-sour").click(function(e) {
				e.preventDefault();
				var $this=$(this);
				var $siblings=$this.siblings(".manage-owner-sour");
				$(".manage-owner-sour").hide();
				$this.siblings(".manage-owner-sour").show();
				$siblings.parent().siblings().find("dd").hide();
				$siblings.parent().parent().siblings().find("dd").hide();
				$(".manage-drop").hide();
				return false;
			});
			$(".reso-alloc-sure").click(function() {
				$(this).parent().parent().hide();
				return false;
			});
			$(".reso-sub-clear").click(function() {
				$(this).parent().parent().show();
				return false;
			});
			$(".manage-drop").hide();
			$(".manage-owner-sour").hide();
			!$(i.target).parents(".select").first().is(s) ? _hide() : "";
		});
		$(document).click();
	});
}

function selectBitch(){
	$(".select").each(function() {
		var s = $(this);
		var z = parseInt(s.css("z-index"));
		var dt = s.children("dt");
		var dd = s.children("dd");
		var _show = function() {
			dd.slideDown(200);
			dt.addClass("cur");
			s.css("z-index", z + 1);
		}; //展开效果
		var _hide = function() {
			dd.slideUp(200);
			dt.removeClass("cur");
			s.css("z-index", z);
		}; //关闭效果
		dt.click(function(e) {
			e.stopPropagation();
			$(".select").children("dd").hide();
			dd.is(":hidden") ? _show() : _hide();
		});
/*		if(dd.offset().top+dd.height()>=$("body").height())
		{
            dd.css('bottom',"26px");
            dd.css('top',"inherit");
		}*/
		if(dd.find('a').hasClass('diy') == true) {
			dd.find(".diy").parent().siblings().click(function() {
				dt.html($(this).html());
				_hide();
			}); //选择效果（如需要传值，可自定义参数，在此处返回对应的“value”值 ）
			$('.apply-btn').click(function() {
				_hide();
				/*$('.com-search').find('.list-box').removeClass('list-topbor').find('.list-hid').hide();*/
			});
		} else {
			dd.find("a").click(function(e) {
				e.stopPropagation();
				var $target= jQuery(e.currentTarget);
				var field = $target.parents(".select");
                var input= field.data("input");
				if(field.data('multi')==true){
					if(s.attr("disabled") == "disabled"){
						return false;
					}
                    $target.parents("li").toggleClass("selected");
                    field.parents(".bomp-p").find(input).val(
                        $target.parents("ul").find(".selected a").map(function(){
                            return $(this).data('value')
                        }).get().join(",")
					);
                    dt.html(
                        $target.parents("ul").find(".selected a").map(function(){
                            return $(this).text();
                        }).get().join(",")
					);
					if($target.parents("ul").find(".selected a").length == 0){
						 dt.html("--请选择--");
					}
				}else{
					$target.parents("li").siblings().removeClass("selected");
                    $target.parents("li").toggleClass("selected");
                    field.find(input).val($(this).data('value'));
                    dt.html($(this).html());
                    _hide();
				}
			}); //选择效果（如需要传值，可自定义参数，在此处返回对应的“value”值 ）
		}
		
		$(document).click(function(){
			dd.slideUp(200);
		});
	});
}

function chooseActionTag(chosenTagsId,chosenTagsName){
	$("#allLabels").val(chosenTagsId.join());
	$("#addLabelBtn").attr("data-chosen-id",chosenTagsId.join());
	$(".com-moTag span[label]").remove();
	$.each(chosenTagsName,function(index,item){
		$("<span label='1' class='e'>"+item+"</span>").insertBefore("#addLabelBtn");
	});
}

function formatMoney(num){
    num = num + '';
    return num.replace(/(?=(?!^)(?:\d{3})+(?:\.|$))(\d{3}(\.\d+$)?)/g,',$1');
}