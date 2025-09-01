console.log('mypage.js open');

// [1] 회원정보 상세조회
const getMember = async ( ) => {
    console.log('getMember func exe');
    // 1. fetch
    const option = { method : "GET" };
    const response = await fetch( "/member/get", option );
    const data = await response.json();         console.log( data );
    // * ming URL 만들기
    let mimgURL = `/upload/member/${data.mimg}`
    if ( data.mimg == null ){
        mimgURL = 'https://placehold.co/100x100';
    } // if end


    // 2. where + print
    document.querySelector('.mimg').innerHTML = `<img src="${mimgURL}">`;
    document.querySelector('.mid').innerHTML = `${data.mid}`;
    document.querySelector('.mname').innerHTML = `${data.mname}`;
    document.querySelector('.mphone').innerHTML = `${data.mphone}`;
    document.querySelector('.memail').innerHTML = `${data.memail}`;
    document.querySelector('.maddress').innerHTML = `${data.maddress}`;
    document.querySelector('.mdate').innerHTML = `${data.mdate}`;


} // func end
getMember();

// [2] 회원탈퇴 기능
const resignMember = async ( ) => {
    console.log('resignMember func exe');
    let con = confirm('정말로 탈퇴하시겠습니까?');
    if ( con == true ){
        try {
            // 1. Input value
            const mpwd = prompt('탈퇴를 원하신다면, 현재 비밀번호를 입력해주세요.');
            // 2. obj
            const obj = { mpwd };
            // 3. fetch
            const option = {
                method : "POST",
                headers : { "Content-Type" : "application/json" },
                body : JSON.stringify( obj )
            } // option end
            const response = await fetch( "/member/resign", option );
            const data = await response.json();
            // 4. result 
            if ( data == true ){
                alert('회원탈퇴 성공!');
                location.href = `/index.jsp`;
            } else {
                alert('회원탈퇴 실패! 다시 입력해주세요.');
            } // if end
        } catch ( error ) {
            console.log( error );
        } // try-catch end
    } else {
        alert('탈퇴가 취소되었습니다.');
    } // if end
} // func end