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


    <div id="container">
        <div class="find-page-container">
            <div class="find-form">
                <h3 class="form-title">아이디 찾기</h3>
                <div class="input-group">
                    <label>회원명</label>
                    <input type="text" style="width: 100%;" class="mname form-control" placeholder="가입 시 등록한 이름을 입력하세요.">
                </div>
                <div class="input-group">
                    <label>휴대번호</label>
                    <input onkeyup="findphone()" style="width: 100%;" type="text" class="mphoneid form-control" placeholder="가입 시 등록한 휴대번호를 입력하세요.">
                    <div class="findPhone validation-message"></div>
                </div>
                <button class="btnid btn btn-primary w-100" type="button" onclick="findid()"> 아이디 찾기 </button>
            </div>
            
            <div class="find-form">
                <h3 class="form-title">비밀번호 찾기</h3>
                <div class="input-group">
                    <label>아이디</label>
                    <input type="text" style="width: 100%;" class="mid form-control" placeholder="아이디를 입력하세요.">
                </div>
                <div class="input-group">
                    <label>휴대번호</label>
                    <input onkeyup="findphone2()" style="width: 100%;" type="text" class="mphonepwd form-control" placeholder="가입 시 등록한 휴대번호를 입력하세요.">
                    <div class="findPhone2 validation-message"></div>
                </div>
                <button class="btnpwd btn btn-primary w-100" type="button" onclick="findpwd()"> 비밀번호 찾기 </button>
            </div>
        </div>
    </div>



    <script src="/js/member/find.js"></script>
</body>
</html>