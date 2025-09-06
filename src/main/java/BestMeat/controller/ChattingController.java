package BestMeat.controller;

import BestMeat.model.dto.ChattingDto;
import BestMeat.service.ChattingService;
import BestMeat.service.SessionService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chatting")
public class ChattingController {
    private final ChattingService chattingService;
    private final SessionService sessionService;


    // [chatting01] 회원별 채팅목록 조회 - getRoomList
    // 기능설명 : [ 회원번호(세션) ]을 받아, 해당 회원의 채팅목록을 조회한다.
    // method : GET, URL : /chatting/getRoomList
    // 매개변수 : HttpSession session
    // 반환타입 : List<ChattingDto>
//    @GetMapping("/getRoomList")
//    public List<ChattingDto> getRoomList( HttpSession session ){
//        System.out.println("ChattingController.getRoomList");
//
//        // 1. 세션으로부터 회원번호 가져오기
//        int mno = sessionService.getSessionNo( "loginMno", session );
//        // 2. 비로그인 상태라면, 메소드 종료
//        if ( mno == 0 ) return null;
//        // 3. Service에게 회원번호 전달 후 결과 받기
//        return chattingService.getRoomList( mno );
//    } // func end

    // [chatting02] 채팅로그 호출 기능 - getChatLog()
    // 기능설명 : 방이름을 입력받아, 해당 경로의 CSV를 조회한다.
    // method : GET, URL : /chatting/getChatLog?room=XXX
    // 매개변수 : String room -> '방이름' -> 모든 CSV 조회
    // 반환타입 : List<ChattingDto>
    @GetMapping("/getChatLog")
    public List<ChattingDto> getChatLog( @RequestParam String room ){
        System.out.println("ChattingController.getChatLog");
        try {
            // 1. Service에게 전달 후 결과 반환하기
            return chattingService.getChatLog( room );
        } catch ( Exception e ) {
            System.out.println("[chatting02] Controller 오류 발생" + e );
        } // try-catch end
        return null;
    } // func end
} // class end