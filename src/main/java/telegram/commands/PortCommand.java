package telegram.commands;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.bots.AbsSender;
import telegram.settings.BotStatus;

public class PortCommand extends Command {

    public PortCommand() {
        super("setport", "set port number the server is listening on");
    }

    @Override
    public BotStatus execute(AbsSender absSender, Chat chat) {
        sendMessage(absSender, chat.getId(), "Send me the port number the Postgres server is listening on.");
        return BotStatus.AWAITING_PORT;
    }
}
