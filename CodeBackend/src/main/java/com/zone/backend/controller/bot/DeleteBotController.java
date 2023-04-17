package com.zone.backend.controller.bot;

import com.zone.backend.service.bot.DeleteBotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DeleteBotController {
    @Autowired
    private DeleteBotService deleteBotService;

    @PostMapping("/api/bot/delete/")
    public Map<String, String> deleteBot(@RequestParam Map<String, String> data) {
        return deleteBotService.deleteBot(Integer.parseInt(data.get("id")));
    }
}
