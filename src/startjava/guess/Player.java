package startjava.guess;

import static startjava.guess.GuessNumber.ROUND_NUMBER;
import static startjava.guess.NumberValidation.containsInRange;

import java.util.Arrays;

public class Player {
    public static final int ATTEMPTS_NUMBER = 10;
    public static final int MAX_ATTEMPTS_NUMBER = ATTEMPTS_NUMBER * ROUND_NUMBER;
    public static final int PENALTY = ATTEMPTS_NUMBER + 1;
    public static final int MAX_PENALTY = PENALTY * ROUND_NUMBER;

    private final String name;
    private final int[] attempts;
    private int attemptCount;
    private int[] attemptCounters;

    public Player(String name) {
        this.name = name;
        this.attempts = new int[ATTEMPTS_NUMBER];
        this.attemptCounters = new int[ROUND_NUMBER];
    }

    private Player(Player player) {
        this.name = player.name;
        this.attempts = Arrays.copyOf(player.attempts, player.attempts.length);
        this.attemptCount = player.attemptCount;
        this.attemptCounters = Arrays.copyOf(player.attemptCounters, player.attemptCounters.length);
    }

    public String getName() {
        return name;
    }

    public int[] getAttempts() {
        return Arrays.copyOf(attempts, attemptCount);
    }

    public int getAttemptCount() {
        return attemptCount;
    }

    public int getAttemptSum() {
        int attemptSum = 0;
        for (int count : attemptCounters) {
            attemptSum += count;
        }
        return attemptSum;
    }

    public int getRoundAttempts(int i) {
        return attemptCounters[i];
    }

    public int getScore() {
        int score = 0;
        for (int count : attemptCounters) {
            if (count < PENALTY) {
                ++score;
            }
        }
        return score;
    }

    public void startGame() {
        Arrays.fill(attemptCounters, PENALTY);
    }

    public void startRound() {
        attemptCount = 0;
    }

    public void saveAttempt(int attempt, NumberValidation validation) {
        if (!containsInRange(attempt)) {
            throw new WrongInputException();
        } else if (!validation.markUsed(attempt)) {
            throw new RepeatedNumberException();
        } else {
            attempts[attemptCount++] = attempt;
        }
    }

    public boolean hasAttempts() {
        return attemptCount < ATTEMPTS_NUMBER;
    }

    public void saveAttemptCount(int round) {
        attemptCounters[round] = attemptCount;
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
