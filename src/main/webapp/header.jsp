<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>Page Title</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>

    <!-- Bootstrap 불러오기 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
    <!-- CSS 불러오기 : static 이하 경로부터 작성 -->
    <link rel="stylesheet" href="/css/common.css">
    <link rel="stylesheet" href="/css/header.css">

</head>

<body>

    <div id="header">
        <header class="d-flex flex-wrap align-items-center justify-content-center justify-content-md-between py-3 mb-4 border-bottom">
            <div class="col-md-3 mb-2 mb-md-0">
                <a href="/index.jsp" class="d-inline-flex link-body-emphasis text-decoration-none">
                    <img src="/img/logo.png" alt="">
                </a>
            </div>
            <ul class="nav col-12 col-md-auto mb-2 justify-content-center mb-md-0">
                <!-- 샘플 리스트 -->
                <div class="menu">

                </div>
            </ul>
            <div>
                <input type="text" class="searchBox" placeholder="검색어를 입력하세요." onkeyup="if( event.keyCode==13 ){search();}">
                <button type="button" onclick="search(event)"> 검색 </button>
            </div>
            <div class="col-md-3 text-end">
                <div class="log"> <!-- 로그인/로그아웃 쪽 js-->
                    
                </div>
            </div>
        </header>
    </div>


    <!-- JS 불러오기 : static 이하 경로부터 작성 -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI"
        crossorigin="anonymous"></script>
    <script src="/js/header.js"></script>

</body>

</html>