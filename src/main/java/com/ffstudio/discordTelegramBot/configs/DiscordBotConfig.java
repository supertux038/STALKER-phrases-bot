package com.ffstudio.discordTelegramBot.configs;

import com.ffstudio.discordTelegramBot.controllers.receivers.DiscordReceiver;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.security.auth.login.LoginException;

@Configuration
@ComponentScan(basePackages = "com.ffstudio.discordTelegramBot")
public class DiscordBotConfig {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private Environment env;

    @Bean
    public JDA jda() throws LoginException {
        JDA jda = new JDABuilder(env.getProperty("discord-token")).setActivity(Activity.listening("сталкеров")).build();
        jda.addEventListener(applicationContext.getBean(DiscordReceiver.class));
        return jda;
    }

}
