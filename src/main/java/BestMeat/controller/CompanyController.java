package BestMeat.controller;

import BestMeat.model.dto.PageDto;
import BestMeat.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/company")
public class CompanyController {
    private final CompanyService companyService;

    @GetMapping("/get")
    public PageDto getCompany(@RequestParam(defaultValue = "1") int page ,
                              @RequestParam(required = false) String key ,
                              @RequestParam(required = false) String keyword ,
                              @RequestParam(required = false) String order){
        return companyService.getCompany(page,key,keyword,order);
    }// func end

//    // 정육점 개별조회
//    @GetMapping("/find")
//    public CompanyDto findCompany(@RequestParam int cno){
//        return companyService.findCompany(cno);
//    }// func end


} // class end