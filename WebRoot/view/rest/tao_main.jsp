<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>

    <title>淘客户算法测试</title>
    <%@ include file="/common/include.jsp" %>
    <script type="text/javascript">
        var list = new Array();
        var up = "";
        var next = "";
        var on = 0;
        jQuery(document).ready(function () {
            init(1);

        });
        function init(type) {
            list = new Array();
            on = 0;
            // alert(type);
            $.ajax({
                type: "POST",
                dataType: 'JSON',
                url: ctx + '/tt/query?type=' + type,
                success: function (data) {
                    var obj = eval(data);
                    var str = "<ul id=\"nav1\">";
                    for (var i = 0; i < obj.length; i++) {
                        list[list.length] = obj[i].resId;
                        if (i == 0) {
                            up = obj[i].resId;
                            on = i
                        }
                        str += "<li id='" + obj[i].resId + "'>";
                        str += "<span id='t" + obj[i].resId + "' style=\"color:green\">"+obj[i].resId+"<span>";
                        str += "<a href=\"javascript:addTab('" + obj[i].resId + "')\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + obj[i].resId + "</a>";
                        str += "</li>";
                        if (i == obj.length - 1) {
                            next = obj[i].resId;
                        }
                    }
                    str += "  </ul>";
                    // alert(str);
                    $("#main").html(str);
                    $("#t" + list[on]).attr("style", "color:red");
                }
            });
        }
        function onUp() {
            $("#t" + list[on]).attr("style", "color:green");
            --on;
            if( (on<1)){on=0;init(1);}
        }
        function onNext() {
            ++on;

           // alert(on+"---->"+list[on]);
            $("#t" + list[on]).attr("style", "color:red");
            if( (on >9)){on=0;init(0);}
        }
    </script>
    <style type="text/css">
        * {
            margin: 0;
            padding: 0;
            border: none;
        }

        ul {
            list-style-image: url(/static/images/u41.png);
            /*     list-style-image:url(/static/images/sprite.png);*/
            clip: rect(0px 0px 100px 20px) list-style-position : inside;
        }

    </style>
</head>
<body>
<div id="main" class="navwrap1"></div>
<div id="main1" class="navwrap1" style="display: none">
    <ul>
        <li>This is a list item</li>
        <li>This is a list item</li>
        <li>This is a list item</li>
        <li>This is a list item</li>
        <li>This is a list item</li>
        <li>This is a list item</li>
        <li>This is a list item</li>
    </ul>

</div>
<a class="easyui-linkbutton" icon="icon-search" onclick="init(1);" href="javascript:void(0)">上一页</a>
<a class="easyui-linkbutton" icon="icon-search" onclick="onUp();" href="javascript:void(0)">上一条</a>
<a class="easyui-linkbutton" icon="icon-search" onclick="onNext();" href="javascript:void(0)">下一条</a>
<a class="easyui-linkbutton" icon="icon-search" onclick="init(2);" href="javascript:void(0)">下一页</a>
</body>
</html>
