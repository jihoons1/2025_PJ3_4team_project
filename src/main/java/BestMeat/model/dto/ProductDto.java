package BestMeat.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor @AllArgsConstructor @Data
public class ProductDto {
    private int pno;
    private String pname;
    private int cno;
    private String pimg;
    private MultipartFile uploads;
} // class end