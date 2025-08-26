package BestMeat.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemberDto {
    private int mno; // 회원번호
    private String mname; // 회원명
    private String mid; // id
    private String mpwd; // password
    private String mphone; // 휴대번호
    private String memail; // 메일
    private String maddress; // 주소
    private String mdate; // 요일
    private String mcheck; // 활성/비활성 쳌
    private String mimg; // img
}
