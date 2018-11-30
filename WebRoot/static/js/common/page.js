var pa = {
	_backFn:null,
	load:function(id,pp,backFn,tableObj){//tableObj是获取到table的jq对象
		this._backFn = backFn;
		var html=
			'<input type=\"hidden\"  id=\"page_totalResult\" name=\"totalResult\" value="' + pp.totalResult + '"></input>'+
			'<div class="com-pp fl_r">'+
			'	<a href="javascript:;" '+((pp.currentPage==1 || pp.currentPage==0)?'class="no"':'onclick="pa.pageFirst()"')+'>首页</a>'+
			'	<a href="javascript:;" '+((pp.currentPage==1 || pp.currentPage==0)?'class="no"':'onclick="pa.pagePrev('+(pp.currentPage-1)+')"')+'>上页</a>'+
			'	<input name="currentPage" type="text"class="pa" maxlength="5" onchange="pa.pageChange(this.value)" onkeydown="if(event.keyCode==13){pa.pageChange(this.value);}" value="'+pp.currentPage+'">'+
			'	<label>/'+pp.totalPage+'</label>'+
			'	<a href="javascript:;"  '+(pp.currentPage==pp.totalPage?'class="no"':' onclick="pa.pageNext('+(pp.currentPage+1)+')"')+'>下页</a>'+
			'   <a href="javascript:;"  '+(pp.currentPage==pp.totalPage?'class="no"':' onclick="pa.pageLast('+pp.totalPage+')"')+'>尾页</a>'+
			'	<select name="showCount"  id="pageShowCount" onchange="pa.pageShowCount(this.value);" >'+
				'	<option value="10" '+(pp.showCount==10?'selected':'')+'>10</option>'+
				'	<option value="20" '+(pp.showCount==20?'selected':'')+'>20</option>'+
				'	<option value="30" '+(pp.showCount==30?'selected':'')+'>30</option>'+
				'	<option value="50" '+(pp.showCount==50?'selected':'')+'>50</option>'+
				'	<option value="100" '+(pp.showCount==100?'selected':'')+'>100</option>'+
			'	</select>'+
			'	<label>条/页</label>&nbsp;&nbsp;共&nbsp;'+
			'	<span>'+
			'		<b>'+pp.totalResult+'</b>'+
			'	</span>&nbsp;条&nbsp;'+
			'</div>';
		delete pp.pageStr;
        delete pp.pageSubStr;
        delete pp.ajaxPageStr;
		$("#"+id).data("page",JSON.stringify(pp))
		$("#"+id).html(html);
		
		var tbody=tableObj.find("tbody");
		var trNums=tbody.find("tr").length;
		var column = tableObj.find("thead").find("th:visible").length;//表格列数
		var tableStr="";
		for(var i = trNums;i<10;i++){
			if(i == 0){
				tableStr  +="<tr><td colspan='" + column + "'>当前列表无数据！</td></tr>";
			}else{
				tableStr  +="<tr class="+(i % 2 ==0? '': 'sty-bgcolor-b')+"><td colspan='" + column + "'></td></tr>";
			}
		}
		tbody.append(tableStr);
	},
	pageFirst:function(){
		$("input[name='currentPage']").val(1);
		this._backFn({totalResult:$("#page_totalResult").val(),currentPage:$("input[name='currentPage']").val(),showCount:$("[name='showCount']").val(),action:'1'});
	},
	pageLast:function(total){
		$("[name='currentPage']").val(total);
        this._backFn({totalResult:$("#page_totalResult").val(),currentPage:$("input[name='currentPage']").val(),showCount:$("[name='showCount']").val(),action:'4'});
//		this._backFn();
	},
	pagePrev : function(curr){
		$("[name='currentPage']").val(curr);
        this._backFn({totalResult:$("#page_totalResult").val(),currentPage:$("input[name='currentPage']").val(),showCount:$("[name='showCount']").val(),action:'2'});

//		this._backFn();
	},
	pageNext:function(curr){
		$("[name='currentPage']").val(curr);
        this._backFn({totalResult:$("#page_totalResult").val(),currentPage:$("input[name='currentPage']").val(),showCount:$("[name='showCount']").val(),action:'3'});

//		this._backFn();
	},
	pageChange:function(curr){
		$("[name='currentPage']").val(curr);
        this._backFn({totalResult:$("#page_totalResult").val(),currentPage:$("input[name='currentPage']").val(),showCount:$("[name='showCount']").val()});

//		this._backFn();
	},
	pageShowCount:function(count){
		$("[name='page.showCount']").val(count);
		$("input[name='currentPage']").val("1");
        this._backFn({totalResult:$("#page_totalResult").val(),currentPage:$("input[name='currentPage']").val(),showCount:$("[name='showCount']").val()});

//		this._backFn();
	},
	pageParameter:function(){
		return {totalResult:$("#page_totalResult").val(),currentPage:$("input[name='currentPage']").val(),showCount:$("[name='showCount']").val()};
	}
};
