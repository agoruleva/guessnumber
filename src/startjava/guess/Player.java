package startjava.guess;

import java.util.Arrays;

public class Player {
    public static final int ATTEMPTS_NUMBER = 10;

    private final String name;
    private final int[] attempts;
    private int count;

    public Player(String name) {
        this.name = name;
        this.attempts = new int[ATTEMPTS_NUMBER];
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

    public void saveAttempt(int attempt) {
        attempts[count++] = attempt;
    }

    public boolean hasAttempts() {
        return count < ATTEMPTS_NUMBER;
    }

    public void start() {
        count = 0;
    }
}
