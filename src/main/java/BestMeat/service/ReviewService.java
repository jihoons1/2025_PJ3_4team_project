package BestMeat.service;

import BestMeat.model.dao.ReviewDao;
import BestMeat.model.dto.PageDto;
import BestMeat.model.dto.ReviewDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewDao reviewDao;
    private final PageService pageService;
    private final SessionService sessionService;


    // [1-1] 리뷰 등록 기능
    public int addReview(ReviewDto dto ){
        return reviewDao.addReview(dto);
    }// func end

    // [1-2] 리뷰 이미지 등록 기능
    public boolean addReviewImg(int rno , String filename){
        return reviewDao.addReviewImg(rno, filename);
    }// func end

    // [2-1]리뷰 수정 기능
    public boolean updateReview(ReviewDto dto){
         return reviewDao.updateReview(dto);
    }// func end

    // [2-2]리뷰 이미지 수정기능
    public boolean updateReviewImg(int rno , String filename ){
        List<Integer> nolist = reviewDao.getReviewImgNo(rno);
        for (int rimgno : nolist){
            boolean result = reviewDao.updateReviewImg(rno, filename, rimgno);
            return result;
        }// for end
        return false;
    }// func end

    // [review03] 정육점별 리뷰조회 - getReview()
    // 기능설명 : 정육점별 리뷰를 조회한다.
    // 매개변수 : startRow, perCount, cno
    // 반환타입 : PageDto
    // cno : 정육점번호, startRow : 조회를 시작할 인덱스번호, perCount :  페이지당 자료 개수
    public PageDto getReview( int cno, int page ){
        // 1. 페이지당 자료개수 선언하기
        int perCount = 10;
        // 2. 페이지당 자료개수에 따른 조회시작 인덱스번호 만들기
        int startRow = ( page - 1 ) * perCount;
        // 3. 정육점별 리뷰 개수 반환받기
        int totalCount = reviewDao.getReviewCount(cno);
        // 4. 정육점별 리뷰 반환받기
        List<ReviewDto> reviewDtoList = reviewDao.getReview( cno, startRow, totalCount );
        for (ReviewDto rdto : reviewDtoList){
            List<String> images = reviewDao.getReviewImg(rdto.getRno());
            rdto.setImages(images);
        }// for end
        // 5. 페이징처리된 pageDto 반환하기
        return pageService.paging( page, new ArrayList<>(reviewDtoList), totalCount );
    } // func end

    // [review04] 회원별 리뷰조회 - getMnoReview()
    public List<ReviewDto> getMnoReview( HttpSession session ){
        // 1. 세션정보에서 회원번호 가져오기
        int mno = sessionService.getSessionNo( "loginMno", session );
        // 2. 회원번호가 0이면 메소드 종료
        if ( mno == 0 ) return null;
        List<ReviewDto> dtolist = reviewDao.getMnoReview(mno);
        for (ReviewDto dto : dtolist){
            List<String> img = reviewDao.getReviewImg(dto.getRno());
            dto.setImages(img);
        }// for end
        // 3. Dao에게 전달 후, 결과 반환하기
        return dtolist;
    } // func end
}// class end
