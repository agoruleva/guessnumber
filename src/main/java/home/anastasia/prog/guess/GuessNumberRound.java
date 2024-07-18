package home.anastasia.prog.guess;

import static home.anastasia.prog.guess.NumberValidation.HIGH;
import static home.anastasia.prog.guess.NumberValidation.LOW;

import java.util.Random;
import java.util.Scanner;

public class GuessNumberRound {
    private static final String ORDINAL_PROMPT = "%n%s, ваш ход: ";
    private static final String PROMPT_ON_ERROR = "Попробуйте ещё раз: ";

    private final Player[] players;
    private final Scanner scanner;
    private final Random random;
    private final NumberValidation validation;
    private int computerNumber;

    public GuessNumberRound(Player[] players, Scanner scanner, Random random) {
        this.players = players;
        this.scanner = scanner;
        this.random = random;
        this.validation = new NumberValidation();
    }

    public void play(int i) {
        start(i);
        int currentIndex = 0;
        Player currentPlayer = players[currentIndex];
        String prompt = formOrdinalPrompt(currentPlayer);
        int number = 0;
        do {
            try {
                number = askNumber(prompt);
                currentPlayer.saveAttempt(number, validation);
            } catch (RuntimeException e) {
                displayWarningMessage(e);
                prompt = PROMPT_ON_ERROR;
                continue;
            }
            CheckResult checkResult = checkPlayerNumber(number);
            if (checkResult == CheckResult.EQUALS) {
                currentPlayer.saveAttemptCount(i);
                displayVictoryMessage(currentPlayer);
            } else {
                displayHint(number, checkResult);
                if (!currentPlayer.hasAttempts()) {
                    displayLostMessage(currentPlayer);
                }
            }
            currentIndex = (currentIndex + 1) % players.length;
            currentPlayer = players[currentIndex];
            prompt = formOrdinalPrompt(currentPlayer);
        } while (number != computerNumber && currentPlayer.hasAttempts());
        displayAttempts();
    }

    private void start(int n) {
        for (Player player : players) {
            player.startRound();
        }
        validation.resetMarks();
        computerNumber = random.nextInt(LOW, HIGH + 1);
        displayStartMessage(n);
    }

    private static void displayStartMessage(int i) {
        System.out.printf("%nРаунд %d%n", i + 1);
    }

    private static String formOrdinalPrompt(Player player) {
        return ORDINAL_PROMPT.formatted(player);
    }

    public int askNumber(String prompt) {
        System.out.print(prompt);
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (RuntimeException e) {
            throw new WrongInputException(e);
        }
    }

    private static void displayWarningMessage(RuntimeException e) {
        System.out.println(e.getMessage());
    }

    private CheckResult checkPlayerNumber(int number) {
        return number == computerNumber ? CheckResult.EQUALS
                : (number < computerNumber ? CheckResult.LESS : CheckResult.GREATER);
    }

    private void displayVictoryMessage(Player player) {
        System.out.printf("%s угадал число %d с %d-й попытки%n",
                player, computerNumber, player.getAttemptCount());
    }

    private static void displayHint(int number, CheckResult result) {
        System.out.printf("Число %d %s того, что загадал компьютер%n", number, result);
    }

    private static void displayLostMessage(Player player) {
        System.out.printf("У %s закончились попытки!%n", player);
    }

    private void displayAttempts() {
        for (Player player : players) {
            displayAttempts(player);
        }
        System.out.println();
    }

    private static void displayAttempts(Player player) {
        System.out.printf("%nПопытки игрока %s:", player);
        final int[] playerAttempts = player.getAttempts();
        for (int attempt : playerAttempts) {
            System.out.printf("%3d", attempt);
        }
    }
}
