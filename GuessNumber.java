public class GuessNumber {
    public static void main(String[] args) {
        int computerNumber = 40;
        int playerNumber = 27;
        do {
            if (playerNumber < computerNumber) {
                System.out.printf("Число %d меньше того, что загадал компьютер%n", playerNumber);
                playerNumber += 3;
            } else if (playerNumber > computerNumber) {
                System.out.printf("Число %d больше того, что загадал компьютер%n", playerNumber);
                playerNumber -= 2;
            }
        } while (playerNumber != computerNumber);
        System.out.println("Вы победили!");
    }
}