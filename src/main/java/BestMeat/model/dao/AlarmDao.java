package BestMeat.model.dao;

import BestMeat.model.dto.AlarmDto;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AlarmDao extends Dao {
    // [alarm01] 알림등록 - addAlarm()
    // 기능설명 : [ 회원번호, 알림메시지 ]를 받아, 알림을 등록한다.
    // 매개변수 : AlarmDto
    // 반환타입 : int -> 성공 : 자동생성된 PK, 실패 : 0
    public int addAlarm ( AlarmDto alarmDto ){
        try {
            String SQL = "insert into alarm ( mno, amessage ) values ( ?, ? )";
            PreparedStatement ps = conn.prepareStatement( SQL, Statement.RETURN_GENERATED_KEYS );
            ps.setInt( 1, alarmDto.getMno() );
            ps.setString( 2, alarmDto.getAmessage() );
            int count = ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if ( count == 1 ){
                if ( rs.next() ){
                    // 자동생성된 PK를 반환
                    return rs.getInt( 1 );
                } // if end
            } // if end
        } catch ( SQLException e ) {
            System.out.println("[alarm01] SQL 기재 실패" + e );
        } // try-catch end
        return 0;
    } // func end

    // [alarm02] 알림수정 - updateAlarm()
    // 기능설명 : [ 푸시알림번호 ]를 받아, 해당 알림의 확인여부를 수정한다.
    // 매개변수 : int ano
    // 반환타입 : int -> 성공 : 수정한 푸시알림번호, 실패 : 0
    public int updateAlarm ( int ano ){
        try {
            String SQL = "update alarm set acheck = true where ano = ? and acheck = false";
            PreparedStatement ps = conn.prepareStatement( SQL );
            ps.setInt( 1, ano );
            int count = ps.executeUpdate();
            if ( count == 1 ){
                // 수정에 성공한 푸시알림번호 반환
                return ano;
            } // if end
        } catch ( SQLException e ){
            System.out.println("[alarm02] SQL 기재 실패" + e );
        } // try-catch end
        return 0;
    } // func end

    // [alarm03] 알림조회 - getAlarm()
    // 기능설명 : [ 회원번호 ]를 받아, 해당 회원의 아직 확인하지않은 알림을 조회한다.
    // 매개변수 : int mno
    // 반환타입 : List<AlarmDto>
    public List<AlarmDto> getAlarm( int mno ){
        List<AlarmDto> list = new ArrayList<>();
        try {
            String SQL = "select * from alarm where mno = ? and acheck = false";
            PreparedStatement ps = conn.prepareStatement( SQL );
            ps.setInt( 1, mno );
            ResultSet rs = ps.executeQuery();
            while ( rs.next() ){
                list.add( AlarmDto.builder().ano( rs.getInt( "ano" ) ).mno( rs.getInt( "mno" ) ).amessage( rs.getString( "amessage" ) ).build() );
            } // while end
        } catch ( SQLException e ){
            System.out.println("[alarm02] SQL 기재 실패" + e );
        } // try-catch end
        return list;
    } // func end
} // class end