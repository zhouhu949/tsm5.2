<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>企业-修改资源</title>
<link href="${ctx}/static/css/dialog.css" rel="stylesheet" type="text/css" />
<style>
	.bomp-p .select {
    	position: relative;
    	float: left;
    	width: 260px;
    }
    
    .bomp-p .select dt {
    	width: 236px;
    	background: url(${ctx}/static/images/drop-down-arrow.png) 245px center no-repeat rgb(255, 255, 255);
    	font-weight: normal;
    	font-size: 12px;
    }
    
    .bomp-p .select,.sel_a{
    	font-size:12px;
    }
    
    .bomp-p .select dd{
    	box-sizing: border-box;
    	width: 100%;
    	z-index: 200;
    }
    .bomp-p .select dd ul {
    	padding: 0;
    	width: 100%;
    }
</style>
<script type="text/javascript" src="${ctx}/static/js/common/replaceWord.js"></script>
<script type="text/javascript" src="${ctx}/static/js/view/res/phoneEncrpyt.js"></script>
<script type="text/javascript">
$(function(){
console.log(window.location.href)
   $("input").each(function(item,obj){
 	   if($(obj).attr("disabled")!="disabled"){
  		  $(obj).focus();
 	   }
 	})
   
   $('input[checkProp^=chk_5]').blur(function(){
 		var tag = $(this);
 		var id = tag.attr('id');
 		if($(this).val()==''){
 			
 		}else{
 			$('#error_'+id).text('');
 			tag.parent().attr('class',"bomp-p");
 		}
 	});
	custDPCheck();
	window.onload=function(){
		var height = $(".bomp_change").height()+30;
		/* window.parent.$("#iframepage").css({"height":height+"px"}); */
	};
	
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
    
	$('input[majorWay="majorWay"]').each(function(item,obj){
		//重点关注
		$("#isMajor_").on("ifChecked",function(){
			$("#isMajor").val(1);
		});
		$("#isMajor_").on("ifUnchecked",function(){
			$("#isMajor").val(0);
		});
	})
	

	//删除
	$("a[id^=del_]").click(function(){
		var tscidId = $(this).attr("id").split("_")[1];
		  <c:choose>
          <c:when test="${dayPlanflag eq 1 }">
          window.top.pubDivDialog('确认','是否确认删除联系人?',function(){
            $.ajax({
                url:ctx+'/res/cust/delDetail',
                type:'post',
                data:{tscidId:tscidId},
                dataType:'html',
                error:function(){},
                success:function(data){
                    if(data == 1){
                        window.top.iDialogMsg("提示","删除联系人成功!");
                          var pageTypeId = window.parent.$('#pageTypeId').val();
                             if (pageTypeId == "1") {
                                 window.parent.window.taoMainPlatform($('#resCustId').val());
                             }else if(pageTypeId == "3"){
                                 window.parent.window.custFollowInfo($('#resCustId').val());
                             }                      
                        setTimeout("fresh();",1000);
                    }else{
                        window.top.iDialogMsg("提示","删除联系人失败!");
                    }
                }
            });
        });
          </c:when>
          <c:otherwise>
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
                          var pageTypeId = window.parent.$('#pageTypeId').val();
                             if (pageTypeId == "1") {
                                 window.parent.window.taoMainPlatform($('#resCustId').val());
                             }else if(pageTypeId == "3"){
                                 window.parent.window.custFollowInfo($('#resCustId').val());
                             }                      
                        setTimeout("fresh();",1000);
                    }else{
                        window.top.iDialogMsg("提示","删除联系人失败!");
                    }
                }
            });
        });
          </c:otherwise>
          </c:choose> 
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
			resourceToDW()
			return false;
		}
        $("select[disabled]").each(function(e,index){
            var $this = $(this);
            $this.attr("disabled",false);
        });
		$("#resForm").ajaxSubmit({
			dataType:'json',
			success:function(data){
            	var custId = $('#custId', window.parent.document).val();
            	retUrl(custId,data);
			}
		});
	});
	
	selectBitch();
});

function addCustQuHui(num,type,val){
	var resId = $('#resCustId').val();
	var $object = $('#error_dp').children(":first");
	if(type !='6' && type !='7' && type !='8'){
	  return;	
	}
	if(num>1){
		 window.top.iDialogMsg("提示", "重复数据多于2（含）条，请到公海查询取回!");
		 return;
	}else{
		
		
		$.ajax({
			url:ctx+'/res/resmanage/getResLimitNum',
			type:'post',
			data:{code:'DATA_10028'},
			success:function(data){
				if(data=='1'){
					window.top.iDialogMsg("提示","超出个人拥有资源限制数量！");
					return false;
				}else{
			        $.ajax({
			            url: ctx + '/res/cust/qiHui',
			            type: 'post',
			            data: {resId:resId,val:val,type:type},
			            dataType: 'json',
			            success: function (data) {
			            	var code = data.code;
			            	var custId = data.custId;
			            	retUrl(custId,data);
			            }
			        });
				}

			}
		});	

	}
}

function retUrl(custId,data){
	//alert(data);
   var oldCustId = $('#custId', window.parent.document).val();
   var pageTypeId = $('#pageTypeId', window.parent.document).val();
   $('#custId', window.parent.document).val(custId);
   if ('1' == pageTypeId) {
       var pageTypeId = window.parent.$('#pageTypeId').val();
       if (pageTypeId == "1") {
           window.parent.window.taoMainPlatform(custId);
       }
       window.top.iDialogMsg("提示", "保存成功！");
       setTimeout('closeParentPubDivDialogIframe("edit_'+oldCustId+'");', 1000);
   } else if ('2' == pageTypeId) {
       	window.parent.location.href = window.parent.location.href;
       	window.top.iDialogMsg("提示", "保存成功！");/* 加于2017-11-17 */
   }else if ('3' == pageTypeId) {
       	window.parent.window.custFollowInfo(custId);
       	setTimeout('closeParentPubDivDialogIframe("edit_'+oldCustId+'");', 1000);
       	window.top.iDialogMsg("提示", "保存成功！");/* 加于2017-11-17 */
   } else {
       if (typeof(data)=="object" && data.result == 1) {
           window.top.iDialogMsg("提示", "保存成功！");
           var currCustId = $('#resCustId').val();
           setTimeout('window.parent.loadData();closeParentPubDivDialogIframe("edit_'+currCustId+'");', 1000);
       }else if (data == 1) {
           window.top.iDialogMsg("提示", "保存成功！");
           var currCustId = $('#resCustId').val();
           setTimeout('window.parent.loadData();closeParentPubDivDialogIframe("edit_'+currCustId+'");', 1000);
       } else {
           window.top.iDialogMsg("提示", "保存失败！");
       }
   }
}

function resourceToDW(){
	var firstInput = "";
	var isShow = false;
	var mark = 0;
	$('span[name=a]').each(function(item,obj){
		firstInput = $(obj).text();
		if(firstInput !=''){
			 if(mark==0){
		 	    firstInput = $(this).attr('id').split('_')[1];
			 }
		    if(mark>=4){
		    	isShow = true;
		    }
		}
		mark = mark +1;
	})
	$('#'+firstInput).focus();
	if(isShow){
		if($('.bomp-drop').hasClass('bomp-pull') == false){
		$('.bomp-drop').parent().find('.bomp-drop-hid').slideToggle();
		$('.bomp-drop').find('label').removeClass('arrow').addClass('arrow_a');
		$('.bomp-drop').addClass('bomp-pull');
		}
	}else{
		if($('.bomp-drop').hasClass('bomp-pull') == true){
			$('.bomp-drop').parent().find('.bomp-drop-hid').slideToggle();
			$('.bomp-drop').find('label').removeClass('arrow_a').addClass('arrow');
			$('.bomp-drop').removeClass('bomp-pull');
	     }		
	}
}

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

//资源去重
function custDPCheck(){
	$('#name').attr('checkProp',$('#name').attr('checkProp')+"0");
	$('#unithome').attr('checkProp',$('#unithome').attr('checkProp')+"0");
	
	var pValiDateType = '${pValiDateType}';
	var uValiDateType = '${uValiDateType}';
	var uhValiDateType = '${uhValiDateType}';
	if($('#uValiDateType').val()!='0'){
		$('#name').attr('checkProp',$('#name').attr('checkProp')+'2');
	}
	if($('#uhValiDateType').val()!='0'){
		$('#unithome').attr('checkProp',$('#unithome').attr('checkProp')+'3');
	}	
}
function fresh(){
    $('#resForm').attr('action',ctx+'/res/cust/toEditRes?custId='+ $("#resCustId").val());
    $('#resForm').submit();
}
</script>
<script type="text/javascript" src="${ctx}/static/js/view/res/resourceCheck.js${_v}"></script>
</head>
<body>
	<form id="resForm" action="${ctx }/res/cust/editRes" method="post" class="clearfix">
          <div class='bomp-cen bomp_change project-comp-idialog-box <c:if test="${dayPlanflag eq 1 }">daily-planedit-cust-box</c:if>' id="editId">
            <c:if test="${dayPlanflag eq 1 }"><input type="hidden" name='dayPlanflag' id='dayPlanflag' value='${dayPlanflag }'></c:if>
	          　　 　<input type="hidden" name='pValiDateType' id='pValiDateType' value='${pValiDateType }'>
		　　　<input type="hidden" name='uValiDateType' id='uValiDateType' value='${uValiDateType }'>
		　　　<input type="hidden" name='uhValiDateType' id='uhValiDateType' value='${uhValiDateType }'>
			<input type="hidden" id="resCustId" name="resCustId" value="${custInfoBean.resCustId }" />
			<input type="hidden" id="idReplaceWord" name="idReplaceWord" value="${idReplaceWord }" />
            <input type="hidden" name='tanpin' id='tanpin' value='0'>			
            <div class='bomp_tit bomp_tit_a skin-minimal'><label class='lab'>企业信息</label>
			<c:if test="${!empty fieldSets && custInfoBean.status eq 3}">
			     <c:forEach items="${fieldSets}" var="field" varStatus="vs"> 
			         <c:if test="${'isMajor' eq field.fieldCode && field.enable eq 1  }">
			            	<div class="check fl_r">
								<input type="hidden" id="isMajor" name="isMajor" value="${custInfoBean.isMajor }" />
								<input type='checkbox' majorWay="majorWay" id="isMajor_" ${custInfoBean.isMajor eq 1 ? 'checked' : '' } class='fl_l' /><label class='fl_l font-s'>重点关注</label>
							</div>
			         </c:if>
			     </c:forEach>
			</c:if>				
			</div>
				<span class="bomp-mcb-tip" id="error_dp"></span>
				<div class='bomp-p'>
					<label class='lab_a fl_l'> <i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i>资源分组：</label>
					<dl class="select resGroup" data-input="[name='resGroupId']"  data-selected-node="${custInfoBean.resGroupId}">
						<dt>资源分组</dt>
						<dd>
							<ul class="resource-group-tree" data-url="${ctx}/res/group/get_res_group_json">
								<!-- 部门树 -->
							</ul>
						</dd>
				 		<input class="input-resGroup" name="resGroupId" type="hidden" value=""  checkProp="chk_1_1" />
						<span class="error" name='a' id="error_resGroupId" style="padding-left:0;"></span>				
				   	</dl>	
				</div>				
					<c:if test="${ !empty fieldSets}">
						   <c:forEach items="${fieldSets}" var="field" varStatus="vs">
						         <c:if test="${vs.index eq  6 }">
						             <div class="bomp-drop-hid">
						         </c:if>
							     <c:choose>
						             <c:when test="${'name' eq field.fieldCode && field.enable eq 1  }">
											<p class='bomp-p' id="bomp_error_${field.fieldCode }">
														   	<label class='lab_a fl_l'>
														   	   <c:choose>
														   	      <c:when test="${field.isRequired eq 1 }">
			                                                              <i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i>											   	      
														   	      </c:when>
														   	   </c:choose>
														    	${field.fieldName}：
														   	</label><input type='text'   id="${field.fieldCode }"  name="${field.fieldCode }"  value='${custInfoBean.name }'   ${((field.isRead eq 0  || isRead)  || isRead) ? '':'readonly'} checkProp="${field.isRequired eq 0?'chk_1_0':'chk_1_1' }" class='ipt_a fl_l' />
												<span  class="error" name='a'  id="error_${ field.fieldCode}"></span>
											</p>		             
						             </c:when>							     
							          <c:when test="${'companyMoney' eq field.fieldCode && field.enable eq 1}">
											<p class='bomp-p' id="bomp_error_${field.fieldCode }">
											   	<label class='lab_a fl_l'>
											   	   <c:choose>
											   	      <c:when test="${field.isRequired eq 1 }">
                                                              <i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i>											   	      
											   	      </c:when>
											   	   </c:choose>
											    	${field.fieldName}：
											   	</label>
											   	  <input type='text'  id="${field.fieldCode }"  name="${field.fieldCode }"  value='${custInfoBean.companyMoney }' checkProp="${field.isRequired eq 0?'':'chk_1_1' }"  ${(field.isRead eq 0  || isRead) ? '':'readonly'}  class='ipt_a fl_l' />
											    <span  class="error" name='a'  id="error_${ field.fieldCode}"></span>
											</p>				          
							          </c:when>
							          <c:when test="${'companyTrade' eq  field.fieldCode && field.enable eq 1 }">
											<p class='bomp-p' id="bomp_error_${field.fieldCode }">
													<label class='lab_a fl_l'>
												   	   <c:choose>
												   	      <c:when test="${field.isRequired eq 1 }">
	                                                              <i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i>											   	      
												   	      </c:when>
												   	   </c:choose>
												    	${field.fieldName}：
												   	</label>
													<select class='sel_a fl_l'  id="${field.fieldCode }"  name="${field.fieldCode }"  checkProp="${field.isRequired eq 0?'':'chk_2_1' }"  ${(field.isRead eq 0  || isRead) ? '':'disabled'} >
													 <option value="">--请选择--</option>
                                                     <c:forEach var="option" items="${optionList }">
                                                     <c:if test="${option.optionlistId ==custInfoBean.companyTrade}">
                                                     <option value="${option.optionlistId }"  selected="selected">${option.optionName }</option>
                                                     </c:if>
								                     <option value="${option.optionlistId }" >${option.optionName }</option>
							                         </c:forEach>
							<%--                     
															<option value="">-请选择-</option>
															<option value="1" ${custInfoBean.companyTrade eq '1' ? 'selected' : '' }>信息传输、软件和信息技术服务业</option>
															<option value="2" ${custInfoBean.companyTrade eq '2' ? 'selected' : '' }>采矿业</option>
															<option value="3" ${custInfoBean.companyTrade eq '3' ? 'selected' : '' }>制造业</option>
															<option value="4" ${custInfoBean.companyTrade eq '4' ? 'selected' : '' }>电力、热力、燃气及水生产和供应业</option>
															<option value="5" ${custInfoBean.companyTrade eq '5' ? 'selected' : '' }>建筑业</option>
															<option value="6" ${custInfoBean.companyTrade eq '6' ? 'selected' : '' }>批发和零售业</option>
															<option value="7" ${custInfoBean.companyTrade eq '7' ? 'selected' : '' }>交通运输、仓储和邮政业</option>
															<option value="8" ${custInfoBean.companyTrade eq '8' ? 'selected' : '' }>住宿和餐饮业</option>
															<option value="9" ${custInfoBean.companyTrade eq '9' ? 'selected' : '' }>农、林、牧、渔业</option>
															<option value="10" ${custInfoBean.companyTrade eq '10' ? 'selected' : '' }>金融业</option>
															<option value="11" ${custInfoBean.companyTrade eq '12' ? 'selected' : '' }>房地产业</option>
															<option value="12" ${custInfoBean.companyTrade eq '12' ? 'selected' : '' }>租赁和商务服务业</option>
															<option value="13" ${custInfoBean.companyTrade eq '13' ? 'selected' : '' }>科学研究和技术服务业</option>
															<option value="14" ${custInfoBean.companyTrade eq '14' ? 'selected' : '' }>水利、环境和公共设施管理业</option>
															<option value="15" ${custInfoBean.companyTrade eq '15' ? 'selected' : '' }>居民服务、修理和其他服务业</option>
															<option value="16" ${custInfoBean.companyTrade eq '16' ? 'selected' : '' }>教育</option>
															<option value="17" ${custInfoBean.companyTrade eq '17' ? 'selected' : '' }>卫生和社会工作</option>
															<option value="18" ${custInfoBean.companyTrade eq '18' ? 'selected' : '' }>文化、体育和娱乐业</option>
															<option value="19" ${custInfoBean.companyTrade eq '19' ? 'selected' : '' }>公共管理、社会保障和社会组织</option>
															<option value="20" ${custInfoBean.companyTrade eq '20' ? 'selected' : '' }>国际组织</option>
															<option value="21" ${custInfoBean.companyTrade eq '21' ? 'selected' : '' }>其他</option>
														 --%>   
														</select>
													 <span  class="error" name='a'  id="error_${ field.fieldCode}"></span>
											</p>				          
							          </c:when>
			                          <c:when test="${'companyUser' eq  field.fieldCode && field.enable eq 1 }">
											<p class='bomp-p' id="bomp_error_${field.fieldCode }">
											   	<label class='lab_a fl_l'>
											   	   <c:choose>
											   	      <c:when test="${field.isRequired eq 1 }">
                                                              <i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i>											   	      
											   	      </c:when>
											   	   </c:choose>
											    	${field.fieldName}：
											   	</label>
											<input type='text'   id="${field.fieldCode }"  name="${field.fieldCode }"  value='${custInfoBean.companyUser }'  checkProp="${field.isRequired eq 0?'':'chk_1_1' }"  ${(field.isRead eq 0  || isRead) ? '':'readonly'}  class='ipt_a fl_l' />
											 <span  class="error" name='a'  id="error_${ field.fieldCode}"></span>
											</p>                          
			                          </c:when>
							          <c:when test="${'companyFax' eq field.fieldCode && field.enable eq 1}">
											<p class='bomp-p' id="bomp_error_${field.fieldCode }">
											   	<label class='lab_a fl_l'>
											   	   <c:choose>
											   	      <c:when test="${field.isRequired eq 1 }">
                                                              <i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i>											   	      
											   	      </c:when>
											   	   </c:choose>
											    	${field.fieldName}：
											   	</label>
												<input type='text' id="${field.fieldCode }"  name="${field.fieldCode }"  value='${custInfoBean.companyFax }' checkProp="${field.isRequired eq 0?'chk_1_04':'chk_1_14' }"  ${(field.isRead eq 0  || isRead) ? '':'readonly'} class='ipt_a fl_l' />
												 <span  class="error" name='a'  id="error_${ field.fieldCode}"></span>
											</p>				          
							          </c:when>
							          <c:when test="${'keyWord' eq  field.fieldCode  && field.enable eq 1}">
											<p class='bomp-p' id="bomp_error_${field.fieldCode }">
											   	<label class='lab_a fl_l'>
											   	   <c:choose>
											   	      <c:when test="${field.isRequired eq 1 }">
                                                              <i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i>											   	      
											   	      </c:when>
											   	   </c:choose>
											    	${field.fieldName}：
											   	</label>
												<input type='text'  id="${field.fieldCode }" name="${field.fieldCode }" value='${custInfoBean.keyWord }'  checkProp="${field.isRequired eq 0?'':'chk_1_1' }" class='ipt_a fl_l' ${(field.isRead eq 0  || isRead) ? '':'readonly'}  />
												 <span  class="error" name='a'  id="error_${ field.fieldCode}"></span>
											</p>				          
							          </c:when>
			                          <c:when test="${'unithome' eq  field.fieldCode  && field.enable eq 1}">
											<p class='bomp-p' id="bomp_error_${field.fieldCode }">
												<label class='lab_a fl_l'>
											   	   <c:choose>
											   	      <c:when test="${field.isRequired eq 1 }">
                                                              <i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i>											   	      
											   	      </c:when>
											   	   </c:choose>
											    	${field.fieldName}：
											   	</label>
											   	<input type='text' value='${custInfoBean.unithome }'   id="${field.fieldCode }"  name="${field.fieldCode }" checkProp="${field.isRequired eq 0?'chk_1_0':'chk_1_1' }"class='ipt_a fl_l' ${(field.isRead eq 0  || isRead) ? '':'readonly'} />
												 <span  class="error" name='a'  id="error_${ field.fieldCode}"></span>
											</p>                          
			                          </c:when>  
							          <c:when test="${'comArea' eq field.fieldCode && field.enable eq 1}">
										    <input type="hidden" id="provinceId" name="provinceId" value="${custInfoBean.provinceId }" />
											<input type="hidden" id="cityId" name="cityId" value="${custInfoBean.cityId }" />
											<input type="hidden" id="countyId" name="countyId" value="${custInfoBean.countyId }" />
											<input type="hidden" id="chk_comArea" value="${field.isRequired}" />
											<p class='bomp-p'>
											   	<label class='lab_a fl_l'>
											   	   <c:choose>
											   	      <c:when test="${field.isRequired eq 1 }">
                                                              <i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i>											   	      
											   	      </c:when>
											   	   </c:choose>
											    	${field.fieldName}：
											   	</label>
												<select class='sel_a sel_mar fl_l' id="s_province"   ${(field.isRead eq 0  || isRead) ? '':'disabled'} >
													<option value="">-请选择-</option>
												</select>
												<select class='sel_a sel_mar fl_l' id="s_city"   ${(field.isRead eq 0  || isRead) ? '':'disabled'} >
													<option  value="">-请选择-</option>
												</select>
												<select class='sel_a sel_mar fl_l' id="s_county"    ${(field.isRead eq 0  || isRead) ? '':'disabled'} >
													<option  value="">-请选择-</option>
												</select>
                                                 <span  class="error" name='a'  id="${field.isRequired eq 0?'':'comArea' }"></span>										
											</p>	
														          
							          </c:when>
							          <c:when test="${'address' eq  field.fieldCode  && field.enable eq 1}">
											<p class='bomp-p'>
											   	<label class='lab_a fl_l'>
											   	   <c:choose>
											   	      <c:when test="${field.isRequired eq 1 }">
                                                              <i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i>											   	      
											   	      </c:when>
											   	   </c:choose>
											    	${field.fieldName}：
											   	</label>
												<input type='text'  id="${field.fieldCode }"  name="${field.fieldCode }"   value='${custInfoBean.address }'   checkProp="${field.isRequired eq 0?'':'chk_1_1' }"  ${(field.isRead eq 0  || isRead) ? '':'readonly'}  class='ipt_a w_b fl_l' />
												 <span  class="error" name='a'  id="error_${ field.fieldCode}"></span>
											</p>				          
							          </c:when>
			                          <c:when test="${'scope' eq  field.fieldCode && field.enable eq 1 }">
											<p class='bomp-p'>
											   	<label class='lab_a fl_l'>
											   	   <c:choose>
											   	      <c:when test="${field.isRequired eq 1 }">
                                                              <i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i>											   	      
											   	      </c:when>
											   	   </c:choose>
											    	${field.fieldName}：
											   	</label>
												<input type='text'  id="${field.fieldCode }"  name="${field.fieldCode }"   value='${custInfoBean.scope }'   checkProp="${field.isRequired eq 0?'':'chk_1_1' }"  ${(field.isRead eq 0  || isRead) ? '':'readonly'} class='ipt_a w_b fl_l' />
												 <span  class="error" name='a'  id="error_${ field.fieldCode}"></span>
											</p>                          
			                          </c:when>
							          <c:when test="${'remark' eq  field.fieldCode  && field.enable eq 1}">
											<p class='bomp-p'>
											   	<label class='lab_a fl_l'>
											   	   <c:choose>
											   	      <c:when test="${field.isRequired eq 1 }">
                                                              <i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i>											   	      
											   	      </c:when>
											   	   </c:choose>
											    	${field.fieldName}：
											   	</label>
												<input type='text'  id="${field.fieldCode }"  name="${field.fieldCode }"   value='${custInfoBean.remark }'   checkProp="${field.isRequired eq 0?'':'chk_1_1' }"  ${(field.isRead eq 0  || isRead) ? '':'readonly'}  class='ipt_a w_b fl_l' />
												 <span  class="error" name='a'  id="error_${ field.fieldCode}"></span>
											</p>  							          
							          </c:when>
			                     </c:choose>
			                     <c:if test="${field.fieldCode.toString().contains('defined') && field.enable eq 1}">

						    	   <c:choose>
						            <c:when test="${ field.dataType eq 1 }">
											<p class='bomp-p'>
											   	<label class='lab_a fl_l'>
											   		<c:choose>
											   	      	<c:when test="${field.isRequired eq 1 }">
						                                	<i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i>											   	      
											   	      	</c:when>
											   		</c:choose>
											    	${field.fieldName}：
											   	</label>
											   	<input type='text'   id="${field.fieldCode }"  name="${field.fieldCode }"  value='${field.showValue }'  checkProp="${field.isRequired eq 0?'':'chk_1_1' }" class='ipt_a w_b fl_l'  <c:if test="${field.isRead eq 1 }">readonly</c:if>/>
												<span class="error"  name='a' id="error_${ field.fieldCode}"></span>
											</p> 		                                    
						            </c:when>
						            <c:when test="${ field.dataType eq 2 }">
											<p class='bomp-p'>
											   	<label class='lab_a fl_l'>
											   	   	<c:choose>
											   	    	<c:when test="${field.isRequired eq 1 }">
						                                    <i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i>											   	      
											   	    	</c:when>
											   	   	</c:choose>
											    	${field.fieldName}：
											   	</label>
											   	<input type='text'  <c:if test="${field.isRead eq 0 }">onclick="WdatePicker()"</c:if>  id="${field.fieldCode }"  name="${field.fieldCode }"  value='${field.showValue }'  checkProp="${field.isRequired eq 0?'':'chk_5_1' }" class='ipt_a w_b fl_l' <c:if test="${field.isRead eq 1 }">readonly</c:if> />
												<span class="error"  name='a' id="error_${ field.fieldCode}"></span>
											</p>		                                    
						            </c:when>
						            <c:when test="${ field.dataType eq 3 }">
											<p class='bomp-p' >
												<label class='lab_a fl_l'><c:if test="${field.isRequired eq 1 }"><i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i></c:if>${field.fieldName}：</label>
												<select name="${field.fieldCode }" class='sel_a fl_l' <c:if test="${field.isRequired eq 1 }">checkProp="chk_2_1"</c:if> <c:if test="${field.isRead eq 1 }">disabled</c:if>>
												    <option value="">--请选择--</option>
													<c:forEach var="os" items="${field.optionList }">
														<option value="${os.optionlistId}"  <c:if test="${field.showValue eq os.optionlistId}">selected="selected"</c:if>>${os.optionName }</option>
													</c:forEach>
												</select>	
												<span class="error" name='a' id="error_${field.fieldCode }"></span>				
											</p>
						            </c:when>
						            <c:when test="${ field.dataType eq 4 }">
						            	<div class='bomp-p' >
											<label class='lab_a fl_l'><c:if test="${field.isRequired eq 1 }"><i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i></c:if>${field.fieldName}：</label>
						                	<dl class="select pos2" data-input="[name='${ field.fieldCode}']" data-multi="true" <c:if test="${field.isRead eq 1 }">disabled</c:if>>
						                	 	<c:set var="optionNames"  value="" />
						                		<c:forEach items="${field.optionList }" var="os">
													<c:if test="${field.showValue.contains(os.optionlistId)}">
														<c:set var="optionNames"  value="${optionNames},${os.optionName } " />
													</c:if>
												</c:forEach>
												<dt>
													<c:choose>
											   	    	<c:when test="${empty optionNames }">
						                                    --请选择--											   	      
											   	    	</c:when>
											   	    	<c:otherwise>
											   	    		${optionNames.substring(1) }
											   	    	</c:otherwise>
											   	   	</c:choose>
												</dt>
												<dd>
													<ul>
														<c:forEach items="${field.optionList }" var="os">
															<li <c:if test="${field.showValue.contains(os.optionlistId)}">class="selected"</c:if>><a href="javascript:void(0);"  data-value="${os.optionlistId}" title="${os.optionName }">${os.optionName }</a></li>
														</c:forEach>
													</ul>
												</dd>
											</dl>
						                    <input type="hidden" name="${ field.fieldCode}" id="s_${ field.fieldCode}" value="${field.showValue }" checkProp="${field.isRequired eq 0?'':'chk_1_1' }" />
											<span class="error" name='a' id="error_${field.fieldCode }"></span>
										</div>
						            </c:when>
						              </c:choose>
					            </c:if>
						   </c:forEach>
						   
					</c:if>
			</div>
			<div class="bomp-drop">
				<span class="sp fl_l"></span>
				<label class="arrow a"><em>◆</em><span>◆</span></label>
				<label class="arrow b"><em>◆</em><span>◆</span></label>
				<span class="sp fl_r"></span>
			</div>
			<!-- 新增联系人 开始-->
			<div class='bomp_tit bomp_tit_a'><label class='lab'>联系人信息</label>
			<c:choose>
			<c:when test="${dayPlanflag eq 1 }">
				<div class="add"><label class="add-label"><b>+</b><a href="${ctx }/res/cust/addDetail?rciId=${ custInfoBean.resCustId }&dayPlanflag=${ dayPlanflag }">新增联系人</a></label>
				</c:when>
				<c:otherwise>
				<div class="add"><label class="add-label"><b>+</b><a href="${ctx }/res/cust/addDetail?rciId=${ custInfoBean.resCustId }">新增联系人</a></label>
				</c:otherwise>
				</c:choose>
					<div class="drop" id="addDiv" style="display:none;">
						
					</div>
				</div>
			</div>
			<!-- 新增联系人 结束-->
			<div class='com-table bomp-table-a fl_l' style="width:89%;margin-left:30px;">
				<table width='100%' cellspacing='0' cellpadding='0' class='sty-borcolor-a sty-borcolor-c com-table-b com-radius'>
					<thead>
						<tr class='sty-bgcolor-b'>
							<th><span class='sp sty-borcolor-b'>姓名</span></th> 
							<th><span class='sp sty-borcolor-b'>电话</span></th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>              
						<c:choose>
							<c:when test="${!empty details }">
								<c:forEach items="${details }" var="detail">
									<tr>
										<td><div class='overflow_hidden w130' title='${detail.name }'>${detail.name }</div></td>
										<td><div class='overflow_hidden w150' title='${empty detail.telphone? detail.telphonebak:detail.telphone }' phone='tel'>${empty detail.telphone? detail.telphonebak:detail.telphone }</div></td>
										<td>
											<div class="bomp-edit overflow_hidden w90">
												<c:choose>
												<c:when test="${dayPlanflag eq 1 }">
												<a href="${ctx }/res/cust/editDetail?rciId=${ custInfoBean.resCustId }&tscidId=${detail.tscidId }&dayPlanflag=${ dayPlanflag }" id="edti_${detail.tscidId }" class='lk'>编辑</a>
												</c:when>
												<c:otherwise>
												<a href="${ctx }/res/cust/editDetail?rciId=${ custInfoBean.resCustId }&tscidId=${detail.tscidId }" id="edti_${detail.tscidId }" class='lk'>编辑</a>
												</c:otherwise>
												</c:choose>
												<div class="drop" id="editDiv_${detail.tscidId }" style="display:none;">
												</div>
												<c:if test="${detail.isDefault ne 1 }">
													<a href='###' id="del_${detail.tscidId }" class='lk'>删除</a>
												</c:if>
											</div>
										</td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr class="no_data_tr">
									<td>暂无联系人！</td>
								</tr>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
			</div>
			<div class='bomb-btn project-comp-idialog-btnbox'>
				<label class='bomb-btn-cen'>
					<a href="###" id="saveResBtn" class="com-btna bomp-btna com-btna-sty sure-btn fl_l"><label>保存</label></a>
					<a href="###" id="cacleResBtn" class="com-btna bomp-btna cancel-btn fl_l"><label>取消</label></a>
				</label>
			</div>
	</form>
</body>
</html>
