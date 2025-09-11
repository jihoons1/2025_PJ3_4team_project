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
    <link rel="stylesheet" href="/css/member/mypage.css">

</head>

<body>
    <!-- header JSP 불러오기 : webapp 이하 경로부터 작성 -->
    <jsp:include page="/header.jsp"></jsp:include>

    <div id="container">
        <div class="mypage-container">
            <div class="mypage-title">
                <h2>마이페이지</h2>
                <p>회원님의 정보를 관리하고 활동 내역을 확인하세요.</p>
            </div>

            <div class="mypage-section">
                <h4>기본 정보</h4>
                <div class="mInfoTop">
                    <from class="fmimg" ><div class="mimg"></div> </from>
                    <div class="mInfo-text">
                        <div><strong>아이디</strong> <span class="mid"></span></div>
                        <div><strong>이름</strong> <span class="mname"></span></div>
                    </div>
                </div>
                <div class="mInfoBot">
                    <div>
                        <div><strong>전화번호</strong> <span class="mphone"></span></div>
                        <div><strong>이메일</strong> <span class="memail"></span></div>
                    </div>
                    <div>
                        <div><strong>주소</strong> <span class="maddress"></span></div>
                        <div><strong>가입일</strong> <span class="mdate"></span></div>
                    </div>
                </div>
                <div class="action-buttons">
                    <button type="button" class="btn btn-outline-secondary btn-sm" data-bs-toggle="modal" data-bs-target="#staticBackdrop100">
                        포인트 결제
                    </button>
                    <button type="button" class="btn btn-outline-secondary btn-sm" data-bs-toggle="offcanvas" data-bs-target="#offcanvasWithBothOptions" aria-controls="offcanvasWithBothOptions" onclick="getRoomList()" >
                        채팅방 목록 열기
                    </button>
                    <button type="button" class="btn btn-outline-secondary btn-sm" data-bs-toggle="modal" data-bs-target="#staticBackdrop5">
                        회원정보 수정
                    </button>
                    <button type="button" class="btn btn-outline-secondary btn-sm" data-bs-toggle="modal" data-bs-target="#staticBackdrop20">
                        비밀번호 수정
                    </button>
                </div>
            </div>

            <div class="mypage-section">
                <div class="section-header">
                    <h4>나의 리뷰</h4>
                </div>
                <table class="table table-hover">
                    <thead class="table-light">
                        <tr>
                            <th>리뷰번호</th>
                            <th>정육점명</th>
                            <th>리뷰내용</th>
                            <th>평점</th>
                            <th>등록일</th>
                            <th>비고</th>
                        </tr>
                    </thead>
                    <tbody class="reviewTbody">
                    </tbody>
                </table>
            </div>

            <div class="mypage-section">
                <div class="section-header">
                    <h4>가격 알림</h4>
                    <button type="button" class="btn btn-primary btn-sm" data-bs-toggle="modal" data-bs-target="#staticBackdrop1">
                        새 알림 등록
                    </button>
                </div>
                <table class="table table-hover">
                    <thead class="table-light">
                        <tr>
                            <th>알림번호</th>
                            <th>카테고리명</th>
                            <th>제품명</th>
                            <th>알림설정가격</th>
                            <th>알림등록일</th>
                            <th>전송여부</th>
                            <th>비고</th>
                        </tr>
                    </thead>
                    <tbody class="noticeTbody">
                    </tbody>
                </table>
            </div>

            <div class="mypage-section danger-zone">
                <h4>회원 탈퇴</h4>
                <p>탈퇴 시 모든 정보가 영구적으로 삭제되며 복구할 수 없습니다.</p>
                <button type="button" class="btn btn-danger" data-bs-toggle="modal"
                    data-bs-target="#staticBackdrop2">
                    회원탈퇴 진행
                </button>
            </div>
        </div>
    </div>

    <!-- 채팅방목록 offcanvas -->
    <div class="offcanvas offcanvas-start" data-bs-scroll="true" tabindex="-1" id="offcanvasWithBothOptions" aria-labelledby="offcanvasWithBothOptionsLabel">
        <div class="offcanvas-header">
            <h5 class="offcanvas-title" id="offcanvasWithBothOptionsLabel">채팅창 목록</h5>
            <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
        </div>
        <div class="offcanvas-body roomList">
            <!-- 해당 회원의 채팅방 목록으로 구성 -->
        </div>
    </div>

    <!-- 포인트 결제 staticBackdrop100 -->
    <div class="modal fade" id="staticBackdrop100" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered"> <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="staticBackdropLabel">포인트 결제</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <select class="pointValue form-select">
                        <option selected disabled>포인트 선택</option>
                        <option value="1000">1,000 P</option>
                        <option value="5000">5,000 P</option>
                        <option value="10000">10,000 P</option>
                    </select>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"> 닫기 </button>
                    <button type="button" class="btn btn-primary" onclick="payment()">포인트 결제</button>
                </div>
            </div>
        </div>
    </div>

    <!-- 회원정보 수정 staticBackdrop5 -->
    <div class="modal fade" id="staticBackdrop5" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
        aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="staticBackdropLabel">회원정보 수정</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id="mig">
                        <div class="input-group">
                            <label for="mhpoen3">전화번호</label>
                            <input style="width: 100%;" onkeyup="mphoneCC()" type="text" id="mhpoen3" class="mphone2 form-control" name="mphone" placeholder="전화번호를 입력해주세요." />
                            <div class="mphone2CC validation-message"></div>
                        </div>
                        <div class="input-group">
                            <label>주소</label>
                            <div class="address-group">
                                <input type="text" id="sample6_postcode" class="form-control" placeholder="우편번호">
                                <input type="button" onclick="sample6_execDaumPostcode()" class="btn btn-secondary btn-sm" value="우편번호 찾기">
                            </div>
                            <!-- 우편번호 API -->
                        <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
                            <input style="width: 100%;" type="text" class="maddress form-control" id="sample6_address" placeholder="주소" />
                            <input style="width: 100%;" type="text" name="mdetailaddress" id="sample6_detailAddress" class="form-control" placeholder="상세주소">
                            <div class="maddressCheck validation-message"></div>
                        </div>
                        <div class="input-group">
                            <label>프로필</label>
                            <input style="width: 100%;" type="file" name="upload" class="form-control" />
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"> 닫기 </button>
                    <button type="button" class="btn btn-primary" onclick="update()">회원정보 수정</button>
                </div>
            </div>
        </div>
    </div>

    <!-- 비밀번호 수정 staticBackdrop20 -->
    <div class="modal fade" id="staticBackdrop20" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
        aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="staticBackdropLabel">비밀번호 수정</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="input-group">
                        <label>기존 비밀번호</label>
                        <input style="width: 100%;" type="password" class="mpwd form-control" id="mpwd"/>
                    </div>
                    <div class="input-group">
                        <label>변경하실 비밀번호</label>
                        <input style="width: 100%;" onkeyup="passad()" type="password" class="mpwd2 form-control" id="mpwd2"/>
                        <div class="asd"></div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
                    <button type="button" class="btn btn-primary" onclick="passup()">비밀번호 수정</button>
                </div>
            </div>
        </div>
    </div>
    <!-- 리뷰 수정 staticBackdrop6 -->
    <div class="modal fade" id="staticBackdrop6" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
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
                    <button type="button" class="btn btn-primary" onclick="addUpdate()">리뷰 수정</button>
                </div>
            </div>
        </div>
    </div>

    <!-- 알림등록 staticBackdrop1 -->
    <div class="modal fade" id="staticBackdrop1" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
        aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="staticBackdropLabel">알림등록</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <span>알림등록할 제품과 가격을 입력해주세요.</span>
                    <select class="pBox form-select">
                        
                    </select>
                    <input style="width: 100%;" type="text" class="nprice form-control" placeholder="가격을 입력하세요." onkeyup="noticeAddCheck()">
                    <span class="noticeAddCheck" style="color: red;"></span>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"> 닫기 </button>
                    <button type="button" class="btn btn-primary" onclick="addNotice()">알림등록</button>
                </div>
            </div>
        </div>
    </div>

    <!-- 알림수정 staticBackdrop4 -->
    <div class="modal fade" id="staticBackdrop4" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
        aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="staticBackdropLabel">알림수정</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <span>알림수정할 제품의 가격을 입력해주세요.</span>
                    <div class="updatePriceBox">
                        <span class="oldPname"></span>
                        <input type="text" class="NewNprice" placeholder="가격을 입력하세요." onkeyup="noticeupdateCheck()">
                    </div>
                    <span class="noticeUpdateCheck" style="color: red;"></span>
                </div>
                <div class="modal-footer" id="updateInput">

                </div>
            </div>
        </div>
    </div>

    <!-- 회원탈퇴 1차 staticBackdrop2 -->
    <div class="modal fade" id="staticBackdrop2" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
        aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="staticBackdropLabel">회원탈퇴</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <span>정말로 탈퇴하시겠습니까? <br> 동의하신다면, [탈퇴 진행]을 클릭해주세요.</span>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"> 닫기 </button>
                    <button type="button" class="btn btn-primary" data-bs-target="#staticBackdrop3"
                        data-bs-toggle="modal">탈퇴 진행하기</button>
                </div>
            </div>
        </div>
    </div>

    <!-- 회원탈퇴 2차 staticBackdrop3 -->
    <div class="modal fade" id="staticBackdrop3" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
        aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="staticBackdropLabel">회원탈퇴</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="mb-3">
                        <label for="message-text" class="col-form-label">탈퇴에 동의하신다면, 현재 비밀번호를 입력해주세요.</label>
                        비밀번호 : <input type="password" class="mpwdInput">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"> 닫기 </button>
                    <button type="button" class="btn btn-primary" onclick="resignMember()">회원탈퇴</button>
                </div>
            </div>
        </div>
    </div>

    <!-- footer JSP 불러오기 : webapp 이하 경로부터 작성 -->
    <jsp:include page="/footer.jsp"></jsp:include>
    <!-- 우편번호 API -->
    <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
    <!-- JS 불러오기 : static 이하 경로부터 작성 -->
    <script src="/js/member/mypage.js"></script>

</body>

</html>