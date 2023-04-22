package postgres.jdbc;

import postgres.data.ConnectionSettings;
import postgres.data.Table;
import utility.PGTelegramBotException;

import java.sql.*;

public class JDBCSession {
    private final String url;
    private final String username;
    private final String password;

    public JDBCSession(ConnectionSettings settings) {
        url = "jdbc:postgresql://" + settings.host() + ":" + settings.port() + "/" + settings.dbName();
        username = settings.username();
        password = settings.password();
    }

    public Table getQueryResult(String query) throws PGTelegramBotException {
        String jdbcDriver = "org.postgresql.Driver";
        try {
            Class.forName(jdbcDriver);
        } catch (ClassNotFoundException e) {
            throw new PGTelegramBotException("Failed to load " + jdbcDriver + ", please address this bug to an administrator.", e);
        }
        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(query)
        ) {
            return new ResultSetConverter(resultSet).convert();
        } catch (SQLException e) {
            throw new PGTelegramBotException("A database error occurred, please ensure that all parameters are correct and try again.", e);
        }
    }
}
