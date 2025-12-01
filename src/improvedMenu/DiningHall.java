package improvedMenu;

import java.io.*;
import java.util.*;

public class DiningHall {

    private ArrayList<ScanEvent> events;
    private Map<Integer, ArrayList<ScanEvent>> dayToEvents;

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

                // added duration
                e.duration = timeSpent();

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
    // Average: 45min, standard deviation: 10, minimum 10 minutes
    public int timeSpent() {
        Random rand = new Random();
        double mean = 45.0;
        double stdDev = 10.0;
        double result = (rand.nextGaussian() * stdDev) + mean;
        int rounded = Math.max(10, (int) Math.ceil(result));
        return rounded;
    }

    // Count how many people are inside at a specific time, this logic does not make
    // any sense yet
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

        return new int[] { lineQueue.size(), seatingPq.size() };
    }

    // Need to fix this
    public int getWaitTime(int lineVolume) {
        int serviceDuration = 1;
        return serviceDuration * lineVolume;
    }

}
