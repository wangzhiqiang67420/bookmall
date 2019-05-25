<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<script>
    function toBookIndex(){
//        var newPage = window.open();
        // window.open('about:blank');
        window.parent.location.href = 'http://localhost:8088/admin/user/logout';
    }
    function tim(){
        var now=new Date;
        document.getElementById("time").innerHTML=now.getFullYear()+"年"+now.getMonth()+"月"+now.getDay()+"日 星期"+now.getDay()+" "+now.getHours()+":"+now.getMinutes()+":"+now.getSeconds();
        var mm=setTimeout("tim()","1000");
    }
    window.onload=tim;
</script>
<body>
<div >
    <span style="color:#00BFFF;margin-left: 20px;">欢迎您，${sessionScope.loginUser.username}，登录后端管理系统！</span>
    <span style = "color:dodgerblue;margin-left: 20px;">当前时间:</span><span style = "color:dodgerblue;margin-left: 5px;"id='time'></span>

    <a onclick="toBookIndex()"  style="color:red;margin-left:450px;" target="_top">注销</a>

</div>
</body>
</html>
