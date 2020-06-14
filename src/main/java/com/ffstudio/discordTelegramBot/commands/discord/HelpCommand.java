package com.ffstudio.discordTelegramBot.commands.discord;

import com.ffstudio.discordTelegramBot.controllers.resolvers.DiscordResolver;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class HelpCommand extends Command {

    @Autowired
    private DiscordResolver discordResolver;

    @Override
    public void execute(MessageReceivedEvent event) {
        MessageChannel channel = event.getChannel();
        Map<String, Command> commandMap = discordResolver.getCommandMap();

        EmbedBuilder help = new EmbedBuilder();
        help.setTitle("Помощь по моим командам");
        help.setDescription("Нуждаешься в помощи, сталкер? Спроси - подскажу");
        for (Map.Entry<String, Command> entry: commandMap.entrySet()) {
            help.addField("☢ " + entry.getKey(), entry.getValue().description(), false);
        }
        channel.sendTyping().queue();
        channel.sendMessage(help.build()).queueAfter(1, TimeUnit.SECONDS);
    }

    @Override
    public Header header() {
        return Header.help;
    }

    @Override
    public String description() {
        return "Вывожу окно списка команд";
    }
}
