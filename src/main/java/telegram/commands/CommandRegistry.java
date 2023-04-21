package telegram.commands;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import telegram.settings.BotStatus;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CommandRegistry {
    private final Map<String, ICommand> registry = new HashMap<>();

    public void addCommand(ICommand command) {
        if (!registry.containsKey(command.getName())) {
            registry.put(command.getName(), command);
        }
    }

    public Collection<ICommand> getCommands() {
        return registry.values();
    }

    public BotStatus executeCommand(AbsSender absSender, Message message) {
        String name = message.getText().substring(1);
        if (registry.containsKey(name)) {
            return registry.get(name).execute(absSender, message.getChat());
        }
        return BotStatus.NO_SUCH_COMMAND;
    }

    public BotStatus executeCommand(AbsSender absSender, Message message, String commandName) {
        if (registry.containsKey(commandName)) {
            return registry.get(commandName).execute(absSender, message.getChat());
        }
        return BotStatus.NO_SUCH_COMMAND;
    }
}
