package BestMeat.service;

import BestMeat.model.dao.NoticeDao;
import BestMeat.model.dao.StockDao;
import BestMeat.model.dto.AlarmDto;
import BestMeat.model.dto.CompanyDto;
import BestMeat.model.dto.NoticeDto;
import BestMeat.model.dto.StockDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockDao stockDao;
    private final NoticeDao noticeDao;
    private final NoticeService noticeService;
    private final CompanyService companyService;
    private final AlarmService alarmService;

    // [stock01] 재고등록 - addStock()
    // 기능설명 : [ 정육점번호(세션), 가격, 제품번호(select) ]를 받아, Stock DB에 저장한다.
    // 매개변수 : StockDto
    // 반환타입 : int
    public int addStock( StockDto stockDto ){
        // 0. 재고등록 전, 재고등록여부 확인하기 -> 등록되어있는 제품이라면, 메소드 종료
        if ( !stockDao.checkStock( stockDto.getCno(), stockDto.getPno() ) ) return 0;
        // 1-1. 재고등록하고
        int sno = stockDao.addStock( stockDto );
        // 1-2. 재고등록에 실패했다면, 메소드 종료
        if ( sno == 0 ) return 0;
        // 1-3. 재고등록가격 가져오기
        int sprice = stockDto.getSprice();
        // 2-1. 제품번호에 해당하는 알림목록 가져오기
        List<NoticeDto> noticeList = noticeDao.getNoticeList( stockDto.getPno() );
        // 2-2. 제품번호에 해당하는 알림목록이 null이라면, 메소드 종료
        if ( noticeList.isEmpty() ) return sno;
        // 3. 알림목록에서 문자전송여부 확인하기
        for ( NoticeDto noticeDto : noticeList ){
            int ncheck = noticeDto.getNcheck();                             // 문자전송여부
            String mphone = noticeDto.getMphone().replaceAll( "-", "" );    // 문자 전송을 위하여, '-' 제거
            String pname = noticeDto.getPname();                            // 문자 내용을 위한, 제품명
            int nprice = noticeDto.getNprice();                             // 알림설정가격
            int cno = stockDto.getCno();                                    // 정육점번호
            String cname = companyService.getCname( cno );                  // cno를 통해 cname 반환받기
            noticeDto.setSprice( stockDto.getSprice() );                    // 재고 가격 수정을 위하여, noticeDto에 저장
            String content = cname + "에서 " + pname + "이 " + nprice + "원 이하로 등록되었습니다.";
            int mno = stockDto.getMno();    // 푸시알림 전송을 위한 회원번호 조회
            // 푸시알림 전송을 위한 AlarmDto 구성 -> Builder를 통해서
            AlarmDto alarmDto = AlarmDto.builder().mno( mno ).amessage( content ).atype( "stock" ).etc( String.valueOf(cno) ).build();
            // 4-1. 문자전송여부가 0이라면
            if ( ncheck == 0 ){
                // 4-2. 알림등록가격과 등록재고가격을 비교하여 문자전송
                if ( nprice > sprice ){
                    // 4-3. 문자전송하기, 발신번호와 문자내용 필요
                    noticeService.asyncSMS( mphone, content );
                    // 4-4. 문자를 전송했다면, Notice DB에 업데이트
                    if ( !noticeDao.updateNcheck( noticeDto ) ) return 0;
                    // 4-5. 푸시알림등록하기 -> 등록에 실패했다면, 메소드 종료
                    if ( alarmService.addAlarm( alarmDto ) <= 0 ) return 0;
                } // if end
            } else {    // 5. 문자전송여부가 0이 아니라면, 문자전송여부와 등록재고가격(sprice)을 비교하여
                // 5-1. 등록재고가격이 낮으면
                if ( ncheck > sprice ){
                    // 5-2. 문자전송하기
                    noticeService.asyncSMS( mphone, content );
                    // 5-3. 문자를 전송했다면, Notice DB에 업데이트
                    if ( !noticeDao.updateNcheck( noticeDto ) ) return 0;
                    // 5-4. 푸시알림등록하기 -> 등록에 실패했다면, 메소드 종료
                    if ( alarmService.addAlarm( alarmDto ) <= 0 ) return 0;
                } // if end
            } // if end
        } // for end
        // 6. Dao에게 전달 후 결과 반환하기
        return sno;
    } // func end

    // [stock02] 재고수정 - updateStock()
    // 기능설명 : [ 정육점번호(세션), 재고번호, 가격 ]을 입력받아, [ 가격, 등록일 ]을 수정한다.
    // 매개변수 : StockDto, session
    // 반환타입 : boolean -> 성공 : true / 실패 : false
    public boolean updateStock( StockDto stockDto ){
        // 1. Dao에게 전달 후 결과받기
        boolean result = stockDao.updateStock( stockDto );
        // 2. 수정실패했으면, 함수 종료
        if ( !result ) return false;
        // 3-0. 재고등록가격 가져오기
        int sprice = stockDto.getSprice();
        // 3-1. 제품번호에 해당하는 알림목록 가져오기
        List<NoticeDto> noticeList = noticeDao.getNoticeList( stockDto.getPno() );
        // 3-2. 제품번호에 해당하는 알림목록이 null이라면, 메소드 종료
        if ( noticeList.isEmpty() ) return result;
        // 4. 알림목록에서 문자전송여부 확인하기
        for ( NoticeDto noticeDto : noticeList ){
            int ncheck = noticeDto.getNcheck();
            String mphone = noticeDto.getMphone().replaceAll( "-", "" );
            String pname = noticeDto.getPname();
            int nprice = noticeDto.getNprice();
            int cno = stockDto.getCno();                                    // 정육점번호
            String cname = companyService.getCname( cno );                  // cno를 통해 cname 반환받기
            noticeDto.setSprice( sprice );                                  // 재고 가격 수정을 위하여, noticeDto에 저장
            String content = cname + "에서 " + pname + "이 " + nprice + "원 이하로 등록되었습니다.";
            int mno = stockDto.getMno();    // 푸시알림 전송을 위한 회원번호 조회
            // 푸시알림 전송을 위한 AlarmDto 구성 -> Builder를 통해서
            AlarmDto alarmDto = AlarmDto.builder().mno( mno ).amessage( content ).atype( "stock" ).etc( String.valueOf(cno) ).build();
            // 5-1. 문자전송여부가 0이라면
            if ( ncheck == 0 ){
                // 5-2. 알림등록가격과 등록재고가격을 비교하여 문자전송
                if ( nprice > sprice ){
                    // 5-3. 문자전송하기, 발신번호와 문자내용 필요
                    noticeService.asyncSMS( mphone, content );
                    // 5-4. 문자를 전송했다면, Notice DB에 업데이트
                    if ( !noticeDao.updateNcheck( noticeDto ) ) return false;
                    // 5-5. 푸시알림등록하기 -> 등록에 실패했다면, 메소드 종료
                    if ( alarmService.addAlarm( alarmDto ) <= 0 ) return false;
                } // if end
            } else {    // 6. 문자전송여부가 0이 아니라면, 문자전송여부와 등록재고가격(sprice)을 비교하여
                // 6-1. 등록재고가격이 낮으면
                if ( ncheck > sprice ){
                    // 6-2. 문자전송하기
                    noticeService.asyncSMS( mphone, content );
                    // 6-3. 문자를 전송했다면, Notice DB에 업데이트
                    if ( !noticeDao.updateNcheck( noticeDto ) ) return false;
                    // 6-4. 푸시알림등록하기 -> 등록에 실패했다면, 메소드 종료
                    if ( alarmService.addAlarm( alarmDto ) <= 0 ) return false;
                } // if end
            } // if end
        } // for end
        return result;
    } // func end

    // [stock03] 재고삭제 - deleteStock()
    // 기능설명 : [ 정육점번호(세션+입력), 재고번호 ]을 받아, 해당하는 회사가 등록한 재고라면, 삭제한다.
    // 매개변수 : StockDto, session
    // 반환타입 : boolean -> 성공 : true / 실패 : false
    public boolean deleteStock( StockDto stockDto ){
        return stockDao.deleteStock( stockDto );
    } // func end

    // [stock04] 정육점별 재고조회 - getStock()
    // 기능설명 : [ 정육점번호 ]를 받아, 해당하는 재고를 조회한다.
    // 매개변수 : session
    // 반환타입 : List<StockDto>
    public List<StockDto> getStock( int cno ){
        return stockDao.getStock( cno );
    } // func end
} // class end