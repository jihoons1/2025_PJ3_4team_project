package BestMeat.service;

import BestMeat.model.dao.NoticeDao;
import BestMeat.model.dto.NoticeDto;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeDao noticeDao;
    private final SessionService sessionService;
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
    // 반환타입 : int -> 성공 : 자동생성된 PK값, 실패 : 0
    public int addNotice( NoticeDto noticeDto, HttpSession session ){
        // 1. 세선정보에서 회원번호 가져오기
        int mno = sessionService.getSessionNo( "loginMno", session );
        // 2. Dto에 값 넣기
        noticeDto.setMno( mno );
        // 3. Dao에게 값 전달 후 결과 반환
        return noticeDao.addNotice( noticeDto );
    } // func end
} // class end