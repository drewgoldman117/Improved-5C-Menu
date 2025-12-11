package improvedMenu;

import java.io.*;
import java.util.*;


/**
 * Represents a dining hall simulation that loads the sample scan events, determines how occupied the dining hall is and therefore estimates waiting times, and categorizes events by day.
 * 
 * @author @ryokasai19
 */
public class DiningHall {

    //list of all scanned events from the CSV file
    private ArrayList<ScanEvent> events;

    //this maps from day number to all ScanEvent(s) on that day
    private Map<Integer, ArrayList<ScanEvent>> dayToEvents;

    //queue which represents people in line, not yet served
    private Queue<ScanEvent> lineQueue;

    //priority queue ordering by leaving time of the people who have already sat down
    private PriorityQueue<ScanEvent> seatingPq;


    /**
     * DiningHall initializes events and dayToEvents as an arraylist and hashmap, but does not do anything yet.
     * This is to create a simulation object.
     */
    public DiningHall() {
        this.events = new ArrayList<>();
        this.dayToEvents = new HashMap<>();
    }

    /**
     * Loads scan event data from a CSV file.
     * The file contains day, minute, hour, userId and using this automatically assigns the meal period and stay durations.
     * @param filename the CSV file we're loading
     */
    public void loadData(String filename) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            br.readLine(); // skip header

            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                int day = Integer.parseInt(p[0]);
                int hour = Integer.parseInt(p[1]);
                int minute = Integer.parseInt(p[2]);
                int userId = Integer.parseInt(p[3]);

                ScanEvent e = new ScanEvent(day, hour, minute, userId);
                int mealPeriod;
                if (450 <= e.enteringTime && e.enteringTime <= 570) {
                    mealPeriod = SystemManager.MEAL1;
                } else if (660 <= e.enteringTime && e.enteringTime <= 810) {
                    mealPeriod = SystemManager.MEAL2;
                } else {
                    mealPeriod = SystemManager.MEAL3;
                }

                // added duration
                e.duration = timeSpent(mealPeriod);

                events.add(e);

                dayToEvents.computeIfAbsent(day, k -> new ArrayList<>()).add(e);
            }

            br.close();
            System.out.println("Loaded " + events.size() + " events.");

        } catch (Exception e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }

    /**
     * Gets all the ScanEvents for the given day
     *
     * @param day the given day
     * @return list of ScanEvents for that given day
     */
    public ArrayList<ScanEvent> getDayEvents(int day) {
        return dayToEvents.getOrDefault(day, new ArrayList<>());
    }

    /**
     * Returns random stay durations for dining hall visits. Minimum duration is 10 minutes
     * @param mealPeriod tells which meal period it is
     * @return estimated duration spent inside dining hall
     */
    public int timeSpent(int mealPeriod) {
        Random rand = new Random();
        double mean;
        double stdDev;

        // Return random stay time in the dining hall in minutes
        // Breakfast Average: 28.84, Standard Deviation: 26.86
        // Lunch Average: 38.92, Standard Deviation: 12.39
        // Dinner Average: 48.09, Standard Deviation: 15.71
        if (mealPeriod == SystemManager.MEAL1) {
            mean = 28.84;
            stdDev = 26.86;
        } else if (mealPeriod == SystemManager.MEAL2) {
            mean = 38.92;
            stdDev = 12.39;
        } else {
            mean = 48.09;
            stdDev = 15.71;
        }
        double result = (rand.nextGaussian() * stdDev) + mean;
        int rounded = Math.max(10, (int) Math.ceil(result));
        return rounded;
    }

    /**
     * Calculates according to the previous calculations how occupied a dining hall is at a given day
     * Returns an an array [line size, the number of people eating, wait time]
     * @param day what day it is
     * @param hour what hour it is
     * @param minute what minute it is
     * @return an an array [line size, the number of people eating, wait time]
     */
    public int[] getOccupancy(int day, int hour, int minute) {
        ArrayList<ScanEvent> list = dayToEvents.get(day);
        if (list == null)
            return null;

        this.lineQueue = new ArrayDeque<>();
        this.seatingPq = new PriorityQueue<>(
                (a, b) -> Integer.compare(a.leavingTime, b.leavingTime));

        // convert the input into a single value
        int targetTime = hour * 60 + minute;

        // the amount of time it takes to serve one person
        int serviceDuration = 1;
        int nextServerAvailable = 0;

        for (ScanEvent e : list) {
            // break if the person entered after the target time
            if (e.enteringTime > targetTime) {
                break;
            }
            // service start time for this person: if the line is empty, it's the
            // enteringTime, if there's a line, it will be nextServerAvailable
            int serviceStart = Math.max(e.enteringTime, nextServerAvailable);
            int serviceEnd = serviceStart + serviceDuration;
            nextServerAvailable = serviceEnd;

            // if the service end is after the target time, they're still in line
            if (serviceEnd > targetTime) {
                lineQueue.add(e);
            } else {
                e.leavingTime = serviceEnd + e.duration;
                // if the leavingTime is after the target time, they are still in the dining
                // hall.
                if (e.leavingTime > targetTime) {
                    seatingPq.add(e);
                }
            }
        }

        int waitTime = getWaitTime(lineQueue.size());
        // System.out.println("Estimated wait time: " + waitTime + " min");
        return new int[] { lineQueue.size(), seatingPq.size(), waitTime };
    }

    /**
     * Estimates wait time based on line volume (how many people are in line) - service duration assumed 1 minute
     * @param lineVolume the number of people in line
     * @return estimated wait time in minutes
     */
    public int getWaitTime(int lineVolume) {
        int serviceDuration = 1;
        return serviceDuration * lineVolume;
    }

    /**
     * Returns a priority queue of people currently seated
     * @return seating priority queue
     */
    public PriorityQueue<ScanEvent> getPQ(){
        return seatingPq;
    }

    /**
     * Returns a queue of people currently in line
     * @return line queue
     */
    public Queue<ScanEvent> getQ(){
        return lineQueue;
    }


    /**
     * Test method
     * Loads a sample data, generated_scans.csv, and prints occupancy details for day 1, hour 7, minute 50.
     * @param args
     */
    public static void main(String[] args) {
        DiningHall test = new DiningHall();
        test.loadData("data/generated_scans.csv");
        int[] result = test.getOccupancy(1, 7, 50);
        System.out.println("line size: " + result[0] + " seating size: " + result[1]);
        System.out.println("people in line: " + test.lineQueue);
        System.out.println("people in the hall: " + test.seatingPq);
    }

}
