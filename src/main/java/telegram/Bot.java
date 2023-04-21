package telegram;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

import telegram.commands.*;
import telegram.responses.*;
import telegram.settings.BotStatus;
import telegram.settings.UserSettings;

public class Bot extends TelegramLongPollingBot {

    private final Map<Long, UserSettings> userSettings = new HashMap<>();
    private final CommandRegistry commandRegistry = new CommandRegistry();
    private final ResponseRegistry responseRegistry = new ResponseRegistry();

    public Bot(String botToken) {
        super(botToken);
        fillCommandRegistry();
        fillResponseRegistry();
    }

    @Override
    public String getBotUsername() {
        return "postgresql_hse_bot";
    }

    @Override
    public void onUpdateReceived(Update update) {
        var message = update.getMessage();
        var userId = message.getFrom().getId();
        initUserSettingsIfNeeded(userId);
        if (message.isCommand()) {
            var status = commandRegistry.executeCommand(this, message);
            if (status == BotStatus.NO_SUCH_COMMAND) {
                executeDefaultAction(message);
                status = BotStatus.DEFAULT;
            }
            userSettings.get(userId).setBotStatus(status);
            return;
        }
        // handle user text message if waiting for some sort of information
        if (userSettings.get(userId).getBotStatus() != BotStatus.DEFAULT) {
            var status = responseRegistry.executeResponse(this, message, userSettings.get(userId).getBotStatus());
            userSettings.get(userId).setBotStatus(status);
            return;
        }
        // If incoming message is not a command and is not an expected text answer, execute default action
        executeDefaultAction(message);
    }

    public UserSettings getUserSettings(Long userId) {
        if (!userSettings.containsKey(userId)) {
            userSettings.put(userId, new UserSettings());
        }
        return userSettings.get(userId);
    }

    private void fillCommandRegistry() {
        commandRegistry.addCommand(new HostCommand());
        commandRegistry.addCommand(new PortCommand());
        commandRegistry.addCommand(new DBNameCommand());
        commandRegistry.addCommand(new UserNameCommand());
        commandRegistry.addCommand(new PasswordCommand());
        commandRegistry.addCommand(new HelpCommand(commandRegistry));
        commandRegistry.addCommand(new SQLCommand());
    }

    private void fillResponseRegistry() {
        responseRegistry.addResponse(BotStatus.AWAITING_HOST, new SetHostResponse());
        responseRegistry.addResponse(BotStatus.AWAITING_PORT, new SetPortResponse());
        responseRegistry.addResponse(BotStatus.AWAITING_DB_NAME, new SetDBNameResponse());
        responseRegistry.addResponse(BotStatus.AWAITING_USERNAME, new SetUsernameResponse());
        responseRegistry.addResponse(BotStatus.AWAITING_PASSWORD, new SetPasswordResponse());
        responseRegistry.addResponse(BotStatus.AWAITING_SQL_QUERY, new SQLResponse());
    }

    private void initUserSettingsIfNeeded(Long userId) {
        if (!userSettings.containsKey(userId)) {
            userSettings.put(userId, new UserSettings());
        }
    }

    private void executeDefaultAction(Message message) {
        commandRegistry.executeCommand(this, message, "help");
    }

    private void tempOut(UserSettings userSettings) {
        System.out.println("bot status:" + userSettings.getBotStatus());
        System.out.println("host: " + userSettings.getHost());
        System.out.println("port:" + userSettings.getPort());
        System.out.println("db name: " + userSettings.getDbName());
        System.out.println("username: " + userSettings.getUsername());
        System.out.println("password: " + userSettings.getPassword());
    }
}
