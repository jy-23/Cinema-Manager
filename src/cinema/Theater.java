package cinema;

import java.sql.*;
import java.util.Arrays;
import java.util.Scanner;

public class Theater {
    private Statement statement;
    private int numRow;
    private int numCol;
    //private char[][] seatingChart;
    private char[] seatingChart;
    private int numFrontRow;
    private float frontTicketPrice = 10f;
    private float backTicketPrice = 10f;

    Finance theaterFinance;

    private int numTicketsSold = 0;
    private float currentIncome = 0f;

    public void initialize(int row, int col, char[] seatingChart) {
        this.numRow = row;
        this.numCol = col;

        int totalSeats = numRow * numCol;
        if (totalSeats > 60) {
            backTicketPrice = 8;
            numFrontRow = numRow / 2;
        }

        this.seatingChart = seatingChart;
        /*int seatIndex = 0;
        for (char[] rowArray : seatingChart){

            Arrays.fill(rowArray, seating);
        }*/
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
        /*if (seatingChart[selectedRow - 1][selectedCol - 1] != '1') {
            seatingChart[selectedRow - 1][selectedCol - 1] = '1';
            return true; // success in updating
        }*/
        int seatIndex = (selectedRow - 1) * numCol + (selectedCol -1);
        if (seatingChart[seatIndex] != '1') {
            seatingChart[seatIndex] = '1';
            return true;
        }
        return false;
    }


    // print functions
    public void printSeats() {
        // print out cinema seating arrangement
        System.out.println("Seating Chart:");

        System.out.print("  ");
        for (int i = 0; i < numCol; i++) {
            System.out.printf("%d ", i+1);
        }
        System.out.println();
        for (int i = 0; i < numRow; i++) {
            System.out.printf("%d ", i+1);
            for (int j = 0; j < numCol; j++) {
                //System.out.printf("%c ", seatingChart[i][j]);
                System.out.printf("%c ", (seatingChart[i*numRow + j] == '0') ? 'S' : 'B');
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

    public char[] getSeatingChart() { return seatingChart; }

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
