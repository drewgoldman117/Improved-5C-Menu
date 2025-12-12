/**
 * @authors @ghoney47 @drewgoldman117 @Uras1717
 */

package improvedMenu;

/**
 * This class mostly interacts with the DiningHall class to manage the overall system.
 * It loads in the scan data, runs simulations and thus provides occupancy info and wait time info.
 *
 * @authors @ghoney47 @drewgoldman117 @Uras1717
 */
public class SystemManager {

    private DiningHall diningHall;
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

    //----Times must be inputted as minutes here, minute 0 intial opening time----//

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

    //07:30 minute equivalent for offsetting regular time to be minutes
    protected static final int OPENTIMEOFFSET = 450;


    /**
     * Constructing a new SystemManager object
     */
    public SystemManager() {
        diningHall = new DiningHall();
    }

    /**
     * Loading the scan event data from the CSV file
     * @param filename path to CSV file
     */
    public void loadScanData(String filename) {
        diningHall.loadData(filename);
    }

    /**
     * This method takes in day, hour, minute, and runs occupancy simulation for these timestamps.
     * @param day day
     * @param hour hour
     * @param minute minute
     */
    public void runSimulation(int day, int hour, int minute) {
        this.occupancyInfo = diningHall.getOccupancy(day, hour, minute);
    }

    /**
     * @return dining hall object in use
     */
    public DiningHall getDiningHall(){
        return diningHall;
    }

    /**
     * This method is the wrapper for occupancy
     * @param day day
     * @param hour hour
     * @param minute minute
     * @return number of peiple in the dining hall at that timestamp
     */
    public int getOccupancy(int day, int hour, int minute) {
        return occupancyInfo[1];
    }

    /**
     * This method is the wrapper for wait time
     * @param day day
     * @param hour hour
     * @param minute minute
     * @return wait time in minutes
     */
    public int getWaitTime(int day, int hour, int minute) {
        return occupancyInfo[2];
    }

    /**
     * @return a str of everyone in line
     */
    public String peopleInLine() {
        return diningHall.getQ().toString();
    }

    /**
     * @return a str of everyone eating
     */
    public String peopleEating() {
        return diningHall.getPQ().toString();
    }
}
