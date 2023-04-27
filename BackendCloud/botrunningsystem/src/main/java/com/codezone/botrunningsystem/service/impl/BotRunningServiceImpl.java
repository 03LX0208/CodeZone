package com.codezone.botrunningsystem.service.impl;

import com.codezone.botrunningsystem.service.BotRunningService;
import com.codezone.botrunningsystem.service.impl.utils.BotPool;
import org.springframework.stereotype.Service;

@Service
public class BotRunningServiceImpl implements BotRunningService {
    public final static BotPool botPool = new BotPool();

    @Override
    public String addBot(Integer userId, String lang, String botCode, String input) {
        botPool.addBot(userId, lang, botCode, input);
        return "success";
    }
}
