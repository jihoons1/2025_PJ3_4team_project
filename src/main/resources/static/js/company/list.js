console.log('list.js check');

const params = new URL(location.href).searchParams;
const page = params.get('page') || 1; console.log(page); // 조건 || false , 만약에 page가 존재하지않으면 1
const key = params.get('key') || ''; // 만약에 key가 존재하지않으면 공백
const keyword = params.get('keyword') || '';


// 정육점 전체조회
const getAllCompany = async() => {
    const listTbody = document.querySelector('#listTbody');
    const order = document.querySelector('.order').value;
    let html = "";
    try{
    const response = await fetch(`/company/get?page=${page}&order=${order}`);
    const data = await response.json();     console.log(data);
    data.data.forEach((com) => {
        html += `<tr>
                    <td><img src=${com.cimg}/></td>
                    <td><a href="/company/find.jsp?cno=${com.cno}">${com.cname}</a></td>
                    <td>${com.caddress}</td>
                    <td>${com.rrank}</td>
                </tr>`;
    })// for end        
    console.log(html);
    listTbody.innerHTML = html;
    viewPageButton(data);
    }catch(e){ console.log(e); }
}// func end
getAllCompany();

// 페이징 버튼 출력 함수 
const viewPageButton = async ( data ) => {

    // 백엔드로 부터 받은 pageDto{} <--->  data{}
    let currentPage = parseInt( data.currentPage ); // parseInt(자료) : 자료를 int 타입으로 변환
    let totalPage = data.totalPage;
    let startBtn = data.startBtn;
    let endBtn = data.endBtn;
    const order = document.querySelector('.order').value;

    // *********** 페이징 처리시 검색 유지 **************** // 
    const searchURL = `&order=${order}`;

    const pageBtnBox = document.querySelector('.pageBtnBox');
    let html = "";
    // ***************************** 이전버튼 : 현재페이지가 1이하이면 1고정 *********************************** //
    if(currentPage > 1){
        html += `<li><a href="list.jsp?page=${currentPage <= 1 ? 1 : currentPage-1}${searchURL}"> 이전 </a></li>`
    }// if end    
    // ***************************** 페이지버튼 : startBtn 부터 endBtn까지 1씩 증가 *********************************** //
    for(let i = startBtn; i <= endBtn; i++){
        html += `<li> <a href="list.jsp?page=${i}${searchURL}" style="${ i == currentPage ? 'color:red' : '' }"> ${i} </a> </li>`
    }// for end
    // ***************************** 다음버튼 : 만약에 다음페이지가 전체페이지수보다 커지면 전체페이지수로 고정 *********************************** //
    if(currentPage < totalPage){
        html += `<li><a href="list.jsp?page=${currentPage+1 >= totalPage ? totalPage : currentPage+1}${searchURL}"> 다음 </a></li>`
    }// if end    
    pageBtnBox.innerHTML = html;
} // func end