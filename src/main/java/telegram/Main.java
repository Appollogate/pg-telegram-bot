package telegram;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.Scanner;

public class Main {
    // todo: handle exception
    public static void main(String[] args) throws TelegramApiException {
        // todo: keep token in safe location instead of reading from System.in
        Scanner scanner = new Scanner(System.in);
        String token = scanner.nextLine();
        // register bot on the Telegram API
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(new Bot(token));
    }
}