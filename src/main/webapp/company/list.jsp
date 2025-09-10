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
    <link rel="stylesheet" href="/css/company/list.css">

</head>

<body>
    <!-- header JSP 불러오기 : webapp 이하 경로부터 작성 -->
    <jsp:include page="/header.jsp"></jsp:include>

<div id="container">
        <div class="list-page-container">
            <div class="list-page-title">
                <h2>정육점 목록</h2>
            </div>

            <div class="order-controls">
                <select class="order form-select" onchange="searchParams()">
                    <option value="order" disabled>정렬방법</option>
                    <option value="rank">평점순</option>
                    <option value="views">조회수순</option>
                </select>
            </div>

            <table class="table table-hover">
                <thead class="table-light">
                    <tr>
                        <th>사진</th>
                        <th>가게명</th>
                        <th>가게주소</th>
                        <th>조회수</th>
                        <th>평점</th>
                    </tr>
                </thead>
                <tbody id="listTbody">
                    </tbody>
            </table>

            <div class="pagination-container">
                <ul class="pageBtnBox pagination">
                    </ul>
            </div>
        </div>
    </div>

    <!-- footer JSP 불러오기 : webapp 이하 경로부터 작성 -->
    <jsp:include page="/footer.jsp"></jsp:include>
    <!-- JS 불러오기 : static 이하 경로부터 작성 -->
    <script src="/js/company/list.js"></script>
</body>

</html>