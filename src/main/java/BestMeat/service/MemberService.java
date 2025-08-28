package BestMeat.service;

import BestMeat.model.dao.MemberDao;
import BestMeat.model.dto.MemberDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberDao memberDao;
    private final SessionService sessionService;


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




    // [7] 회원정보 상세조회 - getMember()
    // 기능설명 : [ 회원번호(세션) ]를 받아, 해당하는 회원정보를 조회한다.
    // 매개변수 : HttpSession
    // 반환타입 : MemberDto
    public MemberDto getMember( HttpSession session ){
        // 1. 세션정보에서 회원번호 가져오기
        int mno = sessionService.getSessionNo( "loginMno", session );
        // 2. 회원번호가 0이면, 비로그인상태이므로 메소드 종료
        if ( mno == 0 ) return null;

        return memberDao.getMember( mno );
    } // func end
} // class end