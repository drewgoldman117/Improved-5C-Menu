/**
 * @author Gavin Honey
 * @author TODO: (add more names!!!) 
 */
//times must be inputted as valid 24hr times TODO: make error catching
package improvedMenu;

import java.io.FileWriter;
import java.util.*;

public class SimulationEngine {

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

    private ArrayList<Integer> allUsers; //arraylist of in use ids 
    private Random rand; //random object
    private Map<Integer, ArrayList<ScanEvent>> dayToScans; //keeps track of scans per day (final variable day #, scan event)

    public SimulationEngine() {
        this.allUsers = new ArrayList<>();
        this.rand = new Random();
        this.dayToScans = new HashMap<>();
    }

    //Generates a week worth of scan data for each day and meal period, writes to a CSV for use
    public void generateData() {
        //iterates through all days
        for (int i = MON; i <= SUN; i++){

            //iterates through times of operation (in 10 minute increments)
            for (int t = OPEN1; t <= CLOSE3; t = (t + 10) % 60){
                if (inOperatingHours(t) == -1){
                    t += 20; //adds 20 to t, plus the base 10 to increment by a 30 minute interval
                    continue;
                }

                //helper to calculate # scans per 10 minute block

                //helper to add the scans to the map


            }

        }
    }

    //calculates a random number of scans based on the time
    /**
     * @param t integer time in 24hr
     * @param meal integer specifying the meal period (see class constants)
     */
    private void int generateScanAmount(int t, int meal){
        int flowRate;
        int offset;
        int amount;
        //calculate base flow rate and determine range
        switch (meal){

            //we assume breakfast (meal 1) will not fill the dining hall, thus max capacity of 250 instead of 325
            case MEAL1: {
                flowRate = (CLOSE1 - OPEN1)/250;

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


            }

            case MEAL2: {
                flowRate = (CLOSE2 - OPEN2)/FRARYMAX;

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

            }

            case MEAL3: {
                flowRate = (CLOSE3 - OPEN3)/FRARYMAX;

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

            }


            //TODO: randomize with the offset!!!! see notes

        }


    }

    //checks if time is in operating hours
    /**
     * @param t int time input in 24hr time HH:MM
     * @return int that specifies the meal period
     */
    private int inOperatingHours(int time){
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

    // Simulate a full day broken into 10-minute intervals
    public void simulateDay(int day) {
        ArrayList<ScanEvent> events = new ArrayList<>();

        // 24 hours * 6 blocks per hour = 144 blocks
        for (int block = 0; block < 144; block++) {
            events.addAll(simulateInterval(day, block));
        }

        dayToScans.put(day, events);
    }

    // Simulate one 10-minute interval
    private ArrayList<ScanEvent> simulateInterval(int day, int block) {
        ArrayList<ScanEvent> intervalEvents = new ArrayList<>();

        int hour = block / 6;            // 0–23
        int minute = (block % 6) * 10;   // 0,10,20,30,40,50

        // Base open hours: 7:30 AM – 7:30 PM
        boolean isOpen =
            (hour > 7 && hour < 19) ||          
            (hour == 7 && minute >= 30) ||      
            (hour == 19 && minute <= 30);

        // CLOSED WINDOW #1: 10:00–11:00
        if (hour == 10) {
            isOpen = false;
        }

        // CLOSED WINDOW #2: 16:30–17:00
        if (hour == 16 && minute >= 30) {
            isOpen = false;
        }

        if (!isOpen) {
            // dining hall is closed -> no scans
            return intervalEvents;
        }

        int base = getBaseRate(hour);
        int count = rand.nextInt(5);  
        
        for (int i = 0; i < count; i++) {
            int user = allUsers.get(rand.nextInt(allUsers.size()));
            intervalEvents.add(new ScanEvent(day, hour, minute, user));
        }

        return intervalEvents;
    }

    // Approximate meal-based base rates using only the HOUR
    private int getBaseRate(int hour) {
        // Breakfast: 7:30–10:00
                   //less and less people

        // Lunch: 11:00–13:30 (approximate with 11–13)
           
        // Continuous Service: 13:30–16:30 (approx 14–16)
    

        // Dinner: 17:00–19:30
         
        return 5;
    }

    // Get all simulated data (if you want to use it directly in memory)
    public Map<Integer, ArrayList<ScanEvent>> getAllScanData() {
        return dayToScans;
    }

    // Save ALL simulated days to a CSV
    public void saveToCSV(String filename) {
        try {
            FileWriter writer = new FileWriter(filename);
            writer.write("day,hour,minute,userId\n");

            for (int day : dayToScans.keySet()) {
                for (ScanEvent e : dayToScans.get(day)) {
                    writer.write(e.day + "," + e.hour + "," + e.minute + "," + e.userId + "\n");
                }
            }

            writer.close();
            System.out.println("Saved dataset to: " + filename);

        } catch (Exception e) {
            System.out.println("Error saving CSV: " + e.getMessage());
        }
    }
}
