package telegram.callbacks;

import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface IEditTextCallback extends ICallback {
    default void sendMessage(AbsSender absSender, Long chatId, Integer msgId, String editText, String callbackQueryId) {
        EditMessageText newMessage = EditMessageText.builder()
                .chatId(chatId)
                .messageId(msgId)
                .text(editText)
                .build();
        AnswerCallbackQuery close = AnswerCallbackQuery.builder()
                .callbackQueryId(callbackQueryId)
                .build();
        try {
            absSender.execute(close);
            absSender.execute(newMessage);
        } catch (TelegramApiException e) {
            // todo: handle better
            e.printStackTrace(System.err);
        }
    }
}
