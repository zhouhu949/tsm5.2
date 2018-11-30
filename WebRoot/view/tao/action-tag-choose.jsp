<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8">
		<title>选择行动标签</title>
		<%@ include file="/common/include-cut-version.jsp"%>
		<style type="text/css">
			.action-tag-box{
				padding-bottom: 40px;
			}
		
			.group-block .head{
				height: 30px;
				line-height: 30px;
				font-size: 14px;
				background-color: #F5F6F7;
				padding-left: 20px;
			}
		
			.tip-box {
				/* width: 640px; */
				height: auto;
				overflow: hidden;
				padding: 0 10px 0 20px;;
			}
			
			.tip-box a {
				display: inline-block;
				float: left;
				width: 100px;
				height: 30px;
				line-height: 30px;
				text-align: center;
				border: dashed #d6d6d6 1px;
				-moz-border-radius: 5px;
				-webkit-border-radius: 5px;
				border-radius: 5px;
				background-color: #fff;
				color: #000;
				margin: 5px 10px 5px 0;
			}
			
			.tip-box .click-hover {
				background-color: #1979ca;
				color: #fff;
				border-color: #0f5aac;
			}
			
			.button-area.sure-cancle {
				width: 100%;
				text-align: center;
				position: fixed;
				bottom: 0;
				left: 0;
				background-color: #fff;
				padding: 4px 0;
				margin: 0;
			}
				
			button.btn {
				border: 1px solid #d3d3d3;
				border-radius: 5px;
				cursor: pointer;
				margin: 0 10px;
				padding:0 15px;
				line-height:22px;
			}
				
			.btn.btn-md {
				width: 100px;
				height: 30px;
				line-height: 30px;
			}
				
			.btn.btn-submit {
				color: #fff;
				background-color: #1979ca;
			}
				
			.btn.btn-submit:hover {
				background-color: #0f5aac;
			}
				
			.btn.btn-default {
				background-color: #fff;
			}
				
			.btn.btn-default:hover {
				color: #fff;
				background-color: #1979ca;
			}
		</style>
	</head>

	<body>
		<div class="action-tag-box">
			<div class="action-tag-block" data-value="${optionList }"></div>
			<div class="button-area sure-cancle">
				<button type="button" class="btn btn-submit btn-md" id="btn-submit">确定</button>
				<button type="button" class="btn btn-default btn-md" id="btn-cancel">取消</button>
			</div>
		</div>
		<script>
			$(function(){
				$.ajax({
					type:"get",
					url:ctx+"/res/tao/getLableList",
					success: function(data){
						console.log(data);
						var groupLength = data.length;
						var appendCode = '';
						for (var i = 0; i < groupLength; i++) {
							appendCode += 
							'<div class="group-block">';
							appendCode += 
								'<div class="head">'+data[i].groupName+'：</div>';
							appendCode += 
								'<div class="content tip-box">';
							var optionList = data[i].optionList;
							for(var j=0;j<optionList.length;j++){
								appendCode += "<a href='javascript:;' class='action-tag' data-id='" + optionList[j].optionlistId + "' data-name='"+optionList[j].optionName+"'>" + optionList[j].optionName + "</a>";
							}
							appendCode = appendCode + 
								'</div>'+
							'</div>';
						}
						$(".action-tag-block").append(appendCode);
						
						chooseChosenIdTags();
						
						$(".tip-box").delegate("a","click",function(e){
							var _this = $(this);
							if(_this.hasClass("click-hover")){
								_this.removeClass("click-hover");
							}else{
								_this.addClass("click-hover");
							}
						});
					},
					error: function(error){}
				});
				
				$("#btn-submit").on("click",function(e){
					var chosenTags = $(".click-hover");
					var chosenTagsId = [];
					var chosenTagsName = [];
					var appendCode = "";
					for(var i=0;i<chosenTags.length;i++){
						appendCode += "<a href='javascript:;' class='click-hover' name='" + $(chosenTags[i]).data("id") + "'>" + $(chosenTags[i]).data("name") + "</a>";
						chosenTagsId.push($(chosenTags[i]).data("id"));
						chosenTagsName.push($(chosenTags[i]).data("name"));
					}
					
					if($("#addLabelBtn",window.parent.document).length>0){
						window.parent.chooseActionTag(chosenTagsId,chosenTagsName);
						window.parent.loadData();
					}else{
						$(".tip-box a.click-hover",window.parent.document).remove();
						$(".tip-box",window.parent.document).append(appendCode);
						$("#labelCodeId",window.parent.document).val(chosenTagsId.join("#"));
						$("#labelNameId",window.parent.document).val(chosenTagsName.join("#"));
						$(".choose-tag",window.parent.document).attr("data-chosen-id",chosenTagsId.join());
					}
					closeParentPubDivDialogIframe("alert_action_tag_choose");
					
				});
				
				$("#btn-cancel").on("click",function(e){
					if($("#addLabelBtn",window.parent.document).length>0){
						window.parent.chooseActionTag([],[]);
						if(window.parent.loadData){
							window.parent.loadData();
						}else{
                            window.parent.document.forms[0].submit();
						}
					}
					closeParentPubDivDialogIframe("alert_action_tag_choose");
				});
				
				function chooseChosenIdTags(){
					var chosenId = $(".action-tag-block").attr("data-value") ? $(".action-tag-block").attr("data-value").split(",") : [];
					var actionTags = $(".action-tag");
					
					if(chosenId.length > 0){
						for(var i=0;i<chosenId.length;i++){
							for(var j=0;j<actionTags.length;j++){
								if(chosenId[i] == actionTags.eq(j).attr("data-id")){
									actionTags.eq(j).addClass("click-hover");
								}
							}
						}
					}
				}
			});
		</script>		
	</body>

</html>