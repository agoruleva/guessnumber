package startjava.guess;

import static startjava.guess.NumberValidation.HIGH;
import static startjava.guess.NumberValidation.LOW;

public enum SaveResult {
    OK(""),
    REPEATED("Число введено повторно."),
    OUT_OF_RANGE("Число должно входить в интервал [%d, %d].".formatted(LOW, HIGH));

    private final String message;

    SaveResult(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return getMessage();
    }
}
