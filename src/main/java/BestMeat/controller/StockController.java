package BestMeat.controller;

import BestMeat.model.dto.StockDto;
import BestMeat.service.StockService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stock")
public class StockController {
    private final StockService stockService;

    // [stock01] 재고등록 - addStock()
    // 기능설명 : [ 정육점번호(세션), 가격, 제품번호(select) ]를 받아, Stock DB에 저장한다.
    // method : POST, URL : /stock/add
    // 매개변수 : StockDto
    // 반환타입 : int
    @PostMapping("/add")
    public int addStock( @RequestBody StockDto stockDto, HttpSession session ){
        System.out.println("StockController.addStock");
        System.out.println("stockDto = " + stockDto);

        return stockService.addStock( stockDto, session );
    } // func end

    // [stock02] 재고수정 - updateStock()
    // 기능설명 : [ 정육점번호(세션), 가격 ]을 입력받아, [ 가격, 등록일 ]을 수정한다.
    // method : PUT, URL : /stock/update
    // 매개변수 : int sprice
    // 반환타입 : boolean -> 성공 : true / 실패 : false

    // [stock03] 재고삭제 - deleteStock()
    // 기능설명 : [ 정육점번호(세션) ]을 받아, 해당하는 회사가 등록한 재고라면, 삭제한다.
    // method : DELETE, URL : /stock/delete
    // 매개변수 : int cno
    // 반환타입 : boolean -> 성공 : true / 실패 : false

} // class end