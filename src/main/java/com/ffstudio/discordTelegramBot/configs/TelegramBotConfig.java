package com.ffstudio.discordTelegramBot.configs;

import com.ffstudio.discordTelegramBot.controllers.receivers.TelegramReceiver;
import com.ffstudio.discordTelegramBot.controllers.resolvers.TelegramResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

@Configuration
@ComponentScan(basePackages = "com.ffstudio.discordTelegramBot")
public class TelegramBotConfig {

    @Autowired
    private Environment env;

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public TelegramLongPollingBot init(TelegramResolver telegramResolver){
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            TelegramLongPollingBot telegramLongPollingBot = new TelegramReceiver(telegramResolver);;
            telegramBotsApi.registerBot(telegramLongPollingBot);

            return telegramLongPollingBot;
        } catch (TelegramApiRequestException e) {
            throw new IllegalStateException("Failed creating bot", e);
        }
    }

}
