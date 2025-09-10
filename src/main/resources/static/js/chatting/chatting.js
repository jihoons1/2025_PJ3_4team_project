//=============================================== 쿼리스트링 ================================================\\
const random1 = Math.floor( Math.random() * 1000 ) + 1;
const randomID1 = `익명-${random1}`;
const random2 = Math.floor( Math.random() * 1000 ) + 1;
const randomID2 = `익명-${random2}`;

const params = new URL( location.href ).searchParams;
const mno = params.get("mno") || randomID1;              // 로그인 상태라면 mno를 받고, 비로그인 상태라면 익명아이디를 받는다.
const room = params.get("room") || "0";                 // 방에 입장하면 roomno를 받고, 아니라면 전채채팅(0)을 받는다.
const cno = params.get("cno") || randomID2;             // 전체채팅 방이라면, 익명아이디를 받는다.
//=============================================== 채팅 연결 ================================================\\
const client = new WebSocket("/chat");

// 1. 클라이언트와 서버의 연결이 시작되었을 때
client.onopen = ( event ) => {
    // 1. JSON형식으로 문자열 메시지 생성
    let message = { type : "join", room : room, mno : mno, cno : cno };
    // 2. 특정한 방에 입장했다는 메시지 전송
    client.send( JSON.stringify( message ) );
} // func end

// 2. 클라이언트와 서버의 연결이 종료되었을 때
client.onclose = ( event ) => {
} // func end

// 3. 서버로부터 메시지를 수신했을 때
client.onmessage = ( event ) => {
    // 1. 받은 메시지를 JSON 타입으로 변환
    const message = JSON.parse( event.data );   
    // 2. 받은 메시지의 type을 확인하여, 서로 다른 html 만들기
    // type : join(입장), alarm(알림메시지), chat(채팅)
    let html = '';
    // 3. type이 alarm이라면
    if ( message.type === 'alarm' ){
        // 4. alarm html 구성하기
        html += `<div class="alarm">
                    <span>${message.message}</span>
                 </div>`
    } else if ( message.type == 'chat' ){
        // 5. chat html 구성하기
        if ( message.from == mno ){
            // 6. 내가 보낸 메시지 html
            html += `<div class="secontent">
                        <div class="date"> ${message.date} </div>
                        <div class="content"> ${message.message} </div>
                     </div>`
        } else {
            // 7. 남이 보낸 메시지 html
            html += `<div class="receiveBox">
                        <div class="profileImg">
                            <img src="/img/default.jpg"/>
                        </div>
                        <div>
                            <div class="recontent">
                                <div class="memberNic"> ${ message.fromname } </div>
                                <div class="subcontent">
                                    <div class="content"> ${ message.message } </div>
                                    <div class="date"> ${ message.date } </div>
                                </div>
                            </div>
                        </div>
                     </div>`
        } // if end
    } // if end
    // 9. 구성한 html을 div에 추가하기
    const msgBox = document.querySelector('.msgbox');
    msgBox.innerHTML += html;
    // 10. 내용물이 넘치면, 자동으로 스크롤 내리기
    msgBox.scrollTop = msgBox.scrollHeight;
} // func end

//=============================================== 일반 로직 ================================================\\
// 1. 메시지 전송 기능 - 클라이언트 ---> 서버
const postMsgSend = async ( ) => {
    // 1. Input value
    const msgInput = document.querySelector('.msginput');
    const Input = msgInput.value;
    // 2. value가 없으면, 함수 종료
    if ( Input === '' ) return;
    // 3. 메시지 구성하기
    // type : join(입장), alarm(알림메시지), chat(채팅)
    // type, message, from, date
    // 3-1. mno로 mname 구하기
    const response = await fetch( `/member/getMname?mno=${mno}` );
    const data = await response.text();     // data == mname
    // 3-2. 날짜를 MySQL 형식으로 구성하기
    let objectDate = new Date();
    let year = objectDate.getFullYear();
    let month = objectDate.getMonth() + 1;
    let day = objectDate.getDate();
    let times = objectDate.toString().split(" ")[4];
    let MySQLDate = year + "-" + month + "-" + day + " " + times;
    const message = { type : "chat", message : Input, from : mno, to : cno, date : MySQLDate, room : room, fromname : data };
    // 4. 구성한 메시지를 서버에게 전송하기
    client.send( JSON.stringify( message ) );
    // 5. Input value 초기화
    msgInput.value = '';
    // 6. chat을 보냈을 때, 채팅방 목록 조회
    getRoomList();
} // func end

// 2. room별 채팅 가져오기
const getChatLog = async ( ) => {
    try {
        // 1. fetch
        const response = await fetch( `/chatting/getChatLog?room=${room}` );
        const data = await response.json();
        // 2. where
        const msgbox = document.querySelector('.msgbox');
        // 3. what
        let html = '';
        for ( let i = 0; i < data.length; i++ ){
            let message = data[i];
            // 4. 내가 보낸 메시지라면
            if ( message.from == mno ){
                // 5. 내가 보낸 메시지 html 구성하기
                html += `<div class="secontent">
                            <div class="date"> ${message.chatdate} </div>
                            <div class="content"> ${message.message} </div>
                        </div>`
            } else {
                // 6. 남이 보낸 메시지 html 구성하기
                html += `<div class="receiveBox">
                            <div class="profileImg">
                                <img src="/img/default.jpg"/>
                            </div>
                            <div>
                                <div class="recontent">
                                    <div class="memberNic"> ${ message.fromname } </div>
                                    <div class="subcontent">
                                        <div class="content"> ${ message.message } </div>
                                        <div class="date"> ${ message.chatdate } </div>
                                    </div>
                                </div>
                            </div>
                        </div>`
            } // if end
            // 7. 메시지 박스에 html 추가하기
            msgbox.innerHTML = html;
        } // for end
    } catch ( error ) {
        console.log( error );
    } // try-catch end
} // func end
getChatLog();

// 3. 채팅방 유효성 검사
const checkSession = async ( ) => {
    try{
        // 1. fetch
        const response = await fetch( "/member/get" );
        const data = await response.json();
        // 2. 전체채팅방이면 유지
        if ( room == null || room == "0" ) return;
        // 3. 세션 회원번호가 수신/발신 어디에도 없다면
        if ( data.mno != mno && data.mno != cno ){
            // 4. 메인페이지로 이동
            alert('유효하지않은 세션이므로 메인페이지로 이동합니다.');
            location.href = "/index.jsp";
        } // if end
    } catch ( error ){
        // 5. 전체채팅방이면 유지
        if ( room == null || room == "0" ) return;
        // 6. 메인페이지로 이동
        alert('유효하지않은 세션이므로 메인페이지로 이동합니다.');
        location.href = "/index.jsp";
    } // try-catch end
} // func end
checkSession();

// 4. 전체채팅방 표시 출력
const printPublicRoom = async ( ) => {
    if ( room == "0" ){
        // 1. where
        const roomTitle = document.querySelector('.roomTitle');
        // 2. what
        let html = `<strong style="font-size: 25px;"> 전체 채팅방 </strong>`;
        // 3. print
        roomTitle.innerHTML = html;
    } // if end
} // func end
printPublicRoom()

// 5. mno별 채팅목록 가져오기
const getRoomList = async ( ) => {
    printPublic();
    try {
        // 1. fetch
        const fetch1 = await fetch( "/chatting/getRoomList" );
        const data = await fetch1.json();
        const getMno = await fetch( "/member/getMno" );
        const mno = await getMno.json();
        // 2. where
        const roomList = document.querySelector('.roomList');
        // 3. html
        let html = ``;
        for ( let i = 0; i < data.length; i++ ){
            let room = data[i];
            // mno와 data.to가 다르다면
            let me = 0;
            let other = 0;
            if ( room.to == mno ){
                me = room.to;
                other = room.from;
            } else {
                me = room.from;
                other = room.to;
            } // if end
            // 4. fetch
            const fetch2 = await fetch( `/member/getMname?mno=${me}` );
            const mename = await fetch2.text();
            const fetch3 = await fetch( `/member/getMname?mno=${other}` );
            const othername = await fetch3.text();

            // 5. what
            html += `<div class="rooms">
                            <a href="/chatting/chatting.jsp?mno=${me}&cno=${other}&room=${room.roomname}">
                            ${mename}님과 ${othername}의 채팅방 <br>
                            최근메시지 : ${room.message}
                            </a>
                        </div>`;
        } // for end
        roomList.innerHTML += html;
    } catch ( error ) {
        console.log( error );
    } // try-catch end
} // func end

// 6. 전체채팅방 출력하기
const printPublic = async ( ) => {
    try {
        // 1. fetch
        const response = await fetch( "/member/getMno" );
        const data = await response.json();
        // 2. where
        const roomList = document.querySelector('.roomList');
        // 3. html
        let html = `<div class="rooms">
                        <a href="/chatting/chatting.jsp?mno=${data}&room=0">
                            전체채팅방
                        </a>
                    </div>`;
        // 4. print
        roomList.innerHTML = html;
    } catch ( error ) {
        console.log( error );
    } // try-catch end
} // func end