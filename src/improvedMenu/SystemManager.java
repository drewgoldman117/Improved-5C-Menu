package improvedMenu;

import java.util.*;
import java.io.*;

public class SystemManager {

    private ArrayList<User> users;                 // all registered users
    private Map<Integer, ArrayList<Integer>> friends; // userId -> list of friend userIds
    private Map<Integer, ArrayList<String>> favorites; // userId -> favorite foods
    private DiningHall diningHall;

    public SystemManager() {
        users = new ArrayList<>();
        friends = new HashMap<>();
        favorites = new HashMap<>();
        diningHall = new DiningHall();
    }

    // ---------------------------------------------
    // USER MANAGEMENT
    // ---------------------------------------------
    public void generateUsers(int n) {
        for (int i = 0; i < n; i++) {
            User u = new User(i, "User" + i);
            users.add(u);

            friends.put(i, new ArrayList<>());
            favorites.put(i, new ArrayList<>());
        }

        // give each user 3 random friends
        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            ArrayList<Integer> fList = friends.get(i);
            while (fList.size() < 3) {
                int friendId = rand.nextInt(n);
                if (friendId != i && !fList.contains(friendId)) {
                    fList.add(friendId);
                }
            }
        }

        // give each user 2 favorite foods
        String[] foodPool = {"Pizza", "Pasta", "Tacos", "Curry", "Salad",
                             "Burrito", "Soup", "Sushi", "Fries"};

        for (int i = 0; i < n; i++) {
            ArrayList<String> fav = favorites.get(i);
            fav.add(foodPool[rand.nextInt(foodPool.length)]);
            fav.add(foodPool[rand.nextInt(foodPool.length)]);
        }

        System.out.println("Generated " + n + " fake users.");
    }

    public User findUserByName(String name) {
        for (User u : users) {
            if (u.getName().equalsIgnoreCase(name)) {
                return u;
            }
        }
        return null;
    }

    public int getUserCount() {
        return users.size();
    }

    // ---------------------------------------------
    // DATA LOADING
    // ---------------------------------------------
    public void loadScanData(String filename) {
        diningHall.loadData(filename);
    }

    // ---------------------------------------------
    // WAIT TIME CALCULATION
    // ---------------------------------------------
    public int getWaitTime(int day, int hour, int minute) {
        int occupancy = diningHall.getOccupancy(day, hour, minute);
        return diningHall.getWaitTime(occupancy);
    }

    // ---------------------------------------------
    // FRIENDS INSIDE SIMULATION
    // ---------------------------------------------
    public ArrayList<User> getFriendsInside(User u, int day, int hour, int minute) {
        ArrayList<User> inside = new ArrayList<>();
        ArrayList<Integer> fList = friends.get(u.getId());

        int cutoff = timeToBlock(hour, minute);

        for (int fId : fList) {
            if (friendIsInside(fId, day, cutoff)) {
                inside.add(users.get(fId));
            }
        }

        return inside;
    }

    // helper for "is inside"
    private boolean friendIsInside(int friendId, int day, int cutoffBlock) {
        ArrayList<ScanEvent> dayEvents = diningHall.getDayEvents(day);
        if (dayEvents == null) return false;

        for (ScanEvent e : dayEvents) {
            if (e.userId == friendId) {
                int block = timeToBlock(e.hour, e.minute);
                if (block <= cutoffBlock) return true;
            }
        }
        return false;
    }

    private int timeToBlock(int hour, int minute) {
        return hour * 6 + (minute / 10);
    }

    // ---------------------------------------------
    // FAVORITE FOODS
    // ---------------------------------------------
    public ArrayList<String> getFavoriteFoods(User u) {
        return favorites.get(u.getId());
    }
}
