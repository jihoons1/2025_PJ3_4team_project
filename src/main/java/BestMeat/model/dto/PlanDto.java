package BestMeat.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PlanDto {
    // 기본적인 정보
    private int planno;         // 결제로그번호
    private int cno;            // 정육점번호
    private String banner;      // 배너이미지명
    private String startdate;   // 결제일 == 시작일
    private String enddate;     // 종료일
    // 부가적인 정보
    private MultipartFile upload; // 파일 받기
    private int mno;            // 해당 정육점의 회원번호
} // class end