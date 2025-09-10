<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>Page Title</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
    <link rel="stylesheet" href="/css/common.css">
    <link rel="stylesheet" href="/css/header.css">
</head>

<body>
    <div id="header">
        <header class="d-flex justify-content-between align-items-center py-3 border-bottom">
   
            <div class="header-left">
                <a href="/index.jsp" class="d-inline-flex link-body-emphasis text-decoration-none">
                    <img src="/img/logo.png" class="header_logo" alt="사이트 로고">
                </a>
            </div>

            <div class="header-center">
                <div class="menu-container">
                    <ul class="nav menu">
                        </ul>
                </div>
                <div class="search-container">
                    <input type="text" class="searchBox" placeholder="부위명을 입력하세요." onkeyup="if( event.keyCode==13 ){search();}">
                    <button type="button" onclick="search()">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
                            <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z" />
                        </svg>
                    </button>
                </div>
            </div>

            <div class="header-right text-end">
                <div class="log">
                    <div class="log-info">
                        <a href="/member/mypage.jsp" class="username">${data.mname}</a>
                        <a href="#" class="points" data-bs-toggle="modal" data-bs-target="#staticBackdrop100">(${totalPoint} Point)</a>
                    </div>
                    <div class="log-actions">
                        <a href="/company/find.jsp?cno=${data.cno}">내 정육점</a>
                        <a href="#" onclick="logout()">로그아웃</a>
                    </div>
                </div>
            </div>
        </header>
    </div>

    <div class="toast-container p-3 bottom-0 end-0" style="position: fixed; z-index: 1100; width: 20%;">
        <!-- 해당 회원의 푸시 알림으로 구성될 예정 -->
    </div>


    <!-- axios JS 불러오기 -->
    <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
    <!-- 포트원 JS 불러오기 -->
    <script src="https://cdn.iamport.kr/v1/iamport.js"></script>
    <!-- 부트스트랩 JS 불러오기 -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js" integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous"></script>
    <script src="/js/header.js"></script>

</body>

</html>