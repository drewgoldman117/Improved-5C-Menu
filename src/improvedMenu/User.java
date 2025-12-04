package improvedMenu;
import java.util.*;

/**
 * @author Gavin Honey
 * */

public class User {

    private int id; //unique id number
    private ArrayList<Integer> friends; //friends id numbers
    private ArrayList<String> favFoods; //strings of favorite foods to be check against the 

    //constructor that only specifies id
    public User(int id) {
        this.id = id;
        friends = new ArrayList<>();
        favFoods = new ArrayList<>();
    }

    //arraylist to intialize all attributes
    public User(int id, ArrayList<Integer> friends, ArrayList<String> foods){
        favFoods = foods;
        this.friends = friends;
        this.id = id;
    }

    //--- getters and setters ---//
    
    //returns id number
    public int getId() {
        return id;
    }

    //return friends arraylist
    public ArrayList<Integer> getFriends(){
        return friends;
    }

    //return favorite foods arraylist
    public ArrayList<String> getFoods(){
        return favFoods;
    }

    //adds favorite food to user, confirmation written in console
    /**
     * @param food string of food to be added
     */
    public void addFavFood(String food){
        favFoods.add(food);
        System.out.println("Food: " + food + " has been added to User " + id);
    }

    /** adds new friend id to user
     * @param id integer friend id to be added, must be a part of the known user pool
     */
    public void addFriend(int id){
        friends.add(id);
        System.out.println("Friend id: " + id + " has been added to User " + this.id);
    }


}
