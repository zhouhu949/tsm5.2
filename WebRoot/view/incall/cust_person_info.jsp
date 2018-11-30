<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<link href="${ctx}/static/css/dialog.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/static/js/common/replaceWord.js"></script>
<script type="text/javascript" src="${ctx}/static/js/view/res/phoneEncrpyt.js"></script>

<script type="text/javascript">
$(function(){
	window.onload=function(){
		var height = $(".bomp_change").height()+30;
		window.parent.$("#iframepage").css({"height":height+"px"});
	};
	
	$('#addDetail').click(function(){
		var getTimestamp=new Date().getTime();  
		 $('li[name$=toSearchRes]').addClass('li_click').siblings('li').removeClass('li_click');
		 window.parent.$('#iframepage').attr('src',ctx +'/res/incall/addDetail?rciId='+$('#resCustId').val()+'&v='+getTimestamp);
	})
	
    // 企业信息下拉效果
    $('.bomp-drop').each(function(i,item){
    	$(item).click(function(){
    		var height = $(".bomp_change").height()+30;
    		var height_hid = $(".bomp-drop-hid").height();
    		if($(this).hasClass('bomp-pull') == false){
    			window.parent.$("#iframepage").css({"height":height+height_hid+"px"});
    			$(this).parent().find('.bomp-drop-hid').eq(i).slideDown();
    			$(this).find('label').removeClass('arrow').addClass('arrow_a');
    			$(this).addClass('bomp-pull');
    		}else{
    			window.parent.$("#iframepage").css({"height":height-height_hid+"px"});
    			$(this).parent().find('.bomp-drop-hid').eq(i).slideUp();
    			$(this).find('label').removeClass('arrow_a').addClass('arrow');
    			$(this).removeClass('bomp-pull');
    		}
    	});
    });
	
	$('input[timeWay="timeWay"]').each(function(item,obj){
		$(obj).dateRangePicker({
			showShortcuts:false,
			singleDate:true,
			showShortcuts:false

	    }).bind('datepicker-change',function(event,obj){
			$(obj).val(obj)
			setErrMsg($(this),'',false)
		});
	})
	
	$('input[majorWay="majorWay"]').each(function(item,obj){
		//重点关注
		$("#isMajor_").on("ifChecked",function(){
			$("#isMajor").val(1);
		});
		$("#isMajor_").on("ifUnchecked",function(){
			$("#isMajor").val(0);
		});
	})

	   	    //设置是否重点关注
   	    $('#majorId').live('click',function(){
   	    	var custId = $('#resCustId').val()
			if(custId=='' || custId==null){return false;}
   	    	var isMajar = $('#majorId').attr('major')=='1'?'0':'1';
   	    	$.ajax({
   	    		url:ctx+'/res/tao/setMajor',
   	    		type:'post',
   	    		data:{custId:custId,isMajar:isMajar},
   	    		success:function(data){
   	    			var isMajar = $('#majorId').attr('major')=='1'?'0':'1';
   	    			if(data=='0'){
   	    				if($('#majorId').hasClass('icon-focus-atten')==true){
   	    					$('#majorId').removeClass('icon-focus-atten');
  	    					    $('#majorId').addClass('icon-nofocus');
   	    				}else{
  	    					 $('#majorId').removeClass('icon-nofocus');
   	    					$('#majorId').addClass('icon-focus-atten');
   	    				}
   	    			    $('#majorId').attr('major',isMajar);
   	    			}
   	    		}
   	    	});
   	    })

    $('a[id^=edti_]').click(function(){
    	var getTimestamp=new Date().getTime();
    	var tscidId = $(this).attr("id").split("_")[1];
    	var resCustId = $("#resCustId").val();
    	window.parent.$('#iframepage').attr('src',ctx+'/res/incall/editDetail?rciId='+resCustId+"&tscidId="+tscidId+"&v="+getTimestamp);
    });
	
	//删除
	$("a[id^=del_]").click(function(){
		var tscidId = $(this).attr("id").split("_")[1];
		pubDivDialog('确认','是否确认删除联系人?',function(){
			$.ajax({
				url:ctx+'/res/cust/delDetail',
				type:'post',
				data:{tscidId:tscidId},
				dataType:'html',
				error:function(){},
				success:function(data){
					if(data == 1){
						window.top.iDialogMsg("提示","删除联系人成功!");
						var getTimestamp=new Date().getTime();  
						 $('li[name$=toSearchRes]').addClass('li_click').siblings('li').removeClass('li_click');
						 window.parent.$('#iframepage').attr('src',ctx+'/res/incall/toSearchRes?custId='+$('#resCustId').val()+'&v='+getTimestamp);
					}else{
						window.top.iDialogMsg("提示","删除联系人失败!");
						var getTimestamp=new Date().getTime();  
						 $('li[name$=toSearchRes]').addClass('li_click').siblings('li').removeClass('li_click');
						 window.parent.$('#iframepage').attr('src',ctx+'/res/incall/toSearchRes?custId='+$('#resCustId').val()+'&v='+getTimestamp);
					}
				}
			});
		});
	});
	
	//加载省、市、区
	initArea();
	//选择省
	$("#s_province").change(function(){
		$("#s_city").html('<option value="" selected="selected">-请选择-</option>');
		$("#s_county").html('<option value="" selected="selected">-请选择-</option>');
		var provinceId = $(this).find("option:selected").val();
		$("#provinceId").val(provinceId);
		$("#cityId").val("");
		$("#countyId").val("");
	    isCity(provinceId)
		if(provinceId != null && provinceId != ''){
			$.ajax({
				url:ctx+'/common/area/city',
				type:'post',
				data:{pid:provinceId},
				dataType:'json',
				success:function(data){
					$.each(data,function(index,obj){
						$("#s_city").append('<option value="'+obj.cid+'">'+obj.cname+'</option>');
					});
				}
			});
		}
	});
	//选择市
	$("#s_city").change(function(){
		$("#s_county").html('<option value="" selected="selected">-请选择-</option>');
		var cityId = $(this).find("option:selected").val();
		$("#cityId").val(cityId);
		$("#countyId").val("");
		isCity(cityId)
		if(cityId != null && cityId != ''){
			$.ajax({
				url:ctx+'/common/area/county',
				type:'post',
				data:{cid:cityId},
				dataType:'json',
				success:function(data){
					$.each(data,function(index,obj){
						$("#s_county").append('<option value="'+obj.oid+'">'+obj.oname+'</option>');
					});
				}
			});
		}
	});
	//选择区
	$("#s_county").change(function(){
		var countyId = $(this).find("option:selected").val();
		$("#countyId").val(countyId);
	});
	
	$('#editId').click(function(){
		var getTimestamp=new Date().getTime();  
		 window.parent.$('li[name$=toEditRes]').addClass('li_click').siblings('li').removeClass('li_click');
		 window.parent.$('#iframepage').attr('src',ctx+'/res/incall/toEditRes?custId='+$('#resCustId').val()+'&v='+getTimestamp);

	})	
	//取消
	$("#cacleResBtn").click(function(){
		closeParentPubDivDialogIframe('edit_${custInfoBean.resCustId }');
	});
	//确定
	$("#saveResBtn").click(function(){
		if($("form").length > 1){
			return false;
		}
		var isArea = true;
		var isSubmited = false;
		var comArea = $('#chk_comArea').val();
		var isSubmited = false;
		if(comArea != undefined && comArea=='1'){
		   if(!isRequireArea()){
			   isArea = false;
		   }
		}
		if(!isSubmited && (!checkForm() || !isArea)){
			isSubmited =true;
			return false;
		}
		$("#resForm").ajaxSubmit({
			dataType:'json',
			success:function(data){
				var pageTypeId = window.parent.$('#pageTypeId').val();
				if('1'== pageTypeId){
					var custId = $('#custId', window.parent.document).val();
					window.parent.window.taoMainPlatform(custId);
					setTimeout('closeParentPubDivDialogIframe("edit_"+$("#custId", window.parent.document).val());',1000);
				}else if('2' == pageTypeId){
					window.parent.location.href = window.parent.location.href;
				}else{
					if(data == 1){
						window.top.iDialogMsg("提示","保存成功！");
						setTimeout('window.parent.document.forms[0].submit();',1000);
					}else{
						window.top.iDialogMsg("提示","保存失败！");
					}
				}
			}
		});
	});
});


function initArea(){
	var provinceId = $("#provinceId").val();
	var cityId = $("#cityId").val();
	var countyId = $("#countyId").val();
	//加载省
	$.ajax({
		url:ctx+'/common/area/province',
		type:'post',
		dataType:'json',
		success:function(data){
			$.each(data,function(index,obj){
				$("#s_province").append('<option value="'+obj.pid+'">'+obj.pname+'</option>');
			});
			if(provinceId != null && provinceId != ''){
				$("#s_province").find("option[value="+provinceId+"]").attr("selected",true);
			}
		}
	});
	//加载市
	if(provinceId != null && provinceId != ''){
		$.ajax({
			url:ctx+'/common/area/city',
			type:'post',
			data:{pid:provinceId},
			dataType:'json',
			success:function(data){
				$.each(data,function(index,obj){
					$("#s_city").append('<option value="'+obj.cid+'">'+obj.cname+'</option>');
				});
				if(cityId != null && cityId != ''){
					$("#s_city").find("option[value="+cityId+"]").attr("selected",true);
				}
			}
		});
	}
	//加载区
	if(cityId != null && cityId != ''){
		$.ajax({
			url:ctx+'/common/area/county',
			type:'post',
			data:{cid:cityId},
			dataType:'json',
			success:function(data){
				$.each(data,function(index,obj){
					$("#s_county").append('<option value="'+obj.oid+'">'+obj.oname+'</option>');
				});
				if(countyId != null && countyId != ''){
					$("#s_county").find("option[value="+countyId+"]").attr("selected",true);
				}
			}
		});
	}
}

function isRequireArea(){
	var provinceId = $("#provinceId").val();
	var cityId = $("#cityId").val();
	var countyId = $("#countyId").val();
	var isSucc = true;
	var comArea = $('#chk_comArea').val();
	if(comArea == undefined || comArea!='1'){
		return  false;
	}
	if(provinceId == null || provinceId ==''||cityId == null || cityId ==''){
		$('#comArea').html('必填项');
		$('#comArea').focus();
		$('#comArea').parent().attr('class',"bomp-p bomp-error");
		isSucc =false;
	}else{
		$('#comArea').html('');
		$('#comArea').parent().attr('class',"bomp-p");
		isSucc =true;
	}
	return isSucc;
}

function isCity(val ){
	var isSucc = true;
	var comArea = $('#chk_comArea').val();
	if(comArea == undefined || comArea!='1'){
		return  false;
	}
	if(val == null || val ==''){
		$('#comArea').html('必填项');
		$('#comArea').focus();
		$('#comArea').parent().attr('class',"bomp-p bomp-error");
		isSucc =false;
	}else{
		$('#comArea').html('');
		$('#comArea').parent().attr('class',"bomp-p");
		isSucc =true;
	}
	return isSucc;
}

</script>
</head>
<body>
		 <c:set var= "mark" value="0"></c:set>
		    <c:set var= "sourceName" value="${pname }&nbsp;&nbsp;${cname }&nbsp;&nbsp;${oname }"></c:set>
          <div class='bomp-cen bomp_change bomp_ica'>
			<input type="hidden" id="resCustId" name="resCustId" value="${custInfoBean.resCustId }" />
			<input type="hidden" id="idReplaceWord" name="idReplaceWord" value="${idReplaceWord }" />
            <div class='bomp_tit bomp_tit_a'><label class='lab'>客户信息</label>
				
<%-- 					<a href="###" class="${'1' eq custInfoBean.isMajor ? 'icon-focus-atten':'icon-nofocus'}" id="majorId" major="${custInfoBean.isMajor }"></a> --%>
					
				<c:if test="${CommonOwnerEditAuth eq 0}"></c:if>
				<c:if test="${CommonOwnerEditAuth eq 1}"><label class="btn"><a  data-authority="base_custEdit" href="###" class="icon-edit bomp_ica_editbtn" id="editId"></a></label></c:if>

					
							
			</div>
			<div class="bomp_ica_save">
<!-- 				<p class='bomp-p'> -->
<%-- 					<label class='lab_a fl_l'>客户名称：</label><span class="fl_l" style="display:inline-block;height:30px;line-height:28px;width:260px;overflow:hidden;font-size:12px;">${custInfoBean.name }</span> --%>
<!-- 				</p> -->
					<c:if test="${ !empty fieldSets}">
						   <c:forEach items="${fieldSets}" var="fieldSet" varStatus="vs">
			     			 	 <c:if test="${mark eq 4 }">
						             <label class="bomp-drop-hid">
						             <c:set var="mark" value="${mark+1 }" />
						         </c:if>						         
								<c:choose>
									<c:when test="${fieldSet.fieldCode eq 'comArea'}">
										 <c:if test="${ !empty pname }">
									      <c:set var="mark" value="${mark+1 }" />
											<p class="bomp-p">
											     <label class='lab_a fl_l'>${fieldSet.fieldName}：</label>
											     <span title="${sourceName}" class="fl_l" style="display:inline-block;height:30px;line-height:28px;width:260px;overflow:hidden;font-size:12px;">${sourceName}</span>
											</p>
										 </c:if>
									</c:when>
									<c:when test="${fieldSet.fieldCode eq 'sex' && !empty custInfoBean[fieldSet.fieldCode]}">
									   <c:set var="mark" value="${mark+1 }" />
											<p class="bomp-p">
											     <label class='lab_a fl_l'>${fieldSet.fieldName}：</label>
											     <span title="${custInfoBean[fieldSet.fieldCode] eq 1?'男':'女'}" class="fl_l" style="display:inline-block;height:30px;line-height:28px;width:260px;overflow:hidden;font-size:12px;">${custInfoBean[fieldSet.fieldCode] eq 1?'男':'女'}</span>
											</p>
									</c:when>									
									<c:when test="${fieldSet.fieldCode eq 'birthday' && !empty custInfoBean[fieldSet.fieldCode] }">
											<c:set var="mark" value="${mark+1 }" />
											<p class="bomp-p">
												<label  class='lab_a fl_l'>${fieldSet.fieldName}：</label>
												<span class="fl_l" style="display:inline-block;height:30px;line-height:28px;width:260px;overflow:hidden;font-size:12px;">
												   <fmt:formatDate value="${custInfoBean.birthday}" pattern="yyyy-MM-dd"/>
												</span>
											</p>
									</c:when>									
									<c:when test="${fieldSet.fieldCode eq 'isMajor' }">
									</c:when>									
									<c:otherwise>
									<c:if test="${ '' ne custInfoBean[fieldSet.fieldCode] && !empty custInfoBean[fieldSet.fieldCode] && fieldSet.fieldCode eq 'companyTrade'}">
									  <c:set var="mark" value="${mark+1 }" />
										<p class="bomp-p">
											<label class='lab_a fl_l'>${fieldSet.fieldName}：</label>
											   <span class="fl_l" style="display:inline-block;height:30px;line-height:28px;width:260px;overflow:hidden;font-size:12px;">
											   <c:choose>
											      <c:when test="${custInfoBean[fieldSet.fieldCode] eq '1'}">信息传输、软件和信息技术服务业</c:when>
											      <c:when test="${custInfoBean[fieldSet.fieldCode] eq '2'}">采矿业</c:when>
											      <c:when test="${custInfoBean[fieldSet.fieldCode] eq '3'}">制造</c:when>
											      <c:when test="${custInfoBean[fieldSet.fieldCode] eq '4'}">电力、热力、燃气及水生产和供应业</c:when>
											      <c:when test="${custInfoBean[fieldSet.fieldCode] eq '5'}">建筑业</c:when>
											      <c:when test="${custInfoBean[fieldSet.fieldCode] eq '6'}">批发和零售业</c:when>
											      <c:when test="${custInfoBean[fieldSet.fieldCode] eq '7'}">交通运输、仓储和邮政业</c:when>
											      <c:when test="${custInfoBean[fieldSet.fieldCode] eq '8'}">住宿和餐饮业</c:when>
											      <c:when test="${custInfoBean[fieldSet.fieldCode] eq '9'}">农、林、牧、渔业</c:when>
											      <c:when test="${custInfoBean[fieldSet.fieldCode] eq '10'}">金融业</c:when>
											      <c:when test="${custInfoBean[fieldSet.fieldCode] eq '11'}">房地产业</c:when>
											      <c:when test="${custInfoBean[fieldSet.fieldCode] eq '12'}">租赁和商务服务业</c:when>
											      <c:when test="${custInfoBean[fieldSet.fieldCode] eq '13'}">科学研究和技术服务业</c:when>
											      <c:when test="${custInfoBean[fieldSet.fieldCode] eq '14'}">水利、环境和公共设施管理业</c:when>
											      <c:when test="${custInfoBean[fieldSet.fieldCode] eq '15'}">居民服务、修理和其他服务业</c:when>
											      <c:when test="${custInfoBean[fieldSet.fieldCode] eq '16'}">教育</c:when>
											      <c:when test="${custInfoBean[fieldSet.fieldCode] eq '17'}">卫生和社会工作</c:when>
											      <c:when test="${custInfoBean[fieldSet.fieldCode] eq '18'}">文化、体育和娱乐业</c:when>
											      <c:when test="${custInfoBean[fieldSet.fieldCode] eq '19'}">公共管理、社会保障和社会组织</c:when>
											      <c:when test="${custInfoBean[fieldSet.fieldCode] eq '20'}">国际组织</c:when>
											      <c:when test="${custInfoBean[fieldSet.fieldCode] eq '21'}">其他</c:when>
											      <c:otherwise></c:otherwise>
											 </c:choose>	
											 </span>								
										</p>
									</c:if>
									<c:if test="${ '' ne custInfoBean[fieldSet.fieldCode] && !empty custInfoBean[fieldSet.fieldCode] && fieldSet.fieldCode ne 'companyTrade'}">
									  <c:set var="mark" value="${mark+1 }" />
										<p class="bomp-p">
											<label class='lab_a fl_l'>${fieldSet.fieldName}：</label>
											<c:choose>
											   <c:when test="${  fieldSet.fieldCode  eq 'mobilephone' or  fieldSet.fieldCode  eq 'telphone' or  fieldSet.fieldCode  eq 'telphonebak'}">
		                                            <span phone='tel' title="${custInfo[fieldSet.fieldCode]}" class="fl_l" style="display:inline-block;height:30px;line-height:28px;width:260px;overflow:hidden;font-size:12px;">${custInfoBean[fieldSet.fieldCode]}</span>												   
											   </c:when>
												<c:when test="${  fieldSet.fieldCode  eq 'unithome'}">
												   <span title="${custInfoBean[fieldSet.fieldCode]}">
													  <a href="###" onclick="showPublicUrl('${custInfoBean[fieldSet.fieldCode]}')">${custInfoBean[fieldSet.fieldCode]}</a>
												   </span>
												</c:when>
												<c:when test="${fieldSet.fieldCode eq 'defined16' }">
													<c:if test="${!empty custInfoBean.showdefined16 }">
														<span title="${custInfoBean.showdefined16 }" class="fl_l" style="display:inline-block;height:30px;line-height:28px;width:260px;overflow:hidden;font-size:12px;">${custInfoBean.showdefined16 }</span>
													</c:if>
												</c:when>
												<c:when test="${fieldSet.fieldCode eq 'defined17' }">
													<c:if test="${!empty custInfoBean.showdefined17 }">
														<span title="${custInfoBean.showdefined17 }" class="fl_l" style="display:inline-block;height:30px;line-height:28px;width:260px;overflow:hidden;font-size:12px;">${custInfoBean.showdefined17 }</span>
													</c:if>
												</c:when>
												<c:when test="${fieldSet.fieldCode eq 'defined18' }">
													<c:if test="${!empty custInfoBean.showdefined18 }">
														<span title="${custInfoBean.showdefined18 }" class="fl_l" style="display:inline-block;height:30px;line-height:28px;width:260px;overflow:hidden;font-size:12px;">${custInfoBean.showdefined18 }</span>
													</c:if>
												</c:when>
											   <c:otherwise>
		                                            <span class="fl_l" style="display:inline-block;height:30px;line-height:28px;width:260px;overflow:hidden;font-size:12px;">	
		                                                ${custInfoBean[fieldSet.fieldCode]}	
		                                            </span>			                                           									
											   </c:otherwise>
											</c:choose> 											
										</p>
									</c:if>							
									</c:otherwise>
							     </c:choose>
						   </c:forEach>
					</c:if>
				<p class='bomp-p'>
					<label class='lab_a fl_l'>资源分组：</label>
						<c:forEach var="group" items="${groupList }">
						    <c:choose>
						       <c:when test="${custInfoBean.resGroupId eq group.resGroupId }">
						          <span class="fl_l" style="display:inline-block;height:30px;line-height:28px;width:260px;overflow:hidden;font-size:12px;">${group.groupName }</span>
						       </c:when>
						       <c:otherwise></c:otherwise>
						    </c:choose>
						</c:forEach>
				</p>
				</label>
			</div>
			<div class="bomp-drop">
				<span class="sp fl_l"></span>
				<label class="arrow a"><em>◆</em><span>◆</span></label>
				<label class="arrow b"><em>◆</em><span>◆</span></label>
				<span class="sp fl_r"></span>
			</div>			
	</div>
</body>
</html>
