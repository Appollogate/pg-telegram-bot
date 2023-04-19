package telegram;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.CopyMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {

    public Bot(String botToken) {
        super(botToken);
    }

    @Override
    public String getBotUsername() {
        return "postgresql_hse_bot";
    }

    @Override
    public void onUpdateReceived(Update update) {
        var msg = update.getMessage();
        var msgId = msg.getMessageId();
        var userId = msg.getFrom().getId();

        copyMessage(userId, msgId);
    }

    private void copyMessage(Long userId, Integer msgId) {
        CopyMessage cm = CopyMessage.builder()
                .fromChatId(userId.toString())
                .chatId(userId.toString())
                .messageId(msgId)
                .build();
        try {
            execute(cm); // send the message
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
