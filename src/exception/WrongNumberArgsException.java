package exception;

public class WrongNumberArgsException extends RuntimeException {

    public WrongNumberArgsException() {
    }

    public WrongNumberArgsException(final String message) {
        super(message);
    }
}
