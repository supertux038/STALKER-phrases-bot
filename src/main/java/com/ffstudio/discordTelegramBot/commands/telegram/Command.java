package com.ffstudio.discordTelegramBot.commands.telegram;

import com.ffstudio.discordTelegramBot.controllers.receivers.TelegramReceiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;

@Component
public abstract class Command {

    @Autowired
    private TelegramReceiver telegramReceiver;

    public abstract void execute(Update update);
    public abstract Header header();
    public abstract String description();

    protected void sendMessage(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage(chatId, message);
        try {
            telegramReceiver.sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public enum Header {
        start,
        help,
        play,
        joke,
        question,
        phrase,
        answer,
        rules,
        discord,
        best,
        changename
    }

}
