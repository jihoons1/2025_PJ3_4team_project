package BestMeat.controller;

import BestMeat.model.dto.CategoryDto;
import BestMeat.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    // [category01] 모든 카테고리 조회 - getCategory()
    // 기능설명 : 모든 카테고리를 조회한다.
    // method : GET, URL : /category/get
    // 매개변수 : X
    // 반환타입 : List<CategoryDto>
    @GetMapping("/get")
    public List<CategoryDto> getCategory(){
        System.out.println("CategoryController.getCategory");

        return categoryService.getCategory();
    } // func end
} // class end