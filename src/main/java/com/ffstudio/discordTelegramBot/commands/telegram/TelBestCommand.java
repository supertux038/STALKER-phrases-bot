package com.ffstudio.discordTelegramBot.commands.telegram;

import com.ffstudio.discordTelegramBot.controllers.receivers.TelegramReceiver;
import com.ffstudio.discordTelegramBot.models.Phrase;
import com.ffstudio.discordTelegramBot.models.User;
import com.ffstudio.discordTelegramBot.services.GroupmentManager;
import com.ffstudio.discordTelegramBot.services.PhrasesManager;
import com.ffstudio.discordTelegramBot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.List;

@Component
public class TelBestCommand extends Command {
    @Autowired
    UserService userService;

    @Autowired
    private TelegramReceiver telegramReceiver;

    @Override
    public void execute(Update update) {
        Message message = update.getMessage();
        List<User> bestStalkers = userService.getBestStalkers();

        StringBuilder builder = new StringBuilder();
        builder.append("Сталкеры с самым острым ухом\n\n");
        for (int i = 0; i < bestStalkers.size(); i++) {
            User currentUser = bestStalkers.get(i);
            int number = i+1;
            builder.append(number);
            builder.append(" ");
            builder.append(currentUser.getNickname());
            builder.append("\nСталкер с платформы ");
            builder.append(currentUser.getPlatform());
            builder.append(" имеет рейтинг ");
            builder.append(currentUser.getRating());
            builder.append("\n");
        }
        builder.append("\nХочешь оказаться в их списке?");
        builder.append("Просто набери /play в чат");

        SendMessage sendMessage = new SendMessage(message.getChatId(), builder.toString());
        try {
            telegramReceiver.sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Header header() {
        return Header.best;
    }

    @Override
    public String description() {
        return "Вывожу трех самых умных сталкеров";
    }
}
