package telegram.commands;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.bots.AbsSender;
import telegram.settings.BotStatus;

public class HostCommand extends Command {

    public HostCommand() {
        super("sethost", "задать хост сервера");
    }

    @Override
    public BotStatus execute(AbsSender absSender, Chat chat) {
        sendMessage(absSender, chat.getId(), "Отправьте мне хост (IP-адрес) сервера.");
        return BotStatus.AWAITING_HOST;
    }
}
