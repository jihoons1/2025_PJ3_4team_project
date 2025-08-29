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
        <h3> 회원가입 </h3>
    <div id="container">
        <form id="mig">
        회원명 : <input type="text" name="mname" placeholder="홍길동" />
        아이디 : <input type="text" name="mid" placeholder="test123" />
        비밀번호 : <input type="password" name="mpwd" placeholder="test123" />
        비밀번호 확인 : <input type="password" name="mpwd2" placeholder="입력하신 패스워드 같아야함" />
        휴대번호 : <input type="text" name="mphone" placeholder="010-1234-5678" />
        주소 : <input type="text" name="maddress" placeholder="홍길동" />
        이메일 : <input type="text" class="emailname"/> 
        <select id="emailselect"> <!-- naver , gmail , daum  3개만 있음 -->
            <option value="@naver.com">@naver.com</option>
            <option value="@gmail.com">@gmail.com</option>
            <option value="@daum.net">@daum.net</option>
        </select>
        프로필 : <input type="file" name="upload"/>
        <button type="button" class="newmember" onclick="signupbtn()">회원가입 </button>
        </form>
    </div>
    </div>

    <jsp:include page="/footer.jsp"></jsp:include>
    <script src='/js/member/signup.js'></script>
</body>
</html>