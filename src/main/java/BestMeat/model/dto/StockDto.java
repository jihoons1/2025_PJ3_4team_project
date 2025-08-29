package BestMeat.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StockDto {
    // 기본 테이블 정보
    private int sno;        // 재고번호
    private int sprice;     // 등록가격
    private String sdate;   // 등록일
    private int cno;        // 정육점번호
    private int pno;        // 제품번호

    // 부가적인 정보
    private String pname;   // 제품명
} // class end