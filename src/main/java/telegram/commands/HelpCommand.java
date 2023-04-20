package telegram.commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegram.settings.Status;

public class HelpCommand extends Command {

    private final CommandRegistry registry;
    public HelpCommand(CommandRegistry registry) {
        super("help", "list all available commands");
        this.registry = registry;
    }

    @Override
    public Status execute(AbsSender absSender, User user, Chat chat) {
        StringBuilder helpMessageBuilder = new StringBuilder("I can help you connect to a remote PostgreSQL database and retrieve data from it.\n");
        helpMessageBuilder.append("You can control me by sending these commands:\n\n");
        for (ICommand command : registry.getCommands()) {
            helpMessageBuilder.append(command.toString()).append("\n\n");
        }
        SendMessage message = SendMessage.builder()
                .chatId(chat.getId())
                .text(helpMessageBuilder.toString())
                .build();
        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            // todo: handle better
            System.err.println(e.getMessage());
        }
        return Status.DEFAULT;
    }
}
