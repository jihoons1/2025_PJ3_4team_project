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
    <link rel="stylesheet" href="/css/member/login.css">

</head>

<body>
    <!-- header JSP 불러오기 : webapp 이하 경로부터 작성 -->
    <jsp:include page="/header.jsp"></jsp:include>

    <div id="container">
        <div class="loginBox">
            아이디<input type="text" class="mid" placeholder="아이디를 입력하세요."> </br>
            비밀번호<input type="password" class="mpwd" placeholder="비밀번호를 입력하세요."> </br>
            <a href="/member/find.jsp"> 아이디 | 비밀번호 찾기 </a></br>
            <button type="button" onclick="loginbtn()"> 로그인 </button>
        </div>
    </div>

    <!-- footer JSP 불러오기 : webapp 이하 경로부터 작성 -->
    <jsp:include page="/footer.jsp"></jsp:include>
    <!-- JS 불러오기 : static 이하 경로부터 작성 -->
    <script src="/js/member/login.js"></script>

</body>

</html>