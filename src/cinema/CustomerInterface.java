package cinema;

import java.util.ArrayList;
import java.util.Scanner;

class CustomerInterface {

    public void printMenu() {
        System.out.println("""
                --------MENU--------
                 1. Show the seats
                 2. Buy a ticket
                 0. Exit
                --------------------""");
    }

    private void createCustomerInterface(Scanner s, ArrayList<Theater> list) {
        int userChoice = 0;
        do {
            printMenu();
            userChoice = Main.promptInteger(s, "Please enter your choice", 0, 2);
            if (userChoice == 1) {
                //showSeatAvailability(list.get(0));
                printAvailableTheaters(list);
                System.out.println("Showing Seats");
            }
            else if (userChoice == 2) {
                printAvailableTheaters(list);
                int theaterChoice = Main.promptInteger(s, "Please enter a theater #", 0, list.size());
                if (theaterChoice == 0) continue;
                buyTicket(list.get(theaterChoice-1), s);
            }
        }
        while(userChoice != 0);
    }

    private int promptTheaterChoice(Scanner s, int num) {
        int userChoice = 0;


        return userChoice;
    }

    private void printAvailableTheaters(ArrayList<Theater> list) {
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.printf("%13s %13s%n", "THEATER #", "OPEN SEATS");
        for (Theater theater : list) {
            System.out.printf("%13d %13d%n", theater.getId(), theater.getCapacity() - theater.getNumTicketsSold());
        }
        System.out.println("-----------------------------------------------------------------------------------------");
    }

    private void showSeatAvailability(Theater t) {
        t.printSeats();
    }

    private void buyTicket(Theater currRoom, Scanner s) {
        while (true) {
            currRoom.printSeats();
            int selectedRow = Main.promptInteger(s, "Enter a row number", 0, currRoom.getNumRow());
            int selectedCol = Main.promptInteger(s, "Enter a column number", 0, currRoom.getNumCol());
            if (currRoom.checkSelection(selectedRow, selectedCol)) break;
        }
        System.out.println();
        currRoom.incNumTicketsSoldBy(1);
    }

    public void startCustomerInterface(Scanner s, ArrayList<Theater> list) {
        createCustomerInterface(s, list);
    }

}
