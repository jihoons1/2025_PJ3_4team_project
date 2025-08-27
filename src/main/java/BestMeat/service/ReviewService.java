package BestMeat.service;

import BestMeat.model.dao.ReviewDao;
import BestMeat.model.dto.ReviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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


}
