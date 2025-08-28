package BestMeat.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class SessionService {
    // [session01] 세션에 있는 번호 반환
    // 기능설명 : [ 세션속성, session ]을 받아, 해당하는 세션속성값을 반환
    // 매개변수 : String 세션속성, session
    // 반환타입 : int -> 성공 : 존재하는 번호, 실패 : 0
    public int getSessionNo( String attribute, HttpSession session ){
        // 1. 세션정보 가져오기
        Object loginObj = session.getAttribute( attribute );
        // 2. 세션 유효성 검사
        if ( session == null || loginObj == null ) return 0;
        // 3. 세션 정보에 존재하는 번호를 int로 변환하여 반환하기
        return (int) loginObj;
    } // func end
} // func end