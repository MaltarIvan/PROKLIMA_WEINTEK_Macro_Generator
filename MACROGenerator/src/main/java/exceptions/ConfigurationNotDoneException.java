package exceptions;

public class ConfigurationNotDoneException extends Exception {
    public ConfigurationNotDoneException() {}
    public ConfigurationNotDoneException(String message) {
        super(message);
    }
}
