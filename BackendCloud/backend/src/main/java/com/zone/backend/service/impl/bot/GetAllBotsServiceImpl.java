package com.zone.backend.service.impl.bot;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zone.backend.mapper.BotMapper;
import com.zone.backend.pojo.Bot;
import com.zone.backend.pojo.User;
import com.zone.backend.service.bot.GetAllBotsService;
import com.zone.backend.service.impl.user.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class GetAllBotsServiceImpl implements GetAllBotsService {
    @Autowired
    private BotMapper botMapper;

    @Override
    public List<Bot> getAll() {
        List<Bot> res = new ArrayList<>();

        //从上下文找到这个已登录的用户
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        QueryWrapper<Bot> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", user.getId());
        res = botMapper.selectList(queryWrapper);
        Collections.reverse(res);

        return res;
    }
}
