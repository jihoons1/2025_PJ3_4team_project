package BestMeat.handler;

import BestMeat.model.dto.AlarmDto;
import BestMeat.service.AlarmService;
import BestMeat.service.ChattingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.*;

@Component  // 스프링 컨테이너에 bean 등록
@RequiredArgsConstructor
public class ChattingSocketHandler extends TextWebSocketHandler {
    private final ChattingService chattingService;

    // 0. 채팅방 모음
    private static final Map< String, List< WebSocketSession > > users = new HashMap<>();
    // key : 방번호, value : 방에 접속한 클라이언트들


    // 1. 클라이언트와 서버의 연결이 시작되었을 때, 실행되는 메소드
    @Override
    public void afterConnectionEstablished( WebSocketSession session ) throws Exception {
        System.out.println("클라이언트와 연결 시작");
    } // func end

    // 2. 클라이언트와 서버의 연결이 종료되었을 때, 실행되는 메소드
    @Override
    public void afterConnectionClosed( WebSocketSession session, CloseStatus status ) throws Exception {
        System.out.println("클라이언트와 연결 종료");
        // 1. 연결이 종료된 클라이언트의 정보 확인하기
        String room = (String) session.getAttributes().get("room"); // 접속했던 방번호
        String mno = (String) session.getAttributes().get("mno");   // 접속한 회원번호
        // 2. 만약 방번호와 회원번호가 일치하는 데이터가 채팅방에 존재한다면 -> null이 아니라는 것은 존재한다는 의미
        if ( room != null || mno != null ){
            // 3. 해당 방의 접속목록을 꺼내서
            List< WebSocketSession > list = users.get( room );
            // 4. 해당 세션을 삭제한다.
            list.remove( session );
            // 5. 클라이언트가 방에서 나갔다고 알림
            alarmMessage( room, mno + "님이 채팅을 종료했습니다.");
        } // if end
    } // func end

    // 3. 클라이언트가 서버에게 메시지를 보냈을 때, 실행되는 메소드
    @Override
    protected void handleTextMessage( WebSocketSession session, TextMessage message ) throws Exception {
        System.out.println("클라이언트로부터 메시지 수신");
        System.out.println("message = " + message.getPayload() );
        // 1. JS가 보내온 메시지를 Map 타입으로 변환
        Map< String, String > msg = objectMapper.readValue( message.getPayload(), Map.class );
        // 2. 만약 msg의 type이 join이라면
        if ( msg.get("type").equals("join") ){
            String room = msg.get("room");          // 접속한 방번호 -> 0(전체채팅) 또는 채팅방 PK
            String mno = msg.get("mno");            // 접속한 회원번호 -> 익명 또는 회원번호
            String cno = msg.get("cno");            // 채팅대상 -> 정육점번호
            // 3. 메시지를 보내온 클라이언트 소켓세션에 정보 추가
            session.getAttributes().put( "room", room );
            session.getAttributes().put( "mno", mno );
            session.getAttributes().put( "cno", cno );
            // 4. 접속한 방이 존재한다면
            if ( users.containsKey( room ) ){
                // 5. 해당 방에 세션 추가
                users.get( room ).add( session );
            } else { // 6. 접속한 방이 생성 전이라면
                // 7. 세션이 들어갈 방을 생성하고
                List< WebSocketSession > list = new ArrayList<>();
                // 8. 생성된 방에 세션 추가
                list.add( session );
                // 9. 채팅방 모음에 생성된 방 추가
                users.put( room, list );
            } // if end
            // 10. 접속한 회원을 알림을 통해 보내기
            alarmMessage( room, mno + "님이 채팅을 시작했습니다." );
            // 11. 만약 msg의 type이 chat이라면
        } else if ( msg.get("type").equals("chat") ){
            // 12. 메시지를 보낸 클라이언트의 방 가져오기
            String room = (String) session.getAttributes().get( "room" );
            // 13. 같은 방에 있는 모든 클라이언트에게 메시지 보내기
            for ( WebSocketSession client : users.get( room ) ){
                client.sendMessage( message );
            } // for end
            // 14. CSV 생성하기
            chattingService.createCSV( room );
            // 15. CSV에 메시지 저장하기
            chattingService.saveSCV( msg );
        } // if end
        // 16. 생성된 채팅방 확인용
        System.out.println("users = " + users);
    } // func end

    // 4. 채팅방 알림 메시지 -> 입·퇴장시 실행
    // room : 어떤 방에, message : 어떤 메시지를
    public void alarmMessage( String room, String message ) throws Exception {
        // 1. 보내려는 메시지를 Map 타입으로 구성
        Map< String, String > msg = new HashMap<>();
        msg.put( "type", "alarm" );
        msg.put( "message", message );
        // 2. Map을 JSON 타입으로 변환
        String JSONMessage = objectMapper.writeValueAsString( msg );
        // 3. 해당 방에 있는 모든 세션에게 메시지 전송
        for ( WebSocketSession session : users.get( room ) ){
            session.sendMessage( new TextMessage( JSONMessage ) );
        } // func end
    } // func end

    // * JSON 타입을 자바 타입으로 변환해주는 라이브러리 객체
    // .readValue( JSON형식, 변환할클래스명.class ) : 문자열(JSON) ---> 변환할클래스
    // .writeValueAsString( 변환될객체 ) : 변환될객체 ---> 문자열(JSON)
    private final ObjectMapper objectMapper = new ObjectMapper();
} // class end