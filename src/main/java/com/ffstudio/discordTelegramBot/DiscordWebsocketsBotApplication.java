package com.ffstudio.discordTelegramBot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class DiscordWebsocketsBotApplication {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        SpringApplication.run(DiscordWebsocketsBotApplication.class, args);
    }

}
