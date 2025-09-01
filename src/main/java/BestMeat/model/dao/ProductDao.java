package BestMeat.model.dao;

import BestMeat.model.dto.ProductDto;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductDao extends Dao {

    // 고기(제품) 전체 조회
    public List<ProductDto> getProduct(int startRow , int count){
        List<ProductDto> list = new ArrayList<>();
        try{
            String sql = "select * from product p inner join category c using ( cno ) limit ? , ? ";
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
                dto.setCname( rs.getString("cname") );
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

    // [product03] 카테고리별 제품조회 - getCnoProduct()
    // 기능설명 : [ 카테고리번호 ]를 받아, 해당하는 제품을 조회한다.
    // 매개변수 : int cno
    // 반환타입 : List<ProductDto>
    public List<ProductDto> getCnoProduct( int cno ){
        List<ProductDto> productDtoList = new ArrayList<>();
        try {
            String SQL = "select * from product where cno = ?";
            PreparedStatement ps = conn.prepareStatement( SQL );
            ps.setInt( 1, cno );
            ResultSet rs = ps.executeQuery();
            while( rs.next() ){
                ProductDto productDto = new ProductDto();
                productDto.setCno( rs.getInt("cno") );
                productDto.setPno( rs.getInt("pno") );
                productDto.setPname( rs.getString("pname") );
                productDto.setPimg( rs.getString("pimg") );
                productDtoList.add( productDto );
            } // while end
        } catch ( SQLException e ){
            System.out.println("[product03] SQL 기재 실패");
        } // try-catch end
        return productDtoList;
    } // func end
} // class end