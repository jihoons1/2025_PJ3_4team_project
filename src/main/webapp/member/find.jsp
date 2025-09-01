<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>Page Title</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <link rel="stylesheet" href="/css/member/find.css">
</head>
<body>
    
    <jsp:include page="/header.jsp"></jsp:include>


    <div>
        <div >
        회원명 :<input type="text" class="mname" > <br>
        휴대번호 :<input type="text" class="mphoneid" > <br>
        <button type="button" onclick="findid()"> 아이디 찾기 </button> 
        </div>
        <hr> 
        <div >
        아이디 :<input type="text" class="mid" > <br>
        휴대번호 :<input type="text" class="mphonepwd" > <br>
        <button type="button" onclick="findpwd()"> 비밀번호 찾기 </button>
        </div>
    </div>



    <script src="/js/member/find.js"></script>
</body>
</html>