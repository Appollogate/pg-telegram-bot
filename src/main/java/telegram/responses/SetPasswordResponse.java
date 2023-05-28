package telegram.responses;

import org.telegram.telegrambots.meta.api.objects.Message;
import telegram.Bot;
import telegram.settings.BotStatus;

public class SetPasswordResponse extends Response {
    public BotStatus execute(Bot bot, Message message) {
        var password = message.getText();
        var userId = message.getFrom().getId();
        bot.getUserSettings(userId).setPassword(password);
        sendMessage(bot, message.getChat().getId(), "Пароль успешно сохранён.");
        return BotStatus.DEFAULT;
    }
}
