//=============================================== 유효성 검사 ================================================\\
const test = [false,false,false,false,false] // 7개 mname/mid/mwd/mwd확인/mphone/maddress/memail

//=============================================== 일반 로직 ================================================\\
// 1. 회원가입 기능
const signupbtn = async() => {
    // 회원버튼 클릭시 일치x 이면
    if(test.includes(false)){
        alert('올바른 정보 입력해주십시오');
        return;
    }
 // 아나면 통과
    const mig = document.querySelector('#mig');
    const emailname = document.querySelector('.emailname').value;
    const emailselect =document.querySelector('#emailselect').value;
    const sample6_address = document.querySelector('#sample6_address').value;
    const sample6_detailAddress= document.querySelector('#sample6_detailAddress').value;
    
    let maddress = sample6_address + "," + sample6_detailAddress;

    const memail = emailname + emailselect;
    const mimgupload = new FormData(mig);
    mimgupload.append("memail", memail);
    mimgupload.append("maddress", maddress);
    try{

    const op = { method : "POST" , body : mimgupload }
    const response = await fetch('/member/signup' , op);
    const data = await response.json();
    if(data > 0 ){
        alert('회원가입 성공');
        location.href="/member/login.jsp"; 
        }else{
        alert('회원가입 실패'); }   
    }catch(error){console.log(error+"회원가입 에러"); }
}

// 2. 이름 체크
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


// 3. 아이디 체크
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

// 4. 비밀번호 체크
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

// 5. 비밀번호 일치 체크
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

// 6. 
const phonecheck = async() =>{
    const phonebox =  document.querySelector('.mphone2');
    const phonechecks = document.querySelector('.phonechecks');

    // phonebox에 입력받은값(value) 를 숫자로 추출해서 hiphone 에 넣기
    let hiphone = phonebox.value.replace(/[^0-9]/g, "");

    // 자동 하이폰 만들기
    if(hiphone.length <=3) { // 전화번호 입력할때 3자리이면 그냥 출력
        phonebox.value = hiphone; // ex) 010
    }else if( hiphone.length <= 7) { // 숫자가 4~7개면 중간에 하이폰 생성
        phonebox.value = hiphone.replace(/(\d{3})(\d{1,4})/, "$1-$2");
    }else { // 8자리 이상이면 010 < 3자리 - 1234 < 4자리 - 5678 4자리 - 형태로 삽입
        phonebox.value = hiphone.replace(/(\d{3})(\d{4})(\d{1,4})/, "$1-$2-$3");
    }

    // 휴대폰 검사
    const phoneche = /^01[0-9]-\d{3,4}-\d{4}$/; // 휴대폰 ^시작 01[0부터9까지]-[3~4자리]-[고정4자리] $끝

    if(phoneche.test(phonebox.value)){
        phonechecks.innerHTML = "사용가능한 휴대폰 번호";
        test[4] = true;
    }else{
        phonechecks.innerHTML = "사용불가능";
        test[4] = false;
    }
}

// 7.
const emailcheck = async() => {
    const emailCheck = document.querySelector('.emailCheck');
    const emailname = document.querySelector('.emailname').value;
    const emailselect = document.querySelector('#emailselect');
    const emailsel = emailselect.value;

    let memail = '';

    // ^ test123 [^\s@] < test 123 xx  , + @ < test123@ [^\s@] test213@naver or gmail \.
    const memailtext = /^[^\s@]+@[^\s@]+\.[^\s@]+$/; // 첫 ^ 문자열받을때 [\s@]공백x,띄어쓰기x 후 + @ 이메일 넣고 + 다시 문자열(도메인)받고 \. [^\s@]+ → 확장자(com, net 등, 1글자 이상, 공백x, @x)  $끝

    if(emailname.includes("@")){
        emailselect.disabled = true;
        memail = emailname;
    }else{
        emailselect.disabled = false;

        if(emailname == '' ||  emailsel == "이메일 선택"){
            emailCheck.innerHTML = "이메일 선택해주십시오.";
            return;
        }
        memail = emailname + emailsel;
    }
    // try{
    //     const op = { method : "GET" };
    //     const response = await fetch(`/member/check?type=memail&data=${memail}`, op);
    //     const data = await response.json();

    if(memailtext.test(memail) &&
        (memail.endsWith("@naver.com") || memail.endsWith("@gmail.com") || memail.endsWith("@daum.net") )){
        emailCheck.innerHTML = "사용 가능.";
        test[5] = true;
    }else{
        emailCheck.innerHTML = "사용 불가능.";
        test[5] = false;
    }
}

// 8.
function sample6_execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if(data.userSelectedType === 'R'){
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraAddr !== ''){
                    extraAddr = ' (' + extraAddr + ')';
                }
                // 조합된 참고항목을 해당 필드에 넣는다.
                
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('sample6_postcode').value = data.zonecode;
            document.getElementById("sample6_address").value = addr + extraAddr;

            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("sample6_detailAddress").focus();

        }
    }).open();
}