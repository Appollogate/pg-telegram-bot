package telegram.commands;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.bots.AbsSender;
import telegram.settings.BotStatus;

public class PasswordCommand extends Command {
    public PasswordCommand() {
        super("setpassword", "set postgres password");
    }

    @Override
    public BotStatus execute(AbsSender absSender, Chat chat) {
        sendMessage(absSender, chat.getId(), "Send me the password which will be used for connection.");
        return BotStatus.AWAITING_PASSWORD;
    }
}
