package BestMeat.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReviewDto {
    // 기본 테이블 정보
    private int rno;            // 리뷰번호
    private String rcontent;    // 리뷰내용
    private int rrank;          // 리뷰평점
    private String rdate;       // 리뷰등록일
    private int cno;            // 정육점번호
    private int mno;            // 회원번호

    // 부가적인 정보

} // class end