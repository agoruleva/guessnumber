package startjava.guess;

import java.util.Random;
import java.util.Scanner;

public class GuessNumber {
    private final Player firstPlayer;
    private final Player secondPlayer;
    private final Scanner scanner;
    private final Random random;

    public GuessNumber(String firstName, String secondName, Scanner scanner) {
        this.firstPlayer = new Player(firstName);
        this.secondPlayer = new Player(secondName);
        this.scanner = scanner;
        this.random = new Random();
    }

    public void play() {
        int computerNumber = random.nextInt(1, 101);
        Player currentPlayer = null;
        do {
            currentPlayer = currentPlayer == firstPlayer ? secondPlayer : firstPlayer;
            currentPlayer.askNumber(scanner);
        } while (!currentPlayer.guessRight(computerNumber));
        currentPlayer.congratulate();
    }
}