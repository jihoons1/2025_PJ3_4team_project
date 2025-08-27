package BestMeat.service;

import BestMeat.model.dao.StockDao;
import BestMeat.model.dto.StockDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockDao stockDao;
    private final CompanyService companyService;

    // [stock01] 재고등록 - addStock()
    // 기능설명 : [ 정육점번호(세션), 가격, 제품번호(select) ]를 받아, Stock DB에 저장한다.
    // 매개변수 : StockDto
    // 반환타입 : int
    public int addStock( StockDto stockDto, HttpSession session ){
        // 1. 세션정보에서 정육점번호 가져오기 -> companyService에 메소드화
        int cno = companyService.getLoginCno( session );
        // 2. 정육점번호를 Dto에 넣기
        stockDto.setCno( cno );
        // 3. Dao에게 전달 후 결과 반환하기
        return stockDao.addStock( stockDto );
    } // func end

    // [stock02] 재고수정 - updateStock()
    // 기능설명 : [ 정육점번호(세션), 재고번호, 가격 ]을 입력받아, [ 가격, 등록일 ]을 수정한다.
    // 매개변수 : StockDto, session
    // 반환타입 : boolean -> 성공 : true / 실패 : false
    public boolean updateStock( StockDto stockDto, HttpSession session ){
        // 1. 세션정보에서 정육점번호 가져오기 -> companyService에 메소드화
        int cno = companyService.getLoginCno( session );
        // 2. 정육점번호를 Dto에 넣기
        stockDto.setCno( cno );
        // 3. 현재날짜를 Dto에 넣기
        String today = LocalDateTime.now().toString();
        stockDto.setSdate( today );
        // 4. Dao에게 전달 후 결과 반환하기
        return stockDao.updateStock( stockDto );
    } // func end

    // [stock03] 재고삭제 - deleteStock()
    // 기능설명 : [ 정육점번호(세션), 재고번호 ]을 받아, 해당하는 회사가 등록한 재고라면, 삭제한다.
    // 매개변수 : int sno, session
    // 반환타입 : boolean -> 성공 : true / 실패 : false

} // class end