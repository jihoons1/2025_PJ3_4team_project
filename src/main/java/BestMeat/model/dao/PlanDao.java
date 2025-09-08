package BestMeat.model.dao;

import BestMeat.model.dto.PlanDto;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PlanDao extends Dao {

    // [plan01] 요금제 결제 - addPlan()
    // 기능설명 : [ 정육점번호(세션), 배너이미지 ]를 받아, 해당 정육점의 포인트가 충분하다면, 요금제 결제를 진행한다.
    // 매개변수 : HttpSession, PlanDto
    // 반환타입 : int -> 성공 : 자동생성된 PK값, 실패 : 0
    public int addPlan( PlanDto planDto ){
        try {
            String SQL = "insert into plan ( cno, startdate, enddate, banner ) values ( ?, ?, ?, ? )";
            PreparedStatement ps = conn.prepareStatement( SQL, Statement.RETURN_GENERATED_KEYS );
            ps.setInt( 1, planDto.getCno() );
            ps.setString( 2, planDto.getStartdate() );
            ps.setString( 3, planDto.getEnddate() );
            ps.setString( 4, planDto.getBanner() );
            int count = ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if ( count == 1 ){
                if ( rs.next() ){
                    return rs.getInt( 1 );
                } // if end
            } // if end
        } catch ( SQLException e ){
            System.out.println("[plan01] SQL 기재 실패" + e );
        } // try-catch end
        return 0;
    } // func end

    // [plan02] 정육점별 요금제 유/무 조회 - getCnoEnddate()
    // 기능설명 : [ 정육점번호 ]를 받아, 해당 정육점의 요금제 유/무를 조회한다.
    // 매개변수 : PlanDto
    // 반환타입 : int days
    public int getCnoEnddate( PlanDto planDto ){
        try {
            String SQL = "select datediff( enddate, ? ) days from plan where cno = ? order by enddate desc limit 1;";
            PreparedStatement ps = conn.prepareStatement( SQL );
            ps.setString( 1, planDto.getStartdate() );
            ps.setInt( 2, planDto.getCno() );
            ResultSet rs = ps.executeQuery();
            if ( rs.next() ){
                return rs.getInt( 1 );
            } // if end
        } catch ( SQLException e ){
            System.out.println("[plan02] SQL 기재 실패" + e );
        } // try-catch end
        return 0;
    } // func end

    // [plan03] 요금제 조회 - getPlan()
    // 기능설명 : 요금제를 구독하고 있는 정육점 번호를 조회한다.
    // 매개변수 : String today
    // 반환타입 : List<Integer>
    public List<Integer> getPlan( String today ){
        List<Integer> list = new ArrayList<>();
        try {
            String SQL = "select cno from plan where ? between startdate and enddate group by cno";
            PreparedStatement ps = conn.prepareStatement( SQL );
            ps.setString( 1, today );
            ResultSet rs = ps.executeQuery();
            while ( rs.next() ){
                list.add( rs.getInt( 1 ) );
            } // while end
        } catch ( SQLException e ){
            System.out.println("[plan03] SQL 기재 실패" + e );
        } // try-catch end
        return list;
    } // func end
} // class end