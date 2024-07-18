package home.anastasia.prog.guess;

import java.util.Scanner;

public class GuessNumberGame {
    private static final String PROMPT = "Хотите продолжить игру? [yes/no]: ";
    private static final String ALT_PROMPT = "Введите корректный ответ [yes/no]: ";
    private static final String NO = "no";
    private static final String YES = "yes";

    private final Scanner scanner = new Scanner(System.in);
    private final GuessNumber guessNumber;

    public GuessNumberGame() {
        System.out.println("Представьтесь, пожалуйста:");
        String[] names = new String[GuessNumber.PLAYER_NUMBER];
        for (int i = 0; i < names.length; ++i) {
            names[i] = askName(i + 1);
        }
        guessNumber = new GuessNumber(names, scanner);
    }

    private String askName(int playerNumber) {
        System.out.print(playerNumber + ". ");
        return scanner.nextLine();
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
        System.out.printf("%s", YES.equals(previous) ? PROMPT : ALT_PROMPT);
        return scanner.next().toLowerCase();
    }

    public static void main(String[] args) {
        new GuessNumberGame().run();
    }
}
