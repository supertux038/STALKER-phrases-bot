package com.ffstudio.discordTelegramBot.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "user")
public class User {
    @Id
    private Long id;

    private String groupment;

    private String nickname;

    private String currentAnswer;

    private String platform;

    private int rating;
}
