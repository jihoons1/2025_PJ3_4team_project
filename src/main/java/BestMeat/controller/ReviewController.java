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

import java.util.List;

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
        MemberDto mdto = memberService.getMember( mno );
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
                boolean result2 = reviewService.addReviewImg(result,filename);
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
        if (result  && dto.getUploads() != null && !dto.getUploads().isEmpty() && !dto.getUploads().get(0).isEmpty()){
            for (MultipartFile file : dto.getUploads()){
                String filename = fileService.fileUpload(file, tableName );
                System.out.println(filename);
                if (filename == null){ return false; }
                boolean result2 = reviewService.updateReviewImg(dto,filename);
                if (result2 == false){return result2; }
            }// for end
        }// if end
        return true;
    }// func end

    // [review03] 정육점별 리뷰조회 - getReview()
    // 기능설명 : 정육점별 리뷰를 조회한다.
    // method : GET, URL : /review/get
    // 매개변수 : startRow, perCount, cno
    // 반환타입 : PageDto
    @GetMapping("/get")
    public PageDto getReview ( @RequestParam int cno,
                               // defaultValue : 만약 쿼리스트링 매개변수가 없으면, 기본값을 대입
                               @RequestParam( defaultValue = "1" ) int page ,
                               HttpSession session){
        int mno = sessionService.getSessionNo("loginMno", session );
        PageDto pageDto =  reviewService.getReview( cno, page );
        List<ReviewDto> list = (List<ReviewDto>) pageDto.getData();
        for (ReviewDto dto : list){
            if (mno == dto.getMno()){
                dto.setCheck(true);
            }// if end
        }// for end
        return pageDto;
    } // func end

    // [review04] 회원별 리뷰조회 - getMnoReview()
    // 기능설명 : 회원별 리뷰를 조회한다.
    // method : GET, URL : /review/getMno
    // 매개변수 : session
    // 반환타입 : PageDto
    @GetMapping("/getMno")
    public List<ReviewDto> getMnoReview( HttpSession session ){
        System.out.println("ReviewController.getMnoReview");
        // 1. 세션정보에서 회원번호 가져오기
        int mno = sessionService.getSessionNo( "loginMno", session );
        // 2. 회원번호가 0이면 메소드 종료
        if ( mno == 0 ) return null;
        // 3. Service에게 전달 후 결과 반환
        return reviewService.getMnoReview( mno );
    } // func end

    // [review05] 리뷰번호 리뷰조회 - getRnoReview()
    // 기능설명 : 리뷰번호에 해당하는 리뷰를 조회한다.
    // method : GET, URL : /review/getRno
    // 매개변수 : int rno
    // 반환타입 : ReviewDto
    @GetMapping("/getRno")
    public ReviewDto getRnoReview( @RequestParam int rno ){
        System.out.println("ReviewController.getRnoReview");

        return reviewService.getRnoReview( rno );
    } // func end

    // [review06-1] 리뷰번호 리뷰삭제 - deleteReview()
    // 기능설명 : 리뷰번호에 해당하는 리뷰를 삭제한다
    // 매개변수 : int rno
    // 반환타입 : boolean
    @DeleteMapping("/delete")
    public boolean deleteReview(int rno , HttpSession session){
        int mno = sessionService.getSessionNo("loginMno",session);
        boolean result = reviewService.deleteReview(rno,mno);
        if (result){
            boolean result2 = reviewService.deleteReviewImg(rno);
            return result2;
        }// if end
        return result;
    }// func end

}// class end
