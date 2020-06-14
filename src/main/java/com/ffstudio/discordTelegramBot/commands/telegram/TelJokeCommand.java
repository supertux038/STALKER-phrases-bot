package com.ffstudio.discordTelegramBot.commands.telegram;

import com.ffstudio.discordTelegramBot.controllers.receivers.TelegramReceiver;
import com.ffstudio.discordTelegramBot.models.Joke;
import com.ffstudio.discordTelegramBot.services.JokeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendSticker;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;

@Component
public class TelJokeCommand extends Command {

    private String stickerId = "CAACAgIAAxkBAAM-XtDkOY1k1Zt0tdHnoRCWFYyZ2uQAAjUBAAKnWqEOQ0_LSDpshIoZBA";

    @Autowired
    private JokeManager jokeManager;

    @Autowired
    private TelegramReceiver telegramReceiver;

    @Override
    public void execute(Update update) {
        Message message = update.getMessage();
        StringBuilder builder = new StringBuilder();
        builder.append(jokeManager.getRandomIntroduction());
        builder.append("\n\n");
        builder.append(Joke.getRandomJoke());
        builder.append("\n\n");
        builder.append(jokeManager.getRandomReaction());

        SendMessage sendMessage = new SendMessage(message.getChatId(), builder.toString());
        SendSticker sendSticker = new SendSticker().setChatId(message.getChatId()).setSticker(stickerId);
        try {
            telegramReceiver.sendMessage(sendMessage);
            telegramReceiver.sendSticker(sendSticker);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Header header() {
        return Header.joke;
    }

    @Override
    public String description() {
        return "Говорю случайную шутку из своей памяти";
    }
}