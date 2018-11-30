<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<script type="text/javascript" src="${ctx}/static/js/common/common.js"></script>
<style>
.tree-node {
	width: 240px;
}

.hyx-hsm-left dl {
	text-align: left;
}

.hyx-hsm-left dl dt{
	text-indent: 30px;
}
 .hyx-hsm-left dl dd {
	text-indent: 13px;
}
.icheckbox_minimal {
	margin: 12px 0 0 10px;
}
</style>
<!--本页面js-->

<script type="text/javascript">

	$(function() {
		getPreContentToShadow();
		/*下拉框部分*/
		$('.mail').click(function() {
			$(this).find('.drop').fadeToggle();
		});
		$('.hyx-cm-new').find('.load').each(function() {
			if ($(this).find('.sp').text().length >= 23) {
				$(this).find('.sp').text($(this).find('.sp').text().substr(0, 23) + '...');
			}
		});

		$('.hyx-cm-new').find('.load').mouseover(function() {
			$(this).find('.drop').fadeIn(1);
		});
		$('.hyx-cm-new').find('.load').mouseleave(function() {
			$(this).find('.drop').fadeOut(1);
		});

	    /*内容展开*/
	    $('.temp-for-words').each(function(i,item){
	    	var text_box = $(item).find('.wrds-decor-pre');
	    	var all_text = text_box.text();
	    	if(all_text.length >= 150){
	    		text_box.next().show();
	    		if(i != 0){
	    			text_box.text(all_text.substr(0,150) + '...');
	    		}else{
	    			text_box.parent().next().removeClass('wrds-down').addClass('wrds-up');
	    		}
	    		$(item).find('i').click(function(){
	    			if($(this).hasClass('wrds-down') == true){
	    				$(this).parent().find('.wrds-decor-pre').text(all_text);
		    			text_box.parent().next().removeClass('wrds-down').addClass('wrds-up');
	    			}else{
	    				$(this).parent().find('.wrds-decor-pre').text(all_text.substr(0,113) + '...');
		    			text_box.parent().next().removeClass('wrds-up').addClass('wrds-down');
	    			}
	    		});
		    }

	    });
		$('#queryBtn').click(function(){
			document.forms[0].submit();
		})

		// 获取和失去焦点
		$("#qKeyWordId").focus(function() {
			$(this).next().text('');
		});
		$("#qKeyWordId").blur(function() {
			$(this).next().text('输入关键字');
		});

			// 全选
		$('input[name=isCheckId]').on('ifChecked', function() {
			  if($(this).val()=="1"){
				  $("input[id^=chk_]").iCheck('check');
		  }else{
			  $("input[id^=chk_]").iCheck('uncheck');
		  }
		});
		//添加只是
		$('#addKnowId').click(function(){
			addKnowlege();
		})
         //添加知识组
		$('#addGroupId').click(function(){
			addOrEditGroup('');
		})
	/**
	 * 删除
	 */
	$("#deleteKnowId").click(function() {
		var ids = '';
		$('input[id^=chk_]').each(function(index, obj) {
			if ($(obj).is(':checked')) {
				ids += $(obj).attr('id').split('_')[1] + ',';
			}
		});
		if (ids.length == 0) {
			window.top.window.top.iDialogMsg("提示", '请先选择！');
			return;
		}
		ids = ids.substring(0, ids.length - 1);
		var delUrl = ctx+ "/sys/knowlege/knowlege_del?ids=" + ids;
		  pubDivDialog('deleteKnow', '是否删除该模板？', function() {
			$.ajax({
				url : delUrl,
				type : 'post',
				error : function(XMLHttpRequest, textStatus) {
				},
				success : function(data) {
					if (data == "0") {
						// 刷新主页面
						window.top.iDialogMsg("提示", '删除成功！');
						setTimeout('location.href ="${ctx}/sys/knowlege/knowlegeMgList"', 1000)
					}
				}
			});
		})
	});
		$('.hyx-hsm-left').find('dd').each(function() {
			var groupId = $(this).attr("id").substring(7);
			var findTag = $("#find_" + groupId);
			var editTag = $("#edit_" + groupId);
			var deleteTag = $("#delete_" + groupId);

			$(this).siblings().hover(function() {
				$(this).addClass("dd-hovers");
			}, function() {
				$(this).removeClass("dd-hovers");
			});
			$(this).click(function() {
				$(this).addClass("dd-hover");
				$(this).siblings().removeClass("dd-hover");
			});
			$(this).hover(function() {
				$(this).find(".reso-grou-edit").show();
				$(this).find(".reso-grou-dele").show();

			}, function() {
				$(this).find(".reso-grou-edit").hide();
				$(this).find(".reso-grou-dele").hide();
			});

			// 编辑图标点击
			editTag.click(function() {
				addOrEditGroup(groupId);
			});
			// // 删除图标点击
			deleteTag.click(function() {
				delGroup(groupId);
			});
			// // 查找点击
			findTag.click(function() {
				findMembers(groupId)
			});

		});
	});


	//查看
	function findMembers(groupId) {
		$("#groupId").val(groupId);
		document.forms[0].submit();
	}

	//删除分类
	function delGroup(groupId) {
		var delUrl = ctx+ "/sys/knowlege/knowlegeGroup_del";
		pubDivDialog('delGroupId', '是否删除该模板分类？', function() {
			$.ajax({
				url : delUrl + '?groupId=' + groupId,
				type : 'post',
				error : function(XMLHttpRequest, textStatus) {
				},
				success : function(data) {
					if (data == 0) {
						// 刷新主页面

						window.top.iDialogMsg("提示", '删除成功！');
						setTimeout('location.href ="${ctx}/sys/knowlege/knowlegeMgList"', 1000)
					}else{
						window.top.iDialogMsg("提示", '操作失败！');
					}
				}
			});
		})
	}

	//新增和修改分类
	function addOrEditGroup(groupId){
		var tmpTitle = groupId==''?'新增分类':'修改分类';
		pubDivShowIframe("addOrUpdateGroup",  "${ctx}/sys/knowlege/toGroup?groupId="+groupId,tmpTitle, 320, 180);
	}

	//新增话术模板
	function addKnowlege(){
        var  groupId=$("[name='groupId']").val();
		pubDivShowIframe("addKnowlege",  "${ctx}/sys/knowlege/toSaveOrUpdate?groupId="+groupId,'新增话术模板', 700, 550);
	}

	function editKnowlege(knowlegeId,groupId){

		pubDivShowIframe("editKnowlege", "${ctx}/sys/knowlege/toSaveOrUpdate?knowlegeId="+knowlegeId+'&groupId='+groupId, '编辑话术模板', 700, 550);
	}

</script>
</head>
<body>
	<form action="${ctx}/sys/knowlege/knowlegeMgList" method="post">
		<input type="hidden" id="groupId" name="groupId" value="${groupId}">
		<input type="hidden" id="ctPath" name="ctPath" value="${ctx}">
		<div class="hyx-dfpzy-reso hyx-layout">
			<div class="hyx-hsm-left hyx-layout-side" style="overflow-y: auto;">
				<dl>
					<dt>模板分类</dt>
					<dt style="background: #f4f4f4;">
						<a class="allo-new-group new-temo-word" href="###" id="addGroupId"><span style="margin-left: -10px; font-size: 14px;">+</span>新建分类</a>
					</dt>
					<dd class="<c:if test='${"" eq groupId or empty groupId}'>dd-hover</c:if>"  id="groupP_"><span id="find_">所有分类(${allKnowlege})</span></dd>
		            <c:forEach items="${knowlegeGroupList}" var="group" varStatus="vs">
			           <dd id="groupP_${group.groupId }" class="<c:if test='${group.groupId eq groupId }'>dd-hover</c:if>" style="position: relative;">
						    <span class="w140 overflow_hidden" id="find_${group.groupId}" title="${group.groupName}(${group.knowlegeNum})">${group.groupName}(${group.knowlegeNum}) </span>
						   	<div style="position: absolute;right:5px;top:0px;">
	                        <a href="###" class="reso-grou-dele min-dele fl_r" title="删除" id="delete_${group.groupId}"></a>
	                        <a href="###" class="reso-grou-edit min-edit fl_r" title="修改" id="edit_${group.groupId}" ></a>
							</div>
					   </dd>
					</c:forEach>
				</dl>
			</div>
			<div class="hyx-hsm-right hyx-layout-content" style="border: none;overflow:hidden;padding:0 0 15px 10px;">
					<div class="com-search hyx-cm-search">
						<div class="com-search-box fl_l" style="padding-bottom: 0;background:#fff;">
							<p class="search clearfix">
								<input class="input-query fl_l" type="text" value="${knowlegeDto.qKeyWordId }" id="qKeyWordId" name="qKeyWordId" style="border: none;background:#fff;width:659px;" /><label class="hide-span" style="line-height: 15px;">输入关键字</label>
							</p>
						</div>
						<input class="query-button fl_r" type="button" value="查&nbsp;询" id="query" />
					</div>
					<div class="com-btnlist hyx-cm-btnlist fl_l" style="width:97%;margin:0 0 0 29px;">
						 <a href="###" class="com-btna temo-word-edit fl_l" id="addKnowId"><i class="min-new-add"></i><label class="lab-mar">新增</label> </a>
					   <c:if test="${!empty knowlegeList }">
						 <a href="###" class="com-btna recor-sample-a fl_l" id="deleteKnowId"><i class="min-dele"></i><label class="lab-mar">删除</label> </a>
						<span class="sty-borcolor-b skin-minimal fl_r">
						     <input type="radio" name="isCheckId" class="check" value="0"/>
							 <span class="fl_l" style="margin-right: 65px; line-height: 25px">取消</span>
					   </span>
							<span class="sty-borcolor-b skin-minimal fl_r">
								<input type="radio" name="isCheckId" class="check" value="1"/><span class="fl_l"  style="margin-right: 20px; line-height: 25px">全选</span>
						   </span>
					   </c:if>
					</div>
					<div class="hid fl_l" style="position:relative;width:100%;display:block;text-align:center">
			<c:choose>
				<c:when test="${!empty knowlegeList }">
					<c:forEach items="${knowlegeList}" var="rlist" varStatus="vs">
						<div class="temp-for-words fl_l">
							<p class="words-p-first clearfix">
								<span class="sp sty-borcolor-b skin-minimal">
								    <input type="checkbox"  id="chk_${rlist.knowlegeId }"  name="a" class="check" />
								</span>
								<span class="temp-words-span fl_l" title="${rlist.question }" style="width:80%;text-align:left;overflow:hidden; text-overflow:ellipsis; white-space:nowrap;">${rlist.question }</span>
								<a href="###" class="edit-words-temp fl_r" style="margin-right:15px;text-decoration:underline;" onclick="editKnowlege('${rlist.knowlegeId }','${rlist.groupId }')">编辑</a>
							</p>
							<div style="position:relative;"><span class="wrds-decor" style="text-indent:0;"><pre class="wrds-decor-pre"  content="shadow" style="display:inline-block;width:100%;line-height:20px;white-space: pre-wrap;word-wrap: break-word;text-align:left;">${rlist.answer }</pre></span><i class="wrds-down"></i></div>
						</div>									  
					</c:forEach>
				</c:when>
				<c:otherwise>
							<!-- <span class="zw-data"></span> -->
							<span class="no-data-mf">暂无数据</span>
				</c:otherwise>
			</c:choose>
			<div class="com-bot">
				<div class="com-page fl_r" style="margin:10px 10px 0 0">
					<div class="page">${knowlegeDto.page.pageStr}</div>
					<div class="clr_both"></div>
				</div>
			</div>
				   </div>
			</div>
		</div>
	</form>
</body>
</html>
