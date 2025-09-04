package BestMeat.service;

import BestMeat.model.dao.PointDao;
import BestMeat.model.dto.PointDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointService {
    private final PointDao pointDao;

    // [point01] 포인트로그 추가 - addPointLog()
    // 기능설명 : [ 회원번호(세션), 결제액, 결제사유 ]를 입력받아, 포인트 로그를 추가한다.
    // 매개변수 : PointDto
    // 반환타입 : boolean -> true : 성공, false : 실패
    public boolean addPointLog( PointDto pointDto ){
        return pointDao.addPointLog( pointDto );
    } // func end

    // [point01] 요금제 결제 - addPlan()
    // 기능설명 : [ 정육점번호(세션) ]을 받아, 해당 정육점의 포인트가 충분하다면, 요금제 결제를 진행한다.
    // 매개변수 : int cno
    // 반환타입 : boolean -> true : 성공, false : 실패

} // class end