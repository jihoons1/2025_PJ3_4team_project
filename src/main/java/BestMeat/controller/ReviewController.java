package BestMeat.controller;

import BestMeat.model.dto.ReviewDto;
import BestMeat.service.FileService;
import BestMeat.service.ReviewService;
import BestMeat.service.SessionService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;
    private final FileService fileService;
    private final SessionService sessionService;

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

}// class end
