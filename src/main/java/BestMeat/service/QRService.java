package BestMeat.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;


@Service
public class QRService {// class start

    // [*] qr 생성
    public byte[] BuildQR(String content){
        ByteArrayOutputStream bout = null;
        try{// QR 생성기
            MultiFormatWriter writer = new MultiFormatWriter();
            BitMatrix matrix = writer.encode( content , BarcodeFormat.QR_CODE , 200 ,200);
            bout = new ByteArrayOutputStream(); // byte 배열로 변환하기 위해 사용
            MatrixToImageWriter.writeToStream(matrix,"png",bout);
        } catch (Exception e) { System.out.println(e); }
        return bout.toByteArray();
    }// func end

    // 네이버 위도/경도 구하기
    public Map<String, Double> latLonToWebMercator(double lat, double lon) {
        double x = lon * 20037508.34 / 180.0;
        double y = Math.log(Math.tan((90.0 + lat) * Math.PI / 360.0)) / (Math.PI / 180.0);
        y = y * 20037508.34 / 180.0;

        Map<String, Double> result = new HashMap<>();
        result.put("x", x); // 경도
        result.put("y", y); // 위도
        return result;
    }// func end



}// class end
