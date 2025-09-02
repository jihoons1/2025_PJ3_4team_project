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
        const data = await response.json(); 
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

// // 리뷰 등록html 불러오기
// const addReviewBox = async() => {
//     const reviewAddBox = document.querySelector('.reviewAddBox');
//     let html = `<textarea name="rcontent"></textarea>
//                 <select name="rrank">
//                     <option value="0">평점</option>
//                     <option value="5">5</option>
//                     <option value="4">4</option>
//                     <option value="3">3</option>
//                     <option value="2">2</option>
//                     <option value="1">1</option>
//                 </select>
//                 <input type="file" multiple name="uploads"/>
//                 <button type="button" onclick="addReview()">등록</button>`;
//     reviewAddBox.innerHTML = html;
// }// func end

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
            getReview();
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
                html += `<div class="rImgBox" style="display: flex;">
                                <div><img src=${rimgUrl}/></div>
                        </div>`  
            }// if end
            re.images.forEach((img) => {             
                let imgurl = '/upload/review/'+img;                   
                html += `<div class="rImgBox" style="display: flex;">
                                <div><img src=${imgurl}/></div>
                        </div>`                     
            })// for end            
            if(re.check == true){
                html += `<tr>
                            <td>${re.rno}</td>                            
                            <td>${re.mname}</td>
                            <td>${re.rcontent}</td>
                            <td>${re.rdate}</td>
                            <td>${re.rrank}</td>
                            <td>
                                <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#staticBackdrop2" onclick="getRnoReview(${re.rno})">
                                    수정
                                </button>
                            </td>
                        </tr>`
            }else{
                html += `<tr>
                            <td>${re.rno}</td>
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

// // 리뷰 수정 버튼 클릭시 입력하는 html로 변경
// const updateBtn = async(btn) => {
//     const thisTr = btn.closest("tr");
//     const rno = thisTr.querySelector("td:nth-child(1)").innerText;
//     const rcontent = thisTr.querySelector('td:nth-child(3)').innerText;      
//     thisTr.innerHTML = `<td>${rno}<input type="hidden" name="rno" value="${rno}"></td>
//                         <td><textarea name="rcontent">${rcontent}</textarea></td>
//                         <td><select name="rrank">
//                             <option value="0">평점</option>
//                             <option value="5">5</option>
//                             <option value="4">4</option>
//                             <option value="3">3</option>
//                             <option value="2">2</option>
//                             <option value="1">1</option>
//                         </select></td>
//                         <td><input type="file" multiple name="uploads"/></td>
//             <td><button type="button" onclick="saveReview(this)">저장</button>
//             <button type="button" onclick="getReview()">취소</button></td>`    
// }// func end

// 리뷰번호 리뷰 내용 조회
const getRnoReview = async ( rno ) => {
    console.log('getRnoReview func exe');
    try {
        // 1. fetch
        const option = { method : "GET" };
        const response = await fetch( `/review/getRno?rno=${rno}`, option );
        const data = await response.json();         console.log( data );
        // 2. print
        document.querySelector('.oldrcontent').innerHTML = data.rcontent;
        document.querySelector('.oldrrank').value = data.rrank;
    } catch ( error ){
        console.log( error );
    } // try-catch end
} // func end

// 리뷰 수정 기능
const saveReview = async(btn) => {
    const thistr = btn.closest("tr");
    const rno = thistr.querySelector("input[name='rno']").value;
    const rcontent = thistr.querySelector("textarea[name='rcontent']").value;
    const rrank = thistr.querySelector("select[name='rrank']").value;
    let uploads = thistr.querySelector("input[name='uploads']").files;   
    const formData = new FormData();
    formData.append('rno',rno);
    formData.append('rcontent',rcontent);
    formData.append('rrank',rrank);
    if( uploads.length > 0){
        for(let i = 0; i < uploads.length; i++){
            formData.append('uploads',uploads[i]);
        }// for end
    }// if end
    const option = { method : "PUT" , body : formData}  
    console.log(option);
    try{
        const response = await fetch('/review/update',option);
        const data = await response.json();
        console.log(data);
        if(data == true){
            alert('수정성공!');
            getReview();
        }else{
            alert('수정실패!');
        }// if end
    }catch(e){ console.log(e); }
}// func end