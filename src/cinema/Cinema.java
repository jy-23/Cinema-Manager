package cinema;

import java.util.ArrayList;
import java.util.Scanner;


public class Cinema {
    private final String name;
    private int numTheaters = 0;
    CustomerInterface cinemaInterface = new CustomerInterface();
    EmployeeInterface employeeInterface;
    ArrayList<Theater> theaterRooms = new ArrayList<>() ;
    Finance totalFinance;

    // use constructor to set up initial state of Cinema;
    Cinema(String name, Scanner s) {
        this.name = name;
        startEmployeeInterface(s);
        // add theater rooms until user quits

    }

    // TODO: needs to be in EmployeeInterface class
    private void startEmployeeInterface(Scanner s) {
        int userChoice;
        do {
            printMenu();
            userChoice = Main.promptInteger(s, "Please enter your choice", 0, 2);
            switch (userChoice) {
                case 1:
                    printTheaterList();
                    break;
                case 2:
                    addTheaterRooms(s);
                    break;
            }
        } while (userChoice != 0);
    }

    private void addTheaterRooms(Scanner s) {
        Theater newRoom = new Theater(s, ++numTheaters);
        theaterRooms.add(newRoom);
        System.out.printf("Added theater #%d%n", newRoom.getId());
    }


    private void printTheaterList() {
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.printf("%3s %10s %14s %8s %10s %15s%n",
                "ID",
                "CAPACITY",
                "TICKETS SOLD",
                "% SOLD",
                "PROFIT",
                "MAX PROFIT");
        for (Theater t : theaterRooms) {
            System.out.printf("%3d %10d %14d %8.2f %10.2f %15.2f%n",
                    t.getId(),
                    t.getCapacity(),
                    t.getNumTicketsSold(),
                    t.getPurchasePercentage(),
                    t.getCurrentIncome(),
                    t.getMaxProfit());
        }
        System.out.println("-----------------------------------------------------------------------------------------");
    }

    private void printMenu() {
        System.out.println("""
                --------MENU--------
                 1. View theater rooms
                 2. Add theater room
                 0. Exit
                --------------------""");
    }

    public void getCustomerInterface(Scanner s) {
        cinemaInterface.startCustomerInterface(s, theaterRooms);
    }

}
