package startjava.guess;

import static startjava.guess.NumberValidation.HIGH;
import static startjava.guess.NumberValidation.LOW;

public class WrongInputException extends RuntimeException {
    private static final String MESSAGE = "Ожидается число в интервале [%d, %d].".formatted(LOW, HIGH);

    public WrongInputException() {
        super(MESSAGE);
    }

    public WrongInputException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
