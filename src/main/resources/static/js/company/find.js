//=============================================== 쿼리스트링 ================================================\\
const params = new URL(location.href).searchParams;
const cno = params.get('cno');
const page = params.get('page') || 1;

let companyData;

//=============================================== 일반 로직 ================================================\\
// 1. 정육점 개별조회
const findCompany = async() => {
    const cimg = document.querySelector('.cimg');
    const cinfo = document.querySelector('.cinfo');
    let html = "";
    try{
        const response = await fetch(`/company/find?cno=${cno}`);
        const data = await response.json();     
        companyData = await data;
        naverMap();
        let imgUrl = '/img/company/' + data.cimg;
        if( data.cimg == null ){
            imgUrl = 'https://placehold.co/50x50';
        } // if end
        cimg.innerHTML = `<img src=${imgUrl}/>`;
        html += `<div>정육점명 : ${data.cname}</div>
                 <div>정육점주소 : ${data.caddress}</div>
                 <div>리뷰평점 : ${data.rrank}</div>
                 <div>조회수 : ${data.views}</div>`;
        cinfo.innerHTML = html;
        loginCheck();
    }catch(e){ console.log(e); }
}// func end
findCompany();

// 2. 채팅방 버튼 출력
const printBtn = async ( ) => {
    const chatBtn = document.querySelector('.chatBtn');
    const sidemno = '1' + cno.substring( 1 );
    try {
        // 1. fetch
        const response = await fetch( "/member/get" );
        const data = await response.json();
        // 2. result
        if ( sidemno == data.mno ){
            // 3. 해당 정육점이 내 정육점이라면, 방으로 안 들어가고 그냥 전체채팅방으로
            chatBtn.innerHTML = `<button type="button" onclick="location.href='/chatting/chatting.jsp?mno=${data.mno}'" class="btn btn-primary">채팅 시작</button>`;
        } else {
            // 4. 해당 정육점이 내 정육점이 아니라면, 해당 정육점과의 채팅방으로 이동
            chatBtn.innerHTML = `<button type="button" onclick="location.href='/chatting/chatting.jsp?mno=${data.mno}&cno=${sidemno}&room=${data.mno}_${sidemno}'" class="btn btn-primary">채팅 시작</button>`;
        } // if end        
    } catch ( error ) {
        // 5. 비로그인 상태라면, 전체채팅방으로 이동
        chatBtn.innerHTML = `<button type="button" onclick="publicRoom()" class="btn btn-primary">채팅 시작</button>`;
    } // try-catch end
} // func end
printBtn();

// 3. 전체채팅방으로 이동
const publicRoom = async ( ) => {
    if ( confirm('비로그인 상태이므로, 전체채팅방으로 이동합니다.') ){
        location.href='/chatting/chatting.jsp?room=0'
    } // if end
} // func end

// 4. 로그인체크
const loginCheck = async() => {
    const reviewactions = document.querySelector('.review-actions');    
    try{
        const response = await fetch("/member/get");
        const data = await response.json();
        if(data.cno != cno){
            html = `<button type="button" class="btn btn-sm btn-outline-secondary" id="reviewBtn"
                        data-bs-toggle="modal" data-bs-target="#staticBackdrop1">
                        리뷰 작성
                    </button>
                    <button type="button" class="btn btn-sm btn-outline-secondary"
                        data-bs-toggle="modal" data-bs-target="#staticBackdrop10" onclick="buildQR()">
                        길찾기 QR
                    </button>`;
        }// if end
        reviewactions.innerHTML = html;
    }catch(e){ console.log(e); }
}// func end

// 5. 리뷰 등록 기능
const addReview = async() => {
    const reviewAddBox = document.querySelector('.reviewAddBox');
    const productForm = new FormData(reviewAddBox);
    productForm.append('cno' , cno);
    const option = {
        method : "POST" , 
        body : productForm
    }// option end
    try{
        const response = await fetch('/review/add',option);
        const data = await response.json();
        if(data == 2){
            alert("리뷰가 등록되었습니다.");
            location.href=`/company/find.jsp?cno=${cno}`;
        }else if(data == 1){
            alert('3km 이상 위치는 작성이 불가능합니다.');
        }else if(data == 3){
            alert('이미지 등록이 실패하였습니다.');
        }else if(data == 0){
            alert('로그인 후 작성 가능합니다.');
            location.href="/member/login.jsp";
        }// if end
    }catch(e){ console.log(e); }
}// func end

// 5. 회사별 리뷰 목록 조회
const getReview = async() => {
    const reviewtbody = document.querySelector('.reviewTbody');    
    let html = "";    
    try{
        const response = await fetch(`/review/get?cno=${cno}&page=${page}`);
        const data = await response.json(); 
        data.data.forEach((re) => {             
            let rimgUrl = "";
            if(re.images == null || re.images == ""){
                rimgUrl = 'https://placehold.co/50x50';
                html += `<div class="rImgBox" >
                                <div><img src=${rimgUrl}/></div>
                        </div>`  
            }// if end
            html += `<div class="rImgBox" style="display: flex;"> `  
            re.images.forEach((img) => {                           
                rimgUrl = '/upload/review/'+encodeURIComponent(img);                   
                html += `<div><img src=${rimgUrl}/></div> `                     
            })// for end          
            html += `</div>`  
            if(re.check == true){
                html += `<tr>
                            <td>${re.rno}</td>                            
                            <td>${re.mname}</td>
                            <td>${re.rcontent}</td>
                            <td>${re.rdate}</td>
                            <td>${re.rrank}</td>
                            <td>
                                <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#staticBackdrop2" onclick="getRnoReview(${re.rno})">
                                    수정
                                </button>
                                <button type="button" class="btn btn-danger" onclick="deleteReview(${re.rno})"> 삭제 </button>
                            </td>
                        </tr>`
            }else{
                html += `<tr>
                            <td>${re.rno}</td>
                            <td>${re.mname}</td>
                            <td>${re.rcontent}</td>
                            <td>${re.rdate}</td>
                            <td>${re.rrank}</td>                            
                        </tr>`
            }// if end
        })// for end        
        reviewtbody.innerHTML = html;
        viewPageButton(data);
    }catch(e){ console.log(e); }
}// func end
getReview();

// 6. 페이징 버튼 출력 함수
const viewPageButton = async ( data ) => {
    let currentPage = parseInt( data.currentPage ); 
    let totalPage = data.totalPage;
    let startBtn = data.startBtn;
    let endBtn = data.endBtn;    

    const pageBtnBox = document.querySelector('.pageBtnBox');
    let html = "";
    //  이전버튼  //
    if(currentPage > 1){
        html += `<li><a href="find.jsp?cno=${cno}&page=${currentPage <= 1 ? 1 : currentPage-1}"> 이전 </a></li>`
    }// if end    
    //  페이지버튼 //
    for(let i = startBtn; i <= endBtn; i++){
        html += `<li> <a href="find.jsp?cno=${cno}&page=${i}" style="${ i == currentPage ? 'color:red' : '' }"> ${i} </a> </li>`
    }// for end
    //  다음버튼 //
    if(currentPage < totalPage){
        html += `<li><a href="find.jsp?cno=${cno}&page=${currentPage+1 >= totalPage ? totalPage : currentPage+1}"> 다음 </a></li>`
    }// if end    
    pageBtnBox.innerHTML = html;
} // func end


// 7. 리뷰번호 리뷰 내용 조회
const getRnoReview = async ( rno ) => {
    try {
        // 1. fetch
        const option = { method : "GET" };
        const response = await fetch( `/review/getRno?rno=${rno}`, option );
        const data = await response.json();         
        // 2. print
        document.querySelector('.oldrno').value = data.rno;
        document.querySelector('.oldrcontent').innerHTML = data.rcontent;
        document.querySelector('.oldrrank').value = data.rrank;
    } catch ( error ){
        console.log( error );
    } // try-catch end
} // func end

// 8. 리뷰 수정 기능
const saveReview = async() => {    
    const reviewupdateBox = document.querySelector('.reviewupdateBox'); 
    const rno = document.querySelector('.oldrno').value;
    const formData = new FormData(reviewupdateBox);  
    formData.append("rno",rno);
    const option = { method : "PUT" , body : formData}  
    try{
        const response = await fetch('/review/update',option);
        const data = await response.json();
        if(data == true){
            alert('수정성공!');
            location.href=`/company/find.jsp?cno=${cno}`
        }else{
            alert('수정실패!');
        }// if end
    }catch(e){ console.log(e); }
}// func end

// 9. 리뷰 삭제
const deleteReview = async(rno) => {
    let result = confirm('삭제 하시겠습니까?');
    if(result == false){ return; }
    const option = { method : "DELETE" }
    try{
        const response = await fetch(`/review/delete?rno=${rno}`,option);
        const data = await response.json();
        if(data == true){
            alert('리뷰 삭제성공');
            location.href=`/company/find.jsp?cno=${cno}`;
        }else{
            alert('리뷰 삭제실패');
        }// if end
    }catch(e){ console.log(e); }
}// func end

// 10. 길찾기 QR Code 출력
const buildQR = async() => {
    const qrbox = document.querySelector('.qrBox');
    try{
        const response = await fetch(`/company/qrcode?cno=${cno}`);
        const blob = await response.blob();
        if(blob.size < 500){
            alert('로그인 후 이용하실 수 있습니다.');
            location.href="/member/login.jsp";
            return;
        }// if end
        const imgUrl = URL.createObjectURL(blob);
        let html = `<img src="${imgUrl}" alt="QR Code"/>`;
        qrbox.innerHTML = html;
    }catch(e){ console.log(e); }
}// func end

// 11. 멤버쉽 신청 버튼 출력
const printMBtn = async ( ) => {
    try {
        // 1. fetch
        const response = await fetch( "/member/get" );
        const data = await response.json();     

        // 2. where
        const checkUser = document.querySelector('#checkUser');
        // 3. what
        let html = '';
        // 4. 해당 정육점이 내 정육점이라면, 멤버쉽 신청 버튼 출력
        if ( data.cno == cno ){
            html += `<div class="membership-status">
                        <span>멤버십</span>
                        <span class="endDate"></span>
                    </div>
                    <button type="button" class="btn btn-sm btn-success" data-bs-toggle="modal" data-bs-target="#staticBackdrop5">
                        신청하기
                    </button>`
        } // if end
        // 5. print
        checkUser.innerHTML = html;
        getCnoEnddate();
    } catch ( error ) {
        console.log( error );
    } // try-catch end
} // func end
printMBtn()

// 12. 멤버쉽 신청
const addPlan = async() => {
    try{
        // 1. Input value
        const planAddBox = document.querySelector('.planAdd');
        const planForm = new FormData( planAddBox );
        // 2. fetch
        const option = {
            method : "POST",
            body : planForm
        } // option end
        const response = await fetch( "/plan/add", option );
        const data = await response.json();
        if( data == 0 ){
            alert('신청 실패');
        }else{
            alert('신청 완료');
            getCnoEnddate();
            location.reload();
        }// if end
    }catch(e){ console.log(e); }
}// func end

// 13. 멤버쉽 남은일 출력
const getCnoEnddate = async() => {
    const endDate = document.querySelector('.endDate');
    let html = "";
    try{
        const response = await fetch("/plan/enddate");
        const data = await response.json();
        if(data >= 0){
            html += `<span>남은일 <span class="endDate">${data}</span>일</span>`
        }// if end
        endDate.innerHTML = html;
    }catch(e){ console.log(e); }
}// func end

// 14. 정육점별 재고목록 조회
const getStock = async() => {
    const stockTbody = document.querySelector('.stockTbody');
    let html = "";
    try{
        const response = await fetch(`/stock/get/find?cno=${cno}`)
        const data = await response.json();
        data.forEach((stock) => {            
            html += `<tr>
                    <td>${stock.cname}</td>
                    <td>${stock.pname}(100g)</td>
                    <td>${stock.sprice}원</td>
                </tr>`                        
        })// for end
        stockTbody.innerHTML = html;
    }catch(e){ console.log(e); }
}// func end
getStock();

//=============================================== 네이버지도 API JS ================================================\\
// 1. 네이버지도 출력
const naverMap = async ( ) => {
    const option = { method : "GET" };
    const response = await fetch ( `/map/getLatLng?caddress=${companyData.caddress}`, option );
    const data = await response.json();     

    var latlng = new naver.maps.LatLng( data.lat, data.lng );

    // 정육점 위치
    var company = new naver.maps.LatLng( data.lat, data.lng ),
        map = new naver.maps.Map('map', {
            center: company.destinationPoint(0, 0),
            zoom: 17
        }),
        marker = new naver.maps.Marker({
            map: map,
            position: company
        });

    naver.maps.Event.addListener(marker, "click", function(e) {
        if (infowindow.getMap()) {
            infowindow.close();
        } else {
            infowindow.open(map, marker);
        }
    });

    infowindow.open(map, marker);
} // func end