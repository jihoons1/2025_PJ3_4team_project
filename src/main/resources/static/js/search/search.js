//=============================================== 쿼리스트링 ================================================\\
const params = new URL(location.href).searchParams;
const cno = params.get('cno');
const page = params.get('page') || 1;
const key = "pname";
const orderr = params.get('order') || "order";
const keyword = params.get('keyword');

//=============================================== 일반 로직 ================================================\\
// 1. 검색기능
const getCompanySearch = async() => {
    const searchtbody = document.querySelector('#searchTbody');    
    let html = "";
    try{
        const response = await fetch(`/company/get?page=${page}&order=${orderr}&key=${key}&keyword=${keyword}`);
        const data = await response.json();
        data.data.forEach((ser) => {
            let img = '/img/company/' + ser.cimg;
            if(ser.cimg == null){
                img = '/img/company/default.png';
            }// if end
            html += `<tr>
                        <td><a href="/company/find.jsp?cno=${ser.cno}"><img src="${img}"/></a></td>
                        <td><a href="/company/find.jsp?cno=${ser.cno}" style="font-weight: bold; font-size: 16px;">${ser.cname}</a></td>
                        <td><a href="/company/find.jsp?cno=${ser.cno}">${ser.caddress}</a></td>
                        <td>${ser.distance.toFixed(2)}km</td>
                        <td>${ser.pname}</td>
                        <td>${ser.sprice}원</td>
                        <td>${ser.views}</td>
                        <td>${ser.rrank}점</td>
                    </tr>`;
        })// for end
        searchtbody.innerHTML = html;
        if(orderr != null){
            document.querySelector('.order').value = orderr;
        }// if end        
        viewPageButton(data);
    }catch(e){ console.log(e); }
}// func end
getCompanySearch();

// 2. 페이지이동
const searchParam = async() => {
    const orders = document.querySelector('.order').value;
    location.href = `/search/search.jsp?page=${page}&order=${orders}&key=${key}&keyword=${keyword}`
    getCompanySearch();
}// func end

// 3. 페이징 버튼 출력 함수 
const viewPageButton = async ( data ) => {   

    let currentPage = parseInt( data.currentPage ); 
    let totalPage = data.totalPage;
    let startBtn = data.startBtn;
    let endBtn = data.endBtn;       
    

    // *********** 페이징 처리시 검색 유지 **************** // 
    const searchURL = `&order=${orderr}&key=${key}&keyword=${keyword}`;

    const pageBtnBox = document.querySelector('.pageBtnBox');
    let html = "";
    // 이전버튼 //
    if(currentPage > 1){
        html += `<li><a href="search.jsp?page=${currentPage <= 1 ? 1 : currentPage-1}${searchURL}"> 이전 </a></li>`
    }// if end    
    // 페이지버튼 //
    for(let i = startBtn; i <= endBtn; i++){
        html += `<li> <a href="search.jsp?page=${i}${searchURL}" style="${ i == currentPage ? 'color:red' : '' }"> ${i} </a> </li>`
    }// for end
    // 다음버튼 //
    if(currentPage < totalPage){
        html += `<li><a href="search.jsp?page=${currentPage+1 >= totalPage ? totalPage : currentPage+1}${searchURL}"> 다음 </a></li>`
    }// if end    
    pageBtnBox.innerHTML = html;
} // func end

// 4. 알림 등록기능
const addNotice = async ( ) => {
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

// 5. 제품 전체조회
const getProduct = async ( ) => {
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