package BestMeat.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CompanyDto {
    // 기본 테이블 정보
    private int cno;            // 정육점번호
    private String cimg;        // 정육점이미지
    private String cname;       // 정육점명
    private String caddress;    // 정육점주소
    private int mno;            // 회원번호 - 정육점 주인

    // 부가적인 정보

} // class end