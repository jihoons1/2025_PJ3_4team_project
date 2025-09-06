console.log('chatting.js open');
//=============================================== 쿼리스트링 ================================================\\
const random = Math.floor( Math.random() * 1000 ) + 1;
const randomID = `익명-${random}`;

const params = new URL( location.href ).searchParams;
const mno = params.get("mno") || randomID;              // 로그인 상태라면 mno를 받고, 비로그인 상태라면 익명아이디를 받는다.
const room = params.get("room") || "0";                 // 방에 입장하면 roomno를 받고, 아니라면 전채채팅(0)을 받는다.
const cno = params.get("cno");                          // 채팅방에 입장하면 cno를 무조건적으로 받는다.
//=============================================== 채팅 연결 ================================================\\
const client = new WebSocket("/chat");

// 1. 클라이언트와 서버의 연결이 시작되었을 때
client.onopen = ( event ) => {
    console.log('서버와 연동 시작');
    // 1. JSON형식으로 문자열 메시지 생성
    let message = { type : "join", room : room, mno : mno, cno : cno };
    // 2. 특정한 방에 입장했다는 메시지 전송
    client.send( JSON.stringify( message ) );
} // func end

// 2. 클라이언트와 서버의 연결이 종료되었을 때
client.onclose = ( event ) => {
    console.log('서버와 연결 종료');
} // func end

// 3. 서버로부터 메시지를 수신했을 때
client.onmessage = ( event ) => {
    console.log('서버로부터 메시지 수신');
    console.log('서버로부터의 메시지 확인' + event.data );
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
                                <div class="memberNic"> ${ message.from } </div>
                                <div class="subcontent">
                                    <div class="content"> ${ message.message } </div>
                                    <div class="date"> ${ message.date } </div>
                                </div>
                            </div>
                        </div>
                     </div>`
        } // if end
    } // if end
    // 8. 구성한 html을 div에 추가하기
    const msgBox = document.querySelector('.msgbox');
    msgBox.innerHTML += html;
    // 9. 내용물이 넘치면, 자동으로 스크롤 내리기
    msgBox.scrollTop = msgBox.scrollHeight;
} // func end

// 4. 메시지 전송 기능 - 클라이언트 ---> 서버
const postMsgSend = async ( ) => {
    console.log('postMsgSend func exe');
    // 1. Input value
    const msgInput = document.querySelector('.msginput');
    const Input = msgInput.value;
    // 2. value가 없으면, 함수 종료
    if ( Input === '' ) return;
    // 3. 메시지 구성하기
    // type : join(입장), alarm(알림메시지), chat(채팅)
    // type, message, from, date
    const message = { type : "chat", message : Input, from : mno, to : cno, date : new Date().toLocaleString(), room : room };
    // 4. 구성한 메시지를 서버에게 전송하기
    client.send( JSON.stringify( message ) );
    // 5. Input value 초기화
    msgInput.value = '';
} // func end

// 5. room별 채팅 가져오기
const getChatLog = async ( ) => {
    console.log('getChatLog func exe');
    try {
        // 1. fetch
        const response = await fetch( `/chatting/getChatLog?room=${room}` );
        const data = await response.json();     console.log( data );
        // 2. where
        const msgbox = document.querySelector('.msgbox');
        // 3. what
        let html = '';
        for ( let i = 0; i < data.length; i++ ){
            let message = data[i];
            // 4. 내가 보낸 메시지라면
            console.log( message.from );
            console.log( mno );
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
                                    <div class="memberNic"> ${ message.from } </div>
                                    <div class="subcontent">
                                        <div class="content"> ${ message.message } </div>
                                        <div class="date"> ${ message.chatdate } </div>
                                    </div>
                                </div>
                            </div>
                        </div>`
            } // if end
            // 7. 메시지 박스에 html 추가하기
            msgbox.innerHTML += html;
        } // for end
    } catch ( error ) {
        console.log( error );
    } // try-catch end
} // func end
getChatLog();

// 6. mno별 채팅목록 가져오기
const getRoomList = async ( ) => {
    console.log('getRoomList func exe');




} // func end