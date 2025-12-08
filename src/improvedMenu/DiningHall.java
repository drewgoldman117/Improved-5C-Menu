package improvedMenu;

import java.io.*;
import java.util.*;

public class DiningHall {

    private ArrayList<ScanEvent> events;
    private Map<Integer, ArrayList<ScanEvent>> dayToEvents;
    // meal period integers
    protected static final int MEAL1 = 1;
    protected static final int MEAL2 = 2;
    protected static final int MEAL3 = 3;

    public DiningHall() {
        this.events = new ArrayList<>();
        this.dayToEvents = new HashMap<>();
    }

    // Load CSV scan data
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
                    mealPeriod = MEAL1;
                } else if (660 <= e.enteringTime && e.enteringTime <= 810) {
                    mealPeriod = MEAL2;
                } else {
                    mealPeriod = MEAL3;
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

    // Return all ScanEvents on a specific day
    public ArrayList<ScanEvent> getDayEvents(int day) {
        return dayToEvents.getOrDefault(day, new ArrayList<>());
    }

    // Return random stay time in the dining hall in minutes
    // Breakfast Average: 28.84, Standard Deviation: 26.86
    // Lunch Average: 38.92, Standard Deviation: 12.39
    // Dinner Average: 48.09, Standard Deviation: 15.71
    public int timeSpent(int mealPeriod) {
        Random rand = new Random();
        double mean;
        double stdDev;
        if (mealPeriod == MEAL1) {
            mean = 28.84;
            stdDev = 26.86;
        } else if (mealPeriod == MEAL2) {
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

    // Returns [line size, the number of people eating, wait time]
    public int[] getOccupancy(int day, int hour, int minute) {
        ArrayList<ScanEvent> list = dayToEvents.get(day);
        if (list == null)
            return null;

        Queue<ScanEvent> lineQueue = new ArrayDeque<>();
        PriorityQueue<ScanEvent> seatingPq = new PriorityQueue<>(
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
        // the time is their entrance time
        System.out.println("People currently in line (not in order): " + lineQueue);
        System.out.println("People seated: " + seatingPq);
        int waitTime = getWaitTime(lineQueue.size());
        System.out.println("Estimated wait time: " + waitTime + "min");
        return new int[] { lineQueue.size(), seatingPq.size(), waitTime };
    }

    // Need to fix this
    public int getWaitTime(int lineVolume) {
        int serviceDuration = 1;
        return serviceDuration * lineVolume;
    }

    public static void main(String[] args) {
        DiningHall test = new DiningHall();
        test.loadData("data/generated_scans.csv");
        int[] result = test.getOccupancy(1, 7, 50);
        System.out.println("line size: " + result[0] + " seating size: " + result[1]);

    }

}
