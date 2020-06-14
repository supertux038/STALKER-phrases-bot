package com.ffstudio.discordTelegramBot.services;

import com.ffstudio.discordTelegramBot.models.Phrase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PhrasesManager {

    @Autowired
    private PhrasesLoader phrasesLoader;

    public Phrase getRandomPhrase() {
        List<List<String>> phrases = phrasesLoader.getPhrases();
        List<String> phrase = phrases.get((int) (Math.random() * (phrases.size())));
        return new Phrase(phrase.get(0), phrase.get(1), phrase.get(2));
    }
}
