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