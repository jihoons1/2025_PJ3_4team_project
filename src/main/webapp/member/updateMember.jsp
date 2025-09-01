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
        <form id="mig">
    휴대번호 : <input type="text" class="mphone2" name="mphone" placeholder="010-1234-5678" />
    주소 : <input type="text" class="maddress2" name="maddress" placeholder="홍길동" />
    프로필 : <input type="file" name="upload"/>
    <button type="button" onclick="update()"> 회원정보 수정 </button>
    </form>
    </div>

    <script src='/js/member/updateMember.js'></script>

</body>
</html>