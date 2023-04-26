package com.codezone.matchingsystem.service.impl;

import com.codezone.matchingsystem.service.MatchingService;
import com.codezone.matchingsystem.service.impl.utils.MatchingPool;
import org.springframework.stereotype.Service;

@Service
public class MatchingServiceImpl implements MatchingService {
    public static MatchingPool matchingPool = new MatchingPool();

    @Override
    public String addPlayer(Integer userId, Integer botId) {
        System.out.println("add " + userId);
        matchingPool.addPlayer(userId, botId);
        return "add success";
    }

    @Override
    public String removePlayer(Integer userId) {
        System.out.println("remove " + userId);
        matchingPool.removePlayer(userId);
        return "remove success";
    }
}
