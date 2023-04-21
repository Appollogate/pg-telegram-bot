package telegram.responses;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public abstract class Response implements IResponse {
    void sendMessage(AbsSender absSender, Long chatId, String message) {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text(message)
                .build();
        try {
            absSender.execute(sendMessage);
        } catch (TelegramApiException e) {
            // todo: handle better
            System.err.println(e.getMessage());
        }
    }
}