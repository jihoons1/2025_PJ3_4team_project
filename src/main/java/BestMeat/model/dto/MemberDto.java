package BestMeat.model.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemberDto {
    // 기본 테이블 정보
    private int mno;            // 회원번호
    private String mname;       // 회원명
    private String mid;         // 회원아이디
    private String mpwd;        // 회원비밀번호
    private String mphone;      // 회원전화번호
    private String memail;      // 회원이메일
    private String maddress;    // 회원주소
    private String mdate;       // 회원 등록일
    private String mcheck;      // 활성/비활성 쳌
    private String mimg;        // 회원프로필 이미지명
    private int cno;    // 정육저 번호

    // 부가적인 정보
    private MultipartFile upload; // 파일 받기


} // class end