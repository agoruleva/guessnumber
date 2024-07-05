package startjava.guess;

import static startjava.guess.NumberValidation.containsInRange;

import java.util.Arrays;

public class Player {
    public static final int ATTEMPTS_NUMBER = 10;

    private final int id;
    private final String name;
    private final int[] attempts;
    private int count;

    public Player(int id, String name) {
        this.id = id;
        this.name = name;
        this.attempts = new int[ATTEMPTS_NUMBER];
    }

    private Player(Player player) {
        this.id = player.id;
        this.name = player.name;
        this.attempts = Arrays.copyOf(player.attempts, player.attempts.length);
        this.count = player.count;
    }

    public int getId() {
        return id;
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

    public void saveAttempt(int attempt, NumberValidation validation) {
        if (!containsInRange(attempt)) {
            throw new WrongInputException();
        } else if (!validation.markUsed(attempt)) {
            throw new RepeatedNumberException();
        } else {
            attempts[count++] = attempt;
        }
    }

    public boolean hasAttempts() {
        return count < ATTEMPTS_NUMBER;
    }

    public Player copyInitial() {
        return new Player(id, name);
    }

    public Player copy() {
        return new Player(this);
    }

    @Override
    public String toString() {
        return getName();
    }

    public static void swap(Player[] players, int i, int j) {
        final Player temp = players[i];
        players[i] = players[j];
        players[j] = temp;
    }
}
