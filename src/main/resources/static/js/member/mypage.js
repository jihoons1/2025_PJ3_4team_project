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

let noticeData;
// [3] 회원별 알림조회
const getNotice = async ( ) => {
    console.log('getNotice func exe');
    // 1. fetch
    const option = { method : "GET" };
    const response = await fetch( "/notice/get", option );
    const data = await response.json();
    noticeData = await data;
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
                        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#staticBackdrop4" onclick="updatePrint(${notice.nno})"> 수정 </button>
                        <button type="button" onclick="deleteNotice(${notice.nno})"> 삭제 </button>
                    </td>
                 </tr>`
    });
    // 4. print
    noticeTbody.innerHTML = html;
} // func end
getNotice();

// [] 수정 기본 출력
const updatePrint = async ( nno ) => {
    console.log( nno )
    console.log( noticeData[1].length );
    // 1. noticeData를 통해 기본 출력
    for ( let i = 0; i < noticeData.length; i++ ){
        if ( noticeData[i].nno == nno ){
            document.querySelector('.oldPname').innerHTML = `${noticeData[i].pname}`;
            document.querySelector('.NewNprice').value = `${noticeData[i].nprice}`;
            document.querySelector('#updateInput').innerHTML =
                                `<button type="button" class="btn btn-secondary" data-bs-dismiss="modal"> 닫기 </button>
                                 <button type="button" class="btn btn-primary"  onclick="updateNotice(${nno})">알림수정</button>`
        } // if end
    } // for end
} // func end

// [7] 알림 수정
const updateNotice = async ( nno ) => {
    console.log('updateNotice func exe');
    // 1. Input value
    const nprice = document.querySelector('.NewNprice').value;
    // 2. obj
    const obj = { nno, nprice };
    // 3. fetch
    const option = {
        method : "PUT",
        headers : { "Content-Type" : "application/json" },
        body : JSON.stringify( obj )
    } // option end
    const response = await fetch( "/notice/update", option );
    const data = await response.json();
    // 4. result
    if ( data == true ){
        alert('알림수정 성공!');
        location.reload();
    } else {
        alert('알림수정 실패!');
    } // if end
} // func end

// [8] 알림 삭제
const deleteNotice = async ( nno ) => {
    console.log('deleteNotice func exe');
    // 1. fetch
    const option = { method : "DELETE" };
    const response = await fetch( `/notice/delete?nno=${nno}`, option );
    const data = await response.json();
    // 2. result
    if ( data == true ){
        alert('알림삭제 성공!');
        location.reload();
    } else {
        alert('알림삭제 실패!')
    } // if end
} // func end

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

// [6] 회원별 리뷰조회
const getMnoReview = async ( ) => {
    console.log('getMnoReview func exe');
    // 1. fetch
    const option = { method : "GET" };
    const response = await fetch( "/review/getMno", option );
    const data = await response.json();     console.log( data );
    // 2. where
    const reviewTbody = document.querySelector('.reviewTbody');
    // 3. what
    let html = ``;
    data.forEach( (review) => {
        if(review.images == null || review.images == ""){
            let reimg = 'https://placehold.co/50x50';
            html += `<div class="rImgBox" style="display: flex;">
                                <div><img src=${reimg}/></div>
                        </div>`
        }// if end
        console.log(reimg);
        review.images.forEach((img) => {
            imgUrl = '/upload/review'+img;
            html += `<div class="rImgBox" style="display: flex;">
                                <div><img src=${imgUrl}/></div>
                        </div>`
        })// for end
        console.log(imgUrl);
        html += `<tr>
                    <td>${review.rno}</td>
                    <td>${review.cname}</td>
                    <td>${review.rcontent}</td>
                    <td>${review.rrank}</td>
                    <td>${review.rdate}</td>
                    <td>
                        <button type="button" onclic="getUpdateBtn()"> 수정 </button>
                        <button type="button"> 삭제 </button>
                    </td>
                 </tr>`
    })
    // 4. print
    reviewTbody.innerHTML = html;
} // func end
getMnoReview();

// [7] 수정html불러오기
const getUpdateBtn = async() => {
    const thisTr = updateBtn.closest("tr");
    const rno = thisTr.querySelector("td:nth-child(1)").innerText;
    const rcontent = thisTr.querySelector("td:nth-child(3)").innerText
    thisTr.innerHTML = `<td>${rno}<input type="hidden" name="rno" value="${rno}"></td>
                        <td><textarea name="rcontent">${rcontent}</textarea></td>
                        <td><select name="rrank">
                            <option value="0">평점</option>
                            <option value="5">5</option>
                            <option value="4">4</option>
                            <option value="3">3</option>
                            <option value="2">2</option>
                            <option value="1">1</option>
                        </select></td>
                        <td><input type="file" multiple name="uploads"/></td>
                        <td><button type="button" onclick="addUpdate(this)">수정</button>
                            <button type="button" onclick="getMnoReview()">취소</button></td>`
}// func end

// [8] 리뷰 수정 기능
const addUpdate = async(btn) => {
    const thistr = btn.closest("tr");
    const rno = thistr.querySelector("input[name='rno']").value;
    const rcontent = thistr.querySelector("textarea[name='rcontent']").value;
    const rrank = thistr.querySelector("select[name='rrank']").value;
    let uploads = thistr.querySelector("input[name='uploads']").files;
    const formData = new FormData();
    formData.append('rno',rno);
    formData.append('rcnotent',rcontent);
    formData.append('rrank',rrank);
    if(uploads.length > 0){
        for(let i = 0; i < uploads.length; i++){
            formData.append('uploads',uploads[i]);
        }// for end
    }// if end
    const option = { method : "PUT" , body : formData }
    try{
        const response = await fetch(`/ewview/update`,option);
        const data = await response.json();
        if(data == true){
            alert('수정 성공!');
            getMnoReview();
        }else{
            alert('수정 실패!');
        }// if end
    }catch(e){ console.log(e); }
}// func end

const dd= async() => {

    location.href="/member/updateMember.jsp";
}