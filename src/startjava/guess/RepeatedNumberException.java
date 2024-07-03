package startjava.guess;

public class RepeatedNumberException extends RuntimeException {
    private static final String MESSAGE = "Число введено повторно.";

    public RepeatedNumberException() {
        super(MESSAGE);
    }

    public RepeatedNumberException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
