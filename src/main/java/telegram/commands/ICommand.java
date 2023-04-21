package telegram.commands;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import telegram.settings.BotStatus;

public interface ICommand {

    BotStatus execute(AbsSender absSender, Chat chat);

    String getName();

    String getDescription();
}
