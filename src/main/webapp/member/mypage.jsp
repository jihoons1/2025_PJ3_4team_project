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
        <div>                           <!-- 기본정보 조회 -->
            <div class="mInfoTop">      <!-- 프로필사진, 이름, 아이디 -->
                <div class="mimg">      <!-- 프로필사진-->

                </div>
                <div>
                    <div>아이디 : <span class="mid"></span></div>
                    <div>아름 : <span class="mname"></span></div>
                </div>
            </div>
            <div class="mInfoBot">      <!-- 전화번호, 이메일, 주소, 가입일 -->
                <div>
                    <div>전화번호 : <span class="mphone"></span></div>
                    <div>이메일 : <span class="memail"></span></div>
                </div>
                <div>
                    <div>주소 : <span class="maddress"></span></div>
                    <div>가입일 : <span class="mdate"></span></div>
                </div>
            </div>
        </div>
        <div>   <!-- 수정 버튼 -->
            <!-- Button trigger modal -->
            <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#staticBackdrop5">
                회원정보 수정
            </button>
            <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#staticBackdrop20">
                비밀번호 수정</button>
        </div>
        <!-- 회원정보 수정 staticBackdrop5 -->
        <div class="modal fade" id="staticBackdrop5" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="staticBackdropLabel">회원정보 수정</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id="mig">
                        전화번호 : <input type="text" class="mphone2" name="mphone" placeholder="전화번호를 입력해주세요." />   <br>
                        주소 : <input type="text" class="maddress2" name="maddress" placeholder="주소를 입력해주세요." />      <br>
                        프로필 : <input type="file" name="upload"/>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"> 닫기 </button>
                    <button type="button" class="btn btn-primary"  onclick="update()">회원정보 수정</button>
                </div>
                </div>
            </div>
        </div>

        <!-- 비밀번호 수정 -->
        <div class="modal fade" id="staticBackdrop20" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <h1 class="modal-title fs-5" id="staticBackdropLabel">비밀번호 수정</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
              </div>
              <div class="modal-body">
                <!-- 기능-->
                 기존 비밀번호 : <input type="text" class="mpwd"/> <br>
                 변경하실 비밀번호 : <input type="text" class="mpwd2"/> <br>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
                <button type="button" class="btn btn-primary" onclick="passup()">비밀번호 수정</button>
              </div>
            </div>
          </div>
        </div>

        <div>   <!-- 회원별 리뷰조회 -->
            <table>
                <thead>
                    <tr>
                        <th>리뷰번호</th>
                        <th>정육점명</th>
                        <th>리뷰내용</th>
                        <th>평점</th>
                        <th>등록일</th>
                        <th>비고</th>           <!-- 리뷰 수정/삭제 버튼 -->
                    </tr>
                </thead>
                <tbody class="reviewTbody">

                </tbody>
            </table>
        </div>
        <!-- 리뷰 수정 staticBackdrop2 -->
            <div class="modal fade" id="staticBackdrop6" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
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
                        <button type="button" class="btn btn-primary"  onclick="addUpdate()">리뷰 수정</button>
                    </div>
                    </div>
                </div>
            </div>
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
                    <input type="text" class="nprice" placeholder="가격을 입력하세요." onkeyup="noticeAddCheck()"> <br>
                    <span class="noticeAddCheck" style="color: red;"></span>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"> 닫기 </button>
                    <button type="button" class="btn btn-primary"  onclick="addNotice()">알림등록</button>
                </div>
                </div>
            </div>
        </div>
        <div>   <!-- 회원별 알림조회 -->
            <table>
                <thead>
                    <tr>
                        <th>알림번호</th>
                        <th>카테고리명</th>
                        <th>제품명</th>
                        <th>알림설정가격</th>
                        <th>알림등록일</th>
                        <th>전송여부</th>
                        <th>비고</th>           <!-- 알림 수정/삭제 버튼 -->
                    </tr>
                </thead>
                <tbody class="noticeTbody">

                </tbody>
            </table>
        </div>
        <!-- 알림수정 staticBackdrop4 -->
        <div class="modal fade" id="staticBackdrop4" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="staticBackdropLabel">알림수정</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <span>알림수정할 제품의 가격을 입력해주세요.</span> <br>
                    <span class="oldPname"></span>
                    <input type="text" class="NewNprice" placeholder="가격을 입력하세요." onkeyup="noticeupdateCheck()"> <br>
                    <span class="noticeUpdateCheck" style="color: red;"></span>
                </div>
                <div class="modal-footer" id="updateInput">

                </div>
                </div>
            </div>
        </div>
        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#staticBackdrop2">
            회원탈퇴
        </button>
        <!-- 회원탈퇴 1차 staticBackdrop2 -->
        <div class="modal fade" id="staticBackdrop2" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
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
                    <button type="button" class="btn btn-primary"  data-bs-target="#staticBackdrop3" data-bs-toggle="modal">회원탈퇴</button>
                </div>
                </div>
            </div>
        </div>
        <!-- 회원탈퇴 2차 staticBackdrop3 -->
        <div class="modal fade" id="staticBackdrop3" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="staticBackdropLabel">회원탈퇴</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="mb-3">
                        <label for="message-text" class="col-form-label">탈퇴에 동의하신다면, 현재 비밀번호를 입력해주세요.</label>
                        <input type="password" class="mpwdInput">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"> 닫기 </button>
                    <button type="button" class="btn btn-primary" onclick="resignMember()" >회원탈퇴</button>
                </div>
                </div>
            </div>
        </div>
    </div>

    <!-- footer JSP 불러오기 : webapp 이하 경로부터 작성 -->
    <jsp:include page="/footer.jsp"></jsp:include>
    <!-- JS 불러오기 : static 이하 경로부터 작성 -->
    <script src="/js/member/mypage.js"></script>

</body>

</html>