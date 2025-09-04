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
    // [*]
    public void QRMake(File qrFile, String qrCodeText, int size, String fileType){
        System.out.println("QRService.QRMake");
        System.out.println("qrFile = " + qrFile + ", qrCodeText = " + qrCodeText + ", size = " + size + ", fileType = " + fileType);
        Hashtable<EncodeHintType , ErrorCorrectionLevel> hintMap = new Hashtable<>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION,ErrorCorrectionLevel.L);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, size, size, hintMap);
            int matrixWidth = bitMatrix.getWidth();
            BufferedImage image = new BufferedImage(matrixWidth,matrixWidth,BufferedImage.TYPE_INT_RGB);
            image.createGraphics();
            Graphics2D graphics = (Graphics2D) image.getGraphics();
            // 배경색
            graphics.setColor(Color.white);
            graphics.fillRect(0,0,matrixWidth,matrixWidth);
            // QR 코드색
            graphics.setColor(Color.BLACK);
            for (int i = 0; i < matrixWidth; i++){
                for (int a = 0; a < matrixWidth; a++){
                    if (bitMatrix.get(i,a)){
                        graphics.fillRect(i,a,1,1);
                    }// if end
                }// for end
            }// for end
            ImageIO.write(image, fileType, qrFile);
        }catch (Exception e){ System.out.println(e); }
    }// func end

    // [*]
    public ResponseEntity<byte[]> BuildQR(String content){
        byte[] qrImage = null;
        ByteArrayOutputStream bout = null;
        try{// QR 생성기
            MultiFormatWriter writer = new MultiFormatWriter();
            BitMatrix matrix = writer.encode( content , BarcodeFormat.QR_CODE , 200 ,200);
            bout = new ByteArrayOutputStream(); // byte 배열로 변환하기 위해 사용
            MatrixToImageWriter.writeToStream(matrix,"png",bout);
        } catch (Exception e) { System.out.println(e); }
        // ResponseEntity를 이용해 HTTP 응답 반환
        // - 상태코드: 200 OK
        // - Content-Type: image/png (브라우저/클라이언트가 이미지로 인식하게 함)
        // - Body: QR 코드 이미지 데이터 (byte[])
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(bout.toByteArray());
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
