package startjava.guess;

import static startjava.guess.NumberValidation.HIGH;
import static startjava.guess.NumberValidation.LOW;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class GuessNumberRound {
    private static final String ORDINAL_PROMPT = "%n%s, ваш ход: ";
    private static final String PROMPT_ON_ERROR = "Попробуйте ещё раз: ";

    private final Scanner scanner;
    private final Random random;
    private final NumberValidation validation;
    private int computerNumber;

    public GuessNumberRound(Scanner scanner, Random random) {
        this.scanner = scanner;
        this.random = random;
        this.validation = new NumberValidation();
    }

    public Player play(int n, Player[] players) {
        start(n);
        int currentIndex = 0;
        Player currentPlayer = players[currentIndex];
        Player winner = null;
        SaveResult saveResult = SaveResult.OK;
        int number;
        do {
            number = askNumber(currentPlayer.getName(), saveResult);
            saveResult = currentPlayer.saveAttempt(number, validation);
            if (saveResult != SaveResult.OK) {
                displayWarningMessage(saveResult);
                continue;
            }
            CheckResult checkResult = checkPlayerNumber(number);
            if (checkResult == CheckResult.EQUALS) {
                displayVictoryMessage(currentPlayer);
                winner = currentPlayer;
            } else {
                displayHint(number, checkResult);
                if (!currentPlayer.hasAttempts()) {
                    displayLostMessage(currentPlayer.getName());
                }
            }
            currentIndex = (currentIndex + 1) % players.length;
            currentPlayer = players[currentIndex];
        } while (number != computerNumber && currentPlayer.hasAttempts());
        displayAttempts(players);
        return winner;
    }

    private void start(int n) {
        validation.resetMarks();
        computerNumber = random.nextInt(LOW, HIGH + 1);
        displayStartMessage(n);
    }

    private static void displayStartMessage(int n) {
        System.out.printf("%nРаунд %d%n", n);
    }

    public int askNumber(String name, SaveResult saveResult) {
        System.out.print(saveResult == SaveResult.OK ? ORDINAL_PROMPT.formatted(name)
                : PROMPT_ON_ERROR);
        return scanner.nextInt();
    }

    private static void displayWarningMessage(SaveResult saveResult) {
        System.out.println(saveResult);
    }

    private CheckResult checkPlayerNumber(int number) {
        return number == computerNumber ? CheckResult.EQUALS
                : (number < computerNumber ? CheckResult.LESS : CheckResult.GREATER);
    }

    private void displayVictoryMessage(Player player) {
        System.out.printf("%s угадал число %d с %d-й попытки%n",
                player.getName(), computerNumber, player.getCount());
    }

    private static void displayHint(int number, CheckResult result) {
        System.out.printf("Число %d %s того, что загадал компьютер%n", number, result);
    }

    private static void displayLostMessage(String name) {
        System.out.printf("У %s закончились попытки!%n", name);
    }

    private static void displayAttempts(Player[] players) {
        System.out.printf("%nПопытки игроков:");
        for (Player player : players) {
            displayAttempts(player);
        }
        System.out.println();
    }

    private static void displayAttempts(Player player) {
        System.out.printf("%n%s: %s", player.getName(), Arrays.toString(player.getAttempts()));
    }
}
