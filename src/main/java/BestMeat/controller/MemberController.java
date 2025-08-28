package BestMeat.controller;

import BestMeat.model.dto.MemberDto;
import BestMeat.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    private String tableName = "member/";       // 파일 업로드 경로

    // [1] 회원가입
    @PostMapping("/signup")
    public int signup(@RequestBody MemberDto dto) {
        System.out.println("MemberController.signup");
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
        if (dto != null) {
            session.setAttribute("loginMno", dto.getMno());
            session.setAttribute("loginCno", dto.getCno());
        }
        return dto.getMno();
    }

    // [3] 아이디찾기
    @GetMapping("/findId")
    public Map<String , String > findId(@RequestParam Map<String , String>map){
        System.out.println("MemberController.findId");
        return memberService.findId(map);
    }

    // [4] 비밀번호찾기
    @PostMapping("/findPwd")
    public boolean findPwd(@RequestParam Map<String , String> map){
        System.out.println("MemberController.findPwd");
        return memberService.findPwd(map);
    }
}