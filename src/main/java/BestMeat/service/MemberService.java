package BestMeat.service;

import BestMeat.model.dao.MemberDao;
import BestMeat.model.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberDao memberDao;

    public int signup(MemberDto dto){
        System.out.println("MemberService.signup");
        int result = memberDao.signup(dto);
        return result;
    }


    public MemberDto login(MemberDto dto){
        System.out.println("MemberService.login");
        int result = memberDao.login(dto);
        dto.setCno(memberDao.getCno(dto));
        dto.setMno(result);
        return dto;
    }
}