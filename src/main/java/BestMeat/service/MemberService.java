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

    // 파일 이미지
    public boolean fileuploads(int mno , String filename){
        return memberDao.fileuploads(mno,filename); // db 이미지 저장
    }

    // [5] 회원정보 수정
    public int updateMember(MemberDto dto , HttpSession session){
        System.out.println("MemberService.updateMember");
        int mno = sessionService.getSessionNo("loginMno" , session);
        dto.setMno(mno);
        if ( memberDao.updateMember(dto)){return mno;
        }
        return 0;

    }


    // [6] 비밀번호 수정
    public boolean updatePwd(int mno , Map<String , String> map){
        System.out.println("MemberService.updatePwd");
        boolean result =  memberDao.updatePwd(mno,map);
        return result;

    }




    // [member07] 회원정보 상세조회 - getMember()
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

    // [member08] 회원 탈퇴 - resignMember()
    // 기능설명 : [ 회원번호(세션), 비밀번호 ]를 받아,  일치하면 회원활성화를 false로 변경한다.
    // 매개변수 : Map< String, Object >, session
    // 반환타입 : boolean -> 성공 : true, 실패 : false
    public boolean resignMember( Map<String , Object> map, HttpSession session ){
        // 1. 세션정봉에서 회원번호 가져오기
        int mno = sessionService.getSessionNo( "loginMno", session );
        // 2. 회원번호가 0이면, 비로그인상태이므로 메소드 종료
        if ( mno == 0 ) return false;
        // 3. Dao에게 전달할 map에 회원번호 추가하기
        map.put( "mno", mno );
        map.put( "mpwd", "qwe123" );
        // 4. Dao에게 매개변수 전달 후, 결과 반환하기
        return memberDao.resignMember( map );
    } // func end
} // class end