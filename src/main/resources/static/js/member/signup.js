
console.log('signup.js exe');

// 유효성
// const test = [false,false,false,false,false,false,false] // 7개 id/pass/pass확인/phone/address/email

const signupbtn = async() => {
    console.log('회원가입1');

    // if(test.includes(false)){
    //     alert('올바른 정보 입력해주십시오');
    //     return;
    // }

    const mig = document.querySelector('#mig');
    const emailname = document.querySelector('.emailname').value;
    const emailselect =document.querySelector('#emailselect').value;

    const memail = emailname + emailselect;
    const mimgupload = new FormData(mig);
    mimgupload.append("memail", memail);
    console.log(mimgupload);
    try{

    const op = { method : "POST" , body : mimgupload }
    const response = await fetch('/member/signup' , op);
    const data = await response.json();
        console.log(data);
    if(data > 0 ){
        alert('회원가입 성공');
        location.href="/member/login.jsp"; 
        }else{
        alert('회원가입 실패'); }
    }catch(error){console.log(error+"회원가입 에러"); }
}


