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
    <header class="d-flex justify-content-between align-items-center py-3 mb-4 border-bottom">

        <div class="header-left">
            <a href="/index.jsp" class="d-inline-flex link-body-emphasis text-decoration-none">
                <img src="/img/logo.png" alt="사이트 로고">
            </a>
        </div>

        <div class="header-center">
            <ul class="nav menu">
            </ul>
            <div class="search-container">
                <input type="text" class="searchBox" placeholder="검색어를 입력하세요." onkeyup="if( event.keyCode==13 ){search();}">
                <button type="button" onclick="search()">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
                        <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"/>
                    </svg>
                </button>
            </div>
        </div>

        <div class="header-right text-end">
            <div class="log">
            </div>
        </div>
    </header>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"></script>
<script src="/js/header.js"></script>
</body>
</html>