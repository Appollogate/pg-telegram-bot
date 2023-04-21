package postgres;

import postgres.data.ConnectionSettings;
import postgres.jdbc.JDBCSession;
import utility.PGTelegramBotException;

public class PGController {
    private final ConnectionSettings connectionSettings;

    public PGController(String host, int port, String dbName, String username, String password) {
        this.connectionSettings = new ConnectionSettings(host, port, dbName, username, password);
    }

    // only meant to support SELECT queries!
    public String executeSQLQuery(String query) throws PGTelegramBotException {
        JDBCSession session = new JDBCSession(connectionSettings);
        return session.getQueryResult(query);
    }
}
