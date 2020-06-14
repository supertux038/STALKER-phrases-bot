package com.ffstudio.discordTelegramBot.commands.telegram;

import com.ffstudio.discordTelegramBot.controllers.receivers.TelegramReceiver;
import com.ffstudio.discordTelegramBot.controllers.resolvers.DiscordResolver;
import com.ffstudio.discordTelegramBot.models.Phrase;
import com.ffstudio.discordTelegramBot.services.GroupmentManager;
import com.ffstudio.discordTelegramBot.services.PhrasesManager;
import com.ffstudio.discordTelegramBot.services.UserService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendSticker;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class TelAnswerCommand extends Command {

    private String stickerId;

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
        String userAnswer = userService.getUserAnswer(new Long(message.getFrom().getId()));

        if (userAnswer != null) {
            if (message.getText().split(" ").length == 2) {
                String currentAnswer = message.getText().split(" ")[1];
                builder.append("Изучил твой ответ.\n");
                if (userAnswer.equals(currentAnswer)) {
                    builder.append("Поздравляю! Ты ответил верно, это ");
                    builder.append(currentAnswer);
                    builder.append("\nХорошо сработано, ты заслужил +1 к рейтингу");
                    userService.addUserPoint(new Long(message.getFrom().getId()));
                    stickerId = "CAACAgIAAxkBAAOmXtEHoX8HDDty-SZ5zY7wq3SsiisAAksBAAKnWqEOiH0Ff5frJgABGQQ";
                } else {
                    builder.append("Извини, не верно. Правильный ответ: ");
                    builder.append(userAnswer);
                    builder.append("\nВ следующий раз представь кто бы мог сказать подобную фразу. Тебе обязательно повезет.");
                    stickerId = "CAACAgIAAxkBAAOnXtEHvpvEsh4678puP7heV9BbyC4AAicBAAKnWqEOe3j0qJ_MVfkZBA";
                }
                builder.append("\nУдачи");
            } else {
                builder.append("Ты не отправил мне ответ. В следующий раз вводи свой ответ в формате \"/answer ОТВЕТ\"");
                builder.append(" Хочешь попробовать еще раз?\n");
                builder.append("Введи /play и я задам тебе еще вопрос\n");
                builder.append("Удачи");
                stickerId = "CAACAgIAAxkBAAOoXtEH5plWEQlw4mUknuV4ZOTYQqQAAkIBAAKnWqEOFc590MMavrQZBA";
            }
        } else {
            builder.append("У тебя еще нет начатаой игры\n");
            builder.append("Хочешь поиграть?\n");
            builder.append("Введи /play и я задам тебе еще вопрос\n");
            builder.append("Удачи");
            stickerId = "CAACAgIAAxkBAAOpXtEH-0dxyeu3wI1J_E0tWTDgq_UAAkEBAAKnWqEOCiZtxMyNqlUZBA";
        }

        userService.setUserAnswer(new Long(message.getFrom().getId()), null);
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
        return Header.answer;
    }

    @Override
    public String description() {
        return "Слушаю ответ сталкера на вопрсо моей игры";
    }

}
