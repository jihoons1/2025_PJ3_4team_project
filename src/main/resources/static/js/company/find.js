console.log('find.js check');

const params = new URL(location.href).searchParams;
const cno = params.get('cno');

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