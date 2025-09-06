package BestMeat.service;

import BestMeat.model.dao.MemberDao;
import BestMeat.model.dao.PointDao;
import BestMeat.model.dto.MemberDto;
import BestMeat.model.dto.PointDto;
import BestMeat.model.dto.ReviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberDao memberDao;
    private final ReviewService reviewService;
    private final PointService pointService;
    private final PointDao pointDao;


    // [1] 회원가입
    public int signup(MemberDto dto){
        System.out.println("MemberService.signup");

        int mno = memberDao.signup(dto);
        // 1. 회원가입에 성공했다면, 해당 회원에게 500 포인트 지급
        PointDto pointDto = new PointDto();
        pointDto.setMno( mno );
        pointDto.setPlpoint( 500 );
        pointDto.setPlcomment( "회원가입 포인트 지급" );
        boolean addPoint = pointService.addPointLog( pointDto );
        // 2. 지급에 실패했으면, 메소드 종료
        if ( !addPoint ) return 0;
        // 3. 지급에 성공했다면, 생성된 회원번호 반환
        return mno;
    } // func end

    // [1-2] 중복값 여부
    public boolean check(String type , String data){
        System.out.println("MemberService.check");
        boolean result = memberDao.check(type,data);
        return result;
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
            maps.put("mid", ""); // 아이디 찾기 실패
        }else { // 아니면
            maps.put("mid", result); // 아이디 찾기 성공
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
    public int updateMember(MemberDto dto){
        System.out.println("MemberService.updateMember");
        if ( memberDao.updateMember(dto)){
            return dto.getMno();
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
    public MemberDto getMember( int mno ){
        // 1. ReviewService에서 회원별 리뷰목록 받아오기
        List<ReviewDto> reviewDtoList = reviewService.getMnoReview( mno );
        // 4. 리뷰목록을 MemberDto에 넣기
        MemberDto memberDto = memberDao.getMember( mno );
        memberDto.setReviewDtoList( reviewDtoList );
        // 5. mno를 통해, cno 반환받기
        int cno = memberDao.getCno( mno );
        memberDto.setCno( cno );
        // 6. mno를 통해, 포인트 총액 반환받기
        int totalPoint = pointDao.getTotalPoint( mno );
        memberDto.setTotalPoint( totalPoint );
        // 6. 최종적으로 반환
        return memberDto;
    } // func end

    // [member08] 회원 탈퇴 - resignMember()
    // 기능설명 : [ 회원번호(세션), 비밀번호 ]를 받아,  일치하면 회원활성화를 false로 변경한다.
    // 매개변수 : Map< String, String >, session
    // 반환타입 : boolean -> 성공 : true, 실패 : false
    public boolean resignMember( Map<String , String> map ){
        // 1. Dao에게 매개변수 전달 후, 결과 반환하기
        return memberDao.resignMember( map );
    } // func end

    // [member10] 회원이름 반환 - getMname()
    // 기능설명 : [ 회원번호 ]를 받아, 해당하는 회원이름을 반환한다.
    // 매개변수 : int mno
    // 매개변수 : String mname
    public String getMname( int mno ){
        // 1. Dao에게 매개변수 전달 후, 결과 반환하기
        return memberDao.getMname( mno );
    } // func end
} // class end