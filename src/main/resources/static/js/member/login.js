//=============================================== 일반 로직 ================================================\\
// 1. 로그인 버튼
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
        const response2 = await fetch(`/member/get?mid=${mid}`);
        const data2 = await response2.json();
        alert(`✅ 로그인 성공!\n\n${data2.mname}님, 어서 오세요 😊`);
        location.href="/index.jsp";
    }else{
        alert('❌ 아이디 또는 비밀번호가 올바르지 않습니다.\n다시 확인해주세요.');
        
        
    }
    }catch(error)  {console.log(error);}
} // func end

// 2. id , pwd
const loginCheck = async () => {
    const mid = document.querySelector('#mid').value;  
    const mpwd = document.querySelector('#mpwd').value; 
    const loginCheck = document.querySelector('.loginCheck');


    if (mid == '' && mpwd != '') {
        loginCheck.innerHTML = ' 아이디를 입력해주세요.';
    }else if(mid != '' && mpwd == ''){
        loginCheck.innerHTML = '비밀번호를 입력해주세요. ';
    }else if(mid == '' && mpwd == ''){
        loginCheck.innerHTML = '아이디와 비밀번호를 입력해주세요.';
    }else{
        loginCheck.innerHTML = '';
    }
}