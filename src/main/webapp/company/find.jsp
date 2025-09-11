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
        <div class="find-page-container">
            <div class="find-page-title">
                <h2>정육점 상세 정보</h2>
            </div>

            <div class="content-grid">
                <div class="container_left">
                    <div class="info-section">
                        <div class="infodiv">
                            <div class="cimg">

                            </div>
                            <div class="cinfo">
                                
                            </div>
                        </div>
                        <div class="actions-group">
                            <div class="chatBtn">
                            </div>
                            <div id="checkUser">
                                <!-- <div class="membership-status">
                                    <span>멤버십</span>
                                    <span class="endDate"></span>
                                </div>
                                <button type="button" class="btn btn-sm btn-success"
                                    onclick="addPlan()">신청하기
                                </button> -->
                            </div>
                        </div>
                    </div>

                    <div class="info-section">
                        <h4>재고 목록</h4>
                        <table class="table table-sm">
                            <thead class="table-light">
                                <tr>
                                    <th>카테고리명</th>
                                    <th>제품명</th>
                                    <th>가격(100g)</th>
                                </tr>
                            </thead>
                            <tbody class="stockTbody">
                            </tbody>
                        </table>
                    </div>

                    <div class="info-section">
                        <div class="section-header">
                            <h4>방문자 리뷰</h4>
                            <div class="review-actions">                                
                            </div>
                        </div>
                        <table class="table table-hover">
                            <thead class="table-light">
                                <tr>
                                    <th>번호</th>
                                    <th>작성자</th>
                                    <th>리뷰내용</th>
                                    <th>작성일</th>
                                    <th>평점</th>
                                    <th>비고</th>
                                </tr>
                            </thead>
                            <tbody class="reviewTbody">
                            </tbody>
                        </table>
                        <div class="pagination-container">
                            <ul class="pageBtnBox pagination pagination-sm">
                            </ul>
                        </div>
                    </div>
                </div>

                <div class="container_right">
                    <div id="map"></div>
                </div>
            </div>
        </div>
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
                        <textarea name="rcontent" class="form-control" placeholder="솔직한 리뷰를 남겨주세요!"></textarea>
                        <div class="review-options">
                            <select name="rrank" class="form-select">
                                <option value="0" selected disabled>평점</option>
                                <option value="5">5</option>
                                <option value="4">4</option>
                                <option value="3">3</option>
                                <option value="2">2</option>
                                <option value="1">1</option>
                            </select>
                            <input type="file" multiple name="uploads" class="form-control" />
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"> 닫기 </button>
                    <button type="button" style="background-color: #143889;"  class="btn btn-primary" onclick="addReview()">리뷰 등록</button>
                </div>
            </div>
        </div>
    </div>

    <!-- QR Code 출력 staticBackdrop10 -->
    <div class="modal fade" id="staticBackdrop10" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
        aria-labelledby="staticBackdropLabel" aria-hidden="true">
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

    <!-- 리뷰 수정 staticBackdrop2 -->
    <div class="modal fade" id="staticBackdrop2" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
        aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="staticBackdropLabel">리뷰 수정</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form class="reviewupdateBox">
                        <input type="text" name="rno" class="oldrno" disabled />
                        <textarea name="rcontent" class="form-control oldrcontent" placeholder="솔직한 리뷰를 남겨주세요!"></textarea>
                        <div class="review-options">
                            <select name="rrank" class="form-select oldrrank">
                                <option value="0" selected disabled>평점</option>
                                <option value="5">5</option>
                                <option value="4">4</option>
                                <option value="3">3</option>
                                <option value="2">2</option>
                                <option value="1">1</option>
                            </select>
                            <input type="file" multiple name="uploads" class="form-control" />
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"> 닫기 </button>
                    <button type="button" style="background-color: #143889;"  class="btn btn-primary" onclick="saveReview()">리뷰 수정</button>
                </div>
            </div>
        </div>
    </div>

    <!-- 멤버쉽 등록 staticBackdrop5 -->
    <div class="modal fade" id="staticBackdrop5" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="staticBackdropLabel">멤버십 등록</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form class="planAdd">
                        <div class="banner-instruction">
                            <h5>배너 이미지 등록</h5>
                            <p>
                                광고 배너로 사용할 이미지를 등록해주세요.<br>
                                권장 크기는 1280x70 픽셀입니다.<br>
                                <span class="text-danger">배너를 등록하지 않으면 광고가 노출되지 않습니다.</span>
                            </p>
                        </div>
                        
                        <input type="file" multiple name="upload" class="form-control" />
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"> 닫기 </button>
                    <button type="button" style="background-color: #143889;"  class="btn btn-primary" onclick="addPlan()">멤버십 등록</button>
                </div>
            </div>
        </div>
    </div>

    <!-- footer JSP 불러오기 : webapp 이하 경로부터 작성 -->
    <jsp:include page="/footer.jsp"></jsp:include>
    <!-- 네이버지도 불러오기 -->
    <script type="text/javascript" src="https://oapi.map.naver.com/openapi/v3/maps.js?ncpKeyId=7dgspubsn7"></script>
    <!-- JS 불러오기 : static 이하 경로부터 작성 -->
    <script src="/js/company/find.js"></script>


</body>

</html>