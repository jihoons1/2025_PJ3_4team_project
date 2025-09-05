console.log('header.js exe');


const myinfo = async() => {
    console.log('myinfo');
    const log = document.querySelector('.log');
    const menu = document.querySelector('.menu');
    let logHtml = '';
    let menuHtml = '';
    try{
        //fetch 실행
        const op = { method : "GET" }
        const response = await fetch(`/member/get` , op)
        const data = await response.json();                     console.log( data );
        let totalPoint = data.totalPoint.toLocaleString();      console.log( totalPoint );

        // 정육점을 가진 회원이라면
        if ( data.cno > 0 ){
            menuHtml += `<li><a href="/product/product.jsp" class="nav-link px-2">제품 목록</a></li>
                         <li><a href="/company/list.jsp" class="nav-link px-2">정육점 목록</a></li>
                         <li><a href="/stock/stock.jsp?cno=${data.cno}" class="nav-link px-2">재고 관리</a></li>`
            logHtml +=` <button type="button" onclick="location.href='/member/mypage.jsp'" class="btn btn-outline-primary me-2">${data.mname}</button>
                        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#staticBackdrop1">
                            ${totalPoint} Point
                        </button>
                        <button type="button" onclick="location.href='/company/find.jsp?cno=${data.cno}'" class="btn btn-outline-primary me-2">내 정육점정보</button>
                        <button type="button" onclick="logout()" class="btn btn-primary">로그아웃</button>`
        } else {
            // 정육점이 없는 회원이라면
            menuHtml += `<li><a href="/product/product.jsp" class="nav-link px-2">제품 목록</a></li>
                        <li><a href="/company/list.jsp" class="nav-link px-2">정육점 목록</a></li>`
            logHtml +=` <button type="button" onclick="location.href='/member/mypage.jsp'" class="btn btn-outline-primary me-2">${data.mname}</button>
                        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#staticBackdrop1">
                            ${totalPoint} Point
                        </button>
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
    console.log('로그아웃 확인');
    
    try{
        const op = { method : "GET" }
        const response = await fetch('/member/logout' , op)
        const data = await response.json();

        if(data==true){
            alert('로그아웃 성공');
            location.href="/index.jsp";
        }else{
            alert('로그아웃 실패');
        }
    }catch(error){console.log(error) ; }
}

// 검색 함수
const search = async (  ) => {
    console.log('search func exe');
    const keyword = document.querySelector('.searchBox').value;
    location.href=`/search/search.jsp?key=pname&keyword=${keyword}`;

} // func end

// 포인트 결제 기능
const payment = async ( ) => {
    console.log('payment func exe');

    const point = document.querySelector('.pointValue').value;
    const pointDot = point.toLocaleString();

    const paymentId = `payment-${crypto.randomUUID()}`;

    let IMP = window.IMP;
    IMP.init("imp28011161");

    IMP.request_pay(
    {
        // 파라미터 값 설정 
        pg: "kakaopay.TC0ONETIME",
        pay_method: "card",
        merchant_uid: paymentId, // 상점 고유 주문번호
        name: `${pointDot} point`,
        amount: point,
        buyer_email: "good@portone.io",
        buyer_name: "포트원 기술지원팀",
        buyer_tel: "010-1234-5678",
        buyer_addr: "서울특별시 강남구 삼성동",
        buyer_postcode: "123-456",
    },
    rsp = ( rsp ) => {
        if (rsp.success) {
            // axios로 HTTP 요청
            axios({
                url: "{서버의 결제 정보를 받는 endpoint}",
                method: "post",
                headers: { "Content-Type": "application/json" },
                data: {
                imp_uid: rsp.imp_uid,
                merchant_uid: rsp.merchant_uid
                }
            }).then( (data) => {
                console.log( data );
            })
            } else {
                alert(`결제에 실패하였습니다. 에러 내용: ${rsp.error_msg}`);
            }
        }
    );

    // const response = await PortOne.requestPayment({
    //     // Store ID 설정
    //     storeId: "store-0bfc00f1-b821-4bba-a568-ed66889fe1a4",
    //     // 채널 키 설정
    //     channelKey: "channel-key-f9ecba09-eb53-4bab-98f6-94de05c967b0",
    //     paymentId: paymentId,
    //     orderName: `${pointDot} point`,
    //     totalAmount: point,
    //     currency: "CURRENCY_KRW",
    //     payMethod: "CARD",
    // });
    // console.log( response );

    // // 오류 발생
    // if (response.code !== undefined) {
    //     return alert(response.message);
    // } // if end

} // func end