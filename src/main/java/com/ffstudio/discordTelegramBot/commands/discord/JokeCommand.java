package com.ffstudio.discordTelegramBot.commands.discord;

import com.ffstudio.discordTelegramBot.models.Joke;
import com.ffstudio.discordTelegramBot.services.JokeManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class JokeCommand extends Command {

    @Autowired
    private JokeManager jokeManager;

    private String[] introductions = {
            "Внимание, анекдот!",
            "Внимание, выдаю шутку юмора.",
    };
    private String[] reactions = {
            "Ахахахха, ну ты выдал.",
            "Блин, так не смешно же.",
            "Ну ты чисто комик."
    };

    @Override
    public void execute(MessageReceivedEvent event) {
        MessageChannel channel = event.getChannel();
        String introduction = jokeManager.getRandomIntroduction();
        String reaction = jokeManager.getRandomReaction();
        EmbedBuilder joke_msg = new EmbedBuilder();
        joke_msg.setTitle(introduction);
        joke_msg.setDescription(Joke.getRandomJoke());
        joke_msg.setFooter(reaction);
        channel.sendTyping().queue();
        channel.sendMessage(joke_msg.build()).queueAfter(1, TimeUnit.SECONDS);
    }

    @Override
    public Header header() {
        return Header.joke;
    }

    @Override
    public String description() {
        return "Говорю случайную шутку из своей памяти";
    }
}
