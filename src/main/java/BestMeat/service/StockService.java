package BestMeat.service;

import BestMeat.model.dao.NoticeDao;
import BestMeat.model.dao.StockDao;
import BestMeat.model.dto.NoticeDto;
import BestMeat.model.dto.StockDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockDao stockDao;
    private final SessionService sessionService;
    private final NoticeDao noticeDao;
    private final NoticeService noticeService;

    // todo 알림 전송할 때, 등록일비교 추가 필요
    // todo 재고수정시, 문자전송 API 추가 필요
    // [stock01] 재고등록 - addStock()
    // 기능설명 : [ 정육점번호(세션), 가격, 제품번호(select) ]를 받아, Stock DB에 저장한다.
    // 매개변수 : StockDto
    // 반환타입 : int
    public int addStock( StockDto stockDto, HttpSession session ){
        // 1-1. 세션정보에서 정육점번호 가져오기 -> SessionService 메소드화
        int cno = sessionService.getSessionNo( "loginCno", session );
        // 1-2. 세션정보가 없으면, 메소드 종료
        if ( cno == 0 ) return 0;
        // 2. 정육점번호를 Dto에 넣기
        stockDto.setCno( cno );
        // 3-1. 재고등록하고
        int sno = stockDao.addStock( stockDto );
        // 3-2. 재고등록에 실패했다면, 메소드 종료
        if ( sno == 0 ) return 0;
        // 4-1. 제품번호에 해당하는 알림목록 가져오기
        List<NoticeDto> noticeList = noticeDao.getNoticeList( stockDto.getPno() );
        // 4-2. 제품번호에 해당하는 알림목록이 null이라면, 메소드 종료
        if ( noticeList.isEmpty() ) return sno;
        // 5. 알림목록에서 문자전송여부 확인하기
        for ( NoticeDto noticeDto : noticeList ){
            int ncheck = noticeDto.getNcheck();
            String mphone = noticeDto.getMphone().replaceAll( "-", "" );
            String pname = noticeDto.getPname();
            int nprice = noticeDto.getNprice();
            // 6-1. 문자전송여부가 0이라면
            if ( ncheck == 0 ){
                // 6-2. 문자전송하기, 발신번호와 문자내용 필요
                String content = pname + "이 " + nprice + "원 이하로 등록되었습니다.";      // 정육점명, 정육점링크(jsp 생성후 포함) 포함
                noticeService.sendSms( mphone, content );
            } else {    // 7. 문자전송여부가 0이 아니라면, 문자전송여부와 등록재고가격(sprice)을 비교하여
                // 8-1. 등록재고가격이 낮으면
                if ( ncheck > stockDto.getSprice() ){
                    // 8-2. 문자전송하기
                    String content = pname + "이 " + ncheck + "원 이하로 등록되었습니다.";
                    noticeService.sendSms( mphone, content );
                } // if end
            } // if end
        } // for end
        // 9. Dao에게 전달 후 결과 반환하기
        return sno;
    } // func end

    // [stock02] 재고수정 - updateStock()
    // 기능설명 : [ 정육점번호(세션), 재고번호, 가격 ]을 입력받아, [ 가격, 등록일 ]을 수정한다.
    // 매개변수 : StockDto, session
    // 반환타입 : boolean -> 성공 : true / 실패 : false
    public boolean updateStock( StockDto stockDto, HttpSession session ){
        // 1. 세션정보에서 정육점번호 가져오기 -> SessionService 메소드화
        int cno = sessionService.getSessionNo( "loginCno", session );
        // 2. 정육점번호를 Dto에 넣기
        stockDto.setCno( cno );
        // 3. 현재날짜를 Dto에 넣기
        String today = LocalDateTime.now().toString();
        stockDto.setSdate( today );
        // 4. Dao에게 전달 후 결과 반환하기
        return stockDao.updateStock( stockDto );
    } // func end

    // [stock03] 재고삭제 - deleteStock()
    // 기능설명 : [ 정육점번호(세션+입력), 재고번호 ]을 받아, 해당하는 회사가 등록한 재고라면, 삭제한다.
    // 매개변수 : StockDto, session
    // 반환타입 : boolean -> 성공 : true / 실패 : false
    public boolean deleteStock( StockDto stockDto, HttpSession session ){
        // 1. 세션정보에서 정육점번호 가져오기
        int loginCno = sessionService.getSessionNo( "loginCno", session );
        // 2. 정육점번호(세션 = 입력)를 비교하기
        if ( loginCno == stockDto.getCno() ){
            // 3-1. 정육점번호가 같으면, Dao에게 전달 후 결과 반환하기
            return stockDao.deleteStock( stockDto );
        } else {
            // 3-2. 정육점번호가 다르면, DB처리 안하고 false 반환하기
            return false;
        } // if end
    } // func end

    // [stock04] 정육점별 재고조회 - getStock()
    // 기능설명 : [ 정육점번호 ]를 받아, 해당하는 재고를 조회한다.
    // 매개변수 : session
    // 반환타입 : List<StockDto>
    public List<StockDto> getStock( HttpSession session ){
        // 1. 세션정보에서 정육점번호 가져오기
        int loginCno = sessionService.getSessionNo( "loginCno", session );
        // 2. 정육점번호가 없으면 메소드 종료
        if ( loginCno == 0 ) return null;
        System.out.println("loginCno = " + loginCno);
        // 3. 재고조회하기
        return stockDao.getStock( loginCno );
    } // func end
} // class end