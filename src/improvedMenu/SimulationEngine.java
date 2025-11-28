package improvedMenu;

import java.util.*;
import java.io.FileWriter;

public class SimulationEngine {

    private ArrayList<Integer> allUsers;
    private Random rand;
    private Map<Integer, ArrayList<ScanEvent>> dayToScans;

    public SimulationEngine() {
        this.allUsers = new ArrayList<>();
        this.rand = new Random();
        this.dayToScans = new HashMap<>();
    }

    // Generate N fake users (IDs 0..N-1)
    public void generateUsers(int n) {
        for (int i = 0; i < n; i++) {
            allUsers.add(i);
        }
        System.out.println("Generated " + n + " users.");
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
        int count = Math.max(0, base + rand.nextInt(5) - 2);  // add small randomness

        for (int i = 0; i < count; i++) {
            int user = allUsers.get(rand.nextInt(allUsers.size()));
            intervalEvents.add(new ScanEvent(day, hour, minute, user));
        }

        return intervalEvents;
    }

    // Approximate meal-based base rates using only the HOUR
    private int getBaseRate(int hour) {
        // Breakfast: 7:30–10:00
        if (hour == 7 || hour == 8) return 5;   // early breakfast
        if (hour == 9) return 4;
        if (hour == 10) return 1;               // tapering

        // Lunch: 11:00–13:30 (approximate with 11–13)
        if (hour == 11 || hour == 12) return 25; // big lunch rush
        if (hour == 13) return 10;               // tapering into continuous

        // Continuous Service: 13:30–16:30 (approx 14–16)
        if (hour >= 14 && hour <= 16) return 2;  // light trickle

        // Dinner: 17:00–19:30
        if (hour == 17 || hour == 18) return 18; // dinner rush
        if (hour == 19) return 10;               // tapering

        // Outside those, but still within open hours
        return 0;
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
