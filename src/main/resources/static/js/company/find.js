console.log('find.js check');

const params = new URL(location.href).searchParams;
const cno = params.get('cno');
const page = params.get('page') || 1;

let companyData;

// 정육점 개별조회
const findCompany = async() => {
    const findTbody = document.querySelector('#findTbody');
    let html = "";
    try{
        const response = await fetch(`/company/find?cno=${cno}`);
        const data = await response.json();     console.log( data );
        companyData = await data;
        naverMap();
        let imgUrl = '/upload/'+encodeURIComponent(data.cimg);
        if( data.cimg == null ){
            imgUrl = 'https://placehold.co/50x50';
        } // if end
        html += `<tr>
                    <td><img src=${imgUrl}/></td>
                    <td>${data.cname}</td>
                    <td>${data.caddress}</td>
                    <td>${data.rrank}</td>
                </tr>`;
        findTbody.innerHTML = html;
    }catch(e){ console.log(e); }
}// func end
findCompany();

// 리뷰 등록 기능
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
        if(data == true){
            alert("리뷰가 등록되었습니다.");
            location.href=`/company/find.jsp?cno=${cno}`;
        }else{
            alert('리뷰 등록 실패!');
        }// if end
    }catch(e){ console.log(e); }
}// func end

// [3] 회사별 리뷰 목록 조회
const getReview = async() => {
    const reviewtbody = document.querySelector('.reviewTbody');    
    let html = "";    
    try{
        const response = await fetch(`/review/get?cno=${cno}`);
        const data = await response.json(); console.log(data);
        data.data.forEach((re) => { 
            console.log(re.images);
            let rimgUrl = "";
            if(re.images == null || re.images == ""){
                rimgUrl = 'https://placehold.co/50x50';
                html += `<div class="rImgBox" >
                                <div><img src=${rimgUrl}/></div>
                        </div>`  
            }// if end
            console.log(rimgUrl);
            html += `<div class="rImgBox" style="display: flex;"> `  
            re.images.forEach((img) => {                           
                rimgUrl = '/upload/review/'+encodeURIComponent(img);                   
                html += `<div><img src=${rimgUrl}/></div> `                     
            })// for end          
            html += `</div>`  
            console.log(rimgUrl);
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
                                <button type="button" onclick="deleteReview(${re.rno})"> 삭제 </button>
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

// [4] 페이징 버튼 출력 함수
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


// [5] 리뷰번호 리뷰 내용 조회
const getRnoReview = async ( rno ) => {
    console.log('getRnoReview func exe');
    try {
        // 1. fetch
        const option = { method : "GET" };
        const response = await fetch( `/review/getRno?rno=${rno}`, option );
        const data = await response.json();         console.log( data );
        // 2. print
        document.querySelector('.oldrno').value = data.rno;
        document.querySelector('.oldrcontent').innerHTML = data.rcontent;
        document.querySelector('.oldrrank').value = data.rrank;
    } catch ( error ){
        console.log( error );
    } // try-catch end
} // func end

// [6] 리뷰 수정 기능
const saveReview = async() => {    
    const reviewupdateBox = document.querySelector('.reviewupdateBox'); 
    const rno = document.querySelector('.oldrno').value;
    const formData = new FormData(reviewupdateBox);  
    formData.append("rno",rno);
    const option = { method : "PUT" , body : formData}  
    console.log(option);
    try{
        const response = await fetch('/review/update',option);
        const data = await response.json();
        console.log(data);
        if(data == true){
            alert('수정성공!');
            location.href=`/company/find.jsp?cno=${cno}`
        }else{
            alert('수정실패!');
        }// if end
    }catch(e){ console.log(e); }
}// func end

// [7] 리뷰 삭제
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

//============================ 네이버지도 API JS ============================\\
const naverMap = async ( ) => {
    const option = { method : "GET" };
    const response = await fetch ( `/map/getLatLng?caddress=${companyData.caddress}`, option );
    const data = await response.json();     console.log( data );

    var latlng = new naver.maps.LatLng( data.lat, data.lng );

    var utmk = naver.maps.TransCoord.fromLatLngToUTMK(latlng);
    var tm128 = naver.maps.TransCoord.fromUTMKToTM128(utmk);
    var naverCoord = naver.maps.TransCoord.fromTM128ToNaver(tm128);
    console.log( utmk );
    console.log( tm128 );
    console.log( naverCoord );

    // 정육점 위치
    var company = new naver.maps.LatLng( data.lat, data.lng ),
        map = new naver.maps.Map('map', {
            center: company.destinationPoint(0, 500),
            zoom: 15
        }),
        marker = new naver.maps.Marker({
            map: map,
            position: company
        });

    let cImgUrl = '/upload/'+encodeURIComponent(companyData.cimg);
    if( companyData.cimg == null ){
        cImgUrl = 'https://placehold.co/50x50';
    } // if end

    var contentString = `<div>
                            <h3>${companyData.cname}</h3>
                            <p>
                                ${companyData.caddress} <br>
                                <img src="${cImgUrl}">
                            </p>
                        </div>`;

    var infowindow = new naver.maps.InfoWindow({
        content: contentString
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

// [8] 길찾기 QR Code 출력
const buildQR = async() => {
    const cookie = document.cookie;
    console.log(cookie);
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
        console.log(blob);
        let html = `<img src="${imgUrl}" alt="QR Code"/>`;
        qrbox.innerHTML = html;
    }catch(e){ console.log(e); }
}// func end

// [9] 멤버쉽 신청
const addPlan = async() => {
    try{
        const response = await fetch("/plan/add");
        const data = await response.json();
        if(data == 0){
            alert('신청 실패');
        }else{
            alert('신청 완료');
            getCnoEnddate();
        }// if end
    }catch(e){ console.log(e); }
}// func end

// [10] 멤버쉽 남은일 출력
const getCnoEnddate = async() => {
    const endDate = document.querySelector('.endDate');
    let html = "";
    try{
        const response = await fetch("/plan/enddate");
        const data = await response.json();
        if(data >= 0){
            html += `<span>남은일 -<span class="endDate">${data}</span>일</span>`
        }// if end
        endDate.innerHTML = html;
    }catch(e){ console.log(e); }
}// func end
getCnoEnddate();

// [11] 정육점별 재고목록 조회
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

