package BestMeat.service;

import BestMeat.model.dao.CompanyDao;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyDao companyDao;

    // [company01] 정육점번호 세션 확인
    // 기능설명 : 세션을 받아, 정육점번호를 반환한다.
    // 매개변수 : session
    // 반환타입 : int -> 성공 : 정육점번호, 실패 : 0
    public int getLoginCno( HttpSession session ){
        // 1. 세션 가져오기
        Object loginCno = session.getAttribute( "loginCno" );
        // 2. 세선졍보에서 정육점번호 가져오기
        int cno = loginCno == null ? 0 : (int) loginCno;
        // 3. 정육점번호 반환하기
        return cno;
    } // func end
} // class end