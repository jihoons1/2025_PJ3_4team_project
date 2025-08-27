package BestMeat.controller;

import BestMeat.model.dto.ReviewDto;
import BestMeat.service.FileService;
import BestMeat.service.ReviewService;
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


    /** method : post 리뷰 등록 기능
     * @param dto
     * @param session
     * @return true : 성공 false : 실패
     */
    @PostMapping("/add")
    public boolean addReview(ReviewDto dto , HttpSession session){
        if (session == null || session.getAttribute("loginMno") == null){
            return false;
        }// if end
        int mno = (int) session.getAttribute("loginMno");
        dto.setMno(mno);
        int result = reviewService.addReview(dto);
        if (result > 0 && !dto.getUploads().isEmpty() && !dto.getUploads().get(0).isEmpty()){
            for (MultipartFile file : dto.getUploads()){
                String filename = fileService.fileUpload(file);
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
     * @param oldname
     * @param session
     * @return true : 성공 false : 실패
     */
    @PutMapping("/update")
    public boolean updateReview(ReviewDto dto , String oldname , HttpSession session){
        if (session == null || session.getAttribute("loginMno") == null){
            return false;
        }// if end
        int mno = (int) session.getAttribute("loginMno");
        dto.setMno(mno);
        boolean result = reviewService.updateReview(dto);
        if (result  && !dto.getUploads().isEmpty() && !dto.getUploads().get(0).isEmpty()){
            for (MultipartFile file : dto.getUploads()){
                String filename = fileService.fileUpload(file);
                if (filename == null){ return false; }
                boolean result2 = reviewService.updateReviewImg(dto.getRno(),filename,oldname);
                if (result2 == false){return result2; }
            }// for end
        }// if end
        return true;
    }// func end

}// class end
