package telegram.commands;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.bots.AbsSender;
import telegram.settings.BotStatus;

public class HostCommand extends Command {

    public HostCommand() {
        super("sethost", "set host name of the server");
    }

    @Override
    public BotStatus execute(AbsSender absSender, Chat chat) {
        sendMessage(absSender, chat.getId(), "Send me the host name of the server.");
        return BotStatus.AWAITING_HOST;
    }
}
