package BestMeat.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AlarmDto {
    // 기본적인 정보
    private int ano;            // 푸시알림번호
    private int mno;            // 회원번호
    private String amessage;    // 푸시알림메시지
    private boolean acheck;     // 확인여부
    private String etc;         // 기타정보
    // 부가적인 정보
    private String atype;       // 알림타입 -> chat, stock ···
} // class end