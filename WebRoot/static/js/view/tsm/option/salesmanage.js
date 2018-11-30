$(function(){
	var resave =$("#myForm").serialize();
	//tabs切换
	//console.log($.cookie('showTab'))
 	if($.cookie('showTab')!==undefined){
        	$(".new-salesmanage-page-tabs>form>div").hide();
        	$(".new-salesmanage-page-changer>div").removeClass("selects");
        	$("#"+$.cookie('showTab')).addClass("selects");
        	$("."+$.cookie('showTab')+"-tabs").show();
	}
	$(".new-salesmanage-page-changer>div").on("click",function(e){
		e.stopPropagation();
		var $this = $(this);
		var nowClass  = $(this).attr("id");
		var $selects = $this.parent().find("div");
		if($this.hasClass("selects")){
			return false;
		}else{
			var nowSave = $("#myForm").serialize();
			if(resave == nowSave){
				$selects.removeClass("selects");
				$this.addClass("selects");
				$(".new-salesmanage-page-tabs>form>div").hide();
				$("."+nowClass+"-tabs").show();
			}else{
				queDivDialog("ask_to_leave","您修改的设置尚未保存，确定要离开吗？",function(){
					$("#myForm")[0].reset();
					 $('.hyx-sms').find('.switch').each(function(i,obj){
						 var that = $(this)
						 var $id =that.attr('id').split('_')[1]; 
						 var val = $("#dic_"+$id).val();
							 if(val == 1){
								 changeStatus(that,"off")
							 }else{
								 changeStatus(that,"on")
							 }
					 })
					$selects.removeClass("selects");
					$this.addClass("selects");
					var now= new Date();
                    $.cookie('showTab', nowClass, { expires:new Date(now.getTime()+10*1000)});
                    window.location.reload()
				//	$(".new-salesmanage-page-tabs>form>div").hide();
				//	$("."+nowClass+"-tabs").show();
				},function(){
					return false;
				})
			}
		}
	})
	//树地址和其他变量
	var url = ctx+"/orgGroup/get_all_group_json";
	var tree_index = $("#tree_index").val();
	var tree_node = $(".tr-add-tree").eq(0);
	//点击唤醒树
	$(".company_choose").live("click",function(e) {
		e.stopPropagation();
		var $this = $(this);
		var $tree = $this.find(".easyui-tree");
		$(".tree-drop").hide();
		$this.children(".tree-drop").show();
//		$(".manage-drop").show();
//		$(".manage-drop").parent().siblings().find("dd").hide();
//		$(".manage-owner-sour").hide();
	});
	$(".double-card-setting-combo").blur(function(){
		var $this = $(this);
		var val = $this.val();
		if(val < 20){
			$this.val(20);
		}
	})
	//审核人唤醒树
	$(".check-man-setting").focus(function(){
		$("#manage_drop").show();
	});
	$(".check-man-setting").click(function(e){
		e.stopPropagation();
		$("#manage_drop").show();
	})
	$("#manage-drop").click(function(e) {
		e.stopPropagation();
		$(this).show();
	});
	$(document).click(function(){
		$("#manage_drop").hide();
	})
	$("#tt1").tree({
			url:ctx+'/orgGroup/get_all_group_user_json',
			checkbox:true,
			onlyLeafCheck: true,
			cascadeCheck : false,
			onSelect:function(node,checked){
				var cknodes =$('#tt1').tree('getChecked', 'checked');
	            for (var i = 0; i < cknodes.length; i++) {
	                if (cknodes[i].id != node.id) {
	                    $('#tt1').tree("uncheck", cknodes[i].target);
	                }
	            }
	            if (node.checked) {
	                $('#tt1').tree('uncheck', node.target);

	            } else {
	                $('#tt1').tree('check', node.target);
	            }
			},
			onLoadSuccess:function(node,data){
				//处理选中
				var userids = $('#useraccount').val();
				var names = "";
				if(userids != null && userids.length > 0){
					$(userids.split(',')).each(function(index,data){
						var node = $("#tt1").tree("find",data);
						if(node != null){
							names+=node.text+';';
							$("#tt1").tree("check",node.target);
						}
					});
					$('#usertext').val(names);
				}
				$(this).find('span.tree-checkbox').unbind().click(function () {
	                $('#tt1').tree('select', $(this).parent());
	                return false;
	            });
				//如果叶子节点为部门的 checkbox隐藏
	           var roots =  $("#tt1").tree("getRoot");
	           var childs = $("#tt1").tree("getChildren",roots);
	           for( var i = 0 ; i < childs.length ; i++){
	        	   var item = childs[i];
	        	   if( item.attributes.type &&item.attributes.type == "G" ){
	        		   $(item.target).find(".tree-checkbox").remove();
	        	   }
	           }
			}
		});
	$("#checkedId").click(function() {
		var nodes = $('#tt1').tree('getChecked');
		var shares ="";
		var names ="";
		if(nodes != null){
			$(nodes).each(function(index,obj){
				if(obj.attributes.type && obj.attributes.type =="G"){
					return false;
				}
				shares+=obj.id.substring(0,obj.id.length)+',';
				names+=obj.text.substring(0,obj.text.length)+';';
			});
		}

        $('#usertext').val(names).attr("title",names);
        $('#useraccount').val(shares);
	});

	//清空
	$("#clearId").click(function(){
		var nodes = $('#tt1').tree('getChecked');
		 $(nodes).each(function(index,obj){
        	$('#tt1').tree('uncheck', obj.target);
        })
        $('#usertext').val('');
		 $('#useraccount').val('');
	});
	//公海池数据新增
	$(".add_btn").click(function() {
	/* <input type="hidden"  class="input_groupId"   name="dictionaryWaterList[0].groupId" value=${water.groupId } >
	<input type="hidden"  class="input_shireGroupIds" name="dictionaryWaterList[0].shareGroupIds"  value=${water.shareGroupIds }> */
		tree_index++;
		var add_tree = tree_node.clone().appendTo("table.high-seas-data-table");
		add_tree.find(".easyui-tree").tree({
    		url:url
    	});
    	add_tree.find(".input_groupId").attr("name","dictionaryWaterList["+tree_index+"].groupId");
    	add_tree.find(".input_groupId").val("");
    	add_tree.find(".input_shireGroupIds").attr("name","dictionaryWaterList["+tree_index+"].shareGroupIds");
    	add_tree.find(".input_shireGroupIds").val("");
		add_tree.find(".choose_input").val("请选择");

	});
	//公海池数据删除
	$(".delete").live("click",function(){
		var $this = $(this);
		var $that = $this.parent().parent();
		$that.remove();
	});
	//客户跟进时间设置增加
	$(".cust_follow_set_add_btns").on("click",function(e){
		e.stopPropagation();
		var tbody =$(this).parents("table").find("tbody");
		if($("[name^='dictionaryFollowList']").length ==0){
			var index = 0;
		}else{
			var index = Number($("[name^='dictionaryFollowList']:last").attr("name").split("[")[1][0])+1;
		}
		var data = {"index":index};
		var template = Handlebars.compile($("#follow_template").html())
		tbody.append(template(data))
		multiDropDownFn();
	})
	//客户跟进时间设置删除
	$(".cust_follow_set_del_btns").live("click",function(e){
		e.stopPropagation();
		var $that = $(this).parents("tr");
		$that.remove();
	});
	$(".cust-info-output-setting").change(function(e){
		var $this = $(this);
		var inputs = $(".add-check-man-label").parent().find("input[type='text']");
		var val = $this[0].checked;
		if(val){
			inputs.removeAttr("disabled");
		}else{
			inputs.attr("disabled","true");
		}
	})
	//客户跟进时间销售进程selected类名初始化添加
	
	$("dl.select[data-multi='true']").each(function(i,obj){
		var $this = $(this);
		var inputs = $this.children("input");
		var arr = []
		$(inputs.val().split(",")).each(function(i,obj){
			$this.find("dd>ul>li>a").each(function(i,item){
				if($(item).data("value") == obj){
					arr.push($(item).text());
					$(item).parent().addClass("selected");
				}
			})
		})
		if(arr.length){
			$this.find("dt.resouceSearch").text(arr.join(","))
		}
	})
	//部门选择
	$(".submit_left").live("click",function(e){
		e.stopPropagation();
		var $this = $(this);
		var $that = $this.parent().parent();
		var $tree = $that.find(".easyui-tree");
		var nodes = $tree.tree('getSelected');
		var nodes_id = "";
		var nodes_text = '';
		if(nodes){
			nodes_text = nodes.text;
			nodes_id = nodes.id;
		}
		if(judge_duplicates(nodes_id,nodes_text)){
			$that.parents("tr").find(".input_groupId").val(nodes_id);
			$that.parent().find(".choose_input").val(nodes_text);
			$that.hide();
		}
	});
	//部门清空
	$(".clean_left").live("click",function(e){
		e.stopPropagation();
		var $this = $(this);
		var $that = $this.parent().parent();
		var $tree = $that.find(".easyui-tree");
		$tree.tree("select","");
		$that.parent().find(".choose_input").val("请选择");
	});
	//公海数据选择
	$(".submit_right").live("click",function(e){
		e.stopPropagation();
		var $this = $(this);
		var $that = $this.parent().parent();
		var $tree = $that.find(".easyui-tree");
		var nodes = $tree.tree('getChecked');
		var nodes_text = '';
		var nodes_id = '';
		for(var i=0; i<nodes.length; i++){
			if (nodes_text != '') nodes_text += ',';
			nodes_text += nodes[i].text;
			if (nodes_id != '') nodes_id += ',';
			nodes_id += nodes[i].id;
		}
		$that.parents("tr").find(".input_shireGroupIds").val(nodes_id);
		$that.parent().find(".choose_input").val(nodes_text);
		$that.hide();
	});
	//公海数据清空
	$(".clean_right").live("click",function(e){
		e.stopPropagation();
		var $this = $(this);
		var $that = $this.parent().parent();
		var $tree = $that.find(".easyui-tree");
		var nodes = $tree.tree('getChecked');
		for(var i=0; i<nodes.length; i++){
			$tree.tree("uncheck",nodes[i].target);
		}
		$that.parent().find(".choose_input").val("请选择");
	});
	//行动完成率的达标值必须有选中值
	$(".action-finish").change(function(){
		var checked=$(".action-finish:checked");
		var $this = $(this) ;
		if(checked.length < 1 ){
			$(".action-finish").not($this)[0].checked = true;
		}
	});
	//弹幕显示项目必须有选中值（暂时不加）
	/*$(".barrage-required").change(function(){
		var checked=$(".barrage-required:checked");
		var $this = $(this) ;
		if(checked.length < 1 ){
			$(".barrage-required").not($this)[0].checked = true;
		}
	});*/
	//弹幕选择其他 input出现
	$("select.select-choose-others-input-show").live("change",function(){
		var $this = $(this) ;
		 if($this.find("option:selected").text() == "其他"){
			 $this.next().val("");
			$this.next().show();
		}else{
			$this.next().hide();
			$this.next().val("");
			$this.next().val($this.find("option:selected").val());
		}
	});
	//弹幕开启关闭 隐藏下部效果
	$("[class^='radio-change-visitity-btn']").on("change",function(e){
		var $this = $(this);
		var className = $this.attr("class");
		var itemClass = className.split("-");
		var num = itemClass[itemClass.length - 1];
		var currVal = $("."+className+":checked").val();
		var divs = $(".radio-change-visitity-"+num);
		if(currVal == 1){
			divs.show();
		}else{
			divs.hide();
		}
	})
	$(".order-amount-max").live("change",function(){
		var $this = $(this);
		var $pare = $this.parent();
		var $nextPar = $pare.next();
		var $prevPar = $pare.prev();
		var $prev = $pare.find(".order-amount-min");
		if( Number($this.val()) <= Number($prev.val()) ){
			$this.data("flag","false");
			window.top.iDialogMsg("提示","订单金额范围有误！");
		}else{
			$this.data("flag","true");
			$prev.data("flag","true");
		}
		if($nextPar.length > 0 ){
			
			var $nextMax = $nextPar.find(".order-amount-max");
			var $nextMaxVal = $.trim($nextMax.val());
			if( $nextMaxVal != "" && $nextMaxVal != null){
				if( Number($this.val()) >= Number($nextMaxVal)){
					$this.data("flag","false");
					window.top.iDialogMsg("提示","订单金额范围有误！");
				}else{
					$this.data("flag","true");
					$nextMax.data("flag","true");
				}
			}
			$nextPar.find(".order-amount-min").val($this.val())
		}
		if($prevPar.length > 0){
			
			var $prevMax = $prevPar.find(".order-amount-max");
			var $prevMaxVal = $.trim($prevMax.val());
			if( $prevMaxVal != "" && $prevMaxVal != null){
				if( Number($this.val()) <= Number($prevMaxVal) ){
					$this.data("flag","false");
					window.top.iDialogMsg("提示","订单金额范围有误！");
				}else{
					$this.data("flag","true");
					$prevMax.data("flag","true");
				}
			}
		}
	})
	$(".order-amount-min").live("change",function(){
		var $this = $(this);
		var $pare = $this.parent();
		var $next = $pare.find(".order-amount-max");
		var nextVal = Number($next.val());
		if( nextVal != "" && nextVal != null && Number($this.val()) > nextVal ){
			$this.data("flag","false");
			window.top.iDialogMsg("提示","订单金额范围有误！");
		}else{
			$this.data("flag","true");
			$next.data("flag","true");
		}
	})
	//订单金额增加
	$(".add-order-balace-set").on("click",function(e){
		e.stopPropagation();
		if(!$(".order-return-radio")[0].checked){
			return false;
		}
		var inputLength = 3;
		var minvalue =$.trim( $(".append-balance-box").find("p.tit_b:last").find(".order-amount-max").val() );
		var newIndex = Number($("[name^='dictionaryMoneyList']:last").attr("name").split("[")[1][0])+1;
		var data = {"index":newIndex};
		var template = Handlebars.compile($("#template").html())
		$(".append-balance-box").append(template(data))
		if(minvalue != "" && minvalue!= null){
			$(".append-balance-box").find("p.tit_b:last").find(".order-amount-min").val(minvalue);
		}
	})
	$(".del-order-balace-set").live("click",function(e){
		e.stopPropagation();
		$(this).parents(".padingleft30").remove();
	})
	/*公海客户防骚扰设置项不可为0*/
	$(".minvalue-notequel-zero").blur(function(){
		var  $this = $(this);
		if($this.val() == 0){
			$this.val(1);
		}
	})
	/*时间分钟和小时的最大值限制*/
	$(".time-rules").blur(function(){
		var  $this = $(this);
		var max = parseInt($this.attr("time-rules"));
		if( $this.val() > max ){
			$this.val(max)
		}
		if($this.val().length == 1){
			var strs = $this.val().toString();
			var result = 0+ strs;
			$this.val(result);
		}
	})
	//行动完成率的单个数值为0 -1  之和不超过1
	$(".action-finish-input").blur(function(){
		var $this = $(this) ;
		var $another = $(".action-finish-input").not($this) ;
		var addSum = Number($this.val()) + Number($another.val());
		var thisSubSum = (10 - Number($this.val())*10)/10;
		var anSubSum = (10 - Number($another.val())*10)/10;
		
		if( $this.val() < 1 && $this.val() > 0 ){
			$another.val(thisSubSum);
		}
		if($this.val() >= 1 || $this.val() <= 0){
			$this.val(anSubSum);
		}
	})
	//启用行动完成率按钮下面的内容显示和隐藏
	$(".actionbtn").on("change",function(){
		var $this = $(this);
		if($this[0].checked){
			$(".actionContent").show();
		}else{
			$(".actionContent").hide();
		}
	})
	
	/*switch开关*/
    $('.hyx-sms').find('.switch').click(function(){
    	var $this =$(this);
    	var $id =$this.attr('id').split('_')[1];    	
    	if($this.attr('name') == 'on'){
    		$("#dic_"+$id).val("0"); // 设置关闭
    		changeStatus($this,"on")
    	}else{
    		$("#dic_"+$id).val("1"); // 设置开启
    		changeStatus($this,"off")
    	}    	
    });
    //资源回收设置禁用未选中项
    $(".recycling-radio").on("change",function(e){
    	e.stopPropagation();
    	var $this = $(this);
    	var current=$this.parent().find("input,select").not($(".recycling-radio"));
    	var other=$this.parent().siblings().find("input,select").not($(".recycling-radio"));
    	var flag=current[0].disabled;
    	if(flag){//如果当前禁用
    		current.each(function(i,item){//如果当前禁用就打开当前 禁用另一项
    			item.disabled=false;
    		})
    		other.each(function(i,item){
    			item.disabled=true;
    		})
    	}else{
    		current.each(function(i,item){
    			item.disabled=true;
    		})
    		other.each(function(i,item){
    			item.disabled=false;
    		})
    	}
    })
    //点击弹幕生日祝福时间弹出时间控件   
 	$(".auto-birthday-out-wav").unbind('click').click(function(){
		WdatePicker({dateFmt:'HH:mm',readOnly:true,onpicked:timeChange,changing:timeChange,oncleared:timeChange});
	});
    //红包金额验证
    $("body").delegate(".gift-to-somebody-balance-setting","blur",function(){
    	var $this = $(this);
    	var vals  = $.trim($this.val());
    	if(! (/(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/.test($this.val()))){
    		$this.val('');
    		window.top.iDialogMsg("提示","请输入正确格式的金额！");
    	}
    	if(vals > 200){
    		window.top.iDialogMsg("提示","红包金额不能大于200！");
    	}
    })
    //客户跟进设置  操作 子复选框
	$("input[id^='r_']").change(function(){
		var target = $(this).attr('id').split('_')[1];
		if($(this).attr('checked')){
			$('#dictionaryList_'+target).removeAttr('disabled');
			if(target == 13){//13 时的input后还有select  id为14 需要一起禁掉 下同
				$('#dictionaryList_14').removeAttr('disabled');
			}
			if(target == 102){
				$('#dictionaryList_'+target).removeAttr('disabled');
			}
		}else{
			$('#dictionaryList_'+target).attr('disabled',true);
			if(target == 13 ){
				$('#dictionaryList_14').attr('disabled',true);
			}
			if(target == 102){
			$('#dictionaryList_'+target).attr('disabled',true);
			}
		}
	});
	
	 // 操作 客户信息 字段格式验证
	$("input[id^='rr_']").change(function(){
		var target = $(this).attr('id').split('_')[1];
		if($(this).is(':checked')){
			$('#formart_'+target).val(1);
		}else{
			$('#formart_'+target).val(0);
		}
		monthChampion(this)
	});

	// 下次跟进客户默认时间 操作复选框
	$("#r13").change(function(){
		if($(this).attr('checked')){
			$('#dictionaryList_13').removeAttr('disabled');
			$('#dictionaryList_14').removeAttr('disabled');
		}else{
			$('#dictionaryList_13').attr('disabled',true);
			$('#dictionaryList_14').attr('disabled',true);
		}
	});
	
/*	$(".check-man-mustbe").blur(function(){
		var vals = $(this).val();
		var mobilePattern =/^(0?((13[0-9]{1})|(15[^4,\D])|(18[0-9]{1})|(14[0-9]{1})|(17[0-9]{1}))+\d{8})$/;
		if(!mobilePattern.test(vals)){
			window.top.iDialogMsg("提示","请输入正确的手机号码！");
		}
	})*/
	// 当个人资源数少于xx人，系统自动分配 操作复选框
	$("#r27").change(function(){
		if($(this).attr('checked')){
			$('#dictionaryList_27').removeAttr('disabled');
			$('#dictionaryList_28').removeAttr('disabled');
		}else{
			$('#dictionaryList_27').attr('disabled',true);
			$('#dictionaryList_28').attr('disabled',true);
		}
	});
	
	// 客户签约设置
	$("#r45").change(function(){
		if($(this).attr('checked')){
			$('#dic_45').val('1');
		}else{
			$('#dic_45').val('0');
		}
	});
	//客户跟进时间复选框关联隐藏域值
	$(".time-remind-tip").live("change",function(){
		var $this =$(this);
		var inputs = $this.parents("td").find(".time-remind-input");
		var checked = $this[0].checked;
		if(checked){
			inputs.val(1);
		}else{
			inputs.val(0);
		}
	})
	//提交
	$("#savemanageId,#savemanageIds").click(function(e){
		e.stopPropagation();
		var shadom = $(".pending-box")
		var isNull = isInputNull();
		var checkman = checkNull();//判断审核人若启用是否为空
		if(!checkman){
			return false;
		}
		if(isNull){
			window.top.iDialogMsg("提示","输入框中有空值，请核实！！！");
		}else{	
			var dic_39_value = $("#dic_39").val();
			if(dic_39_value == 1){
				var input_groupId = $(".input_groupId");
				var input_shireGroupIds = $(".input_shireGroupIds");
				var length = input_groupId.length;
				if(length == 0){
					window.top.iDialogMsg("提示","公海池数据权限设置已开启！您未设置部门权限！");
					return false;
				}else{
					for(var i=0;i<length;i++){
						if(input_groupId.eq(i).val() == ""  || input_groupId.eq(i).val() == null || input_shireGroupIds.eq(i).val() == ""  || input_shireGroupIds.eq(i).val() == null){
							window.top.iDialogMsg("提示","公海池数据权限设置已开启！您未设置部门权限！");
							return false;
						}
					}
				}
			}
			shadom.show();
			$("#myForm").ajaxSubmit({
				url : ctx+'/sales/manage/updateSalesNorms.do',
				type : 'post',
				dataType : 'JSON',
				error : function(XMLHttpRequest, textStatus){alert("请求失败！");},
				complete:function(){
					shadom.hide();
				},
				success : function(data) {		
					if(data == 0){
						window.top.iDialogMsg("提示","设置成功！");
						resave = $("#myForm").serialize();
						//setTimeout('document.forms[0].submit();',1000);
					}else{
						window.top.iDialogMsg("提示","设置失败！");
					}
				}
			});		
		}
	});
	//客户跟进设置的页面弹窗
	$(".fetur_to_follow").click(function(){
		var jsonStr=$("#fetur_to_follow_input").val();		
		var optionValue=$("#dictionaryList_16").val();				
		pubDivShowIframe("fetur_to_follow_",ctx+"/sales/manage/getDicProcessList?optionValue="+optionValue+"&jsonStr="+encodeURIComponent(encodeURIComponent(jsonStr,"utf-8")),"按销售进程设置",550,400);
	})
	//判断下拉框的值是否为其他 如果是其他后面的文本框以及span出现
	$("select.linjie").change(function(){
		 if($(this).find("option:selected").val() == "其他"){
		 	$(this).next().val("");
			$(this).nextAll().show();
		}else{
			$(this).nextAll().hide();
			$("#linjie_num1").val($(this).find("option:selected").val());
		}
	});

	$("select.shichang").change(function(){
		 if($(this).find("option:selected").val() == "其他"){
		 	$(this).next().val("");
			$(this).nextAll().show();
		}else{
			$(this).nextAll().hide();
			$("#shichang_num1").attr("value",$(this).find("option:selected").val());
		}
	});
	//点击空白处后树结构隐藏
	$(document).click(function(){
		$(".baimingdan").hide();
		$(".heimingdan").hide();
		$(".tree-drop").hide();
	});
	$(".baimingdan").live("click",function(e){
		e.stopPropagation();
	});
	$(".heimingdan").live("click",function(e){
		e.stopPropagation();
	});
	//点击添加名单后弹出框
	clickCommon("black_mingdan","heimingdan","hei_numid");
	clickCommon("white_mingdan","baimingdan","bai_numid");
	//点击标签关闭按钮
	clickToClose("zhangsana","baimingdan","heimingdan",$("#hei_numid"),$("#hei_numval"));
	clickToClose("zhangsans","baimingdan","heimingdan",$("#bai_numid"),$("#bai_numval"));
	//定时分配时长  如果点击文本框或span span隐藏并且文本框获取焦点 如果文本框无内容失去焦点span出现并传值为空
	focuon("linjie");
	focuon("shichang");
	//读取界面时长的值填入
 	loadin("linjie");
	loadin("shichang");
	//判断添加名单前的checkbox是否选中 若未选中则按钮禁用
	//idname 勾选的input id   classname是需要隐藏的label
	disab("rr_43","zhangsans");
	disab("rr_44","zhangsana");
	//页面加载如果有标签就添加
	replay('bai_numid','bai_numval','first');
	replay('hei_numid','hei_numval','last');
	//资源安全树提交和取消时候的处理
	resourceSafeTree("submit_rights","clean_rights",$("#bai_numid"),$("#bai_numval"),"first");
	resourceSafeTree("submit_righta","clean_righta",$("#hei_numid"),$("#hei_numval"),"last");
});
//判断当开启开关时，输入框不能为空。
function isInputNull(){
	var flag = false;
	//选中所有开启的选项
	$("i[id^='on_']").each(function(){
		if($(this).attr("name") == "on"){
			var num = $(this).attr("id").substring(3);// 下划线后面的数字
			var parentObj = $("#div_" + num ); //  包含输入框和复选框的div
			//获取选中的复选框子类
			var childObj = parentObj.find("input[type='checkbox']:checked");
			if(childObj.length > 0){// 选中的复选框
				childObj.each(function(){
					var childNum = $(this).attr('id').substring(1);					
					var $input = $("#dictionaryList_" + childNum );
					//判断是否该子类为文本型,排除'selected' 类型
					if($input.is('input')){
						var value = $.trim($input.val());
						if(value == '' || value == null){
							flag = true;
							return false ;
						}
					}
				});
			}
			if(flag){
				return flag;
			}
			var childObj = parentObj.find("input[type='checkbox']");
			//获取不含复选框子类
			if(childObj.length == 0){
				var childObj = parentObj.find("input[type='text']");	
				childObj.each(function(){
					var value = $.trim($(this).val());
					if(value == '' || value == null){
						flag = true;
						return false ;
					}
				});
			}		 //如果含复选框,不处理	
		}			
	});
   return 	flag ;
}
function checkNull(){
	var flag = true;
	var mobilePattern =/^(0?1[123456789]\d{9})$/;
	var isOpen = $(".cust-info-output-setting").get(0).checked;
	var $target = $(".check-man-mustbe");
	var remind = $target.parent().find(".reminder");
	var timeRequied = $(".followCust-timeSetting:visible");
	var birthdayMust = $(".gift-to-somebody-balance-setting:visible");
	var returnOrder = $(".return-order-amount:visible");
	remind.text("");
	var vals = $.trim($target.val()) ;
	if(isOpen){
		if(!mobilePattern.test(vals)){
			flag = false;
			remind.text("请输入正确的手机号码！");
			window.top.iDialogMsg("提示","请输入正确的手机号码！");
		}
		if(vals == "" || vals == null){
			remind.text("手机号码必填！")
			flag = false;
		}
	}
	if( isOpen && $("#usertext:visible").val() == ""){
		window.top.iDialogMsg("提示","审核人必填！");
		flag = false;
		return false;
	}
	timeRequied.each(function(i,obj){
		var timeVal = $.trim($(obj).val());
		if(timeVal == "" || timeVal == null){
			flag = false;
			window.top.iDialogMsg("提示","跟进时间段不能为空！");
			return false;
		}
	})
	birthdayMust.each(function(i,obj){
		var birthVal = $.trim($(obj).val());
		if(birthVal == "" || birthVal == null){
			flag = false;
			window.top.iDialogMsg("提示","红包金额不能为空！");
			return false;
		}
		if(birthVal > 200 ){
			flag = false;
			window.top.iDialogMsg("提示","红包金额不能大于200！");
			return false;
		}
	})
	if($(".order-return-radio")[0].checked){
		returnOrder.each(function(i,obj){
			var val = $.trim($(obj).val());
			if(val == "" || val == null){
				flag = false;
				window.top.iDialogMsg("提示","回款金额范围不能为空！");
				return false;
			}
			if($(obj).data("flag") && $(obj).data("flag") == "false"){
				flag = false;
				window.top.iDialogMsg("提示","订单金额范围有误！");
				return false;
			}
		})
	}
	//意向客户上限不能为空
	if($("#r_9:visible").length > 0 ){
		var willCheck = $("#r_9:visible").get(0).checked;
		if(willCheck && $(".personal-willcust-max:visible").length > 0 ){
			var willCheckVals = $.trim($(".personal-willcust-max:visible").val());
			if(willCheckVals == "" || willCheckVals == null){
				flag = false;
				window.top.iDialogMsg("提示","个人拥有意向客户上限值不能为空！");
				return false;
			}
		}
	}
	//意向客户超过数目
	if($("#r_16:visible").length > 0){
		var willCustNum = $("#r_16:visible").get(0).checked;
		if(willCustNum && $(".willCust-num-overwrite:visible").length > 0 ){
			var willNumVals = $.trim($(".willCust-num-overwrite:visible").val());
			if(willNumVals == "" || willNumVals == null){
				flag = false;
				window.top.iDialogMsg("提示","意向客户回收上限天数不能为空！");
				return false;
			}
		}
	}
	//资源上限不能为空
	if($("#r_18:visible").length > 0){
		var resMaxCheck = $("#r_18:visible").get(0).checked;
		if(resMaxCheck && $(".res-max-number:visible").length > 0 ){
			var resNumVals = $.trim($(".res-max-number:visible").val());
			if(resNumVals == "" || resNumVals == null){
				flag = false;
				window.top.iDialogMsg("提示","个人拥有资源上限值不能为空！");
				return false;
			}
		}
	}
	//客户签单最长期限
	if($("#r_7:visible").length > 0){
		var signTimeCheck = $("#r_7:visible").get(0).checked;
		if(signTimeCheck && $(".sign-time-max:visible").length > 0 ){
			var signTime = $.trim($(".sign-time-max:visible").val());
			if(signTime == "" || signTime == null){
				flag = false;
				window.top.iDialogMsg("提示","客户签单最长期限不能为空！");
				return false;
			}
		}
	}
	return flag;
}
//开关变化
function changeStatus(obj,type){
	if(type == "on"){
		obj.addClass('switch-hover');
		obj.attr('name','off');
		obj.parent().next('.switch-hid').hide();
	}else{
		obj.removeClass('switch-hover');
		obj.attr('name','on');
		obj.parent().next('.switch-hid').show();
	}
}
//点击添加名单后弹出框函数
function clickCommon(className,classNameBox,valueInput){
	$("."+className).live("click",function(e){
		e.stopPropagation();
		var idialogBox = $("."+classNameBox);
		var $tree = idialogBox.find(".easyui-tree");
		var cid = $("#"+valueInput).val();
		var roots = $tree.tree("getRoots");
		for(var i = 0 ; i<roots.length; i++){
			var node = $tree.tree("find",roots[i].id);
			$tree.tree("uncheck",node.target);
		}
		if(cid != null && cid.length > 0 ){
			var text = "";
			$(cid.split(",")).each(function(index,data){
				var node = $tree.tree("find",data);
				if(node != null){
					text += node.text+",";
					$tree.tree("check",node.target);
				}
			})
		}
		var hei_posi = idialogBox.parent().find(".tit_b").first().position();
		idialogBox.css("top",hei_posi.top+"px");
		idialogBox.show();
		return false;
	})
}
//点击标签关闭按钮函数
function clickToClose(className,firstlistName,secondlistName,idsBox,valsBox){
	$("."+className).find("span i").live("click",function(e){
		e.stopPropagation();
		$("."+firstlistName).hide();
		$("."+secondlistName).hide();
		var $this = $(this);
		var $that = $this.parent();
		var cid = $that.attr("id");
		var vals = $that.attr("value");
		idsBox.val(idsBox.val().replace(cid+",",""));
		valsBox.val(valsBox.val().replace(vals+",",""));
		$that.remove();
	})
}
//定时分配时长  如果点击文本框或span span隐藏并且文本框获取焦点 如果文本框无内容失去焦点span出现并传值为空
function focuon(classname){
	$("."+classname+"-span").click(function(){
		$(this).hide();
		$(this).prev().focus();
	});
	$("."+classname+"-span").prev().focus(function(){
		$(this).next().hide();
	});
	$("."+classname+"-span").prev().blur(function(){
		if($("."+classname+"-span").prev().val() != ''){
			 $("#"+classname+"_num1").val($("."+classname+"-span").prev().val());
		}else{
			$(this).next().show();
			$("#"+classname+"_num1").val("");
		};
	});
};
//读取界面时长的值填入
function loadin(idname){
	var num=$("#"+idname+"_num1").val();
	var flag=false;
	if($("select."+idname).find("option[value='其他']").prop("selected")){
		$("select."+idname).next().show();
	}
};
//判断添加名单前的checkbox是否选中 若未选中则按钮禁用
//idname 勾选的input id   classname是需要隐藏的label
function disab(idname,classname){
	var labels=$("."+classname);
	if(!$("#"+idname).prop("checked")){
		$("#"+idname).parent().parent().find("button").attr("disabled","disabled");
		labels.hide();
	}
	$("#"+idname).change(function(){
		var buttons=$(this).parent().parent().find("button");
		if($(this).prop("checked")){
			buttons.removeAttr("disabled");
			labels.show();
		}else{
			buttons.attr("disabled","disabled");
			labels.hide();
		}
	})
}
//页面加载如果有标签就添加
function replay(idname,valname,cixu){
	var str1=$("#"+idname).val();
	var str2=$("#"+valname).val();
	if($.trim(str1) != "" && $.trim(str2) != ""){
		var arr1=str1.split(",");
		var arr2=str2.split(",");
		for(var i=0;i<arr1.length;i++){
		if(arr1[i] != ""&& arr2[i] != ""){
			var strs="<span id="+arr1[i]+" value="+arr2[i]+" title="+arr2[i]+">"+arr2[i]+"<i>×</i></span>";
			$(".tit_c:"+cixu).find("label").append(strs);
			}
		}
	}
}
//月度冠军复选和select逻辑
function monthChampion(obj){
	var $this = $(obj);
	var target = $this.attr('id').split('_')[1];
	var next = $this.next();
	if(target >=86 && target <= 90){
		if($this.is(":checked")){
			next.removeAttr("disabled");
		}else{
			next.find("option[value='0']").attr("selected","selected");
			next.change();
			next.attr("disabled","true");
		}
	}
}
//资源安全树提交和取消时候的处理
function resourceSafeTree(submit_btn,clean_btn,ids,vals,type){
	$("."+submit_btn).live("click",function(e){
		e.stopPropagation();
		var $this = $(this);
		var $that = $this.parent().parent();
		var $tree = $that.find(".easyui-tree");
		var nodes = $tree.tree("getChecked");
		var nodes_text = "";
		var nodes_id = "";
		$that.parent().find(".tit_c:"+type).find("span").remove();//去除之前的标签
		for(var i = 0 ; i <nodes.length; i ++){
			if(nodes[i].attributes.type != "M"){
				continue;
			}
			nodes_text += nodes[i].text+",";
			nodes_id += nodes[i].id+",";
			var strs="<span id="+nodes[i].id+" value="+nodes[i].text+" title="+nodes[i].text+">"+nodes[i].text+"<i>×</i></span>";
			$that.parent().find(".tit_c:"+type).find("label").append(strs);
		}
		ids.val(nodes_id);
		vals.val(nodes_text);
		$that.hide();
	});
	$("."+clean_btn).live("click",function(e){
		e.stopPropagation();
		var $this = $(this);
		var $that = $this.parent().parent();
		var $tree = $that.find(".easyui-tree");
		var nodes = $tree.tree("getChecked");
		for(var i = 0 ; i < nodes.length ; i++){
			$tree.tree("uncheck",nodes[i].target);
		}
	})
}
//树方法
function judge_duplicates(node_id,node_text){
	var group_ids = $(".input_groupId");
	if(node_id == "" && node_text == ""){
		return true;
	}
	for(var i=0;i<group_ids.length;i++){
		if(group_ids.eq(i).val() == node_id){
			window.top.iDialogMsg("提示",node_text + "已经存在！");
			return false;
		}
	}
	return true;
}
function timeChange(){
	$(".WdateDiv").hide();
}