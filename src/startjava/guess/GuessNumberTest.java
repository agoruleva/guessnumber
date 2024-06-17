package startjava.guess;

import java.util.Scanner;

public class GuessNumberTest {
    private static final String PROMPT = "Хотите продолжить игру? [yes/no]: ";
    private static final String ALT_PROMPT = "Введите корректный ответ [yes/no]: ";
    private static final String NO = "no";
    private static final String YES = "yes";

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
        String answer = YES;
        do {
            if (!YES.equals(answer)) {
                continue;
            }
            guessNumber.play();
        } while (!NO.equals(answer = askToContinue(answer)));
    }

    private String askToContinue(String previous) {
        System.out.print(YES.equals(previous) ? PROMPT : ALT_PROMPT);
        return scanner.next().toLowerCase();
    }

    public static void main(String[] args) {
        new GuessNumberTest().run();
    }
}