package BestMeat.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NoticeDto {
    // 기본 테이블 정보
    private int nno;        // 알림번호
    private int mno;        // 회원번호
    private int pno;        // 제품번호
    private int nprice;     // 알림설정가격
    private int ncheck;     // 문자전송여부
    private String ndate;   // 알림등록일

    // 부가적인 정보

} // class end