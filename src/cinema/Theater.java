package cinema;

import java.util.Arrays;
import java.util.Scanner;

class Theater {
    private final int id;
    private final int numRow;
    private final int numCol;
    private char[][] seatingChart;
    private int numFrontRow;
    private float frontTicketPrice = 10f;
    private float backTicketPrice = 10f;

    Finance theaterFinance;

    private int numTicketsSold = 0;
    private float currentIncome = 0f;

    Theater(Scanner s, int id) {
        this.id = id;
        System.out.printf("Creating theater %d%n", id);
        numRow = Main.promptInteger(s, "--> Enter the number of rows", 0, 9);
        numCol = Main.promptInteger(s, "--> Enter the number of columns", 0, 9);

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


    // getters and setters
    public int getNumRow() { return numRow; }

    public int getNumCol() { return numCol; }

    public int getId() { return id; }

    public int getCapacity() { return numRow * numCol; }

    public int getNumTicketsSold() { return numTicketsSold; }

    public float getCurrentIncome() { return currentIncome; }

    public float getMaxProfit() {
        return calculateMaxProfit();
    }

    public double getPurchasePercentage() {
        return ((double) numTicketsSold / (numRow * numCol)) * 100;
    }

    public void incNumTicketsSoldBy(int num) {
        numTicketsSold += num;
    }

}
