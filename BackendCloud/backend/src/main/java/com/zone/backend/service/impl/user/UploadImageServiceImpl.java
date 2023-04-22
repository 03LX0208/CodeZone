package com.zone.backend.service.impl.user;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import com.zone.backend.mapper.UserMapper;
import com.zone.backend.pojo.User;
import com.zone.backend.service.user.UploadImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service
public class UploadImageServiceImpl implements UploadImageService {
    private final String URL = "https://codezone-1313033191.cos.ap-beijing.myqcloud.com/";

    @Autowired
    private UserMapper userMapper;

    @Override
    public Map<String, String> uploadImage(File file) {
        Map<String, String> res = new HashMap<>();
        // 第2步得到的密钥
        String secretId = "AKIDmOkovqWyAEBwiOzX3NTKxQghgV6VeXCd";
        String secretKey = "HJ6dw1rUsJelcDgBZv7GCwUyIRO7cfKj";
        // 生成用户
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 设置地区
        Region region = new Region("ap-beijing");
        ClientConfig clientConfig = new ClientConfig(region);
        clientConfig.setHttpProtocol(HttpProtocol.https);
        COSClient cosClient = new COSClient(cred, clientConfig);

        try{
            // 指定文件将要存放的存储桶
            String bucketName = "codezone-1313033191";
            // key指定该文件上传到存储桶的什么地方
            String key = "user_photo/" + file.getName();
            // 发送请求
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
            // 向前端返回需要的url
            res.put("error_message", "success");
//            res.put("url", URL + key);
            //从上下文找到这个已登录的用户
            UsernamePasswordAuthenticationToken authentication =
                    (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
            User user = loginUser.getUser();
            userMapper.updateById(new User(user.getId(), user.getUsername(), user.getPassword(), URL + key));
            return res;
        } catch (Exception clientException) {
            res.put("error_message", "error");
            clientException.printStackTrace();
            return res;
        }
    }
}