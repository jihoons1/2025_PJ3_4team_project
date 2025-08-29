
console.log('signup.js exe');

const signupbtn = async() => {
    const mname = document.querySelector('.mname');
    const mid = document.querySelector('.mid');
    const mpwd = document.querySelector('.mpwd');
    const mpwd2 = document.querySelector('.mpwd2'); // 비밀번호 재확인 
    const mphone = document.querySelector('.mphone');
    const maddress = document.querySelector('.maddress');
    const emailselect = document.querySelector('#emailselect');

    const obj = {
        "mname" : mname , "mid" : mid ,
        "mpwd" : mpwd , "mpwd2" : mpwd2 ,
        "mphone" : mphone , "maddress" : maddress , "emailselect" : emailselect };

    const op = { method : "POST" ,
        headers : {"Content-Type" : "multipart/form-data" } ,
        body : JSON.stringify(obj);
    }

    const response = await fetch('/member/signup.jsp' , obj);
    const data = await response.json();

    if(data > 0 ){
        alert(' 회원가입 성공 ')
    }
}