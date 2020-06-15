package com.ffstudio.discordTelegramBot.controllers.receivers;

import com.ffstudio.discordTelegramBot.controllers.resolvers.TelegramResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

@Component
public class TelegramReceiver extends TelegramLongPollingBot {
    
    @Value("${telegram-token}")
    private String token;

    private final TelegramResolver telegramResolver;

    public TelegramReceiver(@Lazy TelegramResolver telegramResolver){
        this.telegramResolver = telegramResolver;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage()) {
//            if (update.getMessage().getSticker() != null)
//                System.out.println(update.getMessage().getSticker().getFileId());
            telegramResolver.executeCommand(update);
        }
    }

    @Override
    public String getBotUsername() {
        return "STALKER gaming bot";
    }

    @Override
    public String getBotToken() {
        return token;
    }
}
