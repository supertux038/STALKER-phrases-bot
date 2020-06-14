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
public class StartCommand extends Command {

    @Autowired
    private UserService userService;

    @Override
    public void execute(MessageReceivedEvent event) {
        Message message = event.getMessage();
        MessageChannel channel = event.getChannel();

        EmbedBuilder info = new EmbedBuilder();
        info.setTitle("Приветствую тебя, сталкер.");
        info.setDescription("Добро пожаловать в ряды сталкеров, " + message.getAuthor().getName() +
                ". Я создаюсь для таких же сталкеров, как ты. \nУстал бродить по просторам зоны в поиске артефактов? " +
                "Остановись у костра и давай сыграем в игру \"угадай группировку\". Я буду называть фразы, присущие " +
                "сталкерам определенной группировки, а тебе нужно будет угадать их название.");
        info.addField("Помощь", "Набери ☢ help чтобы увидеть окно с подсказками", false);
        if (userService.createUser((message.getAuthor().getIdLong()), message.getAuthor().getName(), "Discord")) {
            info.addField("Поздравляю! Ты был зарегестрирован в игре под ником " +  message.getAuthor().getName(),
                    "Теперь ты можешь принимать участиве в играх", false);
        }
        info.setImage("https://steamuserimages-a.akamaihd.net/ugc/167032108248507594/B6791985AD9131B19B800786E20C275CA15E3419/");
        channel.sendTyping().queue();
        channel.sendMessage(info.build()).queueAfter(1, TimeUnit.SECONDS);

    }

    @Override
    public Header header() {
        return Header.start;
    }

    @Override
    public String description() {
        return "Приветствую сталкера";
    }
}
