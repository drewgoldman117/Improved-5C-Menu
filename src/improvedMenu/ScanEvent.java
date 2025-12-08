package improvedMenu;

public class ScanEvent {
    public int day;
    public int hour;
    public int minute;
    public int userId;
    // added by Ryo for DiningHall.java. Lmk if you have questions
    public int enteringTime;
    public int duration;
    public int leavingTime;

    public ScanEvent(int d, int h, int m, int u) {
        this.day = d;
        this.hour = h;
        this.minute = m;
        this.userId = u;
        this.enteringTime = h * 60 + m;
    }

    @Override
    public String toString() {
        // This will print: [487 at 7:00]
        return "[" + userId + " at " + hour + ":" + minute + "]";
    }
}
