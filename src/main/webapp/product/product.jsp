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
    <link rel="stylesheet" href="/css/product/product.css">

</head>
<body>
    <!-- header JSP 불러오기 : webapp 이하 경로부터 작성 -->
    <jsp:include page="/header.jsp"></jsp:include>

    <div id="container">
        <h3> 제품 전체 조회 페이지 </h3>
        <div>            
            <table>
                <thead>
                    <tr>
                        <th>사진</th> <th>카테고리</th> <th>부위명</th> 
                    </tr>
                </thead>
                <tbody id="productTbody">                    
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
    <script src="/js/product/product.js"></script>
</body>

</html>