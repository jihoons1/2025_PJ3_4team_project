package BestMeat.model.dao;

import BestMeat.model.dto.ChattingDto;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Repository
public class ChattingDao extends Dao {
    // [chatting00-1] CSV 생성 기능 - createCSV()
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
            System.out.println("[chatting01-1] SQL 기재 실패" + e );
        } // try-catch end
    } // func end

    // [chatting00-2] 해당 방의 DB가 구성되었는지 확인
    public boolean checkRoom( String room ){
        String Rroom = room.split("_")[1] + "_" + room.split("_")[0];
        try {
            String SQL = "select count(*) from chatroom where roomname = ? or roomname = ?";
            PreparedStatement ps = conn.prepareStatement( SQL );
            ps.setString( 1, room );
            ps.setString( 2, Rroom );
            ResultSet rs = ps.executeQuery();
            if( rs.next() ){
                return rs.getInt( 1 ) == 1;
            } // if end
        } catch ( SQLException e ){
            System.out.println("[chatting00-2] SQL 기재 실패" + e );
        } // try-catch end
        return false;
    } // func end

    // [chatting01] 회원별 채팅목록 조회 - getRoomList
    // 기능설명 : [ 회원번호(세션) ]을 받아, 해당 회원의 채팅목록을 조회한다.
    // 매개변수 : HttpSession session
    // 반환타입 : List<ChattingDto>
    public List<ChattingDto> getRoomList( String mno ){
        List<ChattingDto> roomList = new ArrayList<>();
        try {
            String SQL = "select * from chatlog join chatroom using (roomname) where roomname like ? order by chatdate desc limit 1";
            PreparedStatement ps = conn.prepareStatement( SQL );
            ps.setString( 1, "%" + mno + "%" );
            ResultSet rs = ps.executeQuery();
            while ( rs.next() ){
                ChattingDto room = new ChattingDto();
                room.setRoomname( rs.getString( "roomname" ) );
                room.setMessage( rs.getString( "message" ) );
                room.setFrom( rs.getInt( "mno" ) );
                room.setTo( rs.getInt( "cno" ) );
                room.setChatdate( rs.getString( "chatdate" ) );
                roomList.add( room );
            } // func end
        } catch ( SQLException e ){
            System.out.println("[chatting01] SQL 기재 실패" + e );
        } // try-catch end
        return roomList;
    } // func end

    // [chatting02] 채팅로그 호출 기능 - getChatLog()
    // 기능설명 : 방이름을 입력받아, 해당 방의 모든 채팅로그를 조회한다.
    // 매개변수 : String room -> '방이름' -> 모든 채팅로그 조회
    // 반환타입 : List<ChattingDto>
    public List<ChattingDto> getChatLog( String room ){
        List<ChattingDto> list = new ArrayList<>();
        String Rroom = room.split("_")[1] + "_" + room.split("_")[0];
        try {
            String SQL = "select * from chatlog where roomname = ? or roomname = ?";
            PreparedStatement ps = conn.prepareStatement( SQL );
            ps.setString( 1, room );
            ps.setString( 2, Rroom );
            ResultSet rs = ps.executeQuery();
            while ( rs.next() ) {
                ChattingDto chattingDto = new ChattingDto();
                chattingDto.setMessage( rs.getString( "message" ) );
                chattingDto.setRoomname( rs.getString( "roomname" ) );
                chattingDto.setChatdate( rs.getString( "chatdate" ) );
                list.add( chattingDto );
            } // while end
        } catch ( SQLException e ){
            System.out.println("[chatting02] SQL 기재 실패" + e );
        } // try-catch end
        return list;
    } // func end

    // [chatting04] DB chatLog 저장 기능 - saveDBLog()
    public boolean saveDBLog( List<ChattingDto> list ){
        try {
            for ( ChattingDto chattingDto : list ) {
                // 5. SQL 작성
                String SQL = "insert into chatlog( message, chatdate, roomname ) values ( ?, ?, ? )";
                PreparedStatement ps = conn.prepareStatement( SQL );
                ps.setString( 1, chattingDto.getMessage() );    // 대화내용
                ps.setString( 2, chattingDto.getChatdate() );   // 채팅시간
                ps.setString( 3, chattingDto.getRoomname() );   // 방이름
                ps.executeUpdate();
            } // for end
            return true;
        } catch ( SQLException e ) {
            System.out.println("[chatting04] SQL 기재 실패" + e );
        } // try-catch end
        return false;
    } // func end
} // class end