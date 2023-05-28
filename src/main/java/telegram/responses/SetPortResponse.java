package telegram.responses;

import org.telegram.telegrambots.meta.api.objects.Message;
import telegram.Bot;
import telegram.settings.BotStatus;

public class SetPortResponse extends Response {
    @Override
    public BotStatus execute(Bot bot, Message message) {
        int port;
        try { // validation
            port = Integer.parseInt(message.getText());
        }
        catch (NumberFormatException e) {
            sendMessage(bot, message.getChat().getId(), "В качестве порта ожидалось целое положительное число. Пожалуйста, попробуйте ещё раз.");
            return BotStatus.AWAITING_PORT;
        }
        var userId = message.getFrom().getId();
        bot.getUserSettings(userId).setPort(port);
        sendMessage(bot, message.getChat().getId(),
                "Порт успешно сохранён как " + bot.getUserSettings(userId).getPort() + ".");
        return BotStatus.DEFAULT;
    }
}
