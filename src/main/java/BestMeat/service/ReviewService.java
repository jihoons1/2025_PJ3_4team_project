package BestMeat.service;

import BestMeat.model.dao.ReviewDao;
import BestMeat.model.dto.ReviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewDao reviewDao;

    // 리뷰 등록 기능
    public int addReview(ReviewDto dto ){
        return reviewDao.addReview(dto);
    }// func end

    // 리뷰 이미지 등록 기능
    public boolean addReviewImg(int rno , String filename){
        return reviewDao.addReviewImg(rno, filename);
    }// func end

    // 리뷰 수정 기능
    public boolean updateReview(ReviewDto dto){
         return reviewDao.updateReview(dto);
    }// func end

    // 리뷰 이미지 수정기능
    public boolean updateReviewImg(int rno , String filename , List<Integer> nolist){
        for (int rimgno : nolist){
            boolean result = reviewDao.updateReviewImg(rno, filename, rimgno);
            if (result) return true;
        }// for end
        return false;
    }// func end


}// class end
