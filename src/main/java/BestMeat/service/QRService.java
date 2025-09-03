package BestMeat.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Hashtable;


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


}// class end
