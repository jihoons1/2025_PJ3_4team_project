package BestMeat.controller;

import BestMeat.model.dto.ReviewDto;
import BestMeat.service.ReviewService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;

    // 리뷰 등록 기능
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
                String filename = fileservice.fileUpload(file);
            }
        }

    }

}
