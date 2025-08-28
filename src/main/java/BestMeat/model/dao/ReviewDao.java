package BestMeat.model.dao;

import BestMeat.model.dto.ReviewDto;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReviewDao extends Dao  {
    // [1-1] 리뷰 등록 기능
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

    // [1-2] 리뷰 이미지 등록 기능
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

    // [2-1] 리뷰 수정 기능
    public boolean updateReview(ReviewDto dto){
        try{
            String sql = "update review set rcontent = ? , rrank = ? where rno = ? ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,dto.getRcontent());
            ps.setInt(2,dto.getRrank());
            ps.setInt(3,dto.getRno());
            return ps.executeUpdate() == 1;
        } catch (Exception e) { System.out.println(e); }
        return false;
    }// func end

    // [2-2] 리뷰 이미지 수정기능
    public boolean updateReviewImg(int rno , String filename , int rimgno){
        try{
            String sql = "update review set rimg = ? where rno = ? and rimgno = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,filename);
            ps.setInt(2,rno);
            ps.setInt(3,rimgno);
            return ps.executeUpdate() == 1;
        } catch (Exception e) { System.out.println(e); }
        return false;
    }// func end

    // [2-3] 리뷰 이미지 식별번호 조회
    public List<Integer> getReviewImgNo(int rno){
        List<Integer> list = new ArrayList<>();
        try{
            String sql = "select * from reviewimg where rno = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,rno);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int rimgno = rs.getInt("rimgno");
                list.add(rimgno);
            }// while end
        } catch (Exception e) { System.out.println(e); }
        return list;
    }// func end



}// class end
