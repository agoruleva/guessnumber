package home.anastasia.prog.guess;

import static home.anastasia.prog.guess.GuessNumber.ROUND_NUMBER;
import static home.anastasia.prog.guess.Player.MAX_ATTEMPTS_NUMBER;
import static home.anastasia.prog.guess.Player.MAX_PENALTY;
import static home.anastasia.prog.guess.Player.PENALTY;

import java.util.Arrays;

public class GuessNumberResult {
    private final Player[] players;
    private final int[] attemptSums;
    private final int roundWinnerCount;

    public GuessNumberResult(Player[] players) {
        this.players = players;
        this.attemptSums = calculateAttemptSums();
        this.roundWinnerCount = calculateRoundWinnerCount();
    }

    private int[] calculateAttemptSums() {
        final int[] sums = new int[players.length];
        for (int i = 0; i < players.length; ++i) {
            sums[i] = players[i].getAttemptSum();
        }
        return sums;
    }

    public void determineGameWinners() {
        final TotalResult totalResult = getTotalResult();
        if (totalResult.winnerCount() > 0) {
            if (totalResult.winnerCount() > 1 && totalResult.winnerCount() == roundWinnerCount) {
                displayDrawMessage();
            } else {
                displayWinners(totalResult);
            }
        } else {
            displayLossMessage();
        }
        displayRoundWinners();
    }

    private int calculateRoundWinnerCount() {
        int winnerCount = 0;
        for (int sum : attemptSums) {
            if (sum < MAX_PENALTY) {
                ++winnerCount;
            }
        }
        return winnerCount;
    }

    private record TotalResult(int attemptSum, int winnerCount) {}

    private TotalResult getTotalResult() {
        int minAttemptSum = MAX_ATTEMPTS_NUMBER;
        int winnerCount = 0;
        for (int sum : attemptSums) {
            if (sum < minAttemptSum) {
                minAttemptSum = sum;
                winnerCount = 1;
            } else if (sum == minAttemptSum) {
                ++winnerCount;
            }
        }
        return new TotalResult(minAttemptSum, winnerCount);
    }

    private static void displayDrawMessage() {
        System.out.printf("%nНичья. Победила дружба!");
    }

    private void displayWinners(TotalResult totalResult) {
        System.out.printf("%nПобедител%c:", totalResult.winnerCount() > 1 ? 'и' : 'ь');
        for (int i = 0; i < players.length; ++i) {
            if (attemptSums[i] == totalResult.attemptSum()) {
                System.out.printf("%n%s", players[i]);
            }
        }
        System.out.println();
    }

    private static void displayLossMessage() {
        System.out.printf("%nУвы, общий проигрыш.");
    }

    private void displayRoundWinners() {
        if (roundWinnerCount > 0) {
            System.out.printf("%nУгадывал%s:%n", roundWinnerCount > 1 ? "и" : "");
            for (Player player : players) {
                final String[] attempts = new String[player.getScore()];
                for (int j = 0, index = 0; j < ROUND_NUMBER; ++j) {
                    if (player.getRoundAttempts(j) < PENALTY) {
                        attempts[index++] = String.format("%d: %d", j + 1, player.getRoundAttempts(j));
                    }
                }
                if (attempts.length > 0) {
                    System.out.printf("%s: %s%n", player, Arrays.toString(attempts));
                }
            }
        } else {
            System.out.printf("%nУгадавших нет.%n");
        }
        System.out.println();
    }
}
