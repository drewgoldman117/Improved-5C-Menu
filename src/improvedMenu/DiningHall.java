package improvedMenu;

import java.util.*;
import java.io.*;

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


    // Count how many people are inside at a specific time, this logic does not make any sense yet
    public int getOccupancy(int day, int hour, int minute) {
        ArrayList<ScanEvent> list = dayToEvents.get(day);
        if (list == null) return 0;

        // Everyone who scanned BEFORE or AT this time is inside
        int count = 0;
        for (ScanEvent e : list) {
            if (e.hour < hour || (e.hour == hour && e.minute <= minute)) {
                count++;
            }
        }
        return count;
    }

    // Need to fix this
    public int getWaitTime(int occupancy) {
        if (occupancy < 50) return 0;
        if (occupancy < 150) return 5;
        if (occupancy < 250) return 10;
        return 15; // packed
    }
    
}
