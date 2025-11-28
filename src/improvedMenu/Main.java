package improvedMenu;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        SystemManager system = new SystemManager();

        // Load scan dataset
        system.loadScanData("data/generated_scans.csv");

        System.out.println("Welcome to the 5C Wait Time Estimator!");

        System.out.print("Enter day of week (1–7): ");
        int day = in.nextInt();

        System.out.print("Enter hour (0–23): ");
        int hour = in.nextInt();

        System.out.print("Enter minute (0,10,20,30,40,50): ");
        int minute = in.nextInt();

        int occupancy = system.getOccupancy(day, hour, minute);
        int wait = system.getWaitTime(day, hour, minute);

        System.out.println("\n--- Results ---");
        System.out.println("Estimated occupancy: " + occupancy);
        System.out.println("Estimated wait time: " + wait + " minutes");
    }
}
