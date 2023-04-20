package telegram.commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegram.settings.Status;

public class DBNameCommand extends Command {
    public DBNameCommand() {
        super("setdbname", "set database name");
    }

    @Override
    public Status execute(AbsSender absSender, User user, Chat chat) {
        SendMessage message = SendMessage.builder()
                .chatId(chat.getId())
                .text(this.toString())
                .build();
        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            // todo: handle better
            System.err.println(e.getMessage());
        }
        return Status.AWAITING_DB_NAME;
    }
}
