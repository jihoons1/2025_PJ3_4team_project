package BestMeat.model.dao;

import BestMeat.model.dto.ChattingDto;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public class ChattingDao extends Dao {

    // [chatting04] DB 저장 기능 - saveDB()
    public boolean saveDB( List<ChattingDto> list ){
        try {
            for ( ChattingDto chattingDto : list ) {
                String SQL = "insert into ";
            } // for end
        } catch ( SQLException e ) {
            System.out.println("[chatting04] SQL 기재 실패" + e );
        } // try-catch end
        return false;
    } // func end
} // class end