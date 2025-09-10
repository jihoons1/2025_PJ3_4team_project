package BestMeat.controller;

import BestMeat.model.dto.PageDto;
import BestMeat.model.dto.ProductDto;
import BestMeat.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    private String tableName = "product/";       // 파일 업로드 경로

    /**
     * 제품 전체조회(페이징)
     * @param page
     * @return PageDto
     */
    @GetMapping("/get")
    public PageDto getProduct(@RequestParam(defaultValue = "1") int page){
        return productService.getProduct(page);
    }// func end

    // [product02] 모든 제품정보 조회 - getProductAll()
    // 기능설명 : 모든 제품을 조회한다.
    // method : GET, URL : /product/getAll
    // 매개변수 : X
    // 반환타입 : List<ProductDto>
    @GetMapping("/getAll")
    public List<ProductDto> getProductAll(){
        System.out.println("ProductController.getProductAll");

        return productService.getProductAll();
    } // func end

    // [product03] 카테고리별 제품조회 - getCnoProduct()
    // 기능설명 : [ 카테고리번호 ]를 받아, 해당하는 제품을 조회한다.
    // method : GET, URL : /product/getCno
    // 매개변수 : int cno
    // 반환타입 : List<ProductDto>
    @GetMapping("/getCno")
    public List<ProductDto> getCnoProduct( @RequestParam int cno ){
        System.out.println("ProductController.getCnoProduct");

        return productService.getCnoProduct( cno );
    } // func end
} // class end