package telegram;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.sql.*;

import java.util.Scanner;

public class Main {
    // todo: handle exception
    public static void main(String[] args) throws TelegramApiException, ClassNotFoundException, SQLException {
        registerBot();
        //testConnection();
    }

    private static void registerBot() throws TelegramApiException {
        // todo: keep token in safe location instead of reading from System.in
        System.out.println("Please enter bot token");
        Scanner scanner = new Scanner(System.in);
        String token = scanner.nextLine();
        // register bot on the Telegram API
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(new Bot(token));
    }

    private static void testConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://127.0.0.1:5432/test_database";
        String username = "postgres";
        String password = "pgsqlds";
        Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM clients";
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            String role = rs.getString("role");
            System.out.println(
                    "first name: " + firstName +
                    "\tlast name: " + lastName +
                    "\trole: " + role);
        }
        connection.close();
    }
}