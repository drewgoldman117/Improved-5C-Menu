package improvedMenu;
import java.util.*;

/**
 * This class represents a single user in our simulation.
 * Each user has a unique ID, list of IDs who are friends and a list of vaorite food items.
 *
 * @author @ghoney47
 */

public class User {

    private int id; //unique id number
    private ArrayList<Integer> friends; //friends id numbers
    private ArrayList<String> favFoods; //strings of favorite foods to be check against the 

    /**
     * Constructor that specifies ID only
     * @param id ID for the user
     */
    public User(int id) {
        this.id = id;
        friends = new ArrayList<>();
        favFoods = new ArrayList<>();
    }

    /**
     * Arraylist initializing all attributes
     * @param id user ID
     * @param friends list of friend IDs
     * @param foods list of favorite food items
     */
    public User(int id, ArrayList<Integer> friends, ArrayList<String> foods){
        favFoods = foods;
        this.friends = friends;
        this.id = id;
    }

    //--- getters and setters ---//
    
    /**
     * @return integer ID
     */
    public int getId() {
        return id;
    }

    /**
     * @return ArrayList of friend Integer IDs
     */
    public ArrayList<Integer> getFriends(){
        return friends;
    }

    /**
     * @return ArrayList of favorite food strings
     */
    public ArrayList<String> getFoods(){
        return favFoods;
    }

    
    /**adds favorite food to user
     * @param food string of food to be added
     */
    public void addFavFood(String food){
        favFoods.add(food);

        //testing
        //System.out.println("Food: " + food + " has been added to User " + id);
    }

    /** adds new friend id to user
     * @param id integer friend id to be added, must be a part of the known user pool
     */
    public void addFriend(int id){
        friends.add(id);

        //testing
        //System.out.println("Friend id: " + id + " has been added to User " + this.id);
    }


    @Override
    /**
     * Hashes based on ID
     * @return integer hashcode
     */
    public int hashCode(){
        return Integer.hashCode(id);
    }

    /**
     * Determines equality based on ID
     * @param o the object to compare to
     * @return True if IDs match, False otherwise
     */
    public boolean equals (Object o){
        if (o == this) {return true;}
        if (o == null) {return false;}
        if (o.getClass() != this.getClass()){return false;}
        User u = (User) o;

        return this.id == u.id;
    }

    /**
     * @return string representation of a user
     */
    public String toString(){
        String friends = "";
        String foods = "";

        for (int num : this.friends){
            friends += "User " + num + ", ";
        }

        for (String s : favFoods){
            foods += s + ", ";
        }

        return "|User " + id + "\n|Foods: " + foods + "\n|Friends: " + friends + "\n|";
    }

    /**
     * User testing
     * @param args
     */
    public static void main(String[] args) {
       User u1 = new User(0);
       u1.addFavFood("Burger");
       u1.addFavFood("Eggs");
       u1.addFavFood("Pizza");

       u1.addFriend(1);
       u1.addFriend(2);
       u1.addFriend(3);

       System.out.println(u1.toString());
    }

}
