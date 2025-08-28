package BestMeat.service;

import BestMeat.model.dao.CompanyDao;
import jakarta.servlet.http.HttpSession;
import BestMeat.model.dto.CompanyDto;
import BestMeat.model.dto.PageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyDao companyDao;

    // [company01] 정육점번호 세션 확인
    // 기능설명 : 세션을 받아, 정육점번호를 반환한다.
    // 매개변수 : session
    // 반환타입 : int -> 성공 : 정육점번호, 실패 : 0
    public int getLoginCno( HttpSession session ){
        // 1. 세션 가져오기
        Object loginCno = session.getAttribute( "loginCno" );
        // 2. 세선졍보에서 정육점번호 가져오기
        int cno = loginCno == null ? 0 : (int) loginCno;
        // 3. 정육점번호 반환하기
        return cno;
    } // func end


    // 정육점 전체조회(페이징)
    public PageDto getCompany(int page , String key , String keyword){
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
    }// func end


} // class end