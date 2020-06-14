package com.ffstudio.discordTelegramBot.services;

import com.ffstudio.discordTelegramBot.models.User;

import java.util.List;

public interface UserService {
    List<User> getBestStalkers();
    int getUserRating(Long userId);
    void addUserPoint(Long userId);
    String getUserAnswer(Long userId);
    void setUserAnswer(Long userId, String answer);
    boolean createUser(Long userId, String nickname, String platform);
    boolean userHasAnswer(Long userId);
    boolean changeName(Long userId, String newName);
    int getCurrentRating(Long userId);

}
