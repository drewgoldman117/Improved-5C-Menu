package improvedMenu;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        SystemManager system = new SystemManager();

        // Load scan dataset
        system.loadScanData("./data/generated_scans.csv");

        System.out.println("Welcome to the 5C Wait Time Estimator!");

        System.out.print("Enter day of week (1-7): ");
        int day = in.nextInt();
        while (1 > day || day > 7) {
            System.out.print("Input has to be an integer between 1-7 ");
            day = in.nextInt();
        }

        System.out.print("Enter hour (0-23): ");
        int hour = in.nextInt();
        while (0 > hour || hour > 23) {
            System.out.print("Input has to be an integer between 0-23 ");
            hour = in.nextInt();
        }

        System.out.print("Enter minute (0,10,20,30,40,50): ");
        int minute = in.nextInt();
        while (minute != 0 || minute != 10 || minute != 20 || minute != 30 || minute != 40 || minute != 50) {
            System.out.print("Input has to be (0,10,20,30,40,50)");
            minute = in.nextInt();
        }

        system.runSimulation(day, hour, minute);
        int occupancy = system.getOccupancy(day, hour, minute);
        int wait = system.getWaitTime(day, hour, minute);
        String peopleInLine = system.peopleInLine();
        String peopleEating = system.peopleEating();

        System.out.println("\n--- Results ---");
        System.out.println("Estimated occupancy: " + occupancy);
        System.out.println("Estimated wait time: " + wait + " minutes");
        System.out.println("People in line (not in order): " + peopleInLine);
        System.out.println("People eating: " + peopleEating);
    }
}
