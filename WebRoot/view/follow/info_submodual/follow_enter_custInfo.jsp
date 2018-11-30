<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp"%>
<%@ page import="com.qftx.base.shiro.ShiroUtil" %>
<% pageContext.setAttribute("shiroUser", ShiroUtil.getShiroUser());%>
<script type="text/javascript" src="${ctx}/static/js/common/replaceWord.js${_v}"></script>
<style>
	.no_tel{background:#b6b6b6 !important;cursor:default;}
</style>
<!-- 企业客户 -->
<div class="card fl_l">
<input type="hidden" id="idReplaceWord" value="${idReplaceWord}" />
			<c:set var= "sourceName" value="${pname }&nbsp;&nbsp;${cname }&nbsp;&nbsp;${oname }"></c:set>
  			 <c:set var= "mark" value="0"></c:set>
			<p class="p_a fl_l">
				<label title="${custEntity.name}">${custEntity.name}</label>
				<i class="${'1' eq custEntity.isMajor ? 'icon-focus-atten':'icon-nofocus'}" id="majorId" major="${custEntity.isMajor }"></i>
				<i class="icon-edit demoBtn_b" title="编辑1"></i>
			</p>
			<c:forEach items="${fieldSetList}" var="fieldSet" varStatus="vs">
     			 	 <c:if test="${mark eq 4 }">
			             <label class="sty-hid fl_l">
			             <c:set var="mark" value="${mark+1 }" />
			         </c:if>			
						<c:choose>
							<c:when test="${fieldSet.fieldCode eq 'comArea'}">
								 <c:if test="${ !empty pname }">
							      <c:set var="mark" value="${mark+1 }" />
									<p class="p_b fl_l">
									     <label>${fieldSet.fieldName}：</label><span title="${sourceName}">${sourceName}</span>
									</p>
								 </c:if>
							</c:when>
							<c:when test="${fieldSet.fieldCode eq 'isMajor' }">
							</c:when>
							<c:when test="${fieldSet.fieldCode eq 'name' }">
							</c:when>	
							<c:otherwise>
							<c:if test="${ '' ne custEntity[fieldSet.fieldCode] && !empty custEntity[fieldSet.fieldCode] && fieldSet.fieldCode eq 'companyTrade'}">
							  <c:set var="mark" value="${mark+1 }" />
								<p class="p_b fl_l">
									<label>${fieldSet.fieldName}：</label>
									   <c:choose>
									      <c:when test="${custEntity[fieldSet.fieldCode] eq '1'}"><span title="信息传输、软件和信息技术服务业">信息传输、软件和信息技术服务业</span></c:when>
									      <c:when test="${custEntity[fieldSet.fieldCode] eq '2'}"><span title="采矿业">采矿业</span></c:when>
									      <c:when test="${custEntity[fieldSet.fieldCode] eq '3'}"><span title="制造">制造</span></c:when>
									      <c:when test="${custEntity[fieldSet.fieldCode] eq '4'}"><span title="电力、热力、燃气及水生产和供应业">电力、热力、燃气及水生产和供应业</span></c:when>
									      <c:when test="${custEntity[fieldSet.fieldCode] eq '5'}"><span title="建筑业">建筑业</span></c:when>
									      <c:when test="${custEntity[fieldSet.fieldCode] eq '6'}"><span title="批发和零售业">批发和零售业</span></c:when>
									      <c:when test="${custEntity[fieldSet.fieldCode] eq '7'}"><span title="交通运输、仓储和邮政业">交通运输、仓储和邮政业</span></c:when>
									      <c:when test="${custEntity[fieldSet.fieldCode] eq '8'}"><span title="住宿和餐饮业">住宿和餐饮业</span></c:when>
									      <c:when test="${custEntity[fieldSet.fieldCode] eq '9'}"><span title="农、林、牧、渔业">农、林、牧、渔业</span></c:when>
									      <c:when test="${custEntity[fieldSet.fieldCode] eq '10'}"><span title="金融业">金融业</span></c:when>
									      <c:when test="${custEntity[fieldSet.fieldCode] eq '11'}"><span title="房地产业">房地产业</span></c:when>
									      <c:when test="${custEntity[fieldSet.fieldCode] eq '12'}"><span title="租赁和商务服务业">租赁和商务服务业</span></c:when>
									      <c:when test="${custEntity[fieldSet.fieldCode] eq '13'}"><span title="科学研究和技术服务业">科学研究和技术服务业</span></c:when>
									      <c:when test="${custEntity[fieldSet.fieldCode] eq '14'}"><span title="水利、环境和公共设施管理业">水利、环境和公共设施管理业</span></c:when>
									      <c:when test="${custEntity[fieldSet.fieldCode] eq '15'}"><span title="居民服务、修理和其他服务业">居民服务、修理和其他服务业</span></c:when>
									      <c:when test="${custEntity[fieldSet.fieldCode] eq '16'}"><span title="教育">教育</span></c:when>
									      <c:when test="${custEntity[fieldSet.fieldCode] eq '17'}"><span title="卫生和社会工作">卫生和社会工作</span></c:when>
									      <c:when test="${custEntity[fieldSet.fieldCode] eq '18'}"><span title="文化、体育和娱乐业">文化、体育和娱乐业</span></c:when>
									      <c:when test="${custEntity[fieldSet.fieldCode] eq '19'}"><span title="公共管理、社会保障和社会组织">公共管理、社会保障和社会组织</span></c:when>
									      <c:when test="${custEntity[fieldSet.fieldCode] eq '20'}"><span title="国际组织">国际组织</span></c:when>
									      <c:when test="${custEntity[fieldSet.fieldCode] eq '21'}"><span title="其他">其他</span></c:when>
									      <c:otherwise></c:otherwise>
									 </c:choose>									
								</p>
							</c:if>
							<c:if test="${ '' ne custEntity[fieldSet.fieldCode] && !empty custEntity[fieldSet.fieldCode] && fieldSet.fieldCode ne 'companyTrade'}">
							  <c:set var="mark" value="${mark+1 }" />
								<p class="p_b fl_l">
									<label>${fieldSet.fieldName}：</label>
									<c:choose>
									   <c:when test="${  fieldSet.fieldCode  eq 'mobilephone' or  fieldSet.fieldCode  eq 'telphone'  or  fieldSet.fieldCode  eq 'alternatePhone2'  or  fieldSet.fieldCode  eq 'telphonebak'}">
									       <span title="${custEntity[fieldSet.fieldCode]}" phone='tel'>${custEntity[fieldSet.fieldCode]}</span>
									   </c:when>
									   <c:when test="${fieldSet.fieldCode eq 'unithome' }">
												<span title="${custEntity[fieldSet.fieldCode] }">
													<a href="###" onclick="showPublicUrl('${custEntity[fieldSet.fieldCode] }')">${custEntity[fieldSet.fieldCode] }</a>
												</span>
									   </c:when>
									   <c:otherwise>
                                           <span title="${custEntity[fieldSet.fieldCode]}">${custEntity[fieldSet.fieldCode]}</span>									
									   </c:otherwise>
									</c:choose>
								</p>
							</c:if>							
							</c:otherwise>
						</c:choose>
			</c:forEach>
			</label>
			<c:if test="${mark gt 3 }">
				<i class="icon_down" name="down"></i>
			</c:if>		
		</div>
		<p class="group">
			<label class="lab fl_l">联系人：<span id="showSpan">${detail.name}</span></label>
			<input type="hidden" id="tscidName" value="${detail.name }">
			<c:if test="${fn:length(details) gt 1 }">
				<a href="###" class="more_name fl_l"><label class="more_name_btn">更多</label>
					<span class="drop" style="display:none;">
						<label class="arrow"><em>◆</em><span>◆</span></label>
						<label class="box">
							<c:forEach items="${details}" var="v">
								<span telphone="${v.telphone}" id="${v.tscidId}" name="${v.name}"  telphonebak="${v.telphonebak}" qq="${v.qq}"  ww="${v.wangwang}">${v.name}</span>
							</c:forEach>
						</label>
					</span>
				</a>
			</c:if>
	</p>
	
	<div class="main fl_l">
			<input type="hidden" id="detail_telphone" value="${detail.telphone }">
			<input type="hidden" id="detail_telphonebak" value="${detail.telphonebak }">
			<div class="bot">
				<dl class="bot_a fl_l">
					<dt><i></i></dt>
					<c:choose>
					   <c:when test="${ !empty detail.telphone  && shiroUser.serverDay gt 0}">
							<dd id="detail_telphone_text" name="telphone_">${detail.telphone}</dd>
							<dd class="dd_bt dd_a" onclick="toSmsSendPage('${custEntity.name}',$('#detail_telphone').val());"><label><i ></i><span>短信</span></label></dd>
							<dd class="dd_bt dd_b" onclick="followCallPhone($('#detail_telphone').val(),'${custEntity.resCustId}','${custEntity.name}','${custEntity.status}',$('#followId').val(),$('#saleProcessId'),'${detail.name}','${custEntity.type}');"><label><i ></i><span>呼叫</span></label></dd>
					   </c:when>
					   <c:otherwise>
					   		<dt class="no_tel"><i></i></dt>
					   		<dd class="no_tel"></dd>
							<dd class="dd_bt dd_a no_tel" ><label style="cursor:default;"></label></label></dd>
							<dd class="dd_bt dd_b no_tel"><label style="cursor:default;"></label></dd>	
					   </c:otherwise>
					</c:choose>				
				</dl>
				<dl class="bot_b fl_l">				
					<c:choose>
					   <c:when test="${ !empty detail.telphonebak  && shiroUser.serverDay gt 0}">
							<dt><i></i></dt>
							<dd id="detail_telphonebak_text" name="telphonebak_">${detail.telphonebak}</dd>
							<dd class="dd_bt dd_a" onclick="toSmsSendPage('${custEntity.name}',$('detail_telphonebak').val());"><label><i></i><span>短信</span></label></dd>
							<dd class="dd_bt dd_b" onclick="followCallPhone($('#detail_telphonebak').val(),'${custEntity.resCustId}','${custEntity.name}','${custEntity.status}',$('#followId').val(),$('#saleProcessId'),'${detail.name}','${custEntity.type}');"><label><i></i><span>呼叫</span></label></dd>
					   </c:when>
					   <c:otherwise>
					   		<dt class="no_tel"><i></i></dt>
					   		<dd class="no_tel"></dd>
							<dd class="dd_bt dd_a no_tel" ><label style="cursor:default;"></label></label></dd>
							<dd class="dd_bt dd_b no_tel"><label style="cursor:default;"></label></dd>	
					   </c:otherwise>
					</c:choose>	
				</dl>
				<input type="hidden" id="detail_qq" value="${detail.qq }">
				<input type="hidden" id="detail_ww" value="${detail.wangwang }">
				<dl class="bot_c fl_l">
					<c:choose>
						<c:when test="${empty detail.email }">
							<dt style="background-color:#b6b6b6;cursor:default;"><i style="cursor:default;"></i></dt>
						</c:when>
						<c:otherwise>
							<dt><i onclick="emailBysend('${detail.name }','${detail.email }')" ></i></dt>
						</c:otherwise>
					</c:choose>					
					<c:choose>
					   <c:when test="${ !empty detail.qq && shiroUser.serverDay gt 0}">
					   		 <dd class="dd_q"  onclick="qqSend('${detail.qq}')"><i ></i></dd>		             
					   </c:when>
					   <c:otherwise>
				              <dd class="dd_q" style="background-color:#b6b6b6;cursor:default;"><i style="cursor:default;"></i></dd>
					   </c:otherwise>
					</c:choose>	
					<c:choose>
					   <c:when test="${ !empty detail.wangwang  && shiroUser.serverDay gt 0}">
					   <dd class="dd_w" style="cursor:pointer;">
				           <a href="http://www.taobao.com/webww/ww.php?ver=3&touid=${detail.wangwang}&siteid=cntaobao&status=1&charset=utf-8">
				             <i style="cursor:pointer;"></i>
				           </a>
				        </dd>
					   </c:when>
					   <c:otherwise>
				             <dd class="dd_w" style="background-color:#b6b6b6;cursor:default;"><i style="cursor:default;"></i></dd>
					   </c:otherwise>
					</c:choose>						
				</dl>
			</div>
    </div>
    <script type="text/javascript">
    	$(function(){
    		 /*card下拉*/
    	    $('.icon_down').click(function(){
    	    	var sty_hid = $(this).prev();
    	    	if($(this).attr('name') == 'up'){
    	    		$(this).css({'background':'url('+ctx+'/static/images/down-alert.png) no-repeat'});
    	    		$(this).attr('name','down');	
    	    		sty_hid.slideUp();
    	    	}else{
    	    		if($(this).hasClass('icon_down') == true){
    	    			$(this).parent().css({'height':'auto'});
    	    		}
    	    		$(this).css({'background':'url('+ctx+'/static/images/up-alert.png) no-repeat'});
    	    		$(this).attr('name','up');
    	    		sty_hid.slideDown();
    	    	}
    	    });
    	    //联系人更多
    	    $('.more_name_btn').click(function(){
    	    	$(this).next('.drop').toggle();
    	    	$(this).next('.drop').find('.box').find('span').click(function(){
    	    		$(this).parents('.drop').hide();
    	    		$("#showSpan").text($(this).text());
    	    		$("#detail_telphone_text").text($(this).attr("telphone"));
    	    		$("#detail_telphonebak_text").text($(this).attr("telphonebak"));
    	    		$("#detail_telphone").val($(this).attr("telphone"));
    	    		$("#detail_telphonebak").val($(this).attr("telphonebak"));
    	    		$("#detail_qq").val($(this).attr("qq"));
    	    		$("#detail_ww").val($(this).attr("ww"));
    	    		$("#tscidName").val($(this).attr("name"));   	 
    	    		 /********** 屏蔽手机或固话中间几位  *********/
            	    var idReplaceWord = $("#idReplaceWord").val();	
            		if(idReplaceWord==1){
            			var str = $("#detail_telphone_text").text();
    		    		replaceWord(str,$("#detail_telphone_text"));
    		    		var str1 =$("#detail_telphonebak_text").text();
    		    		replaceWord(str1,$("#detail_telphonebak_text"));
            		} 
    	    	}); 	    	
    	    });
    	    
    	 	 //编辑
    		$('.demoBtn_b').click(function(){
   		    	var custId = $("#custId").val();
   		    	var getTimestamp=new Date().getTime();  
   		    	pubDivShowIframe('edit_'+custId,ctx+'/res/cust/toEditRes?custId='+custId+'&v='+getTimestamp,'信息编辑',500,570);
    		});
    	 	 
    		//设置是否重点关注
       	    $('#majorId').live('click',function(){
       	    	var custId = $('#custId').val()
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
       	    });
    		
       	 /********** 屏蔽手机或固话中间几位  *********/
    	    var idReplaceWord = $("#idReplaceWord").val();	
    		if(idReplaceWord==1){
    		    $("dd[name=telphone_]").each(
    		    		function(){
    		    		   var str = $(this).text();
    		    		   replaceWord(str,$(this));
    		    		  }
    		    );    
    		    $("dd[name=telphonebak_]").each(
    		    		function(){
    		    		   var str = $(this).text();
    		    		   replaceWord(str,$(this));
    		    		  }
    		    );   
    		    $("[phone=tel]").each(function(idx,obj){
     				var phone = $(obj).text();
     				if(phone != null && phone != ''){
     					replaceWord(phone,$(obj));
     					replaceTitleWord(phone,$(obj));
     				}
     			});
    		} 
    	});
    	
    	// 打电话公共方法
		function followCallPhone(phone,resCustId,name,status,followId,saleProcess,detailName,custType){
    		var arrays = new Array();
  			arrays.push("\"custId\":\""+resCustId+"\"");
  			arrays.push("\"custName\":\""+name+"\"");
  			arrays.push("\"followId\":\""+followId+"\"");
  			arrays.push("\"custState\":\""+status+"\"");
  			arrays.push("\"custType\":\""+custType+"\"");
			if(saleProcess != null){
				arrays.push("\"saleProcessId\":\""+$(saleProcess).val()+"\"");
				arrays.push("\"saleProcessName\":\""+$.trim($(saleProcess).find("option:selected").text())+"\"");
			}
			if($.trim(detailName) != ""){
				arrays.push("\"define1\":\""+detailName+"\"");
			}
			window.top.custCallPhone(phone,arrays,resCustId);
		}
    	
    	 function qqSend(qq){
        	 $('.qq_iframe').attr('src', "tencent://message/?uin="+qq+"&Site=&menu=yes")
         }
    </script>