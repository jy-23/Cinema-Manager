package cinema;

import java.util.Arrays;
import java.util.Scanner;

public class Cinema {
    static void printSeats(char[][] seats) {
        // print out cinema seating arrangement
        System.out.println("Cinema:");

        System.out.print("  ");
        for (int i = 0; i < seats[0].length; i++) {
            System.out.printf("%d ", i+1);
        }
        System.out.println();
        for (int i = 0; i < seats.length; i++) {
            System.out.printf("%d ", i+1);
            for (int j = 0; j < seats[i].length; j++) {
                System.out.printf("%c ", seats[i][j]);
            }
            System.out.println();
        }
    }

    static int calculateProfit(int row, int col) {
        int totalSeats = row * col;
        if (totalSeats <= 60) {
            return (totalSeats * 10);
        }
        int frontRows = row / 2;
        int backRows = row - frontRows;
        return ((frontRows * col * 10) + (backRows * col * 8));
    }

    public static void main(String[] args) {
        // creating cinema seats
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        int numRow = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int numCol = scanner.nextInt();

        char[][] seats = new char[numRow][numCol];
        for (char[] rowArray : seats){
            Arrays.fill(rowArray, 'S');
        }

        printSeats(seats);
        System.out.printf("%c%d",'$',calculateProfit(numRow, numCol));

    }
}
