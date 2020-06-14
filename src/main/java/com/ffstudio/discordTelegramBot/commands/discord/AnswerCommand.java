package com.ffstudio.discordTelegramBot.commands.discord;

import com.ffstudio.discordTelegramBot.services.UserService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class AnswerCommand extends Command {

    @Autowired
    private UserService userService;

    @Override
    public void execute(MessageReceivedEvent event) {
        Message message = event.getMessage();
        MessageChannel channel = event.getChannel();
        EmbedBuilder game = new EmbedBuilder();
        String userAnswer = userService.getUserAnswer(message.getAuthor().getIdLong());

        if (userAnswer != null) {
            if (message.getContentRaw().split(" ").length > 2) {
                String currentAnswer = message.getContentRaw().split(" ")[2];
                game.setTitle("Изучил твой ответ.");
                if (userAnswer.equals(currentAnswer)) {
                    game.addField("Поздравляю! Ты ответил верно, это " + currentAnswer,
                            "Хорошо сработано, ты заслужил +1 к рейтингу", false);
                    userService.addUserPoint(message.getAuthor().getIdLong());
                } else {
                    game.addField("Извини, не верно. Правильный ответ: " + userAnswer,
                            "В следующий раз представь кто бы мог сказать подобную фразу. Тебе обязательно повезет.", false);
                }
                game.setFooter("Удачи");
            } else {
                game.setTitle("Ты не отправил мне ответ. В следующий раз вводи свой ответ в формате \"☢ answer ОТВЕТ\"");
                game.addField("Хочешь попробовать еще раз?","Введи ☢ play и я задам тебе еще вопрос", false);
                game.setFooter("Удачи");
            }
        } else {
            game.setTitle("У тебя еще нет начатаой игры");
            game.addField("Хочешь поиграть?","Введи ☢ play и я задам тебе еще вопрос", false);
            game.setFooter("Удачи");
        }

        userService.setUserAnswer(message.getAuthor().getIdLong(), null);
        channel.sendTyping().queue();
        channel.sendMessage(game.build()).queueAfter(1, TimeUnit.SECONDS);
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
