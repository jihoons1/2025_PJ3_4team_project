//=============================================== 쿼리스트링 ================================================\\
const params = new URL(location.href).searchParams;
const page = params.get('page') || 1;

//=============================================== 일반 로직 ================================================\\
// 1. 제품 전체 조회
const getProduct = async() => {
    const productbody = document.querySelector('#productTbody');
    let html = "";
    try{
        const response = await fetch(`/product/get?page=${page}`);
        const data = await response.json();
        console.log(data);
        data.data.forEach((pro) => {
            let imgUrl = '/upload/product/'+pro.pimg;
            if(pro.pimg == null){
                imgUrl = 'https://placehold.co/60x60';
            }// if end
            if(pro.cno == 20001){
                pro.cno = '돼지';
            }else if(pro.cno == 20002){
                pro.cno = '소';
            }else if(pro.cno == 20003){
                pro.cno = '양';
            }else if(pro.cno == 20004){
                pro.cno = '오리';
            }else if(pro.cno == 20005){
                pro.cno = '닭';
            }
            html += `<tr>
                        <td><img src="${imgUrl}" style="width: 75px; height: 75px;"/></td>
                        <td>${pro.cno}</td>
                        <td>${pro.pname}</td>                        
                    </tr>`
        })// for end
        productbody.innerHTML = html;
        viewPageButton(data);
    }catch(e){ console.log(e); }
}// func end
getProduct();

// 2. 페이징 버튼 출력 함수 
const viewPageButton = async ( data ) => {
    console.log('viewPageButton exe');
    // 백엔드로 부터 받은 pageDto{} <--->  data{}
    let currentPage = parseInt( data.currentPage ); // parseInt(자료) : 자료를 int 타입으로 변환
    let totalPage = data.totalPage;
    let startBtn = data.startBtn;
    let endBtn = data.endBtn;    

    const pageBtnBox = document.querySelector('.pageBtnBox');
    let html = "";
    // ***************************** 이전버튼 : 현재페이지가 1이하이면 1고정 *********************************** //
    if(currentPage > 1){
        html += `<li><a href="product.jsp?page=${currentPage <= 1 ? 1 : currentPage-1}"> 이전 </a></li>`
    }// if end    
    // ***************************** 페이지버튼 : startBtn 부터 endBtn까지 1씩 증가 *********************************** //
    for(let i = startBtn; i <= endBtn; i++){
        html += `<li> <a href="product.jsp?page=${i}" style="${ i == currentPage ? 'color:red' : '' }"> ${i} </a> </li>`
    }// for end
    // ***************************** 다음버튼 : 만약에 다음페이지가 전체페이지수보다 커지면 전체페이지수로 고정 *********************************** //
    if(currentPage < totalPage){
        html += `<li><a href="product.jsp?page=${currentPage+1 >= totalPage ? totalPage : currentPage+1}"> 다음 </a></li>`
    }// if end    
    pageBtnBox.innerHTML = html;
} // func end