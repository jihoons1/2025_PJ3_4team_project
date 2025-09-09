package BestMeat.controller;

import BestMeat.model.dto.AlarmDto;
import BestMeat.service.AlarmService;
import BestMeat.service.SessionService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/alarm")
public class AlarmController {
    private final AlarmService alarmService;
    private final SessionService sessionService;

    // [alarm02] 푸시알림수정 - updateAlarm()
    // 기능설명 : [ 푸시알림번호 ]를 받아, 해당 알림의 확인여부를 수정한다.
    // method : PUT, URL : /alarm/update
    // 매개변수 : int ano
    // 반환타입 : int -> 성공 : 수정한 푸시알림번호, 실패 : 0
    @PutMapping("/update")
    public int updateAlarm( @RequestParam int ano ){
        System.out.println("AlarmController.updateAlarm");

        // 1. Service에게 전달 후 결과받기
        return alarmService.updateAlarm( ano );
    } // func end


    // [alarm03] 푸시알림조회 - getAlarm()
    // 기능설명 : [ 회원번호(세션) ]를 받아, 해당 회원의 아직 확인하지않은 알림을 조회한다.
    // method : GET, URL : /alarm/get
    // 매개변수 : int mno
    // 반환타입 : List<AlarmDto>
    @GetMapping("/get")
    public List<AlarmDto> getAlarm( HttpSession session ){
        System.out.println("AlarmController.getAlarm");

        // 1. 세션정보에서 회원번호를 꺼내기
        int mno = sessionService.getSessionNo( "loginMno", session );
        // 2. 회원번호가 없다면, 메소드 종료
        if ( mno == 0 ) return null;
        // 3. mno를 Service에게 전달 후 결과받기
        return alarmService.getAlarm( mno );
    } // func end
} // class end