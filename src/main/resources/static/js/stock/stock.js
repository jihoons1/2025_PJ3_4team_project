console.log('stock.js open');



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
    } else {
        alert('재고등록 실패!\n다시 입력해주세요.');
    }
} // func end