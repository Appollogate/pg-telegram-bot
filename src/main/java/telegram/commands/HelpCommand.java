package telegram.commands;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.bots.AbsSender;
import telegram.settings.BotStatus;

public class HelpCommand extends Command {

    private final CommandRegistry registry;
    public HelpCommand(CommandRegistry registry) {
        super("help", "list all available commands");
        this.registry = registry;
    }

    @Override
    public BotStatus execute(AbsSender absSender, Chat chat) {
        StringBuilder helpMessageBuilder = new StringBuilder("<b>Help</b>\n\n");
        helpMessageBuilder.append("I can help you connect to a remote PostgreSQL database and retrieve data from it.\n");
        helpMessageBuilder.append("You can control me by sending these commands:\n\n");
        for (ICommand command : registry.getCommands()) {
            helpMessageBuilder.append(command.toString()).append("\n\n");
        }
        sendMessage(absSender, chat.getId(), helpMessageBuilder.toString());
        return BotStatus.DEFAULT;
    }
}
