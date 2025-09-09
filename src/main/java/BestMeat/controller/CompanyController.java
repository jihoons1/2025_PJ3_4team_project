package BestMeat.controller;

import BestMeat.model.dto.CompanyDto;
import BestMeat.model.dto.MemberDto;
import BestMeat.model.dto.PageDto;
import BestMeat.model.dto.PointDto;
import BestMeat.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/company")
public class CompanyController {
    private final CompanyService companyService;
    private final QRService qrService;
    private final MemberService memberService;
    private final SessionService sessionService;
    private final MapService mapService;


    /** 정육점 전체조회 기능(검색,정렬)
     * @param page
     * @param key
     * @param keyword
     * @param order
     * @return List
     */
    @GetMapping("/get")
    public PageDto getCompany(@RequestParam(defaultValue = "1") int page ,
                              @RequestParam(required = false) String key ,
                              @RequestParam(required = false) String keyword ,
                              @RequestParam(required = false) String order ,
                              HttpSession session){
        PageDto pdto = companyService.getCompany(page,key,keyword,order,session);
        return pdto;
    }// func end

    // 정육점 개별조회
    @GetMapping("/find")
    public CompanyDto findCompany(@RequestParam int cno){
        return companyService.findCompany(cno);
    }// func end

    // QR 코드 생성
    @GetMapping("/qrcode")
    public ResponseEntity<byte[]> bulidQR(@RequestParam int cno , HttpSession session){
        int mno = sessionService.getSessionNo("loginMno",session);
        // 회원객체 정육점객체 꺼내기
        MemberDto mdto = memberService.getMember(mno);
        CompanyDto cdto = companyService.findCompany(cno);
        // 회원주소기반 위도경도 , 정육점주소기반 위도경도
        double[] str = mapService.getLatLng(mdto.getMaddress());
        double[] end = mapService.getLatLng(cdto.getCaddress());
        // 네이버 위도경도 로 변환
        Map<String, Double> strMap = qrService.latLonToWebMercator(str[1],str[0]);
        Map<String, Double> endMap = qrService.latLonToWebMercator(end[1],end[0]);
        String startPoint = strMap.get("x") + "," +strMap.get("y");
        String endPoint = endMap.get("x") + "," + endMap.get("y");
        //String content = "https://map.naver.com/p/directions/"+startPoint+"/"+endPoint+"/-/car"; // 웹 버전
        String content = "https://qp.map.naver.com/end-quick-path/"+startPoint+"/"+endPoint+"/-/car"; // 앱버전
        byte[] qrCode = qrService.BuildQR(content);
        // ResponseEntity를 이용해 HTTP 응답 반환
        // - 상태코드: 200 OK
        // - Content-Type: image/png (브라우저/클라이언트가 이미지로 인식하게 함)
        // - Body: QR 코드 이미지 데이터 (byte[])
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(qrCode);
    }// func end

    // 정육점 전체조회
    @GetMapping("/getAll")
    public List<CompanyDto> getCompanyList(){
        return companyService.getCompanyList();
    }// func end




} // class end