
// console.log('myinfo');

// const myinfo = async() => {
//     const log = document.querySelector('.log');
//     let html = '';
//     try{
//         //fetch 실행
//         const op = { method : "GET" }
//         const response = await fetch(`/member/get` , op)
//         const data = await response.json();

//         html +=`<span> ${data.mid} </span>
//                 <span> 내정보 </span>
//                 <span> 로그아웃 </span>`
//     }catch{
//         html +=`<span><a href="member/login.jsp">로그인</span>
//                 <span>회원가입</span>`
//     } 
//     log.innerHTML = html; // 출력
// } // func end

// myinfo();

console.log('login.js test');

const loginbtn = async() => {
    const mid = document.querySelector('.mid').value;
    const mpwd = document.querySelector('.mpwd').value;

    const obj = { mid , mpwd};
    try{
    const op = {
        method : "POST" , 
        headers : { "Content-Type" : "application/json"},
        body : JSON.stringify(obj)
    };

    const response = await fetch('/member/login' , op);
    const data = await response.json();

    if(data > 0 ){
        alert(' 로그인 성공\n' + `어서오세요`);
        location.href="/index.jsp";
    }else{
        alert(' 로그인 실패 ');
    }
    }catch(error)  {console.log(error);}
} // func end
