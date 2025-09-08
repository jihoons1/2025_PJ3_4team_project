
const params = new URL(location.href).searchParams;
const page = params.get('page') || 1;  // 조건 || false , 만약에 page가 존재하지않으면 1
const orderr = params.get('order') || "order";


// 정육점 전체조회
const getAllCompany = async() => {
    const listTbody = document.querySelector('#listTbody');    
    let html = "";
    try{
        const response = await fetch(`/company/get?page=${page}&order=${orderr}`);
        const data = await response.json();    
        data.data.forEach((com) => {
            let imgUrl = '/upload/company/'+com.cimg;
            if(data.cimg == null){
                imgUrl = 'https://placehold.co/50x50';
            }// if end
            html += `<tr>
                        <td><img src=${imgUrl}/></td>
                        <td><a href="/company/find.jsp?cno=${com.cno}">${com.cname}</a></td>
                        <td>${com.caddress}</td>
                        <td>${com.rrank}</td>
                    </tr>`;
    })// for end        
    listTbody.innerHTML = html;
    document.querySelector('.order').value = orderr;
    viewPageButton(data);
    }catch(e){ console.log(e); }
}// func end
getAllCompany();

// 페이지이동
const searchParams = async() => {
    const orders = document.querySelector('.order').value;
    location.href = `/company/list.jsp?page=${page}&order=${orders}`
    document.querySelector('.order').value = orders;
    getAllCompany();
}// func end

// 페이징 버튼 출력 함수 
const viewPageButton = async ( data ) => {

    // 백엔드로 부터 받은 pageDto{} <--->  data{}
    let currentPage = parseInt( data.currentPage ); // parseInt(자료) : 자료를 int 타입으로 변환
    let totalPage = data.totalPage;
    let startBtn = data.startBtn;
    let endBtn = data.endBtn;    

    // *********** 페이징 처리시 검색 유지 **************** // 
    const searchURL = `&order=${orderr}`;

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