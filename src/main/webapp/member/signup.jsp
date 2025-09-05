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
            <!-- 학원자리 http://192.168.40.189:8080/member/signup.jsp -->
            <!-- http://localhost:8080/member/login.jsp-->
    <jsp:include page="/header.jsp"></jsp:include>

    <div id="container">
        <div class="signup-form-container">
            <h3 class="signup-title">회원가입</h3>
            <form id="mig">
                <div class="input-group">
                    <label>회원명</label>
                    <input type="text" onkeyup="mnamecheck()" name="mname" class="mname2" placeholder="홍길동" />
                    <div class="mnameCheck"></div> <!--이름 유효성 [3자리~10자리] -->
                </div>
                <div class="input-group">
                    <label>아이디</label>
                    <input type="text" onkeyup="idcheck()" name="mid" class="mid2" placeholder="test123" />
                    <div class="idchecks"></div> <!--아이디 유효성 [6글자~10글자]-->
                </div>
                <div class="input-group">
                    <label>비밀번호</label>
                    <input type="password" onkeyup="pwdcheck()" name="mpwd" class="mpwd2" placeholder="test123" />
                    <div class="Pwd"></div> <!--비밀번호 유효성 [6글자~13글자] -->
                </div>
                <div class="input-group">
                    <label>비밀번호 확인</label>
                    <input type="password" onkeyup="pwdcheck2()" name="mpwd2" class="mpwd22" placeholder="입력하신 패스워드와 같아야 합니다" />
                    <div class="Pwdd"></div> <!--비밀번호 재확인 유효성  -->
                </div>
                <div class="input-group">
                    <label>휴대번호</label>
                    <input type="text" onkeyup="phonecheck()"  name="mphone" class="mphone2" placeholder="-없이 숫자만 입력" />
                    <div class="phonechecks"></div> <!-- 전화번호 01부터 가능 123-123-123 안됨  -->
                </div>
                <div class="input-group">
                    <label>주소</label>
                    <input type="text" id="sample6_postcode" placeholder="우편번호">
                    <input type="button" onclick="sample6_execDaumPostcode()" value="우편번호 찾기"><br>
                    <input type="text" onkeyup="maddresscheck()" name="maddress" id="sample6_address" placeholder="주소를 입력해주세요" />
                    <input type="text" name="mdetailaddress" id="sample6_detailAddress" placeholder="상세주소">
                    <input type="text" id="sample6_extraAddress" placeholder="참고항목">
                    <div class="maddressCheck"></div>
                </div>
                <!-- 우편번호 API -->
                <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>


                <div class="input-group">
                    <label>이메일</label>
                    <div class="email-input-group">
                        <input type="text" onkeyup="emailcheck()" class="emailname" />
                        <select id="emailselect" onchange="emailcheck()"> <!-- onchange 사용자가 결과를 변경 할경우 ex) 이메일 수동입력 하다 취소하고 select 쓸때 이런경우-->
                            <option disabled selected>이메일 선택</option>
                            <option value="@naver.com">@naver.com</option>
                            <option value="@gmail.com">@gmail.com</option>
                            <option value="@daum.net">@daum.net</option>
                        </select>
                        <div class="emailCheck"></div> <!--이메일 유효성 -->
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

