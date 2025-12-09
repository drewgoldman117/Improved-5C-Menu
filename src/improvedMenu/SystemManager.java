//TODO: add authors
//TODO: add constants to avoid magic numbers

/**
 * @author @ghoney47 @drewgoldman117
 */

package improvedMenu;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class SystemManager {

    private DiningHall diningHall;
    protected static ArrayList<String> fullMenu; // to be accessed across package
    // to keep track latest data
    private int[] occupancyInfo = { 0, 0, 0 };

    public SystemManager() {
        diningHall = new DiningHall();
    }

    public void loadFullMenu (String menuPath){
        try{
            BufferedReader br = new BufferedReader(new FileReader(menuPath));
        } catch (FileNotFoundException e){
            System.out.println("Menu file not found");
        }
    }

    // load the scan dataset
    public void loadScanData(String filename) {
        diningHall.loadData(filename);
    }

    // run simulation and update occupancyInfo
    public void runSimulation(int day, int hour, int minute) {
        this.occupancyInfo = diningHall.getOccupancy(day, hour, minute);
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
        return diningHall.lineQueue.toString();
    }

    public String peopleEating() {
        return diningHall.seatingPq.toString();
    }
}
