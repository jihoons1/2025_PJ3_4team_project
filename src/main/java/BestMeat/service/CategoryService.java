package BestMeat.service;

import BestMeat.model.dao.CategoryDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryDao categoryDao;


} // class end