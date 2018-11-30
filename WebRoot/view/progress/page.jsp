<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <%@ include file="/common/include.jsp" %>
    <script type="text/javascript">
        $(function () { 
        });
        
        function start(){
        	$.post("/progress/start", { orgId: "test", account: "2pm" ,"type":1},function(data){
        		alert(data);
        	});       	
        }
        
		function query(){
			$.post("/progress/query", { orgId: "test", account: "2pm" ,"type":1},function(data){
				console.log(data);
        	});
        }
        
    </script>
</head>
<body>
<input type="button" value="启动任务" onclick="start()">
<input type="button" value="查询进度" onclick="query()">
</body>
</html>
