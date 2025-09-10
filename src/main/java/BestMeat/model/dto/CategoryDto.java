package BestMeat.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryDto {
    // 기본적인 정보
    private int cno;        // 카테고리 번호
    private String cname;   // 고기명
} // class end