package BestMeat.model.dao;

import BestMeat.model.dto.MemberDto;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Repository
public class MemberDao extends Dao  {

    // 회원가입
    public int signup(MemberDto dto){
        System.out.println("MemberDao.signup");
        try{
            String sql = "INSERT INTO member " +
                    "(mname, mid, mpwd, mphone, memail, maddress )" + // name , id , password , phone , email , address
                    " VALUES " + // 적은
                    "( ? , ? , ? , ? , ?  )"; // 값을 db 저장
            PreparedStatement ps = conn.prepareStatement(sql);
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
        } catch (Exception e) { System.out.println(e);  }
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
        } catch (Exception e) { System.out.println(e);  }
        return 0; // 로그인 실패시 0 반환

    }// func end





} // class end