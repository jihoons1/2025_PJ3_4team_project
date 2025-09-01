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
    
    <div class="container">
        <h3>검색 페이지</h3>
        <div>
            <select class="order" onchange="searchParam()">
                <option value="order">정렬방법</option>
                <option value="rank">평점순</option>
                <option value="sprice">낮은가격순</option>
                <option value="distance">가까운순</option>
            </select>
            <table>
                <thead>
                    <tr>
                        <th> 사진 </th> <th> 정육점명 </th> <th>정육점 주소</th> <th> 거리 </th>  
                        <th> 부위명 </th> <th> 가격 </th> <th> 평점 </th>                   
                    </tr>>
                </thead>
                <tbody id="searchTbody">                
                </tbody>
            </table>
            <div style="width: 380px;">
                <ul class="pageBtnBox" style="display: flex; justify-content: space-between;">                    
                </ul>
            </div>
        </div>
    </div>

    <!-- footer JSP 불러오기 : webapp 이하 경로부터 작성 -->
    <jsp:include page="/footer.jsp"></jsp:include>
    <!-- JS 불러오기 : static 이하 경로부터 작성 -->
    <script src="/js/search/search.js"></script>
</body>
</html>