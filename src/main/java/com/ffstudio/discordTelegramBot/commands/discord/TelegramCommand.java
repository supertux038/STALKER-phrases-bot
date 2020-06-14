package com.ffstudio.discordTelegramBot.commands.discord;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class TelegramCommand extends Command {

    @Override
    public void execute(MessageReceivedEvent event) {
        Message message = event.getMessage();
        MessageChannel channel = event.getChannel();

        EmbedBuilder info = new EmbedBuilder();
        info.setTitle("Хочешь телеграм-версию меня?");
        info.setDescription("Поищи в поиске @itis_semestrovka_bot, думаю найдешь.");
        channel.sendTyping().queue();
        channel.sendMessage(info.build()).queueAfter(1, TimeUnit.SECONDS);

    }

    @Override
    public Header header() {
        return Header.telegram;
    }

    @Override
    public String description() {
        return "Возвращают телеграм версию себя.";
    }
}
