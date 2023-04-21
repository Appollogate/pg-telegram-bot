package telegram.responses;

import org.telegram.telegrambots.meta.api.objects.Message;
import telegram.Bot;
import telegram.settings.BotStatus;

import java.util.HashMap;
import java.util.Map;

public class ResponseRegistry {
    private final Map<BotStatus, IResponse> registry = new HashMap<>();

    public void addResponse(BotStatus botStatus, IResponse response) {
        if (!registry.containsKey(botStatus)) {
            registry.put(botStatus, response);
        }
    }

    public BotStatus executeResponse(Bot bot, Message message, BotStatus botStatus) {
        if (registry.containsKey(botStatus)) {
            return registry.get(botStatus).execute(bot, message);
        }
        return BotStatus.DEFAULT;
    }
}
