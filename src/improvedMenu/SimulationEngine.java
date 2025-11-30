/**
 * @author Gavin Honey
 * @author TODO: (add more names!!!) 
 */
//times must be inputted as valid 24hr times TODO: make error catching
package improvedMenu;

import java.io.FileWriter;
import java.util.*;

public class SimulationEngine {

    //TODO:change to public if we need to reference in other classes

    //static variables to reference days of week
    private static final int MON = 1;
    private static final int TUES = 2;
    private static final int WED = 3;
    private static final int TR = 4;
    private static final int FRI = 5;
    private static final int SAT = 6;
    private static final int SUN = 7;

    //meal period integers
    private static final int MEAL1 = 1;
    private static final int MEAL2 = 2;
    private static final int MEAL3 = 3;

    //opening time for meal period 1 in minutes (07:30)
    private static final int OPEN1 = 0;

    //closing time for meal period 1 in minutes (09:30)
    private static final int CLOSE1 = 120;

    //opening time for meal period 2 in minutes (11:00)
    private static final int OPEN2 = 210;

    //closing time for meal period 2 in minutes (13:30)
    private static final int CLOSE2 = 360;

    //opening time for meal period 3 in minutes (17:00)
    private static final int OPEN3 = 570;

    //closing time for meal period 3 in minutes (19:30)
    private static final int CLOSE3 = 720;

    //busyness offset
    private static final int NOTBUSY = 10;
    private static final int BUSY = 15;
    private static final int VBUSY = 25;



    //frary max capacity (per pomona website)
    private static final int FRARYMAX = 325;

    //student population of pomona (per pomona website)
    private static final int STUDENTPOP = 1766;

    private ArrayList<Integer> allUsers; //arraylist of in use ids 
    private Random rand; //random object
    private Map<Integer, ArrayList<ScanEvent>> scansPerDay; //keeps track of scans per day (final variable day #, scan event)

    public SimulationEngine() {
        this.allUsers = new ArrayList<>();
        this.rand = new Random();
        this.scansPerDay = new HashMap<>();
    }

    //Generates a week worth of scan data for each day and meal period, writes to a CSV for use
    public void generateData() {
        //iterates through all days
        for (int d = MON; d <= SUN; d++){

            //to store day's scans
            ArrayList<ScanEvent> dayScan = new ArrayList<>();
            ArrayList<Integer> mealPeriodIds = new ArrayList<>();

            //iterates through times of operation (in 10 minute increments)
            for (int t = OPEN1; t <= CLOSE3; t += 10){
                if (mealPeriod(t) == -1){
                    mealPeriodIds = new ArrayList<>(); //resets mealperiodids as it is a new meal period
                    t += 20; //adds 20 to t, plus the base 10 to increment by a 30 minute interval
                    continue;
                }

                int meal = mealPeriod(t);

                //helper to calculate # scans per 10 minute block
                int amount = generateScanAmount(t, meal);

                System.out.println("Amount of scans for time " + t + " is " + amount);


                //helper to add the scans to the map
                addScans(dayScan, mealPeriodIds, amount, t, d);
            }

            System.out.println("Day " + d + " completed\n\n");
        }

        saveToCSV("./data/generated_scans.csv");
    }

    //generates a random ID number for a particular scan, writes the ID and scan to the CSV
    /**
     * @param amount integer that specifies the amount of scans to be created
     * @param t the start time (minute) of the 10 minute period
     * @param d the integer day
     */
    private void addScans(ArrayList<ScanEvent> dayScan, ArrayList<Integer> mealPeriodScannedIds, int amount, int t, int d){

        for (int i = 0; i < amount; i++){

            //generating random id section
            int id = rand.nextInt(STUDENTPOP + 1) + 1; //random id between 1 and student population max
            
            while (mealPeriodScannedIds.contains(id)){
                //generates until it is a unique id
                id = rand.nextInt(STUDENTPOP + 1) + 1;
            }
        
            mealPeriodScannedIds.add(id); //adds user to known user base


            //generating random time section

            int time = rand.nextInt(9) + t; //generates a random time between current time and 9 minutes from that time

            //the hour is calculated by trunkating the minutes to hours, then adding 7 as the hour offset (opens at 0730)
            int hour = (time / 60) + 7;

            //the minute is calculated by taking the remaining minutes, offseting by 30 and adding remainer to hours
            int min = (time % 60) + 30;

            if (min >= 60){
                hour++;
                min -= 60;
            }


            dayScan.add(new ScanEvent(d, hour, min, id));
        }

        //links the created ids and the created scans and places them in the map for the given day
        scansPerDay.put(d, dayScan);
        System.out.println(amount + " scans added to map");
    }

    //calculates a random number of scans based on the time
    /**
     * @param t integer time in 24hr
     * @param meal integer specifying the meal period (see class constants)
     */
    private int generateScanAmount(int t, int meal){
        int flowRate = 0;
        int offset = 0;
        //calculate base flow rate and determine range
        switch (meal){

            //we assume breakfast (meal 1) will not fill the dining hall, thus max capacity of 250 instead of 325
            case MEAL1: {
                flowRate = 250/(CLOSE1 - OPEN1);

                //if within the first 30 minutes of a meal period, set offset to NOTBUSY
                if (t <= OPEN1 + 30){
                    offset = NOTBUSY;

                //if within the last 30 minutes of a meal period, set offset to BUSY
                } else if (t >= CLOSE1 - 30){
                    offset = BUSY;

                //otherwise, middle period is most busy, set offset to VBUSY
                } else {
                    offset = VBUSY;
                }

                break;

            }

            case MEAL2: {
                flowRate = FRARYMAX/(CLOSE2 - OPEN2);

                //if within the first 30 minutes of a meal period, set offset to NOTBUSY
                if (t <= OPEN2 + 30){
                    offset = NOTBUSY;

                //if within the last 30 minutes of a meal period, set offset to BUSY
                } else if (t >= CLOSE2 - 30){
                    offset = BUSY;

                //otherwise, middle period is most busy, set offset to VBUSY
                } else {
                    offset = VBUSY;
                }
                break;
            }

            case MEAL3: {
                flowRate = FRARYMAX/(CLOSE3 - OPEN3);

                //if within the first 30 minutes of a meal period, set offset to NOTBUSY
                if (t <= OPEN3 + 30){
                    offset = NOTBUSY;

                //if within the last 30 minutes of a meal period, set offset to BUSY
                } else if (t >= CLOSE3 - 30){
                    offset = BUSY;

                //otherwise, middle period is most busy, set offset to VBUSY
                } else {
                    offset = VBUSY;
                }
                break;
            }
        }

        offset = rand.nextInt(offset+1); //selects random within the offset

        if (rand.nextBoolean()){
            //randomly add or subtracts from base flow
            int amount = flowRate + (offset * -1);

            //if the amount is negative, defaults to flowrate
            if (amount < 0){
                return flowRate;
            }

            //otherwise return altered amount
            return amount;
        }

        return flowRate + offset;
    }

    //checks if time is in operating hours
    /**
     * @param t int time input in 24hr time HH:MM
     * @return int that specifies the meal period
     */
    private int mealPeriod(int time){
        //checks if time lies within an operation time

        //in between period 1 and 2
        if (time >= OPEN1 && time <= CLOSE1){
            return MEAL1;
        }

        //in between period 2 and 3
        if (time <= CLOSE2 && time >= OPEN2){
            return MEAL2;
        }

        if (time >= OPEN3 && time <= CLOSE3){
            return MEAL3;
        }

        //outside of meal period
        return -1;
    }

    // Get all simulated data (if you want to use it directly in memory)
    public Map<Integer, ArrayList<ScanEvent>> getAllScanData() {
        return scansPerDay;
    }

    // Save ALL simulated days to a CSV
    public void saveToCSV(String filename) {
        try {
            FileWriter writer = new FileWriter(filename);
            writer.write("day,hour,minute,userId\n");

            for (int day : scansPerDay.keySet()) {
                for (ScanEvent e : scansPerDay.get(day)) {
                    writer.write(e.day + "," + e.hour + "," + e.minute + "," + e.userId + "\n");
                }
            }

            writer.close();
            System.out.println("Saved dataset to: " + filename);

        } catch (Exception e) {
            System.out.println("Error saving CSV: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SimulationEngine se = new SimulationEngine();

        se.generateData();
    }
}
