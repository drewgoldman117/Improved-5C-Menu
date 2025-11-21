package improvedMenu;

import java.util.ArrayList;

public interface DiningHall{

    public static final int NOTBUSY = 20;
    public static final int BUSY = 80;
    public static final int VERYBUSY = 120;

    public static final int NOTLONG = 20;
    public static final int LONG = 40;
    public static final int VERYLONG = 60;


    public static final int MONDAY = 1;
    public static final int TUESDAY = 2;

    //TODO: comment for javadocs!!! add other final vars
    
    //determines estimated time in dining hall based off of a function (to be created)
    /**
     * @param day 1-7 for day of the week
     * */
    public int occupyTime(int day, int mealTime);
    public void userEnter(int id);
    public ArrayList<Integer> getOccupants();

    //print in output threshold value 
    public void getBusyness();

    //uses last 10 minutes of enterance to estimate wait with thresholds 
    public int getWaitTime();



    //print out menu items for the day
    /**
     * @param day 1-7 to get day of the week
     * */
    public String getMenu(int day); 
    public void addMenuItem(String itemName);
    public String removeMenuItem(String itemName);

    


}
