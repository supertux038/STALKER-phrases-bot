package com.ffstudio.discordTelegramBot.commands.discord;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class QuestionCommand extends Command {
    @Override
    public void execute(MessageReceivedEvent event) {
        MessageChannel channel = event.getChannel();
        EmbedBuilder question = new EmbedBuilder();
        question.setTitle("Ты меня звал, сталкер?");
        question.setDescription("Ты случайно использовал ☢ или пытался до меня достучаться? " +
                "По данной команде у меня пока нет никаких функций.");
        question.addField("☢ help", "Набери эту команду чтобы увидеть окно с подсказками", false);
        channel.sendTyping().queue();
        channel.sendMessage(question.build()).queueAfter(1, TimeUnit.SECONDS);
    }

    @Override
    public Header header() {
        return Header.question;
    }

    @Override
    public String description() {
        return "Переспрашиваю пользователя, обращался ли он ко мне. Если да, возвращаю команду для вывода окна помощи";
    }
}
