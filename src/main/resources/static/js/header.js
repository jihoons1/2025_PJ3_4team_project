console.log('header.js open');

let MemberData;

const myinfo = async() => {
    const log = document.querySelector('.log');
    let html = '';
    try{
        //fetch 실행
        const op = { method : "GET" }
        const response = await fetch(`/member/get` , op)
        const data = await response.json();
        MemberData = data;

        html +=`    <button type="button" class="btn btn-outline-primary me-2">${data.mname}</button>
                    <button type="button" onclick="location.href='/member/mypage.jsp'" class="btn btn-outline-primary me-2">내정보</button>
                    <button type="button"  onclick="logout()" class="btn btn-primary">로그아웃</button>`
    }catch{
        html +=`    <button type="button" onclick="location.href='/member/login.jsp'" class="btn btn-outline-primary me-2">로그인</button>
                    <button type="button" onclick="location.href='/member/signup.jsp'" class="btn btn-primary">회원가입</button>`
    } 
    log.innerHTML = html; // 출력
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