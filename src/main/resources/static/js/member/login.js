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
        alert('로그인 성공\n' + `\n어서오세요`);
        location.href="/index.jsp";
    }else{
        
    }
    }catch(error)  {console.log(error);}
} // func end

// id , pwd

const loginCheck = async () => {
    const mid = document.querySelector('#mid').value;  console.log(mid);
    const mpwd = document.querySelector('#mpwd').value; console.log(mpwd);
    const loginCheck = document.querySelector('.loginCheck');


        if (mid == '') {
            loginCheck.innerHTML = '아이디 | 또는 비밀번호 입력 바람';
        } else {
            loginCheck.innerHTML = '';
        }
        
    }