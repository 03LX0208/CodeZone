package com.zone.backend.controller.bot;

import com.zone.backend.pojo.Bot;
import com.zone.backend.service.bot.GetAllBotsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetAllBotsController {
    @Autowired
    private GetAllBotsService getAllBotsService;

    @GetMapping("/api/bot/get-all/")
    public List<Bot> getAllBots() {
        return getAllBotsService.getAll();
    }
}
