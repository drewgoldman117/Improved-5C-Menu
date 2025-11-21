package improvedMenu;

public interface UserData {
    
    public void addUser(int id);
    public User removeUser(int id);
    public void addFood(String itemName);
    public void addFriend(int id);
    public User removeFriend(int id);

}
