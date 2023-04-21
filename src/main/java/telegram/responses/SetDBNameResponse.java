package telegram.responses;

import org.telegram.telegrambots.meta.api.objects.Message;
import telegram.Bot;
import telegram.settings.BotStatus;

public class SetDBNameResponse extends Response {
    @Override
    public BotStatus execute(Bot bot, Message message) {
        var dbName = message.getText();
        var userId = message.getFrom().getId();
        bot.getUserSettings(userId).setDbName(dbName);
        sendMessage(bot, message.getChat().getId(),
                "Success! Set database name as " + bot.getUserSettings(userId).getDbName() + ".");
        return BotStatus.DEFAULT;
    }
}
