//=============================================== 일반 로직 ================================================\\
// [*] 입력값 유효성 검사
const numCheckList = [ false ];
// 1. 숫자만 유효성 검사 - 알림등록
const noticeAddCheck = (  ) => {
    const nprice = document.querySelector('.nprice').value;
    const regExp = /^[0-9]+$/;
    if ( !regExp.test( nprice ) ){
        document.querySelector('.noticeAddCheck').innerHTML = '숫자만 입력해주세요.'
    } else {
        document.querySelector('.noticeAddCheck').innerHTML = ''
        numCheckList[0] = true;
    } // if end
} // func end
// 2. 숫자만 유효성 검사 - 알림수정
const noticeupdateCheck = (  ) => {
    const NewNprice = document.querySelector('.NewNprice').value;
    const regExp = /^[0-9]+$/;
    if ( !regExp.test( NewNprice ) ){
        document.querySelector('.noticeUpdateCheck').innerHTML = '숫자만 입력해주세요.'
    } else {
        document.querySelector('.noticeUpdateCheck').innerHTML = ''
        numCheckList[0] = true;
    } // if end
} // func end

// 3. 회원정보 상세조회
const getMember = async ( ) => {
    // 1. fetch
    const option = { method : "GET" };
    const response = await fetch( "/member/get", option );
    const data = await response.json();
    // * ming URL 만들기
    let mimgURL = `/upload/member/${data.mimg}`
    // 이미지를 등록하지 않은 회원이면, placehold로 변경
    if ( data.mimg == null ){
        mimgURL = 'https://placehold.co/100x100';
    } // if end

    // 2. where + print
    document.querySelector('.mimg').innerHTML = `<img src="${mimgURL}">`;
    document.querySelector('.mid').innerHTML = `${data.mid}`;
    document.querySelector('.mname').innerHTML = `${data.mname}`;
    document.querySelector('.mphone').innerHTML = `${data.mphone}`;
    document.querySelector('.memail').innerHTML = `${data.memail}`;
    document.querySelector('.maddress').innerHTML = `${data.maddress}`;
    document.querySelector('.mdate').innerHTML = `${data.mdate}`;
} // func end
getMember();

// 4. 회원탈퇴 기능
const resignMember = async ( ) => {
    try {
        // 1. Input value
        const mpwd = document.querySelector('.mpwdInput').value;
        // 2. obj
        const obj = { mpwd };
        // 3. fetch
        const option = {
            method : "POST",
            headers : { "Content-Type" : "application/json" },
            body : JSON.stringify( obj )
        } // option end
        const response = await fetch( "/member/resign", option );
        const data = await response.json();
        // 4. result 
        if ( data == true ){
            alert('회원탈퇴 성공!');
            location.href = `/index.jsp`;
        } else {
            alert('회원탈퇴 실패! 다시 입력해주세요.');
        } // if end
    } catch ( error ) {
        console.log( error );
    } // try-catch end
} // func end

// 다른 함수에서도 사용하기 위해, 전역변수로 선언
let noticeData;
// 5. 회원별 알림조회
const getNotice = async ( ) => {
    // 1. fetch
    const option = { method : "GET" };
    const response = await fetch( "/notice/get", option );
    const data = await response.json();
    // 회원별 알림조회 결과를 전역변수에 대입하기
    noticeData = await data;
    // 2. where
    const noticeTbody = document.querySelector('.noticeTbody');
    // 3. what
    let html = ``;
    data.forEach( (notice) => {
        let ncheck = notice.ncheck;
        if ( ncheck == 0 ){
            ncheck = '미전송'
        } else {
            ncheck = ncheck + '원에 전송완료'
        } // if end
        html += `<tr>
                    <td>${notice.nno}</td>
                    <td>${notice.cname}</td>
                    <td>${notice.pname}</td>
                    <td>${notice.nprice}</td>
                    <td>${notice.ndate}</td>
                    <td>${ncheck}</td>
                    <td>
                        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#staticBackdrop4" onclick="updatePrint(${notice.nno})"> 수정 </button>
                        <button type="button" onclick="deleteNotice(${notice.nno})"> 삭제 </button>
                    </td>
                 </tr>`
    });
    // 4. print
    noticeTbody.innerHTML = html;
} // func end
getNotice();

// 6. 수정 기본 출력
const updatePrint = async ( nno ) => {
    // 1. 전역변수로 선언된 noticeData를 통해 기본 출력
    for ( let i = 0; i < noticeData.length; i++ ){
        if ( noticeData[i].nno == nno ){
            document.querySelector('.oldPname').innerHTML = `${noticeData[i].pname}`;
            document.querySelector('.NewNprice').value = `${noticeData[i].nprice}`;
            document.querySelector('#updateInput').innerHTML = `<button type="button" class="btn btn-secondary" data-bs-dismiss="modal"> 닫기 </button>
                                                                <button type="button" class="btn btn-primary"  onclick="updateNotice(${nno})">알림수정</button>`
        } // if end
    } // for end
} // func end

// 7. 알림 수정
const updateNotice = async ( nno ) => {
    // 숫자 유효성 체크
    if ( numCheckList.includes(false) ){
        alert('올바른 정보를 입력해주세요.');
        return;
    } // if end
    // 1. Input value
    const nprice = document.querySelector('.NewNprice').value;
    // 2. obj
    const obj = { nno, nprice };
    // 3. fetch
    const option = {
        method : "PUT",
        headers : { "Content-Type" : "application/json" },
        body : JSON.stringify( obj )
    } // option end
    const response = await fetch( "/notice/update", option );
    const data = await response.json();
    // 4. result
    if ( data == true ){
        alert('알림수정 성공!');
        location.reload();
    } else {
        alert('알림수정 실패!');
    } // if end
} // func end

// 8. 알림 삭제
const deleteNotice = async ( nno ) => {
    // 1. fetch
    const option = { method : "DELETE" };
    const response = await fetch( `/notice/delete?nno=${nno}`, option );
    const data = await response.json();
    // 2. result
    if ( data == true ){
        alert('알림삭제 성공!');
        location.reload();
    } else {
        alert('알림삭제 실패!')
    } // if end
} // func end

// 9. 제품 전체조회
const getProduct = async ( ) => {
    // 1. fetch
    const option = { method : "GET" };
    const response = await fetch( "/product/get", option );
    const data = await response.json();
    // 2. where
    const pBox = document.querySelector('.pBox');
    // 3. what
    let html = `<option selected disabled>제품을 선택하세요.</option>`;
    data.data.forEach( (product) => {
        html += `<option value="${product.pno}">
                    ${product.cname} - ${product.pname}
                 </option>`
    })
    // 4. print
    pBox.innerHTML = html;
} // func end
getProduct();

// 10. 알림 등록기능
const addNotice = async ( ) => {
    // 숫자 유효성 체크
    if ( numCheckList.includes(false) ){
        alert('올바른 정보를 입력해주세요.');
        return;
    } // if end
    // 1. Input value
    const pno = document.querySelector('.pBox').value;
    const nprice = document.querySelector('.nprice').value;
    // 2. obj
    const obj = { pno, nprice };
    // 3. fetch
    const option = {
        method : "POST",
        headers : { "Content-Type" : "application/json" },
        body : JSON.stringify( obj )
    } // option end
    const response = await fetch( "/notice/add", option );
    const data = await response.json();
    // 4. result
    if ( data > 0 ){
        alert('재고등록 성공!');
        location.reload();
    } else if ( data == -1 ){
        alert('포인트 잔액이 부족합니다.');
    } else {
        alert('재고등록 실패!\n다시 입력해주세요.');
    } // if end
} // func end

// 11. 회원별 리뷰조회
const getMnoReview = async ( ) => {
    // 1. fetch
    const option = { method : "GET" };
    const response = await fetch( "/review/getMno", option );
    const data = await response.json();
    // 2. where
    const reviewTbody = document.querySelector('.reviewTbody');
    // 3. what
    let html = "";
    data.forEach( (review) => {        
        let reimg = "";
        if(review.images == null || review.images == ""){
            reimg = 'https://placehold.co/50x50';
            html += `<div class="rImgBox" style="display: flex;">
                                <div><img src=${reimg}/></div>
                        </div>`
        }// if end
        html += `<div class="rImgBox" style="display: flex;">`
        review.images.forEach((img) => {
            reimg = '/upload/review/'+encodeURIComponent(img);
            html += `<div><img src=${reimg}/></div>`
        })// for end
        html += `</div>`
        html += `<tr>
                    <td>${review.rno}</td>
                    <td>${review.cname}</td>
                    <td>${review.rcontent}</td>
                    <td>${review.rrank}</td>
                    <td>${review.rdate}</td>
                    <td>
                        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#staticBackdrop6" onclick="getRnoReview(${review.rno})">
                                    수정
                                </button>
                        <button type="button" onclick="deleteReview(${review.rno})"> 삭제 </button>
                    </td>
                 </tr>`
    })
    // 4. print
    reviewTbody.innerHTML = html;
} // func end
getMnoReview();

// 12. 리뷰번호 리뷰 내용 조회
const getRnoReview = async ( rno ) => {
    try {
        // 1. fetch
        const option = { method : "GET" };
        const response = await fetch( `/review/getRno?rno=${rno}`, option );
        const data = await response.json();
        // 2. print
        document.querySelector('.oldrno').value = data.rno;
        document.querySelector('.oldrcontent').innerHTML = data.rcontent;
        document.querySelector('.oldrrank').value = data.rrank;
    } catch ( error ){
        console.log( error );
    } // try-catch end
} // func end

// 13. 리뷰 수정 기능
const addUpdate = async() => {
    const reviewupdateBoxs = document.querySelector('.reviewupdateBox');
    const rno = document.querySelector('.oldrno').value;
    const formData = new FormData(reviewupdateBoxs);
    formData.append("rno",rno);
    const option = { method : "PUT" , body : formData }
    try{
        const response = await fetch(`/review/update`,option);
        const data = await response.json();
        if(data == true){
            alert('수정 성공!');
            location.href="/member/mypage.jsp";
        }else{
            alert('수정 실패!');
        }// if end
    }catch(e){ console.log(e); }
}// func end

// 회원정보 불러오기
const modalevent = async() => {
try{
        const option = { method : "GET"}
        const getMember = await fetch(`/member/get` , option );
        const dataInput = await getMember.json();
        console.log(dataInput);

        document.querySelector('.mphone2').value = dataInput.mphone;
        if(dataInput.maddress){
            let maddressselect = dataInput.maddress.split(",");
            document.querySelector('#sample6_address').value = maddressselect[0] ?? '';
            document.querySelector('#sample6_detailAddress').value = maddressselect[1] ?? '';
        }
        console.log(dataInput.maddress);
    }catch(error){ console.log(error); }
}

const modelent = document.getElementById('staticBackdrop5');
modelent.addEventListener('shown.bs.modal' , modalevent); // shown.bs.modal 부트스트렙 모달 열람시 modalevent 정보 불러오기

// 14. 회원정보 수정 [프로필 이미지 , 주소 , 휴대번호 ]
const update = async() => {
    const mig = document.querySelector('#mig');
    const mphone2 = document.querySelector('.mphone2').value;
    const sample6_address = document.querySelector('#sample6_address').value;
    const sample6_detailAddress = document.querySelector('#sample6_detailAddress').value;

    let maddress = '';
    maddress =  sample6_address + "," + sample6_detailAddress;


    if( mphone2 === '' || sample6_address === '' || sample6_detailAddress === '' ){
        alert('칸을 비어 두지 마세요.');
        return;
    } // if end

    const mimg = new FormData(mig);
    mimg.append("maddress", maddress);


    try{
        const op = { method : "PUT" , body : mimg }

        const response = await fetch(`/member/updateMember` , op);
        const data = await response.json();
        

        if(data == 0){
            alert(' 수정실패');
        }else{
            alert('수정 성공');
            location.href="/member/mypage.jsp";
        }

    }catch(error){console.log(error+"회원정보 에러 "); }

}

// 15. 비밀번호 수정 [기존 , 임시]  
const passup = async() => {
    const mpwd = document.querySelector('.mpwd').value;
    const mpwd2 = document.querySelector('.mpwd2').value;
    
    const obj = {oldmpwd : mpwd , newmpwd : mpwd2 }

    let html = '';
    try{
        const option = { 
            method : "PUT" ,
            headers : {"Content-Type" : "application/json" },
            body : JSON.stringify(obj)
        };
        const response = await fetch('/member/update/Pwd' , option);
        const data = await response.json();
    

        

        if(data==true){
            alert('비밀번호 수정완료');
            location.href="/index.jsp";
        }else{
            alert('비밀번호 수정실패')
        }
    }catch(error){console.log(error) ; }
    
}

// 16. 리뷰 삭제 
const deleteReview = async(rno) => {
    let result = confirm('삭제 하시겠습니까?');
    if(result == false){ return; }    
    const option = { method : "DELETE" }
    try{
        const response = await fetch(`/review/delete?rno=${rno}`,option);
        const data = await response.json();
        if(data == true){
            alert('리뷰 삭제성공');
            location.href="/member/mypage.jsp";
        }else{
            alert('리뷰 삭제실패');
        }// if end
    }catch(e){ console.log(e); }
}// func end

// 17. 전화번호 하이폰
const mphoneCC = async() => {
    const mphone = document.querySelector('.mphone2');
    const mphone2CC = document.querySelector('.mphone2CC');

    // hiphone 숫자 추출
    let hiphone = mphone.value.replace(/[^0-9]/g, "");

    // 자동 하이폰 생성
    if(hiphone.length <=3){ // 010- <
    mphone.value = hiphone;
    }else if( hiphone.length < 7) {
    mphone.value = hiphone.replace(/(\d{3})(\d{1,4})/, "$1-$2");
    }else {
    mphone.value = hiphone.replace(/(\d{3})(\d{4})(\d{1,4})/, "$1-$2-$3");
    }

    const hiphonecode = /^01[0-9]-\d{3,4}-\d{4}$/; // 휴대폰 시작 01?-343 or 3424- 마지막 4자리 고정
    if(!hiphonecode.test(mphone.value)){
    mphone2CC.innerHTML = "전화번호 형식이 올바르지 않습니다..";
    numCheckList[1] = false;
    return;
    }
try{
        const response = await fetch(`/member/check?type=mphone&data=${mphone.value}`);
        const data = await response.json();
        
        if(data == true){
            mphone2CC.innerHTML = "사용자가 사용중인 번호입니다. (본인 포함)";
            numCheckList[1] = false;
        }else{
            mphone2CC.innerHTML = "사용가능한 번호입니다.";
            numCheckList[1] = true;
            }
   }catch(error) { }
}

// 18. mno별 채팅목록 가져오기
const getRoomList = async ( ) => {
    printPublic();
    try {
        // 1. fetch
        const fetch1 = await fetch( "/chatting/getRoomList" );
        const data = await fetch1.json();
        const getMno = await fetch( "/member/getMno" );
        const mno = await getMno.json();
        // 2. where
        const roomList = document.querySelector('.roomList');
        // 3. html
        let html = ``;
        for ( let i = 0; i < data.length; i++ ){
            let room = data[i];
            // mno와 data.to가 다르다면
            let me = 0;
            let other = 0;
            if ( room.to == mno ){
                me = room.to;
                other = room.from;
            } else {
                me = room.from;
                other = room.to;
            } // if end
            // 4. fetch
            const fetch2 = await fetch( `/member/getMname?mno=${me}` );
            const mename = await fetch2.text();
            const fetch3 = await fetch( `/member/getMname?mno=${other}` );
            const othername = await fetch3.text();

            // 5. what
            html += `<div class="rooms">
                            <a href="/chatting/chatting.jsp?mno=${me}&cno=${other}&room=${room.roomname}">
                            ${mename}님과 ${othername}의 채팅방 <br>
                            최근메시지 : ${room.message}
                            </a>
                        </div>`;
        } // for end
        roomList.innerHTML += html;
    } catch ( error ) {
        console.log( error );
    } // try-catch end
} // func end

// 19. 전체채팅방 출력하기
const printPublic = async ( ) => {
    try {
        // 1. fetch
        const response = await fetch( "/member/getMno" );
        const data = await response.json();
        // 2. where
        const roomList = document.querySelector('.roomList');
        // 3. html
        let html = `<div class="rooms">
                        <a href="/chatting/chatting.jsp?mno=${data}&room=0">
                            전체채팅방
                        </a>
                    </div>`;
        // 4. print
        roomList.innerHTML = html;
    } catch ( error ) {
        console.log( error );
    } // try-catch end
} // func end

//=============================================== 우편주소 API ================================================\\
function sample6_execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if(data.userSelectedType === 'R'){
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraAddr !== ''){
                    extraAddr = ' (' + extraAddr + ')';
                }
                // 조합된 참고항목을 해당 필드에 넣는다.
                
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('sample6_postcode').value = data.zonecode;
            document.getElementById("sample6_address").value = addr;

            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("sample6_detailAddress").focus();

        }
    }).open();
}

// 회원정보 수정 유효성 주소
const maddresscheck = async() => {
    const sample6_postcode = document.querySelector('#sample6_postcode').value;
    const sample6_detailAddress = document.querySelector('#sample6_detailAddress').value;
    const maddressCheck = document.querySelector('.maddressCheck');


    if (sample6_postcode === '' && sample6_detailAddress === '') {
        maddressCheck.innerHTML = "주소와 상세주소를 모두 입력해주세요.";
        numCheckList[2] = false;
    } else if (sample6_postcode === '') {
        maddressCheck.innerHTML = "주소를 입력해주세요.";
        numCheckList[2] = false;
    } else if (sample6_detailAddress === '') {
        maddressCheck.innerHTML = "상세주소를 입력해주세요.";
        numCheckList[2] = false;
    } else {
        maddressCheck.innerHTML = '';
        numCheckList[2] = true;
    }
}
//=============================================== 결제 API ================================================\\
// 1. 포인트 결제 기능
const payment = async ( ) => {
    // 포트원 기본 정보 제공
    let IMP = window.IMP;
    IMP.init("imp28011161");

    // 1. Select value
    const point = document.querySelector('.pointValue').value;
    const pointDot = point.toLocaleString();    // 천단위 콤마찍기

    // 2. JAVA에서 생성한 UUID 가져오기
    const response = await fetch ( "/point/getUUID" );
    let paymentID = await response.text();
    paymentID = `payment-${paymentID}`;



    IMP.request_pay(
    {   // 파라미터 값 설정 
        pg: "kakaopay.TC0ONETIME",
        pay_method: "card",                     // 결제방식
        merchant_uid: paymentID,                // 고객 거래번호
        name: `${pointDot} point`,              // 상품명
        amount: point,                          // 결제금액
        buyer_email: `${MemberData.memail}`,    // 고객 이메일
        buyer_name: `${MemberData.mname}`,      // 고객 이름
        buyer_tel: `${MemberData.mphone}`,      // 고객 전화번호
        buyer_addr: `${MemberData.maddress}`,   // 고객 주소
    },
    rsp = ( rsp ) => {
        if ( rsp.success ) {
            // axios로 HTTP 요청
            axios({
                url: "/point/payment",          // 매핑할 URL 주소
                method: "post",                 // 매핑 방식
                headers: { "Content-Type": "application/json" },
                data: {                         // JAVA에게 전달할 객체 -> PointDto의 구성요소
                plpoint : point,
                plcomment : `${pointDot} point 결제`
                }
            }).then( (data) => {
                // 결제에 성공했다면
                if ( data.data == true ){
                    // 결제금액이 결제에 성공했다는 알림
                    if ( confirm(`${pointDot} point 결제에 성공하였습니다.`) ){
                        // 확인을 눌러야 새로고침됨.
                        location.reload();
                    } // if end
                } // if end
            })
            } else {
                // 실패했다면, 실패 알림 및 에러 내용 표시
                alert(`결제에 실패하였습니다. 에러 내용: ${rsp.error_msg}`);
            }
        } // func end
    ); // 결제 프롬프트 end
} // func end