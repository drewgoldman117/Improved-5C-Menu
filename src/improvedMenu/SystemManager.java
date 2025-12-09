//TODO: add authors
//TODO: add constants to avoid magic numbers

/**
 * @author @ghoney47 @drewgoldman117
 */

package improvedMenu;

import java.util.ArrayList;

public class SystemManager {

    private DiningHall diningHall;
    protected static ArrayList<String> fullMenu; // to be accessed across package
    // to keep track latest data
    private int[] occupancyInfo = { 0, 0, 0 };

    //package constants
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
    protected static final int OPEN1 = 0;

    //closing time for meal period 1 in minutes (09:30)
    protected static final int CLOSE1 = 120;

    //opening time for meal period 2 in minutes (11:00)
    protected static final int OPEN2 = 210;

    //closing time for meal period 2 in minutes (13:30)
    protected static final int CLOSE2 = 360;

    //opening time for meal period 3 in minutes (17:00)
    protected static final int OPEN3 = 570;

    //closing time for meal period 3 in minutes (19:30)
    protected static final int CLOSE3 = 720;

    //Dining hall max capacity (frary's per pomona website)
    protected static final int DININGHALLMAX = 325;

    //student population (pomona's per pomona website)
    protected static final int STUDENTPOP = 1766;

    public SystemManager() {
        diningHall = new DiningHall();
    }

    public void loadFullMenu (String menuPath){
        //TODO: call menu parser here!!!
    }

    // load the scan dataset
    public void loadScanData(String filename) {
        diningHall.loadData(filename);
    }

    // run simulation and update occupancyInfo
    public void runSimulation(int day, int hour, int minute) {
        this.occupancyInfo = diningHall.getOccupancy(day, hour, minute);
    }

    //returns dining hall object in use
    public DiningHall getDiningHall(){
        return diningHall;
    }

    // wrapper for occupancy
    public int getOccupancy(int day, int hour, int minute) {
        return occupancyInfo[1];
    }

    // wrapper for wait time
    public int getWaitTime(int day, int hour, int minute) {
        return occupancyInfo[2];
    }

    public String peopleInLine() {
        return diningHall.getQ().toString();
    }

    public String peopleEating() {
        return diningHall.getPQ().toString();
    }
}
