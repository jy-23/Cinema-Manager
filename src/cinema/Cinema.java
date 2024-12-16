package cinema;

import java.util.Arrays;

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

    public static void main(String[] args) {
        // creating cinema seats
        int numRow = 7;
        int numCol = 8;
        char[][] seats = new char[numRow][numCol];
        for (char[] rowArray : seats){
            Arrays.fill(rowArray, 'S');
        }

        printSeats(seats);

    }
}
