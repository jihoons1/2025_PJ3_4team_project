package BestMeat.controller;

import BestMeat.model.dto.NoticeDto;
import BestMeat.service.NoticeService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {
    private final NoticeService noticeService;

    // [notice01] 알림등록 - addNotice()
    // 기능설명 : [ 회원번호(세션), 제품번호, 알림설정가격 ]을 받아, Notice DB에 저장한다. 저장된 조건 만족 시, 문자 API를 통해 알림 전송한다.
    // method : POST, URL : /notice/add
    // 매개변수 : NoticeDto, session
    // 반환타입 : int -> 성공 : 자동생성된 PK값, 실패 : 0
    @PostMapping("/add")
    public int addNotice( @RequestBody NoticeDto noticeDto, HttpSession session ){
        System.out.println("NoticeController.addNotice");
        System.out.println("noticeDto = " + noticeDto);

        return noticeService.addNotice( noticeDto, session );
    } // func end
} // class end