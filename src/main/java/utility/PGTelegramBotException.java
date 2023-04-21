package utility;

public class PGTelegramBotException extends Exception {
    public PGTelegramBotException() {
        super();
    }

    public PGTelegramBotException(String message) {
        super(message);
    }

    public PGTelegramBotException(String message, Throwable cause) {
        super(message, cause);
    }

    public PGTelegramBotException(Throwable cause) {
        super(cause);
    }
}
