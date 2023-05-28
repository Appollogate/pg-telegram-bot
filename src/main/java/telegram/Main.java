package telegram;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please provide bot token as a single CLI argument.");
            return;
        }
        registerBot(args[0]);
    }

    /**
     * Used to register a bot on the Telegram API.
     *
     * @param token bot authentication token
     */
    private static void registerBot(String token) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new Bot(token));
        } catch (TelegramApiException e) {
            throw new RuntimeException("Couldn't initialize Telegram Bot", e);
        }
    }
}