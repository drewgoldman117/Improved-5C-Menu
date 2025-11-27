package improvedMenu;

public class Main {

    public static void main(String[] args) {

        SystemManager manager = new SystemManager();

        // Create a couple of users for testing
        manager.addUser(101);
        manager.addUser(102);

        // Add some menu items to test things
        manager.getHall().addMenuItem("Pancakes");
        manager.getHall().addMenuItem("Scrambled Eggs");

        // Simulate someone entering the hall
        manager.getHall().userEnter(101);

        // Output test info
        System.out.println("Occupants: " + manager.getHall().getOccupants());
        System.out.println("Menu: " + manager.getHall().getMenu());
    }
}
