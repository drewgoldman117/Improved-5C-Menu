/**
 * @author @ghoney47 @ryokasai19
 */

package improvedMenu;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        String menupath = "./menu.csv";

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

        String exitString; //for exit logic

        //main user loop
        do {

                //day of week input
                System.out.print("Enter day of week (1-7): ");
                int day = in.nextInt();
                while (1 > day || day > 7) {
                    System.out.print("Input has to be an integer between 1-7 ");
                    day = in.nextInt();
                }

                //hour input
                System.out.print("Enter hour of day (0-23): ");
                int hour = in.nextInt();
                while (0 > hour || hour > 23) {
                    System.out.print("Input has to be an integer between 0-23 ");
                    hour = in.nextInt();
                }

                //minute input
                System.out.print("Enter minute 0-59: ");
                int minute = in.nextInt();
                System.out.println(minute);
                while (minute < 0 || minute >= 60) {
                    System.out.print("Input has to be 0-59");
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

                //creating menu parser object to access menu
                MenuParser mp = new MenuParser(menupath);


                //TODO: USER OUTPUT AND INDIVIDUAL USER STATS FOOD PART YET TO BE DONE
                do { //loops unless user wants to change the time/day
                    System.out.println("Potential IDs for sign in: ");

                    //TODO: should we print out only those within the dining hall? y/n?
                    //prints some known IDs to be used to sign in (the amount potential IDs can be changed at will)
                    for (int i = 0; i < 10; i++){
                        System.out.print("User "+ (i + 1) + ": " + se.userPool.get(i).getId() + ", ");
                    }
                    System.out.println(); //formatting line

                    try{
                        System.out.println("Enter ID to sign in: ");
                        User currUser = se.userMap.get(in.nextInt());

                        if (currUser == null){//user not found
                            throw new NullPointerException();
                        }

                        System.out.println("Favorite foods on the menu:");
                        //TODO: need to test this implementation
                        //use mealPeriod variable declared above
                        for (String s : mp.getItemsForDay(day, se.mealPeriod((hour * 60 + minute) - SystemManager.SEVENTHIRTYOFFSET))){ //gets meal items for that period, offset by minutes at 730
                            if (currUser.getFoods().contains(s)){
                                System.out.print(s + ", ");
                            }
                        }

                        System.out.println(); //formatting line


                        System.out.print("Friends currently in dining hall: ");
                        for (int f : currUser.getFriends()){
                            for (ScanEvent s : system.getDiningHall().getPQ()){
                                //if friend is occupying dining hall prints out
                                if (s.getUserId() == f){
                                    System.out.print("User " + s.getUserId() + ", ");
                                }
                            }

                            for (ScanEvent s : system.getDiningHall().getQ()){
                                //if friend is in line prints out
                                if (s.getUserId() == f){
                                    System.out.print("User " + s.getUserId() + ", ");
                                }
                            }
                        
                        }
                        
                    }catch (NullPointerException e) {
                        System.out.println("User not found");
                    } catch (InputMismatchException e){
                        System.out.println("Input must be an integer");
                    }

                    

                    System.out.println(); //formatting line
                    in.nextLine(); //clears input
                   
                    System.out.println("Do you want to change the date/time or quit (y/n)");
                    exitString = in.nextLine();


                    //checks valid input
                    while (!exitString.equalsIgnoreCase("y") && !exitString.equalsIgnoreCase("n")){
                        System.out.println("Invalid, please input y or n");
                        exitString = in.nextLine();
                    }

                } while (!exitString.equalsIgnoreCase("y")); //exits loop if exit string is yes

                    System.out.println("Quit (y/n)");
                    exitString = in.nextLine();

                    //checks valid input
                    while (!exitString.equalsIgnoreCase("y") && !exitString.equalsIgnoreCase("n")){
                        System.out.println("Invalid, please input y or n");
                        exitString = in.nextLine();
                    }

                }while (!exitString.equalsIgnoreCase("y")); //exits loop if exit string is yes
        

        in.close();
        
    }
}
