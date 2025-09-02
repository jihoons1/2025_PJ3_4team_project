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
    <link rel="stylesheet" href="/css/stock/stock.css">

</head>

<body>
    <!-- header JSP 불러오기 : webapp 이하 경로부터 작성 -->
    <jsp:include page="/header.jsp"></jsp:include>

    <div id="container">
        <div>
            <select name="category" class="cno" onchange="getCnoProduct()">
                <option disabled selected>카테고리 선택</option>
                <option value="20001">돼지</option>
                <option value="20002">소</option>
                <option value="20003">양</option>
                <option value="20004">오리</option>
            </select>
            <select name="product" class="product">
                <option disabled selected>고기 선택</option>
                <!-- 카테고리에 따른 고기 option 추가 -->
                
            </select>
            <input type="text" placeholder="가격 입력" class="sprice">
            <button type="button" onclick="addStock()"> 재고 등록 </button>
        </div>
        <table>         <!-- 재고목록 테이블 -->
            <thead>
                <tr>
                    <th>재고번호</th>
                    <th>제품명</th>
                    <th>등록가격</th>
                    <th>등록일</th>
                    <th>비고</th>
                </tr>
            </thead>
            <tbody class="stockTbody">

            </tbody>
        </table>
    </div>
    <!-- 재고수정 staticBackdrop1 -->
    <div class="modal fade" id="staticBackdrop1" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="staticBackdropLabel">재고수정</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <span>수정할 재고의 가격을 입력해주세요.</span>   <br>
                재고번호 : <span class="snoBox"></span>         <br>
                제품명 :   <span class="pnameBox"></span>       <br>
                수정할 가격 : <input type="text" class="spriceBox" placeholder="가격을 입력하세요.">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"> 닫기 </button>
            </div>
            </div>
        </div>
    </div>


    <!-- footer JSP 불러오기 : webapp 이하 경로부터 작성 -->
    <jsp:include page="/footer.jsp"></jsp:include>
    <!-- JS 불러오기 : static 이하 경로부터 작성 -->
    <script src="/js/stock/stock.js"></script>

</body>

</html>