package BestMeat.service;

import BestMeat.model.dao.PointDao;
import BestMeat.model.dto.PointDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

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

    // [point02] UUID 생성 - createUUID()
    // 기능설명 : UUID를 생성하여 반환한다.
    // 매개변수 : X
    // 반환타입 : String
    public String createUUID(){
        return UUID.randomUUID().toString();
    } // func end
} // class end