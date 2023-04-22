package telegram.responses;

import org.telegram.telegrambots.meta.api.objects.Message;
import postgres.PGController;
import telegram.Bot;
import telegram.settings.BotStatus;
import telegram.settings.UserSettings;
import utility.BatchDispatcher;
import utility.PGTelegramBotException;

// todo: rename to QueryResponse
public class SQLResponse extends Response {

    static final Integer BATCH_SIZE = 5;

    @Override
    public BotStatus execute(Bot bot, Message message) {
        var userId = message.getFrom().getId();
        var chatId = message.getChat().getId();
        UserSettings userSettings = bot.getUserSettings(userId);
        if (isAnyConnectionParamMissing(userSettings)) {
            sendMessage(bot, chatId, """
                    Some connection parameters are missing! Please ensure that you've set all of the following parameters:

                    - host name
                    - port name
                    - database name
                    - username
                    - password""");
            return BotStatus.DEFAULT;
        }
        runQuery(message.getText(), userSettings, bot, chatId);
        return BotStatus.DEFAULT;
    }

    private Boolean isAnyConnectionParamMissing(UserSettings userSettings) {
        return userSettings.getHost() == null ||
                userSettings.getPort() == null ||
                userSettings.getDbName() == null ||
                userSettings.getUsername() == null ||
                userSettings.getPassword() == null;
    }

    private void runQuery(String sqlQuery, UserSettings userSettings, Bot bot, Long chatId) {
        PGController pgController = new PGController(
                userSettings.getHost(),
                userSettings.getPort(),
                userSettings.getDbName(),
                userSettings.getUsername(),
                userSettings.getPassword());
        try {
            var result = pgController.executeSQLQuery(sqlQuery);
            var batchDispatcher = new BatchDispatcher(result, BATCH_SIZE, true, true);
            // todo: override sendMessage to include left/right buttons to switch batches
            Message sentMessage = sendMessage(bot, chatId, batchDispatcher.getCurrentBatch());
            // save BatchDispatcher for this specific message
            bot.setBatchDispatcherForMessage(sentMessage.getMessageId(), sentMessage.getChat().getId(), batchDispatcher);
        } catch (PGTelegramBotException e) {
            sendMessage(bot, chatId, e.getMessage());
            System.err.println(e.getCause().getMessage());
        }
    }
}
