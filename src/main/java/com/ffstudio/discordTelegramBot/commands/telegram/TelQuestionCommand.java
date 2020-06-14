package com.ffstudio.discordTelegramBot.commands.telegram;

import com.ffstudio.discordTelegramBot.controllers.receivers.TelegramReceiver;
import com.ffstudio.discordTelegramBot.models.Phrase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendSticker;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;

@Component
public class TelQuestionCommand extends Command {

    private String stickerId = "CAACAgIAAxkBAAMeXtDhXld4eb2Fxb7gazKxgZQcqkYAAiYBAAKnWqEORVnVzLu8UMAZBA";

    @Autowired
    private TelegramReceiver telegramReceiver;

    @Override
    public void execute(Update update) {
        Message message = update.getMessage();
        StringBuilder builder = new StringBuilder();
        builder.append("Ты меня звал, сталкер?\n");
        builder.append("Ты случайно написал мне или неверено указал команду?\n" +
                "По данной команде у меня пока нет никаких функций.\n");
        builder.append("Набери /help чтобы увидесть список доступных команд");
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
        return Header.question;
    }

    @Override
    public String description() {
        return "Переспрашиваю пользователя, обращался ли он ко мне. Если да, возвращаю команду для вывода окна помощи";
    }
}
