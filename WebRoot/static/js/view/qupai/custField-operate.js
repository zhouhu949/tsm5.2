$(function(){
	$('#operaForm').delegate('.btn-add-option','click',function(e){
		var list_count = $('.form-group-selection')?$('.form-group-selection').length:0;
		//console.log(list_count)
		var selection = `<div class="form-group-selection">
							<input type='text' class="optionName" name='listItemName_${list_count}' data-index='${list_count}' value="" data-rule-required="true" data-rule-selectionInputNotSame="true" maxlength="10" class='ipt_a' />
							<input type='hidden' class="optionlistId" value="" />
							<input type='checkbox' class="isDefault" value="1" />
							<button type="button" class="btn-remove-option">-</button>
							<span class="error"></span>
						</div>`;
		$('.bomp-selections').append(selection);
	});
});