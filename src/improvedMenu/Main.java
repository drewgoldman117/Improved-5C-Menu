package improvedMenu;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        SystemManager system = new SystemManager();

        // Load users and scan data
        system.generateUsers(100);  // 100 fake users
        system.loadScanData("scans.csv"); // make sure this path is correct!

        System.out.println("Welcome to the Improved 5C Menu Simulator!");
        System.out.print("Enter your name: ");

        String name = in.nextLine().trim();
        User current = system.findUserByName(name);

        if (current == null) {
            System.out.println("Name not found. Creating new user profile...");
            current = new User(system.getUserCount(), name);  // adding a helper in User or SystemManager is optional
        }

        System.out.print("Enter day of week (1–7): ");
        int day = in.nextInt();

        System.out.print("Enter hour (0–23): ");
        int hour = in.nextInt();

        System.out.print("Enter minute (0,10,20,30,40,50): ");
        int minute = in.nextInt();

        System.out.println("\n--- Results ---");

        // WAIT TIME
        int wait = system.getWaitTime(day, hour, minute);
        System.out.println("Estimated wait time: " + wait + " minutes");

        // FRIENDS
        System.out.println("\nFriends currently inside:");
        ArrayList<User> friendsInside = system.getFriendsInside(current, day, hour, minute);

        if (friendsInside.isEmpty()) {
            System.out.println("None of your friends are inside right now.");
        } else {
            for (User f : friendsInside) {
                System.out.println("- " + f.getName());
            }
        }

        // FAVORITE FOODS
        System.out.println("\nYour favorite foods today:");
        for (String food : system.getFavoriteFoods(current)) {
            System.out.println("- " + food);
        }

        System.out.println("\nThank you for using the Improved 5C Menu Simulator!");
    }
}
