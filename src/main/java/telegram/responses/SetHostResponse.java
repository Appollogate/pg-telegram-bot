package telegram.responses;

import org.telegram.telegrambots.meta.api.objects.Message;
import telegram.Bot;
import telegram.settings.BotStatus;

public class SetHostResponse extends Response {

    @Override
    public BotStatus execute(Bot bot, Message message) {
        var host = message.getText();
        var userId = message.getFrom().getId();
        bot.getUserSettings(userId).setHost(host);
        sendMessage(bot, message.getChat().getId(),
                "Success! Set host as " + bot.getUserSettings(userId).getHost() + ".");
        return BotStatus.DEFAULT;
    }
}
