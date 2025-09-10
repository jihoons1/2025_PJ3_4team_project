package BestMeat.service;

import BestMeat.model.dao.CategoryDao;
import BestMeat.model.dto.CategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryDao categoryDao;

    // [category01] 모든 카테고리 조회 - getCategory()
    // 기능설명 : 모든 카테고리를 조회한다.
    // 매개변수 : X
    // 반환타입 : List<CategoryDto>
    public List<CategoryDto> getCategory(){
        return categoryDao.getCategory();
    } // func end
} // class end