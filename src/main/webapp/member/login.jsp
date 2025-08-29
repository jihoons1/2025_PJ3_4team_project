<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>Page Title</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>

    <!-- CSS 불러오기 : static 이하 경로부터 작성 -->
    <link rel="stylesheet" href="/css/common.css">

</head>

<body>
    <!-- header JSP 불러오기 : webapp 이하 경로부터 작성 -->
    <jsp:include page="/header.jsp"></jsp:include>

    <div id="container">
        아이디 : <input type="text" class="mid" placeholder="asd123"> </br>
        비밀번호 : <input type="password" class="mpwd" placeholder="asd213"> </br>
        <button type="button" onclick="loginbtn()"> 로그인 </button> </br>
        <a href="/member/findid.jsp"> 아이디찾기 </a>
        <a href="/member/findpwd.jsp"> 비밀번호찾기 </a>
    </div>

    <!-- footer JSP 불러오기 : webapp 이하 경로부터 작성 -->
    <jsp:include page="/footer.jsp"></jsp:include>
    <!-- JS 불러오기 : static 이하 경로부터 작성 -->
    <script src="/js/member/login.js"></script>

</body>

</html>