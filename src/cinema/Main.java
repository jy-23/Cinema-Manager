package cinema;

import java.util.Scanner;

public class Main {


    public static int promptInteger(Scanner s, String prompt, int minVal, int maxVal) {
        int input;
        while (true) {
            System.out.printf("%s: ", prompt);
            if (!s.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                s.nextLine();
                continue;
            }
            input = s.nextInt();
            if (input < minVal || input > maxVal) {
                System.out.printf("Invalid value. Valid range: [%d - %d].%n", minVal, maxVal);
                s.nextLine();
                continue;
            }
            return input;
        }
    }

    public static void main(String[] args) {
        DbConnector db = new DbConnector();
        db.createConnection();

        Scanner scanner = new Scanner(System.in);
        Cinema myCinema = new Cinema(scanner);

        System.out.println("Starting Customer Interface");
        myCinema.getCustomerInterface(scanner, db);

        db.closeConnection();
    }
}
