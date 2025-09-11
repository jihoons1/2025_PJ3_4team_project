//=============================================== 일반 로직 ================================================\\
const test = [false];
// 1. 아이디 찾기
const findid = async() => {
    const mname = document.querySelector('.mname').value;
    const mphoneid = document.querySelector('.mphoneid').value;

    try{

    const response = await fetch(`/member/findId?mname=${mname}&mphone=${mphoneid}`);
    const data = await response.text();
    if(data != null && data != "" ){
        alert(`조회 결과, 회원님의 아이디는 \n[${data}]\n입니다.`);
    }else{
        alert('입력하신 정보로 회원을 찾지 못했습니다.');
    }
    }catch(error){console.log(error); }
}


// 2. 비밀번호 [임시코드 발급]
const findpwd = async() => {
    const mid = document.querySelector('.mid').value;
    const mphonepwd = document.querySelector('.mphonepwd').value;

    const op = {mid , mphone : mphonepwd}
    try{

    const obj = { method : "POST" , headers : {"Content-Type" : "application/json"} , body : JSON.stringify(op)}
    const response = await fetch(`/member/findPwd`,obj);
    const data = await response.json();

    if(data){
        alert(`임시 비밀번호가 정상적으로 발급되었습니다.`);
    }else{
        alert('입력하신 정보로 회원을 찾지 못했습니다.');
    }
    }catch(error){console.log(error) ;} 
}

// 3. 
const findphone = async() => {
    const mname = document.querySelector('.mname').value;
    const mphoneid = document.querySelector('.mphoneid');
    const findPhone = document.querySelector('.findPhone');
    const btnid = document.querySelector('.btnid');

    let hiphone = mphoneid.value.replace(/[^0-9]/g , "");
    // 전화번호 0~ 11자리 -제외
    if(hiphone.length > 11){
        hiphone = hiphone.slice(0 , 11);
    }

    // 자동 하이폰 만들기
    if(hiphone.length <=3) { // 전화번호 입력할때 3자리이면 그냥 출력
        mphoneid.value = hiphone; // ex) 010
    }else if( hiphone.length <= 7) { // 숫자가 4~7개면 중간에 하이폰 생성
        mphoneid.value = hiphone.replace(/(\d{3})(\d{1,4})/, "$1-$2");
    }else { // 8자리 이상이면 010 < 3자리 - 1234 < 4자리 - 5678 4자리 - 형태로 삽입
        mphoneid.value = hiphone.replace(/(\d{3})(\d{4})(\d{1,4})/, "$1-$2-$3");
    }
    const phoneche = /^01[0-9]-\d{3,4}-\d{4}$/; // 휴대폰 ^시작 01[0부터9까지]-[3~4자리]-[고정4자리] $끝
    
    

    if(phoneche.test(mphoneid.value)){
        findPhone.innerHTML = "";
        btnid.disabled = false;
    }else{
        findPhone.innerHTML = "유효하지 않은 전화번호입니다. 다시 확인해주세요.";
        btnid.disabled = true  ;
    }
}


const findphone2 = async() => {
    const mid = document.querySelector('.mid').value;
    const mphonepwd = document.querySelector('.mphonepwd');
    const findPhone2 = document.querySelector('.findPhone2');
    const btnpwd = document.querySelector('.btnpwd');

    let hiphone = mphonepwd.value.replace(/[^0-9]/g , "");
    // 전화번호 0~ 11자리 -제외
    if(hiphone.length > 11){
        hiphone = hiphone.slice(0 , 11);
    }

    // 자동 하이폰 만들기
    if(hiphone.length <=3) { // 전화번호 입력할때 3자리이면 그냥 출력
        mphonepwd.value = hiphone; // ex) 010
    }else if( hiphone.length <= 7) { // 숫자가 4~7개면 중간에 하이폰 생성
        mphonepwd.value = hiphone.replace(/(\d{3})(\d{1,4})/, "$1-$2");
    }else { // 8자리 이상이면 010 < 3자리 - 1234 < 4자리 - 5678 4자리 - 형태로 삽입
        mphonepwd.value = hiphone.replace(/(\d{3})(\d{4})(\d{1,4})/, "$1-$2-$3");
    }
    const phoneche = /^01[0-9]-\d{3,4}-\d{4}$/; // 휴대폰 ^시작 01[0부터9까지]-[3~4자리]-[고정4자리] $끝
    
    if(phoneche.test(mphonepwd.value)){
        findPhone2.innerHTML = "";
        btnpwd.disabled = false;
    }else{
        findPhone2.innerHTML = "아이디 또는 전화번호가 올바르지 않습니다.";
        btnpwd.disabled = true  ;
    }
}