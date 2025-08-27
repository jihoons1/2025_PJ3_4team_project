package BestMeat.service;

import BestMeat.model.dao.NoticeDao;
import BestMeat.model.dto.NoticeDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeDao noticeDao;

    // [notice01] 알림등록 - addNotice()
    // 기능설명 : [ 회원번호(세션), 제품번호, 알림설정가격 ]을 받아, Notice DB에 저장한다. 저장된 조건 만족 시, 문자 API를 통해 알림 전송한다.
    // 매개변수 : NoticeDto, session
    // 반환타입 : int -> 성공 : 자동생성된 PK값, 실패 : 0
    public int addNotice( NoticeDto noticeDto, HttpSession session ){
        // 1. 세션정보 가져오기
        Object loginMno = session.getAttribute("loginMno");
        // 2. 세선정보에서 회원번호 가져오기
        int mno = loginMno == null ? 0 : (int) loginMno;
        // 3. Dto에 값 넣기
        noticeDto.setMno( mno );
        // 4. Dao에게 값 전달 후 결과 반환
        return noticeDao.addNotice( noticeDto );
    } // func end
    // todo 알림설정가격과 문자전송여부에 따른 문자 전송 API 구현 필요
} // class end