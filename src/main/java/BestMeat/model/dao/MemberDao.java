package BestMeat.model.dao;

import BestMeat.model.dto.MemberDto;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Random;

@Repository
public class MemberDao extends Dao  {

    // [1] 회원가입
    public int signup(MemberDto dto){
        System.out.println("MemberDao.signup");
        try{
            String sql = "INSERT INTO member " +
                    "(mname, mid, mpwd, mphone, memail, maddress )" + // name , id , password , phone , email , address
                    " VALUES " + // 적은
                    "( ? , ? , ? , ? , ? , ? )"; // 값을 db 저장
            PreparedStatement ps = conn.prepareStatement(sql , Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, dto.getMname());
            ps.setString(2, dto.getMid());
            ps.setString(3, dto.getMpwd());
            ps.setString(4, dto.getMphone());
            ps.setString(5, dto.getMemail());
            ps.setString(6, dto.getMaddress());
            int count = ps.executeUpdate();
            if (count == 1 ){
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()){
                    int mno = rs.getInt(1);
                    return mno;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } // catch end
        return 0;
    } // func end

    public int getCno(MemberDto dto){
        try{
            String sql = "select * from member as m join Company c on m.mno = c.mno where mid = ? and mpwd = ? ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString( 1 , dto.getMid() );
            ps.setString( 2 , dto.getMpwd() );
            ResultSet rs = ps.executeQuery();
            if( rs.next() ){
                int mno = rs.getInt("cno");
                return mno; // 로그인 성공시 조회한 회원번호 반환
            }
        } catch (Exception e) { System.out.println("회원가입 오류 발생 " + e);  }
        return 0; // 로그인 실패시 0 반환
    }// func end

    // [2] 로그인
    public int login( MemberDto memberDto ){
        try{
            String sql = "select * from member where mid = ? and mpwd = ? ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString( 1 , memberDto.getMid() );
            ps.setString( 2 , memberDto.getMpwd() );
            ResultSet rs = ps.executeQuery();
            if( rs.next() ){
                int mno = rs.getInt("mno");
                return mno; // 로그인 성공시 조회한 회원번호 반환
            }
        } catch (Exception e) { System.out.println("로그인 발생 오류 " + e);  }
        return 0; // 로그인 실패시 0 반환
    }// func end

    // [3] 아이디 찾기
    public String findId(Map<String , String> map){
        try{
            String sql = "select * from member where mname = ? and mphone = ? ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, map.get("mname"));
            ps.setString(2, map.get("mphone"));
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                return rs.getString("mid");
            }
        }catch (Exception e){
            System.out.println("아이디 찾기 오류 발생" + e); }
        return null;
    }
    // [4-1] 임시 비밀번호(랜덤)
    public String getPwd(){

        Random random = new Random(); // 난수
        String chart = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm0123456789"; // 난수 설정 대문자 , 소문자 다적어야함
        String code = ""; // 난수 값 넣을 빈공간

        for (int i = 0 ; i < 5; i++){
            code += chart.charAt(random.nextInt(chart.length())); // chars 문자열에서 랜덤한 문자 하나를 뽑아 code 끝에 이어붙인다.
        }
        System.out.println(code);
        return code;
    }

    // [4] 비밀번호 찾기
    public boolean findPwd(Map<String , String > map){
        try {
            String sql = "select * from member where mid = ? and mphone = ? ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, map.get("mid"));
            ps.setString(2, map.get("mphone"));
            int count = ps.executeUpdate();
            return count == 1;
        }catch (Exception e){
            System.out.println("비밀번호 찾기 오류 발생 "+ e );
        }
        return false;
    }

    // [5] 회원정보 수정

    // [6] 비밀번호 수정

    // [7] 회원정보 상세조회 - getMember()
    // 기능설명 : [ 회원번호(세션) ]를 받아, 해당하는 회원정보를 조회한다.
    // 매개변수 : int mno
    // 반환타입 : MemberDto
    public MemberDto getMember( int mno ){
        try {
            String SQL = "select * from member where mno = ? and mcheck = true";
            PreparedStatement ps = conn.prepareStatement( SQL );
            ps.setInt( 1, mno );
            ResultSet rs = ps.executeQuery();
            if ( rs.next() ){
                MemberDto memberDto = new MemberDto();
                memberDto.setMno( rs.getInt("mno") );
                memberDto.setMname( rs.getString("mname") );
                memberDto.setMid( rs.getString("mid") );
                memberDto.setMphone( rs.getString("mphone") );
                memberDto.setMemail( rs.getString("memail") );
                memberDto.setMaddress( rs.getString("maddress") );
                memberDto.setMdate( rs.getString("mdate") );
                memberDto.setMimg( rs.getString("mimg") );
                memberDto.setMcheck( rs.getString("mcheck") );
                return memberDto;
            } // if end
        } catch ( SQLException e ){
            System.out.println("[member07] SQL 기재 실패");
        } // try-catch end
        return null;
    } // func end


} // class end