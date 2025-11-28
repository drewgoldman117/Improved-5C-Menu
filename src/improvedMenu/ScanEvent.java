package improvedMenu;

public class ScanEvent {
    public int day;
    public int hour;
    public int minute;
    public int userId;

    public ScanEvent(int d, int h, int m, int u) {
        this.day = d;
        this.hour = h;
        this.minute = m;
        this.userId = u;
    }
}
