<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Date"%>
<head>
  <title>消息主页面</title>
  <%@ include file="/common/include.jsp"%>

    <script type="text/javascript" src="${ctx}/static/js/table.js${_v}"></script>
    <script type="text/javascript" src="${ctx}/static/js/stamper/jquery.stamper.js${_v}"></script>

    <script type="text/javascript">
        $(function(){
            var type=${type};
            if(type==1){
                $('#iframepage').attr('src',ctx+"/message/custList");
            }else  if(type==2){
                $('#iframepage').attr('src',ctx+"/message/bbsList");
            }else  if(type==3){
                $('#iframepage').attr('src',ctx+"/message/callList");
            }else  if(type==4){
                $('#iframepage').attr('src',ctx+"/message/dateList");
            }else  if(type==5){
                $('#iframepage').attr('src',ctx+"/message/noticeList");
            }else  if(type==6){
                $('#iframepage').attr('src',ctx+"/message/authList");
            }else  if(type==7){
                $('#iframepage').attr('src',ctx+"/message/sysNoticeList");
            }else  if(type==8){
                $('#iframepage').attr('src',ctx+"/message/otherNoticeList");
            }


            /*左边、右边铺满整屏*/
            var winhight=$(window).height();
            $(".hyx-mc,.hyx-mc-left,.hyx-mc-right").height(winhight);

            /*左边菜单选中效果*/
            $(".hyx-mc-left").find('.box').each(function(i,item){
                $(item).click(function(){
                    $(this).addClass('box-click').siblings('.box').removeClass('box-click');
                    $(this).find('.tip').hide();  //小红点点击之后隐藏
                    var name =$(this).attr('name');
                    $('#iframepage').attr('src',ctx+"/message/"+ name);
                });
            });

  
        	// 小红点
        	$('.hyx-mc-left').find('.tip').each(function(i,item){
        		$(item).show();
        		if($(item).attr('name')>=10 && $(item).attr('name')<100){
        			$(item).removeClass('tip_small').addClass('tip_big').text($(item).attr('name'));
        		}
        		else if($(item).attr('name')<10 && $(item).attr('name')>0){
        			$(item).removeClass('tip_big').addClass('tip_small').text($(item).attr('name'));
        		}
        		else if($(item).attr('name')>=100){
        			$(item).removeClass('tip_small').addClass('tip_big').text('...');
        		}
        		else if($(item).attr('name')==0){
        			$(item).hide();
        		}
        	});
        });
    </script>
</head>
<body>
<div class="hyx-mc" style="overflow:hidden;">
    <div class="hyx-mc-left fl_l">
        <label class="box <c:if test="${type eq 1 }">box-click</c:if> fl_l" name="custList">
            <span class="sp">客户联系</span>
            <span class="tip tip_small" name="${item.custNum}">${item.custNum}</span>
        </label>
        <label class="box <c:if test="${type eq 2 }">box-click</c:if> fl_l" name="bbsList">
            <span class="sp">点评提醒</span>
            <span class="tip tip_small" name="${item.bbsNum}">${item.bbsNum}</span>
        </label>
        <label class="box <c:if test="${type eq 3 }">box-click</c:if> fl_l" name="callList">
            <span class="sp">未接来电</span>
            <span class="tip tip_small" name="${item.callNum}">${item.callNum}</span>
        </label>
        <label class="box <c:if test="${type eq 4 }">box-click</c:if> fl_l" name="dateList">
            <span class="sp">到期提醒</span>
            <span class="tip tip_small" name="${item.dateNum}">${item.dateNum}</span>
        </label>
        <label class="box <c:if test="${type eq 5 }">box-click</c:if> fl_l" name="noticeList">
            <span class="sp">通知公告</span>
            <span class="tip tip_small" name="${item.noticeNum}">${item.noticeNum}</span>
        </label>
        <label class="box <c:if test="${type eq 6 }">box-click</c:if> fl_l" name="authList">
            <span class="sp">待办审核</span>
            <span class="tip tip_small" name="${item.authNum}">authNum</span>
        </label>
         <label class="box <c:if test="${type eq 7 }">box-click</c:if> fl_l" name="sysNoticeList">
            <span class="sp">系统消息</span>
            <span class="tip tip_small" name="${item.sysNum}">${item.sysNum}</span>
        </label>
         <label class="box <c:if test="${type eq 8 }">box-click</c:if> fl_l" name="otherNoticeList">
            <span class="sp">其他消息</span>
            <span class="tip tip_small" name="${item.otherNum}">${item.otherNum}</span>
        </label>
    </div>
    <div class="hyx-mc-right fl_l">
        <iframe src="${ctx }/message/custList.do" width="100%" height="100%" id="iframepage" frameborder="0" scrolling="no" marginheight="0" marginwidth="0"></iframe>
    </div>
</div>
</body>
</html>
