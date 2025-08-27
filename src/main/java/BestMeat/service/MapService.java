package BestMeat.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class MapService {
    // 발급받은 apiKey
    private String apiKey = "A97BA8AB-2612-3296-91F3-FC3944875F00";
    // HTTP 요청을 위한 Spring의 RestTemplate
    private final RestTemplate restTemplate = new RestTemplate();
    // JSON 파싱을 위한 Jackson의 ObjectMapper
    private final ObjectMapper objectMapper = new ObjectMapper();

    /** 매개변수로 위도경도 반환해주는 기능
     * @param address
     * @return double[경도,위도]
     */
    public double[] getLatLng(String address){
        String url = UriComponentsBuilder.fromHttpUrl("http://api.vworld.kr/req/address")
                .queryParam("service" , "address")
                .queryParam("request" , "getCoord")
                .queryParam("version" , "2.0")
                .queryParam("crs" , "EPSG:4326")
                .queryParam("address" , address)
                .queryParam("type" , "ROAD") // ROAD : 도로명 , PARCEL : 지번
                .queryParam("key" , apiKey)
                .queryParam("format" , "json")
                .toUriString();
        try{
            String response = restTemplate.getForObject(url,String.class);
            JsonNode root = objectMapper.readTree(response);
            JsonNode result = root.path("response").path("result").path(0).path("point");
            double lng = result.get("x").asDouble();
            double lat = result.get("y").asDouble();
            return new double[]{ lng , lat };
        }catch (Exception e){ System.out.println(e); }
        return null;
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
