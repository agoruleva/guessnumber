package startjava.guess;

import static startjava.guess.NumberValidation.containsInRange;

import java.util.Arrays;

public class Player {
    public static final int ATTEMPTS_NUMBER = 10;

    private final String name;
    private final int[] attempts;
    private final NumberValidation validation;
    private int count;

    public Player(String name, NumberValidation validation) {
        this.name = name;
        this.attempts = new int[ATTEMPTS_NUMBER];
        this.validation = validation;
    }

    public String getName() {
        return name;
    }

    public int[] getAttempts() {
        return Arrays.copyOf(attempts, count);
    }

    public int getCount() {
        return count;
    }

    public SaveResult saveAttempt(int attempt) {
        SaveResult result;
        if (!containsInRange(attempt)) {
            result = SaveResult.OUT_OF_RANGE;
        } else if (!validation.markUsed(attempt)) {
            result = SaveResult.REPEATED;
        } else {
            attempts[count++] = attempt;
            result = SaveResult.OK;
        }
        return result;
    }

    public boolean hasAttempts() {
        return count < ATTEMPTS_NUMBER;
    }

    public void start() {
        count = 0;
    }
}
