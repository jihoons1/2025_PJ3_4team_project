let MemberData;

//=============================================== 일반 로직 ================================================\\
const myinfo = async() => {
    const log = document.querySelector('.log');
    const menu = document.querySelector('.menu');
    let logHtml = '';
    let menuHtml = '';
    try{
        //fetch 실행
        const op = { method : "GET" }
        const response = await fetch(`/member/get` , op)
        const data = await response.json();                    
        let totalPoint = data.totalPoint.toLocaleString();      
        MemberData = await data;

        // 정육점을 가진 회원이라면
        if ( data.cno > 0 ){
            menuHtml += `<li><a href="/product/product.jsp" class="nav-link px-2">제품 목록</a></li>
                         <li><a href="/company/list.jsp" class="nav-link px-2">정육점 목록</a></li>
                         <li><a href="/stock/stock.jsp?cno=${data.cno}" class="nav-link px-2">재고 관리</a></li>`
            logHtml +=` <button type="button" onclick="location.href='/member/mypage.jsp'" class="btn btn-outline-primary me-2">${data.mname}</button>
                        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#staticBackdrop100">${totalPoint} Point</button>
                        <button type="button" onclick="location.href='/company/find.jsp?cno=${data.cno}'" class="btn btn-outline-primary me-2">내 정육점정보</button>
                        <button type="button" onclick="logout()" class="btn btn-primary">로그아웃</button>`
        } else {
            // 정육점이 없는 회원이라면
            menuHtml += `<li><a href="/product/product.jsp" class="nav-link px-2">제품 목록</a></li>
                        <li><a href="/company/list.jsp" class="nav-link px-2">정육점 목록</a></li>`

            logHtml +=` <button type="button" onclick="location.href='/member/mypage.jsp'" class="btn btn-outline-primary me-2">${data.mname}</button>
                        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#staticBackdrop100">${totalPoint} Point</button>
                        <button type="button" onclick="logout()" class="btn btn-primary">로그아웃</button>`
        } // if end
    }catch{
        menuHtml += `<li><a href="/product/product.jsp" class="nav-link px-2">제품 목록</a></li>
                     <li><a href="/company/list.jsp" class="nav-link px-2">정육점 목록</a></li>`

        logHtml +=` <button type="button" onclick="location.href='/member/login.jsp'" class="btn btn-outline-primary me-2">로그인</button>
                    <button type="button" onclick="location.href='/member/signup.jsp'" class="btn btn-primary">회원가입</button>`
    } // try-catch end
    // 출력
    log.innerHTML = logHtml;
    menu.innerHTML = menuHtml;
} // func end
myinfo();

const logout= async() => {
    
    try{
        const op = { method : "GET" }
        const response = await fetch('/member/logout' , op)
        const data = await response.json();

        if( data > 0){
            alert('로그아웃 성공');
            location.href="/index.jsp";
        }else{
            alert('로그아웃 실패');
        }
    }catch(error){console.log(error) ; }
}

// 검색 함수
const search = async (  ) => {
    const keyword = document.querySelector('.searchBox').value;
    location.href=`/search/search.jsp?key=pname&keyword=${keyword}`;

} // func end

// 4. 푸시알림조회
const getAlarm = async ( ) => {
    console.log('getAlarm func exe');

    try{
        // 1. fetch
        const response = await fetch( "/alarm/get" );
        const data = await response.json();
        // 2. where
        const toastBox = document.querySelector('.toast-container');
        // 3. what
        let html = '';
        data.forEach( (alarm) => {
            html += `<div class="toast show" role="alert" aria-live="assertive" aria-atomic="true">
                        <div class="toast-header">
                            <strong class="me-auto">Push Alarm</strong>
                            <button type="button" onclick="updateAlarm(${alarm.ano})" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
                        </div>
                        <div class="toast-body">
                            ${alarm.amessage}
                        </div>
                     </div>`
        });
        // 4. print
        toastBox.innerHTML = html;
    } catch ( error ){
        console.log( error );
    } // try-catch end
} // func end
getAlarm();

// 5. 푸시알림수정
const updateAlarm = async ( ano ) => {
    console.log('updateAlarm func exe');
    try{
        // 1. fetch
        const option = { method : "PUT" };
        const response = await fetch( `/alarm/update?ano=${ano}`, option );
        const data = await response.json();
    } catch ( error ){
        console.log( error );
    } // try-catch end
} // func end

//=============================================== 결제 API ===========================================\\
// 포인트 결제 기능
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