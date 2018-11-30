<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/include.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx }/static/css/dialog.css${_v}"><!--主要样式-->
  <form id="complaint_submit" method="post">
    <input type="hidden" id="comp_type" name="type" value="${type}">
			<div class="complaint_list_idbox clearfix">
	  			<div class="cust-impo-res" style="width:auto;padding-left:0;">
	    			<div class="com-search clearfix" style="height:50px;min-height:50px;">
	    				<label class="fl_l" for="">联系号码：</label>
	    				<input type="text" name="phone">
			              <div class="help-block">
			                <span class="error"></span>
			              </div>
	    			</div>
	    			<div class="compaint_list_checkbox">
	    				<label>
	    					<input type="checkbox" name="compaint_type1" class="compaint_list_idialog_check"  checked/>限制呼出
	    				</label>
	    				<label>
	    					<input type="checkbox" name="compaint_type2" class="compaint_list_idialog_check"  checked/>限制呼入
	    				</label>
	    			</div>
	  			</div>
	  			<div class="bomb-btn">
	  				<label class="bomb-btn-cen">
	  					<a class="com-btna bomp-btna com-btna-sty fl_l" onclick="submitForm();" href="javascript:;"><label>确定</label></a>
	  					<a class="com-btna bomp-btna fl_l" id="close02"  onclick="close02();" href="javascript:;"><label>取消</label></a>
	  				</label>
	  			</div>
			</div>
  </form>
  <script type="text/javascript">
  	// 保存
  	function submitForm(){
      var phone=$("input[name='phone']").val();
    	if(!/^0(([1-9]\d)|([3-9]\d{2}))\d{7,8}$/.test(phone)&&! /^1(3|4|5|7|8)\d{9}$/.test(phone)){  
    		$(".error").text("格式不正确！")
    		return false;
    	}
      $('#complaint_submit').ajaxSubmit({
        url : '${ctx}/res/cust/insertBlackList',
        type : 'post',
        error : function(XMLHttpRequest, textStatus){alert("请求失败！");},
        success : function(data) {
          console.log(data)
          if(data.status == true){
            window.top.iDialogMsg("提示","保存成功！");
            setTimeout(function(){
            	close02();
            	window.parent.location.reload();
            },1000);
          }else{
            window.top.iDialogMsg("提示",data.message);
          }
        }
      });
	}

  	// 取消
  	function close02(){
  		closeParentPubDivDialogIframe('compaint_list_add');
  	}
  	
  	$(function(){
  		$(".compaint_list_idialog_check").change(function(){
			var checked=$(".compaint_list_idialog_check:checked");
			var $this = $(this) ;
			if(checked.length < 1 ){
				$(".compaint_list_idialog_check").not($this)[0].checked = true;
			}
		});
  	})

  </script>
