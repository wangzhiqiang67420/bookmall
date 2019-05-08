<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <base href="<%=basePath%>">
    <title>登录</title>
    <link rel="stylesheet" type="text/css" href="css/login.css"/>
    <link rel="stylesheet" type="text/css" href="css/bs.css"/>
    <script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="js/jquery.validate.js"></script>
    <script type="text/javascript" src="js/messages_zh.js"></script>

    <script>
        $(function () {
            $("#loginForm").validate({
                rules: {
                    username: "required",
                    password: {
                        rangelength: [3, 15],
                        required: true
                    }
                },
                errorPlacement: function (error, element) {	//错误信息位置设置方法
                    error.appendTo(element.parent().next());//这里的element是录入数据的对象,parent父元素，next()下一个
                },
                success: function (label) {
                    label.addClass("error");
                },
                messages: {
                    username: "用户名不能为空",
                    password: {
                        rangelength: "密码长度在{0}~{1}之间",
                        required: "密码不能为空"
                    }
                }
            });
        })
    </script>
</head>
<body onload="toBookIndex()">
<!--

-->
</body>
<script>
//    toBookIndex();
    function toBookIndex(){
        window.location.href = 'http://localhost:8080/#/login';
        // window.open('about:blank');
//        newPage.location.href = 'http://localhost:8080/#/login';
    }
</script>
</html>
