package com.ffstudio.discordTelegramBot.commands.discord;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Component;

@Component
public abstract class Command {

    public abstract void execute(MessageReceivedEvent event);
    public abstract Header header();
    public abstract String description();

    public enum Header {
        start,
        help,
        play,
        joke,
        question,
        phrase,
        answer,
        rules,
        telegram,
        best,
        changename
    }
}
