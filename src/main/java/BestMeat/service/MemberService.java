package BestMeat.service;

import BestMeat.model.dao.MemberDao;
import BestMeat.model.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberDao memberDao;


    // [1] 회원가입
    public int signup(MemberDto dto){
        System.out.println("MemberService.signup");
        int result = memberDao.signup(dto);
        return result; //반환
    }

    // [2] 로그인
    public MemberDto login(MemberDto dto){
        System.out.println("MemberService.login");
        int result = memberDao.login(dto); // result에 로그인 조회된 회원번호(mno) 저장
        dto.setCno(memberDao.getCno(dto)); // dto에 Cno dto 값
        dto.setMno(result);  // dto에 Mno result 값
        return dto; // 반환
    }

    // [3] 아이디 찾기
    public Map<String , String> findId(Map<String , String > map){
        System.out.println("map = " + map);
        String result = memberDao.findId(map);
        Map<String , String > maps = new HashMap<>();
        if (result == null) { // 만약에 result 가 null이면
            maps.put("false", null); // 아이디 찾기 실패
        }else { // 아니면
            maps.put("true", result); // 아이디 찾기 성공
        }
        return maps; // 반환
    }
    // [4] 비밀번호 찾기
    public boolean findPwd(Map<String , String> map){
        System.out.println("MemberService.findPwd");
        boolean result = memberDao.findPwd(map);
        return result;
    }
}