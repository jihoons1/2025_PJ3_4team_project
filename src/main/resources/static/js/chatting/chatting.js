console.log('chatting.js open');
//=============================================== 쿼리스트링 ================================================\\
const random = Math.floor( Math.random() * 1000 ) + 1;
const randomID = `익명-${random}`;

const params = new URL( location.href ).searchParams;
const mno = params.get("mno") || randomID;      // 로그인 상태라면 mno를 받고, 비로그인 상태라면 익명아이디를 받는다.
const room = params.get("room") || 0;           // 방에 입장하면 roomno를 받고, 아니라면 전채채팅(0)을 받는다.
console.log( mno );
console.log( room );
//=============================================== 채팅 연결 ================================================\\
const client = new WebSocket("/chat");

client.onopen = ( event ) => {
    console.log('서버와 연동 시작');

} // func end

client.onclose = ( event ) => {
    console.log('서버와 연결 종료');

} // func end

client.onmessage = ( event ) => {
    console.log('서버로부터 메시지 수신');

} // func end