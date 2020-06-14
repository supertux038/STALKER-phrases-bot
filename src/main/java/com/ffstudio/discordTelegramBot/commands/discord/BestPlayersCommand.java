package com.ffstudio.discordTelegramBot.commands.discord;

import com.ffstudio.discordTelegramBot.controllers.resolvers.DiscordResolver;
import com.ffstudio.discordTelegramBot.models.User;
import com.ffstudio.discordTelegramBot.services.UserService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class BestPlayersCommand extends Command {

    @Autowired
    private DiscordResolver discordResolver;

    @Autowired
    private UserService userService;

    @Override
    public void execute(MessageReceivedEvent event) {
        MessageChannel channel = event.getChannel();
        List<User> bestStalkers = userService.getBestStalkers();

        EmbedBuilder bestPlayers = new EmbedBuilder();
        bestPlayers.setTitle("Сталкеры с самым острым ухом");
        for (int i = 0; i < bestStalkers.size(); i++) {
            User currentUser = bestStalkers.get(i);
            int number = i+1;
            bestPlayers.addField("№"+ number + " " + currentUser.getNickname(),
                    "Сталкер с платформы " + currentUser.getPlatform() + " имеет рейтинг " + currentUser.getRating(), false);
        }
        bestPlayers.addField("Хочешь оказаться в их списке?", "Просто набери \"☢ play\" в чат", false);

        channel.sendTyping().queue();
        channel.sendMessage(bestPlayers.build()).queueAfter(1, TimeUnit.SECONDS);
    }

    @Override
    public Header header() {
        return Header.best;
    }

    @Override
    public String description() {
        return "Вывожу трех самых умных сталкеров";
    }
}
