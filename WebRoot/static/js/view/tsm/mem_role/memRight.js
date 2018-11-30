$(function(){		
		/*表单优化*/
	    $('.skin-minimal input').iCheck({
	        checkboxClass: 'icheckbox_minimal',
	        radioClass: 'iradio_minimal',
	        increaseArea: '20%'
	    });
	    

		
		//开始拥有时间
		 $('#date-range1').dateRangePicker({
			 	showShortcuts:false
		    }).bind('datepicker-apply',function(event,obj){
		    	var s_t = '';
		    	var e_t = '';
		    	if(obj.date1 != 'Invalid Date'){
		    		$('#d_startDate').val(moment(obj.date1).format('YYYY-MM-DD'));
		    		s_t = moment(obj.date1).format('YY.MM.DD');
		    	}
		    	if(obj.date2 != 'Invalid Date'){
		    		$('#d_endDate').val(moment(obj.date2).format('YYYY-MM-DD'));
		    		e_t = moment(obj.date2).format('YY.MM.DD');
		    	}
		    	var s = $(this).parents('.select');
		    	var dt=s.children("dt");
		        var dd=s.children("dd");
		        dt.html(s_t+'/'+e_t);
		        $("#dDateType").val(5);
		        dd.slideUp(200);
		        dt.removeClass("cur");
		        s.css("z-index",z);
		 });

		 
		// 编辑角色
		$("#editRole").click(function(e){
			e.stopPropagation();
			batchOpreat(1);
		});
		
		// 批量修改密码
		$("#batchUpdatepwd").click(function(e){
			e.stopPropagation();
			batchOpreat(2);
		});		
		
		// 变更部门
		$("#batchUpdateDept").click(function(e){
			e.stopPropagation();
			batchOpreat(3);
		});			
		
		// 账号初始化
		$("#batchCleanAccounts").click(function(e){
			e.stopPropagation();
			batchOpreat(4);
		});		
		
		//批量设置管辖权限
		$("#batchSettingPower").click(function(e){
			e.stopPropagation();
			batchOpreat(5);
		})
	});
		
		/**
		 * 批量操作：编辑角色,密码修改,变更部门
		 */
		function batchOpreat(optType){
			var custIdCounts = $("input[name='memberAcc']:checked").length;
				if(custIdCounts==0){
					window.top.iDialogMsg("提示","请选择成员！");
					return false;
				}	
				var accs = "";
				var userIds = "";
				$("input[name='memberAcc']:checked").each(function(){
					accs += $(this).val() + ",";
					userIds += $(this).attr("memberId") + ",";
				});	
				if(optType == 1){
					openMemberRoleEdit(userIds,accs);
				}else if(optType == 2){	
					openMemberPassEdit(accs);
				}else if(optType == 3){	
					openMemberDeptEdit(userIds,accs);
				}else if(optType == 4){
					queDivDialog("clean_accounts","是否 初始化选中成员的密码，成员姓名、性别、出生年月、手机号码、电子邮箱、职务、通信地址以及呼叫转移和消息提醒的电话解绑？",function(){
						batchCleanAccounts(accs);
					});	
				}else if(optType == 5){
					openMemberPowerBatchEdit(userIds,accs);
				}
		}
		
		var setDdate = function(i){
			$('#d_startDate').val('');
			$('#d_endDate').val('');
			$("#dDateType").val(i);
		}		
		
		function ajax_Submit(){
			$("#myForm").ajaxSubmit({
				dataType:'html',
				type : 'post',
				error:function(){},
				success:function(data){
					$("#memb_right").html(data);			
				}
			});	
		}
		/**
		 * 成员编辑
		 */
		function openMemberEdit(userId){
			window.parent.openMemberEdit(userId);
		}
		
		/** 密码修改*/
		function openMemberPassEdit(memAcc){
			window.parent.openMemberPassEdit(memAcc);
		}
		
		/** 角色编辑 */
		function openMemberRoleEdit(userIds,userCounts){
			window.parent.openMemberRoleEdit(userIds,userCounts);
		}
		
		/** 变更部门 */
		function openMemberDeptEdit(userIds,userCounts){
			window.parent.openMemberDeptEdit(userIds,userCounts);
		}
		
		/** 管辖权限 */
		function openMemberPowerBatchEdit(userIds,accs){
			window.parent.openMemberPowerBatchEdit(userIds,accs);
		}
		
		/** 账号初始化 */
		function batchCleanAccounts(userCounts){
			$.ajax({
				url:ctx+'/auth/user/userAccountInit.do',
				type:'POST',
				data:{'userAccounts':userCounts},
				dataType:'html',
				error:function(){window.top.iDialogMsg("提示","网络异常，请稍后尝试!");},
				success:function(data){
					if(data == 0){
						window.top.iDialogMsg("提示","初始化成功！");
						refreshPageData(pa.pageParameter());
					}else{
						window.top.iDialogMsg("提示","初始化失败！");
					}
				}
			});
		}
