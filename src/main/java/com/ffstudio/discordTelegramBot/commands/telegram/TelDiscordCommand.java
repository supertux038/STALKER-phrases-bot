package com.ffstudio.discordTelegramBot.commands.telegram;

import com.ffstudio.discordTelegramBot.controllers.receivers.TelegramReceiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.Map;

@Component
public class TelDiscordCommand extends Command {

    @Autowired
    private TelegramReceiver telegramReceiver;

    private String discordBotUrl = "https://discord.com/api/oauth2/authorize?client_id=709309685412397146&permissions=6144&scope=bot";

    @Override
    public void execute(Update update) {
        Message message = update.getMessage();
        StringBuilder builder = new StringBuilder();
        builder.append("Хочешь дискорд-версию меня?\n");
        builder.append("Держи! Мне не жалко.\n");
        builder.append(discordBotUrl);
        SendMessage sendMessage = new SendMessage(message.getChatId(), builder.toString());
        try {
            telegramReceiver.sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Header header() {
        return Header.discord;
    }

    @Override
    public String description() {
        return "Даю ссылку на дискорд версию этого бота";
    }
}
