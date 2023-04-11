package com.zone.backend.controller.user;

import com.zone.backend.service.user.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/api/user/token/")
    public Map<String, String> getToken(@RequestParam Map<String, String> data) {
        return loginService.getToken(data.get("username"), data.get("password"));
    }
}


