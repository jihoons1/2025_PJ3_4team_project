package BestMeat.service;

import BestMeat.model.dao.ProductDao;
import BestMeat.model.dto.PageDto;
import BestMeat.model.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductDao productDao;
    private final PageService pageService;

    // 고기(제품) 전체 조회
    public PageDto getProduct(int page){
        int count = 10;
        int startRow = (page - 1) * count;
        int totalCount = productDao.getTotalProduct();
        List<ProductDto> list = productDao.getProduct(startRow , count);
        PageDto dto = pageService.paging( page, new ArrayList<>(list), totalCount );    // 페이징처리 메소드화
        return dto;
    }// func end

    // [product03] 카테고리별 제품조회 - getCnoProduct()
    // 기능설명 : [ 카테고리번호 ]를 받아, 해당하는 제품을 조회한다.
    // 매개변수 : int cno
    // 반환타입 : List<ProductDto>
    public List<ProductDto> getCnoProduct( int cno ){
        return productDao.getCnoProduct( cno );
    } // func end
} // class end