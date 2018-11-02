package exceptions;

public class ConfigurationNotDoneException extends Exception {
    public ConfigurationNotDoneException() {
        super("Configuration Not Done Exception.");
    }
    public ConfigurationNotDoneException(String message) {
        super("Configuration Not Done Exception: " + message);
    }
}
