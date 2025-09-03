
console.log('signup.js exe');

// 유효성
const test = [false,false,false,false,false] // 7개 mname/mid/mwd/mwd확인/mphone/maddress/memail

const signupbtn = async() => {
    console.log('회원가입1');

    // 회원버튼 클릭시 일치x 이면
    if(test.includes(false)){
        alert('올바른 정보 입력해주십시오');
        return;
    }
 // 아나면 통과
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

// 이름 체크
const mnamecheck = async () =>{
    const mname2 = document.querySelector('.mname2').value;
    const mnameCheck =document.querySelector('.mnameCheck');
    
    if(mname2.length < 3 || mname2.length > 10){
        mnameCheck.innerHTML = "명은 3글자 부터 10글자 이하 까지 가능합니다.";
        test[3] = false;
    }else{
        mnameCheck.innerHTML = `사용가능한 회원명입니다.`;
        test[3] = true; 
    }
}


// 아이디 체크
const idcheck = async () => {
    
    const mid = document.querySelector('.mid2').value;
    const idchecks = document.querySelector('.idchecks');

    if(mid.length < 6 || mid.length > 10) {
        idchecks.innerHTML = "6글자 이상 10글자 이하로 가능합니다.";
        test[0] = false; // test[0] < id 
        return; 
    }
    try{
        const op = { method : "GET" };
        const response = await fetch(`/member/check?type=mid&data=${mid}`, op);
        const data = await response.json();

        if(data==true){
            idchecks.innerHTML = '사용중인 아이디 입니다,';
            test[0] = false; // 유효성 실패
        }else{
            idchecks.innerHTML = '사용 가능한 아이디 입니다.';
            test[0] = true; // 유효성 성공
        }
    }catch(error){console.log(error) ; }
}
 // 비밀번호 체크
    const pwdcheck = async() => {

        const mpwd = document.querySelector('.mpwd2').value;
        const Pwd = document.querySelector('.Pwd');

        if(mpwd.length <6 || mpwd.length > 13){
            Pwd.innerHTML = "비밀번호는 6글자 이상 13글자 이하로 가능합니다.";
            test[1] = false; // test[1] mpwd;
            return;
        }
        try{
            const op = { method : "GET"};
            const response = await fetch(`/member/check?type=mpwd&data=${mpwd}` , op );
            const data = await response.json();

            if(data==true){
                Pwd.innerHTML = "사용 불가능";
                test[1] = false; // 비번 실패
            }else{
                Pwd.innerHTML = "사용 가능";
                test[1] = true;
            }
        }catch(error){console.log(error);}
    }
// 비밀번호 일치 체크
    const pwdcheck2 = async() => {
        const mpwd22 = document.querySelector('.mpwd22').value;
        const mpwd2 = document.querySelector('.mpwd2').value;
        const Pwdd = document.querySelector('.Pwdd');
        
        if(mpwd22 !== mpwd2 ){
            Pwdd.innerHTML = "일치XX";
            test[2] = false;
        }else{
            Pwdd.innerHTML = "일치OO";
            test[2] = true;
        }
    }

//     const phonecheck = async() =>{
//         const phonebox =  document.querySelector('.mphone2');
//         const mphones = phonebox.value;
//         const phone = /^01[0-9]-\d{3,4}-\d{4}$/.test(phonebox);
//         const phonechecks = document.querySelector('.phonechecks');

//         if(mphones.length == 3){
//             phonebox.value = mphones +  "-";
//         }
//         if(mphones.length == 8){
//             phonebox.value += "-";
//             return;
//         }
//         try{
//             const op = {method : "GET"};
//             const response = await fetch(`/member/check?type=mphone&data=${mphones}` , op );
//             const data = await response.json();
        
//         if(phone){
//             phonechecks.innerHTML = "사용가능한 전화번호입니다.";
//             test[4] = true;
//         }else{
//             phonechecks.innerHTML = "너무 짧거나 깁니다.";
//             test[4] = false;
//         }
//     }catch(error){console.log(error) ; }
// }
