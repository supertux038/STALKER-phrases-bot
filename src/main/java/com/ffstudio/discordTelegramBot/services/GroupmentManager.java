package com.ffstudio.discordTelegramBot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GroupmentManager {

    @Autowired
    private GroupmentsLoader groupmentsLoader;

    public String getGroupments() {
        List<String> groupmentsList = groupmentsLoader.getGroupments();
        return String.join(", ", groupmentsList);
    }


}
