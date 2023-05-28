package telegram.responses;

import org.telegram.telegrambots.meta.api.objects.Message;
import telegram.Bot;
import telegram.settings.BotStatus;

public class SetUsernameResponse extends Response {
    @Override
    public BotStatus execute(Bot bot, Message message) {
        var username = message.getText();
        var userId = message.getFrom().getId();
        bot.getUserSettings(userId).setUsername(username);
        sendMessage(bot, message.getChat().getId(),
                "Имя пользователя успешно сохранено как " + bot.getUserSettings(userId).getUsername() + ".");
        return BotStatus.DEFAULT;
    }
}
