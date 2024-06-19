package startjava.guess;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class GuessNumber {
    private final Player firstPlayer;
    private final Player secondPlayer;
    private final Scanner scanner;
    private final Random random;
    private int computerNumber;

    public GuessNumber(String firstName, String secondName, Scanner scanner) {
        this.firstPlayer = new Player(firstName);
        this.secondPlayer = new Player(secondName);
        this.scanner = scanner;
        this.random = new Random();
    }

    public void play() {
        start();
        Player currentPlayer = firstPlayer;
        int number;
        do {
            number = askNumber(currentPlayer);
            currentPlayer.saveAttempt(number);
            if (number == computerNumber) {
                displayVictoryMessage(currentPlayer);
            } else {
                displayHelp(number);
                if (!currentPlayer.hasAttempts()) {
                    displayLostMessage(currentPlayer);
                }
            }
            currentPlayer = currentPlayer == firstPlayer ? secondPlayer : firstPlayer;
        } while (number != computerNumber && currentPlayer.hasAttempts());
        displayAttempts();
    }

    private void start() {
        firstPlayer.start();
        secondPlayer.start();
        computerNumber = random.nextInt(1, 101);
        System.out.printf("Игра началась! У каждого игрока по %d попыток.%n", Player.ATTEMPTS_NUMBER);
    }

    public int askNumber(Player player) {
        System.out.printf("%n%s, ваш ход: ", player.getName());
        return scanner.nextInt();
    }

    private void displayVictoryMessage(Player player) {
        System.out.printf("%s угадал число %d с %d-й попытки%n",
                player.getName(), computerNumber, player.getCount());
    }

    private void displayHelp(int number) {
        if (number < computerNumber) {
            System.out.printf("Число %d меньше того, что загадал компьютер%n", number);
        } else {
            System.out.printf("Число %d больше того, что загадал компьютер%n", number);
        }
    }

    private static void displayLostMessage(Player player) {
        System.out.printf("У %s закончились попытки!%n", player.getName());
    }

    private void displayAttempts() {
        System.out.printf("%nПопытки игроков:%n");
        displayAttempts(firstPlayer);
        displayAttempts(secondPlayer);
    }

    private static void displayAttempts(Player player) {
        System.out.printf("%s: %s%n", player.getName(), Arrays.toString(player.getAttempts()));
    }
}
