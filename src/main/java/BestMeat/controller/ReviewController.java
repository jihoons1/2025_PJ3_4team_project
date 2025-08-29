package BestMeat.controller;

import BestMeat.model.dto.CompanyDto;
import BestMeat.model.dto.MemberDto;
import BestMeat.model.dto.PageDto;
import BestMeat.model.dto.ReviewDto;
import BestMeat.service.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;
    private final FileService fileService;
    private final SessionService sessionService;
    private final MapService mapService;
    private final MemberService memberService;
    private final CompanyService companyService;

    private String tableName = "review/";       // 파일 업로드 경로


    /** method : post 리뷰 등록 기능
     * @param dto
     * @param session
     * @return true : 성공 false : 실패
     *
     * 작성자 주소 와 해당하는 회사주소를
     * 위도경도 로 변환하여 거리계산시
     * 3km 이하만 작성가능하게 추가해야함
     */
    @PostMapping("/add")
    public boolean addReview(ReviewDto dto , HttpSession session){
        int mno = sessionService.getSessionNo("loginMno" , session);
        dto.setMno(mno);
        MemberDto mdto = memberService.getMember(session);
        String[] str = mdto.getMaddress().split(",");
        CompanyDto cdto = companyService.findCompany(dto.getCno());
        String[] str1 = cdto.getCaddress().split(",");
        double[] start = mapService.getLatLng(str[0]);
        double[] end = mapService.getLatLng(str1[0]);
        dto.setDistance(mapService.distance(start,end));
        if (dto.getDistance() > 3.0){
            return false;
        }// if end
        int result = reviewService.addReview(dto);
        if (result > 0 && !dto.getUploads().isEmpty() && !dto.getUploads().get(0).isEmpty()){
            for (MultipartFile file : dto.getUploads()){
                String filename = fileService.fileUpload(file, tableName );
                if (filename == null){ return false; }
                boolean result2 = reviewService.addReviewImg(dto.getRno(),filename);
                if (result2 == false){return result2; }
            }// for end
        }// if end
        return true;
    }// func end

    /**
     * method : PUT 리뷰 수정 기능
     * @param dto
     * @param session
     * @return true : 성공 false : 실패
     */
    @PutMapping("/update")
    public boolean updateReview(ReviewDto dto ,  HttpSession session){
        int mno = sessionService.getSessionNo("loginMno" , session);
        dto.setMno(mno);
        boolean result = reviewService.updateReview(dto);
        if (result  && !dto.getUploads().isEmpty() && !dto.getUploads().get(0).isEmpty()){
            for (MultipartFile file : dto.getUploads()){
                String filename = fileService.fileUpload(file, tableName );
                if (filename == null){ return false; }
                boolean result2 = reviewService.updateReviewImg(dto.getRno(),filename);
                if (result2 == false){return result2; }
            }// for end
        }// if end
        return true;
    }// func end

    // [review03] 리뷰 상세조회 - getReview()
    // 기능설명 : 정육점별 리뷰를 조회한다.
    // method : GET, URL : /review/get
    // 매개변수 : startRow, perCount, cno
    // 반환타입 : PageDto
    @GetMapping("/get")
    public PageDto getReview ( @RequestParam int cno,
                               // defaultValue : 만약 쿼리스트링 매개변수가 없으면, 기본값을 대입
                               @RequestParam( defaultValue = "1" ) int page ){
        System.out.println("ReviewController.getReview");

        return reviewService.getReview( cno, page );
    } // func end
}// class end
