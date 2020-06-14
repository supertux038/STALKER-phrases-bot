package com.ffstudio.discordTelegramBot.controllers.resolvers;

import com.ffstudio.discordTelegramBot.commands.discord.Command;
import com.ffstudio.discordTelegramBot.commands.discord.QuestionCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DiscordResolver {

    private final ApplicationContext context;
    private final Map<String, Command> commandMap = new HashMap<>();
    private final JDA jda;

    public DiscordResolver(ApplicationContext context, JDA jda) {
        this.context = context;
        this.jda = jda;
        initializeCommands();
    }

    public Map<String, Command> getCommandMap() {
        return commandMap;
    }

    private void initializeCommands() {
        Collection<Command> commands = context.getBeansOfType(Command.class).values();
        for (Command command : commands) {
            addCommand(command);
        }
    }

    private void addCommand(Command command) {
        commandMap.put(command.header().name(), command);
    }

    public void executeCommand(MessageReceivedEvent event) {

        Message message = event.getMessage();

        String commandText = message.getContentRaw().toLowerCase().split("☢")[1].replaceAll("️ ", "");
        Command command = commandMap.get(commandText.split(" ")[0]);
        if (command != null) {
            command.execute(event);
        } else {
            context.getBean(QuestionCommand.class).execute(event);
        }
    }
}
