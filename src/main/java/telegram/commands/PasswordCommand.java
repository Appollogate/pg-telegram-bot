package telegram.commands;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.bots.AbsSender;
import telegram.settings.BotStatus;

public class PasswordCommand extends Command {
    public PasswordCommand() {
        super("setpassword", "задать пароль пользователя, под которым осуществляется подключение");
    }

    @Override
    public BotStatus execute(AbsSender absSender, Chat chat) {
        sendMessage(absSender, chat.getId(), "Отправьте мне пароль пользователя, под которым будет установлено подключение к серверу.");
        return BotStatus.AWAITING_PASSWORD;
    }
}
