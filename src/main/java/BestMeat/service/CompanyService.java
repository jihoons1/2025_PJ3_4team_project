package BestMeat.service;

import BestMeat.model.dao.CompanyDao;
import BestMeat.model.dto.CompanyDto;
import BestMeat.model.dto.MemberDto;
import BestMeat.model.dto.PageDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyDao companyDao;
    private final PageService pageService;
    private final SessionService sessionService;
    private final MemberService memberService;
    private final MapService mapService;

    // 정육점 전체조회(페이징)
    public PageDto getCompany(int page , String key , String keyword, String order , HttpSession session){
        int count = 10;
        int startRow = (page-1)*count;
        int totalCount;
        List<CompanyDto> list;
        int mno = sessionService.getSessionNo("loginMno",session);
        if (key != null && !key.isEmpty() && keyword != null && !keyword.isEmpty()){ // 검색일때
            totalCount = companyDao.getTotalCompanySearch(key,keyword);
            list = companyDao.getCompanySearch(key,keyword,order);
            if (mno > 0){ // 로그인한 회원이 검색할시
                MemberDto mdto = memberService.getMember(session);
                String[] str = mdto.getMaddress().split(",");
                double[] start = mapService.getLatLng(str[0]);
                for (CompanyDto cdto : list){ // 거리계산 후 dto distance 멤버변수에 저장
                    String[] str1 = cdto.getCaddress().split(",");
                    double[] end = mapService.getLatLng(str1[0]);
                    double result = mapService.distance(start,end);
                    cdto.setDistance(result);
                }// for end
                if ("distance".equals(order)){ // 거리가까운순
                    list.sort(Comparator.comparingDouble(CompanyDto::getDistance));
                }// if end
                int endRow = Math.min(startRow + count, list.size());
                list = list.subList(startRow, endRow);
            }// if end
        }else { // 검색 아닐때
            totalCount = companyDao.getTotalCompany();
            list = companyDao.getCompany(startRow,count,order);
        } // if end
        PageDto dto = pageService.paging( page, new ArrayList<>(list), totalCount );    // 페이징처리 메소드화

        //int totalPage = totalCount % count == 0 ? totalCount / count : totalCount / count + 1;
        //int btnCount = 10;
        //int startBtn = ((page-1)/btnCount)*btnCount+1;
        //int endBtn = startBtn + btnCount -1;
        //if (endBtn > totalPage)endBtn = totalPage;
        //PageDto dto = new PageDto();
        //dto.setCurrentPage(page);       // 현재 페이지 번호
        //dto.setTotalPage(totalPage);    // 전체 페이지수
        //dto.setPerCount(count);         // 한페이지당 게시물 수
        //dto.setTotalCount(totalCount);  // 전체 게시물 수
        //dto.setStartBtn(startBtn);      // 시작 페이징 번호
        //dto.setEndBtn(endBtn);          // 끝 페이징 번호
        //dto.setData(list);              // 페이징한 자료
        return dto;
    }// func end

    // 정육점 개별조회
    public CompanyDto findCompany(int cno){
        return companyDao.findCompany(cno);
    }// func end
} // class end