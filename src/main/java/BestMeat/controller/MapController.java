package BestMeat.controller;

import BestMeat.model.dto.CompanyDto;
import BestMeat.service.CompanyService;
import BestMeat.service.MapService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping("/map")
public class MapController {
    private final MapService mapService;
    private final CompanyService companyService;

    public static List<CompanyDto> list = null;

    // [map01] 정육점별 위도경도 조회 - getLatLng()
    // 기능설명 : [ 정육점주소 ]를 받아, 해당하는 주소의 위도경도를 조회한다.
    // method : GET, URL : /map/getLatLng
    // 매개변수 : String caddress
    // 반환타입 : Map< String, Double >
    @GetMapping("/getLatLng")
    public Map< String, Double > getLatLng( @RequestParam String caddress ){
        System.out.println("MapController.getLatLng");

        // 1. Service에게 주소 전달하고 결과받기
        double[] result = mapService.getLatLng( caddress );
        System.out.println("result = " + Arrays.toString(result));
        // 2. 반환할 Map 만들어서 값 넣기
        Map< String, Double > map = new HashMap<>();
        map.put( "lat", result[1] );    // 위도 넣기
        map.put( "lng", result[0] );    // 경도 넣기
        // 3. 최종적으로 Map 반환하기
        return map;
    } // func end

    // 모든 정육점 위도경도 조회
    @GetMapping("/latlngList")
    public List<CompanyDto> getLatLngList (){
        System.out.println("MapController.getLatLngList");
        if (list == null){
            list = new ArrayList<>();
            List<CompanyDto> clist = companyService.getCompanyList();
            for (CompanyDto dto : clist){
                String[] str1 = dto.getCaddress().split(",");
                double[] darray = mapService.getLatLng(str1[0]);
                dto.setLat(darray[1]);
                dto.setLng(darray[0]);
                list.add(dto);
            }// for end
        }// if end
        return list;
    }// func end
} // class end