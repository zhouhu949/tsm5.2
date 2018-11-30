
	function pubDivDialog(id,title,width,height){
		str="<div class='bomp-cen'>\n" +
				"\t<div class=\"bomp-tip-a\"><label class=\"tip-a fl_l\"></label><span class=\"sp-a fl_l\">确定要执行放入公海操作吗？</span></div>\n" +
				"\t<div class='bomb-btn'>\n" +
				"\t\t<label class='bomb-btn-cen'>\n" +
				"\t\t\t<a href=\"javascript:;\" class=\"com-btna bomp-btna com-btna-sty fl_l\"><label>确定</label></a>\n" +
				"\t\t\t<a href=\"javascript:;\" class=\"com-btna bomp-btna fl_l\" id='close02'><label>取消</label></a>\n" +
				"\t\t</label>\n" +
				"\t</div>\n" +
				"</div>";
		
		var dialog = $.dialog({
			title:title,
			id:id,
			content:str,
			width:width+10,
			height:height,
			drag:true,
			lock: true,
			max:false,
			min:false,
			fixed: true
    });
	}