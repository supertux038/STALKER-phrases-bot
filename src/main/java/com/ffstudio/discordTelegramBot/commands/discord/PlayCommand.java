package com.ffstudio.discordTelegramBot.commands.discord;

import com.ffstudio.discordTelegramBot.models.Phrase;
import com.ffstudio.discordTelegramBot.services.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class PlayCommand extends Command {

    @Autowired
    private UserService userService;

    @Autowired
    private PhrasesManager phrasesManager;

    @Autowired
    private GroupmentManager groupmentManager;

    @Override
    public void execute(MessageReceivedEvent event) {
        Message message = event.getMessage();
        MessageChannel channel = event.getChannel();
        EmbedBuilder game = new EmbedBuilder();
        Phrase phraseObj = phrasesManager.getRandomPhrase();
        game.setTitle("Давай поиграем");
        game.setDescription("Сейчас я назову фразу и тебе с помощью команды \"☢ answer ОТВЕТ\" надо будет отправить группировку или персонажа," +
                " которые разговаривают подобной образом.\nДумай, сталкер, хорошо. У тебя одна попытка дать верный ответ.\n");
        game.addField("Фраза:", phraseObj.getPhrase(), false);
        game.addField("Варианты ответа", groupmentManager.getGroupments(), false);
        game.setFooter("Мозгуй");
        System.out.println(phraseObj.getGroupment());
        userService.setUserAnswer(message.getAuthor().getIdLong(), phraseObj.getGroupment());
        channel.sendTyping().queue();
        channel.sendMessage(game.build()).queueAfter(1, TimeUnit.SECONDS);
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
