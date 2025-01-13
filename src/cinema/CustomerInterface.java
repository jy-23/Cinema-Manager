package cinema;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;

class CustomerInterface {
    DbConnector db = null;

    private void printSearchMenu() {
        System.out.println("""
                --------MENU--------
                 1. Search by Movie
                 2. Search by Date
                 0. Exit
                --------------------""");
    }

    private void printTheaterMenu() {
        System.out.println("""
                --------MENU--------
                 1. Show Seats
                 2. Purchase Tickets
                 0. Exit
                --------------------""");
    }

    private void createCustomerInterface(Scanner s) {
        int userChoice = 0;
        int showtimeId = -1;
        do {
            printSearchMenu();
            userChoice = Main.promptInteger(s, "Please enter your choice", 0, 2);
            if (userChoice == 1) {
                showtimeId = promptDateChoice(s);
            }
            else if (userChoice == 2) {
                showtimeId = promptMovieChoice(s);
            }
            while (userChoice != 0) {
                printTheaterMenu();
                Theater theater = db.getTheater(showtimeId);
                userChoice = Main.promptInteger(s, "Enter your choice", 0, 2);
                if (userChoice == 1) {
                    // view seats
                    int backKey = -1;
                    System.out.println(showtimeId);

                    theater.printSeats();
                    while(backKey != 0) {
                        backKey = Main.promptInteger(s, "Press 0 to exit", 0, 0);
                    }

                }
                else if (userChoice == 2) {
                    // purchase tickets
                    int numTickets = Main.promptInteger(s, "How many tickets would you like to purchase? (Max. 10)", 1, 10);
                    for (int i = 0; i < numTickets; i++) {
                        buyTicket(theater, s);
                    }
                    printPurchaseSummary(theater, s);
                    while (true) {
                        int confirmation = Main.promptInteger(s, "Enter 1 to confirm, 0 to cancel", 0, 1);
                        if (confirmation == 0) {
                            System.out.println("Transaction cancelled. Taking you back to main menu");
                            break;
                        }
                        else if (confirmation == 1) {
                            db.updateSeatingChart(theater.getSeatingChart(), showtimeId);
                            System.out.println("Transaction complete. Thank you!");
                            break;
                            // increase total income of cinema
                        }
                    }
                    break;
                }
            }

        }
        while(userChoice != 0);
    }

    private int promptDateChoice(Scanner s) {
        System.out.println("Available Dates");
        System.out.println("---------------------------------");
        // get sql dates from db
        ArrayList<Date> dates = db.getDates();
        int userChoice = Main.promptInteger(s, "Enter your choice", 1, dates.size());
        ArrayList<Integer> showtimeId = db.getMoviesByDate(dates.get(userChoice - 1));
        userChoice = Main.promptInteger(s, "Enter your choice", 1, showtimeId.size());
        return showtimeId.get(userChoice);
    }

    private int promptMovieChoice(Scanner s) {
        ArrayList<String> movieList = db.getMovies();
        int userChoice = Main.promptInteger(s, "Enter your movie choice", 1, movieList.size());
        String movieTitle = movieList.get(userChoice-1);
        ArrayList<Integer> showtimeId = db.getMovieShowings(movieTitle);
        userChoice = Main.promptInteger(s, "Enter your choice", 1, showtimeId.size());
        return showtimeId.get(userChoice);
    }

    private int promptTheaterChoice(Scanner s, int num) {
        int userChoice = 0;
        return userChoice;
    }
    /*
    private void printAvailableTheaters(ArrayList<Theater> list) {
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.printf("%13s %13s%n", "THEATER #", "OPEN SEATS");
        for (Theater theater : list) {
            System.out.printf("%13d %13d%n", theater.getId(), theater.getCapacity() - theater.getNumTicketsSold());
        }
        System.out.println("-----------------------------------------------------------------------------------------");
    }
    */
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

    private void printPurchaseSummary(Theater currRoom, Scanner s) {
        System.out.println("Purchase Summary Page");
        System.out.println("-----------------------");
        System.out.printf("# of tickets: %d%n",currRoom.getNumTicketsSold());
        System.out.printf("Total cost: $%.2f%n", currRoom.getCurrentIncome());

    }

    public void startCustomerInterface(Scanner s, DbConnector db) {
        this.db = db;
        createCustomerInterface(s);
    }
}
