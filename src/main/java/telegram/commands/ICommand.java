package telegram.commands;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import telegram.settings.Status;

public interface ICommand {

    Status execute(AbsSender absSender, User user, Chat chat);

    String getName();

    String getDescription();
}
