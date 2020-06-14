package com.ffstudio.discordTelegramBot.services;

import org.springframework.stereotype.Component;

@Component
public class JokeManager {

    private String[] introductions = {
            "-Внимание, анекдот!",
            "-Внимание, выдаю шутку юмора.",
    };
    private String[] reactions = {
            "-Ахахахха, ну ты выдал.",
            "-Блин, так не смешно же.",
            "-Ну ты чисто комик."
    };

    public String getRandomIntroduction() {
        return introductions[(int) (Math.random() * (introductions.length))];
    }

    public String getRandomReaction() {
        return reactions[(int) (Math.random() * (reactions.length))];
    }

}
