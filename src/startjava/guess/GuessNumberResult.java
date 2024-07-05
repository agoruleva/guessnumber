package startjava.guess;

import java.util.Arrays;

public class GuessNumberResult {
    private final Player[] players;
    private final int[] ranks;

    public GuessNumberResult(Player[] players) {
        this.players = players;
        this.ranks = getRanks();
    }

    public void determineGameWinners() {
        final TotalResult totalResult = getTotalResult();
        final int roundWinnerCount = calculateRoundWinnerCount();
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

    private int[] getRanks() {
        final boolean[] marked = new boolean[players.length];
        final int[] ranks = new int[players.length];
        for (int i = 0; i < players.length; ++i) {
            if (players[i] != null && !marked[i]) {
                for (int j = i; j < players.length; ++j) {
                    if (players[j] != null && players[i].getId() == players[j].getId()) {
                        ++ranks[i];
                        marked[j] = true;
                    }
                }
            }
        }
        return ranks;
    }

    private int calculateRoundWinnerCount() {
        int winnerCount = 0;
        for (Player player : players) {
            if (player != null) {
                ++winnerCount;
            }
        }
        return winnerCount;
    }

    private record TotalResult(int rank, int attemptCount, int winnerCount) {}

    private TotalResult getTotalResult() {
        int maxRank = 0;
        int minAttempt = Player.ATTEMPTS_NUMBER;
        int winnerCount = 0;
        for (int i = 0; i < players.length; ++i) {
            if (ranks[i] > 0) {
                if (ranks[i] > maxRank || (ranks[i] == maxRank && players[i].getCount() < minAttempt)) {
                    maxRank = ranks[i];
                    minAttempt = players[i].getCount();
                    winnerCount = 1;
                } else if (ranks[i] == maxRank && players[i].getCount() == minAttempt) {
                    ++winnerCount;
                }
            }
        }
        return new TotalResult(maxRank, minAttempt, winnerCount);
    }

    private static void displayDrawMessage() {
        System.out.printf("%nНичья. Победила дружба!");
    }

    private void displayWinners(TotalResult totalResult) {
        System.out.printf("%nПобедител%c:", totalResult.winnerCount() > 1 ? 'и' : 'ь');
        for (int i = 0; i < players.length; ++i) {
            if (ranks[i] == totalResult.rank() && players[i].getCount() == totalResult.attemptCount()) {
                System.out.printf("%n%s", players[i]);
            }
        }
        System.out.println();
    }

    private static void displayLossMessage() {
        System.out.printf("%nУвы, общий проигрыш.");
    }

    private void displayRoundWinners() {
        final int actualWinnerCount = getActualWinnerCount();
        if (actualWinnerCount > 0) {
            System.out.printf("%nУгадывал%s:%n", actualWinnerCount > 1 ? "и" : "");
            for (int i = 0; i < players.length; ++i) {
                if (players[i] != null && ranks[i] != 0) {
                    final String[] attempts = new String[ranks[i]];
                    for (int j = 0, index = 0; j < players.length; ++j) {
                        if (players[j] != null && players[j].getId() == players[i].getId()) {
                            attempts[index++] = String.format("%d: %d", j + 1, players[j].getCount());
                        }
                    }
                    System.out.printf("%s: %s%n", players[i], Arrays.toString(attempts));
                }
            }
        } else {
            System.out.printf("%nУгадавших нет.%n");
        }
        System.out.println();
    }

    private int getActualWinnerCount() {
        int actualWinnerCount = 0;
        for (int rank : ranks) {
            if (rank != 0) {
                ++actualWinnerCount;
            }
        }
        return actualWinnerCount;
    }
}
