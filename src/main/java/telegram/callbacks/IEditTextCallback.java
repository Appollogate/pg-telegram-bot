package telegram.callbacks;

import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

public interface IEditTextCallback extends ICallback {
    default void sendMessage(AbsSender absSender, Long chatId, Integer msgId, String editText, String callbackQueryId) {
        AnswerCallbackQuery close = AnswerCallbackQuery.builder()
                .callbackQueryId(callbackQueryId)
                .build();
        try {
            absSender.execute(close);
            if (editText != null) { // editText == null --> nothing to edit
                absSender.execute(EditMessageText.builder()
                        .chatId(chatId)
                        .messageId(msgId)
                        .text(editText)
                        .replyMarkup(getKeyboard())
                        .build());
            }
        } catch (TelegramApiException e) {
            // todo: handle better
            e.printStackTrace(System.err);
        }
    }

    // todo: move to another class?
    private InlineKeyboardMarkup getKeyboard() {
        var backButton = InlineKeyboardButton.builder()
                .text("Назад").callbackData("back") // show previous batch of query results
                .build();
        var nextButton = InlineKeyboardButton.builder()
                .text("Вперёд").callbackData("next") // show next batch of query results
                .build();
        return InlineKeyboardMarkup.builder().keyboardRow(List.of(backButton, nextButton)).build();
    }
}
