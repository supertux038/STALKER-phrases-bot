package com.ffstudio.discordTelegramBot.commands.telegram;

import com.ffstudio.discordTelegramBot.controllers.receivers.TelegramReceiver;
import com.ffstudio.discordTelegramBot.controllers.resolvers.TelegramResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.Map;

@Component
public class TelHelpCommand extends Command {

    @Autowired
    private TelegramResolver telegramResolver;

    @Autowired
    private TelegramReceiver telegramReceiver;

    @Override
    public void execute(Update update) {
        Map<String, Command> commandMap = telegramResolver.getCommandMap();
        Message message = update.getMessage();
        StringBuilder builder = new StringBuilder();
        builder.append("Помощь по моим командам\n");
        builder.append("Нуждаешься в помощи, сталкер? Спроси - подскажу\n");
        for (Map.Entry<String, Command> entry: commandMap.entrySet()) {
            builder.append("\n/");
            builder.append(entry.getKey());
            builder.append(" - ");
            builder.append(entry.getValue().description());
        }
        SendMessage sendMessage = new SendMessage(message.getChatId(), builder.toString());
        try {
            telegramReceiver.sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Header header() {
        return Header.help;
    }

    @Override
    public String description() {
        return "Вывожу список команд";
    }
}
