package BestMeat.model.dao;

import BestMeat.model.dto.CompanyDto;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CompanyDao extends Dao {

    // 정육점 전체조회(정렬추가)
    public List<CompanyDto> getCompany(int startRow , int count , String order){
        List<CompanyDto> list = new ArrayList<>();
        try{
            String sql = "select c.cno, c.mno , c.cname, c.caddress, c.cimg, c.views , ifnull(round(avg(r.rrank),1),0) as rrank " +
                    " from company c left outer join review r on c.cno = r.cno ";
            if ("rank".equals(order)){
                sql += " group by c.cno order by rrank desc , cno limit ? , ?";
            } else if ("views".equals(order)) {
                sql += " group by c.cno order by views desc , cno limit ? , ?";
            } else{
                sql += " group by c.cno order by cno desc limit ? , ?";
            }// if end
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,startRow);
            ps.setInt(2,count);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                CompanyDto dto = new CompanyDto();
                dto.setMno(rs.getInt("mno"));
                dto.setCno(rs.getInt("cno"));
                dto.setCname(rs.getString("cname"));
                dto.setCaddress(rs.getString("caddress"));
                dto.setCimg(rs.getString("cimg"));
                dto.setRrank(rs.getDouble("rrank"));
                dto.setViews(rs.getInt("views"));
                list.add(dto);
            }// while end
        } catch (Exception e) { System.out.println(e); }
        return list;
    }// func end


    // 정육점 전체 수
    public int getTotalCompany(){
        try{
            String sql = "select count(*) from company";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) { System.out.println(e); }
        return 0;
    }// func end

    // 정육점 전체조회(키워드검색)
    public List<CompanyDto> getCompanySearch( String key , String keyword , String order){
        List<CompanyDto> list = new ArrayList<>();
        try{
            String sql = "select c.cno , c.mno , c.cname , c.caddress , c.cimg ,p.pname , c.views , s.sprice ,  ifnull(round(avg(r.rrank),1),0) as rrank " +
                    " from product p join stock s on p.pno = s.pno join company c on s.cno = c.cno join review r on c.cno = r.cno";
            if (key.equals("pname")){
                sql += " where pname like ? ";
            }// if end
            if ("rank".equals(order)){
                sql += " group by c.cno, c.cname, c.caddress, c.cimg, p.pname, s.sprice order by rrank desc ";
            }else if ("sprice".equals(order)){
                sql += " group by c.cno, c.cname, c.caddress, c.cimg, p.pname, s.sprice order by sprice asc ";
            } else if ("views".equals(order)) {
                sql += " group by c.cno, c.cname, c.caddress, c.cimg, p.pname, s.sprice order by views desc ";
            } else{
                sql += " group by c.cno, c.cname, c.caddress, c.cimg, p.pname, s.sprice order by cno desc ";
            }// if end
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,"%"+keyword+"%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                CompanyDto dto = new CompanyDto();
                dto.setPname(rs.getString("pname"));
                dto.setSprice(rs.getInt("sprice"));
                dto.setMno(rs.getInt("mno"));
                dto.setCno(rs.getInt("cno"));
                dto.setCname(rs.getString("cname"));
                dto.setCaddress(rs.getString("caddress"));
                dto.setCimg(rs.getString("cimg"));
                dto.setRrank(rs.getDouble("rrank"));
                dto.setViews(rs.getInt("views"));
                list.add(dto);
            }// while end
        } catch (Exception e) { System.out.println(e); }
        return list;
    }// func end

    // 정육점(키워드검색) 전체 수
    public int getTotalCompanySearch(String key , String keyword){
        try{
            String sql = "select count(*) from product p join stock s on p.pno = s.pno join company c on s.cno = c.cno";
            if (key.equals("pname")){
                sql += " where pname like ? ";
            }// if end
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,"%"+keyword+"%");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) { System.out.println(e); }
        return 0;
    }// func end

    // 정육점 개별조회
    public CompanyDto findCompany(int cno){
        try{
            String sql = "select c.cno, c.mno, c.views , c.cname, c.caddress, c.cimg, ifnull(round(avg(r.rrank),1),0) as rrank"+
                    " from company c left outer join review r on c.cno = r.cno  where c.cno = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,cno);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                CompanyDto dto = new CompanyDto();
                dto.setCno(rs.getInt("cno"));
                dto.setMno(rs.getInt("mno"));
                dto.setCname(rs.getString("cname"));
                dto.setCaddress(rs.getString("caddress"));
                dto.setCimg(rs.getString("cimg"));
                dto.setRrank(rs.getDouble("rrank"));
                dto.setViews( rs.getInt("views") );
                return dto;
            }// if end
        } catch (Exception e) { System.out.println(e); }
        return null;
    }// func end

    // 정육점 전체조회
    public List<CompanyDto> getCompanyList(){
        List<CompanyDto> list = new ArrayList<>();
        try{
            String sql = "select c.cno , c.mno , c.cimg , c.views , c.cname , c.caddress , ifnull(round(avg(r.rrank),1),0) as rrank from company c left outer join review r on c.cno = r.cno group by c.cno";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                CompanyDto dto = new CompanyDto();
                dto.setMno(rs.getInt("mno"));
                dto.setCno(rs.getInt("cno"));
                dto.setCname(rs.getString("cname"));
                dto.setCaddress(rs.getString("caddress"));
                dto.setCimg(rs.getString("cimg"));
                dto.setRrank(rs.getDouble("rrank"));
                dto.setViews(rs.getInt("views"));
                list.add(dto);
            }// while end
        } catch (Exception e) { System.out.println(e); }
        return list;
    }// func end

    // 정육점 조회수 증가
    public boolean addViews( int cno ){
        try {
            String SQL = "update company set views = views + 1 where cno = ?";
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setInt(1,cno);
            return ps.executeUpdate() == 1;
        } catch ( SQLException e ) {
            System.out.println(e);
        } // try-catch end
        return false;
    } // func end

    // 정육점 이름 조회 - getCname()
    // 기능설명 : 정육점번호를 받아, 해당 정육점이름을 반환한다.
    // 매개변수 : int cno
    // 반환타입 : String cname
    public String getCname( int cno ){
        try {
            String SQL = "select cname from company where cno = ?";
            PreparedStatement ps = conn.prepareStatement( SQL );
            ps.setInt( 1, cno );
            ResultSet rs = ps.executeQuery();
            if ( rs.next() ){
                return rs.getString("cname");
            } // if end
        } catch ( SQLException e ){
            System.out.println("[getCname] SQL 기재 실패" + e );
        } // try-catch end
        return null;
    } // func end

    // 정육점 회원번호 조회 - getMno()
    // 기능설명 : 정육점번호를 받아, 해당 정육점의 회원번호를 반환한다.
    // 매개변수 : int cno
    // 반환타입 : int mno
    public int getMno( int cno ){
        try {
            String SQL = "select mno from company join member using ( mno ) where cno = ?";
            PreparedStatement ps = conn.prepareStatement( SQL );
            ps.setInt( 1, cno );
            ResultSet rs = ps.executeQuery();
            if ( rs.next() ){
                return rs.getInt("mno");
            } // if end
        } catch ( SQLException e ){
            System.out.println("[getMno] SQL 기재 실패" + e );
        } // try-catch end
        return 0;
    } // func end
} // class end