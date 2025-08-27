package BestMeat.model.dao;

import BestMeat.model.dto.ProductDto;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductDao extends Dao {

    // 고기(제품) 전체 조회
    public List<ProductDto> getProduct(int startRow , int count){
        List<ProductDto> list = new ArrayList<>();
        try{
            String sql = "select * from product limit ? , ? ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,startRow);
            ps.setInt(2,count);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                ProductDto dto = new ProductDto();
                dto.setCno(rs.getInt("cno"));
                dto.setPno(rs.getInt("pno"));
                dto.setPname(rs.getString("pname"));
                dto.setPimg(rs.getString("pimg"));
                list.add(dto);
            }// while end
        } catch (Exception e) { System.out.println(e); }
        return list;
    }// func end

    // 전체 제품 수 조회
    public int getTotalProduct(){
        try{
            String sql = "select count(*) from product";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next())return rs.getInt(1);
        } catch (Exception e) { System.out.println(e); }
        return 0;
    }// func end

} // class end