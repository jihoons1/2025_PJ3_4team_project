package BestMeat.controller;

import BestMeat.model.dto.StockDto;
import BestMeat.service.StockService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stock")
public class StockController {
    private final StockService stockService;

    // [stock01] 재고등록 - addStock()
    // 기능설명 : [ 정육점번호(세션), 가격, 제품번호(select) ]를 받아, Stock DB에 저장한다.
    // method : POST, URL : /stock/add
    // 매개변수 : StockDto, session
    // 반환타입 : int
    @PostMapping("/add")
    public int addStock( @RequestBody StockDto stockDto, HttpSession session ){
        System.out.println("StockController.addStock");
        System.out.println("stockDto = " + stockDto);

        return stockService.addStock( stockDto, session );
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

        return stockService.updateStock( stockDto, session );
    } // func end

    // [stock03] 재고삭제 - deleteStock()
    // 기능설명 : [ 정육점번호(세션+입력), 재고번호 ]을 받아, 해당하는 회사가 등록한 재고라면, 삭제한다.
    // method : DELETE, URL : /stock/delete
    // 매개변수 : StockDto, session
    // 반환타입 : boolean -> 성공 : true / 실패 : false
    @DeleteMapping("/delete")
    public boolean deleteStock( @RequestParam int cno, @RequestParam int sno, HttpSession session ){
        System.out.println("StockController.deleteStock");
        // 1. Service에게 전달할 Dto 만들기
        StockDto stockDto = new StockDto();
        // 2. Dto에 값 넣기
        stockDto.setCno( cno );
        stockDto.setSno( sno );
        // 3. Service에게 전달하고 결과 반환하기
        return stockService.deleteStock( stockDto, session );
    } // func end

    // [stock04] 정육점별 재고조회 - getStock()
    // 기능설명 : [ 정육점번호 ]를 받아, 해당하는 재고를 조회한다.
    // method : GET, URL : /stock/get
    // 매개변수 : session
    // 반환타입 : List<StockDto>
    @GetMapping("/get")
    public List<StockDto> getStock( HttpSession session ){
        System.out.println("StockController.getStock");

        return stockService.getStock( session );
    } // func end
} // class end