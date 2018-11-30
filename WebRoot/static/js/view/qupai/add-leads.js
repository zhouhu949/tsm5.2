$(function(){
/*  $("#addBtn").on("click",function(e){
	  e.stopPropagation();
	  var form = $("#cform")
  })
  $.ajax({
	  url:ctx+"/credit/lead/mySignCustsData",
	  data:{queryType:"custName",queryText:"CHM"},
	  success:function(result){
		  console.log(result)
	  }
  })*/
	// 解决validate 与 readonly 冲突问题
/*	$('#leadOperateForm').on("focusin", "[readonly]", function() {
		$(this).prop('readonly', true);  
	});

	$('#leadOperateForm').on("focusout", "[readonly]", function() {
		$(this).prop('readonly', false); 
	});*/
	
	$('[data-readonly]').each(function(idx,item){
		var $item = $(item);
		$item.on("focusin",  function() {
			$item.prop('readonly', true);  
		});
		$item.on("focusout",  function() {
			$item.prop('readonly', false);  
		});
	});

	if($('#leadCode').val() == ""){
		$.ajax({
			url: ctx+'/credit/lead/defaultLeadCode',
			type: 'get',
			cache: false,
			success: function(data){
				$('#leadCode').val(data);
			},
			error: function(XMLRequest,textStatus){
				
			}
		});
	}
  	
  	var qupaiFiledToSysFieldArr = [];
  	$.ajax({
  		url: ctx+'/credit/lead/refFieldMap',
  		type: 'get',
  		async: false,
  		success: function(data){
  			console.log(data);
  			var fieldMap = data.fieldMap;
  			for(var i=0;i<fieldMap.length;i++){
  				if(fieldMap[i].qupaiField && fieldMap[i].resField){
  					qupaiFiledToSysFieldArr.push({
  						qupaiFieldCode:fieldMap[i].qupaiField.fieldCode,
  						sysFieldCode:fieldMap[i].resField.fieldCode
  					});
  				}
  			}
  		},
  		error: function(XMLRequest,textStatus){
  			
  		}
  	})
  	
	$('#borrowMoney').on("blur",function(e){
		var $this = $(this);
		var $this_value = parseFloat((/((^[-]?([1-9]\d*))|^0)(\.\d{1,2})?$|(^[-]0\.\d{1,2}$)/.test($this.val()))?$this.val():0).toFixed(2);
	  
	  	$('[data-datatype="7"]').each(function(idx,item){
	  		var $item = $(item);
	  		var fieldValue = parseFloat($item.data('fieldvalue')?$item.data('fieldvalue'):0);
	  		$item.val(($this_value*fieldValue/100).toFixed(2));
		});
	  
	  	$('[data-datatype="8"]').each(function(idx,item){
	  		var $item = $(item);
	  		var serviceMoneyTotal = 0;
	  		
	  		$('[data-fieldcode^="serviceMoney"]').each(function(idx,item){
	  			var $item = $(item);
	  			serviceMoneyTotal += parseFloat($item.val());
	  		});
	  		
	  		$item.val(($this_value-serviceMoneyTotal).toFixed(2));
		});
	});
  	
  	$('#borrowDate').on("blur",function(e){
		var $this = $(this);
		var $this_value = $this.val();
	  	
	  	$('[data-datatype="9"]').each(function(idx,item){
			  var $item = $(item);
			  var fieldValue = parseInt($item.data('fieldvalue')?$item.data('fieldvalue'):0);
			  if($this_value!=""){
				  $item.val(moment($this_value).add(fieldValue,'days').format("YYYY-MM-DD"));
			  }
		});
	});
  	
  	$(document).on('click',function(e){
  		e.stopPropagation();
  		$('.custname-drop').hide();
  	});
  	
  	$('#custName').on('click',function(e){
  		e.stopPropagation();
  		$('.custname-drop').show();
  	});
  	
  	$('.custname-drop').on('click',function(e){
  		e.stopPropagation();
  		
  	});

  	$('.custname-drop .btn-custname-search').on('click',function(){
  		var search_text = $('.custname-drop .search-text').val();
  		var li_html = "";
  		$.ajax({
  			url: ctx + '/credit/lead/mySignCustsData?page.showCount=30&queryType=custName&searchOwnerType=2&osType=2&queryText=' + search_text,
  			type: 'get',
  			success: function(data){
  				var list = data.list;
  				if(list.length>0){
  					for(var i=0;i<list.length;i++){
  						li_html += `<li data-item='${JSON.stringify(list[i])}'>${list[i].name}</li>`;
  					}
  					$('.custname-drop .body>ul').html(li_html);
  					
  					$('.custname-drop-ul>li').on('click',function(){
  				  		var $this = $(this);
  				  		var itemJson = $this.data('item');
  				  		console.log(itemJson);
  				  		
  				  		$('#custName').val(itemJson['name']);
  				  		$('input[name="rciId"]').val(itemJson['resCustId']);
  				  		$('input[name="company"]').val(itemJson['company']?itemJson['company']:"");
  				  		
  				  		$('[data-datatype="6"]').each(function(idx,item){
  					  		var $item = $(item);
  					  		var fieldCode = $item.data('fieldcode');
  					  		var sysFieldCode = qupaiFieldCodeToSysFieldCode(qupaiFiledToSysFieldArr,fieldCode)
  					  		$item.val(itemJson[sysFieldCode]);
  					  		if(fieldCode == 'phone'){
  					  		    $item.data('hiddenvalue',itemJson[sysFieldCode]);
  					  		}
  						});
  				  		$('.custname-drop').hide();
  				  		
  				  		if($('#cardId').val() != ""){
  				  			$.ajax({
  				  				url: ctx+'/credit/lead/loanCount?cardId='+$('#cardId').val(),
  				  				type: 'get',
  				  				success: function(data){
  				  					if(data.resultCode == 1 && data.result>0){
  				  						$('#cardId').parent().find('span.error').text('该客户已贷款'+data.result+'次');
  				  					}else{
  				  					    $('#cardId').parent().find('span.error').text('');
  				  					}
  				  				}
  				  			})
  				  		}else {
  				  		    $('#cardId').parent().find('span.error').text('');
  				  		}
  				  		
	  				  	$('[data-fieldcode=phone]').each(function(idx,item){
	  						var $item = $(item);
	  						var idReplaceWord = $("#idReplaceWord").val();
	  				    	if(idReplaceWord==1){
	  				    		$item.trigger('replaceWord');
	  				    	}
	  					});
  				  	});
  				}else{
  					$('.custname-drop .body>ul').html("搜索无结果，请重新查询");
  				}
  			}
  		});
  	});
  	
  	$('.btn-submit').on('click',function(){
  		if($('#leadOperateForm').valid()){
  			var leadId = $('input[name="leadId"]').val();
  			var url = ctx + '/credit/lead/editLead';
  			if(!leadId){
  				url = ctx + '/credit/lead/addLead';
  			}
  			$('[data-disabled]').each(function(idx,item){
                $(item).prop('disabled', false);
            });
  			var data = $('#leadOperateForm').serializeObject();
  			if($('[data-fieldcode=phone]').length > 0){
  			    data.phone = $('[data-fieldcode=phone]').data('hiddenvalue');
  			}
  			$.ajax({
  	  			url: url,
  	  			type: 'post',
  	  			data: data,
  	  			success: function(data){
  	  				if(data.resultCode == 1){
  	  					parent.iDialogMsg('提示','保存成功！');
  	  					parent.closePubDivDialogIframe('add_leads');
  	  					parent.loadData();
  	  				}else{
  	  					parent.iDialogMsg('提示',data.resultDesc);
  	  					$('[data-disabled]').each(function(idx,item){
                            $(item).prop('disabled', true);
                        });
  	  				}
  	  			},
  	  			error: function(XMLRequest,textStatus){
  	  				$('[data-disabled]').each(function(idx,item){
                        $(item).prop('disabled', true);
                    });
  	  			}
  	  		})
  		}
  	});

  	$('[data-moneyformat]').each(function(idx,item){
        var $item = $(item);
        var num = $item.text();
        num = formatMoney(num);
        $item.text(num);
        $item.attr('title',num);
    });

    $('[data-disabled]').each(function(idx,item){
        var $item = $(item);
        if(item.tagName != 'INPUT'){
            $item.prop('disabled', true);
        }
        $item.attr('onclick', '');
        $item.unbind("click").click(function(){return false;});
    });
});

function qupaiFieldCodeToSysFieldCode(data,qupaiFieldCode){
	for(var i=0;i<data.length;i++){
		if(data[i].qupaiFieldCode == qupaiFieldCode){
			return data[i].sysFieldCode;
		}
	}
}
