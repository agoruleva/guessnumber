package startjava.guess;

import static startjava.guess.Player.swap;

import java.util.Random;
import java.util.Scanner;

public class GuessNumber {
    public static final int PLAYER_NUMBER = 3;

    public static final int ROUND_NUMBER = 3;

    private final Player[] players;
    private final Random random;
    private final GuessNumberRound round;

    public GuessNumber(String[] names, Scanner scanner) {
        this.players = createPlayers(names);
        this.random = new Random();
        this.round = new GuessNumberRound(scanner, random);
    }

    public static Player[] createPlayers(String[] names) {
        final Player[] players = new Player[names.length];
        for (int i = 0; i < players.length; ++i) {
            players[i] = new Player(i + 1, names[i]);
        }
        return players;
    }

    public void play() {
        start();
        Player[] roundWinners = new Player[ROUND_NUMBER];
        for (int i = 0; i < ROUND_NUMBER; ++i) {
            roundWinners[i] = round.play(i + 1, copyPlayers());
        }
        new GuessNumberResult(roundWinners).determineGameWinners();
    }

    private Player[] copyPlayers() {
        Player[] copy = new Player[players.length];
        for (int i = 0; i < players.length; ++i) {
            copy[i] = players[i].copyInitial();
        }
        return copy;
    }

    private void start() {
        shuffle();
        displayStartMessage();
    }

    private void shuffle() {
        for (int i = players.length - 1; i > 0; --i) {
            final int j = random.nextInt(0, i + 1);
            swap(players, i, j);
        }
    }

    private void displayStartMessage() {
        System.out.printf("%nБрошен жребий. Очерёдность ходов:%n");
        for (int i = 0; i < players.length; ++i) {
            System.out.printf("%d. %s%n", i + 1, players[i].getName());
        }
        System.out.printf("%nИгра началась! В каждом раунде у игроков по %d попыток.%n",
                Player.ATTEMPTS_NUMBER);
    }
}
