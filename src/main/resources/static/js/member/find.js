
console.log('findid.js exe');


// 아이디 찾기

const findid = async() => {
    const mname = document.querySelector('.mname').value;
    const mphoneid = document.querySelector('.mphoneid').value;

    try{

    const response = await fetch(`/member/findId?mname=${mname}&mphone=${mphoneid}`);
    const data = await response.json();

    if(data && data.mid){
        alert(`조회결과 회원님의 아이디는 \n[${data.mid} ]\n입니다.`);
    }else{
        alert('아이디를 찾지 못했습니다.');
    }
    }catch(error){console.log(error); }
}


// 비밀번호 [임시코드 발급]

const findpwd = async() => {
    const mid = document.querySelector('.mid').value;
    const mphonepwd = document.querySelector('.mphonepwd').value;

    const op = {mid , mphone : mphonepwd}
    try{

    const obj = { method : "POST" , headers : {"Content-Type" : "application/json"} , body : JSON.stringify(op)}
    const response = await fetch(`/member/findPwd`,obj);
    const data = await response.json();

    if(data==true){
        alert(`임시 발급 완료 입니다.`);
    }else{
        alert('임시 발급 실패.');
    }
    }catch(error){console.log(error) ;}
    }