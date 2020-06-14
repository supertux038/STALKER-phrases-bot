package com.ffstudio.discordTelegramBot.commands.telegram;

import com.ffstudio.discordTelegramBot.controllers.receivers.TelegramReceiver;
import com.ffstudio.discordTelegramBot.models.Phrase;
import com.ffstudio.discordTelegramBot.services.PhrasesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;

@Component
public class TelPhraseCommand extends Command {

    @Autowired
    private PhrasesManager phrasesManager;

    @Autowired
    private TelegramReceiver telegramReceiver;

    @Override
    public void execute(Update update) {
        Phrase phrase = phrasesManager.getRandomPhrase();
        Message message = update.getMessage();
        StringBuilder builder = new StringBuilder();
        builder.append("Хочешь пару фраз от сталкеров всех мастей и группировок?\n");
        builder.append(phrase.getFullPhrase());
        SendMessage sendMessage = new SendMessage(message.getChatId(), builder.toString());
        try {
            telegramReceiver.sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Header header() {
        return Header.phrase;
    }

    @Override
    public String description() {
        return "Говорю пару слов сталкеру, вернувшемуся с дальней дороги.";
    }
}
