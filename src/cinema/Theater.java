package cinema;

import java.util.Arrays;
import java.util.Scanner;

public class Theater {
    private final int numRow;
    private final int numCol;
    private char[][] seatingChart;
    private int numFrontRow;
    private int frontTicketPrice = 10;
    private int backTicketPrice = 10;

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

    public void printSeats() {
        // print out cinema seating arrangement
        System.out.println("Cinema:");

        System.out.print("  ");
        for (int i = 0; i < numRow; i++) {
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
    }

    private int calculateProfit() {
        int backRows = numRow - numFrontRow;
        int frontProfit = numFrontRow * numCol * frontTicketPrice;
        int backProfit = backRows * numCol * backTicketPrice;
        return frontProfit + backProfit;
    }


    // getters and setters
    public int getNumRow() {
        return numRow;
    }

    public int getNumCol() {
        return numCol;
    }

    /*public int getTicketPrice(int row) {
        return row <= numFrontRow ? frontTicketPrice : backTicketPrice;
    }*/

    public int getProfit() {
        return calculateProfit();
    }

    public static void main(String[] args) {
        // creating cinema seats
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of rows: ");
        int numRow = scanner.nextInt();
        if (numRow <= 0 || numRow > 9) {
            System.out.println("Invalid number of rows.\n" +
                    "Rows must be between 1 and 9 inclusive");
            System.exit(1);
        }

        System.out.println("Enter the number of seats in each row: ");
        int numCol = scanner.nextInt();
        if (numCol <= 0 || numCol > 9) {
            System.out.println("Invalid number of columns.\n" +
                    "Columns must be between 1 and 9 inclusive");
            System.exit(1);
        }

        Theater room = new Theater(numRow, numCol);




        room.printSeats();
        System.out.printf("Total income: %c%d%n",'$', room.getProfit());

        /*System.out.println("Enter a row number: ");
        int selectedRow = scanner.nextInt();
        System.out.println("Enter a row number: ");
        int selectedCol = scanner.nextInt();

        System.out.printf("Ticket price: $%d", calcTicketPrice());*/
    }
}
