package telegram.commands;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.bots.AbsSender;
import telegram.settings.BotStatus;

public class SQLCommand extends Command {
    public SQLCommand() {
        super("sql", "исполнить sql-запрос");
    }

    @Override
    public BotStatus execute(AbsSender absSender, Chat chat) {
        sendMessage(absSender, chat.getId(), "Отправьте мне SQL-запрос, который необходимо выполнить.");
        return BotStatus.AWAITING_SQL_QUERY;
    }
}
