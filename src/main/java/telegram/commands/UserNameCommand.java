package telegram.commands;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.bots.AbsSender;
import telegram.settings.BotStatus;

public class UserNameCommand extends Command {
    public UserNameCommand() {
        super("setusername", "set postgres username");
    }

    @Override
    public BotStatus execute(AbsSender absSender, Chat chat) {
        sendMessage(absSender, chat.getId(), "Send me the username which will be used for connection.");
        return BotStatus.AWAITING_USERNAME;
    }
}
