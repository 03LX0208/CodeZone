package com.zone.backend.service.impl.user;

import com.zone.backend.pojo.User;
import com.zone.backend.service.impl.UserDetailsImpl;
import com.zone.backend.service.user.LoginService;
import com.zone.backend.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Map<String, String> getToken(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);  //  把传入的明文账号密码转为加密过的
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);  //看上面是否会得到用户 没有得到会抛出异常

        UserDetailsImpl loginUser = (UserDetailsImpl)authenticate.getPrincipal();
        User user = loginUser.getUser();

        String token = JwtUtil.createJWT(user.getId().toString());
        Map<String, String> res = new HashMap<>();
        res.put("error_message", "success");
        res.put("token", token);
        return res;
    }
}