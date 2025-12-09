/**
 * @author @ghoney47 @ryokasai19
 */

package improvedMenu;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        String menupath = "./frary_menu_extended.csv";

        //generates new scan data and users
        SimulationEngine se = new SimulationEngine();
        se.initializeUsers(menupath);
        se.generateData();

        //input and system manager initialization
        Scanner in = new Scanner(System.in);
        SystemManager system = new SystemManager();

        // Load scan dataset
        system.loadScanData("./data/generated_scans.csv");

        System.out.println("Welcome to the 5C Wait Time Estimator!");

        //main user loop
        while (true){
            //day of week input
            System.out.print("Enter day of week (1-7): ");
            int day = in.nextInt();
            while (1 > day || day > 7) {
                System.out.print("Input has to be an integer between 1-7 ");
                day = in.nextInt();
            }

            //hour input
            System.out.print("Enter hour (0-23): ");
            int hour = in.nextInt();
            while (0 > hour || hour > 23) {
                System.out.print("Input has to be an integer between 0-23 ");
                hour = in.nextInt();
            }

            //minute input
            System.out.print("Enter minute (0,10,20,30,40,50): ");
            int minute = in.nextInt();
            System.out.println(minute);
            while (minute != 0 && minute != 10 && minute != 20 && minute != 30 && minute != 40 && minute != 50) {
                System.out.print("Input has to be (0,10,20,30,40,50)");
                minute = in.nextInt();
            }

            //getting occupancy and wait time stats from given input and prints
            system.runSimulation(day, hour, minute);
            int occupancy = system.getOccupancy(day, hour, minute);
            int wait = system.getWaitTime(day, hour, minute);
            String peopleInLine = system.peopleInLine();
            String peopleEating = system.peopleEating();
            int mealPeriod = se.mealPeriod(hour * 60 + minute); 

            System.out.println("\n--- Results ---");
            System.out.println("Occupancy: " + occupancy);
            System.out.println("Estimated wait time: " + wait + " minutes");
            System.out.println("People in line (not in order): " + peopleInLine);
            System.out.println("People eating: " + peopleEating);

            //TODO: USER OUTPUT AND INDIVIDUAL USER STATS
            while (true) { //loops unless user wants to change the time/day
                System.out.println("Potential IDs for sign in: ");

                //prints some known IDs to be used to sign in (the amount potential IDs can be changed at will)
                for (int i = 0; i < 10; i++){
                    System.out.print("User "+ (i + 1) + ": " + se.userPool.get(i).getId() + ", ");
                }
                System.out.println(); //formatting line

                try {
                    System.out.println("Enter ID to sign in: ");
                    User currUser = se.userMap.get(in.nextInt());

                    System.out.println("Favorite foods on the menu:");
                    //TODO: impelment w/ known foods at a current meal period need menu parser
                    //use mealPeriod variable declared above


                    System.out.println("Friends currently in dining hall: ");
                    for (int f : currUser.getFriends()){
                        for (ScanEvent s : system.getDiningHall().getPQ()){
                            //if friend is in line, or occupying dining hall, prints out
                            if (system.getDiningHall().getPQ().contains(s) || system.getDiningHall().getQ().contains(s)){
                                System.out.print("User " + s.getUserId() + ", ");
                            }
                        }
                    }

                } catch (NullPointerException e){
                    System.out.println("User does not exist");
                    continue;
                } 

                System.out.println(); //formatting line
                System.out.println("Do you want to change the date/time or quit (y/n)");

                //user wants to change date, breaks out of inner loop
                if (in.nextLine().toLowerCase().equals("y")){
                    break;
                }
            }
            
            System.out.println("Quit (y/n)");
            String exitString = in.nextLine();
            while (true) { //exits when input is y or n
                if (exitString.toLowerCase().equals("y") || exitString.toLowerCase().equals("n")){
                    break;
                }
                System.out.println("Please enter 'y' or 'n':");
            }

            //user terminates loop and ends program
            if (exitString.toLowerCase().equals("y")){
                break;
            }            
        }

        in.close();
        
    }
}
