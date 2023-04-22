package com.zone.backend.controller.user;

import com.zone.backend.service.user.GetUserPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetUserPhotoController {
    @Autowired
    private GetUserPhotoService getUserPhotoService;

    @GetMapping("/api/user/get-photo/")
    public String getUserPhoto(@RequestParam Map<String, String> data) {
        return getUserPhotoService.getPhoto(Integer.parseInt(data.get("id")));
    }
}
