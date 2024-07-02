package startjava.guess;

import java.util.Arrays;

public class NumberValidation {
    public static final int LOW = 1;
    public static final int HIGH = 100;
    private final boolean[] used;

    public NumberValidation() {
        used = new boolean[HIGH - LOW + 1];
    }

    private NumberValidation(NumberValidation validation) {
        this.used = Arrays.copyOf(validation.used, validation.used.length);
    }

    public NumberValidation copy() {
        return new NumberValidation(this);
    }

    public void resetMarks() {
        Arrays.fill(used, false);
    }

    public boolean markUsed(int attempt) {
        final int attemptIndex = getAttemptIndex(attempt);
        boolean success = !used[attemptIndex];
        if (success) {
            used[attemptIndex] = true;
        }
        return success;
    }

    public static boolean containsInRange(int attempt) {
        return attempt >= LOW && attempt <= HIGH;
    }

    private int getAttemptIndex(int attempt) {
        return attempt - LOW;
    }
}
