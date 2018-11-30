<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/include.jsp"%>
	<!--公共样式-->
	<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css"><!--主要样式-->
<%-- 	<link rel="stylesheet" type="text/css" href="${ctx}/static/js/idialog/skins/iblue.css"> --%>
	<script type="text/javascript" src="${ctx}/static/js/common/common.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/common/replaceWord.js"></script>
<script type="text/javascript">
$(function(){

	$('tr[id^="tr_"]').click(function(){
		var detailId = $(this).attr('id').split('_')[1];
		var custId =  $('#custId').val();
		var oldConcatId =  $('#detailId').val();
		$('#concatId', window.parent.document).val(detailId);
		$('input[id^="chk_"]').each(function(item,obj){
			var id = $(obj).attr('id').split('_')[1];
			if(id==detailId){
				$(obj).iCheck('check');
			}else{
				$(obj).iCheck('uncheck');
			}
		})
		$.ajax({
			url :ctx+ '/res/tao/getDetailId',
			data:{'detailId':detailId,'timestamp':new Date().getTime()},
			type : 'post',
			dataType :'json',
			success : function(data) {
				if(data =="-3"){
					dialogMsg(-1,'操作失败,请重试');
				}else{
					var custName = $('#custName').val();
					var mobile = data.telphone;
					var telphonebak = data.telphonebak;
					var name = data.name;
					var email = data.email;
					var work = data.work;
					var wangwang = data.wangwang;
					var qq = data.qq;
					var phone = (mobile==null || mobile=="") ? telphonebak:mobile;
					var safeMobile = '';
					var safeTel  =  '';
					var idReplaceWord = $('#idReplaceWord', window.parent.document).val();
					if(idReplaceWord=='1'){
						if(mobile!=''){
							safeMobile = replaceWordStr(mobile);
						}
						if(telphonebak!=''){
							safeTel = replaceWordStr(telphonebak);
						}
					}
					var sex_class = data.sex == 1?'tip_b':'tip_a';
					if($('.click-left-hover', window.parent.document).children().length>0){
						$('.click-left-hover', window.parent.document).children().html(name);
					}else{
						$('.click-right-hover', window.parent.document).children().html(name);
					}
					var obj = $('#contId_'+oldConcatId, window.parent.document);
					$('#concatId', window.parent.document).val(detailId);
					$('#concat_phone', window.parent.document).val(phone);
					$('#concat_name', window.parent.document).val(name);
					
	    			if($('.click-left-hover', window.parent.document).length>0){
	    				$('.click-left-hover', window.parent.document).attr('id',detailId)
	    			}
	    			if($('.click-right-hover', window.parent.document).length>0){
	    				$('.click-right-hover', window.parent.document).attr('id',detailId)
	    			}
					var html ='<div class="left fl_l">'+
						'<dl>'+
							'<dt>'+name+'</dt>'+
							'<dd>'+work+'</dd>'+
							'<dd class="'+sex_class+'" ></dd>'+
						'</dl>'+
					'</div>'+
					'<div class="right fl_l">'+
						'<div class="bot fl_l">'+
							'<dl class="bot_a fl_l">';
					 if(mobile == ''){
						 html +='<dt class="no_tel"><i></i></dt>';
						 html +='<dd class="no_tel"></dd>';
						 html +='<dd class="dd_bt dd_a" style="background-color:#b6b6b6;cursor:default;"></label></dd>'+
								'<dd class="dd_bt dd_b" style="background-color:#b6b6b6;cursor:default;"></label></dd>';	
					 }else{
						 html +='<dt><i></i></dt>';
						 html += '<dd id="detail_telphone_text" name="telphone_">' + safeMobile + '</dd>';
						 html +='<dd class="dd_bt dd_a" style="cursor:pointer;"   onclick="toSmsSendPage(\''+name+'\',\''+mobile+'\')"><label style="cursor:pointer;"><i style="cursor:pointer;"></i><span>短信</span></label></dd>'+
							'<dd class="dd_bt dd_b" style="cursor:pointer;"  onclick="setIsLianxi(\''+custId+'\',\''+mobile+'\',\''+custName+'\',\''+name+'\')"><label style="cursor:pointer;" ><i style="cursor:pointer;"></i><span>呼叫</span></label></dd>';
					 }
					 html +='</dl>'+
							'<dl class="bot_b fl_l">';
								
					 if(telphonebak == ''){
						 html +='<dt class="no_tel"><i></i></dt>';
						 html +='<dd class="no_tel"></dd>'; 
						 html +='<dd class="dd_bt dd_a" style="background-color:#b6b6b6;cursor:default;"><label style="cursor:default;"></label></dd>'+
							'<dd class="dd_bt dd_b" style="background-color:#b6b6b6;cursor:default;"><label style="cursor:default;"></label></dd>';		
					 }else{
						 html +='<dt><i></i></dt>';
						 html +='<dd id="detail_telphonebak_text" name="telphonebak_">'+safeTel+'</dd>';
						 html +='<dd class="dd_bt dd_a" style="cursor:pointer;" onclick="toSmsSendPage(\''+name+'\',\''+telphonebak+'\')"><label style="cursor:pointer;" ><i style="cursor:pointer;"></i><span>短信</span></label></dd>'+
							'<dd class="dd_bt dd_b" style="cursor:pointer;" onclick="setIsLianxi(\''+custId+'\',\''+telphonebak+'\',\''+custName+'\',\''+name+'\')"><label style="cursor:pointer;" ><i style="cursor:pointer;"></i><span>呼叫</span></label></dd>';
					 }								
					 html +='</dl>'+									
						'</div>'+
						'<div class="top_box fl_l">'+
							'<dl class="top fl_l">';
							if(email!=''){
								html +='<dt style="cursor:pointer;"  onclick="emailBysend(\''+name+'\',\''+email+'\')" title='+email+'><i  style="cursor:pointer;" ></i></dt>';
							}else{
								html +='<dt style="background-color:#b6b6b6;cursor:default;"><i style="cursor:default;"></i></dt>';							   
							}
							
							if(qq!=''){
								html +='<dd class="dd_q" style="cursor:pointer;"   onclick="qqSend(\''+qq+'\')" title='+qq+'><i style="cursor:pointer;"></i></dd>';
							}else{
								html +=' <dd class="dd_q" style="background-color:#b6b6b6;cursor:default;"><i style="cursor:default;"></i></dd>';							   
							}
							if(wangwang!=''){
								html +='<dd class="dd_w" style="cursor:pointer;">'+
								'<a href="http://www.taobao.com/webww/ww.php?ver=3&touid='+wangwang+'&siteid=cntaobao&status=1&charset=utf-8" style="display:block;height:45px;background-color:#f2aa38;"><i style="cursor:pointer;" title='+ wangwang +'></i></a></dd>';
							}else{
								html +=' <dd class="dd_w" style="background-color:#b6b6b6;cursor:default;"><i style="cursor:default;"></i></dd>';							   
							}
							html+='</dl></div></div></div>';
			      obj.html(html);
				}
			}
		});
	})
});
</script>
</head>
<body> 
<form action="${ctx }/res/tao/queryConcatList" method="post">
    <input type="hidden" id="custId" name="custId" value="${custId}">
    <input type="hidden" id="detailId" name="detailId" value="${detailId}">
    <input type="hidden" id="custName" name="custName" value="${custName}">
	<div class="com-table bomp-scc" style="margin:0 auto;margin-top:5px;">
		<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b">
			<thead>
				<tr class="sty-bgcolor-b">
					<th><span class="sp sty-borcolor-b"></span></th>
					<th><span class="sp sty-borcolor-b">姓名</span></th>
					<th><span class="sp sty-borcolor-b">常用电话</span></th>
					<th>职务</th>
				</tr>
			</thead>
			<tbody> 
				<c:choose>
					<c:when test="${!empty concatList }">
						<c:forEach items="${concatList}" var="rlist" varStatus="vs">
							<tr class="${(vs.count % 2) ne 0 ? '' : 'sty-bgcolor-b' }" id="tr_${rlist.tscidId }">
								<td><div class="overflow_hidden w30 skin-minimal"><input type="radio"  id="chk_${rlist.tscidId }" ${rlist.tscidId eq detailId ? 'checked':''}  /></div><span class="hyx-scp-zz"></span></td>
								<td><div class="overflow_hidden w110" title="${rlist.name }">${rlist.name }</div></td>
								<td><div class="overflow_hidden w110" title="${rlist.telphone }">${rlist.telphone }</div></td>
								<td><div class="overflow_hidden w110" title="${rlist.work }">${rlist.work }</div></td>							
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan="4">
								<center>当前列表无数据！</center>
							</td>
						</tr>									
					</c:otherwise>
				</c:choose>            
			</tbody>
		</table>
	</div>
	<div class="com-bot">
		<div class="com-page fl_r">
			<div class="page">${resCustDto.page.pageStr}</div>
			<div class="clr_both"></div>
		</div>
	</div>
</form>
</body>
</html>