import java.util.Random;
import java.util.Scanner;

public class GuessNumber {
    private final Player firstPlayer;
    private final Player secondPlayer;
    private final Scanner scanner;
    private final Random random;

    public GuessNumber(Player firstPlayer, Player secondPlayer, Scanner scanner) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.scanner = scanner;
        this.random = new Random();
    }

    public void play() {
        int computerNumber = random.nextInt(1, 101);
        Player currentPlayer = null;
        int playerNumber;
        do {
            System.out.println();
            currentPlayer = currentPlayer == firstPlayer ? secondPlayer : firstPlayer;
            playerNumber = askNumber(currentPlayer);
            if (playerNumber < computerNumber) {
                System.out.printf("Число %d меньше того, что загадал компьютер%n", playerNumber);
            } else if (playerNumber > computerNumber) {
                System.out.printf("Число %d больше того, что загадал компьютер%n", playerNumber);
            }
        } while (playerNumber != computerNumber);
        System.out.printf("%s, поздравляю, вы победили!%n%n", currentPlayer.getName());
    }

    private int askNumber(Player player) {
        System.out.printf("%s, ваш ход: ", player.getName());
        return scanner.nextInt();
    }
}