package BestMeat.service;

import BestMeat.model.dao.ChattingDao;
import BestMeat.model.dto.ChattingDto;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import lombok.RequiredArgsConstructor;
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
    // CSV 업로드 경로
    private String today = LocalDateTime.now().toString().substring( 0, 10 ).replaceAll( "-", "" );
    private String baseDir = System.getProperty("user.dir");
    private String CSVDir = baseDir + "/build/resources/main/static/csv/";

    // [chatting01] CSV 생성 기능 - createCSV()
    // 기능설명 : 방이름을 입력받아, 해당 경로에 파일명이 없으면 생성한다.
    // 매개변수 : String room -> '연월일_방이름'
    // 반환타입 : void
    public void createCSV( String room ){
        try {
            // 1. 방이름과 현재날짜를 이용하여, 파일명과 파일경로 생성
            String fileName = today + "_" + room + ".csv";
            String filePath = CSVDir + fileName;
            // 2. 파일경로를 기반으로 파일 객체 생성
            File file = new File( CSVDir );
            // 3. 폴더가 존재하지 않으면
            if ( !file.exists() ){
                // 4. 폴더 생성하기
                file.mkdir();
            } // if end
            // 5. CSV 업로드 경로 File 클래스 생성
            file = new File( filePath );
            // 6. 지정한 경로에 파일이 존재하지 않으면
            if ( !file.exists() ){
                // 7. 파일 생성하기
                file.createNewFile();
            } // if end
            // 8. Dao에게 전달해 DB 테이블 생성
            chattingDao.createCSV( room );
        } catch ( Exception e ) {
            System.out.println("[chatting01] Service 오류 발생" + e );
        } // try-catch end
    } // func end

    // [chatting02] 채팅로그 호출 기능 - getChatLog()
    // 기능설명 : 방이름을 입력받아, 해당 방의 모든 채팅로그를 조회한다.
    // 매개변수 : String room -> '방이름' -> 모든 채팅로그 조회
    // 반환타입 : List<ChattingDto>
    public List<ChattingDto> getChatLog( String room ){
        try {
            // 1. Dao에게 전달 후, DB에 존재하는 모든 채팅로그를 조회한다.
            List<ChattingDto> DBList = null;
            // 2. getCSV를 실행하여, 오늘의 채팅로그를 조회한다.
            List<ChattingDto> CSVList = getCSV( room );
            // 3. 두 리스트를 합쳐서 반환한다.
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
            FileWriter fileWriter = new FileWriter( filePath );
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
    public void saveDB() {
        try {
            // 1. CSV 경로의 모든 CSV 가져오기
            File file = new File( CSVDir );
            File[] files = file.listFiles();
            // 2. 배열을 순회하면서
            for( File f : files ){
                // 3. 파일을 읽고
                List<ChattingDto> list = readCSV( f );
                // 4. list를 dao에게 전달해서 모든 로그를 DB에 저장
                chattingDao.saveDBLog( list );
            } // for end
        } catch ( Exception e ) {
            System.out.println("[chatting04] 오류 발생" + e );
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
            } // if end
        } catch ( Exception e ) {
            System.out.println("[chatting05] 오류 발생" + e );
        } // try-catch end
        return list;
    } // func end
} // class end