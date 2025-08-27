package BestMeat.model.dao;

import BestMeat.model.dto.CompanyDto;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CompanyDao extends Dao {

    // 정육점 전체조회
    public List<CompanyDto> getCompany(int startRow , int count){
        List<CompanyDto> list = new ArrayList<>();
        try{
            String sql = "select * from company limit ? , ? ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,startRow);
            ps.setInt(2,count);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                CompanyDto dto = new CompanyDto();
                dto.setCno(rs.getInt("cno"));
                dto.setMno(rs.getInt("mno"));
                dto.setCname(rs.getString("cname"));
                dto.setCaddress(rs.getString("caddress"));
                dto.setCimg(rs.getString("cimg"));
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

} // class end