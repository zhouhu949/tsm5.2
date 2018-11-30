<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>freemarker template test</title>
<@h.easyui />
</head>
<script type="text/javascript">
    if(top.location != self.location) {
        top.location = self.location;
    }
    function reloadcode(){
        var verify=document.getElementById("code");
        verify.setAttribute("src","/getCaptcha?t="+Math.random());
    }
</script>
<body>
<table style="border-image-repeat: round;">
    <tr><td>orgId</td><td>userAccount</td><td>userId</td></tr>
<#list dataList as item>
    <tr>
        <td>${item.orgId?if_exists}</td>
        <td>${item.userAccount?if_exists}</td>
        <td>${item.userId?if_exists}</td>
    </tr>
</#list>
</table>
</body>
</html>
