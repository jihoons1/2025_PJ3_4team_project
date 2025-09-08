package BestMeat.controller;

import BestMeat.model.dto.CompanyDto;
import BestMeat.model.dto.PlanDto;
import BestMeat.model.dto.StockDto;
import BestMeat.service.CompanyService;
import BestMeat.service.PlanService;
import BestMeat.service.SessionService;
import BestMeat.service.StockService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/plan")
public class PlanController {
    private final PlanService planService;
    private final SessionService sessionService;
    private final CompanyService companyService;
    private final StockService stockService;

    // [plan01] 요금제 결제 - addPlan()
    // 기능설명 : [ 정육점번호(세션), 배너이미지 ]를 받아, 해당 정육점의 포인트가 충분하다면, 요금제 결제를 진행한다.
    // method : POST, URL : /plan/add
    // 매개변수 : HttpSession, PlanDto
    // 반환타입 : int -> 성공 : 자동생성된 PK값, 실패 : 0
    @PostMapping("/add")
    public int addPlan( @ModelAttribute PlanDto planDto, HttpSession session ){
        System.out.println("PointController.addPlan");

        // 1. 세션정보에서 회원번호 | 정육점번호 꺼내기
        int mno = sessionService.getSessionNo( "loginMno", session );
        int cno = sessionService.getSessionNo( "loginCno", session );
        // 2. 비로그인 상태라면, 메소드 종료
        if ( mno == 0 || cno == 0 ) return 0;
        // 3. Service에게 전달 후, 결과 반환
        planDto.setCno( cno );
        planDto.setMno( mno );
        return planService.addPlan( planDto );
    } // func end

    // [plan02] 정육점별 요금제 유/무 조회 - getCnoEnddate()
    // 기능설명 : [ 정육점번호 ]를 받아, 해당 정육점의 요금제 유/무를 조회한다.
    // method : GET , URL : /plan/enddate
    // 매개변수 : PlanDto
    // 반환타입 : int days
    @GetMapping("/enddate")
    public int getCnoEnddate( HttpSession session ){
        int cno = sessionService.getSessionNo("loginCno",session);
        PlanDto planDto = new PlanDto();
        planDto.setCno(cno);
        if ( cno == 0 ) return -1;
        return planService.getCnoEnddate(planDto);
    }// func end

    // [plan03-1] 요금제 조회 - getPlan()
    // 기능설명 : 요금제를 구독하고 있는 정육점을 조회한다.
    // method : GET, URL : /plan/get
    // 매개변수 : X
    // 반환타입 : List<PlanDto>
    @GetMapping("/get")
    public List<PlanDto> getPlan(){
        System.out.println("PlanController.getPlan");

        return planService.getPlan();
    } // func end

    // [plan03-2] 요금제 조회 - getPlanStock()
    // 기능설명 : 요금제를 구독하고 있는 정육점의 재고목록을 조회한다.
    // method : GET, URL : /plan/stock
    // 매개변수 : X
    // 반환타입 : List<StockDto>
    @GetMapping("/stock")
    public Map<Integer,List<StockDto>> getPlanStock(){
        System.out.println("PlanController.getPlanStock");
        Map<Integer,List<StockDto>> smap = new HashMap<>();
        List<Integer> ilist = planService.getPlanCno();
        int i = 0;
        for (int cno : ilist){
            List<StockDto> slist = stockService.getStock(cno);
            smap.put(i,slist);
            i++;
        }// for end
        return smap;
    }// func end
} // class end