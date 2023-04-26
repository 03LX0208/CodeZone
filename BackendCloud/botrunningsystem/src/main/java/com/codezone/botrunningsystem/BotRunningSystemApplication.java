package com.codezone.botrunningsystem;

import com.codezone.botrunningsystem.service.impl.BotRunningServiceImpl;
import com.codezone.botrunningsystem.service.impl.utils.BotPool;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BotRunningSystemApplication {
    public static void main(String[] args) {
        BotRunningServiceImpl.botPool.start();
        SpringApplication.run(BotRunningSystemApplication.class, args);
    }
}