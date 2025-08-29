package BestMeat.controller;

import BestMeat.model.dto.CompanyDto;
import BestMeat.model.dto.MemberDto;
import BestMeat.model.dto.PageDto;
import BestMeat.service.CompanyService;
import BestMeat.service.MapService;
import BestMeat.service.MemberService;
import BestMeat.service.SessionService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/company")
public class CompanyController {
    private final CompanyService companyService;
    private final SessionService sessionService;
    private final MemberService memberService;
    private final MapService mapService;


    /** 정육점 전체조회 기능(검색,정렬)
     * @param page
     * @param key
     * @param keyword
     * @param order
     * @return List
     */
    @GetMapping("/get")
    public PageDto getCompany(@RequestParam(defaultValue = "1") int page ,
                              @RequestParam(required = false) String key ,
                              @RequestParam(required = false) String keyword ,
                              @RequestParam(required = false) String order ,
                              HttpSession session){
        PageDto pdto = companyService.getCompany(page,key,keyword,order);
        int mno = sessionService.getSessionNo("loginMno",session);
        if (mno > 0){ // 로그인한 회원이 검색할시
            MemberDto mdto = memberService.getMember(session);
            List<CompanyDto> clist = (List<CompanyDto>) pdto.getData();
            String[] str = mdto.getMaddress().split(",");
            double[] start = mapService.getLatLng(str[0]);
            for (CompanyDto cdto : clist){ // 거리계산 후 dto distance 멤버변수에 저장
                String[] str1 = cdto.getCaddress().split(",");
                double[] end = mapService.getLatLng(str1[0]);
                double result = mapService.distance(start,end);
                cdto.setDistance(result);
            }// for end
            if ("distance".equals(order)){ // 거리가까운순
                clist.sort(Comparator.comparingDouble(CompanyDto::getDistance));
            }// if end
        }// if end
        return pdto;
    }// func end

//    // 정육점 개별조회
//    @GetMapping("/find")
//    public CompanyDto findCompany(@RequestParam int cno){
//        return companyService.findCompany(cno);
//    }// func end

} // class end