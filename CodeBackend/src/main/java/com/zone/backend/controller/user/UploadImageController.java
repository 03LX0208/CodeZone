package com.zone.backend.controller.user;


import com.zone.backend.service.user.UploadImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@RestController
public class UploadImageController {
    @Autowired
    private UploadImageService uploadImageService;

    @PostMapping("/api/upload/image/")
    public Map<String, String> uploadImage(@RequestParam("image") MultipartFile multipartFile) throws IOException {
        File file = null;
        // 把MultipartFile类型转为File类型
        try {
            String originalFilename = multipartFile.getOriginalFilename();
            assert originalFilename != null;
            String[] filename = originalFilename.split("\\.");
            file=File.createTempFile(filename[0], "." + filename[1]); // 创建一个自动回收的临时文件
            multipartFile.transferTo(file); // MultipartFile类型转为File类型
            file.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return uploadImageService.uploadImage(file);
    }
}