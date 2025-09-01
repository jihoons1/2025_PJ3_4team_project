package BestMeat.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@NoArgsConstructor @AllArgsConstructor @Data
public class ReviewDto {
    private int rno;
    private String rcontent;
    private int rrank;
    private String rdate;
    private int cno;
    private int mno;
    private boolean check;
    private List<MultipartFile> uploads;
    private List<String> images;
    private double distance;

    // 부가적인 정보
    private String mname;
    private String cname;       // 정육점명
} // class end