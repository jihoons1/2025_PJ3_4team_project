package BestMeat.model.dao;

import BestMeat.model.dto.PointDto;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class PointDao extends Dao {
    // [point00] 포인트로그 추가 - addPointLog()
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

    // [point01] 요금제 결제 - addPlan()
    // 기능설명 : [ 정육점번호(세션) ]을 받아, 해당 정육점의 포인트가 충분하다면, 요금제 결제를 진행한다.
    // 매개변수 : int cno
    // 반환타입 : boolean -> true : 성공, false : 실패


} // class end