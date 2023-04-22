package com.zone.backend.service.impl.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zone.backend.mapper.UserMapper;
import com.zone.backend.pojo.User;
import com.zone.backend.service.user.GetUserPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetUserPhotoServiceImpl implements GetUserPhotoService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public String getPhoto(Integer id) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        return userMapper.selectOne(queryWrapper).getPhoto();
    }
}
