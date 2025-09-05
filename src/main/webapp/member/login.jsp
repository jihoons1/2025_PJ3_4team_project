<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>로그인</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>


    <link rel="stylesheet" href="/css/common.css">
    <link rel="stylesheet" href="/css/member/login.css">

</head>

<body>
    <jsp:include page="/header.jsp"></jsp:include>

    <div id="container">
        <div class="login-form-container">
            <h3 class="login-title">로그인</h3>
            <div class="loginBox">
                <div class="input-group">
                    <label for="mid">아이디</label>
                    <input onkeyup="loginCheck()" type="text" id="mid" class="mid" placeholder="아이디를 입력하세요.">
                </div>
                <div class="input-group">
                    <label for="mpwd">비밀번호</label>
                    <input type="password" id="mpwd" class="mpwd" placeholder="비밀번호를 입력하세요."
                        onkeyup="if( event.keyCode == 13 ){ loginbtn() }; loginCheck()">
                </div>
                <div class="loginCheck"></div>

                <div class="login-options">
                    <a href="/member/find.jsp">아이디 | 비밀번호 찾기</a>
                </div>

                <button type="button" onclick="loginbtn()"> 로그인 </button>
            </div>
        </div>
    </div>

    <jsp:include page="/footer.jsp"></jsp:include>

    <script src="/js/member/login.js"></script>
</body>

</html>