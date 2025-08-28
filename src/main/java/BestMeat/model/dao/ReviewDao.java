package BestMeat.model.dao;

import BestMeat.model.dto.ReviewDto;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            String sql = "update reviewimg set rimg = ? where rno = ? and rimgno = ?";
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


    // [review03] 리뷰 상세조회 - getReview()
    // 기능설명 : 정육점별 리뷰를 조회한다.
    // 매개변수 : startRow, perCount, cno
    // 반환타입 : List<ReviewDto>
    // cno : 정육점번호, startRow : 조회를 시작할 인덱스번호, perCount :  페이지당 자료 개수
    public List<ReviewDto> getReview( int cno, int startRow, int perCount ){
        List<ReviewDto> list = new ArrayList<>();
        try {
            String SQL = "select * from review inner join member using ( mno ) where cno = ? limit ?, ?";
            PreparedStatement ps = conn.prepareStatement( SQL );
            ps.setInt( 1, cno );
            ps.setInt( 2, startRow );
            ps.setInt( 3, perCount );
            ResultSet rs = ps.executeQuery();
            while ( rs.next() ){
                ReviewDto reviewDto = new ReviewDto();
                reviewDto.setRno( rs.getInt("rno") );
                reviewDto.setRcontent( rs.getString("rcontent") );
                reviewDto.setRrank( rs.getInt("rrank") );
                reviewDto.setRdate( rs.getString("rdate") );
                reviewDto.setCno( rs.getInt("cno") );
                reviewDto.setMname( rs.getString("mname") );
                reviewDto.setMno( rs.getInt("mno") );
                list.add(reviewDto);
            } // while end
        } catch ( SQLException e ){
            System.out.println("[review03] SQL 기재 실패");
        } // try-catch end
        return  list;
    } // func end

    // [review04] 정육점별 리뷰개수 반환
    public int getReviewCount( int cno ){
        try {
            String SQL = "select count(*) from review where cno = ?";
            PreparedStatement ps = conn.prepareStatement( SQL );
            ps.setInt( 1, cno );
            ResultSet rs = ps.executeQuery();
            if ( rs.next() ){
                return rs.getInt( 1 );
            } // if end
        } catch ( SQLException e ){
            System.out.println("[review04] SQL 기재 실패");
        } // try-catch end
        return 0;
    } // func end
}// class end
