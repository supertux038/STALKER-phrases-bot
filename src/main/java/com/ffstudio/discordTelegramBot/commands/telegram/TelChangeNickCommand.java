package com.ffstudio.discordTelegramBot.commands.telegram;

import com.ffstudio.discordTelegramBot.controllers.receivers.TelegramReceiver;
import com.ffstudio.discordTelegramBot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;

@Component
public class TelChangeNickCommand extends Command {

    @Autowired
    private UserService userService;

    @Autowired
    private TelegramReceiver telegramReceiver;

    @Override
    public void execute(Update update) {
        Message message = update.getMessage();
        StringBuilder builder = new StringBuilder();

        if (message.getText().split(" ").length == 2) {
            String newName = message.getText().split(" ")[1];
            builder.append("Ты захотел, чтобы тебя называли под-дургому?");
            if (userService.changeName(new Long(message.getFrom().getId()), newName)) {
                builder.append(" Без проблем!\n");
                builder.append("Теперь я буду звать тебя ");
                builder.append(newName);
            } else {
                builder.append("Извини, во время изменения имени что-то пошло не так. " +
                        "Возможно ты не зарегистрирован в моей памяти? Напиши /start");
            }
        } else {
            builder.append("Чтобы сменить имя введи команду и имя, каким ты хочешь чтобы тебя называли \"/changename ИМЯ\"");
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
        return Header.changename;
    }

    @Override
    public String description() {
        return "Меняю имя сталкера у себя в памяти";
    }

}
