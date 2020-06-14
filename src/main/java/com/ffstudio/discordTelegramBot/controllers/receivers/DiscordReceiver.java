package com.ffstudio.discordTelegramBot.controllers.receivers;

import com.ffstudio.discordTelegramBot.controllers.resolvers.DiscordResolver;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

@Component
public class DiscordReceiver extends ListenerAdapter {

    private final DiscordResolver discordResolver;

    public DiscordReceiver(@Lazy DiscordResolver discordResolver) {
        this.discordResolver = discordResolver;
    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        if (event.getMessage().getContentRaw().startsWith("☢")) {
            discordResolver.executeCommand(event);
        }
    }

}
