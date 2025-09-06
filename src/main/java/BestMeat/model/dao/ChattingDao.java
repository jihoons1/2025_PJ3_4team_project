package BestMeat.model.dao;

import BestMeat.model.dto.ChattingDto;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ChattingDao extends Dao {
    // [chatting01] CSV 생성 기능 - createCSV()
    // 기능설명 : 방이름을 입력받아, 해당 경로에 파일명이 없으면 생성한다.
    // 매개변수 : String room -> '연월일_방이름'
    // 반환타입 : void
    public void createCSV( String room ){
        try {
            String SQL = "insert into chatroom values ( ?, ?, ? )";
            PreparedStatement ps = conn.prepareStatement( SQL );
            ps.setString( 1, room );
            ps.setString( 2, room.split("_")[0] );
            ps.setString( 3, room.split("_")[1] );
            ps.executeUpdate();
        } catch ( SQLException e ){
            System.out.println("[chatting01] SQL 기재 실패" + e );
        } // try-catch end
    } // func end

    // [chatting04] DB chatLog 저장 기능 - saveDBLog()
    public void saveDBLog( List<ChattingDto> list ){
        try {
            for ( ChattingDto chattingDto : list ) {
                String SQL = "insert into chatlog( message, chatdate, roomname ) values ( ?, ?, ? )";
                PreparedStatement ps = conn.prepareStatement( SQL );
                ps.setString( 1, chattingDto.getMessage() );    // 대화내용
                ps.setString( 2, chattingDto.getChatdate() );   // 채팅시간
                ps.setString( 3, chattingDto.getRoomname() );   // 방이름
                ps.executeUpdate();
            } // for end
        } catch ( SQLException e ) {
            System.out.println("[chatting04] SQL 기재 실패" + e );
        } // try-catch end
    } // func end
} // class end