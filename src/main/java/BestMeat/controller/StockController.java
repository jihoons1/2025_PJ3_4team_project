package BestMeat.controller;

import BestMeat.model.dto.StockDto;
import BestMeat.service.SessionService;
import BestMeat.service.StockService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stock")
public class StockController {
    private final StockService stockService;
    private final SessionService sessionService;

    // [stock01] 재고등록 - addStock()
    // 기능설명 : [ 정육점번호(세션), 가격, 제품번호(select) ]를 받아, Stock DB에 저장한다.
    // method : POST, URL : /stock/add
    // 매개변수 : StockDto, session
    // 반환타입 : int
    @PostMapping("/add")
    public int addStock( @RequestBody StockDto stockDto, HttpSession session ){
        System.out.println("StockController.addStock");
        System.out.println("stockDto = " + stockDto);

        // 1. 세션정보에서 정육점번호 가져오기 -> SessionService 메소드화
        int cno = sessionService.getSessionNo( "loginCno", session );
        // 2. 세션정보가 없으면, 메소드 종료
        if ( cno == 0 ) return 0;
        // 3. 정육점번호를 Dto에 넣기
        stockDto.setCno( cno );
        // 4. Service에게 전달 후 결과 받기
        return stockService.addStock( stockDto );
    } // func end

    // [stock02] 재고수정 - updateStock()
    // 기능설명 : [ 정육점번호(세션), 재고번호, 가격 ]을 입력받아, [ 가격, 등록일 ]을 수정한다.
    // method : PUT, URL : /stock/update
    // 매개변수 : StockDto, session
    // 반환타입 : boolean -> 성공 : true / 실패 : false
    @PutMapping("/update")
    public boolean updateStock( @RequestBody StockDto stockDto, HttpSession session ){
        System.out.println("StockController.updateStock");
        System.out.println("stockDto = " + stockDto);

        // 1. 세션정보에서 정육점번호 가져오기 -> SessionService 메소드화
        int cno = sessionService.getSessionNo( "loginCno", session );
        // 2. 정육점번호를 Dto에 넣기
        stockDto.setCno( cno );
        // 3. 현재날짜를 Dto에 넣기
        String today = LocalDateTime.now().toString();
        stockDto.setSdate( today );
        // 4. Service에게 전달 후 결과 반환
        return stockService.updateStock( stockDto );
    } // func end

    // [stock03] 재고삭제 - deleteStock()
    // 기능설명 : [ 정육점번호(세션+입력), 재고번호 ]을 받아, 해당하는 회사가 등록한 재고라면, 삭제한다.
    // method : DELETE, URL : /stock/delete
    // 매개변수 : StockDto, session
    // 반환타입 : boolean -> 성공 : true / 실패 : false
    @DeleteMapping("/delete")
    public boolean deleteStock( @RequestParam int cno, @RequestParam int sno, HttpSession session ){
        System.out.println("StockController.deleteStock");
        // 1. 세션정보에서 정육점번호 가져오기
        int loginCno = sessionService.getSessionNo( "loginCno", session );
        // 2. Service에게 전달할 Dto 만들기
        StockDto stockDto = new StockDto();
        // 3. 정육점번호(세션 = 입력)를 비교하기
        if ( loginCno == stockDto.getCno() ){
            // 4-1. 정육점번호가 같으면, Dao에게 전달 후 결과 반환하기
            // 5. Dto에 값 넣기
            stockDto.setCno( cno );
            stockDto.setSno( sno );
            stockDto.setCno( loginCno );
            // 6. Service에게 전달하고 결과 반환하기
            return stockService.deleteStock( stockDto );
        } else {
            // 4-2. 정육점번호가 다르면, false 반환하기
            return false;
        } // if end
    } // func end

    // [stock04] 정육점별 재고조회 - getStock()
    // 기능설명 : [ 정육점번호 ]를 받아, 해당하는 재고를 조회한다.
    // method : GET, URL : /stock/get
    // 매개변수 : session
    // 반환타입 : List<StockDto>
    @GetMapping("/get")
    public List<StockDto> getStock( HttpSession session ){
        System.out.println("StockController.getStock");

        // 1. 세션정보에서 정육점번호 가져오기
        int loginCno = sessionService.getSessionNo( "loginCno", session );
        // 2. 정육점번호가 없으면 메소드 종료
        if ( loginCno == 0 ) return null;
        // 3. Service에게 전달 후 결과 반환
        return stockService.getStock( loginCno );
    } // func end
} // class end