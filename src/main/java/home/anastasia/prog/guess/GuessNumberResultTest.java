package home.anastasia.prog.guess;

import static home.anastasia.prog.guess.GuessNumber.PLAYER_NUMBER;
import static home.anastasia.prog.guess.GuessNumber.ROUND_NUMBER;

public class GuessNumberResultTest {
    public static void main(String[] args) {
        final String[] names = new String[PLAYER_NUMBER];
        for (int i = 0; i < names.length; ++i) {
            names[i] = String.valueOf((char) ('A' + i));
        }

        final Player[] players = createPlayers(names);

        final NumberValidation validation = new NumberValidation();

        // Test 1: Overall loss
        overallLoss(players);

        // Test 2: one player has won all rounds
        totalWinner(copyPlayers(players), validation.copy());

        // Tests 3-5: one round victory of one player
        for (int i = 0; i < ROUND_NUMBER; ++i) {
            oneRoundWinner(copyPlayers(players), i, validation.copy());
        }

        // Tests 6-8: two round victory of one player
        for (int i = 0; i < ROUND_NUMBER; ++i) {
            twoRoundWinner(copyPlayers(players), i, ROUND_NUMBER - 1 - i, validation.copy());
        }

        // Tests 9-11: one round victory of one player and remaining of another
        for (int i = 0; i < PLAYER_NUMBER; ++i) {
            twoAndOneWinners(copyPlayers(players), i, validation.copy());
        }

        // Test 12: overall draw
        overallDraw(copyPlayers(players), validation.copy());

        // Tests 13-15: draw in two rounds
        for (int i = 0; i < PLAYER_NUMBER; ++i) {
            twoRoundsDraw(copyPlayers(players), i, validation.copy());
        }

        // Tests 16-18: two winners in three rounds
        for (int i = 0; i < players.length; ++i) {
            twoWinnersInThreeRounds(copyPlayers(copyPlayers(players)), i, validation.copy());
        }

        // Test 19: one winner with minimal attempts in three rounds
        winnerWithMinimalAttemptsInThreeRounds(copyPlayers(players), validation.copy());

        // Tests 20-23: winner with minimal attempts in two rounds
        for (int i = 0; i < players.length; ++i) {
            winnerWithMinimalAttemptsInTwoRounds(copyPlayers(players), validation.copy(), i);
        }
    }

    private static void overallLoss(Player[] players) {
        System.out.print("Overall loss");
        new GuessNumberResult(players).determineGameWinners();
    }

    private static void totalWinner(Player[] players, NumberValidation validation) {
        System.out.print("Total winner");
        int attempt = 0;
        for (int i = 0; i < ROUND_NUMBER; ++i) {
            players[0].startRound();
            players[0].saveAttempt(++attempt, validation);
            players[0].saveAttempt(++attempt, validation);
            players[0].saveAttemptCount(i);
        }
        new GuessNumberResult(players).determineGameWinners();
    }

    private static void oneRoundWinner(Player[] players, int i, NumberValidation validation) {
        System.out.printf("One player has won round #%d", i + 1);
        players[i].startRound();
        players[i].saveAttempt(1, validation);
        players[i].saveAttempt(2, validation);
        players[i].saveAttemptCount(i);
        new GuessNumberResult(players).determineGameWinners();
    }

    private static void twoRoundWinner(Player[] players, int i, int round, NumberValidation validation) {
        System.out.printf("One player has won all rounds but #%d", round + 1);
        int attempt = 0;
        for (int r = 0; r < ROUND_NUMBER; ++r) {
            if (r != round) {
                players[i].startRound();
                players[i].saveAttempt(++attempt, validation);
                players[i].saveAttempt(++attempt, validation);
                players[i].saveAttemptCount(r);
            }
        }
        new GuessNumberResult(players).determineGameWinners();
    }

    private static void twoAndOneWinners(Player[] players, int i, NumberValidation validation) {
        System.out.printf("One player has won round #%d and another has won remaining rounds", i + 1);
        int attempt = 0;
        for (int j = 0; j < ROUND_NUMBER; ++j) {
            if (j != i) {
                players[i].startRound();
                players[i].saveAttempt(++attempt, validation);
                players[i].saveAttempt(++attempt, validation);
                players[i].saveAttemptCount(j);
            }
        }
        players[(i + 1) % PLAYER_NUMBER].startRound();
        players[(i + 1) % PLAYER_NUMBER].saveAttempt(++attempt, validation);
        players[(i + 1) % PLAYER_NUMBER].saveAttempt(++attempt, validation);
        players[(i + 1) % PLAYER_NUMBER].saveAttemptCount(i);
        new GuessNumberResult(players).determineGameWinners();
    }

    private static void overallDraw(Player[] players, NumberValidation validation) {
        System.out.print("Overall draw");
        int attempt = 0;
        for (int i = 0; i < players.length; ++i) {
            players[i].startRound();
            players[i].saveAttempt(++attempt, validation);
            players[i].saveAttempt(++attempt, validation);
            players[i].saveAttemptCount(i);
        }
        new GuessNumberResult(players).determineGameWinners();
    }

    private static void twoRoundsDraw(Player[] players, int i, NumberValidation validation) {
        System.out.printf("Loss in round #%d and victory in remaining rounds", (i + 2) % ROUND_NUMBER + 1);
        int attempt = 0;
        for (int j = 0; j < ROUND_NUMBER - 1; ++j) {
            players[(i + j) % PLAYER_NUMBER].startRound();
            players[(i + j) % PLAYER_NUMBER].saveAttempt(++attempt, validation);
            players[(i + j) % PLAYER_NUMBER].saveAttempt(++attempt, validation);
            players[(i + j) % PLAYER_NUMBER].saveAttemptCount((i + j) % ROUND_NUMBER);
        }
        new GuessNumberResult(players).determineGameWinners();
    }

    private static void twoWinnersInThreeRounds(Player[] players, int i,
                                                NumberValidation validation) {
        int attempt = 0;
        for (int j = 0; j < players.length; ++j) {
            players[j].startRound();
            players[j].saveAttempt(++attempt, validation);
            players[j].saveAttempt(++attempt, validation);
            if (j != i) {
                players[j].saveAttemptCount(j);
            }
        }
        players[i].saveAttempt(++attempt, validation);
        players[i].saveAttemptCount(i);
        System.out.printf("Two winners with %d attempts, one with %d",
                players[i].getAttemptCount() - 1, players[i].getAttemptCount());
        new GuessNumberResult(players).determineGameWinners();
    }

    private static void winnerWithMinimalAttemptsInThreeRounds(Player[] players,
                                                               NumberValidation validation) {
        System.out.print("Winner with minimal attempts in three rounds");
        int attempt = 0;
        for (int i = 0; i < players.length; ++i) {
            players[i].startRound();
            for (int j = 0; j <= 2 + i; ++j) {
                players[i].saveAttempt(++attempt, validation);
            }
            players[i].saveAttemptCount(i);
        }
        new GuessNumberResult(players).determineGameWinners();
    }

    private static void winnerWithMinimalAttemptsInTwoRounds(Player[] players,
                                                             NumberValidation validation, int i) {
        System.out.print("Winner with minimal attempts in two rounds");
        int attempt = 0;
        for (int r = 0; r < ROUND_NUMBER - 1; ++r) {
            players[(i + r) % PLAYER_NUMBER].startRound();
            for (int a = 0; a <= 2 + r; ++a) {
                players[(i + r) % PLAYER_NUMBER].saveAttempt(++attempt, validation);
            }
            players[(i + r) % PLAYER_NUMBER].saveAttemptCount((i + r) % ROUND_NUMBER);
        }
        new GuessNumberResult(players).determineGameWinners();
    }

    private static Player[] copyPlayers(Player[] players) {
        final Player[] copy = new Player[players.length];
        for (int i = 0; i < copy.length; ++i) {
            copy[i] = players[i].copy();
        }
        return copy;
    }

    private static Player[] createPlayers(String[] names) {
        final Player[] players = GuessNumber.createPlayers(names);
        for (Player player : players) {
            player.startGame();
        }
        return players;
    }
}
