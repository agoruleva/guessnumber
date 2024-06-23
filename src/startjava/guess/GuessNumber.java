package startjava.guess;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class GuessNumber {
    public static final int PLAYER_NUMBER = 3;
    private static final String ORDINAL_PROMPT = "%n%s, ваш ход: ";
    private static final String PROMPT_ON_ERROR = "Попробуйте ещё раз: ";

    private final NumberValidation validation;
    private final Player[] players;
    private final Scanner scanner;
    private final Random random;
    private int computerNumber;

    public GuessNumber(String[] names, Scanner scanner) {
        this.validation = new NumberValidation();
        this.players = new Player[names.length];
        for (int i = 0; i < players.length; ++i) {
            players[i] = new Player(names[i], validation);
        }
        this.scanner = scanner;
        this.random = new Random();
    }

    public void play() {
        start();
        int currentIndex = 0;
        Player currentPlayer;
        SaveResult saveResult = SaveResult.OK;
        int number;
        do {
            currentPlayer = players[currentIndex];
            number = askNumber(currentPlayer, saveResult);
            saveResult = currentPlayer.saveAttempt(number);
            if (saveResult != SaveResult.OK) {
                displayWarningMessage(saveResult);
                continue;
            }
            CheckResult checkResult = checkPlayerNumber(number);
            if (checkResult == CheckResult.EQUALS) {
                displayVictoryMessage(currentPlayer);
            } else {
                displayHelp(number, checkResult);
                if (!currentPlayer.hasAttempts()) {
                    displayLostMessage(currentPlayer);
                }
            }
            currentIndex = (currentIndex + 1) % players.length;
        } while (number != computerNumber && currentPlayer.hasAttempts());
        displayAttempts();
    }

    private void displayWarningMessage(SaveResult saveResult) {
        System.out.println(saveResult);
    }

    private void start() {
        validation.resetMarks();
        for (Player player : players) {
            player.start();
        }
        computerNumber = random.nextInt(1, 101);
        System.out.printf("Игра началась! У каждого игрока по %d попыток.%n", Player.ATTEMPTS_NUMBER);
    }

    public int askNumber(Player player, SaveResult saveResult) {
        System.out.print(saveResult == SaveResult.OK ? ORDINAL_PROMPT.formatted(player.getName())
                : PROMPT_ON_ERROR);
        return scanner.nextInt();
    }

    private CheckResult checkPlayerNumber(int number) {
        return number == computerNumber ? CheckResult.EQUALS
                : (number < computerNumber ? CheckResult.LESS : CheckResult.GREATER);
    }

    private void displayVictoryMessage(Player player) {
        System.out.printf("%s угадал число %d с %d-й попытки%n",
                player.getName(), computerNumber, player.getCount());
    }

    private void displayHelp(int number, CheckResult result) {
        System.out.printf("Число %d %s того, что загадал компьютер%n", number, result);
    }

    private static void displayLostMessage(Player player) {
        System.out.printf("У %s закончились попытки!%n", player.getName());
    }

    private void displayAttempts() {
        System.out.printf("%nПопытки игроков:%n");
        for (Player player : players) {
            displayAttempts(player);
        }
    }

    private static void displayAttempts(Player player) {
        System.out.printf("%s: %s%n", player.getName(), Arrays.toString(player.getAttempts()));
    }
}
