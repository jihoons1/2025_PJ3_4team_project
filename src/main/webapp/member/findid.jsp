<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>Page Title</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>
</head>
<body>
    
    <jsp:include page="/header.jsp"></jsp:include>


    <div>
        회원명 : <input type="text" class="mname" placeholder="홍길동"/>
        휴대번호 : <input type="text" class="mphone" placeholder="010-1234-5678"/>
        <button type="button" onclick="findid()">아이디 찾기 </button>
    </div>



    <script src="/js/findid/js"></script>
</body>
</html>