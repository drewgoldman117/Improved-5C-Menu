package improvedMenu;

public class SystemManager {

    private DiningHall diningHall;

    public SystemManager() {
        diningHall = new DiningHall();
    }

    // load the scan dataset
    public void loadScanData(String filename) {
        diningHall.loadData(filename);
    }

    // wrapper for occupancy
    public int getOccupancy(int day, int hour, int minute) {
        return diningHall.getOccupancy(day, hour, minute);
    }

    // wrapper for wait time
    public int getWaitTime(int day, int hour, int minute) {
        int occ = diningHall.getOccupancy(day, hour, minute);
        return diningHall.getWaitTime(occ);
    }
}
