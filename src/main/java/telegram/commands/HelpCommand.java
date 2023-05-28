package telegram.commands;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.bots.AbsSender;
import telegram.settings.BotStatus;

public class HelpCommand extends Command {

    private final CommandRegistry registry;
    public HelpCommand(CommandRegistry registry) {
        super("help", "перечислить все доступные команды");
        this.registry = registry;
    }

    @Override
    public BotStatus execute(AbsSender absSender, Chat chat) {
        StringBuilder helpMessageBuilder = new StringBuilder("<b>Справка</b>\n\n");
        helpMessageBuilder.append("Я - бот, способный подключаться к удаленной СУБД PostgreSQL и получать от неё данные через SQL-запросы.\n");
        helpMessageBuilder.append("Вы можете управлять мной с помощью следующих команд:\n\n");
        for (ICommand command : registry.getCommands()) {
            helpMessageBuilder.append(command.toString()).append("\n\n");
        }
        sendMessage(absSender, chat.getId(), helpMessageBuilder.toString());
        return BotStatus.DEFAULT;
    }
}
