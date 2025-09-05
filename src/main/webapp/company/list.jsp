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
        <h3> 정육점 전체 조회 페이지 </h3>
        <div>
            <select class="order" onchange="searchParams()">
                <option value="order">정렬방법</option>
                <option value="rank">평점순</option>
            </select>
            <table class="table table-striped table-hover">
                <thead>
                    <tr>
                        <th>사진</th> <th>가게명</th> <th>가게주소</th> <th>평점</th> 
                    </tr>
                </thead>
                <tbody id="listTbody">                    
                </tbody>
            </table>
            <div style="width: 380px;">
                <ul class="pageBtnBox" style="display: flex; justify-content: space-between;">
                    <li><a href="list.jsp?page=1"> 1 </a></li>
                    <li><a href="list.jsp?page=2"> 2 </a></li>
                    <li><a href="list.jsp?page=3"> 3 </a></li>
                    <li><a href="list.jsp?page=4"> 4 </a></li>
                    <li><a href="list.jsp?page=5"> 5 </a></li>
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