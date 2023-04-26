package com.zone.backend.service.impl.game;

import com.zone.backend.consumer.WebSocketServer;
import com.zone.backend.consumer.gomoku.Game;
import com.zone.backend.consumer.gomoku.Piece;
import com.zone.backend.service.game.ReceiveBotMoveService;
import org.springframework.stereotype.Service;

@Service
public class ReceiveBotMoveServiceImpl implements ReceiveBotMoveService {
    @Override
    public String receiveBotMove(Integer userId, String move) {
        System.out.println(userId + " receive " + move);
        if (WebSocketServer.users.get(userId) != null) {
            Game game = WebSocketServer.users.get(userId).game;
            String[] s = move.split(" ");
            if (game != null) {
                System.out.println(s[0]);
                game.setNowStep(new Piece(Integer.parseInt(s[0]), Integer.parseInt(s[1])), true);
            }
        }
        return "success";
    }
}
