package BestMeat.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class FileService {
    // [*] 업로드 경로
    private String baseDir = System.getProperty("user.dir");
    private String uploadPath = baseDir + "/build/resources/main/static/upload/";

    // 파일 업로드 기능
    public String fileUpload(MultipartFile multipartFile, String tableName ){
        String uuid = UUID.randomUUID().toString();
        String fileName = uuid + "_" + multipartFile.getOriginalFilename().replaceAll("_","-");
        String uploadfilePath = uploadPath + tableName + fileName;
        File pathFile = new File(uploadPath + tableName);
        if (!pathFile.exists()){
            pathFile.mkdir();
        }// if end
        File uploadFile = new File(uploadfilePath);
        try{
            multipartFile.transferTo(uploadFile);
        } catch (Exception e) { System.out.println(e); return null; }
        return fileName;
    }// func end
}
