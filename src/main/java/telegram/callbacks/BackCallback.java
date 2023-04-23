package telegram.callbacks;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import telegram.Bot;

public class BackCallback implements IEditTextCallback {
    @Override
    public void execute(Bot bot, CallbackQuery callbackQuery) {
        var msgId = callbackQuery.getMessage().getMessageId();
        var chatId = callbackQuery.getMessage().getChat().getId();
        var batchDispatcher = bot.getBatchDispatcherByMessage(msgId, chatId);
        if (batchDispatcher == null) {
            return;
        }
        String editText = batchDispatcher.getPrevBatch();
        sendMessage(bot, chatId, msgId, editText, callbackQuery.getId());
    }
}
