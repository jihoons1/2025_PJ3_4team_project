package BestMeat.controller;

import BestMeat.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class FileController {
    private final FileService fileService;

    // 파일 업로드
    @PostMapping("/upload")
    public String fileUpload(MultipartFile multipartFile){
        return fileService.fileUpload(multipartFile);
    }// func end


}
