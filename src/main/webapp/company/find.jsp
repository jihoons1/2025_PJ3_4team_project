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

    <div id="container">
        <h3> 정육점 개별 조회 페이지 </h3>
        <div>            
            <table>
                <thead>
                    <tr>
                        <th>사진</th> <th>가게명</th> <th>가게주소</th> <th>평점</th> 
                    </tr>
                </thead>
                <tbody id="findTbody">                    
                </tbody>
            </table>            
        </div>
        <div>
            <div><h4>리뷰 목록</h4></div> <div><button type="button" onclick="addReviewBox()">리뷰작성</button></div>
            <form class="reviewAddBox">                
            </form>
            <div class="rImgBox">
            </div>
            <div>
                <table>
                    <thead>
                        <tr>
                            <th> 작성자 </th> <th> 리뷰내용 </th> <th> 작성일 </th> <th> 평점 </th> <th> 비고 </th> 
                        </tr>
                    </thead>
                    <tbody class="reviewTbody">
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <!-- footer JSP 불러오기 : webapp 이하 경로부터 작성 -->
    <jsp:include page="/footer.jsp"></jsp:include>
    <!-- JS 불러오기 : static 이하 경로부터 작성 -->
    <script src="/js/company/find.js"></script>
    
</body>

</html>