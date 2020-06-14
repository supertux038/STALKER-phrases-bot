package com.ffstudio.discordTelegramBot.commands.discord;

import com.ffstudio.discordTelegramBot.models.Phrase;
import com.ffstudio.discordTelegramBot.services.PhrasesManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class PhraseCommand extends Command {

    @Autowired
    private PhrasesManager phrasesManager;

    @Override
    public void execute(MessageReceivedEvent event) {
        MessageChannel channel = event.getChannel();
        Phrase phraseObj = phrasesManager.getRandomPhrase();
        EmbedBuilder phrase = new EmbedBuilder();
        phrase.setTitle("Хочешь пару фраз от сталкеров всех мастей и группировок?");
        phrase.setDescription(phraseObj.getFullPhrase());
        phrase.setFooter("Удачной охоты, сталкер");
        channel.sendTyping().queue();
        channel.sendMessage(phrase.build()).queueAfter(1, TimeUnit.SECONDS);
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
