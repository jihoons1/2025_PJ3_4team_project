package BestMeat.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PointDto {
    // DB 기준 기본 정보
    private int plno;           // 지급번호
    private int mno;            // 회원번호
    private int plpoint;        // 포인트
    private String plcomment;   // 지급사유
    private String pldate;      // 지급일시

    // 부가적인 정보
} // class end