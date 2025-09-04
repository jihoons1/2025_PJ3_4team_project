package BestMeat.controller;

import BestMeat.model.dto.PointDto;
import BestMeat.service.PointService;
import BestMeat.service.SessionService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/point")
public class PointController {
    private final PointService pointService;
    private final SessionService sessionService;

    // [point01] 포인트 결제 - pointPayment()
    // 기능설명 : [ 회원번호(세션), 결제액, 결제사유 ]를 입력받아, 포인트 결제를 진행한다.
    // method : PUT, URL : /point/payment
    // 매개변수 : PointDto
    // 반환타입 : boolean -> true : 성공, false : 실패
    @PutMapping("/payment")
    public boolean pointPayment( @RequestBody PointDto pointDto, HttpSession session ){
        System.out.println("PointController.pointPayment");

        // 1. 세션 정보에서 회원번호 꺼내기
        int mno = sessionService.getSessionNo( "loginMno", session );
        // 2. 비로그인 상태라면, 메소드 종료
        if ( mno == 0 ) return false;
        // 3. Service에게 전달할 dto에 mno 주입
        pointDto.setMno( mno );
        // 4. Service에게 전달 후, 결과 반환하기
        return pointService.addPointLog( pointDto );
    } // func end

    // [point01] 요금제 결제 - addPlan()
    // 기능설명 : [ 정육점번호(세션) ]을 받아, 해당 정육점의 포인트가 충분하다면, 요금제 결제를 진행한다.
    // method : GET, URL : /point/plan
    // 매개변수 : int cno
    // 반환타입 : boolean -> true : 성공, false : 실패

} // class end