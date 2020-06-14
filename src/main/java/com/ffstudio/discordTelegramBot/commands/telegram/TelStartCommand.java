package com.ffstudio.discordTelegramBot.commands.telegram;

import com.ffstudio.discordTelegramBot.controllers.receivers.TelegramReceiver;
import com.ffstudio.discordTelegramBot.services.UserService;
import com.google.inject.internal.cglib.proxy.$UndeclaredThrowableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendSticker;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;

@Component
public class TelStartCommand extends Command {

    @Autowired
    private UserService userService;

    private String stickerId = "CAACAgIAAxkBAAM6XtDjv1aIY8e5RJ6ZKXHYbNCsdsEAAmEBAAKnWqEOma6zTJN5BVcZBA";

    @Autowired
    private TelegramReceiver telegramReceiver;

    @Override
    public void execute(Update update) {
        Message message = update.getMessage();
        StringBuilder builder = new StringBuilder();
        builder.append("Приветствую тебя, сталкер.\n");
        builder.append("Добро пожаловать в ряды сталкеров, ");
        builder.append(message.getFrom().getUserName());
        builder.append(". Я создаюсь для таких же искателей, как ты. \nУстал бродить по просторам зоны в поиске артефактов? " +
        "Остановись у костра и давай сыграем в игру \"угадай группировку\". Я буду называть фразы, присущие " +
                "сталкерам определенной группировки, а тебе нужно будет угадать их название.");
        builder.append("\nНабери /help чтобы увидесть список доступных команд");
        if (userService.createUser(new Long(message.getFrom().getId()), message.getFrom().getUserName(), "Telegram")) {
            builder.append("\n\nПоздравляю! Ты был зарегестрирован в игре под ником ");
            builder.append(message.getFrom().getUserName());
        }

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
        return Header.start;
    }

    @Override
    public String description() {
        return "Приветствую сталкера";
    }
}
