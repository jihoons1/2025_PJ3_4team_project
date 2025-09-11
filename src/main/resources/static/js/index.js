//=============================================== 일반 로직 ================================================\\
// 1. 메인페이지 노출시킬 회사정보 가져오기
const getPlan = async() => {
    const carouselInner = document.querySelector('.carousel-inner');
    let html = "";
    try{
        const response = await fetch('/company/getAll');
        const data = await response.json();     
        for(let i = 0; i < data.length; i++){
            let imgURL;
            if( data[i].cimg == null || data[i].cimg == "" ){
                imgURL = 'https://placehold.co/100x100';
            } else {
                imgURL = `/img/company/` + data[i].cimg;
            }// if end
            let str1 = String(data[i].rrank);
            let str = str1.slice(0,1);
            let rankImg = "";
            if(str == '5'){
                rankImg = "/upload/star/별점5.png";
            }else if(str == '4'){
                rankImg = "/upload/star/별점4.png";
            }else if(str == '3'){
                rankImg = "/upload/star/별점3.png";
            }else if(str == '2'){
                rankImg = "/upload/star/별점2.png";
            }else if(str == '1'){
                rankImg = "/upload/star/별점1.png";
            }else if(str == '0'){
                rankImg = "/upload/star/별점0.png";
            }
            if(i == 0){                
                html += `<div class="carousel-item active">
                            <h3>
                                <a href="/company/find.jsp?cno=${data[i].cno}">
                                    ${data[i].cname}
                                </a>
                            </h3>
                            <div style="display: flex; justify-content: space-around; margin-top: 30px">
                                <div class="caimgBox" style="width: 45%;">
                                    <img src="${imgURL}" class="d-block w-40" alt="...">
                                </div>
                                <div style="text-align: left; width: 45%;"><br/>
                                    <span><img style="width:190px; height: 40px;" src=${rankImg}/></span>
                                                                        <span style="padding-left:10px; font-size: 20px;">조회수  ${data[i].views} </span><br/><br/>
                                    <div> ${data[i].caddress} </div>
                                                                        <div class="caBtnBox${data[i].cno}" style="padding-left: 20px; margin-top: 30px;">
                                                                            <button type="button" class="btn btn-primary" style="background-color: #143889;" data-bs-toggle="modal" data-bs-target="#staticBackdrop${data[i].cno}" onclick="buildQR(${data[i].cno})">
                                                                                길찾기 QR
                                                                            </button>
                                                                            <button type="button" style="background-color: #143889;" onclick="printBtn(${data[i].cno})" class="btn btn-primary">
                                                                                문의하기
                                        </button>
                                    </div>
                                </div>
                            </div>
                            <div class="modal fade" id="staticBackdrop${data[i].cno}" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h1 class="modal-title fs-5" id="staticBackdropLabel">QR Code</h1>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                        </div>
                                        <div class="modal-body">
                                            <div class="qrBox${data[i].cno}" >

                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"> 닫기 </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>`                                         
                
            }else{
                html += `<div class="carousel-item">
                            <h3>
                                <a href="/company/find.jsp?cno=${data[i].cno}">
                                    ${data[i].cname}
                                </a>
                            </h3>
                            <div style="display: flex; justify-content: space-around; margin-top: 30px">
                                <div class="caimgBox" style="width: 45%;">
                                    <img src="${imgURL}" class="d-block w-40" alt="...">
                                </div>
                                <div style="text-align: left; width: 45%;"><br/>
                                    <span>평점 : ${data[i].rrank}점</span><br/><br/>
                                    <span>조회수 : ${data[i].views} </span><br/><br/>
                                    <span>주소 : ${data[i].caddress} </span>
                                    <div style="padding-left: 20px; margin-top: 30px;">
                                        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#staticBackdrop${data[i].cno}" onclick="buildQR(${data[i].cno})">
                                            길찾기 QR Code [거주지 기준]
                                        </button>
                                    </div>
                                </div>
                            </div>
                            <!-- QR Code 출력 staticBackdrop10 -->
                            <div class="modal fade" id="staticBackdrop${data[i].cno}" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h1 class="modal-title fs-5" id="staticBackdropLabel">QR Code</h1>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                        </div>
                                        <div class="modal-body">
                                            <div class="qrBox${data[i].cno}">

                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"> 닫기 </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>`;               
            }// if end
        }// for end
        carouselInner.innerHTML = html;
    }catch(e){ console.log(e); }
}// func end
getPlan();

// 2. 채팅방 이동
const printBtn = async ( cno ) => {
    try {
        // 1. fetch
        const response = await fetch( "/member/get" );
        const data = await response.json();
        const response1 = await fetch( `/company/getMno?cno=${cno}` );
        const sidemno = await response1.json();
        // 2. result
        if ( sidemno == data.mno ){
            // 3. 해당 정육점이 내 정육점이라면, 방으로 안 들어가고 그냥 전체채팅방으로
            location.href = `/chatting/chatting.jsp?mno=${data.mno}`;
        } else {
            // 4. 해당 정육점이 내 정육점이 아니라면, 해당 정육점과의 채팅방으로 이동
            location.href = `/chatting/chatting.jsp?mno=${data.mno}&cno=${sidemno}&room=${data.mno}_${sidemno}`;
        } // if end
    } catch ( error ) {
        // 5. 비로그인 상태라면, 전체채팅방으로 이동
        publicRoom();
    } // try-catch end
} // func end

// 3. 전체채팅방으로 이동
const publicRoom = async ( ) => {
    if ( confirm('비로그인 상태이므로, 전체채팅방으로 이동합니다.') ){
        location.href='/chatting/chatting.jsp?room=0'
    } // if end
} // func end

// 2. 길찾기 QR Code 출력
const buildQR = async(cno) => {
    const qrbox = document.querySelector(`.qrBox${cno}`);
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

// 3. 메인페이지 노출시킬 정육점 재고목록
const getPlanStock = async() => {
    const stockInner = document.querySelectorAll('.stock-inner');
    let html1 = "";
    const response = await fetch("/plan/stock");
    const data = await response.json();
    for(let i = 0; i < data.length; i++){
        let st = data[i];
        for(let a = 0; a < st.length; a++){            
            html1 += `<h3> 재고 목록 </h3>
            <span>${st[a].pname}</span> <span>${st[a].sprice}원 (100g)</span>`
        }
    }// for end
    stockInner.innerHTML = html1;
}// func end

// 4. 메인페이지 지도 클러스터
const initMap = async () => {
    const response = await fetch("/map/latlngList");
    const data = await response.json();

    // 지도는 한 번만 생성
    map = new naver.maps.Map('map', {
        center: new naver.maps.LatLng( 37.4904807, 126.7234847),
        zoom: 13
    });
    var markers = [];

    // 정육점 위치 (latlngList 기반)
    for (let i = 0; i < data.length; i++) {
        const company = new naver.maps.LatLng(data[i].lat, data[i].lng);

        const marker = new naver.maps.Marker({
            map: map,
            position: company
        });
        const response2 = await fetch(`/stock/get/find?cno=${data[i].cno}`);
        const data2 = await response2.json();        
        naver.maps.Event.addListener(marker, "click", () => {
            let html = "";
            let rimgUrl;
            if(data[i].cimg == null || data[i].cimg == ""){
                rimgUrl = 'https://placehold.co/50x50';
            } else {
                rimgUrl = '/img/company/' + data[i].cimg;
            } // if end
            const sidebar = document.querySelector('#sidebar');           
            if(data2.length == 0 || data2 == null){
                html += `<div class="sidebar-content-wrapper">
                            <div class="company-info-card">
                                <img src="${rimgUrl}"/>
                                <h3><a href='/company/find.jsp?cno=${data[i].cno}'>${data[i].cname}</a></h3>
                                <div class="address">${data[i].caddress}</div>
                            </div>`
            }else{
                const lastDate = new Date(
                    Math.max(...data2.map(st => new Date(st.sdate)))
                ).toISOString().slice(0, 10);
                html += `<div class="sidebar-content-wrapper">
                            <div class="company-info-card">
                                <img src="${rimgUrl}"/>
                                <h3><a href='/company/find.jsp?cno=${data[i].cno}'>${data[i].cname}</a></h3>
                                <div class="address">${data[i].caddress}</div>
                            </div>
                            <div class="stock-info-card">
                                <div class="stock-header">
                                    <h5>재고 목록</h5>
                                    <span class="last-updated">(${lastDate} 기준)</span>
                                </div>
                                <ul>`                
                data2.forEach( (st) => {
                    const day =  st.sdate.slice(0,10);
                    html += `<li>
                                <span class="stock-name">${st.pname}(100g당)</span>
                                <span class="dot-line"></span>
                                <span class="stock-price">${st.sprice.toLocaleString()}원</span>
                             </li>`
                })// for end
                html += `</ul>
                    </div>`;
            } // if end
            sidebar.innerHTML = html;
        });
        markers.push(marker);
    }// for end
    var htmlMarker1 = {
        content: '<div style="cursor:pointer;width:40px;height:40px;line-height:42px;font-size:10px;color:white;text-align:center;font-weight:bold;background:url(/img/cluster-marker-1.png);background-size:contain;"></div>',
        size: N.Size(40, 40),
        anchor: N.Point(20, 20)
    },
    htmlMarker2 = {
        content: '<div style="cursor:pointer;width:40px;height:40px;line-height:42px;font-size:10px;color:white;text-align:center;font-weight:bold;background:url(/img/cluster-marker-2.png);background-size:contain;"></div>',
        size: N.Size(40, 40),
        anchor: N.Point(20, 20)
    },
    htmlMarker3 = {
        content: '<div style="cursor:pointer;width:40px;height:40px;line-height:42px;font-size:10px;color:white;text-align:center;font-weight:bold;background:url(/img/cluster-marker-3.png);background-size:contain;"></div>',
        size: N.Size(40, 40),
        anchor: N.Point(20, 20)
    },
    htmlMarker4 = {
        content: '<div style="cursor:pointer;width:40px;height:40px;line-height:42px;font-size:10px;color:white;text-align:center;font-weight:bold;background:url(/img/cluster-marker-4.png);background-size:contain;"></div>',
        size: N.Size(40, 40),
        anchor: N.Point(20, 20)
    },
    htmlMarker5 = {
        content: '<div style="cursor:pointer;width:40px;height:40px;line-height:42px;font-size:10px;color:white;text-align:center;font-weight:bold;background:url(/img/cluster-marker-5.png);background-size:contain;"></div>',
        size: N.Size(40, 40),
        anchor: N.Point(20, 20)
    };

    
    var markerClustering = new MarkerClustering({
        minClusterSize: 2,
        maxZoom: 16,
        map: map,
        markers: markers,
        disableClickZoom: false,
        gridSize: 120,
        icons: [htmlMarker1, htmlMarker2, htmlMarker3, htmlMarker4, htmlMarker5],
        indexGenerator: [10, 100, 200, 500, 1000],
        stylingFunction: function(clusterMarker, count) {
            $(clusterMarker.getElement()).find('div:first-child').text(count);
        }
    });
}// func end
initMap();

// 5. 멤버쉽 가입한 정육점의 배너 노출
const printPlanBanner = async ( ) => {
    try {
        // 1. fetch
        const response = await fetch( "/plan/get" );
        const data = await response.json();
        // 2. where
        const bannerBox_top = document.querySelector('.banner_top');
        const bannerBox_bot = document.querySelector('.banner_bot');
        // 3. what
        // 난수를 통해, 무작위 배너 노출
        let randomNum = Math.round( Math.random() * ( data.length - 1 ) );
        
        let banner = data[randomNum].banner;
        let cno = data[randomNum].cno;
        if ( banner == null ){
            // 등록한 배너가 없으면, 기본 배너 노출
            banner = `/img/banner/adBanner.png`;
        } else {
            banner = `/upload/plan/${banner}`;
        } // if end
        let html = `<a href="/company/find.jsp?cno=${cno}">
                        <img class="bimg" src="${banner}" alt="배너 이미지"
                            style="display: block !important;
                            vertical-align: baseline !important;
                            width: 100%;
                            height: 100%;
                            object-fit: cover;">
                    </a>`
        // 4. print
        bannerBox_top.innerHTML = html;
        bannerBox_bot.innerHTML = html;
        // 5초마다 배너 노출 재실행 -> 배너 변경
        setTimeout( printPlanBanner, 5000 );
    } catch ( error ){
        console.log( error );
    } // try-catch end
} // func end
printPlanBanner();