<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <style type="text/css">
        /*a  upload */
        .a-upload {
            padding: 4px 10px;
            height: 20px;
            line-height: 20px;
            position: relative;
            cursor: pointer;
            color: #888;
            background: #fafafa;
            border: 1px solid #ddd;
            border-radius: 4px;
            overflow: hidden;
            display: inline-block;
            *display: inline;
            *zoom: 1
        }

        .a-upload input {
            position: absolute;
            font-size: 100px;
            right: 0;
            top: 0;
            opacity: 0;
            filter: alpha(opacity=0);
            cursor: pointer
        }

        .a-upload:hover {
            color: #444;
            background: #eee;
            border-color: #ccc;
            text-decoration: none
        }

        .mb20 {
            margin-bottom: 20px;
        }

        .br {
            border: 1px solid #d2d2d3;
            padding: 10px;
        }

        .btn_submit input {
            display: block;
            width: 97px;
            height: 33px;
            line-height: 33px;
            text-align: center;
            color: #d69701;
            font-size: 14px;
            font-weight: bold;
            cursor: pointer;
            border: none;
            background: url(../images/mainbg.gif) no-repeat 0 -291px;
        }

        .btn_submit input.bhover {
            color: #bb9d35;
            background-position: 0 -324px;
        }

        .rtop .update {
            background: url(${ctx}/static/images/icon.jpg) no-repeat;
            width: 130px;
            height: 28px;
            text-indent: -999em;
            float: left;
            display: inline;
            margin: 9px 0 0 35px;
        }

        .yxbox {
            display: none;
            position: absolute;
            width: 400px;
            border: 1px solid #acb5c3;
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 2px 2px 1px #888888;
            min-height: 288px;
            _height: 288px;
            z-index: 299;
        }

        .yxbox h2 {
            line-height: 25px;
            height: 25px;
            background-color: #c1d9f3;
            color: #00264f;
            padding: 0 15px;
        }

        .process {
            line-height: 25px;
            height: 25px;
        }

        .process .progress-box {
            border: 1px solid #333;
            background-color: #d2d2d3;
            width: 200px;
            float: left;
            height: 10px;
            display: inline;
            margin-top: 4px;
            position: relative
        }

        .process .progress-bar {
            background: #6edc00 url("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAAKCAIAAAD6sKMdAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyBpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMC1jMDYwIDYxLjEzNDc3NywgMjAxMC8wMi8xMi0xNzozMjowMCAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNSBXaW5kb3dzIiB4bXBNTTpJbnN0YW5jZUlEPSJ4bXAuaWlkOkEwMEQ2REQ2M0MwODExRTE4OTJGQjE3NkJEOTE5OEM3IiB4bXBNTTpEb2N1bWVudElEPSJ4bXAuZGlkOkEwMEQ2REQ3M0MwODExRTE4OTJGQjE3NkJEOTE5OEM3Ij4gPHhtcE1NOkRlcml2ZWRGcm9tIHN0UmVmOmluc3RhbmNlSUQ9InhtcC5paWQ6QTAwRDZERDQzQzA4MTFFMTg5MkZCMTc2QkQ5MTk4QzciIHN0UmVmOmRvY3VtZW50SUQ9InhtcC5kaWQ6QTAwRDZERDUzQzA4MTFFMTg5MkZCMTc2QkQ5MTk4QzciLz4gPC9yZGY6RGVzY3JpcHRpb24+IDwvcmRmOlJERj4gPC94OnhtcG1ldGE+IDw/eHBhY2tldCBlbmQ9InIiPz6YOUiNAAAAGklEQVR42mIuesrA9P8fFP9F0P/+oogBBBgAgDUTTaSW7EUAAAAASUVORK5CYII=") repeat-x;
            display: inline-block;
            font-size: 0;
            height: 10px;
            line-height: 0;
            margin: 0;
            overflow: hidden;
            -moz-transition: width .3s ease-in-out;
            -webkit-transition: width .3s ease-in-out;
            -o-transition: width .3s ease-in-out;
            -ms-transition: width .3s ease-in-out;
            transition: width .3s ease-in-out;
            width: 0
        }

        .process p {
            border: 1px solid #333;
            background-color: #d2d2d3;
            width: 200px;
            float: left;
            height: 12px;
            display: inline;
            margin-top: 4px;
            position: relative
        }

        .process em {
            position: absolute;
            left: 1px;
            top: 1px;
            background-color: #2661af;
            height: 10px;
            overflow: hidden
        }

        .process span {
            float: left;
            width: 30px;
            text-align: right;
        }

        .info {
            color: #969799;
        }

        .info span {
            display: inline-block;
            padding-right: 5px;
        }

        #TB_overlayBG {
            background: #000;
            left: 0;
            opacity: 0.6;
            position: fixed;
            _position: absolute;
            top: 0;
            z-index: 199;
            display: none;
            width: 100%;
            opacity: 0.6;
            filter: Alpha(Opacity=60)
        }

    </style>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>文件上传</title>
    <%@ include file="/common/include.jsp" %>
    <script type="text/javascript" src="${ctx}/static/js/view/rest/ajaxfileupload.js"></script>
    <script type="text/javascript">
        $(function () {

        });
        var oTimer;
        var fileName = "";

        /**
         * 上传文件
         */
        function ajaxFileUpload() {
         //   ctx="http://up.test.com";
         //   document.domain = "test.com";
            $.ajaxFileUpload({
                url: ctx+"/fileupload/upload",
                secureuri: false,
                fileElementId: 'fileToUpload',
                dataType: 'json',
                jsonp: "callback",
                jsonpCallback: "flightHandler",
                success: function (data, status) {
                  //  alert("返回数据="+JSON.stringify(data));
                    if (typeof(data.status) != 'undefined') {
                        window.clearInterval(oTimer);
                        //  console.log(data);
                        if (data.status == 'success') {
                            $("#info").hide();
                            $("#success_info").show();
                            $("#success_info").text(fileName + "\t上传成功");
                            $("#process").hide();
                            $("#cancel").hide();
                            $("#fileToUpload").val("");
                            window.document.getElementById("fileToUpload").disabled = false;
                            //上传进度和上传速度清0
                            $("#has_upload").text("0");
                            $("#upload_speed").text("0");
                            $("#progress_percent").text("0%");
                            $("#progress_bar").width("0%");
                        } else {
                            $("#progress_all").hide();
                            $("#fileToUpload").val("");
                            if (typeof(data.message) != 'undefined') {
                                alert(data.message);
                            }
                            alert("上传错误！");
                        }
                    }
                },error: function (data, status, e) {
                    alert("返回错误:"+e);
                    window.clearInterval(oTimer);
                }
            });
            return false;
        }
        /**
         * 提交上传文件
         */
        function getProgress() {
           // ctx="http://up.test.com";
            $.ajax({
                async: false,
                url: ctx+"/fileupload/status/progress",
                dataType: "jsonp",
                jsonp: "callback",
                jsonpCallback: "flightHandler",
                success: function (data) {
                    console.log(data);
                    if (typeof(data.percent) != 'undefined') {
                        $("#progress_percent").text(data.percent);
                        $("#progress_bar").width(data.percent);
                        $("#has_upload").text(data.mbContentLength + "MB--->" + data.mbRead);
                        $("#upload_speed").text(data.speed);
                        if (data.percent == "100%") {
                            window.clearInterval(oTimer);
                        }
                    }
                },
                error: function (err) {
                    alert("进度错误:"+err);
                    $("#progress_percent").text("Error");
                    window.clearInterval(oTimer);
                }
            });

        }
        function fSubmit() {
            $("#process").show();
            $("#cancel").show();
            $("#info").show();
            $("#success_info").hide();
            //文件名
            fileName = $("#fileToUpload").val().split('/').pop().split('\\').pop();
            //进度和百分比
            $("#progress_percent").text("0%");
            $("#progress_bar").width("0%");
            $("#progress_all").show();

            oTimer = setInterval("getProgress()", 1000);
            ajaxFileUpload();
            //document.getElementById("upload_form").submit();
            window.document.getElementById("fileToUpload").disabled = true;
        }
        var showset = null;
        function setTime() {
            $("#msg").html("");
            $.ajax({
                type: 'GET',
                url: ctx + "/fileupload/settime",
              //  url: "http://localhost:6060/userFile/settime",
                data: {time: $("#settime").val()},
                dataType: 'json',
                success: function (msg) {
                    if (msg.status == "1") {
                        $("#msg").html(msg.message);
                        if (showset == null) {
                            showset = setTimeout("updateStockPrices()", 5000);
                        }
                        // $.messager.alert('提示',msg.message, 'info');
                    }

                }
            });
        }
        function updateStockPrices() {
            $("#msg").html("");
            clearTimeout(showset);
            showset = null;
        }
    </script>
</head>
<body>
<div>
    <input type="text" name="settime" id="settime" value="15">
    <a href="###" onclick="setTime()" class="update">设置速度</a>&nbsp;<span id="msg" style="color:red"></span>
</div>
<%--<div class="rtop">
    <a href="###" onclick="showCont()" class="update" id="upload_button">上传文件测试</a>
</div>--%>
<br>
<div class="yxbox1">


    <div class="pd15">
        <form name="uploadForm" id="upload_form" action="#" method="post" enctype="multipart/form-data">
            <p class="mb20">
                <a href="javascript:void[0]" class="a-upload">
                    <input type="file" name="file" id="fileToUpload" onchange="fSubmit();">点击这里上传文件
                </a>
                <%--  <input type="file"  name="file" id="fileToUpload" title="请选择要上传的文件" onchange="fSubmit();">--%>
            </p>
            上传文件(超过1G文件上传同步较慢)
            <div class="br" style="display:block;" id="progress_all">
                <ul>
                    <li>
                        <div class="process clearfix" id="process">
						<span class="progress-box">
							<span class="progress-bar" style="width: 0%;" id="progress_bar"></span>
						</span>
                            <span id="progress_percent">0%</span>
                        </div>
                        <div class="info" id="info">已上传：<span id="has_upload">0</span>MB 速度：<span
                                id="upload_speed">0</span>KB/s
                        </div>
                        <div class="info" id="success_info" style="display: none;"></div>
                    </li>
                </ul>
            </div>
        </form>
    </div>
</div>
</body>


</html>

</html>
