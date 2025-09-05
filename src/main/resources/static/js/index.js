console.log('index.js exe');

// [1] 메인페이지 노출시킬 회사정보 가져오기
const getPlan = async() => {
    const carouselInner = document.querySelector('.carousel-inner');
    let html = "";
    try{
        const response = await fetch('/plan/get');
        const data = await response.json();
        console.log(data);
        const response2 = await fetch('/plan/stock');
        const data2 = await response2.json();
        console.log(data2[0]);
        for(let i = 0; i < data.length; i++){
            if(data[i].cimg == null || data[i].cimg == ""){
                data[i].cimg = 'https://placehold.co/100x100';
            }// if end
            if(i == 0){                
                html += `<div class="carousel-item active">
                            <div>
                                <h3>${data[i].cname}</h3><span>평점 : ${data[i].rrank}점</span>
                                <img src="${data[i].cimg}" class="d-block w-40" alt="...">
                                <span>주소 : ${data[i].caddress} </span><br/>
                                <div>                                    
                                    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#staticBackdrop${data[i].cno}" onclick="buildQR(${data[i].cno})">
                                        길찾기 QR Code [거주지 기준]
                                    </button>
                                </div>                                
                                <div class="modal fade" id="staticBackdrop${data[i].cno}" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h1 class="modal-title fs-5" id="staticBackdropLabel">QR Code</h1>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                            </div>
                                            <div class="modal-body">
                                                <div class="qrBox">

                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"> 닫기 </button>                            
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>`                                         
                
            }else{
                html += `<div class="carousel-item">
                            <div>
                                <h3>${data[i].cname}</h3><span>평점 : ${data[i].rrank}점</span>
                                <img src="${data[i].cimg}" class="d-block w-40" alt="...">
                                <span>주소 : ${data[i].caddress} </span><br/>
                                <div>
                                    <!-- Button trigger modal -->
                                    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#staticBackdrop${data[i].cno}" onclick="buildQR(${data[i].cno})">
                                        길찾기 QR Code [거주지 기준]
                                    </button>
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
                                                <div class="qrBox">

                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"> 닫기 </button>                            
                                            </div>
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

// [2] 길찾기 QR Code 출력
const buildQR = async(cno) => {
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

// [3] 메인페이지 노출시킬 정육점 재고목록
const getPlanStock = async() => {
    const stockInner = document.querySelectorAll('.stock-inner');
    let html1 = "";
    const response = await fetch("/plan/stock");
    const data = await response.json();
    console.log(data);
    for(let i = 0; i < data.length; i++){
        let st = data[i];
        for(let a = 0; a < st.length; a++){            
            html1 += `<h3> 재고 목록 </h3>
            <span>${st[a].pname}</span> <span>${st[a].sprice}원 (100g)</span>`
        }
    }// for end
    stockInner.innerHTML = html1;
}// func end

