package com.zone.backend.service.impl.bot;

import com.zone.backend.mapper.BotMapper;
import com.zone.backend.pojo.Bot;
import com.zone.backend.pojo.User;
import com.zone.backend.service.bot.AddBotService;
import com.zone.backend.service.impl.user.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AddBotServiceImpl implements AddBotService {
    @Autowired
    private BotMapper botMapper;

    @Override
    public Map<String, String> addBot(Map<String, String> data) {
        Map<String, String> res = new HashMap<>();

        //从上下文找到这个已登录的用户
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        String title = data.get("title"), brief = data.get("brief"), code = data.get("code"), game = data.get("game");
        if (title == null || title.trim().equals("")) {
            res.put("error_message", "名称不能为空！");
            return res;
        }
        if (title.length() > 100) {
            res.put("error_message", "bot名称长度不能超过100");
            return res;
        }
        if (brief != null && brief.length() > 200) {
            res.put("error_message", "简介长度不能超过200");
            return res;
        }
        if (game == null || game.trim().equals("")) {
            res.put("error_message", "请选择对应的游戏！");
            return res;
        }
        if (code == null || code.trim().equals("")) {
            res.put("error_message", "代码不能为空！");
            return res;
        }
        if (code.length() > 50000) {
            res.put("error_message", "代码长度过长！");
            return res;
        }

        Date date = new Date();
        Bot bot = new Bot(null, user.getId(), game, title, brief, code, date);
        botMapper.insert(bot);

        res.put("error_message", "success");
        return res;
    }
}
