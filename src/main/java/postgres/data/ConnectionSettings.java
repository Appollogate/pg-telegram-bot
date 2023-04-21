package postgres.data;

public record ConnectionSettings(String host, Integer port, String dbName, String username, String password) {
}
