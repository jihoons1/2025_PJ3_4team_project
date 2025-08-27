package BestMeat.model.dao;

import BestMeat.model.dto.NoticeDto;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class NoticeDao extends Dao {
    // [notice01] 알림등록 - addNotice()
    // 기능설명 : [ 회원번호(세션), 제품번호, 알림설정가격 ]을 받아, Notice DB에 저장한다. 저장된 조건 만족 시, 문자 API를 통해 알림 전송한다.
    // 매개변수 : NoticeDto
    // 반환타입 : int -> 성공 : 자동생성된 PK값, 실패 : 0
    public int addNotice( NoticeDto noticeDto ){
        try {
            String SQL = "insert into notice ( mno, pno, nprice ) values ( ?, ?, ? )";
            PreparedStatement ps = conn.prepareStatement( SQL, Statement.RETURN_GENERATED_KEYS );
            ps.setInt( 1, noticeDto.getMno() );
            ps.setInt( 2, noticeDto.getPno() );
            ps.setInt( 3, noticeDto.getNprice() );
            int count =  ps.executeUpdate();
            if ( count == 1 ){
                ResultSet rs = ps.getGeneratedKeys();
                if ( rs.next() ){
                    // 등록성공 시, 자동생성된 PK값 반환
                    return rs.getInt( 1 );
                } // if end
            } // if end
        } catch ( SQLException e ){
            System.out.println("[notice01] SQL 기재 실패");
        } // try-catch end
        return 0;
    } // func end
} // class end