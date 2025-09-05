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
        alert('로그인 성공\n' + `어서오세요`);
        location.href="/index.jsp";
        const message = { type : "login", mno : data }
        client.send( JSON.stringify(message) );
    }else{
        alert(' 로그인 실패 ');
    }
    }catch(error)  {console.log(error);}
} // func end
