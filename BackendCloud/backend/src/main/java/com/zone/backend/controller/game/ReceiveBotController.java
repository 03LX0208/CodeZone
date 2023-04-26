package com.zone.backend.controller.game;

import com.zone.backend.service.game.ReceiveBotMoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class ReceiveBotController {
    @Autowired
    private ReceiveBotMoveService receiveBotMoveService;

    @PostMapping("/game/receive/bot/move/")
    public String receiveBotMove(@RequestParam MultiValueMap<String, String> data) {
        Integer userId = Integer.parseInt(Objects.requireNonNull(data.getFirst("user_id")));
        String move = data.getFirst("move");
        return receiveBotMoveService.receiveBotMove(userId, move);
    }
}
