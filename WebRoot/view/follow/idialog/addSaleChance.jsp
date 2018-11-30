<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp"%>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/common/include-cut-version.jsp"%>
		<%@ include file="/common/common-function/function.date.jsp"%>
		<title>添加销售机会</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css" /><!--弹框样式-->
		<style>
			.button-area{
				text-align: center;
			}
			.search-box {
				width: 260px;
				position: absolute;
				border: 1px solid #d6d6d6;
				box-sizing: border-box;
				display: none;
				z-index: 20;
				top: 30px;
				left: 140px;
				background-color: #fff;
				height: 220px;
				overflow-y: auto;
			}
			.search-area{
	    		height: 35px;
			    line-height: 30px;
			    background-color: #e1e1e1;
			    text-align: center;
			}
			.search-area input[name='queryText'] {
				width: 180px;
			    height: 25px;
			    line-height: 25px;
			    padding: 3px;
			    vertical-align: middle;
		        font-size: 12px;
			}
			.search-area>button{
			    height: 25px;
			    padding: 3px;
			    vertical-align: middle;
			    border-radius: 3px;
			    border:1px solid #b4b4b4;
			    background-color:#fff;
			}
			.choose-cust-list li{
				line-height: 22px;
				overflow: hidden;
				text-overflow: ellipsis;
				white-space: nowrap;
				cursor: pointer;
			}
			.choose-cust-list li:hover{
				background-color: #e1e1e1;
			}
			.choose-cust-list .no-data-li {
				text-align: left;
				cursor: default;
			}
		</style>
	</head>
	<body>
		<input type="hidden" name="isState" value="${shiroUser.isState }" />
		<form action="${ctx}/cust/saleChance/addSaleChance" method="post">
			<div class='bomp-cen bomp_change bomp_change_add project-comp-idialog-box clearfix'  style="overflow-x:hidden;overflow-y: auto;">
				<div class='bomp-p'>
					<label class='lab_a fl_l'><i class="font-color" >* </i>销售机会名称：</label>
					<input type='text' id="" name="saleChanceName" value="" checkProp="chk_1_1" class='ipt_a fl_l' maxlength="30" />
					<span class="error" name='a' id="error_saleChanceName"></span>
				</div>
				
				<div class='bomp-p'>
					<label class='lab_a fl_l'><i class="font-color" >* </i>关联客户：</label>
					<input type='text' name="custName" value="" class='ipt_a fl_l'  readonly="readonly"/>
					<input type='hidden' name="custId" value=""  checkProp="chk_1_1"/>
					<span class="error" name='a' id="error_${ field.fieldCode}"></span>
					<div class="search-box">
						<div class="search-area">
							<input type='text' name="queryText" autoComplete="off" placeholder="${shiroUser.isState==1?'请输入客户名称查询':'请输入客户姓名，单位名称查询' }" />
							<button type="button" class="choose-cust">查询</button>
						</div>
						<ul class="choose-cust-list">
							
						</ul>
					</div>
				</div>
				
				<div class='bomp-p'>
					<label class='lab_a fl_l'><i class="font-color" >* </i>预期销售额：</label>
					<input type='text' id="" name="theorySignMoney" value="" checkProp="chk_1_1"  class='ipt_a fl_l' />
					<span class="error" name='a' id="error_theorySignMoney"></span>
				</div>
				
				<div class='bomp-p'>
					<label class='lab_a fl_l'><i class="font-color" >* </i>预期签单时间：</label>
					<input type='text' data-type="timepicker" id="" name="theorySignDate" value="" checkProp="chk_1_1" class='ipt_a fl_l' readonly />
					<span class="error" name='a' id="error_theorySignDate"></span>
				</div>
				
				<div class='bomp-p'>
					<label class='lab_a fl_l'>预期成功率：</label>
					<select name="theorySuccessRate" class='sel_a fl_l' >
						<option value="">--请选择--</option>
						<option value="1">0%</option>
						<option value="2">20%</option>
						<option value="3">50%</option>
						<option value="4">70%</option>
						<option value="5">90%</option>
					</select>
					<span class="error" name='a' id="error_theorySuccessRate"></span>
				</div>
				
				<div class='bomp-p'>
					<label class='lab_a fl_l'>销售对策：</label>
					<input type='text' id="" name="signPlan" value=""  class='ipt_a fl_l' maxlength="30" />
					<span class="error" name='a' id="error_signPlan"></span>
				</div>
				
				<div class='bomp-p'>
					<label class='lab_a fl_l'>竞争对手：</label>
					<input type='text' id="" name="rival" value="" class='ipt_a fl_l' maxlength="30" />
					<span class="error" name='a' id="error_rival"></span>
				</div>
				
				<div class='bomp-p'>
					<label class='lab_a fl_l'>商机备注：</label>
					<input type='text' id="" name="remark" value=""  class='ipt_a fl_l' maxLength="50" />
					<span class="error" name='a' id="error_remark"></span>
				</div>
			</div>
			<div class="button-area">
				<button type="button" class="button-mid" id="btn-save">保存</button>
				<button type="button" class="button-mid" id="btn-cancel">取消</button>
			</div>
		</form>
		<script type="text/javascript" src="${ctx}/static/js/view/res/resourceCheck.js${_v}"></script>
		<script type="text/javascript">
			function isMoney(value){
				return /^-?(?:\d+|\d{1,3}(?:,\d{3})+)?(?:\.\d+)?$/.test(value);
			}
			
			$(function(){
				$("input[name=theorySignMoney]").on("blur",function(e){
					var _this = $(this);
					if(!isMoney(_this.val())){
						_this.parents(".bomp-p").addClass("bomp-error");
						$("#error_theorySignMoney").text("数据格式有误！例：12345.123");
						$("#error_theorySignMoney").show();
					}else{
						_this.parents(".bomp-p").removeClass("bomp-error");
						$("#error_theorySignMoney").hide();
					}
				});
				
				$("#btn-save").on("click",function(e){
					var partten = /(\%|\&|\\#|\\$)/;
					var saleChanceName = $("input[name=saleChanceName]").val();
					var signPlan = $("input[name=signPlan]").val();
					var rival = $("input[name=rival]").val();
					var remark = $("input[name=remark]").val();
					if(partten.test(saleChanceName) || partten.test(signPlan) || partten.test(rival) || partten.test(remark)){
						window.top.iDialogMsg("提示","销售机会名称、销售对策、竞争对手、商机备注不能包含字符%,&,#,$");
						return false;
					}
					
					if(checkForm()){
						$.ajax({
							type:"get",
							url:$("form").attr("action"),
							data:$("form").serialize(),
							success:function(data){
								if(data.status){
									window.top.iDialogMsg("提示","保存成功！");
									window.parent.loadData();
									closeParentPubDivDialogIframe('sale_chance_add');
								}else{
									window.top.iDialogMsg("提示","保存失败！");
								}
							},
							error:function(status){
								window.top.iDialogMsg("提示","保存失败！");
							}
						});
                    }
				});
				
				$("#btn-cancel").on("click",function(e){
					closeParentPubDivDialogIframe('sale_chance_add');
				});
				
				$("input[data-type='timepicker']").on("click",function(e){
					WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-%d',startDate:'%y-%M-{%d}'});
				});
				
				$("input[name=custName]").on("click",function(e){
					e.stopPropagation();
					$(".search-box").show();
				});
				
				$(".choose-cust").on("click",function(e){
					$.ajax({
						url: ctx+"/cust/saleChance/queryResByNameOrCompany",
						type: "get",
						data: {
							"queryText": $("input[name=queryText]").val()
						},
						success: function(data){
							var _isState = $("input[name=isState]").val();
							var appendCode = "";
							if(data.length == 0){
								appendCode += '<li class="no-data-li">搜索无结果，请重新查询</li>';
							}
							for(var i=0;i<data.length;i++){
								if(_isState == 1){
									appendCode += '<li data-id="'+data[i].resCustId+'" title="'+data[i].name+'" data-show-content="'+data[i].name+'">'+data[i].name+'</li>';
								}else{
									var companyStr=(data[i].company!=null&&data[i].company!="")?data[i].company:"";
									var nameStr=(data[i].name!=null&&data[i].name!="")?'（'+data[i].name+'）':"";
									appendCode += '<li data-id="'+data[i].resCustId+'" title="'+companyStr+nameStr+'" data-show-content="'+companyStr+nameStr+'">'+companyStr+nameStr+'</li>';
								}
							}
							$(".choose-cust-list").html(appendCode);
						},
						error: function(error){}
					});
				});
				
				$("ul.choose-cust-list").delegate("li","click",function(e){
					e.stopPropagation();
					var _this = $(this);
					if(_this.hasClass("no-data-li")){
						return false;
					}
					_this.parents(".bomp-p").find("input[name=custName]").val(_this.attr("data-show-content"));
					_this.parents(".bomp-p").find("input[name=custId]").val(_this.data("id"));
					$(".search-box").hide();
				});
				
				$(".search-box").click(function(e){
					e.stopPropagation();
				});
				
				$(document).click(function(e){
					$(".search-box").hide();
				});
			});
		</script>
	</body>
</html>