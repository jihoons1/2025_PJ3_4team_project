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
    try {
        // 1. Input value
        const mpwd = document.querySelector('.mpwdInput').value;
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
} // func end

// [3] 회원별 알림조회
const getNotice = async ( ) => {
    console.log('getNotice func exe');
    // 1. fetch
    const option = { method : "GET" };
    const response = await fetch( "/notice/get", option );
    const data = await response.json();
    // 2. where
    const noticeTbody = document.querySelector('.noticeTbody');
    // 3. what
    let html = ``;
    data.forEach( (notice) => {
        let ncheck = notice.ncheck;
        if ( ncheck == 0 ){
            ncheck = '미전송'
        } else {
            ncheck = ncheck + '원에 전송완료'
        } // if end
        html += `<tr>
                    <td>${notice.nno}</td>
                    <td>${notice.pname}</td>
                    <td>${notice.nprice}</td>
                    <td>${notice.ndate}</td>
                    <td>${ncheck}</td>
                    <td>
                        <button type="button" onclick="updateNotice"> 수정 </button>
                        <button type="button" onclick="deleteNotice"> 삭제 </button>
                    </td>
                 </tr>`
    });
    // 4. print
    noticeTbody.innerHTML = html;
} // func end
getNotice();

// [4] 제품 전체조회
const getProduct = async ( ) => {
    console.log('getProduct func exe');
    // 1. fetch
    const option = { method : "GET" };
    const response = await fetch( "/product/get", option );
    const data = await response.json();     console.log( data );
    // 2. where
    const pBox = document.querySelector('.pBox');
    // 3. what
    let html = `<option selected disabled>제품을 선택하세요.</option>`;
    data.data.forEach( (product) => {
        html += `<option value="${product.pno}">
                    ${product.cname} - ${product.pname}
                 </option>`
    })
    // 4. print
    pBox.innerHTML = html;
} // func end
getProduct();

// [5] 알림 등록기능
const addNotice = async ( ) => {
    console.log('addNotice func exe');
    // 1. Input value
    const pno = document.querySelector('.pBox').value;
    const nprice = document.querySelector('.nprice').value;
    // 2. obj
    const obj = { pno, nprice };
    // 3. fetch
    const option = {
        method : "POST",
        headers : { "Content-Type" : "application/json" },
        body : JSON.stringify( obj )
    } // option end
    const response = await fetch( "/notice/add", option );
    const data = await response.json();
    // 4. result
    if ( data > 0 ){
        alert('알림등록 성공!');
        location.reload();
    } else {
        alert('알림등록 실패!')
    } // if end
} // func end