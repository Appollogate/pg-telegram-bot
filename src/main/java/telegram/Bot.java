package telegram;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.CopyMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;

import telegram.commands.*;
import telegram.settings.Status;
import telegram.settings.UserConnectionSettings;

public class Bot extends TelegramLongPollingBot {

    private final Map<Long, UserConnectionSettings> userSettings = new HashMap<>();
    private final Map<Long, Status> userStatus = new HashMap<>();
    private final CommandRegistry registry = new CommandRegistry();

    public Bot(String botToken) {
        super(botToken);
        fillRegistry();
    }

    @Override
    public String getBotUsername() {
        return "postgresql_hse_bot";
    }

    @Override
    public void onUpdateReceived(Update update) {
        var message = update.getMessage();
        if (!userSettings.containsKey(message.getFrom().getId())) {
            userSettings.put(message.getFrom().getId(), new UserConnectionSettings());
        }
        if (message.isCommand()) {
            var status = registry.executeCommand(this, message);
        }

//        var msg = update.getMessage();
//        var msgId = msg.getMessageId();
//        var userId = msg.getFrom().getId();
//        copyMessage(userId, msgId);
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

    private void fillRegistry() {
        registry.addCommand(new HostCommand());
        registry.addCommand(new PortCommand());
        registry.addCommand(new DBNameCommand());
        registry.addCommand(new UserNameCommand());
        registry.addCommand(new PasswordCommand());
    }
}
