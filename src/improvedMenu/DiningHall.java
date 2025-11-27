package improvedMenu;

import java.util.*;

public class DiningHall {

    private List<Integer> occupants;
    private List<String> menuItems;
    private Queue<Long> recentScans;
    private String hallName;

    public static final int NOTBUSY = 20;
    public static final int BUSY = 80;
    public static final int VERYBUSY = 120;

    public static final int NOTLONG = 20;
    public static final int LONG = 40;
    public static final int VERYLONG = 60;

    public DiningHall(String name) {
        this.hallName = name;
        this.occupants = new ArrayList<>();
        this.menuItems = new ArrayList<>();
        this.recentScans = new LinkedList<>();
    }

    // record that a user has entered the hall
    public void userEnter(int id) {
        occupants.add(id);
    }

    // list of user IDs currently inside
    public List<Integer> getOccupants() {
        return occupants;
    }

    // placeholder: later you'll compute this based on scans/occupancy
    public int getBusyness() {
        return 0;
    }

    // placeholder: later you'll estimate wait time from recentScans
    public int getWaitTime() {
        return 0;
    }

    // for now just returns all menu items joined together
    public String getMenu() {
        return String.join(", ", menuItems);
    }

    public void addMenuItem(String item) {
        menuItems.add(item);
    }

    public boolean removeMenuItem(String item) {
        return menuItems.remove(item);
    }

    // log a swipe/scan time (you'll use this later for wait time)
    public void recordScan(long timestampMillis) {
        recentScans.add(timestampMillis);
    }
}
