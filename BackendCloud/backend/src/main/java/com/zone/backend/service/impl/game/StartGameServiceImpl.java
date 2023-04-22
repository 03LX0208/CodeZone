package com.zone.backend.service.impl.game;

import com.zone.backend.consumer.WebSocketServer;
import com.zone.backend.service.game.StartGameService;
import org.springframework.stereotype.Service;

@Service
public class StartGameServiceImpl implements StartGameService {

    @Override
    public String startGame(Integer aId, Integer bId) {
        System.out.println("start " + aId + " " + bId);
        WebSocketServer.startGame(aId, bId);
        return "success";
    }
}
