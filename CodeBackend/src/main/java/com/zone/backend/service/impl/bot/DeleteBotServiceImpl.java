package com.zone.backend.service.impl.bot;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zone.backend.mapper.BotMapper;
import com.zone.backend.pojo.Bot;
import com.zone.backend.service.bot.DeleteBotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DeleteBotServiceImpl implements DeleteBotService {
    @Autowired
    private BotMapper botMapper;

    @Override
    public Map<String, String> deleteBot(Integer bot_id) {
        Map<String, String> res = new HashMap<>();

        if (bot_id == null) {
            res.put("error_message", "请不要这么做！");
            return res;
        }

        QueryWrapper<Bot> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", bot_id);
        botMapper.delete(queryWrapper);

        res.put("error_message", "success");
        return res;
    }
}
