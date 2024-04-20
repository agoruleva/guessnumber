package startjava.guess;

import java.util.Scanner;

public class GuessNumberTest {
    private final Scanner scanner = new Scanner(System.in);
    private final GuessNumber guessNumber;

    public GuessNumberTest() {
        System.out.println("Представьтесь, пожалуйста:");
        Player firstPlayer = askName(1);
        Player secondPlayer = askName(2);
        guessNumber = new GuessNumber(firstPlayer, secondPlayer, scanner);
    }

    private Player askName(int playerNumber) {
        System.out.print(playerNumber + ". ");
        return new Player(scanner.nextLine());
    }

    public void run() {
        do {
            guessNumber.play();
        } while ("yes".equals(askToContinue()));
    }

    private String askToContinue() {
        String answer;
        do {
            System.out.print("Хотите продолжить игру? [yes/no]: ");
            answer = scanner.next().toLowerCase();
        } while (!("yes".equals(answer) || "no".equals(answer)));
        return answer;
    }

    public static void main(String[] args) {
        new GuessNumberTest().run();
    }
}