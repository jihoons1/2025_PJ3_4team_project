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

</head>

<body>
    <!-- header JSP 불러오기 : webapp 이하 경로부터 작성 -->
    <jsp:include page="/header.jsp"></jsp:include>

    <!-- Modal -->
    <div class="modal fade" id="staticBackdrop1" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="staticBackdropLabel">회원탈퇴</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <span>정말로 탈퇴하시겠습니까? <br> 동의하신다면, [탈퇴 진행]을 클릭해주세요.</span>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"> 닫기 </button>
                <button type="button" class="btn btn-primary"  data-bs-target="#staticBackdrop2" data-bs-toggle="modal">회원탈퇴</button>
            </div>
            </div>
        </div>
    </div>

    <!-- Modal -->
    <div class="modal fade" id="staticBackdrop2" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="staticBackdropLabel">회원탈퇴</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="mb-3">
                    <label for="message-text" class="col-form-label">탈퇴에 동의하신다면, 현재 비밀번호를 입력해주세요.</label>
                    <input type="password" class="mpwdInput">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"> 닫기 </button>
                <button type="button" class="btn btn-primary" onclick="resignMember()" >회원탈퇴</button>
            </div>
            </div>
        </div>
    </div>

    <div id="container">
        <div>                           <!-- 기본정보 조회 -->
            <div class="mInfoTop">      <!-- 프로필사진, 이름, 아이디 -->
                <div class="mimg">      <!-- 프로필사진-->

                </div>
                <div>
                    <div>아이디 : <span class="mid"></span></div>
                    <div>아름 : <span class="mname"></span></div>
                </div>
            </div>
            <div class="mInfoBot">      <!-- 전화번호, 이메일, 주소, 가입일 -->
                <div>
                    <div>전화번호 : <span class="mphone"></span></div>
                    <div>이메일 : <span class="memail"></span></div>
                </div>
                <div>
                    <div>주소 : <span class="maddress"></span></div>
                    <div>가입일 : <span class="mdate"></span></div>
                </div>
            </div>
        </div>
        <div>   <!-- 수정 버튼 -->
            <button type="button" onclick="">회원정보 수정</button>
            <button type="button" onclick="">비밀번호 수정</button>
        </div>
        <div>   <!-- 회원별 리뷰조회 -->
            <table>
                <thead>
                    <tr>
                        <th>리뷰번호</th>
                        <th>정육점명</th>
                        <th>리뷰내용</th>
                        <th>평점</th>
                        <th>등록일</th>
                        <th>비고</th>           <!-- 리뷰 수정/삭제 버튼 -->
                    </tr>
                </thead>
                <tbody class="reviewTbody">

                </tbody>
            </table>
        </div>
        <div>   <!-- 회원별 알림조회 -->
            <table>
                <thead>
                    <tr>
                        <th>알림번호</th>
                        <th>제품명</th>
                        <th>알림설정가격</th>
                        <th>알림등록일</th>
                        <th>전송여부</th>
                        <th>비고</th>           <!-- 알림 수정/삭제 버튼 -->
                    </tr>
                </thead>
                <tbody class="noticeTbody">

                </tbody>
            </table>
        </div>
        <!-- Button trigger modal -->
        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#staticBackdrop1">
            회원탈퇴
        </button>
    </div>

    <!-- footer JSP 불러오기 : webapp 이하 경로부터 작성 -->
    <jsp:include page="/footer.jsp"></jsp:include>
    <!-- JS 불러오기 : static 이하 경로부터 작성 -->
    <script src="/js/member/mypage.js"></script>

</body>

</html>