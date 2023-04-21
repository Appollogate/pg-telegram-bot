package telegram.commands;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.bots.AbsSender;
import telegram.settings.BotStatus;

public class SQLCommand extends Command {
    public SQLCommand() {
        super("sql", "execute sql query");
    }

    @Override
    public BotStatus execute(AbsSender absSender, Chat chat) {
        sendMessage(absSender, chat.getId(), "Send me the SQL query to execute.");
        return BotStatus.AWAITING_SQL_QUERY;
    }
}
