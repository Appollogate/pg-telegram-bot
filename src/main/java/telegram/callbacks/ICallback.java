package telegram.callbacks;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import telegram.Bot;

public interface ICallback {
    void execute(Bot bot, CallbackQuery callbackQuery);
}
