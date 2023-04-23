package telegram.callbacks;

import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
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
//        EditMessageText newMessage = EditMessageText.builder()
//                .chatId(chatId)
//                .messageId(msgId)
//                .text(editText)
//                .build();
//        AnswerCallbackQuery close = AnswerCallbackQuery.builder()
//                .callbackQueryId(callbackQuery.getId())
//                .build();
//        try {
//            bot.execute(close);
//            bot.execute(newMessage);
//        } catch (TelegramApiException e) {
//            // todo: handle better
//            e.printStackTrace(System.err);
//        }
    }
}
