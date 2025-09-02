<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>회원가입</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">

    <link rel="stylesheet" href="/css/common.css">
    <link rel="stylesheet" href="/css/member/signup.css">
</head>

<body>
    <jsp:include page="/header.jsp"></jsp:include>

    <div id="container">
        <div class="signup-form-container">
            <h3 class="signup-title">회원가입</h3>
            <form id="mig">
                <div class="input-group">
                    <label>회원명</label>
                    <input type="text" name="mname" class="miname2" placeholder="홍길동" />
                </div>
                <div class="input-group">
                    <label>아이디</label>
                    <input type="text" name="mid" class="mid2" placeholder="test123" />
                </div>
                <div class="input-group">
                    <label>비밀번호</label>
                    <input type="password" name="mpwd" class="mpwd2" placeholder="test123" />
                </div>
                <div class="input-group">
                    <label>비밀번호 확인</label>
                    <input type="password" name="mpwd2" class="mpwd22" placeholder="입력하신 패스워드와 같아야 합니다" />
                </div>
                <div class="input-group">
                    <label>휴대번호</label>
                    <input type="text" name="mphone" class="mphone2" placeholder="010-1234-5678" />
                </div>
                <div class="input-group">
                    <label>주소</label>
                    <input type="text" name="maddress" class="maddress2" placeholder="주소를 입력해주세요" />
                </div>
                <div class="input-group">
                    <label>이메일</label>
                    <div class="email-input-group">
                        <input type="text" class="emailname" />
                        <select id="emailselect">
                            <option disabled selected>@이메일 선택</option>
                            <option value="@naver.com">@naver.com</option>
                            <option value="@gmail.com">@gmail.com</option>
                            <option value="@daum.net">@daum.net</option>
                        </select>
                    </div>
                </div>
                <div class="input-group">
                    <label>프로필</label>
                    <input type="file" name="upload" class="form-control w-100" />
                </div>

                <button type="button" class="newmember" onclick="signupbtn()">회원가입</button>
            </form>
        </div>
    </div>

    <jsp:include page="/footer.jsp"></jsp:include>

    <script src='/js/member/signup.js'></script>
</body>

</html>