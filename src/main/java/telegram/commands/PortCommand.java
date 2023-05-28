package telegram.commands;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.bots.AbsSender;
import telegram.settings.BotStatus;

public class PortCommand extends Command {

    public PortCommand() {
        super("setport", "задать порт сервера");
    }

    @Override
    public BotStatus execute(AbsSender absSender, Chat chat) {
        sendMessage(absSender, chat.getId(), "Отправьте мне номер порта, который слушает PostgreSQL.");
        return BotStatus.AWAITING_PORT;
    }
}
