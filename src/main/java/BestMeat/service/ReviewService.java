package BestMeat.service;

import BestMeat.model.dao.ReviewDao;
import BestMeat.model.dto.PageDto;
import BestMeat.model.dto.ReviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewDao reviewDao;
    private final PageService pageService;


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
    public boolean updateReviewImg(ReviewDto dto , String filename ){
        List<Integer> nolist = reviewDao.getReviewImgNo(dto.getRno());
        List<MultipartFile> dtoList = dto.getUploads();
        int sum = dtoList.size() - nolist.size();
        for (int rimgno : nolist){
            boolean result = reviewDao.updateReviewImg(dto.getRno(), filename, rimgno);
        }// for end
        if (sum > 0){
            reviewDao.addReviewImg(dto.getRno(),filename);
        }// if end
        return true;
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
    public List<ReviewDto> getMnoReview( int mno ){
        // 1. Dao로부터 mno의 리뷰 목록 받기
        List<ReviewDto> dtolist = reviewDao.getMnoReview( mno );
        for ( ReviewDto dto : dtolist ){
            List<String> img = reviewDao.getReviewImg( dto.getRno() );
            dto.setImages( img );
        }// for end
        // 2. Dao에게 전달 후, 결과 반환하기
        return dtolist;
    } // func end

    // [review05] 리뷰번호 리뷰조회 - getRnoReview()
    // 기능설명 : 리뷰번호에 해당하는 리뷰를 조회한다.
    // 매개변수 : int rno
    // 반환타입 : ReviewDto
    public ReviewDto getRnoReview( int rno ){
        return reviewDao.getRnoReview( rno );
    } // func end
}// class end
