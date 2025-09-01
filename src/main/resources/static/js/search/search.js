console.log('search.js 확인');

const params = new URL(location.href).searchParams;
const cno = params.get('cno');
const page = params.get('page') || 1;
const key = "pname";
const orderr = params.get('order');
const keyword = params.get('keyword');

// [1] 검색기능
const getCompanySearch = async() => {
    const searchtbody = document.querySelector('#searchTbody');    
    let html = "";
    try{
        const response = await fetch(`/company/get?page=${page}&order=${orderr}&key=${key}&keyword=${keyword}`);
        const data = await response.json();
        data.data.forEach((ser) => {
            let img = '/upload/company'+ser.cimg;
            if(ser.cimg == null){
                img = 'https://placehold.co/50x50';
            }// if end
            html += `<tr>
                        <td><img src="${img}"/></td>
                        <td>${ser.cname}</td>
                        <td>${ser.caddress}</td>
                        <td>${ser.distance.toFixed(2)}km</td>
                        <td>${ser.pname}</td>
                        <td>${ser.sprice}원</td>
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

// 페이지이동
const searchParam = async() => {
    const orders = document.querySelector('.order').value;
    location.href = `/search/search.jsp?page=${page}&order=${orders}&key=${key}&keyword=${keyword}`
    getCompanySearch();
}// func end

// 페이징 버튼 출력 함수 
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