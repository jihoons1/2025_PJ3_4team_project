package BestMeat.controller;

import BestMeat.model.dto.MemberDto;
import BestMeat.model.dto.PointDto;
import BestMeat.service.FileService;
import BestMeat.service.MemberService;
import BestMeat.service.PointService;
import BestMeat.service.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    private final SessionService sessionService;
    private final FileService fileService;
    private final PointService pointService;

    private String tableName = "member/";       // 파일 업로드 경로 fileservice 에 업로드 주소(폴더위치) 있음

    // [1] 회원가입
    @PostMapping("/signup")
    public int signup(MemberDto dto) {

        System.out.println("MemberController.signup");
        MultipartFile file = dto.getUpload();
        if (file != null && !file.isEmpty()){
            String new_upload = fileService.fileUpload(file , tableName);
            if (new_upload == null) return 0;
            dto.setMimg(new_upload);
        }
        return memberService.signup(dto);
    }

    // [2] 로그인
    @PostMapping("/login")
    public int login(@RequestBody MemberDto dto, HttpServletRequest request) {
        System.out.println("MemberController.login");
        // 세션 정보
        HttpSession session = request.getSession();
        // 로그인 성공 정보
        dto = memberService.login(dto);
        int mno = dto.getMno();
        // 0. 24시간 내 1번만 로그인 포인트 지급
        // 1. 세션 속성 내 'loginHistory' 값 가져오기
        Object object = session.getAttribute( "loginHistory" );
        Map< Integer, String > loginHistory;
        // 2-1. 세션에 loginHistory가 없으면
        if ( object == null ){
            // 2-2. 새롭게 만들어주기
            loginHistory = new HashMap<>();
        } else {
            // 2-3. 존재하면, 기존 자료를 타입변환한다.
            loginHistory = (Map< Integer, String >) object;
        } // if end
        // 3. 현재 날짜를 문자열로 가져오기
        String today = LocalDate.now().toString();
        // 4. today와 mno를 조합하여, 기록을 체크한다.
        String check = loginHistory.get( mno );
        // 5. today 기록이 없거나, 기록이 today와 다르다면
        if ( check == null || !check.equals( today ) ){
            // 6. mno에게 포인트 지급
            PointDto pointDto = new PointDto();
            pointDto.setMno( mno );
            pointDto.setPlpoint( 10 );
            pointDto.setPlcomment( "로그인 포인트 지급" );
            pointService.addPointLog( pointDto );
            // 7. 오늘을 기록
            loginHistory.put( mno, today );
            // 8. 세션에 업데이트
            session.setAttribute( "loginHistory", loginHistory );
        } // if end
        // 세션에 회원번호 | 정육점번호 저장하기
        if ( dto != null ) {
            session.setAttribute("loginMno", dto.getMno());
            session.setAttribute("loginCno", dto.getCno());
        }
        return dto.getMno();
    }

    @GetMapping("/check")
    public boolean check(@RequestParam String type , @RequestParam String data ){
        boolean result = memberService.check(type,data);
        return result;
    }

    // [3] 아이디찾기
    @GetMapping("/findId")
    public Map<String , String > findId(@RequestParam Map<String , String>map){
        System.out.println("MemberController.findId");
        return memberService.findId(map);
    }

    // [4] 비밀번호찾기
    @PostMapping("/findPwd")
    public boolean findPwd( @RequestBody Map<String , String> map){
        System.out.println("MemberController.findPwd");
        return memberService.findPwd(map);
    }

    // [5] 회원정보수정
    // http://localhost:8080/member/update/Pwd
    @PutMapping("/updateMember")
    public int updateMember( @ModelAttribute MemberDto dto , HttpSession session){
        System.out.println("MemberController.updateMember");
        // session에서 mno을 꺼내서 dto에 넣기
        int loginMno = sessionService.getSessionNo( "loginMno" , session );
        dto.setMno( loginMno );
        int mno = memberService.updateMember( dto );
        if (!dto.getUpload().isEmpty()){
            MultipartFile multipartFile = dto.getUpload();
            String filename =fileService.fileUpload(multipartFile ,tableName);
            if (filename == null)return 0;
            boolean result = memberService.fileuploads( mno ,  filename);
            if (result==false){ return 0; }
            }
        return mno;
    }

    // [6] 비밀번호 수정
    @PutMapping("/update/Pwd")
    public boolean updatePwd( @RequestBody Map<String , String>map , HttpServletRequest request){
        System.out.println("MemberController.updatePwd");
        HttpSession session = request.getSession(false);
        if ( session == null || session.getAttribute("loginMno")==null) return false;
        Object obj = session.getAttribute("loginMno");
        int loginMno = (int)obj;
        boolean result = memberService.updatePwd( loginMno , map);
        if (result == true) session.removeAttribute("loginMno");
        return result;
    }

    // [member07] 회원정보 상세조회 - getMember()
    // 기능설명 : [ 회원번호(세션) ]를 받아, 해당하는 회원정보를 조회한다.
    // method : GET, URL : /member/get
    // 매개변수 : HttpSession
    // 반환타입 : MemberDto
    @GetMapping("/get")
    public MemberDto getMember( HttpSession session ){
        System.out.println("MemberController.getMember");
        // 1. 세션정보에서 회원번호 가져오기
        int mno = sessionService.getSessionNo( "loginMno", session );
        // 2. 회원번호가 0이면, 비로그인상태이므로 메소드 종료
        if ( mno == 0 ) return null;
        // 3. MemberService에게 전달 후 결과 반환
        return memberService.getMember( mno );
    } // func end

    // [member08] 회원 탈퇴 - resignMember()
    // 기능설명 : [ 회원번호(세션), 비밀번호 ]를 받아,  일치하면 회원활성화를 false로 변경한다.
    // method : POST, URL : /member/resign
    // 매개변수 : Map< String, String >
    // 반환타입 : boolean -> 성공 : true, 실패 : false
    @PostMapping("/resign")
    public boolean resignMember( @RequestBody Map<String , String> map, HttpSession session ){
        System.out.println("MemberController.resignMember");
        // 1. 세션정보에서 회원번호 가져오기
        int mno = sessionService.getSessionNo( "loginMno", session );
        // 2. 회원번호가 0이면, 비로그인상태이므로 메소드 종료
        if ( mno == 0 ) return false;
        // 3. Dao에게 전달할 map에 회원번호 추가하기
        map.put( "mno", mno + "" );
        // 4. Service에게 전달 후, 결과 받기
        boolean result = memberService.resignMember( map );
        // 5. 탈퇴에 성공했다면, 세션 초기화
        if ( result ) session.invalidate();
        // 6. 최종 반환하기
        return result;
    } // func end

    // [member09] 로그아웃 - logout()
    // 기능설명 : 로그인 세션을 초기화한다.
    // method : GET, URL : /member/logout
    // 매개변수 : X
    // 반환타입 : boolean -> 성공 : true, 실패 : false
    @GetMapping("/logout")
    public boolean logout( HttpSession session ){
        System.out.println("MemberController.logout");
        // 1. 세션정보 유효성검사하여, 비로그인상태면 실패
        if ( sessionService.getSessionNo( "loginMno", session) == 0 ) return false;
        // 2. 로그인상태라면, 세션 초기화 진행
        session.removeAttribute( "loginMno" );
        session.removeAttribute( "loginCno" );
        // 3. 결과 반환
        return true;
    } // func end
} // class end