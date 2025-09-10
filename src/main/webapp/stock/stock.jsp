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
        <div class="stock-page-container">
            <div class="stock-page-title">
                <h2>재고 관리</h2>
            </div>

            <div class="add-stock-section">
                <select name="category" class="cno form-select" onchange="getCnoProduct()">
                    <option disabled selected>카테고리 선택</option>
                    <option value="20001">돼지</option>
                    <option value="20002">소</option>
                    <option value="20003">양</option>
                    <option value="20004">오리</option>
                </select>
                <select name="product" class="product form-select">
                    <option disabled selected>고기 선택</option>
                    </select>
                <div class="price-input-group">
                    <input type="text" placeholder="가격(100g) 입력" class="sprice form-control" onkeyup="numCheck()">
                    <span class="numCheck validation-message"></span>
                </div>
                <button type="button" class="btn btn-primary" onclick="addStock()">재고 등록</button>
            </div>

            <table class="table table-hover">
                <thead class="table-light">
                    <tr>
                        <th>재고번호</th>
                        <th>카테고리명</th>
                        <th>제품명</th>
                        <th>등록가격(100g)</th>
                        <th>등록일</th>
                        <th>비고</th>
                    </tr>
                </thead>
                <tbody class="stockTbody">
                    </tbody>
            </table>
        </div>
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
                    <span>수정할 재고의 가격을 입력해주세요.</span>
                    
                    <div class="info-group">
                        <span class="label">재고번호 :</span>
                        <span class="snoBox value"></span>
                    </div>
                    <div class="info-group">
                        <span class="label">제품명 :</span>
                        <span class="pnameBox value"></span>
                    </div>
                    
                    <div class="input-group-update">
                        <label for="spriceBoxInput">수정할 가격 (100g)</label>
                        <input type="text" id="spriceBoxInput" class="spriceBox" placeholder="가격을 입력하세요." onkeyup="updateCheck()">
                        <span class="updateSprice" style="color: red;"></span>
                    </div>

                    <span id="pnoBox" style="display: none;"></span>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"> 닫기 </button>
                    <button type="button" id="updateBtn" class="btn btn-primary" onclick="updateStock()"> 수정완료 </button>
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