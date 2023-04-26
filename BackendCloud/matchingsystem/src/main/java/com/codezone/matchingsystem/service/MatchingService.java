package com.codezone.matchingsystem.service;

public interface MatchingService {
    String addPlayer(Integer userId, Integer botId);
    String removePlayer(Integer userId);
}
