package BestMeat.service;

import BestMeat.model.dao.CompanyDao;
import BestMeat.model.dao.ProductDao;
import BestMeat.model.dao.ReviewDao;
import BestMeat.model.dto.CompanyDto;
import BestMeat.model.dto.PageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PageService {
    private final CompanyDao companyDao;
    private final ProductDao productDao;
    private final ReviewDao reviewDao;

    // [page01] 페이징처리
    public PageDto paging( String tableName, int page , String key , String keyword ){
        int count = 10;
        int startRow = (page-1)*count;
        int totalCount;
        List<CompanyDto> list;
        if (key != null && !key.isEmpty() && keyword != null && !keyword.isEmpty()){ // 검색일때
            totalCount = companyDao.getTotalCompanySearch(key,keyword);
            list = companyDao.getCompanySearch(startRow,count,key,keyword);
        }else { // 검색 아닐때
            totalCount = companyDao.getTotalCompany();
            list = companyDao.getCompany(startRow,count);
        }
        int totalPage = totalCount%count == 0 ? totalCount/count : totalCount/count+1;
        int btnCount = 10;
        int startBtn = ((page-1)/btnCount)*btnCount+1;
        int endBtn = startBtn + btnCount -1;
        if (endBtn > totalPage)endBtn = totalPage;
        PageDto dto = new PageDto();
        dto.setCurrentPage(page);       // 현재 페이지 번호
        dto.setTotalPage(totalPage);    // 전체 페이지수
        dto.setPerCount(count);         // 한페이지당 게시물 수
        dto.setTotalCount(totalCount);  // 전체 게시물 수
        dto.setStartBtn(startBtn);      // 시작 페이징 번호
        dto.setEndBtn(endBtn);          // 끝 페이징 번호
        dto.setData(list);              // 페이징한 자료
        return dto;
    }

} // func end