package telegram.commands;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.bots.AbsSender;
import telegram.settings.BotStatus;

public class DBNameCommand extends Command {
    public DBNameCommand() {
        super("setdbname", "задать имя базы данных");
    }

    @Override
    public BotStatus execute(AbsSender absSender, Chat chat) {
        sendMessage(absSender, chat.getId(), "Отправьте мне название базы данных, к которой хотите произвести подключение.");
        return BotStatus.AWAITING_DB_NAME;
    }
}
