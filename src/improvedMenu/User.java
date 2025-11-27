package improvedMenu;

import java.util.ArrayList;
import java.util.List;

public class User {

    private int userID;
    private List<Integer> friends;
    private List<String> favoriteFoods;
    private boolean shareLocation;

    public User(int id) {
        this.userID = id;
        this.friends = new ArrayList<>();
        this.favoriteFoods = new ArrayList<>();
        this.shareLocation = true; // default to sharing location
    }

    public int getUserID() {
        return userID;
    }

    // manage friend list
    public void addFriend(int id) {
        friends.add(id);
    }

    public void removeFriend(int id) {
        friends.remove(Integer.valueOf(id));
    }

    public List<Integer> getFriends() {
        return friends;
    }

    // manage favorite foods
    public void addFavoriteFood(String item) {
        favoriteFoods.add(item);
    }

    public List<String> getFavoriteFoods() {
        return favoriteFoods;
    }

    // privacy controls
    public void setShareLocation(boolean share) {
        this.shareLocation = share;
    }

    public boolean isSharingLocation() {
        return shareLocation;
    }
}
