package telegram.commands;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.bots.AbsSender;
import telegram.settings.BotStatus;

public class DBNameCommand extends Command {
    public DBNameCommand() {
        super("setdbname", "set database name");
    }

    @Override
    public BotStatus execute(AbsSender absSender, Chat chat) {
        sendMessage(absSender, chat.getId(), "Send me the name of the database to connect to.");
        return BotStatus.AWAITING_DB_NAME;
    }
}
