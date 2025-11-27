package improvedMenu;

import java.util.HashMap;
import java.util.Map;

public class SystemManager {

    private DiningHall hall;
    private Map<Integer, User> users;

    public SystemManager() {
        this.hall = new DiningHall("Frary");
        this.users = new HashMap<>();
    }

    // create or register a new user
    public void addUser(int id) {
        users.put(id, new User(id));
    }

    public User getUser(int id) {
        return users.get(id);
    }

    public DiningHall getHall() {
        return hall;
    }

    // placeholder for CSV loading logic
    public void loadScanData() {
        // will implement later
    }

    public void loadMenuData() {
        // will implement later
    }
}
