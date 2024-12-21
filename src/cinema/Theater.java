package cinema;

import java.util.Arrays;
import java.util.Scanner;

public class Theater {
    private final int numRow;
    private final int numCol;
    private char[][] seatingChart;
    private int numFrontRow;
    private float frontTicketPrice = 10f;
    private float backTicketPrice = 10f;

    int numTicketsSold = 0;
    float currentIncome = 0f;

    Theater(int numRow, int numCol) {
        this.numRow = numRow;
        this.numCol = numCol;

        int totalSeats = numRow * numCol;
        if (totalSeats > 60) {
            backTicketPrice = 8;
            numFrontRow = numRow / 2;
        }

        seatingChart = new char[numRow][numCol];
        for (char[] rowArray : seatingChart){
            Arrays.fill(rowArray, 'S');
        }
    }

    private float calculateMaxProfit() {
        int backRows = numRow - numFrontRow;
        float frontProfit = numFrontRow * numCol * frontTicketPrice;
        float backProfit = backRows * numCol * backTicketPrice;
        return frontProfit + backProfit;
    }

    private float calculateTicketPrice(int selectedRow) {
        return selectedRow <= numFrontRow ? frontTicketPrice : backTicketPrice;
    }

    private boolean updateSeatingChart(int selectedRow, int selectedCol) {
        if (seatingChart[selectedRow - 1][selectedCol - 1] != 'B') {
            seatingChart[selectedRow - 1][selectedCol - 1] = 'B';
            return true; // success in updating
        }
        return false;
    }

    private void startUserInterface(Scanner scanner) {
        int userChoice;
        do {
            printMenu();
            userChoice = promptInteger(scanner, "Please enter your choice", 0, 3);
            if (userChoice == 1) {
                printSeats();
            }
            else if (userChoice == 2) {
                while (true) {
                    printSeats();
                    int selectedRow = promptInteger(scanner, "Enter a row number", 0, getNumRow());
                    int selectedCol = promptInteger(scanner, "Enter a column number", 0, getNumCol());
                    if (checkSelection(selectedRow, selectedCol)) break;
                }
                System.out.println();
                numTicketsSold++;
            }
            else if (userChoice == 3) {
                printStatistics();
            }
        }
        while(userChoice != 0);
    }

    // print functions
    public void printSeats() {
        // print out cinema seating arrangement
        System.out.println("Cinema:");

        System.out.print("  ");
        for (int i = 0; i < numCol; i++) {
            System.out.printf("%d ", i+1);
        }
        System.out.println();
        for (int i = 0; i < numRow; i++) {
            System.out.printf("%d ", i+1);
            for (int j = 0; j < numCol; j++) {
                System.out.printf("%c ", seatingChart[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public boolean checkSelection(int selectedRow, int selectedCol) {
        if (updateSeatingChart(selectedRow, selectedCol)) {
            float ticketPrice = calculateTicketPrice(selectedRow);
            currentIncome += ticketPrice;
            System.out.printf("Ticket price: $%.2f%n", ticketPrice);
            return true;
        }
        else System.out.println("Seat already taken. Please choose another seat");
        return false;
    }

    public void printMenu() {
        System.out.println("""
                --------MENU--------
                 1. Show the seats
                 2. Buy a ticket
                 3. Statistics
                 0. Exit
                --------------------""");
    }

    public void printStatistics() {
        System.out.printf("------------STATISTICS------------%n" +
                "Number of purchased tickets: %d%n" +
                "Percentage: %.2f%%%n" +
                "Current income: $%.2f%n" +
                "Maximum income: $%.2f%n" +
                "----------------------------------%n",
                numTicketsSold,
                getPurchasePercentage(),
                currentIncome,
                getMaxProfit());
    }

    public void getUserInterface(Scanner s) {
        startUserInterface(s);
    }


    // getters and setters
    public int getNumRow() {
        return numRow;
    }

    public int getNumCol() {
        return numCol;
    }

    public float getMaxProfit() {
        return calculateMaxProfit();
    }

    private double getPurchasePercentage() {
        return ((double) numTicketsSold / (numRow * numCol)) * 100;
    }



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
        // creating cinema seats


        Scanner scanner = new Scanner(System.in);

        int numRow = promptInteger(scanner, "Enter the number of rows", 0, 9);
        int numCol = promptInteger(scanner, "Enter the number of columns", 0, 9);

        Theater room = new Theater(numRow, numCol);

        room.startUserInterface(scanner);



    }
}
