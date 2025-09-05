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
    <link rel="stylesheet" href="/css/search/search.css">

</head>

<body>
    <!-- header JSP 불러오기 : webapp 이하 경로부터 작성 -->
    <jsp:include page="/header.jsp"></jsp:include>
    
    <div class="container">
        <h3>검색 페이지</h3>
        <!-- Button trigger modal -->
        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#staticBackdrop1">
            알림등록
        </button>
        <!-- 알림등록 staticBackdrop1 -->
        <div class="modal fade" id="staticBackdrop1" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="staticBackdropLabel">알림등록</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <span>알림등록할 제품과 가격을 입력해주세요.</span> <br>
                    <select class="pBox">
                        
                    </select>
                    <input type="text" class="nprice" placeholder="가격을 입력하세요.">
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"> 닫기 </button>
                    <button type="button" class="btn btn-primary"  onclick="addNotice()">알림등록</button>
                </div>
                </div>
            </div>
        </div>
        <div>
            <select class="order" onchange="searchParam()">
                <option value="order">정렬방법</option>
                <option value="rank">평점순</option>
                <option value="sprice">낮은가격순</option>
                <option value="distance">가까운순</option>
            </select>
            <table class="table table-striped table-hover">
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