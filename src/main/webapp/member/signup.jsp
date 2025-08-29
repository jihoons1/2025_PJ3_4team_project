<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>Page Title</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <link rel='stylesheet' type='text/css' media='screen' href='main.css'>

</head>
<body>
    <jsp:include page="/header.jsp"></jsp:include>
    <div>
        <h3> 회원가입 </h3>
    <div>
        회원명 : <input type="text" class="mname" placeholder="홍길동" />
        아이디 : <input type="text" class="mid" placeholder="test123" />
        비밀번호 : <input type="password" class="mpwd" placeholder="test123" />
        비밀번호 확인 : <input type="password" class="mpwd2" placeholder="입력하신 패스워드 같아야함" />
        휴대번호 : <input type="text" class="mphone" placeholder="010-1234-5678" />
        주소 : <input type="text" class="maddress" placeholder="홍길동" />
        <label for="email">이메일 : </label> <!-- select 박스 이름 -->
        <select id="emailselect"> <!-- naver , gmail , daum  3개만 있음 -->
            <option value="XXX@naver.com">XXX@.naver.com</option>
            <option value="XXX@gmail.com">XXX@.gmail.com</option>
            <option value="XXX@daum.net">XXX@daum.net</option>
        </select>
        <button class="newmember" onclick="signupbtn()">회원가입 </button>
    </div>
    </div>

    <script src='/member/signup.js'></script>
</body>
</html>