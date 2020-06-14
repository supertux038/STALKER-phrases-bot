package com.ffstudio.discordTelegramBot.commands.telegram;

import com.ffstudio.discordTelegramBot.controllers.receivers.TelegramReceiver;
import com.ffstudio.discordTelegramBot.models.Phrase;
import com.ffstudio.discordTelegramBot.services.GroupmentManager;
import com.ffstudio.discordTelegramBot.services.PhrasesManager;
import com.ffstudio.discordTelegramBot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;

@Component
public class TelPlayCommand extends Command {

    @Autowired
    UserService userService;

    @Autowired
    private PhrasesManager phrasesManager;

    @Autowired
    private GroupmentManager groupmentManager;

    @Autowired
    private TelegramReceiver telegramReceiver;

    @Override
    public void execute(Update update) {
        Message message = update.getMessage();
        StringBuilder builder = new StringBuilder();
        Phrase phrase = phrasesManager.getRandomPhrase();
        builder.append("Давай поиграем\n\n");
        builder.append("Сейчас я назову фразу и тебе с помощью команды [/answer ОТВЕТ] надо будет отправить группировку или персонажа," +
                " которые разговаривают подобной образом.\nДумай, сталкер, хорошо. У тебя одна попытка дать верный ответ.\n");
        builder.append("\nФраза:\n");
        builder.append(phrase.getPhrase());
        builder.append("\n\nВарианты ответа:\n");
        builder.append(groupmentManager.getGroupments());
        userService.setUserAnswer(new Long(message.getFrom().getId()), phrase.getGroupment());
        SendMessage sendMessage = new SendMessage(message.getChatId(), builder.toString());
        try {
            telegramReceiver.sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Header header() {
        return Header.play;
    }

    @Override
    public String description() {
        return "Давай поиграем в игру, сталкер. Я называю фразу, тебе нужно угадать сталкеры из какой группировки ее произносят.";
    }
}
