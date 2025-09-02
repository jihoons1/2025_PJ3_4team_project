package BestMeat.controller;

import BestMeat.model.dto.NoticeDto;
import BestMeat.service.NoticeService;
import BestMeat.service.SessionService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {
    private final NoticeService noticeService;
    private final SessionService sessionService;

    // [notice01] 알림등록 - addNotice()
    // 기능설명 : [ 회원번호(세션), 제품번호, 알림설정가격 ]을 받아, Notice DB에 저장한다. 저장된 조건 만족 시, 문자 API를 통해 알림 전송한다.
    // method : POST, URL : /notice/add
    // 매개변수 : NoticeDto, session
    // 반환타입 : int -> 성공 : 자동생성된 PK값, 실패 : 0
    @PostMapping("/add")
    public int addNotice( @RequestBody NoticeDto noticeDto, HttpSession session ){
        System.out.println("NoticeController.addNotice");
        System.out.println("noticeDto = " + noticeDto);

        // 1. 세선정보에서 회원번호 가져오기
        int mno = sessionService.getSessionNo( "loginMno", session );
        // 2. Dto에 값 넣기
        noticeDto.setMno( mno );
        // 3. Service에게 전달 후, 결과 반환
        return noticeService.addNotice( noticeDto );
    } // func end

    // [notice03] 회원별 알림조회 - getMnoNotice()
    // 기능설명 : [ 회원번호(세션) ]을 받아, 해당하는 알림을 조회한다.
    // method : GET, URL : /notice/get
    // 매개변수 : session
    // 반환타입 : List<NoticeDto>
    @GetMapping("/get")
    public List<NoticeDto> getMnoNotice( HttpSession session ){
        System.out.println("NoticeController.getMnoNotice");

        // 1. 세션정보에서 회원번호 가져오기
        int mno = sessionService.getSessionNo( "loginMno", session );
        // 2. 비로그인 상태면 메소드 종료
        if ( mno == 0 ) return null;
        // 3. Service에게 전달 후, 결과 반환
        return noticeService.getMnoNotice( mno );
    } // func end

    // [notice04] 알림수정 - updateNotice
    // 기능설명 : [ 회원번호(세션), 알림번호, 알림설정가격 ]을 받아, 해당하는 알림을 수정한다.
    // method : PUT, URL : /notice/update
    // 매개변수 : NoticeDto, session
    // 반환타입 : boolean
    @PutMapping("/update")
    public boolean updateNotice( @RequestBody NoticeDto noticeDto, HttpSession session ){
        System.out.println("NoticeController.updateNotice");

        // 1. 세션정보에서 회원번호 가져오기
        int mno = sessionService.getSessionNo( "loginMno", session );
        // 2. 비로그인 상태라면, 메소드 종료
        if ( mno == 0 ) return false;
        // 3. 현재날짜를 Dto에 넣기
        String today = LocalDateTime.now().toString();
        noticeDto.setNdate( today );
        // 4. Dao에게 전달할 dto에 mno 넣기
        noticeDto.setMno( mno );
        // 5. Service에게 전달 후, 결과 반환
        return noticeService.updateNotice( noticeDto );
    } // func end

    // [notice05] 알림삭제 - deleteNotice
    // 기능설명 : [ 회원번호(세션), 알림번호 ]를 받아, 해당하는 알림을 삭제한다.
    // method : DELETE, URL : /notice/delete
    // 매개변수 : session, int nno
    // 반환타입 : boolean
    @DeleteMapping("/delete")
    public boolean deleteNotice( @RequestParam int nno, HttpSession session ){
        System.out.println("NoticeController.deleteNotice");

        // 1. 세션정보에서 회원번호 가져오기
        int mno = sessionService.getSessionNo( "loginMno", session );
        // 2. 비로그인 상태라면, 메소드 종료
        if ( mno == 0 ) return false;
        // 3. Service에게 전달할 Map< String, Integer > 선언
        Map< String, Integer > map = new HashMap<>();
        // 4. Map에 값 넣기
        map.put( "nno", nno );
        map.put( "mno", mno );
        // 3. Service에게 전달 후, 결과 반환
        return noticeService.deleteNotice( map );
    } // func end
} // class end