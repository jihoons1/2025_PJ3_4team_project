package BestMeat.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor @AllArgsConstructor @Data
public class CompanyDto {
    private int cno;
    private String cimg;
    private String cname;
    private String caddress;
    private int mno;
    private MultipartFile uploads;
    private int sprice;
    private String pname;
    private double rrank;
    private double distance;
    private double lat;
    private double lng;
    private int views;
}
