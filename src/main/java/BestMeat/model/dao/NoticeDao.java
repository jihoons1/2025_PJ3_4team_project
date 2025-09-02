package BestMeat.model.dao;

import BestMeat.model.dto.NoticeDto;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
            System.out.println("[notice01] SQL 기재 실패" + e );
        } // try-catch end
        return 0;
    } // func end

    // [notice02] 제품번호별 알림리스트 반환
    // 기능설명 : [ 제품번호 ]를 받아, 해당하는 알림리스트를 반환한다.
    // 매개변수 : int pno
    // 반환타입 : List<NoticeDto>
    public List<NoticeDto> getNoticeList( int pno ){
        List<NoticeDto> noticeDtoList = new ArrayList<NoticeDto>();
        try {
            String SQL = "select * from notice n inner join member using ( mno ) inner join product p using ( pno ) where pno = ? and ndate >= DATE_SUB(NOW(), INTERVAL 7 DAY)";
            // where ndate >= DATE_SUB(NOW(), INTERVAL 7 DAY) : 현재 날짜로부터 7일 전까지의 데이터만 뽑는다.
            // date_sub( A, interval B ) : 과거 <--> data_add( A, interval B ) : 미래
            PreparedStatement ps = conn.prepareStatement( SQL );
            ps.setInt( 1, pno );
            ResultSet rs = ps.executeQuery();
            while ( rs.next() ){
                NoticeDto noticeDto = new NoticeDto();
                noticeDto.setNno( rs.getInt( "nno" ) );
                noticeDto.setMno( rs.getInt( "mno" ) );
                noticeDto.setPno( rs.getInt( "pno" ) );
                noticeDto.setNprice( rs.getInt( "nprice" ) );
                noticeDto.setNcheck( rs.getInt( "ncheck" ) );
                noticeDto.setNdate( rs.getString( "ndate" ) );
                noticeDto.setMphone( rs.getString( "mphone" ) );
                noticeDto.setPname( rs.getString( "pname" ) );
                noticeDtoList.add( noticeDto );
            } // while end
        } catch ( SQLException e ){
            System.out.println("[notice02] SQL 기재 실패" + e );
        } // try-catch end
        return noticeDtoList;
    } // func end

    // [notice03] 회원별 알림조회 - getMnoNotice()
    // 기능설명 : [ 회원번호(세션) ]을 받아, 해당하는 알림을 조회한다.
    // 매개변수 : int mno
    // 반환타입 : List<NoticeDto>
    public List<NoticeDto> getMnoNotice( int mno ){
        List<NoticeDto> noticeDtoList = new ArrayList<>();
        try {
            String SQL = "select * from notice n inner join product p using ( pno ) inner join category c using ( cno ) where n.mno = ?";
            PreparedStatement ps = conn.prepareStatement( SQL );
            ps.setInt( 1, mno );
            ResultSet rs = ps.executeQuery();
            while ( rs.next() ){
                NoticeDto noticeDto = new NoticeDto();
                noticeDto.setNno( rs.getInt( "nno" ) );
                noticeDto.setMno( rs.getInt( "mno" ) );
                noticeDto.setPname( rs.getString( "pname" ) );
                noticeDto.setNprice( rs.getInt( "nprice" ) );
                noticeDto.setNcheck( rs.getInt( "ncheck" ) );
                noticeDto.setNdate( rs.getString( "ndate" ) );
                noticeDto.setPno( rs.getInt( "pno" ) );
                noticeDto.setCname( rs.getString( "cname" ) );
                noticeDtoList.add( noticeDto );
            } // while end
        } catch ( SQLException e ){
            System.out.println("[notice03] SQL 기재 실패" + e );
        } // try-catch end
        return noticeDtoList;
    } // func end

    // [notice04] 알림수정 - updateNotice
    // 기능설명 : [ 회원번호(세션), 알림번호, 알림설정가격 ]을 받아, 해당하는 알림을 수정한다.
    // 매개변수 : NoticeDto
    // 반환타입 : boolean
    public boolean updateNotice( NoticeDto noticeDto ){
        try {
            String SQL = "update notice set nprice = ?, ndate = ? where nno = ? and mno = ?";
            PreparedStatement ps = conn.prepareStatement( SQL );
            ps.setInt( 1, noticeDto.getNprice() );
            ps.setString( 2, noticeDto.getNdate() );
            ps.setInt( 3, noticeDto.getNno() );
            ps.setInt( 4, noticeDto.getMno() );
            return ps.executeUpdate() == 1;
        } catch ( SQLException e ){
            System.out.println("[notice04] SQL 기재 실패" + e );
        } // try-catch end
        return false;
    } // func end

    // [notice05] 알림삭제 - deleteNotice
    // 기능설명 : [ 회원번호(세션), 알림번호 ]를 받아, 해당하는 알림을 삭제한다.
    // 매개변수 : Map< String, Integer > map
    // 반환타입 : boolean
    public boolean deleteNotice( Map< String, Integer > map ){
        try {
            String SQL = "delete from notice where nno = ? and mno = ?";
            PreparedStatement ps = conn.prepareStatement( SQL );
            ps.setInt( 1, map.get("nno") );
            ps.setInt( 2, map.get("mno") );
            return ps.executeUpdate() == 1;
        } catch ( SQLException e ){
            System.out.println("[notice05] SQL 기재 실패" + e );
        } // try-catch end
        return false;
    } // func end

    // [notice06] 문자전송여부 수정 - updateNcheck()
    // 기능설명 : [ 알림PK번호, 재고(등록/수정)가격 ]을 받아, 문자전송여부를 수정한다.
    // 매개변수 : NoticeDto
    // 반환타입 : boolean -> 성공 : true, 실패 : false
    public boolean updateNcheck( NoticeDto noticeDto ){
        try {
            String SQL = "update notice set ncheck = ? where nno = ?";
            PreparedStatement ps = conn.prepareStatement( SQL );
            ps.setInt( 1, noticeDto.getSprice() );      // 문자전송여부지만, 컨셉 상 문자가 전송됐으면, 등록/수정된 재고가격으로 수정
            ps.setInt( 2, noticeDto.getNno() );
            return ps.executeUpdate() == 1;
        } catch ( SQLException e ){
            System.out.println("[notice06] SQL 기재 실패" + e );
        } // try-catch end
        return false;
    } // func end
} // class end