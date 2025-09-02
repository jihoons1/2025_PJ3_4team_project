package BestMeat.service;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class MapService {


    /** 매개변수로 위도경도 반환해주는 기능
     * @param address
     * @return double[경도,위도]
     */
    public double[] getLatLng(String address) {
        try {
            String apikey = "A97BA8AB-2612-3296-91F3-FC3944875F00";
            String searchType = "road";
            String searchAddr = address;
            String epsg = "epsg:4326";

            StringBuilder sb = new StringBuilder("https://api.vworld.kr/req/address");
            sb.append("?service=address");
            sb.append("&request=getCoord");
            sb.append("&format=json");
            sb.append("&crs=" + epsg);
            sb.append("&key=" + apikey);
            sb.append("&type=" + searchType);
            sb.append("&address=" + URLEncoder.encode(searchAddr, StandardCharsets.UTF_8));
            URL url = new URL(sb.toString());
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
            JSONParser jspa = new JSONParser();
            JSONObject jsob = (JSONObject) jspa.parse(reader);
            JSONObject jsrs = (JSONObject) jsob.get("response");
            JSONObject jsResult = (JSONObject) jsrs.get("result");
            JSONObject jspoitn = (JSONObject) jsResult.get("point");
            double d1 = Double.parseDouble(jspoitn.get("y").toString());
            double d2 = Double.parseDouble(jspoitn.get("x").toString());
            return new double[]{ d2 , d1 };

        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }// func end

    /**
     * 두 주소 간의 위도와 경도를 기반으로 거리를 계산하는 기능
     * @param start
     * @param end
     * @return 두 주소간의 거리(km)
     */
    public double distance(double[] start , double[] end){
        // 두 지점의 경도 위도 라디안값으로 변환
        double lat1 = Math.toRadians(start[1]);
        double lng1 = Math.toRadians(start[0]);
        double lat2 = Math.toRadians(end[1]);
        double lng2 = Math.toRadians(end[0]);
        // 위도 경도 차이 계산
        double deltaLat = lat2 - lat1;
        double deltalng = lng2 - lng1;
        // 하버사인 공식 중간계산값
        double a = Math.sin(deltaLat/2) * Math.sin(deltaLat/2) +
                    Math.cos(lat1) * Math.cos(lat2) *
                    Math.sin(deltalng/2) * Math.sin(deltalng/2);
        // 두  지점간의 각도
        double c = 2 * Math.atan2(Math.sqrt(a) , Math.sqrt(1-a));
        double r = 6371; // 지구의 평균 반지름
        return r * c; // 두 지점의 직선거리
    }// func end
}// class end
