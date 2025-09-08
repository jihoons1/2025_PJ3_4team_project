package BestMeat.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChattingDto {
    // 기본적인 정보
    private int chatno;         // 채팅로그
    private String message;     // 채팅내용
    private String chatdate;    // 채팅시간
    private String roomname;    // 방 이름
    private int from;           // 보내는 회원번호
    private int to;             // 받는 회원번호

    // 부가적인 정보
    private String fromname;    // 보내는 회원이름
    private String toname;      // 받는 회원번호
} // class end