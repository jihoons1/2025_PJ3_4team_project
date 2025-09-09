package BestMeat.model.dao;

import BestMeat.model.dto.CompanyDto;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
            }else{
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
            String sql = "select c.cno , c.mno , c.cname , c.caddress , c.cimg ,p.pname , s.sprice ,  round(avg(r.rrank), 1) as rrank " +
                    " from product p join stock s on p.pno = s.pno join company c on s.cno = c.cno join review r on c.cno = r.cno";
            if (key.equals("pname")){
                sql += " where pname like ? ";
            }// if end
            if ("rank".equals(order)){
                sql += " group by c.cno, c.cname, c.caddress, c.cimg, p.pname, s.sprice order by rrank desc ";
            }else if ("sprice".equals(order)){
                sql += " group by c.cno, c.cname, c.caddress, c.cimg, p.pname, s.sprice order by sprice asc ";
            }else{
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
            String sql = "select c.cno, c.mno , c.cname, c.caddress, c.cimg, ifnull(round(avg(r.rrank),1),0) as rrank"+
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
                return dto;
            }// if end
        } catch (Exception e) { System.out.println(e); }
        return null;
    }// func end

    // 정육점 전체조회
    public List<CompanyDto> getCompanyList(){
        List<CompanyDto> list = new ArrayList<>();
        try{
            String sql = "select c.cno , c.mno , c.cimg , c.cname , c.caddress , ifnull(round(avg(r.rrank),1),0) as rrank from company c left outer join review r on c.cno = r.cno group by c.cno";
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
                list.add(dto);
            }// while end
        } catch (Exception e) { System.out.println(e); }
        return list;
    }// func end
} // class end