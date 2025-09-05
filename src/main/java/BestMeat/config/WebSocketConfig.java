package BestMeat.config;

import BestMeat.handler.ChattingSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration      // 스프링 컨테이너에 bean 등록
@EnableWebSocket    // 웹소켓 기능 활성화
public class WebSocketConfig implements WebSocketConfigurer {
    @Autowired
    private ChattingSocketHandler chattingSocketHandler;

    // 1. 내가 만든 채팅소켓핸들러 등록
    @Override
    public void registerWebSocketHandlers( WebSocketHandlerRegistry registry ){
        registry.addHandler( chattingSocketHandler, "/chat" );
    } // func end
} // class end