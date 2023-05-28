package telegram.commands;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.bots.AbsSender;
import telegram.settings.BotStatus;

public class UserNameCommand extends Command {
    public UserNameCommand() {
        super("setusername", "задать имя пользователя");
    }

    @Override
    public BotStatus execute(AbsSender absSender, Chat chat) {
        sendMessage(absSender, chat.getId(), "Отправьте мне имя пользователя, используемое для подключения.");
        return BotStatus.AWAITING_USERNAME;
    }
}
