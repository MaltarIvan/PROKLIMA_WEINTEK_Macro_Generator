package exceptions;

public class WrongFormatException extends Exception {
    public WrongFormatException() {
        super("Wrong Format Exception.");
    }
    public WrongFormatException(String message) {
        super("Wrong Format Exception: " + message);
    }
}
