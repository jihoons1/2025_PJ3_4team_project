console.log('stock.js open');

// 쿼리스트링에서 정보 가져오기
const queryString = new URL( location.href ).searchParams;
const cno = queryString.get("cno");
let StockData;

// [*] 입력값 유효성 검사
const checkList = [ false ];
// 숫자만 유효성 검사 - 재고등록
const numCheck = (  ) => {
    console.log('numCheck func exe');
    const sprice = document.querySelector('.sprice').value;
    const regExp = /^[0-9]+$/;
    console.log( regExp.test( sprice ) );
    if ( !regExp.test( sprice ) ){
        document.querySelector('.numCheck').innerHTML = '숫자만 입력해주세요.'
    } else {
        document.querySelector('.numCheck').innerHTML = ''
        checkList[0] = true;
    } // if end
} // func end
// 숫자만 유효성 검사 - 재고수정
const updateCheck = (  ) => {
    console.log('updateCheck func exe');
    const sprice = document.querySelector('.spriceBox').value;
    const regExp = /^[0-9]+$/;
    console.log( regExp.test( sprice ) );
    if ( !regExp.test( sprice ) ){
        document.querySelector('.updateSprice').innerHTML = '숫자만 입력해주세요.'
    } else {
        document.querySelector('.updateSprice').innerHTML = ''
        checkList[0] = true;
    } // if end
} // func end


// [1] 카테고리별 상품 출력
const getCnoProduct = async ( ) => {
    console.log('getCnoProduct func exe');
    // 1. Input value
    const cno = document.querySelector('.cno').value;
    // 2. fetch
    const option = { method : "GET" };
    const response = await fetch( `/product/getCno?cno=${cno}`, option );
    const data = await response.json();         console.log( data );
    // 3. where
    const productBox = document.querySelector('.product');
    // 4. what
    let html = `<option disabled selected>고기 선택</option>`;
    data.forEach( (product) => {
        html += `<option value="${product.pno}">${product.pname}</option>`
    });
    productBox.innerHTML = html;
} // func end

// [2] 재고등록 기능
const addStock = async ( ) => {
    console.log('addStock func exe');
    if ( checkList.includes(false) ){
        alert('올바른 정보를 입력해주세요.');
        return;
    } // if end
    // 1. Input value
    const cno = document.querySelector('.cno').value;
    const pno = document.querySelector('.product').value;
    const sprice = document.querySelector('.sprice').value;
    // 2. obj
    const obj = { cno, pno, sprice };
    // 3. fetch
    const option = {
        method : "POST",
        headers : { "Content-Type" : "application/json" },
        body : JSON.stringify( obj )
    } // option end
    const response = await fetch( "/stock/add", option );
    const data = await response.json();
    // 4. result
    if ( data > 0 ){
        alert('재고등록 성공!');
        location.reload();
    } else if ( data == -1 ){
        alert('포인트 잔액이 부족합니다.');
    } else {
        alert('재고등록 실패!\n다시 입력해주세요.');
    } // if end
} // func end

// [3] 재고조회 기능
const getStock = async ( ) => {
    console.log('getStock func exe');
    // 1. fetch
    const option = { method : "GET" };
    const response = await fetch( "/stock/get", option );
    const data = await response.json();         console.log( data );
    StockData = data;
    // 2. where
    const stockTbody = document.querySelector('.stockTbody');
    // 3. what
    let html = ``;
    data.forEach( (stock) => {
        // 함수에 매개변수를 넣은 이유 : sno를 활용하여, 연쇄적으로 함수에서 사용하기 위해서
        html += `<tr>
                    <td>${stock.sno}</td>
                    <td>${stock.pname}</td>
                    <td>${stock.sprice}</td>
                    <td>${stock.sdate}</td>
                    <td>
                        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#staticBackdrop1" onclick="updateButton(${stock.sno})">
                            재고수정
                        </button>
                        <button type="button" onclick="deleteStock(${stock.sno})"> 삭제 </button>
                    </td>
                 </tr>`
    })
    // 4. print
    stockTbody.innerHTML = html;
} // func end
getStock();

// [4] 재고수정 버튼
const updateButton = async ( sno ) => {
    console.log('updateButton func exe');
    // 1. where
    const buttons = document.querySelector(`.modal-footer`);
    const sprice = document.querySelector(`.spriceBox`);
    const pnameBox = document.querySelector('.pnameBox');
    const snoBox = document.querySelector('.snoBox');
    // 2. what
    let oldPrice;
    let pno;
    let pname;
    StockData.forEach( (stock) => {
        if ( stock.sno == sno ){
            oldPrice = stock.sprice;
            pno = stock.pno;
            pname = stock.pname;
        } // if end
    }) // for end
    let button = `<button type="button" class="btn btn-secondary" data-bs-dismiss="modal"> 닫기 </button>
                  <button type="button" class="btn btn-primary" onclick="updateStock(${sno}, ${pno})"> 수정완료 </button>`;
    // 3. print
    snoBox.innerHTML = sno;
    pnameBox.innerHTML = pname;
    buttons.innerHTML = button;
    sprice.value = oldPrice;
} // func end

// [5] 재고수정
const updateStock = async ( sno, pno ) => {
    console.log('updateStock func exe');
    console.log( pno )
    // 입력값 체크
    if ( checkList.includes(false) ){
        alert('올바른 정보를 입력해주세요.');
        return;
    } // if end
    // 1. Input value
    const sprice = document.querySelector(`.spriceBox`).value;
    // 2. obj
    const obj = { sno, sprice, pno };
    // 3. fetch
    const option = {
        method : "PUT",
        headers : { "Content-Type" : "application/json" },
        body : JSON.stringify( obj )
    } // option end
    const response = await fetch( "/stock/update", option );
    const data = await response.json();
    // 4. result
    if ( data == true ){
        alert('재고수정 성공!');
        location.reload();
    } else {
        alert('재고수정 실패!\n다시 입력해주세요.');
    } // if end
} // func end

// [6] 재고삭제
const deleteStock = async ( sno ) => {
    console.log('deleteStock func exe');
    // 1. fetch
    const option = { method : "DELETE" };
    const response = await fetch( `/stock/delete?cno=${cno}&sno=${sno}`, option );
    const data = await response.json();
    // 2. result
    if ( data == true ){
        alert('재고삭제 성공!');
        location.reload();
    } else {
        alert('재고삭제 실패!');
    } // if end
} // func end