package telegram.callbacks;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import telegram.Bot;

import java.util.HashMap;
import java.util.Map;

public class CallbackRegistry {
    private final Map<String, ICallback> registry = new HashMap<>();

    public void addCallback(String callbackData, ICallback callback) {
        if (!registry.containsKey(callbackData)) {
            registry.put(callbackData, callback);
        }
    }

    public void executeCallback(Bot bot, CallbackQuery query) {
        if (registry.containsKey(query.getData())) {
            registry.get(query.getData()).execute(bot, query);
        }
    }
}
