package com.ffstudio.discordTelegramBot.commands.discord;

import com.ffstudio.discordTelegramBot.services.UserService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class ChangeNameCommand extends Command {

    @Autowired
    private UserService userService;

    @Override
    public void execute(MessageReceivedEvent event) {
        Message message = event.getMessage();
        MessageChannel channel = event.getChannel();
        EmbedBuilder name = new EmbedBuilder();
        String userAnswer = userService.getUserAnswer(message.getAuthor().getIdLong());

        if (message.getContentRaw().split(" ").length > 2) {
            String newName = message.getContentRaw().split(" ")[2];
            name.setTitle("Ты захотел, чтобы тебя называли под-дургому?");
            if (userService.changeName(message.getAuthor().getIdLong(), newName)) {
                name.addField("Без проблем!", "Теперь я буду звать тебя "+newName, false);
            } else {
                name.addField("Извини, во время изменения имени что-то пошло не так. ",
                        "Возможно ты не зарегистрирован в моей памяти? Напиши /start", false);
            }
        } else {
            name.setTitle("Чтобы сменить имя введи команду и имя, каким ты хочешь чтобы тебя называли \"☢ changename ИМЯ\"");
        }

        channel.sendTyping().queue();
        channel.sendMessage(name.build()).queueAfter(1, TimeUnit.SECONDS);

    }

    @Override
    public Header header() {
        return Header.changename;
    }

    @Override
    public String description() {
        return "Меняю имя сталкера у себя в памяти";
    }
}
