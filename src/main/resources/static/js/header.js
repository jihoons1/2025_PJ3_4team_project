let MemberData;

//=============================================== 일반 로직 ================================================\\
// 1. 세션정보에 따라 다른 출력 구현
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
            logHtml += `<div class="log-info">
                            <a href="/member/mypage.jsp" class="username">${data.mname} (${totalPoint} Point)</a>
                        </div>
                        <div class="log-actions">
                            <a href="/company/find.jsp?cno=${data.cno}">내 정육점</a>
                            <a href="#" onclick="logout()">로그아웃</a>
                        </div>`
        } else {
            // 정육점이 없는 회원이라면
            menuHtml += `<li><a href="/product/product.jsp" class="nav-link px-2">제품 목록</a></li>
                        <li><a href="/company/list.jsp" class="nav-link px-2">정육점 목록</a></li>`

            logHtml +=`<div class="log-info">
                            <a href="/member/mypage.jsp" class="username">${data.mname} (${totalPoint} Point)</a>
                        </div>
                        <div class="log-actions">
                            <a href="#" onclick="logout()">로그아웃</a>
                        </div>`
        } // if end
    }catch{
        menuHtml += `<li><a href="/product/product.jsp" class="nav-link px-2">제품 목록</a></li>
                     <li><a href="/company/list.jsp" class="nav-link px-2">정육점 목록</a></li>`

        logHtml += `<div class="log-actions">
                        <a href="/member/login.jsp">로그인</a>
                        <a href="/member/signup.jsp">회원가입</a>
                    </div>`
    } // try-catch end
    // 출력
    log.innerHTML = logHtml;
    menu.innerHTML = menuHtml;
} // func end
myinfo();

// 2. 로그아웃 버튼 구현
const logout= async() => {
    try{
        // 1. fetch
        const op = { method : "GET" }
        const response = await fetch('/member/logout' , op)
        const data = await response.json();
        // 2. result
        if( data > 0){
            alert('로그아웃 성공');
            location.href="/index.jsp";
        }else{
            alert('로그아웃 실패');
        }
    }catch(error){console.log(error) ; }
}

// 3. 검색 함수
const search = async (  ) => {
    const keyword = document.querySelector('.searchBox').value;
    location.href=`/search/search.jsp?key=pname&keyword=${keyword}`;
} // func end

// 4. 푸시알림조회
const getAlarm = async ( ) => {
    try{
        // 1. fetch
        const response = await fetch( "/alarm/get" );
        const data = await response.json();
        // 2. where
        const toastBox = document.querySelector('.toast-container');
        // 3. what
        let html = '';
        data.forEach( (alarm) => {
            if ( alarm.atype == "chat" ){
                let amessage = alarm.amessage;
                let room = alarm.etc;
                let members = room.split("_");
                // 반복문을 통해, 상대방의 회원번호 찾기
                let cno = 0;
                for ( let i = 0; i < members.length; i++ ){
                    if ( members[i] != alarm.mno ){
                        cno = members[i];
                    } // if end
                } // for end

                // 4. atype이 chat이라면, a태그로 chat링크 걸기
                html += `<div class="toast" role="alert" aria-live="assertive" aria-atomic="true">
                            <div class="toast-header">
                                <strong class="me-auto">Push Alarm</strong>
                                <button type="button" onclick="updateAlarm(${alarm.ano})" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
                            </div>
                            <div class="toast-body">
                                <a href="/chatting/chatting.jsp?mno=${alarm.mno}&cno=${cno}&room=${room}">${cno}와의 ${amessage}</a>
                            </div>
                        </div>`
            } else if ( alarm.atype == "stock" ){
                let amessage = alarm.amessage;
                let cno = alarm.etc;
                // 5. atype이 stock이라면, a태그로 정육점상세페이지 링크 걸기
                html += `<div class="toast" role="alert" aria-live="assertive" aria-atomic="true">
                            <div class="toast-header">
                                <strong class="me-auto">Push Alarm</strong>
                                <button type="button" onclick="updateAlarm(${alarm.ano})" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
                            </div>
                            <div class="toast-body">
                                <a href="/company/find.jsp?cno=${cno}">${amessage}.</a>
                            </div>
                        </div>`
            } // if end
        });
        // 6. print
        toastBox.innerHTML = html;
        printToast( data );
    } catch ( error ){
        console.log( error );
    } // try-catch end
} // func end
getAlarm();

// 5. 푸시알림수정
const updateAlarm = async ( ano ) => {
    try{
        // 1. fetch
        const option = { method : "PUT" };
        const response = await fetch( `/alarm/update?ano=${ano}`, option );
        const data = await response.json();
    } catch ( error ){
        console.log( error );
    } // try-catch end
} // func end

// 6. toast show 함수
const printToast = async ( data ) => {
    // 1. where
    const toastList = document.querySelectorAll('.toast');
    // 2. show
    toastList.forEach( (toastBox) => {
        if ( toastBox ){
            const toast = new bootstrap.Toast(toastBox);
            toast.show();
        } // if end
    })
    // 3. data 순회하기
    data.forEach( (alarm) => {
        updateAlarm( alarm.ano );
    })
} // func end