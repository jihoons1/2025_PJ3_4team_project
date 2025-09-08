package BestMeat.service;

import BestMeat.model.dao.AlarmDao;
import BestMeat.model.dto.AlarmDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmService {
    private final AlarmDao alarmDao;

    // [alarm01] 알림등록 - addAlarm()
    // 실행조건 : 문자전송 / 채팅전송
    // 기능설명 : [ 회원번호, 알림메시지 ]를 받아, 알림을 등록한다.
    // 매개변수 : AlarmDto
    // 반환타입 : int -> 성공 : 자동생성된 PK, 실패 : 0
    public int addAlarm( AlarmDto alarmDto ){
        // 1. Dao에게 전달 후 결과 반환하기
        return alarmDao.addAlarm( alarmDto );
    } // func end

    // [alarm02] 알림수정 - updateAlarm()
    // 기능설명 : [ 푸시알림번호 ]를 받아, 해당 알림의 확인여부를 수정한다.
    // 매개변수 : int ano
    // 반환타입 : int -> 성공 : 수정한 푸시알림번호, 실패 : 0
    public int updateAlarm( int ano ){
        // 1. Dao에게 전달 후 결과 반환하기
        return alarmDao.updateAlarm( ano );
    } // func end

    // [alarm03] 알림조회 - getAlarm()
    // 기능설명 : [ 회원번호 ]를 받아, 해당 회원의 아직 확인하지않은 알림을 조회한다.
    // 매개변수 : int mno
    // 반환타입 : List<AlarmDto>
    public List<AlarmDto> getAlarm( int mno ){
        // 1. Dao에게 전달 후 결과 반환하기
        return alarmDao.getAlarm( mno );
    } // func end
} // class end