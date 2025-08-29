package BestMeat.model.dao;

import BestMeat.service.MessageService;
import BestMeat.model.dto.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Random;

@Repository
public class MemberDao extends Dao  {
    @Autowired
    MessageService messageService;

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

        for (int i = 0 ; i < 6; i++){
            code += chart.charAt(random.nextInt(chart.length())); // chars 문자열에서 랜덤한 문자 하나를 뽑아 code 끝에 이어붙임.
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
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // 회원 존재시 임시 비밀번호 발급
                String random_password = getPwd();

                String sql2 = "update member set mpwd = ? where mid = ? and mphone = ? ";
                PreparedStatement ps2 = conn.prepareStatement(sql2);
                ps2.setString(1, random_password);
                ps2.setString(2,map.get("mid"));
                ps2.setString(3, map.get("mphone"));
                ps2.executeUpdate();

                System.out.println("임시 비밀번호 = " + random_password);

                // 이메일 전송
                String a = rs.getString("memail"); // 회원메일
                String b = "2025_PJ3_4조_BestMeat 임시 비밀번호";
                String c = "회원님의 임시 비밀번호는 " + random_password + " 입니다. \n";

                messageService.mailMessage(a,b,c);
                return true;
            }
        }catch (Exception e){
            System.out.println("비밀번호 찾기 오류 발생 "+ e );
        }
        return false;
    }

    // 파일 등록
    public boolean fileuploads(int mno , String filename ){
        try{
            String sql = "update member set mimg = ? where mno = ? ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, filename);
            ps.setInt(2, mno);
            return ps.executeUpdate() == 1;
        }catch (Exception e){
            System.out.println("파일등록 오류 발생" + e);
        }
        return false;
    }

    // [5] 회원정보 수정
    public boolean updateMember(MemberDto dto){
        System.out.println("MemberDao.updateMember");
        try{
            String sql = "update member set mphone = ? , maddress = ? where mno = ? ";
            PreparedStatement ps = conn.prepareStatement(sql , Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, dto.getMphone());
            ps.setString(2, dto.getMaddress());
            ps.setInt(3, dto.getMno());
            int count = ps.executeUpdate();
            if (count == 1) { return true; }

        }catch (Exception e){
            System.out.println("회원정보 수저 오류 발생" + e );
        }
        return false;
    }


    // [6] 비밀번호 수정
    public boolean updatePwd(int mno, Map<String , String>map ){
        System.out.println("MemberDao.updatePwd");
        try{
            String sql = "update member set mpwd = ? where mno = ? and mpwd = ? ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1 ,map.get("newmpwd")); // 신규 비번
            ps.setInt(2, mno);
            ps.setString(3, map.get("oldmpwd")); // 기존 비번
            int count = ps.executeUpdate();
            return count == 1 ;
        }catch (Exception e){
            System.out.println("비밀번호 수정 오류 발생 "+e);
        }
        return false;
    }


    // [member07] 회원정보 상세조회 - getMember()
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

    // [member08] 회원 탈퇴 - resignMember()
    // 기능설명 : [ 회원번호(세션), 비밀번호 ]를 받아,  일치하면 회원활성화를 false로 변경한다.
    // 매개변수 : Map< String, String >
    // 반환타입 : boolean -> 성공 : true, 실패 : false
    public boolean resignMember( Map<String , String> map ){
        try {
            String SQL = "update member set mcheck = false where mno = ? and mpwd = ? and mcheck = true";
            PreparedStatement ps = conn.prepareStatement( SQL );
            ps.setInt( 1, Integer.parseInt( map.get("mno") ) );
            ps.setString( 2, (String) map.get("mpwd"));
            return ps.executeUpdate() == 1;
        } catch ( SQLException e ){
            System.out.println("[member08] SQL 기재 실패");
        } // try-catch end
        return false;
    } // func end
} // class end