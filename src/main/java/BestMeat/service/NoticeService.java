package BestMeat.service;

import BestMeat.model.dao.NoticeDao;
import BestMeat.model.dao.PointDao;
import BestMeat.model.dto.NoticeDto;
import BestMeat.model.dto.PointDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeDao noticeDao;
    private final PointService pointService;
    private final PointDao pointDao;
    private DefaultMessageService messageService;

    private final String ApiKey = "NCSQQGPH8BYXIF1S";
    private final String ApiSecret = "PZKC2JWNOSTUCN4PDXRK93XXJSQRZA06";
    private final String domain = "https://api.solapi.com";

    @PostConstruct  // 의존성이 주입된 후, messageService를 초기화, 의존성이 주입되기 전이면 ApiKey, ApiSecret, domain이 null이기에 초기화를 할 수 없음
    public void init(){
        this.messageService = NurigoApp.INSTANCE.initialize( ApiKey, ApiSecret, domain );
    } // func end

    // [notice00] 문자전송 - sendSms()
    public SingleMessageSentResponse sendSms( String mphone, String content ){
        Message message = new Message();
        message.setTo( mphone );                // 수신번호 설정
        message.setText( content );             // 문자내용 설정
        message.setFrom( "01051091342" );       // 발신번호 설정
        // 최종적으로 문자 1개 발송
        return this.messageService.sendOne( new SingleMessageSendingRequest( message ) );
    } // func end

    // [notice01] 알림등록 - addNotice()
    // 기능설명 : [ 회원번호(세션), 제품번호, 알림설정가격 ]을 받아, Notice DB에 저장한다. 저장된 조건 만족 시, 문자 API를 통해 알림 전송한다.
    // 매개변수 : NoticeDto, session
    // 반환타입 : int -> 성공 : 자동생성된 PK값, 실패 : 0, -1(잔액 부족)
    public int addNotice( NoticeDto noticeDto ){
        // 1. 회원 포인트 총액 조회
        int mno = noticeDto.getMno();
        int totalPoint = pointDao.getTotalPoint( mno );
        // 2. 만약 회원의 포인트가 500이 안 넘는다면, 알림등록 실패
        if ( totalPoint < 500 ) return -1;
        // 3. 넘는다면, 포인트 차감 진행
        PointDto pointDto = new PointDto();
        pointDto.setMno( mno );
        pointDto.setPlpoint( 500 );
        pointDto.setPlcomment( "알림등록 포인트 차감" );
        // 4. 포인트 로그 추가 실패하면, 메소드 종료
        if ( !pointService.addPointLog( pointDto ) ) return 0;
        // 5. 최종적으로 Dao에게 값 전달 후 결과 반환
        return noticeDao.addNotice( noticeDto );
    } // func end

    // [notice03] 회원별 알림조회 - getMnoNotice()
    // 기능설명 : [ 회원번호(세션) ]을 받아, 해당하는 알림을 조회한다.
    // 매개변수 : session
    // 반환타입 : List<NoticeDto>
    public List<NoticeDto> getMnoNotice( int mno ){
        // 1. Dao에게 전달후 결과 반환
        return noticeDao.getMnoNotice( mno );
    } // func end

    // [notice04] 알림수정 - updateNotice
    // 기능설명 : [ 회원번호(세션), 알림번호, 알림설정가격 ]을 받아, 해당하는 알림을 수정한다.
    // 매개변수 : NoticeDto, session
    // 반환타입 : boolean
    public boolean updateNotice( NoticeDto noticeDto ){
        // 1. Dao에게 전달 후, 결과 반환하기
        return noticeDao.updateNotice( noticeDto );
    } // func end

    // [notice05] 알림삭제 - deleteNotice
    // 기능설명 : [ 회원번호(세션), 알림번호 ]를 받아, 해당하는 알림을 삭제한다.
    // 매개변수 : Map< String, Integer > map
    // 반환타입 : boolean
    public boolean deleteNotice( Map< String, Integer > map ){
        // 1. Dao에게 전달 후, 결과 반환하기
        return noticeDao.deleteNotice( map );
    } // func end
} // class end