package BestMeat.controller;

import BestMeat.model.dto.PageDto;
import BestMeat.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

} // class end