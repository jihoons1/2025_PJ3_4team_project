console.log('find.js check');

const params = new URL(location.href).searchParams;
const cno = params.get('cno');

// 정육점 개별조회
const findCompany = async() => {
    const findTbody = document.querySelector('.findTbody');
    let html = "";
    try{
        const response = await fetch(`/company/find?cno=${cno}`);
        const data = await response.json(); console.log(data);
        html += `<tr>
                    <td><img src=${data.cimg}/></td>
                    <td>${data.cname}</td>
                    <td>${data.caddress}</td>
                    <td>${data.rrank}</td>
                </tr>`;
        findTbody.innerHTML = html;
    }catch(e){ console.log(e); }
}// func end
findCompany();