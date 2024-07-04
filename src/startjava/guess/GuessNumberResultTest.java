package startjava.guess;

import static startjava.guess.GuessNumber.PLAYER_NUMBER;
import static startjava.guess.GuessNumber.ROUND_NUMBER;
import static startjava.guess.GuessNumber.createPlayers;

import java.util.Arrays;

public class GuessNumberResultTest {
    public static void main(String[] args) {
        final String[] names = new String[PLAYER_NUMBER];
        for (int i = 0; i < names.length; ++i) {
            names[i] = String.valueOf((char) ('A' + i));
        }

        final Player[] players = createPlayers(names);
        final NumberValidation validation = new NumberValidation();
        int attempt = 0;
        for (Player player : players) {
            player.saveAttempt(++attempt, validation);
            player.saveAttempt(++attempt, validation);
        }

        // Test 1: Overall loss
        overallLoss();

        // Test 2: one player has won all rounds
        totalWinner(players[0]);

        // Tests 3-5: one round victory of one player
        oneRoundWinner(players[0], 0);
        oneRoundWinner(players[1], 1);
        oneRoundWinner(players[2], 2);

        // Tests 6-8: two round victory of one player
        twoRoundWinner(players[0], 2);
        twoRoundWinner(players[1], 1);
        twoRoundWinner(players[2], 0);

        // Tests 9-11: one round victory of one player and remaining of another
        twoAndOneWinners(players[0], players[1], 2);
        twoAndOneWinners(players[1], players[2], 1);
        twoAndOneWinners(players[0], players[2], 0);

        // Test 12: overall draw
        overallDraw(players);

        // Tests 13-15: draw in two rounds
        twoRoundsDraw(players, 2);
        twoRoundsDraw(players, 1);
        twoRoundsDraw(players, 0);

        // Tests 16-18: two winners in three rounds
        twoWinnersInThreeRounds(players, 2, attempt, validation);
        twoWinnersInThreeRounds(players, 1, attempt, validation);
        twoWinnersInThreeRounds(players, 0, attempt, validation);

        // Test 19: one winner with minimal attempts in three rounds
        winnerWithMinimalAttemptsInThreeRounds(players, attempt, validation);

        // Tests 20-23: winner with minimal attempts in two rounds
        winnerWithMinimalAttemptsInTwoRounds(players, attempt, validation, 1);
        winnerWithMinimalAttemptsInTwoRounds(players, attempt, validation, 2);
        winnerWithMinimalAttemptsInTwoRounds(players, attempt, validation, 0);
    }

    private static void overallLoss() {
        System.out.print("Overall loss");
        final Player[] empty = new Player[ROUND_NUMBER];
        new GuessNumberResult(empty).determineGameWinners();
    }

    private static void totalWinner(Player winner) {
        System.out.print("Total winner");
        final Player[] players = new Player[ROUND_NUMBER];
        Arrays.fill(players, winner);
        new GuessNumberResult(players).determineGameWinners();
    }

    private static void oneRoundWinner(Player player, int i) {
        System.out.printf("One player has won round #%d", i + 1);
        final Player[] players = new Player[ROUND_NUMBER];
        players[i] = player;
        new GuessNumberResult(players).determineGameWinners();
    }

    private static void twoRoundWinner(Player player, int i) {
        System.out.printf("One player has won all rounds but #%d", i + 1);
        final Player[] players = new Player[ROUND_NUMBER];
        Arrays.fill(players, player);
        players[i] = null;
        new GuessNumberResult(players).determineGameWinners();
    }

    private static void twoAndOneWinners(Player one, Player two, int second) {
        System.out.printf("One player has won round #%d and another has won remaining rounds",
                second + 1);
        final Player[] players = new Player[ROUND_NUMBER];
        Arrays.fill(players, one);
        players[second] = two;
        new GuessNumberResult(players).determineGameWinners();
    }

    private static void overallDraw(Player[] winners) {
        System.out.print("Overall draw");
        final Player[] players = Arrays.copyOf(winners, winners.length);
        new GuessNumberResult(players).determineGameWinners();
    }

    private static void twoRoundsDraw(Player[] winners, int i) {
        System.out.printf("Loss in round #%d and victory in remaining rounds", i + 1);
        final Player[] players = Arrays.copyOf(winners, winners.length);
        players[i] = null;
        new GuessNumberResult(players).determineGameWinners();
    }

    private static void twoWinnersInThreeRounds(Player[] winners, int i, int attempt,
                                                NumberValidation validation) {
        System.out.printf("Two winners with %d attempts, one with %d",
                winners[i].getCount(), winners[i].getCount() + 1);
        final Player[] players = copyPlayers(winners);
        players[i].saveAttempt(++attempt, validation.copy());
        new GuessNumberResult(players).determineGameWinners();
    }

    private static void winnerWithMinimalAttemptsInThreeRounds(Player[] winners, int attempt,
                                                               NumberValidation validation) {
        System.out.print("Winner with minimal attempts in three rounds");
        final Player[] players = copyPlayers(winners);
        NumberValidation localValidation = validation.copy();
        players[0].saveAttempt(++attempt, localValidation);
        players[2].saveAttempt(++attempt, localValidation);
        players[2].saveAttempt(++attempt, localValidation);
        new GuessNumberResult(players).determineGameWinners();
    }

    private static void winnerWithMinimalAttemptsInTwoRounds(Player[] winners, int attempt,
                                                             NumberValidation validation, int i) {
        System.out.print("Winner with minimal attempts in two rounds");
        final Player[] players = copyPlayers(winners);
        players[i].saveAttempt(++attempt, validation.copy());
        players[(i + 1) % players.length] = null;
        new GuessNumberResult(players).determineGameWinners();
    }

    private static Player[] copyPlayers(Player[] players) {
        final Player[] copy = new Player[players.length];
        for (int i = 0; i < copy.length; ++i) {
            copy[i] = players[i].copy();
        }
        return copy;
    }
}
