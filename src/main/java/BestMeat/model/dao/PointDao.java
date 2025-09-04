package BestMeat.model.dao;

import BestMeat.model.dto.PointDto;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class PointDao extends Dao {
    // [point01] 포인트로그 추가 - addPointLog()
    // 기능설명 : [ 회원번호(세션), 결제액, 결제사유 ]를 입력받아, 포인트 로그를 추가한다.
    // 매개변수 : PointDto
    // 반환타입 : boolean -> true : 성공, false : 실패
    public boolean addPointLog( PointDto pointDto ){
        try {
            String SQL = "insert into pointLog ( mno, plpoint, plcomment ) values ( ?, ?, ? )";
            PreparedStatement ps = conn.prepareStatement( SQL );
            ps.setInt( 1, pointDto.getMno() );
            ps.setInt( 2, pointDto.getPlpoint() );
            ps.setString( 3, pointDto.getPlcomment() );
            return ps.executeUpdate() == 1;
        } catch ( SQLException e ){
            System.out.println("[point01] SQL 기재 실패" + e );
        } // try-catch end
        return false;
    } // func end

    // [point02] 회원별 포인트 총액 조회 - getTotalPoint()
    // 기능설명 : [ 회원번호 ]를 받아, 해당 회원의 포인트 총액을 조회한다.
    // 매개변수 : int mno
    // 반환타입 : int -> -1 : 조회 실패
    public int getTotalPoint( int mno ){
        try {
            String SQL = "select sum( plpoint ) from pointLog group by mno having mno = ?";
            PreparedStatement ps = conn.prepareStatement( SQL );
            ps.setInt( 1, mno );
            ResultSet rs = ps.executeQuery();
            if ( rs.next() ){
                return rs.getInt( 1 );
            } // if end
        } catch ( SQLException e ){
            System.out.println("[point02] SQL 기재 실패" + e );
        } // try-catch end
        return -1;
    } // func end
} // class end