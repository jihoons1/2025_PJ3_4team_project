package BestMeat.model.dao;

import BestMeat.model.dto.CategoryDto;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CategoryDao extends Dao {
    // [category01] 모든 카테고리 조회 - getCategory()
    // 기능설명 : 모든 카테고리를 조회한다.
    // 매개변수 : X
    // 반환타입 : List<CategoryDto>
    public List<CategoryDto> getCategory() {
        List<CategoryDto> list = new ArrayList<>();
        try {
            String SQL = "select * from category";
            PreparedStatement ps = conn.prepareStatement( SQL );
            ResultSet rs = ps.executeQuery();
            while ( rs.next() ){
                CategoryDto category = new CategoryDto();
                category.setCno( rs.getInt("cno") );
                category.setCname( rs.getString("cname") );
                list.add( category );
            } // while end
        } catch ( SQLException e ){
            System.out.println("[category01] SQL 기재 실패" + e );
        } // try-catch end
        return list;
    } // func end
} // class end