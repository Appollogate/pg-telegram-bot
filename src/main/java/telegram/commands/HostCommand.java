package telegram.commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegram.settings.Status;

public class HostCommand extends Command {

    public HostCommand() {
        super("sethost", "set host name of the server");
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
        return Status.AWAITING_HOST;
    }
}