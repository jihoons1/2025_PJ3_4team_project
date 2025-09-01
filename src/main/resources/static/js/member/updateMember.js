
console.log('updateMemeber.js exe');


 // 회원정보 수정 [프로필 이미지 , 주소 , 휴대번호 ]
const update = async() => {
    
    const mig = document.querySelector('#mig');
    const mphone = document.querySelector('.mphone2').value;
    const maddress = document.querySelector('.maddress2').value;
    
    const mimg = new FormData(mig);
    mimg.append("mphone2" , mphone);
    mimg.append("maddress2", maddress);
    
    console.log(mimg);
    try{
        const op = { method : "PUT" ,
        body : mimg } 

        const response = await fetch(`/member/updateMember` , op);
        const data = await response.json();

        if(data == 0){
            alert(' 수정실패');
        }else{
            alert('수정 성공');
            location.href="/member/mypage.jsp";
        }

    }catch(error){console.log(error); }

}