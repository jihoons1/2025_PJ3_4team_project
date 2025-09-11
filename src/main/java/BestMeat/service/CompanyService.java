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
                MemberDto mdto = memberService.getMember( mno );
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
        return dto;
    }// func end

    // 정육점 개별조회
    public CompanyDto findCompany(int cno){
        return companyDao.findCompany(cno);
    }// func end

    // 정육점 전체조회
    public List<CompanyDto> getCompanyList(){
        return companyDao.getCompanyList();
    }// func end

    public boolean addViews( int cno ){
        return companyDao.addViews(cno);
    }

    // 정육점 이름 조회 - getCname()
    // 기능설명 : 정육점번호를 받아, 해당 정육점이름을 반환한다.
    // 매개변수 : int cno
    // 반환타입 : String cname
    public String getCname( int cno ){
        return companyDao.getCname( cno );
    } // func end

    // 정육점 회원번호 조회 - getMno()
    // 기능설명 : 정육점번호를 받아, 해당 정육점의 회원번호를 반환한다.
    // 매개변수 : int cno
    // 반환타입 : int mno
    public int getMno( int cno ){
        return  companyDao.getMno( cno );
    } // func end
} // class end