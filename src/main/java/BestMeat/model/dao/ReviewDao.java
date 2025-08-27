package BestMeat.model.dao;

import BestMeat.model.dto.ReviewDto;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

@Repository
public class ReviewDao extends Dao  {
    // 리뷰 등록 기능
    public int addReview(ReviewDto dto ){
        try{
            String sql = "insert into review(rcontent,rrank,cno) values(? , ? ,?)";
            PreparedStatement ps = conn.prepareStatement(sql , Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,dto.getRcontent());
            ps.setInt(2,dto.getRrank());
            ps.setInt(3,dto.getCno());
            int count = ps.executeUpdate();
            if (count == 1) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()){
                    int rno = rs.getInt(1);
                    return rno;
                }// if end
            }// if end
        } catch (Exception e) { System.out.println(e); }
        return 0;
    }// func end

    // 리뷰 이미지 등록 기능
    public boolean addReviewImg(int rno , String filename){
        try{
            String sql = "insert into reviewimg(rimg , rno) values( ? , ? )";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,filename);
            ps.setInt(2,rno);
            return ps.executeUpdate() == 1;
        } catch (Exception e) { System.out.println(e); }
        return false;
    }// func end





}
