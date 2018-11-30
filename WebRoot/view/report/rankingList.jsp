<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <%@ include file="/common/include.jsp" %>
    <style type="text/css">
    .tree-title{width:150px;}
	.tree-node{width:190px !important;}
    .select dd ul{width:190px !important;overflow-x:hidden;}
    .tree li ul{overflow:hidden !important;}
    </style>
    <script type="text/javascript">
        $(function(){
        	/*下拉框部分*/
	        	/* $('.a,.b').click(function(){
	    	        $(this).parent().find('.drop').fadeToggle();
	    	    }); */
	    	    
    	    $(".hyx-rq-selt").find('.select').each(function(){
		        var s=$(this);
		        var z=parseInt(s.css("z-index"));
		        var dt=$(this).children("dt");
		        var dd=$(this).children("dd");
		        var _show=function(){dd.slideDown(200);dt.addClass("cur");s.css("z-index",z+1);};   //展开效果
		        var _hide=function(){dd.slideUp(200);dt.removeClass("cur");s.css("z-index",z);};    //关闭效果
		        dt.click(function(){dd.is(":hidden")?_show():_hide();});
		        
		        $(document).click();
		        $(document).click(function(i){
		            !$(i.target).parents(".select").first().is(s) ? _hide():"";
		        });
		    });
    	    
    	    
        	$("#tt1").tree({
        		url:ctx+"/orgGroup/get_all_group_json",
        		checkbox:true
        	}); 
        	//确定
        	$("#okBtn").click(function(){
        		var nodes = $("#tt1").tree("getChecked", 'checked');
        		var groupNames = "";
        		var groupIds = "";
        		$.each(nodes,function(idx,obj){
        			groupNames+=obj.text+",";
        			groupIds+=obj.id+",";
        		});
        		if(groupNames != ""){
        			groupNames = groupNames.substring(0,groupNames.length-1);
        			groupIds = groupIds.substring(0,groupIds.length-1);
        		}
        		$("#groupIds").val(groupIds);
        		$("#groupNames").text(groupNames);
        		$(".drop").fadeToggle();
        		var yearStr = $("#year_string").val();
        		var monthStr = $("#month_string").val();
        		getRank(yearStr,monthStr);
        		$("#tt1").parent().hide();
        	});
        	//清空
        	$("#cancleBtn").click(function(){
        		var nodes = $('#tt1').tree('getChecked', 'checked');
        		$.each(nodes,function(index,obj){
        			 $('#tt1').tree("uncheck",obj.target);
        		});
        	});
	        	
            var date=new Date();
            var serverDate=new Date();
            var nowYear = date.getFullYear();
            var month =parseInt(date.getMonth());
            var timeDiv = $('.rank-query-year');
            var changeYear = nowYear;

            $(".query-month-twenly").find(".month-max-circle01").each(function(){
                $(".month-max-circle01:lt("+month+")").css({"width":"46px","height":"38px","background":"url(${ctx}/static/images/month-big-circle.png) no-repeat center center","color":"#fff","font-size":"16px","line-height":"38px"});
                $(".month-max-circle01:gt("+month+")").css({"width":"46px","height":"38px","background":"url(${ctx}/static/images/month-big-circle-gray.png) no-repeat center center","color":"#fff","font-size":"16px","line-height":"38px"});
                $(".month-max-line01:lt("+month+")").css({"background":"#0ea279"});
                $(".month-max-line01:gt("+month+")").css({"background":"#c4c4c4"});
            });

            $(".query-month-twenly").find(".month-max-circle01").click(function(){
                var maldate = parseInt($(".date").text())
                var index = $(this).index();
                if(maldate < nowYear){
                    $(this).css({"width":"46px","height":"69px","background":"url(${ctx}/static/images/parent-gold.png) no-repeat","color":"red","font-size":"20px","line-height":"45px"});
                    $(this).siblings(".month-max-circle01").css({"width":"46px","height":"38px","background":"url(${ctx}/static/images/month-big-circle.png) no-repeat center center","color":"#fff","font-size":"16px","line-height":"38px"});
                    $(".start-small-circle01").removeClass("start-small-circle01").addClass("start-small-circle");
                    $(".start-small-circle").text("");
                }else if(maldate == nowYear){

                    /*alert(parseInt($(".date").text()))*/
                    $(".query-month-twenly").find(".month-max-circle01:lt("+(month+1)+")").each(function(){
                        /*alert(month);*/
                        $(this).click(function(){
                            $(this).css({"width":"46px","height":"69px","background":"url(${ctx}/static/images/parent-gold.png) no-repeat","color":"red","font-size":"20px","line-height":"45px"});
                            $(this).siblings(".month-max-circle01:lt("+month+")").css({"width":"46px","height":"38px","background":"url(${ctx}/static/images/month-big-circle.png) no-repeat center center","color":"#fff","font-size":"16px","line-height":"38px"});
                        })
                    })
                }
                getRank(maldate,parseInt($(this).text()));
            });

            for(var i=0; i <= month; i++){
                $(".month-max-circle01:eq("+month+")").css({"width":"46px","height":"69px","background":"url(${ctx}/static/images/parent-gold.png) no-repeat","color":"red","font-size":"20px","line-height":"45px"});


            }
            $(".start-small-circle").click(function(){
                if(parseInt($(".date").text()) < nowYear ){
                    $(".start-small-circle").removeClass("start-small-circle").addClass("start-small-circle01");
                    $(".start-small-circle01").text("年");
                    $(".query-month-twenly").find(".month-max-circle01").css({"width":"46px","height":"38px","background":"url(${ctx}/static/images/month-big-circle.png) no-repeat center center","font-size":"16px","line-height":"38px","color":"#fff"});
                    getRank(parseInt($(".date").text()),'');
                }
            });
            $(".cont-amount-rank").live("click",function(){
                $(".arrow-point").css({"left":"-54px","top":"60px"});
                $("#saleRank").removeClass("com-none").siblings(".all-rember-rank").addClass("com-none");
            });
            $(".cont-custom-rank").live("click",function(){
                $(".arrow-point").css({"left":"-54px","top":"188px"});
                $("#signRank").removeClass("com-none").siblings(".all-rember-rank").addClass("com-none");
            });
            $(".will-custom-rank").live("click",function(){
                $(".arrow-point").css({"left":"-54px","top":"275px"});
                $("#willRank").removeClass("com-none").siblings(".all-rember-rank").addClass("com-none");
            });
            $(".exhal-breath-rank").live("click",function(){
                $(".arrow-point").css({"left":"-54px","top":"355px"});
                $("#callRank").removeClass("com-none").siblings(".all-rember-rank").addClass("com-none");
            });
            $(".call-out-rank").live("click",function(){
                $(".arrow-point").css({"left":"-54px","top":"455px"});
                $("#callOutRank").removeClass("com-none").siblings(".all-rember-rank").addClass("com-none");
            });
            // 年份增减
            function dateSel(){

                function nowY(obj){
                    timeDiv.find('.date').text(obj);
                }
                nowY(nowYear);
                timeDiv.find('.minus').click(function(){
                    var rankdate = parseInt($(".date").text());
                    if(rankdate < parseInt(nowYear)){
                        date.setFullYear(date.getFullYear()-1);
                        changeYear = changeYear + 1;
                        nowY(changeYear);
                    }
                    if(changeYear == parseInt(nowYear)){
                        $(".minus").css({"background":"url(${ctx}/static/images/minus01.png) no-repeat center 0"});
                        $(".start-small-circle01").removeClass("start-small-circle01").addClass("start-small-circle");
                        $(".start-small-circle").text("");
                        $(".month-max-circle01:eq("+month+")").css({"width":"46px","height":"69px","background":"url(${ctx}/static/images/parent-gold.png) no-repeat","color":"red","font-size":"20px","line-height":"45px"});
                        $(".month-max-circle01:gt("+month+")").css({"width":"46px","height":"38px","background":"url(${ctx}/static/images/month-big-circle-gray.png) no-repeat center center","color":"#fff","font-size":"16px","line-height":"38px"});
                        $(".query-month-twenly").find(".month-max-line01").each(function(){
                            $(".month-max-line01:lt("+month+")").css({"background":"#0ea279"});
                            $(".month-max-line01:gt("+month+")").css({"background":"#c4c4c4"});
                        });
                        getRank(changeYear,month+1);
                    }else{
                        /*$(".query-month-twenly").find(".month-max-circle01").click(function(){*/
                        $(".start-small-circle").removeClass("start-small-circle").addClass("start-small-circle01");
                        $(".start-small-circle01").text("年");
                        $(".query-month-twenly").find(".month-max-circle01").css({"width":"46px","height":"38px","background":"url(${ctx}/static/images/month-big-circle.png) no-repeat center center","font-size":"16px","line-height":"38px","color":"#fff"});
                        /*});*/
                        getRank(changeYear,'');
                    }
                });

                timeDiv.find('.pluses').click(function(){
                    date.setFullYear(date.getFullYear()+1);
                    changeYear = changeYear - 1;
                    nowY(changeYear);
                    rankdate = changeYear;
                    $(".minus").css({"background":"url(${ctx}/static/images/minus.png) no-repeat center 0"});
                    if(rankdate < parseInt(nowYear)){
                        $(".start-small-circle").removeClass("start-small-circle").addClass("start-small-circle01");
                        $(".start-small-circle01").text("年");
                        $(".query-month-twenly").find(".month-max-circle01").css({"width":"46px","height":"38px","background":"url(${ctx}/static/images/month-big-circle.png) no-repeat center center","font-size":"16px","line-height":"38px","color":"#fff"});
                        $(".query-month-twenly").find(".month-max-line01").css({"background":"#0ea279"});
                        getRank(changeYear,'');
                    }
                    /*if(date.getFullYear()<serverDate.getFullYear()){
                     $('.hyx-aspa-edit').show();
                     }else{
                     $('.hyx-aspa-edit').hide();
                     }*/
                });
            }
            dateSel();

        });
	window.onload=function(){
		getRank('','');
	}
	var getRank = function(yearStr,monthStr){
		var groupIds = '';
		if($("#groupIds").length > 0){
			groupIds = $("#groupIds").val();
		}
		$("#year_string").val(yearStr);
		$("#month_string").val(monthStr);
		$.ajax({
			url:ctx+'/report/rankingList/data',
			type:'post',
			data:{yearStr:yearStr,monthStr:monthStr,groupIds:groupIds},
			dataType:'html',
			error:function(){},
			success:function(data){
				$("#rankDiv").html(data);
				var height = $(".rank-botto-left").height();
	            $(".rank-botto-right").height(height-6);
	            $("#callRank").find(".rember-td01-money").each(function(){
	            	var time = $(this).text();
	            	if(time == null || time == ''){
	            		time = '0';
	            	}
	            	$(this).text(getHoursTime(time));
	            });
			}
		})
	}
	//将秒转换为小时
	var getHoursTime = function(second) {
		second = Math.ceil(second/60);//向上取整
		return second+"分钟";

	}
    </script>
    <title>排行榜查询</title>
</head>
<body>
<div class="rank-query-contain clearfix">
    <div class="rank-query-year fl_l">
        <label class="center">
			<span class="sp_a fl_l">
				<label class="minus"></label>
				<label class="date"></label>
				<label class="pluses"></label>
			</span>
        </label>
    </div>
    <div class="rank-query-month fl_l">
        <span class="start-small-circle fl_l"></span>
        <div class="query-month-twenly clearfix">
            <span class="month-max-line01 fl_l"></span>
            <span class="month-max-circle01 fl_l">01</span>
            <span class="month-max-line01 fl_l"></span>
            <span class="month-max-circle01 fl_l">02</span>
            <span class="month-max-line01 fl_l"></span>
            <span class="month-max-circle01 fl_l">03</span>
            <span class="month-max-line01 fl_l"></span>
            <span class="month-max-circle01 fl_l">04</span>
            <span class="month-max-line01 fl_l"></span>
            <span class="month-max-circle01 fl_l">05</span>
            <span class="month-max-line01 fl_l"></span>
            <span class="month-max-circle01 fl_l">06</span>
            <span class="month-max-line01 fl_l"></span>
            <span class="month-max-circle01 fl_l">07</span>
            <span class="month-max-line01 fl_l"></span>
            <span class="month-max-circle01 fl_l">08</span>
            <span class="month-max-line01 fl_l"></span>
            <span class="month-max-circle01 fl_l">09</span>
            <span class="month-max-line01 fl_l"></span>
            <span class="month-max-circle01 fl_l">10</span>
            <span class="month-max-line01 fl_l"></span>
            <span class="month-max-circle01 fl_l">11</span>
            <span class="month-max-line01 fl_l"></span>
            <span class="month-max-circle01 fl_l">12</span>
        </div>
    </div>
</div>
<input type="hidden" id="year_string" value="" />
<input type="hidden" id="month_string" value="" />
<div class="hyx-rq-selt">
	<dl class="select fl_l" style="position:relative;">
			<dt>部门名称</dt>
			<dd>
				<ul id="tt1" style="width:250px;max-height:180px;overflow-x:hidden;">
					
				</ul>
				<div class="sure-cancle clearfix" style="width:120px;margin:15px auto 0;padding-bottom:10px;">
					<a class="com-btnc bomp-btna com-btna-sty reso-owner-sure fl_l" id="okBtn" href="javascript:;"><label>确定</label></a>
					<a class="com-btnd bomp-btna fl_l" id="cancleBtn" href="javascript:;"><label>清空</label></a>
				</div>
			</dd>
	</dl>
	<span id="groupNames" class="hyx-rq-list fl_l"></span>
	<input type="hidden" id="groupIds" value="" />
</div>
<div id="rankDiv" class="ranking-query-bottom clearfix">
    
</div>
</body>
</html>
