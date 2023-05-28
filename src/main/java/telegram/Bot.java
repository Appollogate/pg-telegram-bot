package telegram;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

import telegram.callbacks.BackCallback;
import telegram.callbacks.CallbackRegistry;
import telegram.callbacks.NextCallback;
import telegram.commands.*;
import telegram.responses.*;
import telegram.settings.BotStatus;
import telegram.settings.UserSettings;
import utility.BatchDispatcher;
import utility.Pair;

public class Bot extends TelegramLongPollingBot {

    private final Map<Long, UserSettings> userSettings = new HashMap<>();
    private final CommandRegistry commandRegistry = new CommandRegistry();
    private final ResponseRegistry responseRegistry = new ResponseRegistry();
    private final CallbackRegistry callbackRegistry = new CallbackRegistry();
    // todo: store this in a database
    // maps message id + chat id to a batch dispatcher that holds retrieved query
    private final Map<Pair<Integer, Long>, BatchDispatcher> batchDispatcherMap = new HashMap<>();

    public Bot(String botToken) {
        super(botToken);
        fillCommandRegistry();
        fillResponseRegistry();
        fillCallbackRegistry();
    }

    @Override
    public String getBotUsername() {
        return "postgresql_hse_bot";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasCallbackQuery()) { // user pressed a button on an inline keyboard
            callbackRegistry.executeCallback(this, update.getCallbackQuery());
            return;
        }
        if (update.hasMessage() && update.getMessage().hasText()) {
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
    }

    public UserSettings getUserSettings(Long userId) {
        if (!userSettings.containsKey(userId)) {
            userSettings.put(userId, new UserSettings());
        }
        return userSettings.get(userId);
    }

    public void setBatchDispatcherForMessage(Integer messageId, Long chatId, BatchDispatcher batchDispatcher) {
        Pair<Integer, Long> messageChatPair = new Pair<>(messageId, chatId);
        if (!batchDispatcherMap.containsKey(messageChatPair)) {
            batchDispatcherMap.put(messageChatPair, batchDispatcher);
        }
    }

    public BatchDispatcher getBatchDispatcherByMessage(Integer messageId, Long chatId) {
        var pair = new Pair<Integer, Long>(messageId, chatId);
        if (batchDispatcherMap.containsKey(pair)) {
            return batchDispatcherMap.get(pair);
        }
        // if we got here, something went wrong
        System.err.println
                ("Bot.getBatchDispatcherByMessage: couldn't get batch dispatcher." +
                "\nmessageId = " + messageId +
                "\nchatId = " + chatId);
        return null;
    }

    private void fillCommandRegistry() {
        commandRegistry.addCommand(new HostCommand());
        commandRegistry.addCommand(new PortCommand());
        commandRegistry.addCommand(new DBNameCommand());
        commandRegistry.addCommand(new UserNameCommand());
        commandRegistry.addCommand(new PasswordCommand());
        commandRegistry.addCommand(new HelpCommand(commandRegistry));
        commandRegistry.addCommand(new SQLCommand());
        commandRegistry.addCommand(new StartCommand());
    }

    private void fillResponseRegistry() {
        responseRegistry.addResponse(BotStatus.AWAITING_HOST, new SetHostResponse());
        responseRegistry.addResponse(BotStatus.AWAITING_PORT, new SetPortResponse());
        responseRegistry.addResponse(BotStatus.AWAITING_DB_NAME, new SetDBNameResponse());
        responseRegistry.addResponse(BotStatus.AWAITING_USERNAME, new SetUsernameResponse());
        responseRegistry.addResponse(BotStatus.AWAITING_PASSWORD, new SetPasswordResponse());
        responseRegistry.addResponse(BotStatus.AWAITING_SQL_QUERY, new SQLResponse());
    }

    private void fillCallbackRegistry() {
        callbackRegistry.addCallback("back", new BackCallback());
        callbackRegistry.addCallback("next", new NextCallback());
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
