package improvedMenu;

import java.util.ArrayList;

public class SystemManager {

    private DiningHall diningHall;
    protected static ArrayList<String> menu; // to be accessed across package

    public SystemManager() {
        diningHall = new DiningHall();
    }

    // load the scan dataset
    public void loadScanData(String filename) {
        diningHall.loadData(filename);
    }

    // wrapper for occupancy
    public int getOccupancy(int day, int hour, int minute) {
        return diningHall.getOccupancy(day, hour, minute)[1];
    }

    // wrapper for wait time
    public int getWaitTime(int day, int hour, int minute) {
        int occ = diningHall.getOccupancy(day, hour, minute)[2];
        return diningHall.getWaitTime(occ);
    }
}
