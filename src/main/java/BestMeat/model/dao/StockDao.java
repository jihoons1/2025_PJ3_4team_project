package BestMeat.model.dao;

import BestMeat.model.dto.StockDto;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class StockDao extends Dao {
    // [stock01] 재고등록 - addStock()
    // 기능설명 : [ 정육점번호(세션), 가격, 제품번호(select) ]를 받아, Stock DB에 저장한다.
    // 매개변수 : StockDto
    // 반환타입 : int
    public int addStock( StockDto stockDto ){
        try {
            String SQL = "insert into stock ( cno, pno, sprice ) values ( ?, ?, ? )";
            PreparedStatement ps = conn.prepareStatement( SQL, Statement.RETURN_GENERATED_KEYS );
            ps.setInt( 1, stockDto.getCno() );
            ps.setInt( 2, stockDto.getPno() );
            ps.setInt( 3, stockDto.getSprice() );
            int count = ps.executeUpdate();
            if ( count == 1 ){
                ResultSet rs = ps.getGeneratedKeys();
                if ( rs.next() ){
                    // 자동생성된 PK값 반환
                    return rs.getInt( 1 );
                } // if end
            } // if end
        } catch ( SQLException e ){
            System.out.println("[stock01] SQL 기재 실패");
        } // try-catch end
        return 0;
    } // func end

    // [stock02] 재고수정 - updateStock()
    // 기능설명 : [ 정육점번호(세션), 재고번호, 가격 ]을 입력받아, [ 가격, 등록일 ]을 수정한다.
    // 매개변수 : StockDto
    // 반환타입 : boolean -> 성공 : true / 실패 : false
    public boolean updateStock( StockDto stockDto ){
        try {
            String SQL = "update stock set sprice = ?, sdate = ? where sno = ?";
            PreparedStatement ps = conn.prepareStatement( SQL );
            ps.setInt( 1, stockDto.getSprice() );
            ps.setString( 2, stockDto.getSdate() );
            ps.setInt( 3, stockDto.getSno() );
            return ps.executeUpdate() == 1;
        } catch ( SQLException e ){
            System.out.println("[stock02] SQL 기재 실패");
        } // try-catch end
        return false;
    } // func end

    // [stock03] 재고삭제 - deleteStock()
    // 기능설명 : [ 정육점번호(세션), 재고번호 ]을 받아, 해당하는 회사가 등록한 재고라면, 삭제한다.
    // 매개변수 : int sno
    // 반환타입 : boolean -> 성공 : true / 실패 : false

} // class end