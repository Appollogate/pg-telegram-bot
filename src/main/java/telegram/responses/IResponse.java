package telegram.responses;

import org.telegram.telegrambots.meta.api.objects.Message;
import telegram.Bot;
import telegram.settings.BotStatus;

public interface IResponse {
    BotStatus execute(Bot bot, Message message);
}
