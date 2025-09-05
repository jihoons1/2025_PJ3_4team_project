package BestMeat.service;

import BestMeat.model.dao.PlanDao;
import BestMeat.model.dao.PointDao;
import BestMeat.model.dto.PlanDto;
import BestMeat.model.dto.PointDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanService {
    private final PlanDao planDao;
    private final PointDao pointDao;

    // [plan01] 요금제 결제 - addPlan()
    // 기능설명 : [ 정육점번호(세션) ]을 받아, 해당 정육점의 포인트가 충분하다면, 요금제 결제를 진행한다.
    // 매개변수 : int mno, int cno
    // 반환타입 : int -> 성공 : 자동생성된 PK값, 실패 : 0
    public int addPlan( int mno, int cno ){
        // 1. 해당 회원의 포인트 총액 가져오기
        int totalPoint = pointDao.getTotalPoint( mno );
        // 2. 요금제(5000)보다 총액이 작다면, 결제 취소
        if ( totalPoint < 5000 ) return 0;
        // 3. 총액이 크다면, 포인트로그 추가를 위한 PointDto 생성
        PointDto pointDto = new PointDto();
        pointDto.setMno( mno );
        pointDto.setPlpoint( -5000 );
        pointDto.setPlcomment( "멤버쉽 결제로 인한 차감" );
        // 4. 포인트로그 추가 (-5000) -> 로그 추가를 실패했다면, 메소드 종료
        if ( !pointDao.addPointLog( pointDto ) ) return 0;
        // 5. 요금제 테이블 추가를 위한, startdate/enddate 만들기
        String startdate = LocalDate.now().toString();              // 결제일 = 시스템 날짜 기준일
        String enddate = LocalDate.now().plusDays(7).toString();    // 종료일 = 시스템 날짜 기준일 + 7일
        // 6. 요금제 테이블 추가를 위한, PlanDto 만들기
        PlanDto planDto = new PlanDto();
        planDto.setCno( cno );
        planDto.setStartdate( startdate );
        planDto.setEnddate( enddate );
        // 7. 해당 정육점이 결제중인지 확인하기
        // days > 0 : 결제중
        int days = planDao.getCnoEnddate( planDto );
        if ( days > 0 ){
            // 8. 결제중이라면, 종료일에 남은 일을 추가한다.
            enddate = LocalDate.now().plusDays( 7 + days ).toString();
            planDto.setEnddate( enddate );
        } // if end
        // 9. 최종적으로 Dao에게 전달 후, 결과를 반환한다.
        return planDao.addPlan( planDto );
    } // func end

    // [plan02] 정육점별 요금제 유/무 조회 - getCnoEnddate()
    // 기능설명 : [ 정육점번호 ]를 받아, 해당 정육점의 요금제 유/무를 조회한다.
    // 매개변수 : PlanDto
    // 반환타입 : int days
    public int getCnoEnddate( PlanDto planDto ){
        String startdate = LocalDate.now().toString();
        planDto.setStartdate(startdate);
        int result = planDao.getCnoEnddate(planDto);
        return result;
    }// func end

    // [plan03] 요금제 조회 - getPlan()
    // 기능설명 : 요금제를 구독하고 있는 정육점 번호를 조회한다.
    // 매개변수 : X
    // 반환타입 : List<Integer>
    public List<Integer> getPlan(){
        // 1. 오늘 날짜 구하기
        String today = LocalDate.now().toString();
        System.out.println("today = " + today);
        // 2. Dao에게 전달 후 결과 반환하기
        return planDao.getPlan( today );
    } // func end
} // class end