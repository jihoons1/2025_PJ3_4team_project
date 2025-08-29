package BestMeat.service;

import BestMeat.model.dao.CompanyDao;
import BestMeat.model.dto.CompanyDto;
import BestMeat.model.dto.PageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyDao companyDao;
    private final PageService pageService;

    // 정육점 전체조회(페이징)
    public PageDto getCompany(int page , String key , String keyword,String order){
        int count = 10;
        int startRow = (page-1)*count;
        int totalCount;
        List<CompanyDto> list;
        if (key != null && !key.isEmpty() && keyword != null && !keyword.isEmpty()){ // 검색일때
            totalCount = companyDao.getTotalCompanySearch(key,keyword);
            list = companyDao.getCompanySearch(startRow,count,key,keyword,order);
        }else { // 검색 아닐때
            totalCount = companyDao.getTotalCompany();
            list = companyDao.getCompany(startRow,count);
        } // if end
        PageDto dto = pageService.paging( page, new ArrayList<>(list), totalCount );    // 페이징처리 메소드화

//        int totalPage = totalCount%count == 0 ? totalCount/count : totalCount/count+1;
//        int btnCount = 10;
//        int startBtn = ((page-1)/btnCount)*btnCount+1;
//        int endBtn = startBtn + btnCount -1;
//        if (endBtn > totalPage)endBtn = totalPage;
//        PageDto dto = new PageDto();
//        dto.setCurrentPage(page);       // 현재 페이지 번호
//        dto.setTotalPage(totalPage);    // 전체 페이지수
//        dto.setPerCount(count);         // 한페이지당 게시물 수
//        dto.setTotalCount(totalCount);  // 전체 게시물 수
//        dto.setStartBtn(startBtn);      // 시작 페이징 번호
//        dto.setEndBtn(endBtn);          // 끝 페이징 번호
//        dto.setData(list);              // 페이징한 자료
        return dto;
    }// func end

    // 정육점 개별조회
    public CompanyDto findCompany(int cno){
        return companyDao.findCompany(cno);
    }// func end
} // class end