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
            getReview();
        }else{
            alert('리뷰 등록 실패!');
        }// if end
    }catch(e){ console.log(e); }
}// func end

// 회사별 리뷰 목록 조회
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

// 페이징 버튼 출력 함수 
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

// 리뷰번호 리뷰 내용 조회
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

// 리뷰 수정 기능
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


//============================ 네이버지도 API JS ============================\\
const naverMap = ( ) => {
    var company = new naver.maps.LatLng(37.5666805, 126.9784147),
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