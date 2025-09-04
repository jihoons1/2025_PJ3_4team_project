package BestMeat.controller;

import BestMeat.service.PlanService;
import BestMeat.service.SessionService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/plan")
public class PlanController {
    private final PlanService planService;
    private final SessionService sessionService;

    // [plan01] 요금제 결제 - addPlan()
    // 기능설명 : [ 정육점번호(세션) ]을 받아, 해당 정육점의 포인트가 충분하다면, 요금제 결제를 진행한다.
    // method : GET, URL : /plan/add
    // 매개변수 : HttpSession
    // 반환타입 : int -> 성공 : 자동생성된 PK값, 실패 : 0
    @GetMapping("/add")
    public int addPlan( HttpSession session ){
        System.out.println("PointController.addPlan");

        // 1. 세션정보에서 회원번호 | 정육점번호 꺼내기
        int mno = sessionService.getSessionNo( "loginMno", session );
        int cno = sessionService.getSessionNo( "loginCno", session );
        // 2. 비로그인 상태라면, 메소드 종료
        if ( mno == 0 || cno == 0 ) return 0;
        // 3. Service에게 전달 후, 결과 반환
        return planService.addPlan( mno, cno );
    } // func end

    // [plan02] 요금제 조회 - getPlan()
    // 기능설명 : 요금제를 구독하고 있는 정육점 번호를 조회한다.
    // method : GET, URL : /plan/get
    // 매개변수 : X
    // 반환타입 : List<Integer>
    @GetMapping("/get")
    public List<Integer> getPlan(){
        System.out.println("PlanController.getPlan");

        return planService.getPlan();
    } // func end
} // class end