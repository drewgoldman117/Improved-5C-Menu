package improvedMenu;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.time.*;

//TODO delete after implementation
public interface DiningHall{

    //final variables for days of the week
    public static final int MONDAY = 1;
    public static final int TUESDAY = 2;
    public static final int WEDNESDAY = 3;
    public static final int THURSDAY = 4;
    public static final int FRIDAY = 5;
    public static final int SATURDAY = 6;
    public static final int SUNDAY = 7;


    //final vars for meal times
    public static final int BREAKY = 1;
    public static final int LUNCHBRUNCH = 2;
    public static final int DINNER = 3;


    //------wait time methods-----

    /**
     * @param id integer id
     * adds user to the occupant priority queue
     * */
    public void userEnterLine(int id);

    /**
     * @return int of wait time in minutes 
     * uses last 10 minutes of enterance swipes to estimate wait with function (assuming it takes about 1 minute per person for food to be taken) 
    */
    public int getWaitTime();

    /**
     * @param id integer id
     * @return User object associated with the User removed from the queue (dining hall line)
     */
    public User removeFromLine(int id);

    //print out menu items for the day
    /**
     * @param day int1-7 to get day of the week
     * @return the menu as a string representation to be printed
     * */
    public String getMenu(int day); 

    /**
     * @param itemName string menu item name 
     * **/
    public void addMenuItem(String itemName);
    public String removeMenuItem(String itemName);


    //-----User account methods----- 
    //(will likely be in seperate file in final implementation)
    //assumes we have a User object that stores id number and a UserData object. UserData stores two arraylists, one for friend list and the other is a list of favorite foods
    
    /**
     * @param id integer id
     * adds user to the hashmap of account storage
     */
    public void addUser(int id);

    /**
     * @param id integer id
     * @return User object that is removed from the hashmap
     */
    public User removeUser(int id);

    //-----user/userdata object methods------
    /**
     * @param itemName string of food to be added
     * adds favorite food to the user within user data
     */
    public void addFood(String itemName);

    /**
     * @param itemName string of food to remove
     * @return string name of the food removed 
     */
    public String removeFood(String itemName);

    /**
     * @param id integer id
     * adds friend id to friend array list within user data
     * */
    public void addFriend(int id);

    /**
     * @param id integer id
     * @return int of id that was removed 
     * removes friend from user data array list based on the id
     */
    public int removeFriend(int id);

    /**
     * @return arraylist of friend id's within the dining hall
     * will not be displayed as integers, id will be used to search hash map as id is the key
     */
    public ArrayList<Integer> friendsIn();


    //----location methods ------
    //(will likely be in seperate interface in final implementation)
    //methods that allow us to know who is (estimated) to be in the dining hall at a given time
    //occupant object will hold id and exit time, this exit time will be a time value and will determine place in the 
    //occupant priority queue as the comparator within occupant will be comparing wait times


    /**
     * @param id integer id
     * adds id to occupy the dining hall (priority queue)
     */
    public void addOccupant(int id);

    /**
     * removes the next person based on the current time passed, uses current LocalTime method
     * @return integer id
     */
    public int exit();

    /**
     * @return priority queue of those in the dining hall
     */
    public PriorityQueue<Occupant> getOccupants();

    /**
     * @return integer of minutes to spend in dining hall to determine exit rate (function to be created)j
     * this will also determine priority queue placement (as minutes spent will be converted to a time value added from when they entered)
     */
    public int occupyTime();


    


}
