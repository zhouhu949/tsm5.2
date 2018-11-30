$(function () {
        //表格行数
        var rows = $(".sty-borcolor-a").find("tbody").find("tr").length;
        //表格列数
        var column = $(".sty-borcolor-a").find("thead").find("th").length;
        //   alert(rows + "-" + column);
        var str = "<tr><td colspan=\"" + column + "\"></td></tr>";
        
        if($(".no_data_tr>td")){
        	$(".no_data_tr>td").attr("colspan",column);
        }
       /* if (rows == 1) {
            //无数据判断
            $(".sty-borcolor-a").find("tbody").html("");
            for (var i = 0; i < 10; i++) {
                $(".sty-borcolor-a").find("tbody").append(str);
            }
        }*/
        //补10条
        if (rows < 10) {
            for (var i = rows; i < 10; i++) {
                $(".sty-borcolor-a").find("tbody").append(str);
            }

        }
        //表格偶数行
        $("table tbody tr:odd").addClass("sty-bgcolor-b");
});
