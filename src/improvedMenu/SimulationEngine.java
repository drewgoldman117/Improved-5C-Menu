/**
 * @author Gavin Honey
 *
 */
//times must be inputted as minutes
package improvedMenu;

import java.io.FileWriter;
import java.util.*;

public class SimulationEngine {

    //TODO:change to public if we need to reference in other classes
    //TODO: need to add random favorite food application

    //static variables to reference days of week
    protected static final int MON = 1;
    protected static final int TUES = 2;
    protected static final int WED = 3;
    protected static final int TR = 4;
    protected static final int FRI = 5;
    protected static final int SAT = 6;
    protected static final int SUN = 7;

    //meal period integers
    protected static final int MEAL1 = 1;
    protected static final int MEAL2 = 2;
    protected static final int MEAL3 = 3;

    //----Times must be inputted as minutes, minute 0 intial opening time----//

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

    //setting the max amount of friends a user will be generated with
    private static final int FRIENDMAX = 15;

    //setting the max amount of favorite foods a user will be generated with
    private static final int FOODMAX = 5;

    //Dining hall max capacity (frary's per pomona website)
    protected static final int DININGHALLMAX = 325;

    //student population (pomona's per pomona website)
    protected static final int STUDENTPOP = 1766;

    //use to set the number of individual users to be created for the scan data
    protected static final int POOLCOUNT = 400;

    private Random rand; //random object
    private Map<Integer, ArrayList<ScanEvent>> scansPerDay; //keeps track of scans per day (final variable day #, scan event)
    protected ArrayList<User> userPool; //stores users

    public SimulationEngine() {
        this.rand = new Random();
        this.scansPerDay = new HashMap<>();
        this.userPool = new ArrayList<>();
    }

    //generates user pool, friends, and favorite foods to grab from for simulated scans
    public void initializeUsers(){

        ArrayList<Integer> idPool = new ArrayList<>();//stores user ids

        //populating id pool
        for (int i = 0; i < POOLCOUNT; i++){
            int id = rand.nextInt(STUDENTPOP + 1) + 1; //random id between 1 and student population max
            
            while (idPool.contains(id)){
                id = rand.nextInt(STUDENTPOP + 1) + 1; //generates new id if id is already contained
            }

            idPool.add(id);
        }

        //creating random user pool
        for (int i = 0; i < POOLCOUNT; i++){
            userPool.add(new User(idPool.get(i)));
        }

        //TODO: populate friends
        //TODO: need to add random favorite food application
        for (int i = 0; i < POOLCOUNT; i++){
            User currUser = userPool.get(i);

            //adding friends
            for (int f = 1; f <= rand.nextInt(FRIENDMAX); f++){
                
            }


            //adding favorite foods
            for (int f = 1; f <= rand.nextInt(FOODMAX); f++){

            }

        }


        

        
                
    }

    //Generates a week worth of scan data for each day and meal period, writes to a CSV for use
    public void generateData() {
        //iterates through all days
        for (int d = MON; d <= SUN; d++){

            //to store day's scans
            ArrayList<ScanEvent> dayScan = new ArrayList<>();
            ArrayList<User> mealPeriodUsers = new ArrayList<>();

            //iterates through times of operation (in 10 minute increments)
            for (int t = OPEN1; t <= CLOSE3; t += 10){
                if (mealPeriod(t) == -1){
                    mealPeriodUsers = new ArrayList<>(); //resets mealperiodids as it is a new meal period
                    t += 20; //adds 20 to t, plus the base 10 to increment by a 30 minute interval
                    continue;
                }

                int meal = mealPeriod(t);

                //helper to calculate # scans per 10 minute block
                int amount = generateScanAmount(t, meal);

                //for testing
                //System.out.println("Amount of scans for time " + t + " is " + amount);


                //helper to add the scans to the map
                addScans(dayScan, mealPeriodUsers, amount, t, d);
            }

            System.out.println("Day " + d + " completed\n");
        }

        saveToCSV("./data/generated_scans.csv");
    }

    //generates a random ID number for a particular scan, writes the ID and scan to the CSV
    /**
     * @param amount integer that specifies the amount of scans to be created
     * @param t the start time (minute) of the 10 minute period
     * @param d the integer day
     * @param mealPeriodUser ArrayList of users to track those who have already scanned in for the meal period
     * @param dayScan ArrayList of scan events for that day
     */
    private void addScans(ArrayList<ScanEvent> dayScan, ArrayList<User> mealPeriodUsers, int amount, int t, int d){
        for (int i = 0; i < amount; i++){

            //pulling random user from pool
            User randomUser = userPool.get(rand.nextInt(POOLCOUNT)); //random user index from pool
            
            //checks if user has already scanner
            while (mealPeriodUsers.contains(randomUser)){
                //pulls another user
                randomUser = userPool.get(rand.nextInt(POOLCOUNT));
            }
        
            mealPeriodUsers.add(randomUser); //adds user to known user base


            //generating random time section

            int time = rand.nextInt(10) + t; //generates a random time between current time and 9 minutes from that time

            //the hour is calculated by trunkating the minutes to hours, then adding 7 as the hour offset (opens at 0730)
            int hour = (time / 60) + 7;

            //the minute is calculated by taking the remaining minutes, offseting by 30 and adding remainer to hours
            int min = (time % 60) + 30;

            while (min >= 60){
                hour++;
                min -= 60;
            }

            //adds to day's scan events
            dayScan.add(new ScanEvent(d, hour, min, randomUser.getId()));
        }

        //links the created ids and the created scans and places them in the map for the given day
        scansPerDay.put(d, dayScan);

        //for testing
        //System.out.println(amount + " scans added to map\n");
    }

    /** calculates a random number of scans based on the time
     * @param t integer time in minutes
     * @param meal integer specifying the meal period (see class constants)
     */
    private int generateScanAmount(int t, int meal){
        int flowRate = 0;
        int offset = 0;
        //calculate base flow rate and determine range
        switch (meal){

            //From speaking to our peers, we estimate around 1/5 of people do not frequent breakfast, thus we will only take 4/5 of the flow rate
            //TODO: ask if we can do this
            case MEAL1: {
                flowRate = (DININGHALLMAX/(CLOSE1 - OPEN1)) * 4/5;

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
                flowRate = DININGHALLMAX/(CLOSE2 - OPEN2);

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
                flowRate = DININGHALLMAX/(CLOSE3 - OPEN3);

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

    /** checks if time is in operating hours
     * @param t int time input in minutes
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

    // Get all simulated data
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

        //testing generating data for the csv
        SimulationEngine se = new SimulationEngine();

        se.initializeUsers();

        se.generateData();
    }
}
