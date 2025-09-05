package BestMeat.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component  // 스프링 컨테이너에 bean 등록
public class ChattingSocketHandler extends TextWebSocketHandler {
    // 0. 온라인인 클라이언트 리스트
    private static final Map< String, List< WebSocketSession > > users = new HashMap<>();
    // key : "online"
    // value : 온라인인 클라이언트의 이름

    // 1. 클라이언트와 서버의 연결이 시작되었을 때, 실행되는 메소드
    @Override
    public void afterConnectionEstablished( WebSocketSession session ) throws Exception {
        System.out.println("클라이언트와 연결 시작");
    } // func end

    // 2. 클라이언트와 서버의 연결이 종료되었을 때, 실행되는 메소드
    @Override
    public void afterConnectionClosed( WebSocketSession session, CloseStatus status ) throws Exception {
        System.out.println("클라이언트와 연결 종료");
    } // func end

    // 3. 클라이언트가 서버에게 메시지를 보냈을 때, 실행되는 메소드
    @Override
    protected void handleTextMessage( WebSocketSession session, TextMessage message ) throws Exception {
        System.out.println("클라이언트로부터 메시지 수신");
        System.out.println("message = " + message.getPayload() );


    } // func end


    // * JSON 타입을 자바 타입으로 변환해주는 라이브러리 객체
    // .readValue( JSON형식, 변환할클래스명.class ) : 문자열(JSON) ---> 변환할클래스
    // .writeValueAsString( 변환될객체 ) : 변환될객체 ---> 문자열(JSON)
    private final ObjectMapper objectMapper = new ObjectMapper();
} // class end