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
        const data = await response.json();         console.log( data );

        // 정육점을 가진 회원이라면
        if ( data.cno > 0 ){
            menuHtml += `<li><a href="/product/product.jsp" class="nav-link px-2">제품 목록</a></li>
                         <li><a href="/company/list.jsp" class="nav-link px-2">정육점 목록</a></li>
                         <li><a href="/stock/stock.jsp?cno=${data.cno}" class="nav-link px-2">재고 관리</a></li>`
            logHtml +=` <button type="button" onclick="location.href='/member/mypage.jsp'" class="btn btn-outline-primary me-2">${data.mname}</button>
                        <button type="button" onclick="location.href='/company/find.jsp?cno=${data.cno}'" class="btn btn-outline-primary me-2">내 정육점정보</button>
                        <button type="button" onclick="logout()" class="btn btn-primary">로그아웃</button>`
        } else {
            // 정육점이 없는 회원이라면
            menuHtml += `<li><a href="/product/product.jsp" class="nav-link px-2">제품 목록</a></li>
                        <li><a href="/company/list.jsp" class="nav-link px-2">정육점 목록</a></li>`
            logHtml +=` <button type="button" onclick="location.href='/member/mypage.jsp'" class="btn btn-outline-primary me-2">${data.mname}</button>
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