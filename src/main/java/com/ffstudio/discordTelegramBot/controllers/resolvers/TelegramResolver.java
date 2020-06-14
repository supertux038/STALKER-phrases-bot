package com.ffstudio.discordTelegramBot.controllers.resolvers;

import com.ffstudio.discordTelegramBot.commands.telegram.Command;
import com.ffstudio.discordTelegramBot.commands.telegram.TelQuestionCommand;
import com.ffstudio.discordTelegramBot.controllers.receivers.TelegramReceiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class TelegramResolver {

    private final ApplicationContext context;
    private final Map<String, Command> commandMap = new HashMap<>();

    @Autowired
    private TelegramReceiver telegramReceiver;

    public TelegramResolver(ApplicationContext context) {
        this.context = context;
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

    public void executeCommand(Update update) {
        Message message = update.getMessage();

        String commandText = message.getText().toLowerCase().split(" ")[0];
        if (commandText.charAt(0) == '/') {
            String commandName = commandText.substring(1);

            Command command = commandMap.get(commandName);
            if (command != null) {
                command.execute(update);
            } else {
                context.getBean(TelQuestionCommand.class).execute(update);
            }
        }
    }

}