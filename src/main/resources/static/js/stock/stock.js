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

// [3] 재고조회 기능
const getStock = async ( ) => {
    console.log('getStock func exe');
    // 1. fetch
    const option = { method : "GET" };
    const response = await fetch( "/stock/get", option );
    const data = await response.json();         console.log( data );
    // 2. where
    const stockTbody = document.querySelector('.stockTbody');
    // 3. what
    let html = ``;
    data.forEach( (stock) => {
        html += `<tr>
                    <td>${stock.sno}</td>
                    <td>${stock.pname}</td>
                    <td>${stock.sprice}</td>
                    <td>${stock.sdate}</td>
                    <td>
                        <button type="button" onclick="updateStock()"> 수정 </button>
                        <button type="button" onclick="deleteStock()"> 삭제 </button>
                    </td>
                 </tr>`
    })
    // 4. print
    stockTbody.innerHTML = html;

} // func end
getStock();

// [4] 재고수정
const updateStock = async ( ) => {
    console.log('updateStock func exe');
} // func end

// [5] 재고삭제
const deleteStock = async ( ) => {
    console.log('deleteStock func exe');
} // func end