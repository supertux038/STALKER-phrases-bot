package com.ffstudio.discordTelegramBot.models;

public class Phrase {

    private String groupment;
    private String phrase;
    private String action;

    public Phrase(String groupment, String action, String phrase) {
        this.groupment = groupment;
        this.phrase = phrase;
        this.action = action;
    }

    public String getGroupment() {
        return groupment;
    }

    public String getPhrase() {
        return phrase;
    }

    public String getAction() {
        return action;
    }

    public String getFullPhrase(){
        return this.groupment + " " + this.action + ":\n" + this.phrase;
    }
}
