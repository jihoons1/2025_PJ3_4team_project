console.log('find.js check');

const params = new URL(location.href).searchParams;
const cno = params.get('cno');
const page = params.get('page') || 1;

// 정육점 개별조회
const findCompany = async() => {
    const findTbody = document.querySelector('#findTbody');
    let html = "";
    try{
        const response = await fetch(`/company/find?cno=${cno}`);
        const data = await response.json(); console.log(data);
        let imgUrl = '/upload/'+data.cimg;
        if(data.cimg == null){
            imgUrl = 'https://placehold.co/50x50';
        }// if end
        html += `<tr>
                    <td><img src=${imgUrl}/></td>
                    <td>${data.cname}</td>
                    <td>${data.caddress}</td>
                    <td>${data.rrank}</td>
                </tr>`;
        findTbody.innerHTML = html;
    }catch(e){ console.log(e); }
}// func end
findCompany();

// 리뷰 등록html 불러오기
const addReviewBox = async() => {
    const reviewAddBox = document.querySelector('.reviewAddBox');
    let html = `<textarea name="rcontent"></textarea>
                <select name="rrank">
                    <option value="0">평점</option>
                    <option value="5">5</option>
                    <option value="4">4</option>
                    <option value="3">3</option>
                    <option value="2">2</option>
                    <option value="1">1</option>
                </select>
                <input type="file" multiple name="uploads"/>
                <button type="button" onclick="addReview()">등록</button>`;
    reviewAddBox.innerHTML = html;
}// func end

// 리뷰 등록 기능
const addReview = async() => {
    const reviewAddBox = document.querySelector('.reviewAddBox');
    const productForm = new FormData(reviewAddBox);
    productForm.append('cno' , cno);
    const option = {
        method : "POST" , 
        body : productForm
    }// option end
    try{
        const response = await fetch('/review/add',option);
        const data = await response.json();
        if(data == true){
            alert("리뷰가 등록되었습니다.");
        }else{
            alert('리뷰 등록 실패!');
        }// if end
    }catch(e){ console.log(e); }
}// func end

// 회사별 리뷰 목록 조회
const getReview = async() => {
    const reviewtbody = document.querySelector('.reviewTbody');    
    let html = "";    
    try{
        const response = await fetch(`/review/get?cno=${cno}`);
        const data = await response.json(); console.log(data);
        data.data.forEach((re) => { 
            let rimgUrl = '/upload/review/'+re.images;
            if(re.images == null || re.images == ""){
                rimgUrl = 'https://placehold.co/50x50';
            }// if end
            re.images.forEach((img) => {                
                html += `<div class="rImgBox" style="display: flex;">
                                <div><img src=${rimgUrl}/></div>
                        </div>`                     
            })// for end            
            if(re.check == true){
                html += `<tr>                            
                            <td>${re.mname}</td>
                            <td>${re.rcontent}</td>
                            <td>${re.rdate}</td>
                            <td>${re.rrank}</td>
                            <td><button onclick="updateBtn(this)">수정</button></td>
                        </tr>`
            }else{
                html += `<tr>                            
                            <td>${re.mname}</td>
                            <td>${re.rcontent}</td>
                            <td>${re.rdate}</td>
                            <td>${re.rrank}</td>                            
                        </tr>`
            }// if end
        })// for end        
        reviewtbody.innerHTML = html;
        viewPageButton(data);
    }catch(e){ console.log(e); }
}// func end
getReview();

// 페이징 버튼 출력 함수 
const viewPageButton = async ( data ) => {
    let currentPage = parseInt( data.currentPage ); 
    let totalPage = data.totalPage;
    let startBtn = data.startBtn;
    let endBtn = data.endBtn;    

    const pageBtnBox = document.querySelector('.pageBtnBox');
    let html = "";
    //  이전버튼  //
    if(currentPage > 1){
        html += `<li><a href="find.jsp?cno=${cno}&page=${currentPage <= 1 ? 1 : currentPage-1}"> 이전 </a></li>`
    }// if end    
    //  페이지버튼 //
    for(let i = startBtn; i <= endBtn; i++){
        html += `<li> <a href="find.jsp?cno=${cno}&page=${i}" style="${ i == currentPage ? 'color:red' : '' }"> ${i} </a> </li>`
    }// for end
    //  다음버튼 //
    if(currentPage < totalPage){
        html += `<li><a href="find.jsp?cno=${cno}&page=${currentPage+1 >= totalPage ? totalPage : currentPage+1}"> 다음 </a></li>`
    }// if end    
    pageBtnBox.innerHTML = html;
} // func end

// 리뷰 수정 버튼 클릭시 입력하는 html로 변경
const updateBtn = async(btn) => {
    const thisTr = btn.closest("tr");
    const rcontent = thisTr.querySelector('td:nth-child(2)').innerText;
    const rrank = thisTr.querySelector('td:nth-child(4)').innerText;    
    thisTr.innerHTML = `<td><textarea name="rcontent">${rcontent}</textarea></td>
                        <td><select name="rrank">
                            <option value="0">평점</option>
                            <option value="5">5</option>
                            <option value="4">4</option>
                            <option value="3">3</option>
                            <option value="2">2</option>
                            <option value="1">1</option>
                        </select></td>
                        <td><input type="file" multiple name="uploads"/></td>
            <td><button type="button" onclick="saveReview(this)">저장</button>
            <button type="button" onclick="getReview()">취소</button></td>`    
}// func end

// 리뷰 수정 기능
const saveReview = async(btn) => {
    const thistr = btn.closest("tr");
    const rcontent = thistr.querySelector("textarea[name='rcontent']").value;
    const rrank = thistr.querySelector("select[name='rrank']").value;
    const uploads = thistr.querySelector("input[name='uploads']").file;
}// func end