package BestMeat.service;

import BestMeat.model.dao.ChattingDao;
import BestMeat.model.dto.AlarmDto;
import BestMeat.model.dto.ChattingDto;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChattingService {
    private final ChattingDao chattingDao;
    private final AlarmService alarmService;
    // CSV 업로드 경로
    private String today = LocalDateTime.now().toString().substring( 0, 10 ).replaceAll( "-", "" );
    private String baseDir = System.getProperty("user.dir");
    private String CSVDir = baseDir + "/build/resources/main/static/csv/";

    // [chatting00] CSV 생성 기능 - createCSV()
    // 기능설명 : 방이름을 입력받아, 해당 경로에 파일명이 없으면 생성한다.
    // 매개변수 : String room -> '연월일_방이름'
    // 반환타입 : void
    public void createCSV( String room ){
        try {
            // 1. 방이름과 현재날짜를 이용하여, 파일명과 파일경로 생성
            String fileName = today + "_" + room + ".csv";
            String filePath = CSVDir + fileName;
            // 2. 파일경로를 기반으로 파일 객체 생성
            File folder = new File( CSVDir );
            // 3. 폴더가 존재하지 않으면
            if ( !folder.exists() ){
                // 4. 폴더 생성하기
                folder.mkdirs();
            } // if end
            // 5. CSV 업로드 경로 File 클래스 생성
            File file = new File( filePath );
            // 6. 지정한 경로에 파일이 존재하지 않으면
            if ( !file.exists() ){
                // 7. 파일 생성하기
                file.createNewFile();
            } // if end
            // 8. 만약 DB 테이블이 생성안되어있다면
            if ( !chattingDao.checkRoom( room ) ){
                // 9. Dao에게 전달해 DB 테이블 생성
                chattingDao.createCSV( room );
                // 10. 채팅방 개설 푸시알림 전송하기
                int mno = Integer.parseInt( room.split("_")[1] );     // room의 뒷부분이 채팅방이 개설당한 사람
                String alarm = "채팅방이 개설되었습니다.";
                AlarmDto alarmDto = AlarmDto.builder().mno( mno ).amessage( alarm ).atype( "chat" ).etc( room ).build();
                // 11. 알림 전송하기
                alarmService.addAlarm( alarmDto );
            } // if end
        } catch ( Exception e ) {
            System.out.println("[chatting00] Service 오류 발생" + e );
        } // try-catch end
    } // func end

    // [chatting01] 회원별 채팅목록 조회 - getRoomList
    // 기능설명 : [ 회원번호(세션) ]을 받아, 해당 회원의 채팅목록을 조회한다.
    // 매개변수 : HttpSession session
    // 반환타입 : List<ChattingDto>
    public List<ChattingDto> getRoomList( int mno ){
        List<ChattingDto> roomList = new ArrayList<>();
        try {
            // 0. CSV 경로의 모든 CSV 가져오기
            File file = new File( CSVDir );
            File[] files = file.listFiles();
            if ( files == null || files.length == 0) return null;
            // 1. CSV로 저장된 채팅들 순회하기
            for ( File file1 : files ){
                // 2. 구분선으로 다 끊고
                String[] names = file1.getName().split("\\.")[0].split("_");
                for ( String name : names ) {
                    // 3. 그 중에서 mno랑 같은게 있다면
                    if ( name.equals( mno + "" ) ){
                        // 4. 해당 파일의 마지막 내용을 저장
                        FileReader fileReader = new FileReader( file1 );    // FileReader로 읽기모드 객체 생성
                        CSVReader csvReader = new CSVReader( fileReader );  // CSV 객체 생성
                        List<String[]> csv = csvReader.readAll();           // 모든 정보 조회하기
                        if ( !csv.isEmpty() ){                              // csv가 비어있지 않으면
                            String[] lastRow = csv.get( csv.size() - 1 );   // 마지막 행을 조회해서
                            ChattingDto chattingDto = new ChattingDto();    // dto에 저장
                            chattingDto.setMessage( lastRow[0] );                   // 대화내용
                            chattingDto.setFrom( Integer.parseInt(lastRow[1]) );    // 발신자
                            chattingDto.setTo( Integer.parseInt(lastRow[2]) );      // 수신자
                            chattingDto.setChatdate( lastRow[3] );                  // 채팅시간
                            chattingDto.setRoomname( lastRow[4] );                  // 방이름
                            // 5. 최종적으로 리스트에 저장
                            roomList.add( chattingDto );
                        } // if end
                        csvReader.close();
                        fileReader.close();
                    } // if end
                } // for end
            } // for end
            // 6. DB에 저장된 채팅목록 가져오기
            List<ChattingDto> DBroomList = new ArrayList<>();
            // 7. DB에 저장된 채팅목록이 있다면
            if ( !DBroomList.isEmpty() ){
                // 8. CSV로 저장된 채팅목록과 합치기
                roomList.addAll( DBroomList );
            } // if end
        } catch ( Exception e ) {
            System.out.println("[chatting01] Service 오류 발생" + e );
        } // try-catch end
        // 9. 최종적으로 목록 반환
        return roomList;
    } // func end

    // [chatting02] 채팅로그 호출 기능 - getChatLog()
    // 기능설명 : 방이름을 입력받아, 해당 방의 모든 채팅로그를 조회한다.
    // 매개변수 : String room -> '방이름' -> 모든 채팅로그 조회
    // 반환타입 : List<ChattingDto>
    public List<ChattingDto> getChatLog( String room ){
        try {
            // 1. Dao에게 전달 후, DB에 존재하는 모든 채팅로그를 조회한다.
            List<ChattingDto> DBList = chattingDao.getChatLog( room );
            // 2. getCSV를 실행하여, 오늘의 채팅로그를 조회한다.
            List<ChattingDto> CSVList = getCSV( room );
            // 3. DB 채팅로그가 없다면
            if ( DBList == null || DBList.isEmpty() ){
                // 4. CSV 채팅로그만 반환
                return CSVList;
            } else {
                // 5. DB의 채팅로그가 있다면, DB 채팅로그에 합쳐서
                DBList.addAll( CSVList );
                // 6. 반환
                return DBList;
            } // if end
        } catch ( Exception e ) {
            System.out.println("[chatting02] Service 오류 발생" + e );
        } // try-catch end
        return null;
    } // func end

    // [chatting02-1] CSV 호출 기능 - getCSV
    // 기능설명 : 방이름을 입력받아, 해당 경로의 CSV를 조회한다.
    // 매개변수 : String room -> '방이름' -> 모든 CSV 조회
    // 반환타입 : List<ChattingDto>
    public List<ChattingDto> getCSV( String room ){
        try {
            // 1. 방이름과 현재날짜를 이용하여, 파일명과 파일경로 생성
            String fileName = today + "_" + room + ".csv";
            String filePath = CSVDir + fileName;
            // 2. 지정한 경로에 파일이 존재하면
            File file = new File( filePath );
            // 3. 최종적으로 반환
            return readCSV( file );
        } catch ( Exception e ) {
            System.out.println("[chatting02-1] Service 오류 발생" + e );
        } // try-catch end
        return null;
    } // func end

    // [chatting03] CSV 저장 기능 - saveCSV()
    // 기능설명 : 메시지를 입력받아, 해당 경로의 CSV에 저장한다.
    // 매개변수 : Map< String, String >
    // 반환타입 : void
    public void saveSCV( Map< String, String > map ){
        try {
            // 1. 방이름과 현재날짜를 이용하여, 파일명과 파일경로 생성
            String room = map.get("room");
            String fileName = today + "_" + room + ".csv";
            String filePath = CSVDir + fileName;
            // 2. FileWriter를 이용한 쓰기모드 객체 생성
            FileWriter fileWriter = new FileWriter( filePath, true );
            // 3. CSVWriter에 fileWriter 객체를 대입하여 CSV 객체 생성
            CSVWriter csvWriter = new CSVWriter( fileWriter );
            // 4. map을 저장할 빈 리스트 생성
            List<String[]> list = new ArrayList<>();
            // 5. 메시지의 정보를 리스트에 대입하기
            list.add( new String[]{ map.get("message"), map.get("from"), map.get("to"), map.get("date"), map.get("room") } );
            // 6. 정보가 담긴 리스트를 CSV객체에 저장한다
            csvWriter.writeAll( list );
            // 7. CSVWriter를 안전하게 닫기
            csvWriter.close();
        } catch ( Exception e ) {
            System.out.println("[chatting03] Service 오류 발생" + e );
        } // try-catch end
    } // func end

    // [chatting04] DB 저장 기능 - saveDB()
    // 기능설명 : csv 폴더 내의 모든 csv 파일을 DB처리 후 삭제한다.
    // 매개변수 :
    // 반환타입 : void
    // 매일 0시에 CSV의 자료 DB에 저장
    @Scheduled( cron = "* * 0 * * *" )
    public void saveDB(){
        try {
            // 1. CSV 경로의 모든 CSV 가져오기
            File file = new File( CSVDir );
            File[] files = file.listFiles();
            // 2. 만약에 파일들이 없으면 메소드 종료
            if ( files == null || files.length == 0 ) return;
            // 2. 배열을 순회하면서
            for( File f : files ){
                // 3. 파일을 읽고
                List<ChattingDto> list = readCSV( f );
                // 4. list를 dao에게 전달해서 모든 로그를 DB에 저장
                if ( chattingDao.saveDBLog( list ) ){
                    // 5. 저장한 파일을 삭제하기
                    if ( f.delete() ){
                        // 6. 삭제에 성공했으면, 완료 메시지 출력
                        System.out.println( f.getName() + " 파일 처리 및 삭제 완료." );
                    } else {
                        // 7. 삭제에 실패했으면, 실패 메시지 출력
                        System.out.println( f.getName() + " 파일 삭제 실패." );
                    } // if end
                } // if end
            } // for end
        } catch ( Exception e ) {
            System.out.println("[chatting04] Service 오류 발생" + e );
        } // try-catch end
    } // func end

    // [chatting05] CSV 읽기 기능 - readCSV()
    // Service 내의 중복 코드 제거를 위해서
    public List<ChattingDto> readCSV( File file ){
        List<ChattingDto> list = new ArrayList<>();
        try {
            if ( file.exists() ){
                // 3. FileReader를 이용한 읽기모드 객체 생성
                FileReader fileReader = new FileReader( file );
                // 4. CSVReader에 fileReader를 대입하여 CSV 객체 생성
                CSVReader csvReader = new CSVReader( fileReader );
                // 5. .readAll을 통해 모든 정보 조회하기
                List<String[]> csv = csvReader.readAll();
                // 6. 생성된 배열을 순회하며, 각 행을 꺼낸다.
                // 대화내용 | 발신자 | 수신자 | 채팅시간 | 방이름
                for ( int i = 0; i < csv.size(); i++ ) {
                    String[] row = csv.get(i);
                    ChattingDto chattingDto = new ChattingDto();
                    chattingDto.setMessage( row[0] );                   // 대화내용
                    chattingDto.setFrom( Integer.parseInt(row[1]) );    // 발신자
                    chattingDto.setTo( Integer.parseInt(row[2]) );      // 수신자
                    chattingDto.setChatdate( row[3] );                  // 채팅시간
                    chattingDto.setRoomname( row[4] );                  // 방이름
                    // 7. 생성한 dto를 list에 넣는다
                    list.add( chattingDto );
                } // for end
                csvReader.close();
                fileReader.close();
            } // if end
        } catch ( Exception e ) {
            System.out.println("[chatting05] 오류 발생" + e );
        } // try-catch end
        return list;
    } // func end
} // class end