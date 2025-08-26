package BestMeat.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDto {
    // 기본 테이블 정보
    private int pno;        // 제품번호
    private String pname;   // 부위명
    private int cno;        // 카테고리번호
    private String pimg;    // 제품이미지

    // 부가적인 정보

} // class end