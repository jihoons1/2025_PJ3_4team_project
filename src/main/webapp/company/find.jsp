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
    <link rel="stylesheet" href="/css/company/find.css">

</head>

<body>
    <!-- header JSP 불러오기 : webapp 이하 경로부터 작성 -->
    <jsp:include page="/header.jsp"></jsp:include>

    <div id="container">
        <div class="container_left">
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
                <div>
                    <h4>리뷰 목록</h4>
                </div>
                <div style="display: flex;">
                    <div>
                        <!-- Button trigger modal -->
                        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#staticBackdrop1">
                            리뷰 작성
                        </button>
                    </div>
                    <!-- 리뷰 작성 staticBackdrop1 -->
                    <div class="modal fade" id="staticBackdrop1" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                            <div class="modal-header">
                                <h1 class="modal-title fs-5" id="staticBackdropLabel">리뷰 작성</h1>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <form class="reviewAddBox">
                                    <textarea name="rcontent"></textarea>
                                    <select name="rrank">
                                        <option value="0" selected disabled>평점</option>
                                        <option value="5">5</option>
                                        <option value="4">4</option>
                                        <option value="3">3</option>
                                        <option value="2">2</option>
                                        <option value="1">1</option>
                                    </select>
                                    <input type="file" multiple name="uploads"/>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"> 닫기 </button>
                                <button type="button" class="btn btn-primary"  onclick="addReview()">리뷰 등록</button>
                            </div>
                            </div>
                        </div>
                    </div>
                    <div>
                        <!-- Button trigger modal -->
                        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#staticBackdrop10" onclick="buildQR()">
                            길찾기 QR Code [거주지 기준]
                        </button>
                    </div>
                    <!-- QR Code 출력 staticBackdrop10 -->
                    <div class="modal fade" id="staticBackdrop10" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h1 class="modal-title fs-5" id="staticBackdropLabel">QR Code</h1>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <div class="qrBox">

                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"> 닫기 </button>                            
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div>
                    <table>
                        <thead>
                            <tr>
                                <th> 번호 </th><th> 작성자 </th> <th> 리뷰내용 </th> <th> 작성일 </th> <th> 평점 </th> <th> 비고 </th>
                            </tr>
                        </thead>
                        <tbody class="reviewTbody">
                        </tbody>
                    </table>
                </div>
                <div style="width: 380px;">
                    <ul class="pageBtnBox" style="display: flex; justify-content: space-between;">
                    </ul>
                </div>
                <!-- 리뷰 수정 staticBackdrop2 -->
                <div class="modal fade" id="staticBackdrop2" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="staticBackdropLabel">리뷰 수정</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <form class="reviewupdateBox">
                                <input type="text" name="rno" class="oldrno" disabled/>
                                <textarea name="rcontent" class="oldrcontent"></textarea>
                                <select name="rrank" class="oldrrank">
                                    <option value="0" selected disabled>평점</option>
                                    <option value="5">5</option>
                                    <option value="4">4</option>
                                    <option value="3">3</option>
                                    <option value="2">2</option>
                                    <option value="1">1</option>
                                </select>
                                <input type="file" multiple name="uploads"/>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"> 닫기 </button>
                            <button type="button" class="btn btn-primary"  onclick="saveReview()">리뷰 수정</button>
                        </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="container_right">
            <div id="map" style="width:100%;height:400px;"></div>
        </div>
    </div>

    <!-- footer JSP 불러오기 : webapp 이하 경로부터 작성 -->
    <jsp:include page="/footer.jsp"></jsp:include>
    <!-- 네이버지도 불러오기 -->
    <script type="text/javascript" src="https://oapi.map.naver.com/openapi/v3/maps.js?ncpKeyId=7dgspubsn7&callback=CALLBACK_FUNCTION&submodules=geocoder"></script>
    <!-- JS 불러오기 : static 이하 경로부터 작성 -->
    <script src="/js/company/find.js"></script>

    
</body>

</html>