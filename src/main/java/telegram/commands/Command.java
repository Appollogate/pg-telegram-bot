package telegram.commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegram.Bot;

public abstract class Command implements ICommand {
    private final String name;
    private final String description;

    Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "<b>/" + name + "</b> - " + description;
    }

    void sendMessage(AbsSender absSender, Long chatId, String message) {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .parseMode("HTML")
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
