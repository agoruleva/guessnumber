package startjava.guess;

import java.util.Scanner;

public class Player {
    private final String name;
    private int number;

    public Player(String name) {
        this.name = name;
    }

    public void askNumber(Scanner scanner) {
        System.out.printf("%n%s, ваш ход: ", name);
        number = scanner.nextInt();
    }

    public boolean guessRight(int computerNumber) {
        boolean right = false;
        if (number < computerNumber) {
            System.out.printf("Число %d меньше того, что загадал компьютер%n", number);
        } else if (number > computerNumber) {
            System.out.printf("Число %d больше того, что загадал компьютер%n", number);
        } else {
            right = true;
        }
        return right;
    }

    public void congratulate() {
        System.out.printf("%s, поздравляю, вы победили!%n%n", name);
    }
}